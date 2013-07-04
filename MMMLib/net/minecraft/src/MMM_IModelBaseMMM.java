package net.minecraft.src;

public interface MMM_IModelBaseMMM extends MMM_IModelCaps {

	public void renderItems(EntityLivingBase pEntity, Render pRender);
	public void showArmorParts(int pParts);
	public void setEntityCaps(MMM_IModelCaps pModelCaps);
	public void setRender(Render pRender);
	public void setArmorRendering(boolean pFlag);

}
