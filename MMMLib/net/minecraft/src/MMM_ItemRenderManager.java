package net.minecraft.src;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.minecraft.client.Minecraft;

import org.lwjgl.opengl.GL11;

public class MMM_ItemRenderManager {

	protected static Map<Object, MMM_ItemRenderManager> classList = new HashMap<Object, MMM_ItemRenderManager>();
	protected static List<Object> checkList = new ArrayList<Object>();
	
	private Object fobject;
	private Method frenderItem;
	private Method frenderItemInFirstPerson;
	private Method fgetRenderTexture;
	private Method frenderItemWorld;
	private MMM_IItemRenderManager exRender;


	private MMM_ItemRenderManager(Object pObject, Method prenderItem, Method prenderItemInFirstPerson,
			Method pgetRenderTexture, Method prenderItemWorld) {
		fobject = pObject;
		frenderItem = prenderItem;
		frenderItemInFirstPerson = prenderItemInFirstPerson;
		fgetRenderTexture = pgetRenderTexture;
		frenderItemWorld = prenderItemWorld;
		exRender = null;
	}
	
	private MMM_ItemRenderManager(Object pObject, MMM_IItemRenderManager pEXRender) {
		fobject = pObject;
		exRender = pEXRender;
	}

	public static boolean setEXRender(Item pItem, MMM_IItemRenderManager pEXRender) {
		// アイテムの特殊描画機能を強制的に追加する
		if (pItem == null || pEXRender == null) return false;
		
		checkList.add(pItem);
		classList.put(pItem, new MMM_ItemRenderManager(pItem, pEXRender));
		return true;
	}

	public static boolean isEXRender(Item pItem) {
		if (checkList.contains(pItem)) {
			return classList.containsKey(pItem);
		} else {
			checkList.add(pItem);
			Method lrenderItem = null;
			Method lrenderItemInFirstPerson = null;
			Method lgetRenderTexture = null;
			Method lrenderItemWorld = null;
			Class lc = pItem.getClass();
			
			try {
				lrenderItem = lc.getMethod("renderItem", EntityLiving.class, ItemStack.class, int.class);
			} catch (Exception e) {
			}
			try {
				lrenderItemInFirstPerson = lc.getMethod("renderItemInFirstPerson", float.class);
			} catch (Exception e) {
			}
			try {
				lgetRenderTexture = lc.getMethod("getRenderTexture");
			} catch (Exception e) {
			}
			try {
				lrenderItemWorld = lc.getMethod("isRenderItemWorld");
			} catch (Exception e) {
			}
			if (lrenderItem != null || lrenderItemInFirstPerson != null || lgetRenderTexture != null) {
				classList.put(pItem, new MMM_ItemRenderManager(pItem,
						lrenderItem, lrenderItemInFirstPerson,
						lgetRenderTexture, lrenderItemWorld));
				return true;
			}
		}
		return false;
	}

	public static MMM_ItemRenderManager getEXRender(Item pItem) {
		return classList.get(pItem);
	}



	public boolean renderItem(EntityLiving pEntity, ItemStack pItemstack, int pIndex) {
		if (exRender != null) {
			return exRender.renderItem(pEntity, pItemstack, pIndex);
		} else if (frenderItem != null) {
			try {
				return (Boolean)frenderItem.invoke(fobject, pEntity, pItemstack, pIndex);
			} catch (Exception e) {
			}
		}
		return false;
	}

	public boolean renderItemInFirstPerson(float pDelta, MMM_ItemRenderer pItemRenderer) {
		if (exRender != null) {
			return exRender.renderItemInFirstPerson(pDelta, pItemRenderer);
		} else if (frenderItemInFirstPerson != null) {
			try {
				return (Boolean)frenderItemInFirstPerson.invoke(fobject, pDelta);
			} catch (Exception e) {
			}
		}
		return false;
	}

	public String getRenderTexture() {
		if (exRender != null) {
			return exRender.getRenderTexture();
		} else if (fgetRenderTexture != null) {
			try {
				return (String)fgetRenderTexture.invoke(fobject);
			} catch (Exception e) {
			}
		}
		return null;
	}

	public boolean isRenderItemWorld() {
		if (exRender != null) {
			return exRender.isRenderItemWorld();
		} else if (frenderItemWorld != null) {
			try {
				return (Boolean)frenderItemWorld.invoke(fobject);
			} catch (Exception e) {
			}
		}
		return false;
	}

}
