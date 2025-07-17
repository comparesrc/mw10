/*    */ package com.modularwarfare.client.fpp.enhanced.animation;
/*    */ 
/*    */ import com.modularwarfare.client.fpp.enhanced.AnimationType;
/*    */ import com.modularwarfare.client.fpp.enhanced.configs.GunEnhancedRenderConfig;
/*    */ import com.modularwarfare.utility.maths.Interpolation;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class ActionPlayback
/*    */ {
/*    */   public AnimationType action;
/*    */   public double time;
/*    */   public boolean hasPlayed;
/*    */   private AnimationController animationController;
/*    */   private GunEnhancedRenderConfig config;
/*    */   
/*    */   public ActionPlayback(AnimationController animationController, GunEnhancedRenderConfig config) {
/* 21 */     this.animationController = animationController;
/* 22 */     this.config = config;
/*    */   }
/*    */   
/*    */   public void updateTime(double alpha) {
/* 26 */     if (this.action == AnimationType.CUSTOM) {
/* 27 */       double startTime = this.animationController.startTime * 1.0D / this.config.FPS;
/* 28 */       double endTime = this.animationController.endTime * 1.0D / this.config.FPS;
/*    */       try {
/* 30 */         AnimationType type = AnimationType.AnimationTypeJsonAdapter.fromString(this.animationController.customAnimation);
/* 31 */         startTime = ((GunEnhancedRenderConfig.Animation)this.config.animations.get(type)).getStartTime(this.config.FPS);
/* 32 */         endTime = ((GunEnhancedRenderConfig.Animation)this.config.animations.get(type)).getEndTime(this.config.FPS);
/* 33 */       } catch (Exception exception) {}
/*    */       
/* 35 */       this.time = Interpolation.LINEAR.interpolate(startTime, endTime, alpha);
/* 36 */       if (alpha >= 1.0D) {
/* 37 */         this.hasPlayed = true;
/*    */       } else {
/* 39 */         this.hasPlayed = false;
/*    */       } 
/*    */     } else {
/* 42 */       if (this.config.animations.get(this.action) == null) {
/*    */         return;
/*    */       }
/* 45 */       double startTime = ((GunEnhancedRenderConfig.Animation)this.config.animations.get(this.action)).getStartTime(this.config.FPS);
/* 46 */       double endTime = ((GunEnhancedRenderConfig.Animation)this.config.animations.get(this.action)).getEndTime(this.config.FPS);
/* 47 */       this.time = Interpolation.LINEAR.interpolate(startTime, endTime, alpha);
/* 48 */       checkPlayed();
/*    */     } 
/*    */   }
/*    */   
/*    */   public boolean checkPlayed() {
/* 53 */     double endTime = ((GunEnhancedRenderConfig.Animation)this.config.animations.get(this.action)).getEndTime(this.config.FPS);
/* 54 */     if (this.time >= endTime) {
/* 55 */       this.hasPlayed = true;
/*    */     } else {
/* 57 */       this.hasPlayed = false;
/*    */     } 
/* 59 */     return this.hasPlayed;
/*    */   }
/*    */ }


/* Location:              C:\Users\DuduM\OneDrive\Área de Trabalho\mw10\modularwarfare-shining-2023.2.4.4f-fix10.jar!\com\modularwarfare\client\fpp\enhanced\animation\ActionPlayback.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */