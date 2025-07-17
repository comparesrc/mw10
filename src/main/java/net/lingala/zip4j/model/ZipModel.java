/*     */ package net.lingala.zip4j.model;
/*     */ 
/*     */ import java.util.List;
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
/*     */ public class ZipModel
/*     */   implements Cloneable
/*     */ {
/*     */   private List localFileHeaderList;
/*     */   private List dataDescriptorList;
/*     */   private ArchiveExtraDataRecord archiveExtraDataRecord;
/*     */   private CentralDirectory centralDirectory;
/*     */   private EndCentralDirRecord endCentralDirRecord;
/*     */   private Zip64EndCentralDirLocator zip64EndCentralDirLocator;
/*     */   private Zip64EndCentralDirRecord zip64EndCentralDirRecord;
/*     */   private boolean splitArchive;
/*  54 */   private long splitLength = -1L; private String zipFile; private boolean isZip64Format;
/*     */   private boolean isNestedZipFile;
/*     */   
/*     */   public List getLocalFileHeaderList() {
/*  58 */     return this.localFileHeaderList;
/*     */   }
/*     */   private long start; private long end; private String fileNameCharset;
/*     */   public void setLocalFileHeaderList(List localFileHeaderList) {
/*  62 */     this.localFileHeaderList = localFileHeaderList;
/*     */   }
/*     */   
/*     */   public List getDataDescriptorList() {
/*  66 */     return this.dataDescriptorList;
/*     */   }
/*     */   
/*     */   public void setDataDescriptorList(List dataDescriptorList) {
/*  70 */     this.dataDescriptorList = dataDescriptorList;
/*     */   }
/*     */   
/*     */   public CentralDirectory getCentralDirectory() {
/*  74 */     return this.centralDirectory;
/*     */   }
/*     */   
/*     */   public void setCentralDirectory(CentralDirectory centralDirectory) {
/*  78 */     this.centralDirectory = centralDirectory;
/*     */   }
/*     */   
/*     */   public EndCentralDirRecord getEndCentralDirRecord() {
/*  82 */     return this.endCentralDirRecord;
/*     */   }
/*     */   
/*     */   public void setEndCentralDirRecord(EndCentralDirRecord endCentralDirRecord) {
/*  86 */     this.endCentralDirRecord = endCentralDirRecord;
/*     */   }
/*     */   
/*     */   public ArchiveExtraDataRecord getArchiveExtraDataRecord() {
/*  90 */     return this.archiveExtraDataRecord;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setArchiveExtraDataRecord(ArchiveExtraDataRecord archiveExtraDataRecord) {
/*  95 */     this.archiveExtraDataRecord = archiveExtraDataRecord;
/*     */   }
/*     */   
/*     */   public boolean isSplitArchive() {
/*  99 */     return this.splitArchive;
/*     */   }
/*     */   
/*     */   public void setSplitArchive(boolean splitArchive) {
/* 103 */     this.splitArchive = splitArchive;
/*     */   }
/*     */   
/*     */   public String getZipFile() {
/* 107 */     return this.zipFile;
/*     */   }
/*     */   
/*     */   public void setZipFile(String zipFile) {
/* 111 */     this.zipFile = zipFile;
/*     */   }
/*     */   
/*     */   public Zip64EndCentralDirLocator getZip64EndCentralDirLocator() {
/* 115 */     return this.zip64EndCentralDirLocator;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setZip64EndCentralDirLocator(Zip64EndCentralDirLocator zip64EndCentralDirLocator) {
/* 120 */     this.zip64EndCentralDirLocator = zip64EndCentralDirLocator;
/*     */   }
/*     */   
/*     */   public Zip64EndCentralDirRecord getZip64EndCentralDirRecord() {
/* 124 */     return this.zip64EndCentralDirRecord;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setZip64EndCentralDirRecord(Zip64EndCentralDirRecord zip64EndCentralDirRecord) {
/* 129 */     this.zip64EndCentralDirRecord = zip64EndCentralDirRecord;
/*     */   }
/*     */   
/*     */   public boolean isZip64Format() {
/* 133 */     return this.isZip64Format;
/*     */   }
/*     */   
/*     */   public void setZip64Format(boolean isZip64Format) {
/* 137 */     this.isZip64Format = isZip64Format;
/*     */   }
/*     */   
/*     */   public boolean isNestedZipFile() {
/* 141 */     return this.isNestedZipFile;
/*     */   }
/*     */   
/*     */   public void setNestedZipFile(boolean isNestedZipFile) {
/* 145 */     this.isNestedZipFile = isNestedZipFile;
/*     */   }
/*     */   
/*     */   public long getStart() {
/* 149 */     return this.start;
/*     */   }
/*     */   
/*     */   public void setStart(long start) {
/* 153 */     this.start = start;
/*     */   }
/*     */   
/*     */   public long getEnd() {
/* 157 */     return this.end;
/*     */   }
/*     */   
/*     */   public void setEnd(long end) {
/* 161 */     this.end = end;
/*     */   }
/*     */   
/*     */   public long getSplitLength() {
/* 165 */     return this.splitLength;
/*     */   }
/*     */   
/*     */   public void setSplitLength(long splitLength) {
/* 169 */     this.splitLength = splitLength;
/*     */   }
/*     */   
/*     */   public Object clone() throws CloneNotSupportedException {
/* 173 */     return super.clone();
/*     */   }
/*     */   
/*     */   public String getFileNameCharset() {
/* 177 */     return this.fileNameCharset;
/*     */   }
/*     */   
/*     */   public void setFileNameCharset(String fileNameCharset) {
/* 181 */     this.fileNameCharset = fileNameCharset;
/*     */   }
/*     */ }


/* Location:              C:\Users\DuduM\OneDrive\Área de Trabalho\mw10\modularwarfare-shining-2023.2.4.4f-fix10.jar!\net\lingala\zip4j\model\ZipModel.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */