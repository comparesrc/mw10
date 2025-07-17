/*    */ package net.lingala.zip4j.model;
/*    */ 
/*    */ import java.io.FileOutputStream;
/*    */ import net.lingala.zip4j.crypto.IDecrypter;
/*    */ import net.lingala.zip4j.unzip.UnzipEngine;
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
/*    */ public class UnzipEngineParameters
/*    */ {
/*    */   private ZipModel zipModel;
/*    */   private FileHeader fileHeader;
/*    */   private LocalFileHeader localFileHeader;
/*    */   private IDecrypter iDecryptor;
/*    */   private FileOutputStream outputStream;
/*    */   private UnzipEngine unzipEngine;
/*    */   
/*    */   public ZipModel getZipModel() {
/* 39 */     return this.zipModel;
/*    */   }
/*    */   
/*    */   public void setZipModel(ZipModel zipModel) {
/* 43 */     this.zipModel = zipModel;
/*    */   }
/*    */   
/*    */   public FileHeader getFileHeader() {
/* 47 */     return this.fileHeader;
/*    */   }
/*    */   
/*    */   public void setFileHeader(FileHeader fileHeader) {
/* 51 */     this.fileHeader = fileHeader;
/*    */   }
/*    */   
/*    */   public LocalFileHeader getLocalFileHeader() {
/* 55 */     return this.localFileHeader;
/*    */   }
/*    */   
/*    */   public void setLocalFileHeader(LocalFileHeader localFileHeader) {
/* 59 */     this.localFileHeader = localFileHeader;
/*    */   }
/*    */   
/*    */   public IDecrypter getIDecryptor() {
/* 63 */     return this.iDecryptor;
/*    */   }
/*    */   
/*    */   public void setIDecryptor(IDecrypter decrypter) {
/* 67 */     this.iDecryptor = decrypter;
/*    */   }
/*    */   
/*    */   public FileOutputStream getOutputStream() {
/* 71 */     return this.outputStream;
/*    */   }
/*    */   
/*    */   public void setOutputStream(FileOutputStream outputStream) {
/* 75 */     this.outputStream = outputStream;
/*    */   }
/*    */   
/*    */   public UnzipEngine getUnzipEngine() {
/* 79 */     return this.unzipEngine;
/*    */   }
/*    */   
/*    */   public void setUnzipEngine(UnzipEngine unzipEngine) {
/* 83 */     this.unzipEngine = unzipEngine;
/*    */   }
/*    */ }


/* Location:              C:\Users\DuduM\OneDrive\Área de Trabalho\mw10\modularwarfare-shining-2023.2.4.4f-fix10.jar!\net\lingala\zip4j\model\UnzipEngineParameters.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */