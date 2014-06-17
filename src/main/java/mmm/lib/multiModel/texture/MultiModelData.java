package mmm.lib.multiModel.texture;

import io.netty.buffer.ByteBuf;

import java.lang.reflect.Method;

import mmm.lib.multiModel.MultiModelHandler;
import mmm.lib.multiModel.MultiModelManager;
import mmm.lib.multiModel.model.AbstractModelBase;
import mmm.lib.multiModel.model.EntityCapsBase;
import mmm.lib.multiModel.model.IModelCaps;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityAgeable;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import net.minecraftforge.common.IExtendedEntityProperties;

import com.google.common.base.Charsets;

import cpw.mods.fml.relauncher.ReflectionHelper;

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
	
	protected float width;
	protected float height;
	protected float yOffset;
	protected float mountHeight;
	
	public MultiModelContainer model;
	public MultiModelContainer armor;
	
	protected boolean isChanged = false;
	/** 更新情報を送りたくない時に設定 */
	public boolean isSuplessUpdate = false;
	protected int sendCounter;
	
	public IModelCaps modelCaps;


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
		// モデルに関連付けられたデータを読み込む
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
		if (owner instanceof EntityLivingBase) {
			modelCaps = new EntityCapsBase((EntityLivingBase)owner);
		}
		if (owner instanceof IMultiModelEntity) {
//			((IMultiModelEntity)owner).initMultiModel();
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
		if (modelName == null || !modelName.equalsIgnoreCase(pName)) {
			modelName = pName;
			model = MultiModelManager.instance.getMultiModel(pName);
			setChange();
		}
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
		if (pColor != color) setChange();
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
			if (isChanged && (--sendCounter <= 0)) {
				MultiModelHandler.Debug("update %s Entity(%d):%s",
						owner.worldObj.isRemote ? "Client" : "Server",
						owner.getEntityId(), owner.getClass().toString());
				isChanged = false;
				sendCounter = 0;
				// 情報に変更があった。
				if (owner.worldObj.isRemote) {
					onUpdateClient();
				} else {
					onupdateServer();
				}
			}
		}
	}

	public void onUpdateClient() {
		// client
		// サーバーへ変更情報を送信
		MultiModelHandler.sendToServer(this, 0xffff);
	}

	public void onupdateServer() {
		// server
		// クライアントへ変更情報を送信
		MultiModelHandler.sendToClient(this, 0xffff);
	}

	/**
	 * 送信時に書き込まれるデータ
	 * @param pBuf
	 */
	public void sendToServer(ByteBuf pBuf, int pMode) {
		// EntityID
		pBuf.writeInt(owner.getEntityId());
		// Mode
		pBuf.writeShort(pMode);
		if ((pMode & 0x0001) > 0) {
			// color
			pBuf.writeByte(color);
		}
		if ((pMode & 0x0002) > 0) {
			// size
			pBuf.writeFloat(height);
			pBuf.writeFloat(width);
			pBuf.writeFloat(yOffset);
			pBuf.writeFloat(mountHeight);
		}
		if ((pMode & 0x0004) > 0) {
			// visibleFlags
		}
		if ((pMode & 0x0008) > 0) {
			// stabilizers
		}
		if ((pMode & 0x0010) > 0) {
		}
		if ((pMode & 0x0020) > 0) {
		}
		if ((pMode & 0x0040) > 0) {
			// modelName
			if (modelName == null) {
				pBuf.writeByte(0);
			} else {
				pBuf.writeByte(modelName.length());
				pBuf.writeBytes(modelName.getBytes(Charsets.UTF_8));
			}
		}
		if ((pMode & 0x0080) > 0) {
			// armorName
			if (armorName == null) {
				pBuf.writeByte(0);
			} else {
				pBuf.writeByte(armorName.length());
				pBuf.writeBytes(armorName.getBytes(Charsets.UTF_8));
			}
		}
	}

	public void reciveFromClient(ByteBuf pBuf) {
		// EntityIDは既に読まれている
//		PacketBuffer lbuf = new PacketBuffer(pBuf);
		int lmode = pBuf.readShort();
		if (lmode == 0) {
			// クライアントからデータの要求
			MultiModelHandler.sendToClient(this, 0xffff);
			MultiModelHandler.Debug("RequestData(%d)", owner.getEntityId());
		} else {
			int ll;
			if ((lmode & 0x0001) > 0) {
				// color
				color = pBuf.readByte();
			}
			if ((lmode & 0x0002) > 0) {
				height		= pBuf.readFloat();
				width		= pBuf.readFloat();
				yOffset		= pBuf.readFloat();
				mountHeight	= pBuf.readFloat();
			}
			if ((lmode & 0x0004) > 0) {
				// visibleFlags
			}
			if ((lmode & 0x0008) > 0) {
				// stabilizers
			}
			if ((lmode & 0x0010) > 0) {
				
			}
			if ((lmode & 0x0020) > 0) {
				
			}
			if ((lmode & 0x0040) > 0) {
				// modelName
				ll = pBuf.readByte();
				modelName	=  new String(pBuf.readBytes(ll).array(), Charsets.UTF_8);
			}
			if ((lmode & 0x0080) > 0) {
				// armorName
				ll = pBuf.readByte();
				armorName	=  new String(pBuf.readBytes(ll).array(), Charsets.UTF_8);
			}
			setSize(width, height);
			setScale(1.0F);
		}
	}

	public void sendToClient(ByteBuf pBuf, int pMode) {
		// EntityID
		pBuf.writeInt(owner.getEntityId());
		// Mode
		pBuf.writeShort(pMode);
		if ((pMode & 0x0001) > 0) {
			// color
			pBuf.writeByte(color);
		}
		if ((pMode & 0x0002) > 0) {
			// size
			pBuf.writeFloat(height);
			pBuf.writeFloat(width);
			pBuf.writeFloat(yOffset);
			pBuf.writeFloat(mountHeight);
		}
		if ((pMode & 0x0004) > 0) {
			// visibleFlags
		}
		if ((pMode & 0x0008) > 0) {
			// stabilizers
		}
		if ((pMode & 0x0010) > 0) {
		}
		if ((pMode & 0x0020) > 0) {
		}
		if ((pMode & 0x0040) > 0) {
			// modelName
			if (modelName == null) {
				pBuf.writeByte(0);
			} else {
				pBuf.writeByte(modelName.length());
				pBuf.writeBytes(modelName.getBytes(Charsets.UTF_8));
			}
		}
		if ((pMode & 0x0080) > 0) {
			// armorName
			if (armorName == null) {
				pBuf.writeByte(0);
			} else {
				pBuf.writeByte(armorName.length());
				pBuf.writeBytes(armorName.getBytes(Charsets.UTF_8));
			}
		}
	}

	public void reciveFromServer(ByteBuf pBuf) {
		// EntityIDは既に読まれている
		int lmode = pBuf.readShort();
		int ll;
		if ((lmode & 0x0001) > 0) {
			// color
			color = pBuf.readByte();
		}
		if ((lmode & 0x0002) > 0) {
			height		= pBuf.readFloat();
			width		= pBuf.readFloat();
			yOffset		= pBuf.readFloat();
			mountHeight	= pBuf.readFloat();
		}
		if ((lmode & 0x0004) > 0) {
			// visibleFlags
		}
		if ((lmode & 0x0008) > 0) {
			// stabilizers
		}
		if ((lmode & 0x0010) > 0) {
			
		}
		if ((lmode & 0x0020) > 0) {
			
		}
		if ((lmode & 0x0040) > 0) {
			// modelName
			ll = pBuf.readByte();
			modelName	=  new String(pBuf.readBytes(ll).array(), Charsets.UTF_8);
		}
		if ((lmode & 0x0080) > 0) {
			// armorName
			ll = pBuf.readByte();
			armorName	=  new String(pBuf.readBytes(ll).array(), Charsets.UTF_8);
		}
		setSize(width, height);
		setScale(1.0F);
	}

	public void forceChanged(boolean pFlag) {
		isChanged = pFlag;
	}

	public void setChange() {
		isChanged = true;
		sendCounter = 10;
		
		AbstractModelBase lamb = model.getModelClass(getColor())[0];
		height = lamb.getHeight(null);
		width = lamb.getWidth(null);
		yOffset = lamb.getyOffset(null);
		mountHeight = lamb.getMountedYOffset(null);
		setSize(width, height);
		setScale(1.0F);
	}

	public void setSize(float pWidth, float pHeight) {
		if (owner instanceof Entity) {
			// 対象がprotectなので無理やり
			try {
				Method lmethod = ReflectionHelper.findMethod(Entity.class, owner, new String[] {"setSize", "func_70105_a"}, float.class, float.class);
				lmethod.invoke(owner, pWidth, pHeight);
			} catch(Exception e) {
				e.printStackTrace();
			}
			
		}
	}

	public void setScale(float pScale) {
		if (owner instanceof EntityAgeable) {
			// 対象がprotectなので無理やり
			try {
				MultiModelHandler.Debug("popSizeB:%f, %f - %f, %f, (%f, %f, %f - %f %f, %f), %s - %s",
						owner.width, owner.height, owner.yOffset, owner.ySize,
						owner.boundingBox.minX, owner.boundingBox.minY, owner.boundingBox.minZ,
						owner.boundingBox.maxX, owner.boundingBox.maxY, owner.boundingBox.maxZ,
						owner.myEntitySize.toString(),
						owner.worldObj.isRemote ? "client" : "server");

				Method lmethod = ReflectionHelper.findMethod(EntityAgeable.class, (EntityAgeable)owner, new String[] {"setScale", "func_98055_j"}, float.class);
				lmethod.invoke(owner, pScale);
				MultiModelHandler.Debug("popSizeA:%f, %f - %f, %f, (%f, %f, %f - %f %f, %f), %s - %s",
						owner.width, owner.height, owner.yOffset, owner.ySize,
						owner.boundingBox.minX, owner.boundingBox.minY, owner.boundingBox.minZ,
						owner.boundingBox.maxX, owner.boundingBox.maxY, owner.boundingBox.maxZ,
						owner.myEntitySize.toString(),
						owner.worldObj.isRemote ? "client" : "server");
			} catch(Exception e) {
				e.printStackTrace();
			}
		}
	}

}
