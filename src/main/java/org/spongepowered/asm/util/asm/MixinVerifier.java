/*     */ package org.spongepowered.asm.util.asm;
/*     */ 
/*     */ import java.util.List;
/*     */ import org.objectweb.asm.Type;
/*     */ import org.objectweb.asm.tree.analysis.SimpleVerifier;
/*     */ import org.spongepowered.asm.mixin.transformer.ClassInfo;
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
/*     */ public class MixinVerifier
/*     */   extends SimpleVerifier
/*     */ {
/*     */   private Type currentClass;
/*     */   private Type currentSuperClass;
/*     */   private List<Type> currentClassInterfaces;
/*     */   private boolean isInterface;
/*     */   
/*     */   public MixinVerifier(int api, Type currentClass, Type currentSuperClass, List<Type> currentClassInterfaces, boolean isInterface) {
/*  45 */     super(api, currentClass, currentSuperClass, currentClassInterfaces, isInterface);
/*  46 */     this.currentClass = currentClass;
/*  47 */     this.currentSuperClass = currentSuperClass;
/*  48 */     this.currentClassInterfaces = currentClassInterfaces;
/*  49 */     this.isInterface = isInterface;
/*     */   }
/*     */ 
/*     */   
/*     */   protected boolean isInterface(Type type) {
/*  54 */     if (this.currentClass != null && type.equals(this.currentClass)) {
/*  55 */       return this.isInterface;
/*     */     }
/*  57 */     return ClassInfo.forType(type, ClassInfo.TypeLookup.ELEMENT_TYPE).isInterface();
/*     */   }
/*     */ 
/*     */   
/*     */   protected Type getSuperClass(Type type) {
/*  62 */     if (this.currentClass != null && type.equals(this.currentClass)) {
/*  63 */       return this.currentSuperClass;
/*     */     }
/*  65 */     ClassInfo c = ClassInfo.forType(type, ClassInfo.TypeLookup.ELEMENT_TYPE).getSuperClass();
/*  66 */     return (c == null) ? null : Type.getType("L" + c.getName() + ";");
/*     */   }
/*     */ 
/*     */   
/*     */   protected boolean isAssignableFrom(Type type, Type other) {
/*  71 */     if (type.equals(other)) {
/*  72 */       return true;
/*     */     }
/*  74 */     if (this.currentClass != null && type.equals(this.currentClass)) {
/*  75 */       if (getSuperClass(other) == null) {
/*  76 */         return false;
/*     */       }
/*  78 */       if (this.isInterface) {
/*  79 */         return (other.getSort() == 10 || other.getSort() == 9);
/*     */       }
/*  81 */       return isAssignableFrom(type, getSuperClass(other));
/*     */     } 
/*  83 */     if (this.currentClass != null && other.equals(this.currentClass)) {
/*  84 */       if (isAssignableFrom(type, this.currentSuperClass)) {
/*  85 */         return true;
/*     */       }
/*  87 */       if (this.currentClassInterfaces != null) {
/*  88 */         for (int i = 0; i < this.currentClassInterfaces.size(); i++) {
/*  89 */           Type v = this.currentClassInterfaces.get(i);
/*  90 */           if (isAssignableFrom(type, v)) {
/*  91 */             return true;
/*     */           }
/*     */         } 
/*     */       }
/*  95 */       return false;
/*     */     } 
/*  97 */     ClassInfo typeInfo = ClassInfo.forType(type, ClassInfo.TypeLookup.ELEMENT_TYPE);
/*  98 */     if (typeInfo == null) {
/*  99 */       return false;
/*     */     }
/* 101 */     if (typeInfo.isInterface()) {
/* 102 */       typeInfo = ClassInfo.forName("java/lang/Object");
/*     */     }
/* 104 */     return ClassInfo.forType(other, ClassInfo.TypeLookup.ELEMENT_TYPE).hasSuperClass(typeInfo);
/*     */   }
/*     */ }


/* Location:              C:\Users\DuduM\OneDrive\Área de Trabalho\mw10\modularwarfare-shining-2023.2.4.4f-fix10.jar!\org\spongepowered\as\\util\asm\MixinVerifier.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */