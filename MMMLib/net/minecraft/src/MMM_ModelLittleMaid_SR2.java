package net.minecraft.src;

import org.lwjgl.opengl.GL11;

/**
 * 瞬き付き基本形
 */
public class MMM_ModelLittleMaid_SR2 extends MMM_ModelLittleMaid {

	public MMM_ModelRenderer eyeR;
	public MMM_ModelRenderer eyeL;


	public MMM_ModelLittleMaid_SR2() {
		super();
	}
	public MMM_ModelLittleMaid_SR2(float psize) {
		super(psize);
	}
	public MMM_ModelLittleMaid_SR2(float psize, float pyoffset) {
		super(psize, pyoffset);
	}


	@Override
	public void initModel(float psize, float pyoffset) {
		super.initModel(psize, pyoffset);
		
		// 追加パーツ
		eyeR = new MMM_ModelRenderer(this, 32, 19);
		eyeR.addPlate(-4.0F, -5.0F, -4.001F, 4, 4, 0, psize);
		eyeR.setRotationPointMM(0.0F, 0.0F, 0.0F);
		eyeL = new MMM_ModelRenderer(this, 42, 19);
		eyeL.addPlate(0.0F, -5.0F, -4.001F, 4, 4, 0, psize);
		eyeL.setRotationPointMM(0.0F, 0.0F, 0.0F);
		bipedHead.addChildMM(eyeR);
		bipedHead.addChildMM(eyeL);
	}

	@Override
	public void setLivingAnimationsMM(float par2, float par3, float pRenderPartialTicks) {
		super.setLivingAnimationsMM(par2, par3, pRenderPartialTicks);
		
		float f3 = (float)entityTicksExisted + pRenderPartialTicks + entityIdFactor;
		// 目パチ
		if( 0 > mh_sin(f3 * 0.05F) + mh_sin(f3 * 0.13F) + mh_sin(f3 * 0.7F) + 2.55F) { 
			eyeR.setVisible(true);
			eyeL.setVisible(true);
		} else { 
			eyeR.setVisible(false);
			eyeL.setVisible(false);
		}
	}

	@Override
	public void setRotationAnglesMM(float par1, float par2,
			float pTicksExisted, float pHeadYaw, float pHeadPitch, float par6) {
		super.setRotationAnglesMM(par1, par2, pTicksExisted, pHeadYaw, pHeadPitch, par6);
		if (aimedBow) {
			if (MMM_ModelCapsHelper.getCapsValueInt(entityCaps, caps_dominantArm) == 0) {
				eyeL.setVisible(true);
			} else {
				eyeR.setVisible(true);
			}
		}
	}

}
