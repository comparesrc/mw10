/*     */ package de.javagl.jgltf.impl.v1;
/*     */ 
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
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
/*     */ public class TechniqueStates
/*     */   extends GlTFProperty
/*     */ {
/*     */   private List<Integer> enable;
/*     */   private TechniqueStatesFunctions functions;
/*     */   
/*     */   public void setEnable(List<Integer> enable) {
/*  54 */     if (enable == null) {
/*  55 */       this.enable = enable;
/*     */       return;
/*     */     } 
/*  58 */     for (Integer enableElement : enable) {
/*  59 */       if (enableElement.intValue() != 3042 && enableElement.intValue() != 2884 && enableElement.intValue() != 2929 && enableElement.intValue() != 32823 && enableElement.intValue() != 32926 && enableElement.intValue() != 3089) {
/*  60 */         throw new IllegalArgumentException("Invalid value for enableElement: " + enableElement + ", valid: [3042, 2884, 2929, 32823, 32926, 3089]");
/*     */       }
/*     */     } 
/*  63 */     this.enable = enable;
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
/*     */   public List<Integer> getEnable() {
/*  77 */     return this.enable;
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
/*     */   public void addEnable(Integer element) {
/*  90 */     if (element == null) {
/*  91 */       throw new NullPointerException("The element may not be null");
/*     */     }
/*  93 */     List<Integer> oldList = this.enable;
/*  94 */     List<Integer> newList = new ArrayList<>();
/*  95 */     if (oldList != null) {
/*  96 */       newList.addAll(oldList);
/*     */     }
/*  98 */     newList.add(element);
/*  99 */     this.enable = newList;
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
/*     */   
/*     */   public void removeEnable(Integer element) {
/* 114 */     if (element == null) {
/* 115 */       throw new NullPointerException("The element may not be null");
/*     */     }
/* 117 */     List<Integer> oldList = this.enable;
/* 118 */     List<Integer> newList = new ArrayList<>();
/* 119 */     if (oldList != null) {
/* 120 */       newList.addAll(oldList);
/*     */     }
/* 122 */     newList.remove(element);
/* 123 */     if (newList.isEmpty()) {
/* 124 */       this.enable = null;
/*     */     } else {
/* 126 */       this.enable = newList;
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
/*     */   public List<Integer> defaultEnable() {
/* 138 */     return new ArrayList<>();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setFunctions(TechniqueStatesFunctions functions) {
/* 149 */     if (functions == null) {
/* 150 */       this.functions = functions;
/*     */       return;
/*     */     } 
/* 153 */     this.functions = functions;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public TechniqueStatesFunctions getFunctions() {
/* 164 */     return this.functions;
/*     */   }
/*     */ }


/* Location:              C:\Users\DuduM\OneDrive\Área de Trabalho\mw10\modularwarfare-shining-2023.2.4.4f-fix10.jar!\de\javagl\jgltf\impl\v1\TechniqueStates.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */