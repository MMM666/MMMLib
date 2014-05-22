package mmm.lib.multiModel.texture;

import net.minecraft.entity.Entity;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import net.minecraftforge.common.IExtendedEntityProperties;

/**
 * Entityに関連付けられるデータ保持用のクラス
 * Containerと統合する?
 *
 */
public class MultiModelEntity implements IExtendedEntityProperties {
	
	public String modelName;
	public String armorName;
	public int color;
	public int modelPartsVisible;
	public int armorPartsVisible;
	
	public MultiModelContainer model;
	public MultiModelContainer armor;


	@Override
	public void saveNBTData(NBTTagCompound compound) {
		// モデルに関連付けられたデータを保存する
		NBTTagCompound lnbt = new NBTTagCompound();
		lnbt.setInteger("color", color);
		lnbt.setString("modelName", modelName);
		lnbt.setInteger("modelPartsVisible", modelPartsVisible);
		lnbt.setString("armorName", modelName);
		lnbt.setInteger("armorPartsVisible", armorPartsVisible);
		compound.setTag("MultiModel", lnbt);
	}

	@Override
	public void loadNBTData(NBTTagCompound compound) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void init(Entity entity, World world) {
		// 初期化時に実行される
		
	}

	/**
	 * マルチモデルを名前から検索し設定する。
	 * @param pName
	 * @return
	 */
	public boolean setModelFromName(String pName) {
		model = MultiModelManager.instance.getMultiModel(pName);
		return model != null;
	}

}
