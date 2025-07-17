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
/*     */ public class Scene
/*     */   extends GlTFChildOfRootProperty
/*     */ {
/*     */   private List<String> nodes;
/*     */   
/*     */   public void setNodes(List<String> nodes) {
/*  44 */     if (nodes == null) {
/*  45 */       this.nodes = nodes;
/*     */       return;
/*     */     } 
/*  48 */     this.nodes = nodes;
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
/*     */   public List<String> getNodes() {
/*  61 */     return this.nodes;
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
/*     */   public void addNodes(String element) {
/*  74 */     if (element == null) {
/*  75 */       throw new NullPointerException("The element may not be null");
/*     */     }
/*  77 */     List<String> oldList = this.nodes;
/*  78 */     List<String> newList = new ArrayList<>();
/*  79 */     if (oldList != null) {
/*  80 */       newList.addAll(oldList);
/*     */     }
/*  82 */     newList.add(element);
/*  83 */     this.nodes = newList;
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
/*     */   public void removeNodes(String element) {
/*  98 */     if (element == null) {
/*  99 */       throw new NullPointerException("The element may not be null");
/*     */     }
/* 101 */     List<String> oldList = this.nodes;
/* 102 */     List<String> newList = new ArrayList<>();
/* 103 */     if (oldList != null) {
/* 104 */       newList.addAll(oldList);
/*     */     }
/* 106 */     newList.remove(element);
/* 107 */     if (newList.isEmpty()) {
/* 108 */       this.nodes = null;
/*     */     } else {
/* 110 */       this.nodes = newList;
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
/*     */   public List<String> defaultNodes() {
/* 122 */     return new ArrayList<>();
/*     */   }
/*     */ }


/* Location:              C:\Users\DuduM\OneDrive\Área de Trabalho\mw10\modularwarfare-shining-2023.2.4.4f-fix10.jar!\de\javagl\jgltf\impl\v1\Scene.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */