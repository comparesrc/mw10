/*     */ package de.javagl.jgltf.model.animation;
/*     */ 
/*     */ import java.util.Collections;
/*     */ import java.util.List;
/*     */ import java.util.Objects;
/*     */ import java.util.concurrent.CopyOnWriteArrayList;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class AnimationManager
/*     */ {
/*     */   private final AnimationPolicy animationPolicy;
/*     */   private long startNs;
/*     */   private long currentNs;
/*     */   private final List<Animation> animations;
/*     */   private float maxEndTimeS;
/*     */   private final List<AnimationManagerListener> animationManagerListeners;
/*     */   
/*     */   public enum AnimationPolicy
/*     */   {
/*  49 */     ONCE,
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  55 */     PING_PONG,
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  60 */     LOOP;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public AnimationManager(AnimationPolicy animationPolicy) {
/* 101 */     this.animationPolicy = animationPolicy;
/* 102 */     this.startNs = System.nanoTime();
/* 103 */     this.currentNs = this.startNs;
/* 104 */     this.animations = new CopyOnWriteArrayList<>();
/* 105 */     this.maxEndTimeS = 0.0F;
/* 106 */     this.animationManagerListeners = new CopyOnWriteArrayList<>();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void reset() {
/* 116 */     this.startNs = System.nanoTime();
/* 117 */     this.currentNs = System.nanoTime();
/* 118 */     performStep(0L);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   float getCurrentTimeS() {
/* 128 */     long timeNs = this.currentNs - this.startNs;
/* 129 */     float timeS = (float)timeNs * 1.0E-9F;
/* 130 */     return timeS;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void addAnimation(Animation animation) {
/* 140 */     Objects.requireNonNull(animation, "The animation may not be null");
/* 141 */     this.animations.add(animation);
/* 142 */     updateMaxEndTime();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void addAnimations(Iterable<? extends Animation> animations) {
/* 152 */     for (Animation animation : animations)
/*     */     {
/* 154 */       addAnimation(animation);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void removeAnimation(Animation animation) {
/* 166 */     this.animations.remove(animation);
/* 167 */     updateMaxEndTime();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void removeAnimations(Iterable<? extends Animation> animations) {
/* 177 */     for (Animation animation : animations)
/*     */     {
/* 179 */       removeAnimation(animation);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public List<Animation> getAnimations() {
/* 191 */     return Collections.unmodifiableList(this.animations);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void updateMaxEndTime() {
/* 200 */     this.maxEndTimeS = 0.0F;
/* 201 */     for (Animation animation : this.animations)
/*     */     {
/* 203 */       this.maxEndTimeS = Math.max(this.maxEndTimeS, animation.getEndTimeS());
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   void performStep(long deltaNs) {
/* 215 */     this.currentNs += deltaNs;
/* 216 */     float currentTimeS = getCurrentTimeS();
/* 217 */     if (this.animationPolicy == AnimationPolicy.ONCE && currentTimeS > this.maxEndTimeS) {
/*     */ 
/*     */       
/* 220 */       this.animations.clear();
/*     */       return;
/*     */     } 
/* 223 */     for (Animation animation : this.animations) {
/*     */       
/* 225 */       if (this.animationPolicy == AnimationPolicy.LOOP) {
/*     */         
/* 227 */         float loopTimeS = currentTimeS % this.maxEndTimeS;
/* 228 */         animation.update(loopTimeS); continue;
/*     */       } 
/* 230 */       if (this.animationPolicy == AnimationPolicy.PING_PONG) {
/*     */         
/* 232 */         int interval = (int)(currentTimeS / this.maxEndTimeS);
/* 233 */         float loopTimeS = currentTimeS % this.maxEndTimeS;
/* 234 */         float pingPongTimeS = loopTimeS;
/* 235 */         if ((interval & 0x1) != 0)
/*     */         {
/* 237 */           pingPongTimeS = this.maxEndTimeS - loopTimeS;
/*     */         }
/* 239 */         animation.update(pingPongTimeS);
/*     */         
/*     */         continue;
/*     */       } 
/* 243 */       animation.update(currentTimeS);
/*     */     } 
/*     */     
/* 246 */     fireAnimationsUpdated();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void addAnimationManagerListener(AnimationManagerListener listener) {
/* 258 */     this.animationManagerListeners.add(listener);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void removeAnimationManagerListener(AnimationManagerListener listener) {
/* 269 */     this.animationManagerListeners.remove(listener);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void fireAnimationsUpdated() {
/* 279 */     for (AnimationManagerListener listener : this.animationManagerListeners)
/*     */     {
/* 281 */       listener.animationsUpdated(this);
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\DuduM\OneDrive\Área de Trabalho\mw10\modularwarfare-shining-2023.2.4.4f-fix10.jar!\de\javagl\jgltf\model\animation\AnimationManager.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */