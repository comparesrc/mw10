/*     */ package com.modularwarfare.common.entity.item;
/*     */ 
/*     */ import com.modularwarfare.ModConfig;
/*     */ import net.minecraft.block.state.IBlockState;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.entity.EntityLivingBase;
/*     */ import net.minecraft.entity.EntityTracker;
/*     */ import net.minecraft.entity.item.EntityItem;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.nbt.NBTTagCompound;
/*     */ import net.minecraft.network.Packet;
/*     */ import net.minecraft.network.play.server.SPacketCollectItem;
/*     */ import net.minecraft.util.math.BlockPos;
/*     */ import net.minecraft.util.math.MathHelper;
/*     */ import net.minecraft.world.World;
/*     */ import net.minecraft.world.WorldServer;
/*     */ 
/*     */ 
/*     */ 
/*     */ public class EntityItemLoot
/*     */   extends EntityItemNew
/*     */ {
/*     */   EntityItem orig;
/*     */   long gameSeed;
/*     */   private int customAge;
/*     */   
/*     */   public EntityItemLoot(World world) {
/*  29 */     super(world);
/*  30 */     this.orig = null;
/*  31 */     this.hoverStart = this.field_70146_Z.nextFloat();
/*  32 */     func_70105_a(0.65F, 0.65F);
/*  33 */     setCustomAge(0);
/*     */   }
/*     */   
/*     */   public EntityItemLoot(EntityItem orig) {
/*  37 */     this(orig.field_70170_p, orig.field_70165_t, orig.field_70163_u, orig.field_70161_v, orig.func_92059_d());
/*  38 */     NBTTagCompound oldT = new NBTTagCompound();
/*  39 */     orig.func_70014_b(oldT);
/*  40 */     func_70037_a(oldT);
/*  41 */     String thrower = orig.func_145800_j();
/*  42 */     EntityPlayer entityPlayer = (thrower == null) ? null : orig.field_70170_p.func_72924_a(thrower);
/*  43 */     double tossSpd = (entityPlayer != null && entityPlayer.func_70051_ag()) ? 2.0D : 1.0D;
/*  44 */     if (entityPlayer != null) {
/*  45 */       this.field_70159_w = orig.field_70159_w * tossSpd;
/*  46 */       this.field_70181_x = orig.field_70181_x * tossSpd;
/*  47 */       this.field_70179_y = orig.field_70179_y * tossSpd;
/*     */     } 
/*  49 */     setPickupDelay(0);
/*  50 */     setCustomAge(0);
/*     */   }
/*     */   
/*     */   public EntityItemLoot(World world, double x, double y, double z, ItemStack stack) {
/*  54 */     super(world, x, y, z, stack);
/*  55 */     this.orig = null;
/*  56 */     this.hoverStart = this.field_70146_Z.nextFloat();
/*  57 */     func_70105_a(0.65F, 0.65F);
/*  58 */     setCustomAge(0);
/*     */   }
/*     */ 
/*     */   
/*     */   public void func_70071_h_() {
/*  63 */     super.func_70071_h_();
/*  64 */     if (!this.field_70170_p.field_72995_K) {
/*  65 */       setCustomAge(getCustomAge() + 1);
/*     */     }
/*  67 */     float x = MathHelper.func_76128_c(this.field_70165_t);
/*  68 */     float y = MathHelper.func_76128_c(this.field_70163_u);
/*  69 */     float z = MathHelper.func_76128_c(this.field_70161_v);
/*  70 */     IBlockState bsHere = this.field_70170_p.func_180495_p(new BlockPos(x, y, z));
/*  71 */     IBlockState bsAbove = this.field_70170_p.func_180495_p(new BlockPos(x, (y + 1.0F), z));
/*  72 */     boolean liqHere = (bsHere.func_177230_c() instanceof net.minecraft.block.BlockLiquid || bsHere.func_177230_c() instanceof net.minecraftforge.fluids.IFluidBlock);
/*  73 */     boolean liqAbove = (bsAbove.func_177230_c() instanceof net.minecraft.block.BlockLiquid || bsAbove.func_177230_c() instanceof net.minecraftforge.fluids.IFluidBlock);
/*  74 */     if (liqHere) {
/*  75 */       this.field_70122_E = false;
/*  76 */       this.field_70171_ac = true;
/*  77 */       if (this.field_70181_x < 0.05D && (liqAbove || this.field_70163_u % 1.0D < 0.8999999761581421D)) {
/*  78 */         this.field_70181_x += Math.min(0.075D, 0.075D - this.field_70181_x);
/*     */       }
/*  80 */       this.field_70159_w = MathHelper.func_151237_a(this.field_70159_w, -0.05D, 0.05D);
/*  81 */       this.field_70179_y = MathHelper.func_151237_a(this.field_70179_y, -0.05D, 0.05D);
/*     */     } 
/*  83 */     if (getCustomAge() >= ModConfig.INSTANCE.drops.drops_despawn_time * 20) {
/*  84 */       func_70106_y();
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public void func_70014_b(NBTTagCompound compound) {
/*  90 */     super.func_70014_b(compound);
/*  91 */     compound.func_74772_a("GameSeed", this.gameSeed);
/*     */   }
/*     */ 
/*     */   
/*     */   public void func_70037_a(NBTTagCompound compound) {
/*  96 */     super.func_70037_a(compound);
/*  97 */     this.gameSeed = compound.func_74763_f("GameSeed");
/*     */   }
/*     */   
/*     */   public boolean func_70067_L() {
/* 101 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public void func_70100_b_(EntityPlayer player) {}
/*     */ 
/*     */   
/*     */   public int getCustomAge() {
/* 109 */     return this.customAge;
/*     */   }
/*     */   
/*     */   public void setCustomAge(int customAge) {
/* 113 */     this.customAge = customAge;
/*     */   }
/*     */   
/*     */   public void pickup(EntityPlayer player) {
/* 117 */     if (player.field_71071_by.func_70447_i() != -1) {
/* 118 */       if (this.field_70128_L || player.field_70170_p.field_72995_K) {
/*     */         return;
/*     */       }
/* 121 */       int i = getItem().func_190916_E();
/* 122 */       onItemPickup(this, i, (EntityLivingBase)player);
/*     */       
/* 124 */       player.func_191521_c(getItem());
/* 125 */       func_70106_y();
/*     */     } 
/*     */   }
/*     */   
/*     */   public void onItemPickup(Entity entityIn, int quantity, EntityLivingBase player) {
/* 130 */     if (!entityIn.field_70128_L && !this.field_70170_p.field_72995_K) {
/* 131 */       EntityTracker entitytracker = ((WorldServer)this.field_70170_p).func_73039_n();
/* 132 */       if (entityIn instanceof EntityItemNew || entityIn instanceof net.minecraft.entity.projectile.EntityArrow || entityIn instanceof net.minecraft.entity.item.EntityXPOrb)
/* 133 */         entitytracker.func_151247_a(entityIn, (Packet)new SPacketCollectItem(entityIn.func_145782_y(), player.func_145782_y(), quantity)); 
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\DuduM\OneDrive\Área de Trabalho\mw9\modularwarfare-shining-2023.2.4.4f-fix9.jar!\com\modularwarfare\common\entity\item\EntityItemLoot.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */