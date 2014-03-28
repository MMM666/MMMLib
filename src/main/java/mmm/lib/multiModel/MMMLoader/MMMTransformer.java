package mmm.lib.multiModel.MMMLoader;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import net.minecraft.launchwrapper.IClassTransformer;

import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.tree.AbstractInsnNode;
import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.FieldInsnNode;
import org.objectweb.asm.tree.FieldNode;
import org.objectweb.asm.tree.InvokeDynamicInsnNode;
import org.objectweb.asm.tree.MethodInsnNode;
import org.objectweb.asm.tree.MethodNode;
import org.objectweb.asm.tree.MultiANewArrayInsnNode;
import org.objectweb.asm.tree.TypeInsnNode;


/**
 * 古いマルチモデルのロード用。<br>
 * 使用しているクラスを置換えて新しいものへ対応。
 *
 */
public class MMMTransformer implements IClassTransformer, Opcodes {

	private static String packege = "mmm/lib/multiModel/model/mc162/";
	@SuppressWarnings("serial")
	private static final Map<String, String> targets = new HashMap<String, String>() {
		{
			add("EquippedStabilizer");
			add("IModelBaseMMM");
			add("IModelCaps");
			add("ModelBase");
			add("ModelBaseDuo");
			add("ModelBaseNihil");
			add("ModelBaseSolo");
			add("ModelBox");
			add("ModelBoxBase");
			add("ModelCapsHelper");
			add("ModelLittleMaid_Aug");
			add("ModelLittleMaid_SR2");
			add("ModelLittleMaidBase");
			add("ModelMultiBase");
			add("ModelMultiMMMBase");
			add("ModelPlate");
			add("ModelRenderer");
			add("ModelStabilizerBase");
		}
		private void add(String pName) {
			put("MMM_" + pName, packege + pName);
		}
	};

	public static boolean isEnable = false;
	public static boolean isDebugMessage = true;
	private boolean isChange;


	public static void Debug(String pText, Object... pData) {
		// デバッグメッセージ
		if (isDebugMessage) {
			System.out.println(String.format("MMMTransformer-" + pText, pData));
		}
	}

	@Override
	public byte[] transform(String name, String transformedName, byte[] basicClass) {
		if (basicClass != null && isEnable) {
			return replacer(name, transformedName, basicClass);
		}
		return basicClass;
	}

	/**
	 * バイナリを解析して旧MMMLibのクラスを置き換える。
	 * @param name
	 * @param transformedName
	 * @param basicClass
	 * @return
	 */
	private byte[] replacer(String name, String transformedName, byte[] basicClass) {
		ClassReader lcreader = new ClassReader(basicClass);
		if (!targets.containsKey(lcreader.getSuperName())) {
			// 親クラスが特定クラスではないので抜けますね^^;
//			return basicClass;
		} else {
			Debug("Load Old-MulitiModel: %s", name);
		}
		isChange = false;
		
		// 親クラスの置き換え
		ClassNode lcnode = new ClassNode();
		lcreader.accept(lcnode, 0);
		lcnode.superName = checkMMM(lcnode.superName);
		
		// フィールドの置き換え
		for (FieldNode lfn : lcnode.fields) {
			lfn.desc = checkMMM(lfn.desc);
		}
		
		// メソッドの置き換え
		for (MethodNode lmn : lcnode.methods) {
			AbstractInsnNode lin = lmn.instructions.getFirst();
			while(lin != null) {
				if (lin instanceof FieldInsnNode) {
					((FieldInsnNode)lin).desc = checkMMM(((FieldInsnNode)lin).desc);
					((FieldInsnNode)lin).name = checkMMM(((FieldInsnNode)lin).name);
					((FieldInsnNode)lin).owner = checkMMM(((FieldInsnNode)lin).owner);
				} else if (lin instanceof InvokeDynamicInsnNode) {
					((InvokeDynamicInsnNode)lin).desc = checkMMM(((InvokeDynamicInsnNode)lin).desc);
					((InvokeDynamicInsnNode)lin).name = checkMMM(((InvokeDynamicInsnNode)lin).name);
				} else if (lin instanceof MethodInsnNode) {
					((MethodInsnNode)lin).desc = checkMMM(((MethodInsnNode)lin).desc);
					((MethodInsnNode)lin).name = checkMMM(((MethodInsnNode)lin).name);
					((MethodInsnNode)lin).owner = checkMMM(((MethodInsnNode)lin).owner);
				} else if (lin instanceof MultiANewArrayInsnNode) {	//3
					((MultiANewArrayInsnNode)lin).desc = checkMMM(((MultiANewArrayInsnNode)lin).desc);
				} else if (lin instanceof TypeInsnNode) {
					((TypeInsnNode)lin).desc = checkMMM(((TypeInsnNode)lin).desc);
				}
				
				lin = lin.getNext();
			}
		}
		
		// バイナリコードの書き出し
		if (isChange) {
			ClassWriter lcwriter = new ClassWriter(ClassWriter.COMPUTE_FRAMES | ClassWriter.COMPUTE_MAXS);
			lcnode.accept(lcwriter);
			byte[] lb = lcwriter.toByteArray();
			Debug("Replace: %s", name);
			return lb;
		} else {
			return basicClass;
		}
	}

	private String checkMMM(String pText) {
		for (Entry<String, String> le : targets.entrySet()) {
			if (pText.indexOf(le.getKey()) > -1) {
//				Debug("Hit and Replace: %s", le.getKey());
				isChange = true;;
				return pText.replace(le.getKey(), le.getValue());
			}
		}
		return pText;
	}

}
