/*    */ package org.spongepowered.asm.mixin.injection.points;
/*    */ 
/*    */ import java.util.Collection;
/*    */ import org.objectweb.asm.tree.AbstractInsnNode;
/*    */ import org.objectweb.asm.tree.InsnList;
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
/*    */ @AtCode("HEAD")
/*    */ public class MethodHead
/*    */   extends InjectionPoint
/*    */ {
/*    */   public MethodHead(InjectionPointData data) {
/* 50 */     super(data);
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean checkPriority(int targetPriority, int ownerPriority) {
/* 55 */     return true;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean find(String desc, InsnList insns, Collection<AbstractInsnNode> nodes) {
/* 60 */     nodes.add(insns.getFirst());
/* 61 */     return true;
/*    */   }
/*    */ }


/* Location:              C:\Users\DuduM\OneDrive\Área de Trabalho\mw10\modularwarfare-shining-2023.2.4.4f-fix10.jar!\org\spongepowered\asm\mixin\injection\points\MethodHead.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */