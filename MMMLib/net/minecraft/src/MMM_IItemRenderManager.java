package net.minecraft.src;

/**
 * アイテム用の特殊レンダーに継承させるインターフェース。
 * 現状、継承させていなくてもメソッドがItemに記述されていれば動作する。
 */
public interface MMM_IItemRenderManager {

	public static final int VM_FIRST_PERSON		= 0;
	public static final int VM_THERD_PERSON		= 1;
	public static final int VM_THERD_PERSON_INV	= 2;


	/**
	 * アイテムの描画のみ、位置補正はしない。
	 * @param pEntity
	 * @param pItemStack
	 * @param pIndex
	 * @return
	 */
	public boolean renderItem(Entity pEntity, ItemStack pItemStack, int pIndex);
//	public boolean renderItemInFirstPerson(float pDeltaTimepRenderPhatialTick, MMM_ItemRenderer pItemRenderer);
	public boolean renderItemInFirstPerson(Entity pEntity, ItemStack pItemStack, float pDeltaTimepRenderPhatialTick);
	public boolean renderItemWorld();
	public ResourceLocation getRenderTexture();
	public boolean isRenderItem();
	public boolean isRenderItemInFirstPerson();
	public boolean isRenderItemWorld();

}
