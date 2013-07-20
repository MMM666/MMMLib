package net.minecraft.src;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.util.Set;

import javax.imageio.ImageIO;

import com.google.gson.JsonObject;

/**
 * 古いリソースを読みこませるためのパッケージ指定
 * コードはModloaderの丸パクリ
 */
public class MMM_ModOldResourcePack implements ResourcePack {

	protected final Class modClass;

	public MMM_ModOldResourcePack(Class modClass) {
		this.modClass = modClass;
	}

	public InputStream func_110590_a(ResourceLocation var1) throws IOException {
		return this.modClass.getResourceAsStream("" + var1.func_110623_a());
	}

	@Override
	public boolean func_110589_b(ResourceLocation var1) {
		try {
			return this.func_110590_a(var1) != null;
		} catch (IOException var3) {
			return false;
		}
	}

	public Set func_110587_b() {
		return DefaultResourcePack.field_110608_a;
	}

	public MetadataSection func_135058_a(MetadataSerializer var1, String var2) throws IOException {
		return var1.func_110503_a(var2, new JsonObject());
	}

	public BufferedImage func_110586_a() throws IOException {
		return ImageIO.read(DefaultResourcePack.class.getResourceAsStream(
				"/" + (new ResourceLocation("pack.png")).func_110623_a()));
	}

	public String func_130077_b() {
		return this.modClass.getSimpleName();
	}

}
