/*     */ package de.javagl.jgltf.model;
/*     */ 
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ class BoundingBox
/*     */ {
/*  71 */   private float minX = Float.MAX_VALUE;
/*  72 */   private float minY = Float.MAX_VALUE;
/*  73 */   private float minZ = Float.MAX_VALUE;
/*  74 */   private float maxX = -3.4028235E38F;
/*  75 */   private float maxY = -3.4028235E38F;
/*  76 */   private float maxZ = -3.4028235E38F;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   void combine(float x, float y, float z) {
/*  88 */     this.minX = Math.min(this.minX, x);
/*  89 */     this.minY = Math.min(this.minY, y);
/*  90 */     this.minZ = Math.min(this.minZ, z);
/*  91 */     this.maxX = Math.max(this.maxX, x);
/*  92 */     this.maxY = Math.max(this.maxY, y);
/*  93 */     this.maxZ = Math.max(this.maxZ, z);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   void combine(BoundingBox other) {
/* 103 */     Objects.requireNonNull(other, "The other bounding box may not be null");
/* 104 */     this.minX = Math.min(this.minX, other.getMinX());
/* 105 */     this.minY = Math.min(this.minY, other.getMinY());
/* 106 */     this.minZ = Math.min(this.minZ, other.getMinZ());
/* 107 */     this.maxX = Math.max(this.maxX, other.getMaxX());
/* 108 */     this.maxY = Math.max(this.maxY, other.getMaxY());
/* 109 */     this.maxZ = Math.max(this.maxZ, other.getMaxZ());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   float getCenterX() {
/* 119 */     return getMinX() + getSizeX() * 0.5F;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   float getCenterY() {
/* 129 */     return getMinY() + getSizeY() * 0.5F;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   float getCenterZ() {
/* 139 */     return getMinZ() + getSizeZ() * 0.5F;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   float getSizeX() {
/* 149 */     return getMaxX() - getMinX();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   float getSizeY() {
/* 159 */     return getMaxY() - getMinY();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   float getSizeZ() {
/* 169 */     return getMaxZ() - getMinZ();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   float getMinX() {
/* 179 */     return this.minX;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   float getMinY() {
/* 189 */     return this.minY;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   float getMinZ() {
/* 199 */     return this.minZ;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   float getMaxX() {
/* 209 */     return this.maxX;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   float getMaxY() {
/* 219 */     return this.maxY;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   float getMaxZ() {
/* 229 */     return this.maxZ;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public String toString() {
/* 235 */     return "[(" + 
/* 236 */       getMinX() + "," + getMinY() + "," + getMinZ() + ")-(" + 
/* 237 */       getMaxX() + "," + getMaxY() + "," + getMaxZ() + ")]";
/*     */   }
/*     */ }


/* Location:              C:\Users\DuduM\OneDrive\Área de Trabalho\mw10\modularwarfare-shining-2023.2.4.4f-fix10.jar!\de\javagl\jgltf\model\BoundingBox.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */