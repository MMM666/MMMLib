package net.minecraft.src;

import java.util.Map;
import java.util.Random;

import org.lwjgl.opengl.GL11;

/**
 * アーマーの二重描画用クラス。
 */
public class MMM_ModelDuo extends ModelBase implements MMM_IModelCaps {

	public RenderLiving renderLiving;
	public MMM_ModelBiped modelArmorOuter;
	public MMM_ModelBiped modelArmorInner;
	/**
	 * 部位毎のアーマーテクスチャの指定。
	 * 外側。
	 */
	public String[] textureOuter;
	/**
	 * 部位毎のアーマーテクスチャの指定。
	 * 内側。
	 */
	public String[] textureInner;
	/**
	 * 描画されるアーマーの部位。
	 * shouldRenderPassとかで指定する。
	 */
	public int renderParts;
	public boolean isAlphablend;
	public boolean isModelAlphablend;
	public MMM_ModelDuo capsLink;


	public MMM_ModelDuo(RenderLiving pRender) {
		renderLiving = pRender;
		renderParts = 0;
	}

	@Override
	public void setLivingAnimations(EntityLiving par1EntityLiving, float par2, float par3, float par4) {
		if (modelArmorOuter != null) {
			modelArmorOuter.setLivingAnimations(par1EntityLiving, par2, par3, par4);
		}
		if (modelArmorInner != null) {
			modelArmorInner.setLivingAnimations(par1EntityLiving, par2, par3, par4);
		}
		isAlphablend = true;
	}

	@Override
	public void render(Entity par1Entity, float par2, float par3, float par4, float par5, float par6, float par7) {
		if (isAlphablend) {
			if (isModelAlphablend) {
				GL11.glEnable(GL11.GL_BLEND);
				GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
			} else {
				GL11.glDisable(GL11.GL_BLEND);
			}
		}
		if (modelArmorOuter != null) {
			renderLiving.loadTexture(textureOuter[renderParts]);
			modelArmorOuter.render(par1Entity, par2, par3, par4, par5, par6, par7);
		}
		while (modelArmorInner != null) {
			if (textureInner != null) {
				if (textureInner[renderParts] == null) {
					break;
				}
				renderLiving.loadTexture(textureInner[renderParts]);
			}
			modelArmorInner.render(par1Entity, par2, par3, par4, par5, par6, par7);
			break;
		}
		isAlphablend = false;
	}

	public void renderItems(EntityLiving pEntity, Render pRender) {
		if (modelArmorInner != null) {
			modelArmorInner.renderItems(pEntity, pRender);
		}
	}

	@Override
	public ModelRenderer func_85181_a(Random par1Random) {
		return modelArmorInner.func_85181_a(par1Random);
	}

	@Override
	public TextureOffset getTextureOffset(String par1Str) {
		return modelArmorInner.getTextureOffset(par1Str);
	}

	@Override
	public void setRotationAngles(float par1, float par2, float par3,
			float par4, float par5, float par6, Entity par7Entity) {
		if (modelArmorOuter != null) {
			modelArmorOuter.setRotationAngles(par1, par2, par3, par4, par5, par6, par7Entity);
		}
		if (modelArmorInner != null) {
			modelArmorInner.setRotationAngles(par1, par2, par3, par4, par5, par6, par7Entity);
		}
	}

	public void showArmorParts(int pIndex) {
		if (modelArmorOuter != null) {
			modelArmorOuter.showArmorParts(pIndex);
		}
		if (modelArmorInner != null) {
			modelArmorInner.showArmorParts(pIndex);
		}
	}

	/**
	 * Renderer辺でこの変数を設定する。
	 * 設定値はMMM_IModelCapsを継承したEntitiyとかを想定。
	 */
	public void setModelCaps(MMM_IModelCaps pModelCaps) {
		if (modelArmorInner != null) {
			modelArmorInner.setModelCaps(pModelCaps);
		}
		if (modelArmorOuter != null) {
			modelArmorOuter.setModelCaps(pModelCaps);
		}
		if (capsLink != null) {
			capsLink.setModelCaps(pModelCaps);
		}
	}

	@Override
	public Map<String, Integer> getModelCaps() {
		return modelArmorInner == null ? null : modelArmorInner.getModelCaps();
	}

	@Override
	public Object getCapsValue(int pIndex, Object ... pArg) {
		return modelArmorInner == null ? null : modelArmorInner.getCapsValue(pIndex, pArg);
	}
	@Override
	public Object getCapsValue(String pCapsName, Object ... pArg) {
		return modelArmorInner == null ? null : modelArmorInner.getCapsValue(pCapsName, pArg);
	}
	@Override
	public int getCapsValueInt(int pIndex, Object ... pArg) {
		return modelArmorInner == null ? 0 : modelArmorInner.getCapsValueInt(pIndex, pArg);
	}
	@Override
	public float getCapsValueFloat(int pIndex, Object ... pArg) {
		return modelArmorInner == null ? 0F : modelArmorInner.getCapsValueFloat(pIndex, pArg);
	}
	@Override
	public double getCapsValueDouble(int pIndex, Object ... pArg) {
		return modelArmorInner == null ? 0D : modelArmorInner.getCapsValueDouble(pIndex, pArg);
	}
	@Override
	public boolean getCapsValueBoolean(int pIndex, Object ... pArg) {
		return modelArmorInner == null ? false : modelArmorInner.getCapsValueBoolean(pIndex, pArg);
	}

	@Override
	public boolean setCapsValue(int pIndex, Object... pArg) {
		if (capsLink != null) {
			capsLink.setCapsValue(pIndex, pArg);
		}
		if (modelArmorOuter != null) {
			modelArmorOuter.setCapsValue(pIndex, pArg);
		}
		if (modelArmorInner != null) {
			return modelArmorInner.setCapsValue(pIndex, pArg);
		}
		return false;
	}
	@Override
	public boolean setCapsValue(String pCapsName, Object... pArg) {
		return setCapsValue(pCapsName, pArg);
	}

	public void setRender(Render pRender) {
		if (modelArmorInner != null) {
			modelArmorInner.render = pRender;
		}
		if (modelArmorOuter != null) {
			modelArmorOuter.render = pRender;
		}
	}

	public void setArmorRendering(boolean pFlag) {
		if (modelArmorInner != null) {
			modelArmorInner.isRendering = pFlag;
		}
	}

}
