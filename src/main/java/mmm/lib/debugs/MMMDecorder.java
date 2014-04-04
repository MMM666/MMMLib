package mmm.lib.debugs;

import java.io.File;
import java.io.InputStream;
import java.io.InputStreamReader;

import mmm.lib.FileLoaderBase;
import mmm.lib.MMMLib;

import com.google.common.base.Charsets;
import com.google.gson.stream.JsonReader;

/**
 * JSONを解析してアイテム、レシピを追加する。
 *
 */
public class MMMDecorder extends FileLoaderBase {

	@Override
	public void execute() {
		// TODO Auto-generated method stub
		super.execute();
	}

	@Override
	public boolean isZipLoad() {
		return true;
	}

	@Override
	public boolean load(File pFile, String pFileName, InputStream pInputStream) {
		String lname = "/" + pFileName;
		lname = lname.substring(lname.lastIndexOf('/'));
		if (lname.startsWith("/MMMScript") && lname.endsWith(".json")) {
			MMMLib.Debug("decode-file: %s", pFileName);
//			Gson lgson = new Gson();
//			JsonParser ljp = new JsonParser();
//			JsonObject ljo;
			InputStreamReader lisr = new InputStreamReader(pInputStream, Charsets.UTF_8);
//			JsonElement lje = ljp.parse(lisr);
			JsonReader ljr = new JsonReader(lisr);
			try {
				ljr.beginArray();
				while (ljr.hasNext()) {
					MMMLib.Debug("decode: %s", ljr.nextName());
				}
				ljr.endArray();
				ljr.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
//			ljo = lje.
			
//			lgson.fromJson(lisr, null);
			return true;
		}
		return false;
	}

	protected boolean addItems() {
		return true;
	}

	protected boolean addRecipes() {
		return true;
	}

}
