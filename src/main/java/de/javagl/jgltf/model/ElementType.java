/*     */ package de.javagl.jgltf.model;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public enum ElementType
/*     */ {
/*  37 */   SCALAR(1),
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  42 */   VEC2(2),
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  47 */   VEC3(3),
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  52 */   VEC4(4),
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  57 */   MAT2(4),
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  62 */   MAT3(9),
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  67 */   MAT4(16);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private final int numComponents;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   ElementType(int numComponents) {
/*  81 */     this.numComponents = numComponents;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getNumComponents() {
/*  91 */     return this.numComponents;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getByteStride(int componentType) {
/* 121 */     int n = Accessors.getNumBytesForAccessorComponentType(componentType);
/* 122 */     if (n == 1) {
/*     */       
/* 124 */       if (this == MAT2)
/*     */       {
/* 126 */         return 8;
/*     */       }
/* 128 */       if (this == MAT3)
/*     */       {
/* 130 */         return 12;
/*     */       }
/*     */     } 
/* 133 */     if (n == 2)
/*     */     {
/* 135 */       if (this == MAT3)
/*     */       {
/* 137 */         return 24;
/*     */       }
/*     */     }
/* 140 */     return this.numComponents * n;
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
/*     */   public static boolean contains(String s) {
/* 152 */     for (ElementType elementType : values()) {
/*     */       
/* 154 */       if (elementType.name().equals(s))
/*     */       {
/* 156 */         return true;
/*     */       }
/*     */     } 
/* 159 */     return false;
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
/*     */   public static ElementType forString(String string) {
/* 172 */     if (string == null)
/*     */     {
/* 174 */       return null;
/*     */     }
/* 176 */     if (!contains(string))
/*     */     {
/* 178 */       return null;
/*     */     }
/* 180 */     return valueOf(string);
/*     */   }
/*     */ }


/* Location:              C:\Users\DuduM\OneDrive\Área de Trabalho\mw10\modularwarfare-shining-2023.2.4.4f-fix10.jar!\de\javagl\jgltf\model\ElementType.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */