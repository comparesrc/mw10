/*    */ package net.lingala.zip4j.io;
/*    */ 
/*    */ import java.io.IOException;
/*    */ import java.io.OutputStream;
/*    */ import net.lingala.zip4j.model.ZipModel;
/*    */ 
/*    */ public class ZipOutputStream
/*    */   extends DeflaterOutputStream
/*    */ {
/*    */   public ZipOutputStream(OutputStream outputStream) {
/* 11 */     this(outputStream, null);
/*    */   }
/*    */   
/*    */   public ZipOutputStream(OutputStream outputStream, ZipModel zipModel) {
/* 15 */     super(outputStream, zipModel);
/*    */   }
/*    */   
/*    */   public void write(int bval) throws IOException {
/* 19 */     byte[] b = new byte[1];
/* 20 */     b[0] = (byte)bval;
/* 21 */     write(b, 0, 1);
/*    */   }
/*    */   
/*    */   public void write(byte[] b) throws IOException {
/* 25 */     write(b, 0, b.length);
/*    */   }
/*    */   
/*    */   public void write(byte[] b, int off, int len) throws IOException {
/* 29 */     this.crc.update(b, off, len);
/* 30 */     super.write(b, off, len);
/*    */   }
/*    */ }


/* Location:              C:\Users\DuduM\OneDrive\Área de Trabalho\mw10\modularwarfare-shining-2023.2.4.4f-fix10.jar!\net\lingala\zip4j\io\ZipOutputStream.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */