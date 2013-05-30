package net.minecraft.src;

/**
 * SmartMoving対応用モデルベース予定。
 * 未実装につき使用不可。
 */
public abstract class MMM_ModelSmartMovingBase extends MMM_ModelMultiBase {

	public MMM_ModelRenderer bipedOuter;
	public MMM_ModelRenderer bipedTorso;
	public MMM_ModelRenderer bipedBody;
	public MMM_ModelRenderer bipedBreast;
	public MMM_ModelRenderer bipedNeck;
	public MMM_ModelRenderer bipedHead;
	public MMM_ModelRenderer bipedHeadwear;
	public MMM_ModelRenderer bipedRightShoulder;
	public MMM_ModelRenderer bipedRightArm;
	public MMM_ModelRenderer bipedLeftShoulder;
	public MMM_ModelRenderer bipedLeftArm;
	public MMM_ModelRenderer bipedPelvic;
	public MMM_ModelRenderer bipedRightLeg;
	public MMM_ModelRenderer bipedLeftLeg;


	@Override
	public void initModel(float psize, float pyoffset) {
		// TODO Auto-generated method stub

	}

	@Override
	public float[] getArmorModelsSize() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public float getHeight() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public float getWidth() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public float getyOffset() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public float getMountedYOffset() {
		// TODO Auto-generated method stub
		return 0;
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
