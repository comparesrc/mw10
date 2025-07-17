/*    */ package com.modularwarfare.common.hitbox.hits;
/*    */ 
/*    */ import com.modularwarfare.raycast.obb.OBBModelBox;
/*    */ import net.minecraft.entity.EntityLivingBase;
/*    */ import net.minecraft.util.math.RayTraceResult;
/*    */ 
/*    */ public class OBBHit
/*    */   extends BulletHit
/*    */ {
/*    */   public EntityLivingBase entity;
/*    */   public OBBModelBox box;
/*    */   
/*    */   public OBBHit(EntityLivingBase entity, OBBModelBox box, RayTraceResult result) {
/* 14 */     super(result);
/* 15 */     this.box = box;
/* 16 */     this.entity = entity;
/*    */   }
/*    */ }


/* Location:              C:\Users\DuduM\OneDrive\Área de Trabalho\mw9\modularwarfare-shining-2023.2.4.4f-fix9.jar!\com\modularwarfare\common\hitbox\hits\OBBHit.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */