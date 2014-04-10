package mmm.MultiBench;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.world.World;
import cpw.mods.fml.common.network.IGuiHandler;

public class GuiHandler implements IGuiHandler{

	@Override
	public Object getServerGuiElement(int ID, EntityPlayer player, World world,
			int x, int y, int z) {
		ContainerMultiBench lcmb = new ContainerMultiBench(player.inventory, world, x, y, z);
		lcmb.player = (EntityPlayerMP)player;
		return lcmb;
	}

	@Override
	public Object getClientGuiElement(int ID, EntityPlayer player, World world,
			int x, int y, int z) {
		return new GuiMultiBench(player.inventory, world, x, y, z);
	}

}
