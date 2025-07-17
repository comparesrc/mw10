/*     */ package net.lingala.zip4j.unzip;
/*     */ 
/*     */ import java.io.File;
/*     */ import java.io.FileNotFoundException;
/*     */ import java.io.FileOutputStream;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.io.OutputStream;
/*     */ import java.io.RandomAccessFile;
/*     */ import java.util.Arrays;
/*     */ import java.util.zip.CRC32;
/*     */ import net.lingala.zip4j.core.HeaderReader;
/*     */ import net.lingala.zip4j.crypto.AESDecrypter;
/*     */ import net.lingala.zip4j.crypto.IDecrypter;
/*     */ import net.lingala.zip4j.crypto.StandardDecrypter;
/*     */ import net.lingala.zip4j.exception.ZipException;
/*     */ import net.lingala.zip4j.io.BaseInputStream;
/*     */ import net.lingala.zip4j.io.InflaterInputStream;
/*     */ import net.lingala.zip4j.io.PartInputStream;
/*     */ import net.lingala.zip4j.io.ZipInputStream;
/*     */ import net.lingala.zip4j.model.AESExtraDataRecord;
/*     */ import net.lingala.zip4j.model.FileHeader;
/*     */ import net.lingala.zip4j.model.LocalFileHeader;
/*     */ import net.lingala.zip4j.model.UnzipParameters;
/*     */ import net.lingala.zip4j.model.ZipModel;
/*     */ import net.lingala.zip4j.progress.ProgressMonitor;
/*     */ import net.lingala.zip4j.util.Raw;
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
/*     */ public class UnzipEngine
/*     */ {
/*     */   private ZipModel zipModel;
/*     */   private FileHeader fileHeader;
/*  52 */   private int currSplitFileCounter = 0;
/*     */   private LocalFileHeader localFileHeader;
/*     */   private IDecrypter decrypter;
/*     */   private CRC32 crc;
/*     */   
/*     */   public UnzipEngine(ZipModel zipModel, FileHeader fileHeader) throws ZipException {
/*  58 */     if (zipModel == null || fileHeader == null) {
/*  59 */       throw new ZipException("Invalid parameters passed to StoreUnzip. One or more of the parameters were null");
/*     */     }
/*     */     
/*  62 */     this.zipModel = zipModel;
/*  63 */     this.fileHeader = fileHeader;
/*  64 */     this.crc = new CRC32();
/*     */   }
/*     */   
/*     */   public void unzipFile(ProgressMonitor progressMonitor, String outPath, String newFileName, UnzipParameters unzipParameters) throws ZipException {
/*     */     ZipInputStream zipInputStream;
/*  69 */     if (this.zipModel == null || this.fileHeader == null || !Zip4jUtil.isStringNotNullAndNotEmpty(outPath)) {
/*  70 */       throw new ZipException("Invalid parameters passed during unzipping file. One or more of the parameters were null");
/*     */     }
/*  72 */     InputStream is = null;
/*  73 */     OutputStream os = null;
/*     */     try {
/*  75 */       byte[] buff = new byte[4096];
/*  76 */       int readLength = -1;
/*     */       
/*  78 */       zipInputStream = getInputStream();
/*  79 */       os = getOutputStream(outPath, newFileName);
/*     */       
/*  81 */       while ((readLength = zipInputStream.read(buff)) != -1) {
/*  82 */         os.write(buff, 0, readLength);
/*  83 */         progressMonitor.updateWorkCompleted(readLength);
/*  84 */         if (progressMonitor.isCancelAllTasks()) {
/*  85 */           progressMonitor.setResult(3);
/*  86 */           progressMonitor.setState(0);
/*     */           
/*     */           return;
/*     */         } 
/*     */       } 
/*  91 */       closeStreams((InputStream)zipInputStream, os);
/*     */       
/*  93 */       UnzipUtil.applyFileAttributes(this.fileHeader, new File(getOutputFileNameWithPath(outPath, newFileName)), unzipParameters);
/*     */     }
/*  95 */     catch (IOException e) {
/*  96 */       throw new ZipException(e);
/*  97 */     } catch (Exception e) {
/*  98 */       throw new ZipException(e);
/*     */     } finally {
/* 100 */       closeStreams((InputStream)zipInputStream, os);
/*     */     } 
/*     */   }
/*     */   
/*     */   public ZipInputStream getInputStream() throws ZipException {
/* 105 */     if (this.fileHeader == null) {
/* 106 */       throw new ZipException("file header is null, cannot get inputstream");
/*     */     }
/*     */     
/*     */     try {
/* 110 */       RandomAccessFile raf = createFileHandler("r");
/* 111 */       String errMsg = "local header and file header do not match";
/*     */ 
/*     */       
/* 114 */       if (!checkLocalHeader()) {
/* 115 */         throw new ZipException(errMsg);
/*     */       }
/* 117 */       init(raf);
/*     */       
/* 119 */       long comprSize = this.localFileHeader.getCompressedSize();
/* 120 */       long offsetStartOfData = this.localFileHeader.getOffsetStartOfData();
/*     */       
/* 122 */       if (this.localFileHeader.isEncrypted()) {
/* 123 */         if (this.localFileHeader.getEncryptionMethod() == 99) {
/* 124 */           if (this.decrypter instanceof AESDecrypter) {
/* 125 */             comprSize -= (((AESDecrypter)this.decrypter).getSaltLength() + ((AESDecrypter)this.decrypter).getPasswordVerifierLength() + 10);
/*     */             
/* 127 */             offsetStartOfData += (((AESDecrypter)this.decrypter).getSaltLength() + ((AESDecrypter)this.decrypter).getPasswordVerifierLength());
/*     */           } else {
/*     */             
/* 130 */             throw new ZipException("invalid decryptor when trying to calculate compressed size for AES encrypted file: " + this.fileHeader.getFileName());
/*     */           }
/*     */         
/* 133 */         } else if (this.localFileHeader.getEncryptionMethod() == 0) {
/* 134 */           comprSize -= 12L;
/* 135 */           offsetStartOfData += 12L;
/*     */         } 
/*     */       }
/*     */       
/* 139 */       int compressionMethod = this.fileHeader.getCompressionMethod();
/* 140 */       if (this.fileHeader.getEncryptionMethod() == 99) {
/* 141 */         if (this.fileHeader.getAesExtraDataRecord() != null) {
/* 142 */           compressionMethod = this.fileHeader.getAesExtraDataRecord().getCompressionMethod();
/*     */         } else {
/* 144 */           throw new ZipException("AESExtraDataRecord does not exist for AES encrypted file: " + this.fileHeader.getFileName());
/*     */         } 
/*     */       }
/* 147 */       raf.seek(offsetStartOfData);
/* 148 */       switch (compressionMethod) {
/*     */         case 0:
/* 150 */           return new ZipInputStream((BaseInputStream)new PartInputStream(raf, offsetStartOfData, comprSize, this));
/*     */         case 8:
/* 152 */           return new ZipInputStream((BaseInputStream)new InflaterInputStream(raf, offsetStartOfData, comprSize, this));
/*     */       } 
/* 154 */       throw new ZipException("compression type not supported");
/*     */     }
/* 156 */     catch (ZipException e) {
/* 157 */       throw e;
/* 158 */     } catch (Exception e) {
/* 159 */       throw new ZipException(e);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private void init(RandomAccessFile raf) throws ZipException {
/* 166 */     if (this.localFileHeader == null) {
/* 167 */       throw new ZipException("local file header is null, cannot initialize input stream");
/*     */     }
/*     */     
/*     */     try {
/* 171 */       initDecrypter(raf);
/* 172 */     } catch (ZipException e) {
/* 173 */       throw e;
/* 174 */     } catch (Exception e) {
/* 175 */       throw new ZipException(e);
/*     */     } 
/*     */   }
/*     */   
/*     */   private void initDecrypter(RandomAccessFile raf) throws ZipException {
/* 180 */     if (this.localFileHeader == null) {
/* 181 */       throw new ZipException("local file header is null, cannot init decrypter");
/*     */     }
/*     */     
/* 184 */     if (this.localFileHeader.isEncrypted()) {
/* 185 */       if (this.localFileHeader.getEncryptionMethod() == 0) {
/* 186 */         this.decrypter = (IDecrypter)new StandardDecrypter(this.fileHeader, getStandardDecrypterHeaderBytes(raf));
/* 187 */       } else if (this.localFileHeader.getEncryptionMethod() == 99) {
/* 188 */         this.decrypter = (IDecrypter)new AESDecrypter(this.localFileHeader, getAESSalt(raf), getAESPasswordVerifier(raf));
/*     */       } else {
/* 190 */         throw new ZipException("unsupported encryption method");
/*     */       } 
/*     */     }
/*     */   }
/*     */   
/*     */   private byte[] getStandardDecrypterHeaderBytes(RandomAccessFile raf) throws ZipException {
/*     */     try {
/* 197 */       byte[] headerBytes = new byte[12];
/* 198 */       raf.seek(this.localFileHeader.getOffsetStartOfData());
/* 199 */       raf.read(headerBytes, 0, 12);
/* 200 */       return headerBytes;
/* 201 */     } catch (IOException e) {
/* 202 */       throw new ZipException(e);
/* 203 */     } catch (Exception e) {
/* 204 */       throw new ZipException(e);
/*     */     } 
/*     */   }
/*     */   
/*     */   private byte[] getAESSalt(RandomAccessFile raf) throws ZipException {
/* 209 */     if (this.localFileHeader.getAesExtraDataRecord() == null) {
/* 210 */       return null;
/*     */     }
/*     */     try {
/* 213 */       AESExtraDataRecord aesExtraDataRecord = this.localFileHeader.getAesExtraDataRecord();
/* 214 */       byte[] saltBytes = new byte[calculateAESSaltLength(aesExtraDataRecord)];
/* 215 */       raf.seek(this.localFileHeader.getOffsetStartOfData());
/* 216 */       raf.read(saltBytes);
/* 217 */       return saltBytes;
/* 218 */     } catch (IOException e) {
/* 219 */       throw new ZipException(e);
/*     */     } 
/*     */   }
/*     */   
/*     */   private byte[] getAESPasswordVerifier(RandomAccessFile raf) throws ZipException {
/*     */     try {
/* 225 */       byte[] pvBytes = new byte[2];
/* 226 */       raf.read(pvBytes);
/* 227 */       return pvBytes;
/* 228 */     } catch (IOException e) {
/* 229 */       throw new ZipException(e);
/*     */     } 
/*     */   }
/*     */   
/*     */   private int calculateAESSaltLength(AESExtraDataRecord aesExtraDataRecord) throws ZipException {
/* 234 */     if (aesExtraDataRecord == null) {
/* 235 */       throw new ZipException("unable to determine salt length: AESExtraDataRecord is null");
/*     */     }
/* 237 */     switch (aesExtraDataRecord.getAesStrength()) {
/*     */       case 1:
/* 239 */         return 8;
/*     */       case 2:
/* 241 */         return 12;
/*     */       case 3:
/* 243 */         return 16;
/*     */     } 
/* 245 */     throw new ZipException("unable to determine salt length: invalid aes key strength");
/*     */   }
/*     */ 
/*     */   
/*     */   public void checkCRC() throws ZipException {
/* 250 */     if (this.fileHeader != null) {
/* 251 */       if (this.fileHeader.getEncryptionMethod() == 99) {
/* 252 */         if (this.decrypter != null && this.decrypter instanceof AESDecrypter) {
/* 253 */           byte[] tmpMacBytes = ((AESDecrypter)this.decrypter).getCalculatedAuthenticationBytes();
/* 254 */           byte[] storedMac = ((AESDecrypter)this.decrypter).getStoredMac();
/* 255 */           byte[] calculatedMac = new byte[10];
/*     */           
/* 257 */           if (calculatedMac == null || storedMac == null) {
/* 258 */             throw new ZipException("CRC (MAC) check failed for " + this.fileHeader.getFileName());
/*     */           }
/*     */           
/* 261 */           System.arraycopy(tmpMacBytes, 0, calculatedMac, 0, 10);
/*     */           
/* 263 */           if (!Arrays.equals(calculatedMac, storedMac)) {
/* 264 */             throw new ZipException("invalid CRC (MAC) for file: " + this.fileHeader.getFileName());
/*     */           }
/*     */         } 
/*     */       } else {
/* 268 */         long calculatedCRC = this.crc.getValue() & 0xFFFFFFFFL;
/* 269 */         if (calculatedCRC != this.fileHeader.getCrc32()) {
/* 270 */           String errMsg = "invalid CRC for file: " + this.fileHeader.getFileName();
/* 271 */           if (this.localFileHeader.isEncrypted() && this.localFileHeader.getEncryptionMethod() == 0)
/*     */           {
/* 273 */             errMsg = errMsg + " - Wrong Password?";
/*     */           }
/* 275 */           throw new ZipException(errMsg);
/*     */         } 
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
/*     */   private boolean checkLocalHeader() throws ZipException {
/* 310 */     RandomAccessFile rafForLH = null;
/*     */     try {
/* 312 */       rafForLH = checkSplitFile();
/*     */       
/* 314 */       if (rafForLH == null) {
/* 315 */         rafForLH = new RandomAccessFile(new File(this.zipModel.getZipFile()), "r");
/*     */       }
/*     */       
/* 318 */       HeaderReader headerReader = new HeaderReader(rafForLH);
/* 319 */       this.localFileHeader = headerReader.readLocalFileHeader(this.fileHeader);
/*     */       
/* 321 */       if (this.localFileHeader == null) {
/* 322 */         throw new ZipException("error reading local file header. Is this a valid zip file?");
/*     */       }
/*     */ 
/*     */       
/* 326 */       if (this.localFileHeader.getCompressionMethod() != this.fileHeader.getCompressionMethod()) {
/* 327 */         return false;
/*     */       }
/*     */       
/* 330 */       return true;
/* 331 */     } catch (FileNotFoundException e) {
/* 332 */       throw new ZipException(e);
/*     */     } finally {
/* 334 */       if (rafForLH != null) {
/*     */         try {
/* 336 */           rafForLH.close();
/* 337 */         } catch (IOException e) {
/*     */         
/* 339 */         } catch (Exception e) {}
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private RandomAccessFile checkSplitFile() throws ZipException {
/* 347 */     if (this.zipModel.isSplitArchive()) {
/* 348 */       int diskNumberStartOfFile = this.fileHeader.getDiskNumberStart();
/* 349 */       this.currSplitFileCounter = diskNumberStartOfFile + 1;
/* 350 */       String curZipFile = this.zipModel.getZipFile();
/* 351 */       String partFile = null;
/* 352 */       if (diskNumberStartOfFile == this.zipModel.getEndCentralDirRecord().getNoOfThisDisk()) {
/* 353 */         partFile = this.zipModel.getZipFile();
/*     */       }
/* 355 */       else if (diskNumberStartOfFile >= 9) {
/* 356 */         partFile = curZipFile.substring(0, curZipFile.lastIndexOf(".")) + ".z" + (diskNumberStartOfFile + 1);
/*     */       } else {
/* 358 */         partFile = curZipFile.substring(0, curZipFile.lastIndexOf(".")) + ".z0" + (diskNumberStartOfFile + 1);
/*     */       } 
/*     */ 
/*     */       
/*     */       try {
/* 363 */         RandomAccessFile raf = new RandomAccessFile(partFile, "r");
/*     */         
/* 365 */         if (this.currSplitFileCounter == 1) {
/* 366 */           byte[] splitSig = new byte[4];
/* 367 */           raf.read(splitSig);
/* 368 */           if (Raw.readIntLittleEndian(splitSig, 0) != 134695760L) {
/* 369 */             throw new ZipException("invalid first part split file signature");
/*     */           }
/*     */         } 
/* 372 */         return raf;
/* 373 */       } catch (FileNotFoundException e) {
/* 374 */         throw new ZipException(e);
/* 375 */       } catch (IOException e) {
/* 376 */         throw new ZipException(e);
/*     */       } 
/*     */     } 
/* 379 */     return null;
/*     */   }
/*     */   
/*     */   private RandomAccessFile createFileHandler(String mode) throws ZipException {
/* 383 */     if (this.zipModel == null || !Zip4jUtil.isStringNotNullAndNotEmpty(this.zipModel.getZipFile())) {
/* 384 */       throw new ZipException("input parameter is null in getFilePointer");
/*     */     }
/*     */     
/*     */     try {
/* 388 */       RandomAccessFile raf = null;
/* 389 */       if (this.zipModel.isSplitArchive()) {
/* 390 */         raf = checkSplitFile();
/*     */       } else {
/* 392 */         raf = new RandomAccessFile(new File(this.zipModel.getZipFile()), mode);
/*     */       } 
/* 394 */       return raf;
/* 395 */     } catch (FileNotFoundException e) {
/* 396 */       throw new ZipException(e);
/* 397 */     } catch (Exception e) {
/* 398 */       throw new ZipException(e);
/*     */     } 
/*     */   }
/*     */   
/*     */   private FileOutputStream getOutputStream(String outPath, String newFileName) throws ZipException {
/* 403 */     if (!Zip4jUtil.isStringNotNullAndNotEmpty(outPath)) {
/* 404 */       throw new ZipException("invalid output path");
/*     */     }
/*     */     
/*     */     try {
/* 408 */       File file = new File(getOutputFileNameWithPath(outPath, newFileName));
/*     */       
/* 410 */       if (!file.getParentFile().exists()) {
/* 411 */         file.getParentFile().mkdirs();
/*     */       }
/*     */       
/* 414 */       if (file.exists()) {
/* 415 */         file.delete();
/*     */       }
/*     */       
/* 418 */       FileOutputStream fileOutputStream = new FileOutputStream(file);
/* 419 */       return fileOutputStream;
/* 420 */     } catch (FileNotFoundException e) {
/* 421 */       throw new ZipException(e);
/*     */     } 
/*     */   }
/*     */   
/*     */   private String getOutputFileNameWithPath(String outPath, String newFileName) throws ZipException {
/* 426 */     String fileName = null;
/* 427 */     if (Zip4jUtil.isStringNotNullAndNotEmpty(newFileName)) {
/* 428 */       fileName = newFileName;
/*     */     } else {
/* 430 */       fileName = this.fileHeader.getFileName();
/*     */     } 
/* 432 */     return outPath + System.getProperty("file.separator") + fileName;
/*     */   }
/*     */   
/*     */   public RandomAccessFile startNextSplitFile() throws IOException, FileNotFoundException {
/* 436 */     String currZipFile = this.zipModel.getZipFile();
/* 437 */     String partFile = null;
/* 438 */     if (this.currSplitFileCounter == this.zipModel.getEndCentralDirRecord().getNoOfThisDisk()) {
/* 439 */       partFile = this.zipModel.getZipFile();
/*     */     }
/* 441 */     else if (this.currSplitFileCounter >= 9) {
/* 442 */       partFile = currZipFile.substring(0, currZipFile.lastIndexOf(".")) + ".z" + (this.currSplitFileCounter + 1);
/*     */     } else {
/* 444 */       partFile = currZipFile.substring(0, currZipFile.lastIndexOf(".")) + ".z0" + (this.currSplitFileCounter + 1);
/*     */     } 
/*     */     
/* 447 */     this.currSplitFileCounter++;
/*     */     try {
/* 449 */       if (!Zip4jUtil.checkFileExists(partFile)) {
/* 450 */         throw new IOException("zip split file does not exist: " + partFile);
/*     */       }
/* 452 */     } catch (ZipException e) {
/* 453 */       throw new IOException(e.getMessage());
/*     */     } 
/* 455 */     return new RandomAccessFile(partFile, "r");
/*     */   }
/*     */   
/*     */   private void closeStreams(InputStream is, OutputStream os) throws ZipException {
/* 459 */     if (is != null) {
/*     */       try {
/* 461 */         is.close();
/* 462 */         is = null;
/* 463 */       } catch (IOException e) {
/* 464 */         if (e != null && Zip4jUtil.isStringNotNullAndNotEmpty(e.getMessage()) && 
/* 465 */           e.getMessage().indexOf(" - Wrong Password?") >= 0) {
/* 466 */           throw new ZipException(e.getMessage());
/*     */         }
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/* 472 */     if (os != null) {
/*     */       try {
/* 474 */         os.close();
/* 475 */         os = null;
/* 476 */       } catch (IOException e) {}
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void updateCRC(int b) {
/* 483 */     this.crc.update(b);
/*     */   }
/*     */   
/*     */   public void updateCRC(byte[] buff, int offset, int len) {
/* 487 */     if (buff != null) {
/* 488 */       this.crc.update(buff, offset, len);
/*     */     }
/*     */   }
/*     */   
/*     */   public FileHeader getFileHeader() {
/* 493 */     return this.fileHeader;
/*     */   }
/*     */   
/*     */   public IDecrypter getDecrypter() {
/* 497 */     return this.decrypter;
/*     */   }
/*     */   
/*     */   public ZipModel getZipModel() {
/* 501 */     return this.zipModel;
/*     */   }
/*     */   
/*     */   public LocalFileHeader getLocalFileHeader() {
/* 505 */     return this.localFileHeader;
/*     */   }
/*     */ }


/* Location:              C:\Users\DuduM\OneDrive\Área de Trabalho\mw10\modularwarfare-shining-2023.2.4.4f-fix10.jar!\net\lingala\zip4\\unzip\UnzipEngine.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */