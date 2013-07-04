package net.minecraft.src;

import java.util.Map;

public class MMM_EntitySelect extends EntityLiving implements MMM_IModelCaps, MMM_ITextureEntity {

	public int color;
	public int textureIndex[] = new int[] { 0, 0 };
	public MMM_TextureBoxBase textureBox[] = new MMM_TextureBoxBase[] {
			new MMM_TextureBox(), new MMM_TextureBox() };
	public boolean contract;
	public ResourceLocation textures[][] = new ResourceLocation[][] {
			{ null, null },
			{ null, null , null , null },
			{ null, null , null , null }
	};
	protected MMM_EntityCaps entityCaps;



	public MMM_EntitySelect(World par1World) {
		super(par1World);
		entityCaps = new MMM_EntityCaps(this);
	}

	@Override
	protected void entityInit() {
		// Select用だから、これ別にいらんけどな。
		super.entityInit();
		// color
		dataWatcher.addObject(19, Integer.valueOf(0));
		// 20:選択テクスチャインデックス
		dataWatcher.addObject(20, Integer.valueOf(0));
	}

/*
	@Override
	public int getMaxHealth() {
		return 20;
	}
*/
	@Override
	public float getBrightness(float par1) {
		return worldObj == null ? 0.0F : super.getBrightness(par1);
	}

	// EntityCaps

	@Override
	public Map<String, Integer> getModelCaps() {
		return entityCaps.getModelCaps();
	}

	@Override
	public Object getCapsValue(int pIndex, Object... pArg) {
		return entityCaps.getCapsValue(pIndex, pArg);
	}

	@Override
	public boolean setCapsValue(int pIndex, Object... pArg) {
		return entityCaps.setCapsValue(pIndex, pArg);
	}

	// TextureEntity

	@Override
	public void setTexturePackIndex(int pColor, int[] pIndex) {
		// Server
		color = pColor;
		textureIndex[0] = pIndex[0];
		textureIndex[1] = pIndex[1];
		dataWatcher.updateObject(20, (Integer.valueOf(textureIndex[0]) & 0xffff) | ((Integer.valueOf(textureIndex[1]) & 0xffff) << 16));
		textureBox[0] = MMM_TextureManager.instance.getTextureBoxServer(textureIndex[0]);
		textureBox[1] = MMM_TextureManager.instance.getTextureBoxServer(textureIndex[1]);
		// サイズの変更
		setSize(textureBox[0].getWidth(entityCaps), textureBox[0].getHeight(entityCaps));
	}

	@Override
	public void setTexturePackName(MMM_TextureBox[] pTextureBox) {
		// Client
		textureBox[0] = pTextureBox[0];
		textureBox[1] = pTextureBox[1];
		setTextureNames();
		// 身長変更用
		setSize(textureBox[0].getWidth(null), textureBox[0].getHeight(null));
		setPosition(posX, posY, posZ);
		// モデルの初期化
		((MMM_TextureBox)textureBox[0]).models[0].setCapsValue(MMM_IModelCaps.caps_changeModel, this);
	}

	/**
	 * テクスチャのファイル名を獲得
	 */
	protected void setTextureNames() {
		textures[0][0] = ((MMM_TextureBox)textureBox[0]).getTextureName(color + (contract ? 0 : MMM_TextureManager.tx_wild));
		textures[0][1] = ((MMM_TextureBox)textureBox[0]).getTextureName(color + (contract ? MMM_TextureManager.tx_eyecontract : MMM_TextureManager.tx_eyewild));
		textures[1][0] = ((MMM_TextureBox)textureBox[1]).getArmorTextureName(MMM_TextureManager.tx_armor1, getCurrentArmor(0));
		textures[1][1] = ((MMM_TextureBox)textureBox[1]).getArmorTextureName(MMM_TextureManager.tx_armor1, getCurrentArmor(1));
		textures[1][2] = ((MMM_TextureBox)textureBox[1]).getArmorTextureName(MMM_TextureManager.tx_armor1, getCurrentArmor(2));
		textures[1][3] = ((MMM_TextureBox)textureBox[1]).getArmorTextureName(MMM_TextureManager.tx_armor1, getCurrentArmor(3));
		textures[2][0] = ((MMM_TextureBox)textureBox[1]).getArmorTextureName(MMM_TextureManager.tx_armor2, getCurrentArmor(0));
		textures[2][1] = ((MMM_TextureBox)textureBox[1]).getArmorTextureName(MMM_TextureManager.tx_armor2, getCurrentArmor(1));
		textures[2][2] = ((MMM_TextureBox)textureBox[1]).getArmorTextureName(MMM_TextureManager.tx_armor2, getCurrentArmor(2));
		textures[2][3] = ((MMM_TextureBox)textureBox[1]).getArmorTextureName(MMM_TextureManager.tx_armor2, getCurrentArmor(3));
	}

	@Override
	public void setColor(int pColor) {
		color = pColor;
	}

	@Override
	public int getColor() {
		return color;
	}

	@Override
	public void setContract(boolean pContract) {
		contract = pContract;
	}

	@Override
	public boolean isContract() {
		return contract;
	}

	@Override
	public void setTextureBox(MMM_TextureBoxBase[] pTextureBox) {
		textureBox = pTextureBox;
	}

	@Override
	public MMM_TextureBoxBase[] getTextureBox() {
		return textureBox;
	}

	@Override
	public void setTextures(int pIndex, ResourceLocation[] pNames) {
		textures[pIndex] = pNames;
	}

	@Override
	public ResourceLocation[] getTextures(int pIndex) {
		return textures[pIndex];
	}

	@Override
	public void setTextureIndex(int[] pTextureIndex) {
		textureIndex = pTextureIndex;
	}

	@Override
	public int[] getTextureIndex() {
		return textureIndex;
	}

}
