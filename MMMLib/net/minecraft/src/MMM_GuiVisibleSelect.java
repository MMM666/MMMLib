package net.minecraft.src;

import org.lwjgl.opengl.EXTRescaleNormal;
import org.lwjgl.opengl.GL11;

public class MMM_GuiVisibleSelect extends GuiScreen {

	public GuiScreen ownerScreen;
	public MMM_GuiToggleButton select[] = new MMM_GuiToggleButton[32];
	public MMM_EntityCaps target;
	public Entity owner;
	protected float fyaw;
	protected float fpitch;
	protected int flastx;
	protected int flasty;


	public MMM_GuiVisibleSelect(GuiScreen pOwnerScreen, MMM_EntityCaps pTarget) {
		ownerScreen = pOwnerScreen;
		target = pTarget;
		owner = (Entity)target.getCapsValue(MMM_IModelCaps.caps_Entity);
		fyaw = 0F;
		fpitch = 0F;
	}

	@Override
	public void initGui() {
		String lswitch = (String)target.getCapsValue(MMM_IModelCaps.caps_PartsStrings);
		if (lswitch != null) {
			String lbutton[] = lswitch.split(",");
			int lvisible = MMM_ModelCapsHelper.getCapsValueInt(target, MMM_IModelCaps.caps_PartsVisible);
			
			for (int li = 0; li < 32 && lbutton.length > li; li++) {
				if (lbutton[li] != null) {
//					select[li] = new MMM_GuiToggleButton(li, width - 80 * (3 - li / 12), (li % 12) * 20, 80, 20, lbutton[li]);
					select[li] = new MMM_GuiToggleButton(li, width - 80 * (1 + li / 12), (li % 12) * 20, 80, 20, lbutton[li]);
					if ((lvisible & (1 << li)) > 0) {
						select[li].isDown = true;
					}
					buttonList.add(select[li]);
				}
			}
		}
		initAddetionalButton();
	}

	/**
	 * ì∆é©É{É^ÉìÇí«â¡Ç∑ÇÈÅB
	 */
	public void initAddetionalButton() {
		buttonList.add(new MMM_GuiToggleButton(100, width - 80 * (1 + 34 / 12), (34 % 12) * 20, 80, 20, "Back"));
	}

	@Override
	public void drawScreen(int par1, int par2, float par3) {
		drawDefaultBackground();
		super.drawScreen(par1, par2, par3);
		drawString(fontRenderer, "Visible", 10, 10, 0xffffff);
		if (owner != null) {
			GL11.glPushMatrix();
			GL11.glEnable(EXTRescaleNormal.GL_RESCALE_NORMAL_EXT);
			GL11.glEnable(GL11.GL_COLOR_MATERIAL);
			GL11.glEnable(GL11.GL_DEPTH_TEST);
			GL11.glColor3f(1.0F, 1.0F, 1.0F);
			RenderHelper.enableGUIStandardItemLighting();
			OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, 240.0F, 240.0F);
			GL11.glTranslatef(width / 2F - 115F, height / 2F - 30F, 100F);
			GL11.glScalef(60F, -60F, 60F);
			GL11.glRotatef(fpitch, 1F, 0F, 0F);
			GL11.glRotatef(fyaw, 0F, 1F, 0F);
			
			RenderManager.instance.renderEntityWithPosYaw(owner, 0.0D, 0.0D, 0.0D, 0.0F, 1.0F);
			
			GL11.glDisable(EXTRescaleNormal.GL_RESCALE_NORMAL_EXT);
			GL11.glPopMatrix();
		}
	}

	@Override
	protected void actionPerformed(GuiButton par1GuiButton) {
		if (par1GuiButton.id < 32) {
			((MMM_GuiToggleButton)par1GuiButton).isDown = !((MMM_GuiToggleButton)par1GuiButton).isDown;
			
			int lval = 0;
			for (int li = 0; li < 32; li++) {
				if (select[li] != null && select[li].isDown) {
					lval |= (1 << li);
				}
			}
			target.setCapsValue(MMM_IModelCaps.caps_PartsVisible, lval);
		}
		if (par1GuiButton.id == 100) {
			mc.displayGuiScreen(new MMM_GuiTextureSelect(this,
					(MMM_ITextureEntity)target.getCapsValue(MMM_IModelCaps.caps_TextureEntity),
					0xffff, false));
		}
	}

	@Override
	protected void mouseClicked(int par1, int par2, int par3) {
		super.mouseClicked(par1, par2, par3);
		flastx = par1 - (int)fyaw;
		flasty = par2 - (int)fpitch;
	}

	@Override
	protected void mouseClickMove(int par1, int par2, int par3, long par4) {
		super.mouseClickMove(par1, par2, par3, par4);
		fyaw = (par1 - flastx) % 360F;
		fpitch = (par2 - flasty) % 360F;
	}

}
