/*    */ package com.modularwarfare.api;
/*    */ 
/*    */ import com.google.gson.annotations.SerializedName;
/*    */ 
/*    */ public enum MWArmorModel
/*    */ {
/*  7 */   HEAD("headModel"),
/*    */   
/*  9 */   BODY("bodyModel"),
/*    */   
/* 11 */   LEFTLEG("leftLegModel"),
/* 12 */   RIGHTLEG("rightLegModel"),
/*    */   
/* 14 */   LEFTARM("leftArmModel"),
/* 15 */   RIGHTARM("rightArmModel"),
/*    */   
/* 17 */   LEFTFOOT("leftFootModel"),
/* 18 */   RIGHTFOOT("rightFootModel");
/*    */   
/*    */   public String part;
/*    */   
/*    */   MWArmorModel(String part) {
/* 23 */     this.part = part;
/*    */   }
/*    */ }


/* Location:              C:\Users\DuduM\OneDrive\Área de Trabalho\mw10\modularwarfare-shining-2023.2.4.4f-fix10.jar!\com\modularwarfare\api\MWArmorModel.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */