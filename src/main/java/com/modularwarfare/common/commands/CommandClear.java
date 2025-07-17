/*    */ package com.modularwarfare.common.commands;
/*    */ 
/*    */ import com.modularwarfare.common.capability.extraslots.CapabilityExtra;
/*    */ import com.modularwarfare.common.capability.extraslots.IExtraItemHandler;
/*    */ import net.minecraft.command.CommandBase;
/*    */ import net.minecraft.command.CommandException;
/*    */ import net.minecraft.command.ICommandSender;
/*    */ import net.minecraft.entity.player.EntityPlayer;
/*    */ import net.minecraft.entity.player.EntityPlayerMP;
/*    */ import net.minecraft.item.ItemStack;
/*    */ import net.minecraft.server.MinecraftServer;
/*    */ import net.minecraft.util.EnumFacing;
/*    */ 
/*    */ public class CommandClear
/*    */   extends CommandBase
/*    */ {
/*    */   public int func_82362_a() {
/* 18 */     return 2;
/*    */   }
/*    */   
/*    */   public String func_71517_b() {
/* 22 */     return "mw-clear";
/*    */   }
/*    */   
/*    */   public String func_71518_a(ICommandSender sender) {
/* 26 */     return "/mw-clear <player>";
/*    */   }
/*    */ 
/*    */   
/*    */   public void func_184881_a(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException {
/* 31 */     if (args.length != 1) {
/* 32 */       if (sender instanceof EntityPlayerMP) {
/* 33 */         EntityPlayerMP localPlayer = (EntityPlayerMP)sender;
/* 34 */         for (int i = 0; i < ((IExtraItemHandler)localPlayer.getCapability(CapabilityExtra.CAPABILITY, (EnumFacing)null)).getSlots(); i++) {
/* 35 */           ItemStack extra = ((IExtraItemHandler)localPlayer.getCapability(CapabilityExtra.CAPABILITY, (EnumFacing)null)).getStackInSlot(i);
/* 36 */           if (!extra.func_190926_b()) {
/* 37 */             ((IExtraItemHandler)localPlayer.getCapability(CapabilityExtra.CAPABILITY, (EnumFacing)null)).setStackInSlot(i, ItemStack.field_190927_a);
/* 38 */             CapabilityExtra.sync((EntityPlayer)localPlayer, server.func_184103_al().func_181057_v());
/*    */           } 
/*    */         } 
/*    */       } 
/*    */     } else {
/* 43 */       EntityPlayerMP player = func_184888_a(server, sender, args[0]);
/* 44 */       if (player != null)
/* 45 */         for (int i = 0; i < ((IExtraItemHandler)player.getCapability(CapabilityExtra.CAPABILITY, (EnumFacing)null)).getSlots(); i++) {
/* 46 */           ItemStack extra = ((IExtraItemHandler)player.getCapability(CapabilityExtra.CAPABILITY, (EnumFacing)null)).getStackInSlot(i);
/* 47 */           if (!extra.func_190926_b()) {
/* 48 */             ((IExtraItemHandler)player.getCapability(CapabilityExtra.CAPABILITY, (EnumFacing)null)).setStackInSlot(i, ItemStack.field_190927_a);
/* 49 */             CapabilityExtra.sync((EntityPlayer)player, server.func_184103_al().func_181057_v());
/*    */           } 
/*    */         }  
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\DuduM\OneDrive\Área de Trabalho\mw10\modularwarfare-shining-2023.2.4.4f-fix10.jar!\com\modularwarfare\common\commands\CommandClear.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */