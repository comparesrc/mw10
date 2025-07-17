/*     */ package com.modularwarfare.common.entity.grenades;
/*     */ 
/*     */ import com.modularwarfare.common.grenades.GrenadeType;
/*     */ import com.modularwarfare.common.init.ModSounds;
/*     */ import net.minecraft.entity.EntityLivingBase;
/*     */ import net.minecraft.entity.MoverType;
/*     */ import net.minecraft.nbt.NBTTagCompound;
/*     */ import net.minecraft.network.datasync.DataParameter;
/*     */ import net.minecraft.network.datasync.DataSerializers;
/*     */ import net.minecraft.network.datasync.EntityDataManager;
/*     */ import net.minecraft.util.DamageSource;
/*     */ import net.minecraft.util.EnumParticleTypes;
/*     */ import net.minecraft.util.SoundCategory;
/*     */ import net.minecraft.world.World;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class EntitySmokeGrenade
/*     */   extends EntityGrenade
/*     */ {
/*  27 */   private static final DataParameter GRENADE_NAME = EntityDataManager.func_187226_a(EntitySmokeGrenade.class, DataSerializers.field_187194_d);
/*     */   
/*  29 */   public float smokeTime = 240.0F;
/*     */   
/*     */   public EntitySmokeGrenade(World worldIn) {
/*  32 */     super(worldIn);
/*     */   }
/*     */   
/*     */   public EntitySmokeGrenade(World world, EntityLivingBase thrower, boolean isRightClick, GrenadeType grenadeType) {
/*  36 */     super(world, thrower, isRightClick, grenadeType);
/*  37 */     this.smokeTime = grenadeType.smokeTime * 20.0F;
/*  38 */     this.field_70156_m = true;
/*  39 */     this.field_70178_ae = true;
/*  40 */     func_70105_a(0.35F, 0.35F);
/*  41 */     func_184224_h(false);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean func_70112_a(double distance) {
/*  46 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public void func_70071_h_() {
/*  51 */     this.field_70169_q = this.field_70165_t;
/*  52 */     this.field_70167_r = this.field_70163_u;
/*  53 */     this.field_70166_s = this.field_70161_v;
/*     */     
/*  55 */     if (!func_189652_ae()) {
/*  56 */       this.field_70181_x -= 0.04D;
/*     */     }
/*     */     
/*  59 */     this.field_70159_w *= 0.98D;
/*  60 */     this.field_70181_x *= 0.98D;
/*  61 */     this.field_70179_y *= 0.98D;
/*     */     
/*  63 */     if (this.field_70122_E) {
/*  64 */       this.field_70159_w *= 0.8D;
/*  65 */       this.field_70179_y *= 0.8D;
/*  66 */       if (!this.playedSound) {
/*  67 */         this.field_70170_p.func_184148_a(null, this.field_70165_t, this.field_70163_u, this.field_70161_v, ModSounds.GRENADE_HIT, SoundCategory.BLOCKS, 0.5F, 1.0F);
/*  68 */         this.playedSound = true;
/*     */       } 
/*     */     } 
/*     */     
/*  72 */     if (Math.abs(this.field_70159_w) < 0.1D && Math.abs(this.field_70179_y) < 0.1D) {
/*  73 */       this.field_70159_w = 0.0D;
/*  74 */       this.field_70179_y = 0.0D;
/*     */     } 
/*     */     
/*  77 */     this.fuse--;
/*     */     
/*  79 */     func_70091_d(MoverType.SELF, this.field_70159_w, this.field_70181_x, this.field_70179_y);
/*     */     
/*  81 */     if (this.fuse <= 0.0F) {
/*  82 */       explode();
/*     */     } else {
/*  84 */       func_70072_I();
/*  85 */       if (!func_70090_H()) {
/*  86 */         this.field_70170_p.func_175688_a(EnumParticleTypes.SMOKE_NORMAL, this.field_70165_t, this.field_70163_u + 0.2D, this.field_70161_v, 0.0D, 0.0D, 0.0D, new int[0]);
/*     */       } else {
/*  88 */         this.field_70170_p.func_175688_a(EnumParticleTypes.WATER_BUBBLE, this.field_70165_t, this.field_70163_u + 0.2D, this.field_70161_v, 0.0D, 0.1D, 0.0D, new int[0]);
/*     */       } 
/*     */     } 
/*     */     
/*  92 */     if (this.exploded) {
/*  93 */       this.smokeTime--;
/*  94 */       if (this.smokeTime <= 0.0F) {
/*  95 */         func_70106_y();
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void explode() {
/* 102 */     if (!this.exploded) {
/* 103 */       this.field_70170_p.func_184148_a(null, this.field_70165_t, this.field_70163_u, this.field_70161_v, ModSounds.GRENADE_SMOKE, SoundCategory.BLOCKS, 2.0F, 1.0F);
/* 104 */       this.exploded = true;
/* 105 */       this.fuse = 0.0F;
/* 106 */       this.smokeTime = 220.0F;
/*     */     } 
/*     */   }
/*     */   
/*     */   public String getGrenadeName() {
/* 111 */     return (String)this.field_70180_af.func_187225_a(GRENADE_NAME);
/*     */   }
/*     */   
/*     */   public void setGrenadeName(String grenadeName) {
/* 115 */     this.field_70180_af.func_187227_b(GRENADE_NAME, grenadeName);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean func_70067_L() {
/* 121 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean func_70104_M() {
/* 126 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   protected void func_70088_a() {
/* 131 */     this.field_70180_af.func_187214_a(GRENADE_NAME, "");
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean func_70097_a(DamageSource source, float amount) {
/* 136 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   protected void func_70014_b(NBTTagCompound compound) {
/* 141 */     compound.func_74780_a("posX", this.field_70165_t);
/* 142 */     compound.func_74780_a("posY", this.field_70163_u);
/* 143 */     compound.func_74780_a("posZ", this.field_70161_v);
/* 144 */     compound.func_74780_a("motionX", this.field_70159_w);
/* 145 */     compound.func_74780_a("motionY", this.field_70181_x);
/* 146 */     compound.func_74780_a("motionZ", this.field_70179_y);
/* 147 */     compound.func_74776_a("fuse", this.fuse);
/* 148 */     compound.func_74776_a("smokeTime", this.smokeTime);
/*     */   }
/*     */ 
/*     */   
/*     */   protected void func_70037_a(NBTTagCompound compound) {
/* 153 */     this.field_70165_t = compound.func_74769_h("posX");
/* 154 */     this.field_70163_u = compound.func_74769_h("posY");
/* 155 */     this.field_70161_v = compound.func_74769_h("posZ");
/* 156 */     this.field_70159_w = compound.func_74769_h("motionX");
/* 157 */     this.field_70181_x = compound.func_74769_h("motionY");
/* 158 */     this.field_70179_y = compound.func_74769_h("motionZ");
/* 159 */     this.fuse = compound.func_74760_g("fuse");
/* 160 */     this.smokeTime = compound.func_74760_g("smokeTime");
/*     */   }
/*     */ }


/* Location:              C:\Users\DuduM\OneDrive\Área de Trabalho\mw10\modularwarfare-shining-2023.2.4.4f-fix10.jar!\com\modularwarfare\common\entity\grenades\EntitySmokeGrenade.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */