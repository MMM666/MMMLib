package mmm.lib.multiModel.texture;

import java.io.FileInputStream;
import java.util.HashMap;
import java.util.Map;

import mmm.lib.multiModel.model.AbstractModelBase;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

/**
 * 個別のマルチテクスチャ・モデルを管理する。
 *
 */
public class MultiModelContainer {

	public String name;
	/** 標準のパーツ表示状況を設定する */
	public int[] defaultVisivles = new int[16];
	
	/** バインドされているモデルクラス */
	protected AbstractModelBase[] defaultModel;
	protected Map<Integer, AbstractModelBase[]> models;
	/** バインドされているテクスチャ */
	protected Map<Integer, ResourceLocation> textures;
	/** バインドされているアーマーテクスチャ */
	protected Map<String, Map<Integer, ResourceLocation>> armors;
	protected boolean isDecodeJSON;
	protected int colorContracts;
	protected int colorWilds;


	public MultiModelContainer(String pName) {
		name = pName;
		models = new HashMap<Integer, AbstractModelBase[]>();
		textures = new HashMap<Integer, ResourceLocation>();
		armors = new HashMap<String, Map<Integer,ResourceLocation>>();
		isDecodeJSON = false;
		colorContracts = colorWilds = 0;
	}

	@Override
	public String toString() {
		StringBuilder lsb = new StringBuilder();
		lsb.append(name);
		lsb.append(", contract:0x").append(String.format("%02x", colorContracts));
		lsb.append(", wild:0x").append(String.format("%02x", colorWilds));
		if (hasArmor()) {
			lsb.append(", armors[");
			boolean lf = false;
			for (String ls : armors.keySet()) {
				if (lf) {
					lsb.append(", ");
				} else {
					lf = true;
				}
				lsb.append(ls);
			}
			lsb.append("]");
		} else {
			lsb.append(", no armor");
		}
		return lsb.toString();
	}

	/**
	 * 渡されたストリームをJSONとして読み込む
	 * @param pStream
	 * @return
	 */
	public boolean loadJSON(FileInputStream pStream) {
		isDecodeJSON = true;
		return false;
	}

	public void addTexture(int pIndex, ResourceLocation pResource) {
		String ls = pResource.getResourcePath();
		switch ((pIndex & 0xfff0)) {
		case MultiModelManager.tx_oldwild:
			pIndex = MultiModelManager.tx_wild | 0x0c;
			break;
		case MultiModelManager.tx_oldarmor1:
			pIndex = MultiModelManager.tx_wild | 0x0c;
			break;
		case MultiModelManager.tx_oldarmor2:
			pIndex = MultiModelManager.tx_wild | 0x0c;
			break;
		}
		switch ((pIndex & 0xfff0)) {
		case MultiModelManager.tx_armor1:
		case MultiModelManager.tx_armor2:
		case MultiModelManager.tx_armor1light:
		case MultiModelManager.tx_armor2light:
			// アーマーの追加
			ls = ls.substring(ls.lastIndexOf("/") + 1, ls.lastIndexOf("_"));
			addArmorTexture(ls, pIndex, pResource);
			break;
		case MultiModelManager.tx_oldarmor1:
		case MultiModelManager.tx_oldarmor2:
			// アーマーの追加
			addArmorTexture("default", pIndex, pResource);
			break;
			
		default:
			// 通常の追加
			if (pIndex >= MultiModelManager.tx_texture && pIndex < (MultiModelManager.tx_texture + 16)) {
				colorContracts |= (1 << (pIndex & 0x0f));
			} else if (pIndex >= MultiModelManager.tx_wild && pIndex < (MultiModelManager.tx_wild + 16)) {
				colorWilds |= (1 << (pIndex & 0x0f));
			}
			textures.put(pIndex, pResource);
		}
	}

	public void addArmorTexture(String pName, int pIndex, ResourceLocation pResource) {
		// アーマーの追加
		Map<Integer, ResourceLocation> lmap;
		if (armors.containsKey(pName)) {
			lmap = armors.get(pName);
		} else {
			lmap = new HashMap<Integer, ResourceLocation>();
			armors.put(pName, lmap);
		}
		lmap.put(pIndex, pResource);
	}

	/**
	 * インデックスに応じたテクスチャを返す
	 * @param pIndex
	 * @return
	 */
	public ResourceLocation getTexture(int pIndex) {
		if (textures.containsKey(pIndex)) {
			return textures.get(pIndex);
		} else if (pIndex >= MultiModelManager.tx_eyecontract && pIndex < (16 + MultiModelManager.tx_eyecontract)) {
			return getTexture(MultiModelManager.tx_oldeye);
		} else if (pIndex >= MultiModelManager.tx_eyewild && pIndex < (16 + MultiModelManager.tx_eyewild)) {
			return getTexture(MultiModelManager.tx_oldeye);
		}
		return null;
	}

	public ResourceLocation getArmorTexture(int pIndex, ItemStack itemstack) {
		// indexは0x40,0x50番台
		// lightも追加
		if (armors.isEmpty() || itemstack == null) return null;
		if (!(itemstack.getItem() instanceof ItemArmor)) return null;
		
		int l = 0;
		if (itemstack.getMaxDamage() > 0) {
			l = (10 * itemstack.getItemDamage() / itemstack.getMaxDamage());
		}
//		String ls = itemstack.getItem().getArmorTexture(stack, entity, slot, type)
//		return getArmorTextureName(pIndex, MMM_TextureManager.armorFilenamePrefix[((ItemArmor)itemstack.getItem()).renderIndex], l);
		return null;
	}
	public ResourceLocation getArmorTexture(int pIndex, String pArmorPrefix, int pDamage) {
		// indexは0x40,0x50番台
		if (armors.isEmpty() || pArmorPrefix == null) return null;
		
		Map<Integer, ResourceLocation> m = armors.get(pArmorPrefix);
		if (m == null) {
			m = armors.get("default");
			if (m == null) {
//				return null;
				m = (Map)armors.values().toArray()[0];
			}
		}
		ResourceLocation ls = null;
//		int lindex = pInner ? MMM_TextureManager.tx_armor1 : MMM_TextureManager.tx_armor2;
		for (int i = pIndex + pDamage; i >= pIndex; i--) {
			ls = m.get(i);
			if (ls != null) break;
		}
		return ls;
	}

	public AbstractModelBase[] getModelClass(int pIndex) {
		if (models.containsKey(pIndex)) {
			return models.get(pIndex);
		}
		return defaultModel;
	}
	public AbstractModelBase[] getModelClass() {
		return defaultModel;
	}

	public MultiModelEntry getMultiModel() {
		return new MultiModelEntry();
	}

	public int getTextureCount() {
		return textures.size();
	}

	/**
	 * 契約色の有無をビット配列にして返す
	 */
	public int getContractColorBits() {
		if (colorContracts == -1) {
			int li = 0;
			for (Integer i : textures.keySet()) {
				if (i >= 0x00 && i <= 0x0f) {
					li |= 1 << (i & 0x0f);
				}
			}
			colorContracts = li;
		}
		return colorContracts;
	}
	/**
	 * 野生色の有無をビット配列にして返す
	 */
	public int getWildColorBits() {
		if (colorWilds == -1) {
			int li = 0;
			for (Integer i : textures.keySet()) {
				if (i >= MultiModelManager.tx_wild && i <= (MultiModelManager.tx_wild + 0x0f)) {
					li |= 1 << (i & 0x0f);
				}
			}
			colorWilds = li;
		}
		return colorWilds;
	}

	public boolean hasColor(int pIndex) {
		return textures.containsKey(pIndex);
	}

	public boolean hasColor(int pIndex, boolean pContract) {
		return textures.containsKey(pIndex + (pContract ? 0 : MultiModelManager.tx_wild));
	}

	public boolean hasArmor() {
		return !armors.isEmpty();
	}


}
