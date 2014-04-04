package mmm.MultiBench;

import net.minecraft.block.BlockWorkbench;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;

public class BlockMultiBench extends BlockWorkbench {

	public boolean onBlockActivated(World p_149727_1_,
			int p_149727_2_, int p_149727_3_, int p_149727_4_,
			EntityPlayer p_149727_5_, int p_149727_6_,
			float p_149727_7_, float p_149727_8_, float p_149727_9_) {
		if (p_149727_1_.isRemote) {
			return true;
		} else {
			p_149727_5_.openGui(MultiBench.instance, 0, p_149727_1_, p_149727_2_, p_149727_3_, p_149727_4_);
			return true;
		}
	}

}
