/*     */ package de.javagl.jgltf.model.io;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class VersionUtils
/*     */ {
/*     */   public static int compareVersions(String v0, String v1) {
/*  45 */     int[] sv0 = computeMajorMinorPatch(v0);
/*  46 */     int[] sv1 = computeMajorMinorPatch(v1);
/*  47 */     for (int i = 0; i < 3; i++) {
/*     */       
/*  49 */       int c = Integer.compare(sv0[i], sv1[i]);
/*  50 */       if (c != 0)
/*     */       {
/*  52 */         return c;
/*     */       }
/*     */     } 
/*  55 */     return 0;
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
/*     */   static int[] computeMajorMinorPatch(String v) {
/*  71 */     int[] result = new int[3];
/*  72 */     String[] tokens = v.split("\\.");
/*  73 */     int n = Math.min(tokens.length, 3);
/*  74 */     for (int i = 0; i < n; i++) {
/*     */       
/*  76 */       String token = tokens[i];
/*  77 */       result[i] = parseIntPrefix(token);
/*     */     } 
/*  79 */     return result;
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
/*     */   private static int parseIntPrefix(String s) {
/*  93 */     String number = "";
/*  94 */     for (int j = 0; j < s.length(); ) {
/*     */       
/*  96 */       char c = s.charAt(j);
/*  97 */       if (Character.isDigit(c)) {
/*     */         
/*  99 */         number = number + c;
/*     */ 
/*     */         
/*     */         j++;
/*     */       } 
/*     */     } 
/*     */ 
/*     */     
/*     */     try {
/* 108 */       return Integer.parseInt(number);
/*     */     }
/* 110 */     catch (NumberFormatException e) {
/*     */       
/* 112 */       return 0;
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\DuduM\OneDrive\Área de Trabalho\mw10\modularwarfare-shining-2023.2.4.4f-fix10.jar!\de\javagl\jgltf\model\io\VersionUtils.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */