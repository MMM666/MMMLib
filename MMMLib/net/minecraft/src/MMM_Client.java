package net.minecraft.src;

import static net.minecraft.src.mod_MMM_MMMLib.Debug;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Random;

import org.lwjgl.opengl.GL11;

public class MMM_Client {

	public static MMM_ItemRenderer itemRenderer;

	/**
	 * 初期化時実行コード
	 */
	public static void init() {
		try {
			// TODO: バージョンアップ時には確認すること
			List lresourcePacks = (List)ModLoader.getPrivateValue(Minecraft.class, Minecraft.getMinecraft(), 63);
			lresourcePacks.add(new MMM_ModOldResourcePack(mod_MMM_MMMLib.class));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

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
		case MMM_Statics.Client_SetTextureIndex:
			// 問い合わせたテクスチャパックの管理番号を受け取る
			MMM_TextureManager.instance.reciveFormServerSetTexturePackIndex(var2.data);
			break;
		case MMM_Statics.Client_SetTexturePackName:
			// 管理番号に登録されているテクスチャパックの情報を受け取る
			MMM_TextureManager.instance.reciveFromServerSetTexturePackName(var2.data);
			break;
		}
	}

	public static void clientConnect(NetClientHandler var1) {
		if (MMM_Helper.mc.isIntegratedServerRunning()) {
			Debug("Localmode: InitTextureList.");
			MMM_TextureManager.instance.initTextureList(true);
		} else {
			Debug("Remortmode: ClearTextureList.");
			MMM_TextureManager.instance.initTextureList(false);
		}
	}

	public static void clientDisconnect(NetClientHandler var1) {
//		super.clientDisconnect(var1);
//		Debug("Localmode: InitTextureList.");
//		MMM_TextureManager.initTextureList(true);
	}

	public static void sendToServer(byte[] pData) {
		ModLoader.clientSendPacket(new Packet250CustomPayload("MMM|Upd", pData));
		Debug("MMM|Upd:%2x:NOEntity", pData[0]);
	}

	public static boolean isIntegratedServerRunning() {
		return MMM_Helper.mc.isIntegratedServerRunning();
	}

	/**
	 * Duoを使う時は必ずRender側のこの関数を置き換えること。
	 * @param par1EntityLiving
	 * @param par2
	 */
	public static void renderArrowsStuckInEntity(EntityLivingBase par1EntityLiving, float par2,
			Render pRender, MMM_ModelBase pModel) {
		int lacount = par1EntityLiving.getArrowCountInEntity();
		
		if (lacount > 0) {
			EntityArrow larrow = new EntityArrow(par1EntityLiving.worldObj, par1EntityLiving.posX, par1EntityLiving.posY, par1EntityLiving.posZ);
			Random lrand = new Random((long)par1EntityLiving.entityId);
			RenderHelper.disableStandardItemLighting();
			
			for (int var6 = 0; var6 < lacount; ++var6) {
				GL11.glPushMatrix();
				MMM_ModelRenderer var7 = pModel.getRandomModelBox(lrand);
				MMM_ModelBoxBase var8 = var7.cubeList.get(lrand.nextInt(var7.cubeList.size()));
				var7.postRender(0.0625F);
				float var9 = lrand.nextFloat();
				float var10 = lrand.nextFloat();
				float var11 = lrand.nextFloat();
				float var12 = (var8.posX1 + (var8.posX2 - var8.posX1) * var9) / 16.0F;
				float var13 = (var8.posY1 + (var8.posY2 - var8.posY1) * var10) / 16.0F;
				float var14 = (var8.posZ1 + (var8.posZ2 - var8.posZ1) * var11) / 16.0F;
				GL11.glTranslatef(var12, var13, var14);
				var9 = var9 * 2.0F - 1.0F;
				var10 = var10 * 2.0F - 1.0F;
				var11 = var11 * 2.0F - 1.0F;
				var9 *= -1.0F;
				var10 *= -1.0F;
				var11 *= -1.0F;
				float var15 = MathHelper.sqrt_float(var9 * var9 + var11 * var11);
				larrow.prevRotationYaw = larrow.rotationYaw = (float)(Math.atan2((double)var9, (double)var11) * 180.0D / Math.PI);
				larrow.prevRotationPitch = larrow.rotationPitch = (float)(Math.atan2((double)var10, (double)var15) * 180.0D / Math.PI);
				double var16 = 0.0D;
				double var18 = 0.0D;
				double var20 = 0.0D;
				float var22 = 0.0F;
				pRender.renderManager.renderEntityWithPosYaw(larrow, var16, var18, var20, var22, par2);
				GL11.glPopMatrix();
			}
			
			RenderHelper.enableStandardItemLighting();
		}
	}

	public static World getMCtheWorld() {
		if (MMM_Helper.mc !=  null) {
			return MMM_Helper.mc.theWorld;
		}
		return null;
	}

	public static void setLightmapTextureCoords(int pValue) {
//		int ls = pValue % 65536;
//		int lt = pValue / 65536;
		int ls = pValue & 0xffff;
		int lt = pValue >>> 16;
		OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit,
				(float) ls / 1.0F, (float) lt / 1.0F);
	}

	public static void setTexture(ResourceLocation pRLocation) {
		if (pRLocation != null) {
			MMM_Helper.mc.func_110434_K().func_110577_a(pRLocation);
		}
	}

	public static String getVersionString() {
		return Minecraft.func_110431_a(Minecraft.getMinecraft());
	}
}
