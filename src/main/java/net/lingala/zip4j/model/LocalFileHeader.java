/*     */ package net.lingala.zip4j.model;
/*     */ 
/*     */ import java.util.ArrayList;
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
/*     */ public class LocalFileHeader
/*     */ {
/*  70 */   private int encryptionMethod = -1;
/*     */   private boolean writeComprSizeInZip64ExtraRecord = false;
/*  72 */   private long crc32 = 0L;
/*  73 */   private long uncompressedSize = 0L; private int signature; private int versionNeededToExtract; private byte[] generalPurposeFlag; private int compressionMethod; private int lastModFileTime; private byte[] crcBuff; private long compressedSize; private int fileNameLength;
/*     */   private int extraFieldLength;
/*     */   
/*     */   public int getSignature() {
/*  77 */     return this.signature;
/*     */   }
/*     */   private String fileName; private byte[] extraField; private long offsetStartOfData; private boolean isEncrypted; private String password; private ArrayList extraDataRecords; private Zip64ExtendedInfo zip64ExtendedInfo; private AESExtraDataRecord aesExtraDataRecord; private boolean dataDescriptorExists; private boolean fileNameUTF8Encoded;
/*     */   public void setSignature(int signature) {
/*  81 */     this.signature = signature;
/*     */   }
/*     */   
/*     */   public int getVersionNeededToExtract() {
/*  85 */     return this.versionNeededToExtract;
/*     */   }
/*     */   
/*     */   public void setVersionNeededToExtract(int versionNeededToExtract) {
/*  89 */     this.versionNeededToExtract = versionNeededToExtract;
/*     */   }
/*     */   
/*     */   public byte[] getGeneralPurposeFlag() {
/*  93 */     return this.generalPurposeFlag;
/*     */   }
/*     */   
/*     */   public void setGeneralPurposeFlag(byte[] generalPurposeFlag) {
/*  97 */     this.generalPurposeFlag = generalPurposeFlag;
/*     */   }
/*     */   
/*     */   public int getCompressionMethod() {
/* 101 */     return this.compressionMethod;
/*     */   }
/*     */   
/*     */   public void setCompressionMethod(int compressionMethod) {
/* 105 */     this.compressionMethod = compressionMethod;
/*     */   }
/*     */   
/*     */   public int getLastModFileTime() {
/* 109 */     return this.lastModFileTime;
/*     */   }
/*     */   
/*     */   public void setLastModFileTime(int lastModFileTime) {
/* 113 */     this.lastModFileTime = lastModFileTime;
/*     */   }
/*     */   
/*     */   public long getCrc32() {
/* 117 */     return this.crc32;
/*     */   }
/*     */   
/*     */   public void setCrc32(long crc32) {
/* 121 */     this.crc32 = crc32;
/*     */   }
/*     */   
/*     */   public long getCompressedSize() {
/* 125 */     return this.compressedSize;
/*     */   }
/*     */   
/*     */   public void setCompressedSize(long compressedSize) {
/* 129 */     this.compressedSize = compressedSize;
/*     */   }
/*     */   
/*     */   public long getUncompressedSize() {
/* 133 */     return this.uncompressedSize;
/*     */   }
/*     */   
/*     */   public void setUncompressedSize(long uncompressedSize) {
/* 137 */     this.uncompressedSize = uncompressedSize;
/*     */   }
/*     */   
/*     */   public int getFileNameLength() {
/* 141 */     return this.fileNameLength;
/*     */   }
/*     */   
/*     */   public void setFileNameLength(int fileNameLength) {
/* 145 */     this.fileNameLength = fileNameLength;
/*     */   }
/*     */   
/*     */   public int getExtraFieldLength() {
/* 149 */     return this.extraFieldLength;
/*     */   }
/*     */   
/*     */   public void setExtraFieldLength(int extraFieldLength) {
/* 153 */     this.extraFieldLength = extraFieldLength;
/*     */   }
/*     */   
/*     */   public String getFileName() {
/* 157 */     return this.fileName;
/*     */   }
/*     */   
/*     */   public void setFileName(String fileName) {
/* 161 */     this.fileName = fileName;
/*     */   }
/*     */   
/*     */   public byte[] getExtraField() {
/* 165 */     return this.extraField;
/*     */   }
/*     */   
/*     */   public void setExtraField(byte[] extraField) {
/* 169 */     this.extraField = extraField;
/*     */   }
/*     */   
/*     */   public long getOffsetStartOfData() {
/* 173 */     return this.offsetStartOfData;
/*     */   }
/*     */   
/*     */   public void setOffsetStartOfData(long offsetStartOfData) {
/* 177 */     this.offsetStartOfData = offsetStartOfData;
/*     */   }
/*     */   
/*     */   public boolean isEncrypted() {
/* 181 */     return this.isEncrypted;
/*     */   }
/*     */   
/*     */   public void setEncrypted(boolean isEncrypted) {
/* 185 */     this.isEncrypted = isEncrypted;
/*     */   }
/*     */   
/*     */   public int getEncryptionMethod() {
/* 189 */     return this.encryptionMethod;
/*     */   }
/*     */   
/*     */   public void setEncryptionMethod(int encryptionMethod) {
/* 193 */     this.encryptionMethod = encryptionMethod;
/*     */   }
/*     */   
/*     */   public byte[] getCrcBuff() {
/* 197 */     return this.crcBuff;
/*     */   }
/*     */   
/*     */   public void setCrcBuff(byte[] crcBuff) {
/* 201 */     this.crcBuff = crcBuff;
/*     */   }
/*     */   
/*     */   public String getPassword() {
/* 205 */     return this.password;
/*     */   }
/*     */   
/*     */   public void setPassword(String password) {
/* 209 */     this.password = password;
/*     */   }
/*     */   
/*     */   public ArrayList getExtraDataRecords() {
/* 213 */     return this.extraDataRecords;
/*     */   }
/*     */   
/*     */   public void setExtraDataRecords(ArrayList extraDataRecords) {
/* 217 */     this.extraDataRecords = extraDataRecords;
/*     */   }
/*     */   
/*     */   public boolean isDataDescriptorExists() {
/* 221 */     return this.dataDescriptorExists;
/*     */   }
/*     */   
/*     */   public void setDataDescriptorExists(boolean dataDescriptorExists) {
/* 225 */     this.dataDescriptorExists = dataDescriptorExists;
/*     */   }
/*     */   
/*     */   public Zip64ExtendedInfo getZip64ExtendedInfo() {
/* 229 */     return this.zip64ExtendedInfo;
/*     */   }
/*     */   
/*     */   public void setZip64ExtendedInfo(Zip64ExtendedInfo zip64ExtendedInfo) {
/* 233 */     this.zip64ExtendedInfo = zip64ExtendedInfo;
/*     */   }
/*     */   
/*     */   public AESExtraDataRecord getAesExtraDataRecord() {
/* 237 */     return this.aesExtraDataRecord;
/*     */   }
/*     */   
/*     */   public void setAesExtraDataRecord(AESExtraDataRecord aesExtraDataRecord) {
/* 241 */     this.aesExtraDataRecord = aesExtraDataRecord;
/*     */   }
/*     */   
/*     */   public boolean isWriteComprSizeInZip64ExtraRecord() {
/* 245 */     return this.writeComprSizeInZip64ExtraRecord;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setWriteComprSizeInZip64ExtraRecord(boolean writeComprSizeInZip64ExtraRecord) {
/* 250 */     this.writeComprSizeInZip64ExtraRecord = writeComprSizeInZip64ExtraRecord;
/*     */   }
/*     */   
/*     */   public boolean isFileNameUTF8Encoded() {
/* 254 */     return this.fileNameUTF8Encoded;
/*     */   }
/*     */   
/*     */   public void setFileNameUTF8Encoded(boolean fileNameUTF8Encoded) {
/* 258 */     this.fileNameUTF8Encoded = fileNameUTF8Encoded;
/*     */   }
/*     */ }


/* Location:              C:\Users\DuduM\OneDrive\Área de Trabalho\mw10\modularwarfare-shining-2023.2.4.4f-fix10.jar!\net\lingala\zip4j\model\LocalFileHeader.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */