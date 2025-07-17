/*    */ package com.modularwarfare.common.network;
/*    */ 
/*    */ import com.modularwarfare.ModConfig;
/*    */ import com.modularwarfare.ModularWarfare;
/*    */ import com.modularwarfare.common.handler.CommonEventHandler;
/*    */ import io.netty.buffer.ByteBuf;
/*    */ import io.netty.channel.ChannelHandlerContext;
/*    */ import java.util.ArrayList;
/*    */ import net.minecraft.entity.player.EntityPlayer;
/*    */ import net.minecraft.entity.player.EntityPlayerMP;
/*    */ import net.minecraft.network.PacketBuffer;
/*    */ import net.minecraft.util.text.ITextComponent;
/*    */ import net.minecraft.util.text.TextComponentString;
/*    */ 
/*    */ public class PacketVerification
/*    */   extends PacketBase
/*    */ {
/*    */   public boolean usingDirectoryContentPack = false;
/* 19 */   public ArrayList<String> md5List = new ArrayList<>();
/*    */ 
/*    */   
/*    */   public PacketVerification() {}
/*    */ 
/*    */   
/*    */   public PacketVerification(boolean usingDirectoryContentPack, ArrayList<String> md5List) {
/* 26 */     this.usingDirectoryContentPack = usingDirectoryContentPack;
/* 27 */     this.md5List = md5List;
/*    */   }
/*    */ 
/*    */   
/*    */   public void encodeInto(ChannelHandlerContext ctx, ByteBuf data) {
/* 32 */     PacketBuffer buffer = new PacketBuffer(data);
/* 33 */     buffer.writeBoolean(this.usingDirectoryContentPack);
/* 34 */     buffer.writeInt(this.md5List.size());
/* 35 */     for (int i = 0; i < this.md5List.size(); i++) {
/* 36 */       buffer.func_180714_a(this.md5List.get(i));
/*    */     }
/*    */   }
/*    */ 
/*    */   
/*    */   public void decodeInto(ChannelHandlerContext ctx, ByteBuf data) {
/* 42 */     PacketBuffer buffer = new PacketBuffer(data);
/* 43 */     this.usingDirectoryContentPack = buffer.readBoolean();
/* 44 */     int size = buffer.readInt();
/* 45 */     for (int i = 0; i < size; i++) {
/* 46 */       this.md5List.add(buffer.func_150789_c(32767));
/*    */     }
/*    */   }
/*    */ 
/*    */   
/*    */   public void handleServerSide(EntityPlayerMP playerEntity) {
/* 52 */     if (ModConfig.INSTANCE.general.directory_pack_server_kick && this.usingDirectoryContentPack) {
/* 53 */       playerEntity.field_71135_a.func_194028_b((ITextComponent)new TextComponentString("[ModularWarfare] Kicked for client-side is using directory content-pack."));
/*    */     }
/*    */     
/* 56 */     if (!ModConfig.INSTANCE.general.modified_pack_server_kick) {
/*    */       return;
/*    */     }
/* 59 */     ArrayList<String> serverList = ModularWarfare.contentPackHashList;
/* 60 */     if (!ModConfig.INSTANCE.general.content_pack_hash_list.isEmpty()) {
/* 61 */       serverList = ModConfig.INSTANCE.general.content_pack_hash_list;
/*    */     }
/* 63 */     if (serverList.size() == this.md5List.size()) {
/* 64 */       boolean flag = false;
/* 65 */       for (String hash : serverList) {
/* 66 */         if (!this.md5List.contains(hash)) {
/* 67 */           flag = true;
/*    */         }
/*    */       } 
/* 70 */       if (!flag) {
/* 71 */         CommonEventHandler.playerTimeoutMap.remove(playerEntity.func_70005_c_());
/*    */         return;
/*    */       } 
/*    */     } 
/* 75 */     playerEntity.field_71135_a.func_194028_b((ITextComponent)new TextComponentString("[ModularWarfare] Kicked for client-side is using modified content-pack."));
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public void handleClientSide(EntityPlayer clientPlayer) {
/* 81 */     ModularWarfare.NETWORK.sendToServer(new PacketVerification(ModularWarfare.usingDirectoryContentPack, ModularWarfare.contentPackHashList));
/*    */   }
/*    */ }


/* Location:              C:\Users\DuduM\OneDrive\Área de Trabalho\mw10\modularwarfare-shining-2023.2.4.4f-fix10.jar!\com\modularwarfare\common\network\PacketVerification.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */