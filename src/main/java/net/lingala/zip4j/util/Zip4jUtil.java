/*     */ package net.lingala.zip4j.util;
/*     */ 
/*     */ import java.io.File;
/*     */ import java.io.IOException;
/*     */ import java.io.UnsupportedEncodingException;
/*     */ import java.nio.ByteBuffer;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Arrays;
/*     */ import java.util.Calendar;
/*     */ import java.util.List;
/*     */ import java.util.TimeZone;
/*     */ import net.lingala.zip4j.exception.ZipException;
/*     */ import net.lingala.zip4j.model.FileHeader;
/*     */ import net.lingala.zip4j.model.ZipModel;
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
/*     */ public class Zip4jUtil
/*     */ {
/*     */   public static boolean isStringNotNullAndNotEmpty(String str) {
/*  36 */     if (str == null || str.trim().length() <= 0) {
/*  37 */       return false;
/*     */     }
/*     */     
/*  40 */     return true;
/*     */   }
/*     */   
/*     */   public static boolean checkOutputFolder(String path) throws ZipException {
/*  44 */     if (!isStringNotNullAndNotEmpty(path)) {
/*  45 */       throw new ZipException(new NullPointerException("output path is null"));
/*     */     }
/*     */     
/*  48 */     File file = new File(path);
/*     */     
/*  50 */     if (file.exists()) {
/*     */       
/*  52 */       if (!file.isDirectory()) {
/*  53 */         throw new ZipException("output folder is not valid");
/*     */       }
/*     */       
/*  56 */       if (!file.canWrite()) {
/*  57 */         throw new ZipException("no write access to output folder");
/*     */       }
/*     */     } else {
/*     */       try {
/*  61 */         file.mkdirs();
/*  62 */         if (!file.isDirectory()) {
/*  63 */           throw new ZipException("output folder is not valid");
/*     */         }
/*     */         
/*  66 */         if (!file.canWrite()) {
/*  67 */           throw new ZipException("no write access to destination folder");
/*     */ 
/*     */ 
/*     */ 
/*     */         
/*     */         }
/*     */ 
/*     */ 
/*     */       
/*     */       }
/*  77 */       catch (Exception e) {
/*  78 */         throw new ZipException("Cannot create destination folder");
/*     */       } 
/*     */     } 
/*     */     
/*  82 */     return true;
/*     */   }
/*     */   
/*     */   public static boolean checkFileReadAccess(String path) throws ZipException {
/*  86 */     if (!isStringNotNullAndNotEmpty(path)) {
/*  87 */       throw new ZipException("path is null");
/*     */     }
/*     */     
/*  90 */     if (!checkFileExists(path)) {
/*  91 */       throw new ZipException("zip file does not exist");
/*     */     }
/*     */     
/*     */     try {
/*  95 */       File file = new File(path);
/*  96 */       return file.canRead();
/*  97 */     } catch (Exception e) {
/*  98 */       throw new ZipException("cannot read zip file");
/*     */     } 
/*     */   }
/*     */   
/*     */   public static boolean checkFileWriteAccess(String path) throws ZipException {
/* 103 */     if (!isStringNotNullAndNotEmpty(path)) {
/* 104 */       throw new ZipException("path is null");
/*     */     }
/*     */     
/* 107 */     if (!checkFileExists(path)) {
/* 108 */       throw new ZipException("zip file does not exist");
/*     */     }
/*     */     
/*     */     try {
/* 112 */       File file = new File(path);
/* 113 */       return file.canWrite();
/* 114 */     } catch (Exception e) {
/* 115 */       throw new ZipException("cannot read zip file");
/*     */     } 
/*     */   }
/*     */   
/*     */   public static boolean checkFileExists(String path) throws ZipException {
/* 120 */     if (!isStringNotNullAndNotEmpty(path)) {
/* 121 */       throw new ZipException("path is null");
/*     */     }
/*     */     
/* 124 */     File file = new File(path);
/* 125 */     return checkFileExists(file);
/*     */   }
/*     */   
/*     */   public static boolean checkFileExists(File file) throws ZipException {
/* 129 */     if (file == null) {
/* 130 */       throw new ZipException("cannot check if file exists: input file is null");
/*     */     }
/* 132 */     return file.exists();
/*     */   }
/*     */   
/*     */   public static boolean isWindows() {
/* 136 */     String os = System.getProperty("os.name").toLowerCase();
/* 137 */     return (os.indexOf("win") >= 0);
/*     */   }
/*     */   
/*     */   public static void setFileReadOnly(File file) throws ZipException {
/* 141 */     if (file == null) {
/* 142 */       throw new ZipException("input file is null. cannot set read only file attribute");
/*     */     }
/*     */     
/* 145 */     if (file.exists()) {
/* 146 */       file.setReadOnly();
/*     */     }
/*     */   }
/*     */   
/*     */   public static void setFileHidden(File file) throws ZipException {
/* 151 */     if (file == null) {
/* 152 */       throw new ZipException("input file is null. cannot set hidden file attribute");
/*     */     }
/*     */     
/* 155 */     if (!isWindows()) {
/*     */       return;
/*     */     }
/*     */     
/* 159 */     if (file.exists()) {
/*     */       try {
/* 161 */         Runtime.getRuntime().exec("attrib +H \"" + file.getAbsolutePath() + "\"");
/* 162 */       } catch (IOException e) {}
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void setFileArchive(File file) throws ZipException {
/* 170 */     if (file == null) {
/* 171 */       throw new ZipException("input file is null. cannot set archive file attribute");
/*     */     }
/*     */     
/* 174 */     if (!isWindows()) {
/*     */       return;
/*     */     }
/*     */     
/* 178 */     if (file.exists()) {
/*     */       try {
/* 180 */         if (file.isDirectory()) {
/* 181 */           Runtime.getRuntime().exec("attrib +A \"" + file.getAbsolutePath() + "\"");
/*     */         } else {
/* 183 */           Runtime.getRuntime().exec("attrib +A \"" + file.getAbsolutePath() + "\"");
/*     */         }
/*     */       
/* 186 */       } catch (IOException e) {}
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void setFileSystemMode(File file) throws ZipException {
/* 194 */     if (file == null) {
/* 195 */       throw new ZipException("input file is null. cannot set archive file attribute");
/*     */     }
/*     */     
/* 198 */     if (!isWindows()) {
/*     */       return;
/*     */     }
/*     */     
/* 202 */     if (file.exists()) {
/*     */       try {
/* 204 */         Runtime.getRuntime().exec("attrib +S \"" + file.getAbsolutePath() + "\"");
/* 205 */       } catch (IOException e) {}
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static long getLastModifiedFileTime(File file, TimeZone timeZone) throws ZipException {
/* 213 */     if (file == null) {
/* 214 */       throw new ZipException("input file is null, cannot read last modified file time");
/*     */     }
/*     */     
/* 217 */     if (!file.exists()) {
/* 218 */       throw new ZipException("input file does not exist, cannot read last modified file time");
/*     */     }
/*     */     
/* 221 */     return getDayLightSavingsTime(timeZone, file.lastModified());
/*     */   }
/*     */   
/*     */   private static long getDayLightSavingsTime(TimeZone timeZone, long time) throws ZipException {
/*     */     try {
/* 226 */       if (timeZone == null) {
/* 227 */         timeZone = TimeZone.getDefault();
/*     */       }
/* 229 */       if (timeZone != null && 
/* 230 */         timeZone.getDSTSavings() > 0) {
/* 231 */         time -= (timeZone.getOffset(time) - timeZone.getDSTSavings());
/*     */       }
/*     */     }
/* 234 */     catch (Exception e) {}
/*     */ 
/*     */ 
/*     */     
/* 238 */     return time;
/*     */   }
/*     */   
/*     */   public static long getLocalTimeFromDSTTime(TimeZone timeZone, long time) throws ZipException {
/*     */     try {
/* 243 */       if (timeZone == null) {
/* 244 */         timeZone = TimeZone.getDefault();
/*     */       }
/* 246 */       if (timeZone != null && 
/* 247 */         timeZone.getDSTSavings() > 0) {
/* 248 */         time += (timeZone.getOffset(time) - timeZone.getDSTSavings());
/*     */       }
/*     */     }
/* 251 */     catch (Exception e) {}
/*     */ 
/*     */ 
/*     */     
/* 255 */     return time;
/*     */   }
/*     */   
/*     */   public static String getFileNameFromFilePath(File file) throws ZipException {
/* 259 */     if (file == null) {
/* 260 */       throw new ZipException("input file is null, cannot get file name");
/*     */     }
/*     */     
/* 263 */     if (file.isDirectory()) {
/* 264 */       return null;
/*     */     }
/*     */     
/* 267 */     return file.getName();
/*     */   }
/*     */   
/*     */   public static long getFileLengh(String file) throws ZipException {
/* 271 */     if (!isStringNotNullAndNotEmpty(file)) {
/* 272 */       throw new ZipException("invalid file name");
/*     */     }
/*     */     
/* 275 */     return getFileLengh(new File(file));
/*     */   }
/*     */   
/*     */   public static long getFileLengh(File file) throws ZipException {
/* 279 */     if (file == null) {
/* 280 */       throw new ZipException("input file is null, cannot calculate file length");
/*     */     }
/*     */     
/* 283 */     if (file.isDirectory()) {
/* 284 */       return -1L;
/*     */     }
/*     */     
/* 287 */     return file.length();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static long javaToDosTime(long time) {
/* 297 */     Calendar cal = Calendar.getInstance();
/* 298 */     cal.setTimeInMillis(time);
/*     */     
/* 300 */     int year = cal.get(1);
/* 301 */     if (year < 1980) {
/* 302 */       return 2162688L;
/*     */     }
/* 304 */     return (year - 1980 << 25 | cal.get(2) + 1 << 21 | cal.get(5) << 16 | cal.get(11) << 11 | cal.get(12) << 5 | cal.get(13) >> 1);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static long dosToJavaTme(int dosTime) {
/* 315 */     int sec = 2 * (dosTime & 0x1F);
/* 316 */     int min = dosTime >> 5 & 0x3F;
/* 317 */     int hrs = dosTime >> 11 & 0x1F;
/* 318 */     int day = dosTime >> 16 & 0x1F;
/* 319 */     int mon = (dosTime >> 21 & 0xF) - 1;
/* 320 */     int year = (dosTime >> 25 & 0x7F) + 1980;
/*     */     
/* 322 */     Calendar cal = Calendar.getInstance();
/* 323 */     cal.set(year, mon, day, hrs, min, sec);
/* 324 */     return cal.getTime().getTime();
/*     */   }
/*     */   
/*     */   public static FileHeader getFileHeader(ZipModel zipModel, String fileName) throws ZipException {
/* 328 */     if (zipModel == null) {
/* 329 */       throw new ZipException("zip model is null, cannot determine file header for fileName: " + fileName);
/*     */     }
/*     */     
/* 332 */     if (!isStringNotNullAndNotEmpty(fileName)) {
/* 333 */       throw new ZipException("file name is null, cannot determine file header for fileName: " + fileName);
/*     */     }
/*     */     
/* 336 */     FileHeader fileHeader = null;
/* 337 */     fileHeader = getFileHeaderWithExactMatch(zipModel, fileName);
/*     */     
/* 339 */     if (fileHeader == null) {
/* 340 */       fileName = fileName.replaceAll("\\\\", "/");
/* 341 */       fileHeader = getFileHeaderWithExactMatch(zipModel, fileName);
/*     */       
/* 343 */       if (fileHeader == null) {
/* 344 */         fileName = fileName.replaceAll("/", "\\\\");
/* 345 */         fileHeader = getFileHeaderWithExactMatch(zipModel, fileName);
/*     */       } 
/*     */     } 
/*     */     
/* 349 */     return fileHeader;
/*     */   }
/*     */   
/*     */   public static FileHeader getFileHeaderWithExactMatch(ZipModel zipModel, String fileName) throws ZipException {
/* 353 */     if (zipModel == null) {
/* 354 */       throw new ZipException("zip model is null, cannot determine file header with exact match for fileName: " + fileName);
/*     */     }
/*     */     
/* 357 */     if (!isStringNotNullAndNotEmpty(fileName)) {
/* 358 */       throw new ZipException("file name is null, cannot determine file header with exact match for fileName: " + fileName);
/*     */     }
/*     */     
/* 361 */     if (zipModel.getCentralDirectory() == null) {
/* 362 */       throw new ZipException("central directory is null, cannot determine file header with exact match for fileName: " + fileName);
/*     */     }
/*     */     
/* 365 */     if (zipModel.getCentralDirectory().getFileHeaders() == null) {
/* 366 */       throw new ZipException("file Headers are null, cannot determine file header with exact match for fileName: " + fileName);
/*     */     }
/*     */     
/* 369 */     if (zipModel.getCentralDirectory().getFileHeaders().size() <= 0) {
/* 370 */       return null;
/*     */     }
/* 372 */     ArrayList<FileHeader> fileHeaders = zipModel.getCentralDirectory().getFileHeaders();
/* 373 */     for (int i = 0; i < fileHeaders.size(); i++) {
/* 374 */       FileHeader fileHeader = fileHeaders.get(i);
/* 375 */       String fileNameForHdr = fileHeader.getFileName();
/* 376 */       if (isStringNotNullAndNotEmpty(fileNameForHdr))
/*     */       {
/*     */ 
/*     */         
/* 380 */         if (fileName.equalsIgnoreCase(fileNameForHdr)) {
/* 381 */           return fileHeader;
/*     */         }
/*     */       }
/*     */     } 
/* 385 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static int getIndexOfFileHeader(ZipModel zipModel, FileHeader fileHeader) throws ZipException {
/* 391 */     if (zipModel == null || fileHeader == null) {
/* 392 */       throw new ZipException("input parameters is null, cannot determine index of file header");
/*     */     }
/*     */     
/* 395 */     if (zipModel.getCentralDirectory() == null) {
/* 396 */       throw new ZipException("central directory is null, ccannot determine index of file header");
/*     */     }
/*     */     
/* 399 */     if (zipModel.getCentralDirectory().getFileHeaders() == null) {
/* 400 */       throw new ZipException("file Headers are null, cannot determine index of file header");
/*     */     }
/*     */     
/* 403 */     if (zipModel.getCentralDirectory().getFileHeaders().size() <= 0) {
/* 404 */       return -1;
/*     */     }
/* 406 */     String fileName = fileHeader.getFileName();
/*     */     
/* 408 */     if (!isStringNotNullAndNotEmpty(fileName)) {
/* 409 */       throw new ZipException("file name in file header is empty or null, cannot determine index of file header");
/*     */     }
/*     */     
/* 412 */     ArrayList<FileHeader> fileHeaders = zipModel.getCentralDirectory().getFileHeaders();
/* 413 */     for (int i = 0; i < fileHeaders.size(); i++) {
/* 414 */       FileHeader fileHeaderTmp = fileHeaders.get(i);
/* 415 */       String fileNameForHdr = fileHeaderTmp.getFileName();
/* 416 */       if (isStringNotNullAndNotEmpty(fileNameForHdr))
/*     */       {
/*     */ 
/*     */         
/* 420 */         if (fileName.equalsIgnoreCase(fileNameForHdr))
/* 421 */           return i; 
/*     */       }
/*     */     } 
/* 424 */     return -1;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static ArrayList getFilesInDirectoryRec(File path, boolean readHiddenFiles) throws ZipException {
/* 430 */     if (path == null) {
/* 431 */       throw new ZipException("input path is null, cannot read files in the directory");
/*     */     }
/*     */     
/* 434 */     ArrayList<File> result = new ArrayList();
/* 435 */     File[] filesAndDirs = path.listFiles();
/* 436 */     List<File> filesDirs = Arrays.asList(filesAndDirs);
/*     */     
/* 438 */     if (!path.canRead()) {
/* 439 */       return result;
/*     */     }
/*     */     
/* 442 */     for (int i = 0; i < filesDirs.size(); i++) {
/* 443 */       File file = filesDirs.get(i);
/* 444 */       if (file.isHidden() && !readHiddenFiles) {
/* 445 */         return result;
/*     */       }
/* 447 */       result.add(file);
/* 448 */       if (file.isDirectory()) {
/* 449 */         List<? extends File> deeperList = getFilesInDirectoryRec(file, readHiddenFiles);
/* 450 */         result.addAll(deeperList);
/*     */       } 
/*     */     } 
/* 453 */     return result;
/*     */   }
/*     */   
/*     */   public static String getZipFileNameWithoutExt(String zipFile) throws ZipException {
/* 457 */     if (!isStringNotNullAndNotEmpty(zipFile)) {
/* 458 */       throw new ZipException("zip file name is empty or null, cannot determine zip file name");
/*     */     }
/* 460 */     String tmpFileName = zipFile;
/* 461 */     if (zipFile.indexOf(System.getProperty("file.separator")) >= 0) {
/* 462 */       tmpFileName = zipFile.substring(zipFile.lastIndexOf(System.getProperty("file.separator")));
/*     */     }
/*     */     
/* 465 */     if (tmpFileName.indexOf(".") > 0) {
/* 466 */       tmpFileName = tmpFileName.substring(0, tmpFileName.indexOf("."));
/*     */     }
/* 468 */     return tmpFileName;
/*     */   }
/*     */   
/*     */   public static byte[] convertCharset(String str) throws ZipException {
/*     */     try {
/* 473 */       byte[] converted = null;
/* 474 */       String charSet = detectCharSet(str);
/* 475 */       if (charSet.equals("Cp850")) {
/* 476 */         converted = str.getBytes("Cp850");
/* 477 */       } else if (charSet.equals("UTF8")) {
/* 478 */         converted = str.getBytes("UTF8");
/*     */       } else {
/* 480 */         converted = str.getBytes();
/*     */       } 
/* 482 */       return converted;
/*     */     }
/* 484 */     catch (UnsupportedEncodingException err) {
/* 485 */       return str.getBytes();
/* 486 */     } catch (Exception e) {
/* 487 */       throw new ZipException(e);
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
/*     */   public static String decodeFileName(byte[] data, boolean isUTF8) {
/* 501 */     if (isUTF8) {
/*     */       try {
/* 503 */         return new String(data, "UTF8");
/* 504 */       } catch (UnsupportedEncodingException e) {
/* 505 */         return new String(data);
/*     */       } 
/*     */     }
/* 508 */     return getCp850EncodedString(data);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static String getCp850EncodedString(byte[] data) {
/*     */     try {
/* 520 */       String retString = new String(data, "Cp850");
/* 521 */       return retString;
/* 522 */     } catch (UnsupportedEncodingException e) {
/* 523 */       return new String(data);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static String getAbsoluteFilePath(String filePath) throws ZipException {
/* 533 */     if (!isStringNotNullAndNotEmpty(filePath)) {
/* 534 */       throw new ZipException("filePath is null or empty, cannot get absolute file path");
/*     */     }
/*     */     
/* 537 */     File file = new File(filePath);
/* 538 */     return file.getAbsolutePath();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static boolean checkArrayListTypes(ArrayList sourceList, int type) throws ZipException {
/*     */     int i;
/* 549 */     if (sourceList == null) {
/* 550 */       throw new ZipException("input arraylist is null, cannot check types");
/*     */     }
/*     */     
/* 553 */     if (sourceList.size() <= 0) {
/* 554 */       return true;
/*     */     }
/*     */     
/* 557 */     boolean invalidFound = false;
/*     */     
/* 559 */     switch (type) {
/*     */       case 1:
/* 561 */         for (i = 0; i < sourceList.size(); i++) {
/* 562 */           if (!(sourceList.get(i) instanceof File)) {
/* 563 */             invalidFound = true;
/*     */             break;
/*     */           } 
/*     */         } 
/*     */         break;
/*     */       case 2:
/* 569 */         for (i = 0; i < sourceList.size(); i++) {
/* 570 */           if (!(sourceList.get(i) instanceof String)) {
/* 571 */             invalidFound = true;
/*     */             break;
/*     */           } 
/*     */         } 
/*     */         break;
/*     */     } 
/*     */ 
/*     */     
/* 579 */     return !invalidFound;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static String detectCharSet(String str) throws ZipException {
/* 590 */     if (str == null) {
/* 591 */       throw new ZipException("input string is null, cannot detect charset");
/*     */     }
/*     */     
/*     */     try {
/* 595 */       byte[] byteString = str.getBytes("Cp850");
/* 596 */       String tempString = new String(byteString, "Cp850");
/*     */       
/* 598 */       if (str.equals(tempString)) {
/* 599 */         return "Cp850";
/*     */       }
/*     */       
/* 602 */       byteString = str.getBytes("UTF8");
/* 603 */       tempString = new String(byteString, "UTF8");
/*     */       
/* 605 */       if (str.equals(tempString)) {
/* 606 */         return "UTF8";
/*     */       }
/*     */       
/* 609 */       return InternalZipConstants.CHARSET_DEFAULT;
/* 610 */     } catch (UnsupportedEncodingException e) {
/* 611 */       return InternalZipConstants.CHARSET_DEFAULT;
/* 612 */     } catch (Exception e) {
/* 613 */       return InternalZipConstants.CHARSET_DEFAULT;
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
/*     */   public static int getEncodedStringLength(String str) throws ZipException {
/* 626 */     if (!isStringNotNullAndNotEmpty(str)) {
/* 627 */       throw new ZipException("input string is null, cannot calculate encoded String length");
/*     */     }
/*     */     
/* 630 */     String charset = detectCharSet(str);
/* 631 */     return getEncodedStringLength(str, charset);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static int getEncodedStringLength(String str, String charset) throws ZipException {
/* 642 */     if (!isStringNotNullAndNotEmpty(str)) {
/* 643 */       throw new ZipException("input string is null, cannot calculate encoded String length");
/*     */     }
/*     */     
/* 646 */     if (!isStringNotNullAndNotEmpty(charset)) {
/* 647 */       throw new ZipException("encoding is not defined, cannot calculate string length");
/*     */     }
/*     */     
/* 650 */     ByteBuffer byteBuffer = null;
/*     */     
/*     */     try {
/* 653 */       if (charset.equals("Cp850")) {
/* 654 */         byteBuffer = ByteBuffer.wrap(str.getBytes("Cp850"));
/* 655 */       } else if (charset.equals("UTF8")) {
/* 656 */         byteBuffer = ByteBuffer.wrap(str.getBytes("UTF8"));
/*     */       } else {
/* 658 */         byteBuffer = ByteBuffer.wrap(str.getBytes(charset));
/*     */       } 
/* 660 */     } catch (UnsupportedEncodingException e) {
/* 661 */       byteBuffer = ByteBuffer.wrap(str.getBytes());
/* 662 */     } catch (Exception e) {
/* 663 */       throw new ZipException(e);
/*     */     } 
/*     */     
/* 666 */     return byteBuffer.limit();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static boolean isSupportedCharset(String charset) throws ZipException {
/* 676 */     if (!isStringNotNullAndNotEmpty(charset)) {
/* 677 */       throw new ZipException("charset is null or empty, cannot check if it is supported");
/*     */     }
/*     */     
/*     */     try {
/* 681 */       new String("a".getBytes(), charset);
/* 682 */       return true;
/* 683 */     } catch (UnsupportedEncodingException e) {
/* 684 */       return false;
/* 685 */     } catch (Exception e) {
/* 686 */       throw new ZipException(e);
/*     */     } 
/*     */   }
/*     */   
/*     */   public static ArrayList getSplitZipFiles(ZipModel zipModel) throws ZipException {
/* 691 */     if (zipModel == null) {
/* 692 */       throw new ZipException("cannot get split zip files: zipmodel is null");
/*     */     }
/*     */     
/* 695 */     if (zipModel.getEndCentralDirRecord() == null) {
/* 696 */       return null;
/*     */     }
/*     */     
/* 699 */     ArrayList<String> retList = new ArrayList();
/* 700 */     String currZipFile = zipModel.getZipFile();
/* 701 */     String partFile = null;
/*     */     
/* 703 */     if (!isStringNotNullAndNotEmpty(currZipFile)) {
/* 704 */       throw new ZipException("cannot get split zip files: zipfile is null");
/*     */     }
/*     */     
/* 707 */     if (!zipModel.isSplitArchive()) {
/* 708 */       retList.add(currZipFile);
/* 709 */       return retList;
/*     */     } 
/*     */     
/* 712 */     int numberOfThisDisk = zipModel.getEndCentralDirRecord().getNoOfThisDisk();
/*     */     
/* 714 */     if (numberOfThisDisk == 0) {
/* 715 */       retList.add(currZipFile);
/* 716 */       return retList;
/*     */     } 
/* 718 */     for (int i = 0; i <= numberOfThisDisk; i++) {
/* 719 */       if (i == numberOfThisDisk) {
/* 720 */         retList.add(zipModel.getZipFile());
/*     */       } else {
/* 722 */         if (i >= 9) {
/* 723 */           partFile = currZipFile.substring(0, currZipFile.lastIndexOf(".")) + ".z" + (i + 1);
/*     */         } else {
/* 725 */           partFile = currZipFile.substring(0, currZipFile.lastIndexOf(".")) + ".z0" + (i + 1);
/*     */         } 
/* 727 */         retList.add(partFile);
/*     */       } 
/*     */     } 
/*     */     
/* 731 */     return retList;
/*     */   }
/*     */   
/*     */   public static String getRelativeFileName(String file, String rootFolderInZip, String rootFolderPath) throws ZipException {
/* 735 */     if (!isStringNotNullAndNotEmpty(file)) {
/* 736 */       throw new ZipException("input file path/name is empty, cannot calculate relative file name");
/*     */     }
/*     */     
/* 739 */     String fileName = null;
/*     */     
/* 741 */     if (isStringNotNullAndNotEmpty(rootFolderPath)) {
/* 742 */       String tmpFileName = file.substring(rootFolderPath.length());
/* 743 */       if (tmpFileName.startsWith(System.getProperty("file.separator"))) {
/* 744 */         tmpFileName = tmpFileName.substring(1);
/*     */       }
/*     */       
/* 747 */       File tmpFile = new File(file);
/* 748 */       if (tmpFile.isDirectory()) {
/* 749 */         tmpFileName = tmpFileName.replaceAll("\\\\", "/");
/* 750 */         tmpFileName = tmpFileName + "/";
/*     */       } else {
/* 752 */         String bkFileName = tmpFileName.substring(0, tmpFileName.indexOf(tmpFile.getName()));
/* 753 */         bkFileName = bkFileName.replaceAll("\\\\", "/");
/* 754 */         tmpFileName = bkFileName + tmpFile.getName();
/*     */       } 
/*     */       
/* 757 */       fileName = tmpFileName;
/*     */     } else {
/* 759 */       File relFile = new File(file);
/* 760 */       if (relFile.isDirectory()) {
/* 761 */         fileName = relFile.getName() + "/";
/*     */       } else {
/* 763 */         fileName = getFileNameFromFilePath(new File(file));
/*     */       } 
/*     */     } 
/*     */     
/* 767 */     if (isStringNotNullAndNotEmpty(rootFolderInZip)) {
/* 768 */       fileName = rootFolderInZip + fileName;
/*     */     }
/*     */     
/* 771 */     if (!isStringNotNullAndNotEmpty(fileName)) {
/* 772 */       throw new ZipException("Error determining file name");
/*     */     }
/*     */     
/* 775 */     return fileName;
/*     */   }
/*     */ }


/* Location:              C:\Users\DuduM\OneDrive\Área de Trabalho\mw10\modularwarfare-shining-2023.2.4.4f-fix10.jar!\net\lingala\zip4\\util\Zip4jUtil.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */