package net.minecraft.src;

public class MMM_TextureBoxServer {

	public String textureName;
	public int contractColor;
	public int wildColor;
	public float modelHeight;
	public float modelWidth;
	public float modelYOffset;


	public MMM_TextureBoxServer() {
	}

	public MMM_TextureBoxServer(byte[] pData) {
		/*
		 * 0:ID
		 * 1:index óvãÅÇ©ÇØÇΩéûÇÃî‘çÜ
		 * 2-3:contColorBits
		 * 4-5:wildColorBits
		 * 6-9:height
		 * 10-13:width
		 * 14-17:yoffset
		 * 18-:Str
		 */
		contractColor	= MMM_Helper.getShort(pData, 2);
		wildColor		= MMM_Helper.getShort(pData, 4);
		modelHeight		= MMM_Helper.getFloat(pData, 6);
		modelWidth		= MMM_Helper.getFloat(pData, 10);
		modelYOffset	= MMM_Helper.getFloat(pData, 14);
		textureName		= MMM_Helper.getStr(pData, 18);
	}

	public MMM_TextureBoxServer(MMM_TextureBox pBox) {
		contractColor	= pBox.getContractColorBits();
		wildColor		= pBox.getWildColorBits();
		modelHeight		= pBox.models[0].getHeight();
		modelWidth		= pBox.models[0].getWidth();
		modelYOffset	= pBox.models[0].getyOffset();
		textureName		= pBox.packegeName;
	}

}
