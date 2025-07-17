/*    */ package com.modularwarfare.api;
/*    */ 
/*    */ import com.modularwarfare.client.fpp.basic.animations.AnimStateMachine;
/*    */ import com.modularwarfare.client.fpp.basic.animations.ReloadType;
/*    */ import com.modularwarfare.client.fpp.basic.animations.StateEntry;
/*    */ import com.modularwarfare.client.fpp.basic.animations.StateType;
/*    */ import com.modularwarfare.client.model.ModelGun;
/*    */ import com.modularwarfare.common.guns.GunType;
/*    */ import java.util.ArrayList;
/*    */ import org.lwjgl.util.vector.Vector3f;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class WeaponAnimation
/*    */ {
/*    */   public Vector3f ammoLoadOffset;
/*    */   
/*    */   public void onGunAnimation(float reloadRotate, AnimStateMachine animation) {}
/*    */   
/*    */   public void onAmmoAnimation(ModelGun gunModel, float ammoPosition, int reloadAmmoCount, AnimStateMachine animation) {}
/*    */   
/*    */   public ArrayList<StateEntry> getReloadStates(ReloadType reloadType, int reloadCount) {
/* 27 */     ArrayList<StateEntry> states = new ArrayList<>();
/* 28 */     states.add(new StateEntry(StateType.Tilt, 0.15F, 0.0F, StateEntry.MathType.Add));
/* 29 */     if (reloadType == ReloadType.Unload || reloadType == ReloadType.Full)
/* 30 */       states.add(new StateEntry(StateType.Unload, 0.35F, 0.0F, StateEntry.MathType.Add)); 
/* 31 */     if (reloadType == ReloadType.Load || reloadType == ReloadType.Full)
/* 32 */       states.add(new StateEntry(StateType.Load, 0.35F, 1.0F, StateEntry.MathType.Sub, reloadCount)); 
/* 33 */     states.add(new StateEntry(StateType.Untilt, 0.15F, 1.0F, StateEntry.MathType.Sub));
/* 34 */     return states;
/*    */   }
/*    */   
/*    */   public ArrayList<StateEntry> getShootStates(ModelGun gunModel, GunType gunType) {
/* 38 */     ArrayList<StateEntry> states = new ArrayList<>();
/*    */     
/* 40 */     if (gunModel.staticModel != null && 
/* 41 */       gunModel.staticModel.getPart("pumpModel") != null) {
/* 42 */       states.add(new StateEntry(StateType.PumpOut, 0.5F, 1.0F, StateEntry.MathType.Sub));
/* 43 */       states.add(new StateEntry(StateType.PumpIn, 0.5F, 0.0F, StateEntry.MathType.Add));
/*    */     } 
/*    */     
/* 46 */     return states;
/*    */   }
/*    */ }


/* Location:              C:\Users\DuduM\OneDrive\Área de Trabalho\mw9\modularwarfare-shining-2023.2.4.4f-fix9.jar!\com\modularwarfare\api\WeaponAnimation.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */