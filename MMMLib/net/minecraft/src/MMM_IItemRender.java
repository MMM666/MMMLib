package net.minecraft.src;

import java.lang.reflect.Method;

/**
 * 特殊アイテムレンダラ用の識別インターフェース
 *
 */
public interface MMM_IItemRender {

	public boolean renderItem(EntityLiving pEntity, ItemStack pItemstack, int pIndex);
	public boolean renderItemInFirstPerson(float pDelta);
	public String getRenderTexture();

}
