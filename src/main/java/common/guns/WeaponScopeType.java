/*    */ package com.modularwarfare.common.guns;
/*    */ 
/*    */ import com.google.gson.annotations.SerializedName;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ @Deprecated
/*    */ public enum WeaponScopeType
/*    */ {
/* 12 */   DEFAULT,
/*    */   
/* 14 */   REDDOT,
/*    */   
/* 16 */   TWO,
/*    */   
/* 18 */   FOUR,
/*    */   
/* 20 */   EIGHT,
/*    */   
/* 22 */   FIFTEEN;
/*    */   
/*    */   public static WeaponScopeType fromString(String modeName) {
/* 25 */     for (WeaponScopeType scopeType : values()) {
/* 26 */       if (scopeType.name().equalsIgnoreCase(modeName)) {
/* 27 */         return scopeType;
/*    */       }
/*    */     } 
/* 30 */     return null;
/*    */   }
/*    */ }


/* Location:              C:\Users\DuduM\OneDrive\Área de Trabalho\mw9\modularwarfare-shining-2023.2.4.4f-fix9.jar!\com\modularwarfare\common\guns\WeaponScopeType.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */