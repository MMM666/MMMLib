package mmm.lib.guns;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;

public class ItemBulletBase extends Item {

	public ItemBulletBase() {
		setCreativeTab(CreativeTabs.tabCombat);
	}

	/**
	 * 基準発射速度
	 * @param pBullet
	 * @return
	 */
	public float getSpeed(ItemStack pBullet) {
		// 500m/s
		return 25F;
	}

	/**
	 * 集弾性
	 * @param pBullet
	 * @return
	 */
	public float getReaction(ItemStack pBullet) {
		return 1.0F;
	}


	/**
	 * 弾薬に関連付けられたEntityを返す。
	 * @param pGun
	 * @param pBullet
	 * @param pWorld
	 * @param pPlayer
	 * @return
	 */
	public EntityBulletBase getBulletEntity(ItemStack pGun, ItemStack pBullet, World pWorld, EntityPlayer pPlayer, int pUseCount) {
		// 標準弾体
		ItemGunsBase lgun = ((ItemGunsBase)pGun.getItem());
		return new EntityBulletBase(pWorld, pPlayer, pGun, pBullet,
				getSpeed(pBullet) * lgun.getEfficiency(pGun, pPlayer, pUseCount),
				getReaction(pBullet) * lgun.getStability(pGun, pPlayer, pUseCount));
	}

	/**
	 * 発射音
	 * @param pWorld
	 * @param pPlayer
	 * @param pBullet
	 */
	public void soundFire(World pWorld, EntityPlayer pPlayer, ItemStack pGun, ItemStack pBullet) {
	}

	/**
	 * 弾の色
	 * @return
	 */
	@SideOnly(Side.CLIENT)
	public int getBulletColor(ItemStack pBullet) {
		return 0x804000;
	}

	public float getHitDamage(EntityBulletBase pBullrtEntity,  Entity pTargetEntity) {
		float ldam = pBullrtEntity.speed * 0.2F;//Math.ceil((double)lfd * damage * 0.1D * (isInfinity ? 0.5D : 1D));
		GunsBase.Debug("damage: %f", ldam);
		return ldam;
	}

	public boolean onHitEntity(MovingObjectPosition var1, EntityBulletBase pBullrtEntity,  Entity pTargetEntity) {
		pTargetEntity.hurtResistantTime = 0;
		pTargetEntity.attackEntityFrom(
				DamageSource.causeThrownDamage(pBullrtEntity, pBullrtEntity.getThrower()), getHitDamage(pBullrtEntity, pTargetEntity));
		if (!pBullrtEntity.worldObj.isRemote) {
			pBullrtEntity.setDead();
		}
		return true;
	}

}
