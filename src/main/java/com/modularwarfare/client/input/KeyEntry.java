/*    */ package com.modularwarfare.client.input;
/*    */ 
/*    */ import net.minecraft.client.settings.KeyBinding;
/*    */ 
/*    */ public class KeyEntry
/*    */ {
/*    */   public KeyType keyType;
/*    */   public KeyBinding keyBinding;
/*    */   
/*    */   public KeyEntry(KeyType keyType) {
/* 11 */     this.keyType = keyType;
/* 12 */     this.keyBinding = new KeyBinding(keyType.displayName, keyType.keyCode, "ModularWarfare");
/*    */   }
/*    */ }


/* Location:              C:\Users\DuduM\OneDrive\Área de Trabalho\mw10\modularwarfare-shining-2023.2.4.4f-fix10.jar!\com\modularwarfare\client\input\KeyEntry.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */