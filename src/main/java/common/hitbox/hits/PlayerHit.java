/*    */ package com.modularwarfare.common.hitbox.hits;
/*    */ 
/*    */ import com.modularwarfare.common.hitbox.PlayerHitbox;
/*    */ import net.minecraft.entity.player.EntityPlayer;
/*    */ import net.minecraft.util.math.RayTraceResult;
/*    */ 
/*    */ public class PlayerHit
/*    */   extends BulletHit {
/*    */   public PlayerHitbox hitbox;
/*    */   
/*    */   public PlayerHit(PlayerHitbox box, RayTraceResult result) {
/* 12 */     super(result);
/* 13 */     this.hitbox = box;
/*    */   }
/*    */   
/*    */   public EntityPlayer getEntity() {
/* 17 */     return this.hitbox.player;
/*    */   }
/*    */ }


/* Location:              C:\Users\DuduM\OneDrive\Área de Trabalho\mw9\modularwarfare-shining-2023.2.4.4f-fix9.jar!\com\modularwarfare\common\hitbox\hits\PlayerHit.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */