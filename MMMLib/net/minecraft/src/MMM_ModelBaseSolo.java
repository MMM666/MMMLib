package net.minecraft.src;

import java.util.Map;

import org.lwjgl.opengl.GL11;

public class MMM_ModelBaseSolo extends MMM_ModelBaseNihil implements MMM_IModelBaseMMM {

	public MMM_ModelMultiBase model;
	public String[] textures;


	public MMM_ModelBaseSolo(RenderLiving pRender) {
		renderLiving = pRender;
	}

	@Override
	public void setLivingAnimations(EntityLiving par1EntityLiving, float par2, float par3, float par4) {
		if (model != null) {
			model.setLivingAnimations(entityCaps, par2, par3, par4);
		}
		isAlphablend = true;
	}

	@Override
	public void render(Entity par1Entity, float par2, float par3, float par4, float par5, float par6, float par7) {
		if (model == null) {
			isAlphablend = false;
			return;
		}
		if (isAlphablend) {
			if (isModelAlphablend) {
				GL11.glEnable(GL11.GL_BLEND);
				GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
			} else {
				GL11.glDisable(GL11.GL_BLEND);
			}
		}
		if (textures.length > 2 && textures[2] != null) {
			// Actors用
			model.setRotationAngles(par2, par3, par4, par5, par6, par7, entityCaps);
			// Face
			// TODO:テクスチャのロードはなんか考える。
			renderLiving.loadTexture(textures[2]);
			model.setCapsValue(caps_renderFace, entityCaps, par2, par3, par4, par5, par6, par7, isRendering);
			// Body
			renderLiving.loadTexture(textures[0]);
			model.setCapsValue(caps_renderBody, entityCaps, par2, par3, par4, par5, par6, par7, isRendering);
		} else {
			// 通常
			if (textures[0] != null) {
				renderLiving.loadTexture(textures[0]);
			}
			model.render(entityCaps, par2, par3, par4, par5, par6, par7, isRendering);
		}
		isAlphablend = false;
		if (textures.length > 1 && textures[1] != null) {
			// 発光パーツ
			renderLiving.loadTexture(textures[1]);
			float var4 = 1.0F;
			GL11.glEnable(GL11.GL_BLEND);
			GL11.glDisable(GL11.GL_ALPHA_TEST);
			GL11.glBlendFunc(GL11.GL_ONE, GL11.GL_ONE);
			
			if (par1Entity.isInvisible()) {
				GL11.glDepthMask(false);
			} else {
				GL11.glDepthMask(true);
			}
			GL11.glEnable(GL11.GL_ALPHA_TEST);
//			GL11.glDepthMask(true);
			
			MMM_Client.setLightmapTextureCoords(61680);
//			GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
			GL11.glColor4f(1.0F, 1.0F, 1.0F, var4);
			model.render(entityCaps, par2, par3, par4, par5, par6, par7, isRendering);
			
			MMM_Client.setLightmapTextureCoords(par1Entity.getBrightnessForRender(par2));
			
			GL11.glDisable(GL11.GL_BLEND);
			GL11.glEnable(GL11.GL_ALPHA_TEST);
		}
	}

	@Override
	public TextureOffset getTextureOffset(String par1Str) {
		return model == null ? null : model.getTextureOffset(par1Str);
	}

	@Override
	public void setRotationAngles(float par1, float par2, float par3,
			float par4, float par5, float par6, Entity par7Entity) {
		if (model != null) {
			model.setRotationAngles(par1, par2, par3, par4, par5, par6, entityCaps);
		}
	}


	// IModelMMM追加分

	@Override
	public void renderItems(EntityLiving pEntity, Render pRender) {
		if (model != null) {
			model.renderItems(entityCaps);
		}
	}

	@Override
	public void showArmorParts(int pParts) {
		if (model != null) {
			model.showArmorParts(pParts, 0);
		}
	}

	/**
	 * Renderer辺でこの変数を設定する。
	 * 設定値はMMM_IModelCapsを継承したEntitiyとかを想定。
	 */
	@Override
	public void setEntityCaps(MMM_IModelCaps pEntityCaps) {
		entityCaps = pEntityCaps;
		if (capsLink != null) {
			capsLink.setEntityCaps(pEntityCaps);
		}
	}

	@Override
	public void setRender(Render pRender) {
		if (model != null) {
			model.render = pRender;
		}
	}

	@Override
	public void setArmorRendering(boolean pFlag) {
		isRendering = pFlag;
	}


	// IModelCaps追加分

	@Override
	public Map<String, Integer> getModelCaps() {
		return model == null ? null : model.getModelCaps();
	}

	@Override
	public Object getCapsValue(int pIndex, Object ... pArg) {
		return model == null ? null : model.getCapsValue(pIndex, pArg);
	}

	@Override
	public boolean setCapsValue(int pIndex, Object... pArg) {
		if (capsLink != null) {
			capsLink.setCapsValue(pIndex, pArg);
		}
		if (model != null) {
			model.setCapsValue(pIndex, pArg);
		}
		return false;
	}

	@Override
	public void showAllParts() {
		if (model != null) {
			model.showAllParts();
		}
	}


}
