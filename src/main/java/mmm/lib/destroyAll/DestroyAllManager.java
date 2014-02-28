package mmm.lib.destroyAll;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.network.NetHandlerPlayServer;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.network.FMLEventChannel;
import cpw.mods.fml.common.network.FMLNetworkEvent;
import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.common.network.internal.FMLProxyPacket;

/**
 * 一括破壊対象を判定するマネージャ。
 *
 */
public class DestroyAllManager {

	public static DestroyAllManager instance;
	public static String packetChannel = "MMM|DAM";
	public static FMLEventChannel serverEventChannel;


//	public static LinkedList<DestroyPos> destroyList = new LinkedList<DestroyPos>();
	public static int rangeWidth;
	public static int rangeHeight;
	public static int ox;
	public static int oy;
	public static int oz;
	
	public static boolean isDebugMessage = true;


	public static void Debug(String pText, Object... pData) {
		// デバッグメッセージ
		if (isDebugMessage) {
			System.out.println(String.format("DAM-" + pText, pData));
		}
	}


	public static void init() {
		if (instance instanceof DestroyAllManager) return;
		
		// ネットワークのハンドラを登録
		instance = new DestroyAllManager();
		serverEventChannel = NetworkRegistry.INSTANCE.newEventDrivenChannel(packetChannel);
		serverEventChannel.register(instance);
	}

	public static void sendDestroyAllPacket(DestroyAllData pData) {
		Debug("Start DestroyAll.");
		ByteBuf lbuf = Unpooled.buffer();
		pData.writeByteBuf(lbuf);
		FMLProxyPacket lpacket = new FMLProxyPacket(lbuf, packetChannel);
		serverEventChannel.sendToServer(lpacket);
	}

	@SubscribeEvent
	public void onGetPacketServer(FMLNetworkEvent.ServerCustomPacketEvent pEvent) {
		// パケット受信
		if (pEvent.packet.channel().contentEquals(packetChannel)) {
			Debug("get DestroyAll Packet from Client.");
			if (pEvent.handler instanceof NetHandlerPlayServer) {
				EntityPlayerMP lplayer = ((NetHandlerPlayServer)pEvent.handler).playerEntity;
				
				// プレーヤー以外が発動するのを考慮すべき？
				DestroyAllData ldd = new DestroyAllData();
				ldd.readbyteBuf(lplayer, pEvent.packet.payload());
				Debug(ldd.toString());
				ldd.DestroyAll();
			}
		}
	}

}
