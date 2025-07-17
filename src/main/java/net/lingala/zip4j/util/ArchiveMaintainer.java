/*     */ package net.lingala.zip4j.util;
/*     */ 
/*     */ import java.io.File;
/*     */ import java.io.FileNotFoundException;
/*     */ import java.io.FileOutputStream;
/*     */ import java.io.IOException;
/*     */ import java.io.OutputStream;
/*     */ import java.io.RandomAccessFile;
/*     */ import java.util.ArrayList;
/*     */ import java.util.HashMap;
/*     */ import net.lingala.zip4j.core.HeaderReader;
/*     */ import net.lingala.zip4j.core.HeaderWriter;
/*     */ import net.lingala.zip4j.exception.ZipException;
/*     */ import net.lingala.zip4j.io.SplitOutputStream;
/*     */ import net.lingala.zip4j.model.FileHeader;
/*     */ import net.lingala.zip4j.model.LocalFileHeader;
/*     */ import net.lingala.zip4j.model.ZipModel;
/*     */ import net.lingala.zip4j.progress.ProgressMonitor;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class ArchiveMaintainer
/*     */ {
/*     */   public HashMap removeZipFile(final ZipModel zipModel, final FileHeader fileHeader, final ProgressMonitor progressMonitor, boolean runInThread) throws ZipException {
/*  47 */     if (runInThread) {
/*  48 */       Thread thread = new Thread("Zip4j") {
/*     */           public void run() {
/*     */             try {
/*  51 */               ArchiveMaintainer.this.initRemoveZipFile(zipModel, fileHeader, progressMonitor);
/*  52 */               progressMonitor.endProgressMonitorSuccess();
/*  53 */             } catch (ZipException e) {}
/*     */           }
/*     */         };
/*     */       
/*  57 */       thread.start();
/*  58 */       return null;
/*     */     } 
/*  60 */     HashMap retMap = initRemoveZipFile(zipModel, fileHeader, progressMonitor);
/*  61 */     progressMonitor.endProgressMonitorSuccess();
/*  62 */     return retMap;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public HashMap initRemoveZipFile(ZipModel zipModel, FileHeader fileHeader, ProgressMonitor progressMonitor) throws ZipException {
/*     */     SplitOutputStream splitOutputStream;
/*  70 */     if (fileHeader == null || zipModel == null) {
/*  71 */       throw new ZipException("input parameters is null in maintain zip file, cannot remove file from archive");
/*     */     }
/*     */     
/*  74 */     OutputStream outputStream = null;
/*  75 */     File zipFile = null;
/*  76 */     RandomAccessFile inputStream = null;
/*  77 */     boolean successFlag = false;
/*  78 */     String tmpZipFileName = null;
/*  79 */     HashMap<Object, Object> retMap = new HashMap<Object, Object>();
/*     */     
/*     */     try {
/*  82 */       int indexOfFileHeader = Zip4jUtil.getIndexOfFileHeader(zipModel, fileHeader);
/*     */       
/*  84 */       if (indexOfFileHeader < 0) {
/*  85 */         throw new ZipException("file header not found in zip model, cannot remove file");
/*     */       }
/*     */       
/*  88 */       if (zipModel.isSplitArchive()) {
/*  89 */         throw new ZipException("This is a split archive. Zip file format does not allow updating split/spanned files");
/*     */       }
/*     */       
/*  92 */       long currTime = System.currentTimeMillis();
/*  93 */       tmpZipFileName = zipModel.getZipFile() + (currTime % 1000L);
/*  94 */       File tmpFile = new File(tmpZipFileName);
/*     */       
/*  96 */       while (tmpFile.exists()) {
/*  97 */         currTime = System.currentTimeMillis();
/*  98 */         tmpZipFileName = zipModel.getZipFile() + (currTime % 1000L);
/*  99 */         tmpFile = new File(tmpZipFileName);
/*     */       } 
/*     */       
/*     */       try {
/* 103 */         splitOutputStream = new SplitOutputStream(new File(tmpZipFileName));
/* 104 */       } catch (FileNotFoundException e1) {
/* 105 */         throw new ZipException(e1);
/*     */       } 
/*     */       
/* 108 */       zipFile = new File(zipModel.getZipFile());
/*     */       
/* 110 */       inputStream = createFileHandler(zipModel, "r");
/*     */       
/* 112 */       HeaderReader headerReader = new HeaderReader(inputStream);
/* 113 */       LocalFileHeader localFileHeader = headerReader.readLocalFileHeader(fileHeader);
/* 114 */       if (localFileHeader == null) {
/* 115 */         throw new ZipException("invalid local file header, cannot remove file from archive");
/*     */       }
/*     */       
/* 118 */       long offsetLocalFileHeader = fileHeader.getOffsetLocalHeader();
/*     */       
/* 120 */       if (fileHeader.getZip64ExtendedInfo() != null && fileHeader.getZip64ExtendedInfo().getOffsetLocalHeader() != -1L)
/*     */       {
/* 122 */         offsetLocalFileHeader = fileHeader.getZip64ExtendedInfo().getOffsetLocalHeader();
/*     */       }
/*     */       
/* 125 */       long offsetEndOfCompressedFile = -1L;
/*     */       
/* 127 */       long offsetStartCentralDir = zipModel.getEndCentralDirRecord().getOffsetOfStartOfCentralDir();
/* 128 */       if (zipModel.isZip64Format() && 
/* 129 */         zipModel.getZip64EndCentralDirRecord() != null) {
/* 130 */         offsetStartCentralDir = zipModel.getZip64EndCentralDirRecord().getOffsetStartCenDirWRTStartDiskNo();
/*     */       }
/*     */ 
/*     */       
/* 134 */       ArrayList<FileHeader> fileHeaderList = zipModel.getCentralDirectory().getFileHeaders();
/*     */       
/* 136 */       if (indexOfFileHeader == fileHeaderList.size() - 1) {
/* 137 */         offsetEndOfCompressedFile = offsetStartCentralDir - 1L;
/*     */       } else {
/* 139 */         FileHeader nextFileHeader = fileHeaderList.get(indexOfFileHeader + 1);
/* 140 */         if (nextFileHeader != null) {
/* 141 */           offsetEndOfCompressedFile = nextFileHeader.getOffsetLocalHeader() - 1L;
/* 142 */           if (nextFileHeader.getZip64ExtendedInfo() != null && nextFileHeader.getZip64ExtendedInfo().getOffsetLocalHeader() != -1L)
/*     */           {
/* 144 */             offsetEndOfCompressedFile = nextFileHeader.getZip64ExtendedInfo().getOffsetLocalHeader() - 1L;
/*     */           }
/*     */         } 
/*     */       } 
/*     */       
/* 149 */       if (offsetLocalFileHeader < 0L || offsetEndOfCompressedFile < 0L) {
/* 150 */         throw new ZipException("invalid offset for start and end of local file, cannot remove file");
/*     */       }
/*     */       
/* 153 */       if (indexOfFileHeader == 0) {
/* 154 */         if (zipModel.getCentralDirectory().getFileHeaders().size() > 1)
/*     */         {
/* 156 */           copyFile(inputStream, (OutputStream)splitOutputStream, offsetEndOfCompressedFile + 1L, offsetStartCentralDir, progressMonitor);
/*     */         }
/* 158 */       } else if (indexOfFileHeader == fileHeaderList.size() - 1) {
/* 159 */         copyFile(inputStream, (OutputStream)splitOutputStream, 0L, offsetLocalFileHeader, progressMonitor);
/*     */       } else {
/* 161 */         copyFile(inputStream, (OutputStream)splitOutputStream, 0L, offsetLocalFileHeader, progressMonitor);
/* 162 */         copyFile(inputStream, (OutputStream)splitOutputStream, offsetEndOfCompressedFile + 1L, offsetStartCentralDir, progressMonitor);
/*     */       } 
/*     */       
/* 165 */       if (progressMonitor.isCancelAllTasks()) {
/* 166 */         progressMonitor.setResult(3);
/* 167 */         progressMonitor.setState(0);
/* 168 */         return null;
/*     */       } 
/*     */       
/* 171 */       zipModel.getEndCentralDirRecord().setOffsetOfStartOfCentralDir(splitOutputStream.getFilePointer());
/* 172 */       zipModel.getEndCentralDirRecord().setTotNoOfEntriesInCentralDir(zipModel.getEndCentralDirRecord().getTotNoOfEntriesInCentralDir() - 1);
/*     */       
/* 174 */       zipModel.getEndCentralDirRecord().setTotNoOfEntriesInCentralDirOnThisDisk(zipModel.getEndCentralDirRecord().getTotNoOfEntriesInCentralDirOnThisDisk() - 1);
/*     */ 
/*     */       
/* 177 */       zipModel.getCentralDirectory().getFileHeaders().remove(indexOfFileHeader);
/*     */       
/* 179 */       for (int i = indexOfFileHeader; i < zipModel.getCentralDirectory().getFileHeaders().size(); i++) {
/* 180 */         long offsetLocalHdr = ((FileHeader)zipModel.getCentralDirectory().getFileHeaders().get(i)).getOffsetLocalHeader();
/* 181 */         if (((FileHeader)zipModel.getCentralDirectory().getFileHeaders().get(i)).getZip64ExtendedInfo() != null && ((FileHeader)zipModel.getCentralDirectory().getFileHeaders().get(i)).getZip64ExtendedInfo().getOffsetLocalHeader() != -1L)
/*     */         {
/* 183 */           offsetLocalHdr = ((FileHeader)zipModel.getCentralDirectory().getFileHeaders().get(i)).getZip64ExtendedInfo().getOffsetLocalHeader();
/*     */         }
/*     */         
/* 186 */         ((FileHeader)zipModel.getCentralDirectory().getFileHeaders().get(i)).setOffsetLocalHeader(offsetLocalHdr - offsetEndOfCompressedFile - offsetLocalFileHeader - 1L);
/*     */       } 
/*     */ 
/*     */       
/* 190 */       HeaderWriter headerWriter = new HeaderWriter();
/* 191 */       headerWriter.finalizeZipFile(zipModel, (OutputStream)splitOutputStream);
/*     */       
/* 193 */       successFlag = true;
/*     */       
/* 195 */       retMap.put("offsetCentralDir", Long.toString(zipModel.getEndCentralDirRecord().getOffsetOfStartOfCentralDir()));
/*     */     
/*     */     }
/* 198 */     catch (ZipException e) {
/* 199 */       progressMonitor.endProgressMonitorError((Throwable)e);
/* 200 */       throw e;
/* 201 */     } catch (Exception e) {
/* 202 */       progressMonitor.endProgressMonitorError(e);
/* 203 */       throw new ZipException(e);
/*     */     } finally {
/*     */       try {
/* 206 */         if (inputStream != null)
/* 207 */           inputStream.close(); 
/* 208 */         if (splitOutputStream != null)
/* 209 */           splitOutputStream.close(); 
/* 210 */       } catch (IOException e) {
/* 211 */         throw new ZipException("cannot close input stream or output stream when trying to delete a file from zip file");
/*     */       } 
/*     */       
/* 214 */       if (successFlag) {
/* 215 */         restoreFileName(zipFile, tmpZipFileName);
/*     */       } else {
/* 217 */         File newZipFile = new File(tmpZipFileName);
/* 218 */         newZipFile.delete();
/*     */       } 
/*     */     } 
/*     */     
/* 222 */     return retMap;
/*     */   }
/*     */   
/*     */   private void restoreFileName(File zipFile, String tmpZipFileName) throws ZipException {
/* 226 */     if (zipFile.delete()) {
/*     */       
/* 228 */       File newZipFile = new File(tmpZipFileName);
/* 229 */       if (!newZipFile.renameTo(zipFile)) {
/* 230 */         throw new ZipException("cannot rename modified zip file");
/*     */       }
/*     */     } else {
/* 233 */       throw new ZipException("cannot delete old zip file");
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private void copyFile(RandomAccessFile inputStream, OutputStream outputStream, long start, long end, ProgressMonitor progressMonitor) throws ZipException {
/* 240 */     if (inputStream == null || outputStream == null) {
/* 241 */       throw new ZipException("input or output stream is null, cannot copy file");
/*     */     }
/*     */     
/* 244 */     if (start < 0L) {
/* 245 */       throw new ZipException("starting offset is negative, cannot copy file");
/*     */     }
/*     */     
/* 248 */     if (end < 0L) {
/* 249 */       throw new ZipException("end offset is negative, cannot copy file");
/*     */     }
/*     */     
/* 252 */     if (start > end) {
/* 253 */       throw new ZipException("start offset is greater than end offset, cannot copy file");
/*     */     }
/*     */     
/* 256 */     if (start == end) {
/*     */       return;
/*     */     }
/*     */     
/* 260 */     if (progressMonitor.isCancelAllTasks()) {
/* 261 */       progressMonitor.setResult(3);
/* 262 */       progressMonitor.setState(0);
/*     */       return;
/*     */     } 
/*     */     try {
/*     */       byte[] buff;
/* 267 */       inputStream.seek(start);
/*     */       
/* 269 */       int readLen = -2;
/*     */       
/* 271 */       long bytesRead = 0L;
/* 272 */       long bytesToRead = end - start;
/*     */       
/* 274 */       if (end - start < 4096L) {
/* 275 */         buff = new byte[(int)(end - start)];
/*     */       } else {
/* 277 */         buff = new byte[4096];
/*     */       } 
/*     */       
/* 280 */       while ((readLen = inputStream.read(buff)) != -1) {
/* 281 */         outputStream.write(buff, 0, readLen);
/*     */         
/* 283 */         progressMonitor.updateWorkCompleted(readLen);
/* 284 */         if (progressMonitor.isCancelAllTasks()) {
/* 285 */           progressMonitor.setResult(3);
/*     */           
/*     */           return;
/*     */         } 
/* 289 */         bytesRead += readLen;
/*     */         
/* 291 */         if (bytesRead == bytesToRead)
/*     */           break; 
/* 293 */         if (bytesRead + buff.length > bytesToRead) {
/* 294 */           buff = new byte[(int)(bytesToRead - bytesRead)];
/*     */         }
/*     */       }
/*     */     
/* 298 */     } catch (IOException e) {
/* 299 */       throw new ZipException(e);
/* 300 */     } catch (Exception e) {
/* 301 */       throw new ZipException(e);
/*     */     } 
/*     */   }
/*     */   
/*     */   private RandomAccessFile createFileHandler(ZipModel zipModel, String mode) throws ZipException {
/* 306 */     if (zipModel == null || !Zip4jUtil.isStringNotNullAndNotEmpty(zipModel.getZipFile())) {
/* 307 */       throw new ZipException("input parameter is null in getFilePointer, cannot create file handler to remove file");
/*     */     }
/*     */     
/*     */     try {
/* 311 */       return new RandomAccessFile(new File(zipModel.getZipFile()), mode);
/* 312 */     } catch (FileNotFoundException e) {
/* 313 */       throw new ZipException(e);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void mergeSplitZipFiles(final ZipModel zipModel, final File outputZipFile, final ProgressMonitor progressMonitor, boolean runInThread) throws ZipException {
/* 324 */     if (runInThread) {
/* 325 */       Thread thread = new Thread("Zip4j") {
/*     */           public void run() {
/*     */             try {
/* 328 */               ArchiveMaintainer.this.initMergeSplitZipFile(zipModel, outputZipFile, progressMonitor);
/* 329 */             } catch (ZipException e) {}
/*     */           }
/*     */         };
/*     */       
/* 333 */       thread.start();
/*     */     } else {
/* 335 */       initMergeSplitZipFile(zipModel, outputZipFile, progressMonitor);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private void initMergeSplitZipFile(ZipModel zipModel, File outputZipFile, ProgressMonitor progressMonitor) throws ZipException {
/* 341 */     if (zipModel == null) {
/* 342 */       ZipException e = new ZipException("one of the input parameters is null, cannot merge split zip file");
/* 343 */       progressMonitor.endProgressMonitorError((Throwable)e);
/* 344 */       throw e;
/*     */     } 
/*     */     
/* 347 */     if (!zipModel.isSplitArchive()) {
/* 348 */       ZipException e = new ZipException("archive not a split zip file");
/* 349 */       progressMonitor.endProgressMonitorError((Throwable)e);
/* 350 */       throw e;
/*     */     } 
/*     */     
/* 353 */     OutputStream outputStream = null;
/* 354 */     RandomAccessFile inputStream = null;
/* 355 */     ArrayList<Long> fileSizeList = new ArrayList();
/* 356 */     long totBytesWritten = 0L;
/* 357 */     boolean splitSigRemoved = false;
/*     */     
/*     */     try {
/* 360 */       int totNoOfSplitFiles = zipModel.getEndCentralDirRecord().getNoOfThisDisk();
/*     */       
/* 362 */       if (totNoOfSplitFiles <= 0) {
/* 363 */         throw new ZipException("corrupt zip model, archive not a split zip file");
/*     */       }
/*     */       
/* 366 */       outputStream = prepareOutputStreamForMerge(outputZipFile);
/* 367 */       for (int i = 0; i <= totNoOfSplitFiles; i++) {
/* 368 */         inputStream = createSplitZipFileHandler(zipModel, i);
/*     */         
/* 370 */         int start = 0;
/* 371 */         Long end = new Long(inputStream.length());
/*     */         
/* 373 */         if (i == 0 && 
/* 374 */           zipModel.getCentralDirectory() != null && zipModel.getCentralDirectory().getFileHeaders() != null && zipModel.getCentralDirectory().getFileHeaders().size() > 0) {
/*     */ 
/*     */           
/* 377 */           byte[] buff = new byte[4];
/* 378 */           inputStream.seek(0L);
/* 379 */           inputStream.read(buff);
/* 380 */           if (Raw.readIntLittleEndian(buff, 0) == 134695760L) {
/* 381 */             start = 4;
/* 382 */             splitSigRemoved = true;
/*     */           } 
/*     */         } 
/*     */ 
/*     */         
/* 387 */         if (i == totNoOfSplitFiles) {
/* 388 */           end = new Long(zipModel.getEndCentralDirRecord().getOffsetOfStartOfCentralDir());
/*     */         }
/*     */         
/* 391 */         copyFile(inputStream, outputStream, start, end.longValue(), progressMonitor);
/* 392 */         totBytesWritten += end.longValue() - start;
/* 393 */         if (progressMonitor.isCancelAllTasks()) {
/* 394 */           progressMonitor.setResult(3);
/* 395 */           progressMonitor.setState(0);
/*     */           
/*     */           return;
/*     */         } 
/* 399 */         fileSizeList.add(end);
/*     */         
/*     */         try {
/* 402 */           inputStream.close();
/* 403 */         } catch (IOException e) {}
/*     */       } 
/*     */ 
/*     */ 
/*     */       
/* 408 */       ZipModel newZipModel = (ZipModel)zipModel.clone();
/* 409 */       newZipModel.getEndCentralDirRecord().setOffsetOfStartOfCentralDir(totBytesWritten);
/*     */       
/* 411 */       updateSplitZipModel(newZipModel, fileSizeList, splitSigRemoved);
/*     */       
/* 413 */       HeaderWriter headerWriter = new HeaderWriter();
/* 414 */       headerWriter.finalizeZipFileWithoutValidations(newZipModel, outputStream);
/*     */       
/* 416 */       progressMonitor.endProgressMonitorSuccess();
/*     */     }
/* 418 */     catch (IOException e) {
/* 419 */       progressMonitor.endProgressMonitorError(e);
/* 420 */       throw new ZipException(e);
/* 421 */     } catch (Exception e) {
/* 422 */       progressMonitor.endProgressMonitorError(e);
/* 423 */       throw new ZipException(e);
/*     */     } finally {
/* 425 */       if (outputStream != null) {
/*     */         try {
/* 427 */           outputStream.close();
/* 428 */         } catch (IOException e) {}
/*     */       }
/*     */ 
/*     */ 
/*     */       
/* 433 */       if (inputStream != null) {
/*     */         try {
/* 435 */           inputStream.close();
/* 436 */         } catch (IOException e) {}
/*     */       }
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
/*     */   private RandomAccessFile createSplitZipFileHandler(ZipModel zipModel, int partNumber) throws ZipException {
/* 450 */     if (zipModel == null) {
/* 451 */       throw new ZipException("zip model is null, cannot create split file handler");
/*     */     }
/*     */     
/* 454 */     if (partNumber < 0) {
/* 455 */       throw new ZipException("invlaid part number, cannot create split file handler");
/*     */     }
/*     */     
/*     */     try {
/* 459 */       String curZipFile = zipModel.getZipFile();
/* 460 */       String partFile = null;
/* 461 */       if (partNumber == zipModel.getEndCentralDirRecord().getNoOfThisDisk()) {
/* 462 */         partFile = zipModel.getZipFile();
/*     */       }
/* 464 */       else if (partNumber >= 9) {
/* 465 */         partFile = curZipFile.substring(0, curZipFile.lastIndexOf(".")) + ".z" + (partNumber + 1);
/*     */       } else {
/* 467 */         partFile = curZipFile.substring(0, curZipFile.lastIndexOf(".")) + ".z0" + (partNumber + 1);
/*     */       } 
/*     */       
/* 470 */       File tmpFile = new File(partFile);
/*     */       
/* 472 */       if (!Zip4jUtil.checkFileExists(tmpFile)) {
/* 473 */         throw new ZipException("split file does not exist: " + partFile);
/*     */       }
/*     */       
/* 476 */       return new RandomAccessFile(tmpFile, "r");
/* 477 */     } catch (FileNotFoundException e) {
/* 478 */       throw new ZipException(e);
/* 479 */     } catch (Exception e) {
/* 480 */       throw new ZipException(e);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private OutputStream prepareOutputStreamForMerge(File outFile) throws ZipException {
/* 486 */     if (outFile == null) {
/* 487 */       throw new ZipException("outFile is null, cannot create outputstream");
/*     */     }
/*     */     
/*     */     try {
/* 491 */       return new FileOutputStream(outFile);
/* 492 */     } catch (FileNotFoundException e) {
/* 493 */       throw new ZipException(e);
/* 494 */     } catch (Exception e) {
/* 495 */       throw new ZipException(e);
/*     */     } 
/*     */   }
/*     */   
/*     */   private void updateSplitZipModel(ZipModel zipModel, ArrayList fileSizeList, boolean splitSigRemoved) throws ZipException {
/* 500 */     if (zipModel == null) {
/* 501 */       throw new ZipException("zip model is null, cannot update split zip model");
/*     */     }
/*     */     
/* 504 */     zipModel.setSplitArchive(false);
/* 505 */     updateSplitFileHeader(zipModel, fileSizeList, splitSigRemoved);
/* 506 */     updateSplitEndCentralDirectory(zipModel);
/* 507 */     if (zipModel.isZip64Format()) {
/* 508 */       updateSplitZip64EndCentralDirLocator(zipModel, fileSizeList);
/* 509 */       updateSplitZip64EndCentralDirRec(zipModel, fileSizeList);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private void updateSplitFileHeader(ZipModel zipModel, ArrayList<Long> fileSizeList, boolean splitSigRemoved) throws ZipException {
/*     */     try {
/* 516 */       if (zipModel.getCentralDirectory() == null) {
/* 517 */         throw new ZipException("corrupt zip model - getCentralDirectory, cannot update split zip model");
/*     */       }
/*     */       
/* 520 */       int fileHeaderCount = zipModel.getCentralDirectory().getFileHeaders().size();
/* 521 */       int splitSigOverhead = 0;
/* 522 */       if (splitSigRemoved) {
/* 523 */         splitSigOverhead = 4;
/*     */       }
/* 525 */       for (int i = 0; i < fileHeaderCount; i++) {
/* 526 */         long offsetLHToAdd = 0L;
/*     */         
/* 528 */         for (int j = 0; j < ((FileHeader)zipModel.getCentralDirectory().getFileHeaders().get(i)).getDiskNumberStart(); j++) {
/* 529 */           offsetLHToAdd += ((Long)fileSizeList.get(j)).longValue();
/*     */         }
/* 531 */         ((FileHeader)zipModel.getCentralDirectory().getFileHeaders().get(i)).setOffsetLocalHeader(((FileHeader)zipModel.getCentralDirectory().getFileHeaders().get(i)).getOffsetLocalHeader() + offsetLHToAdd - splitSigOverhead);
/*     */ 
/*     */         
/* 534 */         ((FileHeader)zipModel.getCentralDirectory().getFileHeaders().get(i)).setDiskNumberStart(0);
/*     */       }
/*     */     
/* 537 */     } catch (ZipException e) {
/* 538 */       throw e;
/* 539 */     } catch (Exception e) {
/* 540 */       throw new ZipException(e);
/*     */     } 
/*     */   }
/*     */   
/*     */   private void updateSplitEndCentralDirectory(ZipModel zipModel) throws ZipException {
/*     */     try {
/* 546 */       if (zipModel == null) {
/* 547 */         throw new ZipException("zip model is null - cannot update end of central directory for split zip model");
/*     */       }
/*     */       
/* 550 */       if (zipModel.getCentralDirectory() == null) {
/* 551 */         throw new ZipException("corrupt zip model - getCentralDirectory, cannot update split zip model");
/*     */       }
/*     */       
/* 554 */       zipModel.getEndCentralDirRecord().setNoOfThisDisk(0);
/* 555 */       zipModel.getEndCentralDirRecord().setNoOfThisDiskStartOfCentralDir(0);
/* 556 */       zipModel.getEndCentralDirRecord().setTotNoOfEntriesInCentralDir(zipModel.getCentralDirectory().getFileHeaders().size());
/*     */       
/* 558 */       zipModel.getEndCentralDirRecord().setTotNoOfEntriesInCentralDirOnThisDisk(zipModel.getCentralDirectory().getFileHeaders().size());
/*     */     
/*     */     }
/* 561 */     catch (ZipException e) {
/* 562 */       throw e;
/* 563 */     } catch (Exception e) {
/* 564 */       throw new ZipException(e);
/*     */     } 
/*     */   }
/*     */   
/*     */   private void updateSplitZip64EndCentralDirLocator(ZipModel zipModel, ArrayList<Long> fileSizeList) throws ZipException {
/* 569 */     if (zipModel == null) {
/* 570 */       throw new ZipException("zip model is null, cannot update split Zip64 end of central directory locator");
/*     */     }
/*     */     
/* 573 */     if (zipModel.getZip64EndCentralDirLocator() == null) {
/*     */       return;
/*     */     }
/*     */     
/* 577 */     zipModel.getZip64EndCentralDirLocator().setNoOfDiskStartOfZip64EndOfCentralDirRec(0);
/* 578 */     long offsetZip64EndCentralDirRec = 0L;
/*     */     
/* 580 */     for (int i = 0; i < fileSizeList.size(); i++) {
/* 581 */       offsetZip64EndCentralDirRec += ((Long)fileSizeList.get(i)).longValue();
/*     */     }
/* 583 */     zipModel.getZip64EndCentralDirLocator().setOffsetZip64EndOfCentralDirRec(zipModel.getZip64EndCentralDirLocator().getOffsetZip64EndOfCentralDirRec() + offsetZip64EndCentralDirRec);
/*     */ 
/*     */     
/* 586 */     zipModel.getZip64EndCentralDirLocator().setTotNumberOfDiscs(1);
/*     */   }
/*     */   
/*     */   private void updateSplitZip64EndCentralDirRec(ZipModel zipModel, ArrayList<Long> fileSizeList) throws ZipException {
/* 590 */     if (zipModel == null) {
/* 591 */       throw new ZipException("zip model is null, cannot update split Zip64 end of central directory record");
/*     */     }
/*     */     
/* 594 */     if (zipModel.getZip64EndCentralDirRecord() == null) {
/*     */       return;
/*     */     }
/*     */     
/* 598 */     zipModel.getZip64EndCentralDirRecord().setNoOfThisDisk(0);
/* 599 */     zipModel.getZip64EndCentralDirRecord().setNoOfThisDiskStartOfCentralDir(0);
/* 600 */     zipModel.getZip64EndCentralDirRecord().setTotNoOfEntriesInCentralDirOnThisDisk(zipModel.getEndCentralDirRecord().getTotNoOfEntriesInCentralDir());
/*     */ 
/*     */     
/* 603 */     long offsetStartCenDirWRTStartDiskNo = 0L;
/*     */     
/* 605 */     for (int i = 0; i < fileSizeList.size(); i++) {
/* 606 */       offsetStartCenDirWRTStartDiskNo += ((Long)fileSizeList.get(i)).longValue();
/*     */     }
/*     */     
/* 609 */     zipModel.getZip64EndCentralDirRecord().setOffsetStartCenDirWRTStartDiskNo(zipModel.getZip64EndCentralDirRecord().getOffsetStartCenDirWRTStartDiskNo() + offsetStartCenDirWRTStartDiskNo);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void setComment(ZipModel zipModel, String comment) throws ZipException {
/* 615 */     if (comment == null) {
/* 616 */       throw new ZipException("comment is null, cannot update Zip file with comment");
/*     */     }
/*     */     
/* 619 */     if (zipModel == null) {
/* 620 */       throw new ZipException("zipModel is null, cannot update Zip file with comment");
/*     */     }
/*     */     
/* 623 */     zipModel.getEndCentralDirRecord().setComment(comment);
/* 624 */     zipModel.getEndCentralDirRecord().setCommentBytes(comment.getBytes());
/* 625 */     zipModel.getEndCentralDirRecord().setCommentLength(comment.length());
/*     */     
/* 627 */     SplitOutputStream outputStream = null;
/*     */     
/*     */     try {
/* 630 */       HeaderWriter headerWriter = new HeaderWriter();
/* 631 */       outputStream = new SplitOutputStream(zipModel.getZipFile());
/*     */       
/* 633 */       if (zipModel.isZip64Format()) {
/* 634 */         outputStream.seek(zipModel.getZip64EndCentralDirRecord().getOffsetStartCenDirWRTStartDiskNo());
/*     */       } else {
/* 636 */         outputStream.seek(zipModel.getEndCentralDirRecord().getOffsetOfStartOfCentralDir());
/*     */       } 
/*     */       
/* 639 */       headerWriter.finalizeZipFileWithoutValidations(zipModel, (OutputStream)outputStream);
/* 640 */     } catch (FileNotFoundException e) {
/* 641 */       throw new ZipException(e);
/* 642 */     } catch (IOException e) {
/* 643 */       throw new ZipException(e);
/*     */     } finally {
/* 645 */       if (outputStream != null) {
/*     */         try {
/* 647 */           outputStream.close();
/* 648 */         } catch (IOException e) {}
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void initProgressMonitorForRemoveOp(ZipModel zipModel, FileHeader fileHeader, ProgressMonitor progressMonitor) throws ZipException {
/* 657 */     if (zipModel == null || fileHeader == null || progressMonitor == null) {
/* 658 */       throw new ZipException("one of the input parameters is null, cannot calculate total work");
/*     */     }
/*     */     
/* 661 */     progressMonitor.setCurrentOperation(2);
/* 662 */     progressMonitor.setFileName(fileHeader.getFileName());
/* 663 */     progressMonitor.setTotalWork(calculateTotalWorkForRemoveOp(zipModel, fileHeader));
/* 664 */     progressMonitor.setState(1);
/*     */   }
/*     */   
/*     */   private long calculateTotalWorkForRemoveOp(ZipModel zipModel, FileHeader fileHeader) throws ZipException {
/* 668 */     return Zip4jUtil.getFileLengh(new File(zipModel.getZipFile())) - fileHeader.getCompressedSize();
/*     */   }
/*     */   
/*     */   public void initProgressMonitorForMergeOp(ZipModel zipModel, ProgressMonitor progressMonitor) throws ZipException {
/* 672 */     if (zipModel == null) {
/* 673 */       throw new ZipException("zip model is null, cannot calculate total work for merge op");
/*     */     }
/*     */     
/* 676 */     progressMonitor.setCurrentOperation(4);
/* 677 */     progressMonitor.setFileName(zipModel.getZipFile());
/* 678 */     progressMonitor.setTotalWork(calculateTotalWorkForMergeOp(zipModel));
/* 679 */     progressMonitor.setState(1);
/*     */   }
/*     */   
/*     */   private long calculateTotalWorkForMergeOp(ZipModel zipModel) throws ZipException {
/* 683 */     long totSize = 0L;
/* 684 */     if (zipModel.isSplitArchive()) {
/* 685 */       int totNoOfSplitFiles = zipModel.getEndCentralDirRecord().getNoOfThisDisk();
/* 686 */       String partFile = null;
/* 687 */       String curZipFile = zipModel.getZipFile();
/* 688 */       int partNumber = 0;
/* 689 */       for (int i = 0; i <= totNoOfSplitFiles; i++) {
/* 690 */         if (partNumber == zipModel.getEndCentralDirRecord().getNoOfThisDisk()) {
/* 691 */           partFile = zipModel.getZipFile();
/*     */         }
/* 693 */         else if (partNumber >= 9) {
/* 694 */           partFile = curZipFile.substring(0, curZipFile.lastIndexOf(".")) + ".z" + (partNumber + 1);
/*     */         } else {
/* 696 */           partFile = curZipFile.substring(0, curZipFile.lastIndexOf(".")) + ".z0" + (partNumber + 1);
/*     */         } 
/*     */ 
/*     */         
/* 700 */         totSize += Zip4jUtil.getFileLengh(new File(partFile));
/*     */       } 
/*     */     } 
/*     */     
/* 704 */     return totSize;
/*     */   }
/*     */ }


/* Location:              C:\Users\DuduM\OneDrive\Área de Trabalho\mw10\modularwarfare-shining-2023.2.4.4f-fix10.jar!\net\lingala\zip4\\util\ArchiveMaintainer.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */