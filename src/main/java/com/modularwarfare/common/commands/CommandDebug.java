/*    */ package com.modularwarfare.common.commands;
/*    */ 
/*    */ import com.modularwarfare.ModConfig;
/*    */ import net.minecraft.command.CommandBase;
/*    */ import net.minecraft.command.CommandException;
/*    */ import net.minecraft.command.ICommandSender;
/*    */ import net.minecraft.server.MinecraftServer;
/*    */ import net.minecraft.util.text.ITextComponent;
/*    */ import net.minecraft.util.text.TextComponentString;
/*    */ 
/*    */ public class CommandDebug extends CommandBase {
/*    */   public int func_82362_a() {
/* 13 */     return 2;
/*    */   }
/*    */   
/*    */   public String func_71517_b() {
/* 17 */     return "mw-debug";
/*    */   }
/*    */   
/*    */   public String func_71518_a(ICommandSender sender) {
/* 21 */     return "/mwdebug";
/*    */   }
/*    */   
/*    */   public void func_184881_a(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException {
/* 25 */     if (sender instanceof net.minecraft.entity.player.EntityPlayerMP) {
/* 26 */       ModConfig.INSTANCE.debug_hits_message = !ModConfig.INSTANCE.debug_hits_message;
/* 27 */       sender.func_145747_a((ITextComponent)new TextComponentString("[ModularWarfare] Debugs hits set to :" + ModConfig.INSTANCE.debug_hits_message));
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\DuduM\OneDrive\Área de Trabalho\mw10\modularwarfare-shining-2023.2.4.4f-fix10.jar!\com\modularwarfare\common\commands\CommandDebug.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */