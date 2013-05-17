package net.minecraft.src;

public interface MMM_IModelBaseMMM extends MMM_IModelCaps {

	public void renderItems(EntityLiving pEntity, Render pRender);
	public void showArmorParts(int pIndex);
	public void setEntityCaps(MMM_IModelCaps pModelCaps);
	public void setRender(Render pRender);
	public void setArmorRendering(boolean pFlag);

}
