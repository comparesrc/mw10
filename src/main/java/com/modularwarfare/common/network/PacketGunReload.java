/*     */ package com.modularwarfare.common.network;
/*     */ import com.modularwarfare.ModularWarfare;
/*     */ import com.modularwarfare.api.WeaponEnhancedReloadEvent;
/*     */ import com.modularwarfare.api.WeaponReloadEvent;
/*     */ import com.modularwarfare.client.fpp.basic.animations.ReloadType;
/*     */ import com.modularwarfare.common.guns.AmmoType;
/*     */ import com.modularwarfare.common.guns.GunType;
/*     */ import com.modularwarfare.common.guns.ItemAmmo;
/*     */ import com.modularwarfare.common.guns.ItemBullet;
/*     */ import com.modularwarfare.common.guns.ItemGun;
/*     */ import com.modularwarfare.common.guns.WeaponSoundType;
/*     */ import com.modularwarfare.common.handler.ServerTickHandler;
/*     */ import com.modularwarfare.common.handler.data.DataGunReloadEnhancedTask;
/*     */ import com.modularwarfare.utility.ReloadHelper;
/*     */ import io.netty.buffer.ByteBuf;
/*     */ import net.minecraft.entity.EntityLivingBase;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.entity.player.EntityPlayerMP;
/*     */ import net.minecraft.entity.player.InventoryPlayer;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.nbt.NBTBase;
/*     */ import net.minecraft.nbt.NBTTagCompound;
/*     */ import net.minecraftforge.common.MinecraftForge;
/*     */ import net.minecraftforge.fml.common.eventhandler.Event;
/*     */ 
/*     */ public class PacketGunReload extends PacketBase {
/*  27 */   private static final ItemStack UNLOAD_EMPTY = new ItemStack(Blocks.field_150346_d, 1);
/*     */   
/*     */   public boolean unload = false;
/*     */ 
/*     */   
/*     */   public PacketGunReload() {}
/*     */   
/*     */   public PacketGunReload(boolean unload) {
/*  35 */     this.unload = unload;
/*     */   }
/*     */ 
/*     */   
/*     */   public void encodeInto(ChannelHandlerContext ctx, ByteBuf data) {
/*  40 */     data.writeBoolean(this.unload);
/*     */   }
/*     */ 
/*     */   
/*     */   public void decodeInto(ChannelHandlerContext ctx, ByteBuf data) {
/*  45 */     this.unload = data.readBoolean();
/*     */   }
/*     */ 
/*     */   
/*     */   public void handleServerSide(EntityPlayerMP entityPlayer) {
/*  50 */     if (entityPlayer.func_175149_v()) {
/*     */       return;
/*     */     }
/*  53 */     if (entityPlayer.func_184614_ca() != null) {
/*  54 */       if (entityPlayer.func_184614_ca().func_77973_b() instanceof ItemGun) {
/*  55 */         ItemStack gunStack = entityPlayer.func_184614_ca();
/*  56 */         ItemGun itemGun = (ItemGun)entityPlayer.func_184614_ca().func_77973_b();
/*  57 */         GunType gunType = itemGun.type;
/*  58 */         InventoryPlayer inventory = entityPlayer.field_71071_by;
/*     */         
/*  60 */         if (gunType.animationType == WeaponAnimationType.ENHANCED) {
/*  61 */           if (gunType.acceptedAmmo != null) {
/*  62 */             handleMagGunReloadEnhanced(entityPlayer, gunStack, itemGun, gunType, inventory);
/*     */           } else {
/*  64 */             handleBulletGunReloadEnhanced(entityPlayer, gunStack, itemGun, gunType, inventory);
/*     */           }
/*     */         
/*  67 */         } else if (gunType.acceptedAmmo != null) {
/*  68 */           handleMagGunReload(entityPlayer, gunStack, itemGun, gunType, inventory);
/*     */         } else {
/*  70 */           handleBulletGunReload(entityPlayer, gunStack, itemGun, gunType, inventory);
/*     */         }
/*     */       
/*  73 */       } else if (entityPlayer.func_184614_ca().func_77973_b() instanceof ItemAmmo) {
/*  74 */         handleAmmoReload(entityPlayer);
/*     */       } 
/*     */     }
/*     */   }
/*     */   
/*     */   public void handleAmmoReload(EntityPlayerMP entityPlayer) {
/*  80 */     ItemStack ammoStack = entityPlayer.func_184614_ca();
/*  81 */     ItemAmmo itemAmmo = (ItemAmmo)entityPlayer.func_184614_ca().func_77973_b();
/*  82 */     AmmoType ammoType = itemAmmo.type;
/*  83 */     InventoryPlayer inventory = entityPlayer.field_71071_by;
/*     */     
/*  85 */     if (ServerTickHandler.playerReloadCooldown.containsKey(entityPlayer.func_110124_au())) {
/*     */       return;
/*     */     }
/*  88 */     if (entityPlayer.func_184614_ca().func_190916_E() > 1) {
/*  89 */       entityPlayer.func_145747_a((ITextComponent)new TextComponentString("You can only load bullets on a single magazine."));
/*     */       
/*     */       return;
/*     */     } 
/*  93 */     if (ammoType.subAmmo != null) {
/*  94 */       if (!this.unload) {
/*  95 */         NBTTagCompound nbtTagCompound = ammoStack.func_77978_p();
/*  96 */         boolean offhandedReload = false;
/*  97 */         int highestBulletCount = 0;
/*  98 */         ItemStack bulletStackToLoad = null;
/*  99 */         Integer bulletStackSlotToLoad = null;
/*     */ 
/*     */         
/* 102 */         if (inventory.field_184439_c.get(0) != ItemStack.field_190927_a) {
/* 103 */           ItemStack itemStack = (ItemStack)inventory.field_184439_c.get(0);
/* 104 */           if (itemStack != null && itemStack.func_77973_b() instanceof ItemBullet) {
/* 105 */             ItemBullet itemBullet = (ItemBullet)itemStack.func_77973_b();
/* 106 */             for (String ammoName : ammoType.subAmmo) {
/* 107 */               if (ammoName.equalsIgnoreCase(itemBullet.baseType.internalName)) {
/* 108 */                 offhandedReload = true;
/* 109 */                 bulletStackToLoad = itemStack;
/*     */                 
/*     */                 break;
/*     */               } 
/*     */             } 
/*     */           } 
/*     */         } 
/*     */         
/* 117 */         if (!offhandedReload) {
/* 118 */           for (int i = 0; i < inventory.func_70302_i_(); i++) {
/* 119 */             ItemStack itemStack = inventory.func_70301_a(i);
/* 120 */             if (itemStack != null && itemStack.func_77973_b() instanceof ItemBullet) {
/* 121 */               ItemBullet itemBullet = (ItemBullet)itemStack.func_77973_b();
/* 122 */               for (String bulletName : ammoType.subAmmo) {
/* 123 */                 if (bulletName.equalsIgnoreCase(itemBullet.baseType.internalName)) {
/* 124 */                   int count = itemStack.func_190916_E();
/* 125 */                   if (count > highestBulletCount) {
/* 126 */                     bulletStackToLoad = itemStack;
/* 127 */                     highestBulletCount = count;
/* 128 */                     bulletStackSlotToLoad = Integer.valueOf(i);
/*     */                   } 
/*     */                 } 
/*     */               } 
/*     */             } 
/*     */           } 
/*     */         }
/*     */ 
/*     */         
/* 137 */         if (bulletStackToLoad == null) {
/*     */           return;
/*     */         }
/* 140 */         ItemBullet bulletItemToLoad = (ItemBullet)bulletStackToLoad.func_77973_b();
/*     */         
/* 142 */         if (nbtTagCompound.func_74764_b("bullet")) {
/* 143 */           ItemStack currentBullet = new ItemStack(nbtTagCompound.func_74775_l("bullet"));
/* 144 */           if (!ReloadHelper.isSameTypeAmmo(currentBullet, bulletStackToLoad)) {
/* 145 */             ReloadHelper.unloadBullets(entityPlayer, ammoStack);
/*     */           }
/*     */         } 
/* 148 */         ItemStack loadingItemStack = bulletStackToLoad.func_77946_l();
/* 149 */         int reserve = bulletStackToLoad.func_190916_E();
/*     */         
/* 151 */         if (!nbtTagCompound.func_74764_b("magcount")) {
/* 152 */           int loadingCount, i = ammoStack.func_77978_p().func_74762_e("ammocount");
/* 153 */           int amountToLoad = ammoType.ammoCapacity - i;
/*     */           
/* 155 */           if (amountToLoad >= loadingItemStack.func_190916_E()) {
/* 156 */             loadingCount = loadingItemStack.func_190916_E();
/* 157 */             reserve = 0;
/*     */           } else {
/* 159 */             loadingCount = amountToLoad;
/* 160 */             reserve = loadingItemStack.func_190916_E() - loadingCount;
/*     */           } 
/* 162 */           ammoStack.func_77978_p().func_74768_a("ammocount", i + loadingCount);
/*     */         } else {
/* 164 */           for (int i = 1; i < ammoType.magazineCount + 1; i++) {
/* 165 */             int j = ammoStack.func_77978_p().func_74762_e("ammocount" + i);
/* 166 */             if (j < ammoType.ammoCapacity) {
/* 167 */               int loadingCount, amountToLoad = ammoType.ammoCapacity - j;
/*     */               
/* 169 */               if (amountToLoad >= reserve) {
/* 170 */                 loadingCount = reserve;
/* 171 */                 reserve = 0;
/*     */               } else {
/* 173 */                 loadingCount = amountToLoad;
/* 174 */                 reserve -= loadingCount;
/*     */               } 
/* 176 */               ammoStack.func_77978_p().func_74768_a("ammocount" + i, j + loadingCount);
/* 177 */               if (reserve == 0) {
/*     */                 break;
/*     */               }
/*     */             } 
/*     */           } 
/*     */         } 
/* 183 */         loadingItemStack.func_190920_e(1);
/* 184 */         loadingItemStack.func_77964_b(0);
/* 185 */         ammoStack.func_77978_p().func_74782_a("bullet", (NBTBase)loadingItemStack.func_77955_b(new NBTTagCompound()));
/* 186 */         bulletStackToLoad.func_190920_e(reserve);
/*     */         
/* 188 */         if (!entityPlayer.field_71075_bZ.field_75098_d)
/* 189 */           if (offhandedReload) {
/* 190 */             inventory.field_184439_c.set(0, (reserve >= 1) ? bulletStackToLoad : ItemStack.field_190927_a);
/*     */           } else {
/* 192 */             inventory.func_70299_a(bulletStackSlotToLoad.intValue(), (reserve >= 1) ? bulletStackToLoad : ItemStack.field_190927_a);
/*     */           }  
/*     */       } else {
/* 195 */         ReloadHelper.unloadBullets(entityPlayer, ammoStack);
/*     */       } 
/*     */       
/* 198 */       String key = (itemAmmo.type.magazineCount > 1) ? ("ammocount" + ammoStack.func_77978_p().func_74762_e("magcount")) : "ammocount";
/* 199 */       int ammoCount = ammoStack.func_77978_p().func_74762_e(key);
/* 200 */       ammoStack.func_77964_b(ammoStack.func_77958_k() - ammoCount);
/*     */     } 
/*     */   }
/*     */   
/*     */   public void handleBulletGunReload(EntityPlayerMP entityPlayer, ItemStack gunStack, ItemGun itemGun, GunType gunType, InventoryPlayer inventory) {
/* 205 */     if (ServerTickHandler.playerReloadCooldown.containsKey(entityPlayer.func_110124_au())) {
/*     */       return;
/*     */     }
/* 208 */     if (gunType.acceptedBullets != null)
/* 209 */       if (!this.unload) {
/* 210 */         int loadingCount; if (gunStack.func_77978_p() != null && 
/* 211 */           gunType.internalAmmoStorage != null && 
/* 212 */           gunStack.func_77978_p().func_74775_l("bullet") != null && 
/* 213 */           gunStack.func_77978_p().func_74762_e("ammocount") >= gunType.internalAmmoStorage.intValue()) {
/*     */           return;
/*     */         }
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 220 */         NBTTagCompound nbtTagCompound = gunStack.func_77978_p();
/* 221 */         boolean offhandedReload = false;
/* 222 */         int highestBulletCount = 0;
/* 223 */         ItemStack bulletStackToLoad = null;
/* 224 */         Integer bulletStackSlotToLoad = null;
/*     */ 
/*     */         
/* 227 */         if (inventory.field_184439_c.get(0) != ItemStack.field_190927_a) {
/* 228 */           ItemStack itemStack = (ItemStack)inventory.field_184439_c.get(0);
/* 229 */           if (itemStack != null && itemStack.func_77973_b() instanceof ItemBullet) {
/* 230 */             ItemBullet itemBullet = (ItemBullet)itemStack.func_77973_b();
/* 231 */             for (String bulletName : gunType.acceptedBullets) {
/* 232 */               if (bulletName.equalsIgnoreCase(itemBullet.baseType.internalName)) {
/* 233 */                 offhandedReload = true;
/* 234 */                 bulletStackToLoad = itemStack;
/*     */                 
/*     */                 break;
/*     */               } 
/*     */             } 
/*     */           } 
/*     */         } 
/*     */         
/* 242 */         if (!offhandedReload) {
/* 243 */           for (int i = 0; i < inventory.func_70302_i_(); i++) {
/* 244 */             ItemStack itemStack = inventory.func_70301_a(i);
/* 245 */             if (itemStack != null && itemStack.func_77973_b() instanceof ItemBullet) {
/* 246 */               ItemBullet itemBullet = (ItemBullet)itemStack.func_77973_b();
/* 247 */               for (String bulletName : gunType.acceptedBullets) {
/* 248 */                 if (bulletName.equalsIgnoreCase(itemBullet.baseType.internalName)) {
/* 249 */                   int count = itemStack.func_190916_E();
/* 250 */                   if (count > highestBulletCount) {
/* 251 */                     bulletStackToLoad = itemStack;
/* 252 */                     highestBulletCount = count;
/* 253 */                     bulletStackSlotToLoad = Integer.valueOf(i);
/*     */                   } 
/*     */                 } 
/*     */               } 
/*     */             } 
/*     */           } 
/*     */         }
/*     */ 
/*     */         
/* 262 */         if (bulletStackToLoad == null) {
/*     */           return;
/*     */         }
/* 265 */         boolean loadOnly = false;
/* 266 */         WeaponReloadEvent.Pre preReloadEvent = new WeaponReloadEvent.Pre((EntityPlayer)entityPlayer, gunStack, itemGun, offhandedReload, false);
/* 267 */         MinecraftForge.EVENT_BUS.post((Event)preReloadEvent);
/* 268 */         if (preReloadEvent.isCanceled()) {
/*     */           return;
/*     */         }
/* 271 */         ItemBullet bulletItemToLoad = (ItemBullet)bulletStackToLoad.func_77973_b();
/*     */         
/* 273 */         if (nbtTagCompound.func_74764_b("bullet")) {
/* 274 */           ItemStack currentBullet = new ItemStack(nbtTagCompound.func_74775_l("bullet"));
/* 275 */           if (!ReloadHelper.isSameTypeAmmo(currentBullet, bulletStackToLoad))
/* 276 */             ReloadHelper.unloadBullets(entityPlayer, gunStack); 
/*     */         } else {
/* 278 */           loadOnly = true;
/*     */         } 
/*     */         
/* 281 */         ItemStack loadingItemStack = bulletStackToLoad.func_77946_l();
/* 282 */         int reserve = bulletStackToLoad.func_190916_E();
/* 283 */         int ammoCount = gunStack.func_77978_p().func_74762_e("ammocount");
/* 284 */         int amountToLoad = gunType.internalAmmoStorage.intValue() - ammoCount;
/*     */         
/* 286 */         if (amountToLoad >= loadingItemStack.func_190916_E()) {
/* 287 */           loadingCount = loadingItemStack.func_190916_E();
/* 288 */           reserve = 0;
/*     */         } else {
/* 290 */           loadingCount = amountToLoad;
/* 291 */           reserve = loadingItemStack.func_190916_E() - loadingCount;
/*     */         } 
/* 293 */         gunStack.func_77978_p().func_74768_a("ammocount", ammoCount + loadingCount);
/* 294 */         gunStack.func_77978_p().func_74782_a("bullet", (NBTBase)loadingItemStack.func_77955_b(new NBTTagCompound()));
/* 295 */         bulletStackToLoad.func_190920_e(reserve);
/*     */         
/* 297 */         if (!entityPlayer.field_71075_bZ.field_75098_d) {
/* 298 */           if (offhandedReload) {
/* 299 */             inventory.field_184439_c.set(0, (reserve >= 1) ? bulletStackToLoad : ItemStack.field_190927_a);
/*     */           } else {
/* 301 */             inventory.func_70299_a(bulletStackSlotToLoad.intValue(), (reserve >= 1) ? bulletStackToLoad : ItemStack.field_190927_a);
/*     */           } 
/*     */         }
/* 304 */         WeaponReloadEvent.Post postReloadEvent = new WeaponReloadEvent.Post((EntityPlayer)entityPlayer, gunStack, itemGun, offhandedReload, loadOnly, false, preReloadEvent.getReloadTime(), loadingCount);
/* 305 */         MinecraftForge.EVENT_BUS.post((Event)postReloadEvent);
/* 306 */         ServerTickHandler.playerReloadCooldown.put(entityPlayer.func_110124_au(), Integer.valueOf(preReloadEvent.getReloadTime()));
/* 307 */         int reloadType = (postReloadEvent.isLoadOnly() ? ReloadType.Load : (postReloadEvent.isUnload() ? ReloadType.Unload : ReloadType.Full)).i;
/* 308 */         ModularWarfare.NETWORK.sendTo(new PacketClientAnimation(gunType.internalName, postReloadEvent.getReloadTime(), postReloadEvent.getReloadCount(), reloadType), entityPlayer);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/*     */       }
/*     */       else {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 327 */         WeaponReloadEvent.Pre preReloadEvent = new WeaponReloadEvent.Pre((EntityPlayer)entityPlayer, gunStack, itemGun, false, false);
/* 328 */         MinecraftForge.EVENT_BUS.post((Event)preReloadEvent);
/* 329 */         if (preReloadEvent.isCanceled()) {
/*     */           return;
/*     */         }
/* 332 */         Integer bulletCount = ReloadHelper.unloadBullets(entityPlayer, gunStack);
/* 333 */         if (bulletCount != null) {
/* 334 */           WeaponReloadEvent.Post postReloadEvent = new WeaponReloadEvent.Post((EntityPlayer)entityPlayer, gunStack, itemGun, false, false, true, preReloadEvent.getReloadTime(), bulletCount.intValue());
/* 335 */           MinecraftForge.EVENT_BUS.post((Event)postReloadEvent);
/*     */           
/* 337 */           if (postReloadEvent.isUnload()) {
/* 338 */             gunType.playSound((EntityLivingBase)entityPlayer, WeaponSoundType.Unload, gunStack);
/*     */           }
/*     */         } 
/*     */       }  
/*     */   }
/*     */   
/*     */   public void handleMagGunReload(EntityPlayerMP entityPlayer, ItemStack gunStack, ItemGun itemGun, GunType gunType, InventoryPlayer inventory) {
/* 345 */     if (ServerTickHandler.playerReloadCooldown.containsKey(entityPlayer.func_110124_au())) {
/*     */       return;
/*     */     }
/* 348 */     if (!this.unload) {
/* 349 */       NBTTagCompound nbtTagCompound = gunStack.func_77978_p();
/* 350 */       boolean hasAmmoLoaded = ItemGun.hasAmmoLoaded(gunStack);
/* 351 */       boolean offhandedReload = false;
/* 352 */       ItemStack ammoStackToLoad = null;
/* 353 */       Integer ammoStackSlotToLoad = null;
/* 354 */       Integer highestAmmoCount = Integer.valueOf(0);
/* 355 */       Integer multiMagToLoad = null;
/*     */ 
/*     */       
/* 358 */       if (inventory.field_184439_c.get(0) != ItemStack.field_190927_a) {
/* 359 */         ItemStack itemStack = (ItemStack)inventory.field_184439_c.get(0);
/* 360 */         if (itemStack != null && itemStack.func_77973_b() instanceof ItemAmmo) {
/* 361 */           ItemAmmo itemAmmo = (ItemAmmo)itemStack.func_77973_b();
/* 362 */           for (String ammoName : gunType.acceptedAmmo) {
/* 363 */             if (ammoName.equalsIgnoreCase(itemAmmo.baseType.internalName)) {
/* 364 */               offhandedReload = true;
/* 365 */               ammoStackToLoad = itemStack;
/*     */               
/*     */               break;
/*     */             } 
/*     */           } 
/*     */         } 
/*     */       } 
/*     */       
/* 373 */       if (hasAmmoLoaded) {
/* 374 */         ItemStack currentAmmoStack = new ItemStack(nbtTagCompound.func_74775_l("ammo"));
/* 375 */         ItemAmmo currentAmmoItem = (ItemAmmo)currentAmmoStack.func_77973_b();
/* 376 */         NBTTagCompound currentAmmoTag = currentAmmoStack.func_77978_p();
/*     */         
/* 378 */         if (currentAmmoTag.func_74764_b("magcount")) {
/* 379 */           Integer selectedMagazine = null;
/* 380 */           int highestAmmo = -1;
/*     */           
/* 382 */           for (int j = 1; j < currentAmmoItem.type.magazineCount + 1; j++) {
/* 383 */             int ammoCount = currentAmmoTag.func_74762_e("ammocount" + j);
/* 384 */             if (ammoCount > highestAmmo) {
/* 385 */               selectedMagazine = Integer.valueOf(j);
/* 386 */               highestAmmo = ammoCount;
/*     */             } 
/*     */           } 
/*     */           
/* 390 */           if (selectedMagazine != null) {
/* 391 */             currentAmmoTag.func_74768_a("magcount", selectedMagazine.intValue());
/* 392 */             ammoStackToLoad = currentAmmoStack;
/* 393 */             highestAmmoCount = Integer.valueOf(highestAmmo);
/*     */           } 
/*     */         } 
/*     */       } 
/*     */ 
/*     */       
/* 399 */       if (!offhandedReload) {
/* 400 */         for (int i = 0; i < inventory.func_70302_i_(); i++) {
/* 401 */           ItemStack itemStack = inventory.func_70301_a(i);
/* 402 */           if (itemStack != null && itemStack.func_77973_b() instanceof ItemAmmo) {
/* 403 */             ItemAmmo itemAmmo = (ItemAmmo)itemStack.func_77973_b();
/* 404 */             for (String ammoName : gunType.acceptedAmmo) {
/* 405 */               if (ammoName.equalsIgnoreCase(itemAmmo.baseType.internalName)) {
/* 406 */                 if (itemAmmo.type.magazineCount > 1) {
/* 407 */                   for (int j = 1; j < itemAmmo.type.magazineCount + 1; j++) {
/* 408 */                     int ammoCount = itemStack.func_77978_p().func_74762_e("ammocount" + j);
/* 409 */                     if (ammoCount > highestAmmoCount.intValue()) {
/* 410 */                       ammoStackToLoad = itemStack;
/* 411 */                       ammoStackSlotToLoad = Integer.valueOf(i);
/* 412 */                       highestAmmoCount = Integer.valueOf(ammoCount);
/* 413 */                       multiMagToLoad = Integer.valueOf(j);
/*     */                     } 
/*     */                   } 
/*     */                 } else {
/* 417 */                   int ammoCount = itemStack.func_77978_p().func_74762_e("ammocount");
/* 418 */                   if (ammoCount > highestAmmoCount.intValue()) {
/* 419 */                     ammoStackToLoad = itemStack;
/* 420 */                     highestAmmoCount = Integer.valueOf(ammoCount);
/* 421 */                     ammoStackSlotToLoad = Integer.valueOf(i);
/*     */                   } 
/*     */                 } 
/*     */               }
/*     */             } 
/*     */           } 
/*     */         } 
/*     */       }
/*     */ 
/*     */       
/* 431 */       if (ammoStackToLoad == null) {
/*     */         return;
/*     */       }
/*     */       
/* 435 */       if (!ammoStackToLoad.func_77978_p().func_74764_b("magcount")) {
/* 436 */         multiMagToLoad = null;
/*     */       }
/*     */       
/* 439 */       boolean multiMagReload = (hasAmmoLoaded && ammoStackToLoad.func_77978_p().func_74764_b("magcount"));
/* 440 */       boolean loadOnly = false;
/* 441 */       WeaponReloadEvent.Pre preReloadEvent = new WeaponReloadEvent.Pre((EntityPlayer)entityPlayer, gunStack, itemGun, offhandedReload, multiMagReload);
/* 442 */       MinecraftForge.EVENT_BUS.post((Event)preReloadEvent);
/* 443 */       if (preReloadEvent.isCanceled()) {
/*     */         return;
/*     */       }
/* 446 */       if (gunStack.func_77978_p().func_74764_b("shotsremaining") && gunStack.func_77978_p().func_74762_e("shotsremaining") > 0) {
/* 447 */         gunStack.func_77978_p().func_74768_a("shotsremaining", 0);
/*     */       }
/*     */ 
/*     */       
/* 451 */       if (!multiMagReload || multiMagToLoad != null) {
/* 452 */         if (ItemGun.hasAmmoLoaded(gunStack)) {
/* 453 */           ReloadHelper.unloadAmmo(entityPlayer, gunStack);
/*     */         } else {
/* 455 */           loadOnly = true;
/*     */         } 
/*     */       }
/*     */ 
/*     */       
/* 460 */       ItemStack loadingItemStack = ammoStackToLoad.func_77946_l();
/* 461 */       loadingItemStack.func_190920_e(1);
/*     */       
/* 463 */       if (multiMagReload && multiMagToLoad != null) {
/* 464 */         loadingItemStack.func_77978_p().func_74768_a("magcount", multiMagToLoad.intValue());
/*     */       }
/* 466 */       nbtTagCompound.func_74782_a("ammo", (NBTBase)loadingItemStack.func_77955_b(new NBTTagCompound()));
/*     */       
/* 468 */       ammoStackToLoad.func_190920_e(ammoStackToLoad.func_190916_E() - 1);
/*     */       
/* 470 */       if (ammoStackSlotToLoad != null && 
/* 471 */         !entityPlayer.field_71075_bZ.field_75098_d) {
/* 472 */         if (offhandedReload) {
/* 473 */           inventory.field_184439_c.set(0, (ammoStackToLoad.func_190916_E() >= 1) ? ammoStackToLoad : ItemStack.field_190927_a);
/*     */         } else {
/* 475 */           inventory.func_70299_a(ammoStackSlotToLoad.intValue(), (ammoStackToLoad.func_190916_E() >= 1) ? ammoStackToLoad : ItemStack.field_190927_a);
/*     */         } 
/*     */       }
/*     */       
/* 479 */       int reloadTime = preReloadEvent.getReloadTime();
/*     */       
/* 481 */       if (loadingItemStack.func_77973_b() instanceof ItemAmmo) {
/* 482 */         ItemAmmo itemAmmo = (ItemAmmo)loadingItemStack.func_77973_b();
/* 483 */         reloadTime = (int)(reloadTime * itemAmmo.type.reloadTimeFactor);
/*     */       } 
/*     */ 
/*     */       
/* 487 */       WeaponReloadEvent.Post postReloadEvent = new WeaponReloadEvent.Post((EntityPlayer)entityPlayer, gunStack, itemGun, offhandedReload, multiMagReload, loadOnly, false, reloadTime);
/* 488 */       MinecraftForge.EVENT_BUS.post((Event)postReloadEvent);
/*     */       
/* 490 */       if (postReloadEvent.isLoadOnly()) {
/* 491 */         gunType.playSound((EntityLivingBase)entityPlayer, WeaponSoundType.Load, gunStack);
/* 492 */       } else if (!postReloadEvent.isLoadOnly() && !postReloadEvent.isUnload()) {
/* 493 */         gunType.playSound((EntityLivingBase)entityPlayer, WeaponSoundType.Reload, gunStack);
/*     */       } 
/* 495 */       int reloadType = (postReloadEvent.isLoadOnly() ? ReloadType.Load : (postReloadEvent.isUnload() ? ReloadType.Unload : ReloadType.Full)).i;
/* 496 */       ModularWarfare.NETWORK.sendTo(new PacketClientAnimation(gunType.internalName, reloadTime, postReloadEvent.getReloadCount(), reloadType), entityPlayer);
/* 497 */       ServerTickHandler.playerReloadCooldown.put(entityPlayer.func_110124_au(), Integer.valueOf(reloadTime));
/*     */     } else {
/*     */       
/* 500 */       WeaponReloadEvent.Pre preReloadEvent = new WeaponReloadEvent.Pre((EntityPlayer)entityPlayer, gunStack, itemGun, false, false);
/* 501 */       MinecraftForge.EVENT_BUS.post((Event)preReloadEvent);
/* 502 */       if (preReloadEvent.isCanceled()) {
/*     */         return;
/*     */       }
/* 505 */       if (ReloadHelper.unloadAmmo(entityPlayer, gunStack)) {
/* 506 */         WeaponReloadEvent.Post postReloadEvent = new WeaponReloadEvent.Post((EntityPlayer)entityPlayer, gunStack, itemGun, false, false, false, true, preReloadEvent.getReloadTime());
/* 507 */         MinecraftForge.EVENT_BUS.post((Event)postReloadEvent);
/*     */         
/* 509 */         if (postReloadEvent.isUnload()) {
/* 510 */           gunType.playSound((EntityLivingBase)entityPlayer, WeaponSoundType.Unload, gunStack);
/*     */         }
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void handleBulletGunReloadEnhanced(EntityPlayerMP entityPlayer, ItemStack gunStack, ItemGun itemGun, GunType gunType, InventoryPlayer inventory) {
/* 518 */     if (ServerTickHandler.playerReloadCooldown.containsKey(entityPlayer.func_110124_au())) {
/*     */       return;
/*     */     }
/* 521 */     if (gunType.acceptedBullets != null) {
/* 522 */       if (!this.unload) {
/* 523 */         ItemStack bulletStackToLoad = null;
/* 524 */         Integer bulletStackSlotToLoad = null;
/*     */         
/* 526 */         if (gunStack.func_77978_p() != null && 
/* 527 */           gunType.internalAmmoStorage != null && 
/* 528 */           gunStack.func_77978_p().func_74775_l("bullet") != null) {
/*     */ 
/*     */ 
/*     */           
/* 532 */           if (gunStack.func_77978_p().func_74762_e("ammocount") <= 0);
/*     */ 
/*     */ 
/*     */           
/* 536 */           bulletStackToLoad = new ItemStack(gunStack.func_77978_p().func_74775_l("bullet"));
/* 537 */           if (gunStack.func_77978_p().func_74762_e("ammocount") >= gunType.internalAmmoStorage.intValue()) {
/*     */             return;
/*     */           }
/*     */         } 
/*     */ 
/*     */ 
/*     */         
/* 544 */         NBTTagCompound nbtTagCompound = gunStack.func_77978_p();
/* 545 */         boolean offhandedReload = false;
/* 546 */         int highestBulletCount = 0;
/*     */         
/* 548 */         if (bulletStackToLoad == null || bulletStackToLoad.func_190926_b()) {
/*     */ 
/*     */           
/* 551 */           if (inventory.field_184439_c.get(0) != ItemStack.field_190927_a) {
/* 552 */             ItemStack itemStack = (ItemStack)inventory.field_184439_c.get(0);
/* 553 */             if (itemStack != null && itemStack.func_77973_b() instanceof ItemBullet) {
/* 554 */               ItemBullet itemBullet = (ItemBullet)itemStack.func_77973_b();
/* 555 */               for (String bulletName : gunType.acceptedBullets) {
/* 556 */                 if (bulletName.equalsIgnoreCase(itemBullet.baseType.internalName)) {
/* 557 */                   offhandedReload = true;
/* 558 */                   bulletStackToLoad = itemStack;
/*     */ 
/*     */                   
/*     */                   break;
/*     */                 } 
/*     */               } 
/*     */             } 
/*     */           } 
/*     */ 
/*     */           
/* 568 */           if (!offhandedReload) {
/* 569 */             for (int i = 0; i < inventory.func_70302_i_(); i++) {
/* 570 */               ItemStack itemStack = inventory.func_70301_a(i);
/* 571 */               if (itemStack != null && itemStack.func_77973_b() instanceof ItemBullet) {
/* 572 */                 ItemBullet itemBullet = (ItemBullet)itemStack.func_77973_b();
/* 573 */                 for (String bulletName : gunType.acceptedBullets) {
/* 574 */                   if (bulletName.equalsIgnoreCase(itemBullet.baseType.internalName)) {
/* 575 */                     int count = itemStack.func_190916_E();
/* 576 */                     if (count > highestBulletCount) {
/* 577 */                       bulletStackToLoad = itemStack;
/* 578 */                       highestBulletCount = count;
/* 579 */                       bulletStackSlotToLoad = Integer.valueOf(i);
/*     */                     } 
/*     */                   } 
/*     */                 } 
/*     */               } 
/*     */             } 
/*     */           }
/*     */         } 
/*     */ 
/*     */         
/* 589 */         if (bulletStackToLoad == null) {
/*     */           return;
/*     */         }
/* 592 */         ItemStack loadingItemStack = bulletStackToLoad.func_77946_l();
/* 593 */         loadingItemStack.func_190920_e(1);
/* 594 */         int loadingCount = ReloadHelper.inventoryItemCount((EntityPlayer)entityPlayer, loadingItemStack);
/* 595 */         int ammoCount = gunStack.func_77978_p().func_74762_e("ammocount");
/* 596 */         int amountToLoad = gunType.internalAmmoStorage.intValue() - ammoCount;
/* 597 */         if (loadingCount > amountToLoad) {
/* 598 */           loadingCount = amountToLoad;
/*     */         }
/*     */         
/* 601 */         WeaponEnhancedReloadEvent.SearchBullet searchBulletEvent = new WeaponEnhancedReloadEvent.SearchBullet(entityPlayer, gunStack, bulletStackToLoad, loadingCount);
/* 602 */         MinecraftForge.EVENT_BUS.post((Event)searchBulletEvent);
/* 603 */         bulletStackToLoad = searchBulletEvent.bullet;
/* 604 */         loadingCount = searchBulletEvent.count;
/*     */         
/* 606 */         if (loadingCount <= 0) {
/*     */           return;
/*     */         }
/*     */         
/* 610 */         boolean loadOnly = false;
/* 611 */         WeaponReloadEvent.Pre preReloadEvent = new WeaponReloadEvent.Pre((EntityPlayer)entityPlayer, gunStack, itemGun, offhandedReload, false);
/* 612 */         MinecraftForge.EVENT_BUS.post((Event)preReloadEvent);
/* 613 */         if (preReloadEvent.isCanceled()) {
/*     */           return;
/*     */         }
/* 616 */         ItemBullet bulletItemToLoad = (ItemBullet)bulletStackToLoad.func_77973_b();
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
/* 627 */         if (!nbtTagCompound.func_74764_b("bullet")) {
/* 628 */           loadOnly = true;
/*     */         }
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
/* 656 */         WeaponReloadEvent.Post postReloadEvent = new WeaponReloadEvent.Post((EntityPlayer)entityPlayer, gunStack, itemGun, offhandedReload, loadOnly, false, preReloadEvent.getReloadTime(), loadingCount);
/* 657 */         MinecraftForge.EVENT_BUS.post((Event)postReloadEvent);
/* 658 */         int reloadType = (postReloadEvent.isLoadOnly() ? ReloadType.Load : (postReloadEvent.isUnload() ? ReloadType.Unload : ReloadType.Full)).i;
/* 659 */         ServerTickHandler.playerReloadCooldown.put(entityPlayer.func_110124_au(), Integer.valueOf(preReloadEvent.getReloadTime()));
/* 660 */         ServerTickHandler.reloadEnhancedTask.put(entityPlayer.func_110124_au(), new DataGunReloadEnhancedTask(entityPlayer.field_71071_by.field_70461_c, gunStack, loadingItemStack.func_77946_l(), postReloadEvent.getReloadCount()));
/* 661 */         ModularWarfare.NETWORK.sendTo(new PacketGunReloadEnhancedTask(loadingItemStack), entityPlayer);
/* 662 */         ModularWarfare.NETWORK.sendTo(new PacketClientAnimation(gunType.internalName, postReloadEvent.getReloadTime(), postReloadEvent.getReloadCount(), reloadType), entityPlayer);
/*     */       } else {
/*     */         
/* 665 */         WeaponReloadEvent.Pre preReloadEvent = new WeaponReloadEvent.Pre((EntityPlayer)entityPlayer, gunStack, itemGun, false, false);
/* 666 */         MinecraftForge.EVENT_BUS.post((Event)preReloadEvent);
/* 667 */         if (preReloadEvent.isCanceled())
/*     */           return; 
/* 669 */         WeaponEnhancedReloadEvent.Unload unloadEvent = new WeaponEnhancedReloadEvent.Unload(entityPlayer, gunStack);
/* 670 */         MinecraftForge.EVENT_BUS.post((Event)unloadEvent);
/* 671 */         if (unloadEvent.isCanceled()) {
/*     */           return;
/*     */         }
/* 674 */         Integer bulletCount = ReloadHelper.checkUnloadBullets(entityPlayer, gunStack);
/* 675 */         if (bulletCount != null && bulletCount.intValue() > 0) {
/* 676 */           WeaponReloadEvent.Post postReloadEvent = new WeaponReloadEvent.Post((EntityPlayer)entityPlayer, gunStack, itemGun, false, false, true, preReloadEvent.getReloadTime(), bulletCount.intValue());
/* 677 */           MinecraftForge.EVENT_BUS.post((Event)postReloadEvent);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */           
/* 684 */           int reloadType = (postReloadEvent.isLoadOnly() ? ReloadType.Load : (postReloadEvent.isUnload() ? ReloadType.Unload : ReloadType.Full)).i;
/* 685 */           ServerTickHandler.reloadEnhancedTask.put(entityPlayer.func_110124_au(), new DataGunReloadEnhancedTask(entityPlayer.field_71071_by.field_70461_c, gunStack, true, bulletCount.intValue()));
/* 686 */           ModularWarfare.NETWORK.sendTo(new PacketGunReloadEnhancedTask(UNLOAD_EMPTY), entityPlayer);
/* 687 */           ModularWarfare.NETWORK.sendTo(new PacketClientAnimation(gunType.internalName, 0, bulletCount.intValue(), reloadType), entityPlayer);
/* 688 */         } else if (bulletCount != null) {
/* 689 */           handleBulletGunReload(entityPlayer, gunStack, itemGun, gunType, inventory);
/*     */         } 
/*     */       } 
/*     */     }
/*     */   }
/*     */   
/*     */   public void handleMagGunReloadEnhanced(EntityPlayerMP entityPlayer, ItemStack gunStack, ItemGun itemGun, GunType gunType, InventoryPlayer inventory) {
/* 696 */     if (ServerTickHandler.playerReloadCooldown.containsKey(entityPlayer.func_110124_au())) {
/*     */       return;
/*     */     }
/* 699 */     if (!this.unload) {
/* 700 */       NBTTagCompound nbtTagCompound = gunStack.func_77978_p();
/* 701 */       boolean hasAmmoLoaded = ItemGun.hasAmmoLoaded(gunStack);
/* 702 */       boolean offhandedReload = false;
/* 703 */       ItemStack ammoStackToLoad = null;
/* 704 */       Integer ammoStackSlotToLoad = null;
/* 705 */       Integer highestAmmoCount = Integer.valueOf(0);
/* 706 */       Integer multiMagToLoad = null;
/* 707 */       boolean currentAmmo = false;
/* 708 */       ItemStack currentAmmoToLoad = null;
/* 709 */       ItemStack multiAmmoToLoad = null;
/*     */       
/* 711 */       ItemStack currentAmmoStack = (new ItemStack(nbtTagCompound.func_74775_l("ammo"))).func_77946_l();
/*     */ 
/*     */       
/* 714 */       if (inventory.field_184439_c.get(0) != ItemStack.field_190927_a) {
/* 715 */         ItemStack itemStack = (ItemStack)inventory.field_184439_c.get(0);
/* 716 */         if (itemStack != null && itemStack.func_77973_b() instanceof ItemAmmo) {
/* 717 */           ItemAmmo itemAmmo = (ItemAmmo)itemStack.func_77973_b();
/* 718 */           for (String ammoName : gunType.acceptedAmmo) {
/* 719 */             if (ammoName.equalsIgnoreCase(itemAmmo.baseType.internalName)) {
/* 720 */               offhandedReload = true;
/* 721 */               ammoStackToLoad = itemStack;
/*     */               
/*     */               break;
/*     */             } 
/*     */           } 
/*     */         } 
/*     */       } 
/*     */       
/* 729 */       if (hasAmmoLoaded) {
/* 730 */         ItemAmmo currentAmmoItem = (ItemAmmo)currentAmmoStack.func_77973_b();
/* 731 */         NBTTagCompound currentAmmoTag = currentAmmoStack.func_77978_p();
/*     */         
/* 733 */         if (currentAmmoTag.func_74764_b("magcount")) {
/* 734 */           Integer selectedMagazine = null;
/* 735 */           int highestAmmo = -1;
/*     */           
/* 737 */           for (int j = 1; j < currentAmmoItem.type.magazineCount + 1; j++) {
/* 738 */             int ammoCount = currentAmmoTag.func_74762_e("ammocount" + j);
/* 739 */             if (ammoCount > highestAmmo) {
/* 740 */               selectedMagazine = Integer.valueOf(j);
/* 741 */               multiMagToLoad = Integer.valueOf(j);
/* 742 */               highestAmmo = ammoCount;
/*     */             } 
/*     */           } 
/*     */           
/* 746 */           if (selectedMagazine != null) {
/* 747 */             currentAmmoTag.func_74768_a("magcount", selectedMagazine.intValue());
/* 748 */             ammoStackToLoad = currentAmmoStack;
/* 749 */             currentAmmoToLoad = currentAmmoStack;
/* 750 */             currentAmmo = true;
/* 751 */             highestAmmoCount = Integer.valueOf(highestAmmo);
/*     */           } 
/*     */         } 
/*     */       } 
/*     */ 
/*     */       
/* 757 */       if (!offhandedReload) {
/* 758 */         for (int i = 0; i < inventory.func_70302_i_(); i++) {
/* 759 */           ItemStack itemStack = inventory.func_70301_a(i);
/* 760 */           if (itemStack != null && itemStack.func_77973_b() instanceof ItemAmmo) {
/* 761 */             ItemAmmo itemAmmo = (ItemAmmo)itemStack.func_77973_b();
/* 762 */             for (String ammoName : gunType.acceptedAmmo) {
/* 763 */               if (ammoName.equalsIgnoreCase(itemAmmo.baseType.internalName)) {
/* 764 */                 if (itemAmmo.type.magazineCount > 1) {
/* 765 */                   for (int j = 1; j < itemAmmo.type.magazineCount + 1; j++) {
/* 766 */                     int ammoCount = itemStack.func_77978_p().func_74762_e("ammocount" + j);
/* 767 */                     if (ammoCount > highestAmmoCount.intValue()) {
/* 768 */                       ammoStackToLoad = itemStack;
/* 769 */                       multiAmmoToLoad = itemStack;
/* 770 */                       ammoStackSlotToLoad = Integer.valueOf(i);
/* 771 */                       highestAmmoCount = Integer.valueOf(ammoCount);
/* 772 */                       multiMagToLoad = Integer.valueOf(j);
/*     */                     } 
/*     */                   } 
/*     */                 } else {
/* 776 */                   int ammoCount = itemStack.func_77978_p().func_74762_e("ammocount");
/* 777 */                   if (ammoCount > highestAmmoCount.intValue()) {
/* 778 */                     ammoStackToLoad = itemStack;
/* 779 */                     highestAmmoCount = Integer.valueOf(ammoCount);
/* 780 */                     ammoStackSlotToLoad = Integer.valueOf(i);
/*     */                   } 
/*     */                 } 
/*     */               }
/*     */             } 
/*     */           } 
/*     */         } 
/*     */       }
/*     */       
/* 789 */       WeaponEnhancedReloadEvent.SearchAmmo searchAmmoEvent = new WeaponEnhancedReloadEvent.SearchAmmo(entityPlayer, gunStack, ammoStackToLoad);
/* 790 */       MinecraftForge.EVENT_BUS.post((Event)searchAmmoEvent);
/* 791 */       ammoStackToLoad = searchAmmoEvent.ammo;
/*     */ 
/*     */       
/* 794 */       if (ammoStackToLoad == null) {
/*     */         return;
/*     */       }
/*     */       
/* 798 */       if (!ammoStackToLoad.func_77978_p().func_74764_b("magcount")) {
/* 799 */         multiMagToLoad = null;
/*     */       }
/*     */       
/* 802 */       boolean multiMagReload = (hasAmmoLoaded && ammoStackToLoad.func_77978_p().func_74764_b("magcount"));
/* 803 */       boolean loadOnly = false;
/* 804 */       WeaponReloadEvent.Pre preReloadEvent = new WeaponReloadEvent.Pre((EntityPlayer)entityPlayer, gunStack, itemGun, offhandedReload, multiMagReload);
/* 805 */       MinecraftForge.EVENT_BUS.post((Event)preReloadEvent);
/* 806 */       if (preReloadEvent.isCanceled()) {
/*     */         return;
/*     */       }
/* 809 */       if (gunStack.func_77978_p().func_74764_b("shotsremaining") && gunStack.func_77978_p().func_74762_e("shotsremaining") > 0) {
/* 810 */         gunStack.func_77978_p().func_74768_a("shotsremaining", 0);
/*     */       }
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
/* 823 */       if ((!multiMagReload || multiMagToLoad != null) && 
/* 824 */         !ItemGun.hasAmmoLoaded(gunStack)) {
/* 825 */         loadOnly = true;
/*     */       }
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 831 */       ItemStack loadingItemStack = ammoStackToLoad.func_77946_l();
/* 832 */       loadingItemStack.func_190920_e(1);
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
/* 852 */       int reloadTime = preReloadEvent.getReloadTime();
/*     */       
/* 854 */       if (loadingItemStack.func_77973_b() instanceof ItemAmmo) {
/* 855 */         ItemAmmo itemAmmo = (ItemAmmo)loadingItemStack.func_77973_b();
/* 856 */         reloadTime = (int)(reloadTime * itemAmmo.type.reloadTimeFactor);
/*     */       } 
/*     */ 
/*     */       
/* 860 */       WeaponReloadEvent.Post postReloadEvent = new WeaponReloadEvent.Post((EntityPlayer)entityPlayer, gunStack, itemGun, offhandedReload, multiMagReload, loadOnly, false, reloadTime);
/* 861 */       MinecraftForge.EVENT_BUS.post((Event)postReloadEvent);
/* 862 */       int reloadType = (postReloadEvent.isLoadOnly() ? ReloadType.Load : (postReloadEvent.isUnload() ? ReloadType.Unload : ReloadType.Full)).i;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 872 */       ServerTickHandler.playerReloadCooldown.put(entityPlayer.func_110124_au(), Integer.valueOf(reloadTime));
/* 873 */       ServerTickHandler.reloadEnhancedTask.put(entityPlayer.func_110124_au(), new DataGunReloadEnhancedTask(entityPlayer.field_71071_by.field_70461_c, gunStack, loadingItemStack.func_77946_l(), postReloadEvent.getReloadCount(), (ammoStackToLoad == currentAmmoToLoad), (ammoStackToLoad == multiAmmoToLoad), multiMagToLoad));
/* 874 */       ModularWarfare.NETWORK.sendTo(new PacketGunReloadEnhancedTask(loadingItemStack, (ammoStackToLoad == currentAmmoToLoad)), entityPlayer);
/* 875 */       ModularWarfare.NETWORK.sendTo(new PacketClientAnimation(gunType.internalName, reloadTime, postReloadEvent.getReloadCount(), reloadType), entityPlayer);
/*     */     } else {
/*     */       
/* 878 */       WeaponReloadEvent.Pre preReloadEvent = new WeaponReloadEvent.Pre((EntityPlayer)entityPlayer, gunStack, itemGun, false, false);
/* 879 */       MinecraftForge.EVENT_BUS.post((Event)preReloadEvent);
/* 880 */       if (preReloadEvent.isCanceled()) {
/*     */         return;
/*     */       }
/* 883 */       WeaponEnhancedReloadEvent.Unload unloadEvent = new WeaponEnhancedReloadEvent.Unload(entityPlayer, gunStack);
/* 884 */       MinecraftForge.EVENT_BUS.post((Event)unloadEvent);
/* 885 */       if (unloadEvent.isCanceled()) {
/*     */         return;
/*     */       }
/* 888 */       if (ReloadHelper.checkUnloadAmmo(entityPlayer, gunStack)) {
/* 889 */         WeaponReloadEvent.Post postReloadEvent = new WeaponReloadEvent.Post((EntityPlayer)entityPlayer, gunStack, itemGun, false, false, false, true, preReloadEvent.getReloadTime());
/* 890 */         MinecraftForge.EVENT_BUS.post((Event)postReloadEvent);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 898 */         int reloadType = (postReloadEvent.isLoadOnly() ? ReloadType.Load : (postReloadEvent.isUnload() ? ReloadType.Unload : ReloadType.Full)).i;
/* 899 */         ServerTickHandler.reloadEnhancedTask.put(entityPlayer.func_110124_au(), new DataGunReloadEnhancedTask(entityPlayer.field_71071_by.field_70461_c, gunStack, true));
/* 900 */         ModularWarfare.NETWORK.sendTo(new PacketGunReloadEnhancedTask(UNLOAD_EMPTY), entityPlayer);
/* 901 */         ModularWarfare.NETWORK.sendTo(new PacketClientAnimation(gunType.internalName, 0, 1, reloadType), entityPlayer);
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   public void handleClientSide(EntityPlayer entityPlayer) {}
/*     */ }


/* Location:              C:\Users\DuduM\OneDrive\Área de Trabalho\mw10\modularwarfare-shining-2023.2.4.4f-fix10.jar!\com\modularwarfare\common\network\PacketGunReload.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */