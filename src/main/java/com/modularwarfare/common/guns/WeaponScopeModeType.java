/*    */ package com.modularwarfare.common.guns;
/*    */ 
/*    */ import com.google.gson.annotations.SerializedName;
/*    */ 
/*    */ 
/*    */ public enum WeaponScopeModeType
/*    */ {
/*  8 */   SIMPLE_DOT("dot", false, false, false, true),
/*    */ 
/*    */   
/* 11 */   SIMPLE("simple", false, false, false),
/*    */   
/* 13 */   NORMAL("normal", false, true, true),
/*    */   
/* 15 */   PIP("pip", true, true, true);
/*    */   
/*    */   public boolean isDot = false;
/*    */   
/*    */   public String name;
/*    */   public boolean isPIP;
/*    */   public boolean isMirror;
/*    */   public boolean insideGunRendering;
/*    */   
/*    */   WeaponScopeModeType(String name, boolean isPIP, boolean isMirror, boolean insideGunRendering, boolean isDot) {
/* 25 */     this.name = name;
/* 26 */     this.isPIP = isPIP;
/* 27 */     this.isMirror = isMirror;
/* 28 */     this.insideGunRendering = insideGunRendering;
/* 29 */     this.isDot = isDot;
/*    */   }
/*    */ }


/* Location:              C:\Users\DuduM\OneDrive\Área de Trabalho\mw10\modularwarfare-shining-2023.2.4.4f-fix10.jar!\com\modularwarfare\common\guns\WeaponScopeModeType.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */