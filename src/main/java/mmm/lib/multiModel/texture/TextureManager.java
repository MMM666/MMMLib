package mmm.lib.multiModel.texture;

import java.io.File;

import mmm.lib.FileLoaderBase;

public class TextureManager extends FileLoaderBase {

	public static TextureManager instance;


	@Override
	public boolean isZipLoad() {
		return true;
	}

	@Override
	public boolean load(File pFile, String pFileName) {
		// TODO Auto-generated method stub
		return false;
	}

}
