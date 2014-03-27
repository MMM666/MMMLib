package mmm.littleMaidMob;

import mmm.lib.ProxyCommon;
import cpw.mods.fml.client.registry.RenderingRegistry;

public class ProxyClient extends ProxyCommon {

	@Override
	public void init() {
		// レンダラの登録
		RenderingRegistry.registerEntityRenderingHandler(EntityLittleMaidMob.class, new RenderLittleMaidMob(0.5F));
	}

}
