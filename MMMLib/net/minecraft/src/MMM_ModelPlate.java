package net.minecraft.src;

public class MMM_ModelPlate extends MMM_ModelBoxBase {

	private PositionTextureVertex vertexPositions[];
	private TexturedQuad quadList[];
	public final float posX1;
	public final float posY1;
	public final float posZ1;
	public final float posX2;
	public final float posY2;
	public final float posZ2;


	/**
	 * @param pMRenderer
	 * @param pArg
	 * textureX, textureY, posX, posY, posZ, width, height, facePlane, sizeAdjust
	 */
	public MMM_ModelPlate(MMM_ModelRenderer pMRenderer, Object... pArg) {
		this(pMRenderer, (Integer)pArg[0], (Integer)pArg[1],
				(Float)pArg[2], (Float)pArg[3], (Float)pArg[4],
				(Integer)pArg[5], (Integer)pArg[6], (Integer)pArg[7],
				pArg.length < 9 ? 0.0F : (Float)pArg[8]);
	}

	private MMM_ModelPlate(ModelRenderer modelrenderer, int pTextureX, int pTextureY,
			float pX, float pY, float pZ, int pWidth, int pHeight, int pPlane, float pZoom) {
		// 親は呼んでるけど、内容は未使用
		super(modelrenderer, pTextureX, pTextureY, pX, pY, pZ, pWidth, pHeight, pPlane, pZoom);
		
		float f4;
		float f5;
		float f6;
		
		// i1 は平面の生成位置
		switch (pPlane & 3) {
		case 0:
			// xy
			posX1 = pX;
			posY1 = pY;
			posZ1 = pZ;
			posX2 = f4 = pX + (float) pWidth;
			posY2 = f5 = pY + (float) pHeight;
			posZ2 = f6 = pZ;
			pX -= pZoom;
			pY -= pZoom;
			f4 += pZoom;
			f5 += pZoom;
			if (pPlane < 4) {
				pZ -= pZoom;
				f6 -= pZoom;
			} else {
				pZ += pZoom;
				f6 += pZoom;
			}
			break;
		case 1:
			// zy
			posX1 = pX;
			posY1 = pY;
			posZ1 = pZ;
			posX2 = f4 = pX;
			posY2 = f5 = pY + (float) pHeight;
			posZ2 = f6 = pZ + (float) pWidth;
			pY -= pZoom;
			pZ -= pZoom;
			f5 += pZoom;
			f6 += pZoom;
			if (pPlane < 4) {
				pX += pZoom;
				f4 += pZoom;
			} else {
				pX -= pZoom;
				f4 -= pZoom;
			}
			break;
		case 2:
		default:
			// xz
			posX1 = pX;
			posY1 = pY;
			posZ1 = pZ;
			posX2 = f4 = pX + (float) pWidth;
			posY2 = f5 = pY;
			posZ2 = f6 = pZ + (float) pHeight;
			pX -= pZoom;
			pZ -= pZoom;
			f4 += pZoom;
			f6 += pZoom;
			if (pPlane < 4) {
				pY -= pZoom;
				f5 -= pZoom;
			} else {
				pY += pZoom;
				f5 += pZoom;
			}
			break;
		}
		
		vertexPositions = new PositionTextureVertex[4];
		quadList = new TexturedQuad[1];
		// float f4 = field_40674_d;
		// float f5 = field_40675_e;
		// float f6 = field_40672_f;
		// f -= f3;
		// f1 -= f3;
		// f2 -= f3;
		// f4 += f3;
		// f5 += f3;
		// f6 += f3;
		// 面の法面を反転する
		if (modelrenderer.mirror) {
			if (pPlane == 0 && pPlane == 4) {
				// xy
				float f7 = f4;
				f4 = pX;
				pX = f7;
			} else if (pPlane == 1 && pPlane == 5) {
				// zy
				float f7 = f6;
				f6 = pZ;
				pZ = f7;
			} else {
				// xz
				float f7 = f4;
				f4 = pX;
				pX = f7;
			}
		}
		
		PositionTextureVertex positiontexturevertex =
				new PositionTextureVertex(pX, pY, pZ, 0.0F, 0.0F);
		PositionTextureVertex positiontexturevertex1 =
				new PositionTextureVertex(f4, pY, f6, 0.0F, 8F);
		PositionTextureVertex positiontexturevertex2 =
				new PositionTextureVertex(f4, f5, f6, 8F, 8F);
		PositionTextureVertex positiontexturevertex3 =
				new PositionTextureVertex(pX, f5, pZ, 8F, 0.0F);
		vertexPositions[0] = positiontexturevertex;
		vertexPositions[1] = positiontexturevertex1;
		vertexPositions[2] = positiontexturevertex2;
		vertexPositions[3] = positiontexturevertex3;
		
		if ((pPlane & 4) > 0) {
			// 逆周り
			quadList[0] = new TexturedQuad(
					new PositionTextureVertex[] {
							positiontexturevertex,
							positiontexturevertex1,
							positiontexturevertex2,
							positiontexturevertex3 },
					pTextureX, pTextureY, pTextureX + pWidth, pTextureY + pHeight,
					modelrenderer.textureWidth,
					modelrenderer.textureHeight);
		} else {
			quadList[0] = new TexturedQuad(
					new PositionTextureVertex[] {
							positiontexturevertex1,
							positiontexturevertex,
							positiontexturevertex3,
							positiontexturevertex2 },
					pTextureX, pTextureY, pTextureX + pWidth, pTextureY + pHeight,
					modelrenderer.textureWidth,
					modelrenderer.textureHeight);
		}
		// 実際のポリゴンを継承元の変数に突っ込む
		try {
			ModLoader.setPrivateValue(ModelBox.class, this, 0, vertexPositions);
			ModLoader.setPrivateValue(ModelBox.class, this, 1, quadList);
		} catch (Exception exception) {
		}
	}

}
