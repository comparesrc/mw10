/*    */ package moe.komi.mwprotect;
/*    */ 
/*    */ import java.io.IOException;
/*    */ import java.io.InputStream;
/*    */ 
/*    */ public class ProtectInputStream
/*    */   extends InputStream {
/*    */   private final long nativeFileHandle;
/*    */   private long currentPos;
/*    */   
/*    */   public ProtectInputStream(long nativeFileHandle) throws IOException {
/* 12 */     this.nativeFileHandle = nativeFileHandle;
/* 13 */     this.currentPos = 0L;
/*    */   }
/*    */ 
/*    */   
/*    */   public int read() throws IOException {
/* 18 */     int data = Native.read0(this.currentPos, this.nativeFileHandle);
/* 19 */     if (data != -1) {
/* 20 */       this.currentPos++;
/*    */     }
/* 22 */     return data;
/*    */   }
/*    */ 
/*    */   
/*    */   public int read(byte[] bytes) throws IOException {
/* 27 */     return read(bytes, 0, bytes.length);
/*    */   }
/*    */ 
/*    */   
/*    */   public int read(byte[] b, int off, int len) throws IOException {
/* 32 */     if (len <= 0) {
/* 33 */       return 0;
/*    */     }
/* 35 */     if (off + len > b.length) {
/* 36 */       throw new IOException("Not enough buffer space");
/*    */     }
/*    */     
/* 39 */     long readCount = Native.read0(b, off, this.currentPos, len, this.nativeFileHandle);
/* 40 */     if (readCount > 0L) {
/* 41 */       this.currentPos += readCount;
/*    */     }
/* 43 */     return (int)readCount;
/*    */   }
/*    */ 
/*    */   
/*    */   public long skip(long n) throws IOException {
/* 48 */     long fileSize = Native.available0(this.nativeFileHandle);
/* 49 */     long skipCount = Math.min(fileSize - this.currentPos, n);
/* 50 */     this.currentPos += skipCount;
/* 51 */     return skipCount;
/*    */   }
/*    */ 
/*    */   
/*    */   public int available() throws IOException {
/* 56 */     return (int)(Native.available0(this.nativeFileHandle) - this.currentPos);
/*    */   }
/*    */ 
/*    */   
/*    */   public void close() throws IOException {
/* 61 */     super.close();
/* 62 */     Native.close0(this.nativeFileHandle);
/*    */   }
/*    */ }


/* Location:              C:\Users\DuduM\OneDrive\Área de Trabalho\mw10\modularwarfare-shining-2023.2.4.4f-fix10.jar!\moe\komi\mwprotect\ProtectInputStream.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */