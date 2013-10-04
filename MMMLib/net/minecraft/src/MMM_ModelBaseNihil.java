package net.minecraft.src;

public class MMM_ModelBaseNihil extends ModelBase {

	public RendererLivingEntity rendererLivingEntity;

	public boolean isAlphablend;
	public boolean isModelAlphablend;
	public MMM_IModelBaseMMM capsLink;
	public int lighting;
	protected MMM_IModelCaps entityCaps;
	protected boolean isRendering;
	/**
	 * レンダリングが実行された回数。
	 * ダメージ時などの対策。
	 */
	public int renderCount;


//	@Override
//	public ModelRenderer getRandomModelBox(Random par1Random) {
//		return modelArmorInner.getRandomModelBox(par1Random);
//	}

	public void showAllParts() {
	}

	@Override
	public void render(Entity par1Entity, float par2, float par3, float par4,
			float par5, float par6, float par7) {
		renderCount++;
	}

}
