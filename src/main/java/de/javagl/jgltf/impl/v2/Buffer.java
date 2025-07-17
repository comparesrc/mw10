/*    */ package de.javagl.jgltf.impl.v2;
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
/*    */ public class Buffer
/*    */   extends GlTFChildOfRootProperty
/*    */ {
/*    */   private String uri;
/*    */   private Integer byteLength;
/*    */   
/*    */   public void setUri(String uri) {
/* 42 */     if (uri == null) {
/* 43 */       this.uri = uri;
/*    */       return;
/*    */     } 
/* 46 */     this.uri = uri;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public String getUri() {
/* 56 */     return this.uri;
/*    */   }
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
/*    */   public void setByteLength(Integer byteLength) {
/* 70 */     if (byteLength == null) {
/* 71 */       throw new NullPointerException("Invalid value for byteLength: " + byteLength + ", may not be null");
/*    */     }
/* 73 */     if (byteLength.intValue() < 1) {
/* 74 */       throw new IllegalArgumentException("byteLength < 1");
/*    */     }
/* 76 */     this.byteLength = byteLength;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public Integer getByteLength() {
/* 87 */     return this.byteLength;
/*    */   }
/*    */ }


/* Location:              C:\Users\DuduM\OneDrive\Área de Trabalho\mw10\modularwarfare-shining-2023.2.4.4f-fix10.jar!\de\javagl\jgltf\impl\v2\Buffer.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */