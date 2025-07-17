/*    */ package com.modularwarfare.client.commands;
/*    */ 
/*    */ import com.modularwarfare.ModularWarfare;
/*    */ import net.minecraft.command.CommandBase;
/*    */ import net.minecraft.command.CommandException;
/*    */ import net.minecraft.command.ICommandSender;
/*    */ import net.minecraft.server.MinecraftServer;
/*    */ import net.minecraft.util.text.ITextComponent;
/*    */ import net.minecraft.util.text.TextComponentString;
/*    */ 
/*    */ public class CommandMWClient
/*    */   extends CommandBase
/*    */ {
/*    */   public int func_82362_a() {
/* 15 */     return 0;
/*    */   }
/*    */ 
/*    */   
/*    */   public String func_71517_b() {
/* 20 */     return "mw-client";
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public String func_71518_a(ICommandSender sender) {
/* 26 */     return "/mw-client";
/*    */   }
/*    */ 
/*    */   
/*    */   public void func_184881_a(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException {
/* 31 */     if (args.length == 1 && 
/* 32 */       args[0].equals("md5")) {
/* 33 */       for (int i = 0; i < ModularWarfare.contentPackHashList.size(); i++) {
/* 34 */         sender.func_145747_a((ITextComponent)new TextComponentString(ModularWarfare.contentPackHashList.get(i)));
/*    */       }
/* 36 */       if (ModularWarfare.contentPackHashList.size() == 0) {
/* 37 */         sender.func_145747_a((ITextComponent)new TextComponentString("There is not any content pack."));
/*    */       }
/*    */       
/*    */       return;
/*    */     } 
/* 42 */     sender.func_145747_a((ITextComponent)new TextComponentString("/mw-client md5 | Get the md5 of the content pack"));
/*    */   }
/*    */ }


/* Location:              C:\Users\DuduM\OneDrive\Área de Trabalho\mw9\modularwarfare-shining-2023.2.4.4f-fix9.jar!\com\modularwarfare\client\commands\CommandMWClient.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */