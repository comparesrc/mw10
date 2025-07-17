/*     */ package com.modularwarfare.api;
/*     */ 
/*     */ import com.modularwarfare.common.guns.GunType;
/*     */ import com.modularwarfare.common.guns.ItemGun;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraftforge.fml.common.eventhandler.Cancelable;
/*     */ 
/*     */ public class WeaponReloadEvent
/*     */   extends WeaponEvent
/*     */ {
/*     */   public WeaponReloadEvent(EntityPlayer entityPlayer, ItemStack stackWeapon, ItemGun itemWeapon) {
/*  13 */     super(entityPlayer, stackWeapon, itemWeapon);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   @Cancelable
/*     */   public static class Pre
/*     */     extends WeaponReloadEvent
/*     */   {
/*     */     private final boolean offhandReload;
/*     */     
/*     */     private final boolean multiMagReload;
/*     */     
/*     */     private int reloadTime;
/*     */ 
/*     */     
/*     */     public Pre(EntityPlayer entityPlayer, ItemStack stackWeapon, ItemGun itemWeapon, boolean offhandReload, boolean multiMagReload) {
/*  30 */       super(entityPlayer, stackWeapon, itemWeapon);
/*  31 */       this.offhandReload = offhandReload;
/*  32 */       this.multiMagReload = multiMagReload;
/*     */       
/*  34 */       GunType type = itemWeapon.type;
/*  35 */       this.reloadTime = (int)(offhandReload ? ((type.offhandReloadTime != null) ? (type.offhandReloadTime.intValue() * 0.8F) : type.reloadTime) : type.reloadTime);
/*     */     }
/*     */     
/*     */     public int getReloadTime() {
/*  39 */       return this.reloadTime;
/*     */     }
/*     */     
/*     */     public void setReloadTime(int updatedReloadTime) {
/*  43 */       this.reloadTime = updatedReloadTime;
/*     */     }
/*     */     
/*     */     public boolean isOffhandReload() {
/*  47 */       return this.offhandReload;
/*     */     }
/*     */     
/*     */     public boolean isMultiMagReload() {
/*  51 */       return this.multiMagReload;
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public static class Post
/*     */     extends WeaponReloadEvent
/*     */   {
/*     */     private final boolean offhandReload;
/*     */     
/*     */     private final boolean multiMagReload;
/*     */     
/*     */     private final boolean loadOnly;
/*     */     
/*     */     private final boolean unloadOnly;
/*     */     
/*     */     private final int reloadAmount;
/*     */     private int reloadTime;
/*     */     
/*     */     public Post(EntityPlayer entityPlayer, ItemStack stackWeapon, ItemGun itemWeapon, boolean offhandReload, boolean multiMagReload, boolean loadOnly, boolean unloadOnly, int reloadTime, int reloadAmount) {
/*  71 */       super(entityPlayer, stackWeapon, itemWeapon);
/*  72 */       this.offhandReload = offhandReload;
/*  73 */       this.multiMagReload = multiMagReload;
/*  74 */       this.loadOnly = loadOnly;
/*  75 */       this.unloadOnly = unloadOnly;
/*  76 */       this.reloadTime = reloadTime;
/*  77 */       this.reloadAmount = reloadAmount;
/*     */     }
/*     */     
/*     */     public Post(EntityPlayer entityPlayer, ItemStack stackWeapon, ItemGun itemWeapon, boolean offhandReload, boolean multiMagReload, boolean loadOnly, boolean unloadOnly, int reloadTime) {
/*  81 */       this(entityPlayer, stackWeapon, itemWeapon, offhandReload, multiMagReload, loadOnly, unloadOnly, reloadTime, 1);
/*     */     }
/*     */     
/*     */     public Post(EntityPlayer entityPlayer, ItemStack stackWeapon, ItemGun itemWeapon, boolean offhandReload, boolean loadOnly, boolean unloadOnly, int reloadTime, int reloadAmount) {
/*  85 */       this(entityPlayer, stackWeapon, itemWeapon, offhandReload, false, loadOnly, unloadOnly, reloadTime, reloadAmount);
/*     */     }
/*     */     
/*     */     public boolean isOffhandReload() {
/*  89 */       return this.offhandReload;
/*     */     }
/*     */     
/*     */     public boolean isMultiMagReload() {
/*  93 */       return this.multiMagReload;
/*     */     }
/*     */     
/*     */     public boolean isLoadOnly() {
/*  97 */       return this.loadOnly;
/*     */     }
/*     */     
/*     */     public boolean isUnload() {
/* 101 */       return this.unloadOnly;
/*     */     }
/*     */     
/*     */     public int getReloadTime() {
/* 105 */       return this.reloadTime;
/*     */     }
/*     */     
/*     */     public int getReloadCount() {
/* 109 */       return this.reloadAmount;
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\DuduM\OneDrive\Área de Trabalho\mw10\modularwarfare-shining-2023.2.4.4f-fix10.jar!\com\modularwarfare\api\WeaponReloadEvent.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */