/*     */ package com.modularwarfare.common.handler;
/*     */ 
/*     */ import com.modularwarfare.ModularWarfare;
/*     */ import com.modularwarfare.common.guns.GunType;
/*     */ import com.modularwarfare.common.guns.ItemAmmo;
/*     */ import com.modularwarfare.common.guns.ItemGun;
/*     */ import com.modularwarfare.common.handler.data.DataGunReloadEnhancedTask;
/*     */ import com.modularwarfare.common.network.PacketAimingReponse;
/*     */ import com.modularwarfare.common.network.PacketBase;
/*     */ import com.modularwarfare.utility.event.ForgeEvent;
/*     */ import java.util.UUID;
/*     */ import java.util.concurrent.ConcurrentHashMap;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.nbt.NBTTagCompound;
/*     */ import net.minecraft.util.EnumHand;
/*     */ import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
/*     */ import net.minecraftforge.fml.common.gameevent.TickEvent;
/*     */ import net.minecraftforge.fml.relauncher.Side;
/*     */ 
/*     */ 
/*     */ 
/*     */ public class ServerTickHandler
/*     */   extends ForgeEvent
/*     */ {
/*  25 */   public static ConcurrentHashMap<UUID, Integer> playerReloadCooldown = new ConcurrentHashMap<>();
/*  26 */   public static ConcurrentHashMap<UUID, ItemStack> playerReloadItemStack = new ConcurrentHashMap<>();
/*     */   
/*  28 */   public static ConcurrentHashMap<String, Integer> playerAimShootCooldown = new ConcurrentHashMap<>();
/*  29 */   public static ConcurrentHashMap<String, Boolean> playerAimInstant = new ConcurrentHashMap<>();
/*  30 */   public static ConcurrentHashMap<UUID, DataGunReloadEnhancedTask> reloadEnhancedTask = new ConcurrentHashMap<>();
/*     */   
/*  32 */   int i = 0;
/*     */   
/*  34 */   private long lastBackWeaponsSync = -1L;
/*     */   
/*     */   @SubscribeEvent
/*     */   public void onPlayerTick(TickEvent.PlayerTickEvent event) {
/*  38 */     if (event.side != Side.SERVER || event.phase != TickEvent.Phase.END) {
/*     */       return;
/*     */     }
/*  41 */     ItemStack stack = event.player.func_184586_b(EnumHand.MAIN_HAND);
/*  42 */     if (stack != null && stack.func_77973_b() instanceof ItemGun) {
/*  43 */       if (!stack.func_77942_o()) {
/*  44 */         stack.func_77982_d(new NBTTagCompound());
/*     */       }
/*  46 */       stack.func_77978_p().func_74768_a("maxammo", 0);
/*  47 */       GunType gt = ((ItemGun)stack.func_77973_b()).type;
/*  48 */       if (gt.acceptedBullets != null) {
/*  49 */         stack.func_77978_p().func_74768_a("maxammo", gt.internalAmmoStorage.intValue());
/*     */       }
/*  51 */       else if (stack.func_77978_p() != null) {
/*  52 */         ItemStack ammoStack = new ItemStack(stack.func_77978_p().func_74775_l("ammo"));
/*  53 */         if (ammoStack.func_77973_b() instanceof ItemAmmo) {
/*  54 */           stack.func_77978_p().func_74768_a("maxammo", ((ItemAmmo)ammoStack.func_77973_b()).type.ammoCapacity);
/*     */         }
/*     */       } 
/*     */     } 
/*     */ 
/*     */     
/*  60 */     boolean flag = false;
/*  61 */     if (playerAimShootCooldown.containsKey(event.player.func_70005_c_())) {
/*  62 */       flag = true;
/*     */     }
/*  64 */     if (playerAimInstant.get(event.player.func_70005_c_()) == Boolean.TRUE) {
/*  65 */       flag = true;
/*     */     }
/*  67 */     ModularWarfare.NETWORK.sendToAll((PacketBase)new PacketAimingReponse(event.player.func_70005_c_(), flag));
/*     */   }
/*     */ 
/*     */   
/*     */   @SubscribeEvent
/*     */   public void onServerTick(TickEvent.ServerTickEvent event) {
/*  73 */     long currentTime = System.currentTimeMillis();
/*  74 */     if (this.lastBackWeaponsSync == -1L || currentTime - this.lastBackWeaponsSync > 1000L) {
/*  75 */       this.lastBackWeaponsSync = currentTime;
/*     */     }
/*     */ 
/*     */     
/*  79 */     switch (event.phase) {
/*     */       
/*     */       case START:
/*  82 */         ModularWarfare.NETWORK.handleServerPackets();
/*     */         
/*  84 */         for (String playername : playerAimShootCooldown.keySet()) {
/*  85 */           this.i++;
/*     */           
/*  87 */           int value = ((Integer)playerAimShootCooldown.get(playername)).intValue() - 1;
/*     */           
/*  89 */           if (value <= 0) {
/*  90 */             playerAimShootCooldown.remove(playername); continue;
/*     */           } 
/*  92 */           playerAimShootCooldown.replace(playername, Integer.valueOf(value));
/*     */         } 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/*  98 */         for (UUID uuid : playerReloadCooldown.keySet()) {
/*  99 */           this.i++;
/* 100 */           int value = ((Integer)playerReloadCooldown.get(uuid)).intValue() - 1;
/* 101 */           if (value <= 0) {
/* 102 */             playerReloadCooldown.remove(uuid);
/*     */             
/* 104 */             playerReloadItemStack.get(uuid); continue;
/*     */           } 
/* 106 */           playerReloadCooldown.replace(uuid, Integer.valueOf(value));
/*     */         } 
/*     */         break;
/*     */       
/*     */       case END:
/* 111 */         ModularWarfare.PLAYERHANDLER.serverTick();
/*     */         break;
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\DuduM\OneDrive\Área de Trabalho\mw10\modularwarfare-shining-2023.2.4.4f-fix10.jar!\com\modularwarfare\common\handler\ServerTickHandler.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */