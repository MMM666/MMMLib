package mmm.lib.multiModel.texture;

/**
 * MultiModelDataを保持する機能を持ったEntityの証
 *
 */
public interface IMultiModelEntity {

	public MultiModelData getMultiModel();
	public void setMultiModelData(MultiModelData pMultiModelData);
	public void initMultiModel();

}
