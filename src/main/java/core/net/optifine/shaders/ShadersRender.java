/*    */ package com.modularwarfare.core.net.optifine.shaders;
/*    */ 
/*    */ import net.minecraft.launchwrapper.IClassTransformer;
/*    */ import net.minecraftforge.fml.common.FMLLog;
/*    */ import org.objectweb.asm.ClassReader;
/*    */ import org.objectweb.asm.ClassVisitor;
/*    */ import org.objectweb.asm.ClassWriter;
/*    */ import org.objectweb.asm.tree.AbstractInsnNode;
/*    */ import org.objectweb.asm.tree.ClassNode;
/*    */ import org.objectweb.asm.tree.InsnList;
/*    */ import org.objectweb.asm.tree.LabelNode;
/*    */ import org.objectweb.asm.tree.MethodInsnNode;
/*    */ import org.objectweb.asm.tree.MethodNode;
/*    */ 
/*    */ 
/*    */ public class ShadersRender
/*    */   implements IClassTransformer
/*    */ {
/*    */   public byte[] transform(String name, String transformedName, byte[] basicClass) {
/* 20 */     if (name.equals("net.optifine.shaders.ShadersRender")) {
/* 21 */       FMLLog.getLogger().warn("[Transforming:net.optifine.shaders.ShadersRender]");
/* 22 */       ClassNode classNode = new ClassNode(327680);
/* 23 */       ClassReader classReader = new ClassReader(basicClass);
/* 24 */       classReader.accept((ClassVisitor)classNode, 0);
/* 25 */       for (MethodNode method : classNode.methods) {
/* 26 */         if (method.name.equals("renderHand0")) {
/* 27 */           InsnList list = new InsnList();
/* 28 */           list.add((AbstractInsnNode)new LabelNode());
/* 29 */           list.add((AbstractInsnNode)new MethodInsnNode(184, "com/modularwarfare/core/MWFCoreHooks", "onRender0", "()V", false));
/* 30 */           method.instructions.insertBefore(method.instructions.getFirst(), list);
/*    */         } 
/* 32 */         if (method.name.equals("renderHand1")) {
/* 33 */           InsnList list = new InsnList();
/* 34 */           list.add((AbstractInsnNode)new LabelNode());
/* 35 */           list.add((AbstractInsnNode)new MethodInsnNode(184, "com/modularwarfare/core/MWFCoreHooks", "onRender1", "()V", false));
/* 36 */           method.instructions.insertBefore(method.instructions.getFirst(), list);
/*    */         } 
/*    */       } 
/* 39 */       ClassWriter classWriter = new ClassWriter(2);
/* 40 */       classNode.accept((ClassVisitor)classWriter);
/* 41 */       FMLLog.getLogger().warn("[Transformed:net.optifine.shaders.ShadersRender]");
/* 42 */       return classWriter.toByteArray();
/*    */     } 
/* 44 */     return basicClass;
/*    */   }
/*    */ }


/* Location:              C:\Users\DuduM\OneDrive\Área de Trabalho\mw9\modularwarfare-shining-2023.2.4.4f-fix9.jar!\com\modularwarfare\core\net\optifine\shaders\ShadersRender.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */