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
/*    */ public class MaterialNormalTextureInfo
/*    */   extends TextureInfo
/*    */ {
/*    */   private Float scale;
/*    */   
/*    */   public void setScale(Float scale) {
/* 38 */     if (scale == null) {
/* 39 */       this.scale = scale;
/*    */       return;
/*    */     } 
/* 42 */     this.scale = scale;
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
/*    */   public Float getScale() {
/* 54 */     return this.scale;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public Float defaultScale() {
/* 65 */     return Float.valueOf(1.0F);
/*    */   }
/*    */ }


/* Location:              C:\Users\DuduM\OneDrive\Área de Trabalho\mw10\modularwarfare-shining-2023.2.4.4f-fix10.jar!\de\javagl\jgltf\impl\v2\MaterialNormalTextureInfo.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */