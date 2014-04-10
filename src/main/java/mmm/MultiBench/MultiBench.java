package mmm.MultiBench;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.network.NetHandlerPlayServer;
import net.minecraft.network.PacketBuffer;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.TickEvent;
import cpw.mods.fml.common.network.FMLEventChannel;
import cpw.mods.fml.common.network.FMLNetworkEvent;
import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.common.network.internal.FMLProxyPacket;
import cpw.mods.fml.common.registry.GameRegistry;

@Mod(
		modid	= "MultiBench",
		name	= "MultiBench",
		version	= "1.7.2-x"
		)
public class MultiBench {

	@Instance("MultiBench")
	public static MultiBench instance;
	public static final String packetChannel = "MMM|MLB";
	public static FMLEventChannel serverEventChannel;
	public static Block blockMultiBench;
	protected static List<EntityPlayerMP> updateList;

	public static boolean isDebugMessage = true;


	public static void Debug(String pText, Object... pData) {
		// デバッグメッセージ
		if (isDebugMessage) {
			System.out.println(String.format("MultiBench-" + pText, pData));
		}
	}

	@EventHandler
	public void preInit(FMLPreInitializationEvent pEvent) {
		blockMultiBench = (new BlockMultiBench()).setHardness(2.5F).setStepSound(Block.soundTypeWood).setBlockName("MultiBench").setBlockTextureName("crafting_table");
		GameRegistry.registerBlock(blockMultiBench, ItemBlock.class, "MultiBench");
		FMLCommonHandler.instance().bus().register(instance);
		updateList = new ArrayList<EntityPlayerMP>();
	}

	@EventHandler
	public void init(FMLInitializationEvent event) {
		NetworkRegistry.INSTANCE.registerGuiHandler(this, new GuiHandler());
		
		GameRegistry.addShapedRecipe(new ItemStack(blockMultiBench), 
				"ww",
				"ww",
				'w', Blocks.crafting_table
			);
		serverEventChannel = NetworkRegistry.INSTANCE.newEventDrivenChannel(packetChannel);
		serverEventChannel.register(instance);
	}

	public static void sendChangeIndex(int pIndex) {
		Debug("Send change index.(%d)", pIndex);
		ByteBuf lbuf = Unpooled.buffer();
		lbuf.writeInt(pIndex);
		FMLProxyPacket lpacket = new FMLProxyPacket(lbuf, packetChannel);
		serverEventChannel.sendToServer(lpacket);
	}

	public static void sendChangeItemStack(EntityPlayerMP pPlayer, int pIndex, ItemStack pItemStack) {
		Debug("Send change Itemstack.(%s)", pItemStack == null ? "null" : pItemStack.toString());
		try {
			ByteBuf lbuf = Unpooled.buffer();
			PacketBuffer lpbuf = new PacketBuffer(lbuf);
			lpbuf.writeInt(pIndex);
			lpbuf.writeItemStackToBuffer(pItemStack);
			FMLProxyPacket lpacket = new FMLProxyPacket(lpbuf, packetChannel);
			pPlayer.playerNetServerHandler.sendPacket(lpacket);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@SubscribeEvent
	public void onGetPacketServer(FMLNetworkEvent.ServerCustomPacketEvent pEvent) {
		// パケット受信
		if (pEvent.packet.channel().contentEquals(packetChannel)) {
			int lindex = pEvent.packet.payload().readInt();
			Debug("recive change result.(%d)", lindex);
			if (pEvent.handler instanceof NetHandlerPlayServer) {
				EntityPlayerMP lplayer = ((NetHandlerPlayServer)pEvent.handler).playerEntity;
				if (lplayer.openContainer instanceof ContainerMultiBench) {
					ContainerMultiBench lcmb = (ContainerMultiBench)lplayer.openContainer;
//					sendChangeItemStack(lplayer, lcmb.index, lcmb.addIndex(lindex));
					lcmb.addIndex(lindex);
					if (!updateList.contains(lplayer)) {
						updateList.add(lplayer);
					}
				}
			}
		}
	}

	@SubscribeEvent
	public void onGetPacketClient(FMLNetworkEvent.ClientCustomPacketEvent pEvent) {
		// パケット受信
		if (pEvent.packet.channel().contentEquals(packetChannel)) {
			PacketBuffer lpbuf = new PacketBuffer(pEvent.packet.payload());
			try {
				int lindex = lpbuf.readInt();
				ItemStack litemstack = lpbuf.readItemStackFromBuffer();
				Debug("recive change result.(%s)", litemstack == null ? "null" : litemstack.toString());
				GuiMultiBench.getPacket(lindex, litemstack);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	@SubscribeEvent
	public void onServerTick(TickEvent.ServerTickEvent event) {
		// 複数回更新情報を送らないように一括で送る。
		if (!updateList.isEmpty()) {
			for (EntityPlayerMP lplayer : updateList) {
				if (lplayer.openContainer instanceof ContainerMultiBench) {
					ContainerMultiBench lcmb = (ContainerMultiBench)lplayer.openContainer;
					sendChangeItemStack(lplayer, lcmb.index, lcmb.craftResult.getStackInSlot(0));
				}
			}
			updateList.clear();
		}
	}

}
