/*     */ package org.spongepowered.asm.mixin.injection.points;
/*     */ 
/*     */ import java.util.Collection;
/*     */ import java.util.ListIterator;
/*     */ import org.objectweb.asm.Type;
/*     */ import org.objectweb.asm.tree.AbstractInsnNode;
/*     */ import org.objectweb.asm.tree.InsnList;
/*     */ import org.spongepowered.asm.mixin.injection.IInjectionPointContext;
/*     */ import org.spongepowered.asm.mixin.injection.InjectionPoint;
/*     */ import org.spongepowered.asm.mixin.injection.InjectionPoint.AtCode;
/*     */ import org.spongepowered.asm.mixin.injection.struct.InjectionPointData;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ @AtCode("RETURN")
/*     */ public class BeforeReturn
/*     */   extends InjectionPoint
/*     */ {
/*     */   private final int ordinal;
/*     */   
/*     */   public BeforeReturn(InjectionPointData data) {
/*  78 */     super(data);
/*     */     
/*  80 */     this.ordinal = data.getOrdinal();
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean checkPriority(int targetPriority, int ownerPriority) {
/*  85 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public InjectionPoint.RestrictTargetLevel getTargetRestriction(IInjectionPointContext context) {
/*  90 */     return InjectionPoint.RestrictTargetLevel.ALLOW_ALL;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean find(String desc, InsnList insns, Collection<AbstractInsnNode> nodes) {
/*  95 */     boolean found = false;
/*     */ 
/*     */     
/*  98 */     int returnOpcode = Type.getReturnType(desc).getOpcode(172);
/*  99 */     int ordinal = 0;
/*     */     
/* 101 */     ListIterator<AbstractInsnNode> iter = insns.iterator();
/* 102 */     while (iter.hasNext()) {
/* 103 */       AbstractInsnNode insn = iter.next();
/*     */       
/* 105 */       if (insn instanceof org.objectweb.asm.tree.InsnNode && insn.getOpcode() == returnOpcode) {
/* 106 */         if (this.ordinal == -1 || this.ordinal == ordinal) {
/* 107 */           nodes.add(insn);
/* 108 */           found = true;
/*     */         } 
/*     */         
/* 111 */         ordinal++;
/*     */       } 
/*     */     } 
/*     */     
/* 115 */     return found;
/*     */   }
/*     */ }


/* Location:              C:\Users\DuduM\OneDrive\Área de Trabalho\mw10\modularwarfare-shining-2023.2.4.4f-fix10.jar!\org\spongepowered\asm\mixin\injection\points\BeforeReturn.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */