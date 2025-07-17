/*     */ package com.modularwarfare.utility;
/*     */ 
/*     */ import com.modularwarfare.common.guns.AmmoType;
/*     */ import com.modularwarfare.common.guns.ItemAmmo;
/*     */ import com.modularwarfare.common.guns.ItemGun;
/*     */ import com.modularwarfare.common.handler.data.VarInt;
/*     */ import com.modularwarfare.common.type.BaseItem;
/*     */ import java.util.function.Consumer;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.entity.player.EntityPlayerMP;
/*     */ import net.minecraft.item.Item;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.nbt.NBTTagCompound;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class ReloadHelper
/*     */ {
/*     */   public static int getBulletOnMag(ItemStack ammoStack, Integer currentMagcount) {
/*  23 */     if (!(ammoStack.func_77973_b() instanceof ItemAmmo)) {
/*  24 */       return 0;
/*     */     }
/*  26 */     if (!ammoStack.func_77942_o()) {
/*  27 */       return 0;
/*     */     }
/*  29 */     ItemAmmo itemAmmo = (ItemAmmo)ammoStack.func_77973_b();
/*  30 */     AmmoType ammoType = itemAmmo.type;
/*  31 */     NBTTagCompound nbtTagCompound = ammoStack.func_77978_p();
/*  32 */     int ammoCount = 0;
/*  33 */     if (!nbtTagCompound.func_74764_b("magcount")) {
/*  34 */       ammoCount = ammoStack.func_77978_p().func_74762_e("ammocount");
/*     */     }
/*  36 */     else if (currentMagcount != null) {
/*  37 */       ammoCount = ammoStack.func_77978_p().func_74762_e("ammocount" + currentMagcount);
/*     */     } else {
/*  39 */       for (int i = 1; i <= ammoType.magazineCount + 1; i++) {
/*  40 */         ammoCount += ammoStack.func_77978_p().func_74762_e("ammocount" + i);
/*     */       }
/*     */     } 
/*     */     
/*  44 */     return ammoCount;
/*     */   }
/*     */   
/*     */   public static boolean setBulletOnMag(ItemStack ammoStack, Integer currentMagcount, int ammout) {
/*  48 */     if (!(ammoStack.func_77973_b() instanceof ItemAmmo)) {
/*  49 */       return false;
/*     */     }
/*  51 */     if (!ammoStack.func_77942_o()) {
/*  52 */       return false;
/*     */     }
/*  54 */     ItemAmmo itemAmmo = (ItemAmmo)ammoStack.func_77973_b();
/*  55 */     AmmoType ammoType = itemAmmo.type;
/*  56 */     NBTTagCompound nbtTagCompound = ammoStack.func_77978_p();
/*  57 */     int ammoCount = 0;
/*  58 */     if (!nbtTagCompound.func_74764_b("magcount")) {
/*  59 */       ammoStack.func_77978_p().func_74768_a("ammocount", ammout);
/*     */     }
/*  61 */     else if (currentMagcount != null) {
/*  62 */       ammoStack.func_77978_p().func_74768_a("ammocount" + currentMagcount.intValue(), ammout);
/*     */     } else {
/*  64 */       int mag = ammoStack.func_77978_p().func_74762_e("magcount");
/*  65 */       ammoStack.func_77978_p().func_74768_a("ammocount" + mag, ammout);
/*     */     } 
/*     */     
/*  68 */     return true;
/*     */   }
/*     */   
/*     */   public static boolean isSameTypeAmmo(ItemStack stackA, ItemStack stackB) {
/*  72 */     Item itemA = stackA.func_77973_b();
/*  73 */     Item itemB = stackB.func_77973_b();
/*  74 */     if (!(itemA instanceof BaseItem)) {
/*  75 */       return false;
/*     */     }
/*  77 */     if (!itemA.equals(itemB)) {
/*  78 */       return false;
/*     */     }
/*  80 */     String ammoTypeA = ((BaseItem)itemA).baseType.internalName;
/*  81 */     String ammoTypeB = ((BaseItem)itemB).baseType.internalName;
/*  82 */     if (stackA.func_77952_i() != stackB.func_77952_i()) {
/*  83 */       return false;
/*     */     }
/*  85 */     return ammoTypeA.equals(ammoTypeB);
/*     */   }
/*     */   
/*     */   public static boolean checkUnloadAmmo(EntityPlayerMP entityPlayer, ItemStack gunStack) {
/*  89 */     if (ItemGun.hasAmmoLoaded(gunStack)) {
/*  90 */       return true;
/*     */     }
/*  92 */     return false;
/*     */   }
/*     */   
/*     */   public static Integer checkUnloadBullets(EntityPlayerMP entityPlayer, ItemStack targetStack) {
/*  96 */     NBTTagCompound nbtTagCompound = targetStack.func_77978_p();
/*     */ 
/*     */     
/*  99 */     if (nbtTagCompound.func_74764_b("bullet")) {
/* 100 */       ItemStack returningBullet = new ItemStack(nbtTagCompound.func_74775_l("bullet"));
/* 101 */       int bulletsToUnload = 0;
/*     */       
/* 103 */       if (!nbtTagCompound.func_74764_b("magcount")) {
/* 104 */         bulletsToUnload = targetStack.func_77978_p().func_74762_e("ammocount");
/*     */       } else {
/* 106 */         AmmoType ammoType = ((ItemAmmo)targetStack.func_77973_b()).type;
/* 107 */         for (int i = 1; i <= ammoType.magazineCount + 1; i++) {
/* 108 */           bulletsToUnload += nbtTagCompound.func_74762_e("ammocount" + i);
/*     */         }
/*     */       } 
/* 111 */       return Integer.valueOf(bulletsToUnload);
/*     */     } 
/* 113 */     return null;
/*     */   }
/*     */   
/*     */   public static boolean unloadAmmo(EntityPlayerMP entityPlayer, ItemStack gunStack) {
/* 117 */     NBTTagCompound nbtTagCompound = gunStack.func_77978_p();
/* 118 */     if (ItemGun.hasAmmoLoaded(gunStack)) {
/* 119 */       ItemStack returningAmmo = new ItemStack(nbtTagCompound.func_74775_l("ammo"));
/* 120 */       ItemAmmo returningAmmoItem = (ItemAmmo)returningAmmo.func_77973_b();
/* 121 */       boolean returnEmptyAmmo = true;
/* 122 */       returnEmptyAmmo = returningAmmoItem.type.allowEmptyMagazines;
/*     */       
/* 124 */       if (returningAmmoItem.type.subAmmo != null && ItemAmmo.hasAmmo(returningAmmo)) {
/* 125 */         int currentAmmoCount = ItemGun.getMagazineBullets(gunStack);
/* 126 */         returningAmmo.func_77964_b(returningAmmo.func_77958_k() - currentAmmoCount);
/* 127 */         if (!entityPlayer.field_71071_by.func_70441_a(returningAmmo)) {
/* 128 */           entityPlayer.func_71019_a(returningAmmo, false);
/*     */         
/*     */         }
/*     */       }
/* 132 */       else if (!returnEmptyAmmo) {
/* 133 */         int currentAmmoCount = ItemGun.getMagazineBullets(gunStack);
/* 134 */         returningAmmo.func_77964_b(returningAmmo.func_77958_k() - currentAmmoCount);
/* 135 */         entityPlayer.func_71019_a(returningAmmo, false);
/*     */       } else {
/* 137 */         int currentAmmoCount = ItemGun.getMagazineBullets(gunStack);
/* 138 */         returningAmmo.func_77964_b(returningAmmo.func_77958_k() - currentAmmoCount);
/* 139 */         if (!entityPlayer.field_71071_by.func_70441_a(returningAmmo)) {
/* 140 */           entityPlayer.func_71019_a(returningAmmo, false);
/*     */         }
/*     */       } 
/*     */       
/* 144 */       nbtTagCompound.func_82580_o("ammo");
/* 145 */       return true;
/*     */     } 
/* 147 */     return false;
/*     */   }
/*     */   
/*     */   public static Integer unloadBullets(EntityPlayerMP entityPlayer, ItemStack targetStack) {
/* 151 */     return unloadBullets(entityPlayer, targetStack, null);
/*     */   }
/*     */   
/*     */   public static Integer unloadBullets(EntityPlayerMP entityPlayer, ItemStack targetStack, Integer expectBulletsToUnload) {
/* 155 */     NBTTagCompound nbtTagCompound = targetStack.func_77978_p();
/*     */ 
/*     */     
/* 158 */     if (nbtTagCompound.func_74764_b("bullet")) {
/* 159 */       ItemStack returningBullet = new ItemStack(nbtTagCompound.func_74775_l("bullet"));
/* 160 */       int bulletsToUnload = 0;
/* 161 */       int bulletsReturnCount = 0;
/* 162 */       boolean removeFlag = true;
/*     */ 
/*     */       
/* 165 */       if (expectBulletsToUnload != null) {
/* 166 */         bulletsToUnload = expectBulletsToUnload.intValue();
/* 167 */         bulletsReturnCount = bulletsToUnload;
/* 168 */         if (!nbtTagCompound.func_74764_b("magcount")) {
/* 169 */           int count = nbtTagCompound.func_74762_e("ammocount") - expectBulletsToUnload.intValue();
/* 170 */           if (count < 0) {
/* 171 */             bulletsReturnCount += count;
/* 172 */             count = 0;
/*     */           } 
/* 174 */           nbtTagCompound.func_74768_a("ammocount", count);
/* 175 */           if (count > 0) {
/* 176 */             removeFlag = false;
/*     */           }
/*     */         } else {
/* 179 */           int maxCount = expectBulletsToUnload.intValue();
/* 180 */           AmmoType ammoType = ((ItemAmmo)targetStack.func_77973_b()).type;
/* 181 */           for (int i = 1; i < ammoType.magazineCount + 1 && maxCount > 0; i++) {
/* 182 */             int count = nbtTagCompound.func_74762_e("ammocount" + i);
/* 183 */             if (maxCount >= count) {
/* 184 */               nbtTagCompound.func_74768_a("ammocount" + i, 0);
/* 185 */               maxCount -= count;
/*     */             } else {
/* 187 */               nbtTagCompound.func_74768_a("ammocount" + i, count - maxCount);
/* 188 */               maxCount = 0;
/* 189 */               removeFlag = false;
/*     */             } 
/*     */           } 
/* 192 */           bulletsReturnCount -= maxCount;
/*     */         }
/*     */       
/* 195 */       } else if (!nbtTagCompound.func_74764_b("magcount")) {
/* 196 */         bulletsReturnCount = targetStack.func_77978_p().func_74762_e("ammocount");
/* 197 */         nbtTagCompound.func_74768_a("ammocount", 0);
/*     */       } else {
/* 199 */         AmmoType ammoType = ((ItemAmmo)targetStack.func_77973_b()).type;
/* 200 */         for (int i = 1; i < ammoType.magazineCount + 1; i++) {
/* 201 */           bulletsReturnCount += nbtTagCompound.func_74762_e("ammocount" + i);
/* 202 */           nbtTagCompound.func_74768_a("ammocount" + i, 0);
/*     */         } 
/*     */       } 
/*     */ 
/*     */       
/* 207 */       int animBulletsToReload = bulletsReturnCount;
/* 208 */       while (bulletsReturnCount > 0) {
/* 209 */         if (bulletsReturnCount <= 64) {
/* 210 */           ItemStack itemStack = returningBullet.func_77946_l();
/* 211 */           itemStack.func_190920_e(bulletsReturnCount);
/* 212 */           entityPlayer.field_71071_by.func_70441_a(itemStack);
/* 213 */           bulletsReturnCount -= bulletsReturnCount; continue;
/*     */         } 
/* 215 */         ItemStack clonedBullet = returningBullet.func_77946_l();
/* 216 */         clonedBullet.func_190920_e(64);
/* 217 */         entityPlayer.field_71071_by.func_70441_a(clonedBullet);
/* 218 */         bulletsReturnCount -= 64;
/*     */       } 
/*     */       
/* 221 */       if (removeFlag) {
/* 222 */         nbtTagCompound.func_82580_o("bullet");
/* 223 */         nbtTagCompound.func_82580_o("ammo");
/*     */       } 
/* 225 */       return Integer.valueOf(animBulletsToReload);
/*     */     } 
/* 227 */     return null;
/*     */   }
/*     */   
/*     */   public static int inventoryItemCount(EntityPlayer player, ItemStack stack) {
/* 231 */     VarInt count = new VarInt();
/* 232 */     Consumer<ItemStack> consumer = s -> {
/*     */         if (ItemStack.func_179545_c(s, stack) && ItemStack.func_77970_a(s, stack)) {
/*     */           count.i += s.func_190916_E();
/*     */         }
/*     */       };
/* 237 */     player.field_71071_by.field_184439_c.forEach(consumer);
/* 238 */     player.field_71071_by.field_70462_a.forEach(consumer);
/* 239 */     player.field_71071_by.field_70460_b.forEach(consumer);
/* 240 */     return count.i;
/*     */   }
/*     */   
/*     */   public static boolean removeItemstack(EntityPlayer player, ItemStack stack, int count) {
/* 244 */     int maxCount = inventoryItemCount(player, stack);
/* 245 */     VarInt varCount = new VarInt();
/* 246 */     varCount.i = count;
/* 247 */     if (varCount.i > maxCount) {
/* 248 */       return false;
/*     */     }
/* 250 */     Consumer<ItemStack> consumer = s -> {
/*     */         if (varCount.i > 0 && ItemStack.func_179545_c(s, stack) && ItemStack.func_77970_a(s, stack)) {
/*     */           if (varCount.i >= s.func_190916_E()) {
/*     */             varCount.i -= s.func_190916_E();
/*     */             
/*     */             s.func_190920_e(0);
/*     */           } else {
/*     */             s.func_190920_e(s.func_190916_E() - varCount.i);
/*     */             
/*     */             varCount.i = 0;
/*     */           } 
/*     */         }
/*     */       };
/*     */     
/* 264 */     player.field_71071_by.field_184439_c.forEach(consumer);
/* 265 */     player.field_71071_by.field_70462_a.forEach(consumer);
/* 266 */     player.field_71071_by.field_70460_b.forEach(consumer);
/* 267 */     return true;
/*     */   }
/*     */ }


/* Location:              C:\Users\DuduM\OneDrive\Área de Trabalho\mw10\modularwarfare-shining-2023.2.4.4f-fix10.jar!\com\modularwarfar\\utility\ReloadHelper.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */