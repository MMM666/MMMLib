package mmm.lib.debugs;

import java.io.File;

import mmm.lib.FileLoaderBase;
import mmm.lib.MMMLib;

public class fileTest extends FileLoaderBase {

	@Override
	public boolean isZipLoad() {
		return true;
	}

	@Override
	public boolean load(File pfile, String pFileName) {
		MMMLib.Debug(pFileName);
		return true;
	}

}
