/*    */ package com.modularwarfare.common.network;
/*    */ 
/*    */ import com.modularwarfare.ModularWarfare;
/*    */ import com.modularwarfare.common.guns.GunType;
/*    */ import com.modularwarfare.common.guns.ItemGun;
/*    */ import com.modularwarfare.common.guns.WeaponFireMode;
/*    */ import com.modularwarfare.common.guns.manager.ShotManager;
/*    */ import io.netty.buffer.ByteBuf;
/*    */ import io.netty.channel.ChannelHandlerContext;
/*    */ import net.minecraft.entity.player.EntityPlayer;
/*    */ import net.minecraft.entity.player.EntityPlayerMP;
/*    */ import net.minecraft.world.WorldServer;
/*    */ import net.minecraftforge.fml.common.network.ByteBufUtils;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class PacketGunFire
/*    */   extends PacketBase
/*    */ {
/*    */   public String internalname;
/*    */   public int fireTickDelay;
/*    */   public float recoilPitch;
/*    */   public float recoilYaw;
/*    */   public float recoilAimReducer;
/*    */   public float bulletSpread;
/*    */   public float rotationPitch;
/*    */   public float rotationYaw;
/*    */   
/*    */   public PacketGunFire() {}
/*    */   
/*    */   public PacketGunFire(String internalname, int fireTickDelay, float recoilPitch, float recoilYaw, float recoilAimReducer, float bulletSpread, float rotationPitch, float rotationYaw) {
/* 36 */     this.internalname = internalname;
/* 37 */     this.fireTickDelay = fireTickDelay;
/* 38 */     this.recoilPitch = recoilPitch;
/* 39 */     this.recoilYaw = recoilYaw;
/* 40 */     this.recoilAimReducer = recoilAimReducer;
/* 41 */     this.bulletSpread = bulletSpread;
/*    */     
/* 43 */     this.rotationPitch = rotationPitch;
/* 44 */     this.rotationYaw = rotationYaw;
/*    */   }
/*    */ 
/*    */   
/*    */   public void encodeInto(ChannelHandlerContext ctx, ByteBuf data) {
/* 49 */     ByteBufUtils.writeUTF8String(data, this.internalname);
/* 50 */     data.writeInt(this.fireTickDelay);
/* 51 */     data.writeFloat(this.recoilPitch);
/* 52 */     data.writeFloat(this.recoilYaw);
/* 53 */     data.writeFloat(this.recoilAimReducer);
/* 54 */     data.writeFloat(this.bulletSpread);
/*    */     
/* 56 */     data.writeFloat(this.rotationPitch);
/* 57 */     data.writeFloat(this.rotationYaw);
/*    */   }
/*    */ 
/*    */   
/*    */   public void decodeInto(ChannelHandlerContext ctx, ByteBuf data) {
/* 62 */     this.internalname = ByteBufUtils.readUTF8String(data);
/* 63 */     this.fireTickDelay = data.readInt();
/* 64 */     this.recoilPitch = data.readFloat();
/* 65 */     this.recoilYaw = data.readFloat();
/* 66 */     this.recoilAimReducer = data.readFloat();
/* 67 */     this.bulletSpread = data.readFloat();
/*    */     
/* 69 */     this.rotationPitch = data.readFloat();
/* 70 */     this.rotationYaw = data.readFloat();
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public void handleServerSide(final EntityPlayerMP entityPlayer) {
/* 76 */     WorldServer worldServer = (WorldServer)entityPlayer.field_70170_p;
/* 77 */     worldServer.func_152344_a(new Runnable() {
/*    */           public void run() {
/* 79 */             if (entityPlayer != null && 
/* 80 */               ModularWarfare.gunTypes.get(PacketGunFire.this.internalname) != null) {
/* 81 */               ItemGun itemGun = (ItemGun)ModularWarfare.gunTypes.get(PacketGunFire.this.internalname);
/* 82 */               WeaponFireMode fireMode = GunType.getFireMode(entityPlayer.func_184614_ca());
/* 83 */               if (fireMode == null)
/*    */                 return; 
/* 85 */               ShotManager.fireServer((EntityPlayer)entityPlayer, PacketGunFire.this.rotationPitch, PacketGunFire.this.rotationYaw, entityPlayer.field_70170_p, entityPlayer.func_184614_ca(), itemGun, fireMode, PacketGunFire.this.fireTickDelay, PacketGunFire.this.recoilPitch, PacketGunFire.this.recoilYaw, PacketGunFire.this.recoilAimReducer, PacketGunFire.this.bulletSpread);
/*    */             } 
/*    */           }
/*    */         });
/*    */   }
/*    */   
/*    */   public void handleClientSide(EntityPlayer entityPlayer) {}
/*    */ }


/* Location:              C:\Users\DuduM\OneDrive\Área de Trabalho\mw9\modularwarfare-shining-2023.2.4.4f-fix9.jar!\com\modularwarfare\common\network\PacketGunFire.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */