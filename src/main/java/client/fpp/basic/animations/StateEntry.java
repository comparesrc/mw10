/*    */ package com.modularwarfare.client.fpp.basic.animations;
/*    */ import com.modularwarfare.client.ClientRenderHooks;
/*    */ import com.modularwarfare.common.guns.ItemGun;
/*    */ import com.modularwarfare.common.guns.WeaponSoundType;
/*    */ import com.modularwarfare.common.guns.WeaponType;
/*    */ import com.modularwarfare.utility.NumberHelper;
/*    */ import net.minecraft.client.Minecraft;
/*    */ import net.minecraft.entity.EntityLivingBase;
/*    */ import net.minecraft.entity.player.EntityPlayer;
/*    */ import net.minecraft.inventory.EntityEquipmentSlot;
/*    */ 
/*    */ public class StateEntry {
/* 13 */   public static float smoothing = 1.0F;
/* 14 */   public float stateTime = 0.0F;
/* 15 */   public float currentValue = 0.0F;
/* 16 */   public float lastValue = 0.0F;
/*    */   public StateType stateType;
/*    */   public float cutOffTime;
/*    */   public boolean finished = false;
/*    */   private MathType mathType;
/*    */   private float minValue;
/*    */   private float incrementValue;
/*    */   private float startingValue;
/*    */   private float operationCount;
/*    */   
/*    */   public StateEntry(StateType stateType, float stateTime, float startingValue, MathType mathType) {
/* 27 */     this(stateType, stateTime, startingValue, mathType, 1);
/*    */   }
/*    */   
/*    */   public StateEntry(StateType stateType, float stateTime, float startingValue, MathType mathType, int operationCount) {
/* 31 */     this.stateTime = stateTime;
/* 32 */     this.startingValue = this.currentValue = this.lastValue = startingValue;
/* 33 */     this.mathType = mathType;
/* 34 */     this.stateType = stateType;
/* 35 */     this.minValue = 0.0F;
/* 36 */     this.incrementValue = 1.0F;
/* 37 */     this.operationCount = operationCount;
/*    */   }
/*    */   
/*    */   public void onTick(float reloadTime) {
/* 41 */     this.lastValue = this.currentValue;
/* 42 */     if (this.mathType == MathType.Add) {
/* 43 */       this.currentValue += this.incrementValue * smoothing / reloadTime * this.stateTime * this.operationCount;
/* 44 */     } else if (this.mathType == MathType.Sub) {
/* 45 */       this.currentValue -= this.incrementValue * smoothing / reloadTime * this.stateTime * this.operationCount;
/*    */     } 
/* 47 */     this.currentValue = NumberHelper.clamp(this.currentValue, this.minValue, 0.999F);
/*    */     
/* 49 */     if ((this.currentValue == 1.0F || this.currentValue == 0.0F) && this.operationCount > 1.0F) {
/* 50 */       this.currentValue = this.startingValue;
/* 51 */       if ((Minecraft.func_71410_x()).field_71439_g.func_184582_a(EntityEquipmentSlot.MAINHAND) != null && 
/* 52 */         (Minecraft.func_71410_x()).field_71439_g.func_184582_a(EntityEquipmentSlot.MAINHAND).func_77973_b() instanceof ItemGun) {
/* 53 */         ItemGun gun = (ItemGun)(Minecraft.func_71410_x()).field_71439_g.func_184582_a(EntityEquipmentSlot.MAINHAND).func_77973_b();
/* 54 */         if (gun.type.weaponType == WeaponType.Shotgun || gun.type.weaponType == WeaponType.Revolver) {
/* 55 */           gun.type.playClientSound((EntityPlayer)(Minecraft.func_71410_x()).field_71439_g, WeaponSoundType.BulletLoad);
/*    */         }
/*    */       } 
/*    */       
/* 59 */       (ClientRenderHooks.getAnimMachine((EntityLivingBase)(Minecraft.func_71410_x()).field_71439_g)).bulletsToRender++;
/* 60 */       System.out.println("test");
/* 61 */       this.operationCount--;
/*    */     } 
/*    */   }
/*    */   
/*    */   public enum MathType {
/* 66 */     Add,
/* 67 */     Sub;
/*    */   }
/*    */ }


/* Location:              C:\Users\DuduM\OneDrive\Área de Trabalho\mw9\modularwarfare-shining-2023.2.4.4f-fix9.jar!\com\modularwarfare\client\fpp\basic\animations\StateEntry.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */