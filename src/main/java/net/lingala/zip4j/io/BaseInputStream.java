/*    */ package net.lingala.zip4j.io;
/*    */ 
/*    */ import java.io.IOException;
/*    */ import java.io.InputStream;
/*    */ import net.lingala.zip4j.unzip.UnzipEngine;
/*    */ 
/*    */ public abstract class BaseInputStream
/*    */   extends InputStream
/*    */ {
/*    */   public int read() throws IOException {
/* 11 */     return 0;
/*    */   }
/*    */ 
/*    */   
/*    */   public void seek(long pos) throws IOException {}
/*    */   
/*    */   public int available() throws IOException {
/* 18 */     return 0;
/*    */   }
/*    */   
/*    */   public UnzipEngine getUnzipEngine() {
/* 22 */     return null;
/*    */   }
/*    */ }


/* Location:              C:\Users\DuduM\OneDrive\Área de Trabalho\mw10\modularwarfare-shining-2023.2.4.4f-fix10.jar!\net\lingala\zip4j\io\BaseInputStream.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */