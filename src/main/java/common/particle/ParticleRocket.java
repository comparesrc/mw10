/*     */ package com.modularwarfare.common.particle;
/*     */ 
/*     */ import com.modularwarfare.utility.RenderHelperMW;
/*     */ import java.util.Random;
/*     */ import net.minecraft.client.particle.IParticleFactory;
/*     */ import net.minecraft.client.particle.Particle;
/*     */ import net.minecraft.client.renderer.BufferBuilder;
/*     */ import net.minecraft.client.renderer.RenderHelper;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.util.ResourceLocation;
/*     */ import net.minecraft.world.World;
/*     */ import net.minecraftforge.fml.relauncher.Side;
/*     */ import net.minecraftforge.fml.relauncher.SideOnly;
/*     */ import org.lwjgl.opengl.GL11;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class ParticleRocket
/*     */   extends Particle
/*     */ {
/*  26 */   private int index = 1;
/*  27 */   private float alpha = 1.0F;
/*     */   
/*     */   public ParticleRocket(World par1World, double par2, double par4, double par6) {
/*  30 */     super(par1World, par2, par4, par6, 0.0D, 0.0D, 0.0D);
/*  31 */     Random rand = new Random();
/*  32 */     this.field_187129_i *= 0.800000011920929D;
/*  33 */     this.field_187130_j = 0.0D;
/*  34 */     this.field_187131_k *= 0.800000011920929D;
/*  35 */     this.field_187130_j = (this.field_187136_p.nextFloat() * 0.4F + 0.05F);
/*  36 */     this.field_70552_h = this.field_70553_i = this.field_70551_j = 1.0F;
/*  37 */     this.field_70544_f *= this.field_187136_p.nextFloat() * 2.0F + 0.2F;
/*  38 */     this.field_190017_n = false;
/*  39 */     this.field_70547_e = 250;
/*  40 */     this.index = 1 + rand.nextInt(4);
/*  41 */     this.alpha = 0.0F;
/*     */   }
/*     */ 
/*     */   
/*     */   public int func_189214_a(float par1) {
/*  46 */     int i = (this.field_187122_b != null) ? super.func_189214_a(par1) : 0;
/*  47 */     short short1 = 240;
/*  48 */     int j = i >> 16 & 0xFF;
/*  49 */     return short1 | j << 16;
/*     */   }
/*     */   
/*     */   public int func_70537_b() {
/*  53 */     return 3;
/*     */   }
/*     */ 
/*     */   
/*     */   public void func_180434_a(BufferBuilder buffer, Entity entityIn, float par2, float par3, float par4, float par5, float par6, float par7) {
/*  58 */     GL11.glPushMatrix();
/*  59 */     GL11.glPushAttrib(8192);
/*     */     
/*  61 */     RenderHelper.func_74518_a();
/*     */     
/*  63 */     GL11.glDepthMask(false);
/*  64 */     RenderHelperMW.renderPositionedImageInViewWithDepth(new ResourceLocation("modularwarfare", "textures/particles/rocket" + this.index + ".png"), this.field_187126_f, this.field_187127_g + 0.5D, this.field_187128_h, 50.0F, 50.0F, this.alpha);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  72 */     GL11.glDepthMask(true);
/*  73 */     GL11.glPopAttrib();
/*  74 */     GL11.glPopMatrix();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void func_189213_a() {
/*  83 */     if (this.field_187122_b != null) {
/*  84 */       this.field_187123_c = this.field_187126_f;
/*  85 */       this.field_187124_d = this.field_187127_g;
/*  86 */       this.field_187125_e = this.field_187128_h;
/*     */       
/*  88 */       if (this.field_70546_d++ >= this.field_70547_e) {
/*  89 */         func_187112_i();
/*     */       }
/*     */       
/*  92 */       if (this.field_70546_d < 5) {
/*  93 */         this.alpha += 0.1F;
/*     */       } else {
/*  95 */         this.alpha -= 0.004F;
/*     */       } 
/*  97 */       float f = this.field_70546_d / this.field_70547_e;
/*     */       
/*  99 */       this.field_187129_i *= 0.9990000128746033D;
/* 100 */       this.field_187131_k *= 0.9990000128746033D;
/*     */ 
/*     */       
/* 103 */       this.field_187131_k *= 0.9900000190734863D;
/*     */       
/* 105 */       func_187110_a(this.field_187129_i / 20.0D, this.field_187130_j / 20.0D, this.field_187131_k / 20.0D);
/*     */       
/* 107 */       if (this.field_187132_l) {
/* 108 */         this.field_187129_i *= 0.699999988079071D;
/* 109 */         this.field_187131_k *= 0.699999988079071D;
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   @SideOnly(Side.CLIENT)
/*     */   public static class Factory
/*     */     implements IParticleFactory {
/*     */     public Particle func_178902_a(int particleID, World worldIn, double xCoordIn, double yCoordIn, double zCoordIn, double xSpeedIn, double ySpeedIn, double zSpeedIn, int... p_178902_15_) {
/* 118 */       return new ParticleRocket(worldIn, xCoordIn, yCoordIn, zCoordIn);
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\DuduM\OneDrive\Área de Trabalho\mw9\modularwarfare-shining-2023.2.4.4f-fix9.jar!\com\modularwarfare\common\particle\ParticleRocket.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */