/*    */ package com.modularwarfare.common.guns;
/*    */ 
/*    */ import com.google.gson.annotations.SerializedName;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public enum WeaponType
/*    */ {
/* 11 */   Custom("Custom"),
/* 12 */   Pistol("Pistol"),
/* 13 */   Revolver("Revolver"),
/* 14 */   MP("MP"),
/* 15 */   SMG("SMG"),
/* 16 */   Carbine("Carbine"),
/* 17 */   RIFLE("Rifle"),
/* 18 */   AR("Assault Rifle"),
/* 19 */   DMR("DMR"),
/* 20 */   SemiSniper("Semi-sniper"),
/* 21 */   BoltSniper("Bolt-sniper"),
/* 22 */   Shotgun("Shotgun"),
/* 23 */   Launcher("Launcher");
/*    */   
/*    */   public String typeName;
/*    */ 
/*    */   
/*    */   WeaponType(String typeName) {
/* 29 */     this.typeName = typeName;
/*    */   }
/*    */   
/*    */   public static WeaponType fromEventName(String typeName) {
/* 33 */     if (typeName != null) {
/* 34 */       for (WeaponType soundType : values()) {
/* 35 */         if (soundType.typeName.equalsIgnoreCase(typeName)) {
/* 36 */           return soundType;
/*    */         }
/*    */       } 
/*    */     }
/* 40 */     return null;
/*    */   }
/*    */ }


/* Location:              C:\Users\DuduM\OneDrive\Área de Trabalho\mw9\modularwarfare-shining-2023.2.4.4f-fix9.jar!\com\modularwarfare\common\guns\WeaponType.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */