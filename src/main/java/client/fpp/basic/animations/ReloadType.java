/*    */ package com.modularwarfare.client.fpp.basic.animations;
/*    */ 
/*    */ public enum ReloadType
/*    */ {
/*  5 */   Unload(0),
/*  6 */   Load(1),
/*  7 */   Full(2);
/*    */   
/*    */   public int i;
/*    */   
/*    */   ReloadType(int i) {
/* 12 */     this.i = i;
/*    */   }
/*    */   
/*    */   public static ReloadType getTypeFromInt(int i) {
/* 16 */     for (ReloadType type : values()) {
/* 17 */       if (type.i == i) {
/* 18 */         return type;
/*    */       }
/*    */     } 
/* 21 */     return null;
/*    */   }
/*    */ }


/* Location:              C:\Users\DuduM\OneDrive\Área de Trabalho\mw9\modularwarfare-shining-2023.2.4.4f-fix9.jar!\com\modularwarfare\client\fpp\basic\animations\ReloadType.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */