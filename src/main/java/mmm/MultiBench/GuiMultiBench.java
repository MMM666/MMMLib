package mmm.MultiBench;

import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.gui.inventory.GuiCrafting;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

import org.lwjgl.opengl.GL11;

public class GuiMultiBench extends GuiContainer {

	protected static final ResourceLocation craftingTableGuiTextures =
			new ResourceLocation("textures/gui/container/crafting_table.png");


	public GuiMultiBench(InventoryPlayer pInventoryPlayer, World pWorld, int pX, int pY, int pZ) {
		super(new ContainerMultiBench(pInventoryPlayer, pWorld, pX, pY, pZ));
	}

	@Override
	protected void drawGuiContainerForegroundLayer(int pX, int pY) {
		fontRendererObj.drawString(I18n.format("container.crafting", new Object[0]), 28, 6, 0x404040);
		fontRendererObj.drawString(I18n.format("container.inventory", new Object[0]), 8, ySize - 96 + 2, 0x404040);
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float p_146976_1_, int p_146976_2_, int p_146976_3_) {
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		mc.getTextureManager().bindTexture(craftingTableGuiTextures);
		int k = (width - xSize) / 2;
		int l = (height - ySize) / 2;
		this.drawTexturedModalRect(k, l, 0, 0, xSize, ySize);
	}

	@Override
	public void initGui() {
		super.initGui();
		buttonList.add(new GuiButton(10, guiLeft + 102, guiTop + 58, 30, 20, "<"));
		buttonList.add(new GuiButton(11, guiLeft + 132, guiTop + 58, 30, 20, ">"));
	}

	@Override
	protected void actionPerformed(GuiButton p_146284_1_) {
		// TODO Auto-generated method stub
		super.actionPerformed(p_146284_1_);
	}

}
