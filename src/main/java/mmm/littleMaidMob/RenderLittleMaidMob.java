package mmm.littleMaidMob;

import mmm.lib.MMMLib;
import mmm.lib.multiModel.model.mc162.IModelCaps;
import mmm.lib.multiModel.model.mc162.ModelBaseDuo;
import mmm.lib.multiModel.model.mc162.ModelBaseSolo;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.util.ResourceLocation;

public class RenderLittleMaidMob extends RenderLiving {

	public ModelBaseSolo modelMain;
	public ModelBaseDuo modelFATT;
	public IModelCaps fcaps;

//	private static final ResourceLocation textures = new ResourceLocation("textures/entity/pig/pig.png");
	private static final ResourceLocation textures = new ResourceLocation("/mob/littleMaid/CF_Kagami/kagami_c.png");


	public RenderLittleMaidMob(float pShadowSize) {
		super(null, pShadowSize);
		modelFATT = new ModelBaseDuo(this);
		modelFATT.isModelAlphablend = MMMLib.isModelAlphaBlend;
//		modelFATT.isRendering = true;
		modelMain = new ModelBaseSolo(this);
		modelMain.isModelAlphablend = MMMLib.isModelAlphaBlend;
		modelMain.capsLink = modelFATT;
		mainModel = modelMain;
		setRenderPassModel(modelFATT);
		
		modelMain.textures = new ResourceLocation[] { textures };
/*
		modelMain.model = new ModelLittleMaid_Aug();
		try {
			ClassLoader lcl = getClass().getClassLoader();
			Class lc = lcl.loadClass("ModelLittleMaid_Kelo");
			Constructor<ModelMultiBase> lcc = lc.getConstructor();
			modelMain.model = lcc.newInstance();
		} catch (Exception e) {
			e.printStackTrace();
		}
*/
	}

	@Override
	public void doRender(EntityLiving par1EntityLiving, double par2,
			double par4, double par6, float par8, float par9) {
		doRender((EntityLittleMaidMob)par1EntityLiving, par2, par4, par6, par8, par9);
	}

	public void doRender(EntityLittleMaidMob pEntity, double pX, double pY, double pZ, float par8, float par9) {
		modelMain.isRendering = true;
		modelMain.model = pEntity.getModel();
		super.doRender(pEntity, pX, pY, pZ, par8, par9);
	}

	@Override
	protected ResourceLocation getEntityTexture(Entity var1) {
		// TODO Auto-generated method stub
//		return ((EntityLittleMaidMob)var1).getTexture();
		return textures;
	}

}
