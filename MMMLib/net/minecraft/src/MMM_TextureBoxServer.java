package net.minecraft.src;

public class MMM_TextureBoxServer extends MMM_TextureBoxBase {

	// ローカルでモデルを保持している時にリンク
	protected MMM_TextureBox localBox;


	public MMM_TextureBoxServer() {
	}

	public MMM_TextureBoxServer(MMM_TextureBox pBox) {
		localBox		= pBox;
		contractColor	= pBox.getContractColorBits();
		wildColor		= pBox.getWildColorBits();
		textureName		= pBox.textureName;
		isUpdateSize = (pBox.models != null && pBox.models[0] != null) ?
				MMM_ModelCapsHelper.getCapsValueBoolean(pBox.models[0], MMM_IModelCaps.caps_isUpdateSize) : false;
/*
		if (pBox.models != null) {
			modelHeight			= pBox.models[0].getHeight(null);
			modelWidth			= pBox.models[0].getWidth(null);
			modelYOffset		= pBox.models[0].getyOffset(null);
			modelMountedYOffset	= pBox.models[0].getMountedYOffset(null);
		}
*/
	}

	/*
	 * MMM_Statics.Server_GetTextureIndex
	 */
	public void setValue(byte[] pData) {
		contractColor		= MMM_Helper.getShort(pData, 2);
		wildColor			= MMM_Helper.getShort(pData, 4);
		modelHeight			= MMM_Helper.getFloat(pData, 6);
		modelWidth			= MMM_Helper.getFloat(pData, 10);
		modelYOffset		= MMM_Helper.getFloat(pData, 14);
		modelMountedYOffset	= MMM_Helper.getFloat(pData, 18);
		textureName			= MMM_Helper.getStr(pData, 22);
	}

	@Override
	public float getHeight(MMM_IModelCaps pEntityCaps) {
		return localBox != null ? localBox.models[0].getHeight(pEntityCaps) : modelHeight;
	}

	@Override
	public float getWidth(MMM_IModelCaps pEntityCaps) {
		return localBox != null ? localBox.models[0].getWidth(pEntityCaps) : modelWidth;
	}

	@Override
	public float getYOffset(MMM_IModelCaps pEntityCaps) {
		return localBox != null ? localBox.models[0].getyOffset(pEntityCaps) : modelYOffset;
	}

	@Override
	public float getMountedYOffset(MMM_IModelCaps pEntityCaps) {
		return localBox != null ? localBox.models[0].getMountedYOffset(pEntityCaps) : modelMountedYOffset;
	}

}
