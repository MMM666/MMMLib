package mmm.littleMaidMob;

import java.lang.reflect.Constructor;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import mmm.lib.multiModel.model.mc162.ModelLittleMaid_Aug;
import mmm.lib.multiModel.model.mc162.ModelMultiBase;
import net.minecraft.entity.EntityCreature;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

public class EntityLittleMaidMob extends EntityCreature {

	public ModelMultiBase model;


	public EntityLittleMaidMob(World par1World) {
		super(par1World);
		model = new ModelLittleMaid_Aug();
		try {
			ClassLoader lcl = getClass().getClassLoader();
			Class lc = lcl.loadClass("ModelLittleMaid_Kagami");
			Constructor<ModelMultiBase> lcc = lc.getConstructor();
			model = lcc.newInstance();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public ItemStack getHeldItem() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ItemStack getEquipmentInSlot(int var1) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setCurrentItemOrArmor(int var1, ItemStack var2) {
		// TODO Auto-generated method stub

	}

	@Override
	public ItemStack[] getLastActiveItems() {
		// 被ダメ時に此処を参照するのでNULL以外を返すこと。
		return new ItemStack[0];
	}

	@SideOnly(Side.CLIENT)
	public ModelMultiBase getModel() {
		return model;
	}

	@SideOnly(Side.CLIENT)
	public ResourceLocation getTexture() {
		return new ResourceLocation("/mob/littleMaid/CF_Kagami/kagami_c.png");
	}
}
