package net.minecraft.src;

public class MMM_TextureBoxServer extends MMM_TextureBoxBase {

	public MMM_TextureBoxServer() {
	}

	public MMM_TextureBoxServer(MMM_TextureBox pBox) {
		contractColor	= pBox.getContractColorBits();
		wildColor		= pBox.getWildColorBits();
		if (pBox.models != null) {
			modelHeight			= pBox.models[0].getHeight();
			modelWidth			= pBox.models[0].getWidth();
			modelYOffset		= pBox.models[0].getyOffset();
			modelMountedYOffset	= pBox.models[0].getMountedYOffset();
		}
		textureName		= pBox.packegeName;
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

}
