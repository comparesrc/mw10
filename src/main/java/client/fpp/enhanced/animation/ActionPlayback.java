/*    */ package com.modularwarfare.client.fpp.enhanced.animation;
/*    */ 
/*    */ import com.modularwarfare.client.fpp.enhanced.AnimationType;
/*    */ import com.modularwarfare.client.fpp.enhanced.configs.GunEnhancedRenderConfig;
/*    */ import com.modularwarfare.utility.maths.Interpolation;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class ActionPlayback
/*    */ {
/*    */   public AnimationType action;
/*    */   public double time;
/*    */   public boolean hasPlayed;
/*    */   private GunEnhancedRenderConfig config;
/*    */   
/*    */   public ActionPlayback(GunEnhancedRenderConfig config) {
/* 18 */     this.config = config;
/*    */   }
/*    */   
/*    */   public void updateTime(double alpha) {
/* 22 */     if (this.config.animations.get(this.action) == null) {
/*    */       return;
/*    */     }
/* 25 */     double startTime = ((GunEnhancedRenderConfig.Animation)this.config.animations.get(this.action)).getStartTime(this.config.FPS);
/* 26 */     double endTime = ((GunEnhancedRenderConfig.Animation)this.config.animations.get(this.action)).getEndTime(this.config.FPS);
/* 27 */     this.time = Interpolation.LINEAR.interpolate(startTime, endTime, alpha);
/* 28 */     checkPlayed();
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean checkPlayed() {
/* 33 */     double endTime = ((GunEnhancedRenderConfig.Animation)this.config.animations.get(this.action)).getEndTime(this.config.FPS);
/* 34 */     if (this.time >= endTime) {
/* 35 */       this.hasPlayed = true;
/*    */     } else {
/* 37 */       this.hasPlayed = false;
/*    */     } 
/* 39 */     return this.hasPlayed;
/*    */   }
/*    */ }


/* Location:              C:\Users\DuduM\OneDrive\Área de Trabalho\mw9\modularwarfare-shining-2023.2.4.4f-fix9.jar!\com\modularwarfare\client\fpp\enhanced\animation\ActionPlayback.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */