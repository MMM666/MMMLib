package mmm.lib.debugs;

import java.awt.Component;
import java.awt.Frame;

import org.lwjgl.opengl.Display;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import mmm.lib.MMMLib;
import net.minecraft.client.Minecraft;

/**
 * ウィンドウを指定位置へ移動する。
 *
 */
public class MoveWindow {

	public static boolean isMoveWindow = false;
	public static int windowPosX = 20;
	public static int windowPosY = 50;



	@SuppressWarnings("static-access")
	@SideOnly(Side.CLIENT)
	public static void setPosition() {
		if (!isMoveWindow) return;
		
		Component lco = Display.getParent();
		do {
			if (lco == null) {
				MMMLib.Debug("window size : %d, %d", Minecraft.getMinecraft().displayWidth, Minecraft.getMinecraft().displayHeight);
				Display.setResizable(false);
				Display.setLocation(windowPosX, windowPosY);
				Display.setResizable(true);
				MMMLib.Debug("Component: Display");
				break;
			} else {
				MMMLib.Debug(lco.getName());
			}
			if (lco instanceof Frame) {
				lco.setLocation(windowPosX, windowPosY);
				MMMLib.Debug("move window location.");
				Frame lfo = (Frame)lco;
				for (int li = 0; li < lfo.getWindows().length; li++) {
					MMMLib.Debug("ComponentCount:" + lfo.getWindows()[li]);
				}
				break;
			}
			lco = lco.getParent();
		} while(lco != null);
	}

}
