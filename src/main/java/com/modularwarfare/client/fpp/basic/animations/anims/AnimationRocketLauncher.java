/*    */ package com.modularwarfare.client.fpp.basic.animations.anims;
/*    */ 
/*    */ import com.modularwarfare.api.WeaponAnimation;
/*    */ import com.modularwarfare.client.fpp.basic.animations.AnimStateMachine;
/*    */ import com.modularwarfare.client.fpp.basic.animations.ReloadType;
/*    */ import com.modularwarfare.client.fpp.basic.animations.StateEntry;
/*    */ import com.modularwarfare.client.fpp.basic.animations.StateType;
/*    */ import com.modularwarfare.client.model.ModelGun;
/*    */ import java.util.ArrayList;
/*    */ import org.lwjgl.opengl.GL11;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class AnimationRocketLauncher
/*    */   extends WeaponAnimation
/*    */ {
/*    */   public void onGunAnimation(float tiltProgress, AnimStateMachine animation) {
/* 23 */     GL11.glTranslatef(0.0F * tiltProgress, 0.0F, 0.0F);
/*    */     
/* 25 */     GL11.glTranslatef(0.0F, 0.0F * tiltProgress, 0.0F);
/*    */     
/* 27 */     GL11.glTranslatef(0.0F, 0.0F, -0.2F * tiltProgress);
/*    */     
/* 29 */     GL11.glRotatef(10.0F * tiltProgress, 1.0F, 0.0F, 0.0F);
/*    */     
/* 31 */     GL11.glRotatef(-10.0F * tiltProgress, 0.0F, 1.0F, 0.0F);
/*    */     
/* 33 */     GL11.glRotatef(15.0F * tiltProgress, 0.0F, 0.0F, 1.0F);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void onAmmoAnimation(ModelGun gunModel, float ammoProgress, int reloadAmmoCount, AnimStateMachine animation) {
/* 40 */     GL11.glTranslatef(ammoProgress * -0.75F, 0.0F, 0.0F);
/*    */     
/* 42 */     GL11.glTranslatef(0.0F, ammoProgress * -8.0F, 0.0F);
/*    */     
/* 44 */     GL11.glTranslatef(0.0F, 0.0F, ammoProgress * 0.0F);
/*    */     
/* 46 */     GL11.glRotatef(30.0F * ammoProgress, 1.0F, 0.0F, 0.0F);
/*    */     
/* 48 */     GL11.glRotatef(0.0F * ammoProgress, 0.0F, 1.0F, 0.0F);
/*    */     
/* 50 */     GL11.glRotatef(-20.0F * ammoProgress, 0.0F, 0.0F, 1.0F);
/*    */   }
/*    */ 
/*    */   
/*    */   public ArrayList<StateEntry> getReloadStates(ReloadType reloadType, int reloadCount) {
/* 55 */     ArrayList<StateEntry> states = new ArrayList<>();
/* 56 */     states.add(new StateEntry(StateType.Tilt, 0.2F, 0.0F, StateEntry.MathType.Add));
/* 57 */     states.add(new StateEntry(StateType.Unload, 0.2F, 0.0F, StateEntry.MathType.Add));
/* 58 */     states.add(new StateEntry(StateType.Load, 0.2F, 1.0F, StateEntry.MathType.Sub));
/* 59 */     states.add(new StateEntry(StateType.Untilt, 0.2F, 1.0F, StateEntry.MathType.Sub));
/* 60 */     states.add(new StateEntry(StateType.Charge, 0.18F, 1.0F, StateEntry.MathType.Sub));
/* 61 */     states.add(new StateEntry(StateType.Uncharge, 0.02F, 0.0F, StateEntry.MathType.Add));
/* 62 */     return states;
/*    */   }
/*    */ }


/* Location:              C:\Users\DuduM\OneDrive\Área de Trabalho\mw10\modularwarfare-shining-2023.2.4.4f-fix10.jar!\com\modularwarfare\client\fpp\basic\animations\anims\AnimationRocketLauncher.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */