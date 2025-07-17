/*    */ package de.javagl.jgltf.model.io;
/*    */ 
/*    */ import java.io.IOException;
/*    */ import java.io.InputStream;
/*    */ import java.nio.ByteBuffer;
/*    */ import java.util.Objects;
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
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ class ByteBufferInputStream
/*    */   extends InputStream
/*    */ {
/*    */   private final ByteBuffer byteBuffer;
/*    */   
/*    */   ByteBufferInputStream(ByteBuffer byteBuffer) {
/* 54 */     this.byteBuffer = Objects.<ByteBuffer>requireNonNull(byteBuffer, "The byteBuffer may not be null");
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public int read() throws IOException {
/* 61 */     if (!this.byteBuffer.hasRemaining())
/*    */     {
/* 63 */       return -1;
/*    */     }
/* 65 */     return this.byteBuffer.get() & 0xFF;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public int read(byte[] bytes, int off, int len) throws IOException {
/* 71 */     if (!this.byteBuffer.hasRemaining())
/*    */     {
/* 73 */       return -1;
/*    */     }
/* 75 */     int readLength = Math.min(len, this.byteBuffer.remaining());
/* 76 */     this.byteBuffer.get(bytes, off, readLength);
/* 77 */     return readLength;
/*    */   }
/*    */ }


/* Location:              C:\Users\DuduM\OneDrive\Área de Trabalho\mw10\modularwarfare-shining-2023.2.4.4f-fix10.jar!\de\javagl\jgltf\model\io\ByteBufferInputStream.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */