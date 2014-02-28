package mmm.lib.destroyAll;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;

public interface IDestroyAll {

	/**
	 * 一括破壊のシグナルを受け取った時に実行する処理。
	 * サーバー側の動作。
	 * @return
	 */
//	public boolean destroyAll(DestroyData pDData);
	public DestroyAllData getDestroyAllData();

	/**
	 * アイテムのダメージ処理用にsuperを記述する関数。
	 */
	public boolean superOnBlockStartBreak(ItemStack itemstack, int X, int Y, int Z, EntityPlayer player);

	/**
	 * 
	 * @param pBlockIDs
	 * @param pMetadatas
	 */
	public void setTargets(DestroyAllIdentificator[] pBlocks);

}
