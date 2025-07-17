/*     */ package de.javagl.jgltf.model.impl;
/*     */ 
/*     */ import de.javagl.jgltf.model.ExtensionsModel;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collection;
/*     */ import java.util.Collections;
/*     */ import java.util.LinkedHashSet;
/*     */ import java.util.List;
/*     */ import java.util.Set;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ public class DefaultExtensionsModel
/*     */   implements ExtensionsModel
/*     */ {
/*  75 */   private final Set<String> extensionsUsed = new LinkedHashSet<>();
/*  76 */   private final Set<String> extensionsRequired = new LinkedHashSet<>();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void addExtensionUsed(String extension) {
/*  86 */     this.extensionsUsed.add(extension);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void removeExtensionUsed(String extension) {
/*  97 */     this.extensionsUsed.remove(extension);
/*  98 */     removeExtensionRequired(extension);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void addExtensionsUsed(Collection<String> extensions) {
/* 108 */     if (extensions != null)
/*     */     {
/* 110 */       this.extensionsUsed.addAll(extensions);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void clearExtensionUsed() {
/* 119 */     this.extensionsUsed.clear();
/* 120 */     clearExtensionRequired();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public List<String> getExtensionsUsed() {
/* 126 */     return Collections.unmodifiableList(new ArrayList<>(this.extensionsUsed));
/*     */   }
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
/*     */   public void addExtensionRequired(String extension) {
/* 139 */     this.extensionsRequired.add(extension);
/* 140 */     addExtensionUsed(extension);
/*     */   }
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
/*     */   public void removeExtensionRequired(String extension) {
/* 153 */     this.extensionsRequired.remove(extension);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void addExtensionsRequired(Collection<String> extensions) {
/* 164 */     if (extensions != null) {
/*     */       
/* 166 */       this.extensionsRequired.addAll(extensions);
/* 167 */       addExtensionsUsed(extensions);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void clearExtensionRequired() {
/* 176 */     this.extensionsRequired.clear();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public List<String> getExtensionsRequired() {
/* 182 */     return Collections.unmodifiableList(new ArrayList<>(this.extensionsRequired));
/*     */   }
/*     */ }


/* Location:              C:\Users\DuduM\OneDrive\Área de Trabalho\mw10\modularwarfare-shining-2023.2.4.4f-fix10.jar!\de\javagl\jgltf\model\impl\DefaultExtensionsModel.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */