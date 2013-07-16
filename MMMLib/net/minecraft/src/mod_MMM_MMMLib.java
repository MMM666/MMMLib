package net.minecraft.src;

import java.util.Iterator;
import java.util.Map;

public class mod_MMM_MMMLib extends BaseMod {

	public static final String Revision = "2";
	
	@MLProp()
	public static boolean isDebugView = false;
	@MLProp()
	public static boolean isDebugMessage = true;
	@MLProp(info = "Override RenderItem.")
	public static boolean renderHacking = true;
	@MLProp(info = "starting auto assigned ID.")
	public static int startVehicleEntityID = 2176;
	@MLProp(info="true: AlphaBlend(request power), false: AlphaTest(more fast)")
	public static boolean isModelAlphaBlend = true;



	public static void Debug(String pText, Object... pVals) {
		// デバッグメッセージ
		if (isDebugMessage) {
			System.out.println(String.format("MMMLib-" + pText, pVals));
		}
	}

	@Override
	public String getName() {
		return "MMMLib";
	}

	@Override
	public String getVersion() {
		return "1.6.2-" + Revision;
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
		MMM_TextureManager.instance.init();
		MMM_StabilizerManager.init();
		ModLoader.setInGameHook(this, true, true);
		if (isDebugView) {
			MMM_EntityDummy.isEnable = true;
		}
		
		// 独自パケット用チャンネル
		ModLoader.registerPacketChannel(this, "MMM|Upd");
	}

	@Override
	public void modsLoaded() {
		// バイオームに設定されたスポーン情報を置き換え。
		MMM_Helper.replaceBaiomeSpawn();
		
		// テクスチャパックの構築
		MMM_TextureManager.instance.loadTextures();
		// ロード
		if (MMM_Helper.isClient) {
			// テクスチャパックの構築
//			MMM_TextureManager.loadTextures();
			MMM_StabilizerManager.loadStabilizer();
			// テクスチャインデックスの構築
			Debug("Localmode: InitTextureList.");
			MMM_TextureManager.instance.initTextureList(true);
		} else {
			MMM_TextureManager.instance.loadTextureServer();
		}
		
	}

	@Override
	public void addRenderer(Map var1) {
		if (isDebugView) {
			var1.put(net.minecraft.src.MMM_EntityDummy.class, new MMM_RenderDummy());
		}
		// RenderItem
		if (renderHacking && MMM_Helper.isClient) {
			var1.put(EntityItem.class, new MMM_RenderItem());
		}
		// RenderSelect
		var1.put(MMM_EntitySelect.class, new MMM_RenderModelMulti(0.0F));
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
		
		// テクスチャ管理用
		MMM_TextureManager.instance.onUpdate();
		
		return true;
	}

	@Override
	public void serverCustomPayload(NetServerHandler var1, Packet250CustomPayload var2) {
		// サーバ側の動作
		byte lmode = var2.data[0];
		int leid = 0;
		Entity lentity = null;
		if ((lmode & 0x80) != 0) {
			leid = MMM_Helper.getInt(var2.data, 1);
			lentity = MMM_Helper.getEntity(var2.data, 1, var1.playerEntity.worldObj);
			if (lentity == null) return;
		}
		Debug("MMM|Upd Srv Call[%2x:%d].", lmode, leid);
		byte[] ldata;
		
		switch (lmode) {
		case MMM_Statics.Server_SetTexturePackIndex:
			// サーバー側のEntityに対してテクスチャインデックスを設定する
			MMM_TextureManager.instance.reciveFromClientSetTexturePackIndex(lentity, var2.data);
			break;
		case MMM_Statics.Server_GetTextureIndex:
			// サーバー側での管理番号の問い合わせに対して応答する
			MMM_TextureManager.instance.reciveFromClientGetTexturePackIndex(var1, var2.data);
			break;
		case MMM_Statics.Server_GetTexturePackName:
			// 管理番号に対応するテクスチャパック名を返す。
			MMM_TextureManager.instance.reciveFromClientGetTexturePackName(var1, var2.data);
			break;
		}
	}

	public static void sendToClient(NetServerHandler pHandler, byte[] pData) {
		ModLoader.serverSendPacket(pHandler, new Packet250CustomPayload("MMM|Upd", pData));
	}

	@Override
	public void clientCustomPayload(NetClientHandler var1, Packet250CustomPayload var2) {
		MMM_Client.clientCustomPayload(var1, var2);
	}

	@Override
	public void clientConnect(NetClientHandler var1) {
		MMM_Client.clientConnect(var1);
	}

	@Override
	public void clientDisconnect(NetClientHandler var1) {
		MMM_Client.clientDisconnect(var1);
	}

	// Forge
	public void serverDisconnect() {
		MMM_TextureManager.instance.saveTextureServer();
	}

}
