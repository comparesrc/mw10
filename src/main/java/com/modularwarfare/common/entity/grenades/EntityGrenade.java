/*     */ package com.modularwarfare.common.entity.grenades;
/*     */ 
/*     */ import com.modularwarfare.ModularWarfare;
/*     */ import com.modularwarfare.common.grenades.GrenadeType;
/*     */ import com.modularwarfare.common.init.ModSounds;
/*     */ import java.util.List;
/*     */ import mchhui.modularmovements.coremod.ModularMovementsHooks;
/*     */ import net.minecraft.block.Block;
/*     */ import net.minecraft.block.material.Material;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.entity.EntityLivingBase;
/*     */ import net.minecraft.entity.MoverType;
/*     */ import net.minecraft.entity.player.EntityPlayer;
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
/*  34 */   private static final DataParameter GRENADE_NAME = EntityDataManager.func_187226_a(EntityGrenade.class, DataSerializers.field_187194_d);
/*     */   
/*     */   public EntityLivingBase thrower;
/*     */   public GrenadeType grenadeType;
/*     */   public boolean playedSound = false;
/*     */   public float fuse;
/*     */   public boolean exploded = false;
/*     */   
/*     */   public EntityGrenade(World worldIn) {
/*  43 */     super(worldIn);
/*  44 */     this.fuse = 80.0F;
/*  45 */     this.field_70156_m = true;
/*  46 */     this.field_70178_ae = true;
/*  47 */     func_70105_a(0.35F, 0.35F);
/*  48 */     func_184224_h(false);
/*     */   }
/*     */   
/*     */   public EntityGrenade(World world, EntityLivingBase thrower, boolean isRightClick, GrenadeType grenadeType) {
/*  52 */     this(world);
/*     */     
/*  54 */     setGrenadeName(grenadeType.internalName);
/*  55 */     this.grenadeType = grenadeType;
/*  56 */     this.fuse = grenadeType.fuseTime * 20.0F;
/*  57 */     this.exploded = false;
/*  58 */     Vec3d eye = thrower.func_174824_e(1.0F);
/*  59 */     if (ModularWarfare.isLoadedModularMovements && 
/*  60 */       thrower instanceof EntityPlayer) {
/*  61 */       eye = ModularMovementsHooks.onGetPositionEyes((EntityPlayer)thrower, 1.0F);
/*     */     }
/*     */     
/*  64 */     func_70107_b(eye.field_72450_a, eye.field_72448_b, eye.field_72449_c);
/*  65 */     float strenght = grenadeType.throwStrength;
/*     */     
/*  67 */     if (!isRightClick) {
/*  68 */       strenght *= 0.5F;
/*     */     }
/*     */     
/*  71 */     Vec3d vec = thrower.func_70040_Z();
/*  72 */     double modifier = 1.0D;
/*  73 */     if (thrower.func_70051_ag()) {
/*  74 */       modifier = 1.25D;
/*     */     }
/*     */     
/*  77 */     this.field_70159_w = vec.field_72450_a * 1.5D * modifier * strenght;
/*  78 */     this.field_70181_x = vec.field_72448_b * 1.5D * modifier * strenght;
/*  79 */     this.field_70179_y = vec.field_72449_c * 1.5D * modifier * strenght;
/*     */     
/*  81 */     this.thrower = thrower;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean func_70112_a(double distance) {
/*  86 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public void func_70071_h_() {
/*  91 */     this.field_70169_q = this.field_70165_t;
/*  92 */     this.field_70167_r = this.field_70163_u;
/*  93 */     this.field_70166_s = this.field_70161_v;
/*     */     
/*  95 */     if (!func_189652_ae()) {
/*  96 */       this.field_70181_x -= 0.04D;
/*     */     }
/*     */     
/*  99 */     this.field_70159_w *= 0.98D;
/* 100 */     this.field_70181_x *= 0.98D;
/* 101 */     this.field_70179_y *= 0.98D;
/*     */     
/* 103 */     if (this.field_70122_E) {
/* 104 */       this.field_70159_w *= 0.8D;
/* 105 */       this.field_70179_y *= 0.8D;
/* 106 */       if (!this.playedSound) {
/* 107 */         this.field_70170_p.func_184148_a(null, this.field_70165_t, this.field_70163_u, this.field_70161_v, ModSounds.GRENADE_HIT, SoundCategory.BLOCKS, 0.5F, 1.0F);
/* 108 */         this.playedSound = true;
/*     */       } 
/*     */     } 
/*     */     
/* 112 */     if (Math.abs(this.field_70159_w) < 0.1D && Math.abs(this.field_70179_y) < 0.1D) {
/* 113 */       this.field_70159_w = 0.0D;
/* 114 */       this.field_70179_y = 0.0D;
/*     */     } 
/*     */     
/* 117 */     this.fuse--;
/*     */     
/* 119 */     func_70091_d(MoverType.SELF, this.field_70159_w, this.field_70181_x, this.field_70179_y);
/*     */     
/* 121 */     if (this.fuse <= 0.0F) {
/* 122 */       explode();
/* 123 */       if (this.fuse <= -20.0F) {
/* 124 */         func_70106_y();
/*     */       }
/*     */     } else {
/* 127 */       func_70072_I();
/* 128 */       if (!func_70090_H()) {
/* 129 */         this.field_70170_p.func_175688_a(EnumParticleTypes.SMOKE_NORMAL, this.field_70165_t, this.field_70163_u + 0.2D, this.field_70161_v, 0.0D, 0.0D, 0.0D, new int[0]);
/*     */       } else {
/* 131 */         this.field_70170_p.func_175688_a(EnumParticleTypes.WATER_BUBBLE, this.field_70165_t, this.field_70163_u + 0.2D, this.field_70161_v, 0.0D, 0.1D, 0.0D, new int[0]);
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public String getGrenadeName() {
/* 138 */     return (String)this.field_70180_af.func_187225_a(GRENADE_NAME);
/*     */   }
/*     */   
/*     */   public void setGrenadeName(String grenadeName) {
/* 142 */     this.field_70180_af.func_187227_b(GRENADE_NAME, grenadeName);
/*     */   }
/*     */ 
/*     */   
/*     */   public void explode() {
/* 147 */     if (!this.field_70170_p.field_72995_K && !this.exploded) {
/* 148 */       this.field_70170_p.func_184148_a(null, this.field_70165_t, this.field_70163_u, this.field_70161_v, ModSounds.EXPLOSIONS_CLOSE, SoundCategory.BLOCKS, 2.0F, 1.0F);
/* 149 */       if (this.grenadeType != null) {
/*     */         
/* 151 */         Explosion explosion = new Explosion(this.field_70170_p, this.grenadeType.throwerVulnerable ? null : (Entity)this.thrower, this.field_70165_t, this.field_70163_u, this.field_70161_v, this.grenadeType.explosionPower, false, this.grenadeType.damageWorld);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 157 */         if (this.grenadeType.damageWorld) {
/* 158 */           float f = this.grenadeType.explosionPower * (0.7F + this.field_70170_p.field_73012_v.nextFloat() * 0.6F);
/* 159 */           for (int x = -this.grenadeType.explosionPower; x <= this.grenadeType.explosionPower; x++) {
/* 160 */             for (int y = -this.grenadeType.explosionPower; y <= this.grenadeType.explosionPower; y++) {
/* 161 */               for (int z = -this.grenadeType.explosionPower; z <= this.grenadeType.explosionPower; z++) {
/* 162 */                 BlockPos blockPos = new BlockPos(this.field_70165_t + x, this.field_70163_u + y, this.field_70161_v + z);
/* 163 */                 if (this.field_70170_p.func_180495_p(blockPos).func_185904_a() != Material.field_151579_a && 
/* 164 */                   f - (this.field_70170_p.func_180495_p(blockPos).func_177230_c().getExplosionResistance(this.field_70170_p, blockPos, this, explosion) + 0.3D) * 0.30000001192092896D > 0.0D)
/*     */                 {
/* 166 */                   if (Math.sqrt((x * x + y * y + z * z)) <= this.grenadeType.explosionPower) {
/* 167 */                     Block block = this.field_70170_p.func_180495_p(blockPos).func_177230_c();
/* 168 */                     if (block.func_149659_a(explosion)) {
/* 169 */                       block.func_180653_a(this.field_70170_p, blockPos, this.field_70170_p
/* 170 */                           .func_180495_p(blockPos), 1.0F / this.grenadeType.explosionPower, 0);
/*     */                     }
/*     */                     
/* 173 */                     block.onBlockExploded(this.field_70170_p, blockPos, explosion);
/*     */                   } 
/*     */                 }
/*     */               } 
/*     */             } 
/*     */           } 
/*     */         } 
/*     */ 
/*     */         
/* 182 */         List<Entity> entities = this.field_70170_p.func_175674_a(this, new AxisAlignedBB(this.field_70165_t - (1.0F / this.grenadeType.explosionParamK), this.field_70163_u - (1.0F / this.grenadeType.explosionParamK), this.field_70161_v - (1.0F / this.grenadeType.explosionParamK), this.field_70165_t + (1.0F / this.grenadeType.explosionParamK), this.field_70163_u + (1.0F / this.grenadeType.explosionParamK), this.field_70161_v + (1.0F / this.grenadeType.explosionParamK)), null);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 188 */         for (Entity entity : entities) {
/* 189 */           if (!this.grenadeType.throwerVulnerable && 
/* 190 */             entity == this.thrower) {
/*     */             continue;
/*     */           }
/*     */           
/* 194 */           for (int i = 1; i <= 3; i++) {
/* 195 */             Vec3d entityPos = entity.func_174791_d().func_72441_c(0.0D, (entity.func_70047_e() / 3.0F * i), 0.0D);
/* 196 */             if (this.field_70170_p.func_147447_a(func_174791_d(), entityPos, false, true, false) == null) {
/* 197 */               entity.func_70097_a(DamageSource.func_94539_a(explosion), this.grenadeType.explosionParamA * 
/* 198 */                   (float)Math.max(0.0D, this.grenadeType.explosionParamK * entityPos
/* 199 */                     .func_72438_d(func_174791_d()) + 1.0D));
/*     */               
/*     */               break;
/*     */             } 
/*     */           } 
/*     */         } 
/* 205 */         ModularWarfare.PROXY.spawnExplosionParticle(this.field_70170_p, this.field_70165_t, this.field_70163_u, this.field_70161_v);
/*     */       } 
/*     */     } 
/* 208 */     this.exploded = true;
/* 209 */     func_70106_y();
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean func_70067_L() {
/* 214 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean func_70104_M() {
/* 219 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   protected void func_70088_a() {
/* 224 */     this.field_70180_af.func_187214_a(GRENADE_NAME, "");
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean func_70097_a(DamageSource source, float amount) {
/* 229 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   protected void func_70014_b(NBTTagCompound compound) {
/* 234 */     compound.func_74780_a("posX", this.field_70165_t);
/* 235 */     compound.func_74780_a("posY", this.field_70163_u);
/* 236 */     compound.func_74780_a("posZ", this.field_70161_v);
/* 237 */     compound.func_74780_a("motionX", this.field_70159_w);
/* 238 */     compound.func_74780_a("motionY", this.field_70181_x);
/* 239 */     compound.func_74780_a("motionZ", this.field_70179_y);
/* 240 */     compound.func_74776_a("fuse", this.fuse);
/*     */   }
/*     */ 
/*     */   
/*     */   protected void func_70037_a(NBTTagCompound compound) {
/* 245 */     this.field_70165_t = compound.func_74769_h("posX");
/* 246 */     this.field_70163_u = compound.func_74769_h("posY");
/* 247 */     this.field_70161_v = compound.func_74769_h("posZ");
/* 248 */     this.field_70159_w = compound.func_74769_h("motionX");
/* 249 */     this.field_70181_x = compound.func_74769_h("motionY");
/* 250 */     this.field_70179_y = compound.func_74769_h("motionZ");
/* 251 */     this.fuse = compound.func_74762_e("fuse");
/*     */   }
/*     */ }


/* Location:              C:\Users\DuduM\OneDrive\Área de Trabalho\mw10\modularwarfare-shining-2023.2.4.4f-fix10.jar!\com\modularwarfare\common\entity\grenades\EntityGrenade.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */