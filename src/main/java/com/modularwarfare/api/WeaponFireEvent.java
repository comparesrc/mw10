/*     */ package com.modularwarfare.api;
/*     */ 
/*     */ import com.modularwarfare.common.guns.GunType;
/*     */ import com.modularwarfare.common.guns.ItemGun;
/*     */ import java.util.List;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraftforge.fml.common.eventhandler.Cancelable;
/*     */ import net.minecraftforge.fml.common.eventhandler.Event.HasResult;
/*     */ 
/*     */ 
/*     */ @HasResult
/*     */ @Deprecated
/*     */ public class WeaponFireEvent
/*     */   extends WeaponEvent
/*     */ {
/*     */   public WeaponFireEvent(EntityPlayer entityPlayer, ItemStack stackWeapon, ItemGun itemWeapon) {
/*  19 */     super(entityPlayer, stackWeapon, itemWeapon);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Cancelable
/*     */   public static class PreClient
/*     */     extends WeaponFireEvent
/*     */   {
/*     */     private int weaponRange;
/*     */ 
/*     */ 
/*     */     
/*     */     public PreClient(EntityPlayer entityPlayer, ItemStack stackWeapon, ItemGun itemWeapon, int weaponRange) {
/*  34 */       super(entityPlayer, stackWeapon, itemWeapon);
/*  35 */       this.weaponRange = weaponRange;
/*     */     }
/*     */     
/*     */     public int getWeaponRange() {
/*  39 */       return this.weaponRange;
/*     */     }
/*     */     
/*     */     public void setWeaponRange(int updatedRange) {
/*  43 */       this.weaponRange = updatedRange;
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Cancelable
/*     */   public static class PreServer
/*     */     extends WeaponFireEvent
/*     */   {
/*     */     private int weaponRange;
/*     */ 
/*     */ 
/*     */     
/*     */     public PreServer(EntityPlayer entityPlayer, ItemStack stackWeapon, ItemGun itemWeapon, int weaponRange) {
/*  59 */       super(entityPlayer, stackWeapon, itemWeapon);
/*  60 */       this.weaponRange = weaponRange;
/*     */     }
/*     */     
/*     */     public int getWeaponRange() {
/*  64 */       return this.weaponRange;
/*     */     }
/*     */     
/*     */     public void setWeaponRange(int updatedRange) {
/*  68 */       this.weaponRange = updatedRange;
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static class Post
/*     */     extends WeaponFireEvent
/*     */   {
/*     */     private List<Entity> affectedEntities;
/*     */     
/*     */     private int fireTickDelay;
/*     */     
/*     */     private float damage;
/*     */ 
/*     */     
/*     */     public Post(EntityPlayer entityPlayer, ItemStack stackWeapon, ItemGun itemWeapon, List<Entity> affectedEntities) {
/*  85 */       super(entityPlayer, stackWeapon, itemWeapon);
/*  86 */       this.affectedEntities = affectedEntities;
/*     */       
/*  88 */       GunType type = itemWeapon.type;
/*     */       
/*  90 */       this.damage = type.gunDamage;
/*     */       
/*  92 */       this.fireTickDelay = type.fireTickDelay;
/*     */     }
/*     */     
/*     */     public List<Entity> getAffectedEntities() {
/*  96 */       return this.affectedEntities;
/*     */     }
/*     */     
/*     */     public void setAffectedEntities(List<Entity> updatedList) {
/* 100 */       this.affectedEntities = updatedList;
/*     */     }
/*     */     
/*     */     public float getDamage() {
/* 104 */       return this.damage;
/*     */     }
/*     */     
/*     */     public void setDamage(float updatedDamage) {
/* 108 */       this.damage = updatedDamage;
/*     */     }
/*     */     
/*     */     public float getTickDelay() {
/* 112 */       return this.fireTickDelay;
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\DuduM\OneDrive\Área de Trabalho\mw10\modularwarfare-shining-2023.2.4.4f-fix10.jar!\com\modularwarfare\api\WeaponFireEvent.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */