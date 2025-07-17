/*    */ package net.lingala.zip4j.util;
/*    */ 
/*    */ import java.io.File;
/*    */ import java.io.FileInputStream;
/*    */ import java.io.IOException;
/*    */ import java.io.InputStream;
/*    */ import java.util.zip.CRC32;
/*    */ import net.lingala.zip4j.exception.ZipException;
/*    */ import net.lingala.zip4j.progress.ProgressMonitor;
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
/*    */ public class CRCUtil
/*    */ {
/*    */   private static final int BUF_SIZE = 16384;
/*    */   
/*    */   public static long computeFileCRC(String inputFile) throws ZipException {
/* 33 */     return computeFileCRC(inputFile, null);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public static long computeFileCRC(String inputFile, ProgressMonitor progressMonitor) throws ZipException {
/* 44 */     if (!Zip4jUtil.isStringNotNullAndNotEmpty(inputFile)) {
/* 45 */       throw new ZipException("input file is null or empty, cannot calculate CRC for the file");
/*    */     }
/* 47 */     InputStream inputStream = null;
/*    */     try {
/* 49 */       Zip4jUtil.checkFileReadAccess(inputFile);
/*    */       
/* 51 */       inputStream = new FileInputStream(new File(inputFile));
/*    */       
/* 53 */       byte[] buff = new byte[16384];
/* 54 */       int readLen = -2;
/* 55 */       CRC32 crc32 = new CRC32();
/*    */       
/* 57 */       while ((readLen = inputStream.read(buff)) != -1) {
/* 58 */         crc32.update(buff, 0, readLen);
/* 59 */         if (progressMonitor != null) {
/* 60 */           progressMonitor.updateWorkCompleted(readLen);
/* 61 */           if (progressMonitor.isCancelAllTasks()) {
/* 62 */             progressMonitor.setResult(3);
/*    */             
/* 64 */             progressMonitor.setState(0);
/* 65 */             return 0L;
/*    */           } 
/*    */         } 
/*    */       } 
/*    */       
/* 70 */       return crc32.getValue();
/* 71 */     } catch (IOException e) {
/* 72 */       throw new ZipException(e);
/* 73 */     } catch (Exception e) {
/* 74 */       throw new ZipException(e);
/*    */     } finally {
/* 76 */       if (inputStream != null)
/*    */         try {
/* 78 */           inputStream.close();
/* 79 */         } catch (IOException e) {
/* 80 */           throw new ZipException("error while closing the file after calculating crc");
/*    */         }  
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\DuduM\OneDrive\Área de Trabalho\mw10\modularwarfare-shining-2023.2.4.4f-fix10.jar!\net\lingala\zip4\\util\CRCUtil.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */