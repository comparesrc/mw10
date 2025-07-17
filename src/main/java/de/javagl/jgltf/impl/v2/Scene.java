/*     */ package de.javagl.jgltf.impl.v2;
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
/*     */ public class Scene
/*     */   extends GlTFChildOfRootProperty
/*     */ {
/*     */   private List<Integer> nodes;
/*     */   
/*     */   public void setNodes(List<Integer> nodes) {
/*  48 */     if (nodes == null) {
/*  49 */       this.nodes = nodes;
/*     */       return;
/*     */     } 
/*  52 */     if (nodes.size() < 1) {
/*  53 */       throw new IllegalArgumentException("Number of nodes elements is < 1");
/*     */     }
/*  55 */     for (Integer nodesElement : nodes) {
/*  56 */       if (nodesElement.intValue() < 0) {
/*  57 */         throw new IllegalArgumentException("nodesElement < 0");
/*     */       }
/*     */     } 
/*  60 */     this.nodes = nodes;
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
/*     */   public List<Integer> getNodes() {
/*  74 */     return this.nodes;
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
/*     */   public void addNodes(Integer element) {
/*  87 */     if (element == null) {
/*  88 */       throw new NullPointerException("The element may not be null");
/*     */     }
/*  90 */     List<Integer> oldList = this.nodes;
/*  91 */     List<Integer> newList = new ArrayList<>();
/*  92 */     if (oldList != null) {
/*  93 */       newList.addAll(oldList);
/*     */     }
/*  95 */     newList.add(element);
/*  96 */     this.nodes = newList;
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
/*     */   public void removeNodes(Integer element) {
/* 111 */     if (element == null) {
/* 112 */       throw new NullPointerException("The element may not be null");
/*     */     }
/* 114 */     List<Integer> oldList = this.nodes;
/* 115 */     List<Integer> newList = new ArrayList<>();
/* 116 */     if (oldList != null) {
/* 117 */       newList.addAll(oldList);
/*     */     }
/* 119 */     newList.remove(element);
/* 120 */     if (newList.isEmpty()) {
/* 121 */       this.nodes = null;
/*     */     } else {
/* 123 */       this.nodes = newList;
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\DuduM\OneDrive\Área de Trabalho\mw10\modularwarfare-shining-2023.2.4.4f-fix10.jar!\de\javagl\jgltf\impl\v2\Scene.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */