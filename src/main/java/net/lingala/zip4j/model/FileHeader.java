/*     */ package net.lingala.zip4j.model;
/*     */ 
/*     */ import java.util.ArrayList;
/*     */ import net.lingala.zip4j.exception.ZipException;
/*     */ import net.lingala.zip4j.progress.ProgressMonitor;
/*     */ import net.lingala.zip4j.unzip.Unzip;
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
/*     */ public class FileHeader
/*     */ {
/*  85 */   private int encryptionMethod = -1;
/*  86 */   private long crc32 = 0L;
/*  87 */   private long uncompressedSize = 0L; private int signature; private int versionMadeBy; private int versionNeededToExtract; private byte[] generalPurposeFlag; private int compressionMethod; private int lastModFileTime; private byte[] crcBuff; private long compressedSize; private int fileNameLength; private int extraFieldLength; private int fileCommentLength;
/*     */   private int diskNumberStart;
/*     */   
/*     */   public int getSignature() {
/*  91 */     return this.signature;
/*     */   }
/*     */   private byte[] internalFileAttr; private byte[] externalFileAttr; private long offsetLocalHeader; private String fileName; private String fileComment; private boolean isDirectory; private boolean isEncrypted; private String password; private boolean dataDescriptorExists; private Zip64ExtendedInfo zip64ExtendedInfo; private AESExtraDataRecord aesExtraDataRecord; private ArrayList extraDataRecords; private boolean fileNameUTF8Encoded;
/*     */   public void setSignature(int signature) {
/*  95 */     this.signature = signature;
/*     */   }
/*     */   
/*     */   public int getVersionMadeBy() {
/*  99 */     return this.versionMadeBy;
/*     */   }
/*     */   
/*     */   public void setVersionMadeBy(int versionMadeBy) {
/* 103 */     this.versionMadeBy = versionMadeBy;
/*     */   }
/*     */   
/*     */   public int getVersionNeededToExtract() {
/* 107 */     return this.versionNeededToExtract;
/*     */   }
/*     */   
/*     */   public void setVersionNeededToExtract(int versionNeededToExtract) {
/* 111 */     this.versionNeededToExtract = versionNeededToExtract;
/*     */   }
/*     */   
/*     */   public byte[] getGeneralPurposeFlag() {
/* 115 */     return this.generalPurposeFlag;
/*     */   }
/*     */   
/*     */   public void setGeneralPurposeFlag(byte[] generalPurposeFlag) {
/* 119 */     this.generalPurposeFlag = generalPurposeFlag;
/*     */   }
/*     */   
/*     */   public int getCompressionMethod() {
/* 123 */     return this.compressionMethod;
/*     */   }
/*     */   
/*     */   public void setCompressionMethod(int compressionMethod) {
/* 127 */     this.compressionMethod = compressionMethod;
/*     */   }
/*     */   
/*     */   public int getLastModFileTime() {
/* 131 */     return this.lastModFileTime;
/*     */   }
/*     */   
/*     */   public void setLastModFileTime(int lastModFileTime) {
/* 135 */     this.lastModFileTime = lastModFileTime;
/*     */   }
/*     */   
/*     */   public long getCrc32() {
/* 139 */     return this.crc32 & 0xFFFFFFFFL;
/*     */   }
/*     */   
/*     */   public void setCrc32(long crc32) {
/* 143 */     this.crc32 = crc32;
/*     */   }
/*     */   
/*     */   public long getCompressedSize() {
/* 147 */     return this.compressedSize;
/*     */   }
/*     */   
/*     */   public void setCompressedSize(long compressedSize) {
/* 151 */     this.compressedSize = compressedSize;
/*     */   }
/*     */   
/*     */   public long getUncompressedSize() {
/* 155 */     return this.uncompressedSize;
/*     */   }
/*     */   
/*     */   public void setUncompressedSize(long uncompressedSize) {
/* 159 */     this.uncompressedSize = uncompressedSize;
/*     */   }
/*     */   
/*     */   public int getFileNameLength() {
/* 163 */     return this.fileNameLength;
/*     */   }
/*     */   
/*     */   public void setFileNameLength(int fileNameLength) {
/* 167 */     this.fileNameLength = fileNameLength;
/*     */   }
/*     */   
/*     */   public int getExtraFieldLength() {
/* 171 */     return this.extraFieldLength;
/*     */   }
/*     */   
/*     */   public void setExtraFieldLength(int extraFieldLength) {
/* 175 */     this.extraFieldLength = extraFieldLength;
/*     */   }
/*     */   
/*     */   public int getFileCommentLength() {
/* 179 */     return this.fileCommentLength;
/*     */   }
/*     */   
/*     */   public void setFileCommentLength(int fileCommentLength) {
/* 183 */     this.fileCommentLength = fileCommentLength;
/*     */   }
/*     */   
/*     */   public int getDiskNumberStart() {
/* 187 */     return this.diskNumberStart;
/*     */   }
/*     */   
/*     */   public void setDiskNumberStart(int diskNumberStart) {
/* 191 */     this.diskNumberStart = diskNumberStart;
/*     */   }
/*     */   
/*     */   public byte[] getInternalFileAttr() {
/* 195 */     return this.internalFileAttr;
/*     */   }
/*     */   
/*     */   public void setInternalFileAttr(byte[] internalFileAttr) {
/* 199 */     this.internalFileAttr = internalFileAttr;
/*     */   }
/*     */   
/*     */   public byte[] getExternalFileAttr() {
/* 203 */     return this.externalFileAttr;
/*     */   }
/*     */   
/*     */   public void setExternalFileAttr(byte[] externalFileAttr) {
/* 207 */     this.externalFileAttr = externalFileAttr;
/*     */   }
/*     */   
/*     */   public long getOffsetLocalHeader() {
/* 211 */     return this.offsetLocalHeader;
/*     */   }
/*     */   
/*     */   public void setOffsetLocalHeader(long offsetLocalHeader) {
/* 215 */     this.offsetLocalHeader = offsetLocalHeader;
/*     */   }
/*     */   
/*     */   public String getFileName() {
/* 219 */     return this.fileName;
/*     */   }
/*     */   
/*     */   public void setFileName(String fileName) {
/* 223 */     this.fileName = fileName;
/*     */   }
/*     */   
/*     */   public String getFileComment() {
/* 227 */     return this.fileComment;
/*     */   }
/*     */   
/*     */   public void setFileComment(String fileComment) {
/* 231 */     this.fileComment = fileComment;
/*     */   }
/*     */   
/*     */   public boolean isDirectory() {
/* 235 */     return this.isDirectory;
/*     */   }
/*     */   
/*     */   public void setDirectory(boolean isDirectory) {
/* 239 */     this.isDirectory = isDirectory;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void extractFile(ZipModel zipModel, String outPath, ProgressMonitor progressMonitor, boolean runInThread) throws ZipException {
/* 250 */     extractFile(zipModel, outPath, null, progressMonitor, runInThread);
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
/*     */   public void extractFile(ZipModel zipModel, String outPath, UnzipParameters unzipParameters, ProgressMonitor progressMonitor, boolean runInThread) throws ZipException {
/* 263 */     extractFile(zipModel, outPath, unzipParameters, null, progressMonitor, runInThread);
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
/*     */   public void extractFile(ZipModel zipModel, String outPath, UnzipParameters unzipParameters, String newFileName, ProgressMonitor progressMonitor, boolean runInThread) throws ZipException {
/* 280 */     if (zipModel == null) {
/* 281 */       throw new ZipException("input zipModel is null");
/*     */     }
/*     */     
/* 284 */     if (!Zip4jUtil.checkOutputFolder(outPath)) {
/* 285 */       throw new ZipException("Invalid output path");
/*     */     }
/*     */     
/* 288 */     if (this == null) {
/* 289 */       throw new ZipException("invalid file header");
/*     */     }
/* 291 */     Unzip unzip = new Unzip(zipModel);
/* 292 */     unzip.extractFile(this, outPath, unzipParameters, newFileName, progressMonitor, runInThread);
/*     */   }
/*     */   
/*     */   public boolean isEncrypted() {
/* 296 */     return this.isEncrypted;
/*     */   }
/*     */   
/*     */   public void setEncrypted(boolean isEncrypted) {
/* 300 */     this.isEncrypted = isEncrypted;
/*     */   }
/*     */   
/*     */   public int getEncryptionMethod() {
/* 304 */     return this.encryptionMethod;
/*     */   }
/*     */   
/*     */   public void setEncryptionMethod(int encryptionMethod) {
/* 308 */     this.encryptionMethod = encryptionMethod;
/*     */   }
/*     */   
/*     */   public byte[] getCrcBuff() {
/* 312 */     return this.crcBuff;
/*     */   }
/*     */   
/*     */   public void setCrcBuff(byte[] crcBuff) {
/* 316 */     this.crcBuff = crcBuff;
/*     */   }
/*     */   
/*     */   public String getPassword() {
/* 320 */     return this.password;
/*     */   }
/*     */   
/*     */   public void setPassword(String password) {
/* 324 */     this.password = password;
/*     */   }
/*     */   
/*     */   public ArrayList getExtraDataRecords() {
/* 328 */     return this.extraDataRecords;
/*     */   }
/*     */   
/*     */   public void setExtraDataRecords(ArrayList extraDataRecords) {
/* 332 */     this.extraDataRecords = extraDataRecords;
/*     */   }
/*     */   
/*     */   public boolean isDataDescriptorExists() {
/* 336 */     return this.dataDescriptorExists;
/*     */   }
/*     */   
/*     */   public void setDataDescriptorExists(boolean dataDescriptorExists) {
/* 340 */     this.dataDescriptorExists = dataDescriptorExists;
/*     */   }
/*     */   
/*     */   public Zip64ExtendedInfo getZip64ExtendedInfo() {
/* 344 */     return this.zip64ExtendedInfo;
/*     */   }
/*     */   
/*     */   public void setZip64ExtendedInfo(Zip64ExtendedInfo zip64ExtendedInfo) {
/* 348 */     this.zip64ExtendedInfo = zip64ExtendedInfo;
/*     */   }
/*     */   
/*     */   public AESExtraDataRecord getAesExtraDataRecord() {
/* 352 */     return this.aesExtraDataRecord;
/*     */   }
/*     */   
/*     */   public void setAesExtraDataRecord(AESExtraDataRecord aesExtraDataRecord) {
/* 356 */     this.aesExtraDataRecord = aesExtraDataRecord;
/*     */   }
/*     */   
/*     */   public boolean isFileNameUTF8Encoded() {
/* 360 */     return this.fileNameUTF8Encoded;
/*     */   }
/*     */   
/*     */   public void setFileNameUTF8Encoded(boolean fileNameUTF8Encoded) {
/* 364 */     this.fileNameUTF8Encoded = fileNameUTF8Encoded;
/*     */   }
/*     */ }


/* Location:              C:\Users\DuduM\OneDrive\Área de Trabalho\mw10\modularwarfare-shining-2023.2.4.4f-fix10.jar!\net\lingala\zip4j\model\FileHeader.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */