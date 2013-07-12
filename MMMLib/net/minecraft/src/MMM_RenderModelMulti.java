package net.minecraft.src;

import org.lwjgl.opengl.GL11;

public class MMM_RenderModelMulti extends RenderLiving {

	public MMM_ModelBaseSolo modelMain;
	public MMM_ModelBaseDuo modelFATT;



	public MMM_RenderModelMulti(float pShadowSize) {
		super(null, pShadowSize);
		modelFATT = new MMM_ModelBaseDuo(this);
		modelFATT.isModelAlphablend = mod_MMM_MMMLib.isModelAlphaBlend;
		modelFATT.isRendering = true;
		modelMain = new MMM_ModelBaseSolo(this);
		modelMain.isModelAlphablend = mod_MMM_MMMLib.isModelAlphaBlend;
		modelMain.capsLink = modelFATT;
		mainModel = modelMain;
		setRenderPassModel(modelFATT);
	}

	protected int showArmorParts(EntityLivingBase par1EntityLiving, int par2, float par3) {
		// アーマーの表示設定
		modelFATT.renderParts = par2;
		ItemStack is = par1EntityLiving.getCurrentItemOrArmor(par2 + 1);
		if (is != null && is.stackSize > 0) {
			modelFATT.showArmorParts(par2);
			return is.isItemEnchanted() ? 15 : 1;
		}
		
		return -1;
	}
	@Override
	protected int shouldRenderPass(EntityLivingBase par1EntityLiving, int par2, float par3) {
		return showArmorParts((EntityLiving)par1EntityLiving, par2, par3);
	}

	@Override
	protected void preRenderCallback(EntityLivingBase entityliving, float f) {
		Float lscale = (Float)modelMain.getCapsValue(MMM_IModelCaps.caps_ScaleFactor);
		if (lscale != null) {
			GL11.glScalef(lscale, lscale, lscale);
		}
	}

	public void setModelValues(EntityLiving par1EntityLiving, double par2,
			double par4, double par6, float par8, float par9, MMM_IModelCaps pEntityCaps) {
		if (par1EntityLiving instanceof MMM_ITextureEntity) {
			MMM_ITextureEntity ltentity = (MMM_ITextureEntity)par1EntityLiving;
			modelMain.model = ((MMM_TextureBox)ltentity.getTextureBox()[0]).models[0];
			modelMain.textures = ltentity.getTextures(0);
			modelFATT.modelInner = ((MMM_TextureBox)ltentity.getTextureBox()[1]).models[1];
			modelFATT.modelOuter = ((MMM_TextureBox)ltentity.getTextureBox()[1]).models[2];
			modelFATT.textureInner = ltentity.getTextures(1);
			modelFATT.textureOuter = ltentity.getTextures(2);
		}
		modelMain.setEntityCaps(pEntityCaps);
		modelMain.setRender(this);
		modelMain.showAllParts();
		modelMain.isAlphablend = true;
		modelFATT.isAlphablend = true;
		
		modelMain.setCapsValue(MMM_IModelCaps.caps_heldItemLeft, (Integer)0);
		modelMain.setCapsValue(MMM_IModelCaps.caps_heldItemRight, (Integer)0);
		modelMain.setCapsValue(MMM_IModelCaps.caps_onGround, renderSwingProgress(par1EntityLiving, par9));
		modelMain.setCapsValue(MMM_IModelCaps.caps_isRiding, par1EntityLiving.isRiding());
		modelMain.setCapsValue(MMM_IModelCaps.caps_isSneak, par1EntityLiving.isSneaking());
		modelMain.setCapsValue(MMM_IModelCaps.caps_aimedBow, false);
		modelMain.setCapsValue(MMM_IModelCaps.caps_isWait, false);
		modelMain.setCapsValue(MMM_IModelCaps.caps_isChild, par1EntityLiving.isChild());
		modelMain.setCapsValue(MMM_IModelCaps.caps_entityIdFactor, 0F);
		modelMain.setCapsValue(MMM_IModelCaps.caps_ticksExisted, par1EntityLiving.ticksExisted);
	}

	public void renderModelMulti(EntityLiving par1EntityLiving, double par2,
			double par4, double par6, float par8, float par9, MMM_IModelCaps pEntityCaps) {
		setModelValues(par1EntityLiving, par2, par4, par6, par8, par9, pEntityCaps);
		super.doRenderLiving(par1EntityLiving, par2, par4, par6, par8, par9);
	}

	@Override
	public void doRenderLiving(EntityLiving par1EntityLiving, double par2,
			double par4, double par6, float par8, float par9) {
		renderModelMulti(par1EntityLiving, par2, par4, par6, par8, par9, (MMM_IModelCaps)par1EntityLiving);
	}

	@Override
	protected void renderModel(EntityLivingBase par1EntityLiving, float par2,
			float par3, float par4, float par5, float par6, float par7) {
		if (!par1EntityLiving.isInvisible()) {
			modelMain.setArmorRendering(true);
		} else {
			modelMain.setArmorRendering(false);
		}
		// アイテムのレンダリング位置を獲得するためrenderを呼ぶ必要がある
		mainModel.render(par1EntityLiving, par2, par3, par4, par5, par6, par7);
	}

	@Override
	protected void renderEquippedItems(EntityLivingBase par1EntityLiving, float par2) {
		// ハードポイントの描画
		modelMain.renderItems(par1EntityLiving, this);
		renderArrowsStuckInEntity(par1EntityLiving, par2);
	}

	@Override
	protected void renderArrowsStuckInEntity(EntityLivingBase par1EntityLiving, float par2) {
		MMM_Client.renderArrowsStuckInEntity(par1EntityLiving, par2, this, modelMain.model);
	}

	@Override
	protected ResourceLocation func_110775_a(Entity var1) {
		// テクスチャリソースを返すところだけれど、基本的に使用しない。
		return null;
	}

}
