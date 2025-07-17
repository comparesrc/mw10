/*    */ package com.modularwarfare.common.entity;
/*    */ 
/*    */ import com.modularwarfare.ModularWarfare;
/*    */ import com.modularwarfare.common.guns.ItemBullet;
/*    */ import com.modularwarfare.common.world.MWFExplosion;
/*    */ import net.minecraft.entity.Entity;
/*    */ import net.minecraft.entity.IProjectile;
/*    */ import net.minecraft.entity.player.EntityPlayer;
/*    */ import net.minecraft.util.math.RayTraceResult;
/*    */ import net.minecraft.util.math.Vec3d;
/*    */ import net.minecraft.world.World;
/*    */ import net.minecraftforge.event.ForgeEventFactory;
/*    */ 
/*    */ public class EntityExplosiveProjectile
/*    */   extends EntityBullet
/*    */   implements IProjectile {
/*    */   public EntityExplosiveProjectile(World world) {
/* 18 */     super(world);
/* 19 */     func_70105_a(0.2F, 0.2F);
/*    */   }
/*    */   
/*    */   public EntityExplosiveProjectile(World par1World, EntityPlayer par2EntityPlayer, float damage, float accuracy, float velocity, String bulletName) {
/* 23 */     super(par1World, par2EntityPlayer, damage, accuracy, velocity, bulletName);
/*    */   }
/*    */   
/*    */   public void func_70071_h_() {
/* 27 */     super.func_70071_h_();
/*    */     
/* 29 */     Vec3d vec3d1 = new Vec3d(this.field_70165_t, this.field_70163_u, this.field_70161_v);
/* 30 */     Vec3d vec3d = new Vec3d(this.field_70165_t + this.field_70159_w, this.field_70163_u + this.field_70181_x, this.field_70161_v + this.field_70179_y);
/* 31 */     RayTraceResult raytraceresult = this.field_70170_p.func_147447_a(vec3d1, vec3d, false, true, false);
/*    */     
/* 33 */     ModularWarfare.PROXY.spawnRocketParticle(this.field_70170_p, this.field_70165_t, this.field_70163_u, this.field_70161_v);
/*    */     
/* 35 */     if (raytraceresult != null && !ForgeEventFactory.onProjectileImpact(this, raytraceresult)) {
/* 36 */       explode();
/* 37 */       func_70106_y();
/*    */     } 
/*    */   }
/*    */   
/*    */   public void explode() {
/* 42 */     if (!this.field_70170_p.field_72995_K && 
/* 43 */       ModularWarfare.bulletTypes.containsKey(getBulletName())) {
/* 44 */       ItemBullet itemBullet = (ItemBullet)ModularWarfare.bulletTypes.get(getBulletName());
/* 45 */       MWFExplosion explosion = new MWFExplosion(this.field_70170_p, (Entity)this.player, this.field_70165_t, this.field_70163_u, this.field_70161_v, itemBullet.type.explosionStrength, false, itemBullet.type.damageWorld);
/* 46 */       explosion.doExplosionA();
/* 47 */       explosion.doExplosionB(true);
/* 48 */       ModularWarfare.PROXY.spawnExplosionParticle(this.field_70170_p, this.field_70165_t, this.field_70163_u, this.field_70161_v);
/*    */     } 
/*    */     
/* 51 */     func_70106_y();
/*    */   }
/*    */ }


/* Location:              C:\Users\DuduM\OneDrive\Área de Trabalho\mw10\modularwarfare-shining-2023.2.4.4f-fix10.jar!\com\modularwarfare\common\entity\EntityExplosiveProjectile.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */