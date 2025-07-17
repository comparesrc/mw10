/*     */ package com.modularwarfare.client.fpp.basic.renderers;
/*     */ 
/*     */ import com.modularwarfare.api.WeaponAnimation;
/*     */ import com.modularwarfare.client.fpp.basic.animations.AnimStateMachine;
/*     */ import com.modularwarfare.client.fpp.basic.animations.ReloadType;
/*     */ import com.modularwarfare.client.fpp.basic.animations.StateEntry;
/*     */ import com.modularwarfare.client.fpp.basic.animations.StateType;
/*     */ import com.modularwarfare.client.model.ModelGun;
/*     */ import com.modularwarfare.utility.NumberHelper;
/*     */ import java.util.Optional;
/*     */ import org.lwjgl.opengl.GL11;
/*     */ import org.lwjgl.util.vector.Vector3f;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class RenderArms
/*     */ {
/*     */   public static void renderToFrom(ModelGun model, AnimStateMachine anim, float smoothing, Vector3f targetRot, Vector3f targetPos, Vector3f originRot, Vector3f originPos, boolean leftHand) {
/*  28 */     float progress = anim.getReloadState().isPresent() ? ((StateEntry)anim.getReloadState().get()).currentValue : (anim.getShootState().isPresent() ? ((StateEntry)anim.getShootState().get()).currentValue : 1.0F);
/*     */     
/*  30 */     if (NumberHelper.subtractVector(targetPos, originPos) != null) {
/*  31 */       Vector3f offsetPosition = NumberHelper.multiplyVector(NumberHelper.subtractVector(targetPos, originPos), progress);
/*  32 */       float cancelOut = anim.getReloadState().isPresent() ? ((((StateEntry)anim.getReloadState().get()).stateType == StateType.ReturnHands) ? 0.0F : 1.0F) : (anim.getShootState().isPresent() ? ((((StateEntry)anim.getShootState().get()).stateType == StateType.ReturnHands) ? 0.0F : 1.0F) : 1.0F);
/*  33 */       GL11.glTranslatef(originPos.x + offsetPosition.x + cancelOut * Math.abs(1.0F + 0.0F * smoothing) * model.config.bolt.chargeModifier.x * model.config.extra.modelScale, 0.0F, 0.0F);
/*  34 */       GL11.glTranslatef(0.0F, originPos.y + offsetPosition.y + cancelOut * Math.abs(1.0F + 0.0F * smoothing) * model.config.bolt.chargeModifier.y * model.config.extra.modelScale, 0.0F);
/*  35 */       GL11.glTranslatef(0.0F, 0.0F, originPos.z + offsetPosition.z + cancelOut * Math.abs(1.0F + 0.0F * smoothing) * model.config.bolt.chargeModifier.z * model.config.extra.modelScale);
/*     */ 
/*     */       
/*  38 */       Vector3f offsetRotation = NumberHelper.multiplyVector(NumberHelper.subtractVector(targetRot, originRot), progress);
/*  39 */       if (leftHand) {
/*  40 */         GL11.glTranslatef(0.225F, 0.75F, 0.0F);
/*  41 */         GL11.glRotatef(originRot.x + offsetRotation.x, 1.0F, 0.0F, 0.0F);
/*  42 */         GL11.glRotatef(originRot.y + offsetRotation.y, 0.0F, 1.0F, 0.0F);
/*  43 */         GL11.glRotatef(originRot.z + offsetRotation.z, 0.0F, 0.0F, 1.0F);
/*  44 */         GL11.glTranslatef(-0.225F, -0.75F, 0.0F);
/*     */       } else {
/*  46 */         GL11.glTranslatef(-0.225F, 0.75F, 0.0F);
/*  47 */         GL11.glRotatef(originRot.x + offsetRotation.x, 1.0F, 0.0F, 0.0F);
/*  48 */         GL11.glRotatef(originRot.y + offsetRotation.y, 0.0F, 1.0F, 0.0F);
/*  49 */         GL11.glRotatef(originRot.z + offsetRotation.z, 0.0F, 0.0F, 1.0F);
/*  50 */         GL11.glTranslatef(0.225F, -0.75F, 0.0F);
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   public static void renderArmPump(ModelGun model, AnimStateMachine anim, float smoothing, Vector3f reloadRot, Vector3f reloadPos, boolean leftHand) {
/*  56 */     Optional<StateEntry> currentShootState = anim.getShootState();
/*  57 */     float pumpCurrent = currentShootState.isPresent() ? ((((StateEntry)currentShootState.get()).stateType == StateType.PumpOut || ((StateEntry)currentShootState.get()).stateType == StateType.PumpIn) ? ((StateEntry)currentShootState.get()).currentValue : 1.0F) : 1.0F;
/*  58 */     float pumpLast = currentShootState.isPresent() ? ((((StateEntry)currentShootState.get()).stateType == StateType.PumpOut || ((StateEntry)currentShootState.get()).stateType == StateType.PumpIn) ? ((StateEntry)currentShootState.get()).lastValue : 1.0F) : 1.0F;
/*     */     
/*  60 */     if (leftHand) {
/*  61 */       GL11.glTranslatef(model.config.arms.leftArm.armPos.x - (1.0F - Math.abs(pumpLast + (pumpCurrent - pumpLast) * smoothing)) * model.config.bolt.pumpHandleDistance, model.config.arms.leftArm.armPos.y, model.config.arms.leftArm.armPos.z);
/*  62 */       handleRotateLeft(reloadRot);
/*     */     } else {
/*  64 */       GL11.glTranslatef(model.config.arms.rightArm.armPos.x - (1.0F - Math.abs(pumpLast + (pumpCurrent - pumpLast) * smoothing)) * model.config.bolt.pumpHandleDistance, model.config.arms.rightArm.armPos.y, model.config.arms.rightArm.armPos.z);
/*  65 */       handleRotateRight(reloadRot);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static void renderArmCharge(ModelGun model, AnimStateMachine anim, float smoothing, Vector3f reloadRot, Vector3f reloadPos, Vector3f defaultRot, Vector3f defaultPos, boolean leftHand) {
/*  72 */     Vector3f offsetPosition = NumberHelper.multiplyVector(NumberHelper.subtractVector(reloadPos, defaultPos), 1.0F);
/*  73 */     Optional<StateEntry> currentReloadState = anim.getReloadState();
/*  74 */     float chargeCurrent = currentReloadState.isPresent() ? ((((StateEntry)currentReloadState.get()).stateType == StateType.Charge || ((StateEntry)currentReloadState.get()).stateType == StateType.Uncharge) ? ((StateEntry)currentReloadState.get()).currentValue : 1.0F) : 1.0F;
/*  75 */     float chargeLast = currentReloadState.isPresent() ? ((((StateEntry)currentReloadState.get()).stateType == StateType.Charge || ((StateEntry)currentReloadState.get()).stateType == StateType.Uncharge) ? ((StateEntry)currentReloadState.get()).lastValue : 1.0F) : 1.0F;
/*     */     
/*  77 */     GL11.glTranslatef(defaultPos.x + offsetPosition.x + Math.abs(chargeLast + (chargeCurrent - chargeLast) * smoothing) * model.config.extra.chargeHandleDistance * model.config.extra.modelScale, 0.0F, 0.0F);
/*     */     
/*  79 */     GL11.glTranslatef(0.0F, defaultPos.y + offsetPosition.y, 0.0F);
/*  80 */     GL11.glTranslatef(0.0F, 0.0F, defaultPos.z + offsetPosition.z);
/*     */ 
/*     */     
/*  83 */     Vector3f offsetRotation = NumberHelper.multiplyVector(NumberHelper.subtractVector(reloadRot, defaultRot), 1.0F);
/*  84 */     if (leftHand) {
/*  85 */       GL11.glTranslatef(0.225F, 0.75F, 0.0F);
/*  86 */       GL11.glRotatef(defaultRot.x + offsetRotation.x, 1.0F, 0.0F, 0.0F);
/*  87 */       GL11.glRotatef(defaultRot.y + offsetRotation.y, 0.0F, 1.0F, 0.0F);
/*  88 */       GL11.glRotatef(defaultRot.z + offsetRotation.z, 0.0F, 0.0F, 1.0F);
/*  89 */       GL11.glTranslatef(-0.225F, -0.75F, 0.0F);
/*     */     } else {
/*  91 */       GL11.glTranslatef(-0.225F, 0.75F, 0.0F);
/*  92 */       GL11.glRotatef(defaultRot.x + offsetRotation.x, 1.0F, 0.0F, 0.0F);
/*  93 */       GL11.glRotatef(defaultRot.y + offsetRotation.y, 0.0F, 1.0F, 0.0F);
/*  94 */       GL11.glRotatef(defaultRot.z + offsetRotation.z, 0.0F, 0.0F, 1.0F);
/*  95 */       GL11.glTranslatef(0.225F, -0.75F, 0.0F);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static void renderArmBolt(ModelGun model, AnimStateMachine anim, float smoothing, Vector3f reloadRot, Vector3f reloadPos, boolean leftHand) {
/* 102 */     Optional<StateEntry> currentShootState = anim.getShootState();
/* 103 */     float pumpCurrent = currentShootState.isPresent() ? ((((StateEntry)currentShootState.get()).stateType == StateType.Charge || ((StateEntry)currentShootState.get()).stateType == StateType.Uncharge) ? ((StateEntry)currentShootState.get()).currentValue : 1.0F) : 1.0F;
/* 104 */     float pumpLast = currentShootState.isPresent() ? ((((StateEntry)currentShootState.get()).stateType == StateType.Charge || ((StateEntry)currentShootState.get()).stateType == StateType.Uncharge) ? ((StateEntry)currentShootState.get()).lastValue : 1.0F) : 1.0F;
/*     */     
/* 106 */     if (anim.isReloadState(StateType.Charge) || anim.isReloadState(StateType.Uncharge)) {
/* 107 */       StateEntry boltState = anim.getReloadState().get();
/* 108 */       pumpCurrent = boltState.currentValue;
/* 109 */       pumpLast = boltState.lastValue;
/*     */     } 
/*     */     
/* 112 */     GL11.glTranslatef(reloadPos.x - (1.0F - Math.abs(pumpLast + (pumpCurrent - pumpLast) * smoothing)) * model.config.bolt.chargeModifier.x, 0.0F, 0.0F);
/* 113 */     GL11.glTranslatef(0.0F, reloadPos.y - (1.0F - Math.abs(pumpLast + (pumpCurrent - pumpLast) * smoothing)) * model.config.bolt.chargeModifier.y, 0.0F);
/* 114 */     GL11.glTranslatef(0.0F, 0.0F, reloadPos.z - (1.0F - Math.abs(pumpLast + (pumpCurrent - pumpLast) * smoothing)) * model.config.bolt.chargeModifier.z);
/*     */     
/* 116 */     if (leftHand) {
/* 117 */       handleRotateLeft(reloadRot);
/*     */     } else {
/* 119 */       handleRotateRight(reloadRot);
/*     */     } 
/*     */   }
/*     */   public static void renderArmDefault(ModelGun model, AnimStateMachine anim, float smoothing, Vector3f reloadRot, Vector3f reloadPos, boolean firingHand, boolean leftHand) {
/* 123 */     GL11.glTranslatef(reloadPos.x - (firingHand ? RenderParameters.triggerPullSwitch : 0.0F), reloadPos.y, reloadPos.z);
/* 124 */     if (leftHand) {
/* 125 */       handleRotateLeft(reloadRot);
/*     */     } else {
/* 127 */       handleRotateRight(reloadRot);
/*     */     } 
/*     */   }
/*     */   
/*     */   public static void renderArmReload(ModelGun model, AnimStateMachine anim, WeaponAnimation animation, float smoothing, float tiltProgress, Vector3f reloadRot, Vector3f reloadPos, Vector3f defaultRot, Vector3f defaultPos, boolean leftHand) {
/* 132 */     Vector3f offsetPosition = NumberHelper.multiplyVector(NumberHelper.subtractVector(reloadPos, defaultPos), tiltProgress);
/*     */     
/* 134 */     Optional<StateEntry> currentState = anim.getReloadState();
/* 135 */     Vector3f ammoLoadOffset = (anim.isReloadType(ReloadType.Load) && currentState.isPresent() && ((StateEntry)currentState.get()).stateType != StateType.Load && ((StateEntry)currentState.get()).stateType != StateType.Untilt) ? ((animation.ammoLoadOffset != null) ? animation.ammoLoadOffset : new Vector3f(0.0F, 0.0F, 0.0F)) : new Vector3f(0.0F, 0.0F, 0.0F);
/*     */ 
/*     */     
/* 138 */     GL11.glTranslatef(defaultPos.x + offsetPosition.x + ammoLoadOffset.x * tiltProgress, 0.0F, 0.0F);
/* 139 */     GL11.glTranslatef(0.0F, defaultPos.y + offsetPosition.y + ammoLoadOffset.y * tiltProgress, 0.0F);
/* 140 */     GL11.glTranslatef(0.0F, 0.0F, defaultPos.z + offsetPosition.z + ammoLoadOffset.z * tiltProgress);
/*     */ 
/*     */     
/* 143 */     Vector3f offsetRotation = NumberHelper.multiplyVector(NumberHelper.subtractVector(reloadRot, defaultRot), tiltProgress);
/* 144 */     if (leftHand) {
/* 145 */       GL11.glTranslatef(0.225F, 0.75F, 0.0F);
/* 146 */       GL11.glRotatef(defaultRot.x + offsetRotation.x, 1.0F, 0.0F, 0.0F);
/* 147 */       GL11.glRotatef(defaultRot.y + offsetRotation.y, 0.0F, 1.0F, 0.0F);
/* 148 */       GL11.glRotatef(defaultRot.z + offsetRotation.z, 0.0F, 0.0F, 1.0F);
/* 149 */       GL11.glTranslatef(-0.225F, -0.75F, 0.0F);
/*     */     } else {
/* 151 */       GL11.glTranslatef(-0.225F, 0.75F, 0.0F);
/* 152 */       GL11.glRotatef(defaultRot.x + offsetRotation.x, 1.0F, 0.0F, 0.0F);
/* 153 */       GL11.glRotatef(defaultRot.y + offsetRotation.y, 0.0F, 1.0F, 0.0F);
/* 154 */       GL11.glRotatef(defaultRot.z + offsetRotation.z, 0.0F, 0.0F, 1.0F);
/* 155 */       GL11.glTranslatef(0.225F, -0.75F, 0.0F);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public static void renderStaticArmReload(ModelGun model, AnimStateMachine anim, float smoothing, float tiltProgress, Vector3f reloadRot, Vector3f reloadPos, Vector3f defaultRot, Vector3f defaultPos, boolean leftHand) {
/* 161 */     Vector3f offsetPosition = NumberHelper.multiplyVector(NumberHelper.subtractVector(reloadPos, defaultPos), tiltProgress);
/* 162 */     GL11.glTranslatef(defaultPos.x + offsetPosition.x, defaultPos.y + offsetPosition.y, defaultPos.z + offsetPosition.z);
/*     */ 
/*     */     
/* 165 */     Vector3f offsetRotation = NumberHelper.multiplyVector(NumberHelper.subtractVector(reloadRot, defaultRot), tiltProgress);
/* 166 */     if (leftHand) {
/* 167 */       GL11.glTranslatef(0.225F, 0.75F, 0.0F);
/* 168 */       GL11.glRotatef(defaultRot.x + offsetRotation.x, 1.0F, 0.0F, 0.0F);
/* 169 */       GL11.glRotatef(defaultRot.y + offsetRotation.y, 0.0F, 1.0F, 0.0F);
/* 170 */       GL11.glRotatef(defaultRot.z + offsetRotation.z, 0.0F, 0.0F, 1.0F);
/* 171 */       GL11.glTranslatef(-0.225F, -0.75F, 0.0F);
/*     */     } else {
/* 173 */       GL11.glTranslatef(-0.225F, 0.75F, 0.0F);
/* 174 */       GL11.glRotatef(defaultRot.x + offsetRotation.x, 1.0F, 0.0F, 0.0F);
/* 175 */       GL11.glRotatef(defaultRot.y + offsetRotation.y, 0.0F, 1.0F, 0.0F);
/* 176 */       GL11.glRotatef(defaultRot.z + offsetRotation.z, 0.0F, 0.0F, 1.0F);
/* 177 */       GL11.glTranslatef(0.225F, -0.75F, 0.0F);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static void renderArmLoad(ModelGun model, AnimStateMachine anim, WeaponAnimation animation, float smoothing, float tiltProgress, Vector3f reloadRot, Vector3f reloadPos, Vector3f defaultRot, Vector3f defaultPos, boolean leftHand) {
/* 184 */     Vector3f offsetPosition = NumberHelper.multiplyVector(NumberHelper.subtractVector(reloadPos, defaultPos), tiltProgress);
/* 185 */     Optional<StateEntry> currentState = anim.getReloadState();
/* 186 */     Vector3f ammoLoadOffset = (anim.isReloadType(ReloadType.Load) && currentState.isPresent() && ((StateEntry)currentState.get()).stateType != StateType.Load) ? ((animation.ammoLoadOffset != null) ? animation.ammoLoadOffset : new Vector3f(0.0F, 0.0F, 0.0F)) : new Vector3f(0.0F, 0.0F, 0.0F);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 193 */     GL11.glTranslatef(defaultPos.x + offsetPosition.x + ammoLoadOffset.x * tiltProgress, 0.0F, 0.0F);
/* 194 */     GL11.glTranslatef(0.0F, defaultPos.y + offsetPosition.y + ammoLoadOffset.y * tiltProgress, 0.0F);
/* 195 */     GL11.glTranslatef(0.0F, 0.0F, defaultPos.z + offsetPosition.z + ammoLoadOffset.z * tiltProgress);
/*     */     
/* 197 */     Vector3f offsetRotation = NumberHelper.multiplyVector(NumberHelper.subtractVector(reloadRot, defaultRot), tiltProgress);
/* 198 */     if (leftHand) {
/* 199 */       GL11.glTranslatef(0.225F, 0.75F, 0.0F);
/* 200 */       GL11.glRotatef(defaultRot.x + offsetRotation.x, 1.0F, 0.0F, 0.0F);
/* 201 */       GL11.glRotatef(defaultRot.y + offsetRotation.y, 0.0F, 1.0F, 0.0F);
/* 202 */       GL11.glRotatef(defaultRot.z + offsetRotation.z, 0.0F, 0.0F, 1.0F);
/* 203 */       GL11.glTranslatef(-0.225F, -0.75F, 0.0F);
/*     */     } else {
/* 205 */       GL11.glTranslatef(-0.225F, 0.75F, 0.0F);
/* 206 */       GL11.glRotatef(defaultRot.x + offsetRotation.x, 1.0F, 0.0F, 0.0F);
/* 207 */       GL11.glRotatef(defaultRot.y + offsetRotation.y, 0.0F, 1.0F, 0.0F);
/* 208 */       GL11.glRotatef(defaultRot.z + offsetRotation.z, 0.0F, 0.0F, 1.0F);
/* 209 */       GL11.glTranslatef(0.225F, -0.75F, 0.0F);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public static void renderArmUnload(ModelGun model, AnimStateMachine anim, WeaponAnimation animation, float smoothing, float tiltProgress, Vector3f reloadRot, Vector3f reloadPos, Vector3f defaultRot, Vector3f defaultPos, boolean leftHand) {
/* 215 */     Vector3f offsetPosition = NumberHelper.multiplyVector(NumberHelper.subtractVector(reloadPos, defaultPos), tiltProgress);
/* 216 */     Vector3f ammoLoadOffset = anim.isReloadType(ReloadType.Load) ? ((animation.ammoLoadOffset != null) ? animation.ammoLoadOffset : new Vector3f(0.0F, 0.0F, 0.0F)) : new Vector3f(0.0F, 0.0F, 0.0F);
/* 217 */     GL11.glTranslatef(defaultPos.x + offsetPosition.x + ammoLoadOffset.x * tiltProgress, 0.0F, 0.0F);
/* 218 */     GL11.glTranslatef(0.0F, defaultPos.y + offsetPosition.y + ammoLoadOffset.y * tiltProgress, 0.0F);
/* 219 */     GL11.glTranslatef(0.0F, 0.0F, defaultPos.z + offsetPosition.z + ammoLoadOffset.z * tiltProgress);
/*     */     
/* 221 */     Vector3f offsetRotation = NumberHelper.multiplyVector(NumberHelper.subtractVector(reloadRot, defaultRot), tiltProgress);
/*     */     
/* 223 */     if (leftHand) {
/* 224 */       GL11.glTranslatef(0.225F, 0.75F, 0.0F);
/* 225 */       GL11.glRotatef(defaultRot.x + offsetRotation.x, 1.0F, 0.0F, 0.0F);
/* 226 */       GL11.glRotatef(defaultRot.y + offsetRotation.y, 0.0F, 1.0F, 0.0F);
/* 227 */       GL11.glRotatef(defaultRot.z + offsetRotation.z, 0.0F, 0.0F, 1.0F);
/* 228 */       GL11.glTranslatef(-0.225F, -0.75F, 0.0F);
/*     */     } else {
/* 230 */       GL11.glTranslatef(-0.225F, 0.75F, 0.0F);
/* 231 */       GL11.glRotatef(defaultRot.x + offsetRotation.x, 1.0F, 0.0F, 0.0F);
/* 232 */       GL11.glRotatef(defaultRot.y + offsetRotation.y, 0.0F, 1.0F, 0.0F);
/* 233 */       GL11.glRotatef(defaultRot.z + offsetRotation.z, 0.0F, 0.0F, 1.0F);
/* 234 */       GL11.glTranslatef(0.225F, -0.75F, 0.0F);
/*     */     } 
/*     */   }
/*     */   
/*     */   private static void handleRotateLeft(Vector3f reloadRot) {
/* 239 */     GL11.glTranslatef(0.225F, 0.75F, 0.0F);
/* 240 */     GL11.glRotatef(reloadRot.x, 1.0F, 0.0F, 0.0F);
/* 241 */     GL11.glRotatef(reloadRot.y, 0.0F, 1.0F, 0.0F);
/* 242 */     GL11.glRotatef(reloadRot.z, 0.0F, 0.0F, 1.0F);
/* 243 */     GL11.glTranslatef(-0.225F, -0.75F, 0.0F);
/*     */   }
/*     */ 
/*     */   
/*     */   private static void handleRotateRight(Vector3f reloadRot) {
/* 248 */     GL11.glTranslatef(-0.225F, 0.75F, 0.0F);
/* 249 */     GL11.glRotatef(reloadRot.x, 1.0F, 0.0F, 0.0F);
/* 250 */     GL11.glRotatef(reloadRot.y, 0.0F, 1.0F, 0.0F);
/* 251 */     GL11.glRotatef(reloadRot.z, 0.0F, 0.0F, 1.0F);
/* 252 */     GL11.glTranslatef(0.225F, -0.75F, 0.0F);
/*     */   }
/*     */ }


/* Location:              C:\Users\DuduM\OneDrive\Área de Trabalho\mw9\modularwarfare-shining-2023.2.4.4f-fix9.jar!\com\modularwarfare\client\fpp\basic\renderers\RenderArms.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */