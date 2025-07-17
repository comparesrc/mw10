/*   */ package com.modularwarfare.api;
/*   */ 
/*   */ import net.minecraft.entity.player.EntityPlayer;
/*   */ import net.minecraftforge.fml.common.eventhandler.Event;
/*   */ 
/*   */ public class WeaponExpShotEvent
/*   */   extends Event {
/*   */   public WeaponExpShotEvent(EntityPlayer player) {
/* 9 */     this.player = player;
/*   */   }
/*   */   
/*   */   public EntityPlayer player;
/*   */ }


/* Location:              C:\Users\DuduM\OneDrive\Área de Trabalho\mw10\modularwarfare-shining-2023.2.4.4f-fix10.jar!\com\modularwarfare\api\WeaponExpShotEvent.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */