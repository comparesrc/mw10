/*     */ package net.lingala.zip4j.unzip;
/*     */ 
/*     */ import java.io.File;
/*     */ import java.util.ArrayList;
/*     */ import net.lingala.zip4j.exception.ZipException;
/*     */ import net.lingala.zip4j.io.ZipInputStream;
/*     */ import net.lingala.zip4j.model.CentralDirectory;
/*     */ import net.lingala.zip4j.model.FileHeader;
/*     */ import net.lingala.zip4j.model.UnzipParameters;
/*     */ import net.lingala.zip4j.model.ZipModel;
/*     */ import net.lingala.zip4j.progress.ProgressMonitor;
/*     */ import net.lingala.zip4j.util.InternalZipConstants;
/*     */ import net.lingala.zip4j.util.Zip4jUtil;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class Unzip
/*     */ {
/*     */   private ZipModel zipModel;
/*     */   
/*     */   public Unzip(ZipModel zipModel) throws ZipException {
/*  38 */     if (zipModel == null) {
/*  39 */       throw new ZipException("ZipModel is null");
/*     */     }
/*     */     
/*  42 */     this.zipModel = zipModel;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void extractAll(final UnzipParameters unzipParameters, final String outPath, final ProgressMonitor progressMonitor, boolean runInThread) throws ZipException {
/*  48 */     CentralDirectory centralDirectory = this.zipModel.getCentralDirectory();
/*     */     
/*  50 */     if (centralDirectory == null || centralDirectory.getFileHeaders() == null)
/*     */     {
/*  52 */       throw new ZipException("invalid central directory in zipModel");
/*     */     }
/*     */     
/*  55 */     final ArrayList fileHeaders = centralDirectory.getFileHeaders();
/*     */     
/*  57 */     progressMonitor.setCurrentOperation(1);
/*  58 */     progressMonitor.setTotalWork(calculateTotalWork(fileHeaders));
/*  59 */     progressMonitor.setState(1);
/*     */     
/*  61 */     if (runInThread) {
/*  62 */       Thread thread = new Thread("Zip4j") {
/*     */           public void run() {
/*     */             try {
/*  65 */               Unzip.this.initExtractAll(fileHeaders, unzipParameters, progressMonitor, outPath);
/*  66 */               progressMonitor.endProgressMonitorSuccess();
/*  67 */             } catch (ZipException e) {}
/*     */           }
/*     */         };
/*     */       
/*  71 */       thread.start();
/*     */     } else {
/*  73 */       initExtractAll(fileHeaders, unzipParameters, progressMonitor, outPath);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void initExtractAll(ArrayList<FileHeader> fileHeaders, UnzipParameters unzipParameters, ProgressMonitor progressMonitor, String outPath) throws ZipException {
/*  81 */     for (int i = 0; i < fileHeaders.size(); i++) {
/*  82 */       FileHeader fileHeader = fileHeaders.get(i);
/*  83 */       initExtractFile(fileHeader, outPath, unzipParameters, null, progressMonitor);
/*  84 */       if (progressMonitor.isCancelAllTasks()) {
/*  85 */         progressMonitor.setResult(3);
/*  86 */         progressMonitor.setState(0);
/*     */         return;
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void extractFile(final FileHeader fileHeader, final String outPath, final UnzipParameters unzipParameters, final String newFileName, final ProgressMonitor progressMonitor, boolean runInThread) throws ZipException {
/*  95 */     if (fileHeader == null) {
/*  96 */       throw new ZipException("fileHeader is null");
/*     */     }
/*     */     
/*  99 */     progressMonitor.setTotalWork(fileHeader.getCompressedSize());
/*     */     
/* 101 */     if (runInThread) {
/* 102 */       Thread thread = new Thread("Zip4j") {
/*     */           public void run() {
/*     */             try {
/* 105 */               Unzip.this.initExtractFile(fileHeader, outPath, unzipParameters, newFileName, progressMonitor);
/* 106 */             } catch (ZipException e) {}
/*     */           }
/*     */         };
/*     */       
/* 110 */       thread.start();
/*     */     } else {
/* 112 */       initExtractFile(fileHeader, outPath, unzipParameters, newFileName, progressMonitor);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void initExtractFile(FileHeader fileHeader, String outPath, UnzipParameters unzipParameters, String newFileName, ProgressMonitor progressMonitor) throws ZipException {
/* 120 */     if (fileHeader == null) {
/* 121 */       throw new ZipException("fileHeader is null");
/*     */     }
/*     */     
/*     */     try {
/* 125 */       progressMonitor.setFileName(fileHeader.getFileName());
/*     */       
/* 127 */       if (!outPath.endsWith(InternalZipConstants.FILE_SEPARATOR)) {
/* 128 */         outPath = outPath + InternalZipConstants.FILE_SEPARATOR;
/*     */       }
/*     */ 
/*     */ 
/*     */       
/* 133 */       if (fileHeader.isDirectory()) {
/*     */         try {
/* 135 */           String fileName = fileHeader.getFileName();
/* 136 */           if (!Zip4jUtil.isStringNotNullAndNotEmpty(fileName)) {
/*     */             return;
/*     */           }
/* 139 */           String completePath = outPath + fileName;
/* 140 */           File file = new File(completePath);
/* 141 */           if (!file.exists()) {
/* 142 */             file.mkdirs();
/*     */           }
/* 144 */         } catch (Exception e) {
/* 145 */           progressMonitor.endProgressMonitorError(e);
/* 146 */           throw new ZipException(e);
/*     */         } 
/*     */       } else {
/*     */         
/* 150 */         checkOutputDirectoryStructure(fileHeader, outPath, newFileName);
/*     */         
/* 152 */         UnzipEngine unzipEngine = new UnzipEngine(this.zipModel, fileHeader);
/*     */         try {
/* 154 */           unzipEngine.unzipFile(progressMonitor, outPath, newFileName, unzipParameters);
/* 155 */         } catch (Exception e) {
/* 156 */           progressMonitor.endProgressMonitorError(e);
/* 157 */           throw new ZipException(e);
/*     */         } 
/*     */       } 
/* 160 */     } catch (ZipException e) {
/* 161 */       progressMonitor.endProgressMonitorError((Throwable)e);
/* 162 */       throw e;
/* 163 */     } catch (Exception e) {
/* 164 */       progressMonitor.endProgressMonitorError(e);
/* 165 */       throw new ZipException(e);
/*     */     } 
/*     */   }
/*     */   
/*     */   public ZipInputStream getInputStream(FileHeader fileHeader) throws ZipException {
/* 170 */     UnzipEngine unzipEngine = new UnzipEngine(this.zipModel, fileHeader);
/* 171 */     return unzipEngine.getInputStream();
/*     */   }
/*     */   
/*     */   private void checkOutputDirectoryStructure(FileHeader fileHeader, String outPath, String newFileName) throws ZipException {
/* 175 */     if (fileHeader == null || !Zip4jUtil.isStringNotNullAndNotEmpty(outPath)) {
/* 176 */       throw new ZipException("Cannot check output directory structure...one of the parameters was null");
/*     */     }
/*     */     
/* 179 */     String fileName = fileHeader.getFileName();
/*     */     
/* 181 */     if (Zip4jUtil.isStringNotNullAndNotEmpty(newFileName)) {
/* 182 */       fileName = newFileName;
/*     */     }
/*     */     
/* 185 */     if (!Zip4jUtil.isStringNotNullAndNotEmpty(fileName)) {
/*     */       return;
/*     */     }
/*     */ 
/*     */     
/* 190 */     String compOutPath = outPath + fileName;
/*     */     try {
/* 192 */       File file = new File(compOutPath);
/* 193 */       String parentDir = file.getParent();
/* 194 */       File parentDirFile = new File(parentDir);
/* 195 */       if (!parentDirFile.exists()) {
/* 196 */         parentDirFile.mkdirs();
/*     */       }
/* 198 */     } catch (Exception e) {
/* 199 */       throw new ZipException(e);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private long calculateTotalWork(ArrayList<FileHeader> fileHeaders) throws ZipException {
/* 205 */     if (fileHeaders == null) {
/* 206 */       throw new ZipException("fileHeaders is null, cannot calculate total work");
/*     */     }
/*     */     
/* 209 */     long totalWork = 0L;
/*     */     
/* 211 */     for (int i = 0; i < fileHeaders.size(); i++) {
/* 212 */       FileHeader fileHeader = fileHeaders.get(i);
/* 213 */       if (fileHeader.getZip64ExtendedInfo() != null && fileHeader.getZip64ExtendedInfo().getUnCompressedSize() > 0L) {
/*     */         
/* 215 */         totalWork += fileHeader.getZip64ExtendedInfo().getCompressedSize();
/*     */       } else {
/* 217 */         totalWork += fileHeader.getCompressedSize();
/*     */       } 
/*     */     } 
/*     */ 
/*     */     
/* 222 */     return totalWork;
/*     */   }
/*     */ }


/* Location:              C:\Users\DuduM\OneDrive\Área de Trabalho\mw10\modularwarfare-shining-2023.2.4.4f-fix10.jar!\net\lingala\zip4\\unzip\Unzip.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */