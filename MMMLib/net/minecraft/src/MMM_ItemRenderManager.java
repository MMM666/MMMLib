package net.minecraft.src;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MMM_ItemRenderManager {

	protected static Map<Object, MMM_ItemRenderManager> classList = new HashMap<Object, MMM_ItemRenderManager>();
	protected static List<Object> checkList = new ArrayList<Object>();
	
	private Object fobject;
	private Method frenderItem;
	private Method frenderItemInFirstPerson;
	private Method fgetRenderTexture;


	public MMM_ItemRenderManager(Object pObject, Method prenderItem, Method prenderItemInFirstPerson, Method pgetRenderTexture) {
		fobject = pObject;
		frenderItem = prenderItem;
		frenderItemInFirstPerson = prenderItemInFirstPerson;
		fgetRenderTexture = pgetRenderTexture;
	}

	public static boolean isEXRender(Item pItem) {
		if (checkList.contains(pItem)) {
			return classList.containsKey(pItem);
		} else {
			checkList.add(pItem);
			Method lrenderItem = null;
			Method lrenderItemInFirstPerson = null;
			Method lgetRenderTexture = null;
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
			if (lrenderItem != null || lrenderItemInFirstPerson != null || lgetRenderTexture != null) {
				classList.put(pItem, new MMM_ItemRenderManager(pItem,
						lrenderItem, lrenderItemInFirstPerson, lgetRenderTexture));
				return true;
			}
		}
		return false;
	}

	public static MMM_ItemRenderManager getEXRender(Item pItem) {
		return classList.get(pItem);
	}

	public boolean renderItem(EntityLiving pEntity, ItemStack pItemstack, int pIndex) {
		if (frenderItem != null) {
			try {
				return (Boolean)frenderItem.invoke(fobject, pEntity, pItemstack, pIndex);
			} catch (Exception e) {
			}
		}
		return false;
	}

	public boolean renderItemInFirstPerson(float pDelta) {
		if (frenderItemInFirstPerson != null) {
			try {
				return (Boolean)frenderItemInFirstPerson.invoke(fobject, pDelta);
			} catch (Exception e) {
			}
		}
		return false;
	}

	public String getRenderTexture() {
		if (fgetRenderTexture != null) {
			try {
				return (String)fgetRenderTexture.invoke(fobject);
			} catch (Exception e) {
			}
		}
		return null;
	}

}
