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
/*     */ public class Skin
/*     */   extends GlTFChildOfRootProperty
/*     */ {
/*     */   private Integer inverseBindMatrices;
/*     */   private Integer skeleton;
/*     */   private List<Integer> joints;
/*     */   
/*     */   public void setInverseBindMatrices(Integer inverseBindMatrices) {
/*  54 */     if (inverseBindMatrices == null) {
/*  55 */       this.inverseBindMatrices = inverseBindMatrices;
/*     */       return;
/*     */     } 
/*  58 */     this.inverseBindMatrices = inverseBindMatrices;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Integer getInverseBindMatrices() {
/*  69 */     return this.inverseBindMatrices;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setSkeleton(Integer skeleton) {
/*  79 */     if (skeleton == null) {
/*  80 */       this.skeleton = skeleton;
/*     */       return;
/*     */     } 
/*  83 */     this.skeleton = skeleton;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Integer getSkeleton() {
/*  93 */     return this.skeleton;
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
/*     */   public void setJoints(List<Integer> joints) {
/* 110 */     if (joints == null) {
/* 111 */       throw new NullPointerException("Invalid value for joints: " + joints + ", may not be null");
/*     */     }
/* 113 */     if (joints.size() < 1) {
/* 114 */       throw new IllegalArgumentException("Number of joints elements is < 1");
/*     */     }
/* 116 */     for (Integer jointsElement : joints) {
/* 117 */       if (jointsElement.intValue() < 0) {
/* 118 */         throw new IllegalArgumentException("jointsElement < 0");
/*     */       }
/*     */     } 
/* 121 */     this.joints = joints;
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
/*     */   public List<Integer> getJoints() {
/* 135 */     return this.joints;
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
/*     */   public void addJoints(Integer element) {
/* 148 */     if (element == null) {
/* 149 */       throw new NullPointerException("The element may not be null");
/*     */     }
/* 151 */     List<Integer> oldList = this.joints;
/* 152 */     List<Integer> newList = new ArrayList<>();
/* 153 */     if (oldList != null) {
/* 154 */       newList.addAll(oldList);
/*     */     }
/* 156 */     newList.add(element);
/* 157 */     this.joints = newList;
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
/*     */   public void removeJoints(Integer element) {
/* 170 */     if (element == null) {
/* 171 */       throw new NullPointerException("The element may not be null");
/*     */     }
/* 173 */     List<Integer> oldList = this.joints;
/* 174 */     List<Integer> newList = new ArrayList<>();
/* 175 */     if (oldList != null) {
/* 176 */       newList.addAll(oldList);
/*     */     }
/* 178 */     newList.remove(element);
/* 179 */     this.joints = newList;
/*     */   }
/*     */ }


/* Location:              C:\Users\DuduM\OneDrive\Área de Trabalho\mw10\modularwarfare-shining-2023.2.4.4f-fix10.jar!\de\javagl\jgltf\impl\v2\Skin.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */