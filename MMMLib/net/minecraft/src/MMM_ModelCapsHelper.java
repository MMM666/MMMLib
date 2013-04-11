package net.minecraft.src;

/**
 * ModelCapsÇÃï‚èïä÷êîåQ
 */
public class MMM_ModelCapsHelper {

	public static Object getCapsValue(MMM_IModelCaps pOwner, String pCapsName, Object ...pArg) {
		return pOwner.getCapsValue(pOwner.getModelCaps().get(pCapsName), pArg);
	}

	public static int getCapsValueInt(MMM_IModelCaps pOwner, int pIndex, Object ...pArg) {
		Integer li = (Integer)pOwner.getCapsValue(pIndex, pArg);
		return li == null ? 0 : li;
	}

	public static float getCapsValueFloat(MMM_IModelCaps pOwner, int pIndex, Object ...pArg) {
		Float lf = (Float)pOwner.getCapsValue(pIndex, pArg);
		return lf == null ? 0F : lf;
	}

	public static double getCapsValueDouble(MMM_IModelCaps pOwner, int pIndex, Object ...pArg) {
		Double ld = (Double)pOwner.getCapsValue(pIndex, pArg);
		return ld == null ? 0D : ld;
	}

	public static boolean getCapsValueBoolean(MMM_IModelCaps pOwner, int pIndex, Object ...pArg) {
		Boolean lb = (Boolean)pOwner.getCapsValue(pIndex, pArg);
		return lb == null ? false : lb;
	}

	public static boolean setCapsValue(MMM_IModelCaps pOwner, String pCapsName, Object... pArg) {
		return pOwner.setCapsValue(pOwner.getModelCaps().get(pCapsName), pArg);
	}

}
