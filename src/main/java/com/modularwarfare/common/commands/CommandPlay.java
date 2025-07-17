/*    */ package com.modularwarfare.common.commands;
/*    */ 
/*    */ import com.modularwarfare.ModularWarfare;
/*    */ import com.modularwarfare.common.network.PacketBase;
/*    */ import com.modularwarfare.common.network.PacketCustomAnimation;
/*    */ import net.minecraft.command.CommandBase;
/*    */ import net.minecraft.command.CommandException;
/*    */ import net.minecraft.command.ICommandSender;
/*    */ import net.minecraft.entity.player.EntityPlayerMP;
/*    */ import net.minecraft.server.MinecraftServer;
/*    */ 
/*    */ 
/*    */ public class CommandPlay
/*    */   extends CommandBase
/*    */ {
/*    */   public int func_82362_a() {
/* 17 */     return 4;
/*    */   }
/*    */   
/*    */   public String func_71517_b() {
/* 21 */     return "mw-play";
/*    */   }
/*    */   
/*    */   public String func_71518_a(ICommandSender sender) {
/* 25 */     return "/mw-play player startTime endTime speedFactor allowReload allowFire";
/*    */   }
/*    */   
/*    */   public void func_184881_a(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException {
/* 29 */     EntityPlayerMP player = func_184888_a(server, sender, args[0]);
/* 30 */     if (args.length == 6) {
/* 31 */       double s = Double.valueOf(args[1]).doubleValue();
/* 32 */       double e = Double.valueOf(args[2]).doubleValue();
/* 33 */       float speedFactor = Float.valueOf(args[3]).floatValue();
/* 34 */       boolean allowReload = Boolean.valueOf(args[4]).booleanValue();
/* 35 */       boolean allowFire = Boolean.valueOf(args[5]).booleanValue();
/* 36 */       ModularWarfare.NETWORK.sendTo((PacketBase)new PacketCustomAnimation(player
/* 37 */             .func_110124_au(), "", s, e, speedFactor, allowReload, allowFire), player);
/*    */     } 
/* 39 */     if (args.length == 5) {
/* 40 */       String name = args[1];
/* 41 */       float speedFactor = Float.valueOf(args[2]).floatValue();
/* 42 */       boolean allowReload = Boolean.valueOf(args[3]).booleanValue();
/* 43 */       boolean allowFire = Boolean.valueOf(args[4]).booleanValue();
/* 44 */       ModularWarfare.NETWORK.sendTo((PacketBase)new PacketCustomAnimation(player
/* 45 */             .func_110124_au(), "" + name, 0.0D, 0.0D, speedFactor, allowReload, allowFire), player);
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\DuduM\OneDrive\Área de Trabalho\mw10\modularwarfare-shining-2023.2.4.4f-fix10.jar!\com\modularwarfare\common\commands\CommandPlay.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */