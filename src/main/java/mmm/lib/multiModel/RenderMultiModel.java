package mmm.lib.multiModel;

import mmm.lib.MMMLib;
import mmm.lib.multiModel.model.mc162.IModelCaps;
import mmm.lib.multiModel.model.mc162.ModelBaseDuo;
import mmm.lib.multiModel.model.mc162.ModelBaseSolo;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.ResourceLocation;

import org.lwjgl.opengl.GL11;

public class RenderMultiModel extends RenderLiving {

	public ModelBaseSolo modelMain;
	public ModelBaseDuo modelFATT;
	public IModelCaps fcaps;


	public RenderMultiModel(float pShadowSize) {
		super(null, pShadowSize);
		modelFATT = new ModelBaseDuo(this);
		modelFATT.isModelAlphablend = MMMLib.isModelAlphaBlend;
//		modelFATT.isRendering = true;
		modelMain = new ModelBaseSolo(this);
		modelMain.isModelAlphablend = MMMLib.isModelAlphaBlend;
		modelMain.capsLink = modelFATT;
		mainModel = modelMain;
		setRenderPassModel(modelFATT);
	}

	@Override
	protected ResourceLocation getEntityTexture(Entity var1) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected void preRenderCallback(EntityLivingBase entityliving, float f) {
		Float lscale = (Float)modelMain.getCapsValue(IModelCaps.caps_ScaleFactor);
		if (lscale != null) {
			GL11.glScalef(lscale, lscale, lscale);
		}
	}


}
