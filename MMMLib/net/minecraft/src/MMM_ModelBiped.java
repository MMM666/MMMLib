package net.minecraft.src;

import java.util.Map;
import java.util.Random;
import java.util.Map.Entry;

import javax.crypto.spec.PSource;

import org.lwjgl.opengl.GL11;

/**
 * 自作の人型モデル置き換え系の共通クラス
 */
public abstract class MMM_ModelBiped extends ModelBiped {

	/**
	 * アイテム表示対策
	 */
	public boolean isRendering = true;
	public boolean isWait;
	public MMM_ModelRenderer Arms[];
	public MMM_ModelRenderer HeadMount;
	public MMM_ModelRenderer HardPoint[];
	
	public Render render;
	public Map<String, MMM_EquippedStabilizer> stabiliser;


	/**
	 * コンストラクタは全て継承させること
	 */
	public MMM_ModelBiped() {
		this(0.0F);
	}
	/**
	 * コンストラクタは全て継承させること
	 */
	public MMM_ModelBiped(float psize) {
		this(psize, 0.0F);
	}
	/**
	 * コンストラクタは全て継承させること
	 */
	public MMM_ModelBiped(float psize, float pyoffset) {
		super();
		
		heldItemLeft = 0;
		heldItemRight = 0;
		isSneak = false;
		aimedBow = false;
		
		// ハードポイント
		Arms = new MMM_ModelRenderer[2];
		HeadMount = new MMM_ModelRenderer(this, "HeadMount");
		
		initModel(psize, pyoffset);
	}

	/**
	 *  モデルの初期化コード
	 */
	public abstract void initModel(float psize, float pyoffset);

	/**
	 * モデル切替時に実行されるコード
	 */
	public void changeModel(EntityLiving pentity) {
		// カウンタ系の加算値、リミット値の設定など行う予定。
	
	}

	/**
	 * ハードポイントに接続されたアイテムを表示する
	 */
	public void renderItems(EntityLiving pEntity, Render pRender) {
	}

	@Override
	public void render(Entity par1Entity, float par2, float par3, float par4, float par5, float par6, float par7) {
		if (preRender(par1Entity, par2, par3, par4, par5, par6, par7)) {
			this.setRotationAngles(par2, par3, par4, par5, par6, par7, par1Entity);
			this.bipedHead.render(par7);
			this.bipedBody.render(par7);
			this.bipedRightArm.render(par7);
			this.bipedLeftArm.render(par7);
			this.bipedRightLeg.render(par7);
			this.bipedLeftLeg.render(par7);
			this.bipedHeadwear.render(par7);
		}
		renderExtention(par1Entity, par2, par3, par4, par5, par6, par7);
		renderStabilizer(par1Entity, stabiliser, par2, par3, par4, par5, par6, par7);
	}

	/**
	 * 通常のレンダリング前に呼ばれる。
	 * @return falseを返すと通常のレンダリングをスキップする。
	 */
	public boolean preRender(Entity par1Entity, float par2, float par3, float par4, float par5, float par6, float par7) {
		return true;
	}

	/**
	 * 通常のレンダリング後に呼ぶ。
	 * 基本的に装飾品などの自律運動しないパーツの描画用。
	 */
	public void renderExtention(Entity par1Entity, float par2, float par3, float par4, float par5, float par6, float par7) {    	
	}

	/**
	 * スタビライザーの描画。
	 * 自動では呼ばれないのでrender内で呼ぶ必要があります。
	 */
	protected void renderStabilizer(Entity pEntity, Map<String, MMM_EquippedStabilizer> pmap, float par2, float par3, float par4, float par5, float par6, float par7) {
		// スタビライザーの描画、doRenderの方がいいか？
		if (pmap == null || pmap.isEmpty() || render == null) return;
		
		GL11.glPushMatrix();
		for (Entry<String, MMM_EquippedStabilizer> le : pmap.entrySet()) {
			MMM_EquippedStabilizer les = le.getValue();
			if (les != null && les.equipPoint != null) {
				MMM_ModelStabilizerBase lsb = les.stabilizer;
				if (lsb.isLoadAnotherTexture()) {
					render.loadTexture(lsb.getTexture());
				}
				les.equipPoint.loadMatrix();
				lsb.render(this, pEntity, par2, par3, par4, par5, par6, par7);
			}
		}
		GL11.glPopMatrix();
	}


	// 身長
	public abstract float getHeight();

	// 横幅
	public abstract float getWidth();

	public boolean isItemHolder() {
		// アイテムを持っているときに手を前に出すかどうか。
		return false;
	}

	public void showAllParts() {
		// 表示すべきすべての部品
	}
	
	public int showArmorParts(int parts) {
		// 部位ごとの装甲表示
		// 3:頭部
		// 2:胴部
		// 1:脚部
		// 0:足部
		// 戻り値は基本 -1
		return -1;
	}

	/**
	 * モデル毎のスポーン判定。
	 * マルチ化で実行不能になりました。
	 */
	@Deprecated
	public boolean getCanSpawnHere(World pworld, int px, int py, int pz, EntityLiving pentity) {
		return true;
	}


    // MathHelperトンネル関数群
    public static final float mh_sin(float f) {
    	f = f % 6.283185307179586F;
    	f = (f < 0F) ? 360 + f : f;
        return MathHelper.sin(f);
    }

    public static final float mh_cos(float f) {
    	f = f % 6.283185307179586F;
    	f = (f < 0F) ? 360 + f : f;
        return MathHelper.cos(f);
    }

    public static final float mh_sqrt_float(float f) {
        return MathHelper.sqrt_float(f);
    }

    public static final float mh_sqrt_double(double d) {
        return MathHelper.sqrt_double(d);
    }

    public static final int mh_floor_float(float f) {
    	return MathHelper.floor_float(f);
    }

    public static final int mh_floor_double(double d) {
        return MathHelper.floor_double(d);
    }

    public static final long mh_floor_double_long(double d) {
        return MathHelper.floor_double_long(d);
    }

    public static final float mh_abs(float f) {
    	return MathHelper.abs(f);
    }

    public static final double mh_abs_max(double d, double d1) {
        return MathHelper.abs_max(d, d1);
    }

    public static final int mh_bucketInt(int i, int j) {
        return MathHelper.bucketInt(i, j);
    }

    public static final boolean mh_stringNullOrLengthZero(String s) {
        return MathHelper.stringNullOrLengthZero(s);
    }

    public static final int mh_getRandomIntegerInRange(Random random, int i, int j) {
        return MathHelper.getRandomIntegerInRange(random, i, j);
    }


}
