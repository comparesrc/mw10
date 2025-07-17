/*     */ package com.modularwarfare.common.entity;
/*     */ 
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.util.EnumParticleTypes;
/*     */ import net.minecraft.util.math.MathHelper;
/*     */ import net.minecraft.world.World;
/*     */ import net.minecraftforge.fml.relauncher.Side;
/*     */ import net.minecraftforge.fml.relauncher.SideOnly;
/*     */ 
/*     */ 
/*     */ public class EntityBulletClient
/*     */   extends EntityBullet
/*     */ {
/*  14 */   public float renderLifeTime = 0.0F;
/*     */   
/*     */   public EntityBulletClient(World par1World, EntityPlayer p, float damage, float accuracy, float velocity, String bulletName) {
/*  17 */     super(par1World, p, damage, accuracy, velocity, bulletName);
/*     */   }
/*     */   
/*     */   public EntityBulletClient(World par1World) {
/*  21 */     super(par1World);
/*     */   }
/*     */   
/*     */   @SideOnly(Side.CLIENT)
/*     */   public boolean func_70112_a(double par1) {
/*  26 */     double d1 = func_174813_aQ().func_72320_b() * 4.0D;
/*  27 */     d1 *= 64.0D;
/*  28 */     return (par1 < d1 * d1);
/*     */   }
/*     */   
/*     */   @SideOnly(Side.CLIENT)
/*     */   public void func_70016_h(double par1, double par3, double par5) {
/*  33 */     this.field_70159_w = par1;
/*  34 */     this.field_70181_x = par3;
/*  35 */     this.field_70179_y = par5;
/*     */     
/*  37 */     if (this.field_70127_C == 0.0F && this.field_70126_B == 0.0F) {
/*  38 */       float f = MathHelper.func_76133_a(par1 * par1 + par5 * par5);
/*  39 */       this.field_70126_B = this.field_70177_z = (float)(Math.atan2(par1, par5) * 180.0D / Math.PI);
/*  40 */       this.field_70127_C = this.field_70125_A = (float)(Math.atan2(par3, f) * 180.0D / Math.PI);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void func_70071_h_() {
/*  46 */     super.func_70071_h_();
/*     */     
/*  48 */     if (this.ticks > 1000) {
/*  49 */       func_70106_y();
/*     */     }
/*     */     
/*  52 */     if (this.field_70127_C == 0.0F && this.field_70126_B == 0.0F) {
/*  53 */       float var1 = MathHelper.func_76133_a(this.field_70159_w * this.field_70159_w + this.field_70179_y * this.field_70179_y);
/*  54 */       this.field_70126_B = this.field_70177_z = (float)(Math.atan2(this.field_70159_w, this.field_70179_y) * 180.0D / Math.PI);
/*  55 */       this.field_70127_C = this.field_70125_A = (float)(Math.atan2(this.field_70181_x, var1) * 180.0D / Math.PI);
/*     */     } 
/*     */     
/*  58 */     if (this.field_70163_u <= 0.0D || this.field_70163_u >= 256.0D) {
/*  59 */       func_70106_y();
/*     */     }
/*     */     
/*  62 */     if (this.field_70249_b > 0) {
/*  63 */       this.field_70249_b--;
/*     */     }
/*     */     
/*  66 */     if (this.field_70122_E) {
/*  67 */       this.field_70122_E = false;
/*  68 */       this.field_70159_w *= (this.field_70146_Z.nextFloat() * 0.2F);
/*  69 */       this.field_70181_x *= (this.field_70146_Z.nextFloat() * 0.2F);
/*  70 */       this.field_70179_y *= (this.field_70146_Z.nextFloat() * 0.2F);
/*  71 */       this.ticks = 0;
/*     */     } else {
/*  73 */       this.field_70257_an++;
/*     */     } 
/*     */     
/*  76 */     this.field_70165_t += this.field_70159_w;
/*  77 */     this.field_70163_u += this.field_70181_x;
/*  78 */     this.field_70161_v += this.field_70179_y;
/*  79 */     float f1 = MathHelper.func_76133_a(this.field_70159_w * this.field_70159_w + this.field_70179_y * this.field_70179_y);
/*  80 */     this.field_70177_z = (float)(Math.atan2(this.field_70159_w, this.field_70179_y) * 180.0D / Math.PI);
/*     */     
/*  82 */     for (this.field_70125_A = (float)(Math.atan2(this.field_70181_x, f1) * 180.0D / Math.PI); this.field_70125_A - this.field_70127_C < -180.0F; this.field_70127_C -= 360.0F);
/*     */ 
/*     */ 
/*     */     
/*  86 */     while (this.field_70125_A - this.field_70127_C >= 180.0F) {
/*  87 */       this.field_70127_C += 360.0F;
/*     */     }
/*     */     
/*  90 */     while (this.field_70177_z - this.field_70126_B < -180.0F) {
/*  91 */       this.field_70126_B -= 360.0F;
/*     */     }
/*     */     
/*  94 */     while (this.field_70177_z - this.field_70126_B >= 180.0F) {
/*  95 */       this.field_70126_B += 360.0F;
/*     */     }
/*     */     
/*  98 */     this.field_70125_A = this.field_70127_C + (this.field_70125_A - this.field_70127_C) * 0.2F;
/*  99 */     this.field_70177_z = this.field_70126_B + (this.field_70177_z - this.field_70126_B) * 0.2F;
/* 100 */     float f2 = 0.99F;
/*     */     
/* 102 */     if (func_70090_H()) {
/* 103 */       for (int k = 0; k < 4; k++) {
/* 104 */         float f4 = 0.25F;
/* 105 */         this.field_70170_p.func_175688_a(EnumParticleTypes.WATER_BUBBLE, this.field_70165_t - this.field_70159_w * f4, this.field_70163_u - this.field_70181_x * f4, this.field_70161_v - this.field_70179_y * f4, this.field_70159_w, this.field_70181_x, this.field_70179_y, new int[0]);
/*     */       } 
/*     */       
/* 108 */       f2 = 0.8F;
/*     */     } 
/*     */     
/* 111 */     this.field_70159_w *= f2;
/* 112 */     this.field_70181_x *= f2;
/* 113 */     this.field_70179_y *= f2;
/* 114 */     func_70107_b(this.field_70165_t, this.field_70163_u, this.field_70161_v);
/*     */   }
/*     */   
/*     */   @SideOnly(Side.CLIENT)
/*     */   public float getShadowSize() {
/* 119 */     return 0.0F;
/*     */   }
/*     */ }


/* Location:              C:\Users\DuduM\OneDrive\Área de Trabalho\mw9\modularwarfare-shining-2023.2.4.4f-fix9.jar!\com\modularwarfare\common\entity\EntityBulletClient.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */