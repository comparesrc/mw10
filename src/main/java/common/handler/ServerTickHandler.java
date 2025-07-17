/*    */ package com.modularwarfare.common.handler;
/*    */ 
/*    */ import com.modularwarfare.ModularWarfare;
/*    */ import com.modularwarfare.common.handler.data.DataGunReloadEnhancedTask;
/*    */ import com.modularwarfare.common.network.PacketAimingReponse;
/*    */ import com.modularwarfare.common.network.PacketBase;
/*    */ import com.modularwarfare.utility.event.ForgeEvent;
/*    */ import java.util.UUID;
/*    */ import java.util.concurrent.ConcurrentHashMap;
/*    */ import net.minecraft.item.ItemStack;
/*    */ import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
/*    */ import net.minecraftforge.fml.common.gameevent.TickEvent;
/*    */ import net.minecraftforge.fml.relauncher.Side;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class ServerTickHandler
/*    */   extends ForgeEvent
/*    */ {
/* 20 */   public static ConcurrentHashMap<UUID, Integer> playerReloadCooldown = new ConcurrentHashMap<>();
/* 21 */   public static ConcurrentHashMap<UUID, ItemStack> playerReloadItemStack = new ConcurrentHashMap<>();
/*    */   
/* 23 */   public static ConcurrentHashMap<String, Integer> playerAimShootCooldown = new ConcurrentHashMap<>();
/* 24 */   public static ConcurrentHashMap<String, Boolean> playerAimInstant = new ConcurrentHashMap<>();
/* 25 */   public static ConcurrentHashMap<UUID, DataGunReloadEnhancedTask> reloadEnhancedTask = new ConcurrentHashMap<>();
/*    */   
/* 27 */   int i = 0;
/*    */   
/* 29 */   private long lastBackWeaponsSync = -1L;
/*    */   
/*    */   @SubscribeEvent
/*    */   public void onPlayerTick(TickEvent.PlayerTickEvent event) {
/* 33 */     if (event.side != Side.SERVER || event.phase != TickEvent.Phase.END) {
/*    */       return;
/*    */     }
/* 36 */     boolean flag = false;
/* 37 */     if (playerAimShootCooldown.containsKey(event.player.func_70005_c_())) {
/* 38 */       flag = true;
/*    */     }
/* 40 */     if (playerAimInstant.get(event.player.func_70005_c_()) == Boolean.TRUE) {
/* 41 */       flag = true;
/*    */     }
/* 43 */     ModularWarfare.NETWORK.sendToAll((PacketBase)new PacketAimingReponse(event.player.func_70005_c_(), flag));
/*    */   }
/*    */ 
/*    */   
/*    */   @SubscribeEvent
/*    */   public void onServerTick(TickEvent.ServerTickEvent event) {
/* 49 */     long currentTime = System.currentTimeMillis();
/* 50 */     if (this.lastBackWeaponsSync == -1L || currentTime - this.lastBackWeaponsSync > 1000L) {
/* 51 */       this.lastBackWeaponsSync = currentTime;
/*    */     }
/*    */ 
/*    */     
/* 55 */     switch (event.phase) {
/*    */       
/*    */       case START:
/* 58 */         ModularWarfare.NETWORK.handleServerPackets();
/*    */         
/* 60 */         for (String playername : playerAimShootCooldown.keySet()) {
/* 61 */           this.i++;
/*    */           
/* 63 */           int value = ((Integer)playerAimShootCooldown.get(playername)).intValue() - 1;
/*    */           
/* 65 */           if (value <= 0) {
/* 66 */             playerAimShootCooldown.remove(playername); continue;
/*    */           } 
/* 68 */           playerAimShootCooldown.replace(playername, Integer.valueOf(value));
/*    */         } 
/*    */ 
/*    */ 
/*    */ 
/*    */         
/* 74 */         for (UUID uuid : playerReloadCooldown.keySet()) {
/* 75 */           this.i++;
/* 76 */           int value = ((Integer)playerReloadCooldown.get(uuid)).intValue() - 1;
/* 77 */           if (value <= 0) {
/* 78 */             playerReloadCooldown.remove(uuid);
/*    */             
/* 80 */             playerReloadItemStack.get(uuid); continue;
/*    */           } 
/* 82 */           playerReloadCooldown.replace(uuid, Integer.valueOf(value));
/*    */         } 
/*    */         break;
/*    */       
/*    */       case END:
/* 87 */         ModularWarfare.PLAYERHANDLER.serverTick();
/*    */         break;
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\DuduM\OneDrive\Área de Trabalho\mw9\modularwarfare-shining-2023.2.4.4f-fix9.jar!\com\modularwarfare\common\handler\ServerTickHandler.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */