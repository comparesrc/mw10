/*    */ package com.modularwarfare.core.net.minecraft.entity.player;
/*    */ 
/*    */ import net.minecraft.launchwrapper.IClassTransformer;
/*    */ import net.minecraftforge.fml.common.FMLLog;
/*    */ import org.objectweb.asm.ClassReader;
/*    */ import org.objectweb.asm.ClassVisitor;
/*    */ import org.objectweb.asm.ClassWriter;
/*    */ import org.objectweb.asm.tree.AbstractInsnNode;
/*    */ import org.objectweb.asm.tree.ClassNode;
/*    */ import org.objectweb.asm.tree.InsnList;
/*    */ import org.objectweb.asm.tree.InsnNode;
/*    */ import org.objectweb.asm.tree.LabelNode;
/*    */ import org.objectweb.asm.tree.MethodInsnNode;
/*    */ import org.objectweb.asm.tree.MethodNode;
/*    */ import org.objectweb.asm.tree.VarInsnNode;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class EntityLivingBase
/*    */   implements IClassTransformer
/*    */ {
/*    */   public byte[] transform(String name, String transformedName, byte[] basicClass) {
/* 24 */     if (name.equals("net.minecraft.entity.EntityLivingBase") || name.equals("vp")) {
/* 25 */       FMLLog.getLogger().warn("[Transforming:net.minecraft.entity.EntityLivingBase]");
/* 26 */       ClassNode classNode = new ClassNode(327680);
/* 27 */       ClassReader classReader = new ClassReader(basicClass);
/* 28 */       classReader.accept((ClassVisitor)classNode, 0);
/* 29 */       classNode.methods.forEach(method -> {
/*    */             if (method.name.equals("updateElytra") || method.name.equals("func_184616_r") || method.name.equals("r")) {
/*    */               InsnList list = new InsnList();
/*    */               
/*    */               list.add((AbstractInsnNode)new LabelNode());
/*    */               
/*    */               list.add((AbstractInsnNode)new VarInsnNode(25, 0));
/*    */               list.add((AbstractInsnNode)new MethodInsnNode(184, "com/modularwarfare/core/MWFCoreHooks", "updateElytra", "(Lnet/minecraft/entity/EntityLivingBase;)V", false));
/*    */               list.add((AbstractInsnNode)new InsnNode(177));
/*    */               list.add((AbstractInsnNode)new LabelNode());
/*    */               method.instructions.clear();
/*    */               method.instructions.insert(list);
/*    */               FMLLog.getLogger().warn("[Transformed:updateElytra]");
/*    */             } 
/*    */           });
/* 44 */       ClassWriter classWriter = new ClassWriter(0);
/* 45 */       classNode.accept((ClassVisitor)classWriter);
/* 46 */       FMLLog.getLogger().warn("[Transformed:net.minecraft.entity.EntityLivingBase]");
/* 47 */       return classWriter.toByteArray();
/*    */     } 
/* 49 */     return basicClass;
/*    */   }
/*    */ }


/* Location:              C:\Users\DuduM\OneDrive\Área de Trabalho\mw10\modularwarfare-shining-2023.2.4.4f-fix10.jar!\com\modularwarfare\core\net\minecraft\entity\player\EntityLivingBase.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */