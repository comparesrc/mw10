/*    */ package com.modularwarfare.common.hitbox.playerdata;
/*    */ 
/*    */ import com.modularwarfare.ModularWarfare;
/*    */ import java.util.HashMap;
/*    */ import java.util.Map;
/*    */ import net.minecraft.client.Minecraft;
/*    */ import net.minecraft.entity.player.EntityPlayer;
/*    */ import net.minecraft.world.WorldServer;
/*    */ import net.minecraftforge.fml.common.FMLCommonHandler;
/*    */ import net.minecraftforge.fml.relauncher.Side;
/*    */ 
/*    */ 
/*    */ public class PlayerDataHandler
/*    */ {
/* 15 */   public static Map<String, PlayerData> serverSideData = new HashMap<>();
/* 16 */   public static Map<String, PlayerData> clientSideData = new HashMap<>();
/*    */   
/*    */   public static PlayerData getPlayerData(EntityPlayer player) {
/* 19 */     if (player == null)
/* 20 */       return null; 
/* 21 */     return getPlayerData(player.func_70005_c_(), player.field_70170_p.field_72995_K ? Side.CLIENT : Side.SERVER);
/*    */   }
/*    */   
/*    */   public static PlayerData getPlayerData(String username) {
/* 25 */     return getPlayerData(username, Side.SERVER);
/*    */   }
/*    */   
/*    */   public static void onPlayerLeave(String username, Side side) {
/* 29 */     if (side.isClient()) {
/* 30 */       clientSideData.remove(username);
/*    */     } else {
/* 32 */       serverSideData.remove(username);
/*    */     } 
/*    */   }
/*    */   
/*    */   public static PlayerData getPlayerData(EntityPlayer player, Side side) {
/* 37 */     if (player == null)
/* 38 */       return null; 
/* 39 */     return getPlayerData(player.func_70005_c_(), side);
/*    */   }
/*    */   
/*    */   public static PlayerData getPlayerData(String username, Side side) {
/* 43 */     if (side.isClient()) {
/* 44 */       if (!clientSideData.containsKey(username)) {
/* 45 */         clientSideData.put(username, new PlayerData(username));
/*    */       }
/* 47 */     } else if (!serverSideData.containsKey(username)) {
/* 48 */       serverSideData.put(username, new PlayerData(username));
/*    */     } 
/* 50 */     return side.isClient() ? clientSideData.get(username) : serverSideData.get(username);
/*    */   }
/*    */   
/*    */   public void clientTick() {
/* 54 */     if ((Minecraft.func_71410_x()).field_71441_e != null)
/*    */     {
/* 56 */       for (Object player : (Minecraft.func_71410_x()).field_71441_e.field_73010_i)
/*    */       {
/* 58 */         getPlayerData((EntityPlayer)player).tick((EntityPlayer)player);
/*    */       }
/*    */     }
/*    */   }
/*    */   
/*    */   public void serverTick() {
/* 64 */     if (FMLCommonHandler.instance().getMinecraftServerInstance() == null) {
/* 65 */       ModularWarfare.LOGGER.warn("Receiving server ticks when server is null");
/*    */       return;
/*    */     } 
/* 68 */     for (WorldServer world : (FMLCommonHandler.instance().getMinecraftServerInstance()).field_71305_c) {
/* 69 */       for (Object player : world.field_73010_i)
/* 70 */         getPlayerData((EntityPlayer)player).tick((EntityPlayer)player); 
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\DuduM\OneDrive\Área de Trabalho\mw9\modularwarfare-shining-2023.2.4.4f-fix9.jar!\com\modularwarfare\common\hitbox\playerdata\PlayerDataHandler.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */