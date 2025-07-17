/*    */ package com.modularwarfare.api;
/*    */ 
/*    */ import net.minecraft.entity.EntityLivingBase;
/*    */ import net.minecraftforge.event.entity.living.LivingEvent;
/*    */ 
/*    */ public class EntityHeadShotEvent
/*    */   extends LivingEvent {
/*    */   public EntityHeadShotEvent(EntityLivingBase entity, EntityLivingBase shooter) {
/*  9 */     super(entity);
/* 10 */     this.shooter = shooter;
/*    */   }
/*    */   public final EntityLivingBase shooter;
/*    */   public EntityLivingBase getVictim() {
/* 14 */     return getEntityLiving();
/*    */   }
/*    */   
/*    */   public EntityLivingBase getShooter() {
/* 18 */     return this.shooter;
/*    */   }
/*    */ }


/* Location:              C:\Users\DuduM\OneDrive\Área de Trabalho\mw9\modularwarfare-shining-2023.2.4.4f-fix9.jar!\com\modularwarfare\api\EntityHeadShotEvent.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */