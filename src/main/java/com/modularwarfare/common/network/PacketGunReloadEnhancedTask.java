/*    */ package com.modularwarfare.common.network;
/*    */ 
/*    */ import com.modularwarfare.client.handler.ClientTickHandler;
/*    */ import io.netty.buffer.ByteBuf;
/*    */ import io.netty.channel.ChannelHandlerContext;
/*    */ import net.minecraft.entity.player.EntityPlayer;
/*    */ import net.minecraft.entity.player.EntityPlayerMP;
/*    */ import net.minecraft.item.ItemStack;
/*    */ import net.minecraft.nbt.JsonToNBT;
/*    */ import net.minecraft.nbt.NBTException;
/*    */ import net.minecraftforge.fml.common.network.ByteBufUtils;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class PacketGunReloadEnhancedTask
/*    */   extends PacketBase
/*    */ {
/*    */   public ItemStack prognosisAmmo;
/*    */   public boolean isQuickly = false;
/*    */   
/*    */   public PacketGunReloadEnhancedTask() {}
/*    */   
/*    */   public PacketGunReloadEnhancedTask(ItemStack prognosisAmmo) {
/* 25 */     this.prognosisAmmo = prognosisAmmo;
/*    */   }
/*    */   
/*    */   public PacketGunReloadEnhancedTask(ItemStack prognosisAmmo, boolean isQuickly) {
/* 29 */     this.prognosisAmmo = prognosisAmmo;
/* 30 */     this.isQuickly = isQuickly;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public void encodeInto(ChannelHandlerContext ctx, ByteBuf data) {
/* 36 */     ByteBufUtils.writeUTF8String(data, this.prognosisAmmo.serializeNBT().toString());
/* 37 */     data.writeBoolean(this.isQuickly);
/*    */   }
/*    */ 
/*    */   
/*    */   public void decodeInto(ChannelHandlerContext ctx, ByteBuf data) {
/*    */     try {
/* 43 */       this.prognosisAmmo = new ItemStack(JsonToNBT.func_180713_a(ByteBufUtils.readUTF8String(data)));
/* 44 */       this.prognosisAmmo.func_190920_e(1);
/* 45 */       this.isQuickly = data.readBoolean();
/* 46 */     } catch (NBTException e) {
/* 47 */       e.printStackTrace();
/*    */     } 
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public void handleServerSide(EntityPlayerMP playerEntity) {}
/*    */ 
/*    */ 
/*    */   
/*    */   public void handleClientSide(EntityPlayer clientPlayer) {
/* 58 */     ClientTickHandler.reloadEnhancedPrognosisAmmo = this.prognosisAmmo;
/* 59 */     ClientTickHandler.reloadEnhancedIsQuickly = this.isQuickly;
/*    */   }
/*    */ }


/* Location:              C:\Users\DuduM\OneDrive\Área de Trabalho\mw10\modularwarfare-shining-2023.2.4.4f-fix10.jar!\com\modularwarfare\common\network\PacketGunReloadEnhancedTask.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */