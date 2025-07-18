/*     */ package org.spongepowered.asm.mixin.transformer;
/*     */ 
/*     */ import java.util.HashSet;
/*     */ import java.util.Iterator;
/*     */ import java.util.Set;
/*     */ import org.objectweb.asm.tree.AbstractInsnNode;
/*     */ import org.objectweb.asm.tree.ClassNode;
/*     */ import org.objectweb.asm.tree.MethodInsnNode;
/*     */ import org.objectweb.asm.tree.MethodNode;
/*     */ import org.spongepowered.asm.mixin.struct.MemberRef;
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
/*     */ abstract class ClassContext
/*     */ {
/*  50 */   private final Set<ClassInfo.Method> upgradedMethods = new HashSet<>();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   abstract String getClassRef();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   abstract ClassNode getClassNode();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   abstract ClassInfo getClassInfo();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   void addUpgradedMethod(MethodNode method) {
/*  80 */     ClassInfo.Method md = getClassInfo().findMethod(method);
/*  81 */     if (md == null)
/*     */     {
/*  83 */       throw new IllegalStateException("Meta method for " + method.name + " not located in " + this);
/*     */     }
/*  85 */     this.upgradedMethods.add(md);
/*     */   }
/*     */   
/*     */   protected void upgradeMethods() {
/*  89 */     for (MethodNode method : (getClassNode()).methods) {
/*  90 */       upgradeMethod(method);
/*     */     }
/*     */   }
/*     */   
/*     */   private void upgradeMethod(MethodNode method) {
/*  95 */     for (Iterator<AbstractInsnNode> iter = method.instructions.iterator(); iter.hasNext(); ) {
/*  96 */       AbstractInsnNode insn = iter.next();
/*  97 */       if (!(insn instanceof MethodInsnNode)) {
/*     */         continue;
/*     */       }
/*     */       
/* 101 */       MemberRef.Method method1 = new MemberRef.Method((MethodInsnNode)insn);
/* 102 */       if (method1.getOwner().equals(getClassRef())) {
/* 103 */         ClassInfo.Method md = getClassInfo().findMethod(method1.getName(), method1.getDesc(), 10);
/* 104 */         upgradeMethodRef(method, (MemberRef)method1, md);
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   protected void upgradeMethodRef(MethodNode containingMethod, MemberRef methodRef, ClassInfo.Method method) {
/* 110 */     if (methodRef.getOpcode() != 183) {
/*     */       return;
/*     */     }
/*     */     
/* 114 */     if (this.upgradedMethods.contains(method))
/* 115 */       methodRef.setOpcode(182); 
/*     */   }
/*     */ }


/* Location:              C:\Users\DuduM\OneDrive\Área de Trabalho\mw10\modularwarfare-shining-2023.2.4.4f-fix10.jar!\org\spongepowered\asm\mixin\transformer\ClassContext.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */