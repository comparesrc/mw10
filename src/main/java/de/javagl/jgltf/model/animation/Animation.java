/*     */ package de.javagl.jgltf.model.animation;
/*     */ 
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class Animation
/*     */ {
/*     */   private final float[] timesS;
/*     */   private final float[][] values;
/*     */   private final Interpolator interpolator;
/*     */   private final float[] outputValues;
/*     */   private final List<AnimationListener> listeners;
/*     */   
/*     */   public Animation(float[] timesS, float[][] values, InterpolatorType interpolatorType) {
/*  93 */     Objects.requireNonNull(timesS, "The times may not be null");
/*  94 */     Objects.requireNonNull(values, "The values may not be null");
/*  95 */     if (timesS.length == 0)
/*     */     {
/*  97 */       throw new IllegalArgumentException("The keys may not have a length of 0");
/*     */     }
/*     */     
/* 100 */     if (values.length != timesS.length)
/*     */     {
/* 102 */       throw new IllegalArgumentException("The values must have a length of " + timesS.length + ", but have a length of " + values.length);
/*     */     }
/*     */ 
/*     */     
/* 106 */     this.timesS = (float[])timesS.clone();
/* 107 */     this.values = new float[values.length][];
/* 108 */     for (int i = 0; i < values.length; i++)
/*     */     {
/* 110 */       this.values[i] = (float[])values[i].clone();
/*     */     }
/* 112 */     this.outputValues = new float[(values[0]).length];
/* 113 */     this.interpolator = Interpolators.create(interpolatorType);
/* 114 */     this.listeners = new CopyOnWriteArrayList<>();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   float getStartTimeS() {
/* 125 */     return this.timesS[0];
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   float getEndTimeS() {
/* 135 */     return this.timesS[this.timesS.length - 1];
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   float getDurationS() {
/* 145 */     return getEndTimeS() - getStartTimeS();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void addAnimationListener(AnimationListener listener) {
/* 156 */     this.listeners.add(listener);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void removeAnimationListener(AnimationListener listener) {
/* 166 */     this.listeners.remove(listener);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   void update(float timeS) {
/* 177 */     int index0 = InterpolatorKeys.computeIndex(timeS, this.timesS);
/* 178 */     int index1 = Math.min(this.timesS.length - 1, index0 + 1);
/* 179 */     float alpha = InterpolatorKeys.computeAlpha(timeS, this.timesS, index0);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 186 */     float[] a = this.values[index0];
/* 187 */     float[] b = this.values[index1];
/* 188 */     this.interpolator.interpolate(a, b, alpha, this.outputValues);
/* 189 */     for (AnimationListener listener : this.listeners)
/*     */     {
/* 191 */       listener.animationUpdated(this, timeS, this.outputValues);
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\DuduM\OneDrive\Área de Trabalho\mw10\modularwarfare-shining-2023.2.4.4f-fix10.jar!\de\javagl\jgltf\model\animation\Animation.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */