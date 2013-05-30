package net.minecraft.src;

/**
 * スタビライザー搭載機
 */
public class MMM_ModelLittleMaid_AC extends MMM_ModelMultiMMMBase {

	public MMM_ModelRenderer bipedHead;
	public MMM_ModelRenderer bipedBody;
	public MMM_ModelRenderer bipedRightArm;
	public MMM_ModelRenderer bipedLeftArm;
	public MMM_ModelRenderer bipedRightLeg;
	public MMM_ModelRenderer bipedLeftLeg;
	public MMM_ModelRenderer Skirt;

	public MMM_ModelRenderer ChignonR;
	public MMM_ModelRenderer ChignonL;
	public MMM_ModelRenderer ChignonB;
	public MMM_ModelRenderer Tail;
	public MMM_ModelRenderer SideTailR;
	public MMM_ModelRenderer SideTailL;

	public MMM_ModelLittleMaid_AC() {
		super();
	}

	public MMM_ModelLittleMaid_AC(float psize) {
		super(psize);
	}

	public MMM_ModelLittleMaid_AC(float psize, float pyoffset) {
		super(psize, pyoffset, 64, 32);
	}

	@Override
	public void initModel(float psize, float pyoffset) {
		// TODO Auto-generated method stub
		/*
		Arms = new MMM_ModelRenderer[18];
		// バイプロダクトエフェクター
		Arms[2] = new MMM_ModelRenderer(this, 0, 0);
		Arms[2].setRotationPoint(-3F, 9F, 6F);
		Arms[2].setRotateAngleDeg(45F, 0F, 0F);
		Arms[3] = new MMM_ModelRenderer(this, 0, 0);
		Arms[3].setRotationPoint(3F, 9F, 6F);
		Arms[3].setRotateAngleDeg(45F, 0F, 0F);
		Arms[3].isInvertX = true;
		// テールソード
		Arms[4] = new MMM_ModelRenderer(this, 0, 0);
		Arms[4].setRotationPoint(-2F, 0F, 0F);
		Arms[4].setRotateAngleDeg(180F, 0F, 0F);
		Arms[5] = new MMM_ModelRenderer(this, 0, 0);
		Arms[5].setRotationPoint(2F, 0F, 0F);
		Arms[5].setRotateAngleDeg(180F, 0F, 0F);
		*/
		//
//		Arms[2].setRotateAngle(-0.78539816339744830961566084581988F - bipedRightArm.getRotateAngleX(), 0F, 0F);
//		Arms[3].setRotateAngle(-0.78539816339744830961566084581988F - bipedLeftArm.getRotateAngleX(), 0F, 0F);

	}

	@Override
	public float[] getArmorModelsSize() {
		return new float[] {0.1F, 0.5F};
	}

	@Override
	public float getHeight() {
		return 1.35F;
	}

	@Override
	public float getWidth() {
		return 0.5F;
	}
	
	@Override
	public float getyOffset() {
		return 1.215F;
	}
	
	@Override
	public float getMountedYOffset() {
		return 0.35F;
	}
	
	@Override
	public void renderItems(MMM_IModelCaps pEntityCaps) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void renderFirstPersonHand(MMM_IModelCaps pEntityCaps) {
		// TODO Auto-generated method stub
		
	}

}
