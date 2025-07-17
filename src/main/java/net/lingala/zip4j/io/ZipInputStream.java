/*    */ package net.lingala.zip4j.io;
/*    */ 
/*    */ import java.io.IOException;
/*    */ import java.io.InputStream;
/*    */ import net.lingala.zip4j.exception.ZipException;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class ZipInputStream
/*    */   extends InputStream
/*    */ {
/*    */   private BaseInputStream is;
/*    */   
/*    */   public ZipInputStream(BaseInputStream is) {
/* 29 */     this.is = is;
/*    */   }
/*    */   
/*    */   public int read() throws IOException {
/* 33 */     int readByte = this.is.read();
/* 34 */     if (readByte != -1) {
/* 35 */       this.is.getUnzipEngine().updateCRC(readByte);
/*    */     }
/* 37 */     return readByte;
/*    */   }
/*    */   
/*    */   public int read(byte[] b) throws IOException {
/* 41 */     return read(b, 0, b.length);
/*    */   }
/*    */   
/*    */   public int read(byte[] b, int off, int len) throws IOException {
/* 45 */     int readLen = this.is.read(b, off, len);
/* 46 */     if (readLen > 0 && this.is.getUnzipEngine() != null) {
/* 47 */       this.is.getUnzipEngine().updateCRC(b, off, readLen);
/*    */     }
/* 49 */     return readLen;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void close() throws IOException {
/* 60 */     close(false);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void close(boolean skipCRCCheck) throws IOException {
/*    */     try {
/* 72 */       this.is.close();
/* 73 */       if (!skipCRCCheck && this.is.getUnzipEngine() != null) {
/* 74 */         this.is.getUnzipEngine().checkCRC();
/*    */       }
/* 76 */     } catch (ZipException e) {
/* 77 */       throw new IOException(e.getMessage());
/*    */     } 
/*    */   }
/*    */   
/*    */   public int available() throws IOException {
/* 82 */     return this.is.available();
/*    */   }
/*    */   
/*    */   public long skip(long n) throws IOException {
/* 86 */     return this.is.skip(n);
/*    */   }
/*    */ }


/* Location:              C:\Users\DuduM\OneDrive\Área de Trabalho\mw10\modularwarfare-shining-2023.2.4.4f-fix10.jar!\net\lingala\zip4j\io\ZipInputStream.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */