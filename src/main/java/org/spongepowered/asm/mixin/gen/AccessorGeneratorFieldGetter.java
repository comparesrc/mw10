/*    */ package org.spongepowered.asm.mixin.gen;
/*    */ 
/*    */ import org.objectweb.asm.tree.AbstractInsnNode;
/*    */ import org.objectweb.asm.tree.FieldInsnNode;
/*    */ import org.objectweb.asm.tree.InsnNode;
/*    */ import org.objectweb.asm.tree.MethodNode;
/*    */ import org.objectweb.asm.tree.VarInsnNode;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class AccessorGeneratorFieldGetter
/*    */   extends AccessorGeneratorField
/*    */ {
/*    */   public AccessorGeneratorFieldGetter(AccessorInfo info) {
/* 39 */     super(info);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public MethodNode generate() {
/* 47 */     MethodNode method = createMethod(this.targetType.getSize(), this.targetType.getSize());
/* 48 */     if (!this.targetIsStatic) {
/* 49 */       method.instructions.add((AbstractInsnNode)new VarInsnNode(25, 0));
/*    */     }
/* 51 */     int opcode = this.targetIsStatic ? 178 : 180;
/* 52 */     method.instructions.add((AbstractInsnNode)new FieldInsnNode(opcode, (this.info.getClassNode()).name, this.targetField.name, this.targetField.desc));
/* 53 */     method.instructions.add((AbstractInsnNode)new InsnNode(this.targetType.getOpcode(172)));
/* 54 */     return method;
/*    */   }
/*    */ }


/* Location:              C:\Users\DuduM\OneDrive\Área de Trabalho\mw10\modularwarfare-shining-2023.2.4.4f-fix10.jar!\org\spongepowered\asm\mixin\gen\AccessorGeneratorFieldGetter.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */