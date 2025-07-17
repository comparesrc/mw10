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
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class AESExtraDataRecord
/*    */ {
/* 29 */   private long signature = -1L;
/* 30 */   private int dataSize = -1;
/* 31 */   private int versionNumber = -1;
/* 32 */   private String vendorID = null;
/* 33 */   private int aesStrength = -1;
/* 34 */   private int compressionMethod = -1;
/*    */ 
/*    */ 
/*    */   
/*    */   public long getSignature() {
/* 39 */     return this.signature;
/*    */   }
/*    */ 
/*    */   
/*    */   public void setSignature(long signature) {
/* 44 */     this.signature = signature;
/*    */   }
/*    */ 
/*    */   
/*    */   public int getDataSize() {
/* 49 */     return this.dataSize;
/*    */   }
/*    */ 
/*    */   
/*    */   public void setDataSize(int dataSize) {
/* 54 */     this.dataSize = dataSize;
/*    */   }
/*    */ 
/*    */   
/*    */   public int getVersionNumber() {
/* 59 */     return this.versionNumber;
/*    */   }
/*    */ 
/*    */   
/*    */   public void setVersionNumber(int versionNumber) {
/* 64 */     this.versionNumber = versionNumber;
/*    */   }
/*    */ 
/*    */   
/*    */   public String getVendorID() {
/* 69 */     return this.vendorID;
/*    */   }
/*    */ 
/*    */   
/*    */   public void setVendorID(String vendorID) {
/* 74 */     this.vendorID = vendorID;
/*    */   }
/*    */ 
/*    */   
/*    */   public int getAesStrength() {
/* 79 */     return this.aesStrength;
/*    */   }
/*    */ 
/*    */   
/*    */   public void setAesStrength(int aesStrength) {
/* 84 */     this.aesStrength = aesStrength;
/*    */   }
/*    */ 
/*    */   
/*    */   public int getCompressionMethod() {
/* 89 */     return this.compressionMethod;
/*    */   }
/*    */ 
/*    */   
/*    */   public void setCompressionMethod(int compressionMethod) {
/* 94 */     this.compressionMethod = compressionMethod;
/*    */   }
/*    */ }


/* Location:              C:\Users\DuduM\OneDrive\Área de Trabalho\mw10\modularwarfare-shining-2023.2.4.4f-fix10.jar!\net\lingala\zip4j\model\AESExtraDataRecord.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */