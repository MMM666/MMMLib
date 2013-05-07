package net.minecraft.src;

public abstract class MMM_ModelBoxBase extends ModelBox {

	/**
	 * こちらを必ず実装すること。
	 * @param pMRenderer
	 * @param pArg
	 */
	public MMM_ModelBoxBase(MMM_ModelRenderer pMRenderer, Object ... pArg) {
		this(pMRenderer, (Integer)pArg[0], (Integer)pArg[1],
				(Float)pArg[2], (Float)pArg[3], (Float)pArg[4],
				(Integer)pArg[5], (Integer)pArg[6], (Integer)pArg[7], (Float)pArg[8]);
	}
	/**
	 * 通常のインスタンス作成は推奨されません。
	 */
	protected MMM_ModelBoxBase(ModelRenderer par1ModelRenderer, int par2, int par3,
			float par4, float par5, float par6,
			int par7, int par8, int par9, float par10) {
		super(par1ModelRenderer, par2, par3, par4, par5, par6, par7, par8, par9, par10);
	}

	public void renderMM(Tessellator par1Tessellator, float par2) {
		render(par1Tessellator, par2);
	}
	@Override
	@Deprecated
	public void render(Tessellator par1Tessellator, float par2) {
		super.render(par1Tessellator, par2);
	}

	public MMM_ModelBoxBase setBoxNameMM(String pName) {
		return (MMM_ModelBoxBase)func_78244_a(pName);
	}
	@Override
	@Deprecated
	public ModelBox func_78244_a(String par1Str) {
		return super.func_78244_a(par1Str);
	}

}
