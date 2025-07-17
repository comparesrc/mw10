/*    */ package com.modularwarfare.client.fpp.basic.animations.anims;
/*    */ 
/*    */ import com.modularwarfare.api.WeaponAnimation;
/*    */ import com.modularwarfare.client.fpp.basic.animations.AnimStateMachine;
/*    */ import com.modularwarfare.client.model.ModelGun;
/*    */ import net.minecraft.util.math.MathHelper;
/*    */ import org.lwjgl.opengl.GL11;
/*    */ 
/*    */ 
/*    */ public class AnimationRifle3
/*    */   extends WeaponAnimation
/*    */ {
/*    */   public void onGunAnimation(float tiltProgress, AnimStateMachine animation) {
/* 14 */     GL11.glTranslatef(0.25F * tiltProgress, 0.0F, 0.0F);
/*    */     
/* 16 */     GL11.glTranslatef(0.0F, 0.2F * tiltProgress, 0.0F);
/*    */     
/* 18 */     GL11.glTranslatef(0.0F, 0.0F, 0.0F * tiltProgress);
/*    */     
/* 20 */     GL11.glRotatef(30.0F * tiltProgress, 1.0F, 0.0F, 0.0F);
/*    */     
/* 22 */     GL11.glRotatef(-10.0F * tiltProgress, 0.0F, 1.0F, 0.0F);
/*    */     
/* 24 */     GL11.glRotatef(30.0F * tiltProgress, 0.0F, 0.0F, 1.0F);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public void onAmmoAnimation(ModelGun gunModel, float ammoProgress, int reloadAmmoCount, AnimStateMachine animation) {
/* 30 */     float multiAmmoPosition = ammoProgress * 1.0F;
/* 31 */     int bulletNum = MathHelper.func_76141_d(multiAmmoPosition);
/* 32 */     float bulletProgress = multiAmmoPosition - bulletNum;
/*    */ 
/*    */     
/* 35 */     GL11.glTranslatef(ammoProgress * -2.75F, 0.0F, 0.0F);
/*    */     
/* 37 */     GL11.glTranslatef(0.0F, ammoProgress * -8.0F, 0.0F);
/*    */     
/* 39 */     GL11.glTranslatef(0.0F, 0.0F, ammoProgress * 0.0F);
/*    */     
/* 41 */     GL11.glRotatef(0.0F * ammoProgress, 1.0F, 0.0F, 0.0F);
/*    */     
/* 43 */     GL11.glRotatef(0.0F * ammoProgress, 0.0F, 1.0F, 0.0F);
/*    */     
/* 45 */     GL11.glRotatef(-50.0F * ammoProgress, 0.0F, 0.0F, 1.0F);
/*    */   }
/*    */ }


/* Location:              C:\Users\DuduM\OneDrive\Área de Trabalho\mw10\modularwarfare-shining-2023.2.4.4f-fix10.jar!\com\modularwarfare\client\fpp\basic\animations\anims\AnimationRifle3.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */