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
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class Shader
/*    */   extends GlTFChildOfRootProperty
/*    */ {
/*    */   private String uri;
/*    */   private Integer type;
/*    */   
/*    */   public void setUri(String uri) {
/* 43 */     if (uri == null) {
/* 44 */       throw new NullPointerException("Invalid value for uri: " + uri + ", may not be null");
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
/*    */   public void setType(Integer type) {
/* 70 */     if (type == null) {
/* 71 */       throw new NullPointerException("Invalid value for type: " + type + ", may not be null");
/*    */     }
/* 73 */     if (type.intValue() != 35632 && type.intValue() != 35633) {
/* 74 */       throw new IllegalArgumentException("Invalid value for type: " + type + ", valid: [35632, 35633]");
/*    */     }
/* 76 */     this.type = type;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public Integer getType() {
/* 87 */     return this.type;
/*    */   }
/*    */ }


/* Location:              C:\Users\DuduM\OneDrive\Área de Trabalho\mw10\modularwarfare-shining-2023.2.4.4f-fix10.jar!\de\javagl\jgltf\impl\v1\Shader.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */