package mmm.lib.multiModel.texture;

/**
 * MultiModelDataを保持する機能を持ったEntityの証。<br>
 * フルスペックの動作を希望ならこれを継承する必要がある。
 *
 */
public interface IMultiModelEntity {

	public MultiModelData getMultiModel();
	public void setMultiModelData(MultiModelData pMultiModelData);
	/**
	 * 初期化時に呼ばれる
	 */
	public void initMultiModel();
	/**
	 * 状態が変更された時に呼ばれる
	 */
	public void onMultiModelChange();

}
