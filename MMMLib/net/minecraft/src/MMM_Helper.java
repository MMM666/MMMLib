package net.minecraft.src;

import static net.minecraft.src.mod_MMM_MMMLib.Debug;

import java.io.File;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MMM_Helper {

	public static final boolean isClient;
	public static final Package fpackage;
	public static final String packegeBase;
	public static final boolean isForge = ModLoader.isModLoaded("Forge");
	public static final Minecraft mc;
	public static Method methGetSmeltingResultForge = null;
	public static Class entityRegistry = null;
	public static Method registerModEntity = null;
	protected static final Map<Class, Class>replaceEntitys = new HashMap<Class, Class>();
	protected static Map<String, Integer> entityIDList = new HashMap<String, Integer>();
	
	static {
		fpackage = ModLoader.class.getPackage();
		packegeBase = fpackage == null ? "" : fpackage.getName().concat(".");

		Minecraft lm = null;
		try {
			lm = ModLoader.getMinecraftInstance();
		} catch (Exception e) {
//			e.printStackTrace();
		} catch (Error e) {
//			e.printStackTrace();
		}
		mc = lm;
		isClient = mc != null;
		if (isForge) {
			Debug("Get Forge Class.");
			try {
				methGetSmeltingResultForge = FurnaceRecipes.class.getMethod("getExperience", ItemStack.class);
			} catch (Exception e) {
				e.printStackTrace();
			}
			try {
				entityRegistry = getNameOfClass("cpw.mods.fml.common.registry.EntityRegistry");
				registerModEntity = entityRegistry.getMethod("registerModEntity",
						Class.class, String.class, int.class, Object.class, int.class, int.class, boolean.class);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * ���݂̎��s�������[�J�����ǂ����𔻒肷��B
	 */
	public static boolean isLocalPlay() {
		return isClient && mc.isIntegratedServerRunning();
	}

	/**
	 * �}���`�Ή��p�B
	 * ItemStack�ɏ��X�V���s���ƁA�T�[�o�[���Ƃ̍��ق���Slot�̃A�b�v�f�[�g���s����B
	 * ���̍ہAUsingItem�̍X�V�������s���Ȃ����߈Ⴄ�A�C�e���Ɏ��ւ���ꂽ�Ɣ��肳���B
	 * �����ł͔�r�p�Ɏg����X�^�b�N���X�g�������I�ɏ������鎖�ɂ��Ή������B
	 */
	public static void updateCheckinghSlot(Entity pEntity, ItemStack pItemstack) {
		if (pEntity instanceof EntityPlayerMP) {
			// �T�[�o�[���ł̂ݏ���
			EntityPlayerMP lep = (EntityPlayerMP)pEntity;
			Container lctr = lep.openContainer;
			for (int li = 0; li < lctr.inventorySlots.size(); li++) {
				ItemStack lis = ((Slot)lctr.getSlot(li)).getStack(); 
				if (lis == pItemstack) {
					lctr.inventoryItemStacks.set(li, pItemstack.copy());
					break;
				}
			}
		}
	}
	
	/**
	 * Forge�p�N���X�l���B
	 */
	public static Class getForgeClass(BaseMod pMod, String pName) {
		if (isForge) {
			pName = pName.concat("_Forge");
		}
		return getNameOfClass(pName);
	}

	/**
	 * ���O����N���X���l������
	 */
	public static Class getNameOfClass(String pName) {
		if (fpackage != null) {
			pName = fpackage.getName() + "." + pName;
		}
		Class lclass = null;
		try {
			lclass = Class.forName(pName);
		} catch (Exception e) {
			mod_MMM_MMMLib.Debug("Class:%s is not found.", pName);
		}
		
		return lclass;
	}

	/**
	 * ���M�p�f�[�^�̃Z�b�g
	 */
	public static void setValue(byte[] pData, int pIndex, int pVal, int pSize) {
		for (int li = 0; li < pSize; li++) {
			pData[pIndex++] = (byte)(pVal & 0xff);
			pVal = pVal >>> 8;
		}
	}
	
	public static void setInt(byte[] pData, int pIndex, int pVal) {
		pData[pIndex + 3]	= (byte)(pVal & 0xff);
		pData[pIndex + 2]	= (byte)((pVal >>> 8) & 0xff);
		pData[pIndex + 1]	= (byte)((pVal >>> 16) & 0xff);
		pData[pIndex + 0]	= (byte)((pVal >>> 24) & 0xff);
	}
	
	public static int getInt(byte[] pData, int pIndex) {
		return (pData[pIndex + 3] & 0xff) | ((pData[pIndex + 2] & 0xff) << 8) | ((pData[pIndex + 1] & 0xff) << 16) | ((pData[pIndex + 0] & 0xff) << 24);
	}

	public static void setFloat(byte[] pData, int pIndex, float pVal) {
		setInt(pData, pIndex, Float.floatToIntBits(pVal));
	}

	public static float getFloat(byte[] pData, int pIndex) {
		return Float.intBitsToFloat(getInt(pData, pIndex));
	}

	public static void setShort(byte[] pData, int pIndex, int pVal) {
		pData[pIndex++]	= (byte)(pVal & 0xff);
		pData[pIndex]	= (byte)((pVal >>> 8) & 0xff);
	}

	public static short getShort(byte[] pData, int pIndex) {
		return (short)((pData[pIndex] & 0xff) | ((pData[pIndex + 1] & 0xff) << 8));
	}

	public static String getStr(byte[] pData, int pIndex, int pLen) {
		String ls = new String(pData, pIndex, pLen);
		return ls;
	}
	public static String getStr(byte[] pData, int pIndex) {
		return getStr(pData, pIndex, pData.length - pIndex);
	}

	public static void setStr(byte[] pData, int pIndex, String pVal) {
		byte[] lb = pVal.getBytes();
		for (int li = pIndex; li < pData.length; li++) {
			pData[li] = lb[li - pIndex];
		}
	}

	// �󋵔��f�v�֐��Q
	protected static boolean canBlockBeSeen(Entity pEntity, int x, int y, int z, boolean toTop, boolean do1, boolean do2) {
		// �u���b�N�̉�����
		Vec3 vec3d = Vec3.createVectorHelper(pEntity.posX, pEntity.posY + pEntity.getEyeHeight(), pEntity.posZ);
		Vec3 vec3d1 = Vec3.createVectorHelper((double)x + 0.5D, (double)y + (toTop ? 0.9D : 0.5D), (double)z + 0.5D);
		
		MovingObjectPosition movingobjectposition = pEntity.worldObj.rayTraceBlocks_do_do(vec3d, vec3d1, do1, do2);
		if (movingobjectposition == null) {
			return false;
		}
		if (movingobjectposition.typeOfHit == EnumMovingObjectType.TILE) {
			if (movingobjectposition.blockX == MathHelper.floor_double(vec3d1.xCoord) && 
				movingobjectposition.blockY == MathHelper.floor_double(vec3d1.yCoord) &&
				movingobjectposition.blockZ == MathHelper.floor_double(vec3d1.zCoord)) {
				return true;
			}
		}
		return false;
	}

	public static boolean setPathToTile(EntityLiving pEntity, TileEntity pTarget, boolean flag) {
		// Tile�܂ł̃p�X�����
		PathNavigate lpn = pEntity.getNavigator();
		float lspeed = 1.0F;
		// �����ɍ��킹�ċ����𒲐�
		int i = (pTarget.yCoord == MathHelper.floor_double(pEntity.posY) && flag) ? 2 : 1;
		switch (pEntity.worldObj.getBlockMetadata(pTarget.xCoord, pTarget.yCoord, pTarget.zCoord)) {
		case 3:
			return lpn.tryMoveToXYZ(pTarget.xCoord, pTarget.yCoord, pTarget.zCoord + i, lspeed);
		case 2:
			return lpn.tryMoveToXYZ(pTarget.xCoord, pTarget.yCoord, pTarget.zCoord - i, lspeed);
		case 5:
			return lpn.tryMoveToXYZ(pTarget.xCoord + 1, pTarget.yCoord, pTarget.zCoord, lspeed);
		case 4:
			return lpn.tryMoveToXYZ(pTarget.xCoord - i, pTarget.yCoord, pTarget.zCoord, lspeed);
		default:
			return lpn.tryMoveToXYZ(pTarget.xCoord, pTarget.yCoord, pTarget.zCoord, lspeed);
		}
	}

	/**
	 * Modloader�����ŋ󂢂Ă���EntityID��Ԃ��B
	 * �L���Ȓl���l���ł��Ȃ����-1��Ԃ��B
	 */
	private static int getNextEntityID(boolean isLiving) {
		if (isLiving) {
			// �����p
			for (int li = 1; li < 256; li++) {
				if (EntityList.getClassFromID(li) == null) {
					return li;
				}
			}
		} else {
			// ���p
			for (int li = mod_MMM_MMMLib.cfg_startVehicleEntityID; li < mod_MMM_MMMLib.cfg_startVehicleEntityID + 2048; li++) {
				if (EntityList.getClassFromID(li) == null) {
					return li;
				}
			}
		}
		return -1;
	}

	/**
	 * Entity��o�^����B
	 * RML�AForge���Ή��B
	 * @param entityclass
	 * @param entityName
	 * @param defaultId
	 * 0 : �I�[�g�A�T�C��
	 * @param mod
	 * @param uniqueModeName
	 * @param trackingRange
	 * @param updateFrequency
	 * @param sendVelocityUpdate
	 */
	public static int registerEntity(
			Class<? extends Entity> entityclass, String entityName, int defaultId,
			BaseMod mod, int trackingRange, int updateFrequency, boolean sendVelocityUpdate,
			int pEggColor1, int pEggColor2) {
		int lid = 0;
		lid = getModEntityID(mod.getName());
		if (isForge) {
			Debug("RegisterEntity Forge.");
			try {
				Method lmethod;
				// EntityID�̊l��
				lmethod = entityRegistry.getMethod("findGlobalUniqueEntityId");
				defaultId = (Integer)lmethod.invoke(null);
				
				if (pEggColor1 == 0 && pEggColor2 == 0) {
					lmethod = entityRegistry.getMethod("registerGlobalEntityID",
							Class.class, String.class, int.class);
					lmethod.invoke(null, entityclass, entityName, defaultId);
				} else {
					lmethod = entityRegistry.getMethod("registerGlobalEntityID",
							Class.class, String.class, int.class, int.class, int.class);
					lmethod.invoke(null, entityclass, entityName, defaultId, pEggColor1, pEggColor2);
				}
				// EntityList�ւ̓o�^�͓K���Ȑ����ł悢�B
				registerModEntity.invoke(
						null, entityclass, entityName, lid,
						mod, trackingRange, updateFrequency, sendVelocityUpdate);
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else {
			Debug("RegisterEntity Modloader.");
			// EntityList�ւ̓o�^��
			if (defaultId == 0) {
				defaultId = getNextEntityID(entityclass.isAssignableFrom(EntityLivingBase.class));
			}
			if (pEggColor1 == 0 && pEggColor2 == 0) {
				ModLoader.registerEntityID(entityclass, entityName, defaultId);
			} else {
				ModLoader.registerEntityID(entityclass, entityName, defaultId, pEggColor1, pEggColor2);
			}
			ModLoader.addEntityTracker(mod, entityclass, defaultId, trackingRange, updateFrequency, sendVelocityUpdate);
		}
		Debug("RegisterEntity ID:%d / %s-%d : %s", defaultId, mod.getName(), lid, entityName);
		return defaultId;
	}
	public static int registerEntity(
			Class<? extends Entity> entityclass, String entityName, int defaultId,
			BaseMod mod, int trackingRange, int updateFrequency, boolean sendVelocityUpdate) {
		return registerEntity(entityclass, entityName, defaultId, mod, trackingRange, updateFrequency, sendVelocityUpdate, 0, 0);
	}

	private static int getModEntityID(String uniqueModeName) {
		int li = 0;
		if (entityIDList.containsKey(uniqueModeName)) {
			li = entityIDList.get(uniqueModeName);
		}
		entityIDList.put(uniqueModeName, li + 1);
		return li;
	}

	/**
	 * Entity��Ԃ��B
	 */
	public static Entity getEntity(byte[] pData, int pIndex, World pWorld) {
		return pWorld.getEntityByID(MMM_Helper.getInt(pData, pIndex));
	}

	/**
	 * �ϐ��uavatar�v����l�����o���߂�l�Ƃ��ĕԂ��B
	 * avatar�����݂��Ȃ��ꍇ�͌��̒l��Ԃ��B
	 * avatar��EntityLiving�݊��B
	 */
	public static Entity getAvatarEntity(Entity pEntity){
		// littleMaid�p�R�[�h��������
		if (pEntity == null) return null;
		try {
			// �ˎ�̏���EntityLittleMaidAvatar����EntityLittleMaid�֒u��������
			Field field = pEntity.getClass().getField("avatar");
			pEntity = (EntityLivingBase)field.get(pEntity);
		} catch (NoSuchFieldException e) {
		} catch (Exception e) {
			e.printStackTrace();
		} catch (Error e) {
			e.printStackTrace();
		}
		// �����܂�
		return pEntity;
	}

	/**
	 * �ϐ��umaidAvatar�v����l�����o���߂�l�Ƃ��ĕԂ��B
	 * maidAvatar�����݂��Ȃ��ꍇ�͌��̒l��Ԃ��B
	 * maidAvatar��EntityPlayer�݊��B
	 */
	public static Entity getAvatarPlayer(Entity entity) {
		// ���C�h����`�F�b�N
		try {
			Field field = entity.getClass().getField("maidAvatar");
			entity = (Entity)field.get(entity);
		}
		catch (NoSuchFieldException e) {
		}
		catch (Exception e) {
		}
		return entity;
	}

	/**
	 * �v���[���̃C���x���g������A�C�e�������炷
	 */
	protected static ItemStack decPlayerInventory(EntityPlayer par1EntityPlayer, int par2Index, int par3DecCount) {
		if (par1EntityPlayer == null) {
			return null;
		}
		
		if (par2Index == -1) {
			par2Index = par1EntityPlayer.inventory.currentItem;
		}
		ItemStack itemstack1 = par1EntityPlayer.inventory.getStackInSlot(par2Index);
		if (itemstack1 == null) {
			return null;
		}
		
		if (!par1EntityPlayer.capabilities.isCreativeMode) {
			// �N���G�C�e�B�u���ƌ���Ȃ�
			itemstack1.stackSize -= par3DecCount;
		}
		
		if (itemstack1.getItem() instanceof ItemPotion) {
			if(itemstack1.stackSize <= 0) {
				par1EntityPlayer.inventory.setInventorySlotContents(par1EntityPlayer.inventory.currentItem, new ItemStack(Item.glassBottle, par3DecCount));
				return null;
			} else {
				par1EntityPlayer.inventory.addItemStackToInventory(new ItemStack(Item.glassBottle, par3DecCount));
			}
		} else {
			if (itemstack1.stackSize <= 0) {
				par1EntityPlayer.inventory.setInventorySlotContents(par2Index, null);
				return null;
			}
		}
		
		return itemstack1;
	}

	protected static float convRevision(String pRev) {
		Pattern lp = Pattern.compile("(\\d+)(\\w*)");
		Matcher lm = lp.matcher(pRev);
		float lf = 0;
		if (lm.find()) {
			lf = Integer.valueOf(lm.group(1));
			if (!lm.group(2).isEmpty()) {
				lf += (float)(lm.group(2).charAt(0) - 96) * 0.01;
			}
		}
		return lf;
	}
	protected static float convRevision() {
		return convRevision(mod_MMM_MMMLib.Revision);
	}

	/**
	 * �w�肳�ꂽ���r�W���������Â���Η�O�𓊂��ăX�g�b�v
	 */
	public static void checkRevision(String pRev) {
		if (convRevision() < convRevision(pRev)) {
			// �K���o�[�W�����ł͂Ȃ��̂ŃX�g�b�v
			ModLoader.getLogger().warning("you must check MMMLib revision.");
			throw new RuntimeException("The revision of MMMLib is old.");
		}
	}

	/**
	 * EntityList�ɓo�^����Ă�����Entity��u��������B
	 */
	public static void replaceEntityList(Class pSrcClass, Class pDestClass) {
		// EntityList�o�^����u������
		// �Â�Entity�ł��X�|�[���ł���悤�Ɉꕔ�̕��͓�d�o�^
		try {
			// stringToClassMapping
			Map lmap;
			int lint = 0;
			String ls = "";
			lmap = (Map)ModLoader.getPrivateValue(EntityList.class, null, 0);
			for (Entry<String, Class> le : ((Map<String, Class>)lmap).entrySet()) {
				if (le.getValue() == pSrcClass) {
					le.setValue(pDestClass);
				}
			}
			// classToStringMapping
			lmap = (Map)ModLoader.getPrivateValue(EntityList.class, null, 1);
			if (lmap.containsKey(pSrcClass)) {
				ls = (String)lmap.get(pSrcClass);
//				lmap.remove(pSrcClass);
				lmap.put(pDestClass, ls);
			}
			// IDtoClassMapping
			lmap = (Map)ModLoader.getPrivateValue(EntityList.class, null, 2);
			for (Entry<Integer, Class> le : ((Map<Integer, Class>)lmap).entrySet()) {
				if (le.getValue() == pSrcClass) {
					le.setValue(pDestClass);
				}
			}
			// classToIDMapping
			lmap = (Map)ModLoader.getPrivateValue(EntityList.class, null, 3);
			if (lmap.containsKey(pSrcClass)) {
				lint = (Integer)lmap.get(pSrcClass);
//				lmap.remove(pSrcClass);
				lmap.put(pDestClass, lint);
			}
			replaceEntitys.put(pSrcClass, pDestClass);
			Debug("Replace %s -> %s(EntityListID: %d, EntityListString: %s)", pSrcClass.getSimpleName(), pDestClass.getSimpleName(), lint, ls);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private static void replaceCreatureList(List<SpawnListEntry> pMobs) {
		if (pMobs == null) return;
		for (Entry<Class, Class> le : replaceEntitys.entrySet()) {
			for (int j = 0; j < pMobs.size(); j++) {
				if (pMobs.get(j).entityClass == le.getKey()) {
					pMobs.get(j).entityClass = le.getValue();
					Debug("ReplaceCreatureList: %s -> %s", le.getKey().getSimpleName(), le.getValue().getSimpleName());
				}
			}
		}
	}

	/**
	 * �o�C�I�[���̐ݒ�Entity��u��������ꂽEntity�֒u��������B
	 * ��{�I��MMMLib�ȊO����͌Ă΂�Ȃ��B
	 */
	protected static void replaceBaiomeSpawn() {
		// �o�C�I�[���̔����������̂��Ƃ�
		if (replaceEntitys.isEmpty()) return;
		for (int i = 0; i < BiomeGenBase.biomeList.length; i++) {
			if (BiomeGenBase.biomeList[i] == null) continue;
			List<SpawnListEntry> mobs;
			Debug("ReplaceBaiomeSpawn:%s", BiomeGenBase.biomeList[i].biomeName);
			Debug("[Creature]");
			replaceCreatureList(BiomeGenBase.biomeList[i].spawnableCreatureList);
			Debug("[WaterCreature]");
			replaceCreatureList(BiomeGenBase.biomeList[i].spawnableWaterCreatureList);
			Debug("[CaveCreature]");
			replaceCreatureList(BiomeGenBase.biomeList[i].spawnableCaveCreatureList);
			Debug("[Monster]");
			replaceCreatureList(BiomeGenBase.biomeList[i].spawnableMonsterList);
		}
	}

	/**
	 * �����̐�ɂ���ŏ���Entity��Ԃ�
	 * @param pEntity
	 * ���_
	 * @param pRange
	 * �����̗L������
	 * @param pDelta
	 * �����␳
	 * @param pExpand
	 * ���m�̈�̊g��͈�
	 * @return
	 */
	public static Entity getRayTraceEntity(EntityLivingBase pEntity, double pRange, float pDelta, float pExpand) {
		Vec3 lvpos = pEntity.worldObj.getWorldVec3Pool().getVecFromPool(
				pEntity.posX, pEntity.posY + pEntity.getEyeHeight(), pEntity.posZ);
//		Vec3 lvpos = pEntity.getPosition(pDelta).addVector(0D, pEntity.getEyeHeight(), 0D);
		Vec3 lvlook = pEntity.getLook(pDelta);
		Vec3 lvview = lvpos.addVector(lvlook.xCoord * pRange, lvlook.yCoord * pRange, lvlook.zCoord * pRange);
		Entity ltarget = null;
		List llist = pEntity.worldObj.getEntitiesWithinAABBExcludingEntity(pEntity, pEntity.boundingBox.addCoord(lvlook.xCoord * pRange, lvlook.yCoord * pRange, lvlook.zCoord * pRange).expand((double)pExpand, (double)pExpand, (double)pExpand));
		double ltdistance = pRange * pRange;
		
		for (int var13 = 0; var13 < llist.size(); ++var13) {
			Entity lentity = (Entity)llist.get(var13);
			
			if (lentity.canBeCollidedWith()) {
				float lexpand = lentity.getCollisionBorderSize() + 0.3F;
				AxisAlignedBB laabb = lentity.boundingBox.expand((double)lexpand, (double)lexpand, (double)lexpand);
				MovingObjectPosition lmop = laabb.calculateIntercept(lvpos, lvview);
				
				if (laabb.isVecInside(lvpos)) {
					if (0.0D < ltdistance || ltdistance == 0.0D) {
						ltarget = lentity;
						ltdistance = 0.0D;
					}
				} else if (lmop != null) {
					double ldis = lvpos.squareDistanceTo(lmop.hitVec);
					
					if (ldis < ltdistance || ltdistance == 0.0D) {
						ltarget = lentity;
						ltdistance = ldis;
					}
				}
			}
		}
		return ltarget;
	}


	// Forge�΍�

	/**
	 * Forge�΍��p�̃��\�b�h
	 */
	public static ItemStack getSmeltingResult(ItemStack pItemstack) {
		if (methGetSmeltingResultForge != null) {
			try {
				return (ItemStack)methGetSmeltingResultForge.invoke(FurnaceRecipes.smelting(), pItemstack);
			}catch (Exception e) {
			}
		}
		return FurnaceRecipes.smelting().getSmeltingResult(pItemstack.itemID);
	}

	/**
	 * �A�C�e���ɒǉ����ʂ��݂邩�𔻒肷��B
	 * Forge�΍�B
	 * @param pItemStack
	 * @return
	 */
	public static boolean hasEffect(ItemStack pItemStack) {
		// �}�WClientSIDE�Ƃ����߂Ăق����B
		if (pItemStack != null) {
			Item litem = pItemStack.getItem();
			if (litem instanceof ItemPotion) {
				List llist = ((ItemPotion)litem).getEffects(pItemStack);
				return llist != null && !llist.isEmpty();
			}
		}
		return false;
	}

	/**
	 * Block�̃C���X�^���X��u��������B
	 * static final�̕ϐ��ɑ΂��čs���̂�Forge�ł͖����B
	 * @param pOriginal
	 * @param pReplace
	 * @return
	 */
	public static boolean replaceBlock(Block pOriginal, Block pReplace) {
		if (isForge) {
			return false;
		}
		try {
			// Block��static final���̒u����
			Field[] lfield = Block.class.getDeclaredFields();
			for (int li = 0; li < lfield.length; li++) {
				if (!Modifier.isStatic(lfield[li].getModifiers())) {
					// static�ȊO�͑ΏۊO
					continue;
				}
				
				Object lobject = lfield[li].get(null);
				if (lobject == pOriginal) {
					ModLoader.setPrivateValue(Block.class, null, li, pReplace);
					return true;
				}
			}
		}
		catch(Exception exception) {
		}
		return false;
	}

	/**
	 * 16�i���̕������Int�֕ϊ�����B
	 * 0xffffffff�΍�B
	 * @param pValue
	 * @return
	 */
	public static int getHexToInt(String pValue) {
		String ls = "00000000".concat(pValue);
		int llen = ls.length();
		int li = Integer.parseInt(ls.substring(llen - 4, llen), 16);
		int lj = Integer.parseInt(ls.substring(llen - 8, llen - 4), 16);
		return (lj << 16) | li;
	}

	/**
	 *  �A�C�e���ɐݒ肳�ꂽ�U���͂�����
	 * @param pItemStack
	 * @return
	 */
	public static double getAttackVSEntity(ItemStack pItemStack) {
		AttributeModifier lam = (AttributeModifier)pItemStack.func_111283_C().get(SharedMonsterAttributes.field_111264_e.func_111108_a());
		return lam == null ? 0 : lam.func_111164_d();
	}

}
