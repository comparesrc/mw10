/*    */ package com.modularwarfare.api;
/*    */ 
/*    */ import net.minecraftforge.fml.common.eventhandler.Event;
/*    */ 
/*    */ public class OnTickRenderEvent
/*    */   extends Event {
/*    */   public float smooth;
/*    */   
/*    */   public OnTickRenderEvent(float smooth) {
/* 10 */     this.smooth = smooth;
/*    */   }
/*    */ }


/* Location:              C:\Users\DuduM\OneDrive\Área de Trabalho\mw10\modularwarfare-shining-2023.2.4.4f-fix10.jar!\com\modularwarfare\api\OnTickRenderEvent.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */