/*    */ package com.modularwarfare.client.fpp.enhanced.configs;
/*    */ 
/*    */ import com.google.gson.annotations.SerializedName;
/*    */ 
/*    */ 
/*    */ public class EnhancedRenderConfig
/*    */ {
/*  8 */   public String modelFileName = "";
/*  9 */   public int FPS = 24;
/*    */   
/* 11 */   public ShowHandArmorType showHandArmorType = ShowHandArmorType.NONE;
/*    */ 
/*    */   
/*    */   public EnhancedRenderConfig() {}
/*    */ 
/*    */   
/*    */   public EnhancedRenderConfig(String modelFileName, int fPS) {
/* 18 */     this.modelFileName = modelFileName;
/* 19 */     this.FPS = fPS;
/*    */   }
/*    */   
/*    */   public enum ShowHandArmorType {
/* 23 */     NONE,
/* 24 */     STATIC,
/* 25 */     SKIN;
/*    */   }
/*    */ }


/* Location:              C:\Users\DuduM\OneDrive\Área de Trabalho\mw9\modularwarfare-shining-2023.2.4.4f-fix9.jar!\com\modularwarfare\client\fpp\enhanced\configs\EnhancedRenderConfig.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */