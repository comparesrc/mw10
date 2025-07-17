/*    */ package net.lingala.zip4j.model;
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
/*    */ public class ExtraDataRecord
/*    */ {
/*    */   private long header;
/*    */   private int sizeOfData;
/*    */   private byte[] data;
/*    */   
/*    */   public long getHeader() {
/* 28 */     return this.header;
/*    */   }
/*    */   
/*    */   public void setHeader(long header) {
/* 32 */     this.header = header;
/*    */   }
/*    */   
/*    */   public int getSizeOfData() {
/* 36 */     return this.sizeOfData;
/*    */   }
/*    */   
/*    */   public void setSizeOfData(int sizeOfData) {
/* 40 */     this.sizeOfData = sizeOfData;
/*    */   }
/*    */   
/*    */   public byte[] getData() {
/* 44 */     return this.data;
/*    */   }
/*    */   
/*    */   public void setData(byte[] data) {
/* 48 */     this.data = data;
/*    */   }
/*    */ }


/* Location:              C:\Users\DuduM\OneDrive\Área de Trabalho\mw10\modularwarfare-shining-2023.2.4.4f-fix10.jar!\net\lingala\zip4j\model\ExtraDataRecord.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */