/*      */ package net.lingala.zip4j.core;
/*      */ 
/*      */ import java.io.File;
/*      */ import java.io.FileNotFoundException;
/*      */ import java.io.IOException;
/*      */ import java.io.InputStream;
/*      */ import java.io.RandomAccessFile;
/*      */ import java.io.UnsupportedEncodingException;
/*      */ import java.util.ArrayList;
/*      */ import java.util.List;
/*      */ import net.lingala.zip4j.exception.ZipException;
/*      */ import net.lingala.zip4j.io.ZipInputStream;
/*      */ import net.lingala.zip4j.model.FileHeader;
/*      */ import net.lingala.zip4j.model.UnzipParameters;
/*      */ import net.lingala.zip4j.model.ZipModel;
/*      */ import net.lingala.zip4j.model.ZipParameters;
/*      */ import net.lingala.zip4j.progress.ProgressMonitor;
/*      */ import net.lingala.zip4j.unzip.Unzip;
/*      */ import net.lingala.zip4j.util.ArchiveMaintainer;
/*      */ import net.lingala.zip4j.util.InternalZipConstants;
/*      */ import net.lingala.zip4j.util.Zip4jUtil;
/*      */ import net.lingala.zip4j.zip.ZipEngine;
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
/*      */ public class ZipFile
/*      */ {
/*      */   private String file;
/*      */   private int mode;
/*      */   private ZipModel zipModel;
/*      */   private boolean isEncrypted;
/*      */   private ProgressMonitor progressMonitor;
/*      */   private boolean runInThread;
/*      */   private String fileNameCharset;
/*      */   
/*      */   public ZipFile(String zipFile) throws ZipException {
/*   72 */     this(new File(zipFile));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public ZipFile(File zipFile) throws ZipException {
/*   82 */     if (zipFile == null) {
/*   83 */       throw new ZipException("Input zip file parameter is not null", 1);
/*      */     }
/*      */ 
/*      */     
/*   87 */     this.file = zipFile.getPath();
/*   88 */     this.mode = 2;
/*   89 */     this.progressMonitor = new ProgressMonitor();
/*   90 */     this.runInThread = false;
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
/*      */   public void createZipFile(File sourceFile, ZipParameters parameters) throws ZipException {
/*  102 */     ArrayList<File> sourceFileList = new ArrayList();
/*  103 */     sourceFileList.add(sourceFile);
/*  104 */     createZipFile(sourceFileList, parameters, false, -1L);
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
/*      */   public void createZipFile(File sourceFile, ZipParameters parameters, boolean splitArchive, long splitLength) throws ZipException {
/*  123 */     ArrayList<File> sourceFileList = new ArrayList();
/*  124 */     sourceFileList.add(sourceFile);
/*  125 */     createZipFile(sourceFileList, parameters, splitArchive, splitLength);
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
/*      */   public void createZipFile(ArrayList sourceFileList, ZipParameters parameters) throws ZipException {
/*  138 */     createZipFile(sourceFileList, parameters, false, -1L);
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
/*      */   public void createZipFile(ArrayList sourceFileList, ZipParameters parameters, boolean splitArchive, long splitLength) throws ZipException {
/*  157 */     if (!Zip4jUtil.isStringNotNullAndNotEmpty(this.file)) {
/*  158 */       throw new ZipException("zip file path is empty");
/*      */     }
/*      */     
/*  161 */     if (Zip4jUtil.checkFileExists(this.file)) {
/*  162 */       throw new ZipException("zip file: " + this.file + " already exists. To add files to existing zip file use addFile method");
/*      */     }
/*      */     
/*  165 */     if (sourceFileList == null) {
/*  166 */       throw new ZipException("input file ArrayList is null, cannot create zip file");
/*      */     }
/*      */     
/*  169 */     if (!Zip4jUtil.checkArrayListTypes(sourceFileList, 1)) {
/*  170 */       throw new ZipException("One or more elements in the input ArrayList is not of type File");
/*      */     }
/*      */     
/*  173 */     this.zipModel = new ZipModel();
/*  174 */     this.zipModel.setZipFile(this.file);
/*  175 */     this.zipModel.setSplitArchive(splitArchive);
/*  176 */     this.zipModel.setSplitLength(splitLength);
/*  177 */     addFiles(sourceFileList, parameters);
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
/*      */   public void createZipFileFromFolder(String folderToAdd, ZipParameters parameters, boolean splitArchive, long splitLength) throws ZipException {
/*  196 */     if (!Zip4jUtil.isStringNotNullAndNotEmpty(folderToAdd)) {
/*  197 */       throw new ZipException("folderToAdd is empty or null, cannot create Zip File from folder");
/*      */     }
/*      */     
/*  200 */     createZipFileFromFolder(new File(folderToAdd), parameters, splitArchive, splitLength);
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
/*      */   
/*      */   public void createZipFileFromFolder(File folderToAdd, ZipParameters parameters, boolean splitArchive, long splitLength) throws ZipException {
/*  220 */     if (folderToAdd == null) {
/*  221 */       throw new ZipException("folderToAdd is null, cannot create zip file from folder");
/*      */     }
/*      */     
/*  224 */     if (parameters == null) {
/*  225 */       throw new ZipException("input parameters are null, cannot create zip file from folder");
/*      */     }
/*      */     
/*  228 */     if (Zip4jUtil.checkFileExists(this.file)) {
/*  229 */       throw new ZipException("zip file: " + this.file + " already exists. To add files to existing zip file use addFolder method");
/*      */     }
/*      */     
/*  232 */     this.zipModel = new ZipModel();
/*  233 */     this.zipModel.setZipFile(this.file);
/*  234 */     this.zipModel.setSplitArchive(splitArchive);
/*  235 */     if (splitArchive) {
/*  236 */       this.zipModel.setSplitLength(splitLength);
/*      */     }
/*  238 */     addFolder(folderToAdd, parameters, false);
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
/*      */   public void addFile(File sourceFile, ZipParameters parameters) throws ZipException {
/*  250 */     ArrayList<File> sourceFileList = new ArrayList();
/*  251 */     sourceFileList.add(sourceFile);
/*  252 */     addFiles(sourceFileList, parameters);
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
/*      */   public void addFiles(ArrayList sourceFileList, ZipParameters parameters) throws ZipException {
/*  265 */     checkZipModel();
/*      */     
/*  267 */     if (this.zipModel == null) {
/*  268 */       throw new ZipException("internal error: zip model is null");
/*      */     }
/*      */     
/*  271 */     if (sourceFileList == null) {
/*  272 */       throw new ZipException("input file ArrayList is null, cannot add files");
/*      */     }
/*      */     
/*  275 */     if (!Zip4jUtil.checkArrayListTypes(sourceFileList, 1)) {
/*  276 */       throw new ZipException("One or more elements in the input ArrayList is not of type File");
/*      */     }
/*      */     
/*  279 */     if (parameters == null) {
/*  280 */       throw new ZipException("input parameters are null, cannot add files to zip");
/*      */     }
/*      */     
/*  283 */     if (this.progressMonitor.getState() == 1) {
/*  284 */       throw new ZipException("invalid operation - Zip4j is in busy state");
/*      */     }
/*      */     
/*  287 */     if (Zip4jUtil.checkFileExists(this.file) && 
/*  288 */       this.zipModel.isSplitArchive()) {
/*  289 */       throw new ZipException("Zip file already exists. Zip file format does not allow updating split/spanned files");
/*      */     }
/*      */ 
/*      */     
/*  293 */     ZipEngine zipEngine = new ZipEngine(this.zipModel);
/*  294 */     zipEngine.addFiles(sourceFileList, parameters, this.progressMonitor, this.runInThread);
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
/*      */   public void addFolder(String path, ZipParameters parameters) throws ZipException {
/*  307 */     if (!Zip4jUtil.isStringNotNullAndNotEmpty(path)) {
/*  308 */       throw new ZipException("input path is null or empty, cannot add folder to zip file");
/*      */     }
/*      */     
/*  311 */     addFolder(new File(path), parameters);
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
/*      */   public void addFolder(File path, ZipParameters parameters) throws ZipException {
/*  324 */     if (path == null) {
/*  325 */       throw new ZipException("input path is null, cannot add folder to zip file");
/*      */     }
/*      */     
/*  328 */     if (parameters == null) {
/*  329 */       throw new ZipException("input parameters are null, cannot add folder to zip file");
/*      */     }
/*      */     
/*  332 */     addFolder(path, parameters, true);
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
/*      */   private void addFolder(File path, ZipParameters parameters, boolean checkSplitArchive) throws ZipException {
/*  345 */     checkZipModel();
/*      */     
/*  347 */     if (this.zipModel == null) {
/*  348 */       throw new ZipException("internal error: zip model is null");
/*      */     }
/*      */     
/*  351 */     if (checkSplitArchive && 
/*  352 */       this.zipModel.isSplitArchive()) {
/*  353 */       throw new ZipException("This is a split archive. Zip file format does not allow updating split/spanned files");
/*      */     }
/*      */ 
/*      */     
/*  357 */     ZipEngine zipEngine = new ZipEngine(this.zipModel);
/*  358 */     zipEngine.addFolderToZip(path, parameters, this.progressMonitor, this.runInThread);
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
/*      */   public void addStream(InputStream inputStream, ZipParameters parameters) throws ZipException {
/*  372 */     if (inputStream == null) {
/*  373 */       throw new ZipException("inputstream is null, cannot add file to zip");
/*      */     }
/*      */     
/*  376 */     if (parameters == null) {
/*  377 */       throw new ZipException("zip parameters are null");
/*      */     }
/*      */     
/*  380 */     setRunInThread(false);
/*      */     
/*  382 */     checkZipModel();
/*      */     
/*  384 */     if (this.zipModel == null) {
/*  385 */       throw new ZipException("internal error: zip model is null");
/*      */     }
/*      */     
/*  388 */     if (Zip4jUtil.checkFileExists(this.file) && 
/*  389 */       this.zipModel.isSplitArchive()) {
/*  390 */       throw new ZipException("Zip file already exists. Zip file format does not allow updating split/spanned files");
/*      */     }
/*      */ 
/*      */     
/*  394 */     ZipEngine zipEngine = new ZipEngine(this.zipModel);
/*  395 */     zipEngine.addStreamToZip(inputStream, parameters);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void readZipInfo() throws ZipException {
/*  406 */     if (!Zip4jUtil.checkFileExists(this.file)) {
/*  407 */       throw new ZipException("zip file does not exist");
/*      */     }
/*      */     
/*  410 */     if (!Zip4jUtil.checkFileReadAccess(this.file)) {
/*  411 */       throw new ZipException("no read access for the input zip file");
/*      */     }
/*      */     
/*  414 */     if (this.mode != 2) {
/*  415 */       throw new ZipException("Invalid mode");
/*      */     }
/*      */     
/*  418 */     RandomAccessFile raf = null;
/*      */     try {
/*  420 */       raf = new RandomAccessFile(new File(this.file), "r");
/*      */       
/*  422 */       if (this.zipModel == null) {
/*      */         
/*  424 */         HeaderReader headerReader = new HeaderReader(raf);
/*  425 */         this.zipModel = headerReader.readAllHeaders(this.fileNameCharset);
/*  426 */         if (this.zipModel != null) {
/*  427 */           this.zipModel.setZipFile(this.file);
/*      */         }
/*      */       } 
/*  430 */     } catch (FileNotFoundException e) {
/*  431 */       throw new ZipException(e);
/*      */     } finally {
/*  433 */       if (raf != null) {
/*      */         try {
/*  435 */           raf.close();
/*  436 */         } catch (IOException e) {}
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
/*      */   public void extractAll(String destPath) throws ZipException {
/*  451 */     extractAll(destPath, null);
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
/*      */   public void extractAll(String destPath, UnzipParameters unzipParameters) throws ZipException {
/*  466 */     if (!Zip4jUtil.isStringNotNullAndNotEmpty(destPath)) {
/*  467 */       throw new ZipException("output path is null or invalid");
/*      */     }
/*      */     
/*  470 */     if (!Zip4jUtil.checkOutputFolder(destPath)) {
/*  471 */       throw new ZipException("invalid output path");
/*      */     }
/*      */     
/*  474 */     if (this.zipModel == null) {
/*  475 */       readZipInfo();
/*      */     }
/*      */ 
/*      */     
/*  479 */     if (this.zipModel == null) {
/*  480 */       throw new ZipException("Internal error occurred when extracting zip file");
/*      */     }
/*      */     
/*  483 */     if (this.progressMonitor.getState() == 1) {
/*  484 */       throw new ZipException("invalid operation - Zip4j is in busy state");
/*      */     }
/*      */     
/*  487 */     Unzip unzip = new Unzip(this.zipModel);
/*  488 */     unzip.extractAll(unzipParameters, destPath, this.progressMonitor, this.runInThread);
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
/*      */   public void extractFile(FileHeader fileHeader, String destPath) throws ZipException {
/*  500 */     extractFile(fileHeader, destPath, (UnzipParameters)null);
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
/*      */   public void extractFile(FileHeader fileHeader, String destPath, UnzipParameters unzipParameters) throws ZipException {
/*  517 */     extractFile(fileHeader, destPath, unzipParameters, (String)null);
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
/*      */   public void extractFile(FileHeader fileHeader, String destPath, UnzipParameters unzipParameters, String newFileName) throws ZipException {
/*  532 */     if (fileHeader == null) {
/*  533 */       throw new ZipException("input file header is null, cannot extract file");
/*      */     }
/*      */     
/*  536 */     if (!Zip4jUtil.isStringNotNullAndNotEmpty(destPath)) {
/*  537 */       throw new ZipException("destination path is empty or null, cannot extract file");
/*      */     }
/*      */     
/*  540 */     readZipInfo();
/*      */     
/*  542 */     if (this.progressMonitor.getState() == 1) {
/*  543 */       throw new ZipException("invalid operation - Zip4j is in busy state");
/*      */     }
/*      */     
/*  546 */     fileHeader.extractFile(this.zipModel, destPath, unzipParameters, newFileName, this.progressMonitor, this.runInThread);
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
/*      */   
/*      */   public void extractFile(String fileName, String destPath) throws ZipException {
/*  566 */     extractFile(fileName, destPath, (UnzipParameters)null);
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
/*      */ 
/*      */   
/*      */   public void extractFile(String fileName, String destPath, UnzipParameters unzipParameters) throws ZipException {
/*  587 */     extractFile(fileName, destPath, unzipParameters, (String)null);
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void extractFile(String fileName, String destPath, UnzipParameters unzipParameters, String newFileName) throws ZipException {
/*  614 */     if (!Zip4jUtil.isStringNotNullAndNotEmpty(fileName)) {
/*  615 */       throw new ZipException("file to extract is null or empty, cannot extract file");
/*      */     }
/*      */     
/*  618 */     if (!Zip4jUtil.isStringNotNullAndNotEmpty(destPath)) {
/*  619 */       throw new ZipException("destination string path is empty or null, cannot extract file");
/*      */     }
/*      */     
/*  622 */     readZipInfo();
/*      */     
/*  624 */     FileHeader fileHeader = Zip4jUtil.getFileHeader(this.zipModel, fileName);
/*      */     
/*  626 */     if (fileHeader == null) {
/*  627 */       throw new ZipException("file header not found for given file name, cannot extract file");
/*      */     }
/*      */     
/*  630 */     if (this.progressMonitor.getState() == 1) {
/*  631 */       throw new ZipException("invalid operation - Zip4j is in busy state");
/*      */     }
/*      */     
/*  634 */     fileHeader.extractFile(this.zipModel, destPath, unzipParameters, newFileName, this.progressMonitor, this.runInThread);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setPassword(String password) throws ZipException {
/*  644 */     if (this.zipModel == null) {
/*  645 */       readZipInfo();
/*  646 */       if (this.zipModel == null) {
/*  647 */         throw new ZipException("Zip Model is null");
/*      */       }
/*      */     } 
/*      */     
/*  651 */     if (this.zipModel.getCentralDirectory() == null || this.zipModel.getCentralDirectory().getFileHeaders() == null) {
/*  652 */       throw new ZipException("invalid zip file");
/*      */     }
/*      */     
/*  655 */     for (int i = 0; i < this.zipModel.getCentralDirectory().getFileHeaders().size(); i++) {
/*  656 */       if (this.zipModel.getCentralDirectory().getFileHeaders().get(i) != null && (
/*  657 */         (FileHeader)this.zipModel.getCentralDirectory().getFileHeaders().get(i)).isEncrypted()) {
/*  658 */         ((FileHeader)this.zipModel.getCentralDirectory().getFileHeaders().get(i)).setPassword(password);
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
/*      */   public List getFileHeaders() throws ZipException {
/*  671 */     readZipInfo();
/*  672 */     if (this.zipModel == null || this.zipModel.getCentralDirectory() == null) {
/*  673 */       return null;
/*      */     }
/*  675 */     return this.zipModel.getCentralDirectory().getFileHeaders();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public FileHeader getFileHeader(String fileName) throws ZipException {
/*  686 */     if (!Zip4jUtil.isStringNotNullAndNotEmpty(fileName)) {
/*  687 */       throw new ZipException("input file name is emtpy or null, cannot get FileHeader");
/*      */     }
/*      */     
/*  690 */     readZipInfo();
/*  691 */     if (this.zipModel == null || this.zipModel.getCentralDirectory() == null) {
/*  692 */       return null;
/*      */     }
/*      */     
/*  695 */     return Zip4jUtil.getFileHeader(this.zipModel, fileName);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean isEncrypted() throws ZipException {
/*  704 */     if (this.zipModel == null) {
/*  705 */       readZipInfo();
/*  706 */       if (this.zipModel == null) {
/*  707 */         throw new ZipException("Zip Model is null");
/*      */       }
/*      */     } 
/*      */     
/*  711 */     if (this.zipModel.getCentralDirectory() == null || this.zipModel.getCentralDirectory().getFileHeaders() == null) {
/*  712 */       throw new ZipException("invalid zip file");
/*      */     }
/*      */     
/*  715 */     ArrayList<FileHeader> fileHeaderList = this.zipModel.getCentralDirectory().getFileHeaders();
/*  716 */     for (int i = 0; i < fileHeaderList.size(); i++) {
/*  717 */       FileHeader fileHeader = fileHeaderList.get(i);
/*  718 */       if (fileHeader != null && 
/*  719 */         fileHeader.isEncrypted()) {
/*  720 */         this.isEncrypted = true;
/*      */         
/*      */         break;
/*      */       } 
/*      */     } 
/*      */     
/*  726 */     return this.isEncrypted;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean isSplitArchive() throws ZipException {
/*  736 */     if (this.zipModel == null) {
/*  737 */       readZipInfo();
/*  738 */       if (this.zipModel == null) {
/*  739 */         throw new ZipException("Zip Model is null");
/*      */       }
/*      */     } 
/*      */     
/*  743 */     return this.zipModel.isSplitArchive();
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
/*      */   public void removeFile(String fileName) throws ZipException {
/*  758 */     if (!Zip4jUtil.isStringNotNullAndNotEmpty(fileName)) {
/*  759 */       throw new ZipException("file name is empty or null, cannot remove file");
/*      */     }
/*      */     
/*  762 */     if (this.zipModel == null && 
/*  763 */       Zip4jUtil.checkFileExists(this.file)) {
/*  764 */       readZipInfo();
/*      */     }
/*      */ 
/*      */     
/*  768 */     if (this.zipModel.isSplitArchive()) {
/*  769 */       throw new ZipException("Zip file format does not allow updating split/spanned files");
/*      */     }
/*      */     
/*  772 */     FileHeader fileHeader = Zip4jUtil.getFileHeader(this.zipModel, fileName);
/*  773 */     if (fileHeader == null) {
/*  774 */       throw new ZipException("could not find file header for file: " + fileName);
/*      */     }
/*      */     
/*  777 */     removeFile(fileHeader);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void removeFile(FileHeader fileHeader) throws ZipException {
/*  788 */     if (fileHeader == null) {
/*  789 */       throw new ZipException("file header is null, cannot remove file");
/*      */     }
/*      */     
/*  792 */     if (this.zipModel == null && 
/*  793 */       Zip4jUtil.checkFileExists(this.file)) {
/*  794 */       readZipInfo();
/*      */     }
/*      */ 
/*      */     
/*  798 */     if (this.zipModel.isSplitArchive()) {
/*  799 */       throw new ZipException("Zip file format does not allow updating split/spanned files");
/*      */     }
/*      */     
/*  802 */     ArchiveMaintainer archiveMaintainer = new ArchiveMaintainer();
/*  803 */     archiveMaintainer.initProgressMonitorForRemoveOp(this.zipModel, fileHeader, this.progressMonitor);
/*  804 */     archiveMaintainer.removeZipFile(this.zipModel, fileHeader, this.progressMonitor, this.runInThread);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void mergeSplitFiles(File outputZipFile) throws ZipException {
/*  814 */     if (outputZipFile == null) {
/*  815 */       throw new ZipException("outputZipFile is null, cannot merge split files");
/*      */     }
/*      */     
/*  818 */     if (outputZipFile.exists()) {
/*  819 */       throw new ZipException("output Zip File already exists");
/*      */     }
/*      */     
/*  822 */     checkZipModel();
/*      */     
/*  824 */     if (this.zipModel == null) {
/*  825 */       throw new ZipException("zip model is null, corrupt zip file?");
/*      */     }
/*      */     
/*  828 */     ArchiveMaintainer archiveMaintainer = new ArchiveMaintainer();
/*  829 */     archiveMaintainer.initProgressMonitorForMergeOp(this.zipModel, this.progressMonitor);
/*  830 */     archiveMaintainer.mergeSplitZipFiles(this.zipModel, outputZipFile, this.progressMonitor, this.runInThread);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setComment(String comment) throws ZipException {
/*  839 */     if (comment == null) {
/*  840 */       throw new ZipException("input comment is null, cannot update zip file");
/*      */     }
/*      */     
/*  843 */     if (!Zip4jUtil.checkFileExists(this.file)) {
/*  844 */       throw new ZipException("zip file does not exist, cannot set comment for zip file");
/*      */     }
/*      */     
/*  847 */     readZipInfo();
/*      */     
/*  849 */     if (this.zipModel == null) {
/*  850 */       throw new ZipException("zipModel is null, cannot update zip file");
/*      */     }
/*      */     
/*  853 */     if (this.zipModel.getEndCentralDirRecord() == null) {
/*  854 */       throw new ZipException("end of central directory is null, cannot set comment");
/*      */     }
/*      */     
/*  857 */     ArchiveMaintainer archiveMaintainer = new ArchiveMaintainer();
/*  858 */     archiveMaintainer.setComment(this.zipModel, comment);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String getComment() throws ZipException {
/*  867 */     return getComment(null);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String getComment(String encoding) throws ZipException {
/*  877 */     if (encoding == null) {
/*  878 */       encoding = InternalZipConstants.CHARSET_DEFAULT;
/*      */     }
/*      */     
/*  881 */     if (Zip4jUtil.checkFileExists(this.file)) {
/*  882 */       checkZipModel();
/*      */     } else {
/*  884 */       throw new ZipException("zip file does not exist, cannot read comment");
/*      */     } 
/*      */     
/*  887 */     if (this.zipModel == null) {
/*  888 */       throw new ZipException("zip model is null, cannot read comment");
/*      */     }
/*      */     
/*  891 */     if (this.zipModel.getEndCentralDirRecord() == null) {
/*  892 */       throw new ZipException("end of central directory record is null, cannot read comment");
/*      */     }
/*      */     
/*  895 */     if (this.zipModel.getEndCentralDirRecord().getCommentBytes() == null || (this.zipModel.getEndCentralDirRecord().getCommentBytes()).length <= 0)
/*      */     {
/*  897 */       return null;
/*      */     }
/*      */     
/*      */     try {
/*  901 */       return new String(this.zipModel.getEndCentralDirRecord().getCommentBytes(), encoding);
/*  902 */     } catch (UnsupportedEncodingException e) {
/*  903 */       throw new ZipException(e);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void checkZipModel() throws ZipException {
/*  912 */     if (this.zipModel == null) {
/*  913 */       if (Zip4jUtil.checkFileExists(this.file)) {
/*  914 */         readZipInfo();
/*      */       } else {
/*  916 */         this.zipModel = new ZipModel();
/*  917 */         this.zipModel.setZipFile(this.file);
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
/*      */   public void setFileNameCharset(String charsetName) throws ZipException {
/*  929 */     if (!Zip4jUtil.isStringNotNullAndNotEmpty(charsetName)) {
/*  930 */       throw new ZipException("null or empty charset name");
/*      */     }
/*      */     
/*  933 */     if (!Zip4jUtil.isSupportedCharset(charsetName)) {
/*  934 */       throw new ZipException("unsupported charset: " + charsetName);
/*      */     }
/*      */     
/*  937 */     this.fileNameCharset = charsetName;
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
/*      */   public ZipInputStream getInputStream(FileHeader fileHeader) throws ZipException {
/*  949 */     if (fileHeader == null) {
/*  950 */       throw new ZipException("FileHeader is null, cannot get InputStream");
/*      */     }
/*      */     
/*  953 */     checkZipModel();
/*      */     
/*  955 */     if (this.zipModel == null) {
/*  956 */       throw new ZipException("zip model is null, cannot get inputstream");
/*      */     }
/*      */     
/*  959 */     Unzip unzip = new Unzip(this.zipModel);
/*  960 */     return unzip.getInputStream(fileHeader);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean isValidZipFile() {
/*      */     try {
/*  972 */       readZipInfo();
/*  973 */       return true;
/*  974 */     } catch (Exception e) {
/*  975 */       return false;
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
/*      */   public ArrayList getSplitZipFiles() throws ZipException {
/*  988 */     checkZipModel();
/*  989 */     return Zip4jUtil.getSplitZipFiles(this.zipModel);
/*      */   }
/*      */   
/*      */   public ProgressMonitor getProgressMonitor() {
/*  993 */     return this.progressMonitor;
/*      */   }
/*      */   
/*      */   public boolean isRunInThread() {
/*  997 */     return this.runInThread;
/*      */   }
/*      */   
/*      */   public void setRunInThread(boolean runInThread) {
/* 1001 */     this.runInThread = runInThread;
/*      */   }
/*      */ }


/* Location:              C:\Users\DuduM\OneDrive\Área de Trabalho\mw10\modularwarfare-shining-2023.2.4.4f-fix10.jar!\net\lingala\zip4j\core\ZipFile.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */