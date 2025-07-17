/*    */ package com.modularwarfare.client.fpp.basic.animations.anims;
/*    */ 
/*    */ import com.modularwarfare.api.WeaponAnimation;
/*    */ import com.modularwarfare.client.fpp.basic.animations.AnimStateMachine;
/*    */ import com.modularwarfare.client.fpp.basic.animations.ReloadType;
/*    */ import com.modularwarfare.client.fpp.basic.animations.StateEntry;
/*    */ import com.modularwarfare.client.fpp.basic.animations.StateType;
/*    */ import com.modularwarfare.client.model.ModelGun;
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
/*    */ public class AnimationRevolver
/*    */   extends WeaponAnimation
/*    */ {
/*    */   public void onGunAnimation(float tiltProgress, AnimStateMachine animation) {
/* 24 */     GL11.glTranslatef(0.2F * tiltProgress, 0.0F, 0.0F);
/*    */     
/* 26 */     GL11.glTranslatef(0.0F, 0.1F * tiltProgress, 0.0F);
/*    */     
/* 28 */     GL11.glTranslatef(0.0F, 0.0F, -0.1F * tiltProgress);
/*    */     
/* 30 */     GL11.glRotatef(-10.0F * tiltProgress, 1.0F, 0.0F, 0.0F);
/*    */     
/* 32 */     GL11.glRotatef(-10.0F * tiltProgress, 0.0F, 1.0F, 0.0F);
/*    */     
/* 34 */     GL11.glRotatef(25.0F * tiltProgress, 0.0F, 0.0F, 1.0F);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public void onAmmoAnimation(ModelGun gunModel, float ammoProgress, int reloadAmmoCount, AnimStateMachine animation) {
/* 40 */     float multiAmmoPosition = ammoProgress * reloadAmmoCount;
/* 41 */     int bulletNum = MathHelper.func_76141_d(multiAmmoPosition);
/* 42 */     float bulletProgress = multiAmmoPosition - bulletNum;
/*    */ 
/*    */     
/* 45 */     GL11.glTranslatef(bulletProgress * -0.75F, 0.0F, 0.0F);
/*    */     
/* 47 */     GL11.glTranslatef(0.0F, bulletProgress * -8.0F, 0.0F);
/*    */     
/* 49 */     GL11.glTranslatef(0.0F, 0.0F, bulletProgress * 0.0F);
/*    */     
/* 51 */     GL11.glRotatef(30.0F * bulletProgress, 1.0F, 0.0F, 0.0F);
/*    */     
/* 53 */     GL11.glRotatef(0.0F * bulletProgress, 0.0F, 1.0F, 0.0F);
/*    */     
/* 55 */     GL11.glRotatef(-20.0F * bulletProgress, 0.0F, 0.0F, 1.0F);
/*    */   }
/*    */ 
/*    */   
/*    */   public ArrayList<StateEntry> getReloadStates(ReloadType reloadType, int reloadCount) {
/* 60 */     reloadCount++;
/* 61 */     if (reloadCount >= 6) {
/* 62 */       reloadCount++;
/*    */     }
/* 64 */     ArrayList<StateEntry> states = new ArrayList<>();
/* 65 */     states.add(new StateEntry(StateType.Tilt, 0.2F, 0.7F, StateEntry.MathType.Add));
/* 66 */     if (reloadCount >= 6) {
/* 67 */       states.add(new StateEntry(StateType.Load, 0.35F, 1.0F, StateEntry.MathType.Sub, reloadCount));
/* 68 */     } else if (reloadCount >= 3 && reloadCount <= 5) {
/* 69 */       states.add(new StateEntry(StateType.Load, 0.15F, 1.0F, StateEntry.MathType.Sub, reloadCount));
/* 70 */     } else if (reloadCount >= 0 && reloadCount <= 2) {
/* 71 */       states.add(new StateEntry(StateType.Load, 0.1F, 1.0F, StateEntry.MathType.Sub, reloadCount));
/*    */     } 
/* 73 */     states.add(new StateEntry(StateType.Untilt, 0.2F, 1.0F, StateEntry.MathType.Sub));
/* 74 */     states.add(new StateEntry(StateType.Charge, 0.18F, 0.1F, StateEntry.MathType.Sub));
/* 75 */     states.add(new StateEntry(StateType.Uncharge, 0.02F, 0.9F, StateEntry.MathType.Add));
/* 76 */     return states;
/*    */   }
/*    */ }


/* Location:              C:\Users\DuduM\OneDrive\Área de Trabalho\mw10\modularwarfare-shining-2023.2.4.4f-fix10.jar!\com\modularwarfare\client\fpp\basic\animations\anims\AnimationRevolver.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */