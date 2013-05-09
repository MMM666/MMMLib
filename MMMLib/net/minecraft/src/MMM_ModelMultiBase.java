package net.minecraft.src;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Map.Entry;

import org.lwjgl.opengl.GL11;

/**
 * Mincraftネイティブなクラスや継承関数などを排除して、難読化対策を行う。
 *
 */
public abstract class MMM_ModelMultiBase
	extends MMM_ModelBase implements MMM_IModelCaps {

	public MMM_ModelRenderer mainFrame;
	
	public int heldItemLeft;
	public int heldItemRight;
	public boolean isSneak;
	public boolean aimedBow;
	public boolean isWait;
	
	public boolean isRendering = true;
	public MMM_ModelRenderer Arms[];
	public MMM_ModelRenderer HeadMount;
	public MMM_ModelRenderer HeadTop;
	public MMM_ModelRenderer HardPoint[];
	
	public Render render;
	public Map<String, MMM_EquippedStabilizer> stabiliser;
	public float scaleFactor = 0.9375F;
	public float entityIdFactor;
	public int entityTicksExisted;
	/**
	 * Entityの代わりに固有値を取得するのに使う。
	 * 逆に対応していないとまともに動作しない。
	 */
	public MMM_IModelCaps entityCaps;
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
		setTextureSize(pTextureWidth, pTextureHeight);
		
		// ハードポイント
		Arms = new MMM_ModelRenderer[2];
		HeadMount = new MMM_ModelRenderer(this, "HeadMount");
		HeadTop = new MMM_ModelRenderer(this, "HeadTop");
		
		initModel(pSizeAdjust, pYOffset);
		checkParents();
	}

	/**
	 * 登録されたボックスの親子関係をチェックして、子に親を認識させる。
	 */
	protected void checkParents() {
		for (int li = 0; li < boxList.size(); li++) {
			ModelRenderer lmr = (ModelRenderer)boxList.get(li);
			if (lmr.childModels != null) {
				for (int lj = 0; lj < lmr.childModels.size(); lj++) {
					ModelRenderer lmc = (ModelRenderer)lmr.childModels.get(lj);
					if (lmc instanceof MMM_ModelRenderer) {
						((MMM_ModelRenderer)lmc).pearent = lmr;
					}
				}
			}
		}
	}



	/**
	 * mainFrameに全てぶら下がっているならば標準で描画する。
	 */
	public void renderMM(float par2, float par3, float ticksExisted,
			float pheadYaw, float pheadPitch, float par7) {
		setRotationAnglesMM(par2, par3, ticksExisted, pheadYaw, pheadPitch, par7);
		mainFrame.renderMM(par7);
		renderStabilizer(par2, par3, ticksExisted, pheadYaw, pheadPitch, par7);
	}
	@Override
	@Deprecated
	public final void render(Entity par1Entity, float par2, float par3, float par4,
			float par5, float par6, float par7) {
		renderMM(par2, par3, par4, par5, par6, par7);
	}

	public void setRotationAnglesMM(float par1, float par2, float pTicksExisted,
			float pHeadYaw, float pHeadPitch, float par6) {
	}
	@Override
	@Deprecated
	public final void setRotationAngles(float par1, float par2, float par3,
			float par4, float par5, float par6, Entity par7Entity) {
		setRotationAnglesMM(par1, par2, par3, par4, par5, par6);
	}

	public void setLivingAnimationsMM(float par2, float par3, float pRenderPartialTicks) {
	}
	@Override
	@Deprecated
	public final void setLivingAnimations(EntityLiving par1EntityLiving,
			float par2, float par3, float par4) {
		setLivingAnimationsMM(par2, par3, par4);
	}

	public MMM_ModelRenderer getRandomModelBoxMM(Random par1Random) {
		// 膝に矢を受けてしまってな・・・
		int li = par1Random.nextInt(this.boxList.size());
		MMM_ModelRenderer lmr = (MMM_ModelRenderer)this.boxList.get(li);
		for (int lj = 0; lj < boxList.size(); lj++) {
			if (!lmr.cubeList.isEmpty()) {
				break;
			}
			// 箱がない
			if (++li >= boxList.size()) {
				li = 0;
			}
			lmr = (MMM_ModelRenderer)this.boxList.get(li);
		}
		return lmr;
	}
	@Override
	@Deprecated
	public final ModelRenderer getRandomModelBox(Random par1Random) {
		return getRandomModelBoxMM(par1Random);
	}

	protected void setTextureOffsetMM(String par1Str, int par2, int par3) {
		super.setTextureOffset(par1Str, par2, par3);
	}
	@Override
	@Deprecated
	protected final void setTextureOffset(String par1Str, int par2, int par3) {
		setTextureOffsetMM(par1Str, par2, par3);
	}

	/**
	 * 推奨されません。
	 */
	public TextureOffset getTextureOffsetMM(String par1Str) {
		// このままだと意味ないな。
		return super.getTextureOffset(par1Str);
	}
	@Override
	@Deprecated
	public final TextureOffset getTextureOffset(String par1Str) {
		return getTextureOffsetMM(par1Str);
	}



	// 独自定義関数群
	/**
	 * モデルの初期化コード
	 */
	public abstract  void initModel(float psize, float pyoffset);

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
	 * アイテムを持っているときに手を前に出すかどうか。
	 */
	public boolean isItemHolder() {
		return false;
	}

	/**
	 * モデル切替時に実行されるコード
	 * @param pEntityCaps
	 * Entityの値を操作するためのModelCaps。
	 */
	public void changeModel(MMM_IModelCaps pEntityCaps) {
		// カウンタ系の加算値、リミット値の設定など行う予定。
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
	 * @return
	 * 戻り値は基本 -1
	 */
	public int showArmorParts(int parts) {
		return -1;
	}

	@Override
	public Map<String, Integer> getModelCaps() {
		return fcapsmap;
	}

	/**
	 * Renderer辺でこの変数を設定する。
	 * 設定値はMMM_IModelCapsを継承したEntitiyとかを想定。
	 */
	public void setEntityCaps(MMM_IModelCaps pEntityCaps) {
		entityCaps = pEntityCaps;
	}

	/**
	 * 通常のレンダリング前に呼ばれる。
	 * @return falseを返すと通常のレンダリングをスキップする。
	 */
	public boolean preRender(float par2, float par3,
			float par4, float par5, float par6, float par7) {
		return true;
	}

	/**
	 * 通常のレンダリング後に呼ぶ。 基本的に装飾品などの自律運動しないパーツの描画用。
	 */
	public void renderExtention(float par2, float par3,
			float par4, float par5, float par6, float par7) {
	}

	/**
	 * スタビライザーの描画。 自動では呼ばれないのでrender内で呼ぶ必要があります。
	 */
	protected void renderStabilizer(float par2, float par3,
			float ticksExisted, float pheadYaw, float pheadPitch, float par7) {
		// スタビライザーの描画、doRenderの方がいいか？
		if (stabiliser == null || stabiliser.isEmpty() || render == null)
			return;

		GL11.glPushMatrix();
		for (Entry<String, MMM_EquippedStabilizer> le : stabiliser.entrySet()) {
			MMM_EquippedStabilizer les = le.getValue();
			if (les != null && les.equipPoint != null) {
				MMM_ModelStabilizerBase lsb = les.stabilizer;
				if (lsb.isLoadAnotherTexture()) {
					render.loadTexture(lsb.getTexture());
				}
				les.equipPoint.loadMatrix();
				lsb.render(this, null, par2, par3, ticksExisted, pheadYaw, pheadPitch, par7);
			}
		}
		GL11.glPopMatrix();
	}

	/**
	 * ハードポイントに接続されたアイテムを表示する
	 */
	public abstract void renderItems();


	// IModelCaps
	@Override
	public Object getCapsValue(int pIndex, Object ...pArg) {
		switch (pIndex) {
		case caps_onGround:
			return onGround;
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
		case caps_ScaleFactor:
			return scaleFactor;
		case caps_entityIdFactor:
			return entityIdFactor;
		}
		return null;
	}

	@Override
	public boolean setCapsValue(int pIndex, Object... pArg) {
		switch (pIndex) {
		case caps_onGround:
			onGround = (Float)pArg[0];
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
		case caps_ScaleFactor:
			scaleFactor = (Float)pArg[0];
			return true;
		case caps_entityIdFactor:
			entityIdFactor = (Float)pArg[0];
			return true;
		}
		
		return false;
	}

}
