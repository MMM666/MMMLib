package net.minecraft.src;

import java.lang.reflect.Method;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;

import net.minecraft.client.Minecraft;

public class MMM_ItemRendererHD extends ItemRendererHD implements MMM_IItemRenderer {

	// プライベート変数を使えるように
	public Minecraft mc;
	public ItemStack itemToRender;
	public float equippedProgress;
	public float prevEquippedProgress;


	public MMM_ItemRendererHD(Minecraft minecraft) {
		super(minecraft);
		
		mc = minecraft;
	}

	@Override
	public Minecraft getMC() {
		return mc;
	}

	@Override
	public ItemStack getItemToRender() {
		return itemToRender;
	}

	@Override
	public float getEquippedProgress() {
		return equippedProgress;
	}

	@Override
	public float getPrevEquippedProgress() {
		return prevEquippedProgress;
	}

	@Override
	public void renderItem(EntityLiving entityliving, ItemStack itemstack, int i) {
		Item litem = itemstack.getItem();
		if (MMM_ItemRenderManager.isEXRender(litem)) {
			MMM_ItemRenderManager lii = MMM_ItemRenderManager.getEXRender(litem);
			String ltex = lii.getRenderTexture();
			if (ltex != null) {
				this.mc.renderEngine.func_98187_b(ltex);
			}
			if (lii.renderItem(entityliving, itemstack, i)) {
				if (itemstack != null && itemstack.hasEffect() && i == 0) {
//					float var19 = (float)(Minecraft.getSystemTime() % 3000L) / 3000.0F * 8.0F;
					float var19 = (float)(Minecraft.getSystemTime() % 65536L) / 3000.0F * 8.0F;
					this.mc.renderEngine.func_98187_b("%blur%/misc/glint.png");
					GL11.glEnable(GL11.GL_BLEND);
					float var20 = 0.5F;
					GL11.glColor4f(var20, var20, var20, 1.0F);
					GL11.glDepthFunc(GL11.GL_EQUAL);
					GL11.glDepthMask(false);
					
					for (int var21 = 0; var21 < 2; ++var21) {
						GL11.glDisable(GL11.GL_LIGHTING);
						float var22 = 0.76F;
						GL11.glColor4f(0.5F * var22, 0.25F * var22, 0.8F * var22, 1.0F);
						GL11.glBlendFunc(GL11.GL_SRC_COLOR, GL11.GL_ONE);
						GL11.glMatrixMode(GL11.GL_TEXTURE);
						GL11.glLoadIdentity();
						float var23 = var19 * (0.001F + (float)var21 * 0.003F) * 20.0F;
						float var24 = 0.33333334F;
						GL11.glScalef(var24, var24, var24);
						GL11.glRotatef(30.0F - (float)var21 * 60.0F, 0.0F, 0.0F, 1.0F);
						GL11.glTranslatef(0.0F, var23, 0.0F);
						GL11.glMatrixMode(GL11.GL_MODELVIEW);
						lii.renderItem(entityliving, itemstack, i);
					}
					
					GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
					GL11.glMatrixMode(GL11.GL_TEXTURE);
					GL11.glDepthMask(true);
					GL11.glLoadIdentity();
					GL11.glMatrixMode(GL11.GL_MODELVIEW);
					GL11.glEnable(GL11.GL_LIGHTING);
					GL11.glDisable(GL11.GL_BLEND);
					GL11.glDepthFunc(GL11.GL_LEQUAL);
				}
				return;
			}
		}
		super.renderItem(entityliving, itemstack, i);
	}

	@Override
	public void renderItemInFirstPerson(float f) {
		itemToRender = null;
		equippedProgress = 0.0F;
		prevEquippedProgress = 0.0F;
		
		try {
			// ローカル変数を確保
			itemToRender = (ItemStack)ModLoader.getPrivateValue(ItemRenderer.class, this, 1);
			equippedProgress = (Float)ModLoader.getPrivateValue(ItemRenderer.class, this, 2);
			prevEquippedProgress = (Float)ModLoader.getPrivateValue(ItemRenderer.class, this, 3);
		} catch (Exception e) {
		}
		
		if (itemToRender != null) {
			Item litem = itemToRender.getItem();
			if (MMM_ItemRenderManager.isEXRender(litem)) {
				MMM_ItemRenderManager lim = MMM_ItemRenderManager.getEXRender(litem);
				
				if (MMM_ItemRenderManager.getEXRender(litem).renderItemInFirstPerson(f, this)) {
					return;
				}
			}
		}
		
		super.renderItemInFirstPerson(f);
	}

}
