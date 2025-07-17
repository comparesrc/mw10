/*    */ package com.modularwarfare.client.fpp.basic.animations.anims;
/*    */ 
/*    */ import com.modularwarfare.api.WeaponAnimation;
/*    */ import com.modularwarfare.client.fpp.basic.animations.AnimStateMachine;
/*    */ import com.modularwarfare.client.fpp.basic.animations.ReloadType;
/*    */ import com.modularwarfare.client.fpp.basic.animations.StateEntry;
/*    */ import com.modularwarfare.client.fpp.basic.animations.StateType;
/*    */ import com.modularwarfare.client.model.ModelGun;
/*    */ import com.modularwarfare.common.guns.GunType;
/*    */ import com.modularwarfare.common.guns.WeaponType;
/*    */ import java.util.ArrayList;
/*    */ import net.minecraft.util.math.MathHelper;
/*    */ import org.lwjgl.opengl.GL11;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class AnimationSniperTop
/*    */   extends WeaponAnimation
/*    */ {
/*    */   public void onGunAnimation(float tiltProgress, AnimStateMachine animation) {
/* 27 */     GL11.glTranslatef(0.0F * tiltProgress, 0.0F, 0.0F);
/*    */     
/* 29 */     GL11.glTranslatef(0.0F, 0.0F * tiltProgress, 0.0F);
/*    */     
/* 31 */     GL11.glTranslatef(0.0F, 0.0F, -0.2F * tiltProgress);
/*    */     
/* 33 */     GL11.glRotatef(10.0F * tiltProgress, 1.0F, 0.0F, 0.0F);
/*    */     
/* 35 */     GL11.glRotatef(-10.0F * tiltProgress, 0.0F, 1.0F, 0.0F);
/*    */     
/* 37 */     GL11.glRotatef(15.0F * tiltProgress, 0.0F, 0.0F, 1.0F);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public void onAmmoAnimation(ModelGun gunModel, float ammoProgress, int reloadAmmoCount, AnimStateMachine animation) {
/* 43 */     float multiAmmoPosition = ammoProgress * 1.0F;
/* 44 */     int bulletNum = MathHelper.func_76141_d(multiAmmoPosition);
/* 45 */     float bulletProgress = multiAmmoPosition - bulletNum;
/* 46 */     float modelScale = gunModel.config.extra.modelScale;
/*    */     
/* 48 */     GL11.glRotatef(bulletProgress * 55.0F, 0.0F, 1.0F, 0.0F);
/* 49 */     GL11.glRotatef(bulletProgress * 95.0F, 0.0F, 0.0F, 1.0F);
/* 50 */     GL11.glTranslatef(bulletProgress * -0.1F * 1.0F / modelScale, bulletProgress * 1.0F * 1.0F / modelScale, bulletProgress * 0.5F * 1.0F / modelScale);
/*    */   }
/*    */ 
/*    */   
/*    */   public ArrayList<StateEntry> getReloadStates(ReloadType reloadType, int reloadCount) {
/* 55 */     ArrayList<StateEntry> states = new ArrayList<>();
/*    */     
/* 57 */     states.add(new StateEntry(StateType.Tilt, 0.15F, 0.0F, StateEntry.MathType.Add));
/*    */ 
/*    */     
/* 60 */     if (reloadType == ReloadType.Load || reloadType == ReloadType.Full) {
/* 61 */       states.add(new StateEntry(StateType.Load, 0.35F, 1.0F, StateEntry.MathType.Sub));
/*    */     }
/*    */     
/* 64 */     states.add(new StateEntry(StateType.Untilt, 0.15F, 1.0F, StateEntry.MathType.Sub));
/* 65 */     states.add(new StateEntry(StateType.MoveHands, 0.1F, 0.0F, StateEntry.MathType.Add));
/* 66 */     states.add(new StateEntry(StateType.Charge, 0.1F, 1.0F, StateEntry.MathType.Sub));
/* 67 */     states.add(new StateEntry(StateType.Uncharge, 0.1F, 0.0F, StateEntry.MathType.Add));
/* 68 */     states.add(new StateEntry(StateType.ReturnHands, 0.1F, 1.0F, StateEntry.MathType.Sub));
/* 69 */     return states;
/*    */   }
/*    */ 
/*    */   
/*    */   public ArrayList<StateEntry> getShootStates(ModelGun gunModel, GunType gunType) {
/* 74 */     ArrayList<StateEntry> states = new ArrayList<>();
/* 75 */     if (gunType.weaponType == WeaponType.BoltSniper) {
/*    */       
/* 77 */       states.add(new StateEntry(StateType.MoveHands, 0.15F, 0.0F, StateEntry.MathType.Add));
/* 78 */       states.add(new StateEntry(StateType.Charge, 0.35F, 1.0F, StateEntry.MathType.Sub));
/* 79 */       states.add(new StateEntry(StateType.Uncharge, 0.35F, 0.0F, StateEntry.MathType.Add));
/* 80 */       states.add(new StateEntry(StateType.ReturnHands, 0.15F, 1.0F, StateEntry.MathType.Sub));
/*    */     } 
/*    */     
/* 83 */     return states;
/*    */   }
/*    */ }


/* Location:              C:\Users\DuduM\OneDrive\Área de Trabalho\mw10\modularwarfare-shining-2023.2.4.4f-fix10.jar!\com\modularwarfare\client\fpp\basic\animations\anims\AnimationSniperTop.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */