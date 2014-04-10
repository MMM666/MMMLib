package mmm.lib;

import java.io.File;
import java.util.List;

import mmm.lib.debugs.EzRecipes;
import mmm.lib.debugs.MMMDecorder;
import mmm.lib.debugs.MoveWindow;
import mmm.lib.destroyAll.DestroyAllManager;
import mmm.lib.guns.GunsBase;
import mmm.lib.multiModel.MMMLoader.MMMTransformer;
import mmm.lib.multiModel.texture.MultiModelManager;
import net.minecraftforge.common.config.Configuration;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.relauncher.ReflectionHelper;
import cpw.mods.fml.relauncher.Side;

@Mod(
		modid	= "MMMLib",
		name	= "MMMLib",
		version	= "1.7.x-srg-1"
		)
public class MMMLib {

	public static boolean isDebugMessage = true;
	public static boolean isModelAlphaBlend = true;



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
		isDebugMessage		= lconf.get("MMMLib", "isDebugMessage", false).getBoolean(false);
		isModelAlphaBlend	= lconf.get("MMMLib", "isModelAlphaBlend", true).getBoolean(true);
		
		String ls;
		ls = "DestroyAll";
		lconf.addCustomCategoryComment(ls, "Package destruction of the fixed range is carried out.");
		DestroyAllManager.isDebugMessage = lconf.get(ls, "isDebugMessage", false).getBoolean(false);
		
		ls = "GunsBase";
		lconf.addCustomCategoryComment(ls, "Basic processing of a firearm.");
		GunsBase.isDebugMessage = lconf.get(ls, "isDebugMessage", false).getBoolean(false);
		
		ls = "MoveScreen";
		lconf.addCustomCategoryComment(ls, "The position of a window is automatically moved to a start-up.");
		MoveWindow.isMoveWindow	= lconf.get(ls, "isMoveWindow", false).getBoolean(false);
		MoveWindow.windowPosX	= lconf.get(ls, "windowPosX", 20).getInt(20);
		MoveWindow.windowPosY	= lconf.get(ls, "windowPosY", 50).getInt(50);
		
		ls = "EzRecipes";
		lconf.addCustomCategoryComment(ls, "Append Recipes from JSON.");
		EzRecipes.isDebugMessage = lconf.get(ls, "isDebugMessage", false).getBoolean(false);
		lconf.save();
		
		// 独自スクリプトデコーダー
		(new MMMDecorder()).execute();
		
	}

	@Mod.EventHandler
	public void init(FMLInitializationEvent pEvent) {
		if (pEvent.getSide() == Side.CLIENT) {
			MoveWindow.setPosition();
		}
	}

	@Mod.EventHandler
	public void loaded(FMLPostInitializationEvent pEvent) {
		// 独自スクリプトデコーダー
//		EzRecipes.init();
		// 
		GunsBase.initAppend();
		
		// 旧モデル用変換開始
		MMMTransformer.isEnable = true;
		MultiModelManager.instance.execute();
		
		// TODO test
		List<File> llist = FileManager.getAllmodsFiles(getClass().getClassLoader(), true);
		for (File lf : llist) {
			Debug("targetFiles: %s", lf.getAbsolutePath());
		}
		
		
		try {
			Class<?> lc = ReflectionHelper.getClass(getClass().getClassLoader(), "net.minecraft.entity.EntityLivingBase");
			Debug("test-getClass: %s", lc.toString());
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

}
