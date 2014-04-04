package mmm.MultiBench;

import net.minecraft.block.Block;
import net.minecraft.item.ItemBlock;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.common.registry.GameRegistry;

@Mod(
		modid	= "MultiBench",
		name	= "MultiBench",
		version	= "1.7.2-x"
		)
public class MultiBench {

	@Instance("MultiBench")
	public static MultiBench instance;

	public static Block blockMultiBench;


	@EventHandler
	public void preInit(FMLPreInitializationEvent pEvent) {
	}

	@EventHandler
	public void init(FMLInitializationEvent event) {
		blockMultiBench = (new BlockMultiBench()).setHardness(2.5F).setStepSound(Block.soundTypeWood).setBlockName("MultiBench").setBlockTextureName("crafting_table");
		GameRegistry.registerBlock(blockMultiBench, ItemBlock.class, "MultiBench");
//		GameRegistry.registerTileEntity(TileEntityTiny.class, "containerTiny");
		NetworkRegistry.INSTANCE.registerGuiHandler(this, new GuiHandler());
	}

}
