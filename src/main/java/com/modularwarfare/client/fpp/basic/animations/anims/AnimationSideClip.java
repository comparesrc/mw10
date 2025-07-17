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
/*    */ 
/*    */ public class AnimationSideClip
/*    */   extends WeaponAnimation
/*    */ {
/*    */   public void onGunAnimation(float tiltProgress, AnimStateMachine animation) {
/* 25 */     GL11.glTranslatef(0.0F * tiltProgress, 0.0F, 0.0F);
/*    */     
/* 27 */     GL11.glTranslatef(0.0F, 0.0F * tiltProgress, 0.0F);
/*    */     
/* 29 */     GL11.glTranslatef(0.0F, 0.0F, -0.2F * tiltProgress);
/*    */     
/* 31 */     GL11.glRotatef(10.0F * tiltProgress, 1.0F, 0.0F, 0.0F);
/*    */     
/* 33 */     GL11.glRotatef(-10.0F * tiltProgress, 0.0F, 1.0F, 0.0F);
/*    */     
/* 35 */     GL11.glRotatef(15.0F * tiltProgress, 0.0F, 0.0F, 1.0F);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public void onAmmoAnimation(ModelGun gunModel, float ammoProgress, int reloadAmmoCount, AnimStateMachine animation) {
/* 41 */     float multiAmmoPosition = ammoProgress * 1.0F;
/* 42 */     int bulletNum = MathHelper.func_76141_d(multiAmmoPosition);
/* 43 */     float bulletProgress = multiAmmoPosition - bulletNum;
/*    */     
/* 45 */     GL11.glRotatef(180.0F * bulletProgress, 0.0F, 1.0F, 0.0F);
/* 46 */     GL11.glRotatef(60.0F * bulletProgress, 0.0F, 1.0F, 0.0F);
/* 47 */     GL11.glTranslatef(0.5F * bulletProgress * 1.0F / gunModel.config.extra.modelScale, 0.0F, 0.0F);
/*    */   }
/*    */ 
/*    */   
/*    */   public ArrayList<StateEntry> getReloadStates(ReloadType reloadType, int reloadCount) {
/* 52 */     ArrayList<StateEntry> states = new ArrayList<>();
/* 53 */     states.add(new StateEntry(StateType.Tilt, 0.15F, 0.0F, StateEntry.MathType.Add));
/* 54 */     if (reloadType == ReloadType.Unload || reloadType == ReloadType.Full)
/* 55 */       states.add(new StateEntry(StateType.Unload, 0.35F, 0.0F, StateEntry.MathType.Add)); 
/* 56 */     if (reloadType == ReloadType.Load || reloadType == ReloadType.Full)
/* 57 */       states.add(new StateEntry(StateType.Load, 0.35F, 1.0F, StateEntry.MathType.Sub, reloadCount)); 
/* 58 */     states.add(new StateEntry(StateType.Untilt, 0.15F, 1.0F, StateEntry.MathType.Sub));
/* 59 */     return states;
/*    */   }
/*    */ }


/* Location:              C:\Users\DuduM\OneDrive\Área de Trabalho\mw10\modularwarfare-shining-2023.2.4.4f-fix10.jar!\com\modularwarfare\client\fpp\basic\animations\anims\AnimationSideClip.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */