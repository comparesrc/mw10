/*     */ package com.modularwarfare.common.network;
/*     */ 
/*     */ import com.modularwarfare.ModularWarfare;
/*     */ import com.modularwarfare.api.AnimationUtils;
/*     */ import com.modularwarfare.common.backpacks.BackpackType;
/*     */ import com.modularwarfare.common.backpacks.ItemBackpack;
/*     */ import com.modularwarfare.common.capability.extraslots.CapabilityExtra;
/*     */ import com.modularwarfare.common.capability.extraslots.IExtraItemHandler;
/*     */ import com.modularwarfare.common.guns.WeaponSoundType;
/*     */ import io.netty.buffer.ByteBuf;
/*     */ import io.netty.channel.ChannelHandlerContext;
/*     */ import java.lang.reflect.Field;
/*     */ import net.minecraft.block.Block;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.entity.player.EntityPlayerMP;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.network.NetHandlerPlayServer;
/*     */ import net.minecraft.network.PacketBuffer;
/*     */ import net.minecraft.util.EnumParticleTypes;
/*     */ import net.minecraft.world.WorldServer;
/*     */ 
/*     */ public class PacketBackpackJet
/*     */   extends PacketBase
/*     */ {
/*  26 */   public String name = "";
/*     */   public boolean jetFire;
/*  28 */   public int jetDuraton = 100;
/*     */   
/*     */   public static Field fieldFloatingTickCount;
/*     */ 
/*     */   
/*     */   public PacketBackpackJet() {}
/*     */   
/*     */   public PacketBackpackJet(boolean jetFire) {
/*  36 */     this.jetFire = jetFire;
/*     */   }
/*     */   
/*     */   public PacketBackpackJet(String name, int jetDuraton) {
/*  40 */     this.name = name;
/*  41 */     this.jetDuraton = jetDuraton;
/*     */   }
/*     */ 
/*     */   
/*     */   public void encodeInto(ChannelHandlerContext ctx, ByteBuf data) {
/*  46 */     PacketBuffer buf = new PacketBuffer(data);
/*  47 */     buf.func_180714_a(this.name);
/*  48 */     buf.writeBoolean(this.jetFire);
/*  49 */     buf.writeInt(this.jetDuraton);
/*     */   }
/*     */ 
/*     */   
/*     */   public void decodeInto(ChannelHandlerContext ctx, ByteBuf data) {
/*  54 */     PacketBuffer buf = new PacketBuffer(data);
/*  55 */     this.name = buf.func_150789_c(32767);
/*  56 */     this.jetFire = buf.readBoolean();
/*  57 */     this.jetDuraton = buf.readInt();
/*     */   }
/*     */ 
/*     */   
/*     */   public void handleServerSide(EntityPlayerMP playerEntity) {
/*  62 */     playerEntity.field_70143_R = 0.0F;
/*  63 */     playerEntity.func_71122_b(this.jetDuraton, this.jetFire);
/*  64 */     if (playerEntity.hasCapability(CapabilityExtra.CAPABILITY, null)) {
/*  65 */       IExtraItemHandler extraSlots = (IExtraItemHandler)playerEntity.getCapability(CapabilityExtra.CAPABILITY, null);
/*  66 */       ItemStack itemstackBackpack = extraSlots.getStackInSlot(0);
/*     */       
/*  68 */       if (!itemstackBackpack.func_190926_b() && 
/*  69 */         itemstackBackpack.func_77973_b() instanceof ItemBackpack) {
/*  70 */         BackpackType backpack = ((ItemBackpack)itemstackBackpack.func_77973_b()).type;
/*  71 */         if (!this.jetFire) {
/*  72 */           ModularWarfare.NETWORK.sendToAllTracking(new PacketBackpackJet(playerEntity.func_70005_c_(), 100), (Entity)playerEntity);
/*     */         } else {
/*     */           
/*  75 */           ModularWarfare.NETWORK.sendToAllTracking(new PacketBackpackJet(playerEntity
/*  76 */                 .func_70005_c_(), backpack.jetElytraBoostDuration * 50), (Entity)playerEntity);
/*     */         } 
/*     */         
/*  79 */         if (backpack.isJet && backpack.weaponSoundMap != null) {
/*  80 */           backpack.playSoundPos(playerEntity.func_180425_c(), playerEntity.field_70170_p, WeaponSoundType.JetWork);
/*  81 */           if (this.jetFire) {
/*  82 */             backpack.playSoundPos(playerEntity.func_180425_c(), playerEntity.field_70170_p, WeaponSoundType.JetFire);
/*     */           } else {
/*     */             
/*  85 */             ((WorldServer)playerEntity.field_70170_p).func_175739_a(EnumParticleTypes.BLOCK_DUST, playerEntity.field_70165_t, playerEntity.field_70163_u, playerEntity.field_70161_v, 5, 0.0D, 0.0D, 0.0D, 0.15000000596046448D, new int[] {
/*     */                   
/*  87 */                   Block.func_176210_f(playerEntity.field_70170_p.func_180495_p(playerEntity.func_180425_c().func_177977_b())) });
/*  88 */             ((WorldServer)playerEntity.field_70170_p).func_175739_a(EnumParticleTypes.BLOCK_DUST, playerEntity.field_70165_t, playerEntity.field_70163_u, playerEntity.field_70161_v, 5, 0.0D, 0.0D, 0.0D, 0.15000000596046448D, new int[] {
/*     */                   
/*  90 */                   Block.func_176210_f(playerEntity.field_70170_p.func_180495_p(playerEntity.func_180425_c().func_177979_c(2)))
/*     */                 });
/*     */           } 
/*     */         } 
/*     */       } 
/*     */     } 
/*  96 */     if (fieldFloatingTickCount == null) {
/*  97 */       Class<NetHandlerPlayServer> clz = NetHandlerPlayServer.class;
/*     */       try {
/*  99 */         fieldFloatingTickCount = clz.getDeclaredField("field_147365_f");
/* 100 */       } catch (NoSuchFieldException|SecurityException e) {
/*     */         try {
/* 102 */           fieldFloatingTickCount = clz.getDeclaredField("floatingTickCount");
/* 103 */         } catch (NoSuchFieldException|SecurityException e1) {
/* 104 */           e1.printStackTrace();
/*     */         } 
/*     */       } 
/* 107 */       if (fieldFloatingTickCount != null) {
/* 108 */         fieldFloatingTickCount.setAccessible(true);
/*     */       }
/*     */     } 
/*     */     try {
/* 112 */       fieldFloatingTickCount.set(playerEntity.field_71135_a, Integer.valueOf(0));
/* 113 */     } catch (IllegalArgumentException|IllegalAccessException e) {
/*     */       
/* 115 */       e.printStackTrace();
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void handleClientSide(EntityPlayer clientPlayer) {
/* 122 */     AnimationUtils.isJet.put(this.name, Long.valueOf(System.currentTimeMillis() + this.jetDuraton));
/*     */   }
/*     */ }


/* Location:              C:\Users\DuduM\OneDrive\Área de Trabalho\mw10\modularwarfare-shining-2023.2.4.4f-fix10.jar!\com\modularwarfare\common\network\PacketBackpackJet.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */