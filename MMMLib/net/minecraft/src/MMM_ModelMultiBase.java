package net.minecraft.src;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Map.Entry;

import org.lwjgl.opengl.GL11;

/**
 * マルチモデル用の基本クラス、これを継承していればマルチモデルとして使用できる。
 * Mincraftネイティブなクラスや継承関数などを排除して、難読化対策を行う。
 * 継承クラスではなくなったため、直接的な互換性はない。
 */
public abstract class MMM_ModelMultiBase extends MMM_ModelBase implements MMM_IModelCaps {

	public float heldItemLeft;
	public float heldItemRight;
	public boolean aimedBow;
	public boolean isSneak;
	public boolean isWait;
	
	public MMM_ModelRenderer mainFrame;
	public MMM_ModelRenderer HeadMount;
	public MMM_ModelRenderer HeadTop;
	public MMM_ModelRenderer Arms[];
	public MMM_ModelRenderer HardPoint[];
	
	public float entityIdFactor;
	public int entityTicksExisted;
	// 変数である意味ない？
	public float scaleFactor = 0.9375F;
	/**
	 * モデルが持っている機能群
	 */
	private final Map<String, Integer> fcapsmap = new HashMap<String, Integer>() {{
		put("onGround",			caps_onGround);
		put("isRiding",			caps_isRiding);
		put("isSneak",			caps_isSneak);
		put("isWait",			caps_isWait);
		put("isChild",			caps_isChild);
		put("heldItemLeft",		caps_heldItemLeft);
		put("heldItemRight",	caps_heldItemRight);
		put("aimedBow",			caps_aimedBow);
		put("ScaleFactor", 		caps_ScaleFactor);
		put("entityIdFactor",	caps_entityIdFactor);
	}};



	public MMM_ModelMultiBase() {
		this(0.0F);
	}

	public MMM_ModelMultiBase(float pSizeAdjust) {
		this(pSizeAdjust, 0.0F, 64, 32);
	}

	public MMM_ModelMultiBase(float pSizeAdjust, float pYOffset, int pTextureWidth, int pTextureHeight) {
		heldItemLeft = 0;
		heldItemRight = 0;
		isSneak = false;
		aimedBow = false;
		textureWidth = pTextureWidth;
		textureHeight = pTextureHeight;
		
		// ハードポイント
		Arms = new MMM_ModelRenderer[2];
		HeadMount = new MMM_ModelRenderer(this, "HeadMount");
		HeadTop = new MMM_ModelRenderer(this, "HeadTop");
		
		initModel(pSizeAdjust, pYOffset);
	}

	// 独自定義関数群

	/**
	 * モデルの初期化コード
	 */
	public abstract void initModel(float psize, float pyoffset);

	/**
	 * アーマーモデルのサイズを返す。
	 * サイズは内側のものから。
	 */
	public abstract float[] getArmorModelsSize();

	/**
	 * モデル指定詞に依らずに使用するテクスチャパック名
	 * @return
	 */
	public String getUsingTexture() {
		return null;
	}

	/**
	 *  身長
	 */
	public abstract float getHeight();
	/**
	 * 横幅
	 */
	public abstract float getWidth();
	/**
	 * モデルのYオフセット
	 * PF用。
	 */
	public abstract float getyOffset();
	/**
	 * 上に乗せる時のオフセット高
	 */
	public abstract float getMountedYOffset();

	/**
	 * アイテムを持っているときに手を前に出すかどうか。
	 */
	public boolean isItemHolder() {
		return false;
	}

	/**
	 * 表示すべきすべての部品
	 */
	public void showAllParts() {
	}

	/**
	 * 部位ごとの装甲表示。
	 * @param parts
	 * 3:頭部。
	 * 2:胴部。
	 * 1:脚部
	 * 0:足部
	 * @param index
	 * 0:inner
	 * 1:outer
	 * @return
	 * 戻り値は基本 -1
	 */
	public int showArmorParts(int parts, int index) {
		return -1;
	}

	/**
	 * ハードポイントに接続されたアイテムを表示する
	 */
	public abstract void renderItems(MMM_IModelCaps pEntityCaps);

	public abstract void renderFirstPersonHand(MMM_IModelCaps pEntityCaps);



	// IModelCaps

	@Override
	public Map<String, Integer> getModelCaps() {
		return fcapsmap;
	}

	@Override
	public Object getCapsValue(int pIndex, Object ...pArg) {
		switch (pIndex) {
		case caps_onGround:
			return onGrounds;
		case caps_isRiding:
			return isRiding;
		case caps_isSneak:
			return isSneak;
		case caps_isWait:
			return isWait;
		case caps_isChild:
			return isChild;
		case caps_heldItemLeft:
			return heldItemLeft;
		case caps_heldItemRight:
			return heldItemRight;
		case caps_aimedBow:
			return aimedBow;
		case caps_entityIdFactor:
			return entityIdFactor;
		case caps_ticksExisted:
			return entityTicksExisted;
		case caps_ScaleFactor:
			return scaleFactor;
		}
		return null;
	}

	@Override
	public boolean setCapsValue(int pIndex, Object... pArg) {
		switch (pIndex) {
		case caps_onGround:
			for (int li = 0; li < onGrounds.length && li < pArg.length; li++) {
				onGrounds[li] = (Float)pArg[li];
			}
			return true;
		case caps_isRiding:
			isRiding = (Boolean)pArg[0];
			return true;
		case caps_isSneak:
			isSneak = (Boolean)pArg[0];
			return true;
		case caps_isWait:
			isWait = (Boolean)pArg[0];
			return true;
		case caps_isChild:
			isChild = (Boolean)pArg[0];
			return true;
		case caps_heldItemLeft:
			heldItemLeft = (Integer)pArg[0];
			return true;
		case caps_heldItemRight:
			heldItemRight = (Integer)pArg[0];
			return true;
		case caps_aimedBow:
			aimedBow = (Boolean)pArg[0];
			return true;
		case caps_entityIdFactor:
			entityIdFactor = (Float)pArg[0];
			return true;
		case caps_ticksExisted:
			entityTicksExisted = (Integer)pArg[0];
			return true;
		case caps_ScaleFactor:
			scaleFactor = (Float)pArg[0];
			return true;
		}
		
		return false;
	}

}
