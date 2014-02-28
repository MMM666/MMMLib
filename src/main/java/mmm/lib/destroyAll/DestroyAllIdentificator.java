package mmm.lib.destroyAll;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;

import java.util.ArrayList;
import java.util.List;

import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;

/**
 * 一括破壊対象を記録するコンテナ。
 *
 */
public class DestroyAllIdentificator {

	public class TargetBlock {
		public Block block;
		public int metadata;
		
		public TargetBlock(Block pBlock, int pMetadata) {
			block = pBlock;
			metadata = pMetadata;
		}
		public boolean equals(Block pBlock, int pMetadata) {
			return block == pBlock && metadata == pMetadata;
		}
		public boolean isTargetBlock(Block pBlock, int pMetadata) {
			if (block == pBlock) {
				int lmask = 0x0f ^ ((metadata >>> 4) & 0x0f);
				return (pMetadata & lmask) == (metadata & lmask);
			}
			return false;
		}
		public String toString() {
			return GameRegistry.findUniqueIdentifierFor(block).toString() + "/" + String.format("0x%02x", metadata);
		}
		public byte[] toByte() {
			ByteBuf lbuf = Unpooled.buffer(5);
			lbuf.writeInt(Block.getIdFromBlock(block));
			lbuf.writeByte(metadata);
			return lbuf.array();
		}
		public void writeByteBuf(ByteBuf pByteBuf) {
			pByteBuf.writeInt(Block.getIdFromBlock(block));
			pByteBuf.writeByte(metadata);
		}
	}

	protected List<TargetBlock> targets;
	protected DestroyAllIdentificator chain;


	public static int getInt(String pString) {
		pString = pString.trim();
		if (pString.startsWith("0x")) {
			return Integer.parseInt(pString.substring(2), 16);
		} else {
			return Integer.parseInt(pString);
		}
	}


	public DestroyAllIdentificator() {
		chain = null;
		targets = new ArrayList<TargetBlock>();
	}

	public DestroyAllIdentificator(String pParam) {
		this();
		// 文字列の解析
		if (pParam.startsWith("\"")) {
			pParam = pParam.substring(1, pParam.length() - 1);
		}
		String lchain[] = pParam.split("=");
		if (lchain.length > 0) {
			for (String lls : lchain[0].split(";")) {
				String lts[] = lls.split("/");
				if (lts.length > 0) {
					Block lblock;
					if (lts[0].startsWith("#")) {
						lblock = Block.getBlockById(getInt(lts[0].substring(1)));
					} else {
						lblock = (Block)Block.blockRegistry.getObject(lts[0]);
					}
					int lmetadata = 0;
					if (lts.length > 1) {
						lmetadata = getInt(lts[1]);
					}
					add(lblock, lmetadata);
				}
			}
		}
		if (lchain.length > 1) {
			chain = new DestroyAllIdentificator(lchain[1]);
		}
	}

	public DestroyAllIdentificator(ByteBuf pBuf, int pTargetOrMeta, int pChainOrMeta) {
		this();
		// Target
		if (pBuf.isReadable(4)) {
			for (int li = pBuf.readInt(); li > 0; li--) {
				add(pBuf.readInt(), pBuf.readByte() | pTargetOrMeta);
			}
		}
		// Chain
		if (pBuf.isReadable(4)) {
			int li = pBuf.readInt();
			if (li > 0) {
				chain = new DestroyAllIdentificator();
				for (; li > 0; li--) {
					chain.add(pBuf.readInt(), pBuf.readByte() | pChainOrMeta);
				}
			}
		}
	}


	/**
	 * 登録ブロックを全削除。いらん？
	 */
	public void clear() {
		targets.clear();
	}

	/**
	 * ブロックをグループに追加する。
	 * @param pBlock
	 * @param pMetadata
	 */
	public void add(Block pBlock, int pMetadata) {
		if (pBlock != Blocks.air && !isTargetBlock(pBlock, pMetadata)) {
			targets.add(new TargetBlock(pBlock, pMetadata));
		}
	}
	public void add(int pBlockID, int pMetadata) {
//		DestroyAllManager.Debug("add:%d/%d", pBlockID, pMetadata);
		add(Block.getBlockById(pBlockID), pMetadata);
	}

	public boolean delete(Block pBlock, int pMetadata) {
		for (TargetBlock ltb : targets) {
			if (ltb.equals(pBlock, pMetadata)) {
				targets.remove(ltb);
				return true;
			}
		}
		return false;
	}

	/**
	 * チェインするブロックのコンテナを設定する。<br>
	 * 主に葉っぱ。
	 * @param pDBI
	 */
	public void setChain(DestroyAllIdentificator pDBI) {
		chain = pDBI;
	}

	/**
	 * 指定されたブロックが対象かどうかを判定する。
	 * @param pBlock
	 * @param pMetadata
	 * @return
	 */
	public boolean isTargetBlock(Block pBlock, int pMetadata) {
		for (TargetBlock ltb : targets) {
			if (ltb.isTargetBlock(pBlock, pMetadata)) {
				return true;
			}
		}
		return false;
	}
	public boolean isTargetBlock(World pWorld, int pX, int pY, int pZ) {
		return isTargetBlock(pWorld.getBlock(pX, pY, pZ), pWorld.getBlockMetadata(pX, pY, pZ));
	}

	public boolean isChainBlock(Block pBlock, int pMetadata) {
		if (chain != null) {
			return chain.isTargetBlock(pBlock, pMetadata);
		}
		return false;
	}
	public boolean isChainBlock(World pWorld, int pX, int pY, int pZ) {
		return isChainBlock(pWorld.getBlock(pX, pY, pZ), pWorld.getBlockMetadata(pX, pY, pZ));
	}

	/**
	 * 内容を文字列にして返す。
	 * @return
	 */
	public String toString() {
		String ls = "";
		for (TargetBlock ltb : targets) {
			if (!ls.isEmpty()) ls += ";";
			ls += ltb.toString();
		}
		if (chain != null) {
			ls += "=" + chain.toString();
		}
		return ls;
	}

	/**
	 * パケット送信用にバイトデータを作成。
	 * @return
	 */
	public byte[] toByte() {
		ByteBuf lbuf = Unpooled.buffer(4 + targets.size() * 5);
		lbuf.writeInt(targets.size());
		for (TargetBlock ltb : targets) {
			lbuf.writeBytes(ltb.toByte());
		}
		return lbuf.array();
	}

	/**
	 * パケット送信用にデータを書き込む
	 * @param pByteBuf
	 */
	public void writeByteBuf(ByteBuf pByteBuf) {
		pByteBuf.writeInt(targets.size());
		for (TargetBlock ltb : targets) {
			ltb.writeByteBuf(pByteBuf);
		}
	}

}
