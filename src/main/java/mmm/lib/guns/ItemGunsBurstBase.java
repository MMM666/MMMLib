package mmm.lib.guns;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;

/**
 * バーストショット用ベースクラス
 *
 */
public abstract class ItemGunsBurstBase extends ItemGunsBase {

	public static final String Static_Burst = "Burst";
	public static final String Static_Cycle = "Cycle";


	/**
	 * 最大点射数
	 * @return
	 */
	public abstract int getBurstCount(ItemStack pGun);

	/**
	 * 発射インターバル
	 * @return
	 */
	public abstract short getCycleCount(ItemStack pGun);

	@Override
	public ItemStack onItemRightClick(ItemStack par1ItemStack, World par2World, EntityPlayer par3EntityPlayer) {
		GunsBase.Debug("%s - trigger", par3EntityPlayer instanceof EntityPlayerMP ? "MP" : "SP");
		int li = getState(par1ItemStack);
		if (par3EntityPlayer.isSwingInProgress) {
			setState(par1ItemStack, State_ReloadTac);
			GunsBase.Debug("Tactical Reload.");
		} else {
			if (li >= State_Empty && li < State_Reload) {
				if (hasAmmo(par1ItemStack, par2World, par3EntityPlayer)) {
					// リロード
					setState(par1ItemStack, State_Reload);
					GunsBase.Debug("Reload.");
				} else {
					// 空打ち
//					setState(par1ItemStack, State_Empty);
					soundEmpty(par2World, par3EntityPlayer, par1ItemStack);
					GunsBase.Debug("Empty.");
				}
			} else if (li < State_Empty) {
				// 発射可能
				resetBolt(par1ItemStack);
				resetBurst(par1ItemStack);
			}
		}
		GunsBase.Debug("%s - ItemStack: %s",
				par3EntityPlayer instanceof EntityPlayerMP ? "MP" : "SP",
				par1ItemStack.toString()
				);
		GunsBase.setUncheckedItemStack(par1ItemStack, par3EntityPlayer);
		par3EntityPlayer.setItemInUse(par1ItemStack, this.getMaxItemUseDuration(par1ItemStack));
		return par1ItemStack;
	}

	@Override
	public void onPlayerStoppedUsing(ItemStack par1ItemStack, World par2World,
			EntityPlayer par3EntityPlayer, int par4) {
		int li = getState(par1ItemStack);
		if (li == State_ReloadEnd) {
			setState(par1ItemStack, State_Ready);
			GunsBase.setUncheckedItemStack(par1ItemStack, par3EntityPlayer);
		}
	}

	public void onFireTick(ItemStack pGun, World pWorld, EntityPlayer pPlayer, int count, int pState) {
		if (pState == State_Ready && !isAmmoEmpty(pGun)) {
			if (checkBolt(pGun) && decBurst(pGun) > 0) {
				// 発射
				if (fireBullet(pGun, pWorld, pPlayer, count) <= 0) {
					setState(pGun, State_Empty);
				}
			}
			GunsBase.setUncheckedItemStack(pGun, pPlayer);
		}
	}

	public boolean checkBolt(ItemStack pGun) {
		NBTTagCompound ltag = getTagCompound(pGun);
		short lval = ltag.getShort(Static_Cycle);
		if (--lval <= 0) {
			ltag.setShort(Static_Cycle, getCycleCount(pGun));
			return true;
		}
		ltag.setShort(Static_Cycle, lval);
		return false;
	}

	public void resetBolt(ItemStack pGun) {
		NBTTagCompound ltag = getTagCompound(pGun);
		ltag.setShort(Static_Cycle, getCycleCount(pGun));
	}

	public int decBurst(ItemStack pGun) {
		NBTTagCompound ltag = getTagCompound(pGun);
		int lburst = ltag.getInteger(Static_Burst);
		if (lburst > 0) {
			ltag.setInteger(Static_Burst, lburst - 1);
		}
		return lburst;
	}

	public void resetBurst(ItemStack pGun) {
		NBTTagCompound ltag = getTagCompound(pGun);
		ltag.setInteger(Static_Burst, getBurstCount(pGun));
	}

}
