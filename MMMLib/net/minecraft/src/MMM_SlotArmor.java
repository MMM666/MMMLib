package net.minecraft.src;

import java.lang.reflect.InvocationTargetException;

public class MMM_SlotArmor extends SlotArmor {

	protected final Container parent;



	MMM_SlotArmor(Container par1Container, IInventory par2IInventory,
			int par3, int par4, int par5, int par6) {
		super(null, par2IInventory, par3, par4, par5, par6);
		this.parent = par1Container;
	}

	public boolean isItemValid(ItemStack par1ItemStack) {
		if (par1ItemStack == null) return false;
		Item litem = par1ItemStack.getItem();
		// Ç‡Å[
		if (MMM_Helper.isForge) {
			try {
				MMM_Helper.getNameOfClass("Item").getMethod("isValidArmor", ItemStack.class, int.class).invoke(litem, par1ItemStack, armorType);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		if (litem instanceof ItemArmor) {
			return ((ItemArmor)litem).armorType == armorType;
		}
		if (litem.itemID == Block.pumpkin.blockID || litem.itemID == Item.skull.itemID) {
			return armorType == 0;
		}
		return false;
	}

}
