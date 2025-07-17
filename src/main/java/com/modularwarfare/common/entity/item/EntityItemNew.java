/*     */ package com.modularwarfare.common.entity.item;
/*     */ 
/*     */ import javax.annotation.Nullable;
/*     */ import net.minecraft.block.material.Material;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.entity.MoverType;
/*     */ import net.minecraft.entity.item.EntityItem;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.init.Items;
/*     */ import net.minecraft.init.SoundEvents;
/*     */ import net.minecraft.item.Item;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.nbt.NBTBase;
/*     */ import net.minecraft.nbt.NBTTagCompound;
/*     */ import net.minecraft.network.datasync.DataParameter;
/*     */ import net.minecraft.network.datasync.DataSerializers;
/*     */ import net.minecraft.network.datasync.EntityDataManager;
/*     */ import net.minecraft.stats.StatList;
/*     */ import net.minecraft.util.DamageSource;
/*     */ import net.minecraft.util.datafix.DataFixer;
/*     */ import net.minecraft.util.datafix.FixTypes;
/*     */ import net.minecraft.util.datafix.IDataWalker;
/*     */ import net.minecraft.util.datafix.walkers.ItemStackData;
/*     */ import net.minecraft.util.math.BlockPos;
/*     */ import net.minecraft.util.math.MathHelper;
/*     */ import net.minecraft.util.text.translation.I18n;
/*     */ import net.minecraft.world.World;
/*     */ import net.minecraftforge.event.ForgeEventFactory;
/*     */ import net.minecraftforge.fml.common.FMLCommonHandler;
/*     */ import net.minecraftforge.fml.relauncher.Side;
/*     */ import net.minecraftforge.fml.relauncher.SideOnly;
/*     */ import org.apache.logging.log4j.LogManager;
/*     */ import org.apache.logging.log4j.Logger;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class EntityItemNew
/*     */   extends Entity
/*     */ {
/*  41 */   private static final Logger LOGGER = LogManager.getLogger();
/*  42 */   private static final DataParameter<ItemStack> ITEM = EntityDataManager.func_187226_a(EntityItemNew.class, DataSerializers.field_187196_f);
/*     */   
/*     */   public float hoverStart;
/*     */   
/*     */   public int lifespan;
/*     */   private int age;
/*     */   private int delayBeforeCanPickup;
/*     */   private int health;
/*     */   private String thrower;
/*     */   private String owner;
/*     */   
/*     */   public EntityItemNew(World worldIn, double x, double y, double z) {
/*  54 */     super(worldIn);
/*  55 */     this.lifespan = 6000;
/*  56 */     this.health = 5;
/*  57 */     this.hoverStart = (float)(Math.random() * Math.PI * 2.0D);
/*  58 */     func_70105_a(0.65F, 0.65F);
/*  59 */     func_70107_b(x, y, z);
/*  60 */     this.field_70177_z = (float)(Math.random() * 360.0D);
/*  61 */     this.field_70159_w = ((float)(Math.random() * 0.20000000298023224D - 0.10000000149011612D) / 10.0F);
/*  62 */     this.field_70181_x = 0.020000000298023225D;
/*  63 */     this.field_70179_y = ((float)(Math.random() * 0.20000000298023224D - 0.10000000149011612D) / 10.0F);
/*     */   }
/*     */   
/*     */   public EntityItemNew(World worldIn, double x, double y, double z, ItemStack stack) {
/*  67 */     this(worldIn, x, y, z);
/*  68 */     setItem(stack);
/*  69 */     this.lifespan = (stack.func_77973_b() == null) ? 6000 : stack.func_77973_b().getEntityLifespan(stack, worldIn);
/*     */   }
/*     */   
/*     */   public EntityItemNew(World worldIn) {
/*  73 */     super(worldIn);
/*  74 */     this.lifespan = 6000;
/*  75 */     this.health = 5;
/*  76 */     this.hoverStart = (float)(Math.random() * Math.PI * 2.0D);
/*  77 */     func_70105_a(0.25F, 0.25F);
/*  78 */     setItem(ItemStack.field_190927_a);
/*     */   }
/*     */   
/*     */   public static void registerFixesItem(DataFixer fixer) {
/*  82 */     fixer.func_188258_a(FixTypes.ENTITY, (IDataWalker)new ItemStackData(EntityItemNew.class, new String[] { "Item" }));
/*     */   }
/*     */   
/*     */   protected boolean func_70041_e_() {
/*  86 */     return false;
/*     */   }
/*     */   
/*     */   protected void func_70088_a() {
/*  90 */     func_184212_Q().func_187214_a(ITEM, ItemStack.field_190927_a);
/*     */   }
/*     */   
/*     */   public void func_70071_h_() {
/*  94 */     if (getItem().func_77973_b().onEntityItemUpdate(new EntityItem(this.field_70170_p, this.field_70165_t, this.field_70163_u, this.field_70161_v, getItem()))) {
/*     */       return;
/*     */     }
/*  97 */     if (getItem().func_190926_b()) {
/*  98 */       func_70106_y();
/*     */     } else {
/* 100 */       super.func_70071_h_();
/* 101 */       if (this.delayBeforeCanPickup > 0 && this.delayBeforeCanPickup != 32767) {
/* 102 */         this.delayBeforeCanPickup--;
/*     */       }
/* 104 */       this.field_70169_q = this.field_70165_t;
/* 105 */       this.field_70167_r = this.field_70163_u;
/* 106 */       this.field_70166_s = this.field_70161_v;
/* 107 */       double d0 = this.field_70159_w;
/* 108 */       double d2 = this.field_70181_x;
/* 109 */       double d3 = this.field_70179_y;
/* 110 */       if (!func_189652_ae()) {
/* 111 */         this.field_70181_x -= 0.03999999910593033D;
/*     */       }
/* 113 */       if (this.field_70170_p.field_72995_K) {
/* 114 */         this.field_70145_X = false;
/*     */       } else {
/* 116 */         this.field_70145_X = func_145771_j(this.field_70165_t, ((func_174813_aQ()).field_72338_b + (func_174813_aQ()).field_72337_e) / 2.0D, this.field_70161_v);
/*     */       } 
/* 118 */       func_70091_d(MoverType.SELF, this.field_70159_w, this.field_70181_x, this.field_70179_y);
/* 119 */       boolean flag = ((int)this.field_70169_q != (int)this.field_70165_t || (int)this.field_70167_r != (int)this.field_70163_u || (int)this.field_70166_s != (int)this.field_70161_v);
/* 120 */       if (flag || this.field_70173_aa % 25 == 0) {
/* 121 */         if (this.field_70170_p.func_180495_p(new BlockPos(this)).func_185904_a() == Material.field_151587_i) {
/* 122 */           this.field_70181_x = 0.20000000298023224D;
/* 123 */           this.field_70159_w = ((this.field_70146_Z.nextFloat() - this.field_70146_Z.nextFloat()) * 0.2F);
/* 124 */           this.field_70179_y = ((this.field_70146_Z.nextFloat() - this.field_70146_Z.nextFloat()) * 0.2F);
/* 125 */           func_184185_a(SoundEvents.field_187658_bx, 0.4F, 2.0F + this.field_70146_Z.nextFloat() * 0.4F);
/*     */         } 
/* 127 */         if (!this.field_70170_p.field_72995_K) {
/* 128 */           searchForOtherItemsNearby();
/*     */         }
/*     */       } 
/* 131 */       float f = 0.98F;
/* 132 */       if (this.field_70122_E) {
/* 133 */         f = (this.field_70170_p.func_180495_p(new BlockPos(MathHelper.func_76128_c(this.field_70165_t), MathHelper.func_76128_c((func_174813_aQ()).field_72338_b) - 1, MathHelper.func_76128_c(this.field_70161_v))).func_177230_c()).field_149765_K * 0.98F;
/*     */       }
/* 135 */       this.field_70159_w *= f;
/* 136 */       this.field_70181_x *= 0.9800000190734863D;
/* 137 */       this.field_70179_y *= f;
/* 138 */       if (this.field_70122_E) {
/* 139 */         this.field_70181_x *= -0.5D;
/*     */       }
/* 141 */       if (this.age != -32768) {
/* 142 */         this.age++;
/*     */       }
/* 144 */       func_70072_I();
/* 145 */       if (!this.field_70170_p.field_72995_K) {
/* 146 */         double d4 = this.field_70159_w - d0;
/* 147 */         double d5 = this.field_70181_x - d2;
/* 148 */         double d6 = this.field_70179_y - d3;
/* 149 */         double d7 = d4 * d4 + d5 * d5 + d6 * d6;
/* 150 */         if (d7 > 0.01D) {
/* 151 */           this.field_70160_al = true;
/*     */         }
/*     */       } 
/* 154 */       ItemStack item = getItem();
/* 155 */       if (this.field_70170_p.field_72995_K || this.age >= this.lifespan);
/*     */       
/* 157 */       if (item.func_190926_b()) {
/* 158 */         func_70106_y();
/*     */       }
/*     */     } 
/*     */   }
/*     */   
/*     */   private void searchForOtherItemsNearby() {
/* 164 */     for (EntityItemNew entityitem : this.field_70170_p.func_72872_a(EntityItemNew.class, func_174813_aQ().func_72314_b(0.5D, 0.0D, 0.5D))) {
/* 165 */       combineItems(entityitem);
/*     */     }
/*     */   }
/*     */   
/*     */   private boolean combineItems(EntityItemNew other) {
/* 170 */     if (other == this) {
/* 171 */       return false;
/*     */     }
/* 173 */     if (!other.func_70089_S() || !func_70089_S()) {
/* 174 */       return false;
/*     */     }
/* 176 */     ItemStack itemstack = getItem();
/* 177 */     ItemStack itemstack2 = other.getItem();
/* 178 */     if (this.delayBeforeCanPickup == 32767 || other.delayBeforeCanPickup == 32767) {
/* 179 */       return false;
/*     */     }
/* 181 */     if (this.age == -32768 || other.age == -32768) {
/* 182 */       return false;
/*     */     }
/* 184 */     if (itemstack2.func_77973_b() != itemstack.func_77973_b()) {
/* 185 */       return false;
/*     */     }
/* 187 */     if ((itemstack2.func_77942_o() ^ itemstack.func_77942_o()) != 0) {
/* 188 */       return false;
/*     */     }
/* 190 */     if (itemstack2.func_77942_o() && !itemstack2.func_77978_p().equals(itemstack.func_77978_p())) {
/* 191 */       return false;
/*     */     }
/* 193 */     if (itemstack2.func_77973_b() == null) {
/* 194 */       return false;
/*     */     }
/* 196 */     if (itemstack2.func_77973_b().func_77614_k() && itemstack2.func_77960_j() != itemstack.func_77960_j()) {
/* 197 */       return false;
/*     */     }
/* 199 */     if (itemstack2.func_190916_E() < itemstack.func_190916_E()) {
/* 200 */       return other.combineItems(this);
/*     */     }
/* 202 */     if (itemstack2.func_190916_E() + itemstack.func_190916_E() > itemstack2.func_77976_d()) {
/* 203 */       return false;
/*     */     }
/* 205 */     if (!itemstack.areCapsCompatible(itemstack2)) {
/* 206 */       return false;
/*     */     }
/* 208 */     itemstack2.func_190917_f(itemstack.func_190916_E());
/* 209 */     other.delayBeforeCanPickup = Math.max(other.delayBeforeCanPickup, this.delayBeforeCanPickup);
/* 210 */     other.age = Math.min(other.age, this.age);
/* 211 */     other.setItem(itemstack2);
/* 212 */     func_70106_y();
/* 213 */     return true;
/*     */   }
/*     */   
/*     */   public void setAgeToCreativeDespawnTime() {
/* 217 */     this.age = 4800;
/*     */   }
/*     */   
/*     */   public boolean func_70072_I() {
/* 221 */     if (this.field_70170_p.func_72918_a(func_174813_aQ(), Material.field_151586_h, this)) {
/* 222 */       if (!this.field_70171_ac && !this.field_70148_d) {
/* 223 */         func_71061_d_();
/*     */       }
/* 225 */       this.field_70171_ac = true;
/*     */     } else {
/* 227 */       this.field_70171_ac = false;
/*     */     } 
/* 229 */     return this.field_70171_ac;
/*     */   }
/*     */   
/*     */   protected void func_70081_e(int amount) {
/* 233 */     func_70097_a(DamageSource.field_76372_a, amount);
/*     */   }
/*     */   
/*     */   public boolean func_70097_a(DamageSource source, float amount) {
/* 237 */     if (this.field_70170_p.field_72995_K || this.field_70128_L) {
/* 238 */       return false;
/*     */     }
/* 240 */     if (func_180431_b(source)) {
/* 241 */       return false;
/*     */     }
/* 243 */     if (!getItem().func_190926_b() && getItem().func_77973_b() == Items.field_151156_bN && source.func_94541_c()) {
/* 244 */       return false;
/*     */     }
/* 246 */     func_70018_K();
/* 247 */     this.health -= (int)amount;
/* 248 */     if (this.health <= 0) {
/* 249 */       func_70106_y();
/*     */     }
/* 251 */     return false;
/*     */   }
/*     */   
/*     */   public void func_70014_b(NBTTagCompound compound) {
/* 255 */     compound.func_74777_a("Health", (short)this.health);
/* 256 */     compound.func_74777_a("Age", (short)this.age);
/* 257 */     compound.func_74777_a("PickupDelay", (short)this.delayBeforeCanPickup);
/* 258 */     compound.func_74768_a("Lifespan", this.lifespan);
/* 259 */     if (getThrower() != null) {
/* 260 */       compound.func_74778_a("Thrower", this.thrower);
/*     */     }
/* 262 */     if (getOwner() != null) {
/* 263 */       compound.func_74778_a("Owner", this.owner);
/*     */     }
/* 265 */     if (!getItem().func_190926_b()) {
/* 266 */       compound.func_74782_a("Item", (NBTBase)getItem().func_77955_b(new NBTTagCompound()));
/*     */     }
/*     */   }
/*     */   
/*     */   public void func_70037_a(NBTTagCompound compound) {
/* 271 */     this.health = compound.func_74765_d("Health");
/* 272 */     this.age = compound.func_74765_d("Age");
/* 273 */     if (compound.func_74764_b("PickupDelay")) {
/* 274 */       this.delayBeforeCanPickup = compound.func_74765_d("PickupDelay");
/*     */     }
/* 276 */     if (compound.func_74764_b("Owner")) {
/* 277 */       this.owner = compound.func_74779_i("Owner");
/*     */     }
/* 279 */     if (compound.func_74764_b("Thrower")) {
/* 280 */       this.thrower = compound.func_74779_i("Thrower");
/*     */     }
/* 282 */     NBTTagCompound nbttagcompound = compound.func_74775_l("Item");
/* 283 */     setItem(new ItemStack(nbttagcompound));
/* 284 */     if (getItem().func_190926_b()) {
/* 285 */       func_70106_y();
/*     */     }
/* 287 */     if (compound.func_74764_b("Lifespan")) {
/* 288 */       this.lifespan = compound.func_74762_e("Lifespan");
/*     */     }
/*     */   }
/*     */   
/*     */   public void func_70100_b_(EntityPlayer entityIn) {
/* 293 */     if (!this.field_70170_p.field_72995_K) {
/* 294 */       if (this.delayBeforeCanPickup > 0) {
/*     */         return;
/*     */       }
/* 297 */       ItemStack itemstack = getItem();
/* 298 */       Item item = itemstack.func_77973_b();
/* 299 */       int i = itemstack.func_190916_E();
/* 300 */       int hook = ForgeEventFactory.onItemPickup(new EntityItem(this.field_70170_p, this.field_70165_t, this.field_70163_u, this.field_70161_v, getItem()), entityIn);
/* 301 */       if (hook < 0) {
/*     */         return;
/*     */       }
/* 304 */       if (this.delayBeforeCanPickup <= 0 && (this.owner == null || this.lifespan - this.age <= 200 || this.owner.equals(entityIn.func_70005_c_())) && (hook == 1 || i <= 0 || entityIn.field_71071_by.func_70441_a(itemstack))) {
/* 305 */         FMLCommonHandler.instance().firePlayerItemPickupEvent(entityIn, new EntityItem(this.field_70170_p, this.field_70165_t, this.field_70163_u, this.field_70161_v, getItem()), getItem());
/* 306 */         entityIn.func_71001_a((Entity)new EntityItem(this.field_70170_p, this.field_70165_t, this.field_70163_u, this.field_70161_v, getItem()), i);
/* 307 */         if (itemstack.func_190926_b()) {
/* 308 */           func_70106_y();
/* 309 */           itemstack.func_190920_e(i);
/*     */         } 
/* 311 */         entityIn.func_71064_a(StatList.func_188056_d(item), i);
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   public String func_70005_c_() {
/* 317 */     return func_145818_k_() ? func_95999_t() : I18n.func_74838_a("item." + getItem().func_77977_a());
/*     */   }
/*     */   
/*     */   public boolean func_70075_an() {
/* 321 */     return false;
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   public Entity func_184204_a(int dimensionIn) {
/* 326 */     Entity entity = super.func_184204_a(dimensionIn);
/* 327 */     if (!this.field_70170_p.field_72995_K && entity instanceof EntityItemNew) {
/* 328 */       ((EntityItemNew)entity).searchForOtherItemsNearby();
/*     */     }
/* 330 */     return entity;
/*     */   }
/*     */   
/*     */   public ItemStack getItem() {
/* 334 */     return (ItemStack)func_184212_Q().func_187225_a(ITEM);
/*     */   }
/*     */   
/*     */   public void setItem(ItemStack stack) {
/* 338 */     func_184212_Q().func_187227_b(ITEM, stack);
/* 339 */     func_184212_Q().func_187217_b(ITEM);
/*     */   }
/*     */   
/*     */   public String getOwner() {
/* 343 */     return this.owner;
/*     */   }
/*     */   
/*     */   public void setOwner(String owner) {
/* 347 */     this.owner = owner;
/*     */   }
/*     */   
/*     */   public String getThrower() {
/* 351 */     return this.thrower;
/*     */   }
/*     */   
/*     */   public void setThrower(String thrower) {
/* 355 */     this.thrower = thrower;
/*     */   }
/*     */   
/*     */   @SideOnly(Side.CLIENT)
/*     */   public int getAge() {
/* 360 */     return this.age;
/*     */   }
/*     */   
/*     */   public void setDefaultPickupDelay() {
/* 364 */     this.delayBeforeCanPickup = 10;
/*     */   }
/*     */   
/*     */   public void setNoPickupDelay() {
/* 368 */     this.delayBeforeCanPickup = 0;
/*     */   }
/*     */   
/*     */   public void setInfinitePickupDelay() {
/* 372 */     this.delayBeforeCanPickup = 32767;
/*     */   }
/*     */   
/*     */   public void setPickupDelay(int ticks) {
/* 376 */     this.delayBeforeCanPickup = ticks;
/*     */   }
/*     */   
/*     */   public boolean cannotPickup() {
/* 380 */     return (this.delayBeforeCanPickup > 0);
/*     */   }
/*     */   
/*     */   public void setNoDespawn() {
/* 384 */     this.age = -6000;
/*     */   }
/*     */   
/*     */   public void makeFakeItem() {
/* 388 */     setInfinitePickupDelay();
/* 389 */     this.age = getItem().func_77973_b().getEntityLifespan(getItem(), this.field_70170_p) - 1;
/*     */   }
/*     */ }


/* Location:              C:\Users\DuduM\OneDrive\Área de Trabalho\mw10\modularwarfare-shining-2023.2.4.4f-fix10.jar!\com\modularwarfare\common\entity\item\EntityItemNew.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */