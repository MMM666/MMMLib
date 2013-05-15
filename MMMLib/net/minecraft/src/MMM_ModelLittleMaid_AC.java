package net.minecraft.src;

/**
 * スタビライザー搭載機
 */
public class MMM_ModelLittleMaid_AC extends MMM_ModelMultiBase {

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
	public void renderItems() {
		// TODO Auto-generated method stub
		
	}

}
