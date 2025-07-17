/*     */ package com.modularwarfare.common.world;
/*     */ 
/*     */ import com.google.common.collect.Sets;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.Random;
/*     */ import java.util.Set;
/*     */ import javax.annotation.Nullable;
/*     */ import net.minecraft.block.Block;
/*     */ import net.minecraft.block.material.Material;
/*     */ import net.minecraft.block.state.IBlockState;
/*     */ import net.minecraft.enchantment.EnchantmentProtection;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.entity.EntityLivingBase;
/*     */ import net.minecraft.entity.item.EntityTNTPrimed;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.init.Blocks;
/*     */ import net.minecraft.util.DamageSource;
/*     */ import net.minecraft.util.EnumParticleTypes;
/*     */ import net.minecraft.util.math.AxisAlignedBB;
/*     */ import net.minecraft.util.math.BlockPos;
/*     */ import net.minecraft.util.math.MathHelper;
/*     */ import net.minecraft.util.math.Vec3d;
/*     */ import net.minecraft.world.Explosion;
/*     */ import net.minecraft.world.World;
/*     */ import net.minecraftforge.event.ForgeEventFactory;
/*     */ import net.minecraftforge.fml.relauncher.Side;
/*     */ import net.minecraftforge.fml.relauncher.SideOnly;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class MWFExplosion
/*     */ {
/*     */   private final boolean causesFire;
/*     */   private final boolean damagesTerrain;
/*     */   private final Random random;
/*     */   private final World world;
/*     */   private final double x;
/*     */   private final double y;
/*     */   private final double z;
/*     */   private final Entity exploder;
/*     */   private final float size;
/*     */   private final List<BlockPos> affectedBlockPositions;
/*     */   private final Map<EntityPlayer, Vec3d> playerKnockbackMap;
/*     */   private final Vec3d position;
/*     */   private final Explosion explosion;
/*     */   
/*     */   @SideOnly(Side.CLIENT)
/*     */   public MWFExplosion(World worldIn, Entity entityIn, double x, double y, double z, float size, List<BlockPos> affectedPositions) {
/*  52 */     this(worldIn, entityIn, x, y, z, size, false, true, affectedPositions);
/*     */   }
/*     */ 
/*     */   
/*     */   @SideOnly(Side.CLIENT)
/*     */   public MWFExplosion(World worldIn, Entity entityIn, double x, double y, double z, float size, boolean causesFire, boolean damagesTerrain, List<BlockPos> affectedPositions) {
/*  58 */     this(worldIn, entityIn, x, y, z, size, causesFire, damagesTerrain);
/*  59 */     this.affectedBlockPositions.addAll(affectedPositions);
/*  60 */     this.explosion.func_180343_e().addAll(affectedPositions);
/*     */   }
/*     */ 
/*     */   
/*     */   public MWFExplosion(World worldIn, Entity entityIn, double x, double y, double z, float size, boolean flaming, boolean damagesTerrain) {
/*  65 */     this.random = new Random();
/*  66 */     this.world = worldIn;
/*  67 */     this.exploder = entityIn;
/*  68 */     this.size = size;
/*  69 */     this.x = x;
/*  70 */     this.y = y;
/*  71 */     this.z = z;
/*  72 */     this.causesFire = flaming;
/*  73 */     this.damagesTerrain = damagesTerrain;
/*  74 */     this.position = new Vec3d(this.x, this.y, this.z);
/*  75 */     this.explosion = new Explosion(worldIn, entityIn, x, y, z, size, flaming, damagesTerrain);
/*  76 */     this.affectedBlockPositions = this.explosion.func_180343_e();
/*  77 */     this.playerKnockbackMap = this.explosion.func_77277_b();
/*     */   }
/*     */ 
/*     */   
/*     */   public void doExplosionA() {
/*  82 */     Set<BlockPos> set = Sets.newHashSet();
/*  83 */     int i = 16;
/*     */     
/*  85 */     for (int j = 0; j < 16; j++) {
/*     */       
/*  87 */       for (int k = 0; k < 16; k++) {
/*     */         
/*  89 */         for (int l = 0; l < 16; l++) {
/*     */           
/*  91 */           if (j == 0 || j == 15 || k == 0 || k == 15 || l == 0 || l == 15) {
/*     */             
/*  93 */             double d0 = (j / 15.0F * 2.0F - 1.0F);
/*  94 */             double d1 = (k / 15.0F * 2.0F - 1.0F);
/*  95 */             double d2 = (l / 15.0F * 2.0F - 1.0F);
/*  96 */             double d3 = Math.sqrt(d0 * d0 + d1 * d1 + d2 * d2);
/*  97 */             d0 /= d3;
/*  98 */             d1 /= d3;
/*  99 */             d2 /= d3;
/* 100 */             float f = this.size * (0.7F + this.world.field_73012_v.nextFloat() * 0.6F);
/* 101 */             double d4 = this.x;
/* 102 */             double d6 = this.y;
/* 103 */             double d8 = this.z;
/*     */             
/* 105 */             for (float f1 = 0.3F; f > 0.0F; f -= 0.22500001F) {
/*     */               
/* 107 */               BlockPos blockpos = new BlockPos(d4, d6, d8);
/* 108 */               IBlockState iblockstate = this.world.func_180495_p(blockpos);
/*     */               
/* 110 */               if (iblockstate.func_185904_a() != Material.field_151579_a) {
/*     */                 
/* 112 */                 float f2 = (this.exploder != null) ? this.exploder.func_180428_a(this.explosion, this.world, blockpos, iblockstate) : iblockstate.func_177230_c().getExplosionResistance(this.world, blockpos, (Entity)null, this.explosion);
/* 113 */                 f -= (f2 + 0.3F) * 0.3F;
/*     */               } 
/*     */               
/* 116 */               if (f > 0.0F && (this.exploder == null || this.exploder.func_174816_a(this.explosion, this.world, blockpos, iblockstate, f)))
/*     */               {
/* 118 */                 set.add(blockpos);
/*     */               }
/*     */               
/* 121 */               d4 += d0 * 0.30000001192092896D;
/* 122 */               d6 += d1 * 0.30000001192092896D;
/* 123 */               d8 += d2 * 0.30000001192092896D;
/*     */             } 
/*     */           } 
/*     */         } 
/*     */       } 
/*     */     } 
/*     */     
/* 130 */     this.affectedBlockPositions.addAll(set);
/* 131 */     float f3 = this.size * 2.0F;
/* 132 */     int k1 = MathHelper.func_76128_c(this.x - f3 - 1.0D);
/* 133 */     int l1 = MathHelper.func_76128_c(this.x + f3 + 1.0D);
/* 134 */     int i2 = MathHelper.func_76128_c(this.y - f3 - 1.0D);
/* 135 */     int i1 = MathHelper.func_76128_c(this.y + f3 + 1.0D);
/* 136 */     int j2 = MathHelper.func_76128_c(this.z - f3 - 1.0D);
/* 137 */     int j1 = MathHelper.func_76128_c(this.z + f3 + 1.0D);
/* 138 */     List<Entity> list = this.world.func_72839_b(this.exploder, new AxisAlignedBB(k1, i2, j2, l1, i1, j1));
/* 139 */     ForgeEventFactory.onExplosionDetonate(this.world, this.explosion, list, f3);
/* 140 */     Vec3d vec3d = new Vec3d(this.x, this.y, this.z);
/*     */     
/* 142 */     for (int k2 = 0; k2 < list.size(); k2++) {
/*     */       
/* 144 */       Entity entity = list.get(k2);
/*     */       
/* 146 */       if (!entity.func_180427_aV()) {
/*     */         
/* 148 */         double d12 = entity.func_70011_f(this.x, this.y, this.z) / f3;
/*     */         
/* 150 */         if (d12 <= 1.0D) {
/*     */           
/* 152 */           double d5 = entity.field_70165_t - this.x;
/* 153 */           double d7 = entity.field_70163_u + entity.func_70047_e() - this.y;
/* 154 */           double d9 = entity.field_70161_v - this.z;
/* 155 */           double d13 = MathHelper.func_76133_a(d5 * d5 + d7 * d7 + d9 * d9);
/*     */           
/* 157 */           if (d13 != 0.0D) {
/*     */             
/* 159 */             d5 /= d13;
/* 160 */             d7 /= d13;
/* 161 */             d9 /= d13;
/* 162 */             double d14 = this.world.func_72842_a(vec3d, entity.func_174813_aQ());
/* 163 */             double d10 = (1.0D - d12) * d14;
/* 164 */             entity.func_70097_a(DamageSource.func_94539_a(this.explosion), (int)((d10 * d10 + d10) / 2.0D * 7.0D * f3 + 1.0D));
/* 165 */             double d11 = d10;
/*     */             
/* 167 */             if (entity instanceof EntityLivingBase)
/*     */             {
/* 169 */               d11 = EnchantmentProtection.func_92092_a((EntityLivingBase)entity, d10);
/*     */             }
/*     */             
/* 172 */             entity.field_70159_w += d5 * d11;
/* 173 */             entity.field_70181_x += d7 * d11;
/* 174 */             entity.field_70179_y += d9 * d11;
/*     */             
/* 176 */             if (entity instanceof EntityPlayer) {
/*     */               
/* 178 */               EntityPlayer entityplayer = (EntityPlayer)entity;
/*     */               
/* 180 */               if (!entityplayer.func_175149_v() && (!entityplayer.func_184812_l_() || !entityplayer.field_71075_bZ.field_75100_b))
/*     */               {
/* 182 */                 this.playerKnockbackMap.put(entityplayer, new Vec3d(d5 * d10, d7 * d10, d9 * d10));
/*     */               }
/*     */             } 
/*     */           } 
/*     */         } 
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void doExplosionB(boolean spawnParticles) {
/* 195 */     if (this.size >= 2.0F && this.damagesTerrain) {
/*     */       
/* 197 */       this.world.func_175688_a(EnumParticleTypes.EXPLOSION_HUGE, this.x, this.y, this.z, 1.0D, 0.0D, 0.0D, new int[0]);
/*     */     }
/*     */     else {
/*     */       
/* 201 */       this.world.func_175688_a(EnumParticleTypes.EXPLOSION_LARGE, this.x, this.y, this.z, 1.0D, 0.0D, 0.0D, new int[0]);
/*     */     } 
/*     */     
/* 204 */     if (this.damagesTerrain)
/*     */     {
/* 206 */       for (BlockPos blockpos : this.affectedBlockPositions) {
/*     */         
/* 208 */         IBlockState iblockstate = this.world.func_180495_p(blockpos);
/* 209 */         Block block = iblockstate.func_177230_c();
/*     */         
/* 211 */         if (spawnParticles) {
/*     */           
/* 213 */           double d0 = (blockpos.func_177958_n() + this.world.field_73012_v.nextFloat());
/* 214 */           double d1 = (blockpos.func_177956_o() + this.world.field_73012_v.nextFloat());
/* 215 */           double d2 = (blockpos.func_177952_p() + this.world.field_73012_v.nextFloat());
/* 216 */           double d3 = d0 - this.x;
/* 217 */           double d4 = d1 - this.y;
/* 218 */           double d5 = d2 - this.z;
/* 219 */           double d6 = MathHelper.func_76133_a(d3 * d3 + d4 * d4 + d5 * d5);
/* 220 */           d3 /= d6;
/* 221 */           d4 /= d6;
/* 222 */           d5 /= d6;
/* 223 */           double d7 = 0.5D / (d6 / this.size + 0.1D);
/* 224 */           d7 *= (this.world.field_73012_v.nextFloat() * this.world.field_73012_v.nextFloat() + 0.3F);
/* 225 */           d3 *= d7;
/* 226 */           d4 *= d7;
/* 227 */           d5 *= d7;
/* 228 */           this.world.func_175688_a(EnumParticleTypes.EXPLOSION_NORMAL, (d0 + this.x) / 2.0D, (d1 + this.y) / 2.0D, (d2 + this.z) / 2.0D, d3, d4, d5, new int[0]);
/* 229 */           this.world.func_175688_a(EnumParticleTypes.SMOKE_NORMAL, d0, d1, d2, d3, d4, d5, new int[0]);
/*     */         } 
/*     */         
/* 232 */         if (iblockstate.func_185904_a() != Material.field_151579_a) {
/*     */           
/* 234 */           if (block.func_149659_a(this.explosion))
/*     */           {
/* 236 */             block.func_180653_a(this.world, blockpos, this.world.func_180495_p(blockpos), 1.0F / this.size, 0);
/*     */           }
/*     */           
/* 239 */           block.onBlockExploded(this.world, blockpos, this.explosion);
/*     */         } 
/*     */       } 
/*     */     }
/*     */     
/* 244 */     if (this.causesFire)
/*     */     {
/* 246 */       for (BlockPos blockpos1 : this.affectedBlockPositions) {
/*     */         
/* 248 */         if (this.world.func_180495_p(blockpos1).func_185904_a() == Material.field_151579_a && this.world.func_180495_p(blockpos1.func_177977_b()).func_185913_b() && this.random.nextInt(3) == 0)
/*     */         {
/* 250 */           this.world.func_175656_a(blockpos1, Blocks.field_150480_ab.func_176223_P());
/*     */         }
/*     */       } 
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public Map<EntityPlayer, Vec3d> getPlayerKnockbackMap() {
/* 258 */     return this.playerKnockbackMap;
/*     */   }
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public EntityLivingBase getExplosivePlacedBy() {
/* 264 */     if (this.exploder == null)
/*     */     {
/* 266 */       return null;
/*     */     }
/* 268 */     if (this.exploder instanceof EntityTNTPrimed)
/*     */     {
/* 270 */       return ((EntityTNTPrimed)this.exploder).func_94083_c();
/*     */     }
/*     */ 
/*     */     
/* 274 */     return (this.exploder instanceof EntityLivingBase) ? (EntityLivingBase)this.exploder : null;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void clearAffectedBlockPositions() {
/* 280 */     this.affectedBlockPositions.clear();
/*     */   }
/*     */ 
/*     */   
/*     */   public List<BlockPos> getAffectedBlockPositions() {
/* 285 */     return this.affectedBlockPositions;
/*     */   }
/*     */   public Vec3d getPosition() {
/* 288 */     return this.position;
/*     */   }
/*     */ }


/* Location:              C:\Users\DuduM\OneDrive\Área de Trabalho\mw10\modularwarfare-shining-2023.2.4.4f-fix10.jar!\com\modularwarfare\common\world\MWFExplosion.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */