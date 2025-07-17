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
/*    */ public class ArchiveExtraDataRecord
/*    */ {
/*    */   private int signature;
/*    */   private int extraFieldLength;
/*    */   private String extraFieldData;
/*    */   
/*    */   public int getSignature() {
/* 28 */     return this.signature;
/*    */   }
/*    */   
/*    */   public void setSignature(int signature) {
/* 32 */     this.signature = signature;
/*    */   }
/*    */   
/*    */   public int getExtraFieldLength() {
/* 36 */     return this.extraFieldLength;
/*    */   }
/*    */   
/*    */   public void setExtraFieldLength(int extraFieldLength) {
/* 40 */     this.extraFieldLength = extraFieldLength;
/*    */   }
/*    */   
/*    */   public String getExtraFieldData() {
/* 44 */     return this.extraFieldData;
/*    */   }
/*    */   
/*    */   public void setExtraFieldData(String extraFieldData) {
/* 48 */     this.extraFieldData = extraFieldData;
/*    */   }
/*    */ }


/* Location:              C:\Users\DuduM\OneDrive\Área de Trabalho\mw10\modularwarfare-shining-2023.2.4.4f-fix10.jar!\net\lingala\zip4j\model\ArchiveExtraDataRecord.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */