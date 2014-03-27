package mmm.lib.multiModel.texture;

import net.minecraft.util.ResourceLocation;

/**
 * マルチモデルに含まれる個別のモデル、テクスチャを保持
 *
 */
public class MultiModelEntry {

	protected int color;
	protected Class modelClass;
	protected ResourceLocation texture;


	public Class getModelClass() {
		return null;
	}

	public int getColor() {
		return color;
	}

}
