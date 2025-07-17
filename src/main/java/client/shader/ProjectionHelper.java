/*    */ package com.modularwarfare.client.shader;
/*    */ 
/*    */ import java.nio.FloatBuffer;
/*    */ import java.nio.IntBuffer;
/*    */ import net.minecraft.client.renderer.GLAllocation;
/*    */ import net.minecraft.util.math.Vec3d;
/*    */ import org.lwjgl.opengl.GL11;
/*    */ import org.lwjgl.util.glu.GLU;
/*    */ 
/*    */ public class ProjectionHelper
/*    */ {
/* 12 */   private IntBuffer viewport = GLAllocation.func_74527_f(16);
/*    */   
/* 14 */   private FloatBuffer modelview = GLAllocation.func_74529_h(16);
/*    */   
/* 16 */   private FloatBuffer projection = GLAllocation.func_74529_h(16);
/*    */   
/* 18 */   private FloatBuffer objectCoords = GLAllocation.func_74529_h(3);
/*    */   
/* 20 */   private FloatBuffer winCoords = GLAllocation.func_74529_h(3);
/*    */   
/*    */   public void updateMatrices() {
/* 23 */     GL11.glGetFloat(2982, this.modelview);
/* 24 */     GL11.glGetFloat(2983, this.projection);
/* 25 */     GL11.glGetInteger(2978, this.viewport);
/*    */   }
/*    */   
/*    */   public Vec3d unproject(float winX, float winY, float winZ) {
/* 29 */     GLU.gluUnProject(winX, winY, winZ, this.modelview, this.projection, this.viewport, this.objectCoords);
/*    */     
/* 31 */     float objectX = this.objectCoords.get(0);
/* 32 */     float objectY = this.objectCoords.get(1);
/* 33 */     float objectZ = this.objectCoords.get(2);
/*    */     
/* 35 */     return new Vec3d(objectX, objectY, objectZ);
/*    */   }
/*    */ 
/*    */   
/*    */   public Vec3d project(float objX, float objY, float objZ) {
/* 40 */     int[] vp = new int[2];
/*    */     
/* 42 */     this.viewport.rewind();
/* 43 */     int x = this.viewport.get(this.viewport.position() + 2);
/* 44 */     int y = this.viewport.get(this.viewport.position() + 3);
/* 45 */     this.viewport.rewind();
/*    */ 
/*    */ 
/*    */     
/* 49 */     GLU.gluProject(objX, objY, objZ, this.modelview, this.projection, this.viewport, this.winCoords);
/*    */     
/* 51 */     float winX = this.winCoords.get(0);
/* 52 */     float winY = this.winCoords.get(1);
/* 53 */     float winZ = this.winCoords.get(2);
/*    */     
/* 55 */     return new Vec3d(winX, winY, winZ);
/*    */   }
/*    */ }


/* Location:              C:\Users\DuduM\OneDrive\Área de Trabalho\mw9\modularwarfare-shining-2023.2.4.4f-fix9.jar!\com\modularwarfare\client\shader\ProjectionHelper.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */