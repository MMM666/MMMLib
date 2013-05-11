package net.minecraft.src;

import static net.minecraft.src.mod_MMM_MMMLib.*;

import java.lang.reflect.Constructor;

import net.minecraft.client.Minecraft;

public class MMM_Client {

	public static MMM_ItemRenderer itemRenderer;


	public static void setItemRenderer() {
		if (itemRenderer == null) {
			itemRenderer = new MMM_ItemRenderer(MMM_Helper.mc);
		}
		if (!(MMM_Helper.mc.entityRenderer.itemRenderer instanceof MMM_ItemRenderer)) {
			mod_MMM_MMMLib.Debug("replace entityRenderer.itemRenderer.");
			MMM_Helper.mc.entityRenderer.itemRenderer = itemRenderer;
		}
		if (!(RenderManager.instance.itemRenderer instanceof MMM_ItemRenderer)) {
			mod_MMM_MMMLib.Debug("replace RenderManager.itemRenderer.");
			RenderManager.instance.itemRenderer = itemRenderer;
		}
		// GUIの表示を変えるには常時監視が必要？
	}

	public static void clientCustomPayload(NetClientHandler var1, Packet250CustomPayload var2) {
		// クライアント側の特殊パケット受信動作
		byte lmode = var2.data[0];
		int leid = 0;
		Entity lentity = null;
		if ((lmode & 0x80) != 0) {
			leid = MMM_Helper.getInt(var2.data, 1);
			lentity = MMM_Helper.getEntity(var2.data, 1, MMM_Helper.mc.theWorld);
			if (lentity == null) return;
		}
		Debug("MMM|Upd Clt Call[%2x:%d].", lmode, leid);
		
		switch (lmode) {
		case MMM_Client_SetTextureIndex:
			// テクスチャ名称に対応するサーバー側と同じインデックスを設定する
			/*
			 * 0:id
			 * 1:index
			 * 2-5:TextureIndex
			 */
			int li7 = MMM_Helper.getShort(var2.data, 2);
			String ls7 = MMM_TextureManager.getRequestString(var2.data[1]);
			Debug("%d = %d : %s", li7, var2.data[1], ls7 == null ? "NULL" : ls7);
			MMM_TextureManager.setStringToIndex(li7, ls7);
			break;
		case MMM_Client_SetTextureStr:
			// インデックスに対応するテクスチャ名称を登録する、主にサーバー側のみに名称登録がある場合に使用。
			/*
			 * 0:id
			 * 1-2:index 登録テクスチャ番号
			 * 3-:Str 名称
			 */
			int li8 = MMM_Helper.getShort(var2.data, 1);
			String ls8 = MMM_Helper.getStr(var2.data, 3);
			Debug("%d = %s", li8, ls8 == null ? "NULL" : ls8);
			MMM_TextureManager.setStringToIndex(li8, ls8);
			break;
			
		}
	}

	public static void clientConnect(NetClientHandler var1) {
		if (MMM_Helper.mc.isIntegratedServerRunning()) {
//			Debug("Localmode: InitTextureList.");
//			MMM_TextureManager.initTextureList(true);
		} else {
			Debug("Remortmode: ClearTextureList.");
			MMM_TextureManager.initTextureList(false);
		}
	}

	public static void clientDisconnect(NetClientHandler var1) {
//		super.clientDisconnect(var1);
		Debug("Localmode: InitTextureList.");
		MMM_TextureManager.initTextureList(true);
	}

	public static void sendToServer(byte[] pData) {
		ModLoader.clientSendPacket(new Packet250CustomPayload("MMM|Upd", pData));
		Debug("MMM|Upd:%2x:NOEntity", pData[0]);
	}

	public static boolean isIntegratedServerRunning() {
		return MMM_Helper.mc.isIntegratedServerRunning();
	}

	public static void setArmorPrefix() {
		// アーマープリフィックスを設定
		try {
			ModLoader.setPrivateValue(RenderBiped.class, null, 4, ModLoader.getPrivateValue(RenderPlayer.class, null, 3));
		} catch (Exception e) {
		}
	}

}
