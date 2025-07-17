/*     */ package net.lingala.zip4j.io;
/*     */ 
/*     */ import java.io.File;
/*     */ import java.io.IOException;
/*     */ import java.io.OutputStream;
/*     */ import java.util.ArrayList;
/*     */ import java.util.zip.CRC32;
/*     */ import net.lingala.zip4j.core.HeaderWriter;
/*     */ import net.lingala.zip4j.crypto.AESEncrpyter;
/*     */ import net.lingala.zip4j.crypto.IEncrypter;
/*     */ import net.lingala.zip4j.crypto.StandardEncrypter;
/*     */ import net.lingala.zip4j.exception.ZipException;
/*     */ import net.lingala.zip4j.model.AESExtraDataRecord;
/*     */ import net.lingala.zip4j.model.CentralDirectory;
/*     */ import net.lingala.zip4j.model.EndCentralDirRecord;
/*     */ import net.lingala.zip4j.model.FileHeader;
/*     */ import net.lingala.zip4j.model.LocalFileHeader;
/*     */ import net.lingala.zip4j.model.Zip64EndCentralDirLocator;
/*     */ import net.lingala.zip4j.model.Zip64EndCentralDirRecord;
/*     */ import net.lingala.zip4j.model.ZipModel;
/*     */ import net.lingala.zip4j.model.ZipParameters;
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
/*     */ public class CipherOutputStream
/*     */   extends BaseOutputStream
/*     */ {
/*     */   protected OutputStream outputStream;
/*     */   private File sourceFile;
/*     */   protected FileHeader fileHeader;
/*     */   protected LocalFileHeader localFileHeader;
/*     */   private IEncrypter encrypter;
/*     */   protected ZipParameters zipParameters;
/*     */   protected ZipModel zipModel;
/*     */   private long totalBytesWritten;
/*     */   protected CRC32 crc;
/*     */   private long bytesWrittenForThisFile;
/*     */   private byte[] pendingBuffer;
/*     */   private int pendingBufferLength;
/*     */   
/*     */   public CipherOutputStream(OutputStream outputStream, ZipModel zipModel) {
/*  60 */     this.outputStream = outputStream;
/*  61 */     initZipModel(zipModel);
/*  62 */     this.crc = new CRC32();
/*  63 */     this.totalBytesWritten = 0L;
/*  64 */     this.bytesWrittenForThisFile = 0L;
/*  65 */     this.pendingBuffer = new byte[16];
/*  66 */     this.pendingBufferLength = 0;
/*     */   }
/*     */   
/*     */   public void putNextEntry(File file, ZipParameters zipParameters) throws ZipException {
/*  70 */     if (!zipParameters.isSourceExternalStream() && file == null) {
/*  71 */       throw new ZipException("input file is null");
/*     */     }
/*     */     
/*  74 */     if (!zipParameters.isSourceExternalStream() && !Zip4jUtil.checkFileExists(file)) {
/*  75 */       throw new ZipException("input file does not exist");
/*     */     }
/*     */     
/*  78 */     if (zipParameters == null) {
/*  79 */       zipParameters = new ZipParameters();
/*     */     }
/*     */     
/*     */     try {
/*  83 */       this.sourceFile = file;
/*     */       
/*  85 */       this.zipParameters = (ZipParameters)zipParameters.clone();
/*     */       
/*  87 */       if (!zipParameters.isSourceExternalStream()) {
/*  88 */         if (this.sourceFile.isDirectory()) {
/*  89 */           this.zipParameters.setEncryptFiles(false);
/*  90 */           this.zipParameters.setEncryptionMethod(-1);
/*  91 */           this.zipParameters.setCompressionMethod(0);
/*     */         } 
/*     */       } else {
/*  94 */         if (!Zip4jUtil.isStringNotNullAndNotEmpty(this.zipParameters.getFileNameInZip())) {
/*  95 */           throw new ZipException("file name is empty for external stream");
/*     */         }
/*  97 */         if (this.zipParameters.getFileNameInZip().endsWith("/") || this.zipParameters.getFileNameInZip().endsWith("\\")) {
/*     */           
/*  99 */           this.zipParameters.setEncryptFiles(false);
/* 100 */           this.zipParameters.setEncryptionMethod(-1);
/* 101 */           this.zipParameters.setCompressionMethod(0);
/*     */         } 
/*     */       } 
/*     */       
/* 105 */       createFileHeader();
/* 106 */       createLocalFileHeader();
/*     */       
/* 108 */       if (this.zipModel.isSplitArchive() && (
/* 109 */         this.zipModel.getCentralDirectory() == null || this.zipModel.getCentralDirectory().getFileHeaders() == null || this.zipModel.getCentralDirectory().getFileHeaders().size() == 0)) {
/*     */ 
/*     */         
/* 112 */         byte[] intByte = new byte[4];
/* 113 */         Raw.writeIntLittleEndian(intByte, 0, 134695760);
/* 114 */         this.outputStream.write(intByte);
/* 115 */         this.totalBytesWritten += 4L;
/*     */       } 
/*     */ 
/*     */       
/* 119 */       if (this.outputStream instanceof SplitOutputStream) {
/* 120 */         if (this.totalBytesWritten == 4L) {
/* 121 */           this.fileHeader.setOffsetLocalHeader(4L);
/*     */         } else {
/* 123 */           this.fileHeader.setOffsetLocalHeader(((SplitOutputStream)this.outputStream).getFilePointer());
/*     */         }
/*     */       
/* 126 */       } else if (this.totalBytesWritten == 4L) {
/* 127 */         this.fileHeader.setOffsetLocalHeader(4L);
/*     */       } else {
/* 129 */         this.fileHeader.setOffsetLocalHeader(this.totalBytesWritten);
/*     */       } 
/*     */ 
/*     */       
/* 133 */       HeaderWriter headerWriter = new HeaderWriter();
/* 134 */       this.totalBytesWritten += headerWriter.writeLocalFileHeader(this.zipModel, this.localFileHeader, this.outputStream);
/*     */       
/* 136 */       if (this.zipParameters.isEncryptFiles()) {
/* 137 */         initEncrypter();
/* 138 */         if (this.encrypter != null) {
/* 139 */           if (zipParameters.getEncryptionMethod() == 0) {
/* 140 */             byte[] headerBytes = ((StandardEncrypter)this.encrypter).getHeaderBytes();
/* 141 */             this.outputStream.write(headerBytes);
/* 142 */             this.totalBytesWritten += headerBytes.length;
/* 143 */             this.bytesWrittenForThisFile += headerBytes.length;
/* 144 */           } else if (zipParameters.getEncryptionMethod() == 99) {
/* 145 */             byte[] saltBytes = ((AESEncrpyter)this.encrypter).getSaltBytes();
/* 146 */             byte[] passwordVerifier = ((AESEncrpyter)this.encrypter).getDerivedPasswordVerifier();
/* 147 */             this.outputStream.write(saltBytes);
/* 148 */             this.outputStream.write(passwordVerifier);
/* 149 */             this.totalBytesWritten += (saltBytes.length + passwordVerifier.length);
/* 150 */             this.bytesWrittenForThisFile += (saltBytes.length + passwordVerifier.length);
/*     */           } 
/*     */         }
/*     */       } 
/*     */       
/* 155 */       this.crc.reset();
/* 156 */     } catch (CloneNotSupportedException e) {
/* 157 */       throw new ZipException(e);
/* 158 */     } catch (ZipException e) {
/* 159 */       throw e;
/* 160 */     } catch (Exception e) {
/* 161 */       throw new ZipException(e);
/*     */     } 
/*     */   }
/*     */   
/*     */   private void initEncrypter() throws ZipException {
/* 166 */     if (!this.zipParameters.isEncryptFiles()) {
/* 167 */       this.encrypter = null;
/*     */       
/*     */       return;
/*     */     } 
/* 171 */     switch (this.zipParameters.getEncryptionMethod()) {
/*     */       case 0:
/* 173 */         this.encrypter = (IEncrypter)new StandardEncrypter(this.zipParameters.getPassword(), this.zipParameters.getSourceFileCRC());
/*     */         return;
/*     */       case 99:
/* 176 */         this.encrypter = (IEncrypter)new AESEncrpyter(this.zipParameters.getPassword(), this.zipParameters.getAesKeyStrength());
/*     */         return;
/*     */     } 
/* 179 */     throw new ZipException("invalid encprytion method");
/*     */   }
/*     */ 
/*     */   
/*     */   private void initZipModel(ZipModel zipModel) {
/* 184 */     if (zipModel == null) {
/* 185 */       this.zipModel = new ZipModel();
/*     */     } else {
/* 187 */       this.zipModel = zipModel;
/*     */     } 
/*     */     
/* 190 */     if (this.zipModel.getEndCentralDirRecord() == null) {
/* 191 */       this.zipModel.setEndCentralDirRecord(new EndCentralDirRecord());
/*     */     }
/* 193 */     if (this.zipModel.getCentralDirectory() == null) {
/* 194 */       this.zipModel.setCentralDirectory(new CentralDirectory());
/*     */     }
/* 196 */     if (this.zipModel.getCentralDirectory().getFileHeaders() == null) {
/* 197 */       this.zipModel.getCentralDirectory().setFileHeaders(new ArrayList());
/*     */     }
/* 199 */     if (this.zipModel.getLocalFileHeaderList() == null) {
/* 200 */       this.zipModel.setLocalFileHeaderList(new ArrayList());
/*     */     }
/* 202 */     if (this.outputStream instanceof SplitOutputStream && (
/* 203 */       (SplitOutputStream)this.outputStream).isSplitZipFile()) {
/* 204 */       this.zipModel.setSplitArchive(true);
/* 205 */       this.zipModel.setSplitLength(((SplitOutputStream)this.outputStream).getSplitLength());
/*     */     } 
/*     */ 
/*     */     
/* 209 */     this.zipModel.getEndCentralDirRecord().setSignature(101010256L);
/*     */   }
/*     */   
/*     */   public void write(int bval) throws IOException {
/* 213 */     byte[] b = new byte[1];
/* 214 */     b[0] = (byte)bval;
/* 215 */     write(b, 0, 1);
/*     */   }
/*     */   
/*     */   public void write(byte[] b) throws IOException {
/* 219 */     if (b == null) {
/* 220 */       throw new NullPointerException();
/*     */     }
/* 222 */     if (b.length == 0)
/*     */       return; 
/* 224 */     write(b, 0, b.length);
/*     */   }
/*     */   
/*     */   public void write(byte[] b, int off, int len) throws IOException {
/* 228 */     if (len == 0)
/*     */       return; 
/* 230 */     if (this.zipParameters.isEncryptFiles() && this.zipParameters.getEncryptionMethod() == 99) {
/*     */       
/* 232 */       if (this.pendingBufferLength != 0) {
/* 233 */         if (len >= 16 - this.pendingBufferLength) {
/* 234 */           System.arraycopy(b, off, this.pendingBuffer, this.pendingBufferLength, 16 - this.pendingBufferLength);
/*     */           
/* 236 */           encryptAndWrite(this.pendingBuffer, 0, this.pendingBuffer.length);
/* 237 */           off = 16 - this.pendingBufferLength;
/* 238 */           len -= off;
/* 239 */           this.pendingBufferLength = 0;
/*     */         } else {
/* 241 */           System.arraycopy(b, off, this.pendingBuffer, this.pendingBufferLength, len);
/*     */           
/* 243 */           this.pendingBufferLength += len;
/*     */           return;
/*     */         } 
/*     */       }
/* 247 */       if (len != 0 && len % 16 != 0) {
/* 248 */         System.arraycopy(b, len + off - len % 16, this.pendingBuffer, 0, len % 16);
/* 249 */         this.pendingBufferLength = len % 16;
/* 250 */         len -= this.pendingBufferLength;
/*     */       } 
/*     */     } 
/* 253 */     if (len != 0)
/* 254 */       encryptAndWrite(b, off, len); 
/*     */   }
/*     */   
/*     */   private void encryptAndWrite(byte[] b, int off, int len) throws IOException {
/* 258 */     if (this.encrypter != null) {
/*     */       try {
/* 260 */         this.encrypter.encryptData(b, off, len);
/* 261 */       } catch (ZipException e) {
/* 262 */         throw new IOException(e.getMessage());
/*     */       } 
/*     */     }
/* 265 */     this.outputStream.write(b, off, len);
/* 266 */     this.totalBytesWritten += len;
/* 267 */     this.bytesWrittenForThisFile += len;
/*     */   }
/*     */ 
/*     */   
/*     */   public void closeEntry() throws IOException, ZipException {
/* 272 */     if (this.pendingBufferLength != 0) {
/* 273 */       encryptAndWrite(this.pendingBuffer, 0, this.pendingBufferLength);
/* 274 */       this.pendingBufferLength = 0;
/*     */     } 
/*     */     
/* 277 */     if (this.zipParameters.isEncryptFiles() && this.zipParameters.getEncryptionMethod() == 99)
/*     */     {
/* 279 */       if (this.encrypter instanceof AESEncrpyter) {
/* 280 */         this.outputStream.write(((AESEncrpyter)this.encrypter).getFinalMac());
/* 281 */         this.bytesWrittenForThisFile += 10L;
/* 282 */         this.totalBytesWritten += 10L;
/*     */       } else {
/* 284 */         throw new ZipException("invalid encrypter for AES encrypted file");
/*     */       } 
/*     */     }
/* 287 */     this.fileHeader.setCompressedSize(this.bytesWrittenForThisFile);
/* 288 */     this.localFileHeader.setCompressedSize(this.bytesWrittenForThisFile);
/*     */     
/* 290 */     long crc32 = this.crc.getValue();
/* 291 */     if (this.fileHeader.isEncrypted()) {
/* 292 */       if (this.fileHeader.getEncryptionMethod() == 99) {
/* 293 */         crc32 = 0L;
/* 294 */       } else if (this.fileHeader.getEncryptionMethod() == 0 && 
/* 295 */         (int)crc32 != this.zipParameters.getSourceFileCRC()) {
/* 296 */         throw new ZipException("source file CRC and calculated CRC do not match for file: " + this.fileHeader.getFileName());
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/* 301 */     if (this.zipParameters.isEncryptFiles() && this.zipParameters.getEncryptionMethod() == 99) {
/*     */       
/* 303 */       this.fileHeader.setCrc32(0L);
/* 304 */       this.localFileHeader.setCrc32(0L);
/*     */     } else {
/* 306 */       this.fileHeader.setCrc32(crc32);
/* 307 */       this.localFileHeader.setCrc32(crc32);
/*     */     } 
/*     */     
/* 310 */     this.zipModel.getLocalFileHeaderList().add(this.localFileHeader);
/* 311 */     this.zipModel.getCentralDirectory().getFileHeaders().add(this.fileHeader);
/*     */     
/* 313 */     HeaderWriter headerWriter = new HeaderWriter();
/* 314 */     if (this.outputStream instanceof SplitOutputStream) {
/* 315 */       byte[] intByte = new byte[4];
/* 316 */       if (this.localFileHeader.isWriteComprSizeInZip64ExtraRecord()) {
/* 317 */         byte[] longByte = new byte[8];
/* 318 */         Raw.writeLongLittleEndian(longByte, 0, this.localFileHeader.getCompressedSize());
/* 319 */         headerWriter.updateLocalFileHeader(this.localFileHeader, this.fileHeader.getOffsetLocalHeader(), 18, this.zipModel, longByte, this.fileHeader.getDiskNumberStart(), (SplitOutputStream)this.outputStream);
/*     */       }
/*     */       else {
/*     */         
/* 323 */         Raw.writeIntLittleEndian(intByte, 0, (int)this.localFileHeader.getCompressedSize());
/* 324 */         headerWriter.updateLocalFileHeader(this.localFileHeader, this.fileHeader.getOffsetLocalHeader(), 18, this.zipModel, intByte, this.fileHeader.getDiskNumberStart(), (SplitOutputStream)this.outputStream);
/*     */       } 
/*     */ 
/*     */ 
/*     */       
/* 329 */       if (this.localFileHeader.getCrc32() != 0L) {
/* 330 */         Raw.writeIntLittleEndian(intByte, 0, (int)this.localFileHeader.getCrc32());
/*     */         
/* 332 */         headerWriter.updateLocalFileHeader(this.localFileHeader, this.fileHeader.getOffsetLocalHeader(), 14, this.zipModel, intByte, this.fileHeader.getDiskNumberStart(), (SplitOutputStream)this.outputStream);
/*     */       }
/*     */     
/*     */     } else {
/*     */       
/* 337 */       this.totalBytesWritten += headerWriter.writeExtendedLocalHeader(this.localFileHeader, this.outputStream);
/*     */     } 
/*     */     
/* 340 */     this.crc.reset();
/* 341 */     this.bytesWrittenForThisFile = 0L;
/* 342 */     this.encrypter = null;
/*     */   }
/*     */   
/*     */   public void finish() throws IOException, ZipException {
/* 346 */     int currSplitFileCounter = 0;
/* 347 */     if (this.outputStream instanceof SplitOutputStream) {
/* 348 */       this.zipModel.getEndCentralDirRecord().setOffsetOfStartOfCentralDir(((SplitOutputStream)this.outputStream).getFilePointer());
/*     */       
/* 350 */       currSplitFileCounter = ((SplitOutputStream)this.outputStream).getCurrSplitFileCounter();
/*     */     } else {
/*     */       
/* 353 */       this.zipModel.getEndCentralDirRecord().setOffsetOfStartOfCentralDir(this.totalBytesWritten);
/*     */     } 
/*     */     
/* 356 */     if (this.zipModel.isZip64Format()) {
/* 357 */       if (this.zipModel.getZip64EndCentralDirRecord() == null) {
/* 358 */         this.zipModel.setZip64EndCentralDirRecord(new Zip64EndCentralDirRecord());
/*     */       }
/* 360 */       if (this.zipModel.getZip64EndCentralDirLocator() == null) {
/* 361 */         this.zipModel.setZip64EndCentralDirLocator(new Zip64EndCentralDirLocator());
/*     */       }
/*     */       
/* 364 */       this.zipModel.getZip64EndCentralDirLocator().setNoOfDiskStartOfZip64EndOfCentralDirRec(currSplitFileCounter);
/* 365 */       this.zipModel.getZip64EndCentralDirLocator().setTotNumberOfDiscs(currSplitFileCounter + 1);
/*     */     } 
/* 367 */     this.zipModel.getEndCentralDirRecord().setNoOfThisDisk(currSplitFileCounter);
/* 368 */     this.zipModel.getEndCentralDirRecord().setNoOfThisDiskStartOfCentralDir(currSplitFileCounter);
/*     */     
/* 370 */     HeaderWriter headerWriter = new HeaderWriter();
/* 371 */     headerWriter.finalizeZipFile(this.zipModel, this.outputStream);
/*     */   }
/*     */   
/*     */   public void close() throws IOException {
/* 375 */     if (this.outputStream != null)
/* 376 */       this.outputStream.close(); 
/*     */   }
/*     */   
/*     */   private void createFileHeader() throws ZipException {
/* 380 */     this.fileHeader = new FileHeader();
/* 381 */     this.fileHeader.setSignature(33639248);
/* 382 */     this.fileHeader.setVersionMadeBy(20);
/* 383 */     this.fileHeader.setVersionNeededToExtract(20);
/* 384 */     if (this.zipParameters.isEncryptFiles() && this.zipParameters.getEncryptionMethod() == 99) {
/*     */       
/* 386 */       this.fileHeader.setCompressionMethod(99);
/* 387 */       this.fileHeader.setAesExtraDataRecord(generateAESExtraDataRecord(this.zipParameters));
/*     */     } else {
/* 389 */       this.fileHeader.setCompressionMethod(this.zipParameters.getCompressionMethod());
/*     */     } 
/* 391 */     if (this.zipParameters.isEncryptFiles()) {
/* 392 */       this.fileHeader.setEncrypted(true);
/* 393 */       this.fileHeader.setEncryptionMethod(this.zipParameters.getEncryptionMethod());
/*     */     } 
/* 395 */     String fileName = null;
/* 396 */     if (this.zipParameters.isSourceExternalStream()) {
/* 397 */       this.fileHeader.setLastModFileTime((int)Zip4jUtil.javaToDosTime(System.currentTimeMillis()));
/* 398 */       if (!Zip4jUtil.isStringNotNullAndNotEmpty(this.zipParameters.getFileNameInZip())) {
/* 399 */         throw new ZipException("fileNameInZip is null or empty");
/*     */       }
/* 401 */       fileName = this.zipParameters.getFileNameInZip();
/*     */     } else {
/* 403 */       this.fileHeader.setLastModFileTime((int)Zip4jUtil.javaToDosTime(Zip4jUtil.getLastModifiedFileTime(this.sourceFile, this.zipParameters.getTimeZone())));
/*     */       
/* 405 */       this.fileHeader.setUncompressedSize(this.sourceFile.length());
/* 406 */       fileName = Zip4jUtil.getRelativeFileName(this.sourceFile.getAbsolutePath(), this.zipParameters.getRootFolderInZip(), this.zipParameters.getDefaultFolderPath());
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 411 */     if (!Zip4jUtil.isStringNotNullAndNotEmpty(fileName)) {
/* 412 */       throw new ZipException("fileName is null or empty. unable to create file header");
/*     */     }
/*     */     
/* 415 */     this.fileHeader.setFileName(fileName);
/*     */     
/* 417 */     if (Zip4jUtil.isStringNotNullAndNotEmpty(this.zipModel.getFileNameCharset())) {
/* 418 */       this.fileHeader.setFileNameLength(Zip4jUtil.getEncodedStringLength(fileName, this.zipModel.getFileNameCharset()));
/*     */     } else {
/*     */       
/* 421 */       this.fileHeader.setFileNameLength(Zip4jUtil.getEncodedStringLength(fileName));
/*     */     } 
/*     */     
/* 424 */     if (this.outputStream instanceof SplitOutputStream) {
/* 425 */       this.fileHeader.setDiskNumberStart(((SplitOutputStream)this.outputStream).getCurrSplitFileCounter());
/*     */     } else {
/* 427 */       this.fileHeader.setDiskNumberStart(0);
/*     */     } 
/*     */     
/* 430 */     int fileAttrs = 0;
/* 431 */     if (!this.zipParameters.isSourceExternalStream())
/* 432 */       fileAttrs = getFileAttributes(this.sourceFile); 
/* 433 */     byte[] externalFileAttrs = { (byte)fileAttrs, 0, 0, 0 };
/* 434 */     this.fileHeader.setExternalFileAttr(externalFileAttrs);
/*     */     
/* 436 */     if (this.zipParameters.isSourceExternalStream()) {
/* 437 */       this.fileHeader.setDirectory((fileName.endsWith("/") || fileName.endsWith("\\")));
/*     */     } else {
/* 439 */       this.fileHeader.setDirectory(this.sourceFile.isDirectory());
/*     */     } 
/* 441 */     if (this.fileHeader.isDirectory()) {
/* 442 */       this.fileHeader.setCompressedSize(0L);
/* 443 */       this.fileHeader.setUncompressedSize(0L);
/*     */     }
/* 445 */     else if (!this.zipParameters.isSourceExternalStream()) {
/* 446 */       long fileSize = Zip4jUtil.getFileLengh(this.sourceFile);
/* 447 */       if (this.zipParameters.getCompressionMethod() == 0) {
/* 448 */         if (this.zipParameters.getEncryptionMethod() == 0) {
/* 449 */           this.fileHeader.setCompressedSize(fileSize + 12L);
/*     */         }
/* 451 */         else if (this.zipParameters.getEncryptionMethod() == 99) {
/* 452 */           int saltLength = 0;
/* 453 */           switch (this.zipParameters.getAesKeyStrength()) {
/*     */             case 1:
/* 455 */               saltLength = 8;
/*     */               break;
/*     */             case 3:
/* 458 */               saltLength = 16;
/*     */               break;
/*     */             default:
/* 461 */               throw new ZipException("invalid aes key strength, cannot determine key sizes");
/*     */           } 
/* 463 */           this.fileHeader.setCompressedSize(fileSize + saltLength + 10L + 2L);
/*     */         } else {
/*     */           
/* 466 */           this.fileHeader.setCompressedSize(0L);
/*     */         } 
/*     */       } else {
/* 469 */         this.fileHeader.setCompressedSize(0L);
/*     */       } 
/* 471 */       this.fileHeader.setUncompressedSize(fileSize);
/*     */     } 
/*     */     
/* 474 */     if (this.zipParameters.isEncryptFiles() && this.zipParameters.getEncryptionMethod() == 0)
/*     */     {
/* 476 */       this.fileHeader.setCrc32(this.zipParameters.getSourceFileCRC());
/*     */     }
/* 478 */     byte[] shortByte = new byte[2];
/* 479 */     shortByte[0] = Raw.bitArrayToByte(generateGeneralPurposeBitArray(this.fileHeader.isEncrypted(), this.zipParameters.getCompressionMethod()));
/*     */     
/* 481 */     if ((Zip4jUtil.isStringNotNullAndNotEmpty(this.zipModel.getFileNameCharset()) && this.zipModel.getFileNameCharset().equalsIgnoreCase("UTF8")) || Zip4jUtil.detectCharSet(this.fileHeader.getFileName()).equals("UTF8")) {
/*     */ 
/*     */       
/* 484 */       shortByte[1] = 8;
/*     */     } else {
/* 486 */       shortByte[1] = 0;
/*     */     } 
/* 488 */     this.fileHeader.setGeneralPurposeFlag(shortByte);
/*     */   }
/*     */   
/*     */   private void createLocalFileHeader() throws ZipException {
/* 492 */     if (this.fileHeader == null) {
/* 493 */       throw new ZipException("file header is null, cannot create local file header");
/*     */     }
/* 495 */     this.localFileHeader = new LocalFileHeader();
/* 496 */     this.localFileHeader.setSignature(67324752);
/* 497 */     this.localFileHeader.setVersionNeededToExtract(this.fileHeader.getVersionNeededToExtract());
/* 498 */     this.localFileHeader.setCompressionMethod(this.fileHeader.getCompressionMethod());
/* 499 */     this.localFileHeader.setLastModFileTime(this.fileHeader.getLastModFileTime());
/* 500 */     this.localFileHeader.setUncompressedSize(this.fileHeader.getUncompressedSize());
/* 501 */     this.localFileHeader.setFileNameLength(this.fileHeader.getFileNameLength());
/* 502 */     this.localFileHeader.setFileName(this.fileHeader.getFileName());
/* 503 */     this.localFileHeader.setEncrypted(this.fileHeader.isEncrypted());
/* 504 */     this.localFileHeader.setEncryptionMethod(this.fileHeader.getEncryptionMethod());
/* 505 */     this.localFileHeader.setAesExtraDataRecord(this.fileHeader.getAesExtraDataRecord());
/* 506 */     this.localFileHeader.setCrc32(this.fileHeader.getCrc32());
/* 507 */     this.localFileHeader.setCompressedSize(this.fileHeader.getCompressedSize());
/* 508 */     this.localFileHeader.setGeneralPurposeFlag((byte[])this.fileHeader.getGeneralPurposeFlag().clone());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private int getFileAttributes(File file) throws ZipException {
/* 518 */     if (file == null) {
/* 519 */       throw new ZipException("input file is null, cannot get file attributes");
/*     */     }
/*     */     
/* 522 */     if (!file.exists()) {
/* 523 */       return 0;
/*     */     }
/*     */     
/* 526 */     if (file.isDirectory()) {
/* 527 */       if (file.isHidden()) {
/* 528 */         return 18;
/*     */       }
/* 530 */       return 16;
/*     */     } 
/*     */     
/* 533 */     if (!file.canWrite() && file.isHidden())
/* 534 */       return 3; 
/* 535 */     if (!file.canWrite())
/* 536 */       return 1; 
/* 537 */     if (file.isHidden()) {
/* 538 */       return 2;
/*     */     }
/* 540 */     return 0;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private int[] generateGeneralPurposeBitArray(boolean isEncrpyted, int compressionMethod) {
/* 547 */     int[] generalPurposeBits = new int[8];
/* 548 */     if (isEncrpyted) {
/* 549 */       generalPurposeBits[0] = 1;
/*     */     } else {
/* 551 */       generalPurposeBits[0] = 0;
/*     */     } 
/*     */     
/* 554 */     if (compressionMethod != 8) {
/*     */ 
/*     */       
/* 557 */       generalPurposeBits[1] = 0;
/* 558 */       generalPurposeBits[2] = 0;
/*     */     } 
/*     */     
/* 561 */     if (this.outputStream instanceof SplitOutputStream) {
/* 562 */       generalPurposeBits[3] = 0;
/*     */     } else {
/* 564 */       generalPurposeBits[3] = 1;
/*     */     } 
/* 566 */     return generalPurposeBits;
/*     */   }
/*     */ 
/*     */   
/*     */   private AESExtraDataRecord generateAESExtraDataRecord(ZipParameters parameters) throws ZipException {
/* 571 */     if (parameters == null) {
/* 572 */       throw new ZipException("zip parameters are null, cannot generate AES Extra Data record");
/*     */     }
/*     */     
/* 575 */     AESExtraDataRecord aesDataRecord = new AESExtraDataRecord();
/* 576 */     aesDataRecord.setSignature(39169L);
/* 577 */     aesDataRecord.setDataSize(7);
/* 578 */     aesDataRecord.setVendorID("AE");
/*     */ 
/*     */ 
/*     */     
/* 582 */     aesDataRecord.setVersionNumber(2);
/* 583 */     if (parameters.getAesKeyStrength() == 1) {
/* 584 */       aesDataRecord.setAesStrength(1);
/* 585 */     } else if (parameters.getAesKeyStrength() == 3) {
/* 586 */       aesDataRecord.setAesStrength(3);
/*     */     } else {
/* 588 */       throw new ZipException("invalid AES key strength, cannot generate AES Extra data record");
/*     */     } 
/* 590 */     aesDataRecord.setCompressionMethod(parameters.getCompressionMethod());
/*     */     
/* 592 */     return aesDataRecord;
/*     */   }
/*     */   
/*     */   public void setSourceFile(File sourceFile) {
/* 596 */     this.sourceFile = sourceFile;
/*     */   }
/*     */   
/*     */   public File getSourceFile() {
/* 600 */     return this.sourceFile;
/*     */   }
/*     */ }


/* Location:              C:\Users\DuduM\OneDrive\Área de Trabalho\mw10\modularwarfare-shining-2023.2.4.4f-fix10.jar!\net\lingala\zip4j\io\CipherOutputStream.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */