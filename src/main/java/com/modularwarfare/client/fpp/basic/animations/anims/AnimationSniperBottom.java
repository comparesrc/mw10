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
/*    */ 
/*    */ 
/*    */ public class AnimationSniperBottom
/*    */   extends WeaponAnimation
/*    */ {
/*    */   public void onGunAnimation(float tiltProgress, AnimStateMachine animation) {
/* 29 */     GL11.glTranslatef(0.0F * tiltProgress, 0.0F, 0.0F);
/*    */     
/* 31 */     GL11.glTranslatef(0.0F, 0.0F * tiltProgress, 0.0F);
/*    */     
/* 33 */     GL11.glTranslatef(0.0F, 0.0F, -0.2F * tiltProgress);
/*    */     
/* 35 */     GL11.glRotatef(10.0F * tiltProgress, 1.0F, 0.0F, 0.0F);
/*    */     
/* 37 */     GL11.glRotatef(-10.0F * tiltProgress, 0.0F, 1.0F, 0.0F);
/*    */     
/* 39 */     GL11.glRotatef(15.0F * tiltProgress, 0.0F, 0.0F, 1.0F);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public void onAmmoAnimation(ModelGun gunModel, float ammoProgress, int reloadAmmoCount, AnimStateMachine animation) {
/* 45 */     float multiAmmoPosition = ammoProgress * 1.0F;
/* 46 */     int bulletNum = MathHelper.func_76141_d(multiAmmoPosition);
/* 47 */     float bulletProgress = multiAmmoPosition - bulletNum;
/*    */ 
/*    */ 
/*    */     
/* 51 */     GL11.glTranslatef(ammoProgress * -0.75F, 0.0F, 0.0F);
/*    */     
/* 53 */     GL11.glTranslatef(0.0F, ammoProgress * -8.0F, 0.0F);
/*    */     
/* 55 */     GL11.glTranslatef(0.0F, 0.0F, ammoProgress * 0.0F);
/*    */     
/* 57 */     GL11.glRotatef(30.0F * ammoProgress, 1.0F, 0.0F, 0.0F);
/*    */     
/* 59 */     GL11.glRotatef(0.0F * ammoProgress, 0.0F, 1.0F, 0.0F);
/*    */     
/* 61 */     GL11.glRotatef(-90.0F * ammoProgress, 0.0F, 0.0F, 1.0F);
/*    */   }
/*    */ 
/*    */   
/*    */   public ArrayList<StateEntry> getReloadStates(ReloadType reloadType, int reloadCount) {
/* 66 */     ArrayList<StateEntry> states = new ArrayList<>();
/*    */     
/* 68 */     states.add(new StateEntry(StateType.Tilt, 0.15F, 0.0F, StateEntry.MathType.Add));
/* 69 */     if (reloadType == ReloadType.Unload || reloadType == ReloadType.Full)
/* 70 */       states.add(new StateEntry(StateType.Unload, 0.15F, 0.0F, StateEntry.MathType.Add)); 
/* 71 */     if (reloadType == ReloadType.Load || reloadType == ReloadType.Full) {
/* 72 */       states.add(new StateEntry(StateType.Load, 0.15F, 1.0F, StateEntry.MathType.Sub, reloadCount));
/*    */     }
/* 74 */     states.add(new StateEntry(StateType.Untilt, 0.15F, 1.0F, StateEntry.MathType.Sub));
/* 75 */     states.add(new StateEntry(StateType.MoveHands, 0.1F, 0.0F, StateEntry.MathType.Add));
/* 76 */     states.add(new StateEntry(StateType.Charge, 0.1F, 1.0F, StateEntry.MathType.Sub));
/* 77 */     states.add(new StateEntry(StateType.Uncharge, 0.1F, 0.0F, StateEntry.MathType.Add));
/* 78 */     states.add(new StateEntry(StateType.ReturnHands, 0.1F, 1.0F, StateEntry.MathType.Sub));
/* 79 */     return states;
/*    */   }
/*    */ 
/*    */   
/*    */   public ArrayList<StateEntry> getShootStates(ModelGun gunModel, GunType gunType) {
/* 84 */     ArrayList<StateEntry> states = new ArrayList<>();
/* 85 */     if (gunType.weaponType == WeaponType.BoltSniper) {
/* 86 */       states.add(new StateEntry(StateType.MoveHands, 0.15F, 0.0F, StateEntry.MathType.Add));
/* 87 */       states.add(new StateEntry(StateType.Charge, 0.35F, 1.0F, StateEntry.MathType.Sub));
/* 88 */       states.add(new StateEntry(StateType.Uncharge, 0.35F, 0.0F, StateEntry.MathType.Add));
/* 89 */       states.add(new StateEntry(StateType.ReturnHands, 0.15F, 1.0F, StateEntry.MathType.Sub));
/*    */     } 
/* 91 */     return states;
/*    */   }
/*    */ }


/* Location:              C:\Users\DuduM\OneDrive\Área de Trabalho\mw10\modularwarfare-shining-2023.2.4.4f-fix10.jar!\com\modularwarfare\client\fpp\basic\animations\anims\AnimationSniperBottom.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */