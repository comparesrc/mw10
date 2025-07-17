/*    */ package com.modularwarfare.common.guns;
/*    */ 
/*    */ import com.google.gson.annotations.SerializedName;
/*    */ 
/*    */ public enum WeaponAnimationType
/*    */ {
/*  7 */   BASIC,
/*    */   
/*  9 */   ENHANCED;
/*    */   
/*    */   public static WeaponAnimationType fromString(String modeName) {
/* 12 */     for (WeaponAnimationType animationType : values()) {
/* 13 */       if (animationType.name().equalsIgnoreCase(modeName)) {
/* 14 */         return animationType;
/*    */       }
/*    */     } 
/* 17 */     return null;
/*    */   }
/*    */ }


/* Location:              C:\Users\DuduM\OneDrive\Área de Trabalho\mw9\modularwarfare-shining-2023.2.4.4f-fix9.jar!\com\modularwarfare\common\guns\WeaponAnimationType.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */