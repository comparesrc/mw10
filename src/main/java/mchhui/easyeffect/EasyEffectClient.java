/*    */ package mchhui.easyeffect;
/*    */ 
/*    */ import net.minecraft.client.renderer.GlStateManager;
/*    */ import net.minecraft.network.PacketBuffer;
/*    */ import net.minecraftforge.client.event.RenderWorldLastEvent;
/*    */ import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
/*    */ import net.minecraftforge.fml.common.network.FMLNetworkEvent;
/*    */ 
/*    */ public class EasyEffectClient {
/* 10 */   public static EasyEffectRenderer renderer = new EasyEffectRenderer();
/*    */   
/*    */   @SubscribeEvent
/*    */   public void onHandle(FMLNetworkEvent.ClientCustomPacketEvent event) {
/* 14 */     PacketBuffer buf = new PacketBuffer(event.getPacket().payload());
/* 15 */     double x = buf.readDouble();
/* 16 */     double y = buf.readDouble();
/* 17 */     double z = buf.readDouble();
/* 18 */     double vx = buf.readDouble();
/* 19 */     double vy = buf.readDouble();
/* 20 */     double vz = buf.readDouble();
/* 21 */     double ax = buf.readDouble();
/* 22 */     double ay = buf.readDouble();
/* 23 */     double az = buf.readDouble();
/* 24 */     int delay = buf.readInt();
/* 25 */     int fps = buf.readInt();
/* 26 */     int length = buf.readInt();
/* 27 */     int unit = buf.readInt();
/* 28 */     double size = buf.readDouble();
/* 29 */     String name = buf.func_150789_c(32767);
/* 30 */     renderer.objects.add(new EEObject(name, delay, fps, unit, length, x, y, z, vx, vy, vz, ax, ay, az, size, System.currentTimeMillis()));
/*    */   }
/*    */   
/*    */   @SubscribeEvent
/*    */   public void onRenderWolrd(RenderWorldLastEvent event) {
/* 35 */     GlStateManager.func_179094_E();
/* 36 */     GlStateManager.func_179132_a(false);
/* 37 */     GlStateManager.func_179147_l();
/* 38 */     renderer.render(event.getPartialTicks());
/* 39 */     GlStateManager.func_179084_k();
/* 40 */     GlStateManager.func_179132_a(true);
/* 41 */     GlStateManager.func_179121_F();
/*    */   }
/*    */ }


/* Location:              C:\Users\DuduM\OneDrive\Área de Trabalho\mw10\modularwarfare-shining-2023.2.4.4f-fix10.jar!\mchhui\easyeffect\EasyEffectClient.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */