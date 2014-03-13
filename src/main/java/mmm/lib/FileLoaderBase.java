package mmm.lib;

import java.io.File;
import java.io.FileInputStream;
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
		for (File lf : FileManager.getAllmodsFiles()) {
			String ls = lf.getName();
			if (isZipLoad() && ls.matches("(.+).(zip|jar)$")) {
				decodeZip(lf);
			} else {
				load(lf, ls);
			}
		}
	}

	public void decodeZip(File pFile) {
		try {
			FileInputStream lfis = new FileInputStream(pFile);
			ZipInputStream lzis = new ZipInputStream(lfis);
			for (ZipEntry lze = lzis.getNextEntry(); lze != null; lze = lzis.getNextEntry()) {
				if (!lze.isDirectory()) {
					load(pFile, lze.getName());
				}
			}
			
			lzis.close();
			lfis.close();
		} catch (Exception e) {
			e.printStackTrace();
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

}
