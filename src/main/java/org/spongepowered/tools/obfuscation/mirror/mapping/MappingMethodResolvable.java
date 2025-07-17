/*     */ package org.spongepowered.tools.obfuscation.mirror.mapping;
/*     */ 
/*     */ import org.spongepowered.asm.obfuscation.mapping.common.MappingMethod;
/*     */ import org.spongepowered.tools.obfuscation.mirror.TypeHandle;
/*     */ import org.spongepowered.tools.obfuscation.mirror.TypeUtils;
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
/*     */ public final class MappingMethodResolvable
/*     */   extends MappingMethod
/*     */ {
/*     */   private final TypeHandle ownerHandle;
/*     */   
/*     */   public MappingMethodResolvable(TypeHandle owner, String name, String desc) {
/*  41 */     super(owner.getName(), name, desc);
/*  42 */     this.ownerHandle = owner;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public MappingMethod getSuper() {
/*  51 */     if (this.ownerHandle == null) {
/*  52 */       return super.getSuper();
/*     */     }
/*     */     
/*  55 */     String name = getSimpleName();
/*  56 */     String desc = getDesc();
/*  57 */     String signature = TypeUtils.getJavaSignature(desc);
/*     */     
/*  59 */     TypeHandle superClass = this.ownerHandle.getSuperclass();
/*  60 */     if (superClass != null)
/*     */     {
/*  62 */       if (superClass.findMethod(name, signature) != null) {
/*  63 */         return superClass.getMappingMethod(name, desc);
/*     */       }
/*     */     }
/*     */ 
/*     */     
/*  68 */     for (TypeHandle iface : this.ownerHandle.getInterfaces()) {
/*  69 */       if (iface.findMethod(name, signature) != null) {
/*  70 */         return iface.getMappingMethod(name, desc);
/*     */       }
/*     */     } 
/*     */ 
/*     */     
/*  75 */     if (superClass != null) {
/*  76 */       return superClass.getMappingMethod(name, desc).getSuper();
/*     */     }
/*     */     
/*  79 */     return super.getSuper();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public MappingMethod move(TypeHandle newOwner) {
/*  90 */     return new MappingMethodResolvable(newOwner, getSimpleName(), getDesc());
/*     */   }
/*     */ 
/*     */   
/*     */   public MappingMethod remap(String newName) {
/*  95 */     return new MappingMethodResolvable(this.ownerHandle, newName, getDesc());
/*     */   }
/*     */ 
/*     */   
/*     */   public MappingMethod transform(String newDesc) {
/* 100 */     return new MappingMethodResolvable(this.ownerHandle, getSimpleName(), newDesc);
/*     */   }
/*     */ 
/*     */   
/*     */   public MappingMethod copy() {
/* 105 */     return new MappingMethodResolvable(this.ownerHandle, getSimpleName(), getDesc());
/*     */   }
/*     */ }


/* Location:              C:\Users\DuduM\OneDrive\Área de Trabalho\mw10\modularwarfare-shining-2023.2.4.4f-fix10.jar!\org\spongepowered\tools\obfuscation\mirror\mapping\MappingMethodResolvable.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */