/*    */ package org.spongepowered.asm.mixin.gen;
/*    */ 
/*    */ import org.apache.logging.log4j.LogManager;
/*    */ import org.objectweb.asm.tree.AbstractInsnNode;
/*    */ import org.objectweb.asm.tree.FieldInsnNode;
/*    */ import org.objectweb.asm.tree.InsnNode;
/*    */ import org.objectweb.asm.tree.MethodNode;
/*    */ import org.objectweb.asm.tree.VarInsnNode;
/*    */ import org.spongepowered.asm.mixin.MixinEnvironment;
/*    */ import org.spongepowered.asm.mixin.transformer.ClassInfo;
/*    */ import org.spongepowered.asm.mixin.transformer.MixinTargetContext;
/*    */ import org.spongepowered.asm.util.Bytecode;
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
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class AccessorGeneratorFieldSetter
/*    */   extends AccessorGeneratorField
/*    */ {
/*    */   private boolean mutable;
/*    */   
/*    */   public AccessorGeneratorFieldSetter(AccessorInfo info) {
/* 50 */     super(info);
/*    */   }
/*    */ 
/*    */   
/*    */   public void validate() {
/* 55 */     super.validate();
/*    */     
/* 57 */     ClassInfo.Method method = this.info.getClassInfo().findMethod(this.info.getMethod());
/* 58 */     this.mutable = method.isDecoratedMutable();
/* 59 */     if (this.mutable || !Bytecode.hasFlag(this.targetField, 16)) {
/*    */       return;
/*    */     }
/*    */     
/* 63 */     if (this.info.getContext().getOption(MixinEnvironment.Option.DEBUG_VERBOSE)) {
/* 64 */       LogManager.getLogger("mixin").warn("{} for final field {}::{} is not @Mutable", new Object[] { this.info, ((MixinTargetContext)this.info
/* 65 */             .getContext()).getTarget(), this.targetField.name });
/*    */     }
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public MethodNode generate() {
/* 74 */     if (this.mutable) {
/* 75 */       this.targetField.access &= 0xFFFFFFEF;
/*    */     }
/*    */     
/* 78 */     int stackSpace = this.targetIsStatic ? 0 : 1;
/* 79 */     int maxLocals = stackSpace + this.targetType.getSize();
/* 80 */     int maxStack = stackSpace + this.targetType.getSize();
/* 81 */     MethodNode method = createMethod(maxLocals, maxStack);
/* 82 */     if (!this.targetIsStatic) {
/* 83 */       method.instructions.add((AbstractInsnNode)new VarInsnNode(25, 0));
/*    */     }
/* 85 */     method.instructions.add((AbstractInsnNode)new VarInsnNode(this.targetType.getOpcode(21), stackSpace));
/* 86 */     int opcode = this.targetIsStatic ? 179 : 181;
/* 87 */     method.instructions.add((AbstractInsnNode)new FieldInsnNode(opcode, (this.info.getClassNode()).name, this.targetField.name, this.targetField.desc));
/* 88 */     method.instructions.add((AbstractInsnNode)new InsnNode(177));
/* 89 */     return method;
/*    */   }
/*    */ }


/* Location:              C:\Users\DuduM\OneDrive\Área de Trabalho\mw10\modularwarfare-shining-2023.2.4.4f-fix10.jar!\org\spongepowered\asm\mixin\gen\AccessorGeneratorFieldSetter.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */