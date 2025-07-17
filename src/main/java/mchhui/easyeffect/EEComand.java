/*    */ package mchhui.easyeffect;
/*    */ 
/*    */ import java.util.List;
/*    */ import net.minecraft.command.CommandBase;
/*    */ import net.minecraft.command.CommandException;
/*    */ import net.minecraft.command.ICommandSender;
/*    */ import net.minecraft.entity.Entity;
/*    */ import net.minecraft.entity.player.EntityPlayerMP;
/*    */ import net.minecraft.server.MinecraftServer;
/*    */ import net.minecraft.util.text.ITextComponent;
/*    */ import net.minecraft.util.text.TextComponentString;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class EEComand
/*    */   extends CommandBase
/*    */ {
/*    */   public int func_82362_a() {
/* 22 */     return 2;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public String func_71517_b() {
/* 28 */     return "ee";
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public String func_71518_a(ICommandSender sender) {
/* 34 */     return "/ee <player> <x> <y> <z> <vx> <vy> <vz> <ax> <ay> <az> <delay> <fps> <duration> <unit> <size> <image>";
/*    */   }
/*    */ 
/*    */   
/*    */   public void func_184881_a(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException {
/* 39 */     if (args.length != 16) {
/* 40 */       sender.func_145747_a((ITextComponent)new TextComponentString("wrong args"));
/*    */       return;
/*    */     } 
/* 43 */     List<Entity> list = func_184890_c(server, sender, args[0]);
/* 44 */     double x = func_175770_a((sender.func_174791_d()).field_72450_a, args[1], true).func_179628_a();
/* 45 */     double y = func_175770_a((sender.func_174791_d()).field_72448_b, args[2], true).func_179628_a();
/* 46 */     double z = func_175770_a((sender.func_174791_d()).field_72449_c, args[3], true).func_179628_a();
/* 47 */     double vx = Double.valueOf(args[4]).doubleValue();
/* 48 */     double vy = Double.valueOf(args[5]).doubleValue();
/* 49 */     double vz = Double.valueOf(args[6]).doubleValue();
/* 50 */     double ax = Double.valueOf(args[7]).doubleValue();
/* 51 */     double ay = Double.valueOf(args[8]).doubleValue();
/* 52 */     double az = Double.valueOf(args[9]).doubleValue();
/* 53 */     int delay = Integer.valueOf(args[10]).intValue();
/* 54 */     int fps = Integer.valueOf(args[11]).intValue();
/* 55 */     int length = Integer.valueOf(args[12]).intValue();
/* 56 */     int unit = Integer.valueOf(args[13]).intValue();
/* 57 */     double size = Double.valueOf(args[14]).doubleValue();
/* 58 */     list.forEach(e -> {
/*    */           if (e instanceof EntityPlayerMP)
/*    */             EasyEffect.sendEffect((EntityPlayerMP)e, x, y, z, vx, vy, vz, ax, ay, az, delay, fps, length, unit, size, args[15]); 
/*    */         });
/*    */   }
/*    */ }


/* Location:              C:\Users\DuduM\OneDrive\Área de Trabalho\mw10\modularwarfare-shining-2023.2.4.4f-fix10.jar!\mchhui\easyeffect\EEComand.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */