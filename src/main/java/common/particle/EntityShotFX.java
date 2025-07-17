/*     */ package com.modularwarfare.common.particle;
/*     */ 
/*     */ import java.util.List;
/*     */ import net.minecraft.client.particle.IParticleFactory;
/*     */ import net.minecraft.client.particle.Particle;
/*     */ import net.minecraft.client.renderer.BufferBuilder;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.util.math.AxisAlignedBB;
/*     */ import net.minecraft.world.World;
/*     */ import net.minecraftforge.fml.relauncher.Side;
/*     */ import net.minecraftforge.fml.relauncher.SideOnly;
/*     */ 
/*     */ public class EntityShotFX
/*     */   extends Particle {
/*     */   private boolean isCollided;
/*     */   
/*     */   public EntityShotFX(World world, double d, double d1, double d2, double d3, double d4, double d5, double mult) {
/*  18 */     super(world, d, d1, d2, d3, d4, d5);
/*  19 */     this.field_70545_g = 1.2F;
/*  20 */     this.field_70552_h = 0.0F;
/*  21 */     this.field_70551_j = 0.0F;
/*  22 */     this.field_70553_i = 0.0F;
/*  23 */     double scale = 1.5D * (1.0D + mult) / 2.0D;
/*  24 */     func_70541_f((float)scale);
/*  25 */     double expandBB = func_187116_l().func_72320_b() * (scale - 1.0D);
/*  26 */     func_187116_l().func_72321_a(expandBB * 2.0D, expandBB * 2.0D, expandBB * 2.0D);
/*  27 */     func_70543_e(1.2F);
/*  28 */     this.field_187130_j += (this.field_187136_p.nextFloat() * 0.15F);
/*  29 */     this.field_187131_k *= (0.4F / (this.field_187136_p.nextFloat() * 0.9F + 0.1F));
/*  30 */     this.field_187129_i *= (0.4F / (this.field_187136_p.nextFloat() * 0.9F + 0.1F));
/*  31 */     this.field_70547_e = (int)(200.0F + 20.0F / (this.field_187136_p.nextFloat() * 0.9F + 0.1F));
/*  32 */     func_70536_a(19 + this.field_187136_p.nextInt(4));
/*  33 */     this.isCollided = false;
/*     */   }
/*     */   
/*     */   public void func_180434_a(BufferBuilder tessellator, Entity e, float f, float f1, float f2, float f3, float f4, float f5) {
/*  37 */     super.func_180434_a(tessellator, e, f, f1, f2, f3, f4, f5);
/*     */   }
/*     */   
/*     */   public int func_189214_a(float f) {
/*  41 */     int i = super.func_189214_a(f);
/*  42 */     float f2 = (this.field_70546_d / this.field_70547_e);
/*  43 */     f2 *= f2;
/*  44 */     f2 *= f2;
/*  45 */     int j = i & 0xFF;
/*  46 */     int k = i >> 16 & 0xFF;
/*  47 */     k += (int)(f2 * 15.0F * 16.0F);
/*  48 */     if (k > 240) {
/*  49 */       k = 240;
/*     */     }
/*  51 */     return j | k << 16;
/*     */   }
/*     */   
/*     */   public float getBrightness(float f) {
/*  55 */     float f2 = super.func_189214_a(f);
/*  56 */     float f3 = (this.field_70546_d / this.field_70547_e);
/*  57 */     f3 *= f3;
/*  58 */     f3 *= f3;
/*  59 */     return f2 * (1.0F - f3) + f3;
/*     */   }
/*     */   
/*     */   public void func_187110_a(double x, double y, double z) {
/*  63 */     double d0 = y;
/*  64 */     double origX = x;
/*  65 */     double origZ = z;
/*  66 */     if (this.field_190017_n) {
/*  67 */       List<AxisAlignedBB> list = this.field_187122_b.func_184144_a((Entity)null, func_187116_l().func_72321_a(x, y, z));
/*  68 */       for (AxisAlignedBB axisalignedbb : list) {
/*  69 */         y = axisalignedbb.func_72323_b(func_187116_l(), y);
/*  70 */         this.isCollided = true;
/*     */       } 
/*  72 */       func_187108_a(func_187116_l().func_72317_d(0.0D, y, 0.0D));
/*  73 */       for (AxisAlignedBB axisalignedbb2 : list) {
/*  74 */         x = axisalignedbb2.func_72316_a(func_187116_l(), x);
/*  75 */         this.isCollided = true;
/*     */       } 
/*  77 */       func_187108_a(func_187116_l().func_72317_d(x, 0.0D, 0.0D));
/*  78 */       for (AxisAlignedBB axisalignedbb3 : list) {
/*  79 */         z = axisalignedbb3.func_72322_c(func_187116_l(), z);
/*  80 */         this.isCollided = true;
/*     */       } 
/*  82 */       func_187108_a(func_187116_l().func_72317_d(0.0D, 0.0D, z));
/*     */     } else {
/*  84 */       func_187108_a(func_187116_l().func_72317_d(x, y, z));
/*     */     } 
/*  86 */     func_187118_j();
/*  87 */     this.field_187132_l = (d0 != y && d0 < 0.0D);
/*  88 */     if (origX != x) {
/*  89 */       this.field_187129_i = 0.0D;
/*     */     }
/*  91 */     if (origZ != z) {
/*  92 */       this.field_187131_k = 0.0D;
/*     */     }
/*     */   }
/*     */   
/*     */   public void func_189213_a() {
/*  97 */     if (this.field_70546_d++ >= this.field_70547_e) {
/*  98 */       func_187112_i();
/*     */     }
/* 100 */     this.field_187123_c = this.field_187126_f;
/* 101 */     this.field_187124_d = this.field_187127_g;
/* 102 */     this.field_187125_e = this.field_187128_h;
/* 103 */     if (!this.isCollided) {
/* 104 */       this.field_187130_j -= 0.04D * this.field_70545_g;
/* 105 */       func_187110_a(this.field_187129_i, this.field_187130_j, this.field_187131_k);
/* 106 */       this.field_187129_i *= 0.9800000190734863D;
/* 107 */       this.field_187130_j *= 0.9800000190734863D;
/* 108 */       this.field_187131_k *= 0.9800000190734863D;
/* 109 */       if (this.field_187132_l) {
/* 110 */         this.field_187129_i *= 0.699999988079071D;
/* 111 */         this.field_187131_k *= 0.699999988079071D;
/* 112 */         this.field_187127_g += 0.1D;
/*     */       } 
/*     */     } else {
/* 115 */       func_187112_i();
/*     */     } 
/*     */   }
/*     */   
/*     */   @SideOnly(Side.CLIENT)
/*     */   public static class Factory implements IParticleFactory {
/*     */     public Particle func_178902_a(int particleID, World worldIn, double xCoordIn, double yCoordIn, double zCoordIn, double xSpeedIn, double ySpeedIn, double zSpeedIn, int... p_178902_15_) {
/* 122 */       return new EntityShotFX(worldIn, xCoordIn, yCoordIn, zCoordIn, xSpeedIn, ySpeedIn, zSpeedIn, zSpeedIn);
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\DuduM\OneDrive\Área de Trabalho\mw9\modularwarfare-shining-2023.2.4.4f-fix9.jar!\com\modularwarfare\common\particle\EntityShotFX.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */