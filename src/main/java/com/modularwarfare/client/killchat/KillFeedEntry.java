/*    */ package com.modularwarfare.client.killchat;
/*    */ 
/*    */ public class KillFeedEntry {
/*    */   private String text;
/*    */   private boolean causeIsGun = false;
/*    */   private String weaponInternalName;
/*    */   private int timeLived;
/*    */   private int timeLiving;
/*    */   
/*    */   public KillFeedEntry(String text, int timeLiving, String weaponInternalName) {
/* 11 */     this.text = text;
/* 12 */     this.timeLiving = timeLiving;
/*    */     
/* 14 */     if (weaponInternalName != null) {
/* 15 */       this.weaponInternalName = weaponInternalName;
/* 16 */       this.causeIsGun = true;
/*    */     } 
/*    */   }
/*    */   
/*    */   public int incrementTimeLived() {
/* 21 */     return this.timeLived++;
/*    */   }
/*    */   
/*    */   public boolean isCausedByGun() {
/* 25 */     return this.causeIsGun;
/*    */   }
/*    */   
/*    */   public String getWeaponInternalName() {
/* 29 */     return this.weaponInternalName;
/*    */   }
/*    */   
/*    */   public int getTimeLiving() {
/* 33 */     return this.timeLiving;
/*    */   }
/*    */   
/*    */   public int getTimeLived() {
/* 37 */     return this.timeLived;
/*    */   }
/*    */   
/*    */   public String getText() {
/* 41 */     return this.text;
/*    */   }
/*    */ }


/* Location:              C:\Users\DuduM\OneDrive\Área de Trabalho\mw10\modularwarfare-shining-2023.2.4.4f-fix10.jar!\com\modularwarfare\client\killchat\KillFeedEntry.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */