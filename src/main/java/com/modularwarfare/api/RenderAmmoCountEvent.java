/*    */ package com.modularwarfare.api;
/*    */ 
/*    */ import net.minecraftforge.fml.common.eventhandler.Cancelable;
/*    */ import net.minecraftforge.fml.common.eventhandler.Event;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ @Cancelable
/*    */ public class RenderAmmoCountEvent
/*    */   extends Event
/*    */ {
/*    */   private final int width;
/*    */   private final int height;
/*    */   
/*    */   public RenderAmmoCountEvent(int width, int height) {
/* 21 */     this.width = width;
/* 22 */     this.height = height;
/*    */   }
/*    */   
/*    */   public int getWidth() {
/* 26 */     return this.width;
/*    */   }
/*    */   
/*    */   public int getHeight() {
/* 30 */     return this.height;
/*    */   }
/*    */ }


/* Location:              C:\Users\DuduM\OneDrive\Área de Trabalho\mw10\modularwarfare-shining-2023.2.4.4f-fix10.jar!\com\modularwarfare\api\RenderAmmoCountEvent.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */