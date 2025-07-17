/*     */ package net.lingala.zip4j.zip;
/*     */ 
/*     */ import java.io.File;
/*     */ import java.io.FileInputStream;
/*     */ import java.io.FileNotFoundException;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.io.OutputStream;
/*     */ import java.io.RandomAccessFile;
/*     */ import java.util.ArrayList;
/*     */ import java.util.HashMap;
/*     */ import net.lingala.zip4j.exception.ZipException;
/*     */ import net.lingala.zip4j.io.SplitOutputStream;
/*     */ import net.lingala.zip4j.io.ZipOutputStream;
/*     */ import net.lingala.zip4j.model.EndCentralDirRecord;
/*     */ import net.lingala.zip4j.model.FileHeader;
/*     */ import net.lingala.zip4j.model.ZipModel;
/*     */ import net.lingala.zip4j.model.ZipParameters;
/*     */ import net.lingala.zip4j.progress.ProgressMonitor;
/*     */ import net.lingala.zip4j.util.ArchiveMaintainer;
/*     */ import net.lingala.zip4j.util.CRCUtil;
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
/*     */ 
/*     */ public class ZipEngine
/*     */ {
/*     */   private ZipModel zipModel;
/*     */   
/*     */   public ZipEngine(ZipModel zipModel) throws ZipException {
/*  48 */     if (zipModel == null) {
/*  49 */       throw new ZipException("zip model is null in ZipEngine constructor");
/*     */     }
/*     */     
/*  52 */     this.zipModel = zipModel;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void addFiles(final ArrayList fileList, final ZipParameters parameters, final ProgressMonitor progressMonitor, boolean runInThread) throws ZipException {
/*  58 */     if (fileList == null || parameters == null) {
/*  59 */       throw new ZipException("one of the input parameters is null when adding files");
/*     */     }
/*     */     
/*  62 */     if (fileList.size() <= 0) {
/*  63 */       throw new ZipException("no files to add");
/*     */     }
/*     */     
/*  66 */     progressMonitor.setTotalWork(calculateTotalWork(fileList, parameters));
/*  67 */     progressMonitor.setCurrentOperation(0);
/*  68 */     progressMonitor.setState(1);
/*  69 */     progressMonitor.setResult(1);
/*     */     
/*  71 */     if (runInThread) {
/*     */       
/*  73 */       Thread thread = new Thread("Zip4j") {
/*     */           public void run() {
/*     */             try {
/*  76 */               ZipEngine.this.initAddFiles(fileList, parameters, progressMonitor);
/*  77 */             } catch (ZipException e) {}
/*     */           }
/*     */         };
/*     */       
/*  81 */       thread.start();
/*     */     } else {
/*     */       
/*  84 */       initAddFiles(fileList, parameters, progressMonitor);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private void initAddFiles(ArrayList<File> fileList, ZipParameters parameters, ProgressMonitor progressMonitor) throws ZipException {
/*  91 */     if (fileList == null || parameters == null) {
/*  92 */       throw new ZipException("one of the input parameters is null when adding files");
/*     */     }
/*     */     
/*  95 */     if (fileList.size() <= 0) {
/*  96 */       throw new ZipException("no files to add");
/*     */     }
/*     */     
/*  99 */     if (this.zipModel.getEndCentralDirRecord() == null) {
/* 100 */       this.zipModel.setEndCentralDirRecord(createEndOfCentralDirectoryRecord());
/*     */     }
/*     */     
/* 103 */     ZipOutputStream outputStream = null;
/* 104 */     InputStream inputStream = null;
/*     */     try {
/* 106 */       checkParameters(parameters);
/*     */       
/* 108 */       removeFilesIfExists(fileList, parameters, progressMonitor);
/*     */       
/* 110 */       boolean isZipFileAlreadExists = Zip4jUtil.checkFileExists(this.zipModel.getZipFile());
/*     */       
/* 112 */       SplitOutputStream splitOutputStream = new SplitOutputStream(new File(this.zipModel.getZipFile()), this.zipModel.getSplitLength());
/* 113 */       outputStream = new ZipOutputStream((OutputStream)splitOutputStream, this.zipModel);
/*     */       
/* 115 */       if (isZipFileAlreadExists) {
/* 116 */         if (this.zipModel.getEndCentralDirRecord() == null) {
/* 117 */           throw new ZipException("invalid end of central directory record");
/*     */         }
/* 119 */         splitOutputStream.seek(this.zipModel.getEndCentralDirRecord().getOffsetOfStartOfCentralDir());
/*     */       } 
/* 121 */       byte[] readBuff = new byte[4096];
/* 122 */       int readLen = -1;
/* 123 */       for (int i = 0; i < fileList.size(); i++) {
/* 124 */         if (parameters.isEncryptFiles() && parameters.getEncryptionMethod() == 0) {
/* 125 */           progressMonitor.setCurrentOperation(3);
/* 126 */           parameters.setSourceFileCRC((int)CRCUtil.computeFileCRC(((File)fileList.get(i)).getAbsolutePath(), progressMonitor));
/* 127 */           progressMonitor.setCurrentOperation(0);
/*     */         } 
/*     */         
/* 130 */         outputStream.putNextEntry(fileList.get(i), parameters);
/* 131 */         if (((File)fileList.get(i)).isDirectory()) {
/* 132 */           outputStream.closeEntry();
/*     */         }
/*     */         else {
/*     */           
/* 136 */           inputStream = new FileInputStream(fileList.get(i));
/*     */           
/* 138 */           while ((readLen = inputStream.read(readBuff)) != -1) {
/* 139 */             outputStream.write(readBuff, 0, readLen);
/* 140 */             progressMonitor.updateWorkCompleted(readLen);
/*     */           } 
/*     */           
/* 143 */           outputStream.closeEntry();
/*     */           
/* 145 */           if (inputStream != null) {
/* 146 */             inputStream.close();
/*     */           }
/*     */         } 
/*     */       } 
/* 150 */       outputStream.finish();
/* 151 */       progressMonitor.endProgressMonitorSuccess();
/* 152 */     } catch (ZipException e) {
/* 153 */       progressMonitor.endProgressMonitorError((Throwable)e);
/* 154 */       throw e;
/* 155 */     } catch (Exception e) {
/* 156 */       progressMonitor.endProgressMonitorError(e);
/* 157 */       throw new ZipException(e);
/*     */     } finally {
/* 159 */       if (inputStream != null) {
/*     */         try {
/* 161 */           inputStream.close();
/* 162 */         } catch (IOException e) {}
/*     */       }
/*     */ 
/*     */       
/* 166 */       if (outputStream != null) {
/*     */         try {
/* 168 */           outputStream.close();
/* 169 */         } catch (IOException e) {}
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void addStreamToZip(InputStream inputStream, ZipParameters parameters) throws ZipException {
/* 176 */     if (inputStream == null || parameters == null) {
/* 177 */       throw new ZipException("one of the input parameters is null, cannot add stream to zip");
/*     */     }
/*     */     
/* 180 */     ZipOutputStream outputStream = null;
/*     */     
/*     */     try {
/* 183 */       checkParameters(parameters);
/*     */       
/* 185 */       boolean isZipFileAlreadExists = Zip4jUtil.checkFileExists(this.zipModel.getZipFile());
/*     */       
/* 187 */       SplitOutputStream splitOutputStream = new SplitOutputStream(new File(this.zipModel.getZipFile()), this.zipModel.getSplitLength());
/* 188 */       outputStream = new ZipOutputStream((OutputStream)splitOutputStream, this.zipModel);
/*     */       
/* 190 */       if (isZipFileAlreadExists) {
/* 191 */         if (this.zipModel.getEndCentralDirRecord() == null) {
/* 192 */           throw new ZipException("invalid end of central directory record");
/*     */         }
/* 194 */         splitOutputStream.seek(this.zipModel.getEndCentralDirRecord().getOffsetOfStartOfCentralDir());
/*     */       } 
/*     */       
/* 197 */       byte[] readBuff = new byte[4096];
/* 198 */       int readLen = -1;
/*     */       
/* 200 */       outputStream.putNextEntry(null, parameters);
/*     */       
/* 202 */       if (!parameters.getFileNameInZip().endsWith("/") && !parameters.getFileNameInZip().endsWith("\\"))
/*     */       {
/* 204 */         while ((readLen = inputStream.read(readBuff)) != -1) {
/* 205 */           outputStream.write(readBuff, 0, readLen);
/*     */         }
/*     */       }
/*     */       
/* 209 */       outputStream.closeEntry();
/* 210 */       outputStream.finish();
/*     */     }
/* 212 */     catch (ZipException e) {
/* 213 */       throw e;
/* 214 */     } catch (Exception e) {
/* 215 */       throw new ZipException(e);
/*     */     } finally {
/* 217 */       if (outputStream != null) {
/*     */         try {
/* 219 */           outputStream.close();
/* 220 */         } catch (IOException e) {}
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void addFolderToZip(File file, ZipParameters parameters, ProgressMonitor progressMonitor, boolean runInThread) throws ZipException {
/* 229 */     if (file == null || parameters == null) {
/* 230 */       throw new ZipException("one of the input parameters is null, cannot add folder to zip");
/*     */     }
/*     */     
/* 233 */     if (!Zip4jUtil.checkFileExists(file.getAbsolutePath())) {
/* 234 */       throw new ZipException("input folder does not exist");
/*     */     }
/*     */     
/* 237 */     if (!file.isDirectory()) {
/* 238 */       throw new ZipException("input file is not a folder, user addFileToZip method to add files");
/*     */     }
/*     */     
/* 241 */     if (!Zip4jUtil.checkFileReadAccess(file.getAbsolutePath())) {
/* 242 */       throw new ZipException("cannot read folder: " + file.getAbsolutePath());
/*     */     }
/*     */     
/* 245 */     String rootFolderPath = null;
/* 246 */     if (parameters.isIncludeRootFolder()) {
/* 247 */       rootFolderPath = (file.getParentFile() != null) ? file.getParentFile().getAbsolutePath() : "";
/*     */     } else {
/* 249 */       rootFolderPath = file.getAbsolutePath();
/*     */     } 
/*     */     
/* 252 */     parameters.setDefaultFolderPath(rootFolderPath);
/*     */     
/* 254 */     addFiles(Zip4jUtil.getFilesInDirectoryRec(file, parameters.isReadHiddenFiles()), parameters, progressMonitor, runInThread);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void checkParameters(ZipParameters parameters) throws ZipException {
/* 261 */     if (parameters == null) {
/* 262 */       throw new ZipException("cannot validate zip parameters");
/*     */     }
/*     */     
/* 265 */     if (parameters.getCompressionMethod() != 0 && parameters.getCompressionMethod() != 8)
/*     */     {
/* 267 */       throw new ZipException("unsupported compression type");
/*     */     }
/*     */     
/* 270 */     if (parameters.getCompressionMethod() == 8 && 
/* 271 */       parameters.getCompressionLevel() < 0 && parameters.getCompressionLevel() > 9) {
/* 272 */       throw new ZipException("invalid compression level. compression level dor deflate should be in the range of 0-9");
/*     */     }
/*     */ 
/*     */     
/* 276 */     if (parameters.isEncryptFiles()) {
/* 277 */       if (parameters.getEncryptionMethod() != 0 && parameters.getEncryptionMethod() != 99)
/*     */       {
/* 279 */         throw new ZipException("unsupported encryption method");
/*     */       }
/*     */       
/* 282 */       String password = parameters.getPassword();
/* 283 */       if (!Zip4jUtil.isStringNotNullAndNotEmpty(password)) {
/* 284 */         throw new ZipException("input password is empty or null");
/*     */       }
/*     */     } else {
/* 287 */       parameters.setAesKeyStrength(-1);
/* 288 */       parameters.setEncryptionMethod(-1);
/*     */     } 
/*     */   }
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
/*     */   private void removeFilesIfExists(ArrayList<File> fileList, ZipParameters parameters, ProgressMonitor progressMonitor) throws ZipException {
/* 306 */     if (this.zipModel == null || this.zipModel.getCentralDirectory() == null || this.zipModel.getCentralDirectory().getFileHeaders() == null || this.zipModel.getCentralDirectory().getFileHeaders().size() <= 0) {
/*     */       return;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/* 312 */     RandomAccessFile outputStream = null;
/*     */     
/*     */     try {
/* 315 */       for (int i = 0; i < fileList.size(); i++) {
/* 316 */         File file = fileList.get(i);
/*     */         
/* 318 */         String fileName = Zip4jUtil.getRelativeFileName(file.getAbsolutePath(), parameters.getRootFolderInZip(), parameters.getDefaultFolderPath());
/*     */ 
/*     */         
/* 321 */         FileHeader fileHeader = Zip4jUtil.getFileHeader(this.zipModel, fileName);
/* 322 */         if (fileHeader != null) {
/*     */           
/* 324 */           if (outputStream != null) {
/* 325 */             outputStream.close();
/* 326 */             outputStream = null;
/*     */           } 
/*     */           
/* 329 */           ArchiveMaintainer archiveMaintainer = new ArchiveMaintainer();
/* 330 */           progressMonitor.setCurrentOperation(2);
/* 331 */           HashMap retMap = archiveMaintainer.initRemoveZipFile(this.zipModel, fileHeader, progressMonitor);
/*     */ 
/*     */           
/* 334 */           if (progressMonitor.isCancelAllTasks()) {
/* 335 */             progressMonitor.setResult(3);
/* 336 */             progressMonitor.setState(0);
/*     */             
/*     */             return;
/*     */           } 
/* 340 */           progressMonitor.setCurrentOperation(0);
/*     */ 
/*     */           
/* 343 */           if (outputStream == null) {
/* 344 */             outputStream = prepareFileOutputStream();
/*     */             
/* 346 */             if (retMap != null && 
/* 347 */               retMap.get("offsetCentralDir") != null) {
/* 348 */               long offsetCentralDir = -1L;
/*     */               try {
/* 350 */                 offsetCentralDir = Long.parseLong((String)retMap.get("offsetCentralDir"));
/*     */               
/*     */               }
/* 353 */               catch (NumberFormatException e) {
/* 354 */                 throw new ZipException("NumberFormatException while parsing offset central directory. Cannot update already existing file header");
/*     */               
/*     */               }
/* 357 */               catch (Exception e) {
/* 358 */                 throw new ZipException("Error while parsing offset central directory. Cannot update already existing file header");
/*     */               } 
/*     */ 
/*     */ 
/*     */               
/* 363 */               if (offsetCentralDir >= 0L) {
/* 364 */                 outputStream.seek(offsetCentralDir);
/*     */               }
/*     */             }
/*     */           
/*     */           } 
/*     */         } 
/*     */       } 
/* 371 */     } catch (IOException e) {
/* 372 */       throw new ZipException(e);
/*     */     } finally {
/* 374 */       if (outputStream != null) {
/*     */         try {
/* 376 */           outputStream.close();
/* 377 */         } catch (IOException e) {}
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private RandomAccessFile prepareFileOutputStream() throws ZipException {
/* 385 */     String outPath = this.zipModel.getZipFile();
/* 386 */     if (!Zip4jUtil.isStringNotNullAndNotEmpty(outPath)) {
/* 387 */       throw new ZipException("invalid output path");
/*     */     }
/*     */     
/*     */     try {
/* 391 */       File outFile = new File(outPath);
/* 392 */       if (!outFile.getParentFile().exists()) {
/* 393 */         outFile.getParentFile().mkdirs();
/*     */       }
/* 395 */       return new RandomAccessFile(outFile, "rw");
/* 396 */     } catch (FileNotFoundException e) {
/* 397 */       throw new ZipException(e);
/*     */     } 
/*     */   }
/*     */   
/*     */   private EndCentralDirRecord createEndOfCentralDirectoryRecord() {
/* 402 */     EndCentralDirRecord endCentralDirRecord = new EndCentralDirRecord();
/* 403 */     endCentralDirRecord.setSignature(101010256L);
/* 404 */     endCentralDirRecord.setNoOfThisDisk(0);
/* 405 */     endCentralDirRecord.setTotNoOfEntriesInCentralDir(0);
/* 406 */     endCentralDirRecord.setTotNoOfEntriesInCentralDirOnThisDisk(0);
/* 407 */     endCentralDirRecord.setOffsetOfStartOfCentralDir(0L);
/* 408 */     return endCentralDirRecord;
/*     */   }
/*     */   
/*     */   private long calculateTotalWork(ArrayList<File> fileList, ZipParameters parameters) throws ZipException {
/* 412 */     if (fileList == null) {
/* 413 */       throw new ZipException("file list is null, cannot calculate total work");
/*     */     }
/*     */     
/* 416 */     long totalWork = 0L;
/*     */     
/* 418 */     for (int i = 0; i < fileList.size(); i++) {
/* 419 */       if (fileList.get(i) instanceof File && (
/* 420 */         (File)fileList.get(i)).exists()) {
/* 421 */         if (parameters.isEncryptFiles() && parameters.getEncryptionMethod() == 0) {
/*     */           
/* 423 */           totalWork += Zip4jUtil.getFileLengh(fileList.get(i)) * 2L;
/*     */         } else {
/* 425 */           totalWork += Zip4jUtil.getFileLengh(fileList.get(i));
/*     */         } 
/*     */         
/* 428 */         if (this.zipModel.getCentralDirectory() != null && this.zipModel.getCentralDirectory().getFileHeaders() != null && this.zipModel.getCentralDirectory().getFileHeaders().size() > 0) {
/*     */ 
/*     */           
/* 431 */           String relativeFileName = Zip4jUtil.getRelativeFileName(((File)fileList.get(i)).getAbsolutePath(), parameters.getRootFolderInZip(), parameters.getDefaultFolderPath());
/*     */           
/* 433 */           FileHeader fileHeader = Zip4jUtil.getFileHeader(this.zipModel, relativeFileName);
/* 434 */           if (fileHeader != null) {
/* 435 */             totalWork += Zip4jUtil.getFileLengh(new File(this.zipModel.getZipFile())) - fileHeader.getCompressedSize();
/*     */           }
/*     */         } 
/*     */       } 
/*     */     } 
/*     */ 
/*     */     
/* 442 */     return totalWork;
/*     */   }
/*     */ }


/* Location:              C:\Users\DuduM\OneDrive\Área de Trabalho\mw10\modularwarfare-shining-2023.2.4.4f-fix10.jar!\net\lingala\zip4j\zip\ZipEngine.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */