package mmm.lib;

import java.io.File;
import java.lang.reflect.Constructor;
import java.net.URL;
import java.net.URLClassLoader;

import mmm.lib.debugs.EzRecipes;
import mmm.lib.debugs.MoveWindow;
import mmm.lib.debugs.fileTest;
import mmm.lib.destroyAll.DestroyAllManager;
import mmm.lib.guns.GunsBase;
import mmm.lib.model.ModelBase;
import mmm.lib.model.ModelLittleMaid_Archetype;
import mmm.lib.model.ModelMultiBase;
import net.minecraft.launchwrapper.LaunchClassLoader;
import net.minecraftforge.common.config.Configuration;
import cpw.mods.fml.common.Loader;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
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
		
	}

	@Mod.EventHandler
	public void init(FMLInitializationEvent pEvent) {
		if (pEvent.getSide() == Side.CLIENT) {
			MoveWindow.setPosition();
		}
	}

	@Mod.EventHandler
	public void loaded(FMLPostInitializationEvent pEvent) {
		for (Object lo : Loader.instance().getModList()) {
			Debug("%s", lo.toString());
		}
		EzRecipes.init();
		
		// TODO test
		(new fileTest()).execute();
		ModelMultiBase lmodel = new ModelLittleMaid_Archetype();
		Debug(lmodel.toString());
//		MMM_ModelBase lmb = new MMM_ModelBase();
		
		viewClasses(getClass().getClassLoader());
		viewClasses(getClass().getClassLoader().getClass().getClassLoader());
		try {
			ClassLoader lcl = this.getClass().getClassLoader();
			Debug("ClassLoader: %s", lcl.toString());
			Class lc = lcl.loadClass("mmm.testCode.ModelLittleMaid_Aokise");
			Debug("Class: %s - %s", lc.toString(), lc.getPackage());
			Constructor<ModelMultiBase> lcc = lc.getConstructor();
			Debug("Constructor: %s", lcc.toString());
			lmodel = lcc.newInstance();
			Debug(lmodel.toString());
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		try {
			ClassLoader lcl;
			lcl = Loader.instance().getModClassLoader();
			lcl.getClass().getClassLoader();
//			if (lcl instanceof LaunchClassLoader) {
//				Debug("addURL");
//				((LaunchClassLoader)lcl).addURL(new URL("file:/E:/GAME/SIM/MineCraft/MMMMOD/MyGit/ModsGradle/master/eclipse/mods/littleMaidMob-ModelTest.zip"));
//			}
			
			lcl = Loader.instance().getModClassLoader();
//			if (lcl instanceof LaunchClassLoader) {
//				((LaunchClassLoader)lcl).addTransformerExclusion("ModelLittleMaid");
//				((LaunchClassLoader)lcl).addClassLoaderExclusion("ModelLittleMaid");
//				((LaunchClassLoader)lcl).addClassLoaderExclusion("MMM_");
//				
//			}
			Debug("ClassLoader: %s", lcl.toString());
			Class lc;
//			lc = lcl.loadClass("MMM_ModelBase");
//			Debug("Class: %s", lc.toString());
//			ModelBase lmb = (ModelBase) lc.getConstructor().newInstance();
			lc = lcl.loadClass("MMM_ModelLittleMaid_Archetype");
			Debug("Class: %s - %s", lc.toString(), lc.getPackage());
			Constructor<ModelMultiBase> lcc = lc.getConstructor();
			Debug("Constructor: %s", lcc.toString());
			lmodel = lcc.newInstance();
			Debug(lmodel.toString());
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	private void viewClasses(ClassLoader pClassLoader) {
		Debug("\r\nClassLoader: %s", pClassLoader.toString());
		if (pClassLoader instanceof URLClassLoader ) {
			for (URL lurl : ((URLClassLoader)pClassLoader).getURLs()) {
				Debug("%s", lurl.toString());
			}
		}
		Class<?>[] lca = pClassLoader.getClass().getClasses();
		Debug("Classes: %d", lca == null ? 0 : lca.length);
		for (Class<?> lc : pClassLoader.getClass().getClasses()) {
			Debug("%s", lc.toString());
		}
	}

}
