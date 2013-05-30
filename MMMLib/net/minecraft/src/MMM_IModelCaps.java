package net.minecraft.src;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * モデル共通化用インターフェース。
 * 形式指定の値読み出しはModelCapsHelperを使うこと。
 * TODO:次バージョンで色々消すこと。
 */
public interface MMM_IModelCaps {

	/*
	 * 機能名称群、取り敢えず書いてあるけど別に被らなければ何でも良し。
	 * 一応予約値ってことで。
	 */
	// ModelBase
	public static final int caps_onGround			= 0x0001;
	public static final int caps_isRiding			= 0x0002;
	public static final int caps_isChild			= 0x0003;
	// ModelBiped
	public static final int caps_heldItemLeft		= 0x0010;
	public static final int caps_heldItemRight		= 0x0011;
	public static final int caps_heldItems			= 0x0012;
	public static final int caps_isSneak			= 0x0013;
	public static final int caps_aimedBow			= 0x0014;
	// EntityCaps
	public static final int caps_Entity					= 0x0020;
	public static final int caps_health					= 0x0021;
	public static final int caps_ticksExisted			= 0x0022;
	public static final int caps_currentEquippedItem	= 0x0023;
	public static final int caps_currentArmor			= 0x0024;
	// littleMaid
	public static final int caps_isRendering		= 0x0100;
	public static final int caps_isBloodsuck		= 0x0101;
	public static final int caps_isFreedom			= 0x0102;
	public static final int caps_isTracer			= 0x0103;
	public static final int caps_isPlaying			= 0x0104;
	public static final int caps_isLookSuger		= 0x0105;
	public static final int caps_isBlocking			= 0x0106;
	public static final int caps_isWait				= 0x0107;
	public static final int caps_isWaitEX			= 0x0108;
	public static final int caps_isOpenInv			= 0x0109;
	public static final int caps_isWorking			= 0x010a;
	public static final int caps_isWorkingDelay		= 0x010b;
	public static final int caps_isContract			= 0x010c;
	public static final int caps_isContractEX		= 0x010d;
	public static final int caps_isRemainsC			= 0x010e;
	public static final int caps_isClock			= 0x010f;
	public static final int caps_isMasked			= 0x0110;
	public static final int caps_isCamouflage		= 0x0111;
	public static final int caps_isPlanter			= 0x0112;
	public static final int caps_entityIdFactor		= 0x0120;
	public static final int caps_height				= 0x0121;
	public static final int caps_width				= 0x0122;
	public static final int caps_YOffset			= 0x0123;
	public static final int caps_mountedYOffset		= 0x0124;
	public static final int caps_dominantArm		= 0x0125;
	public static final int caps_render				= 0x0130;
	public static final int caps_Arms				= 0x0131;
	public static final int caps_HeadMount			= 0x0132;
	/** モデルに設定されているハードポイントを配列で返します **/
	public static final int caps_HardPoint			= 0x0133;
	/** 装備しているスタビライザーを返します **/
	public static final int caps_stabiliser			= 0x0134;
	/** 現在保持しているアイテムの配列を返します **/
	public static final int caps_Items				= 0x0135;
	/** 現在保持しているアイテムの挙動の配列を返します **/
	public static final int caps_Actions			= 0x0136;
	/** 現在保持しているアイテムの振り回し状態を配列で返します **/
	public static final int caps_Grounds			= 0x0137;
	/** Inventoryを返します **/
	public static final int caps_Inventory			= 0x0138;
	public static final int caps_Ground				= 0x0139;
	public static final int caps_interestedAngle	= 0x0150;
	
	// PlayerFormLittleMaid
	public static final int caps_ScaleFactor	= 0x0200;
	public static final int caps_PartsVisible	= 0x0201;
	public static final int caps_Posing			= 0x0202;
	public static final int caps_Actors			= 0x0203;
	public static final int caps_PartsStrings	= 0x0204;

	/**
	 * モデルが実装している機能をリストに入れて返す。
	 * @return
	 */
	public Map<String, Integer> getModelCaps();

	/**
	 * 現在の設定値を読み取る。
	 * @param pIndex
	 * @return
	 */
	public Object getCapsValue(int pIndex, Object ... pArg);

	/**
	 * 機能番号に値を設定する。
	 * @param pIndex
	 * @param pArg
	 * @return
	 */
	public boolean setCapsValue(int pIndex, Object ... pArg);

}
