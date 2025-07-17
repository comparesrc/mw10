/*    */ package de.javagl.jgltf.impl.v1;
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
/*    */ public class Image
/*    */   extends GlTFChildOfRootProperty
/*    */ {
/*    */   private String uri;
/*    */   
/*    */   public void setUri(String uri) {
/* 37 */     if (uri == null) {
/* 38 */       throw new NullPointerException("Invalid value for uri: " + uri + ", may not be null");
/*    */     }
/* 40 */     this.uri = uri;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public String getUri() {
/* 50 */     return this.uri;
/*    */   }
/*    */ }


/* Location:              C:\Users\DuduM\OneDrive\Área de Trabalho\mw10\modularwarfare-shining-2023.2.4.4f-fix10.jar!\de\javagl\jgltf\impl\v1\Image.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */