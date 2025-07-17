/*    */ package mchhui.easyeffect;
/*    */ 
/*    */ import io.netty.buffer.Unpooled;
/*    */ import net.minecraft.command.ICommand;
/*    */ import net.minecraft.entity.player.EntityPlayerMP;
/*    */ import net.minecraft.network.PacketBuffer;
/*    */ import net.minecraftforge.common.MinecraftForge;
/*    */ import net.minecraftforge.fml.common.FMLCommonHandler;
/*    */ import net.minecraftforge.fml.common.Mod;
/*    */ import net.minecraftforge.fml.common.Mod.EventHandler;
/*    */ import net.minecraftforge.fml.common.event.FMLInitializationEvent;
/*    */ import net.minecraftforge.fml.common.event.FMLServerStartingEvent;
/*    */ import net.minecraftforge.fml.common.network.FMLEventChannel;
/*    */ import net.minecraftforge.fml.common.network.NetworkRegistry;
/*    */ import net.minecraftforge.fml.common.network.internal.FMLProxyPacket;
/*    */ import net.minecraftforge.fml.relauncher.Side;
/*    */ 
/*    */ @Mod(modid = "easyeffect")
/*    */ public class EasyEffect
/*    */ {
/*    */   public static FMLEventChannel channel;
/*    */   
/*    */   @EventHandler
/*    */   public void onInit(FMLInitializationEvent event) {
/* 25 */     channel = NetworkRegistry.INSTANCE.newEventDrivenChannel("easyeffect");
/* 26 */     if (FMLCommonHandler.instance().getSide() == Side.CLIENT) {
/* 27 */       EasyEffectClient client = new EasyEffectClient();
/* 28 */       MinecraftForge.EVENT_BUS.register(client);
/* 29 */       channel.register(client);
/*    */     } 
/*    */   }
/*    */   
/*    */   @EventHandler
/*    */   public void onServerStarting(FMLServerStartingEvent event) {
/* 35 */     event.registerServerCommand((ICommand)new EEComand());
/*    */   }
/*    */ 
/*    */   
/*    */   public static void sendEffect(EntityPlayerMP e, double x, double y, double z, double vx, double vy, double vz, double ax, double ay, double az, int delay, int fps, int length, int unit, double size, String path) {
/* 40 */     PacketBuffer buf = new PacketBuffer(Unpooled.buffer());
/* 41 */     buf.writeDouble(x);
/* 42 */     buf.writeDouble(y);
/* 43 */     buf.writeDouble(z);
/* 44 */     buf.writeDouble(vx);
/* 45 */     buf.writeDouble(vy);
/* 46 */     buf.writeDouble(vz);
/* 47 */     buf.writeDouble(ax);
/* 48 */     buf.writeDouble(ay);
/* 49 */     buf.writeDouble(az);
/* 50 */     buf.writeInt(delay);
/* 51 */     buf.writeInt(fps);
/* 52 */     buf.writeInt(length);
/* 53 */     buf.writeInt(unit);
/* 54 */     buf.writeDouble(size);
/* 55 */     buf.func_180714_a(path);
/* 56 */     channel.sendTo(new FMLProxyPacket(buf, "easyeffect"), e);
/*    */   }
/*    */ }


/* Location:              C:\Users\DuduM\OneDrive\Área de Trabalho\mw10\modularwarfare-shining-2023.2.4.4f-fix10.jar!\mchhui\easyeffect\EasyEffect.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */