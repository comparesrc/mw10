/*    */ package com.modularwarfare.client.customplayer;
/*    */ 
/*    */ import java.util.HashMap;
/*    */ 
/*    */ public class CustomPlayerConfig {
/*    */   public String name;
/*    */   public String model;
/*    */   public String tex;
/*    */   public HashMap<String, Animation> animations;
/* 10 */   public int FPS = 24;
/*    */   
/*    */   public static class Animation {
/* 13 */     public double startTime = 0.0D;
/* 14 */     public double endTime = 1.0D;
/* 15 */     public double speed = 1.0D;
/*    */     
/*    */     public double getStartTime(double FPS) {
/* 18 */       return this.startTime * 1.0D / FPS;
/*    */     }
/*    */     
/*    */     public double getEndTime(double FPS) {
/* 22 */       return this.endTime * 1.0D / FPS;
/*    */     }
/*    */     
/*    */     public double getSpeed(double FPS) {
/* 26 */       double a = getEndTime(FPS) - getStartTime(FPS);
/* 27 */       if (a <= 0.0D) {
/* 28 */         a = 1.0D;
/*    */       }
/* 30 */       return this.speed / a;
/*    */     }
/*    */   }
/*    */ }


/* Location:              C:\Users\DuduM\OneDrive\Área de Trabalho\mw9\modularwarfare-shining-2023.2.4.4f-fix9.jar!\com\modularwarfare\client\customplayer\CustomPlayerConfig.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */