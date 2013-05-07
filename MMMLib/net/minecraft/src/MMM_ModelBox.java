package net.minecraft.src;

public class MMM_ModelBox extends MMM_ModelBoxBase {

	/**
	 * @param pMRenderer
	 * @param pArg
	 * textureX, textureY, posX, posY, posZ, width, height, depth, sizeAdjust
	 */
	public MMM_ModelBox(MMM_ModelRenderer pMRenderer, Object... pArg) {
		super(pMRenderer, (Integer)pArg[0], (Integer)pArg[1],
				(Float)pArg[2], (Float)pArg[3], (Float)pArg[4],
				(Integer)pArg[5], (Integer)pArg[6], (Integer)pArg[7],
				pArg.length < 9 ? 0.0F : (Float)pArg[8]);
	}

	private MMM_ModelBox(ModelRenderer par1ModelRenderer, int par2, int par3,
			float par4, float par5, float par6,
			int par7, int par8, int par9, float par10) {
		super(par1ModelRenderer, par2, par3, par4, par5, par6, par7, par8, par9, par10);
	}

}
