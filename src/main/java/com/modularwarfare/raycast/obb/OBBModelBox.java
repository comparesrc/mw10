/*     */ package com.modularwarfare.raycast.obb;
/*     */ 
/*     */ import com.modularwarfare.common.vector.Matrix4f;
/*     */ import com.modularwarfare.common.vector.ReadableVector3f;
/*     */ import com.modularwarfare.common.vector.Vector3f;
/*     */ import com.modularwarfare.loader.ObjModel;
/*     */ import com.modularwarfare.loader.api.ObjModelLoader;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Iterator;
/*     */ import net.minecraft.client.Minecraft;
/*     */ import net.minecraft.client.renderer.GlStateManager;
/*     */ import net.minecraft.util.ResourceLocation;
/*     */ import net.minecraftforge.fml.relauncher.Side;
/*     */ import net.minecraftforge.fml.relauncher.SideOnly;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class OBBModelBox
/*     */ {
/*     */   public String name;
/*     */   public Vector3f anchor;
/*     */   public Vector3f rotation;
/*     */   public Vector3f size;
/*     */   public Vector3f center;
/*  31 */   public Axis axis = new Axis();
/*  32 */   public Axis axisNormal = new Axis();
/*     */ 
/*     */   
/*  35 */   private static final transient ObjModel debugBoxModel = ObjModelLoader.load(new ResourceLocation("modularwarfare:obb/model.obj"));
/*  36 */   private static final transient ResourceLocation debugBoxTex = new ResourceLocation("modularwarfare:obb/debugbox.png");
/*     */   
/*     */   public static class Axis
/*     */     implements Iterable<Vector3f> {
/*  40 */     public Vector3f x = new Vector3f(1.0F, 0.0F, 0.0F);
/*  41 */     public Vector3f y = new Vector3f(0.0F, 1.0F, 0.0F);
/*  42 */     public Vector3f z = new Vector3f(0.0F, 0.0F, 1.0F);
/*     */ 
/*     */     
/*     */     public Iterator<Vector3f> iterator() {
/*  46 */       ArrayList<Vector3f> list = new ArrayList<>();
/*  47 */       list.add(this.x);
/*  48 */       list.add(this.y);
/*  49 */       list.add(this.z);
/*  50 */       return list.iterator();
/*     */     }
/*     */     
/*     */     public Axis copy() {
/*  54 */       Axis axis = new Axis();
/*  55 */       axis.x = new Vector3f((ReadableVector3f)this.x);
/*  56 */       axis.y = new Vector3f((ReadableVector3f)this.y);
/*  57 */       axis.z = new Vector3f((ReadableVector3f)this.z);
/*  58 */       return axis;
/*     */     }
/*     */     
/*     */     public Vector3f getAxi(int i) {
/*  62 */       if (i == 0)
/*  63 */         return this.x; 
/*  64 */       if (i == 1) {
/*  65 */         return this.y;
/*     */       }
/*  67 */       return this.z;
/*     */     }
/*     */   }
/*     */   
/*     */   public static class RayCastResult
/*     */   {
/*     */     public double t;
/*     */     public Vector3f normal;
/*     */     
/*     */     public RayCastResult(double t, Vector3f normal) {
/*  77 */       this.t = t;
/*  78 */       this.normal = normal;
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public OBBModelBox copy() {
/*  84 */     OBBModelBox box = new OBBModelBox();
/*  85 */     box.name = this.name;
/*  86 */     box.anchor = new Vector3f((ReadableVector3f)this.anchor);
/*  87 */     box.rotation = new Vector3f((ReadableVector3f)this.rotation);
/*  88 */     box.size = new Vector3f((ReadableVector3f)this.size);
/*  89 */     box.center = new Vector3f((ReadableVector3f)this.center);
/*  90 */     box.axis = this.axis.copy();
/*  91 */     box.axisNormal = this.axisNormal.copy();
/*  92 */     return box;
/*     */   }
/*     */   
/*     */   public void compute(Matrix4f matrix) {
/*  96 */     this.center = Matrix4f.transform(matrix, this.anchor, null).add(matrix.m30, matrix.m31, matrix.m32);
/*     */     
/*  98 */     matrix = matrix.rotate(this.rotation.y, OBBModelBone.YAW).rotate(this.rotation.x, OBBModelBone.PITCH).rotate(this.rotation.z, OBBModelBone.ROOL).scale(this.size);
/*  99 */     this.axisNormal = new Axis();
/* 100 */     this.axisNormal.x = Matrix4f.transform(matrix, this.axisNormal.x, null).normalise(null);
/* 101 */     this.axisNormal.y = Matrix4f.transform(matrix, this.axisNormal.y, null).normalise(null);
/* 102 */     this.axisNormal.z = Matrix4f.transform(matrix, this.axisNormal.z, null).normalise(null);
/* 103 */     this.axis = new Axis();
/* 104 */     this.axis.x = Matrix4f.transform(matrix, this.axis.x, null);
/* 105 */     this.axis.y = Matrix4f.transform(matrix, this.axis.y, null);
/* 106 */     this.axis.z = Matrix4f.transform(matrix, this.axis.z, null);
/*     */   }
/*     */   
/*     */   @SideOnly(Side.CLIENT)
/*     */   public void renderDebugBox() {
/* 111 */     GlStateManager.func_179094_E();
/* 112 */     GlStateManager.func_179109_b(this.anchor.x, this.anchor.y, this.anchor.z);
/* 113 */     GlStateManager.func_179152_a(this.size.x * 2.0F, this.size.y * 2.0F, this.size.z * 2.0F);
/* 114 */     GlStateManager.func_179114_b((float)Math.toDegrees(this.rotation.y), 0.0F, -1.0F, 0.0F);
/* 115 */     GlStateManager.func_179114_b((float)Math.toDegrees(this.rotation.x), -1.0F, 0.0F, 0.0F);
/* 116 */     GlStateManager.func_179114_b((float)Math.toDegrees(this.rotation.z), 0.0F, 0.0F, 1.0F);
/* 117 */     (Minecraft.func_71410_x()).field_71446_o.func_110577_a(debugBoxTex);
/* 118 */     debugBoxModel.renderAll(16.0F);
/* 119 */     GlStateManager.func_179121_F();
/*     */   }
/*     */   
/*     */   public static boolean testCollisionOBBAndOBB(OBBModelBox obb1, OBBModelBox obb2) {
/* 123 */     OBBModelBox[] obbs = { obb1, obb2 };
/* 124 */     Vector3f obb1VecX = obb1.axis.x;
/* 125 */     Vector3f obb1VecY = obb1.axis.y;
/* 126 */     Vector3f obb1VecZ = obb1.axis.z;
/* 127 */     Vector3f obb2VecX = obb2.axis.x;
/* 128 */     Vector3f obb2VecY = obb2.axis.y;
/* 129 */     Vector3f obb2VecZ = obb2.axis.z;
/*     */     
/* 131 */     Vector3f dis = new Vector3f(obb2.center.x - obb1.center.x, obb2.center.y - obb1.center.y, obb2.center.z - obb1.center.z);
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 136 */     for (OBBModelBox obb : obbs) {
/* 137 */       for (Vector3f axi : obb.axisNormal) {
/* 138 */         Vector3f axiVec = axi;
/*     */         
/* 140 */         double proj1 = projectionFast(obb1VecX, axiVec) + projectionFast(obb1VecY, axiVec) + projectionFast(obb1VecZ, axiVec);
/*     */         
/* 142 */         double proj2 = projectionFast(obb2VecX, axiVec) + projectionFast(obb2VecY, axiVec) + projectionFast(obb2VecZ, axiVec);
/* 143 */         double projAB = projectionFast(dis, axiVec);
/* 144 */         if (projAB > proj1 + proj2) {
/* 145 */           return false;
/*     */         }
/*     */       } 
/*     */     } 
/* 149 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public static RayCastResult testCollisionOBBAndRay(OBBModelBox obb1, Vector3f pos, Vector3f ray) {
/* 154 */     double result = Double.MAX_VALUE;
/* 155 */     double errorRange = 0.009999999776482582D;
/* 156 */     boolean flag = false;
/* 157 */     Vector3f resultVec = null;
/* 158 */     Vector3f rayDir = ray;
/* 159 */     for (int i = 0; i < 3; i++) {
/* 160 */       Vector3f n = obb1.axisNormal.getAxi(i);
/* 161 */       Vector3f axi = obb1.axis.getAxi(i);
/* 162 */       Vector3f center1 = new Vector3f(obb1.center.x + axi.x, obb1.center.y + axi.y, obb1.center.z + axi.z);
/* 163 */       Vector3f center2 = new Vector3f(obb1.center.x - axi.x, obb1.center.y - axi.y, obb1.center.z - axi.z);
/* 164 */       double projp = Vector3f.dotDouble(pos, n);
/* 165 */       double d1 = Vector3f.dotDouble(center1, n);
/* 166 */       double d2 = Vector3f.dotDouble(center2, n);
/* 167 */       double len = Vector3f.dotDouble(rayDir, n);
/* 168 */       double t1 = (d1 - projp) / len;
/* 169 */       double t2 = (d2 - projp) / len;
/*     */       
/* 171 */       if (t1 > 0.0D && t1 < result) {
/* 172 */         Vector3f crossDis = new Vector3f(pos.x + ray.x * t1 - obb1.center.x, pos.y + ray.y * t1 - obb1.center.y, pos.z + ray.z * t1 - obb1.center.z);
/*     */         
/* 174 */         if (projectionFast(crossDis, obb1.axisNormal.x) < projectionFast(obb1.axis.x, obb1.axisNormal.x) + 0.009999999776482582D && 
/* 175 */           projectionFast(crossDis, obb1.axisNormal.y) < projectionFast(obb1.axis.y, obb1.axisNormal.y) + 0.009999999776482582D && 
/* 176 */           projectionFast(crossDis, obb1.axisNormal.z) < projectionFast(obb1.axis.z, obb1.axisNormal.z) + 0.009999999776482582D) {
/*     */           
/* 178 */           result = t1;
/* 179 */           resultVec = n;
/*     */         } 
/*     */       } 
/*     */ 
/*     */       
/* 184 */       if (t2 > 0.0D && t2 < result) {
/* 185 */         Vector3f crossDis = new Vector3f(pos.x + ray.x * t2 - obb1.center.x, pos.y + ray.y * t2 - obb1.center.y, pos.z + ray.z * t2 - obb1.center.z);
/*     */         
/* 187 */         if (projectionFast(crossDis, obb1.axisNormal.x) < projectionFast(obb1.axis.x, obb1.axisNormal.x) + 0.009999999776482582D && 
/* 188 */           projectionFast(crossDis, obb1.axisNormal.y) < projectionFast(obb1.axis.y, obb1.axisNormal.y) + 0.009999999776482582D && 
/* 189 */           projectionFast(crossDis, obb1.axisNormal.z) < projectionFast(obb1.axis.z, obb1.axisNormal.z) + 0.009999999776482582D) {
/*     */           
/* 191 */           result = t2;
/* 192 */           resultVec = n.negate(null);
/*     */         } 
/*     */       } 
/*     */     } 
/*     */ 
/*     */     
/* 198 */     return new RayCastResult(result, resultVec);
/*     */   }
/*     */   
/*     */   public static double projectionFast(Vector3f vec1, Vector3f vec2) {
/* 202 */     double delta = Vector3f.dotDouble(vec1, vec2);
/* 203 */     return Math.abs(delta);
/*     */   }
/*     */ }


/* Location:              C:\Users\DuduM\OneDrive\Área de Trabalho\mw10\modularwarfare-shining-2023.2.4.4f-fix10.jar!\com\modularwarfare\raycast\obb\OBBModelBox.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */