package mmm.lib.destroyAll;

import io.netty.buffer.ByteBuf;

/**
 * 特殊な破壊パターンを実装したい場合は、このクラスを上書きし、<br>
 * IDestroyAll.getDestroyAllData() で返す。
 *
 */
public class DestroyAllData extends DestroyAllDataBase {

	public static final byte Flag_isUnder			= 0x01;
	public static final byte Flag_isOtherDirection	= 0x02;
	public static final byte Flag_isBreakLeaves		= 0x04;
	public static final byte Flag_isTheBig			= 0x08;
	public static final byte Flag_isAdvMeta			= 0x10;
	
	public boolean isUnder;
	public boolean isOtherDirection;
	public boolean isBreakLeaves;
	public boolean isTheBig;
	public boolean isAdvMeta;


	@Override
	public int getFlags() {
		int lflag = 0;
		lflag |= isUnder ? Flag_isUnder : 0;
		lflag |= isOtherDirection ? Flag_isOtherDirection : 0;
		lflag |= isBreakLeaves ? Flag_isBreakLeaves : 0;
		lflag |= isTheBig ? Flag_isTheBig : 0;
		lflag |= isAdvMeta ? Flag_isAdvMeta : 0;
		return lflag;
	}

	@Override
	public void setFlags() {
		isUnder = (flag & Flag_isUnder) > 0;
		isOtherDirection = (flag & Flag_isOtherDirection) > 0;
		isBreakLeaves = (flag & Flag_isBreakLeaves) > 0;
		isTheBig = (flag & Flag_isTheBig) > 0;
		isAdvMeta = (flag & Flag_isAdvMeta) > 0;
	}

	@Override
	public DestroyAllIdentificator getDestroyAllIdentificator(ByteBuf pBuf) {
		int ladmeta = isAdvMeta ? metadata : 0;
		int ltometa = ladmeta | (isOtherDirection ? 0xc0 : 0x00);
		int lcometa = ladmeta | (isBreakLeaves ? 0x80 : 0x00);
		return new DestroyAllIdentificator(pBuf, ltometa, lcometa);
	}

	/**
	 * 一括破壊処理として呼ばれる
	 */
	@Override
	public void DestroyAll() {
		if (!isBreakLeaves) {
			identificator.chain = null;
		}
		super.DestroyAll();
	}

	@Override
	protected void checkAround(int pX, int pY, int pZ, int pCount) {
		// 周囲をチェックして作業キューへ入れる。
		checkBlock(pX + 1, pY, pZ, pCount);
		checkBlock(pX - 1, pY, pZ, pCount);
		checkBlock(pX, pY, pZ + 1, pCount);
		checkBlock(pX, pY, pZ - 1, pCount);
		checkBlock(pX, pY + 1, pZ, pCount);
		checkBlock(pX, pY - 1, pZ, pCount);
		if (isTheBig) {
			checkBlock(pX + 1, pY + 1, pZ + 1, pCount);
			checkBlock(pX + 1, pY + 1, pZ, pCount);
			checkBlock(pX + 1, pY + 1, pZ - 1, pCount);
			checkBlock(pX, pY + 1, pZ + 1, pCount);
			checkBlock(pX, pY + 1, pZ - 1, pCount);
			checkBlock(pX - 1, pY + 1, pZ + 1, pCount);
			checkBlock(pX - 1, pY + 1, pZ, pCount);
			checkBlock(pX - 1, pY + 1, pZ - 1, pCount);
		}
	}

	@Override
	protected void checkAroundChain(int pX, int pY, int pZ, int pCount) {
		// 周囲をチェックして作業キューへ入れる。
		if (pCount < maxChain) {
			checkBlockChain(pX + 1, pY, pZ, pCount);
			checkBlockChain(pX - 1, pY, pZ, pCount);
			checkBlockChain(pX, pY, pZ + 1, pCount);
			checkBlockChain(pX, pY, pZ - 1, pCount);
			checkBlockChain(pX, pY + 1, pZ, pCount);
			if (isUnder && pY <= oy) {
				checkBlockChain(pX, pY - 1, pZ, pCount);
			}
		}
	}

	@Override
	protected boolean checkBlock(int pX, int pY, int pZ, int pCount) {
		if (Math.abs(pX - ox) > rangeWidth) return false;
		if (Math.abs(pZ - oz) > rangeWidth) return false;
		if (Math.abs(pY - oy) > rangeHeight) return false;
		if (!isUnder && ((pY - oy) < 0)) return false;
		
		if (identificator.isTargetBlock(breaker.worldObj, pX, pY, pZ)) {
			addDestroyList(pX, pY, pZ, 0);
			breaker.theItemInWorldManager.tryHarvestBlock(pX, pY, pZ);
			return true;
		}
		return false;
	}

}
