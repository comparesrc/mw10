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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class Node
/*     */   extends GlTFChildOfRootProperty
/*     */ {
/*     */   private Integer camera;
/*     */   private List<Integer> children;
/*     */   private Integer skin;
/*     */   private float[] matrix;
/*     */   private Integer mesh;
/*     */   private float[] rotation;
/*     */   private float[] scale;
/*     */   private float[] translation;
/*     */   private List<Float> weights;
/*     */   
/*     */   public void setCamera(Integer camera) {
/* 118 */     if (camera == null) {
/* 119 */       this.camera = camera;
/*     */       return;
/*     */     } 
/* 122 */     this.camera = camera;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Integer getCamera() {
/* 132 */     return this.camera;
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
/*     */   public void setChildren(List<Integer> children) {
/* 148 */     if (children == null) {
/* 149 */       this.children = children;
/*     */       return;
/*     */     } 
/* 152 */     if (children.size() < 1) {
/* 153 */       throw new IllegalArgumentException("Number of children elements is < 1");
/*     */     }
/* 155 */     for (Integer childrenElement : children) {
/* 156 */       if (childrenElement.intValue() < 0) {
/* 157 */         throw new IllegalArgumentException("childrenElement < 0");
/*     */       }
/*     */     } 
/* 160 */     this.children = children;
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
/*     */   public List<Integer> getChildren() {
/* 174 */     return this.children;
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
/*     */   public void addChildren(Integer element) {
/* 187 */     if (element == null) {
/* 188 */       throw new NullPointerException("The element may not be null");
/*     */     }
/* 190 */     List<Integer> oldList = this.children;
/* 191 */     List<Integer> newList = new ArrayList<>();
/* 192 */     if (oldList != null) {
/* 193 */       newList.addAll(oldList);
/*     */     }
/* 195 */     newList.add(element);
/* 196 */     this.children = newList;
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
/*     */   public void removeChildren(Integer element) {
/* 211 */     if (element == null) {
/* 212 */       throw new NullPointerException("The element may not be null");
/*     */     }
/* 214 */     List<Integer> oldList = this.children;
/* 215 */     List<Integer> newList = new ArrayList<>();
/* 216 */     if (oldList != null) {
/* 217 */       newList.addAll(oldList);
/*     */     }
/* 219 */     newList.remove(element);
/* 220 */     if (newList.isEmpty()) {
/* 221 */       this.children = null;
/*     */     } else {
/* 223 */       this.children = newList;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setSkin(Integer skin) {
/* 234 */     if (skin == null) {
/* 235 */       this.skin = skin;
/*     */       return;
/*     */     } 
/* 238 */     this.skin = skin;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Integer getSkin() {
/* 248 */     return this.skin;
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
/*     */   
/*     */   public void setMatrix(float[] matrix) {
/* 266 */     if (matrix == null) {
/* 267 */       this.matrix = matrix;
/*     */       return;
/*     */     } 
/* 270 */     if (matrix.length < 16) {
/* 271 */       throw new IllegalArgumentException("Number of matrix elements is < 16");
/*     */     }
/* 273 */     if (matrix.length > 16) {
/* 274 */       throw new IllegalArgumentException("Number of matrix elements is > 16");
/*     */     }
/* 276 */     this.matrix = matrix;
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
/*     */   public float[] getMatrix() {
/* 292 */     return this.matrix;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public float[] defaultMatrix() {
/* 303 */     return new float[] { 1.0F, 0.0F, 0.0F, 0.0F, 0.0F, 1.0F, 0.0F, 0.0F, 0.0F, 0.0F, 1.0F, 0.0F, 0.0F, 0.0F, 0.0F, 1.0F };
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setMesh(Integer mesh) {
/* 313 */     if (mesh == null) {
/* 314 */       this.mesh = mesh;
/*     */       return;
/*     */     } 
/* 317 */     this.mesh = mesh;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Integer getMesh() {
/* 327 */     return this.mesh;
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
/*     */ 
/*     */   
/*     */   public void setRotation(float[] rotation) {
/* 346 */     if (rotation == null) {
/* 347 */       this.rotation = rotation;
/*     */       return;
/*     */     } 
/* 350 */     if (rotation.length < 4) {
/* 351 */       throw new IllegalArgumentException("Number of rotation elements is < 4");
/*     */     }
/* 353 */     if (rotation.length > 4) {
/* 354 */       throw new IllegalArgumentException("Number of rotation elements is > 4");
/*     */     }
/* 356 */     for (float rotationElement : rotation) {
/* 357 */       if (rotationElement > 1.0D) {
/* 358 */         throw new IllegalArgumentException("rotationElement > 1.0");
/*     */       }
/* 360 */       if (rotationElement < -1.0D) {
/* 361 */         throw new IllegalArgumentException("rotationElement < -1.0");
/*     */       }
/*     */     } 
/* 364 */     this.rotation = rotation;
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
/*     */   public float[] getRotation() {
/* 381 */     return this.rotation;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public float[] defaultRotation() {
/* 392 */     return new float[] { 0.0F, 0.0F, 0.0F, 1.0F };
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
/*     */   public void setScale(float[] scale) {
/* 409 */     if (scale == null) {
/* 410 */       this.scale = scale;
/*     */       return;
/*     */     } 
/* 413 */     if (scale.length < 3) {
/* 414 */       throw new IllegalArgumentException("Number of scale elements is < 3");
/*     */     }
/* 416 */     if (scale.length > 3) {
/* 417 */       throw new IllegalArgumentException("Number of scale elements is > 3");
/*     */     }
/* 419 */     this.scale = scale;
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
/*     */   public float[] getScale() {
/* 434 */     return this.scale;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public float[] defaultScale() {
/* 445 */     return new float[] { 1.0F, 1.0F, 1.0F };
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
/*     */   public void setTranslation(float[] translation) {
/* 461 */     if (translation == null) {
/* 462 */       this.translation = translation;
/*     */       return;
/*     */     } 
/* 465 */     if (translation.length < 3) {
/* 466 */       throw new IllegalArgumentException("Number of translation elements is < 3");
/*     */     }
/* 468 */     if (translation.length > 3) {
/* 469 */       throw new IllegalArgumentException("Number of translation elements is > 3");
/*     */     }
/* 471 */     this.translation = translation;
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
/*     */   public float[] getTranslation() {
/* 485 */     return this.translation;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public float[] defaultTranslation() {
/* 496 */     return new float[] { 0.0F, 0.0F, 0.0F };
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
/* 513 */     if (weights == null) {
/* 514 */       this.weights = weights;
/*     */       return;
/*     */     } 
/* 517 */     if (weights.size() < 1) {
/* 518 */       throw new IllegalArgumentException("Number of weights elements is < 1");
/*     */     }
/* 520 */     this.weights = weights;
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
/* 535 */     return this.weights;
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
/* 548 */     if (element == null) {
/* 549 */       throw new NullPointerException("The element may not be null");
/*     */     }
/* 551 */     List<Float> oldList = this.weights;
/* 552 */     List<Float> newList = new ArrayList<>();
/* 553 */     if (oldList != null) {
/* 554 */       newList.addAll(oldList);
/*     */     }
/* 556 */     newList.add(element);
/* 557 */     this.weights = newList;
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
/* 572 */     if (element == null) {
/* 573 */       throw new NullPointerException("The element may not be null");
/*     */     }
/* 575 */     List<Float> oldList = this.weights;
/* 576 */     List<Float> newList = new ArrayList<>();
/* 577 */     if (oldList != null) {
/* 578 */       newList.addAll(oldList);
/*     */     }
/* 580 */     newList.remove(element);
/* 581 */     if (newList.isEmpty()) {
/* 582 */       this.weights = null;
/*     */     } else {
/* 584 */       this.weights = newList;
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\DuduM\OneDrive\Área de Trabalho\mw10\modularwarfare-shining-2023.2.4.4f-fix10.jar!\de\javagl\jgltf\impl\v2\Node.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */