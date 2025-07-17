/*     */ package net.lingala.zip4j.model;
/*     */ 
/*     */ import java.util.TimeZone;
/*     */ import net.lingala.zip4j.util.InternalZipConstants;
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
/*     */ public class ZipParameters
/*     */   implements Cloneable
/*     */ {
/*  43 */   private int compressionMethod = 8;
/*     */   private boolean encryptFiles = false;
/*     */   private boolean readHiddenFiles = true;
/*  46 */   private int encryptionMethod = -1;
/*  47 */   private int aesKeyStrength = -1;
/*     */   private boolean includeRootFolder = true;
/*  49 */   private TimeZone timeZone = TimeZone.getDefault(); private int compressionLevel; private String password;
/*     */   private String rootFolderInZip;
/*     */   
/*     */   public int getCompressionMethod() {
/*  53 */     return this.compressionMethod;
/*     */   }
/*     */   private int sourceFileCRC; private String defaultFolderPath; private String fileNameInZip; private boolean isSourceExternalStream;
/*     */   public void setCompressionMethod(int compressionMethod) {
/*  57 */     this.compressionMethod = compressionMethod;
/*     */   }
/*     */   
/*     */   public boolean isEncryptFiles() {
/*  61 */     return this.encryptFiles;
/*     */   }
/*     */   
/*     */   public void setEncryptFiles(boolean encryptFiles) {
/*  65 */     this.encryptFiles = encryptFiles;
/*     */   }
/*     */   
/*     */   public int getEncryptionMethod() {
/*  69 */     return this.encryptionMethod;
/*     */   }
/*     */   
/*     */   public void setEncryptionMethod(int encryptionMethod) {
/*  73 */     this.encryptionMethod = encryptionMethod;
/*     */   }
/*     */   
/*     */   public int getCompressionLevel() {
/*  77 */     return this.compressionLevel;
/*     */   }
/*     */   
/*     */   public void setCompressionLevel(int compressionLevel) {
/*  81 */     this.compressionLevel = compressionLevel;
/*     */   }
/*     */   
/*     */   public boolean isReadHiddenFiles() {
/*  85 */     return this.readHiddenFiles;
/*     */   }
/*     */   
/*     */   public void setReadHiddenFiles(boolean readHiddenFiles) {
/*  89 */     this.readHiddenFiles = readHiddenFiles;
/*     */   }
/*     */   
/*     */   public Object clone() throws CloneNotSupportedException {
/*  93 */     return super.clone();
/*     */   }
/*     */   
/*     */   public String getPassword() {
/*  97 */     return this.password;
/*     */   }
/*     */   
/*     */   public void setPassword(String password) {
/* 101 */     this.password = password;
/*     */   }
/*     */   
/*     */   public int getAesKeyStrength() {
/* 105 */     return this.aesKeyStrength;
/*     */   }
/*     */   
/*     */   public void setAesKeyStrength(int aesKeyStrength) {
/* 109 */     this.aesKeyStrength = aesKeyStrength;
/*     */   }
/*     */   
/*     */   public boolean isIncludeRootFolder() {
/* 113 */     return this.includeRootFolder;
/*     */   }
/*     */   
/*     */   public void setIncludeRootFolder(boolean includeRootFolder) {
/* 117 */     this.includeRootFolder = includeRootFolder;
/*     */   }
/*     */   
/*     */   public String getRootFolderInZip() {
/* 121 */     return this.rootFolderInZip;
/*     */   }
/*     */   
/*     */   public void setRootFolderInZip(String rootFolderInZip) {
/* 125 */     if (Zip4jUtil.isStringNotNullAndNotEmpty(rootFolderInZip)) {
/*     */       
/* 127 */       if (!rootFolderInZip.endsWith("\\") && !rootFolderInZip.endsWith("/")) {
/* 128 */         rootFolderInZip = rootFolderInZip + InternalZipConstants.FILE_SEPARATOR;
/*     */       }
/*     */       
/* 131 */       rootFolderInZip = rootFolderInZip.replaceAll("\\\\", "/");
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 138 */     this.rootFolderInZip = rootFolderInZip;
/*     */   }
/*     */   
/*     */   public TimeZone getTimeZone() {
/* 142 */     return this.timeZone;
/*     */   }
/*     */   
/*     */   public void setTimeZone(TimeZone timeZone) {
/* 146 */     this.timeZone = timeZone;
/*     */   }
/*     */   
/*     */   public int getSourceFileCRC() {
/* 150 */     return this.sourceFileCRC;
/*     */   }
/*     */   
/*     */   public void setSourceFileCRC(int sourceFileCRC) {
/* 154 */     this.sourceFileCRC = sourceFileCRC;
/*     */   }
/*     */   
/*     */   public String getDefaultFolderPath() {
/* 158 */     return this.defaultFolderPath;
/*     */   }
/*     */   
/*     */   public void setDefaultFolderPath(String defaultFolderPath) {
/* 162 */     this.defaultFolderPath = defaultFolderPath;
/*     */   }
/*     */   
/*     */   public String getFileNameInZip() {
/* 166 */     return this.fileNameInZip;
/*     */   }
/*     */   
/*     */   public void setFileNameInZip(String fileNameInZip) {
/* 170 */     this.fileNameInZip = fileNameInZip;
/*     */   }
/*     */   
/*     */   public boolean isSourceExternalStream() {
/* 174 */     return this.isSourceExternalStream;
/*     */   }
/*     */   
/*     */   public void setSourceExternalStream(boolean isSourceExternalStream) {
/* 178 */     this.isSourceExternalStream = isSourceExternalStream;
/*     */   }
/*     */ }


/* Location:              C:\Users\DuduM\OneDrive\Área de Trabalho\mw10\modularwarfare-shining-2023.2.4.4f-fix10.jar!\net\lingala\zip4j\model\ZipParameters.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */