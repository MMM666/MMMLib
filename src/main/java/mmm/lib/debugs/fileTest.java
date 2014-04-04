package mmm.lib.debugs;

import java.io.File;
import java.io.InputStream;

import mmm.lib.FileLoaderBase;
import mmm.lib.MMMLib;

public class fileTest extends FileLoaderBase {

	@Override
	public boolean isZipLoad() {
		return true;
	}

	@Override
	public boolean load(File pFile, String pFileName, InputStream pInputStream) {
		MMMLib.Debug(pFileName);
		return true;
	}

}
