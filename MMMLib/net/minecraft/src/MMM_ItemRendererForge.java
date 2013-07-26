package net.minecraft.src;

import net.minecraftforge.client.IItemRenderer;

public class MMM_ItemRendererForge implements IItemRenderer {

	@Override
	public boolean handleRenderType(ItemStack item, ItemRenderType type) {
		if (item == null || MMM_ItemRenderManager.isEXRender(item.getItem())) return false;
		
		MMM_ItemRenderManager lirm = MMM_ItemRenderManager.getEXRender(item.getItem());
		switch (type) {
		case ENTITY:
			return lirm.isRenderItemWorld();
		case EQUIPPED:
			return lirm.isRenderItem();
		case EQUIPPED_FIRST_PERSON:
			return lirm.isRenderItemInFirstPerson();
		case INVENTORY:
		case FIRST_PERSON_MAP:
			break;
		}
		return false;
	}

	@Override
	public boolean shouldUseRenderHelper(ItemRenderType type, ItemStack item, ItemRendererHelper helper) {
		return false;
	}

	@Override
	public void renderItem(ItemRenderType type, ItemStack item, Object... data) {
		MMM_ItemRenderManager lirm = MMM_ItemRenderManager.getEXRender(item.getItem());
		switch (type) {
		case ENTITY:
			EntityItem lei = (EntityItem)data[1];
			lirm.renderItemWorld(lei, 0D, 0D, 0D, 0F, 1.0F);
			break;
		case EQUIPPED:
			lirm.renderItem((Entity)data[1], item, 0);
			break;
		case EQUIPPED_FIRST_PERSON:
			lirm.renderItemInFirstPerson((Entity)data[1], item, 0.0F);
			break;
		case INVENTORY:
		case FIRST_PERSON_MAP:
			break;
		}
	}

}
