package net.minecraft.src;

import java.lang.reflect.Constructor;

import net.minecraft.client.Minecraft;

public class MMM_Client {

	public static Class itemRendererClass;
	public static Constructor itemRendererConstructor;


	public static void getItemRendererClass() {
		itemRendererClass = MMM_ItemRenderer.class;
		if (MMM_Helper.mc.entityRenderer.itemRenderer.getClass().getSimpleName().equals("ItemRendererHD")) {
			try {
				String ls = "MMM_ItemRendererHD";
				if (MMM_Helper.fpackage != null) {
					ls = MMM_Helper.fpackage.getName() + "." + ls;
				}
				Class lc = Class.forName(ls);
				itemRendererClass = lc;
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		try {
			itemRendererConstructor = itemRendererClass.getConstructor(Minecraft.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}


	public static void setItemRenderer() {
		if (!(MMM_Helper.mc.entityRenderer.itemRenderer instanceof MMM_IItemRenderer)) {
			getItemRendererClass();
			try {
				Object lo = itemRendererConstructor.newInstance(MMM_Helper.mc);
				MMM_Helper.mc.entityRenderer.itemRenderer = (ItemRenderer)lo;
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		if (!(RenderManager.instance.itemRenderer instanceof MMM_IItemRenderer)) {
			getItemRendererClass();
			try {
				Object lo = itemRendererConstructor.newInstance(MMM_Helper.mc);
				RenderManager.instance.itemRenderer = (ItemRenderer)lo;
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		// GUIの表示を変えるには常時監視が必要？
	}


}
