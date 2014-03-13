package mmm.lib;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import cpw.mods.fml.relauncher.FMLInjectionData;

public class FileManager {

	public static File dirMinecraft;
	public static File dirMods;
	public static File dirModsVersion;

	static {
		Object[] lo = FMLInjectionData.data();
		dirMinecraft = (File)lo[6];
		dirMods = new File(dirMinecraft, "mods");
		dirModsVersion = new File(dirMods, (String)lo[4]);
		MMMLib.Debug("init FileManager.");
	}

	/**
	 * modsディレクトリに含まれるファイルを全て返す。<br>
	 * バージョンごとの物も含む。
	 * @return
	 */
	public static List<File> getAllmodsFiles() {
		List<File> llist = new ArrayList<File>();
		if (dirMods.exists()) {
			for (File lf : dirMods.listFiles()) {
				llist.add(lf);
			}
		}
		if (dirModsVersion.exists()) {
			for (File lf : dirModsVersion.listFiles()) {
				llist.add(lf);
			}
		}
		return llist;
	}

}
