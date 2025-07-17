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
/*     */ public class Mesh
/*     */   extends GlTFChildOfRootProperty
/*     */ {
/*     */   private List<MeshPrimitive> primitives;
/*     */   
/*     */   public void setPrimitives(List<MeshPrimitive> primitives) {
/*  49 */     if (primitives == null) {
/*  50 */       this.primitives = primitives;
/*     */       return;
/*     */     } 
/*  53 */     this.primitives = primitives;
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
/*  68 */     return this.primitives;
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
/*  81 */     if (element == null) {
/*  82 */       throw new NullPointerException("The element may not be null");
/*     */     }
/*  84 */     List<MeshPrimitive> oldList = this.primitives;
/*  85 */     List<MeshPrimitive> newList = new ArrayList<>();
/*  86 */     if (oldList != null) {
/*  87 */       newList.addAll(oldList);
/*     */     }
/*  89 */     newList.add(element);
/*  90 */     this.primitives = newList;
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
/*     */   public void removePrimitives(MeshPrimitive element) {
/* 105 */     if (element == null) {
/* 106 */       throw new NullPointerException("The element may not be null");
/*     */     }
/* 108 */     List<MeshPrimitive> oldList = this.primitives;
/* 109 */     List<MeshPrimitive> newList = new ArrayList<>();
/* 110 */     if (oldList != null) {
/* 111 */       newList.addAll(oldList);
/*     */     }
/* 113 */     newList.remove(element);
/* 114 */     if (newList.isEmpty()) {
/* 115 */       this.primitives = null;
/*     */     } else {
/* 117 */       this.primitives = newList;
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
/*     */   public List<MeshPrimitive> defaultPrimitives() {
/* 129 */     return new ArrayList<>();
/*     */   }
/*     */ }


/* Location:              C:\Users\DuduM\OneDrive\Área de Trabalho\mw10\modularwarfare-shining-2023.2.4.4f-fix10.jar!\de\javagl\jgltf\impl\v1\Mesh.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */