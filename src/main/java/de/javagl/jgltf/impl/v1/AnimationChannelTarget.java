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
/*    */ public class AnimationChannelTarget
/*    */   extends GlTFProperty
/*    */ {
/*    */   private String id;
/*    */   private String path;
/*    */   
/*    */   public void setId(String id) {
/* 43 */     if (id == null) {
/* 44 */       throw new NullPointerException("Invalid value for id: " + id + ", may not be null");
/*    */     }
/* 46 */     this.id = id;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public String getId() {
/* 56 */     return this.id;
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
/*    */   public void setPath(String path) {
/* 70 */     if (path == null) {
/* 71 */       throw new NullPointerException("Invalid value for path: " + path + ", may not be null");
/*    */     }
/* 73 */     if (!"translation".equals(path) && !"rotation".equals(path) && !"scale".equals(path)) {
/* 74 */       throw new IllegalArgumentException("Invalid value for path: " + path + ", valid: [\"translation\", \"rotation\", \"scale\"]");
/*    */     }
/* 76 */     this.path = path;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public String getPath() {
/* 87 */     return this.path;
/*    */   }
/*    */ }


/* Location:              C:\Users\DuduM\OneDrive\Área de Trabalho\mw10\modularwarfare-shining-2023.2.4.4f-fix10.jar!\de\javagl\jgltf\impl\v1\AnimationChannelTarget.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */