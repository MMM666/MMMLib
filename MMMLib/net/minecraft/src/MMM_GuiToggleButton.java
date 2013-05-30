package net.minecraft.src;

import net.minecraft.client.Minecraft;

public class MMM_GuiToggleButton extends GuiButton {

	public boolean isDown = false;

	public MMM_GuiToggleButton(int par1, int par2, int par3, String par4Str) {
		super(par1, par2, par3, par4Str);
	}

	public MMM_GuiToggleButton(int par1, int par2, int par3, int par4,
			int par5, String par6Str) {
		super(par1, par2, par3, par4, par5, par6Str);
	}

	protected int getHoverState(boolean par1) {
		if (!enabled || isDown) {
			return 0;
		} else if (par1) {
			return 2;
		}
		return 1;
	}

}
