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
/*    */ public class AnimationPistol
/*    */   extends WeaponAnimation
/*    */ {
/*    */   public void onGunAnimation(float tiltProgress, AnimStateMachine animation) {
/* 24 */     GL11.glTranslatef(0.2F * tiltProgress, 0.0F, 0.0F);
/*    */     
/* 26 */     GL11.glTranslatef(0.0F, 0.1F * tiltProgress, 0.0F);
/*    */     
/* 28 */     GL11.glTranslatef(0.0F, 0.0F, -0.1F * tiltProgress);
/*    */     
/* 30 */     GL11.glRotatef(20.0F * tiltProgress, 1.0F, 0.0F, 0.0F);
/*    */     
/* 32 */     GL11.glRotatef(-10.0F * tiltProgress, 0.0F, 1.0F, 0.0F);
/*    */     
/* 34 */     GL11.glRotatef(25.0F * tiltProgress, 0.0F, 0.0F, 1.0F);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public void onAmmoAnimation(ModelGun gunModel, float ammoProgress, int reloadAmmoCount, AnimStateMachine animation) {
/* 40 */     float multiAmmoPosition = ammoProgress * 1.0F;
/* 41 */     int bulletNum = MathHelper.func_76141_d(multiAmmoPosition);
/* 42 */     float bulletProgress = multiAmmoPosition - bulletNum;
/*    */ 
/*    */     
/* 45 */     GL11.glTranslatef(ammoProgress * -0.75F, 0.0F, 0.0F);
/*    */     
/* 47 */     GL11.glTranslatef(0.0F, ammoProgress * -8.0F, 0.0F);
/*    */     
/* 49 */     GL11.glTranslatef(0.0F, 0.0F, ammoProgress * 0.0F);
/*    */     
/* 51 */     GL11.glRotatef(30.0F * ammoProgress, 1.0F, 0.0F, 0.0F);
/*    */     
/* 53 */     GL11.glRotatef(0.0F * ammoProgress, 0.0F, 1.0F, 0.0F);
/*    */     
/* 55 */     GL11.glRotatef(-20.0F * ammoProgress, 0.0F, 0.0F, 1.0F);
/*    */   }
/*    */ 
/*    */   
/*    */   public ArrayList<StateEntry> getReloadStates(ReloadType reloadType, int reloadCount) {
/* 60 */     ArrayList<StateEntry> states = new ArrayList<>();
/* 61 */     states.add(new StateEntry(StateType.Tilt, 0.2F, 0.0F, StateEntry.MathType.Add));
/* 62 */     states.add(new StateEntry(StateType.Unload, 0.2F, 0.0F, StateEntry.MathType.Add));
/* 63 */     states.add(new StateEntry(StateType.Load, 0.2F, 1.0F, StateEntry.MathType.Sub));
/* 64 */     states.add(new StateEntry(StateType.Untilt, 0.2F, 1.0F, StateEntry.MathType.Sub));
/* 65 */     states.add(new StateEntry(StateType.Charge, 0.18F, 1.0F, StateEntry.MathType.Sub));
/* 66 */     states.add(new StateEntry(StateType.Uncharge, 0.02F, 0.0F, StateEntry.MathType.Add));
/* 67 */     return states;
/*    */   }
/*    */ }


/* Location:              C:\Users\DuduM\OneDrive\Área de Trabalho\mw10\modularwarfare-shining-2023.2.4.4f-fix10.jar!\com\modularwarfare\client\fpp\basic\animations\anims\AnimationPistol.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */