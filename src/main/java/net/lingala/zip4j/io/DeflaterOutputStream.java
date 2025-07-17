/*     */ package net.lingala.zip4j.io;
/*     */ 
/*     */ import java.io.File;
/*     */ import java.io.IOException;
/*     */ import java.io.OutputStream;
/*     */ import java.util.zip.Deflater;
/*     */ import net.lingala.zip4j.exception.ZipException;
/*     */ import net.lingala.zip4j.model.ZipModel;
/*     */ import net.lingala.zip4j.model.ZipParameters;
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
/*     */ public class DeflaterOutputStream
/*     */   extends CipherOutputStream
/*     */ {
/*     */   private byte[] buff;
/*     */   protected Deflater deflater;
/*     */   private boolean firstBytesRead;
/*     */   
/*     */   public DeflaterOutputStream(OutputStream outputStream, ZipModel zipModel) {
/*  37 */     super(outputStream, zipModel);
/*  38 */     this.deflater = new Deflater();
/*  39 */     this.buff = new byte[4096];
/*  40 */     this.firstBytesRead = false;
/*     */   }
/*     */ 
/*     */   
/*     */   public void putNextEntry(File file, ZipParameters zipParameters) throws ZipException {
/*  45 */     super.putNextEntry(file, zipParameters);
/*  46 */     if (zipParameters.getCompressionMethod() == 8) {
/*  47 */       this.deflater.reset();
/*  48 */       if ((zipParameters.getCompressionLevel() < 0 || zipParameters.getCompressionLevel() > 9) && zipParameters.getCompressionLevel() != -1)
/*     */       {
/*     */         
/*  51 */         throw new ZipException("invalid compression level for deflater. compression level should be in the range of 0-9");
/*     */       }
/*     */       
/*  54 */       this.deflater.setLevel(zipParameters.getCompressionLevel());
/*     */     } 
/*     */   }
/*     */   
/*     */   public void write(byte[] b) throws IOException {
/*  59 */     write(b, 0, b.length);
/*     */   }
/*     */   
/*     */   private void deflate() throws IOException {
/*  63 */     int len = this.deflater.deflate(this.buff, 0, this.buff.length);
/*  64 */     if (len > 0) {
/*  65 */       if (this.deflater.finished()) len -= 4; 
/*  66 */       if (!this.firstBytesRead) {
/*  67 */         super.write(this.buff, 2, len - 2);
/*  68 */         this.firstBytesRead = true;
/*     */       } else {
/*  70 */         super.write(this.buff, 0, len);
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   public void write(int bval) throws IOException {
/*  76 */     byte[] b = new byte[1];
/*  77 */     b[0] = (byte)bval;
/*  78 */     write(b, 0, 1);
/*     */   }
/*     */   
/*     */   public void write(byte[] buf, int off, int len) throws IOException {
/*  82 */     if (this.zipParameters.getCompressionMethod() != 8) {
/*  83 */       super.write(buf, off, len);
/*     */     } else {
/*  85 */       this.deflater.setInput(buf, off, len);
/*  86 */       while (!this.deflater.needsInput()) {
/*  87 */         deflate();
/*     */       }
/*     */     } 
/*     */   }
/*     */   
/*     */   public void closeEntry() throws IOException, ZipException {
/*  93 */     if (this.zipParameters.getCompressionMethod() == 8) {
/*  94 */       if (!this.deflater.finished()) {
/*  95 */         this.deflater.finish();
/*  96 */         while (!this.deflater.finished()) {
/*  97 */           deflate();
/*     */         }
/*     */       } 
/* 100 */       this.firstBytesRead = false;
/*     */     } 
/* 102 */     super.closeEntry();
/*     */   }
/*     */   
/*     */   public void finish() throws IOException, ZipException {
/* 106 */     super.finish();
/*     */   }
/*     */ }


/* Location:              C:\Users\DuduM\OneDrive\Área de Trabalho\mw10\modularwarfare-shining-2023.2.4.4f-fix10.jar!\net\lingala\zip4j\io\DeflaterOutputStream.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */