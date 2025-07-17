/*     */ package net.lingala.zip4j.unzip;
/*     */ 
/*     */ import java.io.File;
/*     */ import java.util.TimeZone;
/*     */ import net.lingala.zip4j.exception.ZipException;
/*     */ import net.lingala.zip4j.model.FileHeader;
/*     */ import net.lingala.zip4j.model.UnzipParameters;
/*     */ import net.lingala.zip4j.util.Zip4jUtil;
/*     */ 
/*     */ 
/*     */ 
/*     */ public class UnzipUtil
/*     */ {
/*  14 */   private static TimeZone defaultTimeZone = TimeZone.getDefault();
/*     */   
/*     */   public static void applyFileAttributes(FileHeader fileHeader, File file) throws ZipException {
/*  17 */     applyFileAttributes(fileHeader, file, null);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static void applyFileAttributes(FileHeader fileHeader, File file, UnzipParameters unzipParameters) throws ZipException {
/*  23 */     if (fileHeader == null) {
/*  24 */       throw new ZipException("cannot set file properties: file header is null");
/*     */     }
/*     */     
/*  27 */     if (file == null) {
/*  28 */       throw new ZipException("cannot set file properties: output file is null");
/*     */     }
/*     */     
/*  31 */     if (!Zip4jUtil.checkFileExists(file)) {
/*  32 */       throw new ZipException("cannot set file properties: file doesnot exist");
/*     */     }
/*     */     
/*  35 */     if (unzipParameters == null || !unzipParameters.isIgnoreDateTimeAttributes()) {
/*  36 */       setFileLastModifiedTime(fileHeader, file);
/*     */     }
/*     */     
/*  39 */     if (unzipParameters == null) {
/*  40 */       setFileAttributes(fileHeader, file, true, true, true, true);
/*     */     }
/*  42 */     else if (unzipParameters.isIgnoreAllFileAttributes()) {
/*  43 */       setFileAttributes(fileHeader, file, false, false, false, false);
/*     */     } else {
/*  45 */       setFileAttributes(fileHeader, file, !unzipParameters.isIgnoreReadOnlyFileAttribute(), !unzipParameters.isIgnoreHiddenFileAttribute(), !unzipParameters.isIgnoreArchiveFileAttribute(), !unzipParameters.isIgnoreSystemFileAttribute());
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static void setFileAttributes(FileHeader fileHeader, File file, boolean setReadOnly, boolean setHidden, boolean setArchive, boolean setSystem) throws ZipException {
/*  55 */     if (fileHeader == null) {
/*  56 */       throw new ZipException("invalid file header. cannot set file attributes");
/*     */     }
/*     */     
/*  59 */     byte[] externalAttrbs = fileHeader.getExternalFileAttr();
/*  60 */     if (externalAttrbs == null) {
/*     */       return;
/*     */     }
/*     */     
/*  64 */     int atrrib = externalAttrbs[0];
/*  65 */     switch (atrrib) {
/*     */       case 1:
/*  67 */         if (setReadOnly) Zip4jUtil.setFileReadOnly(file); 
/*     */         break;
/*     */       case 2:
/*     */       case 18:
/*  71 */         if (setHidden) Zip4jUtil.setFileHidden(file); 
/*     */         break;
/*     */       case 32:
/*     */       case 48:
/*  75 */         if (setArchive) Zip4jUtil.setFileArchive(file); 
/*     */         break;
/*     */       case 3:
/*  78 */         if (setReadOnly) Zip4jUtil.setFileReadOnly(file); 
/*  79 */         if (setHidden) Zip4jUtil.setFileHidden(file); 
/*     */         break;
/*     */       case 33:
/*  82 */         if (setArchive) Zip4jUtil.setFileArchive(file); 
/*  83 */         if (setReadOnly) Zip4jUtil.setFileReadOnly(file); 
/*     */         break;
/*     */       case 34:
/*     */       case 50:
/*  87 */         if (setArchive) Zip4jUtil.setFileArchive(file); 
/*  88 */         if (setHidden) Zip4jUtil.setFileHidden(file); 
/*     */         break;
/*     */       case 35:
/*  91 */         if (setArchive) Zip4jUtil.setFileArchive(file); 
/*  92 */         if (setReadOnly) Zip4jUtil.setFileReadOnly(file); 
/*  93 */         if (setHidden) Zip4jUtil.setFileHidden(file); 
/*     */         break;
/*     */       case 38:
/*  96 */         if (setReadOnly) Zip4jUtil.setFileReadOnly(file); 
/*  97 */         if (setHidden) Zip4jUtil.setFileHidden(file); 
/*  98 */         if (setSystem) Zip4jUtil.setFileSystemMode(file);
/*     */         
/*     */         break;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private static void setFileLastModifiedTime(FileHeader fileHeader, File file) throws ZipException {
/* 107 */     if (fileHeader.getLastModFileTime() <= 0) {
/*     */       return;
/*     */     }
/*     */     
/* 111 */     if (file.exists())
/* 112 */       file.setLastModified(Zip4jUtil.getLocalTimeFromDSTTime(defaultTimeZone, Zip4jUtil.dosToJavaTme(fileHeader.getLastModFileTime()))); 
/*     */   }
/*     */ }


/* Location:              C:\Users\DuduM\OneDrive\Área de Trabalho\mw10\modularwarfare-shining-2023.2.4.4f-fix10.jar!\net\lingala\zip4\\unzip\UnzipUtil.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */