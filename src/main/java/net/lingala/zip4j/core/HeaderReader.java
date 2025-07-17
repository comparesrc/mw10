/*      */ package net.lingala.zip4j.core;
/*      */ 
/*      */ import java.io.IOException;
/*      */ import java.io.RandomAccessFile;
/*      */ import java.util.ArrayList;
/*      */ import net.lingala.zip4j.exception.ZipException;
/*      */ import net.lingala.zip4j.model.AESExtraDataRecord;
/*      */ import net.lingala.zip4j.model.CentralDirectory;
/*      */ import net.lingala.zip4j.model.DigitalSignature;
/*      */ import net.lingala.zip4j.model.EndCentralDirRecord;
/*      */ import net.lingala.zip4j.model.ExtraDataRecord;
/*      */ import net.lingala.zip4j.model.FileHeader;
/*      */ import net.lingala.zip4j.model.LocalFileHeader;
/*      */ import net.lingala.zip4j.model.Zip64EndCentralDirLocator;
/*      */ import net.lingala.zip4j.model.Zip64EndCentralDirRecord;
/*      */ import net.lingala.zip4j.model.Zip64ExtendedInfo;
/*      */ import net.lingala.zip4j.model.ZipModel;
/*      */ import net.lingala.zip4j.util.Raw;
/*      */ import net.lingala.zip4j.util.Zip4jUtil;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public class HeaderReader
/*      */ {
/*   47 */   private RandomAccessFile zip4jRaf = null;
/*      */ 
/*      */   
/*      */   private ZipModel zipModel;
/*      */ 
/*      */ 
/*      */   
/*      */   public HeaderReader(RandomAccessFile zip4jRaf) {
/*   55 */     this.zip4jRaf = zip4jRaf;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public ZipModel readAllHeaders() throws ZipException {
/*   65 */     return readAllHeaders(null);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public ZipModel readAllHeaders(String fileNameCharset) throws ZipException {
/*   76 */     this.zipModel = new ZipModel();
/*   77 */     this.zipModel.setFileNameCharset(fileNameCharset);
/*   78 */     this.zipModel.setEndCentralDirRecord(readEndOfCentralDirectoryRecord());
/*      */ 
/*      */ 
/*      */     
/*   82 */     this.zipModel.setZip64EndCentralDirLocator(readZip64EndCentralDirLocator());
/*      */     
/*   84 */     if (this.zipModel.isZip64Format()) {
/*   85 */       this.zipModel.setZip64EndCentralDirRecord(readZip64EndCentralDirRec());
/*   86 */       if (this.zipModel.getZip64EndCentralDirRecord() != null && this.zipModel.getZip64EndCentralDirRecord().getNoOfThisDisk() > 0) {
/*      */         
/*   88 */         this.zipModel.setSplitArchive(true);
/*      */       } else {
/*   90 */         this.zipModel.setSplitArchive(false);
/*      */       } 
/*      */     } 
/*      */     
/*   94 */     this.zipModel.setCentralDirectory(readCentralDirectory());
/*      */     
/*   96 */     return this.zipModel;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private EndCentralDirRecord readEndOfCentralDirectoryRecord() throws ZipException {
/*  106 */     if (this.zip4jRaf == null) {
/*  107 */       throw new ZipException("random access file was null", 3);
/*      */     }
/*      */     
/*      */     try {
/*  111 */       byte[] ebs = new byte[4];
/*  112 */       long pos = this.zip4jRaf.length() - 22L;
/*      */       
/*  114 */       EndCentralDirRecord endCentralDirRecord = new EndCentralDirRecord();
/*  115 */       int counter = 0;
/*      */       do {
/*  117 */         this.zip4jRaf.seek(pos--);
/*  118 */         counter++;
/*  119 */       } while (Raw.readLeInt(this.zip4jRaf, ebs) != 101010256L && counter <= 3000);
/*      */       
/*  121 */       if (Raw.readIntLittleEndian(ebs, 0) != 101010256L) {
/*  122 */         throw new ZipException("zip headers not found. probably not a zip file");
/*      */       }
/*  124 */       byte[] intBuff = new byte[4];
/*  125 */       byte[] shortBuff = new byte[2];
/*      */ 
/*      */       
/*  128 */       endCentralDirRecord.setSignature(101010256L);
/*      */ 
/*      */       
/*  131 */       readIntoBuff(this.zip4jRaf, shortBuff);
/*  132 */       endCentralDirRecord.setNoOfThisDisk(Raw.readShortLittleEndian(shortBuff, 0));
/*      */ 
/*      */       
/*  135 */       readIntoBuff(this.zip4jRaf, shortBuff);
/*  136 */       endCentralDirRecord.setNoOfThisDiskStartOfCentralDir(Raw.readShortLittleEndian(shortBuff, 0));
/*      */ 
/*      */       
/*  139 */       readIntoBuff(this.zip4jRaf, shortBuff);
/*  140 */       endCentralDirRecord.setTotNoOfEntriesInCentralDirOnThisDisk(Raw.readShortLittleEndian(shortBuff, 0));
/*      */ 
/*      */       
/*  143 */       readIntoBuff(this.zip4jRaf, shortBuff);
/*  144 */       endCentralDirRecord.setTotNoOfEntriesInCentralDir(Raw.readShortLittleEndian(shortBuff, 0));
/*      */ 
/*      */       
/*  147 */       readIntoBuff(this.zip4jRaf, intBuff);
/*  148 */       endCentralDirRecord.setSizeOfCentralDir(Raw.readIntLittleEndian(intBuff, 0));
/*      */ 
/*      */       
/*  151 */       readIntoBuff(this.zip4jRaf, intBuff);
/*  152 */       byte[] longBuff = getLongByteFromIntByte(intBuff);
/*  153 */       endCentralDirRecord.setOffsetOfStartOfCentralDir(Raw.readLongLittleEndian(longBuff, 0));
/*      */ 
/*      */       
/*  156 */       readIntoBuff(this.zip4jRaf, shortBuff);
/*  157 */       int commentLength = Raw.readShortLittleEndian(shortBuff, 0);
/*  158 */       endCentralDirRecord.setCommentLength(commentLength);
/*      */ 
/*      */       
/*  161 */       if (commentLength > 0) {
/*  162 */         byte[] commentBuf = new byte[commentLength];
/*  163 */         readIntoBuff(this.zip4jRaf, commentBuf);
/*  164 */         endCentralDirRecord.setComment(new String(commentBuf));
/*  165 */         endCentralDirRecord.setCommentBytes(commentBuf);
/*      */       } else {
/*  167 */         endCentralDirRecord.setComment(null);
/*      */       } 
/*      */       
/*  170 */       int diskNumber = endCentralDirRecord.getNoOfThisDisk();
/*  171 */       if (diskNumber > 0) {
/*  172 */         this.zipModel.setSplitArchive(true);
/*      */       } else {
/*  174 */         this.zipModel.setSplitArchive(false);
/*      */       } 
/*      */       
/*  177 */       return endCentralDirRecord;
/*  178 */     } catch (IOException e) {
/*  179 */       throw new ZipException("Probably not a zip file or a corrupted zip file", e, 4);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private CentralDirectory readCentralDirectory() throws ZipException {
/*  190 */     if (this.zip4jRaf == null) {
/*  191 */       throw new ZipException("random access file was null", 3);
/*      */     }
/*      */     
/*  194 */     if (this.zipModel.getEndCentralDirRecord() == null) {
/*  195 */       throw new ZipException("EndCentralRecord was null, maybe a corrupt zip file");
/*      */     }
/*      */     
/*      */     try {
/*  199 */       CentralDirectory centralDirectory = new CentralDirectory();
/*  200 */       ArrayList<FileHeader> fileHeaderList = new ArrayList();
/*      */       
/*  202 */       EndCentralDirRecord endCentralDirRecord = this.zipModel.getEndCentralDirRecord();
/*  203 */       long offSetStartCentralDir = endCentralDirRecord.getOffsetOfStartOfCentralDir();
/*  204 */       int centralDirEntryCount = endCentralDirRecord.getTotNoOfEntriesInCentralDir();
/*      */       
/*  206 */       if (this.zipModel.isZip64Format()) {
/*  207 */         offSetStartCentralDir = this.zipModel.getZip64EndCentralDirRecord().getOffsetStartCenDirWRTStartDiskNo();
/*  208 */         centralDirEntryCount = (int)this.zipModel.getZip64EndCentralDirRecord().getTotNoOfEntriesInCentralDir();
/*      */       } 
/*      */       
/*  211 */       this.zip4jRaf.seek(offSetStartCentralDir);
/*      */       
/*  213 */       byte[] intBuff = new byte[4];
/*  214 */       byte[] shortBuff = new byte[2];
/*  215 */       byte[] longBuff = new byte[8];
/*      */       
/*  217 */       for (int i = 0; i < centralDirEntryCount; i++) {
/*  218 */         FileHeader fileHeader = new FileHeader();
/*      */ 
/*      */         
/*  221 */         readIntoBuff(this.zip4jRaf, intBuff);
/*  222 */         int j = Raw.readIntLittleEndian(intBuff, 0);
/*  223 */         if (j != 33639248L) {
/*  224 */           throw new ZipException("Expected central directory entry not found (#" + (i + 1) + ")");
/*      */         }
/*  226 */         fileHeader.setSignature(j);
/*      */ 
/*      */         
/*  229 */         readIntoBuff(this.zip4jRaf, shortBuff);
/*  230 */         fileHeader.setVersionMadeBy(Raw.readShortLittleEndian(shortBuff, 0));
/*      */ 
/*      */         
/*  233 */         readIntoBuff(this.zip4jRaf, shortBuff);
/*  234 */         fileHeader.setVersionNeededToExtract(Raw.readShortLittleEndian(shortBuff, 0));
/*      */ 
/*      */         
/*  237 */         readIntoBuff(this.zip4jRaf, shortBuff);
/*  238 */         fileHeader.setFileNameUTF8Encoded(((Raw.readShortLittleEndian(shortBuff, 0) & 0x800) != 0));
/*  239 */         int firstByte = shortBuff[0];
/*  240 */         int result = firstByte & 0x1;
/*  241 */         if (result != 0) {
/*  242 */           fileHeader.setEncrypted(true);
/*      */         }
/*  244 */         fileHeader.setGeneralPurposeFlag((byte[])shortBuff.clone());
/*      */ 
/*      */         
/*  247 */         fileHeader.setDataDescriptorExists((firstByte >> 3 == 1));
/*      */ 
/*      */         
/*  250 */         readIntoBuff(this.zip4jRaf, shortBuff);
/*  251 */         fileHeader.setCompressionMethod(Raw.readShortLittleEndian(shortBuff, 0));
/*      */ 
/*      */         
/*  254 */         readIntoBuff(this.zip4jRaf, intBuff);
/*  255 */         fileHeader.setLastModFileTime(Raw.readIntLittleEndian(intBuff, 0));
/*      */ 
/*      */         
/*  258 */         readIntoBuff(this.zip4jRaf, intBuff);
/*  259 */         fileHeader.setCrc32(Raw.readIntLittleEndian(intBuff, 0));
/*  260 */         fileHeader.setCrcBuff((byte[])intBuff.clone());
/*      */ 
/*      */         
/*  263 */         readIntoBuff(this.zip4jRaf, intBuff);
/*  264 */         longBuff = getLongByteFromIntByte(intBuff);
/*  265 */         fileHeader.setCompressedSize(Raw.readLongLittleEndian(longBuff, 0));
/*      */ 
/*      */         
/*  268 */         readIntoBuff(this.zip4jRaf, intBuff);
/*  269 */         longBuff = getLongByteFromIntByte(intBuff);
/*  270 */         fileHeader.setUncompressedSize(Raw.readLongLittleEndian(longBuff, 0));
/*      */ 
/*      */         
/*  273 */         readIntoBuff(this.zip4jRaf, shortBuff);
/*  274 */         int fileNameLength = Raw.readShortLittleEndian(shortBuff, 0);
/*  275 */         fileHeader.setFileNameLength(fileNameLength);
/*      */ 
/*      */         
/*  278 */         readIntoBuff(this.zip4jRaf, shortBuff);
/*  279 */         int extraFieldLength = Raw.readShortLittleEndian(shortBuff, 0);
/*  280 */         fileHeader.setExtraFieldLength(extraFieldLength);
/*      */ 
/*      */         
/*  283 */         readIntoBuff(this.zip4jRaf, shortBuff);
/*  284 */         int fileCommentLength = Raw.readShortLittleEndian(shortBuff, 0);
/*  285 */         fileHeader.setFileComment(new String(shortBuff));
/*      */ 
/*      */         
/*  288 */         readIntoBuff(this.zip4jRaf, shortBuff);
/*  289 */         fileHeader.setDiskNumberStart(Raw.readShortLittleEndian(shortBuff, 0));
/*      */ 
/*      */         
/*  292 */         readIntoBuff(this.zip4jRaf, shortBuff);
/*  293 */         fileHeader.setInternalFileAttr((byte[])shortBuff.clone());
/*      */ 
/*      */         
/*  296 */         readIntoBuff(this.zip4jRaf, intBuff);
/*  297 */         fileHeader.setExternalFileAttr((byte[])intBuff.clone());
/*      */ 
/*      */         
/*  300 */         readIntoBuff(this.zip4jRaf, intBuff);
/*      */ 
/*      */         
/*  303 */         longBuff = getLongByteFromIntByte(intBuff);
/*  304 */         fileHeader.setOffsetLocalHeader(Raw.readLongLittleEndian(longBuff, 0) & 0xFFFFFFFFL);
/*      */         
/*  306 */         if (fileNameLength > 0) {
/*  307 */           byte[] fileNameBuf = new byte[fileNameLength];
/*  308 */           readIntoBuff(this.zip4jRaf, fileNameBuf);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */           
/*  314 */           String fileName = null;
/*      */           
/*  316 */           if (Zip4jUtil.isStringNotNullAndNotEmpty(this.zipModel.getFileNameCharset())) {
/*  317 */             fileName = new String(fileNameBuf, this.zipModel.getFileNameCharset());
/*      */           } else {
/*  319 */             fileName = Zip4jUtil.decodeFileName(fileNameBuf, fileHeader.isFileNameUTF8Encoded());
/*      */           } 
/*      */           
/*  322 */           if (fileName == null) {
/*  323 */             throw new ZipException("fileName is null when reading central directory");
/*      */           }
/*      */           
/*  326 */           if (fileName.indexOf(":" + System.getProperty("file.separator")) >= 0) {
/*  327 */             fileName = fileName.substring(fileName.indexOf(":" + System.getProperty("file.separator")) + 2);
/*      */           }
/*      */           
/*  330 */           fileHeader.setFileName(fileName);
/*  331 */           fileHeader.setDirectory((fileName.endsWith("/") || fileName.endsWith("\\")));
/*      */         } else {
/*      */           
/*  334 */           fileHeader.setFileName(null);
/*      */         } 
/*      */ 
/*      */         
/*  338 */         readAndSaveExtraDataRecord(fileHeader);
/*      */ 
/*      */         
/*  341 */         readAndSaveZip64ExtendedInfo(fileHeader);
/*      */ 
/*      */         
/*  344 */         readAndSaveAESExtraDataRecord(fileHeader);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/*  363 */         if (fileCommentLength > 0) {
/*  364 */           byte[] fileCommentBuf = new byte[fileCommentLength];
/*  365 */           readIntoBuff(this.zip4jRaf, fileCommentBuf);
/*  366 */           fileHeader.setFileComment(new String(fileCommentBuf));
/*      */         } 
/*      */         
/*  369 */         fileHeaderList.add(fileHeader);
/*      */       } 
/*  371 */       centralDirectory.setFileHeaders(fileHeaderList);
/*      */ 
/*      */       
/*  374 */       DigitalSignature digitalSignature = new DigitalSignature();
/*  375 */       readIntoBuff(this.zip4jRaf, intBuff);
/*  376 */       int signature = Raw.readIntLittleEndian(intBuff, 0);
/*  377 */       if (signature != 84233040L) {
/*  378 */         return centralDirectory;
/*      */       }
/*      */       
/*  381 */       digitalSignature.setHeaderSignature(signature);
/*      */ 
/*      */       
/*  384 */       readIntoBuff(this.zip4jRaf, shortBuff);
/*  385 */       int sizeOfData = Raw.readShortLittleEndian(shortBuff, 0);
/*  386 */       digitalSignature.setSizeOfData(sizeOfData);
/*      */       
/*  388 */       if (sizeOfData > 0) {
/*  389 */         byte[] sigDataBuf = new byte[sizeOfData];
/*  390 */         readIntoBuff(this.zip4jRaf, sigDataBuf);
/*  391 */         digitalSignature.setSignatureData(new String(sigDataBuf));
/*      */       } 
/*      */       
/*  394 */       return centralDirectory;
/*  395 */     } catch (IOException e) {
/*  396 */       throw new ZipException(e);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void readAndSaveExtraDataRecord(FileHeader fileHeader) throws ZipException {
/*  407 */     if (this.zip4jRaf == null) {
/*  408 */       throw new ZipException("invalid file handler when trying to read extra data record");
/*      */     }
/*      */     
/*  411 */     if (fileHeader == null) {
/*  412 */       throw new ZipException("file header is null");
/*      */     }
/*      */     
/*  415 */     int extraFieldLength = fileHeader.getExtraFieldLength();
/*  416 */     if (extraFieldLength <= 0) {
/*      */       return;
/*      */     }
/*      */     
/*  420 */     fileHeader.setExtraDataRecords(readExtraDataRecords(extraFieldLength));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void readAndSaveExtraDataRecord(LocalFileHeader localFileHeader) throws ZipException {
/*  431 */     if (this.zip4jRaf == null) {
/*  432 */       throw new ZipException("invalid file handler when trying to read extra data record");
/*      */     }
/*      */     
/*  435 */     if (localFileHeader == null) {
/*  436 */       throw new ZipException("file header is null");
/*      */     }
/*      */     
/*  439 */     int extraFieldLength = localFileHeader.getExtraFieldLength();
/*  440 */     if (extraFieldLength <= 0) {
/*      */       return;
/*      */     }
/*      */     
/*  444 */     localFileHeader.setExtraDataRecords(readExtraDataRecords(extraFieldLength));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private ArrayList readExtraDataRecords(int extraFieldLength) throws ZipException {
/*  456 */     if (extraFieldLength <= 0) {
/*  457 */       return null;
/*      */     }
/*      */     
/*      */     try {
/*  461 */       byte[] extraFieldBuf = new byte[extraFieldLength];
/*  462 */       this.zip4jRaf.read(extraFieldBuf);
/*      */       
/*  464 */       int counter = 0;
/*  465 */       ArrayList<ExtraDataRecord> extraDataList = new ArrayList();
/*  466 */       while (counter < extraFieldLength) {
/*  467 */         ExtraDataRecord extraDataRecord = new ExtraDataRecord();
/*  468 */         int header = Raw.readShortLittleEndian(extraFieldBuf, counter);
/*  469 */         extraDataRecord.setHeader(header);
/*  470 */         counter += 2;
/*  471 */         int sizeOfRec = Raw.readShortLittleEndian(extraFieldBuf, counter);
/*      */         
/*  473 */         if (2 + sizeOfRec > extraFieldLength) {
/*  474 */           sizeOfRec = Raw.readShortBigEndian(extraFieldBuf, counter);
/*  475 */           if (2 + sizeOfRec > extraFieldLength) {
/*      */             break;
/*      */           }
/*      */         } 
/*      */ 
/*      */ 
/*      */         
/*  482 */         extraDataRecord.setSizeOfData(sizeOfRec);
/*  483 */         counter += 2;
/*      */         
/*  485 */         if (sizeOfRec > 0) {
/*  486 */           byte[] data = new byte[sizeOfRec];
/*  487 */           System.arraycopy(extraFieldBuf, counter, data, 0, sizeOfRec);
/*  488 */           extraDataRecord.setData(data);
/*      */         } 
/*  490 */         counter += sizeOfRec;
/*  491 */         extraDataList.add(extraDataRecord);
/*      */       } 
/*  493 */       if (extraDataList.size() > 0) {
/*  494 */         return extraDataList;
/*      */       }
/*  496 */       return null;
/*      */     }
/*  498 */     catch (IOException e) {
/*  499 */       throw new ZipException(e);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private Zip64EndCentralDirLocator readZip64EndCentralDirLocator() throws ZipException {
/*  510 */     if (this.zip4jRaf == null) {
/*  511 */       throw new ZipException("invalid file handler when trying to read Zip64EndCentralDirLocator");
/*      */     }
/*      */     
/*      */     try {
/*  515 */       Zip64EndCentralDirLocator zip64EndCentralDirLocator = new Zip64EndCentralDirLocator();
/*      */       
/*  517 */       setFilePointerToReadZip64EndCentralDirLoc();
/*      */       
/*  519 */       byte[] intBuff = new byte[4];
/*  520 */       byte[] longBuff = new byte[8];
/*      */       
/*  522 */       readIntoBuff(this.zip4jRaf, intBuff);
/*  523 */       int signature = Raw.readIntLittleEndian(intBuff, 0);
/*  524 */       if (signature == 117853008L) {
/*  525 */         this.zipModel.setZip64Format(true);
/*  526 */         zip64EndCentralDirLocator.setSignature(signature);
/*      */       } else {
/*  528 */         this.zipModel.setZip64Format(false);
/*  529 */         return null;
/*      */       } 
/*      */       
/*  532 */       readIntoBuff(this.zip4jRaf, intBuff);
/*  533 */       zip64EndCentralDirLocator.setNoOfDiskStartOfZip64EndOfCentralDirRec(Raw.readIntLittleEndian(intBuff, 0));
/*      */ 
/*      */       
/*  536 */       readIntoBuff(this.zip4jRaf, longBuff);
/*  537 */       zip64EndCentralDirLocator.setOffsetZip64EndOfCentralDirRec(Raw.readLongLittleEndian(longBuff, 0));
/*      */ 
/*      */       
/*  540 */       readIntoBuff(this.zip4jRaf, intBuff);
/*  541 */       zip64EndCentralDirLocator.setTotNumberOfDiscs(Raw.readIntLittleEndian(intBuff, 0));
/*      */       
/*  543 */       return zip64EndCentralDirLocator;
/*      */     }
/*  545 */     catch (Exception e) {
/*  546 */       throw new ZipException(e);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private Zip64EndCentralDirRecord readZip64EndCentralDirRec() throws ZipException {
/*  558 */     if (this.zipModel.getZip64EndCentralDirLocator() == null) {
/*  559 */       throw new ZipException("invalid zip64 end of central directory locator");
/*      */     }
/*      */     
/*  562 */     long offSetStartOfZip64CentralDir = this.zipModel.getZip64EndCentralDirLocator().getOffsetZip64EndOfCentralDirRec();
/*      */ 
/*      */     
/*  565 */     if (offSetStartOfZip64CentralDir < 0L) {
/*  566 */       throw new ZipException("invalid offset for start of end of central directory record");
/*      */     }
/*      */     
/*      */     try {
/*  570 */       this.zip4jRaf.seek(offSetStartOfZip64CentralDir);
/*      */       
/*  572 */       Zip64EndCentralDirRecord zip64EndCentralDirRecord = new Zip64EndCentralDirRecord();
/*      */       
/*  574 */       byte[] shortBuff = new byte[2];
/*  575 */       byte[] intBuff = new byte[4];
/*  576 */       byte[] longBuff = new byte[8];
/*      */ 
/*      */       
/*  579 */       readIntoBuff(this.zip4jRaf, intBuff);
/*  580 */       int signature = Raw.readIntLittleEndian(intBuff, 0);
/*  581 */       if (signature != 101075792L) {
/*  582 */         throw new ZipException("invalid signature for zip64 end of central directory record");
/*      */       }
/*  584 */       zip64EndCentralDirRecord.setSignature(signature);
/*      */ 
/*      */       
/*  587 */       readIntoBuff(this.zip4jRaf, longBuff);
/*  588 */       zip64EndCentralDirRecord.setSizeOfZip64EndCentralDirRec(Raw.readLongLittleEndian(longBuff, 0));
/*      */ 
/*      */ 
/*      */       
/*  592 */       readIntoBuff(this.zip4jRaf, shortBuff);
/*  593 */       zip64EndCentralDirRecord.setVersionMadeBy(Raw.readShortLittleEndian(shortBuff, 0));
/*      */ 
/*      */       
/*  596 */       readIntoBuff(this.zip4jRaf, shortBuff);
/*  597 */       zip64EndCentralDirRecord.setVersionNeededToExtract(Raw.readShortLittleEndian(shortBuff, 0));
/*      */ 
/*      */       
/*  600 */       readIntoBuff(this.zip4jRaf, intBuff);
/*  601 */       zip64EndCentralDirRecord.setNoOfThisDisk(Raw.readIntLittleEndian(intBuff, 0));
/*      */ 
/*      */       
/*  604 */       readIntoBuff(this.zip4jRaf, intBuff);
/*  605 */       zip64EndCentralDirRecord.setNoOfThisDiskStartOfCentralDir(Raw.readIntLittleEndian(intBuff, 0));
/*      */ 
/*      */ 
/*      */       
/*  609 */       readIntoBuff(this.zip4jRaf, longBuff);
/*  610 */       zip64EndCentralDirRecord.setTotNoOfEntriesInCentralDirOnThisDisk(Raw.readLongLittleEndian(longBuff, 0));
/*      */ 
/*      */ 
/*      */       
/*  614 */       readIntoBuff(this.zip4jRaf, longBuff);
/*  615 */       zip64EndCentralDirRecord.setTotNoOfEntriesInCentralDir(Raw.readLongLittleEndian(longBuff, 0));
/*      */ 
/*      */ 
/*      */       
/*  619 */       readIntoBuff(this.zip4jRaf, longBuff);
/*  620 */       zip64EndCentralDirRecord.setSizeOfCentralDir(Raw.readLongLittleEndian(longBuff, 0));
/*      */ 
/*      */       
/*  623 */       readIntoBuff(this.zip4jRaf, longBuff);
/*  624 */       zip64EndCentralDirRecord.setOffsetStartCenDirWRTStartDiskNo(Raw.readLongLittleEndian(longBuff, 0));
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  629 */       long extDataSecSize = zip64EndCentralDirRecord.getSizeOfZip64EndCentralDirRec() - 44L;
/*  630 */       if (extDataSecSize > 0L) {
/*  631 */         byte[] extDataSecRecBuf = new byte[(int)extDataSecSize];
/*  632 */         readIntoBuff(this.zip4jRaf, extDataSecRecBuf);
/*  633 */         zip64EndCentralDirRecord.setExtensibleDataSector(extDataSecRecBuf);
/*      */       } 
/*      */       
/*  636 */       return zip64EndCentralDirRecord;
/*      */     }
/*  638 */     catch (IOException e) {
/*  639 */       throw new ZipException(e);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void readAndSaveZip64ExtendedInfo(FileHeader fileHeader) throws ZipException {
/*  650 */     if (fileHeader == null) {
/*  651 */       throw new ZipException("file header is null in reading Zip64 Extended Info");
/*      */     }
/*      */     
/*  654 */     if (fileHeader.getExtraDataRecords() == null || fileHeader.getExtraDataRecords().size() <= 0) {
/*      */       return;
/*      */     }
/*      */     
/*  658 */     Zip64ExtendedInfo zip64ExtendedInfo = readZip64ExtendedInfo(fileHeader.getExtraDataRecords(), fileHeader.getUncompressedSize(), fileHeader.getCompressedSize(), fileHeader.getOffsetLocalHeader(), fileHeader.getDiskNumberStart());
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  665 */     if (zip64ExtendedInfo != null) {
/*  666 */       fileHeader.setZip64ExtendedInfo(zip64ExtendedInfo);
/*  667 */       if (zip64ExtendedInfo.getUnCompressedSize() != -1L) {
/*  668 */         fileHeader.setUncompressedSize(zip64ExtendedInfo.getUnCompressedSize());
/*      */       }
/*  670 */       if (zip64ExtendedInfo.getCompressedSize() != -1L) {
/*  671 */         fileHeader.setCompressedSize(zip64ExtendedInfo.getCompressedSize());
/*      */       }
/*  673 */       if (zip64ExtendedInfo.getOffsetLocalHeader() != -1L) {
/*  674 */         fileHeader.setOffsetLocalHeader(zip64ExtendedInfo.getOffsetLocalHeader());
/*      */       }
/*  676 */       if (zip64ExtendedInfo.getDiskNumberStart() != -1) {
/*  677 */         fileHeader.setDiskNumberStart(zip64ExtendedInfo.getDiskNumberStart());
/*      */       }
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void readAndSaveZip64ExtendedInfo(LocalFileHeader localFileHeader) throws ZipException {
/*  687 */     if (localFileHeader == null) {
/*  688 */       throw new ZipException("file header is null in reading Zip64 Extended Info");
/*      */     }
/*      */     
/*  691 */     if (localFileHeader.getExtraDataRecords() == null || localFileHeader.getExtraDataRecords().size() <= 0) {
/*      */       return;
/*      */     }
/*      */     
/*  695 */     Zip64ExtendedInfo zip64ExtendedInfo = readZip64ExtendedInfo(localFileHeader.getExtraDataRecords(), localFileHeader.getUncompressedSize(), localFileHeader.getCompressedSize(), -1L, -1);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  701 */     if (zip64ExtendedInfo != null) {
/*  702 */       localFileHeader.setZip64ExtendedInfo(zip64ExtendedInfo);
/*      */       
/*  704 */       if (zip64ExtendedInfo.getUnCompressedSize() != -1L) {
/*  705 */         localFileHeader.setUncompressedSize(zip64ExtendedInfo.getUnCompressedSize());
/*      */       }
/*  707 */       if (zip64ExtendedInfo.getCompressedSize() != -1L) {
/*  708 */         localFileHeader.setCompressedSize(zip64ExtendedInfo.getCompressedSize());
/*      */       }
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private Zip64ExtendedInfo readZip64ExtendedInfo(ArrayList<ExtraDataRecord> extraDataRecords, long unCompressedSize, long compressedSize, long offsetLocalHeader, int diskNumberStart) throws ZipException {
/*  729 */     for (int i = 0; i < extraDataRecords.size(); i++) {
/*  730 */       ExtraDataRecord extraDataRecord = extraDataRecords.get(i);
/*  731 */       if (extraDataRecord != null)
/*      */       {
/*      */ 
/*      */         
/*  735 */         if (extraDataRecord.getHeader() == 1L) {
/*      */           
/*  737 */           Zip64ExtendedInfo zip64ExtendedInfo = new Zip64ExtendedInfo();
/*      */           
/*  739 */           byte[] byteBuff = extraDataRecord.getData();
/*      */           
/*  741 */           if (extraDataRecord.getSizeOfData() <= 0) {
/*      */             break;
/*      */           }
/*  744 */           byte[] longByteBuff = new byte[8];
/*  745 */           byte[] intByteBuff = new byte[4];
/*  746 */           int counter = 0;
/*  747 */           boolean valueAdded = false;
/*      */           
/*  749 */           if ((unCompressedSize & 0xFFFFL) == 65535L && counter < extraDataRecord.getSizeOfData()) {
/*  750 */             System.arraycopy(byteBuff, counter, longByteBuff, 0, 8);
/*  751 */             long val = Raw.readLongLittleEndian(longByteBuff, 0);
/*  752 */             zip64ExtendedInfo.setUnCompressedSize(val);
/*  753 */             counter += 8;
/*  754 */             valueAdded = true;
/*      */           } 
/*      */           
/*  757 */           if ((compressedSize & 0xFFFFL) == 65535L && counter < extraDataRecord.getSizeOfData()) {
/*  758 */             System.arraycopy(byteBuff, counter, longByteBuff, 0, 8);
/*  759 */             long val = Raw.readLongLittleEndian(longByteBuff, 0);
/*  760 */             zip64ExtendedInfo.setCompressedSize(val);
/*  761 */             counter += 8;
/*  762 */             valueAdded = true;
/*      */           } 
/*      */           
/*  765 */           if ((offsetLocalHeader & 0xFFFFL) == 65535L && counter < extraDataRecord.getSizeOfData()) {
/*  766 */             System.arraycopy(byteBuff, counter, longByteBuff, 0, 8);
/*  767 */             long val = Raw.readLongLittleEndian(longByteBuff, 0);
/*  768 */             zip64ExtendedInfo.setOffsetLocalHeader(val);
/*  769 */             counter += 8;
/*  770 */             valueAdded = true;
/*      */           } 
/*      */           
/*  773 */           if ((diskNumberStart & 0xFFFF) == 65535 && counter < extraDataRecord.getSizeOfData()) {
/*  774 */             System.arraycopy(byteBuff, counter, intByteBuff, 0, 4);
/*  775 */             int val = Raw.readIntLittleEndian(intByteBuff, 0);
/*  776 */             zip64ExtendedInfo.setDiskNumberStart(val);
/*  777 */             counter += 8;
/*  778 */             valueAdded = true;
/*      */           } 
/*      */           
/*  781 */           if (valueAdded) {
/*  782 */             return zip64ExtendedInfo;
/*      */           }
/*      */           break;
/*      */         } 
/*      */       }
/*      */     } 
/*  788 */     return null;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void setFilePointerToReadZip64EndCentralDirLoc() throws ZipException {
/*      */     try {
/*  798 */       byte[] ebs = new byte[4];
/*  799 */       long pos = this.zip4jRaf.length() - 22L;
/*      */       
/*      */       do {
/*  802 */         this.zip4jRaf.seek(pos--);
/*  803 */       } while (Raw.readLeInt(this.zip4jRaf, ebs) != 101010256L);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  814 */       this.zip4jRaf.seek(this.zip4jRaf.getFilePointer() - 4L - 4L - 8L - 4L - 4L);
/*  815 */     } catch (IOException e) {
/*  816 */       throw new ZipException(e);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public LocalFileHeader readLocalFileHeader(FileHeader fileHeader) throws ZipException {
/*  827 */     if (fileHeader == null || this.zip4jRaf == null) {
/*  828 */       throw new ZipException("invalid read parameters for local header");
/*      */     }
/*      */     
/*  831 */     long locHdrOffset = fileHeader.getOffsetLocalHeader();
/*      */     
/*  833 */     if (fileHeader.getZip64ExtendedInfo() != null) {
/*  834 */       Zip64ExtendedInfo zip64ExtendedInfo = fileHeader.getZip64ExtendedInfo();
/*  835 */       if (zip64ExtendedInfo.getOffsetLocalHeader() > 0L) {
/*  836 */         locHdrOffset = fileHeader.getOffsetLocalHeader();
/*      */       }
/*      */     } 
/*      */     
/*  840 */     if (locHdrOffset < 0L) {
/*  841 */       throw new ZipException("invalid local header offset");
/*      */     }
/*      */     
/*      */     try {
/*  845 */       this.zip4jRaf.seek(locHdrOffset);
/*      */       
/*  847 */       int length = 0;
/*  848 */       LocalFileHeader localFileHeader = new LocalFileHeader();
/*      */       
/*  850 */       byte[] shortBuff = new byte[2];
/*  851 */       byte[] intBuff = new byte[4];
/*  852 */       byte[] longBuff = new byte[8];
/*      */ 
/*      */       
/*  855 */       readIntoBuff(this.zip4jRaf, intBuff);
/*  856 */       int sig = Raw.readIntLittleEndian(intBuff, 0);
/*  857 */       if (sig != 67324752L) {
/*  858 */         throw new ZipException("invalid local header signature for file: " + fileHeader.getFileName());
/*      */       }
/*  860 */       localFileHeader.setSignature(sig);
/*  861 */       length += 4;
/*      */ 
/*      */       
/*  864 */       readIntoBuff(this.zip4jRaf, shortBuff);
/*  865 */       localFileHeader.setVersionNeededToExtract(Raw.readShortLittleEndian(shortBuff, 0));
/*  866 */       length += 2;
/*      */ 
/*      */       
/*  869 */       readIntoBuff(this.zip4jRaf, shortBuff);
/*  870 */       localFileHeader.setFileNameUTF8Encoded(((Raw.readShortLittleEndian(shortBuff, 0) & 0x800) != 0));
/*  871 */       int firstByte = shortBuff[0];
/*  872 */       int result = firstByte & 0x1;
/*  873 */       if (result != 0) {
/*  874 */         localFileHeader.setEncrypted(true);
/*      */       }
/*  876 */       localFileHeader.setGeneralPurposeFlag(shortBuff);
/*  877 */       length += 2;
/*      */ 
/*      */       
/*  880 */       String binary = Integer.toBinaryString(firstByte);
/*  881 */       if (binary.length() >= 4) {
/*  882 */         localFileHeader.setDataDescriptorExists((binary.charAt(3) == '1'));
/*      */       }
/*      */       
/*  885 */       readIntoBuff(this.zip4jRaf, shortBuff);
/*  886 */       localFileHeader.setCompressionMethod(Raw.readShortLittleEndian(shortBuff, 0));
/*  887 */       length += 2;
/*      */ 
/*      */       
/*  890 */       readIntoBuff(this.zip4jRaf, intBuff);
/*  891 */       localFileHeader.setLastModFileTime(Raw.readIntLittleEndian(intBuff, 0));
/*  892 */       length += 4;
/*      */ 
/*      */       
/*  895 */       readIntoBuff(this.zip4jRaf, intBuff);
/*  896 */       localFileHeader.setCrc32(Raw.readIntLittleEndian(intBuff, 0));
/*  897 */       localFileHeader.setCrcBuff((byte[])intBuff.clone());
/*  898 */       length += 4;
/*      */ 
/*      */       
/*  901 */       readIntoBuff(this.zip4jRaf, intBuff);
/*  902 */       longBuff = getLongByteFromIntByte(intBuff);
/*  903 */       localFileHeader.setCompressedSize(Raw.readLongLittleEndian(longBuff, 0));
/*  904 */       length += 4;
/*      */ 
/*      */       
/*  907 */       readIntoBuff(this.zip4jRaf, intBuff);
/*  908 */       longBuff = getLongByteFromIntByte(intBuff);
/*  909 */       localFileHeader.setUncompressedSize(Raw.readLongLittleEndian(longBuff, 0));
/*  910 */       length += 4;
/*      */ 
/*      */       
/*  913 */       readIntoBuff(this.zip4jRaf, shortBuff);
/*  914 */       int fileNameLength = Raw.readShortLittleEndian(shortBuff, 0);
/*  915 */       localFileHeader.setFileNameLength(fileNameLength);
/*  916 */       length += 2;
/*      */ 
/*      */       
/*  919 */       readIntoBuff(this.zip4jRaf, shortBuff);
/*  920 */       int extraFieldLength = Raw.readShortLittleEndian(shortBuff, 0);
/*  921 */       localFileHeader.setExtraFieldLength(extraFieldLength);
/*  922 */       length += 2;
/*      */ 
/*      */       
/*  925 */       if (fileNameLength > 0) {
/*  926 */         byte[] fileNameBuf = new byte[fileNameLength];
/*  927 */         readIntoBuff(this.zip4jRaf, fileNameBuf);
/*      */ 
/*      */ 
/*      */         
/*  931 */         String fileName = Zip4jUtil.decodeFileName(fileNameBuf, localFileHeader.isFileNameUTF8Encoded());
/*      */         
/*  933 */         if (fileName == null) {
/*  934 */           throw new ZipException("file name is null, cannot assign file name to local file header");
/*      */         }
/*      */         
/*  937 */         if (fileName.indexOf(":" + System.getProperty("file.separator")) >= 0) {
/*  938 */           fileName = fileName.substring(fileName.indexOf(":" + System.getProperty("file.separator")) + 2);
/*      */         }
/*      */         
/*  941 */         localFileHeader.setFileName(fileName);
/*  942 */         length += fileNameLength;
/*      */       } else {
/*  944 */         localFileHeader.setFileName(null);
/*      */       } 
/*      */ 
/*      */       
/*  948 */       readAndSaveExtraDataRecord(localFileHeader);
/*  949 */       length += extraFieldLength;
/*      */       
/*  951 */       localFileHeader.setOffsetStartOfData(locHdrOffset + length);
/*      */ 
/*      */       
/*  954 */       localFileHeader.setPassword(fileHeader.getPassword());
/*      */       
/*  956 */       readAndSaveZip64ExtendedInfo(localFileHeader);
/*      */       
/*  958 */       readAndSaveAESExtraDataRecord(localFileHeader);
/*      */       
/*  960 */       if (localFileHeader.isEncrypted())
/*      */       {
/*  962 */         if (localFileHeader.getEncryptionMethod() != 99)
/*      */         {
/*      */           
/*  965 */           if ((firstByte & 0x40) == 64) {
/*      */             
/*  967 */             localFileHeader.setEncryptionMethod(1);
/*      */           } else {
/*  969 */             localFileHeader.setEncryptionMethod(0);
/*      */           } 
/*      */         }
/*      */       }
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  977 */       if (localFileHeader.getCrc32() <= 0L) {
/*  978 */         localFileHeader.setCrc32(fileHeader.getCrc32());
/*  979 */         localFileHeader.setCrcBuff(fileHeader.getCrcBuff());
/*      */       } 
/*      */       
/*  982 */       if (localFileHeader.getCompressedSize() <= 0L) {
/*  983 */         localFileHeader.setCompressedSize(fileHeader.getCompressedSize());
/*      */       }
/*      */       
/*  986 */       if (localFileHeader.getUncompressedSize() <= 0L) {
/*  987 */         localFileHeader.setUncompressedSize(fileHeader.getUncompressedSize());
/*      */       }
/*      */       
/*  990 */       return localFileHeader;
/*  991 */     } catch (IOException e) {
/*  992 */       throw new ZipException(e);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void readAndSaveAESExtraDataRecord(FileHeader fileHeader) throws ZipException {
/* 1002 */     if (fileHeader == null) {
/* 1003 */       throw new ZipException("file header is null in reading Zip64 Extended Info");
/*      */     }
/*      */     
/* 1006 */     if (fileHeader.getExtraDataRecords() == null || fileHeader.getExtraDataRecords().size() <= 0) {
/*      */       return;
/*      */     }
/*      */     
/* 1010 */     AESExtraDataRecord aesExtraDataRecord = readAESExtraDataRecord(fileHeader.getExtraDataRecords());
/* 1011 */     if (aesExtraDataRecord != null) {
/* 1012 */       fileHeader.setAesExtraDataRecord(aesExtraDataRecord);
/* 1013 */       fileHeader.setEncryptionMethod(99);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void readAndSaveAESExtraDataRecord(LocalFileHeader localFileHeader) throws ZipException {
/* 1023 */     if (localFileHeader == null) {
/* 1024 */       throw new ZipException("file header is null in reading Zip64 Extended Info");
/*      */     }
/*      */     
/* 1027 */     if (localFileHeader.getExtraDataRecords() == null || localFileHeader.getExtraDataRecords().size() <= 0) {
/*      */       return;
/*      */     }
/*      */     
/* 1031 */     AESExtraDataRecord aesExtraDataRecord = readAESExtraDataRecord(localFileHeader.getExtraDataRecords());
/* 1032 */     if (aesExtraDataRecord != null) {
/* 1033 */       localFileHeader.setAesExtraDataRecord(aesExtraDataRecord);
/* 1034 */       localFileHeader.setEncryptionMethod(99);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private AESExtraDataRecord readAESExtraDataRecord(ArrayList<ExtraDataRecord> extraDataRecords) throws ZipException {
/* 1046 */     if (extraDataRecords == null) {
/* 1047 */       return null;
/*      */     }
/*      */     
/* 1050 */     for (int i = 0; i < extraDataRecords.size(); i++) {
/* 1051 */       ExtraDataRecord extraDataRecord = extraDataRecords.get(i);
/* 1052 */       if (extraDataRecord != null)
/*      */       {
/*      */ 
/*      */         
/* 1056 */         if (extraDataRecord.getHeader() == 39169L) {
/*      */           
/* 1058 */           if (extraDataRecord.getData() == null) {
/* 1059 */             throw new ZipException("corrput AES extra data records");
/*      */           }
/*      */           
/* 1062 */           AESExtraDataRecord aesExtraDataRecord = new AESExtraDataRecord();
/*      */           
/* 1064 */           aesExtraDataRecord.setSignature(39169L);
/* 1065 */           aesExtraDataRecord.setDataSize(extraDataRecord.getSizeOfData());
/*      */           
/* 1067 */           byte[] aesData = extraDataRecord.getData();
/* 1068 */           aesExtraDataRecord.setVersionNumber(Raw.readShortLittleEndian(aesData, 0));
/* 1069 */           byte[] vendorIDBytes = new byte[2];
/* 1070 */           System.arraycopy(aesData, 2, vendorIDBytes, 0, 2);
/* 1071 */           aesExtraDataRecord.setVendorID(new String(vendorIDBytes));
/* 1072 */           aesExtraDataRecord.setAesStrength(aesData[4] & 0xFF);
/* 1073 */           aesExtraDataRecord.setCompressionMethod(Raw.readShortLittleEndian(aesData, 5));
/*      */           
/* 1075 */           return aesExtraDataRecord;
/*      */         } 
/*      */       }
/*      */     } 
/* 1079 */     return null;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private byte[] readIntoBuff(RandomAccessFile zip4jRaf, byte[] buf) throws ZipException {
/*      */     try {
/* 1091 */       if (zip4jRaf.read(buf, 0, buf.length) != -1) {
/* 1092 */         return buf;
/*      */       }
/* 1094 */       throw new ZipException("unexpected end of file when reading short buff");
/*      */     }
/* 1096 */     catch (IOException e) {
/* 1097 */       throw new ZipException("IOException when reading short buff", e);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private byte[] getLongByteFromIntByte(byte[] intByte) throws ZipException {
/* 1108 */     if (intByte == null) {
/* 1109 */       throw new ZipException("input parameter is null, cannot expand to 8 bytes");
/*      */     }
/*      */     
/* 1112 */     if (intByte.length != 4) {
/* 1113 */       throw new ZipException("invalid byte length, cannot expand to 8 bytes");
/*      */     }
/*      */     
/* 1116 */     byte[] longBuff = { intByte[0], intByte[1], intByte[2], intByte[3], 0, 0, 0, 0 };
/* 1117 */     return longBuff;
/*      */   }
/*      */ }


/* Location:              C:\Users\DuduM\OneDrive\Área de Trabalho\mw10\modularwarfare-shining-2023.2.4.4f-fix10.jar!\net\lingala\zip4j\core\HeaderReader.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */