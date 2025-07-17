/*     */ package com.modularwarfare.common.guns;
/*     */ 
/*     */ import com.google.common.collect.Multimap;
/*     */ import com.modularwarfare.ModularWarfare;
/*     */ import com.modularwarfare.client.handler.ClientTickHandler;
/*     */ import com.modularwarfare.common.entity.decals.EntityDecal;
/*     */ import com.modularwarfare.common.handler.ServerTickHandler;
/*     */ import com.modularwarfare.common.network.PacketBase;
/*     */ import com.modularwarfare.common.network.PacketDecal;
/*     */ import com.modularwarfare.common.type.BaseItem;
/*     */ import com.modularwarfare.common.type.BaseType;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.UUID;
/*     */ import java.util.function.Function;
/*     */ import javax.annotation.Nullable;
/*     */ import net.minecraft.block.material.Material;
/*     */ import net.minecraft.block.state.IBlockState;
/*     */ import net.minecraft.client.util.ITooltipFlag;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.entity.EntityLivingBase;
/*     */ import net.minecraft.entity.SharedMonsterAttributes;
/*     */ import net.minecraft.entity.ai.attributes.AttributeModifier;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.inventory.EntityEquipmentSlot;
/*     */ import net.minecraft.item.EnumAction;
/*     */ import net.minecraft.item.Item;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.nbt.NBTBase;
/*     */ import net.minecraft.nbt.NBTTagCompound;
/*     */ import net.minecraft.util.EnumFacing;
/*     */ import net.minecraft.util.math.BlockPos;
/*     */ import net.minecraft.util.math.RayTraceResult;
/*     */ import net.minecraft.util.math.Vec3d;
/*     */ import net.minecraft.world.IBlockAccess;
/*     */ import net.minecraft.world.World;
/*     */ import net.minecraftforge.fml.relauncher.Side;
/*     */ import net.minecraftforge.fml.relauncher.SideOnly;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class ItemGun
/*     */   extends BaseItem
/*     */ {
/*     */   public static final Function<GunType, ItemGun> factory;
/*     */   
/*     */   static {
/*  51 */     factory = (type -> new ItemGun(type));
/*     */   }
/*     */   
/*  54 */   protected static final UUID MOVEMENT_SPEED_MODIFIER = UUID.fromString("99999999-4180-4865-B01B-BCCE9785ACA3");
/*     */   public static boolean canDryFire = true;
/*     */   public static boolean fireButtonHeld = false;
/*     */   public static boolean lastFireButtonHeld = false;
/*     */   public GunType type;
/*     */   
/*     */   public ItemGun(GunType type) {
/*  61 */     super(type);
/*  62 */     this.type = type;
/*  63 */     setNoRepair();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static boolean isOnShootCooldown(UUID uuid) {
/*  72 */     return ClientTickHandler.playerShootCooldown.containsKey(uuid);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static boolean isClientReloading(EntityPlayer entityPlayer) {
/*  82 */     return ClientTickHandler.playerReloadCooldown.containsKey(entityPlayer.func_110124_au());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static boolean isServerReloading(EntityPlayer entityPlayer) {
/*  92 */     return ServerTickHandler.playerReloadCooldown.containsKey(entityPlayer.func_110124_au());
/*     */   }
/*     */   
/*     */   public static boolean hasAmmoLoaded(ItemStack gunStack) {
/*  96 */     return !gunStack.func_190926_b() ? (!(gunStack.func_77973_b() instanceof net.minecraft.item.ItemAir) ? (gunStack.func_77942_o() ? (gunStack.func_77978_p().func_74764_b("ammo") ? ((gunStack.func_77978_p().func_74781_a("ammo") != null)) : false) : false) : false) : false;
/*     */   }
/*     */   
/*     */   public static int getMagazineBullets(ItemStack gunStack) {
/* 100 */     if (hasAmmoLoaded(gunStack)) {
/* 101 */       ItemStack ammoStack = new ItemStack(gunStack.func_77978_p().func_74775_l("ammo"));
/* 102 */       if (ammoStack.func_77973_b() instanceof ItemAmmo) {
/* 103 */         ItemAmmo itemAmmo = (ItemAmmo)ammoStack.func_77973_b();
/* 104 */         if (ammoStack.func_77978_p() != null) {
/* 105 */           String key = (itemAmmo.type.magazineCount > 1) ? ("ammocount" + ammoStack.func_77978_p().func_74762_e("magcount")) : "ammocount";
/* 106 */           int ammoCount = ammoStack.func_77978_p().func_74762_e(key);
/* 107 */           return ammoCount;
/*     */         } 
/*     */       } 
/*     */     } 
/* 111 */     return 0;
/*     */   }
/*     */   
/*     */   public static boolean hasNextShot(ItemStack gunStack) {
/* 115 */     if (hasAmmoLoaded(gunStack)) {
/* 116 */       ItemStack ammoStack = new ItemStack(gunStack.func_77978_p().func_74775_l("ammo"));
/* 117 */       if (ammoStack != null && 
/* 118 */         ammoStack.func_77973_b() instanceof ItemAmmo) {
/* 119 */         ItemAmmo itemAmmo = (ItemAmmo)ammoStack.func_77973_b();
/* 120 */         if (ammoStack.func_77978_p() != null) {
/* 121 */           String key = (itemAmmo.type.magazineCount > 1) ? ("ammocount" + ammoStack.func_77978_p().func_74762_e("magcount")) : "ammocount";
/* 122 */           int ammoCount = ammoStack.func_77978_p().func_74762_e(key) - 1;
/* 123 */           return (ammoCount >= 0);
/*     */         }
/*     */       
/*     */       } 
/* 127 */     } else if (gunStack.func_77978_p() != null && gunStack.func_77978_p().func_74764_b("ammocount")) {
/* 128 */       return (gunStack.func_77978_p().func_74762_e("ammocount") > 0);
/*     */     } 
/* 130 */     return false;
/*     */   }
/*     */   
/*     */   public static void consumeShot(ItemStack gunStack) {
/* 134 */     if (hasAmmoLoaded(gunStack)) {
/* 135 */       ItemStack ammoStack = new ItemStack(gunStack.func_77978_p().func_74775_l("ammo"));
/* 136 */       ItemAmmo itemAmmo = (ItemAmmo)ammoStack.func_77973_b();
/* 137 */       if (ammoStack.func_77978_p() != null) {
/* 138 */         NBTTagCompound nbtTagCompound = ammoStack.func_77978_p();
/* 139 */         String key = (itemAmmo.type.magazineCount > 1) ? ("ammocount" + nbtTagCompound.func_74762_e("magcount")) : "ammocount";
/* 140 */         nbtTagCompound.func_74768_a(key, nbtTagCompound.func_74762_e(key) - 1);
/* 141 */         gunStack.func_77978_p().func_74782_a("ammo", (NBTBase)ammoStack.func_77955_b(new NBTTagCompound()));
/*     */       } 
/* 143 */     } else if (gunStack.func_77978_p() != null && gunStack.func_77978_p().func_74764_b("ammocount")) {
/* 144 */       int ammoCount = gunStack.func_77978_p().func_74762_e("ammocount");
/* 145 */       gunStack.func_77978_p().func_74768_a("ammocount", ammoCount - 1);
/*     */     } 
/*     */   }
/*     */   
/*     */   public static ItemBullet getUsedBullet(ItemStack gunStack, GunType gunType) {
/* 150 */     if (gunType.acceptedAmmo != null)
/* 151 */       return ItemAmmo.getUsedBullet(gunStack); 
/* 152 */     if (gunType.acceptedBullets != null && 
/* 153 */       gunStack.func_77942_o() && gunStack.func_77978_p().func_74764_b("bullet")) {
/* 154 */       ItemStack usedBullet = new ItemStack(gunStack.func_77978_p().func_74775_l("bullet"));
/* 155 */       ItemBullet usedBulletItem = (ItemBullet)usedBullet.func_77973_b();
/* 156 */       return usedBulletItem;
/*     */     } 
/*     */     
/* 159 */     return null;
/*     */   }
/*     */   
/*     */   public static boolean isIndoors(EntityLivingBase givenEntity) {
/* 163 */     BlockPos blockPos = givenEntity.field_70170_p.func_175725_q(givenEntity.func_180425_c());
/* 164 */     if (blockPos != null) {
/* 165 */       if (blockPos.func_177956_o() > givenEntity.field_70163_u) {
/* 166 */         return true;
/*     */       }
/* 168 */       return false;
/*     */     } 
/*     */     
/* 171 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setType(BaseType type) {
/* 176 */     this.type = (GunType)type;
/*     */   }
/*     */ 
/*     */   
/*     */   public void func_77663_a(ItemStack unused, World world, Entity holdingEntity, int intI, boolean flag) {
/* 181 */     if (holdingEntity instanceof EntityPlayer) {
/* 182 */       EntityPlayer entityPlayer = (EntityPlayer)holdingEntity;
/*     */       
/* 184 */       if (entityPlayer.func_184614_ca() != null && entityPlayer.func_184614_ca().func_77973_b() instanceof ItemGun) {
/* 185 */         ItemStack heldStack = entityPlayer.func_184614_ca();
/* 186 */         ItemGun itemGun = (ItemGun)heldStack.func_77973_b();
/* 187 */         GunType gunType = itemGun.type;
/*     */         
/* 189 */         if (world.field_72995_K) {
/* 190 */           onUpdateClient(entityPlayer, world, heldStack, itemGun, gunType);
/*     */         } else {
/* 192 */           onUpdateServer(entityPlayer, world, heldStack, itemGun, gunType);
/*     */         } 
/* 194 */         if (heldStack.func_77978_p() == null) {
/* 195 */           NBTTagCompound nbtTagCompound = new NBTTagCompound();
/* 196 */           nbtTagCompound.func_74778_a("firemode", gunType.fireModes[0].name().toLowerCase());
/* 197 */           nbtTagCompound.func_74768_a("skinId", 0);
/* 198 */           nbtTagCompound.func_74757_a("punched", gunType.isEnergyGun);
/* 199 */           heldStack.func_77982_d(nbtTagCompound);
/* 200 */           if (gunType.defaultAttachments != null) {
/* 201 */             for (Map.Entry<AttachmentPresetEnum, String> e : gunType.defaultAttachments.entrySet()) {
/* 202 */               GunType.addAttachment(heldStack, e.getKey(), new ItemStack((Item)ModularWarfare.attachmentTypes
/* 203 */                     .get(e.getValue())));
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
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void onUpdateClient(EntityPlayer entityPlayer, World world, ItemStack heldStack, ItemGun itemGun, GunType gunType) {
/*     */     // Byte code:
/*     */     //   0: aload_1
/*     */     //   1: invokevirtual func_184614_ca : ()Lnet/minecraft/item/ItemStack;
/*     */     //   4: ifnull -> 270
/*     */     //   7: aload_1
/*     */     //   8: invokevirtual func_184614_ca : ()Lnet/minecraft/item/ItemStack;
/*     */     //   11: invokevirtual func_77973_b : ()Lnet/minecraft/item/Item;
/*     */     //   14: instanceof com/modularwarfare/common/guns/ItemGun
/*     */     //   17: ifeq -> 270
/*     */     //   20: getstatic com/modularwarfare/client/fpp/basic/renderers/RenderParameters.GUN_CHANGE_Y : F
/*     */     //   23: fconst_0
/*     */     //   24: fcmpl
/*     */     //   25: ifne -> 270
/*     */     //   28: getstatic com/modularwarfare/client/fpp/basic/renderers/RenderParameters.collideFrontDistance : F
/*     */     //   31: ldc_w 0.2
/*     */     //   34: fcmpg
/*     */     //   35: ifgt -> 270
/*     */     //   38: getstatic com/modularwarfare/common/guns/ItemGun.fireButtonHeld : Z
/*     */     //   41: ifeq -> 84
/*     */     //   44: invokestatic func_71410_x : ()Lnet/minecraft/client/Minecraft;
/*     */     //   47: getfield field_71415_G : Z
/*     */     //   50: ifeq -> 84
/*     */     //   53: aload #5
/*     */     //   55: pop
/*     */     //   56: aload_3
/*     */     //   57: invokestatic getFireMode : (Lnet/minecraft/item/ItemStack;)Lcom/modularwarfare/common/guns/WeaponFireMode;
/*     */     //   60: getstatic com/modularwarfare/common/guns/WeaponFireMode.FULL : Lcom/modularwarfare/common/guns/WeaponFireMode;
/*     */     //   63: if_acmpne -> 84
/*     */     //   66: aload_1
/*     */     //   67: aload_2
/*     */     //   68: aload_3
/*     */     //   69: aload #4
/*     */     //   71: aload #5
/*     */     //   73: pop
/*     */     //   74: aload_3
/*     */     //   75: invokestatic getFireMode : (Lnet/minecraft/item/ItemStack;)Lcom/modularwarfare/common/guns/WeaponFireMode;
/*     */     //   78: invokestatic fireClient : (Lnet/minecraft/entity/player/EntityPlayer;Lnet/minecraft/world/World;Lnet/minecraft/item/ItemStack;Lcom/modularwarfare/common/guns/ItemGun;Lcom/modularwarfare/common/guns/WeaponFireMode;)V
/*     */     //   81: goto -> 254
/*     */     //   84: getstatic com/modularwarfare/common/guns/ItemGun.fireButtonHeld : Z
/*     */     //   87: getstatic com/modularwarfare/common/guns/ItemGun.lastFireButtonHeld : Z
/*     */     //   90: ifne -> 97
/*     */     //   93: iconst_1
/*     */     //   94: goto -> 98
/*     */     //   97: iconst_0
/*     */     //   98: iand
/*     */     //   99: ifeq -> 142
/*     */     //   102: invokestatic func_71410_x : ()Lnet/minecraft/client/Minecraft;
/*     */     //   105: getfield field_71415_G : Z
/*     */     //   108: ifeq -> 142
/*     */     //   111: aload #5
/*     */     //   113: pop
/*     */     //   114: aload_3
/*     */     //   115: invokestatic getFireMode : (Lnet/minecraft/item/ItemStack;)Lcom/modularwarfare/common/guns/WeaponFireMode;
/*     */     //   118: getstatic com/modularwarfare/common/guns/WeaponFireMode.SEMI : Lcom/modularwarfare/common/guns/WeaponFireMode;
/*     */     //   121: if_acmpne -> 142
/*     */     //   124: aload_1
/*     */     //   125: aload_2
/*     */     //   126: aload_3
/*     */     //   127: aload #4
/*     */     //   129: aload #5
/*     */     //   131: pop
/*     */     //   132: aload_3
/*     */     //   133: invokestatic getFireMode : (Lnet/minecraft/item/ItemStack;)Lcom/modularwarfare/common/guns/WeaponFireMode;
/*     */     //   136: invokestatic fireClient : (Lnet/minecraft/entity/player/EntityPlayer;Lnet/minecraft/world/World;Lnet/minecraft/item/ItemStack;Lcom/modularwarfare/common/guns/ItemGun;Lcom/modularwarfare/common/guns/WeaponFireMode;)V
/*     */     //   139: goto -> 254
/*     */     //   142: aload #5
/*     */     //   144: pop
/*     */     //   145: aload_3
/*     */     //   146: invokestatic getFireMode : (Lnet/minecraft/item/ItemStack;)Lcom/modularwarfare/common/guns/WeaponFireMode;
/*     */     //   149: getstatic com/modularwarfare/common/guns/WeaponFireMode.BURST : Lcom/modularwarfare/common/guns/WeaponFireMode;
/*     */     //   152: if_acmpne -> 254
/*     */     //   155: aload_3
/*     */     //   156: invokevirtual func_77978_p : ()Lnet/minecraft/nbt/NBTTagCompound;
/*     */     //   159: astore #6
/*     */     //   161: iconst_1
/*     */     //   162: istore #7
/*     */     //   164: aload #6
/*     */     //   166: ldc_w 'shotsremaining'
/*     */     //   169: invokevirtual func_74764_b : (Ljava/lang/String;)Z
/*     */     //   172: ifeq -> 207
/*     */     //   175: aload #6
/*     */     //   177: ldc_w 'shotsremaining'
/*     */     //   180: invokevirtual func_74762_e : (Ljava/lang/String;)I
/*     */     //   183: ifle -> 207
/*     */     //   186: aload_1
/*     */     //   187: aload_2
/*     */     //   188: aload_3
/*     */     //   189: aload #4
/*     */     //   191: aload #5
/*     */     //   193: pop
/*     */     //   194: aload_3
/*     */     //   195: invokestatic getFireMode : (Lnet/minecraft/item/ItemStack;)Lcom/modularwarfare/common/guns/WeaponFireMode;
/*     */     //   198: invokestatic fireClient : (Lnet/minecraft/entity/player/EntityPlayer;Lnet/minecraft/world/World;Lnet/minecraft/item/ItemStack;Lcom/modularwarfare/common/guns/ItemGun;Lcom/modularwarfare/common/guns/WeaponFireMode;)V
/*     */     //   201: iconst_0
/*     */     //   202: istore #7
/*     */     //   204: goto -> 254
/*     */     //   207: getstatic com/modularwarfare/common/guns/ItemGun.fireButtonHeld : Z
/*     */     //   210: getstatic com/modularwarfare/common/guns/ItemGun.lastFireButtonHeld : Z
/*     */     //   213: ifne -> 220
/*     */     //   216: iconst_1
/*     */     //   217: goto -> 221
/*     */     //   220: iconst_0
/*     */     //   221: iand
/*     */     //   222: ifeq -> 254
/*     */     //   225: invokestatic func_71410_x : ()Lnet/minecraft/client/Minecraft;
/*     */     //   228: getfield field_71415_G : Z
/*     */     //   231: ifeq -> 254
/*     */     //   234: iload #7
/*     */     //   236: ifeq -> 254
/*     */     //   239: aload_1
/*     */     //   240: aload_2
/*     */     //   241: aload_3
/*     */     //   242: aload #4
/*     */     //   244: aload #5
/*     */     //   246: pop
/*     */     //   247: aload_3
/*     */     //   248: invokestatic getFireMode : (Lnet/minecraft/item/ItemStack;)Lcom/modularwarfare/common/guns/WeaponFireMode;
/*     */     //   251: invokestatic fireClient : (Lnet/minecraft/entity/player/EntityPlayer;Lnet/minecraft/world/World;Lnet/minecraft/item/ItemStack;Lcom/modularwarfare/common/guns/ItemGun;Lcom/modularwarfare/common/guns/WeaponFireMode;)V
/*     */     //   254: getstatic com/modularwarfare/common/guns/ItemGun.fireButtonHeld : Z
/*     */     //   257: putstatic com/modularwarfare/common/guns/ItemGun.lastFireButtonHeld : Z
/*     */     //   260: getstatic com/modularwarfare/common/guns/ItemGun.fireButtonHeld : Z
/*     */     //   263: ifne -> 270
/*     */     //   266: iconst_1
/*     */     //   267: putstatic com/modularwarfare/common/guns/manager/ShotManager.defemptyclickLock : Z
/*     */     //   270: return
/*     */     // Line number table:
/*     */     //   Java source line number -> byte code offset
/*     */     //   #212	-> 0
/*     */     //   #213	-> 38
/*     */     //   #214	-> 66
/*     */     //   #215	-> 84
/*     */     //   #216	-> 124
/*     */     //   #217	-> 142
/*     */     //   #218	-> 155
/*     */     //   #219	-> 161
/*     */     //   #220	-> 164
/*     */     //   #221	-> 186
/*     */     //   #222	-> 201
/*     */     //   #223	-> 207
/*     */     //   #224	-> 239
/*     */     //   #227	-> 254
/*     */     //   #228	-> 260
/*     */     //   #229	-> 266
/*     */     //   #232	-> 270
/*     */     // Local variable table:
/*     */     //   start	length	slot	name	descriptor
/*     */     //   161	93	6	tagCompound	Lnet/minecraft/nbt/NBTTagCompound;
/*     */     //   164	90	7	canFire	Z
/*     */     //   0	271	0	this	Lcom/modularwarfare/common/guns/ItemGun;
/*     */     //   0	271	1	entityPlayer	Lnet/minecraft/entity/player/EntityPlayer;
/*     */     //   0	271	2	world	Lnet/minecraft/world/World;
/*     */     //   0	271	3	heldStack	Lnet/minecraft/item/ItemStack;
/*     */     //   0	271	4	itemGun	Lcom/modularwarfare/common/guns/ItemGun;
/*     */     //   0	271	5	gunType	Lcom/modularwarfare/common/guns/GunType;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void onUpdateServer(EntityPlayer entityPlayer, World world, ItemStack heldStack, ItemGun itemGun, GunType gunType) {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void playImpactSound(World world, BlockPos pos, GunType gunType) {
/* 239 */     if (world.func_180495_p(pos).func_185904_a() == Material.field_151576_e) {
/* 240 */       gunType.playSoundPos(pos, world, WeaponSoundType.ImpactStone, null, 1.0F);
/* 241 */     } else if (world.func_180495_p(pos).func_185904_a() == Material.field_151577_b || world.func_180495_p(pos).func_185904_a() == Material.field_151578_c || world.func_180495_p(pos).func_185904_a() == Material.field_151595_p) {
/* 242 */       gunType.playSoundPos(pos, world, WeaponSoundType.ImpactDirt, null, 1.0F);
/* 243 */     } else if (world.func_180495_p(pos).func_185904_a() == Material.field_151575_d) {
/* 244 */       gunType.playSoundPos(pos, world, WeaponSoundType.ImpactWood, null, 1.0F);
/* 245 */     } else if (world.func_180495_p(pos).func_185904_a() == Material.field_151592_s) {
/* 246 */       gunType.playSoundPos(pos, world, WeaponSoundType.ImpactGlass, null, 1.0F);
/* 247 */     } else if (world.func_180495_p(pos).func_185904_a() == Material.field_151586_h) {
/* 248 */       gunType.playSoundPos(pos, world, WeaponSoundType.ImpactWater, null, 1.0F);
/* 249 */     } else if (world.func_180495_p(pos).func_185904_a() == Material.field_151573_f) {
/* 250 */       gunType.playSoundPos(pos, world, WeaponSoundType.ImpactMetal, null, 1.0F);
/*     */     } else {
/* 252 */       gunType.playSoundPos(pos, world, WeaponSoundType.ImpactDirt, null, 1.0F);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public Multimap<String, AttributeModifier> getAttributeModifiers(EntityEquipmentSlot slot, ItemStack stack) {
/* 258 */     Multimap<String, AttributeModifier> multimap = super.getAttributeModifiers(slot, stack);
/* 259 */     if (slot == EntityEquipmentSlot.MAINHAND && 
/* 260 */       this.type.moveSpeedModifier - 1.0F != 0.0F) {
/* 261 */       multimap.put(SharedMonsterAttributes.field_111263_d.func_111108_a(), new AttributeModifier(MOVEMENT_SPEED_MODIFIER, "MovementSpeed", (this.type.moveSpeedModifier - 1.0F), 2));
/*     */     }
/*     */     
/* 264 */     return multimap;
/*     */   }
/*     */   
/*     */   public static void doHit(double posX, double posY, double posZ, EnumFacing facing, EntityPlayer shooter) {
/* 268 */     doHit(new RayTraceResult(RayTraceResult.Type.BLOCK, new Vec3d(posX, posY, posZ), facing, new BlockPos(posX, posY, posZ)), shooter);
/*     */   }
/*     */   
/*     */   public static void doHit(RayTraceResult raytraceResultIn, EntityPlayer shooter) {
/* 272 */     if (raytraceResultIn.func_178782_a() != null) {
/* 273 */       BlockPos pos = raytraceResultIn.func_178782_a();
/*     */       
/* 275 */       EntityDecal.EnumDecalSide side = EntityDecal.EnumDecalSide.ALL;
/* 276 */       boolean shouldRender = true;
/* 277 */       double hitX = raytraceResultIn.field_72307_f.field_72450_a;
/* 278 */       double hitY = raytraceResultIn.field_72307_f.field_72448_b;
/* 279 */       double hitZ = raytraceResultIn.field_72307_f.field_72449_c;
/* 280 */       switch (raytraceResultIn.field_178784_b) {
/*     */         case UP:
/* 282 */           side = EntityDecal.EnumDecalSide.FLOOR;
/*     */           break;
/*     */         case DOWN:
/* 285 */           side = EntityDecal.EnumDecalSide.CEILING;
/*     */           break;
/*     */         case EAST:
/* 288 */           side = EntityDecal.EnumDecalSide.WEST;
/*     */           break;
/*     */         case WEST:
/* 291 */           side = EntityDecal.EnumDecalSide.EAST;
/*     */           break;
/*     */         case SOUTH:
/* 294 */           side = EntityDecal.EnumDecalSide.NORTH;
/*     */           break;
/*     */         case NORTH:
/* 297 */           side = EntityDecal.EnumDecalSide.SOUTH;
/*     */           break;
/*     */         default:
/* 300 */           shouldRender = false;
/*     */           break;
/*     */       } 
/* 303 */       if (shouldRender) {
/* 304 */         ModularWarfare.NETWORK.sendToAll((PacketBase)new PacketDecal(0, side, hitX, hitY, hitZ, false));
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public static boolean canEntityGetHeadshot(Entity e) {
/* 311 */     return (e instanceof net.minecraft.entity.monster.EntityZombie || e instanceof net.minecraft.entity.monster.EntitySkeleton || e instanceof net.minecraft.entity.monster.EntityCreeper || e instanceof net.minecraft.entity.monster.EntityWitch || e instanceof net.minecraft.entity.monster.EntityPigZombie || e instanceof net.minecraft.entity.monster.EntityEnderman || e instanceof net.minecraft.entity.monster.EntityWitherSkeleton || e instanceof EntityPlayer || e instanceof net.minecraft.entity.passive.EntityVillager || e instanceof net.minecraft.entity.monster.EntityEvoker || e instanceof net.minecraft.entity.monster.EntityStray || e instanceof net.minecraft.entity.monster.EntityVindicator || e instanceof net.minecraft.entity.monster.EntityIronGolem || e instanceof net.minecraft.entity.monster.EntitySnowman || e.func_70005_c_().contains("common"));
/*     */   }
/*     */   
/*     */   public void onGunSwitchMode(EntityPlayer entityPlayer, World world, ItemStack gunStack, ItemGun itemGun, WeaponFireMode fireMode) {
/* 315 */     GunType.setFireMode(gunStack, fireMode);
/*     */     
/* 317 */     GunType gunType = itemGun.type;
/* 318 */     if (WeaponSoundType.ModeSwitch != null && !world.field_72995_K) {
/* 319 */       gunType.playSound((EntityLivingBase)entityPlayer, WeaponSoundType.ModeSwitch, gunStack);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @SideOnly(Side.CLIENT)
/*     */   public void func_77624_a(ItemStack stack, @Nullable World worldIn, List<String> tooltip, ITooltipFlag flagIn) {
/* 328 */     GunType gunType = ((ItemGun)stack.func_77973_b()).type;
/* 329 */     super.func_77624_a(stack, worldIn, tooltip, flagIn);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean func_77651_p() {
/* 456 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public int func_77626_a(ItemStack p_77626_1_) {
/* 461 */     return 0;
/*     */   }
/*     */ 
/*     */   
/*     */   public EnumAction func_77661_b(ItemStack p_77661_1_) {
/* 466 */     return EnumAction.NONE;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean onEntitySwing(EntityLivingBase entityLiving, ItemStack stack) {
/* 471 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean shouldCauseReequipAnimation(ItemStack oldStack, ItemStack newStack, boolean slotChanged) {
/* 476 */     boolean result = !oldStack.equals(newStack);
/* 477 */     if (result);
/*     */ 
/*     */     
/* 480 */     return result;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean onBlockStartBreak(ItemStack itemstack, BlockPos pos, EntityPlayer player) {
/* 485 */     World world = player.field_70170_p;
/* 486 */     if (!world.field_72995_K) {
/*     */       
/* 488 */       IBlockState state = world.func_180495_p(pos);
/* 489 */       world.func_184138_a(pos, state, state, 3);
/*     */     } 
/* 491 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean doesSneakBypassUse(ItemStack stack, IBlockAccess world, BlockPos pos, EntityPlayer player) {
/* 496 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean canHarvestBlock(IBlockState state, ItemStack stack) {
/* 501 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean func_82788_x() {
/* 506 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean onLeftClickEntity(ItemStack stack, EntityPlayer player, Entity entity) {
/* 511 */     return true;
/*     */   }
/*     */ }


/* Location:              C:\Users\DuduM\OneDrive\Área de Trabalho\mw10\modularwarfare-shining-2023.2.4.4f-fix10.jar!\com\modularwarfare\common\guns\ItemGun.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */