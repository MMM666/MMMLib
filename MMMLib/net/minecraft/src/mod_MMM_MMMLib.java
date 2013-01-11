package net.minecraft.src;

import java.util.Iterator;
import java.util.Map;

import net.minecraft.client.Minecraft;
import net.minecraft.server.MinecraftServer;

public class mod_MMM_MMMLib extends BaseMod {

	@MLProp()
	public static boolean isDebugView = false;
	@MLProp()
	public static boolean isDebugMessage = true;
	@MLProp(info = "Override RenderItem.")
	public static boolean renderHacking = true;
	
	
	public static void Debug(String pText) {
		// デバッグメッセージ
		if (isDebugMessage) {
			System.out.println(String.format("MMMLib-%s", pText));
		}
	}

	@Override
	public String getName() {
		return "MMMLib";
	}

	@Override
	public String getVersion() {
		return "1.4.6-5";
	}
	
	@Override
	public String getPriorities() {
		return MMM_Helper.isForge ? "befor-all" : "before:*";
	}

	@Override
	public void load() {
		// 初期化
		Debug(MMM_Helper.isClient ? "Client" : "Server");
		Debug(MMM_Helper.isForge ? "Forge" : "Modloader");
		MMM_FileManager.init();
		MMM_TextureManager.init();
		MMM_StabilizerManager.init();
		ModLoader.setInGameHook(this, true, true);
		if (isDebugView) {
			MMM_EntityDummy.isEnable = true;
		}
	}

	@Override
	public void modsLoaded() {
		// ロード
		if (MMM_Helper.isClient) {
			MMM_TextureManager.loadTextures();
			MMM_StabilizerManager.loadStabilizer();
		} else {
			MMM_TextureManager.loadTextureIndex();
		}
	}

	@Override
	public void addRenderer(Map var1) {
		if (isDebugView) {
			var1.put(net.minecraft.src.MMM_EntityDummy.class, new MMM_RenderDummy());
		}
		//RenderItem
		var1.put(EntityItem.class, new MMM_RenderItem());
	}

	@Override
	public boolean onTickInGame(float var1, Minecraft var2) {
		if (isDebugView && MMM_Helper.isClient) {
			// ダミーマーカーの表示用処理
			if (var2.theWorld != null && var2.thePlayer != null) {
				try {
					for (Iterator<MMM_EntityDummy> li = MMM_EntityDummy.appendList.iterator(); li.hasNext();) {
						var2.theWorld.spawnEntityInWorld(li.next());
						li.remove();
					}
				} catch (Exception e) {
//					e.printStackTrace();
				}
			}
		}
		
		// アイテムレンダーをオーバーライド
		if (renderHacking && MMM_Helper.isClient) {
			MMM_Client.setItemRenderer();
		}
		
		return true;
	}


}
