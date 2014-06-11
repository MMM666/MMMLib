package mmm.lib.multiModel;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;

import java.util.HashMap;
import java.util.Map;

import mmm.lib.multiModel.texture.IMultiModelEntity;
import mmm.lib.multiModel.texture.MultiModelData;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.network.NetHandlerPlayServer;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.EntityEvent.EntityConstructing;
import cpw.mods.fml.client.FMLClientHandler;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.network.FMLEventChannel;
import cpw.mods.fml.common.network.FMLNetworkEvent;
import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.common.network.internal.FMLProxyPacket;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class MultiModelHandler {

	public static MultiModelHandler instance;
	public static String packetChannel = "MMM|MMD";
	public static FMLEventChannel networkEventChannel;

	public Map<Class<? extends Entity>, Class<MultiModelData>> targets = new HashMap<Class<? extends Entity>, Class<MultiModelData>>();
	public Map<Class<? extends Entity>, String> defaultModels = new HashMap<Class<? extends Entity>, String>();
	
	/** Entityに関連付けられたデータのリスト */
	public Map<Entity, MultiModelData> datas = new HashMap<Entity, MultiModelData>();

	public static boolean isDebugMessage = true;


	public static void Debug(String pText, Object... pData) {
		// デバッグメッセージ
		if (isDebugMessage) {
			System.out.println(String.format("MultiModel-" + pText, pData));
		}
	}


	public static void init() {
		// TODO イベントハンドラに登録
		if (instance instanceof MultiModelHandler) return;
		
		// ネットワークのハンドラを登録
		instance = new MultiModelHandler();
		networkEventChannel = NetworkRegistry.INSTANCE.newEventDrivenChannel(packetChannel);
		networkEventChannel.register(instance);
		MinecraftForge.EVENT_BUS.register(instance);
	}

	/**
	 * MultiModel表示対象を追加する
	 * @param pEntityClass
	 * @param pEntityData
	 */
	public void registerEntityClass(Class<? extends Entity> pEntityClass,
			Class<MultiModelData> pEntityData, String pModelName) {
		targets.put(pEntityClass, pEntityData);
		defaultModels.put(pEntityClass, pModelName);
	}

	/**
	 * Entity生成時に対象Classならマルチモデル保存クラスを生成。
	 * @param pEvent
	 */
	@SubscribeEvent
	public void onEntityConstructing(EntityConstructing pEvent) {
		Entity lentity = pEvent.entity;
		if (targets.containsKey(lentity.getClass())) {
			try {
				MultiModelData ledata = targets.get(lentity.getClass()).newInstance();
				ledata.setModelFromName(defaultModels.get(lentity.getClass()));
				lentity.registerExtendedProperties("MultiModel", ledata);
				datas.put(lentity, ledata);
				if (lentity instanceof IMultiModelEntity) {
					((IMultiModelEntity)lentity).setMultiModelData(ledata);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * サーバーへ変更通知
	 * @param pData
	 */
	public static void sendToServer(MultiModelData pData) {
		Debug("send MultiModelData to Server.");
		ByteBuf lbuf = Unpooled.buffer();
		pData.sendToServer(lbuf);
		FMLProxyPacket lpacket = new FMLProxyPacket(lbuf, packetChannel);
		networkEventChannel.sendToServer(lpacket);
	}

	/**
	 * クライアントへ変更通知
	 * @param pData
	 */
	public static void sendToClient(MultiModelData pData) {
		Debug("send MultiModelData to Client.");
		ByteBuf lbuf = Unpooled.buffer();
		pData.sendToClient(lbuf);
		FMLProxyPacket lpacket = new FMLProxyPacket(lbuf, packetChannel);
		networkEventChannel.sendToDimension(lpacket, pData.getOwner().dimension);
	}

	@SubscribeEvent
	public void onGetPacketServer(FMLNetworkEvent.ServerCustomPacketEvent pEvent) {
		// サーバーでのパケット受信
		if (pEvent.packet.channel().contentEquals(packetChannel)) {
			Debug("get MultiModelData from Client.");
			if (pEvent.handler instanceof NetHandlerPlayServer) {
				int lindex = pEvent.packet.payload().readInt();
				EntityPlayerMP lplayer = ((NetHandlerPlayServer)pEvent.handler).playerEntity;
				Entity lentity = lplayer.worldObj.getEntityByID(lindex);
				MultiModelData lmme = datas.get(lentity);
				if (lmme != null) {
					lmme.reciveFromClient(pEvent.packet.payload());
				}
			}
		}
	}

	@SideOnly(Side.CLIENT)
	@SubscribeEvent
	public void onGetPacketClient(FMLNetworkEvent.ClientCustomPacketEvent pEvent) {
		// クライアントでのパケット受信
		if (pEvent.packet.channel().contentEquals(packetChannel)) {
			Debug("get MultiModelData from Server.");
			int lindex = pEvent.packet.payload().readInt();
			Entity lentity = FMLClientHandler.instance().getWorldClient().getEntityByID(lindex);
			MultiModelData lmme = datas.get(lentity);
			if (lmme != null) {
				lmme.reciveFromServer(pEvent.packet.payload());
			}
		}
	}

}
