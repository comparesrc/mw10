/*    */ package org.spongepowered.tools.obfuscation;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public enum SuppressedBy
/*    */ {
/* 37 */   CONSTRAINTS("constraints"),
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/* 43 */   VISIBILITY("visibility"),
/*    */ 
/*    */ 
/*    */ 
/*    */   
/* 48 */   TARGET("target"),
/*    */ 
/*    */ 
/*    */ 
/*    */   
/* 53 */   MAPPING("mapping"),
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/* 59 */   OVERWRITE("overwrite"),
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/* 65 */   DEFAULT_PACKAGE("default-package"),
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/* 71 */   PUBLIC_TARGET("public-target"),
/*    */ 
/*    */ 
/*    */ 
/*    */   
/* 76 */   RAW_TYPES("rawtypes");
/*    */   
/*    */   private final String token;
/*    */   
/*    */   SuppressedBy(String token) {
/* 81 */     this.token = token;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public String getToken() {
/* 89 */     return this.token;
/*    */   }
/*    */ }


/* Location:              C:\Users\DuduM\OneDrive\Área de Trabalho\mw10\modularwarfare-shining-2023.2.4.4f-fix10.jar!\org\spongepowered\tools\obfuscation\SuppressedBy.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */