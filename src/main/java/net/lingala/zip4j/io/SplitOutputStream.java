/*     */ package net.lingala.zip4j.io;
/*     */ 
/*     */ import java.io.File;
/*     */ import java.io.FileNotFoundException;
/*     */ import java.io.IOException;
/*     */ import java.io.OutputStream;
/*     */ import java.io.RandomAccessFile;
/*     */ import net.lingala.zip4j.exception.ZipException;
/*     */ import net.lingala.zip4j.util.Zip4jUtil;
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
/*     */ public class SplitOutputStream
/*     */   extends OutputStream
/*     */ {
/*     */   private RandomAccessFile raf;
/*     */   private long splitLength;
/*     */   private File zipFile;
/*     */   private File outFile;
/*     */   private int currSplitFileCounter;
/*     */   private long bytesWrittenForThisPart;
/*     */   
/*     */   public SplitOutputStream(String name) throws FileNotFoundException, ZipException {
/*  39 */     this(Zip4jUtil.isStringNotNullAndNotEmpty(name) ? new File(name) : null);
/*     */   }
/*     */ 
/*     */   
/*     */   public SplitOutputStream(File file) throws FileNotFoundException, ZipException {
/*  44 */     this(file, -1L);
/*     */   }
/*     */   
/*     */   public SplitOutputStream(String name, long splitLength) throws FileNotFoundException, ZipException {
/*  48 */     this(!Zip4jUtil.isStringNotNullAndNotEmpty(name) ? new File(name) : null, splitLength);
/*     */   }
/*     */ 
/*     */   
/*     */   public SplitOutputStream(File file, long splitLength) throws FileNotFoundException, ZipException {
/*  53 */     if (splitLength >= 0L && splitLength < 65536L) {
/*  54 */       throw new ZipException("split length less than minimum allowed split length of 65536 Bytes");
/*     */     }
/*  56 */     this.raf = new RandomAccessFile(file, "rw");
/*  57 */     this.splitLength = splitLength;
/*  58 */     this.outFile = file;
/*  59 */     this.zipFile = file;
/*  60 */     this.currSplitFileCounter = 0;
/*  61 */     this.bytesWrittenForThisPart = 0L;
/*     */   }
/*     */   
/*     */   public void write(int b) throws IOException {
/*  65 */     byte[] buff = new byte[1];
/*  66 */     buff[0] = (byte)b;
/*  67 */     write(buff, 0, 1);
/*     */   }
/*     */   
/*     */   public void write(byte[] b) throws IOException {
/*  71 */     write(b, 0, b.length);
/*     */   }
/*     */   
/*     */   public void write(byte[] b, int off, int len) throws IOException {
/*  75 */     if (len <= 0)
/*     */       return; 
/*  77 */     if (this.splitLength != -1L) {
/*     */       
/*  79 */       if (this.splitLength < 65536L) {
/*  80 */         throw new IOException("split length less than minimum allowed split length of 65536 Bytes");
/*     */       }
/*     */       
/*  83 */       if (this.bytesWrittenForThisPart >= this.splitLength) {
/*  84 */         startNextSplitFile();
/*  85 */         this.raf.write(b, off, len);
/*  86 */         this.bytesWrittenForThisPart = len;
/*  87 */       } else if (this.bytesWrittenForThisPart + len > this.splitLength) {
/*  88 */         this.raf.write(b, off, (int)(this.splitLength - this.bytesWrittenForThisPart));
/*  89 */         startNextSplitFile();
/*  90 */         this.raf.write(b, off + (int)(this.splitLength - this.bytesWrittenForThisPart), (int)(len - this.splitLength - this.bytesWrittenForThisPart));
/*  91 */         this.bytesWrittenForThisPart = len - this.splitLength - this.bytesWrittenForThisPart;
/*     */       } else {
/*  93 */         this.raf.write(b, off, len);
/*  94 */         this.bytesWrittenForThisPart += len;
/*     */       } 
/*     */     } else {
/*     */       
/*  98 */       this.raf.write(b, off, len);
/*  99 */       this.bytesWrittenForThisPart += len;
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private void startNextSplitFile() throws IOException {
/*     */     try {
/* 106 */       String zipFileWithoutExt = Zip4jUtil.getZipFileNameWithoutExt(this.outFile.getName());
/* 107 */       File currSplitFile = null;
/* 108 */       String zipFileName = this.zipFile.getAbsolutePath();
/*     */       
/* 110 */       if (this.currSplitFileCounter < 9) {
/* 111 */         currSplitFile = new File(this.outFile.getParent() + System.getProperty("file.separator") + zipFileWithoutExt + ".z0" + (this.currSplitFileCounter + 1));
/*     */       } else {
/*     */         
/* 114 */         currSplitFile = new File(this.outFile.getParent() + System.getProperty("file.separator") + zipFileWithoutExt + ".z" + (this.currSplitFileCounter + 1));
/*     */       } 
/*     */ 
/*     */       
/* 118 */       this.raf.close();
/*     */       
/* 120 */       if (currSplitFile.exists()) {
/* 121 */         throw new IOException("split file: " + currSplitFile.getName() + " already exists in the current directory, cannot rename this file");
/*     */       }
/*     */       
/* 124 */       if (!this.zipFile.renameTo(currSplitFile)) {
/* 125 */         throw new IOException("cannot rename newly created split file");
/*     */       }
/*     */       
/* 128 */       this.zipFile = new File(zipFileName);
/* 129 */       this.raf = new RandomAccessFile(this.zipFile, "rw");
/* 130 */       this.currSplitFileCounter++;
/* 131 */     } catch (ZipException e) {
/* 132 */       throw new IOException(e.getMessage());
/*     */     } 
/*     */   }
/*     */   
/*     */   public void seek(long pos) throws IOException {
/* 137 */     this.raf.seek(pos);
/*     */   }
/*     */   
/*     */   public void close() throws IOException {
/* 141 */     if (this.raf != null) {
/* 142 */       this.raf.close();
/*     */     }
/*     */   }
/*     */   
/*     */   public void flush() throws IOException {}
/*     */   
/*     */   public long getFilePointer() throws IOException {
/* 149 */     return this.raf.getFilePointer();
/*     */   }
/*     */   
/*     */   public boolean isSplitZipFile() {
/* 153 */     return (this.splitLength != -1L);
/*     */   }
/*     */   
/*     */   public long getSplitLength() {
/* 157 */     return this.splitLength;
/*     */   }
/*     */   
/*     */   public int getCurrSplitFileCounter() {
/* 161 */     return this.currSplitFileCounter;
/*     */   }
/*     */ }


/* Location:              C:\Users\DuduM\OneDrive\Área de Trabalho\mw10\modularwarfare-shining-2023.2.4.4f-fix10.jar!\net\lingala\zip4j\io\SplitOutputStream.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */