package net.minecraft.src;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public abstract class MMM_GuiMobSelect extends GuiScreen {

	public Map<String, Entity> entityMap;
	public static Map<Class, String> entityMapClass = new HashMap<Class, String>();
	public static List<String> exclusionList = new ArrayList<String>();
	
	protected String screenTitle;
	protected GuiSlot selectPanel;



	public MMM_GuiMobSelect(World pWorld) {
		entityMap = new TreeMap<String, Entity>();
		initEntitys(pWorld, true);
	}

	public MMM_GuiMobSelect(World pWorld, Map<String, Entity> pMap) {
		entityMap = pMap;
		initEntitys(pWorld, false);
	}


	public void initEntitys(World world, boolean pForce) {
		// 表示用EntityListの初期化
		if (entityMapClass.isEmpty()) {
			try {
				Map lmap = (Map)ModLoader.getPrivateValue(EntityList.class, null, 1);
				entityMapClass.putAll(lmap);
			}
			catch (Exception e) {
				mod_MMM_MMMLib.Debug("EntityClassMap copy failed.");
			}
		}
		
		if (entityMap == null) return;
		if (!pForce && !entityMap.isEmpty()) return;
		
		for (Map.Entry<Class, String> le : entityMapClass.entrySet()) {
			int li = 0;
			Entity lentity = null;
			try {
				// 表示用のEntityを作る
				do {
					lentity = (EntityLiving)EntityList.createEntityByName(le.getValue(), world);
				} while (lentity != null && checkEntity(le.getValue(), lentity, li++));
			} catch (Exception e) {
				mod_MMM_MMMLib.Debug("Entity [" + le.getValue() + "] can't created.");
			}
		}
	}

	/**
	 * 渡されたEntityのチェック及び加工。
	 * trueを返すと同じクラスのエンティティを再度渡してくる、そのときpIndexはカウントアップされる
	 */
	protected boolean checkEntity(String pName, Entity pEntity, int pIndex) {
		entityMap.put(pName, pEntity);
		return false;
	}

	@Override
	public void initGui() {
		selectPanel = new MMM_GuiSlotMobSelect(mc, this);
		selectPanel.registerScrollButtons(buttonList, 3, 4);
	}

	@Override
	public void drawScreen(int px, int py, float pf) {
		drawDefaultBackground();
		selectPanel.drawScreen(px, py, pf);
		drawCenteredString(fontRenderer, StatCollector.translateToLocal(screenTitle), width / 2, 20, 0xffffff);
		super.drawScreen(px, py, pf);
	}

	/**
	 *  スロットがクリックされた
	 */
	public abstract void clickSlot(int pIndex, boolean pDoubleClick, String pName, EntityLiving pEntity);

	/**
	 *  スロットの描画
	 */
	public abstract void drawSlot(int pSlotindex, int pX, int pY, int pDrawheight, Tessellator pTessellator, String pName, Entity pEntity);
	
}
