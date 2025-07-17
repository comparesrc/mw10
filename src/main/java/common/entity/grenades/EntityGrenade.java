/*     */ package com.modularwarfare.common.entity.grenades;
/*     */ 
/*     */ import com.modularwarfare.ModularWarfare;
/*     */ import com.modularwarfare.common.grenades.GrenadeType;
/*     */ import com.modularwarfare.common.init.ModSounds;
/*     */ import java.util.List;
/*     */ import net.minecraft.block.Block;
/*     */ import net.minecraft.block.material.Material;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.entity.EntityLivingBase;
/*     */ import net.minecraft.entity.MoverType;
/*     */ import net.minecraft.nbt.NBTTagCompound;
/*     */ import net.minecraft.network.datasync.DataParameter;
/*     */ import net.minecraft.network.datasync.DataSerializers;
/*     */ import net.minecraft.network.datasync.EntityDataManager;
/*     */ import net.minecraft.util.DamageSource;
/*     */ import net.minecraft.util.EnumParticleTypes;
/*     */ import net.minecraft.util.SoundCategory;
/*     */ import net.minecraft.util.math.AxisAlignedBB;
/*     */ import net.minecraft.util.math.BlockPos;
/*     */ import net.minecraft.util.math.Vec3d;
/*     */ import net.minecraft.world.Explosion;
/*     */ import net.minecraft.world.World;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class EntityGrenade
/*     */   extends Entity
/*     */ {
/*  32 */   private static final DataParameter GRENADE_NAME = EntityDataManager.func_187226_a(EntityGrenade.class, DataSerializers.field_187194_d);
/*     */   
/*     */   public EntityLivingBase thrower;
/*     */   public GrenadeType grenadeType;
/*     */   public boolean playedSound = false;
/*     */   public float fuse;
/*     */   public boolean exploded = false;
/*     */   
/*     */   public EntityGrenade(World worldIn) {
/*  41 */     super(worldIn);
/*  42 */     this.fuse = 80.0F;
/*  43 */     this.field_70156_m = true;
/*  44 */     this.field_70178_ae = true;
/*  45 */     func_70105_a(0.35F, 0.35F);
/*  46 */     func_184224_h(false);
/*     */   }
/*     */   
/*     */   public EntityGrenade(World world, EntityLivingBase thrower, boolean isRightClick, GrenadeType grenadeType) {
/*  50 */     this(world);
/*     */     
/*  52 */     setGrenadeName(grenadeType.internalName);
/*  53 */     this.grenadeType = grenadeType;
/*  54 */     this.fuse = grenadeType.fuseTime * 20.0F;
/*  55 */     this.exploded = false;
/*  56 */     func_70107_b(thrower.field_70165_t, thrower.field_70163_u + thrower.func_70047_e(), thrower.field_70161_v);
/*  57 */     float strenght = grenadeType.throwStrength;
/*     */     
/*  59 */     if (!isRightClick) {
/*  60 */       strenght *= 0.5F;
/*     */     }
/*     */     
/*  63 */     Vec3d vec = thrower.func_70040_Z();
/*  64 */     double modifier = 1.0D;
/*  65 */     if (thrower.func_70051_ag()) {
/*  66 */       modifier = 1.25D;
/*     */     }
/*     */     
/*  69 */     this.field_70159_w = vec.field_72450_a * 1.5D * modifier * strenght;
/*  70 */     this.field_70181_x = vec.field_72448_b * 1.5D * modifier * strenght;
/*  71 */     this.field_70179_y = vec.field_72449_c * 1.5D * modifier * strenght;
/*     */     
/*  73 */     this.thrower = thrower;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean func_70112_a(double distance) {
/*  78 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public void func_70071_h_() {
/*  83 */     this.field_70169_q = this.field_70165_t;
/*  84 */     this.field_70167_r = this.field_70163_u;
/*  85 */     this.field_70166_s = this.field_70161_v;
/*     */     
/*  87 */     if (!func_189652_ae()) {
/*  88 */       this.field_70181_x -= 0.04D;
/*     */     }
/*     */     
/*  91 */     this.field_70159_w *= 0.98D;
/*  92 */     this.field_70181_x *= 0.98D;
/*  93 */     this.field_70179_y *= 0.98D;
/*     */     
/*  95 */     if (this.field_70122_E) {
/*  96 */       this.field_70159_w *= 0.8D;
/*  97 */       this.field_70179_y *= 0.8D;
/*  98 */       if (!this.playedSound) {
/*  99 */         this.field_70170_p.func_184148_a(null, this.field_70165_t, this.field_70163_u, this.field_70161_v, ModSounds.GRENADE_HIT, SoundCategory.BLOCKS, 0.5F, 1.0F);
/* 100 */         this.playedSound = true;
/*     */       } 
/*     */     } 
/*     */     
/* 104 */     if (Math.abs(this.field_70159_w) < 0.1D && Math.abs(this.field_70179_y) < 0.1D) {
/* 105 */       this.field_70159_w = 0.0D;
/* 106 */       this.field_70179_y = 0.0D;
/*     */     } 
/*     */     
/* 109 */     this.fuse--;
/*     */     
/* 111 */     func_70091_d(MoverType.SELF, this.field_70159_w, this.field_70181_x, this.field_70179_y);
/*     */     
/* 113 */     if (this.fuse <= 0.0F) {
/* 114 */       explode();
/* 115 */       if (this.fuse <= -20.0F) {
/* 116 */         func_70106_y();
/*     */       }
/*     */     } else {
/* 119 */       func_70072_I();
/* 120 */       if (!func_70090_H()) {
/* 121 */         this.field_70170_p.func_175688_a(EnumParticleTypes.SMOKE_NORMAL, this.field_70165_t, this.field_70163_u + 0.2D, this.field_70161_v, 0.0D, 0.0D, 0.0D, new int[0]);
/*     */       } else {
/* 123 */         this.field_70170_p.func_175688_a(EnumParticleTypes.WATER_BUBBLE, this.field_70165_t, this.field_70163_u + 0.2D, this.field_70161_v, 0.0D, 0.1D, 0.0D, new int[0]);
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public String getGrenadeName() {
/* 130 */     return (String)this.field_70180_af.func_187225_a(GRENADE_NAME);
/*     */   }
/*     */   
/*     */   public void setGrenadeName(String grenadeName) {
/* 134 */     this.field_70180_af.func_187227_b(GRENADE_NAME, grenadeName);
/*     */   }
/*     */ 
/*     */   
/*     */   public void explode() {
/* 139 */     if (!this.field_70170_p.field_72995_K && !this.exploded) {
/* 140 */       this.field_70170_p.func_184148_a(null, this.field_70165_t, this.field_70163_u, this.field_70161_v, ModSounds.EXPLOSIONS_CLOSE, SoundCategory.BLOCKS, 2.0F, 1.0F);
/* 141 */       if (this.grenadeType != null) {
/*     */         
/* 143 */         Explosion explosion = new Explosion(this.field_70170_p, this.grenadeType.throwerVulnerable ? null : (Entity)this.thrower, this.field_70165_t, this.field_70163_u, this.field_70161_v, this.grenadeType.explosionPower, false, this.grenadeType.damageWorld);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 149 */         if (this.grenadeType.damageWorld) {
/* 150 */           float f = this.grenadeType.explosionPower * (0.7F + this.field_70170_p.field_73012_v.nextFloat() * 0.6F);
/* 151 */           for (int x = -this.grenadeType.explosionPower; x <= this.grenadeType.explosionPower; x++) {
/* 152 */             for (int y = -this.grenadeType.explosionPower; y <= this.grenadeType.explosionPower; y++) {
/* 153 */               for (int z = -this.grenadeType.explosionPower; z <= this.grenadeType.explosionPower; z++) {
/* 154 */                 BlockPos blockPos = new BlockPos(this.field_70165_t + x, this.field_70163_u + y, this.field_70161_v + z);
/* 155 */                 if (this.field_70170_p.func_180495_p(blockPos).func_185904_a() != Material.field_151579_a && 
/* 156 */                   f - (this.field_70170_p.func_180495_p(blockPos).func_177230_c().getExplosionResistance(this.field_70170_p, blockPos, this, explosion) + 0.3D) * 0.30000001192092896D > 0.0D)
/*     */                 {
/* 158 */                   if (Math.sqrt((x * x + y * y + z * z)) <= this.grenadeType.explosionPower) {
/* 159 */                     Block block = this.field_70170_p.func_180495_p(blockPos).func_177230_c();
/* 160 */                     if (block.func_149659_a(explosion)) {
/* 161 */                       block.func_180653_a(this.field_70170_p, blockPos, this.field_70170_p
/* 162 */                           .func_180495_p(blockPos), 1.0F / this.grenadeType.explosionPower, 0);
/*     */                     }
/*     */                     
/* 165 */                     block.onBlockExploded(this.field_70170_p, blockPos, explosion);
/*     */                   } 
/*     */                 }
/*     */               } 
/*     */             } 
/*     */           } 
/*     */         } 
/*     */ 
/*     */         
/* 174 */         List<Entity> entities = this.field_70170_p.func_175674_a(this, new AxisAlignedBB(this.field_70165_t - (1.0F / this.grenadeType.explosionParamK), this.field_70163_u - (1.0F / this.grenadeType.explosionParamK), this.field_70161_v - (1.0F / this.grenadeType.explosionParamK), this.field_70165_t + (1.0F / this.grenadeType.explosionParamK), this.field_70163_u + (1.0F / this.grenadeType.explosionParamK), this.field_70161_v + (1.0F / this.grenadeType.explosionParamK)), null);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 180 */         for (Entity entity : entities) {
/* 181 */           if (!this.grenadeType.throwerVulnerable && 
/* 182 */             entity == this.thrower) {
/*     */             continue;
/*     */           }
/*     */           
/* 186 */           for (int i = 1; i <= 3; i++) {
/* 187 */             Vec3d entityPos = entity.func_174791_d().func_72441_c(0.0D, (entity.func_70047_e() / 3.0F * i), 0.0D);
/* 188 */             if (this.field_70170_p.func_147447_a(func_174791_d(), entityPos, false, true, false) == null) {
/* 189 */               entity.func_70097_a(DamageSource.func_94539_a(explosion), this.grenadeType.explosionParamA * 
/* 190 */                   (float)Math.max(0.0D, this.grenadeType.explosionParamK * entityPos
/* 191 */                     .func_72438_d(func_174791_d()) + 1.0D));
/*     */               
/*     */               break;
/*     */             } 
/*     */           } 
/*     */         } 
/* 197 */         ModularWarfare.PROXY.spawnExplosionParticle(this.field_70170_p, this.field_70165_t, this.field_70163_u, this.field_70161_v);
/*     */       } 
/*     */     } 
/* 200 */     this.exploded = true;
/* 201 */     func_70106_y();
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean func_70067_L() {
/* 206 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean func_70104_M() {
/* 211 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   protected void func_70088_a() {
/* 216 */     this.field_70180_af.func_187214_a(GRENADE_NAME, "");
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean func_70097_a(DamageSource source, float amount) {
/* 221 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   protected void func_70014_b(NBTTagCompound compound) {
/* 226 */     compound.func_74780_a("posX", this.field_70165_t);
/* 227 */     compound.func_74780_a("posY", this.field_70163_u);
/* 228 */     compound.func_74780_a("posZ", this.field_70161_v);
/* 229 */     compound.func_74780_a("motionX", this.field_70159_w);
/* 230 */     compound.func_74780_a("motionY", this.field_70181_x);
/* 231 */     compound.func_74780_a("motionZ", this.field_70179_y);
/* 232 */     compound.func_74776_a("fuse", this.fuse);
/*     */   }
/*     */ 
/*     */   
/*     */   protected void func_70037_a(NBTTagCompound compound) {
/* 237 */     this.field_70165_t = compound.func_74769_h("posX");
/* 238 */     this.field_70163_u = compound.func_74769_h("posY");
/* 239 */     this.field_70161_v = compound.func_74769_h("posZ");
/* 240 */     this.field_70159_w = compound.func_74769_h("motionX");
/* 241 */     this.field_70181_x = compound.func_74769_h("motionY");
/* 242 */     this.field_70179_y = compound.func_74769_h("motionZ");
/* 243 */     this.fuse = compound.func_74762_e("fuse");
/*     */   }
/*     */ }


/* Location:              C:\Users\DuduM\OneDrive\Área de Trabalho\mw9\modularwarfare-shining-2023.2.4.4f-fix9.jar!\com\modularwarfare\common\entity\grenades\EntityGrenade.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */