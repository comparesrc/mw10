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
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class Texture
/*    */   extends GlTFChildOfRootProperty
/*    */ {
/*    */   private Integer sampler;
/*    */   private Integer source;
/*    */   
/*    */   public void setSampler(Integer sampler) {
/* 47 */     if (sampler == null) {
/* 48 */       this.sampler = sampler;
/*    */       return;
/*    */     } 
/* 51 */     this.sampler = sampler;
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
/*    */   public Integer getSampler() {
/* 63 */     return this.sampler;
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
/*    */   public void setSource(Integer source) {
/* 75 */     if (source == null) {
/* 76 */       this.source = source;
/*    */       return;
/*    */     } 
/* 79 */     this.source = source;
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
/*    */   public Integer getSource() {
/* 91 */     return this.source;
/*    */   }
/*    */ }


/* Location:              C:\Users\DuduM\OneDrive\Área de Trabalho\mw10\modularwarfare-shining-2023.2.4.4f-fix10.jar!\de\javagl\jgltf\impl\v2\Texture.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */