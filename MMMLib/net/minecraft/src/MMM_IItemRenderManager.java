package net.minecraft.src;

/**
 * アイテム用の特殊レンダーに継承させるインターフェース。
 * 現状、継承させていなくてもメソッドがItemに記述されていれば動作する。
 */
public interface MMM_IItemRenderManager {

	public boolean renderItem(EntityLiving pEntityLiving, ItemStack pItemStack, int pIndex);
	public boolean renderItemInFirstPerson(float pDeltaTimepRenderPhatialTick, MMM_ItemRenderer pItemRenderer);
	public String getRenderTexture();
	public boolean isRenderItemWorld();

}
