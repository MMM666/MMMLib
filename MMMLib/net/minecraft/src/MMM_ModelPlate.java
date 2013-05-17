package net.minecraft.src;

public class MMM_ModelPlate extends MMM_ModelBoxBase {

	/**
	 * @param pMRenderer
	 * @param pArg
	 * textureX, textureY, posX, posY, posZ, width, height, facePlane, sizeAdjust
	 */
	public MMM_ModelPlate(MMM_ModelRenderer pMRenderer, Object... pArg) {
		super(pMRenderer, pArg);
		init(pMRenderer, (Integer)pArg[0], (Integer)pArg[1],
				(Float)pArg[2], (Float)pArg[3], (Float)pArg[4],
				(Integer)pArg[5], (Integer)pArg[6], (Integer)pArg[7],
				pArg.length < 9 ? 0.0F : (Float)pArg[8]);
	}

	private void init(MMM_ModelRenderer modelrenderer, int pTextureX, int pTextureY,
			float pX, float pY, float pZ, int pWidth, int pHeight, int pPlane, float pZoom) {
		float lx;
		float ly;
		float lz;
		
		// i1 ‚Í•½–Ê‚Ì¶¬ˆÊ’u
		switch (pPlane & 3) {
		case 0:
			// xy
			posX1 = pX;
			posY1 = pY;
			posZ1 = pZ;
			posX2 = lx = pX + (float) pWidth;
			posY2 = ly = pY + (float) pHeight;
			posZ2 = lz = pZ;
			pX -= pZoom;
			pY -= pZoom;
			lx += pZoom;
			ly += pZoom;
			if (pPlane < 4) {
				pZ -= pZoom;
				lz -= pZoom;
			} else {
				pZ += pZoom;
				lz += pZoom;
			}
			break;
		case 1:
			// zy
			posX1 = pX;
			posY1 = pY;
			posZ1 = pZ;
			posX2 = lx = pX;
			posY2 = ly = pY + (float) pHeight;
			posZ2 = lz = pZ + (float) pWidth;
			pY -= pZoom;
			pZ -= pZoom;
			ly += pZoom;
			lz += pZoom;
			if (pPlane < 4) {
				pX += pZoom;
				lx += pZoom;
			} else {
				pX -= pZoom;
				lx -= pZoom;
			}
			break;
		case 2:
		default:
			// xz
			posX1 = pX;
			posY1 = pY;
			posZ1 = pZ;
			posX2 = lx = pX + (float) pWidth;
			posY2 = ly = pY;
			posZ2 = lz = pZ + (float) pHeight;
			pX -= pZoom;
			pZ -= pZoom;
			lx += pZoom;
			lz += pZoom;
			if (pPlane < 4) {
				pY -= pZoom;
				ly -= pZoom;
			} else {
				pY += pZoom;
				ly += pZoom;
			}
			break;
		}
		
		vertexPositions = new PositionTextureVertex[4];
		quadList = new TexturedQuad[1];
		// –Ê‚Ì–@–Ê‚ð”½“]‚·‚é
		if (modelrenderer.mirror) {
			if (pPlane == 0 && pPlane == 4) {
				// xy
				float f7 = lx;
				lx = pX;
				pX = f7;
			} else if (pPlane == 1 && pPlane == 5) {
				// zy
				float f7 = lz;
				lz = pZ;
				pZ = f7;
			} else {
				// xz
				float f7 = lx;
				lx = pX;
				pX = f7;
			}
		}
		
		PositionTextureVertex positiontexturevertex =
				new PositionTextureVertex(pX, pY, pZ, 0.0F, 0.0F);
		PositionTextureVertex positiontexturevertex1 =
				new PositionTextureVertex(lx, pY, lz, 0.0F, 8F);
		PositionTextureVertex positiontexturevertex2 =
				new PositionTextureVertex(lx, ly, lz, 8F, 8F);
		PositionTextureVertex positiontexturevertex3 =
				new PositionTextureVertex(pX, ly, pZ, 8F, 0.0F);
		vertexPositions[0] = positiontexturevertex;
		vertexPositions[1] = positiontexturevertex1;
		vertexPositions[2] = positiontexturevertex2;
		vertexPositions[3] = positiontexturevertex3;
		
		if ((pPlane & 4) > 0) {
			// ‹tŽü‚è
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
	}

}
