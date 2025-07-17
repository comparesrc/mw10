/*     */ package net.lingala.zip4j.core;
/*     */ 
/*     */ import java.io.File;
/*     */ import java.io.IOException;
/*     */ import java.io.OutputStream;
/*     */ import java.util.ArrayList;
/*     */ import net.lingala.zip4j.exception.ZipException;
/*     */ import net.lingala.zip4j.io.SplitOutputStream;
/*     */ import net.lingala.zip4j.model.AESExtraDataRecord;
/*     */ import net.lingala.zip4j.model.FileHeader;
/*     */ import net.lingala.zip4j.model.LocalFileHeader;
/*     */ import net.lingala.zip4j.model.Zip64EndCentralDirLocator;
/*     */ import net.lingala.zip4j.model.Zip64EndCentralDirRecord;
/*     */ import net.lingala.zip4j.model.ZipModel;
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
/*     */ public class HeaderWriter
/*     */ {
/*  38 */   private final int ZIP64_EXTRA_BUF = 50;
/*     */ 
/*     */   
/*     */   public int writeLocalFileHeader(ZipModel zipModel, LocalFileHeader localFileHeader, OutputStream outputStream) throws ZipException {
/*  42 */     if (localFileHeader == null) {
/*  43 */       throw new ZipException("input parameters are null, cannot write local file header");
/*     */     }
/*     */     
/*     */     try {
/*  47 */       ArrayList byteArrayList = new ArrayList();
/*     */       
/*  49 */       int headerLength = 0;
/*     */       
/*  51 */       byte[] shortByte = new byte[2];
/*  52 */       byte[] intByte = new byte[4];
/*  53 */       byte[] longByte = new byte[8];
/*  54 */       byte[] emptyLongByte = { 0, 0, 0, 0, 0, 0, 0, 0 };
/*     */       
/*  56 */       Raw.writeIntLittleEndian(intByte, 0, localFileHeader.getSignature());
/*  57 */       copyByteArrayToArrayList(intByte, byteArrayList);
/*  58 */       headerLength += 4;
/*     */       
/*  60 */       Raw.writeShortLittleEndian(shortByte, 0, (short)localFileHeader.getVersionNeededToExtract());
/*  61 */       copyByteArrayToArrayList(shortByte, byteArrayList);
/*  62 */       headerLength += 2;
/*     */ 
/*     */       
/*  65 */       copyByteArrayToArrayList(localFileHeader.getGeneralPurposeFlag(), byteArrayList);
/*  66 */       headerLength += 2;
/*     */ 
/*     */       
/*  69 */       Raw.writeShortLittleEndian(shortByte, 0, (short)localFileHeader.getCompressionMethod());
/*  70 */       copyByteArrayToArrayList(shortByte, byteArrayList);
/*  71 */       headerLength += 2;
/*     */ 
/*     */       
/*  74 */       int dateTime = localFileHeader.getLastModFileTime();
/*  75 */       Raw.writeIntLittleEndian(intByte, 0, dateTime);
/*  76 */       copyByteArrayToArrayList(intByte, byteArrayList);
/*  77 */       headerLength += 4;
/*     */ 
/*     */       
/*  80 */       Raw.writeIntLittleEndian(intByte, 0, (int)localFileHeader.getCrc32());
/*  81 */       copyByteArrayToArrayList(intByte, byteArrayList);
/*  82 */       headerLength += 4;
/*     */       
/*  84 */       boolean writingZip64Rec = false;
/*     */ 
/*     */       
/*  87 */       long uncompressedSize = localFileHeader.getUncompressedSize();
/*  88 */       if (uncompressedSize + 50L >= 4294967295L) {
/*  89 */         Raw.writeLongLittleEndian(longByte, 0, 4294967295L);
/*  90 */         System.arraycopy(longByte, 0, intByte, 0, 4);
/*     */ 
/*     */ 
/*     */         
/*  94 */         copyByteArrayToArrayList(intByte, byteArrayList);
/*     */         
/*  96 */         copyByteArrayToArrayList(intByte, byteArrayList);
/*  97 */         zipModel.setZip64Format(true);
/*  98 */         writingZip64Rec = true;
/*  99 */         localFileHeader.setWriteComprSizeInZip64ExtraRecord(true);
/*     */       } else {
/* 101 */         Raw.writeLongLittleEndian(longByte, 0, localFileHeader.getCompressedSize());
/* 102 */         System.arraycopy(longByte, 0, intByte, 0, 4);
/* 103 */         copyByteArrayToArrayList(intByte, byteArrayList);
/*     */         
/* 105 */         Raw.writeLongLittleEndian(longByte, 0, localFileHeader.getUncompressedSize());
/* 106 */         System.arraycopy(longByte, 0, intByte, 0, 4);
/*     */         
/* 108 */         copyByteArrayToArrayList(intByte, byteArrayList);
/*     */         
/* 110 */         localFileHeader.setWriteComprSizeInZip64ExtraRecord(false);
/*     */       } 
/* 112 */       headerLength += 8;
/*     */       
/* 114 */       Raw.writeShortLittleEndian(shortByte, 0, (short)localFileHeader.getFileNameLength());
/* 115 */       copyByteArrayToArrayList(shortByte, byteArrayList);
/* 116 */       headerLength += 2;
/*     */ 
/*     */       
/* 119 */       int extraFieldLength = 0;
/* 120 */       if (writingZip64Rec) {
/* 121 */         extraFieldLength += 20;
/*     */       }
/* 123 */       if (localFileHeader.getAesExtraDataRecord() != null) {
/* 124 */         extraFieldLength += 11;
/*     */       }
/* 126 */       Raw.writeShortLittleEndian(shortByte, 0, (short)extraFieldLength);
/* 127 */       copyByteArrayToArrayList(shortByte, byteArrayList);
/* 128 */       headerLength += 2;
/*     */       
/* 130 */       if (Zip4jUtil.isStringNotNullAndNotEmpty(zipModel.getFileNameCharset())) {
/* 131 */         byte[] fileNameBytes = localFileHeader.getFileName().getBytes(zipModel.getFileNameCharset());
/* 132 */         copyByteArrayToArrayList(fileNameBytes, byteArrayList);
/* 133 */         headerLength += fileNameBytes.length;
/*     */       } else {
/* 135 */         copyByteArrayToArrayList(Zip4jUtil.convertCharset(localFileHeader.getFileName()), byteArrayList);
/* 136 */         headerLength += Zip4jUtil.getEncodedStringLength(localFileHeader.getFileName());
/*     */       } 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 142 */       if (writingZip64Rec) {
/*     */ 
/*     */ 
/*     */         
/* 146 */         Raw.writeShortLittleEndian(shortByte, 0, (short)1);
/* 147 */         copyByteArrayToArrayList(shortByte, byteArrayList);
/* 148 */         headerLength += 2;
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 153 */         Raw.writeShortLittleEndian(shortByte, 0, (short)16);
/* 154 */         copyByteArrayToArrayList(shortByte, byteArrayList);
/* 155 */         headerLength += 2;
/*     */ 
/*     */         
/* 158 */         Raw.writeLongLittleEndian(longByte, 0, localFileHeader.getUncompressedSize());
/* 159 */         copyByteArrayToArrayList(longByte, byteArrayList);
/* 160 */         headerLength += 8;
/*     */ 
/*     */         
/* 163 */         copyByteArrayToArrayList(emptyLongByte, byteArrayList);
/* 164 */         headerLength += 8;
/*     */       } 
/*     */       
/* 167 */       if (localFileHeader.getAesExtraDataRecord() != null) {
/* 168 */         AESExtraDataRecord aesExtraDataRecord = localFileHeader.getAesExtraDataRecord();
/*     */         
/* 170 */         Raw.writeShortLittleEndian(shortByte, 0, (short)(int)aesExtraDataRecord.getSignature());
/* 171 */         copyByteArrayToArrayList(shortByte, byteArrayList);
/*     */         
/* 173 */         Raw.writeShortLittleEndian(shortByte, 0, (short)aesExtraDataRecord.getDataSize());
/* 174 */         copyByteArrayToArrayList(shortByte, byteArrayList);
/*     */         
/* 176 */         Raw.writeShortLittleEndian(shortByte, 0, (short)aesExtraDataRecord.getVersionNumber());
/* 177 */         copyByteArrayToArrayList(shortByte, byteArrayList);
/*     */         
/* 179 */         copyByteArrayToArrayList(aesExtraDataRecord.getVendorID().getBytes(), byteArrayList);
/*     */         
/* 181 */         byte[] aesStrengthBytes = new byte[1];
/* 182 */         aesStrengthBytes[0] = (byte)aesExtraDataRecord.getAesStrength();
/* 183 */         copyByteArrayToArrayList(aesStrengthBytes, byteArrayList);
/*     */         
/* 185 */         Raw.writeShortLittleEndian(shortByte, 0, (short)aesExtraDataRecord.getCompressionMethod());
/* 186 */         copyByteArrayToArrayList(shortByte, byteArrayList);
/*     */       } 
/* 188 */       byte[] lhBytes = byteArrayListToByteArray(byteArrayList);
/* 189 */       outputStream.write(lhBytes);
/* 190 */       return lhBytes.length;
/* 191 */     } catch (Exception e) {
/* 192 */       throw new ZipException(e);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public int writeExtendedLocalHeader(LocalFileHeader localFileHeader, OutputStream outputStream) throws ZipException, IOException {
/* 198 */     if (localFileHeader == null || outputStream == null) {
/* 199 */       throw new ZipException("input parameters is null, cannot write extended local header");
/*     */     }
/*     */     
/* 202 */     ArrayList byteArrayList = new ArrayList();
/* 203 */     byte[] intByte = new byte[4];
/*     */ 
/*     */     
/* 206 */     Raw.writeIntLittleEndian(intByte, 0, 134695760);
/* 207 */     copyByteArrayToArrayList(intByte, byteArrayList);
/*     */ 
/*     */     
/* 210 */     Raw.writeIntLittleEndian(intByte, 0, (int)localFileHeader.getCrc32());
/* 211 */     copyByteArrayToArrayList(intByte, byteArrayList);
/*     */ 
/*     */     
/* 214 */     long compressedSize = localFileHeader.getCompressedSize();
/* 215 */     if (compressedSize >= 2147483647L) {
/* 216 */       compressedSize = 2147483647L;
/*     */     }
/* 218 */     Raw.writeIntLittleEndian(intByte, 0, (int)compressedSize);
/* 219 */     copyByteArrayToArrayList(intByte, byteArrayList);
/*     */ 
/*     */     
/* 222 */     long uncompressedSize = localFileHeader.getUncompressedSize();
/* 223 */     if (uncompressedSize >= 2147483647L) {
/* 224 */       uncompressedSize = 2147483647L;
/*     */     }
/* 226 */     Raw.writeIntLittleEndian(intByte, 0, (int)uncompressedSize);
/* 227 */     copyByteArrayToArrayList(intByte, byteArrayList);
/*     */     
/* 229 */     byte[] extLocHdrBytes = byteArrayListToByteArray(byteArrayList);
/* 230 */     outputStream.write(extLocHdrBytes);
/* 231 */     return extLocHdrBytes.length;
/*     */   }
/*     */ 
/*     */   
/*     */   public void finalizeZipFile(ZipModel zipModel, OutputStream outputStream) throws ZipException {
/* 236 */     if (zipModel == null || outputStream == null) {
/* 237 */       throw new ZipException("input parameters is null, cannot finalize zip file");
/*     */     }
/*     */     
/*     */     try {
/* 241 */       long offsetCentralDir = zipModel.getEndCentralDirRecord().getOffsetOfStartOfCentralDir();
/*     */       
/* 243 */       int sizeOfCentralDir = writeCentralDirectory(zipModel, outputStream);
/*     */       
/* 245 */       if (zipModel.isZip64Format()) {
/* 246 */         if (zipModel.getZip64EndCentralDirRecord() == null) {
/* 247 */           zipModel.setZip64EndCentralDirRecord(new Zip64EndCentralDirRecord());
/*     */         }
/* 249 */         if (zipModel.getZip64EndCentralDirLocator() == null) {
/* 250 */           zipModel.setZip64EndCentralDirLocator(new Zip64EndCentralDirLocator());
/*     */         }
/*     */         
/* 253 */         zipModel.getZip64EndCentralDirLocator().setOffsetZip64EndOfCentralDirRec(offsetCentralDir + sizeOfCentralDir);
/* 254 */         if (outputStream instanceof SplitOutputStream) {
/* 255 */           zipModel.getZip64EndCentralDirLocator().setNoOfDiskStartOfZip64EndOfCentralDirRec(((SplitOutputStream)outputStream).getCurrSplitFileCounter());
/* 256 */           zipModel.getZip64EndCentralDirLocator().setTotNumberOfDiscs(((SplitOutputStream)outputStream).getCurrSplitFileCounter() + 1);
/*     */         } else {
/* 258 */           zipModel.getZip64EndCentralDirLocator().setNoOfDiskStartOfZip64EndOfCentralDirRec(0);
/* 259 */           zipModel.getZip64EndCentralDirLocator().setTotNumberOfDiscs(1);
/*     */         } 
/*     */         
/* 262 */         writeZip64EndOfCentralDirectoryRecord(zipModel, outputStream, sizeOfCentralDir, offsetCentralDir);
/*     */         
/* 264 */         writeZip64EndOfCentralDirectoryLocator(zipModel, outputStream);
/*     */       } 
/*     */       
/* 267 */       writeEndOfCentralDirectoryRecord(zipModel, outputStream, sizeOfCentralDir, offsetCentralDir);
/* 268 */     } catch (Exception e) {
/* 269 */       throw new ZipException(e);
/*     */     } 
/*     */   }
/*     */   
/*     */   public void finalizeZipFileWithoutValidations(ZipModel zipModel, OutputStream outputStream) throws ZipException {
/* 274 */     if (zipModel == null || outputStream == null) {
/* 275 */       throw new ZipException("input parameters is null, cannot finalize zip file without validations");
/*     */     }
/*     */     
/* 278 */     long offsetCentralDir = zipModel.getEndCentralDirRecord().getOffsetOfStartOfCentralDir();
/*     */     
/* 280 */     int sizeOfCentralDir = writeCentralDirectory(zipModel, outputStream);
/*     */     
/* 282 */     if (zipModel.isZip64Format()) {
/* 283 */       if (zipModel.getZip64EndCentralDirRecord() == null) {
/* 284 */         zipModel.setZip64EndCentralDirRecord(new Zip64EndCentralDirRecord());
/*     */       }
/* 286 */       if (zipModel.getZip64EndCentralDirLocator() == null) {
/* 287 */         zipModel.setZip64EndCentralDirLocator(new Zip64EndCentralDirLocator());
/*     */       }
/*     */       
/* 290 */       zipModel.getZip64EndCentralDirLocator().setOffsetZip64EndOfCentralDirRec(offsetCentralDir + sizeOfCentralDir);
/*     */       
/* 292 */       writeZip64EndOfCentralDirectoryRecord(zipModel, outputStream, sizeOfCentralDir, offsetCentralDir);
/* 293 */       writeZip64EndOfCentralDirectoryLocator(zipModel, outputStream);
/*     */     } 
/*     */     
/* 296 */     writeEndOfCentralDirectoryRecord(zipModel, outputStream, sizeOfCentralDir, offsetCentralDir);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private int writeCentralDirectory(ZipModel zipModel, OutputStream outputStream) throws ZipException {
/* 302 */     if (zipModel == null || outputStream == null) {
/* 303 */       throw new ZipException("input parameters is null, cannot write central directory");
/*     */     }
/*     */     
/* 306 */     if (zipModel.getCentralDirectory() == null || zipModel.getCentralDirectory().getFileHeaders() == null || zipModel.getCentralDirectory().getFileHeaders().size() <= 0)
/*     */     {
/*     */       
/* 309 */       return 0;
/*     */     }
/*     */     
/* 312 */     int sizeOfCentralDir = 0;
/* 313 */     for (int i = 0; i < zipModel.getCentralDirectory().getFileHeaders().size(); i++) {
/* 314 */       FileHeader fileHeader = zipModel.getCentralDirectory().getFileHeaders().get(i);
/* 315 */       int sizeOfFileHeader = writeFileHeader(zipModel, fileHeader, outputStream);
/* 316 */       sizeOfCentralDir += sizeOfFileHeader;
/*     */     } 
/* 318 */     return sizeOfCentralDir;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private int writeFileHeader(ZipModel zipModel, FileHeader fileHeader, OutputStream outputStream) throws ZipException {
/* 324 */     if (fileHeader == null || outputStream == null) {
/* 325 */       throw new ZipException("input parameters is null, cannot write local file header");
/*     */     }
/*     */     
/*     */     try {
/* 329 */       int sizeOfFileHeader = 0;
/*     */       
/* 331 */       ArrayList byteArrayList = new ArrayList();
/*     */       
/* 333 */       byte[] shortByte = new byte[2];
/* 334 */       byte[] intByte = new byte[4];
/* 335 */       byte[] longByte = new byte[8];
/* 336 */       byte[] emptyShortByte = { 0, 0 };
/* 337 */       byte[] emptyIntByte = { 0, 0, 0, 0 };
/*     */       
/* 339 */       boolean writeZip64FileSize = false;
/* 340 */       boolean writeZip64OffsetLocalHeader = false;
/*     */       
/* 342 */       Raw.writeIntLittleEndian(intByte, 0, fileHeader.getSignature());
/* 343 */       copyByteArrayToArrayList(intByte, byteArrayList);
/* 344 */       sizeOfFileHeader += 4;
/*     */       
/* 346 */       Raw.writeShortLittleEndian(shortByte, 0, (short)fileHeader.getVersionMadeBy());
/* 347 */       copyByteArrayToArrayList(shortByte, byteArrayList);
/* 348 */       sizeOfFileHeader += 2;
/*     */       
/* 350 */       Raw.writeShortLittleEndian(shortByte, 0, (short)fileHeader.getVersionNeededToExtract());
/* 351 */       copyByteArrayToArrayList(shortByte, byteArrayList);
/* 352 */       sizeOfFileHeader += 2;
/*     */       
/* 354 */       copyByteArrayToArrayList(fileHeader.getGeneralPurposeFlag(), byteArrayList);
/* 355 */       sizeOfFileHeader += 2;
/*     */       
/* 357 */       Raw.writeShortLittleEndian(shortByte, 0, (short)fileHeader.getCompressionMethod());
/* 358 */       copyByteArrayToArrayList(shortByte, byteArrayList);
/* 359 */       sizeOfFileHeader += 2;
/*     */       
/* 361 */       int dateTime = fileHeader.getLastModFileTime();
/* 362 */       Raw.writeIntLittleEndian(intByte, 0, dateTime);
/* 363 */       copyByteArrayToArrayList(intByte, byteArrayList);
/* 364 */       sizeOfFileHeader += 4;
/*     */       
/* 366 */       Raw.writeIntLittleEndian(intByte, 0, (int)fileHeader.getCrc32());
/* 367 */       copyByteArrayToArrayList(intByte, byteArrayList);
/* 368 */       sizeOfFileHeader += 4;
/*     */       
/* 370 */       if (fileHeader.getCompressedSize() >= 4294967295L || fileHeader.getUncompressedSize() + 50L >= 4294967295L) {
/*     */         
/* 372 */         Raw.writeLongLittleEndian(longByte, 0, 4294967295L);
/* 373 */         System.arraycopy(longByte, 0, intByte, 0, 4);
/*     */         
/* 375 */         copyByteArrayToArrayList(intByte, byteArrayList);
/* 376 */         sizeOfFileHeader += 4;
/*     */         
/* 378 */         copyByteArrayToArrayList(intByte, byteArrayList);
/* 379 */         sizeOfFileHeader += 4;
/*     */         
/* 381 */         writeZip64FileSize = true;
/*     */       } else {
/* 383 */         Raw.writeLongLittleEndian(longByte, 0, fileHeader.getCompressedSize());
/* 384 */         System.arraycopy(longByte, 0, intByte, 0, 4);
/*     */         
/* 386 */         copyByteArrayToArrayList(intByte, byteArrayList);
/* 387 */         sizeOfFileHeader += 4;
/*     */         
/* 389 */         Raw.writeLongLittleEndian(longByte, 0, fileHeader.getUncompressedSize());
/* 390 */         System.arraycopy(longByte, 0, intByte, 0, 4);
/*     */         
/* 392 */         copyByteArrayToArrayList(intByte, byteArrayList);
/* 393 */         sizeOfFileHeader += 4;
/*     */       } 
/*     */       
/* 396 */       Raw.writeShortLittleEndian(shortByte, 0, (short)fileHeader.getFileNameLength());
/* 397 */       copyByteArrayToArrayList(shortByte, byteArrayList);
/* 398 */       sizeOfFileHeader += 2;
/*     */ 
/*     */ 
/*     */       
/* 402 */       byte[] offsetLocalHeaderBytes = new byte[4];
/* 403 */       if (fileHeader.getOffsetLocalHeader() > 4294967295L) {
/* 404 */         Raw.writeLongLittleEndian(longByte, 0, 4294967295L);
/* 405 */         System.arraycopy(longByte, 0, offsetLocalHeaderBytes, 0, 4);
/* 406 */         writeZip64OffsetLocalHeader = true;
/*     */       } else {
/* 408 */         Raw.writeLongLittleEndian(longByte, 0, fileHeader.getOffsetLocalHeader());
/* 409 */         System.arraycopy(longByte, 0, offsetLocalHeaderBytes, 0, 4);
/*     */       } 
/*     */ 
/*     */       
/* 413 */       int extraFieldLength = 0;
/* 414 */       if (writeZip64FileSize || writeZip64OffsetLocalHeader) {
/* 415 */         extraFieldLength += 4;
/* 416 */         if (writeZip64FileSize)
/* 417 */           extraFieldLength += 16; 
/* 418 */         if (writeZip64OffsetLocalHeader)
/* 419 */           extraFieldLength += 8; 
/*     */       } 
/* 421 */       if (fileHeader.getAesExtraDataRecord() != null) {
/* 422 */         extraFieldLength += 11;
/*     */       }
/* 424 */       Raw.writeShortLittleEndian(shortByte, 0, (short)extraFieldLength);
/* 425 */       copyByteArrayToArrayList(shortByte, byteArrayList);
/* 426 */       sizeOfFileHeader += 2;
/*     */ 
/*     */       
/* 429 */       copyByteArrayToArrayList(emptyShortByte, byteArrayList);
/* 430 */       sizeOfFileHeader += 2;
/*     */ 
/*     */       
/* 433 */       Raw.writeShortLittleEndian(shortByte, 0, (short)fileHeader.getDiskNumberStart());
/* 434 */       copyByteArrayToArrayList(shortByte, byteArrayList);
/* 435 */       sizeOfFileHeader += 2;
/*     */ 
/*     */       
/* 438 */       copyByteArrayToArrayList(emptyShortByte, byteArrayList);
/* 439 */       sizeOfFileHeader += 2;
/*     */ 
/*     */       
/* 442 */       if (fileHeader.getExternalFileAttr() != null) {
/* 443 */         copyByteArrayToArrayList(fileHeader.getExternalFileAttr(), byteArrayList);
/*     */       } else {
/* 445 */         copyByteArrayToArrayList(emptyIntByte, byteArrayList);
/*     */       } 
/* 447 */       sizeOfFileHeader += 4;
/*     */ 
/*     */ 
/*     */       
/* 451 */       copyByteArrayToArrayList(offsetLocalHeaderBytes, byteArrayList);
/* 452 */       sizeOfFileHeader += 4;
/*     */       
/* 454 */       if (Zip4jUtil.isStringNotNullAndNotEmpty(zipModel.getFileNameCharset())) {
/* 455 */         byte[] fileNameBytes = fileHeader.getFileName().getBytes(zipModel.getFileNameCharset());
/* 456 */         copyByteArrayToArrayList(fileNameBytes, byteArrayList);
/* 457 */         sizeOfFileHeader += fileNameBytes.length;
/*     */       } else {
/* 459 */         copyByteArrayToArrayList(Zip4jUtil.convertCharset(fileHeader.getFileName()), byteArrayList);
/* 460 */         sizeOfFileHeader += Zip4jUtil.getEncodedStringLength(fileHeader.getFileName());
/*     */       } 
/*     */       
/* 463 */       if (writeZip64FileSize || writeZip64OffsetLocalHeader) {
/* 464 */         zipModel.setZip64Format(true);
/*     */ 
/*     */         
/* 467 */         Raw.writeShortLittleEndian(shortByte, 0, (short)1);
/* 468 */         copyByteArrayToArrayList(shortByte, byteArrayList);
/* 469 */         sizeOfFileHeader += 2;
/*     */ 
/*     */         
/* 472 */         int dataSize = 0;
/*     */         
/* 474 */         if (writeZip64FileSize) {
/* 475 */           dataSize += 16;
/*     */         }
/* 477 */         if (writeZip64OffsetLocalHeader) {
/* 478 */           dataSize += 8;
/*     */         }
/*     */         
/* 481 */         Raw.writeShortLittleEndian(shortByte, 0, (short)dataSize);
/* 482 */         copyByteArrayToArrayList(shortByte, byteArrayList);
/* 483 */         sizeOfFileHeader += 2;
/*     */         
/* 485 */         if (writeZip64FileSize) {
/* 486 */           Raw.writeLongLittleEndian(longByte, 0, fileHeader.getUncompressedSize());
/* 487 */           copyByteArrayToArrayList(longByte, byteArrayList);
/* 488 */           sizeOfFileHeader += 8;
/*     */           
/* 490 */           Raw.writeLongLittleEndian(longByte, 0, fileHeader.getCompressedSize());
/* 491 */           copyByteArrayToArrayList(longByte, byteArrayList);
/* 492 */           sizeOfFileHeader += 8;
/*     */         } 
/*     */         
/* 495 */         if (writeZip64OffsetLocalHeader) {
/* 496 */           Raw.writeLongLittleEndian(longByte, 0, fileHeader.getOffsetLocalHeader());
/* 497 */           copyByteArrayToArrayList(longByte, byteArrayList);
/* 498 */           sizeOfFileHeader += 8;
/*     */         } 
/*     */       } 
/*     */       
/* 502 */       if (fileHeader.getAesExtraDataRecord() != null) {
/* 503 */         AESExtraDataRecord aesExtraDataRecord = fileHeader.getAesExtraDataRecord();
/*     */         
/* 505 */         Raw.writeShortLittleEndian(shortByte, 0, (short)(int)aesExtraDataRecord.getSignature());
/* 506 */         copyByteArrayToArrayList(shortByte, byteArrayList);
/*     */         
/* 508 */         Raw.writeShortLittleEndian(shortByte, 0, (short)aesExtraDataRecord.getDataSize());
/* 509 */         copyByteArrayToArrayList(shortByte, byteArrayList);
/*     */         
/* 511 */         Raw.writeShortLittleEndian(shortByte, 0, (short)aesExtraDataRecord.getVersionNumber());
/* 512 */         copyByteArrayToArrayList(shortByte, byteArrayList);
/*     */         
/* 514 */         copyByteArrayToArrayList(aesExtraDataRecord.getVendorID().getBytes(), byteArrayList);
/*     */         
/* 516 */         byte[] aesStrengthBytes = new byte[1];
/* 517 */         aesStrengthBytes[0] = (byte)aesExtraDataRecord.getAesStrength();
/* 518 */         copyByteArrayToArrayList(aesStrengthBytes, byteArrayList);
/*     */         
/* 520 */         Raw.writeShortLittleEndian(shortByte, 0, (short)aesExtraDataRecord.getCompressionMethod());
/* 521 */         copyByteArrayToArrayList(shortByte, byteArrayList);
/*     */         
/* 523 */         sizeOfFileHeader += 11;
/*     */       } 
/*     */       
/* 526 */       outputStream.write(byteArrayListToByteArray(byteArrayList));
/*     */       
/* 528 */       return sizeOfFileHeader;
/* 529 */     } catch (Exception e) {
/* 530 */       throw new ZipException(e);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private void writeZip64EndOfCentralDirectoryRecord(ZipModel zipModel, OutputStream outputStream, int sizeOfCentralDir, long offsetCentralDir) throws ZipException {
/* 537 */     if (zipModel == null || outputStream == null) {
/* 538 */       throw new ZipException("zip model or output stream is null, cannot write zip64 end of central directory record");
/*     */     }
/*     */     
/*     */     try {
/* 542 */       ArrayList byteArrayList = new ArrayList();
/*     */       
/* 544 */       byte[] shortByte = new byte[2];
/* 545 */       byte[] emptyShortByte = { 0, 0 };
/* 546 */       byte[] intByte = new byte[4];
/* 547 */       byte[] longByte = new byte[8];
/*     */ 
/*     */       
/* 550 */       Raw.writeIntLittleEndian(intByte, 0, 101075792);
/* 551 */       copyByteArrayToArrayList(intByte, byteArrayList);
/*     */ 
/*     */       
/* 554 */       Raw.writeLongLittleEndian(longByte, 0, 44L);
/* 555 */       copyByteArrayToArrayList(longByte, byteArrayList);
/*     */ 
/*     */ 
/*     */       
/* 559 */       if (zipModel.getCentralDirectory() != null && zipModel.getCentralDirectory().getFileHeaders() != null && zipModel.getCentralDirectory().getFileHeaders().size() > 0) {
/*     */ 
/*     */         
/* 562 */         Raw.writeShortLittleEndian(shortByte, 0, (short)((FileHeader)zipModel.getCentralDirectory().getFileHeaders().get(0)).getVersionMadeBy());
/*     */         
/* 564 */         copyByteArrayToArrayList(shortByte, byteArrayList);
/*     */         
/* 566 */         Raw.writeShortLittleEndian(shortByte, 0, (short)((FileHeader)zipModel.getCentralDirectory().getFileHeaders().get(0)).getVersionNeededToExtract());
/*     */         
/* 568 */         copyByteArrayToArrayList(shortByte, byteArrayList);
/*     */       } else {
/* 570 */         copyByteArrayToArrayList(emptyShortByte, byteArrayList);
/* 571 */         copyByteArrayToArrayList(emptyShortByte, byteArrayList);
/*     */       } 
/*     */ 
/*     */       
/* 575 */       Raw.writeIntLittleEndian(intByte, 0, zipModel.getEndCentralDirRecord().getNoOfThisDisk());
/* 576 */       copyByteArrayToArrayList(intByte, byteArrayList);
/*     */ 
/*     */       
/* 579 */       Raw.writeIntLittleEndian(intByte, 0, zipModel.getEndCentralDirRecord().getNoOfThisDiskStartOfCentralDir());
/* 580 */       copyByteArrayToArrayList(intByte, byteArrayList);
/*     */ 
/*     */       
/* 583 */       int numEntries = 0;
/* 584 */       int numEntriesOnThisDisk = 0;
/* 585 */       if (zipModel.getCentralDirectory() == null || zipModel.getCentralDirectory().getFileHeaders() == null)
/*     */       {
/* 587 */         throw new ZipException("invalid central directory/file headers, cannot write end of central directory record");
/*     */       }
/*     */       
/* 590 */       numEntries = zipModel.getCentralDirectory().getFileHeaders().size();
/* 591 */       if (zipModel.isSplitArchive()) {
/* 592 */         countNumberOfFileHeaderEntriesOnDisk(zipModel.getCentralDirectory().getFileHeaders(), zipModel.getEndCentralDirRecord().getNoOfThisDisk());
/*     */       } else {
/*     */         
/* 595 */         numEntriesOnThisDisk = numEntries;
/*     */       } 
/*     */       
/* 598 */       Raw.writeLongLittleEndian(longByte, 0, numEntriesOnThisDisk);
/* 599 */       copyByteArrayToArrayList(longByte, byteArrayList);
/*     */ 
/*     */       
/* 602 */       Raw.writeLongLittleEndian(longByte, 0, numEntries);
/* 603 */       copyByteArrayToArrayList(longByte, byteArrayList);
/*     */ 
/*     */       
/* 606 */       Raw.writeLongLittleEndian(longByte, 0, sizeOfCentralDir);
/* 607 */       copyByteArrayToArrayList(longByte, byteArrayList);
/*     */ 
/*     */       
/* 610 */       Raw.writeLongLittleEndian(longByte, 0, offsetCentralDir);
/* 611 */       copyByteArrayToArrayList(longByte, byteArrayList);
/*     */       
/* 613 */       outputStream.write(byteArrayListToByteArray(byteArrayList));
/* 614 */     } catch (ZipException zipException) {
/* 615 */       throw zipException;
/* 616 */     } catch (Exception e) {
/* 617 */       throw new ZipException(e);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private void writeZip64EndOfCentralDirectoryLocator(ZipModel zipModel, OutputStream outputStream) throws ZipException {
/* 623 */     if (zipModel == null || outputStream == null) {
/* 624 */       throw new ZipException("zip model or output stream is null, cannot write zip64 end of central directory locator");
/*     */     }
/*     */     
/*     */     try {
/* 628 */       ArrayList byteArrayList = new ArrayList();
/*     */       
/* 630 */       byte[] intByte = new byte[4];
/* 631 */       byte[] longByte = new byte[8];
/*     */ 
/*     */       
/* 634 */       Raw.writeIntLittleEndian(intByte, 0, 117853008);
/* 635 */       copyByteArrayToArrayList(intByte, byteArrayList);
/*     */ 
/*     */       
/* 638 */       Raw.writeIntLittleEndian(intByte, 0, zipModel.getZip64EndCentralDirLocator().getNoOfDiskStartOfZip64EndOfCentralDirRec());
/* 639 */       copyByteArrayToArrayList(intByte, byteArrayList);
/*     */ 
/*     */       
/* 642 */       Raw.writeLongLittleEndian(longByte, 0, zipModel.getZip64EndCentralDirLocator().getOffsetZip64EndOfCentralDirRec());
/* 643 */       copyByteArrayToArrayList(longByte, byteArrayList);
/*     */ 
/*     */       
/* 646 */       Raw.writeIntLittleEndian(intByte, 0, zipModel.getZip64EndCentralDirLocator().getTotNumberOfDiscs());
/* 647 */       copyByteArrayToArrayList(intByte, byteArrayList);
/*     */       
/* 649 */       outputStream.write(byteArrayListToByteArray(byteArrayList));
/* 650 */     } catch (ZipException zipException) {
/* 651 */       throw zipException;
/* 652 */     } catch (Exception e) {
/* 653 */       throw new ZipException(e);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void writeEndOfCentralDirectoryRecord(ZipModel zipModel, OutputStream outputStream, int sizeOfCentralDir, long offsetCentralDir) throws ZipException {
/* 661 */     if (zipModel == null || outputStream == null) {
/* 662 */       throw new ZipException("zip model or output stream is null, cannot write end of central directory record");
/*     */     }
/*     */ 
/*     */     
/*     */     try {
/* 667 */       ArrayList byteArrayList = new ArrayList();
/*     */       
/* 669 */       byte[] shortByte = new byte[2];
/* 670 */       byte[] intByte = new byte[4];
/* 671 */       byte[] longByte = new byte[8];
/*     */ 
/*     */       
/* 674 */       Raw.writeIntLittleEndian(intByte, 0, (int)zipModel.getEndCentralDirRecord().getSignature());
/* 675 */       copyByteArrayToArrayList(intByte, byteArrayList);
/*     */ 
/*     */       
/* 678 */       Raw.writeShortLittleEndian(shortByte, 0, (short)zipModel.getEndCentralDirRecord().getNoOfThisDisk());
/* 679 */       copyByteArrayToArrayList(shortByte, byteArrayList);
/*     */ 
/*     */       
/* 682 */       Raw.writeShortLittleEndian(shortByte, 0, (short)zipModel.getEndCentralDirRecord().getNoOfThisDiskStartOfCentralDir());
/* 683 */       copyByteArrayToArrayList(shortByte, byteArrayList);
/*     */ 
/*     */       
/* 686 */       int numEntries = 0;
/* 687 */       int numEntriesOnThisDisk = 0;
/* 688 */       if (zipModel.getCentralDirectory() == null || zipModel.getCentralDirectory().getFileHeaders() == null)
/*     */       {
/* 690 */         throw new ZipException("invalid central directory/file headers, cannot write end of central directory record");
/*     */       }
/*     */       
/* 693 */       numEntries = zipModel.getCentralDirectory().getFileHeaders().size();
/* 694 */       if (zipModel.isSplitArchive()) {
/* 695 */         numEntriesOnThisDisk = countNumberOfFileHeaderEntriesOnDisk(zipModel.getCentralDirectory().getFileHeaders(), zipModel.getEndCentralDirRecord().getNoOfThisDisk());
/*     */       } else {
/*     */         
/* 698 */         numEntriesOnThisDisk = numEntries;
/*     */       } 
/*     */       
/* 701 */       Raw.writeShortLittleEndian(shortByte, 0, (short)numEntriesOnThisDisk);
/* 702 */       copyByteArrayToArrayList(shortByte, byteArrayList);
/*     */ 
/*     */       
/* 705 */       Raw.writeShortLittleEndian(shortByte, 0, (short)numEntries);
/* 706 */       copyByteArrayToArrayList(shortByte, byteArrayList);
/*     */ 
/*     */       
/* 709 */       Raw.writeIntLittleEndian(intByte, 0, sizeOfCentralDir);
/* 710 */       copyByteArrayToArrayList(intByte, byteArrayList);
/*     */ 
/*     */       
/* 713 */       if (offsetCentralDir > 4294967295L) {
/* 714 */         Raw.writeLongLittleEndian(longByte, 0, 4294967295L);
/* 715 */         System.arraycopy(longByte, 0, intByte, 0, 4);
/* 716 */         copyByteArrayToArrayList(intByte, byteArrayList);
/*     */       } else {
/* 718 */         Raw.writeLongLittleEndian(longByte, 0, offsetCentralDir);
/* 719 */         System.arraycopy(longByte, 0, intByte, 0, 4);
/*     */         
/* 721 */         copyByteArrayToArrayList(intByte, byteArrayList);
/*     */       } 
/*     */ 
/*     */       
/* 725 */       int commentLength = 0;
/* 726 */       if (zipModel.getEndCentralDirRecord().getComment() != null) {
/* 727 */         commentLength = zipModel.getEndCentralDirRecord().getComment().length();
/*     */       }
/* 729 */       Raw.writeShortLittleEndian(shortByte, 0, (short)commentLength);
/* 730 */       copyByteArrayToArrayList(shortByte, byteArrayList);
/*     */ 
/*     */       
/* 733 */       if (commentLength > 0) {
/* 734 */         copyByteArrayToArrayList(zipModel.getEndCentralDirRecord().getComment().getBytes(), byteArrayList);
/*     */       }
/*     */       
/* 737 */       outputStream.write(byteArrayListToByteArray(byteArrayList));
/*     */     }
/* 739 */     catch (Exception e) {
/* 740 */       throw new ZipException(e);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void updateLocalFileHeader(LocalFileHeader localFileHeader, long offset, int toUpdate, ZipModel zipModel, byte[] bytesToWrite, int noOfDisk, SplitOutputStream outputStream) throws ZipException {
/* 746 */     if (localFileHeader == null || offset < 0L || zipModel == null) {
/* 747 */       throw new ZipException("invalid input parameters, cannot update local file header");
/*     */     }
/*     */     
/*     */     try {
/* 751 */       boolean closeFlag = false;
/* 752 */       SplitOutputStream currOutputStream = null;
/*     */       
/* 754 */       if (noOfDisk != outputStream.getCurrSplitFileCounter()) {
/* 755 */         File zipFile = new File(zipModel.getZipFile());
/* 756 */         String parentFile = zipFile.getParent();
/* 757 */         String fileNameWithoutExt = Zip4jUtil.getZipFileNameWithoutExt(zipFile.getName());
/* 758 */         String fileName = parentFile + System.getProperty("file.separator");
/* 759 */         if (noOfDisk < 9) {
/* 760 */           fileName = fileName + fileNameWithoutExt + ".z0" + (noOfDisk + 1);
/*     */         } else {
/* 762 */           fileName = fileName + fileNameWithoutExt + ".z" + (noOfDisk + 1);
/*     */         } 
/* 764 */         currOutputStream = new SplitOutputStream(new File(fileName));
/* 765 */         closeFlag = true;
/*     */       } else {
/* 767 */         currOutputStream = outputStream;
/*     */       } 
/*     */       
/* 770 */       long currOffset = currOutputStream.getFilePointer();
/*     */       
/* 772 */       if (currOutputStream == null) {
/* 773 */         throw new ZipException("invalid output stream handler, cannot update local file header");
/*     */       }
/*     */       
/* 776 */       switch (toUpdate) {
/*     */         case 14:
/* 778 */           currOutputStream.seek(offset + toUpdate);
/* 779 */           currOutputStream.write(bytesToWrite);
/*     */           break;
/*     */         case 18:
/* 782 */           updateCompressedSizeInLocalFileHeader(currOutputStream, localFileHeader, offset, toUpdate, bytesToWrite, zipModel.isZip64Format());
/*     */           break;
/*     */       } 
/*     */ 
/*     */ 
/*     */       
/* 788 */       if (closeFlag) {
/* 789 */         currOutputStream.close();
/*     */       } else {
/* 791 */         outputStream.seek(currOffset);
/*     */       }
/*     */     
/* 794 */     } catch (Exception e) {
/* 795 */       throw new ZipException(e);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private void updateCompressedSizeInLocalFileHeader(SplitOutputStream outputStream, LocalFileHeader localFileHeader, long offset, long toUpdate, byte[] bytesToWrite, boolean isZip64Format) throws ZipException {
/* 802 */     if (outputStream == null) {
/* 803 */       throw new ZipException("invalid output stream, cannot update compressed size for local file header");
/*     */     }
/*     */     
/*     */     try {
/* 807 */       if (localFileHeader.isWriteComprSizeInZip64ExtraRecord()) {
/* 808 */         if (bytesToWrite.length != 8) {
/* 809 */           throw new ZipException("attempting to write a non 8-byte compressed size block for a zip64 file");
/*     */         }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 820 */         long zip64CompressedSizeOffset = offset + toUpdate + 4L + 4L + 2L + 2L + localFileHeader.getFileNameLength() + 2L + 2L + 8L;
/* 821 */         outputStream.seek(zip64CompressedSizeOffset);
/* 822 */         outputStream.write(bytesToWrite);
/*     */       } else {
/* 824 */         outputStream.seek(offset + toUpdate);
/* 825 */         outputStream.write(bytesToWrite);
/*     */       } 
/* 827 */     } catch (IOException e) {
/* 828 */       throw new ZipException(e);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private void copyByteArrayToArrayList(byte[] byteArray, ArrayList<String> arrayList) throws ZipException {
/* 834 */     if (arrayList == null || byteArray == null) {
/* 835 */       throw new ZipException("one of the input parameters is null, cannot copy byte array to array list");
/*     */     }
/*     */     
/* 838 */     for (int i = 0; i < byteArray.length; i++) {
/* 839 */       arrayList.add(Byte.toString(byteArray[i]));
/*     */     }
/*     */   }
/*     */   
/*     */   private byte[] byteArrayListToByteArray(ArrayList arrayList) throws ZipException {
/* 844 */     if (arrayList == null) {
/* 845 */       throw new ZipException("input byte array list is null, cannot conver to byte array");
/*     */     }
/*     */     
/* 848 */     if (arrayList.size() <= 0) {
/* 849 */       return null;
/*     */     }
/*     */     
/* 852 */     byte[] retBytes = new byte[arrayList.size()];
/*     */     
/* 854 */     for (int i = 0; i < arrayList.size(); i++) {
/* 855 */       retBytes[i] = Byte.parseByte((String)arrayList.get(i));
/*     */     }
/*     */     
/* 858 */     return retBytes;
/*     */   }
/*     */ 
/*     */   
/*     */   private int countNumberOfFileHeaderEntriesOnDisk(ArrayList<FileHeader> fileHeaders, int numOfDisk) throws ZipException {
/* 863 */     if (fileHeaders == null) {
/* 864 */       throw new ZipException("file headers are null, cannot calculate number of entries on this disk");
/*     */     }
/*     */     
/* 867 */     int noEntries = 0;
/* 868 */     for (int i = 0; i < fileHeaders.size(); i++) {
/* 869 */       FileHeader fileHeader = fileHeaders.get(i);
/* 870 */       if (fileHeader.getDiskNumberStart() == numOfDisk - 1) {
/* 871 */         noEntries++;
/*     */       }
/*     */     } 
/* 874 */     return noEntries;
/*     */   }
/*     */ }


/* Location:              C:\Users\DuduM\OneDrive\Área de Trabalho\mw10\modularwarfare-shining-2023.2.4.4f-fix10.jar!\net\lingala\zip4j\core\HeaderWriter.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */