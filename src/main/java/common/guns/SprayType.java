/*    */ package com.modularwarfare.common.guns;
/*    */ 
/*    */ import com.modularwarfare.common.type.BaseType;
/*    */ 
/*    */ public class SprayType
/*    */   extends BaseType {
/*    */   public String skinName;
/*  8 */   public int usableMaxAmount = 5;
/*    */ 
/*    */   
/*    */   public void loadExtraValues() {
/* 12 */     if (this.maxStackSize == null) {
/* 13 */       this.maxStackSize = Integer.valueOf(1);
/*    */     }
/* 15 */     loadBaseValues();
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public void reloadModel() {}
/*    */ 
/*    */ 
/*    */   
/*    */   public String getAssetDir() {
/* 25 */     return "sprays";
/*    */   }
/*    */ }


/* Location:              C:\Users\DuduM\OneDrive\Área de Trabalho\mw9\modularwarfare-shining-2023.2.4.4f-fix9.jar!\com\modularwarfare\common\guns\SprayType.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */