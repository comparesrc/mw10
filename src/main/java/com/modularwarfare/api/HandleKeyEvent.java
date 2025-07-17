/*    */ package com.modularwarfare.api;
/*    */ 
/*    */ import com.modularwarfare.client.input.KeyType;
/*    */ import net.minecraftforge.fml.common.eventhandler.Event;
/*    */ 
/*    */ public class HandleKeyEvent extends Event {
/*    */   public KeyType keyType;
/*    */   
/*    */   public HandleKeyEvent(KeyType keyType) {
/* 10 */     this.keyType = keyType;
/*    */   }
/*    */ }


/* Location:              C:\Users\DuduM\OneDrive\Área de Trabalho\mw10\modularwarfare-shining-2023.2.4.4f-fix10.jar!\com\modularwarfare\api\HandleKeyEvent.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */