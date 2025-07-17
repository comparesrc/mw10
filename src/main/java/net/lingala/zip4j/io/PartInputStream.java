/*     */ package net.lingala.zip4j.io;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.io.RandomAccessFile;
/*     */ import net.lingala.zip4j.crypto.AESDecrypter;
/*     */ import net.lingala.zip4j.crypto.IDecrypter;
/*     */ import net.lingala.zip4j.exception.ZipException;
/*     */ import net.lingala.zip4j.unzip.UnzipEngine;
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
/*     */ public class PartInputStream
/*     */   extends BaseInputStream
/*     */ {
/*     */   private RandomAccessFile raf;
/*     */   private long bytesRead;
/*     */   private long length;
/*     */   private UnzipEngine unzipEngine;
/*     */   private IDecrypter decrypter;
/*  35 */   private byte[] oneByteBuff = new byte[1];
/*  36 */   private byte[] aesBlockByte = new byte[16];
/*  37 */   private int aesBytesReturned = 0;
/*     */   private boolean isAESEncryptedFile = false;
/*  39 */   private int count = -1;
/*     */   
/*     */   public PartInputStream(RandomAccessFile raf, long start, long len, UnzipEngine unzipEngine) {
/*  42 */     this.raf = raf;
/*  43 */     this.unzipEngine = unzipEngine;
/*  44 */     this.decrypter = unzipEngine.getDecrypter();
/*  45 */     this.bytesRead = 0L;
/*  46 */     this.length = len;
/*  47 */     this.isAESEncryptedFile = (unzipEngine.getFileHeader().isEncrypted() && unzipEngine.getFileHeader().getEncryptionMethod() == 99);
/*     */   }
/*     */ 
/*     */   
/*     */   public int available() {
/*  52 */     long amount = this.length - this.bytesRead;
/*  53 */     if (amount > 2147483647L)
/*  54 */       return Integer.MAX_VALUE; 
/*  55 */     return (int)amount;
/*     */   }
/*     */   
/*     */   public int read() throws IOException {
/*  59 */     if (this.bytesRead >= this.length) {
/*  60 */       return -1;
/*     */     }
/*  62 */     if (this.isAESEncryptedFile) {
/*  63 */       if (this.aesBytesReturned == 0 || this.aesBytesReturned == 16) {
/*  64 */         if (read(this.aesBlockByte) == -1) {
/*  65 */           return -1;
/*     */         }
/*  67 */         this.aesBytesReturned = 0;
/*     */       } 
/*  69 */       return this.aesBlockByte[this.aesBytesReturned++] & 0xFF;
/*     */     } 
/*  71 */     return (read(this.oneByteBuff, 0, 1) == -1) ? -1 : (this.oneByteBuff[0] & 0xFF);
/*     */   }
/*     */ 
/*     */   
/*     */   public int read(byte[] b) throws IOException {
/*  76 */     return read(b, 0, b.length);
/*     */   }
/*     */   
/*     */   public int read(byte[] b, int off, int len) throws IOException {
/*  80 */     if (len > this.length - this.bytesRead) {
/*  81 */       len = (int)(this.length - this.bytesRead);
/*  82 */       if (len == 0) {
/*  83 */         checkAndReadAESMacBytes();
/*  84 */         return -1;
/*     */       } 
/*     */     } 
/*     */     
/*  88 */     if (this.unzipEngine.getDecrypter() instanceof AESDecrypter && 
/*  89 */       this.bytesRead + len < this.length && 
/*  90 */       len % 16 != 0) {
/*  91 */       len -= len % 16;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*  96 */     synchronized (this.raf) {
/*  97 */       this.count = this.raf.read(b, off, len);
/*  98 */       if (this.count < len && this.unzipEngine.getZipModel().isSplitArchive()) {
/*  99 */         this.raf.close();
/* 100 */         this.raf = this.unzipEngine.startNextSplitFile();
/* 101 */         if (this.count < 0) this.count = 0; 
/* 102 */         int newlyRead = this.raf.read(b, this.count, len - this.count);
/* 103 */         if (newlyRead > 0) {
/* 104 */           this.count += newlyRead;
/*     */         }
/*     */       } 
/*     */     } 
/* 108 */     if (this.count > 0) {
/* 109 */       if (this.decrypter != null) {
/*     */         try {
/* 111 */           this.decrypter.decryptData(b, off, this.count);
/* 112 */         } catch (ZipException e) {
/* 113 */           throw new IOException(e.getMessage());
/*     */         } 
/*     */       }
/* 116 */       this.bytesRead += this.count;
/*     */     } 
/*     */     
/* 119 */     if (this.bytesRead >= this.length) {
/* 120 */       checkAndReadAESMacBytes();
/*     */     }
/*     */     
/* 123 */     return this.count;
/*     */   }
/*     */   
/*     */   private void checkAndReadAESMacBytes() throws IOException {
/* 127 */     if (this.isAESEncryptedFile && 
/* 128 */       this.decrypter != null && this.decrypter instanceof AESDecrypter) {
/* 129 */       if (((AESDecrypter)this.decrypter).getStoredMac() != null) {
/*     */         return;
/*     */       }
/*     */       
/* 133 */       byte[] macBytes = new byte[10];
/* 134 */       int readLen = -1;
/* 135 */       readLen = this.raf.read(macBytes);
/* 136 */       if (readLen != 10) {
/* 137 */         if (this.unzipEngine.getZipModel().isSplitArchive()) {
/* 138 */           this.raf.close();
/* 139 */           this.raf = this.unzipEngine.startNextSplitFile();
/* 140 */           int newlyRead = this.raf.read(macBytes, readLen, 10 - readLen);
/* 141 */           readLen += newlyRead;
/*     */         } else {
/* 143 */           throw new IOException("Error occured while reading stored AES authentication bytes");
/*     */         } 
/*     */       }
/*     */       
/* 147 */       ((AESDecrypter)this.unzipEngine.getDecrypter()).setStoredMac(macBytes);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public long skip(long amount) throws IOException {
/* 153 */     if (amount < 0L)
/* 154 */       throw new IllegalArgumentException(); 
/* 155 */     if (amount > this.length - this.bytesRead)
/* 156 */       amount = this.length - this.bytesRead; 
/* 157 */     this.bytesRead += amount;
/* 158 */     return amount;
/*     */   }
/*     */   
/*     */   public void close() throws IOException {
/* 162 */     this.raf.close();
/*     */   }
/*     */   
/*     */   public void seek(long pos) throws IOException {
/* 166 */     this.raf.seek(pos);
/*     */   }
/*     */   
/*     */   public UnzipEngine getUnzipEngine() {
/* 170 */     return this.unzipEngine;
/*     */   }
/*     */ }


/* Location:              C:\Users\DuduM\OneDrive\Área de Trabalho\mw10\modularwarfare-shining-2023.2.4.4f-fix10.jar!\net\lingala\zip4j\io\PartInputStream.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */