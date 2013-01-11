package net.minecraft.src;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Random;
import java.util.TreeMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import net.minecraft.server.MinecraftServer;

public class MMM_TextureManager {

//	private static String defDirName = "/mob/littleMaid/";
	/**
	 * 旧タイプのファイル名
	 */
	private static String defNames[] = {
		"mob_littlemaid0.png", "mob_littlemaid1.png",
		"mob_littlemaid2.png", "mob_littlemaid3.png",
		"mob_littlemaid4.png", "mob_littlemaid5.png",
		"mob_littlemaid6.png", "mob_littlemaid7.png",
		"mob_littlemaid8.png", "mob_littlemaid9.png",
		"mob_littlemaida.png", "mob_littlemaidb.png",
		"mob_littlemaidc.png", "mob_littlemaidd.png",
		"mob_littlemaide.png", "mob_littlemaidf.png",
		"mob_littlemaidw.png",
		"mob_littlemaid_a00.png", "mob_littlemaid_a01.png"
	};
	
	public static final int tx_oldwild		= 0x10; //16;
	public static final int tx_oldarmor1	= 0x11; //17;
	public static final int tx_oldarmor2	= 0x12; //18;
	public static final int tx_gui			= 0x20; //32;
	public static final int tx_wild			= 0x30; //48;
	public static final int tx_armor1		= 0x40; //64;
	public static final int tx_armor2		= 0x50; //80;
	public static List<MMM_TextureBox> textures = new ArrayList<MMM_TextureBox>();
	private static Map<String, MMM_ModelBiped[]> modelMap = new TreeMap<String, MMM_ModelBiped[]>();
	public static String[] armorFilenamePrefix;
	public static MMM_ModelBiped[] defaultModel;
	/**
	 * サーバー・クライアント間でテクスチャパックの名称リストの同期を取るのに使う。
	 * うまいこと作れば、クライアント側にだけテクスチャパックを入れれば、サーバには不要になるはず。
	 * クライアントからサーバーにインデックスリストに無い名称のインデックスをリクエスト。
	 * サーバーからリクエストされたインデックスを返す、無ければサーバー側のリストに追加して値を返す。
	 * クライアント側のリストに追加。
	 */
	public static Map<Integer, String> textureIndex = new HashMap<Integer, String>();
	/**
	 * クライアント側は要らない
	 */
	public static Map<Integer, Integer> textureColor = new HashMap<Integer, Integer>();
	private static String[] requestString = new String[] {
		null, null, null, null, null, null, null, null,
		null, null, null, null, null, null, null, null
	};
	protected static List<String[]> searchPrefix = new ArrayList<String[]>();



	public static void init() {
		MMM_FileManager.getModFile("littleMaidMob", "littleMaidMob");
		addSearch("littleMaidMob", "/mob/littleMaid/", "ModelLittleMaid_");
	}

	public static String[] getSearch(String pName) {
		for (String[] lss : searchPrefix) {
			if (lss[0].equals(pName)) {
				return lss;
			}
		}
		return null;
	}

	/**
	 * 追加対象となる検索対象ファイル群とそれぞれの検索文字列を設定する。
	 */
	public static void addSearch(String pName, String pTextureDir, String pClassPrefix) {
		searchPrefix.add(new String[] {pName, pTextureDir, pClassPrefix});
	}

	/**
	 * パッケージ名称の一致する物を返す。
	 */
	public static MMM_TextureBox getTextureBox(String pName) {
		for (MMM_TextureBox ltb : textures) {
			if (ltb.packegeName.equals(pName)) {
				return ltb;
			}
		}
		return null;
	}

	private static void getArmorPrefix() {
		// アーマーファイルのプリフィックスを獲得
		try {
			Field f = RenderPlayer.class.getDeclaredFields()[3];
			f.setAccessible(true);
			String[] s = (String[])f.get(null);
			List<String> list = Arrays.asList(s);
			armorFilenamePrefix = list.toArray(new String[0]);
//			for (String t : armorFilenamePrefix) {
//				mod_littleMaidMob.Debug("armor:".concat(t));
//			}
		}
		catch (Exception e) {
		}
	}


	public static boolean loadTextures() {
		// アーマーのファイル名を識別するための文字列を獲得する
		getArmorPrefix();
		
		// デフォルトテクスチャ名の作成
		if (defaultModel != null) {
			String[] lss = getSearch("littleMaidMob");
			for (int i = 0; i < defNames.length; i++) {
				addTextureName((new StringBuilder()).append(lss[1]).append("default/").append(defNames[i]).toString(), lss);
			}
			modelMap.put("default", defaultModel);
			getStringToIndex("default");
			mod_MMM_MMMLib.Debug("getTexture-append-default-done.");
		}
		
		for (String[] lss : searchPrefix) {
			mod_MMM_MMMLib.Debug(String.format("getTexture[%s].", lss[0]));
			// jar内のテクスチャを追加
			if (MMM_FileManager.minecraftJar == null) {
				mod_MMM_MMMLib.Debug("getTexture-append-jar-file not founded.");
			} else {
				addTexturesJar(MMM_FileManager.minecraftJar, lss);
			}
			
			// mods
			for (File lf : MMM_FileManager.getFileList(lss[0])) {
				boolean lflag;
				if (lf.isDirectory()) {
					// ディレクトリ
					lflag = addTexturesDir(lf, lss);
				} else {
					// zip
					lflag = addTexturesZip(lf, lss);
				}
				mod_MMM_MMMLib.Debug(String.format("getTexture-append-%s-%s.", lf.getName(), lflag ? "done" : "fail"));
			}
		}
/*		
		// ロードしたテクスチャパックからクラスを強制ロード
		for (Entry<String, Map<Integer, String>> tt: textures.entrySet()) {
			String st = tt.getKey();
			int index = st.lastIndexOf("_");
			if (index > -1) {
				st = st.substring(index + 1);
				if (!st.isEmpty()) {
					addModelClass("ModelLittleMaid_".concat(st).concat(".class"));
				}
			}
		}
		mod_MMM_MMMLib.Debug("getTexture-append-Models-append-done.");
*/
		// テクスチャパッケージにモデルクラスを紐付け
		for (MMM_TextureBox ltb : textures) {
			int li = ltb.packegeName.lastIndexOf("_");
			if (li > -1) {
				String ls = ltb.packegeName.substring(li + 1);
				ltb.setModels(ls, modelMap.get(ls));
				if (ltb.models == null) {
					ltb.setModels("default", defaultModel);
				}
			} else {
				ltb.setModels("default", defaultModel);
			}
		}
		
		return false;
	}

	public static boolean loadTextureIndex() {
		// サーバー用テクスチャ名称のインデクッスローダー
		File lfile = MinecraftServer.getServer().getFile("config/textureList.cfg");
		if (lfile.exists() && lfile.isFile()) {
			try {
				FileReader fr = new FileReader(lfile);
				BufferedReader br = new BufferedReader(fr);
				String ls;
				int li = 0;
				textureIndex.clear();
				textureColor.clear();
				
				while ((ls = br.readLine()) != null) {
					String lt[] = ls.split(",");
					if (lt.length > 1) {
						textureIndex.put(li, lt[1]);
						textureColor.put(li, Integer.valueOf(lt[0], 16));
						li++;
					}
				}
				
				br.close();
				fr.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
			return true;
		} else {
			textureIndex.put(0, "default");
			textureColor.put(0, 0x1000);
		}
		
		return false;
	}

	/**
	 * テクスチャインデックスを構築。
	 */
	public static void initTextureList(boolean pFlag) {
		textureIndex.clear();
		textureColor.clear();
		if (pFlag) {
			// Internal
			int li = 0;
			for (MMM_TextureBox lte : textures) {
				textureIndex.put(li, lte.packegeName);
				textureColor.put(li, lte.getWildColorBits());
				li++;
			}
		}
	}

	/**
	 * 渡された名称を解析してLMM用のモデルクラスかどうかを判定する。
	 * 「ModelLittleMaid_」という文字列が含まれていて、
	 * 「MMM_ModelBiped」を継承していればマルチモデルとしてクラスを登録する。
	 * @param fname
	 */
	private static void addModelClass(String fname, String[] pSearch) {
		// モデルを追加
		int lfindprefix = fname.indexOf(pSearch[2]);
		if (lfindprefix > -1 && fname.endsWith(".class")) {
			String cn = fname.replace(".class", "");
			String pn = cn.substring(pSearch[2].length() + lfindprefix);
			
			if (modelMap.containsKey(pn)) return;
			
			ClassLoader lclassloader = mod_MMM_MMMLib.class.getClassLoader();
			Package lpackage = mod_MMM_MMMLib.class.getPackage();
			Class lclass;
			try {
				if (lpackage != null) {
					cn = (new StringBuilder(String.valueOf(lpackage.getName()))).append(".").append(cn).toString();
					lclass = lclassloader.loadClass(cn);
				} else {
					lclass = Class.forName(cn);
				}
				if (!(MMM_ModelBiped.class).isAssignableFrom(lclass) || Modifier.isAbstract(lclass.getModifiers())) {
					mod_MMM_MMMLib.Debug(String.format("getModelClass-fail."));
					return;
				}
				MMM_ModelBiped mlm[] = new MMM_ModelBiped[3];
				Constructor<MMM_ModelBiped> cm = lclass.getConstructor(float.class);
				mlm[0] = cm.newInstance(0.0F);
				mlm[1] = cm.newInstance(0.5F);
				mlm[2] = cm.newInstance(0.1F);
				modelMap.put(pn, mlm);
//				mod_littleMaidMob.Debug(String.format("getModelClass-%s", mlm[0].getClass().getName()));
				mod_MMM_MMMLib.Debug(String.format("getModelClass-%s:%s", pn, cn));
				
			}
			catch (Exception exception) {
				mod_MMM_MMMLib.Debug(String.format("getModelClass-Exception: %s", fname));
			}
			catch (Error error) {
				mod_MMM_MMMLib.Debug(String.format("getModelClass-Error: ".concat(fname)));
			}
		}
	}
	
	private static void addTextureName(String fname, String[] pSearch) {
		// パッケージにテクスチャを登録
		if (!fname.startsWith("/")) {
			fname = (new StringBuilder()).append("/").append(fname).toString();
		}
		
		if (fname.startsWith(pSearch[1])) {
			int i = fname.lastIndexOf("/");
			if (pSearch[1].length() < i) {
				String pn = fname.substring(pSearch[1].length(), i);
				pn = pn.replace('/', '.');
				String fn = fname.substring(i);
				int j = getIndex(fn);
				if (j > -1) {
					String an = null;
					if (j == tx_oldarmor1) {
						j = tx_armor1;
						an = "default";
					}
					if (j == tx_oldarmor2) {
						j = tx_armor2;
						an = "default";
					}
					if (j == tx_oldwild) {
						j = tx_wild + 12;
					}
					MMM_TextureBox lts = getTextureBox(pn);
					if (lts == null) {
						lts = new MMM_TextureBox(pn, pSearch);
						textures.add(lts);
						mod_MMM_MMMLib.Debug(String.format("getTextureName-append-texturePack-%s", pn));
//						mod_MMM_MMMLib.Debug(String.format("getTextureName-append-armorPack-%s", pn));
					}
					if (j >= 0x40 && j <= 0x5f) {
						// ダメージドアーマー
						Map<String, Map<Integer, String>> s = lts.armors;
						if (an == null) an = fn.substring(1, fn.lastIndexOf('_'));
						Map<Integer, String> ss = s.get(an);
						if (ss == null) {
							ss = new HashMap<Integer, String>();
							s.put(an, ss);
						}
						ss.put(j, fn);
//						mod_littleMaidMob.Debug(String.format("getTextureName-append-armor-%s:%d:%s", pn, j, fn));
					} else {
						// 通常のテクスチャ
						Map<Integer, String> s = lts.textures;
						s.put(j, fn);
//						mod_littleMaidMob.Debug(String.format("getTextureName-append-%s:%d:%s", pn, j, fn));
					}
				}
			}
		}
	}

	public static boolean addTexturesZip(File file, String[] pSearch) {
		//
		if (file == null || file.isDirectory()) {
			return false;
		}
		try {
			FileInputStream fileinputstream = new FileInputStream(file);
			ZipInputStream zipinputstream = new ZipInputStream(fileinputstream);
			ZipEntry zipentry;
			do {
				zipentry = zipinputstream.getNextEntry();
				if(zipentry == null)
				{
					break;
				}
				if (!zipentry.isDirectory()) {
					if (zipentry.getName().endsWith(".class")) {
						addModelClass(zipentry.getName(), pSearch);
					} else {
						addTextureName(zipentry.getName(), pSearch);
					}
				}
			} while(true);
			
			zipinputstream.close();
			fileinputstream.close();
			
			return true;
		} catch (Exception exception) {
			mod_MMM_MMMLib.Debug("addTextureZip-Exception.");
			return false;
		}
	}

	protected static void addTexturesJar(File file, String[] pSearch) {
		// 
		if (file.isFile()) {
			mod_MMM_MMMLib.Debug("addTextureJar-zip.");
			if (addTexturesZip(file, pSearch)) {
				mod_MMM_MMMLib.Debug("getTexture-append-jar-done.");
			} else {
				mod_MMM_MMMLib.Debug("getTexture-append-jar-fail.");
			}
		}
		
		// 意味なし？
		if (file.isDirectory()) {
			mod_MMM_MMMLib.Debug("addTextureJar-file.");
			
			for (File t : file.listFiles()) {
				if (t.isDirectory() && t.getName().equalsIgnoreCase("mob")) {
					if (addTexturesDir(file, pSearch)) {
						mod_MMM_MMMLib.Debug("getTexture-append-jar-done.");
					} else {
						mod_MMM_MMMLib.Debug("getTexture-append-jar-fail.");
					}
				}
			}
			
			Package package1 = (net.minecraft.src.ModLoader.class).getPackage();
			if(package1 != null)
			{
				String s = package1.getName().replace('.', File.separatorChar);
				file = new File(file, s);
				mod_MMM_MMMLib.Debug(String.format("addTextureJar-file-Packege:%s", s));
			} else {
				mod_MMM_MMMLib.Debug("addTextureJar-file-null.");
			}
			if (addTexturesDir(file, pSearch)) {
				mod_MMM_MMMLib.Debug("getTexture-append-jar-done.");
			} else {
				mod_MMM_MMMLib.Debug("getTexture-append-jar-fail.");
			}
			
		}
	}

	public static boolean addTexturesDir(File file, String[] pSearch) {
		// modsフォルダに突っ込んであるものも検索、再帰で。
		if (file == null) {
			return false;
		}
		
		try {
			for (File t : file.listFiles()) {
				if(t.isDirectory()) {
					addTexturesDir(t, pSearch);
				} else {
					if (t.getName().endsWith(".class")) {
						addModelClass(t.getName(), pSearch);
					} else {
						String s = t.getPath().replace('\\', '/');
						int i = s.indexOf(pSearch[1]);
						if (i > -1) {
							// 対象はテクスチャディレクトリ
							addTextureName(s.substring(i), pSearch);
//							addTextureName(s.substring(i).replace('\\', '/'));
						}
					}
				}
			}
			return true;
			
		} catch (Exception e) {
			mod_MMM_MMMLib.Debug("addTextureDebug-Exception.");
			return false;
		}
	}

	private static int getIndex(String name) {
		// 名前からインデックスを取り出す
		for (int i = 0; i < defNames.length; i++) {
			if (name.endsWith(defNames[i])) {
				return i;
			}
		}
		
		Pattern p = Pattern.compile("_([0-9a-f]+).png");
		Matcher m = p.matcher(name);
		if (m.find()) {
			return Integer.decode("0x" + m.group(1));
		}
		
		return -1;
	}

	public static String getNextPackege(String nowname, int index) {
		// 次のテクスチャパッケージの名前を返す
		boolean f = false;
		MMM_TextureBox lreturn = null;
		for (MMM_TextureBox ltb : textures) {
			if (ltb.hasColor(index)) {
				if (f) {
					return ltb.packegeName;
				}
				if (lreturn == null) {
					lreturn = ltb;
				}
			}
			if (ltb.packegeName.equalsIgnoreCase(nowname)) {
				f = true;
			}
		}
		return lreturn == null ? null : lreturn.packegeName;
	}

	public static String getPrevPackege(String nowname, int index) {
		// 前のテクスチャパッケージの名前を返す
		MMM_TextureBox lreturn = null;
		for (MMM_TextureBox ltb : textures) {
			if (ltb.packegeName.equalsIgnoreCase(nowname)) {
				if (lreturn != null) {
					break;
				}
			}
			if (ltb.hasColor(index)) {
				lreturn = ltb;
			}
		}
		return lreturn == null ? null  :lreturn.packegeName;
	}

	public static String getTextureName(String name, int index) {
		MMM_TextureBox ltb = getTextureBox(name);
		if (ltb == null || !ltb.hasColor(index)) {
			return null;
		} else {
			return ltb.getTextureName(index);
		}
	}
	
	public static int getTextureCount() {
		return textures.size();
	}
	
	public static String getNextArmorPackege(String nowname) {
		// 次のテクスチャパッケージの名前を返す
		boolean f = false;
		MMM_TextureBox lreturn = null;
		for (MMM_TextureBox ltb : textures) {
			if (ltb.hasArmor()) {
				if (f) {
					return ltb.packegeName;
				}
				if (lreturn == null) {
					lreturn = ltb;
				}
			}
			if (ltb.packegeName.equalsIgnoreCase(nowname)) {
				f = true;
			}
		}
		return lreturn.packegeName;
	}

	public static String getPrevArmorPackege(String nowname) {
		// 前のテクスチャパッケージの名前を返す
		MMM_TextureBox lreturn = null;
		for (MMM_TextureBox ltb : textures) {
			if (ltb.packegeName.equalsIgnoreCase(nowname)) {
				if (lreturn != null) {
					break;
				}
			}
			if (ltb.hasArmor()) {
				lreturn = ltb;
			}
		}
		return lreturn.packegeName;
	}

	/**
	 * アーマーのテクスチャファイル名を返す
	 */
	public static String getArmorTextureName(String name, int index, ItemStack itemstack) {
		// indexは0x40,0x50番台
		MMM_TextureBox ltb = getTextureBox(name);
		if (ltb == null) {
			return null;
		}
		return ltb.getArmorTextureName(index, itemstack);
	}

	public static String getRandomTexture(Random pRand) {
		if (textureIndex.isEmpty()) {
			return "default";
		} else {
			return textureIndex.values().toArray()[pRand.nextInt(textureIndex.size())].toString();
		}
//		return textures.keySet().toArray()[pRand.nextInt(getTextureCount())].toString();
	}

	/**
	 * 野生のメイドの色をランダムで返す
	 */
	public static int getRandomWildColor(int pIndex, Random rand) {
		if (textureColor.isEmpty() || pIndex < 0) return -1;
		
		List<Integer> llist = new ArrayList<Integer>();
		int lcolor = textureColor.get(pIndex);
		for (int li = 0; li < 16; li++) {
			if ((lcolor & 0x01) > 0) {
				llist.add(li);
				lcolor = lcolor >>> 1;
			}
		}
		
		if (llist.size() > 0) {
			return llist.get(rand.nextInt(llist.size()));
		} else {
			return -1;
		}
	}

	/*
	 * サーバークライアント間でのテクスチャ管理関数群
	 */
	public static int getStringToIndex(String pname) {
		for (Entry<Integer, String> le : textureIndex.entrySet()) {
			if (le.getValue().equals(pname)) {
				return le.getKey();
			}
		}
		return -1;
	}
	public static int setStringToIndex(int pIndex, String pname) {
		if (getTextureBox(pname) != null) {
			textureIndex.put(pIndex, pname);
		} else {
			// 自分のところにはないテクスチャパック
			textureIndex.put(pIndex, "default");
		}
		return getStringToIndex(pname);
	}
	public static int setStringToIndex(int pIndex) {
		// サーチかける時用のブランクを設置
		textureIndex.put(pIndex, "");
		return pIndex;
	}
	public static int setStringToIndex(String pname, int pColorBits) {
		if (!textureIndex.containsValue(pname)) {
			// 既にある分は登録しない
			int li = textureIndex.size();
			textureIndex.put(li, pname);
			textureColor.put(li, pColorBits);
		}
		return getStringToIndex(pname);
	}

	public static String getIndexToString(int pindex) {
		return textureIndex.get(pindex);
	}

	// ネットワーク越しにテクスチャインデクスを得る際に使う
	public static int getRequestIndex(String pVal) {
		int lblank = -1;
		for (int li = 0; li < requestString.length; li++) {
			if (requestString[li] == null) {
				lblank = li;
			} else if (requestString[li].equals(pVal)) {
				// 既に要求中
				return -2;
			}
		}
		if (lblank >= 0) {
			requestString[lblank] = pVal;
		}
		return lblank;
	}

	public static String getRequestString(int pIndex) {
		String ls = requestString[pIndex];
		requestString[pIndex] = null;
		return ls;
	}

}
