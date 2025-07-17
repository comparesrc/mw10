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
/*     */ import org.spongepowered.asm.mixin.injection.throwables.InvalidInjectionException;
/*     */ import org.spongepowered.asm.mixin.refmap.IMixinContext;
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
/*     */ @AtCode("TAIL")
/*     */ public class BeforeFinalReturn
/*     */   extends InjectionPoint
/*     */ {
/*     */   private final IMixinContext context;
/*     */   
/*     */   public BeforeFinalReturn(InjectionPointData data) {
/*  65 */     super(data);
/*     */     
/*  67 */     this.context = data.getContext();
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean checkPriority(int targetPriority, int ownerPriority) {
/*  72 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public InjectionPoint.RestrictTargetLevel getTargetRestriction(IInjectionPointContext context) {
/*  77 */     return InjectionPoint.RestrictTargetLevel.ALLOW_ALL;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean find(String desc, InsnList insns, Collection<AbstractInsnNode> nodes) {
/*  82 */     AbstractInsnNode ret = null;
/*     */ 
/*     */     
/*  85 */     int returnOpcode = Type.getReturnType(desc).getOpcode(172);
/*     */     
/*  87 */     ListIterator<AbstractInsnNode> iter = insns.iterator();
/*  88 */     while (iter.hasNext()) {
/*  89 */       AbstractInsnNode insn = iter.next();
/*  90 */       if (insn instanceof org.objectweb.asm.tree.InsnNode && insn.getOpcode() == returnOpcode) {
/*  91 */         ret = insn;
/*     */       }
/*     */     } 
/*     */ 
/*     */     
/*  96 */     if (ret == null) {
/*  97 */       throw new InvalidInjectionException(this.context, "TAIL could not locate a valid RETURN in the target method!");
/*     */     }
/*     */     
/* 100 */     nodes.add(ret);
/* 101 */     return true;
/*     */   }
/*     */ }


/* Location:              C:\Users\DuduM\OneDrive\Área de Trabalho\mw10\modularwarfare-shining-2023.2.4.4f-fix10.jar!\org\spongepowered\asm\mixin\injection\points\BeforeFinalReturn.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */