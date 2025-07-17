/*    */ package mchhui.modularmovements.coremod.minecraft;
/*    */ import org.objectweb.asm.ClassReader;
/*    */ import org.objectweb.asm.ClassWriter;
/*    */ import org.objectweb.asm.tree.AbstractInsnNode;
/*    */ import org.objectweb.asm.tree.ClassNode;
/*    */ import org.objectweb.asm.tree.InsnList;
/*    */ import org.objectweb.asm.tree.LabelNode;
/*    */ import org.objectweb.asm.tree.LocalVariableNode;
/*    */ import org.objectweb.asm.tree.MethodNode;
/*    */ import org.objectweb.asm.tree.VarInsnNode;
/*    */ 
/*    */ public class EntityPlayerSP implements IClassTransformer {
/*    */   public byte[] transform(String name, String transformedName, byte[] basicClass) {
/* 14 */     if (transformedName.equals("net.minecraft.client.entity.EntityPlayerSP")) {
/* 15 */       FMLLog.log.warn("[Transforming:net.minecraft.client.entity.EntityPlayerSP]");
/* 16 */       ClassNode classNode = new ClassNode(327680);
/* 17 */       ClassReader classReader = new ClassReader(basicClass);
/* 18 */       classReader.accept((ClassVisitor)classNode, 0);
/* 19 */       MethodNode methodNode = new MethodNode(327680, 1, "getPositionEyes", "(F)Lnet/minecraft/util/math/Vec3d;", null, null);
/*    */       
/* 21 */       methodNode.visitMaxs(2, 2);
/* 22 */       InsnList list = new InsnList();
/* 23 */       LabelNode startLabelNode = new LabelNode();
/* 24 */       LabelNode endLabelNode = new LabelNode();
/* 25 */       list.add((AbstractInsnNode)startLabelNode);
/* 26 */       list.add((AbstractInsnNode)new VarInsnNode(25, 0));
/* 27 */       list.add((AbstractInsnNode)new VarInsnNode(23, 1));
/* 28 */       list.add((AbstractInsnNode)new MethodInsnNode(184, "mchhui/modularmovements/coremod/ModularMovementsHooks", "onGetPositionEyes", "(Lnet/minecraft/entity/player/EntityPlayer;F)Lnet/minecraft/util/math/Vec3d;", false));
/*    */       
/* 30 */       list.add((AbstractInsnNode)endLabelNode);
/* 31 */       list.add((AbstractInsnNode)new InsnNode(176));
/* 32 */       methodNode.localVariables.add(new LocalVariableNode("this", "Lnet/minecraft/client/entity/EntityPlayerSP;", null, startLabelNode, endLabelNode, 0));
/*    */       
/* 34 */       methodNode.localVariables
/* 35 */         .add(new LocalVariableNode("partialTicks", "F", null, startLabelNode, endLabelNode, 1));
/* 36 */       methodNode.instructions.add(list);
/* 37 */       classNode.methods.add(methodNode);
/*    */       
/* 39 */       methodNode = new MethodNode(327680, 1, "func_174824_e", "(F)Lnet/minecraft/util/math/Vec3d;", null, null);
/*    */       
/* 41 */       methodNode.visitMaxs(2, 2);
/* 42 */       list = new InsnList();
/* 43 */       startLabelNode = new LabelNode();
/* 44 */       endLabelNode = new LabelNode();
/* 45 */       list.add((AbstractInsnNode)startLabelNode);
/* 46 */       list.add((AbstractInsnNode)new VarInsnNode(25, 0));
/* 47 */       list.add((AbstractInsnNode)new VarInsnNode(23, 1));
/* 48 */       list.add((AbstractInsnNode)new MethodInsnNode(184, "mchhui/modularmovements/coremod/ModularMovementsHooks", "onGetPositionEyes", "(Lnet/minecraft/entity/player/EntityPlayer;F)Lnet/minecraft/util/math/Vec3d;", false));
/*    */       
/* 50 */       list.add((AbstractInsnNode)endLabelNode);
/* 51 */       list.add((AbstractInsnNode)new InsnNode(176));
/* 52 */       methodNode.localVariables.add(new LocalVariableNode("this", "Lnet/minecraft/client/entity/EntityPlayerSP;", null, startLabelNode, endLabelNode, 0));
/*    */       
/* 54 */       methodNode.localVariables
/* 55 */         .add(new LocalVariableNode("partialTicks", "F", null, startLabelNode, endLabelNode, 1));
/* 56 */       methodNode.instructions.add(list);
/* 57 */       classNode.methods.add(methodNode);
/* 58 */       ClassWriter classWriter = new ClassWriter(0);
/* 59 */       classNode.accept((ClassVisitor)classWriter);
/* 60 */       FMLLog.log.warn("[Transformed:net.minecraft.client.entity.EntityPlayerSP]");
/* 61 */       return classWriter.toByteArray();
/*    */     } 
/* 63 */     return basicClass;
/*    */   }
/*    */ }


/* Location:              C:\Users\DuduM\OneDrive\Área de Trabalho\mw10\modularwarfare-shining-2023.2.4.4f-fix10.jar!\mchhui\modularmovements\coremod\minecraft\EntityPlayerSP.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */