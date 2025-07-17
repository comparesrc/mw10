/*     */ package com.modularwarfare.common.entity;
/*     */ 
/*     */ import com.modularwarfare.common.entity.decals.EntityShell;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.entity.IProjectile;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.entity.projectile.EntityArrow;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.nbt.NBTTagCompound;
/*     */ import net.minecraft.network.datasync.DataParameter;
/*     */ import net.minecraft.network.datasync.DataSerializers;
/*     */ import net.minecraft.network.datasync.EntityDataManager;
/*     */ import net.minecraft.util.EnumParticleTypes;
/*     */ import net.minecraft.util.math.MathHelper;
/*     */ import net.minecraft.world.World;
/*     */ 
/*     */ public class EntityBullet extends EntityArrow implements IProjectile {
/*     */   public EntityPlayer player;
/*  19 */   public int field_70257_an = 0;
/*  20 */   public int ticks = 0;
/*  21 */   private int xTile = -1;
/*  22 */   private int yTile = -1;
/*  23 */   private int zTile = -1;
/*  24 */   private int inTile = 0;
/*  25 */   private int inData = 0;
/*     */   private boolean inGround = false;
/*     */   private float damage;
/*  28 */   private int liveTime = 600;
/*     */   
/*     */   private float velocity;
/*     */   
/*  32 */   private static final DataParameter BULLET_NAME = EntityDataManager.func_187226_a(EntityShell.class, DataSerializers.field_187194_d);
/*     */   
/*     */   public EntityBullet(World world) {
/*  35 */     super(world);
/*  36 */     func_70105_a(0.2F, 0.2F);
/*     */   }
/*     */   
/*     */   public EntityBullet(World par1World, EntityPlayer par2EntityPlayer, float damage, float accuracy, float velocity, String bulletName) {
/*  40 */     super(par1World);
/*  41 */     setBulletType(bulletName);
/*  42 */     this.player = par2EntityPlayer;
/*  43 */     this.field_70250_c = (Entity)par2EntityPlayer;
/*  44 */     func_70105_a(0.2F, 0.2F);
/*  45 */     func_70012_b(par2EntityPlayer.field_70165_t, par2EntityPlayer.field_70163_u + par2EntityPlayer.func_70047_e(), par2EntityPlayer.field_70161_v, par2EntityPlayer.field_70177_z, par2EntityPlayer.field_70125_A);
/*  46 */     this.field_70165_t -= (MathHelper.func_76134_b(this.field_70177_z / 180.0F * 3.141593F) * 0.16F);
/*  47 */     this.field_70163_u -= 0.0D;
/*  48 */     this.field_70161_v -= (MathHelper.func_76126_a(this.field_70177_z / 180.0F * 3.141593F) * 0.16F);
/*  49 */     func_70107_b(par2EntityPlayer.field_70165_t, par2EntityPlayer.field_70163_u + par2EntityPlayer.func_70047_e(), par2EntityPlayer.field_70161_v);
/*  50 */     this.field_70159_w = (-MathHelper.func_76126_a(this.field_70177_z / 180.0F * 3.141593F) * MathHelper.func_76134_b(this.field_70125_A / 180.0F * 3.141593F));
/*  51 */     this.field_70179_y = (MathHelper.func_76134_b(this.field_70177_z / 180.0F * 3.141593F) * MathHelper.func_76134_b(this.field_70125_A / 180.0F * 3.141593F));
/*  52 */     this.field_70181_x = -MathHelper.func_76126_a(this.field_70125_A / 180.0F * 3.141593F);
/*  53 */     this.damage = damage;
/*  54 */     func_70186_c(this.field_70159_w, this.field_70181_x, this.field_70179_y, velocity, accuracy);
/*     */   }
/*     */   
/*     */   public void func_70071_h_() {
/*  58 */     func_70030_z();
/*  59 */     this.ticks++;
/*  60 */     this.liveTime--;
/*  61 */     if (this.field_70163_u > 300.0D || this.liveTime <= 0) {
/*  62 */       func_70106_y();
/*     */     }
/*  64 */     if (this.field_70127_C == 0.0F && this.field_70126_B == 0.0F) {
/*  65 */       float f = (float)Math.sqrt(this.field_70159_w * this.field_70159_w + this.field_70179_y * this.field_70179_y);
/*  66 */       this.field_70126_B = this.field_70177_z = (float)(Math.atan2(this.field_70159_w, this.field_70179_y) * 180.0D / Math.PI);
/*  67 */       this.field_70127_C = this.field_70125_A = (float)(Math.atan2(this.field_70181_x, f) * 180.0D / Math.PI);
/*     */     } 
/*  69 */     if (this.inGround) {
/*  70 */       func_70106_y();
/*     */     } else {
/*  72 */       this.field_70165_t += this.field_70159_w;
/*  73 */       this.field_70163_u += this.field_70181_x;
/*  74 */       this.field_70161_v += this.field_70179_y;
/*     */       
/*  76 */       float f2 = (float)Math.sqrt(this.field_70159_w * this.field_70159_w + this.field_70179_y * this.field_70179_y);
/*  77 */       this.field_70177_z = (float)(Math.atan2(this.field_70159_w, this.field_70179_y) * 180.0D / Math.PI);
/*     */       
/*  79 */       for (this.field_70125_A = (float)(Math.atan2(this.field_70181_x, f2) * 180.0D / Math.PI); this.field_70125_A - this.field_70127_C < -180.0F; this.field_70127_C -= 360.0F);
/*     */ 
/*     */       
/*  82 */       while (this.field_70125_A - this.field_70127_C >= 180.0F) {
/*  83 */         this.field_70127_C += 360.0F;
/*     */       }
/*     */       
/*  86 */       while (this.field_70177_z - this.field_70126_B < -180.0F) {
/*  87 */         this.field_70126_B -= 360.0F;
/*     */       }
/*     */       
/*  90 */       while (this.field_70177_z - this.field_70126_B >= 180.0F) {
/*  91 */         this.field_70126_B += 360.0F;
/*     */       }
/*     */       
/*  94 */       this.field_70125_A = this.field_70127_C + (this.field_70125_A - this.field_70127_C) * 0.2F;
/*  95 */       this.field_70177_z = this.field_70126_B + (this.field_70177_z - this.field_70126_B) * 0.2F;
/*     */       
/*  97 */       float f4 = 1.0F;
/*  98 */       if (func_70090_H()) {
/*  99 */         for (int j1 = 0; j1 < 4; j1++) {
/* 100 */           float f3 = 0.25F;
/* 101 */           float f5 = 0.75F;
/* 102 */           this.field_70170_p.func_175688_a(EnumParticleTypes.WATER_BUBBLE, this.field_70165_t - this.field_70159_w * f3, this.field_70163_u - 0.25D + this.field_70181_x * f5, this.field_70161_v - this.field_70179_y * f3, this.field_70159_w, this.field_70181_x, this.field_70179_y, new int[0]);
/*     */         } 
/* 104 */         f4 = 0.8F;
/*     */       } 
/*     */       
/* 107 */       this.field_70159_w *= f4;
/* 108 */       this.field_70181_x *= f4;
/* 109 */       this.field_70179_y *= f4;
/* 110 */       func_70107_b(this.field_70165_t, this.field_70163_u, this.field_70161_v);
/* 111 */       if (this.inGround) {
/* 112 */         func_70106_y();
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void func_70014_b(NBTTagCompound par1NBTTagCompound) {
/* 119 */     par1NBTTagCompound.func_74777_a("xTile", (short)this.xTile);
/* 120 */     par1NBTTagCompound.func_74777_a("yTile", (short)this.yTile);
/* 121 */     par1NBTTagCompound.func_74777_a("zTile", (short)this.zTile);
/* 122 */     par1NBTTagCompound.func_74780_a("motX", this.field_70159_w);
/* 123 */     par1NBTTagCompound.func_74780_a("motY", this.field_70181_x);
/* 124 */     par1NBTTagCompound.func_74780_a("motZ", this.field_70179_y);
/* 125 */     par1NBTTagCompound.func_74774_a("inTile", (byte)this.inTile);
/* 126 */     par1NBTTagCompound.func_74774_a("inData", (byte)this.inData);
/* 127 */     par1NBTTagCompound.func_74774_a("inGround", (byte)(this.inGround ? 1 : 0));
/* 128 */     par1NBTTagCompound.func_74776_a("damage", this.damage);
/* 129 */     par1NBTTagCompound.func_74776_a("velocity", this.velocity);
/*     */   }
/*     */   
/*     */   public void func_70037_a(NBTTagCompound par1NBTTagCompound) {
/* 133 */     this.xTile = par1NBTTagCompound.func_74765_d("xTile");
/* 134 */     this.yTile = par1NBTTagCompound.func_74765_d("yTile");
/* 135 */     this.zTile = par1NBTTagCompound.func_74765_d("zTile");
/* 136 */     this.field_70159_w = par1NBTTagCompound.func_74769_h("motX");
/* 137 */     this.field_70181_x = par1NBTTagCompound.func_74769_h("motY");
/* 138 */     this.field_70179_y = par1NBTTagCompound.func_74769_h("motZ");
/* 139 */     this.inTile = par1NBTTagCompound.func_74771_c("inTile") & 0xFF;
/* 140 */     this.inData = par1NBTTagCompound.func_74771_c("inData") & 0xFF;
/* 141 */     this.inGround = (par1NBTTagCompound.func_74771_c("inGround") == 1);
/* 142 */     if (par1NBTTagCompound.func_74764_b("damage")) {
/* 143 */       this.damage = par1NBTTagCompound.func_74760_g("damage");
/*     */     }
/* 145 */     this.velocity = par1NBTTagCompound.func_74760_g("velocity");
/*     */   }
/*     */ 
/*     */   
/*     */   protected ItemStack func_184550_j() {
/* 150 */     return null;
/*     */   }
/*     */ 
/*     */   
/*     */   protected void func_70088_a() {
/* 155 */     this.field_70180_af.func_187214_a(BULLET_NAME, "");
/*     */   }
/*     */   
/*     */   public String getBulletName() {
/* 159 */     return (String)this.field_70180_af.func_187225_a(BULLET_NAME);
/*     */   }
/*     */   
/*     */   public void setBulletType(String bulletType) {
/* 163 */     this.field_70180_af.func_187227_b(BULLET_NAME, bulletType);
/*     */   }
/*     */ }


/* Location:              C:\Users\DuduM\OneDrive\Área de Trabalho\mw10\modularwarfare-shining-2023.2.4.4f-fix10.jar!\com\modularwarfare\common\entity\EntityBullet.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */