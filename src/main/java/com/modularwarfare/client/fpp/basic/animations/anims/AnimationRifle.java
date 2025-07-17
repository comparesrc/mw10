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
/*    */ 
/*    */ public class AnimationRifle
/*    */   extends WeaponAnimation
/*    */ {
/*    */   public void onGunAnimation(float tiltProgress, AnimStateMachine animation) {
/* 26 */     GL11.glTranslatef(0.0F * tiltProgress, 0.0F, 0.0F);
/*    */     
/* 28 */     GL11.glTranslatef(0.0F, 0.0F * tiltProgress, 0.0F);
/*    */     
/* 30 */     GL11.glTranslatef(0.0F, 0.0F, -0.2F * tiltProgress);
/*    */     
/* 32 */     GL11.glRotatef(20.0F * tiltProgress, 1.0F, 0.0F, 0.0F);
/*    */     
/* 34 */     GL11.glRotatef(-10.0F * tiltProgress, 0.0F, 1.0F, 0.0F);
/*    */     
/* 36 */     GL11.glRotatef(25.0F * tiltProgress, 0.0F, 0.0F, 1.0F);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public void onAmmoAnimation(ModelGun gunModel, float ammoProgress, int reloadAmmoCount, AnimStateMachine animation) {
/* 42 */     float multiAmmoPosition = ammoProgress * 1.0F;
/* 43 */     int bulletNum = MathHelper.func_76141_d(multiAmmoPosition);
/* 44 */     float bulletProgress = multiAmmoPosition - bulletNum;
/*    */ 
/*    */ 
/*    */     
/* 48 */     GL11.glTranslatef(ammoProgress * -0.75F, 0.0F, 0.0F);
/*    */     
/* 50 */     GL11.glTranslatef(0.0F, ammoProgress * -8.0F, 0.0F);
/*    */     
/* 52 */     GL11.glTranslatef(0.0F, 0.0F, ammoProgress * 0.0F);
/*    */     
/* 54 */     GL11.glRotatef(30.0F * ammoProgress, 1.0F, 0.0F, 0.0F);
/*    */     
/* 56 */     GL11.glRotatef(0.0F * ammoProgress, 0.0F, 1.0F, 0.0F);
/*    */     
/* 58 */     GL11.glRotatef(-90.0F * ammoProgress, 0.0F, 0.0F, 1.0F);
/*    */   }
/*    */ 
/*    */   
/*    */   public ArrayList<StateEntry> getReloadStates(ReloadType reloadType, int reloadCount) {
/* 63 */     ArrayList<StateEntry> states = new ArrayList<>();
/* 64 */     states.add(new StateEntry(StateType.Tilt, 0.15F, 0.0F, StateEntry.MathType.Add));
/* 65 */     if (reloadType == ReloadType.Unload || reloadType == ReloadType.Full)
/* 66 */       states.add(new StateEntry(StateType.Unload, 0.35F, 0.0F, StateEntry.MathType.Add)); 
/* 67 */     if (reloadType == ReloadType.Load || reloadType == ReloadType.Full)
/* 68 */       states.add(new StateEntry(StateType.Load, 0.35F, 1.0F, StateEntry.MathType.Sub, reloadCount)); 
/* 69 */     states.add(new StateEntry(StateType.Untilt, 0.15F, 1.0F, StateEntry.MathType.Sub));
/* 70 */     return states;
/*    */   }
/*    */ }


/* Location:              C:\Users\DuduM\OneDrive\Área de Trabalho\mw10\modularwarfare-shining-2023.2.4.4f-fix10.jar!\com\modularwarfare\client\fpp\basic\animations\anims\AnimationRifle.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */