/*     */ package com.modularwarfare.common.entity.decals;
/*     */ 
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.nbt.NBTTagCompound;
/*     */ import net.minecraft.network.datasync.DataParameter;
/*     */ import net.minecraft.network.datasync.DataSerializers;
/*     */ import net.minecraft.network.datasync.EntityDataManager;
/*     */ import net.minecraft.util.ResourceLocation;
/*     */ import net.minecraft.world.World;
/*     */ import net.minecraftforge.fml.relauncher.Side;
/*     */ import net.minecraftforge.fml.relauncher.SideOnly;
/*     */ 
/*     */ public abstract class EntityDecal
/*     */   extends Entity {
/*  15 */   private static final DataParameter PERMANENT = EntityDataManager.func_187226_a(EntityDecal.class, DataSerializers.field_187198_h);
/*  16 */   private static final DataParameter AGE = EntityDataManager.func_187226_a(EntityDecal.class, DataSerializers.field_187192_b);
/*  17 */   private static final DataParameter SEED = EntityDataManager.func_187226_a(EntityDecal.class, DataSerializers.field_187192_b);
/*  18 */   private static final DataParameter SIDE_ID = EntityDataManager.func_187226_a(EntityDecal.class, DataSerializers.field_187192_b);
/*  19 */   private static final DataParameter TEXTURE_NUMBER = EntityDataManager.func_187226_a(EntityDecal.class, DataSerializers.field_187192_b);
/*     */   protected int maxTimeAlive;
/*     */   private EnumDecalSide side;
/*     */   
/*     */   public EntityDecal(World var1) {
/*  24 */     super(var1);
/*  25 */     this.field_70178_ae = true;
/*  26 */     setSide(EnumDecalSide.ALL);
/*  27 */     func_70105_a(0.0F, 0.0F);
/*  28 */     this.maxTimeAlive = 900;
/*     */   }
/*     */   
/*     */   public abstract ResourceLocation getDecalTexture();
/*     */   
/*     */   public abstract int getTextureCount();
/*     */   
/*     */   protected void func_70088_a() {
/*  36 */     this.field_70180_af.func_187214_a(PERMANENT, Boolean.valueOf(false));
/*  37 */     this.field_70180_af.func_187214_a(AGE, Integer.valueOf(0));
/*  38 */     this.field_70180_af.func_187214_a(SEED, Integer.valueOf(0));
/*  39 */     this.field_70180_af.func_187214_a(SIDE_ID, Integer.valueOf(0));
/*  40 */     this.field_70180_af.func_187214_a(TEXTURE_NUMBER, Integer.valueOf(this.field_70170_p.field_73012_v.nextInt(getTextureCount())));
/*     */   }
/*     */ 
/*     */   
/*     */   public void func_174812_G() {}
/*     */   
/*     */   public EnumDecalSide getSide() {
/*  47 */     return this.side;
/*     */   }
/*     */   
/*     */   public void setSide(EnumDecalSide side) {
/*  51 */     this.side = side;
/*  52 */     setSideID(side.getId());
/*     */   }
/*     */   
/*     */   public float getAgeRatio() {
/*  56 */     return Math.max(0.0F, (getAge() + this.maxTimeAlive) / this.maxTimeAlive);
/*     */   }
/*     */   
/*     */   public boolean isPermanent() {
/*  60 */     return ((Boolean)this.field_70180_af.func_187225_a(PERMANENT)).booleanValue();
/*     */   }
/*     */   
/*     */   public void setPermanent(boolean bool) {
/*  64 */     this.field_70180_af.func_187227_b(PERMANENT, Boolean.valueOf(bool));
/*     */   }
/*     */   
/*     */   public int getAge() {
/*  68 */     return ((Integer)this.field_70180_af.func_187225_a(AGE)).intValue();
/*     */   }
/*     */   
/*     */   public void setAge(int num) {
/*  72 */     this.field_70180_af.func_187227_b(AGE, Integer.valueOf(num));
/*     */   }
/*     */   
/*     */   public int getSeed() {
/*  76 */     return ((Integer)this.field_70180_af.func_187225_a(SEED)).intValue();
/*     */   }
/*     */   
/*     */   public void setSeed(int num) {
/*  80 */     this.field_70180_af.func_187227_b(SEED, Integer.valueOf(num));
/*     */   }
/*     */   
/*     */   public int getSideID() {
/*  84 */     return ((Integer)this.field_70180_af.func_187225_a(SIDE_ID)).intValue();
/*     */   }
/*     */   
/*     */   public void setSideID(int num) {
/*  88 */     this.field_70180_af.func_187227_b(SIDE_ID, Integer.valueOf(num));
/*     */   }
/*     */   
/*     */   public int getTextureNumber() {
/*  92 */     return ((Integer)this.field_70180_af.func_187225_a(TEXTURE_NUMBER)).intValue();
/*     */   }
/*     */   
/*     */   public void setTextureNumber(int textureNumber) {
/*  96 */     this.field_70180_af.func_187227_b(TEXTURE_NUMBER, Integer.valueOf(textureNumber));
/*     */   }
/*     */   
/*     */   public boolean func_70067_L() {
/* 100 */     return false;
/*     */   }
/*     */   
/*     */   public void func_70106_y() {
/* 104 */     super.func_70106_y();
/*     */   }
/*     */   
/*     */   protected boolean func_70041_e_() {
/* 108 */     return false;
/*     */   }
/*     */   
/*     */   @SideOnly(Side.CLIENT)
/*     */   public boolean func_70112_a(double var1) {
/* 113 */     return true;
/*     */   }
/*     */   
/*     */   @SideOnly(Side.CLIENT)
/*     */   public boolean func_145770_h(double x, double y, double z) {
/* 118 */     return true;
/*     */   }
/*     */   
/*     */   public void func_70071_h_() {
/* 122 */     this.field_70145_X = true;
/* 123 */     if (!isPermanent()) {
/* 124 */       if (getAge() > -this.maxTimeAlive) {
/* 125 */         setAge(getAge() - 1);
/*     */       } else {
/* 127 */         func_70106_y();
/*     */       } 
/*     */     }
/*     */     
/* 131 */     this.field_70145_X = false;
/* 132 */     func_189654_d(true);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean func_70094_T() {
/* 137 */     return false;
/*     */   }
/*     */   
/*     */   public void func_70014_b(NBTTagCompound var1) {
/* 141 */     var1.func_74757_a("Permanent", isPermanent());
/* 142 */     var1.func_74768_a("Age", getAge());
/* 143 */     var1.func_74768_a("Seed", getSeed());
/* 144 */     var1.func_74768_a("SideID", getSideID());
/* 145 */     var1.func_74768_a("TextureNumber", getTextureNumber());
/*     */   }
/*     */   
/*     */   public void func_70037_a(NBTTagCompound var1) {
/* 149 */     setPermanent(var1.func_74767_n("Permanent"));
/* 150 */     setAge(var1.func_74762_e("Age"));
/* 151 */     setSeed(var1.func_74762_e("Seed"));
/* 152 */     setSideID(var1.func_74762_e("SideID"));
/* 153 */     if (var1.func_74764_b("TextureNumber")) {
/* 154 */       setTextureNumber(var1.func_74762_e("TextureNumber"));
/*     */     }
/*     */   }
/*     */   
/*     */   public enum EnumDecalSide
/*     */   {
/* 160 */     ALL(0),
/* 161 */     WALLS(1),
/* 162 */     FLOOR(2),
/* 163 */     NORTH(3),
/* 164 */     EAST(4),
/* 165 */     SOUTH(5),
/* 166 */     WEST(6),
/* 167 */     CEILING(7);
/*     */     
/*     */     private final int id;
/*     */     
/*     */     EnumDecalSide(int id) {
/* 172 */       this.id = id;
/*     */     }
/*     */     
/*     */     public static EnumDecalSide getEnumFromId(int id) {
/* 176 */       return values()[id];
/*     */     }
/*     */     
/*     */     public int getId() {
/* 180 */       return this.id;
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\DuduM\OneDrive\Área de Trabalho\mw10\modularwarfare-shining-2023.2.4.4f-fix10.jar!\com\modularwarfare\common\entity\decals\EntityDecal.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */