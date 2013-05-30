package net.minecraft.src;

public class MMM_ModelLittleMaid_RX0 extends MMM_ModelLittleMaidBase {

	public MMM_ModelRenderer bipedForelockRight;
	public MMM_ModelRenderer bipedForelockLeft;
	public MMM_ModelRenderer bipedBreast;
	public MMM_ModelRenderer bipedTrunc;
	public MMM_ModelRenderer bipedHipRight;
	public MMM_ModelRenderer bipedHipLeft;
	public MMM_ModelRenderer bipedForearmRight;
	public MMM_ModelRenderer bipedForearmLeft;
	public MMM_ModelRenderer bipedShinRight;
	public MMM_ModelRenderer bipedShinRight1;
	public MMM_ModelRenderer bipedShinRight2;
	public MMM_ModelRenderer bipedShinLeft;
	public MMM_ModelRenderer bipedShinLeft1;
	public MMM_ModelRenderer bipedShinLeft2;
	public MMM_ModelRenderer bipedRibbon;
	public MMM_ModelRenderer bipedRibbon1;
	public MMM_ModelRenderer bipedRibbon2;
	public MMM_ModelRenderer bipedTail;
	public MMM_ModelRenderer SkirtRU;
	public MMM_ModelRenderer SkirtRB;
	public MMM_ModelRenderer SkirtLU;
	public MMM_ModelRenderer SkirtLB;
	

	
	
	public MMM_ModelLittleMaid_RX0() {
		this(0.0F);
	}
	public MMM_ModelLittleMaid_RX0(float psize) {
		this(psize, 0.0F, 64, 64);
	}
	public MMM_ModelLittleMaid_RX0(float psize, float pyoffset, int pTextureWidth, int pTextureHeight) {
		super(psize, pyoffset, pTextureWidth, pTextureHeight);
	}


	@Override
	public void initModel(float psize, float pyoffset) {
		bipedHead = new MMM_ModelRenderer(this);
		bipedHead.setTextureOffset(0, 0).addBox(-4F, -8F, -4F, 8, 8, 8, -0.2F);
		bipedForelockRight = new MMM_ModelRenderer(this);
		bipedForelockRight.setTextureOffset(0, 0).addBox(0F, 0F, -0.5F, 3, 13, 1);
		bipedForelockLeft = new MMM_ModelRenderer(this);
		bipedForelockLeft.setTextureOffset(0, 0).addBox(-3F, 0F, -0.5F, 3, 13, 1);
		
		bipedBody = new MMM_ModelRenderer(this);
		bipedBody.setTextureOffset(0, 0).addBox(-3F, 0F, -1F, 6, 3, 3);
		bipedBreast = new MMM_ModelRenderer(this);
		bipedBreast.setTextureOffset(0, 0).addBox(-3F, -2.5F, 0F, 6, 4, 3);
		
		bipedTrunc = new MMM_ModelRenderer(this);
		bipedTrunc.setTextureOffset(0, 0).addBox(-3F, 0F, -1.95F, 4, 7, 3);
		bipedHipRight = new MMM_ModelRenderer(this);
		bipedHipRight.setTextureOffset(0, 0).addBox(-3F, 0F, -2F, 3, 4, 4);
		bipedHipLeft = new MMM_ModelRenderer(this);
		bipedHipLeft.setTextureOffset(0, 0).addBox(0F, 0F, -2F, 3, 4, 4);
		
		bipedRightArm = new MMM_ModelRenderer(this);
		bipedRightArm.setTextureOffset(0, 0).addBox(-1.5F, -0.5F, -1F, 2, 7, 2);
		bipedForearmRight = new MMM_ModelRenderer(this);
		bipedForearmRight.setTextureOffset(0, 0).addBox(-1F, -1F, -1F, 2, 8, 2, -0.05F);
		bipedRightArm = new MMM_ModelRenderer(this);
		bipedRightArm.setTextureOffset(0, 0).addBox(-0.5F, -0.5F, -1F, 2, 7, 2);
		bipedForearmLeft = new MMM_ModelRenderer(this);
		bipedForearmLeft.setTextureOffset(0, 0).addBox(-1F, -1F, -1F, 2, 8, 2, -0.05F);

		Skirt = new MMM_ModelRenderer(this);
		Skirt.setTextureOffset(0, 0).addBox(-3F, 0F, -3F, 6, 8, 6, 0.05F);
		SkirtRU = new MMM_ModelRenderer(this);
		SkirtRU.setTextureOffset(0, 0).addBox(2F, 2F, -3F, 3, 7, 6);
		SkirtRB = new MMM_ModelRenderer(this);
		SkirtRB.setTextureOffset(0, 0).addBox(-4F, 0F, -4F, 4, 8, 8);
		SkirtLU = new MMM_ModelRenderer(this);
		SkirtLU.setTextureOffset(0, 0).addBox(-5F, 2F, -3F, 3, 7, 6);
		SkirtLB = new MMM_ModelRenderer(this);
		SkirtLB.setTextureOffset(0, 0).addBox(0F, 0F, -4F, 4, 8, 8);
		
	}

	@Override
	public void setDefaultPause() {
		// TODO Auto-generated method stub
		super.setDefaultPause();
	}

}
