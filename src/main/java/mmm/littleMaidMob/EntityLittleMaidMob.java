package mmm.littleMaidMob;

import net.minecraft.entity.EntityCreature;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class EntityLittleMaidMob extends EntityCreature {


	public EntityLittleMaidMob(World par1World) {
		super(par1World);
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

}
