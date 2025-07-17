/*     */ package de.javagl.jgltf.model.impl;
/*     */ 
/*     */ import de.javagl.jgltf.model.ModelElement;
/*     */ import java.util.LinkedHashMap;
/*     */ import java.util.Map;
/*     */ import java.util.Objects;
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
/*     */ public class AbstractModelElement
/*     */   implements ModelElement
/*     */ {
/*     */   private Map<String, Object> extensions;
/*     */   private Object extras;
/*     */   
/*     */   public void setExtensions(Map<String, Object> extensions) {
/*  57 */     this.extensions = extensions;
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
/*     */   
/*     */   public void addExtension(String name, Object extension) {
/*  71 */     Objects.requireNonNull(name, "The name may not be null");
/*  72 */     if (this.extensions == null)
/*     */     {
/*  74 */       this.extensions = new LinkedHashMap<>();
/*     */     }
/*  76 */     this.extensions.put(name, extension);
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
/*     */   public void removeExtension(String name) {
/*  89 */     if (this.extensions != null) {
/*     */       
/*  91 */       this.extensions.remove(name);
/*  92 */       if (this.extensions.isEmpty())
/*     */       {
/*  94 */         this.extensions = null;
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setExtras(Object extras) {
/* 107 */     this.extras = extras;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public Map<String, Object> getExtensions() {
/* 113 */     return this.extensions;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public Object getExtras() {
/* 119 */     return this.extras;
/*     */   }
/*     */ }


/* Location:              C:\Users\DuduM\OneDrive\Área de Trabalho\mw10\modularwarfare-shining-2023.2.4.4f-fix10.jar!\de\javagl\jgltf\model\impl\AbstractModelElement.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */