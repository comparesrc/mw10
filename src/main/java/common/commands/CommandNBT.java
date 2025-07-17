/*    */ package com.modularwarfare.common.commands;
/*    */ 
/*    */ import java.awt.Toolkit;
/*    */ import java.awt.datatransfer.StringSelection;
/*    */ import net.minecraft.command.CommandBase;
/*    */ import net.minecraft.command.CommandException;
/*    */ import net.minecraft.command.ICommandSender;
/*    */ import net.minecraft.entity.player.EntityPlayerMP;
/*    */ import net.minecraft.server.MinecraftServer;
/*    */ import net.minecraft.util.text.ITextComponent;
/*    */ import net.minecraft.util.text.TextComponentString;
/*    */ import net.minecraft.util.text.TextFormatting;
/*    */ 
/*    */ public class CommandNBT extends CommandBase {
/*    */   public int func_82362_a() {
/* 16 */     return 2;
/*    */   }
/*    */   
/*    */   public String func_71517_b() {
/* 20 */     return "mw-nbt";
/*    */   }
/*    */   
/*    */   public String func_71518_a(ICommandSender sender) {
/* 24 */     return "/mw-nbt";
/*    */   }
/*    */ 
/*    */   
/*    */   public void func_184881_a(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException {
/* 29 */     if (args.length != 1 && 
/* 30 */       sender instanceof EntityPlayerMP) {
/* 31 */       EntityPlayerMP localPlayer = (EntityPlayerMP)sender;
/* 32 */       localPlayer.func_145747_a((ITextComponent)new TextComponentString(TextFormatting.GRAY + "[" + TextFormatting.RED + "ModularWarfare" + TextFormatting.GRAY + "] Item NBT copied to clipboard !"));
/* 33 */       Toolkit.getDefaultToolkit()
/* 34 */         .getSystemClipboard()
/* 35 */         .setContents(new StringSelection(localPlayer
/* 36 */             .func_184614_ca().serializeNBT().toString()), null);
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\DuduM\OneDrive\Área de Trabalho\mw9\modularwarfare-shining-2023.2.4.4f-fix9.jar!\com\modularwarfare\common\commands\CommandNBT.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */