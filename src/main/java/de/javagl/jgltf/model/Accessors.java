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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class Accessors
/*     */ {
/*     */   public static int getNumComponentsForAccessorType(String accessorType) {
/*  57 */     switch (accessorType) {
/*     */       case "SCALAR":
/*  59 */         return 1;
/*  60 */       case "VEC2": return 2;
/*  61 */       case "VEC3": return 3;
/*  62 */       case "VEC4": return 4;
/*  63 */       case "MAT2": return 4;
/*  64 */       case "MAT3": return 9;
/*  65 */       case "MAT4": return 16;
/*     */     } 
/*     */ 
/*     */     
/*  69 */     throw new IllegalArgumentException("Invalid accessor type: " + accessorType);
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
/*     */   public static int getNumBytesForAccessorComponentType(int componentType) {
/*  94 */     switch (componentType) {
/*     */       case 5120:
/*  96 */         return 1;
/*  97 */       case 5121: return 1;
/*  98 */       case 5122: return 2;
/*  99 */       case 5123: return 2;
/* 100 */       case 5124: return 4;
/* 101 */       case 5125: return 4;
/* 102 */       case 5126: return 4;
/*     */     } 
/*     */ 
/*     */     
/* 106 */     throw new IllegalArgumentException("Invalid accessor component type: " + componentType);
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
/*     */   public static Class<?> getDataTypeForAccessorComponentType(int componentType) {
/* 131 */     switch (componentType) {
/*     */       case 5120:
/* 133 */         return byte.class;
/* 134 */       case 5121: return byte.class;
/* 135 */       case 5122: return short.class;
/* 136 */       case 5123: return short.class;
/* 137 */       case 5124: return int.class;
/* 138 */       case 5125: return int.class;
/* 139 */       case 5126: return float.class;
/*     */     } 
/*     */ 
/*     */     
/* 143 */     throw new IllegalArgumentException("Invalid accessor component type: " + componentType);
/*     */   }
/*     */ }


/* Location:              C:\Users\DuduM\OneDrive\Área de Trabalho\mw10\modularwarfare-shining-2023.2.4.4f-fix10.jar!\de\javagl\jgltf\model\Accessors.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */