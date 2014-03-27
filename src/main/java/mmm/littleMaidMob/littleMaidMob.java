package mmm.littleMaidMob;

import java.io.File;

import mmm.lib.ProxyCommon;
import net.minecraftforge.common.config.Configuration;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.registry.EntityRegistry;

@Mod(
		modid	= "littleMaidMob",
		name	= "LittleMaidMob"
		)
public class littleMaidMob {

	@SidedProxy(clientSide = "mmm.littleMaidMob.ProxyClient", serverSide = "mmm.lib.ProxyCommon")
	public static ProxyCommon proxy;

	public static boolean isDebugMessage = true;


	public static void Debug(String pText, Object... pData) {
		// デバッグメッセージ
		if (isDebugMessage) {
			System.out.println(String.format("littleMaidMob-" + pText, pData));
		}
	}

	@Mod.EventHandler
	public void preInit(FMLPreInitializationEvent pEvent) {
		// コンフィグの解析・設定
		File configFile = pEvent.getSuggestedConfigurationFile();
		Configuration lconf = new Configuration(configFile);
		lconf.load();
		isDebugMessage	= lconf.get("littleMaidMob", "isDebugMessage", false).getBoolean(false);
		lconf.save();
		
		String ls = "littleMaidMob";
		int lid = EntityRegistry.findGlobalUniqueEntityId();
		EntityRegistry.registerGlobalEntityID(EntityLittleMaidMob.class, ls, lid, 0xefffef, 0x9f5f5f);
		EntityRegistry.registerModEntity(EntityLittleMaidMob.class, ls, lid, this, 80, 3, true);
	}

	@Mod.EventHandler
	public void init(FMLInitializationEvent pEvent) {
		// レンダラの登録
		proxy.init();

	}

}
