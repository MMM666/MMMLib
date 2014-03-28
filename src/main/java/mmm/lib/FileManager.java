package mmm.lib;

import java.io.File;
import java.net.URL;
import java.net.URLClassLoader;
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
	public static List<File> getAllmodsFiles(ClassLoader pClassLoader) {
		List<File> llist = new ArrayList<File>();
		if (pClassLoader instanceof URLClassLoader ) {
			for (URL lurl : ((URLClassLoader)pClassLoader).getURLs()) {
				try {
					String ls = lurl.toString();
					if (ls.endsWith("/bin/") || ls.indexOf("/mods/") > -1) {
						llist.add(new File(lurl.toURI()));
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		return llist;
	}

}
