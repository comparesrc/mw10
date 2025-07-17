/*    */ package org.spongepowered.asm.mixin.injection.points;
/*    */ 
/*    */ import java.util.Collection;
/*    */ import org.objectweb.asm.Type;
/*    */ import org.objectweb.asm.tree.AbstractInsnNode;
/*    */ import org.objectweb.asm.tree.InsnList;
/*    */ import org.objectweb.asm.tree.MethodInsnNode;
/*    */ import org.spongepowered.asm.mixin.injection.InjectionPoint;
/*    */ import org.spongepowered.asm.mixin.injection.InjectionPoint.AtCode;
/*    */ import org.spongepowered.asm.mixin.injection.struct.InjectionPointData;
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
/*    */ @AtCode("INVOKE_ASSIGN")
/*    */ public class AfterInvoke
/*    */   extends BeforeInvoke
/*    */ {
/*    */   public AfterInvoke(InjectionPointData data) {
/* 74 */     super(data);
/*    */   }
/*    */ 
/*    */   
/*    */   protected boolean addInsn(InsnList insns, Collection<AbstractInsnNode> nodes, AbstractInsnNode insn) {
/* 79 */     MethodInsnNode methodNode = (MethodInsnNode)insn;
/* 80 */     if (Type.getReturnType(methodNode.desc) == Type.VOID_TYPE) {
/* 81 */       return false;
/*    */     }
/*    */     
/* 84 */     insn = InjectionPoint.nextNode(insns, insn);
/* 85 */     if (insn instanceof org.objectweb.asm.tree.VarInsnNode && insn.getOpcode() >= 54) {
/* 86 */       insn = InjectionPoint.nextNode(insns, insn);
/*    */     }
/*    */     
/* 89 */     nodes.add(insn);
/* 90 */     return true;
/*    */   }
/*    */ }


/* Location:              C:\Users\DuduM\OneDrive\Área de Trabalho\mw10\modularwarfare-shining-2023.2.4.4f-fix10.jar!\org\spongepowered\asm\mixin\injection\points\AfterInvoke.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */