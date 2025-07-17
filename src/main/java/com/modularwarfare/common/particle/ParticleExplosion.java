/*     */ package com.modularwarfare.common.particle;
/*     */ 
/*     */ import com.modularwarfare.client.fpp.basic.renderers.RenderParameters;
/*     */ import com.modularwarfare.utility.RenderHelperMW;
/*     */ import java.util.Random;
/*     */ import net.minecraft.client.Minecraft;
/*     */ import net.minecraft.client.entity.EntityPlayerSP;
/*     */ import net.minecraft.client.particle.IParticleFactory;
/*     */ import net.minecraft.client.particle.Particle;
/*     */ import net.minecraft.client.renderer.BufferBuilder;
/*     */ import net.minecraft.client.renderer.RenderHelper;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.init.SoundEvents;
/*     */ import net.minecraft.util.ResourceLocation;
/*     */ import net.minecraft.util.SoundCategory;
/*     */ import net.minecraft.world.World;
/*     */ import net.minecraftforge.fml.relauncher.Side;
/*     */ import net.minecraftforge.fml.relauncher.SideOnly;
/*     */ import org.lwjgl.opengl.GL11;
/*     */ 
/*     */ public class ParticleExplosion
/*     */   extends Particle
/*     */ {
/*  25 */   public float lastSwing = 0.0F;
/*  26 */   private float alpha_rubble = 0.0F;
/*     */   
/*     */   public ParticleExplosion(World par1World, double par2, double par4, double par6) {
/*  29 */     super(par1World, par2, par4, par6, 0.0D, 0.0D, 0.0D);
/*  30 */     Random rand = new Random();
/*  31 */     this.field_187129_i *= 0.800000011920929D;
/*  32 */     this.field_187130_j = 0.0D;
/*  33 */     this.field_187131_k *= 0.800000011920929D;
/*  34 */     this.field_70552_h = this.field_70553_i = this.field_70551_j = 1.0F;
/*  35 */     this.field_70544_f *= this.field_187136_p.nextFloat() * 2.0F + 0.2F;
/*  36 */     this.field_70547_e = 220;
/*     */   }
/*     */ 
/*     */   
/*     */   public int func_189214_a(float par1) {
/*  41 */     int i = (this.field_187122_b != null) ? super.func_189214_a(par1) : 0;
/*  42 */     short short1 = 240;
/*  43 */     int j = i >> 16 & 0xFF;
/*  44 */     return short1 | j << 16;
/*     */   }
/*     */   
/*     */   public int func_70537_b() {
/*  48 */     return 3;
/*     */   }
/*     */ 
/*     */   
/*     */   public void func_180434_a(BufferBuilder buffer, Entity entityIn, float par2, float par3, float par4, float par5, float par6, float par7) {
/*  53 */     GL11.glPushMatrix();
/*  54 */     GL11.glPushAttrib(8192);
/*     */     
/*  56 */     RenderHelper.func_74518_a();
/*     */     
/*  58 */     if (this.lastSwing != RenderParameters.SMOOTH_SWING) {
/*     */       
/*  60 */       this.lastSwing = RenderParameters.SMOOTH_SWING;
/*     */       
/*  62 */       EntityPlayerSP entityPlayerSP = (Minecraft.func_71410_x()).field_71439_g;
/*     */       
/*  64 */       double d = Math.random();
/*  65 */       double d2 = Math.random();
/*     */       
/*  67 */       double dist = entityPlayerSP.func_70011_f(this.field_187126_f, this.field_187127_g, this.field_187128_h);
/*     */ 
/*     */       
/*  70 */       if (dist <= 16.0D) {
/*  71 */         float distFactor = (float)(1.0D / dist);
/*  72 */         ((EntityPlayer)entityPlayerSP).field_70125_A += distFactor * ((d < 0.5D) ? this.alpha_rubble : -this.alpha_rubble) * 5.0F;
/*  73 */         ((EntityPlayer)entityPlayerSP).field_70177_z += distFactor * ((d2 < 0.5D) ? this.alpha_rubble : -this.alpha_rubble) * 5.0F;
/*     */       } 
/*     */       
/*  76 */       if (this.field_70546_d < 6) {
/*  77 */         this.alpha_rubble = (float)(this.alpha_rubble + 0.1D);
/*     */       } else {
/*  79 */         this.alpha_rubble -= 0.1F;
/*     */       } 
/*     */     } 
/*     */     
/*  83 */     if (this.alpha_rubble < 0.0F) {
/*  84 */       this.alpha_rubble = 0.0F;
/*     */     }
/*     */     
/*  87 */     RenderHelperMW.renderSmoke(new ResourceLocation("modularwarfare", "textures/particles/explosion.png"), this.field_187126_f, this.field_187127_g + (this.field_70546_d / 100), this.field_187128_h, 1.0F, 80 + this.field_70546_d * 80, 80 + this.field_70546_d * 50, "0xFFFFFF", this.alpha_rubble);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  95 */     GL11.glPopAttrib();
/*  96 */     GL11.glPopMatrix();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void func_189213_a() {
/* 105 */     if (this.field_187122_b != null) {
/*     */       
/* 107 */       if (this.field_70546_d == 1) {
/* 108 */         this.field_187122_b.func_184134_a(this.field_187126_f, this.field_187127_g, this.field_187128_h, SoundEvents.field_187539_bB, SoundCategory.AMBIENT, 20.0F, 0.9F + this.field_187136_p.nextFloat() * 0.15F, true);
/*     */       }
/*     */       
/* 111 */       this.field_187123_c = this.field_187126_f;
/* 112 */       this.field_187124_d = this.field_187127_g;
/* 113 */       this.field_187125_e = this.field_187128_h;
/*     */       
/* 115 */       if (this.field_70546_d++ >= this.field_70547_e) {
/* 116 */         func_187112_i();
/*     */       }
/*     */       
/* 119 */       this.field_187129_i *= 0.9990000128746033D;
/* 120 */       this.field_187130_j *= 0.9990000128746033D;
/* 121 */       this.field_187131_k *= 0.9990000128746033D;
/*     */       
/* 123 */       func_187110_a(this.field_187129_i / 40.0D, this.field_187130_j, this.field_187131_k / 40.0D);
/*     */       
/* 125 */       if (this.field_187132_l) {
/* 126 */         this.field_187129_i *= 0.699999988079071D;
/* 127 */         this.field_187131_k *= 0.699999988079071D;
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   @SideOnly(Side.CLIENT)
/*     */   public static class Factory
/*     */     implements IParticleFactory {
/*     */     public Particle func_178902_a(int particleID, World worldIn, double xCoordIn, double yCoordIn, double zCoordIn, double xSpeedIn, double ySpeedIn, double zSpeedIn, int... p_178902_15_) {
/* 136 */       return new ParticleExplosion(worldIn, xCoordIn, yCoordIn, zCoordIn);
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\DuduM\OneDrive\Área de Trabalho\mw10\modularwarfare-shining-2023.2.4.4f-fix10.jar!\com\modularwarfare\common\particle\ParticleExplosion.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */