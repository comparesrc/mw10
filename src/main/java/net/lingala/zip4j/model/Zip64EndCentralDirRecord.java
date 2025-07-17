/*     */ package net.lingala.zip4j.model;
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
/*     */ public class Zip64EndCentralDirRecord
/*     */ {
/*     */   private long signature;
/*     */   private long sizeOfZip64EndCentralDirRec;
/*     */   private int versionMadeBy;
/*     */   private int versionNeededToExtract;
/*     */   private int noOfThisDisk;
/*     */   private int noOfThisDiskStartOfCentralDir;
/*     */   private long totNoOfEntriesInCentralDirOnThisDisk;
/*     */   private long totNoOfEntriesInCentralDir;
/*     */   private long sizeOfCentralDir;
/*     */   private long offsetStartCenDirWRTStartDiskNo;
/*     */   private byte[] extensibleDataSector;
/*     */   
/*     */   public long getSignature() {
/*  44 */     return this.signature;
/*     */   }
/*     */   
/*     */   public void setSignature(long signature) {
/*  48 */     this.signature = signature;
/*     */   }
/*     */   
/*     */   public long getSizeOfZip64EndCentralDirRec() {
/*  52 */     return this.sizeOfZip64EndCentralDirRec;
/*     */   }
/*     */   
/*     */   public void setSizeOfZip64EndCentralDirRec(long sizeOfZip64EndCentralDirRec) {
/*  56 */     this.sizeOfZip64EndCentralDirRec = sizeOfZip64EndCentralDirRec;
/*     */   }
/*     */   
/*     */   public int getVersionMadeBy() {
/*  60 */     return this.versionMadeBy;
/*     */   }
/*     */   
/*     */   public void setVersionMadeBy(int versionMadeBy) {
/*  64 */     this.versionMadeBy = versionMadeBy;
/*     */   }
/*     */   
/*     */   public int getVersionNeededToExtract() {
/*  68 */     return this.versionNeededToExtract;
/*     */   }
/*     */   
/*     */   public void setVersionNeededToExtract(int versionNeededToExtract) {
/*  72 */     this.versionNeededToExtract = versionNeededToExtract;
/*     */   }
/*     */   
/*     */   public int getNoOfThisDisk() {
/*  76 */     return this.noOfThisDisk;
/*     */   }
/*     */   
/*     */   public void setNoOfThisDisk(int noOfThisDisk) {
/*  80 */     this.noOfThisDisk = noOfThisDisk;
/*     */   }
/*     */   
/*     */   public int getNoOfThisDiskStartOfCentralDir() {
/*  84 */     return this.noOfThisDiskStartOfCentralDir;
/*     */   }
/*     */   
/*     */   public void setNoOfThisDiskStartOfCentralDir(int noOfThisDiskStartOfCentralDir) {
/*  88 */     this.noOfThisDiskStartOfCentralDir = noOfThisDiskStartOfCentralDir;
/*     */   }
/*     */   
/*     */   public long getTotNoOfEntriesInCentralDirOnThisDisk() {
/*  92 */     return this.totNoOfEntriesInCentralDirOnThisDisk;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setTotNoOfEntriesInCentralDirOnThisDisk(long totNoOfEntriesInCentralDirOnThisDisk) {
/*  97 */     this.totNoOfEntriesInCentralDirOnThisDisk = totNoOfEntriesInCentralDirOnThisDisk;
/*     */   }
/*     */   
/*     */   public long getTotNoOfEntriesInCentralDir() {
/* 101 */     return this.totNoOfEntriesInCentralDir;
/*     */   }
/*     */   
/*     */   public void setTotNoOfEntriesInCentralDir(long totNoOfEntriesInCentralDir) {
/* 105 */     this.totNoOfEntriesInCentralDir = totNoOfEntriesInCentralDir;
/*     */   }
/*     */   
/*     */   public long getSizeOfCentralDir() {
/* 109 */     return this.sizeOfCentralDir;
/*     */   }
/*     */   
/*     */   public void setSizeOfCentralDir(long sizeOfCentralDir) {
/* 113 */     this.sizeOfCentralDir = sizeOfCentralDir;
/*     */   }
/*     */   
/*     */   public long getOffsetStartCenDirWRTStartDiskNo() {
/* 117 */     return this.offsetStartCenDirWRTStartDiskNo;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setOffsetStartCenDirWRTStartDiskNo(long offsetStartCenDirWRTStartDiskNo) {
/* 122 */     this.offsetStartCenDirWRTStartDiskNo = offsetStartCenDirWRTStartDiskNo;
/*     */   }
/*     */   
/*     */   public byte[] getExtensibleDataSector() {
/* 126 */     return this.extensibleDataSector;
/*     */   }
/*     */   
/*     */   public void setExtensibleDataSector(byte[] extensibleDataSector) {
/* 130 */     this.extensibleDataSector = extensibleDataSector;
/*     */   }
/*     */ }


/* Location:              C:\Users\DuduM\OneDrive\Área de Trabalho\mw10\modularwarfare-shining-2023.2.4.4f-fix10.jar!\net\lingala\zip4j\model\Zip64EndCentralDirRecord.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */