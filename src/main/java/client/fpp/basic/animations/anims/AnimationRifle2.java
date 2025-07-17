/*    */ package com.modularwarfare.client.fpp.basic.animations.anims;
/*    */ 
/*    */ import com.modularwarfare.api.WeaponAnimation;
/*    */ import com.modularwarfare.client.fpp.basic.animations.AnimStateMachine;
/*    */ import com.modularwarfare.client.model.ModelGun;
/*    */ import net.minecraft.util.math.MathHelper;
/*    */ import org.lwjgl.opengl.GL11;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class AnimationRifle2
/*    */   extends WeaponAnimation
/*    */ {
/*    */   public void onGunAnimation(float tiltProgress, AnimStateMachine animation) {
/* 18 */     GL11.glTranslatef(0.0F * tiltProgress, 0.0F, 0.0F);
/*    */     
/* 20 */     GL11.glTranslatef(0.0F, 0.0F * tiltProgress, 0.0F);
/*    */     
/* 22 */     GL11.glTranslatef(0.0F, 0.0F, 0.0F * tiltProgress);
/*    */     
/* 24 */     GL11.glRotatef(10.0F * tiltProgress, 1.0F, 0.0F, 0.0F);
/*    */     
/* 26 */     GL11.glRotatef(-15.0F * tiltProgress, 0.0F, 1.0F, 0.0F);
/*    */     
/* 28 */     GL11.glRotatef(25.0F * tiltProgress, 0.0F, 0.0F, 1.0F);
/*    */   }
/*    */ 
/*    */   
/*    */   public void onAmmoAnimation(ModelGun gunModel, float ammoProgress, int reloadAmmoCount, AnimStateMachine animation) {
/* 33 */     float multiAmmoPosition = ammoProgress * 1.0F;
/* 34 */     int bulletNum = MathHelper.func_76141_d(multiAmmoPosition);
/* 35 */     float bulletProgress = multiAmmoPosition - bulletNum;
/*    */ 
/*    */ 
/*    */ 
/*    */     
/* 40 */     GL11.glTranslatef(ammoProgress * -2.75F, 0.0F, 0.0F);
/*    */     
/* 42 */     GL11.glTranslatef(0.0F, ammoProgress * -2.0F, 0.0F);
/*    */     
/* 44 */     GL11.glTranslatef(0.0F, 0.0F, ammoProgress * 0.0F);
/*    */     
/* 46 */     GL11.glRotatef(30.0F * ammoProgress, 1.0F, 0.0F, 0.0F);
/*    */     
/* 48 */     GL11.glRotatef(0.0F * ammoProgress, 0.0F, 1.0F, 0.0F);
/*    */     
/* 50 */     GL11.glRotatef(-150.0F * ammoProgress, 0.0F, 0.0F, 1.0F);
/*    */   }
/*    */ }


/* Location:              C:\Users\DuduM\OneDrive\Área de Trabalho\mw9\modularwarfare-shining-2023.2.4.4f-fix9.jar!\com\modularwarfare\client\fpp\basic\animations\anims\AnimationRifle2.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */