package net.minecraft.src;

public class MMM_SlotArmor extends SlotArmor {

	protected final Container parent;



	MMM_SlotArmor(Container par1Container, IInventory par2IInventory,
			int par3, int par4, int par5, int par6) {
		super(null, par2IInventory, par3, par4, par5, par6);
		this.parent = par1Container;
	}

	public boolean isItemValid(ItemStack par1ItemStack) {
		return par1ItemStack == null ?
				false : (par1ItemStack.getItem() instanceof ItemArmor ?
						((ItemArmor) par1ItemStack.getItem()).armorType == this.armorType
						: (par1ItemStack.getItem().itemID != Block.pumpkin.blockID
								&& par1ItemStack.getItem().itemID != Item.skull.itemID ?
										false : this.armorType == 0));
	}

}
