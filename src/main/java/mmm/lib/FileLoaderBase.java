package mmm.lib;

import java.io.File;
import java.io.FileInputStream;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

/**
 * modsディレクトリに存在するファイルを処理するための基本クラス。
 * 特にMOD化されていないアーカイブも追加されているので、リソースの扱いとしてはファイル名だけで良いはず。
 *
 */
public abstract class FileLoaderBase {

	/**
	 * 処理を実行
	 */
	public void execute() {
//		List<File> llist = FileManager.getAllmodsFiles();
		List<File> llist = FileManager.getAllmodsFiles(getClass().getClassLoader());
		for (File lf : llist) {
			String ls = lf.getName();
			if (isZipLoad() && ls.matches("(.+).(zip|jar)$")) {
				decodeZip(lf);
			} if (lf.isDirectory()) {
				decodeDir(lf, lf);
			} else {
				preLoad(lf, ls);
			}
		}
	}

	/**
	 * Zipの解析
	 * @param pFile
	 */
	public void decodeZip(File pFile) {
		try {
			FileInputStream lfis = new FileInputStream(pFile);
			ZipInputStream lzis = new ZipInputStream(lfis);
			for (ZipEntry lze = lzis.getNextEntry(); lze != null; lze = lzis.getNextEntry()) {
				if (!lze.isDirectory()) {
					preLoad(pFile, lze.getName());
				}
			}
			
			lzis.close();
			lfis.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * ディレクトリの解析
	 * @param pFile
	 */
	public void decodeDir(File pBaseDir, File pFile) {
		for (File lf : pFile.listFiles()) {
			if (lf.isDirectory()) {
				decodeDir(pBaseDir, lf);
			} else {
				preLoad(lf, lf.getAbsolutePath().substring(pBaseDir.getAbsolutePath().length()));
			}
		}
	}

	/**
	 * zipファイルを解析するか？
	 * @return
	 */
	public abstract boolean isZipLoad();

	/**
	 * 解析動作の実装
	 * @param pFile
	 * @param pFileName
	 * @return
	 */
	public abstract boolean load(File pFile, String pFileName);

	public boolean preLoad(File pFile, String pFileName) {
//		if (!pFileName.startsWith("/")) {
//			pFileName = (new StringBuilder()).append("/").append(pFileName).toString();
//		} else {
//		}
		pFileName = pFileName.replace("\\", "/");
		return load(pFile, pFileName);
	}

}
