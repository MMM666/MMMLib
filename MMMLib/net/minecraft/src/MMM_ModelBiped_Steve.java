package net.minecraft.src;

public class MMM_ModelBiped_Steve extends MMM_ModelBiped {

	public MMM_ModelRenderer mainFrame;


	public MMM_ModelBiped_Steve() {
		super();
	}
	public MMM_ModelBiped_Steve(float psize) {
		super(psize);
	}
	public MMM_ModelBiped_Steve(float psize, float pyoffset) {
		super(psize, pyoffset);
	}

	@Override
	public void initModel(float psize, float pyoffset) {
		bipedCloak = new ModelRenderer(this, 0, 0);
		bipedCloak.addBox(-5.0F, 0.0F, -1.0F, 10, 16, 1, psize);
		bipedEars = new ModelRenderer(this, 24, 0);
		bipedEars.addBox(-3.0F, -6.0F, -1.0F, 6, 6, 1, psize);
		
		bipedHead = new MMM_ModelRenderer(this, 0, 0);
		bipedHead.addBox(-4.0F, -8.0F, -4.0F, 8, 8, 8, psize);
		bipedHead.setRotationPoint(0.0F, 0.0F, 0.0F);
		bipedHeadwear = new MMM_ModelRenderer(this, 32, 0);
		bipedHeadwear.addBox(-4.0F, -8.0F, -4.0F, 8, 8, 8, psize + 0.5F);
		bipedHeadwear.setRotationPoint(0.0F, 0.0F, 0.0F);
		bipedBody = new MMM_ModelRenderer(this, 16, 16);
		bipedBody.addBox(-4.0F, 0.0F, -2.0F, 8, 12, 4, psize);
		bipedBody.setRotationPoint(0.0F, 0.0F, 0.0F);
		bipedRightArm = new MMM_ModelRenderer(this, 40, 16);
		bipedRightArm.addBox(-3.0F, -2.0F, -2.0F, 4, 12, 4, psize);
		bipedRightArm.setRotationPoint(-5.0F, 2.0F, 0.0F);
		bipedLeftArm = new MMM_ModelRenderer(this, 40, 16);
		bipedLeftArm.mirror = true;
		bipedLeftArm.addBox(-1.0F, -2.0F, -2.0F, 4, 12, 4, psize);
		bipedLeftArm.setRotationPoint(5.0F, 2.0F, 0.0F);
		bipedRightLeg = new MMM_ModelRenderer(this, 0, 16);
		bipedRightLeg.addBox(-2.0F, 0.0F, -2.0F, 4, 12, 4, psize);
		bipedRightLeg.setRotationPoint(-1.9F, 12.0F, 0.0F);
		bipedLeftLeg = new MMM_ModelRenderer(this, 0, 16);
		bipedLeftLeg.mirror = true;
		bipedLeftLeg.addBox(-2.0F, 0.0F, -2.0F, 4, 12, 4, psize);
		bipedLeftLeg.setRotationPoint(1.9F, 12.0F, 0.0F);
		
		Arms[0] = new MMM_ModelRenderer(this);
		Arms[0].setRotationPoint(0F, 10F, 0F);
		bipedRightArm.addChild(Arms[0]);
		Arms[1] = new MMM_ModelRenderer(this);
		Arms[1].setRotationPoint(0F, 10F, 0F);
		bipedLeftArm.addChild(Arms[1]);
		
		mainFrame = new MMM_ModelRenderer(this);
		mainFrame.setRotationPoint(0F, pyoffset, 0F);
		mainFrame.addChild(bipedHead);
		mainFrame.addChild(bipedHeadwear);
		mainFrame.addChild(bipedBody);
		mainFrame.addChild(bipedRightArm);
		mainFrame.addChild(bipedLeftArm);
		mainFrame.addChild(bipedRightLeg);
		mainFrame.addChild(bipedLeftLeg);
		mainFrame.addChild(bipedEars);
		mainFrame.addChild(bipedCloak);
	}

	@Override
	public void render(Entity par1Entity, float par2, float par3, float par4,
			float par5, float par6, float par7) {
		mainFrame.render(par7, (EntityLiving)par1Entity);
	}

	@Override
	public void renderItems(EntityLiving pEntity, Render pRender) {
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
