/*    */ package com.modularwarfare.client.patch.obfuscate;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class ObfuscateInteropImpl
/*    */   implements ObfuscateCompatInterop
/*    */ {
/*    */   public boolean fixApplied = false;
/*    */   
/*    */   public boolean isModLoaded() {
/* 12 */     return true;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean isFixApplied() {
/* 17 */     return this.fixApplied;
/*    */   }
/*    */ 
/*    */   
/*    */   public void setFixed() {
/* 22 */     this.fixApplied = true;
/*    */   }
/*    */ }


/* Location:              C:\Users\DuduM\OneDrive\Área de Trabalho\mw9\modularwarfare-shining-2023.2.4.4f-fix9.jar!\com\modularwarfare\client\patch\obfuscate\ObfuscateInteropImpl.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */