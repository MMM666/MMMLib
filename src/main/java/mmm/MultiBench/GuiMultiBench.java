package mmm.MultiBench;

import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.inventory.GuiCrafting;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import cpw.mods.fml.client.FMLClientHandler;

public class GuiMultiBench extends GuiCrafting {

	public ContainerMultiBench multiContainer;


	public GuiMultiBench(InventoryPlayer par1InventoryPlayer, World par2World,
			int par3, int par4, int par5) {
		super(par1InventoryPlayer, par2World, par3, par4, par5);
		
		multiContainer = new ContainerMultiBench(par1InventoryPlayer, par2World, par3, par4, par5);
		inventorySlots = multiContainer;
//		par1EntityPlayer.openContainer = this.inventorySlots;
		
	}

	@SuppressWarnings("unchecked")
	@Override
	public void initGui() {
		super.initGui();
		buttonList.add(new GuiButton(10, guiLeft + 102, guiTop + 58, 30, 20, "<"));
		buttonList.add(new GuiButton(11, guiLeft + 132, guiTop + 58, 30, 20, ">"));
	}

	@Override
	protected void actionPerformed(GuiButton p_146284_1_) {
		if (p_146284_1_.id == 10) {
//			multiContainer.addIndex(-1);
			MultiBench.sendChangeIndex(-1);
		} else if (p_146284_1_.id == 11) {
//			multiContainer.addIndex(1);
			MultiBench.sendChangeIndex(1);
		} else {
			super.actionPerformed(p_146284_1_);
		}
	}

	public static void getPacket(int pIndex, ItemStack pItemStack) {
		// アイテムスタックを受信
		EntityPlayerSP lplayer = FMLClientHandler.instance().getClientPlayerEntity();
		if (lplayer.openContainer instanceof ContainerMultiBench) {
			ContainerMultiBench lcmb = (ContainerMultiBench)lplayer.openContainer;
			lcmb.index = pIndex;
//			lcmb.putStackInSlot(0, pItemStack);
			lcmb.craftResult.setInventorySlotContents(0, pItemStack);
		}
	}

}
