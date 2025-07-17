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
/*    */ public class Zip64EndCentralDirLocator
/*    */ {
/*    */   private long signature;
/*    */   private int noOfDiskStartOfZip64EndOfCentralDirRec;
/*    */   private long offsetZip64EndOfCentralDirRec;
/*    */   private int totNumberOfDiscs;
/*    */   
/*    */   public long getSignature() {
/* 30 */     return this.signature;
/*    */   }
/*    */   
/*    */   public void setSignature(long signature) {
/* 34 */     this.signature = signature;
/*    */   }
/*    */   
/*    */   public int getNoOfDiskStartOfZip64EndOfCentralDirRec() {
/* 38 */     return this.noOfDiskStartOfZip64EndOfCentralDirRec;
/*    */   }
/*    */ 
/*    */   
/*    */   public void setNoOfDiskStartOfZip64EndOfCentralDirRec(int noOfDiskStartOfZip64EndOfCentralDirRec) {
/* 43 */     this.noOfDiskStartOfZip64EndOfCentralDirRec = noOfDiskStartOfZip64EndOfCentralDirRec;
/*    */   }
/*    */   
/*    */   public long getOffsetZip64EndOfCentralDirRec() {
/* 47 */     return this.offsetZip64EndOfCentralDirRec;
/*    */   }
/*    */   
/*    */   public void setOffsetZip64EndOfCentralDirRec(long offsetZip64EndOfCentralDirRec) {
/* 51 */     this.offsetZip64EndOfCentralDirRec = offsetZip64EndOfCentralDirRec;
/*    */   }
/*    */   
/*    */   public int getTotNumberOfDiscs() {
/* 55 */     return this.totNumberOfDiscs;
/*    */   }
/*    */   
/*    */   public void setTotNumberOfDiscs(int totNumberOfDiscs) {
/* 59 */     this.totNumberOfDiscs = totNumberOfDiscs;
/*    */   }
/*    */ }


/* Location:              C:\Users\DuduM\OneDrive\Área de Trabalho\mw10\modularwarfare-shining-2023.2.4.4f-fix10.jar!\net\lingala\zip4j\model\Zip64EndCentralDirLocator.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */