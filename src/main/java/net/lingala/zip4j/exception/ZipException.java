/*    */ package net.lingala.zip4j.exception;
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
/*    */ public class ZipException
/*    */   extends Exception
/*    */ {
/*    */   private static final long serialVersionUID = 1L;
/* 23 */   private int code = -1;
/*    */ 
/*    */   
/*    */   public ZipException() {}
/*    */   
/*    */   public ZipException(String msg) {
/* 29 */     super(msg);
/*    */   }
/*    */   
/*    */   public ZipException(String message, Throwable cause) {
/* 33 */     super(message, cause);
/*    */   }
/*    */   
/*    */   public ZipException(String msg, int code) {
/* 37 */     super(msg);
/* 38 */     this.code = code;
/*    */   }
/*    */   
/*    */   public ZipException(String message, Throwable cause, int code) {
/* 42 */     super(message, cause);
/* 43 */     this.code = code;
/*    */   }
/*    */   
/*    */   public ZipException(Throwable cause) {
/* 47 */     super(cause);
/*    */   }
/*    */   
/*    */   public ZipException(Throwable cause, int code) {
/* 51 */     super(cause);
/* 52 */     this.code = code;
/*    */   }
/*    */   
/*    */   public int getCode() {
/* 56 */     return this.code;
/*    */   }
/*    */ }


/* Location:              C:\Users\DuduM\OneDrive\Área de Trabalho\mw10\modularwarfare-shining-2023.2.4.4f-fix10.jar!\net\lingala\zip4j\exception\ZipException.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */