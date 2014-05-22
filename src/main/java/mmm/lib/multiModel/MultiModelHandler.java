package mmm.lib.multiModel;

import java.util.HashMap;
import java.util.Map;

import mmm.lib.multiModel.texture.MultiModelEntity;
import net.minecraft.entity.Entity;
import net.minecraftforge.event.entity.EntityEvent.EntityConstructing;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;

public class MultiModelHandler {

	public Map<Class<? extends Entity>, Class<MultiModelEntity>> targets = new HashMap<Class<? extends Entity>, Class<MultiModelEntity>>();
	public Map<Class<? extends Entity>, String> defaultModels = new HashMap<Class<? extends Entity>, String>();
	
	/** Entityに関連付けられたデータのリスト */
	public Map<Entity, MultiModelEntity> datas = new HashMap<Entity, MultiModelEntity>();


	public void init() {
		// TODO イベントハンドラに登録
	}

	/**
	 * MultiModel表示対象を追加する
	 * @param pEntityClass
	 * @param pEntityData
	 */
	public void registerEntityClass(Class<? extends Entity> pEntityClass,
			Class<MultiModelEntity> pEntityData, String pModelName) {
		targets.put(pEntityClass, pEntityData);
		defaultModels.put(pEntityClass, pModelName);
	}

	/**
	 * Entity生成時に対象Classならマルチモデル保存クラスを生成。
	 * @param pEvent
	 */
	@SubscribeEvent
	public void onEntityConstructing(EntityConstructing pEvent) {
		Entity lentity = pEvent.entity;
		if (targets.containsKey(lentity.getClass())) {
			try {
				MultiModelEntity ledata = targets.get(lentity).newInstance();
				ledata.setModelFromName(defaultModels.get(lentity.getClass()));
				lentity.registerExtendedProperties("MultiModel", ledata);
				datas.put(lentity, ledata);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

}
