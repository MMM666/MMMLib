package mmm.lib.debugs;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import mmm.lib.FileLoaderBase;
import mmm.lib.MMMLib;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.JsonToNBT;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;

import com.google.common.base.Charsets;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.stream.JsonReader;

import cpw.mods.fml.common.registry.GameRegistry;

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
			InputStreamReader lisr = new InputStreamReader(pInputStream, Charsets.UTF_8);
			JsonReader ljr = new JsonReader(lisr);
			try {
				ljr.beginArray();
				while (ljr.hasNext()) {
					decodeEntry(ljr);
				}
				ljr.endArray();
				ljr.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
			return true;
		}
		return false;
	}

	protected void decodeEntry(JsonReader pReader) throws IOException, ClassNotFoundException {
		// モードを切り替える
		String lname;
		
		pReader.beginObject();
		while (pReader.hasNext()) {
			lname = pReader.nextName();
			if (lname.equals("addItem")) {
				addItems(pReader);
			} else if (lname.equals("addRecipe")) {
				addRecipes(pReader);
			} else {
				pReader.skipValue();
			}
		}
		
		pReader.endObject();
	}

	protected void addItems(JsonReader pReader) throws IOException, ClassNotFoundException {
		// アイテムの追加
		String lname;
		Item litem = null;
		String luname = null;
		String licon = null;
		int lmaxdamage = -1;
		
		pReader.beginObject();
		while (pReader.hasNext()) {
			lname = pReader.nextName();
			if (lname.equals("class")) {
				litem = getItem(pReader);
			} else if (lname.equals("UnlocalizedName")) {
				luname = pReader.nextString();
			} else if (lname.equals("icon")) {
				licon = pReader.nextString();
			} else if (lname.equals("MaxDamage")) {
				lmaxdamage = pReader.nextInt();
			} else {
				pReader.skipValue();
			}
		}
		if (litem != null) {
			litem.setUnlocalizedName(luname);
			if (licon != null) {
				litem.setTextureName(licon);
			}
			if (lmaxdamage > -1) {
				litem.setMaxDamage(lmaxdamage);
			}
			GameRegistry.registerItem(litem, luname);
			MMMLib.Debug("addItem: %s", litem.toString());
		}
		pReader.endObject();
	}

	protected void addRecipes(JsonReader pReader) throws IOException, ClassNotFoundException {
		// レシピの追加
		String lname;
		ItemStack lres = null;
		String[] lshape = null;
		Map<Character, ItemStack> lmat = null;
		
		pReader.beginObject();
		while (pReader.hasNext()) {
			lname = pReader.nextName();
			if (lname.equals("result")) {
				lres = getItemStack(pReader.nextString(), 0);
			} else if (lname.equals("shape")) {
				lshape = getStrings(pReader);
			} else if (lname.equals("materials")) {
				lmat = getMaterials(pReader);
			} else {
				pReader.skipValue();
			}
		}
		Object[] lobject = new Object[(lshape == null ? 0 : lshape.length) + (lmat.size() * 2)];
		int li = 0;
		if (lshape != null) {
			for (String ls : lshape) {
				lobject[li++] = ls;
			}
		}
		for (Entry<Character, ItemStack> le : lmat.entrySet()) {
			lobject[li++] = le.getKey();
			lobject[li++] = le.getValue();
		}
		
		if (lshape == null) {
			GameRegistry.addShapelessRecipe(lres, lobject);
		} else {
			GameRegistry.addShapedRecipe(lres, lobject);
		}
		MMMLib.Debug("addRecipe: %s", lres.toString());
		
		pReader.endObject();
	}

	protected Item getItem(JsonReader pReader) throws IOException, ClassNotFoundException {
		// アイテムの作成
		String lcname = null;
		Item litem = null;
		
		pReader.beginObject();
		if (pReader.hasNext()) {
			lcname = pReader.nextName();
			Class<?> lclass = getClass().getClassLoader().loadClass(lcname);
//			Gson lgson = (new GsonBuilder()).excludeFieldsWithoutExposeAnnotation().create();
			Gson lgson = (new GsonBuilder()).create();
			litem = lgson.fromJson(pReader, lclass);
		}
		pReader.endObject();
		
		return litem;
	}

	protected ItemStack getItemStack(String pStr, int pIndex) {
		// アイテムスタックを作成、文字列はgiveコマンドと同じ
		String ls[] = pStr.trim().split(" ");
		if (ls.length <= pIndex) {
			return null;
		}
		int lc = (ls.length > (1 + pIndex)) ? Integer.parseInt(ls[1 + pIndex]) : 1;
		int ld = (ls.length > (2 + pIndex)) ? Integer.parseInt(ls[2 + pIndex]) : 0;
		Item litem = (Item)Item.itemRegistry.getObject(ls[pIndex]);
		if (litem == null) {
			// IDから
			litem = Item.getItemById(Integer.parseInt(ls[pIndex]));
		}
		ItemStack lis = new ItemStack(litem, lc, ld);
		if (ls.length > (3 + pIndex)) {
			try {
				NBTBase lnbtbase = JsonToNBT.func_150315_a(getSerialStr(ls, 3 + pIndex));
				if (lnbtbase instanceof NBTTagCompound) {
					lis.setTagCompound((NBTTagCompound)lnbtbase);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return lis;
	}

	protected String getSerialStr(String[] pStr, int pIndex) {
		String ls = "";
		for (;pIndex < pStr.length; pIndex++) {
			ls += pStr[pIndex];
		}
		return ls;
	}

	protected String[] getStrings(JsonReader pReader) throws IOException, ClassNotFoundException {
		// String[]
		List<String> llist = new ArrayList<String>();
		
		pReader.beginArray();
		while (pReader.hasNext()) {
			llist.add(pReader.nextString());
		}
		pReader.endArray();
		
		return llist.toArray(new String[0]);
	}

	protected Map<Character, ItemStack> getMaterials(JsonReader pReader) throws IOException, ClassNotFoundException {
		// レシピの要素を獲得
		Map<Character, ItemStack> lmat = new HashMap<Character, ItemStack>();
		
		pReader.beginObject();
		while (pReader.hasNext()) {
			lmat.put(pReader.nextName().charAt(0), getItemStack(pReader.nextString(), 0));
		}
		pReader.endObject();
		
		return lmat;
	}

}
