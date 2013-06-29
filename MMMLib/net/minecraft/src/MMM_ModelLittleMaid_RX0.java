package net.minecraft.src;

import org.lwjgl.opengl.GL11;

public class MMM_ModelLittleMaid_RX0 extends MMM_ModelLittleMaidBase {

	public MMM_ModelRenderer bipedForelock;
	public MMM_ModelRenderer bipedForelockRight;
	public MMM_ModelRenderer bipedForelockLeft;
	public MMM_ModelRenderer bipedBreast;
	public MMM_ModelRenderer bipedTrunc;
	public MMM_ModelRenderer bipedHipRight;
	public MMM_ModelRenderer bipedHipLeft;
	public MMM_ModelRenderer bipedForearmRight;
	public MMM_ModelRenderer bipedForearmLeft;
	public MMM_ModelRenderer bipedShinRight;
	public MMM_ModelRenderer bipedTiptoeRight;
	public MMM_ModelRenderer bipedHeelRight;
	public MMM_ModelRenderer bipedShinLeft;
	public MMM_ModelRenderer bipedTiptoeLeft;
	public MMM_ModelRenderer bipedHeelLeft;
	public MMM_ModelRenderer bipedRibbon;
	public MMM_ModelRenderer bipedRibbon1;
	public MMM_ModelRenderer bipedRibbon2;
	public MMM_ModelRenderer bipedTail;
	public MMM_ModelRenderer SkirtRU;
	public MMM_ModelRenderer SkirtRB;
	public MMM_ModelRenderer SkirtLU;
	public MMM_ModelRenderer SkirtLB;
	public MMM_ModelRenderer bipedRibbonR;
	public MMM_ModelRenderer bipedRibbonRSensorU;
	public MMM_ModelRenderer bipedRibbonRSensorB;
	public MMM_ModelRenderer bipedSideTailR;
	public MMM_ModelRenderer bipedRibbonL;
	public MMM_ModelRenderer bipedRibbonLSensorU;
	public MMM_ModelRenderer bipedRibbonLSensorB;
	public MMM_ModelRenderer bipedSideTailL;



	public MMM_ModelLittleMaid_RX0() {
		this(0.0F);
	}
	public MMM_ModelLittleMaid_RX0(float psize) {
		this(psize, 0.0F, 128, 64);
	}
	public MMM_ModelLittleMaid_RX0(float psize, float pyoffset, int pTextureWidth, int pTextureHeight) {
		super(psize, pyoffset, pTextureWidth, pTextureHeight);
	}


	@Override
	public void initModel(float psize, float pyoffset) {
		bipedHead = new MMM_ModelRenderer(this);
		bipedHead.setTextureOffset(0, 0).addBox(-4F, -8F, -4F, 8, 8, 8, -0.2F);
		bipedForelock = new MMM_ModelRenderer(this);
		bipedForelockRight = new MMM_ModelRenderer(this);
		bipedForelockRight.setTextureOffset(0, 50).addBox(0F, 0F, -0.5F, 3, 13, 1);
		bipedForelockLeft = new MMM_ModelRenderer(this);
		bipedForelockLeft.setTextureOffset(56, 50).addBox(-3F, 0F, -0.5F, 3, 13, 1);
		bipedRibbon = new MMM_ModelRenderer(this);
		bipedRibbon.setTextureOffset(116, 10).addBox(-1.5F, 0F, -1.5F, 3, 3, 3);
		bipedRibbon1 = new MMM_ModelRenderer(this);
		bipedRibbon1.setTextureOffset(116, 0).addBox(-4F, 0F, -2F, 4, 3, 2);
		bipedRibbon2 = new MMM_ModelRenderer(this);
		bipedRibbon2.setTextureOffset(116, 5).addBox(0F, 0F, -2F, 4, 3, 2);
		bipedTail = new MMM_ModelRenderer(this);
		bipedTail.setTextureOffset(108, 0).addBox(-1.5F, -0.5F, -0.5F, 3, 11, 1);
		
		bipedRibbonR = new MMM_ModelRenderer(this);
		bipedRibbonR.setTextureOffset(80, 0).addBox(-1F, 0F, -1F, 2, 2, 2, 0.1F);
		bipedRibbonRSensorU = new MMM_ModelRenderer(this);
		bipedRibbonRSensorU.setTextureOffset(80, 4).addBox(-1F, 0F, -1F, 2, 5, 2, 0.2F);
		bipedRibbonRSensorB = new MMM_ModelRenderer(this);
		bipedRibbonRSensorB.setTextureOffset(74, 0).addBox(-1F, 0F, 0F, 2, 2, 1, 0.1F);
		bipedSideTailR = new MMM_ModelRenderer(this);
		bipedSideTailR.setTextureOffset(96, 0).addBox(-1F, -0.5F, -0.5F, 2, 11, 1);
		
		bipedRibbonL = new MMM_ModelRenderer(this);
		bipedRibbonL.setTextureOffset(88, 0).addBox(-1F, 0F, -1F, 2, 2, 2, 0.1F);
		bipedRibbonLSensorU = new MMM_ModelRenderer(this);
		bipedRibbonLSensorU.setTextureOffset(88, 4).addBox(-1F, 0F, -1F, 2, 5, 2, 0.2F);
		bipedRibbonLSensorB = new MMM_ModelRenderer(this);
		bipedRibbonLSensorB.setTextureOffset(74, 3).addBox(-1F, 0F, 0F, 2, 2, 1, 0.1F);
		bipedSideTailL = new MMM_ModelRenderer(this);
		bipedSideTailL.setTextureOffset(102, 0).addBox(-1F, -0.5F, -0.5F, 2, 11, 1);
		
		
		
		bipedBody = new MMM_ModelRenderer(this);
		bipedBody.setTextureOffset(32, 7).addBox(-3F, 0F, -1F, 6, 3, 3);
		bipedBreast = new MMM_ModelRenderer(this);
		bipedBreast.setTextureOffset(32, 0).addBox(-3F, -2.5F, 0F, 6, 4, 3, -0.05F);
		
		bipedRightArm = new MMM_ModelRenderer(this);
		bipedRightArm.setTextureOffset(8, 47).addBox(-2F, -0.5F, -1F, 2, 7, 2);
		bipedForearmRight = new MMM_ModelRenderer(this);
		bipedForearmRight.setTextureOffset(0, 40).addBox(-1F, -1F, -1F, 2, 8, 2, -0.05F);
		bipedLeftArm = new MMM_ModelRenderer(this);
		bipedLeftArm.setTextureOffset(48, 47).addBox(0F, -0.5F, -1F, 2, 7, 2);
		bipedForearmLeft = new MMM_ModelRenderer(this);
		bipedForearmLeft.setTextureOffset(56, 40).addBox(-1F, -1F, -1F, 2, 8, 2, -0.05F);
		
		bipedTrunc = new MMM_ModelRenderer(this);
		bipedTrunc.setTextureOffset(24, 46).addBox(-2.5F, 0F, -1.95F, 5, 7, 3);
		bipedHipRight = new MMM_ModelRenderer(this);
		bipedHipRight.setTextureOffset(50, 0).addBox(0F, -1.5F, -2F, 3, 4, 4);
		bipedHipLeft = new MMM_ModelRenderer(this);
		bipedHipLeft.setTextureOffset(50, 8).addBox(-3F, -1.5F, -2F, 3, 4, 4);
		
		bipedRightLeg = new MMM_ModelRenderer(this);
		bipedRightLeg.setTextureOffset(0, 29).addBox(-3F, 0F, -2F, 3, 7, 4);
		bipedShinRight = new MMM_ModelRenderer(this);
		bipedShinRight.setTextureOffset(0, 16).addBox(-3F, 0F, -3F, 3, 9, 4, -0.2F);
		bipedTiptoeRight = new MMM_ModelRenderer(this);
		bipedTiptoeRight.setTextureOffset(12, 26).addBox(-1.5F, 0F, -4F, 3, 2, 4);
		bipedHeelRight = new MMM_ModelRenderer(this);
		bipedHeelRight.setTextureOffset(10, 16).addBox(-1F, 0.25F, -3.25F, 2, 1, 3, 0.25F);
		
		bipedLeftLeg = new MMM_ModelRenderer(this);
		bipedLeftLeg.setTextureOffset(50, 29).addBox(0F, 0F, -2F, 3, 7, 4);
		bipedShinLeft = new MMM_ModelRenderer(this);
		bipedShinLeft.setTextureOffset(50, 16).addBox(0F, 0F, -3F, 3, 9, 4, -0.2F);
		bipedTiptoeLeft = new MMM_ModelRenderer(this);
		bipedTiptoeLeft.setTextureOffset(38, 26).addBox(-1.5F, 0F, -4F, 3, 2, 4);
		bipedHeelLeft = new MMM_ModelRenderer(this);
		bipedHeelLeft.setTextureOffset(20, 16).addBox(-1F, 0.25F, -3.25F, 2, 1, 3, 0.25F);
		
		Skirt = new MMM_ModelRenderer(this);
		Skirt.setTextureOffset(20, 26).addBox(-3F, 0F, -3F, 6, 8, 6, 0.05F);
		SkirtRU = new MMM_ModelRenderer(this);
		SkirtRU.setTextureOffset(8, 34).addBox(2F, 2F, -3F, 3, 7, 6);
		SkirtRB = new MMM_ModelRenderer(this);
		SkirtRB.setTextureOffset(8, 48).addBox(-4F, 0F, -4F, 4, 8, 8);
		SkirtLU = new MMM_ModelRenderer(this);
		SkirtLU.setTextureOffset(38, 34).addBox(-5F, 2F, -3F, 3, 7, 6);
		SkirtLB = new MMM_ModelRenderer(this);
		SkirtLB.setTextureOffset(32, 48).addBox(0F, 0F, -4F, 4, 8, 8);
		
		mainFrame = new MMM_ModelRenderer(this);
		bipedNeck = new MMM_ModelRenderer(this);
		bipedNeck.setTextureOffset(26, 40).addBox(-1.5F, -0.5F, -2.0F, 3, 3, 3);
		bipedTorso = new MMM_ModelRenderer(this);
		bipedPelvic = new MMM_ModelRenderer(this);
		
		Arms[0] = new MMM_ModelRenderer(this);
		Arms[0].setRotationPoint(0F, 5F, -1F);
		Arms[1] = new MMM_ModelRenderer(this);
		Arms[1].setRotationPoint(0F, 5F, -1F);
		Arms[1].isInvertX = true;
		
		HeadMount.setRotationPoint(0F, -4F, 0F);
		HeadTop.setRotationPoint(0F, -13F, 0F);
		
		mainFrame.addChild(bipedTorso);
		bipedTorso.addChild(bipedNeck);
		bipedNeck.addChild(bipedHead);
		bipedHead.addChild(bipedForelock);
		bipedForelock.addChild(bipedForelockRight);
		bipedForelock.addChild(bipedForelockLeft);
		bipedHead.addChild(bipedRibbon);
		bipedRibbon.addChild(bipedRibbon1);
		bipedRibbon.addChild(bipedRibbon2);
		bipedRibbon.addChild(bipedTail);
		bipedHead.addChild(bipedRibbonR);
		bipedRibbonR.addChild(bipedRibbonRSensorU);
		bipedRibbonR.addChild(bipedRibbonRSensorB);
		bipedRibbonR.addChild(bipedSideTailR);
		bipedHead.addChild(bipedRibbonL);
		bipedRibbonL.addChild(bipedRibbonLSensorU);
		bipedRibbonL.addChild(bipedRibbonLSensorB);
		bipedRibbonL.addChild(bipedSideTailL);
		bipedNeck.addChild(bipedRightArm);
		bipedRightArm.addChild(bipedForearmRight);
		bipedNeck.addChild(bipedLeftArm);
		bipedLeftArm.addChild(bipedForearmLeft);
		bipedTorso.addChild(bipedBody);
		bipedBody.addChild(bipedBreast);
		bipedTorso.addChild(bipedTrunc);
		bipedTrunc.addChild(bipedHipRight);
		bipedTrunc.addChild(bipedHipLeft);
		bipedTrunc.addChild(bipedPelvic);
		bipedPelvic.addChild(bipedRightLeg);
		bipedRightLeg.addChild(bipedShinRight);
		bipedShinRight.addChild(bipedTiptoeRight);
		bipedTiptoeRight.addChild(bipedHeelRight);
		bipedPelvic.addChild(bipedLeftLeg);
		bipedLeftLeg.addChild(bipedShinLeft);
		bipedShinLeft.addChild(bipedTiptoeLeft);
		bipedTiptoeLeft.addChild(bipedHeelLeft);
		bipedPelvic.addChild(Skirt);
		Skirt.addChild(SkirtRU);
		SkirtRU.addChild(SkirtRB);
		Skirt.addChild(SkirtLU);
		SkirtLU.addChild(SkirtLB);
		
		bipedForearmRight.addChild(Arms[0]);
		bipedForearmLeft.addChild(Arms[1]);
		bipedHead.addChild(HeadTop);
		bipedHead.addChild(HeadMount);
		
		
	}

	@Override
	public void setDefaultPause(float par1, float par2, float pTicksExisted,
			float pHeadYaw, float pHeadPitch, float par6, MMM_IModelCaps pEntityCaps) {
		// TODO Auto-generated method stub
		super.setDefaultPause(par1, par2, pTicksExisted, pHeadYaw, pHeadPitch, par6, pEntityCaps);
		int lvisible = MMM_ModelCapsHelper.getCapsValueInt(pEntityCaps, caps_PartsVisible);
		
		
		bipedRibbon.setVisible(true);
		Skirt.setVisible((lvisible & 0x0004) == 0);
//		bipedRightLeg.setVisible(true);
//		bipedLeftLeg.setVisible(true);
//		GL11.glScalef(0.85F, 0.85F, 0.85F);
		scaleFactor = 0.80F;
		if ((!MMM_ModelCapsHelper.getCapsValueBoolean(pEntityCaps, caps_isOverdriveDelay) &&
				!MMM_ModelCapsHelper.getCapsValueBoolean(pEntityCaps, caps_isBloodsuck) &&
				((lvisible & 0x0001) == 0)) && true) {
			// bansheeMode
			bipedBreast.setRotationPoint(0F, 2.5F, -2F);
			bipedBreast.setRotateAngleDegX(-0F);
			bipedForearmRight.setRotationPoint(-1.025F, 0.5F, 0F);
			bipedForearmLeft.setRotationPoint(1.025F, 0.5F, 0F);
			bipedForearmRight.setRotateAngleDegX(0F);
			bipedForearmLeft.setRotateAngleDegX(0F);

			bipedTrunc.setRotationPoint(0F, 0F, 0F);
			bipedForelock.setRotationPoint(0F, -7.5F, -4F);
			bipedForelockRight.setRotationPoint(-3.9F, 0F, 0F);
			bipedForelockLeft.setRotationPoint(3.9F, 0F, 0F);
			bipedForelockRight.setRotateAngleDegY(0F);
			bipedForelockLeft.setRotateAngleDegY(0F);
			float lf = bipedHead.rotateAngleX > 0 ? -bipedHead.rotateAngleX / 2F : 0F;
			bipedForelock.setRotateAngleX(lf);
			bipedRibbon.setRotationPoint(0F, -0.2F, 4F);
			bipedRibbon.setRotateAngleDegX(20F - bipedHead.getRotateAngleDegX());
			bipedRibbon1.setRotationPoint(-1.5F, 0F, 1F);
			bipedRibbon2.setRotationPoint(1.5F, 0F, 1F);
			bipedRibbon1.setRotateAngleDegY(0F);
			bipedRibbon2.setRotateAngleDegY(0F);
			bipedTail.setRotationPoint(0F, 3.0F, 0F);
			bipedTail.setRotateAngle(0F, 0F, 0F);
			
			bipedRibbonR.setRotationPoint(-3.5F, 0F, 4F).setRotateAngleDeg(15F - bipedHead.getRotateAngleDegX(), 0F, 10F);
			bipedRibbonRSensorU.setRotationPoint(0F, 1F, -0.2F).setRotateAngleDeg(10F, 0F, 0F);
			bipedRibbonRSensorB.setRotationPoint(0F, 2F, -0.9F).setRotateAngleDeg(0F, 0F, 0F);
			bipedSideTailR.setRotationPoint(0F, 2F, 0F).setRotateAngleDeg(0F, 0F, 0F);
			
			bipedRibbonL.setRotationPoint(3.5F, 0F, 4F).setRotateAngleDeg(15F - bipedHead.getRotateAngleDegX(), 0F, -10F);
			bipedRibbonLSensorU.setRotationPoint(0F, 1F, -0.2F).setRotateAngleDeg(10F, 0F, 0F);
			bipedRibbonLSensorB.setRotationPoint(0F, 2F, -0.9F).setRotateAngleDeg(0F, 0F, 0F);
			bipedSideTailL.setRotationPoint(0F, 2F, 0F).setRotateAngleDeg(0F, 0F, 0F);

			
			bipedHead.setRotationPoint(0F, 0.2F, 0F);
			bipedHipRight.setRotationPoint(-3F, 4.5F, 0F);
			bipedHipLeft.setRotationPoint(3F, 4.5F, 0F);
			bipedHipRight.setRotateAngleDeg(0F, 0F, 0F);
			bipedHipLeft.setRotateAngleDeg(0F, 0F, 0F);
			
			bipedRightLeg.setRotationPoint(0F, 0F, 0F);
			bipedRightLeg.setRotateAngleDegZ(0F);
			bipedShinRight.setRotationPoint(0F, 0F, 1F);
			bipedShinRight.setRotateAngleDeg(0F, 0F, 0F);
			bipedTiptoeRight.setRotationPoint(-1.5F, 7F, 1F);
			bipedTiptoeRight.setRotateAngleDeg(0F, 0F, 0F);
			bipedHeelRight.setRotationPoint(0F, 0F, -0.05F);
			bipedHeelRight.setRotateAngleDeg(0F, 0F, 0F);

			bipedLeftLeg.setRotationPoint(0F, 0F, 0F);
			bipedLeftLeg.setRotateAngleDegZ(0F);
			bipedShinLeft.setRotationPoint(0F, 0F, 1F);
			bipedShinLeft.setRotateAngleDeg(0F, 0F, 0F);
			bipedTiptoeLeft.setRotationPoint(1.5F, 7F, 1F);
			bipedTiptoeLeft.setRotateAngleDeg(0F, 0F, 0F);
			bipedHeelLeft.setRotationPoint(0F, 0F, -0.05F);
			bipedHeelLeft.setRotateAngleDeg(0F, 0F, 0F);
			
			bipedPelvic.setRotationPoint(0F, 7F, 0F);
			
			Skirt.setRotationPoint(0F, -2F, 0F);
			SkirtRU.setRotationPoint(-5F, -1F, 0F);
			SkirtRU.setRotateAngleDegZ(0F);
			SkirtRB.setRotationPoint(5F, 1F, 0F);
			SkirtLU.setRotationPoint(5F, -1F, 0F);
			SkirtLU.setRotateAngleDegZ(0F);
			SkirtLB.setRotationPoint(-5F, 1F, 0F);

//			bipedTorso.setRotationPointY(4.1F);
			bipedTorso.setRotationPointY(0F);

		} else {
			// DestroyMode
			bipedBody.setRotationPoint(0F, 0F, -0.5F);
			bipedBreast.setRotationPoint(0F, 2.5F, -2F);
			bipedBreast.setRotateAngleDegX(-41F);
			bipedForearmRight.setRotationPoint(-1.025F, 6F, 0F);
			bipedForearmLeft.setRotationPoint(1.025F, 6F, 0F);
			bipedForearmRight.setRotateAngleDegX(-30F);
			bipedForearmLeft.setRotateAngleDegX(-30F);
			bipedTrunc.setRotationPoint(0F, 4F, 0F);
			bipedForelock.setRotationPoint(0F, -8.5F, -2F);
			bipedForelockRight.setRotationPoint(-3.8F, 0F, 0F);
			bipedForelockLeft.setRotationPoint(3.8F, 0F, 0F);
			bipedForelockRight.setRotateAngleDegY(90F);
			bipedForelockLeft.setRotateAngleDegY(-90F);
//			float lf = bipedHead.rotateAngleX > 0 ? -bipedHead.rotateAngleX / 2F : 0F;
			float lf = -bipedHead.rotateAngleX / (bipedHead.rotateAngleX > 0 ? 2F : 4F);
			bipedForelock.setRotateAngleX(lf);
			bipedRibbon.setRotationPoint(0F, -6.2F, 3.8F);
			bipedRibbon.setRotateAngleDegX(90F);
			bipedRibbon1.setRotationPoint(-1.5F, 0F, 0.5F);
			bipedRibbon1.setRotateAngleDegY(30F);
			bipedRibbon2.setRotationPoint(1.5F, 0F, 0.5F);
			bipedRibbon2.setRotateAngleDegY(-30F);
			bipedTail.setRotationPoint(0F, 3.5F, -0F);
			lf = bipedHead.rotateAngleX < 0 ? -bipedHead.rotateAngleX : 0F;
			bipedTail.setRotateAngleX(lf);
			bipedTail.addRotateAngleDegX(-80F);
			
			lf = bipedHead.getRotateAngleDegX() / 2F;
			bipedRibbonR.setRotationPoint(-4F, -6F, 2F).setRotateAngleDeg(0F, -90F + lf, 90F);
			bipedRibbonRSensorU.setRotationPoint(0F, 1F, -0.2F).setRotateAngleDeg(30F, 0F, 0F);
			bipedRibbonRSensorB.setRotationPoint(0F, 2F, -0.9F).setRotateAngleDeg(-45F, 0F, 0F);
			bipedSideTailR.setRotationPoint(0F, 2F, 0F).setRotateAngleDeg(-80F, 0F, 0F);
			
			bipedRibbonL.setRotationPoint(4F, -6F, 2F).setRotateAngleDeg(0F, 90F - lf, -90F);
			bipedRibbonLSensorU.setRotationPoint(0F, 1F, -0.2F).setRotateAngleDeg(30F, 0F, 0F);
			bipedRibbonLSensorB.setRotationPoint(0F, 2F, -0.9F).setRotateAngleDeg(-45F, 0F, 0F);
			bipedSideTailL.setRotationPoint(0F, 2F, 0F).setRotateAngleDeg(-80F, 0F, 0F);
			
			bipedHead.setRotationPoint(0F, 0.2F, 0F);
			bipedHipRight.setRotationPoint(-3F, 4.5F, 0.45F);
			bipedHipLeft.setRotationPoint(3F, 4.5F, 0.45F);
			bipedHipRight.setRotateAngleDeg(15F, 0F, 15F);
			bipedHipLeft.setRotateAngleDeg(15F, 0F, -15F);

			bipedRightLeg.setRotationPoint(-0.7F, -0.5F, 1.0F);
			bipedRightLeg.addRotateAngleDegX(-5F);
			bipedRightLeg.addRotateAngleDegZ(-6F);
			bipedShinRight.setRotationPoint(-0.1F, 6.5F, 1F);
			bipedShinRight.setRotateAngleDeg(5F, 0F, 4F);
			bipedTiptoeRight.setRotationPoint(-1.5F, 7F, -0.5F);
			bipedTiptoeRight.setRotateAngleDeg(30F, 0F, 2F);//6F
			bipedHeelRight.setRotationPoint(0F, 0F, -0.05F);
			bipedHeelRight.setRotateAngleDegX(60F);
			bipedLeftLeg.setRotationPoint(0.7F, -0.5F, 1.0F);
			bipedLeftLeg.addRotateAngleDegX(-5F);
			bipedLeftLeg.addRotateAngleDegZ(6F);
			bipedShinLeft.setRotationPoint(0.1F, 6.5F, 1F);
			bipedShinLeft.setRotateAngleDeg(5F, 0F, -4F);
			bipedTiptoeLeft.setRotationPoint(1.5F, 7F, -0.5F);
			bipedTiptoeLeft.setRotateAngleDeg(30F, 0F, -2F);
			bipedHeelLeft.setRotationPoint(0F, 0F, -0.05F);
			bipedHeelLeft.setRotateAngleDegX(60F);
			bipedPelvic.setRotationPoint(0F, 7F, 0F);
			
			Skirt.setRotationPoint(0F, -4.5F, 0.2F);
			SkirtRU.setRotationPoint(-5F, -1F, 0F);
			SkirtRU.setRotateAngleDegZ(13F);
			SkirtLU.setRotationPoint(5F, -1F, 0F);
			SkirtLU.setRotateAngleDegZ(-13F);
			if ((lvisible & 0x0002) > 0) {
				SkirtRB.setRotationPoint(5F, 1F, 0F);
				SkirtLB.setRotationPoint(-5F, 1F, 0F);
			} else {
				SkirtRB.setRotationPoint(5F, 7F, 0F);
				SkirtLB.setRotationPoint(-5F, 7F, 0F);
			}
			
			
			bipedTorso.setRotationPointY(-12F);
			bipedRightArm.setRotationPoint(-3F, 1F, 0F);
			bipedLeftArm.setRotationPoint(3F, 1F, 0F);
			bipedRightArm.addRotateAngleDegZ(-15F);
			bipedLeftArm.addRotateAngleDegZ(15F);
		}
	}

	@Override
	public Object getCapsValue(int pIndex, Object... pArg) {
		switch (pIndex) {
		case caps_PartsStrings:
			return "DestroyMode,mimiSkirt,noSkirt";
		}
		
		return super.getCapsValue(pIndex, pArg);
	}

	@Override
	public float getHeight(MMM_IModelCaps pEntityCaps) {
		int lvisible = MMM_ModelCapsHelper.getCapsValueInt(pEntityCaps, caps_PartsVisible);
		boolean lf = (!MMM_ModelCapsHelper.getCapsValueBoolean(pEntityCaps, caps_isOverdriveDelay) &&
				!MMM_ModelCapsHelper.getCapsValueBoolean(pEntityCaps, caps_isBloodsuck) &&
				((lvisible & 0x0001) == 0)) && true;
		return lf ? 1.152F : 1.58F;
	}

	@Override
	public float getWidth(MMM_IModelCaps pEntityCaps) {
		return super.getWidth(pEntityCaps);
	}

}
