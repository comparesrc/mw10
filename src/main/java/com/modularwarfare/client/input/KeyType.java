/*    */ package com.modularwarfare.client.input;
/*    */ 
/*    */ 
/*    */ 
/*    */ public enum KeyType
/*    */ {
/*  7 */   GunReload("Reload Gun", 19),
/*  8 */   ClientReload("Reload Client", 67),
/*  9 */   DebugMode("Debug Mode", 68),
/* 10 */   FireMode("Fire Mode", 47),
/* 11 */   Inspect("Inspect", 49),
/* 12 */   GunUnload("Unload Key", 22),
/* 13 */   AddAttachment("Attachment Mode", 50),
/* 14 */   Flashlight("Flashlight", 35),
/* 15 */   Backpack("Backpack Inventory", 48),
/* 16 */   Jetpack("Jetpack fire", 45),
/*    */ 
/*    */   
/* 19 */   Left("Left (Attach mode)", 203),
/* 20 */   Right("Right (Attach mode)", 205),
/* 21 */   Up("Up (Attach mode)", 200),
/* 22 */   Down("Down (Attach mode)", 208);
/*    */   
/*    */   public String displayName;
/*    */   
/*    */   public int keyCode;
/*    */ 
/*    */   
/*    */   KeyType(String displayName, int keyCode) {
/* 30 */     this.displayName = displayName;
/* 31 */     this.keyCode = keyCode;
/*    */   }
/*    */ 
/*    */   
/*    */   public String toString() {
/* 36 */     return name().toLowerCase();
/*    */   }
/*    */ }


/* Location:              C:\Users\DuduM\OneDrive\Área de Trabalho\mw10\modularwarfare-shining-2023.2.4.4f-fix10.jar!\com\modularwarfare\client\input\KeyType.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */