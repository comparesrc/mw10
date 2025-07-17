/*    */ package com.modularwarfare.common.guns;
/*    */ 
/*    */ import com.google.gson.annotations.SerializedName;
/*    */ 
/*    */ public enum WeaponDotColorType
/*    */ {
/*  7 */   RED,
/*    */   
/*  9 */   BLUE,
/*    */   
/* 11 */   GREEN;
/*    */ 
/*    */   
/*    */   public static WeaponDotColorType fromString(String modeName) {
/* 15 */     for (WeaponDotColorType dotColorType : values()) {
/* 16 */       if (dotColorType.name().equalsIgnoreCase(modeName)) {
/* 17 */         return dotColorType;
/*    */       }
/*    */     } 
/* 20 */     return null;
/*    */   }
/*    */ }


/* Location:              C:\Users\DuduM\OneDrive\Área de Trabalho\mw9\modularwarfare-shining-2023.2.4.4f-fix9.jar!\com\modularwarfare\common\guns\WeaponDotColorType.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */