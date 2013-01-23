package net.minecraft.src;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Random;

import org.lwjgl.opengl.GL11;

public class MMM_RenderItem extends RenderItem {

	private Random random;


	public MMM_RenderItem() {
		super();
		random = new Random();
	}
	
	public boolean doRenderEXItem(EntityItem entityitem, double d, double d1, double d2, float f, float f1, Method pMethod) {
		boolean lflag = false;
		random.setSeed(187L);
		GL11.glPushMatrix();
		float f2 = MathHelper.sin(((float)entityitem.age + f1) / 10F + entityitem.hoverStart) * 0.1F + 0.1F;
		float f3 = (((float)entityitem.age + f1) / 20F + entityitem.hoverStart) * 57.29578F;
		byte byte0 = 1;
		ItemStack lis = entityitem.getEntityItem();
		if (lis.stackSize > 1) {
			byte0 = 2;
		}
		if (lis.stackSize > 5) {
			byte0 = 3;
		}
		if (lis.stackSize > 20) {
			byte0 = 4;
		}
		GL11.glTranslatef((float)d, (float)d1 + f2, (float)d2);
		GL11.glEnable(32826 /*GL_RESCALE_NORMAL_EXT*/);
		GL11.glRotatef(f3, 0.0F, 1.0F, 0.0F);
		float f4 = 1.0F; //0.25F;
		for (int j = 0; j < byte0; j++) {
			GL11.glPushMatrix();
			if(j > 0)
			{
				float f5 = ((random.nextFloat() * 2.0F - 1.0F) * 0.2F) / f4;
				float f7 = ((random.nextFloat() * 2.0F - 1.0F) * 0.2F) / f4;
				float f9 = ((random.nextFloat() * 2.0F - 1.0F) * 0.2F) / f4;
				GL11.glTranslatef(f5, f7, f9);
			}
			
			try {
				lflag = (Boolean)pMethod.invoke(lis.getItem(), 0, lis, entityitem);
			} catch (Exception e) {
				e.printStackTrace();
			}
			GL11.glPopMatrix();
		}
		
		GL11.glPopMatrix();
		return lflag;
	}

	@Override
	public void doRender(Entity entity, double d, double d1, double d2, float f, float f1) {
		if (entity instanceof EntityItem) {
			EntityItem ei = (EntityItem)entity;
			try {
				Method lmethod = ei.getClass().getMethod("renderItem", int.class, ItemStack.class, EntityLiving.class);
				if (lmethod != null) {
					if (doRenderEXItem(ei, d, d1, d2, f, f1, lmethod)) {
						return;
					}
				}
			} catch (Exception e) {
			}
		}
		
		super.doRender(entity, d, d1, d2, f, f1);
	}

	@Override
	public void renderItemIntoGUI(FontRenderer pFontrenderer, RenderEngine pRenderengine, ItemStack pItemStack, int pX, int pY) {
		try {
			Method lmethod;
			lmethod = pItemStack.getItem().getClass().getMethod("renderItemIntoGUI", FontRenderer.class, RenderEngine.class, int.class, int.class, int.class, int.class, int.class);
			if ((Boolean)lmethod.invoke(null, pFontrenderer, pRenderengine, pItemStack, pX, pY)) {
				return;
			}
		} catch (Exception e) {
		}
		super.renderItemIntoGUI(pFontrenderer, pRenderengine, pItemStack, pX, pY);
	}

}
