/*     */ package com.modularwarfare.common.entity.grenades;
/*     */ 
/*     */ import com.modularwarfare.ModularWarfare;
/*     */ import com.modularwarfare.common.grenades.GrenadeType;
/*     */ import com.modularwarfare.common.init.ModSounds;
/*     */ import com.modularwarfare.common.network.PacketBase;
/*     */ import com.modularwarfare.common.network.PacketFlashClient;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.entity.EntityLivingBase;
/*     */ import net.minecraft.entity.MoverType;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.entity.player.EntityPlayerMP;
/*     */ import net.minecraft.nbt.NBTTagCompound;
/*     */ import net.minecraft.network.datasync.DataParameter;
/*     */ import net.minecraft.network.datasync.DataSerializers;
/*     */ import net.minecraft.network.datasync.EntityDataManager;
/*     */ import net.minecraft.util.DamageSource;
/*     */ import net.minecraft.util.EnumParticleTypes;
/*     */ import net.minecraft.util.SoundCategory;
/*     */ import net.minecraft.util.math.MathHelper;
/*     */ import net.minecraft.world.World;
/*     */ 
/*     */ public class EntityStunGrenade extends EntityGrenade {
/*  24 */   private static final DataParameter GRENADE_NAME = EntityDataManager.func_187226_a(EntityStunGrenade.class, DataSerializers.field_187194_d);
/*     */   
/*     */   public EntityStunGrenade(World worldIn) {
/*  27 */     super(worldIn);
/*     */   }
/*     */   
/*     */   public EntityStunGrenade(World world, EntityLivingBase thrower, boolean isRightClick, GrenadeType grenadeType) {
/*  31 */     super(world, thrower, isRightClick, grenadeType);
/*  32 */     this.field_70156_m = true;
/*  33 */     this.field_70178_ae = true;
/*  34 */     func_70105_a(0.35F, 0.35F);
/*  35 */     func_184224_h(false);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean func_70112_a(double distance) {
/*  40 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public void func_70071_h_() {
/*  45 */     this.field_70169_q = this.field_70165_t;
/*  46 */     this.field_70167_r = this.field_70163_u;
/*  47 */     this.field_70166_s = this.field_70161_v;
/*     */     
/*  49 */     if (!func_189652_ae()) {
/*  50 */       this.field_70181_x -= 0.04D;
/*     */     }
/*     */     
/*  53 */     this.field_70159_w *= 0.98D;
/*  54 */     this.field_70181_x *= 0.98D;
/*  55 */     this.field_70179_y *= 0.98D;
/*     */     
/*  57 */     if (this.field_70122_E) {
/*  58 */       this.field_70159_w *= 0.8D;
/*  59 */       this.field_70179_y *= 0.8D;
/*  60 */       if (!this.playedSound) {
/*  61 */         this.field_70170_p.func_184148_a(null, this.field_70165_t, this.field_70163_u, this.field_70161_v, ModSounds.GRENADE_HIT, SoundCategory.BLOCKS, 0.5F, 1.0F);
/*  62 */         this.playedSound = true;
/*     */       } 
/*     */     } 
/*     */     
/*  66 */     if (Math.abs(this.field_70159_w) < 0.1D && Math.abs(this.field_70179_y) < 0.1D) {
/*  67 */       this.field_70159_w = 0.0D;
/*  68 */       this.field_70179_y = 0.0D;
/*     */     } 
/*     */     
/*  71 */     this.fuse--;
/*     */     
/*  73 */     func_70091_d(MoverType.SELF, this.field_70159_w, this.field_70181_x, this.field_70179_y);
/*     */     
/*  75 */     if (this.fuse <= 0.0F) {
/*  76 */       if (!this.exploded) {
/*  77 */         explode();
/*     */       }
/*     */     } else {
/*  80 */       func_70072_I();
/*  81 */       if (!func_70090_H()) {
/*  82 */         this.field_70170_p.func_175688_a(EnumParticleTypes.SMOKE_NORMAL, this.field_70165_t, this.field_70163_u + 0.2D, this.field_70161_v, 0.0D, 0.0D, 0.0D, new int[0]);
/*     */       } else {
/*  84 */         this.field_70170_p.func_175688_a(EnumParticleTypes.WATER_BUBBLE, this.field_70165_t, this.field_70163_u + 0.2D, this.field_70161_v, 0.0D, 0.1D, 0.0D, new int[0]);
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void explode() {
/*  91 */     if (!this.exploded) {
/*  92 */       this.field_70170_p.func_184148_a(null, this.field_70165_t, this.field_70163_u, this.field_70161_v, ModSounds.GRENADE_STUN, SoundCategory.BLOCKS, 2.0F, 1.0F);
/*  93 */       this.exploded = true;
/*  94 */       func_70106_y();
/*  95 */       if (!this.field_70170_p.field_72995_K) {
/*  96 */         for (EntityPlayer player : this.field_70170_p.field_73010_i) {
/*  97 */           if (isInFieldOfVision(this, (EntityLivingBase)player)) {
/*  98 */             if (player.func_70032_d(this) < 10.0F) {
/*  99 */               ModularWarfare.NETWORK.sendTo((PacketBase)new PacketFlashClient(255), (EntityPlayerMP)player); continue;
/* 100 */             }  if (player.func_70032_d(this) < 15.0F) {
/* 101 */               ModularWarfare.NETWORK.sendTo((PacketBase)new PacketFlashClient(180), (EntityPlayerMP)player); continue;
/* 102 */             }  if (player.func_70032_d(this) < 20.0F) {
/* 103 */               ModularWarfare.NETWORK.sendTo((PacketBase)new PacketFlashClient(100), (EntityPlayerMP)player); continue;
/* 104 */             }  if (player.func_70032_d(this) < 35.0F) {
/* 105 */               ModularWarfare.NETWORK.sendTo((PacketBase)new PacketFlashClient(60), (EntityPlayerMP)player);
/*     */             }
/*     */           } 
/*     */         } 
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   boolean isInFieldOfVision(Entity e1, EntityLivingBase e2) {
/* 116 */     float rotationYawPrime = e2.field_70177_z;
/* 117 */     float rotationPitchPrime = e2.field_70125_A;
/*     */     
/* 119 */     faceEntity(e2, e1, 360.0F, 360.0F);
/*     */     
/* 121 */     float f = e2.field_70177_z;
/* 122 */     float f2 = e2.field_70125_A;
/*     */     
/* 124 */     e2.field_70177_z = rotationYawPrime;
/* 125 */     e2.field_70125_A = rotationPitchPrime;
/*     */     
/* 127 */     rotationYawPrime = f;
/* 128 */     rotationPitchPrime = f2;
/*     */     
/* 130 */     float X = 60.0F;
/* 131 */     float Y = 60.0F;
/*     */     
/* 133 */     float yawFOVMin = e2.field_70177_z - X;
/* 134 */     float yawFOVMax = e2.field_70177_z + X;
/* 135 */     float pitchFOVMin = e2.field_70125_A - Y;
/* 136 */     float pitchFOVMax = e2.field_70125_A + Y;
/*     */     
/* 138 */     boolean flag1 = (rotationYawPrime < yawFOVMax && rotationYawPrime > yawFOVMin);
/*     */     
/* 140 */     boolean flag2 = ((pitchFOVMin <= -180.0F && (rotationPitchPrime >= pitchFOVMin + 360.0F || rotationPitchPrime <= pitchFOVMax)) || (pitchFOVMax > 180.0F && (rotationPitchPrime <= pitchFOVMax - 360.0F || rotationPitchPrime >= pitchFOVMin)) || (pitchFOVMax < 180.0F && pitchFOVMin >= -180.0F && rotationPitchPrime <= pitchFOVMax && rotationPitchPrime >= pitchFOVMin));
/* 141 */     return (flag1 && flag2 && e2.func_70685_l(e1));
/*     */   }
/*     */   
/*     */   public void faceEntity(EntityLivingBase par1, Entity par1Entity, float par2, float par3) {
/* 145 */     double d2, d0 = par1Entity.field_70165_t - par1.field_70165_t;
/* 146 */     double d1 = par1Entity.field_70161_v - par1.field_70161_v;
/*     */ 
/*     */     
/* 149 */     if (par1Entity instanceof EntityLivingBase) {
/* 150 */       EntityLivingBase entitylivingbase = (EntityLivingBase)par1Entity;
/* 151 */       d2 = entitylivingbase.field_70163_u + entitylivingbase.func_70047_e() - par1.field_70163_u + par1.func_70047_e();
/*     */     } else {
/* 153 */       d2 = ((par1Entity.func_174813_aQ()).field_72338_b + (par1Entity.func_174813_aQ()).field_72337_e) / 2.0D - par1.field_70163_u + par1.func_70047_e();
/*     */     } 
/*     */     
/* 156 */     double d3 = MathHelper.func_76133_a(d0 * d0 + d1 * d1);
/* 157 */     float f2 = (float)(Math.atan2(d1, d0) * 180.0D / Math.PI) - 90.0F;
/* 158 */     float f3 = (float)-(Math.atan2(d2, d3) * 180.0D / Math.PI);
/* 159 */     par1.field_70125_A = updateRotation(par1.field_70125_A, f3, par3);
/* 160 */     par1.field_70177_z = updateRotation(par1.field_70177_z, f2, par2);
/*     */   }
/*     */   
/*     */   private float updateRotation(float par1, float par2, float par3) {
/* 164 */     float f3 = MathHelper.func_76142_g(par2 - par1);
/* 165 */     if (f3 > par3) {
/* 166 */       f3 = par3;
/*     */     }
/* 168 */     if (f3 < -par3) {
/* 169 */       f3 = -par3;
/*     */     }
/* 171 */     return par1 + f3;
/*     */   }
/*     */ 
/*     */   
/*     */   public String getGrenadeName() {
/* 176 */     return (String)this.field_70180_af.func_187225_a(GRENADE_NAME);
/*     */   }
/*     */   
/*     */   public void setGrenadeName(String grenadeName) {
/* 180 */     this.field_70180_af.func_187227_b(GRENADE_NAME, grenadeName);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean func_70067_L() {
/* 186 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean func_70104_M() {
/* 191 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   protected void func_70088_a() {
/* 196 */     this.field_70180_af.func_187214_a(GRENADE_NAME, "");
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean func_70097_a(DamageSource source, float amount) {
/* 201 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   protected void func_70014_b(NBTTagCompound compound) {
/* 206 */     compound.func_74780_a("posX", this.field_70165_t);
/* 207 */     compound.func_74780_a("posY", this.field_70163_u);
/* 208 */     compound.func_74780_a("posZ", this.field_70161_v);
/* 209 */     compound.func_74780_a("motionX", this.field_70159_w);
/* 210 */     compound.func_74780_a("motionY", this.field_70181_x);
/* 211 */     compound.func_74780_a("motionZ", this.field_70179_y);
/* 212 */     compound.func_74776_a("fuse", this.fuse);
/*     */   }
/*     */ 
/*     */   
/*     */   protected void func_70037_a(NBTTagCompound compound) {
/* 217 */     this.field_70165_t = compound.func_74769_h("posX");
/* 218 */     this.field_70163_u = compound.func_74769_h("posY");
/* 219 */     this.field_70161_v = compound.func_74769_h("posZ");
/* 220 */     this.field_70159_w = compound.func_74769_h("motionX");
/* 221 */     this.field_70181_x = compound.func_74769_h("motionY");
/* 222 */     this.field_70179_y = compound.func_74769_h("motionZ");
/* 223 */     this.fuse = compound.func_74760_g("fuse");
/*     */   }
/*     */ }


/* Location:              C:\Users\DuduM\OneDrive\Área de Trabalho\mw9\modularwarfare-shining-2023.2.4.4f-fix9.jar!\com\modularwarfare\common\entity\grenades\EntityStunGrenade.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */