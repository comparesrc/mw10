/*    */ package com.modularwarfare.api;
/*    */ 
/*    */ import com.modularwarfare.common.hitbox.PlayerHitbox;
/*    */ import com.modularwarfare.common.vector.Vector3f;
/*    */ import java.util.ArrayList;
/*    */ import net.minecraft.entity.player.EntityPlayer;
/*    */ import net.minecraftforge.fml.common.eventhandler.Event;
/*    */ 
/*    */ public class PlayerSnapshotCreateEvent
/*    */   extends Event {
/*    */   public static class Pre
/*    */     extends PlayerSnapshotCreateEvent {
/*    */     public final EntityPlayer player;
/*    */     public Vector3f pos;
/*    */     
/*    */     public Pre(EntityPlayer player, Vector3f pos) {
/* 17 */       this.player = player;
/* 18 */       this.pos = pos;
/*    */     }
/*    */   }
/*    */   
/*    */   public static class Post extends PlayerSnapshotCreateEvent {
/*    */     public final ArrayList<PlayerHitbox> hitboxes;
/*    */     public final EntityPlayer player;
/*    */     public Vector3f pos;
/*    */     
/*    */     public Post(EntityPlayer player, Vector3f pos, ArrayList<PlayerHitbox> hitboxes) {
/* 28 */       this.player = player;
/* 29 */       this.pos = pos;
/* 30 */       this.hitboxes = hitboxes;
/*    */     }
/*    */   }
/*    */ }


/* Location:              C:\Users\DuduM\OneDrive\Área de Trabalho\mw9\modularwarfare-shining-2023.2.4.4f-fix9.jar!\com\modularwarfare\api\PlayerSnapshotCreateEvent.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */