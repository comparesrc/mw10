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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class Mesh
/*     */   extends GlTFChildOfRootProperty
/*     */ {
/*     */   private List<MeshPrimitive> primitives;
/*     */   private List<Float> weights;
/*     */   
/*     */   public void setPrimitives(List<MeshPrimitive> primitives) {
/*  62 */     if (primitives == null) {
/*  63 */       throw new NullPointerException("Invalid value for primitives: " + primitives + ", may not be null");
/*     */     }
/*  65 */     if (primitives.size() < 1) {
/*  66 */       throw new IllegalArgumentException("Number of primitives elements is < 1");
/*     */     }
/*  68 */     this.primitives = primitives;
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
/*     */   public List<MeshPrimitive> getPrimitives() {
/*  83 */     return this.primitives;
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
/*     */   public void addPrimitives(MeshPrimitive element) {
/*  96 */     if (element == null) {
/*  97 */       throw new NullPointerException("The element may not be null");
/*     */     }
/*  99 */     List<MeshPrimitive> oldList = this.primitives;
/* 100 */     List<MeshPrimitive> newList = new ArrayList<>();
/* 101 */     if (oldList != null) {
/* 102 */       newList.addAll(oldList);
/*     */     }
/* 104 */     newList.add(element);
/* 105 */     this.primitives = newList;
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
/*     */   public void removePrimitives(MeshPrimitive element) {
/* 118 */     if (element == null) {
/* 119 */       throw new NullPointerException("The element may not be null");
/*     */     }
/* 121 */     List<MeshPrimitive> oldList = this.primitives;
/* 122 */     List<MeshPrimitive> newList = new ArrayList<>();
/* 123 */     if (oldList != null) {
/* 124 */       newList.addAll(oldList);
/*     */     }
/* 126 */     newList.remove(element);
/* 127 */     this.primitives = newList;
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
/*     */ 
/*     */   
/*     */   public void setWeights(List<Float> weights) {
/* 144 */     if (weights == null) {
/* 145 */       this.weights = weights;
/*     */       return;
/*     */     } 
/* 148 */     if (weights.size() < 1) {
/* 149 */       throw new IllegalArgumentException("Number of weights elements is < 1");
/*     */     }
/* 151 */     this.weights = weights;
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
/*     */   public List<Float> getWeights() {
/* 166 */     return this.weights;
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
/*     */   public void addWeights(Float element) {
/* 179 */     if (element == null) {
/* 180 */       throw new NullPointerException("The element may not be null");
/*     */     }
/* 182 */     List<Float> oldList = this.weights;
/* 183 */     List<Float> newList = new ArrayList<>();
/* 184 */     if (oldList != null) {
/* 185 */       newList.addAll(oldList);
/*     */     }
/* 187 */     newList.add(element);
/* 188 */     this.weights = newList;
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
/*     */   public void removeWeights(Float element) {
/* 203 */     if (element == null) {
/* 204 */       throw new NullPointerException("The element may not be null");
/*     */     }
/* 206 */     List<Float> oldList = this.weights;
/* 207 */     List<Float> newList = new ArrayList<>();
/* 208 */     if (oldList != null) {
/* 209 */       newList.addAll(oldList);
/*     */     }
/* 211 */     newList.remove(element);
/* 212 */     if (newList.isEmpty()) {
/* 213 */       this.weights = null;
/*     */     } else {
/* 215 */       this.weights = newList;
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\DuduM\OneDrive\Área de Trabalho\mw10\modularwarfare-shining-2023.2.4.4f-fix10.jar!\de\javagl\jgltf\impl\v2\Mesh.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */