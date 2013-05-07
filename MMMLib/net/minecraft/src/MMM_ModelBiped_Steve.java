package net.minecraft.src;


public class MMM_ModelBiped_Steve extends MMM_ModelMultiBase {

	public MMM_ModelRenderer bipedHead;
	public MMM_ModelRenderer bipedHeadwear;
	public MMM_ModelRenderer bipedBody;
	public MMM_ModelRenderer bipedRightArm;
	public MMM_ModelRenderer bipedLeftArm;
	public MMM_ModelRenderer bipedRightLeg;
	public MMM_ModelRenderer bipedLeftLeg;
	public MMM_ModelRenderer bipedEars;
	public MMM_ModelRenderer bipedCloak;


	public MMM_ModelBiped_Steve() {
		super();
	}
	public MMM_ModelBiped_Steve(float psize) {
		super(psize);
	}
	public MMM_ModelBiped_Steve(float psize, float pyoffset) {
		super(psize, pyoffset, 64, 32);
	}

	@Override
	public void initModel(float psize, float pyoffset) {
		bipedCloak = new MMM_ModelRenderer(this, 0, 0);
		bipedCloak.addBoxMM(-5.0F, 0.0F, -1.0F, 10, 16, 1, psize);
		bipedEars = new MMM_ModelRenderer(this, 24, 0);
		bipedEars.addBoxMM(-3.0F, -6.0F, -1.0F, 6, 6, 1, psize);
		
		bipedHead = new MMM_ModelRenderer(this, 0, 0);
		bipedHead.addBoxMM(-4.0F, -8.0F, -4.0F, 8, 8, 8, psize);
		bipedHead.setRotationPointMM(0.0F, 0.0F, 0.0F);
		bipedHeadwear = new MMM_ModelRenderer(this, 32, 0);
		bipedHeadwear.addBoxMM(-4.0F, -8.0F, -4.0F, 8, 8, 8, psize + 0.5F);
		bipedHeadwear.setRotationPointMM(0.0F, 0.0F, 0.0F);
		bipedBody = new MMM_ModelRenderer(this, 16, 16);
		bipedBody.addBoxMM(-4.0F, 0.0F, -2.0F, 8, 12, 4, psize);
		bipedBody.setRotationPointMM(0.0F, 0.0F, 0.0F);
		bipedRightArm = new MMM_ModelRenderer(this, 40, 16);
		bipedRightArm.addBoxMM(-3.0F, -2.0F, -2.0F, 4, 12, 4, psize);
		bipedRightArm.setRotationPointMM(-5.0F, 2.0F, 0.0F);
		bipedLeftArm = new MMM_ModelRenderer(this, 40, 16);
		bipedLeftArm.mirror = true;
		bipedLeftArm.addBoxMM(-1.0F, -2.0F, -2.0F, 4, 12, 4, psize);
		bipedLeftArm.setRotationPointMM(5.0F, 2.0F, 0.0F);
		bipedRightLeg = new MMM_ModelRenderer(this, 0, 16);
		bipedRightLeg.addBoxMM(-2.0F, 0.0F, -2.0F, 4, 12, 4, psize);
		bipedRightLeg.setRotationPointMM(-1.9F, 12.0F, 0.0F);
		bipedLeftLeg = new MMM_ModelRenderer(this, 0, 16);
		bipedLeftLeg.mirror = true;
		bipedLeftLeg.addBoxMM(-2.0F, 0.0F, -2.0F, 4, 12, 4, psize);
		bipedLeftLeg.setRotationPointMM(1.9F, 12.0F, 0.0F);
		
		Arms[0] = new MMM_ModelRenderer(this);
		Arms[0].setRotationPointMM(0F, 10F, 0F);
		bipedRightArm.addChildMM(Arms[0]);
		Arms[1] = new MMM_ModelRenderer(this);
		Arms[1].setRotationPointMM(0F, 10F, 0F);
		bipedLeftArm.addChildMM(Arms[1]);
		
		mainFrame = new MMM_ModelRenderer(this);
		mainFrame.setRotationPointMM(0F, pyoffset, 0F);
		mainFrame.addChildMM(bipedHead);
		mainFrame.addChildMM(bipedHeadwear);
		mainFrame.addChildMM(bipedBody);
		mainFrame.addChildMM(bipedRightArm);
		mainFrame.addChildMM(bipedLeftArm);
		mainFrame.addChildMM(bipedRightLeg);
		mainFrame.addChildMM(bipedLeftLeg);
		mainFrame.addChildMM(bipedEars);
		mainFrame.addChildMM(bipedCloak);
	}

	@Override
	public void renderMM(float par2, float par3, float ticksExisted,
			float pheadYaw, float pheadPitch, float par7) {
		super.renderMM(par2, par3, ticksExisted, pheadYaw, pheadPitch, par7);
	}

	@Override
	public void renderItems() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public float[] getArmorModelsSize() {
		return new float[] {0.5F, 0.1F};
	}

	@Override
	public float getHeight() {
		return 1.8F;
	}

	@Override
	public float getWidth() {
		return 0.6F;
	}

	@Override
	public float getyOffset() {
		return 1.62F;
	}

}
