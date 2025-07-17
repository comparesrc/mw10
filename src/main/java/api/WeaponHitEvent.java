/*    */ package com.modularwarfare.api;
/*    */ 
/*    */ import com.modularwarfare.common.guns.ItemGun;
/*    */ import java.util.List;
/*    */ import net.minecraft.entity.Entity;
/*    */ import net.minecraft.entity.player.EntityPlayer;
/*    */ import net.minecraft.item.ItemStack;
/*    */ import net.minecraftforge.fml.common.eventhandler.Cancelable;
/*    */ 
/*    */ 
/*    */ @Deprecated
/*    */ public class WeaponHitEvent
/*    */   extends WeaponEvent
/*    */ {
/*    */   public WeaponHitEvent(EntityPlayer entityPlayer, ItemStack stackWeapon, ItemGun itemWeapon) {
/* 16 */     super(entityPlayer, stackWeapon, itemWeapon);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   @Cancelable
/*    */   public static class Pre
/*    */     extends WeaponHitEvent
/*    */   {
/*    */     private boolean isHeadshot;
/*    */     
/*    */     private float damage;
/*    */     
/*    */     private Entity victim;
/*    */ 
/*    */     
/*    */     public Pre(EntityPlayer entityPlayer, ItemStack stackWeapon, ItemGun itemWeapon, boolean isHeadshot, float damage, Entity victim) {
/* 33 */       super(entityPlayer, stackWeapon, itemWeapon);
/* 34 */       this.isHeadshot = isHeadshot;
/* 35 */       this.damage = damage;
/*    */       
/* 37 */       this.victim = victim;
/*    */     }
/*    */     
/*    */     public float getDamage() {
/* 41 */       return this.damage;
/*    */     }
/*    */     
/*    */     public void setDamage(float damage) {
/* 45 */       this.damage = damage;
/*    */     }
/*    */     
/*    */     public boolean isHeadhot() {
/* 49 */       return this.isHeadshot;
/*    */     }
/*    */     
/*    */     public Entity getVictim() {
/* 53 */       return this.victim;
/*    */     }
/*    */     
/*    */     public void setVictim(Entity entity) {
/* 57 */       this.victim = this.victim;
/*    */     }
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public static class Post
/*    */     extends WeaponHitEvent
/*    */   {
/*    */     private List<Entity> affectedEntities;
/*    */ 
/*    */     
/*    */     private float finalDamage;
/*    */ 
/*    */     
/*    */     public Post(EntityPlayer entityPlayer, ItemStack stackWeapon, ItemGun itemWeapon, List<Entity> affectedEntities, float finalDamage) {
/* 73 */       super(entityPlayer, stackWeapon, itemWeapon);
/* 74 */       this.affectedEntities = affectedEntities;
/* 75 */       this.finalDamage = finalDamage;
/*    */     }
/*    */     
/*    */     public List<Entity> getAffectedEntities() {
/* 79 */       return this.affectedEntities;
/*    */     }
/*    */     
/*    */     public void setAffectedEntities(List<Entity> updatedList) {
/* 83 */       this.affectedEntities = updatedList;
/*    */     }
/*    */     
/*    */     public float getFinalDamage() {
/* 87 */       return this.finalDamage;
/*    */     }
/*    */   }
/*    */ }


/* Location:              C:\Users\DuduM\OneDrive\Área de Trabalho\mw9\modularwarfare-shining-2023.2.4.4f-fix9.jar!\com\modularwarfare\api\WeaponHitEvent.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */