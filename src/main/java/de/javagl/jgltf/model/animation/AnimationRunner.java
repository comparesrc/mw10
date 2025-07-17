/*     */ package de.javagl.jgltf.model.animation;
/*     */ 
/*     */ import java.util.Objects;
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
/*     */ public final class AnimationRunner
/*     */ {
/*     */   private final AnimationManager animationManager;
/*     */   private boolean running = false;
/*     */   private Thread animationThread;
/*  54 */   private final long stepSizeMs = 10L;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public AnimationRunner(AnimationManager animationManager) {
/*  63 */     Objects.requireNonNull(animationManager, "The animationManager may not be null");
/*     */     
/*  65 */     this.animationManager = animationManager;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public synchronized void start() {
/*  74 */     if (isRunning()) {
/*     */       return;
/*     */     }
/*     */     
/*  78 */     this.animationThread = new Thread(this::runAnimations, "animationThread");
/*  79 */     this.animationThread.setDaemon(true);
/*  80 */     this.animationThread.start();
/*  81 */     this.running = true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public synchronized void stop() {
/*  90 */     if (!isRunning()) {
/*     */       return;
/*     */     }
/*     */     
/*  94 */     this.running = false;
/*  95 */     this.animationThread = null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   boolean isRunning() {
/* 105 */     return this.running;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void runAnimations() {
/* 114 */     long previousNs = System.nanoTime();
/* 115 */     while (isRunning()) {
/*     */       
/* 117 */       long currentNs = System.nanoTime();
/* 118 */       long deltaNs = currentNs - previousNs;
/* 119 */       this.animationManager.performStep(deltaNs);
/* 120 */       previousNs = currentNs;
/*     */       
/*     */       try {
/* 123 */         Thread.sleep(10L);
/*     */       }
/* 125 */       catch (InterruptedException e) {
/*     */         
/* 127 */         Thread.currentThread().interrupt();
/*     */         return;
/*     */       } 
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\DuduM\OneDrive\Área de Trabalho\mw10\modularwarfare-shining-2023.2.4.4f-fix10.jar!\de\javagl\jgltf\model\animation\AnimationRunner.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */