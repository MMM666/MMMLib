package mmm.lib.destroyAll;

import io.netty.buffer.ByteBuf;

import java.util.LinkedList;

import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayerMP;

/**
 * 特殊な破壊パターンを実装したい場合は、このクラスを上書きし、<br>
 * IDestroyAll.getDestroyAllData() で返す。
 *
 */
public class DestroyAllDataBase {

	protected class DestroyPos {
		public int X;
		public int Y;
		public int Z;
		public int count;
	
		public DestroyPos(int pX, int pY, int pZ, int pCount) {
			X = pX;
			Y = pY;
			Z = pZ;
			count = pCount;
		}
	
		@Override
		public boolean equals(Object obj) {
			if (obj instanceof DestroyPos) {
				DestroyPos ldo = (DestroyPos)obj;
				return (X == ldo.X) && (Y == ldo.Y) && (Z == ldo.Z);
			}
			return false;
		}
	
	}

	public EntityPlayerMP breaker;
	public int rangeWidth;
	public int rangeHeight;
	public int maxChain = 32000;
	public int ox;
	public int oy;
	public int oz;
	public Block block;
	public int metadata;
	public int flag;
	public DestroyAllIdentificator identificator;
	
	public LinkedList<DestroyPos> targets;


	@Override
	public String toString() {
		return String.format("%s; w:%d, h:%d, c:%d; pos:%d, %d, %d; %s/0x%02x; flag:%02x; %s",
				breaker.toString(), rangeWidth, rangeHeight, maxChain, ox, oy, oz,
				block.toString(), metadata, flag, identificator.toString());
	}

	public int getFlags() {
		return flag;
	};

	/**
	 * 送信時に書き込まれるデータ
	 * @param pBuf
	 */
	public void writeByteBuf(ByteBuf pBuf) {
		// EntityID
//		pBuf.writeInt(breaker.getEntityId());
		// Range W H
		pBuf.writeInt(rangeWidth);
		pBuf.writeInt(rangeHeight);
		pBuf.writeInt(maxChain);
		// Target Pos
		pBuf.writeInt(ox);
		pBuf.writeInt(oy);
		pBuf.writeInt(oz);
		// Target Block
		pBuf.writeInt(Block.getIdFromBlock(block));
		pBuf.writeByte(metadata);
		// Flags
		pBuf.writeByte(getFlags());
		// TargetBlocks
		if (identificator != null) {
			identificator.writeByteBuf(pBuf);
		}
		// ChainBlocks
		if (identificator.chain != null) {
			identificator.chain.writeByteBuf(pBuf);
		}
	}

	public void setFlags() {}

	/**
	 * 受信時に読み取るデータ
	 * @param pPlayer
	 * @param pBuf
	 */
	public void readbyteBuf(EntityPlayerMP pPlayer, ByteBuf pBuf) {
		// pBuf.readInt();
		breaker = pPlayer;
		rangeWidth	= pBuf.readInt();
		rangeHeight	= pBuf.readInt();
		maxChain	= pBuf.readInt();
		ox = pBuf.readInt();
		oy = pBuf.readInt();
		oz = pBuf.readInt();
		block = Block.getBlockById(pBuf.readInt());
		metadata = (int)pBuf.readByte();
		flag = pBuf.readByte();
		setFlags();
		
		identificator = getDestroyAllIdentificator(pBuf);
		
		targets = new LinkedList<DestroyAllDataBase.DestroyPos>();
	}

	public DestroyAllIdentificator getDestroyAllIdentificator(ByteBuf pBuf) {
		return new DestroyAllIdentificator(pBuf, 0, 0);
	}

	/**
	 * 一括破壊処理として呼ばれる
	 */
	public void DestroyAll() {
		// 標準破壊処理
		targets.clear();
		
		checkAround(ox, oy, oz, 0);
		if (identificator.chain != null) {
			checkAroundChain(ox, oy, oz, 0);
		}
		
		DestroyPos ldp;
		while ((ldp = targets.poll()) != null) {
			if (ldp.count == 0) {
				checkAround(ldp.X, ldp.Y, ldp.Z, 0);
			}
			if (identificator.chain != null) {
				checkAroundChain(ldp.X, ldp.Y, ldp.Z, ldp.count);
			}
		}
	}

	protected void checkAround(int pX, int pY, int pZ, int pCount) {
		// 周囲をチェックして作業キューへ入れる。
		if ((pX - ox) < rangeWidth) {
			checkBlock(pX + 1, pY, pZ, pCount);
		}
		if ((ox - pX) < rangeWidth) {
			checkBlock(pX - 1, pY, pZ, pCount);
		}
		if ((pZ - oz) < rangeWidth) {
			checkBlock(pX, pY, pZ + 1, pCount);
		}
		if ((oz - pZ) < rangeWidth) {
			checkBlock(pX, pY, pZ - 1, pCount);
		}
		if ((pY - oy) < rangeHeight) {
			checkBlock(pX, pY + 1, pZ, pCount);
		}
		if ((oy - pY) < rangeHeight) {
			checkBlock(pX, pY - 1, pZ, pCount);
		}
	}
	protected void checkAroundChain(int pX, int pY, int pZ, int pCount) {
		// 周囲をチェックして作業キューへ入れる。
		if (pCount < maxChain) {
			checkBlockChain(pX + 1, pY, pZ, pCount);
			checkBlockChain(pX - 1, pY, pZ, pCount);
			checkBlockChain(pX, pY, pZ + 1, pCount);
			checkBlockChain(pX, pY, pZ - 1, pCount);
			checkBlockChain(pX, pY + 1, pZ, pCount);
			checkBlockChain(pX, pY - 1, pZ, pCount);
		}
	}

	protected boolean checkBlock(int pX, int pY, int pZ, int pCount) {
		if (identificator.isTargetBlock(breaker.worldObj, pX, pY, pZ)) {
			addDestroyList(pX, pY, pZ, 0);
			breaker.theItemInWorldManager.tryHarvestBlock(pX, pY, pZ);
			return true;
		}
		return false;
	}
	protected boolean checkBlockChain(int pX, int pY, int pZ, int pCount) {
		if (identificator.chain.isTargetBlock(breaker.worldObj, pX, pY, pZ)) {
			addDestroyList(pX, pY, pZ, pCount + 1);
			breaker.theItemInWorldManager.tryHarvestBlock(pX, pY, pZ);
			return true;
		}
		return false;
	}

	protected boolean addDestroyList(int pX, int pY, int pZ, int pCount) {
		// 重複チェックしながらスタック
		DestroyPos lpos = new DestroyPos(pX, pY, pZ, pCount);
		if (targets.contains(lpos)) {
			return false;
		}
		targets.offer(lpos);
//		DestroyAllManager.Debug("append:%d,%d,%d,%d", pX, pY, pZ, pCount);
		return true;
	}

}
