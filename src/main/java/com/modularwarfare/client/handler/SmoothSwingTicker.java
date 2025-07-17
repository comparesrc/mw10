/*    */ package com.modularwarfare.client.handler;
/*    */ 
/*    */ import com.modularwarfare.client.fpp.basic.renderers.RenderParameters;
/*    */ import java.util.Timer;
/*    */ import java.util.TimerTask;
/*    */ 
/*    */ public class SmoothSwingTicker
/*    */   extends TimerTask
/*    */ {
/*    */   private static final double amountOfTicks = 60.0D;
/* 11 */   private long lastTime = System.nanoTime();
/* 12 */   private double frameDelta = 0.0D;
/*    */ 
/*    */   
/*    */   public void run() {
/* 16 */     long nowNano = System.nanoTime();
/* 17 */     double nanoPerFrame = 1.6666666666666666E7D;
/* 18 */     this.frameDelta += (nowNano - this.lastTime) / nanoPerFrame;
/* 19 */     this.lastTime = nowNano;
/* 20 */     int frameDeltaCount = (int)this.frameDelta;
/* 21 */     RenderParameters.SMOOTH_SWING += frameDeltaCount;
/* 22 */     this.frameDelta -= frameDeltaCount;
/*    */   }
/*    */   
/*    */   public static void startSmoothSwingTimer() {
/* 26 */     Timer timer = new Timer("SmoothSwingThread");
/* 27 */     TimerTask task = new SmoothSwingTicker();
/* 28 */     timer.schedule(task, 0L, 16L);
/*    */   }
/*    */ }


/* Location:              C:\Users\DuduM\OneDrive\Área de Trabalho\mw10\modularwarfare-shining-2023.2.4.4f-fix10.jar!\com\modularwarfare\client\handler\SmoothSwingTicker.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */