package mmm.lib;

import java.io.File;

import mmm.lib.debugs.MoveWindow;
import mmm.lib.destroyAll.DestroyAllManager;
import mmm.lib.guns.GunsBase;
import net.minecraftforge.common.config.Configuration;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.relauncher.Side;

@Mod(
		modid	= "MMMLib",
		name	= "MMMLib",
		version	= "1.7.x-srg-1"
		)
public class MMMLib {

	public static boolean isDebugMessage = true;



	public static void Debug(String pText, Object... pData) {
		// デバッグメッセージ
		if (isDebugMessage) {
			System.out.println(String.format("MMMLib-" + pText, pData));
		}
	}

	@Mod.EventHandler
	public void preInit(FMLPreInitializationEvent pEvent) {
		// コンフィグの解析・設定
		File configFile = pEvent.getSuggestedConfigurationFile();
		Configuration lconf = new Configuration(configFile);
		lconf.load();
		isDebugMessage	= lconf.get("MMMLib", "isDebugMessage", false).getBoolean(false);
		
		lconf.addCustomCategoryComment("DestroyAll", "Package destruction of the fixed range is carried out.");
		DestroyAllManager.isDebugMessage = lconf.get("DestroyAll", "isDebugMessage", false).getBoolean(false);
		
		lconf.addCustomCategoryComment("GunsBase", "Basic processing of a firearm.");
		GunsBase.isDebugMessage = lconf.get("GunsBase", "isDebugMessage", false).getBoolean(false);
		
		lconf.addCustomCategoryComment("MoveScreen", "The position of a window is automatically moved to a start-up.");
		MoveWindow.isMoveWindow	= lconf.get("MoveScreen", "isMoveWindow", false).getBoolean(false);
		MoveWindow.windowPosX	= lconf.get("MoveScreen", "windowPosX", 20).getInt(20);
		MoveWindow.windowPosY	= lconf.get("MoveScreen", "windowPosY", 50).getInt(50);
		lconf.save();
		
	}

	@Mod.EventHandler
	public void init(FMLInitializationEvent pEvent) {
		if (pEvent.getSide() == Side.CLIENT) {
			MoveWindow.setPosition();
		}
	}

}
