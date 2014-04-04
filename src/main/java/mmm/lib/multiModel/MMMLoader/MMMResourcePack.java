package mmm.lib.multiModel.MMMLoader;

import java.awt.image.BufferedImage;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Set;

import javax.imageio.ImageIO;

import net.minecraft.client.resources.DefaultResourcePack;
import net.minecraft.client.resources.IResourcePack;
import net.minecraft.client.resources.data.IMetadataSection;
import net.minecraft.client.resources.data.IMetadataSerializer;
import net.minecraft.util.ResourceLocation;
import cpw.mods.fml.common.ModContainer;

/**
 * 旧リソースを扱えるようにするクラス。<br>
 * 基本的にクラスローダーでマップされたリソースにアクセスできるようにするだけなので、特殊なマップ方法でロードされている場合は対応できない。
 *
 */
public class MMMResourcePack implements IResourcePack {

	protected ModContainer ownerContainer;


	public MMMResourcePack(ModContainer pContainer) {
		ownerContainer = pContainer;
	}

	@Override
	public InputStream getInputStream(ResourceLocation par1ResourceLocation) throws IOException {
		InputStream inputstream = getResourceStream(par1ResourceLocation);
		
		if (inputstream != null) {
			return inputstream;
		} else {
			throw new FileNotFoundException(par1ResourceLocation.getResourcePath());
		}
	}

	private InputStream getResourceStream(ResourceLocation par1ResourceLocation) {
		InputStream lis = MMMResourcePack.class.getResourceAsStream(par1ResourceLocation.getResourcePath());
//		MMMLib.Debug("getResource:%s", lis == null ? "Null" : lis.toString());
		return lis;
	}

	@Override
	public boolean resourceExists(ResourceLocation par1ResourceLocation) {
		return getResourceStream(par1ResourceLocation) != null;
	}

	@Override
	@SuppressWarnings("rawtypes")
	public Set getResourceDomains() {
		return DefaultResourcePack.defaultResourceDomains;
	}

	@Override
	public IMetadataSection getPackMetadata(IMetadataSerializer par1MetadataSerializer, String par2Str)
	{ //throws IOException {
		return null;
	}

	@Override
	public BufferedImage getPackImage() {// throws IOException {
		try {
			return ImageIO.read(DefaultResourcePack.class.getResourceAsStream("/"
					+ (new ResourceLocation("pack.png")).getResourcePath()));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public String getPackName() {
		return "Default";
	}

}
