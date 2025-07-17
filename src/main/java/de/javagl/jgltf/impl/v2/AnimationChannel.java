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
/*    */ public class AnimationChannel
/*    */   extends GlTFProperty
/*    */ {
/*    */   private Integer sampler;
/*    */   private AnimationChannelTarget target;
/*    */   
/*    */   public void setSampler(Integer sampler) {
/* 45 */     if (sampler == null) {
/* 46 */       throw new NullPointerException("Invalid value for sampler: " + sampler + ", may not be null");
/*    */     }
/* 48 */     this.sampler = sampler;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public Integer getSampler() {
/* 59 */     return this.sampler;
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
/* 70 */     if (target == null) {
/* 71 */       throw new NullPointerException("Invalid value for target: " + target + ", may not be null");
/*    */     }
/* 73 */     this.target = target;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public AnimationChannelTarget getTarget() {
/* 83 */     return this.target;
/*    */   }
/*    */ }


/* Location:              C:\Users\DuduM\OneDrive\Área de Trabalho\mw10\modularwarfare-shining-2023.2.4.4f-fix10.jar!\de\javagl\jgltf\impl\v2\AnimationChannel.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */