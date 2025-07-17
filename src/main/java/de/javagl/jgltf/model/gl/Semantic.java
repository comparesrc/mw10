/*     */ package de.javagl.jgltf.model.gl;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public enum Semantic
/*     */ {
/*  38 */   LOCAL,
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  43 */   MODEL,
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  48 */   VIEW,
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  53 */   PROJECTION,
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  58 */   MODELVIEW,
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  63 */   MODELVIEWPROJECTION,
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  68 */   MODELINVERSE,
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  73 */   VIEWINVERSE,
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  78 */   MODELVIEWINVERSE,
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  83 */   PROJECTIONINVERSE,
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  88 */   MODELVIEWPROJECTIONINVERSE,
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  93 */   MODELINVERSETRANSPOSE,
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  98 */   MODELVIEWINVERSETRANSPOSE,
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 103 */   VIEWPORT,
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 108 */   JOINTMATRIX;
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
/* 119 */     for (Semantic semantic : values()) {
/*     */       
/* 121 */       if (semantic.name().equals(s))
/*     */       {
/* 123 */         return true;
/*     */       }
/*     */     } 
/* 126 */     return false;
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
/*     */   public static Semantic forString(String string) {
/* 139 */     if (string == null)
/*     */     {
/* 141 */       return null;
/*     */     }
/* 143 */     if (!contains(string))
/*     */     {
/* 145 */       return null;
/*     */     }
/* 147 */     return valueOf(string);
/*     */   }
/*     */ }


/* Location:              C:\Users\DuduM\OneDrive\Área de Trabalho\mw10\modularwarfare-shining-2023.2.4.4f-fix10.jar!\de\javagl\jgltf\model\gl\Semantic.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */