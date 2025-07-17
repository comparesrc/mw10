/*     */ package net.lingala.zip4j.io;
/*     */ 
/*     */ import java.io.EOFException;
/*     */ import java.io.IOException;
/*     */ import java.io.RandomAccessFile;
/*     */ import java.util.zip.DataFormatException;
/*     */ import java.util.zip.Inflater;
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
/*     */ 
/*     */ public class InflaterInputStream
/*     */   extends PartInputStream
/*     */ {
/*     */   private Inflater inflater;
/*     */   private byte[] buff;
/*  33 */   private byte[] oneByteBuff = new byte[1];
/*     */   private UnzipEngine unzipEngine;
/*     */   private long bytesWritten;
/*     */   private long uncompressedSize;
/*     */   
/*     */   public InflaterInputStream(RandomAccessFile raf, long start, long len, UnzipEngine unzipEngine) {
/*  39 */     super(raf, start, len, unzipEngine);
/*  40 */     this.inflater = new Inflater(true);
/*  41 */     this.buff = new byte[4096];
/*  42 */     this.unzipEngine = unzipEngine;
/*  43 */     this.bytesWritten = 0L;
/*  44 */     this.uncompressedSize = unzipEngine.getFileHeader().getUncompressedSize();
/*     */   }
/*     */   
/*     */   public int read() throws IOException {
/*  48 */     return (read(this.oneByteBuff, 0, 1) == -1) ? -1 : (this.oneByteBuff[0] & 0xFF);
/*     */   }
/*     */   
/*     */   public int read(byte[] b) throws IOException {
/*  52 */     if (b == null) {
/*  53 */       throw new NullPointerException("input buffer is null");
/*     */     }
/*     */     
/*  56 */     return read(b, 0, b.length);
/*     */   }
/*     */ 
/*     */   
/*     */   public int read(byte[] b, int off, int len) throws IOException {
/*  61 */     if (b == null)
/*  62 */       throw new NullPointerException("input buffer is null"); 
/*  63 */     if (off < 0 || len < 0 || len > b.length - off)
/*  64 */       throw new IndexOutOfBoundsException(); 
/*  65 */     if (len == 0) {
/*  66 */       return 0;
/*     */     }
/*     */ 
/*     */     
/*     */     try {
/*  71 */       if (this.bytesWritten >= this.uncompressedSize)
/*  72 */         return -1;  int n;
/*  73 */       while ((n = this.inflater.inflate(b, off, len)) == 0) {
/*  74 */         if (this.inflater.finished() || this.inflater.needsDictionary()) {
/*  75 */           return -1;
/*     */         }
/*  77 */         if (this.inflater.needsInput()) {
/*  78 */           fill();
/*     */         }
/*     */       } 
/*  81 */       this.bytesWritten += n;
/*  82 */       return n;
/*  83 */     } catch (DataFormatException e) {
/*  84 */       String s = "Invalid ZLIB data format";
/*  85 */       if (e.getMessage() != null) {
/*  86 */         s = e.getMessage();
/*     */       }
/*  88 */       if (this.unzipEngine != null && 
/*  89 */         this.unzipEngine.getLocalFileHeader().isEncrypted() && this.unzipEngine.getLocalFileHeader().getEncryptionMethod() == 0)
/*     */       {
/*  91 */         s = s + " - Wrong Password?";
/*     */       }
/*     */       
/*  94 */       throw new IOException(s);
/*     */     } 
/*     */   }
/*     */   
/*     */   private void fill() throws IOException {
/*  99 */     int len = super.read(this.buff, 0, this.buff.length);
/* 100 */     if (len == -1) {
/* 101 */       throw new EOFException("Unexpected end of ZLIB input stream");
/*     */     }
/* 103 */     this.inflater.setInput(this.buff, 0, len);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public long skip(long n) throws IOException {
/* 114 */     if (n < 0L) {
/* 115 */       throw new IllegalArgumentException("negative skip length");
/*     */     }
/* 117 */     int max = (int)Math.min(n, 2147483647L);
/* 118 */     int total = 0;
/* 119 */     byte[] b = new byte[512];
/* 120 */     while (total < max) {
/* 121 */       int len = max - total;
/* 122 */       if (len > b.length) {
/* 123 */         len = b.length;
/*     */       }
/* 125 */       len = read(b, 0, len);
/* 126 */       if (len == -1) {
/*     */         break;
/*     */       }
/* 129 */       total += len;
/*     */     } 
/* 131 */     return total;
/*     */   }
/*     */ 
/*     */   
/*     */   public void seek(long pos) throws IOException {
/* 136 */     super.seek(pos);
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
/*     */   public int available() {
/* 150 */     return this.inflater.finished() ? 0 : 1;
/*     */   }
/*     */   
/*     */   public void close() throws IOException {
/* 154 */     super.close();
/*     */   }
/*     */   
/*     */   public UnzipEngine getUnzipEngine() {
/* 158 */     return super.getUnzipEngine();
/*     */   }
/*     */ }


/* Location:              C:\Users\DuduM\OneDrive\Área de Trabalho\mw10\modularwarfare-shining-2023.2.4.4f-fix10.jar!\net\lingala\zip4j\io\InflaterInputStream.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */