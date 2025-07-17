/*     */ package com.modularwarfare.raycast.obb;
/*     */ 
/*     */ import com.modularwarfare.common.vector.Matrix4f;
/*     */ import com.modularwarfare.common.vector.Vector3f;
/*     */ import java.nio.FloatBuffer;
/*     */ import java.util.ArrayList;
/*     */ import java.util.HashMap;
/*     */ import net.minecraft.client.renderer.GlStateManager;
/*     */ import net.minecraft.client.renderer.Tessellator;
/*     */ import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
/*     */ import net.minecraftforge.fml.relauncher.Side;
/*     */ import net.minecraftforge.fml.relauncher.SideOnly;
/*     */ import org.lwjgl.BufferUtils;
/*     */ import org.lwjgl.opengl.GL11;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class OBBModelObject
/*     */ {
/*  24 */   public static final FloatBuffer FLOAT_BUFFER = BufferUtils.createFloatBuffer(16);
/*     */   public OBBModelScene scene;
/*  26 */   public ArrayList<OBBModelBox> boxes = new ArrayList<>();
/*  27 */   public HashMap<OBBModelBox, OBBModelBone> boneBinding = new HashMap<>();
/*  28 */   public ArrayList<IBoneUpdatePoseListener> boneUpdatePoseListeners = new ArrayList<>();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void updatePose() {
/*  35 */     this.scene.updatePose(this);
/*     */   }
/*     */   
/*     */   public void computePose() {
/*  39 */     this.scene.computePose(this);
/*  40 */     this.boneBinding.forEach((box, bone) -> box.compute((new Matrix4f(bone.currentPose)).translate(bone.oirign)));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void onBoneUpdatePose(OBBModelBone bone) {
/*  47 */     for (int i = 0; i < this.boneUpdatePoseListeners.size(); i++) {
/*  48 */       ((IBoneUpdatePoseListener)this.boneUpdatePoseListeners.get(i)).onBoneUpdatePose(bone);
/*     */     }
/*     */   }
/*     */   
/*     */   @SideOnly(Side.CLIENT)
/*     */   public void renderDebugBoxes() {
/*  54 */     this.boneBinding.forEach((box, bone) -> {
/*     */           GlStateManager.func_179094_E();
/*     */           FLOAT_BUFFER.rewind();
/*     */           FLOAT_BUFFER.put(bone.currentPose.m00);
/*     */           FLOAT_BUFFER.put(bone.currentPose.m01);
/*     */           FLOAT_BUFFER.put(bone.currentPose.m02);
/*     */           FLOAT_BUFFER.put(bone.currentPose.m03);
/*     */           FLOAT_BUFFER.put(bone.currentPose.m10);
/*     */           FLOAT_BUFFER.put(bone.currentPose.m11);
/*     */           FLOAT_BUFFER.put(bone.currentPose.m12);
/*     */           FLOAT_BUFFER.put(bone.currentPose.m13);
/*     */           FLOAT_BUFFER.put(bone.currentPose.m20);
/*     */           FLOAT_BUFFER.put(bone.currentPose.m21);
/*     */           FLOAT_BUFFER.put(bone.currentPose.m22);
/*     */           FLOAT_BUFFER.put(bone.currentPose.m23);
/*     */           FLOAT_BUFFER.put(bone.currentPose.m30);
/*     */           FLOAT_BUFFER.put(bone.currentPose.m31);
/*     */           FLOAT_BUFFER.put(bone.currentPose.m32);
/*     */           FLOAT_BUFFER.put(bone.currentPose.m33);
/*     */           FLOAT_BUFFER.flip();
/*     */           GL11.glPushMatrix();
/*     */           GlStateManager.func_179110_a(FLOAT_BUFFER);
/*     */           GL11.glTranslatef(bone.oirign.getX(), bone.oirign.getY(), bone.oirign.getZ());
/*     */           box.renderDebugBox();
/*     */           GL11.glPopMatrix();
/*     */           GlStateManager.func_179121_F();
/*     */         });
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   @SideOnly(Side.CLIENT)
/*     */   public void renderDebugAixs() {
/*  87 */     GlStateManager.func_179131_c(1.0F, 1.0F, 1.0F, 1.0F);
/*  88 */     GlStateManager.func_187441_d(2.0F);
/*  89 */     GlStateManager.func_179090_x();
/*  90 */     this.boxes.forEach(box -> {
/*     */           box.axis.forEach(());
/*     */ 
/*     */ 
/*     */ 
/*     */           
/*     */           GlStateManager.func_187441_d(4.0F);
/*     */ 
/*     */ 
/*     */ 
/*     */           
/*     */           box.axisNormal.forEach(());
/*     */         });
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 107 */     GlStateManager.func_187441_d(2.0F);
/* 108 */     GlStateManager.func_179098_w();
/*     */   }
/*     */   
/*     */   public static interface IBoneUpdatePoseListener {
/*     */     void onBoneUpdatePose(OBBModelBone param1OBBModelBone);
/*     */   }
/*     */ }


/* Location:              C:\Users\DuduM\OneDrive\Área de Trabalho\mw9\modularwarfare-shining-2023.2.4.4f-fix9.jar!\com\modularwarfare\raycast\obb\OBBModelObject.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */