/*     */ package com.modularwarfare.script;
/*     */ 
/*     */ import com.modularwarfare.ModularWarfare;
/*     */ import com.modularwarfare.common.guns.AttachmentPresetEnum;
/*     */ import com.modularwarfare.common.guns.AttachmentType;
/*     */ import com.modularwarfare.common.guns.GunType;
/*     */ import com.modularwarfare.common.guns.ItemAmmo;
/*     */ import com.modularwarfare.common.guns.ItemAttachment;
/*     */ import com.modularwarfare.common.guns.ItemBullet;
/*     */ import com.modularwarfare.common.guns.ItemGun;
/*     */ import com.modularwarfare.common.guns.WeaponFireMode;
/*     */ import java.util.ArrayList;
/*     */ import java.util.HashMap;
/*     */ import net.minecraft.client.resources.I18n;
/*     */ import net.minecraft.init.Items;
/*     */ import net.minecraft.item.Item;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.nbt.NBTTagCompound;
/*     */ import org.lwjgl.input.Keyboard;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class ScriptAPI
/*     */ {
/*  29 */   public Lang Lang = new Lang();
/*  30 */   public Stack Stack = new Stack();
/*  31 */   public Gun Gun = new Gun();
/*  32 */   public Ammo Ammo = new Ammo();
/*  33 */   public Input Input = new Input();
/*  34 */   public Bullet Bullet = new Bullet();
/*     */   
/*     */   public static class Lang {
/*     */     public String format(String key, Object... parms) {
/*  38 */       return I18n.func_135052_a(key, parms);
/*     */     }
/*     */   }
/*     */   
/*     */   public static class Stack {
/*     */     public boolean hasNbt(ItemStack stack) {
/*  44 */       return stack.func_77942_o();
/*     */     }
/*     */     
/*     */     public NBTTagCompound getNbt(ItemStack stack) {
/*  48 */       if (stack.func_77978_p() == null) {
/*  49 */         return new NBTTagCompound();
/*     */       }
/*  51 */       return stack.func_77978_p().func_74737_b();
/*     */     }
/*     */     
/*     */     public ItemStack getStack(int itemid) {
/*  55 */       return new ItemStack(Item.func_150899_d(itemid));
/*     */     }
/*     */     
/*     */     public String getDisplayName(ItemStack stack) {
/*  59 */       return stack.func_82833_r();
/*     */     }
/*     */     
/*     */     public boolean isEmpty(ItemStack stack) {
/*  63 */       return stack.func_190926_b();
/*     */     }
/*     */   }
/*     */   
/*     */   public static class Gun {
/*     */     public boolean isGun(ItemStack stack) {
/*  69 */       return stack.func_77973_b() instanceof ItemGun;
/*     */     }
/*     */     
/*     */     public boolean hasAmmoLoaded(ItemStack stack) {
/*  73 */       return ItemGun.hasAmmoLoaded(stack);
/*     */     }
/*     */     
/*     */     public ItemStack getAmmoStack(ItemStack gunStack) {
/*  77 */       if (hasAmmoLoaded(gunStack)) {
/*  78 */         ItemStack ammoStack = new ItemStack(gunStack.func_77978_p().func_74775_l("ammo"));
/*  79 */         return ammoStack;
/*     */       } 
/*  81 */       return ItemStack.field_190927_a;
/*     */     }
/*     */     
/*     */     public boolean isBulletGun(ItemStack itemStack) {
/*  85 */       if (!isGun(itemStack)) {
/*  86 */         return false;
/*     */       }
/*  88 */       if (((ItemGun)itemStack.func_77973_b()).type.acceptedBullets != null && 
/*  89 */         ((ItemGun)itemStack.func_77973_b()).type.acceptedBullets.length > 0) {
/*  90 */         return true;
/*     */       }
/*  92 */       return false;
/*     */     }
/*     */     
/*     */     public String getGunExtraLore(ItemStack stack) {
/*  96 */       if (!isGun(stack)) {
/*  97 */         return "";
/*     */       }
/*  99 */       return ((ItemGun)stack.func_77973_b()).type.extraLore;
/*     */     }
/*     */     
/*     */     public ArrayList<String> getInstalledAttachments(ItemStack stack) {
/* 103 */       ArrayList<String> list = new ArrayList<>();
/* 104 */       if (!isGun(stack)) {
/* 105 */         return list;
/*     */       }
/* 107 */       for (AttachmentPresetEnum attachment : AttachmentPresetEnum.values()) {
/* 108 */         ItemStack itemStack = GunType.getAttachment(stack, attachment);
/* 109 */         if (itemStack != null && itemStack.func_77973_b() != Items.field_190931_a) {
/* 110 */           AttachmentType attachmentType = ((ItemAttachment)itemStack.func_77973_b()).type;
/* 111 */           list.add(attachmentType.displayName);
/*     */         } 
/*     */       } 
/* 114 */       return list;
/*     */     }
/*     */     
/*     */     public int getAmmoStorage(ItemStack itemStack) {
/* 118 */       if (!isBulletGun(itemStack)) {
/* 119 */         return 0;
/*     */       }
/* 121 */       return ((ItemGun)itemStack.func_77973_b()).type.internalAmmoStorage.intValue();
/*     */     }
/*     */     
/*     */     public int getUsedBulletItem(ItemStack stack) {
/* 125 */       if (!isGun(stack)) {
/* 126 */         return Item.func_150891_b(Items.field_190931_a);
/*     */       }
/* 128 */       if (ItemGun.getUsedBullet(stack, ((ItemGun)stack.func_77973_b()).type) != null) {
/* 129 */         return Item.func_150891_b((Item)ItemGun.getUsedBullet(stack, ((ItemGun)stack.func_77973_b()).type));
/*     */       }
/* 131 */       return Item.func_150891_b(Items.field_190931_a);
/*     */     }
/*     */     
/*     */     public float getGunBulletSpread(ItemStack itemStack) {
/* 135 */       if (!isGun(itemStack)) {
/* 136 */         return 0.0F;
/*     */       }
/* 138 */       return ((ItemGun)itemStack.func_77973_b()).type.bulletSpread;
/*     */     }
/*     */     
/*     */     public float getGunDamage(ItemStack itemStack) {
/* 142 */       if (!isGun(itemStack)) {
/* 143 */         return 0.0F;
/*     */       }
/* 145 */       return ((ItemGun)itemStack.func_77973_b()).type.gunDamage;
/*     */     }
/*     */     
/*     */     public float getGunNumBullets(ItemStack itemStack) {
/* 149 */       if (!isGun(itemStack)) {
/* 150 */         return 0.0F;
/*     */       }
/* 152 */       return ((ItemGun)itemStack.func_77973_b()).type.numBullets;
/*     */     }
/*     */     
/*     */     public WeaponFireMode getFireMode(ItemStack stack) {
/* 156 */       return GunType.getFireMode(stack);
/*     */     }
/*     */     
/*     */     public HashMap<String, ArrayList<String>> getAcceptedAttachment(ItemStack stack) {
/* 160 */       HashMap<String, ArrayList<String>> map = new HashMap<>();
/* 161 */       if (!isGun(stack)) {
/* 162 */         return map;
/*     */       }
/* 164 */       if (((ItemGun)stack.func_77973_b()).type.acceptedAttachments == null) {
/* 165 */         return map;
/*     */       }
/* 167 */       ((ItemGun)stack.func_77973_b()).type.acceptedAttachments.forEach((k, v) -> {
/*     */             if (!map.containsKey(k.typeName)) {
/*     */               map.put(k.typeName, new ArrayList());
/*     */             }
/*     */             
/*     */             v.forEach(());
/*     */           });
/*     */       
/* 175 */       return map;
/*     */     }
/*     */     
/*     */     public ArrayList<String> getAcceptedAmmoOrBullet(ItemStack stack) {
/* 179 */       ArrayList<String> list = new ArrayList<>();
/* 180 */       if (!isGun(stack)) {
/* 181 */         return list;
/*     */       }
/* 183 */       if (((ItemGun)stack.func_77973_b()).type.acceptedAmmo != null) {
/* 184 */         for (String name : ((ItemGun)stack.func_77973_b()).type.acceptedAmmo) {
/* 185 */           list.add(((ItemAmmo)ModularWarfare.ammoTypes.get(name)).type.displayName);
/*     */         }
/*     */       }
/* 188 */       if (((ItemGun)stack.func_77973_b()).type.acceptedBullets != null) {
/* 189 */         for (String name : ((ItemGun)stack.func_77973_b()).type.acceptedBullets) {
/* 190 */           list.add(((ItemBullet)ModularWarfare.bulletTypes.get(name)).type.displayName);
/*     */         }
/*     */       }
/* 193 */       return list;
/*     */     }
/*     */   }
/*     */   
/*     */   public static class Ammo
/*     */   {
/*     */     public boolean isAmmo(ItemStack stack) {
/* 200 */       return stack.func_77973_b() instanceof ItemAmmo;
/*     */     }
/*     */     
/*     */     public int getUsedBulletItem(ItemStack stack) {
/* 204 */       if (!isAmmo(stack)) {
/* 205 */         return Item.func_150891_b(Items.field_190931_a);
/*     */       }
/* 207 */       if (stack.func_77978_p() != null && 
/* 208 */         stack.func_77978_p().func_74764_b("bullet")) {
/* 209 */         ItemStack usedBullet = new ItemStack(stack.func_77978_p().func_74775_l("bullet"));
/* 210 */         return Item.func_150891_b(usedBullet.func_77973_b());
/*     */       } 
/*     */       
/* 213 */       return Item.func_150891_b(Items.field_190931_a);
/*     */     }
/*     */     
/*     */     public ArrayList<String> getAcceptedBullet(ItemStack stack) {
/* 217 */       ArrayList<String> list = new ArrayList<>();
/* 218 */       if (!isAmmo(stack)) {
/* 219 */         return list;
/*     */       }
/* 221 */       if (((ItemAmmo)stack.func_77973_b()).type.subAmmo != null) {
/* 222 */         for (String name : ((ItemAmmo)stack.func_77973_b()).type.subAmmo) {
/* 223 */           list.add(((ItemBullet)ModularWarfare.bulletTypes.get(name)).type.displayName);
/*     */         }
/*     */       }
/* 226 */       return list;
/*     */     }
/*     */     
/*     */     public int getAmmoCapacity(ItemStack stack) {
/* 230 */       return ((ItemAmmo)stack.func_77973_b()).type.ammoCapacity;
/*     */     }
/*     */     
/*     */     public int getMagazineCount(ItemStack stack) {
/* 234 */       return ((ItemAmmo)stack.func_77973_b()).type.magazineCount;
/*     */     }
/*     */   }
/*     */   
/*     */   public static class Bullet
/*     */   {
/*     */     public boolean isBullet(ItemStack stack) {
/* 241 */       return stack.func_77973_b() instanceof ItemBullet;
/*     */     }
/*     */     
/*     */     public float getDamageFactor(ItemStack itemStack) {
/* 245 */       if (itemStack.func_77973_b() instanceof ItemBullet) {
/* 246 */         return ((ItemBullet)itemStack.func_77973_b()).type.bulletDamageFactor;
/*     */       }
/* 248 */       return 1.0F;
/*     */     }
/*     */     
/*     */     public float getAccuracyFactor(ItemStack itemStack) {
/* 252 */       if (itemStack.func_77973_b() instanceof ItemBullet) {
/* 253 */         return ((ItemBullet)itemStack.func_77973_b()).type.bulletAccuracyFactor;
/*     */       }
/* 255 */       return 1.0F;
/*     */     }
/*     */   }
/*     */   
/*     */   public static class Input {
/*     */     public boolean isKeyHolding(int key) {
/* 261 */       return Keyboard.isKeyDown(key);
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\DuduM\OneDrive\Área de Trabalho\mw9\modularwarfare-shining-2023.2.4.4f-fix9.jar!\com\modularwarfare\script\ScriptAPI.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */