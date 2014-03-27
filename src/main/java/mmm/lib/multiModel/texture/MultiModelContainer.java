package mmm.lib.multiModel.texture;

import java.io.File;
import java.io.FileInputStream;
import java.util.HashMap;
import java.util.Map;

import net.minecraft.client.model.ModelBase;
import net.minecraft.util.ResourceLocation;

/**
 * 個別のマルチテクスチャ・モデルを管理する。
 *
 */
public class MultiModelContainer {

	/**
	 * 標準のパーツ表示状況を設定する
	 */
	public int[] defaultVisivles = new int[16];
	
	/**
	 * バインドされているモデルクラス
	 */
	protected ModelBase defaultModel;
	protected Map<Integer, ModelBase> models;
	/**
	 * バインドされているテクスチャ
	 */
	protected Map<Integer, ResourceLocation> textures;


	public MultiModelContainer() {
		models = new HashMap<Integer, ModelBase>();
		textures = new HashMap<Integer, ResourceLocation>();
	}

	/**
	 * 渡されたストリームをJSONとして読み込む
	 * @param pStream
	 * @return
	 */
	public boolean loadJSON(FileInputStream pStream) {
		return false;
	}

	public void addTexture(int pIndex) {
		ResourceLocation lres = new ResourceLocation("");
	}

	/**
	 * インデックスに応じたテクスチャを返す
	 * @param pIndex
	 * @return
	 */
	public ResourceLocation getTexture(int pIndex) {
		return textures.get(pIndex);
	}

	public ModelBase getModelClass(int pIndex) {
		if (models.containsKey(pIndex)) {
			return models.get(pIndex);
		}
		return defaultModel;
	}

	public MultiModelEntry getMultiModel() {
		return new MultiModelEntry();
	}

}
