/*    */ package net.lingala.zip4j.model;
/*    */ 
/*    */ import java.util.ArrayList;
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
/*    */ public class CentralDirectory
/*    */ {
/*    */   private ArrayList fileHeaders;
/*    */   private DigitalSignature digitalSignature;
/*    */   
/*    */   public ArrayList getFileHeaders() {
/* 28 */     return this.fileHeaders;
/*    */   }
/*    */   
/*    */   public void setFileHeaders(ArrayList fileHeaders) {
/* 32 */     this.fileHeaders = fileHeaders;
/*    */   }
/*    */   
/*    */   public DigitalSignature getDigitalSignature() {
/* 36 */     return this.digitalSignature;
/*    */   }
/*    */   
/*    */   public void setDigitalSignature(DigitalSignature digitalSignature) {
/* 40 */     this.digitalSignature = digitalSignature;
/*    */   }
/*    */ }


/* Location:              C:\Users\DuduM\OneDrive\Área de Trabalho\mw10\modularwarfare-shining-2023.2.4.4f-fix10.jar!\net\lingala\zip4j\model\CentralDirectory.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */