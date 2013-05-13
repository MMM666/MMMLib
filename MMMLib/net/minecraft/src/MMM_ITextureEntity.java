package net.minecraft.src;

/**
 * MMM_Texture仕様のテクスチャパック設定に対応しているEntityへ継承させる。
 */
public interface MMM_ITextureEntity {

	/**
	 * Server用。
	 * TextureManagerがサーバー側のEntityへテクスチャ変更の通知を行う。
	 * @param pIndex
	 * 設定されるテクスチャパックのインデックス（TextureBoxServer）
	 */
	public void setTexturePackIndex(int pColor, int[] pIndex);

	/**
	 * Client用。
	 * TextureManagerがクライアント側のEntityへテクスチャ変更の通知を行う。
	 * @param pPackName
	 * 設定されるテクスチャパックの名称（TextureBoxClient）
	 */
	public void setTexturePackName(MMM_TextureBox[] pTextureBox);

}
