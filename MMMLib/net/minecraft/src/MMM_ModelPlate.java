package net.minecraft.src;

public class MMM_ModelPlate extends ModelBox {

	private PositionTextureVertex vertexPositions[];
	private TexturedQuad quadList[];
	public final float posX1;
	public final float posY1;
	public final float posZ1;
	public final float posX2;
	public final float posY2;
	public final float posZ2;

	public MMM_ModelPlate(ModelRenderer modelrenderer, int pTextureX,
			int pTextureY, float f, float f1, float f2, int pWidth,
			int pHeight, int pPlane, float pZoom) {
		// 親は呼んでるけど、内容は未使用
		super(modelrenderer, pTextureX, pTextureY, f, f1, f2, pWidth, pHeight, pPlane, pZoom);
		
		float f4;
		float f5;
		float f6;
		
		// i1 は平面の生成位置
		switch (pPlane & 3) {
		case 0:
			// xy
			posX1 = f;
			posY1 = f1;
			posZ1 = f2;
			posX2 = f4 = f + (float) pWidth;
			posY2 = f5 = f1 + (float) pHeight;
			posZ2 = f6 = f2;
			f -= pZoom;
			f1 -= pZoom;
			f4 += pZoom;
			f5 += pZoom;
			if (pPlane < 4) {
				f2 -= pZoom;
				f6 -= pZoom;
			} else {
				f2 += pZoom;
				f6 += pZoom;
			}
			break;
		case 1:
			// zy
			posX1 = f;
			posY1 = f1;
			posZ1 = f2;
			posX2 = f4 = f;
			posY2 = f5 = f1 + (float) pHeight;
			posZ2 = f6 = f2 + (float) pWidth;
			f1 -= pZoom;
			f2 -= pZoom;
			f5 += pZoom;
			f6 += pZoom;
			if (pPlane < 4) {
				f += pZoom;
				f4 += pZoom;
			} else {
				f -= pZoom;
				f4 -= pZoom;
			}
			break;
		case 2:
		default:
			// xz
			posX1 = f;
			posY1 = f1;
			posZ1 = f2;
			posX2 = f4 = f + (float) pWidth;
			posY2 = f5 = f1;
			posZ2 = f6 = f2 + (float) pHeight;
			f -= pZoom;
			f2 -= pZoom;
			f4 += pZoom;
			f6 += pZoom;
			if (pPlane < 4) {
				f1 -= pZoom;
				f5 -= pZoom;
			} else {
				f1 += pZoom;
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
				f4 = f;
				f = f7;
			} else if (pPlane == 1 && pPlane == 5) {
				// zy
				float f7 = f6;
				f6 = f2;
				f2 = f7;
			} else {
				// xz
				float f7 = f4;
				f4 = f;
				f = f7;
			}
		}
		
		PositionTextureVertex positiontexturevertex =
				new PositionTextureVertex(f, f1, f2, 0.0F, 0.0F);
		PositionTextureVertex positiontexturevertex1 =
				new PositionTextureVertex(f4, f1, f6, 0.0F, 8F);
		PositionTextureVertex positiontexturevertex2 =
				new PositionTextureVertex(f4, f5, f6, 8F, 8F);
		PositionTextureVertex positiontexturevertex3 =
				new PositionTextureVertex(f, f5, f2, 8F, 0.0F);
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

	@Override
	public MMM_ModelPlate func_78244_a(String par1Str) {
		super.func_78244_a(par1Str);
		return this;
	}

}
