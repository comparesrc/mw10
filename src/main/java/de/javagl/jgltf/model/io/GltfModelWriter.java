/*     */ package de.javagl.jgltf.model.io;
/*     */ 
/*     */ import de.javagl.jgltf.model.GltfModel;
/*     */ import de.javagl.jgltf.model.io.v1.GltfModelWriterV1;
/*     */ import de.javagl.jgltf.model.io.v2.GltfModelWriterV2;
/*     */ import de.javagl.jgltf.model.v1.GltfModelV1;
/*     */ import java.io.File;
/*     */ import java.io.FileOutputStream;
/*     */ import java.io.IOException;
/*     */ import java.io.OutputStream;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class GltfModelWriter
/*     */ {
/*     */   public void write(GltfModel gltfModel, String fileName) throws IOException {
/*  69 */     write(gltfModel, new File(fileName));
/*     */   }
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
/*     */ 
/*     */   
/*     */   public void write(GltfModel gltfModel, File file) throws IOException {
/*  86 */     if (gltfModel instanceof GltfModelV1) {
/*     */       
/*  88 */       GltfModelV1 gltfModelV1 = (GltfModelV1)gltfModel;
/*  89 */       GltfModelWriterV1 gltfModelWriterV1 = new GltfModelWriterV1();
/*     */       
/*  91 */       gltfModelWriterV1.write(gltfModelV1, file);
/*     */       return;
/*     */     } 
/*  94 */     GltfModelWriterV2 gltfModelWriterV2 = new GltfModelWriterV2();
/*     */     
/*  96 */     gltfModelWriterV2.write(gltfModel, file);
/*     */   }
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
/*     */   public void writeBinary(GltfModel gltfModel, File file) throws IOException {
/* 110 */     try (OutputStream outputStream = new FileOutputStream(file)) {
/*     */       
/* 112 */       writeBinary(gltfModel, outputStream);
/*     */     } 
/*     */   }
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
/*     */   public void writeBinary(GltfModel gltfModel, OutputStream outputStream) throws IOException {
/* 128 */     if (gltfModel instanceof GltfModelV1) {
/*     */       
/* 130 */       GltfModelV1 gltfModelV1 = (GltfModelV1)gltfModel;
/* 131 */       GltfModelWriterV1 gltfModelWriterV1 = new GltfModelWriterV1();
/*     */       
/* 133 */       gltfModelWriterV1.writeBinary(gltfModelV1, outputStream);
/*     */       return;
/*     */     } 
/* 136 */     GltfModelWriterV2 gltfModelWriterV2 = new GltfModelWriterV2();
/*     */     
/* 138 */     gltfModelWriterV2.writeBinary(gltfModel, outputStream);
/*     */   }
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
/*     */   public void writeEmbedded(GltfModel gltfModel, File file) throws IOException {
/* 153 */     try (OutputStream outputStream = new FileOutputStream(file)) {
/*     */       
/* 155 */       writeEmbedded(gltfModel, outputStream);
/*     */     } 
/*     */   }
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
/*     */   public void writeEmbedded(GltfModel gltfModel, OutputStream outputStream) throws IOException {
/* 171 */     if (gltfModel instanceof GltfModelV1) {
/*     */       
/* 173 */       GltfModelV1 gltfModelV1 = (GltfModelV1)gltfModel;
/* 174 */       GltfModelWriterV1 gltfModelWriterV1 = new GltfModelWriterV1();
/*     */       
/* 176 */       gltfModelWriterV1.writeEmbedded(gltfModelV1, outputStream);
/*     */       return;
/*     */     } 
/* 179 */     GltfModelWriterV2 gltfModelWriterV2 = new GltfModelWriterV2();
/*     */     
/* 181 */     gltfModelWriterV2.writeEmbedded(gltfModel, outputStream);
/*     */   }
/*     */ }


/* Location:              C:\Users\DuduM\OneDrive\Área de Trabalho\mw10\modularwarfare-shining-2023.2.4.4f-fix10.jar!\de\javagl\jgltf\model\io\GltfModelWriter.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */