/*    */ package com.modularwarfare.client.customplayer;
/*    */ 
/*    */ import java.util.HashMap;
/*    */ import net.minecraftforge.client.event.RenderPlayerEvent;
/*    */ import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class CPEventHandler
/*    */ {
/* 12 */   public static HashMap<String, CustomPlayerConfig> cpConfig = new HashMap<>();
/*    */   
/*    */   @SubscribeEvent
/*    */   public void onRenderPlayer(RenderPlayerEvent.Pre event) {}
/*    */ }


/* Location:              C:\Users\DuduM\OneDrive\Área de Trabalho\mw9\modularwarfare-shining-2023.2.4.4f-fix9.jar!\com\modularwarfare\client\customplayer\CPEventHandler.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */