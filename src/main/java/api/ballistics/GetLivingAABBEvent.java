/*    */ package com.modularwarfare.api.ballistics;
/*    */ 
/*    */ import net.minecraft.entity.EntityLivingBase;
/*    */ import net.minecraft.util.math.AxisAlignedBB;
/*    */ import net.minecraftforge.fml.common.eventhandler.Event;
/*    */ 
/*    */ public class GetLivingAABBEvent extends Event {
/*    */   public final EntityLivingBase entity;
/*    */   
/*    */   public GetLivingAABBEvent(EntityLivingBase entity, AxisAlignedBB box) {
/* 11 */     this.entity = entity;
/* 12 */     this.box = box;
/*    */   }
/*    */   
/*    */   public AxisAlignedBB box;
/*    */ }


/* Location:              C:\Users\DuduM\OneDrive\Área de Trabalho\mw9\modularwarfare-shining-2023.2.4.4f-fix9.jar!\com\modularwarfare\api\ballistics\GetLivingAABBEvent.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */