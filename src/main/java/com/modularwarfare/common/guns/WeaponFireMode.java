/*    */ package com.modularwarfare.common.guns;
/*    */ 
/*    */ import com.google.gson.annotations.SerializedName;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public enum WeaponFireMode
/*    */ {
/* 11 */   SEMI,
/*    */ 
/*    */ 
/*    */ 
/*    */   
/* 16 */   FULL,
/*    */ 
/*    */ 
/*    */ 
/*    */   
/* 21 */   BURST;
/*    */   
/*    */   public static WeaponFireMode fromString(String modeName) {
/* 24 */     for (WeaponFireMode fireMode : values()) {
/* 25 */       if (fireMode.name().equalsIgnoreCase(modeName)) {
/* 26 */         return fireMode;
/*    */       }
/*    */     } 
/* 29 */     return null;
/*    */   }
/*    */ }


/* Location:              C:\Users\DuduM\OneDrive\Área de Trabalho\mw10\modularwarfare-shining-2023.2.4.4f-fix10.jar!\com\modularwarfare\common\guns\WeaponFireMode.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */