package net.minecraft.src;

import java.util.List;
import java.util.Map;
import java.util.Random;

public class MMM_ModelBase extends ModelBase {



	@Override
	@Deprecated
	public final void setRotationAngles(float par1, float par2, float par3,
			float par4, float par5, float par6, Entity par7Entity) {
	}

	@Override
	@Deprecated
	public final void setLivingAnimations(EntityLiving par1EntityLiving, float par2,
			float par3, float par4) {
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
		setTextureOffset(par1Str, par2, par3);
	}
	@Override
	@Deprecated
	protected final void setTextureOffset(String par1Str, int par2, int par3) {
		super.setTextureOffset(par1Str, par2, par3);
	}

	public TextureOffset getTextureOffsetMM(String par1Str) {
		return getTextureOffset(par1Str);
	}
	@Override
	@Deprecated
	public final TextureOffset getTextureOffset(String par1Str) {
		return super.getTextureOffset(par1Str);
	}

	// ゲッター、セッター群
	public List getBoxList() {
		return boxList;
	}
	public float getOnGround() {
		return onGround;
	}
	public float setOnGround(float pOnGround) {
		return onGround = pOnGround;
	}
	public boolean getIsRiding() {
		return isRiding;
	}
	public boolean setIsRiding(boolean pIsRiding) {
		return isRiding = pIsRiding;
	}
	public boolean getIsChild() {
		return isChild;
	}
	public boolean setIsChild(boolean pIsChild) {
		return isChild = pIsChild;
	}
	public int getTextureWidth() {
		return textureWidth;
	}
	public int getTextureHeight() {
		return textureHeight;
	}
	public void setTextureSize(int pWidth, int pHeight) {
		textureWidth = pWidth;
		textureHeight = pHeight;
	}



}
