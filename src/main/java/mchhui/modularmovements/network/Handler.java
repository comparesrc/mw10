/*    */ package mchhui.modularmovements.network;
/*    */ 
/*    */ import mchhui.modularmovements.tactical.network.TacticalHandler;
/*    */ import net.minecraft.network.PacketBuffer;
/*    */ import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
/*    */ import net.minecraftforge.fml.common.network.FMLNetworkEvent;
/*    */ 
/*    */ public class Handler
/*    */ {
/*    */   @SubscribeEvent
/*    */   public void onHandle(FMLNetworkEvent.ClientCustomPacketEvent event) {
/* 12 */     PacketBuffer buffer = (PacketBuffer)event.getPacket().payload();
/* 13 */     EnumFeatures type = (EnumFeatures)buffer.func_179257_a(EnumFeatures.class);
/* 14 */     switch (type) {
/*    */       case Tactical:
/* 16 */         TacticalHandler.onHandle(event);
/*    */         break;
/*    */     } 
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   @SubscribeEvent
/*    */   public void onHandle(FMLNetworkEvent.ServerCustomPacketEvent event) {
/* 25 */     PacketBuffer buffer = (PacketBuffer)event.getPacket().payload();
/* 26 */     EnumFeatures type = (EnumFeatures)buffer.func_179257_a(EnumFeatures.class);
/* 27 */     switch (type) {
/*    */       case Tactical:
/* 29 */         TacticalHandler.onHandle(event);
/*    */         break;
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\DuduM\OneDrive\Área de Trabalho\mw10\modularwarfare-shining-2023.2.4.4f-fix10.jar!\mchhui\modularmovements\network\Handler.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */