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
}
