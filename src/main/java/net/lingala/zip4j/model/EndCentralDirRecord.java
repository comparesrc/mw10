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
/*     */ public class EndCentralDirRecord
/*     */ {
/*     */   private long signature;
/*     */   private int noOfThisDisk;
/*     */   private int noOfThisDiskStartOfCentralDir;
/*     */   private int totNoOfEntriesInCentralDirOnThisDisk;
/*     */   private int totNoOfEntriesInCentralDir;
/*     */   private int sizeOfCentralDir;
/*     */   private long offsetOfStartOfCentralDir;
/*     */   private int commentLength;
/*     */   private String comment;
/*     */   private byte[] commentBytes;
/*     */   
/*     */   public long getSignature() {
/*  42 */     return this.signature;
/*     */   }
/*     */   
/*     */   public void setSignature(long signature) {
/*  46 */     this.signature = signature;
/*     */   }
/*     */   
/*     */   public int getNoOfThisDisk() {
/*  50 */     return this.noOfThisDisk;
/*     */   }
/*     */   
/*     */   public void setNoOfThisDisk(int noOfThisDisk) {
/*  54 */     this.noOfThisDisk = noOfThisDisk;
/*     */   }
/*     */   
/*     */   public int getNoOfThisDiskStartOfCentralDir() {
/*  58 */     return this.noOfThisDiskStartOfCentralDir;
/*     */   }
/*     */   
/*     */   public void setNoOfThisDiskStartOfCentralDir(int noOfThisDiskStartOfCentralDir) {
/*  62 */     this.noOfThisDiskStartOfCentralDir = noOfThisDiskStartOfCentralDir;
/*     */   }
/*     */   
/*     */   public int getTotNoOfEntriesInCentralDirOnThisDisk() {
/*  66 */     return this.totNoOfEntriesInCentralDirOnThisDisk;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setTotNoOfEntriesInCentralDirOnThisDisk(int totNoOfEntriesInCentralDirOnThisDisk) {
/*  71 */     this.totNoOfEntriesInCentralDirOnThisDisk = totNoOfEntriesInCentralDirOnThisDisk;
/*     */   }
/*     */   
/*     */   public int getTotNoOfEntriesInCentralDir() {
/*  75 */     return this.totNoOfEntriesInCentralDir;
/*     */   }
/*     */   
/*     */   public void setTotNoOfEntriesInCentralDir(int totNoOfEntrisInCentralDir) {
/*  79 */     this.totNoOfEntriesInCentralDir = totNoOfEntrisInCentralDir;
/*     */   }
/*     */   
/*     */   public int getSizeOfCentralDir() {
/*  83 */     return this.sizeOfCentralDir;
/*     */   }
/*     */   
/*     */   public void setSizeOfCentralDir(int sizeOfCentralDir) {
/*  87 */     this.sizeOfCentralDir = sizeOfCentralDir;
/*     */   }
/*     */   
/*     */   public long getOffsetOfStartOfCentralDir() {
/*  91 */     return this.offsetOfStartOfCentralDir;
/*     */   }
/*     */   
/*     */   public void setOffsetOfStartOfCentralDir(long offSetOfStartOfCentralDir) {
/*  95 */     this.offsetOfStartOfCentralDir = offSetOfStartOfCentralDir;
/*     */   }
/*     */   
/*     */   public int getCommentLength() {
/*  99 */     return this.commentLength;
/*     */   }
/*     */   
/*     */   public void setCommentLength(int commentLength) {
/* 103 */     this.commentLength = commentLength;
/*     */   }
/*     */   
/*     */   public String getComment() {
/* 107 */     return this.comment;
/*     */   }
/*     */   
/*     */   public void setComment(String comment) {
/* 111 */     this.comment = comment;
/*     */   }
/*     */   
/*     */   public byte[] getCommentBytes() {
/* 115 */     return this.commentBytes;
/*     */   }
/*     */   
/*     */   public void setCommentBytes(byte[] commentBytes) {
/* 119 */     this.commentBytes = commentBytes;
/*     */   }
/*     */ }


/* Location:              C:\Users\DuduM\OneDrive\Área de Trabalho\mw10\modularwarfare-shining-2023.2.4.4f-fix10.jar!\net\lingala\zip4j\model\EndCentralDirRecord.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */