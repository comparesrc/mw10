/*     */ package org.spongepowered.asm.util;
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
/*     */ public class ReEntranceLock
/*     */ {
/*     */   private final int maxDepth;
/*  41 */   private int depth = 0;
/*     */ 
/*     */   
/*     */   private boolean semaphore = false;
/*     */ 
/*     */ 
/*     */   
/*     */   public ReEntranceLock(int maxDepth) {
/*  49 */     this.maxDepth = maxDepth;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getMaxDepth() {
/*  56 */     return this.maxDepth;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getDepth() {
/*  63 */     return this.depth;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ReEntranceLock push() {
/*  73 */     this.depth++;
/*  74 */     checkAndSet();
/*  75 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ReEntranceLock pop() {
/*  84 */     if (this.depth == 0) {
/*  85 */       throw new IllegalStateException("ReEntranceLock pop() with zero depth");
/*     */     }
/*     */     
/*  88 */     this.depth--;
/*  89 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean check() {
/*  98 */     return (this.depth > this.maxDepth);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean checkAndSet() {
/* 107 */     return this.semaphore |= check();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ReEntranceLock set() {
/* 116 */     this.semaphore = true;
/* 117 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isSet() {
/* 124 */     return this.semaphore;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ReEntranceLock clear() {
/* 133 */     this.semaphore = false;
/* 134 */     return this;
/*     */   }
/*     */ }


/* Location:              C:\Users\DuduM\OneDrive\Área de Trabalho\mw10\modularwarfare-shining-2023.2.4.4f-fix10.jar!\org\spongepowered\as\\util\ReEntranceLock.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */