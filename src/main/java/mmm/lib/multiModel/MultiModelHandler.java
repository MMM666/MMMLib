package mmm.lib.multiModel;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.IdentityHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import mmm.lib.multiModel.texture.IMultiModelEntity;
import mmm.lib.multiModel.texture.MultiModelData;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.network.NetHandlerPlayServer;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.EntityEvent.EntityConstructing;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import cpw.mods.fml.client.FMLClientHandler;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.TickEvent.WorldTickEvent;
import cpw.mods.fml.common.network.FMLEventChannel;
import cpw.mods.fml.common.network.FMLNetworkEvent;
import cpw.mods.fml.common.network.FMLNetworkEvent.ClientConnectedToServerEvent;
import cpw.mods.fml.common.network.FMLNetworkEvent.ClientDisconnectionFromServerEvent;
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
	public Map<Entity, MultiModelData> datasClient = new IdentityHashMap<Entity, MultiModelData>();
	public Map<Entity, MultiModelData> datasServer = new IdentityHashMap<Entity, MultiModelData>();

	public static boolean isDebugMessage = true;


	public static void Debug(String pText, Object... pData) {
		// デバッグメッセージ
		if (isDebugMessage) {
			System.out.println(String.format("MultiModel-" + pText, pData));
		}
	}


	/**
	 * マルチモデルを使用する場合は此処を呼ぶこと
	 */
	public static void init() {
		// イベントハンドラに登録
		if (instance instanceof MultiModelHandler) return;
		
		// ネットワークのハンドラを登録
		instance = new MultiModelHandler();
		networkEventChannel = NetworkRegistry.INSTANCE.newEventDrivenChannel(packetChannel);
		networkEventChannel.register(instance);
		MinecraftForge.EVENT_BUS.register(instance);
		FMLCommonHandler.instance().bus().register(instance);
	}

	public static void clear() {
		if (instance != null) {
			instance.datasClient.clear();
			instance.datasServer.clear();
		}
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
				if (lentity.worldObj.isRemote) {
					datasClient.put(lentity, ledata);
				} else {
					datasServer.put(lentity, ledata);
				}
				if (lentity instanceof IMultiModelEntity) {
					((IMultiModelEntity)lentity).setMultiModelData(ledata);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	@SubscribeEvent
	public void onEntityJoinInWorld(EntityJoinWorldEvent pEvent) {
		// ワールドに追加された時の処理
		Entity lentity = pEvent.entity;
		if (targets.containsKey(lentity.getClass())) {
			if (lentity.worldObj.isRemote) {
				// Client
				MultiModelData ldata = datasClient.get(lentity);
				if (ldata != null) {
					sendToServer(ldata, 0x0000);
				}
			} else {
				// Server
				
			}
		}
	}

	/**
	 * サーバーへ変更通知
	 * @param pData
	 */
	public static void sendToServer(MultiModelData pData, int pMode) {
		Debug("send MultiModelData to Server.");
		ByteBuf lbuf = Unpooled.buffer();
		pData.sendToServer(lbuf, pMode);
		FMLProxyPacket lpacket = new FMLProxyPacket(lbuf, packetChannel);
		networkEventChannel.sendToServer(lpacket);
	}

	/**
	 * クライアントへ変更通知
	 * @param pData
	 */
	public static void sendToClient(MultiModelData pData, int pMode) {
		Debug("send MultiModelData to Client.");
		ByteBuf lbuf = Unpooled.buffer();
		pData.sendToClient(lbuf, pMode);
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
				MultiModelData lmme = datasServer.get(lentity);
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
			MultiModelData lmme = datasClient.get(lentity);
			if (lmme != null) {
				lmme.reciveFromServer(pEvent.packet.payload());
			}
		}
	}

	@SubscribeEvent
	public void onDisconnectClient(ClientDisconnectionFromServerEvent pEvent) {
		Debug("Disconnect From Server. last datasCount CL:%d, SV:%d", datasClient.size(), datasServer.size());
		clear();
	}

	@SubscribeEvent
	public void onConnectClient(ClientConnectedToServerEvent pEvent) {
		Debug("Connect To Server. datasCount CL:%d, SV:%d", datasClient.size(), datasServer.size());
	}

	@SubscribeEvent
	public void onUpdate(WorldTickEvent pEvent) {
		// 毎時処理
		List<Entity> llist = new ArrayList<Entity>();
		for (Entry<Entity, MultiModelData> le : datasServer.entrySet()) {
			le.getValue().onUpdate();
			if (le.getValue().getOwner().isDead) {
				llist.add(le.getKey());
			}
		}
		for (Entity le : llist) {
			datasServer.remove(le);
		}
		llist.clear();
		for (Entry<Entity, MultiModelData> le : datasClient.entrySet()) {
			le.getValue().onUpdate();
			if (le.getValue().getOwner().isDead) {
				llist.add(le.getKey());
			}
		}
		for (Entity le : llist) {
			datasClient.remove(le);
		}
	}

}
