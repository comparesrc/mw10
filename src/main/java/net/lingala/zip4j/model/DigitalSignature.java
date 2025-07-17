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
/*    */ public class DigitalSignature
/*    */ {
/*    */   private int headerSignature;
/*    */   private int sizeOfData;
/*    */   private String signatureData;
/*    */   
/*    */   public int getHeaderSignature() {
/* 28 */     return this.headerSignature;
/*    */   }
/*    */   
/*    */   public void setHeaderSignature(int headerSignature) {
/* 32 */     this.headerSignature = headerSignature;
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
/*    */   public String getSignatureData() {
/* 44 */     return this.signatureData;
/*    */   }
/*    */   
/*    */   public void setSignatureData(String signatureData) {
/* 48 */     this.signatureData = signatureData;
/*    */   }
/*    */ }


/* Location:              C:\Users\DuduM\OneDrive\Área de Trabalho\mw10\modularwarfare-shining-2023.2.4.4f-fix10.jar!\net\lingala\zip4j\model\DigitalSignature.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */