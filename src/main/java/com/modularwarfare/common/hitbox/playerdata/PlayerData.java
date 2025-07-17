/*    */ package com.modularwarfare.common.hitbox.playerdata;
/*    */ 
/*    */ import com.modularwarfare.common.hitbox.PlayerSnapshot;
/*    */ import net.minecraft.entity.player.EntityPlayer;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class PlayerData
/*    */ {
/*    */   public String username;
/*    */   public PlayerSnapshot[] snapshots;
/*    */   
/*    */   public PlayerData(String name) {
/* 19 */     this.username = name;
/* 20 */     this.snapshots = new PlayerSnapshot[20];
/*    */   }
/*    */   
/*    */   public void tick(EntityPlayer player) {
/* 24 */     System.arraycopy(this.snapshots, 0, this.snapshots, 1, this.snapshots.length - 2 + 1);
/*    */     
/* 26 */     this.snapshots[0] = new PlayerSnapshot(player);
/*    */   }
/*    */ }


/* Location:              C:\Users\DuduM\OneDrive\Área de Trabalho\mw10\modularwarfare-shining-2023.2.4.4f-fix10.jar!\com\modularwarfare\common\hitbox\playerdata\PlayerData.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */