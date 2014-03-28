package mmm.lib.multiModel.MMMLoader;

import java.util.Map;

import cpw.mods.fml.relauncher.IFMLLoadingPlugin;
import cpw.mods.fml.relauncher.IFMLLoadingPlugin.TransformerExclusions;

@TransformerExclusions({"mmm.lib.multiModel.MMMLoader"})
public class MMMCoremod implements IFMLLoadingPlugin{

	@Override
	public String[] getASMTransformerClass() {
		return new String[] {"mmm.lib.multiModel.MMMLoader.MMMTransformer"};
	}

	@Override
	public String getModContainerClass() {
		return "mmm.lib.multiModel.MMMLoader.MMMModContainer";
//		return null;
	}

	@Override
	public String getSetupClass() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void injectData(Map<String, Object> data) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String getAccessTransformerClass() {
		// TODO Auto-generated method stub
		return null;
	}

}
