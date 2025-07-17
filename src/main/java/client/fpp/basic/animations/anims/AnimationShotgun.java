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
/*    */ public class AnimationShotgun
/*    */   extends WeaponAnimation
/*    */ {
/*    */   public void onGunAnimation(float tiltProgress, AnimStateMachine animation) {
/* 24 */     GL11.glTranslatef(0.0F * tiltProgress, 0.0F, 0.0F);
/*    */     
/* 26 */     GL11.glTranslatef(0.0F, 0.0F * tiltProgress, 0.0F);
/*    */     
/* 28 */     GL11.glTranslatef(0.0F, 0.0F, -0.2F * tiltProgress);
/*    */     
/* 30 */     GL11.glRotatef(10.0F * tiltProgress, 1.0F, 0.0F, 0.0F);
/*    */     
/* 32 */     GL11.glRotatef(-10.0F * tiltProgress, 0.0F, 1.0F, 0.0F);
/*    */     
/* 34 */     GL11.glRotatef(15.0F * tiltProgress, 0.0F, 0.0F, 1.0F);
/*    */   }
/*    */ 
/*    */   
/*    */   public void onAmmoAnimation(ModelGun gunModel, float ammoPosition, int reloadAmmoCount, AnimStateMachine animation) {
/* 39 */     float multiAmmoPosition = ammoPosition * reloadAmmoCount;
/* 40 */     int bulletNum = MathHelper.func_76141_d(multiAmmoPosition);
/* 41 */     float bulletProgress = multiAmmoPosition - bulletNum;
/*    */ 
/*    */     
/* 44 */     GL11.glTranslatef(bulletProgress * -0.125F / gunModel.config.extra.modelScale, 0.0F, 0.0F);
/*    */     
/* 46 */     GL11.glTranslatef(0.0F, bulletProgress * -0.5F / gunModel.config.extra.modelScale, 0.0F);
/*    */     
/* 48 */     GL11.glTranslatef(0.0F, 0.0F, bulletProgress * -0.0625F / gunModel.config.extra.modelScale);
/*    */     
/* 50 */     GL11.glRotatef(1.0F * bulletProgress, 1.0F, 0.0F, 0.0F);
/*    */     
/* 52 */     GL11.glRotatef(1.0F * bulletProgress, 0.0F, 1.0F, 0.0F);
/*    */     
/* 54 */     GL11.glRotatef(20.0F * bulletProgress, 0.0F, 0.0F, 1.0F);
/*    */   }
/*    */ 
/*    */   
/*    */   public ArrayList<StateEntry> getReloadStates(ReloadType reloadType, int reloadCount) {
/* 59 */     ArrayList<StateEntry> states = new ArrayList<>();
/* 60 */     states.add(new StateEntry(StateType.Tilt, 0.15F, 0.0F, StateEntry.MathType.Add));
/*    */     
/* 62 */     if (reloadCount >= 6) {
/* 63 */       states.add(new StateEntry(StateType.Load, 0.35F, 1.0F, StateEntry.MathType.Sub, reloadCount));
/* 64 */     } else if (reloadCount >= 3 && reloadCount <= 5) {
/* 65 */       states.add(new StateEntry(StateType.Load, 0.15F, 1.0F, StateEntry.MathType.Sub, reloadCount));
/* 66 */     } else if (reloadCount >= 0 && reloadCount <= 2) {
/* 67 */       states.add(new StateEntry(StateType.Load, 0.1F, 1.0F, StateEntry.MathType.Sub, reloadCount));
/*    */     } 
/*    */     
/* 70 */     states.add(new StateEntry(StateType.Untilt, 0.15F, 1.0F, StateEntry.MathType.Sub));
/* 71 */     return states;
/*    */   }
/*    */ }


/* Location:              C:\Users\DuduM\OneDrive\Área de Trabalho\mw9\modularwarfare-shining-2023.2.4.4f-fix9.jar!\com\modularwarfare\client\fpp\basic\animations\anims\AnimationShotgun.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */