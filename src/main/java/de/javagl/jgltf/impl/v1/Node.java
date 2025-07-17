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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
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
/*     */   private String camera;
/*     */   private List<String> children;
/*     */   private List<String> skeletons;
/*     */   private String skin;
/*     */   private String jointName;
/*     */   private float[] matrix;
/*     */   private List<String> meshes;
/*     */   private float[] rotation;
/*     */   private float[] scale;
/*     */   private float[] translation;
/*     */   
/*     */   public void setCamera(String camera) {
/* 122 */     if (camera == null) {
/* 123 */       this.camera = camera;
/*     */       return;
/*     */     } 
/* 126 */     this.camera = camera;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getCamera() {
/* 136 */     return this.camera;
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
/*     */   public void setChildren(List<String> children) {
/* 149 */     if (children == null) {
/* 150 */       this.children = children;
/*     */       return;
/*     */     } 
/* 153 */     this.children = children;
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
/*     */   public List<String> getChildren() {
/* 166 */     return this.children;
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
/*     */   public void addChildren(String element) {
/* 179 */     if (element == null) {
/* 180 */       throw new NullPointerException("The element may not be null");
/*     */     }
/* 182 */     List<String> oldList = this.children;
/* 183 */     List<String> newList = new ArrayList<>();
/* 184 */     if (oldList != null) {
/* 185 */       newList.addAll(oldList);
/*     */     }
/* 187 */     newList.add(element);
/* 188 */     this.children = newList;
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
/*     */   public void removeChildren(String element) {
/* 203 */     if (element == null) {
/* 204 */       throw new NullPointerException("The element may not be null");
/*     */     }
/* 206 */     List<String> oldList = this.children;
/* 207 */     List<String> newList = new ArrayList<>();
/* 208 */     if (oldList != null) {
/* 209 */       newList.addAll(oldList);
/*     */     }
/* 211 */     newList.remove(element);
/* 212 */     if (newList.isEmpty()) {
/* 213 */       this.children = null;
/*     */     } else {
/* 215 */       this.children = newList;
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
/*     */   public List<String> defaultChildren() {
/* 227 */     return new ArrayList<>();
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
/*     */   public void setSkeletons(List<String> skeletons) {
/* 239 */     if (skeletons == null) {
/* 240 */       this.skeletons = skeletons;
/*     */       return;
/*     */     } 
/* 243 */     this.skeletons = skeletons;
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
/*     */   public List<String> getSkeletons() {
/* 255 */     return this.skeletons;
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
/*     */   public void addSkeletons(String element) {
/* 268 */     if (element == null) {
/* 269 */       throw new NullPointerException("The element may not be null");
/*     */     }
/* 271 */     List<String> oldList = this.skeletons;
/* 272 */     List<String> newList = new ArrayList<>();
/* 273 */     if (oldList != null) {
/* 274 */       newList.addAll(oldList);
/*     */     }
/* 276 */     newList.add(element);
/* 277 */     this.skeletons = newList;
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
/*     */   public void removeSkeletons(String element) {
/* 292 */     if (element == null) {
/* 293 */       throw new NullPointerException("The element may not be null");
/*     */     }
/* 295 */     List<String> oldList = this.skeletons;
/* 296 */     List<String> newList = new ArrayList<>();
/* 297 */     if (oldList != null) {
/* 298 */       newList.addAll(oldList);
/*     */     }
/* 300 */     newList.remove(element);
/* 301 */     if (newList.isEmpty()) {
/* 302 */       this.skeletons = null;
/*     */     } else {
/* 304 */       this.skeletons = newList;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setSkin(String skin) {
/* 315 */     if (skin == null) {
/* 316 */       this.skin = skin;
/*     */       return;
/*     */     } 
/* 319 */     this.skin = skin;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getSkin() {
/* 329 */     return this.skin;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setJointName(String jointName) {
/* 339 */     if (jointName == null) {
/* 340 */       this.jointName = jointName;
/*     */       return;
/*     */     } 
/* 343 */     this.jointName = jointName;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getJointName() {
/* 353 */     return this.jointName;
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
/* 371 */     if (matrix == null) {
/* 372 */       this.matrix = matrix;
/*     */       return;
/*     */     } 
/* 375 */     if (matrix.length < 16) {
/* 376 */       throw new IllegalArgumentException("Number of matrix elements is < 16");
/*     */     }
/* 378 */     if (matrix.length > 16) {
/* 379 */       throw new IllegalArgumentException("Number of matrix elements is > 16");
/*     */     }
/* 381 */     this.matrix = matrix;
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
/* 397 */     return this.matrix;
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
/* 408 */     return new float[] { 1.0F, 0.0F, 0.0F, 0.0F, 0.0F, 1.0F, 0.0F, 0.0F, 0.0F, 0.0F, 1.0F, 0.0F, 0.0F, 0.0F, 0.0F, 1.0F };
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
/*     */   public void setMeshes(List<String> meshes) {
/* 420 */     if (meshes == null) {
/* 421 */       this.meshes = meshes;
/*     */       return;
/*     */     } 
/* 424 */     this.meshes = meshes;
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
/*     */   public List<String> getMeshes() {
/* 436 */     return this.meshes;
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
/*     */   public void addMeshes(String element) {
/* 449 */     if (element == null) {
/* 450 */       throw new NullPointerException("The element may not be null");
/*     */     }
/* 452 */     List<String> oldList = this.meshes;
/* 453 */     List<String> newList = new ArrayList<>();
/* 454 */     if (oldList != null) {
/* 455 */       newList.addAll(oldList);
/*     */     }
/* 457 */     newList.add(element);
/* 458 */     this.meshes = newList;
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
/*     */   public void removeMeshes(String element) {
/* 473 */     if (element == null) {
/* 474 */       throw new NullPointerException("The element may not be null");
/*     */     }
/* 476 */     List<String> oldList = this.meshes;
/* 477 */     List<String> newList = new ArrayList<>();
/* 478 */     if (oldList != null) {
/* 479 */       newList.addAll(oldList);
/*     */     }
/* 481 */     newList.remove(element);
/* 482 */     if (newList.isEmpty()) {
/* 483 */       this.meshes = null;
/*     */     } else {
/* 485 */       this.meshes = newList;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setRotation(float[] rotation) {
/* 503 */     if (rotation == null) {
/* 504 */       this.rotation = rotation;
/*     */       return;
/*     */     } 
/* 507 */     if (rotation.length < 4) {
/* 508 */       throw new IllegalArgumentException("Number of rotation elements is < 4");
/*     */     }
/* 510 */     if (rotation.length > 4) {
/* 511 */       throw new IllegalArgumentException("Number of rotation elements is > 4");
/*     */     }
/* 513 */     this.rotation = rotation;
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
/*     */   public float[] getRotation() {
/* 528 */     return this.rotation;
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
/* 539 */     return new float[] { 0.0F, 0.0F, 0.0F, 1.0F };
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
/*     */   public void setScale(float[] scale) {
/* 555 */     if (scale == null) {
/* 556 */       this.scale = scale;
/*     */       return;
/*     */     } 
/* 559 */     if (scale.length < 3) {
/* 560 */       throw new IllegalArgumentException("Number of scale elements is < 3");
/*     */     }
/* 562 */     if (scale.length > 3) {
/* 563 */       throw new IllegalArgumentException("Number of scale elements is > 3");
/*     */     }
/* 565 */     this.scale = scale;
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
/*     */   public float[] getScale() {
/* 579 */     return this.scale;
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
/* 590 */     return new float[] { 1.0F, 1.0F, 1.0F };
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
/* 606 */     if (translation == null) {
/* 607 */       this.translation = translation;
/*     */       return;
/*     */     } 
/* 610 */     if (translation.length < 3) {
/* 611 */       throw new IllegalArgumentException("Number of translation elements is < 3");
/*     */     }
/* 613 */     if (translation.length > 3) {
/* 614 */       throw new IllegalArgumentException("Number of translation elements is > 3");
/*     */     }
/* 616 */     this.translation = translation;
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
/* 630 */     return this.translation;
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
/* 641 */     return new float[] { 0.0F, 0.0F, 0.0F };
/*     */   }
/*     */ }


/* Location:              C:\Users\DuduM\OneDrive\Área de Trabalho\mw10\modularwarfare-shining-2023.2.4.4f-fix10.jar!\de\javagl\jgltf\impl\v1\Node.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */