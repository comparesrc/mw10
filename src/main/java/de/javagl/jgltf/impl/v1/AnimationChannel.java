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
/*    */ 
/*    */ public class AnimationChannel
/*    */   extends GlTFProperty
/*    */ {
/*    */   private String sampler;
/*    */   private AnimationChannelTarget target;
/*    */   
/*    */   public void setSampler(String sampler) {
/* 44 */     if (sampler == null) {
/* 45 */       throw new NullPointerException("Invalid value for sampler: " + sampler + ", may not be null");
/*    */     }
/* 47 */     this.sampler = sampler;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public String getSampler() {
/* 58 */     return this.sampler;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void setTarget(AnimationChannelTarget target) {
/* 69 */     if (target == null) {
/* 70 */       throw new NullPointerException("Invalid value for target: " + target + ", may not be null");
/*    */     }
/* 72 */     this.target = target;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public AnimationChannelTarget getTarget() {
/* 82 */     return this.target;
/*    */   }
/*    */ }


/* Location:              C:\Users\DuduM\OneDrive\Área de Trabalho\mw10\modularwarfare-shining-2023.2.4.4f-fix10.jar!\de\javagl\jgltf\impl\v1\AnimationChannel.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */