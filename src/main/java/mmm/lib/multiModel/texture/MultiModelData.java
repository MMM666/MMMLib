package mmm.lib.multiModel.texture;

import io.netty.buffer.ByteBuf;
import mmm.lib.multiModel.MultiModelHandler;
import mmm.lib.multiModel.model.AbstractModelBase;
import mmm.lib.multiModel.model.IModelCaps;
import net.minecraft.entity.Entity;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import net.minecraftforge.common.IExtendedEntityProperties;

/**
 * Entityに関連付けられるデータ保持用のクラス
 * Containerと統合する?
 *
 */
public class MultiModelData implements IExtendedEntityProperties {

	/** 処理対象となるEntity */
	protected Entity owner;
	
	protected String modelName;
	protected String armorName;
	protected int color;
	protected int modelPartsVisible;
	protected int armorPartsVisible;
	
	public MultiModelContainer model;
	public MultiModelContainer armor;
	
	protected boolean isChanged = false;
	/** 更新情報を送りたくない時に設定 */
	public boolean isSuplessUpdate = false;


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
		if (compound.hasKey("MultiModel")) {
			NBTTagCompound lnbt = compound.getCompoundTag("MultiModel");
			color = lnbt.getInteger("color");
			modelName = lnbt.getString("modelName");
			modelPartsVisible = lnbt.getInteger("modelPartsVisible");
			armorName = lnbt.getString("armorName");
			armorPartsVisible = lnbt.getInteger("armorPartsVisible");
		}
	}

	@Override
	public void init(Entity entity, World world) {
		// 初期化時に実行される
		owner = entity;
		if (owner instanceof IMultiModelEntity) {
			((IMultiModelEntity)owner).initMultiModel();
		}
	}


	public Entity getOwner() {
		return owner;
	}

	/**
	 * マルチモデルを名前から検索し設定する。
	 * @param pName
	 * @return
	 */
	public boolean setModelFromName(String pName) {
		modelName = pName;
		model = MultiModelManager.instance.getMultiModel(pName);
		isChanged = true;
		return model != null;
	}

	/**
	 * 選択中のマルチモデル名称
	 * @return
	 */
	public String getModelName() {
		return modelName;
	}

	/**
	 * 色を選択
	 * @param pColor
	 * @return 指定色が有ればtrue, 無ければfalse
	 */
	public boolean setColor(int pColor) {
		isChanged = (pColor != color);
		color = pColor;
		return true;
	}

	public int getColor() {
		return color;
	}

	public float getModelWidth(IModelCaps pModelCaps) {
		AbstractModelBase lamb = model.getModelClass(color)[0];
		return lamb.getWidth(pModelCaps);
	}

	public float getModelHeight(IModelCaps pModelCaps) {
		AbstractModelBase lamb = model.getModelClass(color)[0];
		return lamb.getWidth(pModelCaps);
	}

	/**
	 * 毎時処理
	 */
	public void onUpdate() {
		if (owner != null && !isSuplessUpdate) {
			if (isChanged) {
				isChanged = false;
				// 情報に変更があった。
				if (owner.worldObj.isRemote) {
					// client
					// サーバーへ変更情報を送信
					MultiModelHandler.sendToServer(this);
				} else {
					// server
					// クライアントへ変更情報を送信
					MultiModelHandler.sendToClient(this);
				}
			}
		}
	}

	/**
	 * 送信時に書き込まれるデータ
	 * @param pBuf
	 */
	public void sendToServer(ByteBuf pBuf) {
		// EntityID
		pBuf.writeInt(owner.getEntityId());
		// modelName
		// color
		pBuf.writeByte(color);
		// visibleFlags
		// stabilizers
	}

	public void reciveFromClient(ByteBuf pBuf) {
		// EntityIDは既に読まれている
		// modelName
		// color
		color = pBuf.readByte();
		// visibleFlags
		// stabilizers
	}

	public void sendToClient(ByteBuf pBuf) {
		// EntityID
		pBuf.writeInt(owner.getEntityId());
		// modelName
		// color
		pBuf.writeByte(color);
		// visibleFlags
		// stabilizers
	}

	public void reciveFromServer(ByteBuf pBuf) {
		// EntityIDは既に読まれている
		// modelName
		// color
		color = pBuf.readByte();
		// visibleFlags
		// stabilizers
	}

}
