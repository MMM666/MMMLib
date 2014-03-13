package mmm.lib.debugs;

import java.io.File;
import java.io.FileReader;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;

import mmm.lib.FileManager;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.JsonToNBT;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;

import com.google.gson.Gson;

import cpw.mods.fml.common.registry.GameRegistry;

/**
 * JSONでレシピを追加する
 *
 */
public class EzRecipes {

	public static boolean isDebugMessage = true;


	protected class useItem {
		Character chr;
		String item;
	}

	protected class EzRecipe {
		public boolean isShape;
		public String output;
		public String[] shape;
		public useItem[] material;
	
		public void addRecipe() {
			ItemStack lis = getItemStack(output, 0);
			EzRecipes.Debug("Add Recipes to:%s", lis.toString());
			Object[] lo;
			int li = 0;
			if (isShape) {
				lo = new Object[shape.length + material.length * 2];
				for (int lj = 0; lj < shape.length; lj++) {
					lo[li++] = shape[lj];
				}
				for (int lj = 0; lj < material.length; lj++) {
					lo[li++] = material[lj].chr;
					lo[li++] = getItemStack(material[lj].item, 0);
				}
				GameRegistry.addShapedRecipe(lis, lo);
			} else {
				lo = new Object[shape.length + material.length * 2];
				for (int lj = 0; lj < material.length; lj++) {
					lo[li++] = material[lj].chr;
					lo[li++] = getItemStack(material[lj].item, 0);
				}
				GameRegistry.addShapelessRecipe(lis, lo);
			}
		}
	}

	private static final ParameterizedType typeListString = new ParameterizedType() {
		public Type[] getActualTypeArguments() {
			return new Type[] { EzRecipe.class };
		}
		public Type getRawType() {
			return List.class;
		}
		public Type getOwnerType() {
			return null;
		}
	};


	public static void Debug(String pText, Object... pData) {
		// デバッグメッセージ
		if (isDebugMessage) {
			System.out.println(String.format("EzRecipes-" + pText, pData));
		}
	}

	public static String getSerialStr(String[] pStr, int pIndex) {
		String ls = "";
		for (;pIndex < pStr.length; pIndex++) {
			ls += pStr[pIndex];
		}
		return ls;
	}

	public static ItemStack getItemStack(String pStr, int pIndex) {
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

	public static void init() {
		List<File> llist = FileManager.getAllmodsFiles();
		for (File lf : llist) {
			decodeJSON(lf);
		}
	}

	public static boolean decodeJSON(File pFile) {
		if (!pFile.getName().startsWith("MMMRecipes_") || !pFile.getName().endsWith(".json")) {
			return false;
		}
		// jsonの解析
		Gson lgson = new Gson();
		FileReader lreader;
		try {
			lreader = new FileReader(pFile);
			List<EzRecipe> lrep = lgson.fromJson(lreader, typeListString);
			lreader.close();
			
			for (EzRecipe lr : lrep) {
				lr.addRecipe();
			}
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

}
