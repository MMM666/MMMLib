package mmm.lib.multiModel.model;

import net.minecraft.world.World;

/**
 * マルチモデル用識別クラス<br>
 * インターフェースでもいいような気がする。
 *
 */
public abstract class AbstractModelBase {

	/**
	 * アーマーモデルのサイズを返す。
	 * サイズは内側のものから。
	 */
	public abstract float[] getArmorModelsSize();

	public boolean canSpawnHear(World pWorld, int pX, int  pY, int pZ) {
		return true;
	}

	/**
	 *  身長
	 */
	public abstract float getHeight(IModelCaps pEntityCaps);
	/**
	 * 横幅
	 */
	public abstract float getWidth(IModelCaps pEntityCaps);
	/**
	 * モデルのYオフセット
	 */
	public abstract float getyOffset(IModelCaps pEntityCaps);
	/**
	 * 上に乗せる時のオフセット高
	 */
	public abstract float getMountedYOffset(IModelCaps pEntityCaps);

	/**
	 * ロープの取り付け位置調整用
	 * @return
	 */
	public abstract float getLeashOffset(IModelCaps pEntityCaps);

}
