/*    */ package mchhui.modularmovements.coremod.minecraft;
/*    */ import net.minecraftforge.fml.common.FMLLog;
/*    */ import org.objectweb.asm.ClassReader;
/*    */ import org.objectweb.asm.ClassVisitor;
/*    */ import org.objectweb.asm.ClassWriter;
/*    */ import org.objectweb.asm.tree.AbstractInsnNode;
/*    */ import org.objectweb.asm.tree.ClassNode;
/*    */ import org.objectweb.asm.tree.InsnList;
/*    */ import org.objectweb.asm.tree.MethodNode;
/*    */ import org.objectweb.asm.tree.VarInsnNode;
/*    */ 
/*    */ public class Entity implements IClassTransformer {
/*    */   public byte[] transform(String name, String transformedName, byte[] basicClass) {
/* 14 */     if (transformedName.equals("net.minecraft.entity.Entity")) {
/* 15 */       FMLLog.log.warn("[net.minecraft.entity.Entity]");
/* 16 */       ClassNode classNode = new ClassNode(327680);
/* 17 */       ClassReader classReader = new ClassReader(basicClass);
/* 18 */       classReader.accept((ClassVisitor)classNode, 0);
/* 19 */       for (MethodNode method : classNode.methods) {
/* 20 */         if (method.name.equals("setEntityBoundingBox")) {
/* 21 */           InsnList list = new InsnList();
/* 22 */           list.add((AbstractInsnNode)new VarInsnNode(25, 0));
/* 23 */           list.add((AbstractInsnNode)new VarInsnNode(25, 1));
/* 24 */           list.add((AbstractInsnNode)new MethodInsnNode(184, "mchhui/modularmovements/coremod/ModularMovementsHooks", "getEntityBoundingBox", "(Lnet/minecraft/entity/Entity;Lnet/minecraft/util/math/AxisAlignedBB;)Lnet/minecraft/util/math/AxisAlignedBB;", false));
/*    */ 
/*    */ 
/*    */           
/* 28 */           list.add((AbstractInsnNode)new VarInsnNode(58, 1));
/* 29 */           list.add((AbstractInsnNode)new LabelNode());
/* 30 */           method.instructions.insert(method.instructions.getFirst(), list);
/*    */         } 
/*    */       } 
/* 33 */       ClassWriter classWriter = new ClassWriter(0);
/* 34 */       classNode.accept((ClassVisitor)classWriter);
/* 35 */       FMLLog.log.warn("[net.minecraft.entity.Entity]");
/* 36 */       return classWriter.toByteArray();
/*    */     } 
/* 38 */     return basicClass;
/*    */   }
/*    */ }


/* Location:              C:\Users\DuduM\OneDrive\Área de Trabalho\mw10\modularwarfare-shining-2023.2.4.4f-fix10.jar!\mchhui\modularmovements\coremod\minecraft\Entity.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */