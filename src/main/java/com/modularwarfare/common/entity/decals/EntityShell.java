/*     */ package com.modularwarfare.common.entity.decals;
/*     */ 
/*     */ import com.modularwarfare.ModConfig;
/*     */ import com.modularwarfare.ModularWarfare;
/*     */ import com.modularwarfare.common.guns.ItemBullet;
/*     */ import com.modularwarfare.common.guns.ItemGun;
/*     */ import com.modularwarfare.common.handler.ServerTickHandler;
/*     */ import com.modularwarfare.common.network.PacketBase;
/*     */ import com.modularwarfare.common.network.PacketPlaySound;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.entity.IProjectile;
/*     */ import net.minecraft.entity.MoverType;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.nbt.NBTTagCompound;
/*     */ import net.minecraft.network.datasync.DataParameter;
/*     */ import net.minecraft.network.datasync.DataSerializers;
/*     */ import net.minecraft.network.datasync.EntityDataManager;
/*     */ import net.minecraft.util.math.MathHelper;
/*     */ import net.minecraft.util.math.Vec3d;
/*     */ import net.minecraft.world.World;
/*     */ import net.minecraftforge.fml.common.network.NetworkRegistry;
/*     */ import net.minecraftforge.fml.relauncher.Side;
/*     */ import net.minecraftforge.fml.relauncher.SideOnly;
/*     */ 
/*     */ public class EntityShell extends Entity implements IProjectile {
/*  27 */   private static final DataParameter AGE = EntityDataManager.func_187226_a(EntityShell.class, DataSerializers.field_187192_b);
/*  28 */   private static final DataParameter BULLET_NAME = EntityDataManager.func_187226_a(EntityShell.class, DataSerializers.field_187194_d);
/*  29 */   private static final DataParameter GUN_NAME = EntityDataManager.func_187226_a(EntityShell.class, DataSerializers.field_187194_d);
/*  30 */   private static final DataParameter GUN_SKINID = EntityDataManager.func_187226_a(EntityShell.class, DataSerializers.field_187192_b);
/*     */   
/*     */   public boolean playedSound;
/*     */   protected int ticksInGround;
/*     */   protected int maxTimeAlive;
/*     */   
/*     */   public EntityShell(World worldIn) {
/*  37 */     super(worldIn);
/*  38 */     func_70105_a(0.1F, 0.1F);
/*  39 */     this.maxTimeAlive = 20 * ModConfig.INSTANCE.casings_drops.despawn_time;
/*  40 */     this.playedSound = false;
/*     */   }
/*     */   
/*     */   public EntityShell(World worldIn, EntityPlayer throwerIn, ItemStack gunStack, ItemGun gun, ItemBullet bullet) {
/*  44 */     super(worldIn);
/*     */     
/*  46 */     if (bullet == null) {
/*  47 */       func_70106_y();
/*     */       
/*     */       return;
/*     */     } 
/*  51 */     int skinId = 0;
/*  52 */     if (gunStack.func_77942_o() && 
/*  53 */       gunStack.func_77978_p().func_74764_b("skinId")) {
/*  54 */       skinId = gunStack.func_77978_p().func_74762_e("skinId");
/*     */     }
/*     */     
/*  57 */     setGunSkinID(skinId);
/*  58 */     setGunType(gun.type.internalName);
/*     */     
/*  60 */     setBulletType(bullet.type.internalName);
/*     */     
/*  62 */     Vec3d rotateYaw = new Vec3d(0.0D, 0.0D, 0.0D);
/*     */     
/*  64 */     if (ServerTickHandler.playerAimInstant.get(throwerIn.getDisplayNameString()) != null || ServerTickHandler.playerReloadCooldown.get(throwerIn.getDisplayNameString()) != null) {
/*  65 */       if (((Boolean)ServerTickHandler.playerAimInstant.get(throwerIn.getDisplayNameString())).booleanValue()) {
/*  66 */         rotateYaw = gun.type.shellEjectOffsetAiming.func_178789_a(-((float)Math.toRadians(throwerIn.field_70125_A))).func_178785_b(-((float)Math.toRadians(throwerIn.field_70177_z)));
/*     */       } else {
/*  68 */         rotateYaw = gun.type.shellEjectOffsetNormal.func_178789_a(-((float)Math.toRadians(throwerIn.field_70125_A))).func_178785_b(-((float)Math.toRadians(throwerIn.field_70177_z)));
/*     */       } 
/*     */     }
/*     */     
/*  72 */     Vec3d source = new Vec3d(throwerIn.field_70165_t + throwerIn.field_70159_w + rotateYaw.field_72450_a, throwerIn.field_70163_u + throwerIn.func_70047_e() - 0.10000000149011612D + throwerIn.field_70181_x + rotateYaw.field_72448_b, throwerIn.field_70161_v + throwerIn.field_70179_y + rotateYaw.field_72449_c);
/*  73 */     func_70107_b(source.field_72450_a, source.field_72448_b, source.field_72449_c);
/*  74 */     func_70105_a(0.25F, 0.25F);
/*  75 */     this.maxTimeAlive = 20 * ModConfig.INSTANCE.casings_drops.despawn_time;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected void func_70088_a() {
/*  81 */     this.field_70180_af.func_187214_a(AGE, Integer.valueOf(0));
/*  82 */     this.field_70180_af.func_187214_a(BULLET_NAME, "");
/*  83 */     this.field_70180_af.func_187214_a(GUN_NAME, "");
/*  84 */     this.field_70180_af.func_187214_a(GUN_SKINID, Integer.valueOf(0));
/*     */   }
/*     */   
/*     */   public int getAge() {
/*  88 */     return ((Integer)this.field_70180_af.func_187225_a(AGE)).intValue();
/*     */   }
/*     */   
/*     */   public void setAge(int num) {
/*  92 */     this.field_70180_af.func_187227_b(AGE, Integer.valueOf(num));
/*     */   }
/*     */   
/*     */   public String getBulletName() {
/*  96 */     return (String)this.field_70180_af.func_187225_a(BULLET_NAME);
/*     */   }
/*     */   
/*     */   public void setBulletType(String bulletType) {
/* 100 */     this.field_70180_af.func_187227_b(BULLET_NAME, bulletType);
/*     */   }
/*     */   
/*     */   public String getGunName() {
/* 104 */     return (String)this.field_70180_af.func_187225_a(GUN_NAME);
/*     */   }
/*     */   
/*     */   public void setGunType(String gunType) {
/* 108 */     this.field_70180_af.func_187227_b(GUN_NAME, gunType);
/*     */   }
/*     */   
/*     */   public int getGunSkinID() {
/* 112 */     return ((Integer)this.field_70180_af.func_187225_a(GUN_SKINID)).intValue();
/*     */   }
/*     */   
/*     */   public void setGunSkinID(int num) {
/* 116 */     this.field_70180_af.func_187227_b(GUN_SKINID, Integer.valueOf(num));
/*     */   }
/*     */ 
/*     */   
/*     */   public void func_70071_h_() {
/* 121 */     super.func_70071_h_();
/* 122 */     this.field_70169_q = this.field_70165_t;
/* 123 */     this.field_70167_r = this.field_70163_u;
/* 124 */     this.field_70166_s = this.field_70161_v;
/*     */     
/* 126 */     if (!func_189652_ae()) {
/* 127 */       this.field_70181_x -= 0.03999999910593033D;
/*     */     }
/*     */     
/* 130 */     func_70091_d(MoverType.SELF, this.field_70159_w, this.field_70181_x, this.field_70179_y);
/* 131 */     this.field_70159_w *= 0.9800000190734863D;
/* 132 */     this.field_70181_x *= 0.9800000190734863D;
/* 133 */     this.field_70179_y *= 0.9800000190734863D;
/*     */     
/* 135 */     if (this.field_70122_E) {
/* 136 */       this.field_70159_w *= 0.699999988079071D;
/* 137 */       this.field_70179_y *= 0.699999988079071D;
/* 138 */       this.field_70181_x *= -0.5D;
/* 139 */       if (!this.playedSound && 
/* 140 */         ModularWarfare.bulletTypes.containsKey(getBulletName())) {
/* 141 */         ItemBullet itemBullet = (ItemBullet)ModularWarfare.bulletTypes.get(getBulletName());
/* 142 */         ModularWarfare.NETWORK.sendToAllAround((PacketBase)new PacketPlaySound(func_180425_c(), itemBullet.type.shellSound, 0.8F, 1.0F), new NetworkRegistry.TargetPoint(this.field_71093_bK, this.field_70165_t, this.field_70163_u, this.field_70161_v, 3.0D));
/* 143 */         this.playedSound = true;
/*     */       } 
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 149 */     this.field_70165_t += this.field_70159_w;
/* 150 */     this.field_70163_u += this.field_70181_x;
/* 151 */     this.field_70161_v += this.field_70179_y;
/* 152 */     float f = MathHelper.func_76133_a(this.field_70159_w * this.field_70159_w + this.field_70179_y * this.field_70179_y);
/*     */     
/* 154 */     for (this.field_70125_A = (float)(MathHelper.func_181159_b(this.field_70181_x, f) * 57.29577951308232D); this.field_70125_A - this.field_70127_C < -180.0F; this.field_70127_C -= 360.0F);
/*     */ 
/*     */ 
/*     */     
/* 158 */     while (this.field_70125_A - this.field_70127_C >= 180.0F) {
/* 159 */       this.field_70127_C += 360.0F;
/*     */     }
/*     */     
/* 162 */     while (this.field_70177_z - this.field_70126_B < -180.0F) {
/* 163 */       this.field_70126_B -= 360.0F;
/*     */     }
/*     */     
/* 166 */     while (this.field_70177_z - this.field_70126_B >= 180.0F) {
/* 167 */       this.field_70126_B += 360.0F;
/*     */     }
/*     */     
/* 170 */     this.field_70125_A = this.field_70127_C + (this.field_70125_A - this.field_70127_C) * this.field_70146_Z.nextFloat();
/* 171 */     this.field_70177_z = this.field_70126_B + (this.field_70177_z - this.field_70126_B) * this.field_70146_Z.nextFloat();
/*     */     
/* 173 */     if (getAge() > -this.maxTimeAlive) {
/* 174 */       setAge(getAge() - 1);
/*     */     } else {
/* 176 */       func_70106_y();
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void func_70037_a(NBTTagCompound compound) {}
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void func_70014_b(NBTTagCompound compound) {}
/*     */ 
/*     */ 
/*     */   
/*     */   public void func_70186_c(double x, double y, double z, float velocity, float inaccuracy) {}
/*     */ 
/*     */ 
/*     */   
/*     */   public void setHeadingFromThrower(Entity entityThrower, float rotationPitchIn, float rotationYawIn, float pitchOffset, float velocity, float inaccuracy) {
/* 197 */     setHeadingFromThrower(entityThrower, rotationPitchIn, rotationYawIn, pitchOffset, velocity, inaccuracy, 0.1F);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void setHeadingFromThrower(Entity entityThrower, float rotationPitchIn, float rotationYawIn, float pitchOffset, float velocity, float inaccuracy, float forwardOffset) {
/* 203 */     float sideOffset = 0.0F;
/*     */     
/* 205 */     this.field_70165_t -= (MathHelper.func_76134_b(this.field_70177_z / 180.0F * 3.1415927F) * sideOffset + 
/* 206 */       MathHelper.func_76126_a(this.field_70177_z / 180.0F * 3.1415927F) * 
/* 207 */       MathHelper.func_76134_b(this.field_70125_A / 180.0F * 3.1415927F) * forwardOffset);
/*     */     
/* 209 */     this.field_70163_u += (-MathHelper.func_76126_a(this.field_70125_A / 180.0F * 3.1415927F) * forwardOffset);
/*     */     
/* 211 */     this.field_70161_v -= (MathHelper.func_76126_a(this.field_70177_z / 180.0F * 3.1415927F) * sideOffset - 
/* 212 */       MathHelper.func_76134_b(this.field_70177_z / 180.0F * 3.1415927F) * 
/* 213 */       MathHelper.func_76134_b(this.field_70125_A / 180.0F * 3.1415927F) * forwardOffset);
/* 214 */     func_70107_b(this.field_70165_t, this.field_70163_u, this.field_70161_v);
/*     */     
/* 216 */     float f = -MathHelper.func_76126_a(rotationYawIn * 0.017453292F) * MathHelper.func_76134_b(rotationPitchIn * 0.017453292F);
/* 217 */     float f1 = -MathHelper.func_76126_a((rotationPitchIn + pitchOffset) * 0.017453292F);
/* 218 */     float f2 = MathHelper.func_76134_b(rotationYawIn * 0.017453292F) * MathHelper.func_76134_b(rotationPitchIn * 0.017453292F);
/* 219 */     setThrowableHeading(f, f1, f2, velocity * this.field_70146_Z.nextFloat(), inaccuracy);
/* 220 */     this.field_70159_w += entityThrower.field_70159_w;
/* 221 */     this.field_70179_y += entityThrower.field_70179_y;
/*     */     
/* 223 */     if (!entityThrower.field_70122_E) {
/* 224 */       this.field_70181_x += entityThrower.field_70181_x;
/*     */     }
/*     */   }
/*     */   
/*     */   public void setThrowableHeading(double x, double y, double z, float velocity, float inaccuracy) {
/* 229 */     float f = MathHelper.func_76133_a(x * x + y * y + z * z);
/* 230 */     x /= f;
/* 231 */     y /= f;
/* 232 */     z /= f;
/* 233 */     x += this.field_70146_Z.nextGaussian() * 0.007499999832361937D * inaccuracy;
/* 234 */     y += this.field_70146_Z.nextGaussian() * 0.007499999832361937D * inaccuracy;
/* 235 */     z += this.field_70146_Z.nextGaussian() * 0.007499999832361937D * inaccuracy;
/* 236 */     x *= velocity;
/* 237 */     y *= velocity;
/* 238 */     z *= velocity;
/* 239 */     this.field_70159_w = x;
/* 240 */     this.field_70181_x = y;
/* 241 */     this.field_70179_y = z;
/* 242 */     float f1 = MathHelper.func_76133_a(x * x + z * z);
/* 243 */     this.field_70177_z = (float)(MathHelper.func_181159_b(x, z) * 57.29577951308232D);
/* 244 */     this.field_70125_A = (float)(MathHelper.func_181159_b(y, f1) * 57.29577951308232D);
/* 245 */     this.field_70126_B = this.field_70177_z;
/* 246 */     this.field_70127_C = this.field_70125_A;
/* 247 */     this.ticksInGround = 0;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @SideOnly(Side.CLIENT)
/*     */   public void func_70016_h(double x, double y, double z) {
/* 255 */     this.field_70159_w = x;
/* 256 */     this.field_70181_x = y;
/* 257 */     this.field_70179_y = z;
/*     */     
/* 259 */     if (this.field_70127_C == 0.0F && this.field_70126_B == 0.0F) {
/* 260 */       float f = MathHelper.func_76133_a(x * x + z * z);
/* 261 */       this.field_70177_z = (float)(MathHelper.func_181159_b(x, z) * 57.29577951308232D);
/* 262 */       this.field_70125_A = (float)(MathHelper.func_181159_b(y, f) * 57.29577951308232D);
/* 263 */       this.field_70126_B = this.field_70177_z;
/* 264 */       this.field_70127_C = this.field_70125_A;
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\DuduM\OneDrive\Área de Trabalho\mw10\modularwarfare-shining-2023.2.4.4f-fix10.jar!\com\modularwarfare\common\entity\decals\EntityShell.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */