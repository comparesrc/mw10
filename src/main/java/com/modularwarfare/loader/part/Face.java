/*     */ package com.modularwarfare.loader.part;
/*     */ 
/*     */ import java.util.List;
/*     */ import net.minecraft.client.renderer.BufferBuilder;
/*     */ import net.minecraft.client.renderer.Tessellator;
/*     */ import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
/*     */ import net.minecraft.util.math.Vec3d;
/*     */ import net.minecraftforge.fml.relauncher.Side;
/*     */ import net.minecraftforge.fml.relauncher.SideOnly;
/*     */ 
/*     */ 
/*     */ public class Face
/*     */ {
/*     */   public Vertex[] vertices;
/*     */   public Vertex[] vertexNormals;
/*     */   public Vertex faceNormal;
/*     */   public TextureCoordinate[] textureCoordinates;
/*     */   
/*     */   @SideOnly(Side.CLIENT)
/*     */   public void render(int glMode, BufferBuilder buffer, float scale) {
/*  21 */     if (this.faceNormal == null) {
/*  22 */       this.faceNormal = calculateFaceNormal();
/*     */     }
/*     */     
/*  25 */     boolean hasTexture = (this.textureCoordinates != null && this.textureCoordinates.length > 0);
/*     */     
/*  27 */     if (glMode < 0) {
/*  28 */       glMode = 4;
/*     */     }
/*     */     
/*  31 */     if (hasTexture) {
/*  32 */       buffer.func_181668_a(glMode, DefaultVertexFormats.field_181710_j);
/*     */     } else {
/*  34 */       buffer.func_181668_a(glMode, DefaultVertexFormats.field_181708_h);
/*     */     } 
/*     */     
/*  37 */     for (int i = 0; i < this.vertices.length; i++) {
/*     */       
/*  39 */       if (hasTexture) {
/*  40 */         buffer.func_181662_b((this.vertices[i]).x * scale, (this.vertices[i]).y * scale, (this.vertices[i]).z * scale)
/*  41 */           .func_187315_a((this.textureCoordinates[i]).u, (this.textureCoordinates[i]).v);
/*  42 */         if (this.vertexNormals != null && i < this.vertexNormals.length) {
/*  43 */           buffer.func_181663_c((this.vertexNormals[i]).x, (this.vertexNormals[i]).y, (this.vertexNormals[i]).z).func_181675_d();
/*     */         } else {
/*  45 */           buffer.func_181663_c(this.faceNormal.x, this.faceNormal.y, this.faceNormal.z).func_181675_d();
/*     */         } 
/*     */       } else {
/*  48 */         buffer.func_181662_b((this.vertices[i]).x * scale, (this.vertices[i]).y * scale, (this.vertices[i]).z * scale)
/*  49 */           .func_181663_c(this.faceNormal.x, this.faceNormal.y, this.faceNormal.z).func_181675_d();
/*     */       } 
/*     */     } 
/*     */     
/*  53 */     Tessellator.func_178181_a().func_78381_a();
/*     */   }
/*     */   
/*     */   private void addFloatToList(List<Float> list, double... f) {
/*  57 */     for (int i = 0; i < f.length; i++) {
/*  58 */       list.add(Float.valueOf((float)f[i]));
/*     */     }
/*     */   }
/*     */   
/*     */   private void addFloatToList(List<Float> list, float... f) {
/*  63 */     for (int i = 0; i < f.length; i++) {
/*  64 */       list.add(Float.valueOf(f[i]));
/*     */     }
/*     */   }
/*     */   
/*     */   @SideOnly(Side.CLIENT)
/*     */   public int renderByVAO(int glMode, List<Float> list, float scale) {
/*  70 */     if (this.faceNormal == null) {
/*  71 */       this.faceNormal = calculateFaceNormal();
/*     */     }
/*     */     
/*  74 */     boolean hasTexture = (this.textureCoordinates != null && this.textureCoordinates.length > 0);
/*     */     
/*  76 */     for (int i = 0; i < this.vertices.length; i++) {
/*     */       
/*  78 */       if (hasTexture) {
/*  79 */         addFloatToList(list, new double[] { (this.vertices[i]).x * scale, (this.vertices[i]).y * scale, (this.vertices[i]).z * scale });
/*     */         
/*  81 */         addFloatToList(list, new float[] { (this.textureCoordinates[i]).u, (this.textureCoordinates[i]).v });
/*  82 */         if (this.vertexNormals != null && i < this.vertexNormals.length) {
/*  83 */           addFloatToList(list, new float[] { (this.vertexNormals[i]).x, (this.vertexNormals[i]).y, (this.vertexNormals[i]).z });
/*     */         } else {
/*  85 */           addFloatToList(list, new float[] { this.faceNormal.x, this.faceNormal.y, this.faceNormal.z });
/*     */         } 
/*     */       } else {
/*     */         
/*  89 */         addFloatToList(list, new double[] { (this.vertices[i]).x * scale, (this.vertices[i]).y * scale, (this.vertices[i]).z * scale });
/*     */         
/*  91 */         addFloatToList(list, new float[] { this.faceNormal.x, this.faceNormal.y, this.faceNormal.z });
/*     */       } 
/*     */     } 
/*  94 */     if (hasTexture) {
/*  95 */       return 8;
/*     */     }
/*  97 */     return 6;
/*     */   }
/*     */ 
/*     */   
/*     */   public Vertex calculateFaceNormal() {
/* 102 */     Vec3d v1 = new Vec3d(((this.vertices[1]).x - (this.vertices[0]).x), ((this.vertices[1]).y - (this.vertices[0]).y), ((this.vertices[1]).z - (this.vertices[0]).z));
/*     */     
/* 104 */     Vec3d v2 = new Vec3d(((this.vertices[2]).x - (this.vertices[0]).x), ((this.vertices[2]).y - (this.vertices[0]).y), ((this.vertices[2]).z - (this.vertices[0]).z));
/*     */ 
/*     */     
/* 107 */     Vec3d normalVector = v1.func_72431_c(v2).func_72432_b();
/*     */     
/* 109 */     return new Vertex((float)normalVector.field_72450_a, (float)normalVector.field_72448_b, (float)normalVector.field_72449_c);
/*     */   }
/*     */ }


/* Location:              C:\Users\DuduM\OneDrive\Área de Trabalho\mw10\modularwarfare-shining-2023.2.4.4f-fix10.jar!\com\modularwarfare\loader\part\Face.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */