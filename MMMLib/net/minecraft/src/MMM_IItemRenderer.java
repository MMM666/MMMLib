package net.minecraft.src;

import net.minecraft.client.Minecraft;

/**
 * ItemRendererが置き換わっているかどうかを判定するためのインターフェース。
 */
public interface MMM_IItemRenderer {

	public Minecraft getMC();
	public ItemStack getItemToRender();
	public float getEquippedProgress();
	public float getPrevEquippedProgress();

}
