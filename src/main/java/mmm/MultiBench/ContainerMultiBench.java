package mmm.MultiBench;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.ContainerWorkbench;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.CraftingManager;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.world.World;

public class ContainerMultiBench extends ContainerWorkbench {

	protected World worldObj;
	protected int posX;
	protected int posY;
	protected int posZ;

	public ContainerMultiBench(InventoryPlayer par1InventoryPlayer,
			World par2World, int par3, int par4, int par5) {
		super(par1InventoryPlayer, par2World, par3, par4, par5);
		worldObj = par2World;
		posX = par3;
		posY = par4;
		posZ = par5;
	}

	@Override
	public void onCraftMatrixChanged(IInventory par1iInventory) {
		craftResult.setInventorySlotContents(0, CraftingManager.getInstance().findMatchingRecipe(craftMatrix, worldObj));
	}

	protected List<ItemStack> getCraftList() {
		// 合致する全てのレシピを獲得する
		// CraftingManager.findMatchingRecipe丸パクリ
		@SuppressWarnings("unchecked")
		List<IRecipe> lrlist = CraftingManager.getInstance().getRecipeList();
		List<ItemStack> lresult = new ArrayList<ItemStack>();
		
		int i = 0;
		ItemStack itemstack = null;
		ItemStack itemstack1 = null;
		
		// 修理レシピ判定
		for (int j = 0; j < craftMatrix.getSizeInventory(); ++j) {
			ItemStack itemstack2 = craftMatrix.getStackInSlot(j);
			
			if (itemstack2 != null) {
				if (i == 0) {
					itemstack = itemstack2;
				}
				
				if (i == 1) {
					itemstack1 = itemstack2;
				}
				
				++i;
			}
		}
		
		if (i == 2 && itemstack.getItem() == itemstack1.getItem()
				&& itemstack.stackSize == 1 && itemstack1.stackSize == 1
				&& itemstack.getItem().isRepairable()) {
			// 修理
			Item item = itemstack.getItem();
			int j1 = item.getMaxDamage() - itemstack.getItemDamageForDisplay();
			int k = item.getMaxDamage() - itemstack1.getItemDamageForDisplay();
			int l = j1 + k + item.getMaxDamage() * 5 / 100;
			int i1 = item.getMaxDamage() - l;
			
			if (i1 < 0) {
				i1 = 0;
			}
			
			lresult.add(new ItemStack(itemstack.getItem(), 1, i1));
		} else {
			for (int j = 0; j < lrlist.size(); ++j) {
				IRecipe lr = lrlist.get(j);
				
				if (lr.matches(craftMatrix, worldObj)) {
					lresult.add(lr.getCraftingResult(craftMatrix));
				}
			}
		}
		
		return lresult;
	}

	@Override
	public boolean canInteractWith(EntityPlayer par1EntityPlayer) {
		return worldObj.getBlock(posX, posY, posZ) != MultiBench.blockMultiBench ? false : par1EntityPlayer.getDistanceSq((double)posX + 0.5D, (double)posY + 0.5D, (double)posZ + 0.5D) <= 64.0D;
	}
	
}
