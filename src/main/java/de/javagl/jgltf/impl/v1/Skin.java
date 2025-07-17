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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class Skin
/*     */   extends GlTFChildOfRootProperty
/*     */ {
/*     */   private float[] bindShapeMatrix;
/*     */   private String inverseBindMatrices;
/*     */   private List<String> jointNames;
/*     */   
/*     */   public void setBindShapeMatrix(float[] bindShapeMatrix) {
/*  66 */     if (bindShapeMatrix == null) {
/*  67 */       this.bindShapeMatrix = bindShapeMatrix;
/*     */       return;
/*     */     } 
/*  70 */     if (bindShapeMatrix.length < 16) {
/*  71 */       throw new IllegalArgumentException("Number of bindShapeMatrix elements is < 16");
/*     */     }
/*  73 */     if (bindShapeMatrix.length > 16) {
/*  74 */       throw new IllegalArgumentException("Number of bindShapeMatrix elements is > 16");
/*     */     }
/*  76 */     this.bindShapeMatrix = bindShapeMatrix;
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
/*     */   public float[] getBindShapeMatrix() {
/*  92 */     return this.bindShapeMatrix;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public float[] defaultBindShapeMatrix() {
/* 103 */     return new float[] { 1.0F, 0.0F, 0.0F, 0.0F, 0.0F, 1.0F, 0.0F, 0.0F, 0.0F, 0.0F, 1.0F, 0.0F, 0.0F, 0.0F, 0.0F, 1.0F };
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
/*     */   public void setInverseBindMatrices(String inverseBindMatrices) {
/* 115 */     if (inverseBindMatrices == null) {
/* 116 */       throw new NullPointerException("Invalid value for inverseBindMatrices: " + inverseBindMatrices + ", may not be null");
/*     */     }
/* 118 */     this.inverseBindMatrices = inverseBindMatrices;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getInverseBindMatrices() {
/* 129 */     return this.inverseBindMatrices;
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
/*     */   public void setJointNames(List<String> jointNames) {
/* 143 */     if (jointNames == null) {
/* 144 */       throw new NullPointerException("Invalid value for jointNames: " + jointNames + ", may not be null");
/*     */     }
/* 146 */     this.jointNames = jointNames;
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
/*     */   public List<String> getJointNames() {
/* 159 */     return this.jointNames;
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
/*     */   public void addJointNames(String element) {
/* 172 */     if (element == null) {
/* 173 */       throw new NullPointerException("The element may not be null");
/*     */     }
/* 175 */     List<String> oldList = this.jointNames;
/* 176 */     List<String> newList = new ArrayList<>();
/* 177 */     if (oldList != null) {
/* 178 */       newList.addAll(oldList);
/*     */     }
/* 180 */     newList.add(element);
/* 181 */     this.jointNames = newList;
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
/*     */   public void removeJointNames(String element) {
/* 194 */     if (element == null) {
/* 195 */       throw new NullPointerException("The element may not be null");
/*     */     }
/* 197 */     List<String> oldList = this.jointNames;
/* 198 */     List<String> newList = new ArrayList<>();
/* 199 */     if (oldList != null) {
/* 200 */       newList.addAll(oldList);
/*     */     }
/* 202 */     newList.remove(element);
/* 203 */     this.jointNames = newList;
/*     */   }
/*     */ }


/* Location:              C:\Users\DuduM\OneDrive\Área de Trabalho\mw10\modularwarfare-shining-2023.2.4.4f-fix10.jar!\de\javagl\jgltf\impl\v1\Skin.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */