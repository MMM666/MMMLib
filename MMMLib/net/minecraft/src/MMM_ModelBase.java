package net.minecraft.src;

import java.util.List;
import java.util.Random;

public class MMM_ModelBase extends ModelBase {

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

	public static final int mh_getRandomIntegerInRange(Random random, int i,
			int j) {
		return MathHelper.getRandomIntegerInRange(random, i, j);
	}

}
