/*    */ package net.lingala.zip4j.model;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class Zip64ExtendedInfo
/*    */ {
/*    */   private int header;
/*    */   private int size;
/* 34 */   private long compressedSize = -1L;
/* 35 */   private long unCompressedSize = -1L;
/* 36 */   private long offsetLocalHeader = -1L;
/* 37 */   private int diskNumberStart = -1;
/*    */ 
/*    */   
/*    */   public int getHeader() {
/* 41 */     return this.header;
/*    */   }
/*    */   
/*    */   public void setHeader(int header) {
/* 45 */     this.header = header;
/*    */   }
/*    */   
/*    */   public int getSize() {
/* 49 */     return this.size;
/*    */   }
/*    */   
/*    */   public void setSize(int size) {
/* 53 */     this.size = size;
/*    */   }
/*    */   
/*    */   public long getCompressedSize() {
/* 57 */     return this.compressedSize;
/*    */   }
/*    */   
/*    */   public void setCompressedSize(long compressedSize) {
/* 61 */     this.compressedSize = compressedSize;
/*    */   }
/*    */   
/*    */   public long getUnCompressedSize() {
/* 65 */     return this.unCompressedSize;
/*    */   }
/*    */   
/*    */   public void setUnCompressedSize(long unCompressedSize) {
/* 69 */     this.unCompressedSize = unCompressedSize;
/*    */   }
/*    */   
/*    */   public long getOffsetLocalHeader() {
/* 73 */     return this.offsetLocalHeader;
/*    */   }
/*    */   
/*    */   public void setOffsetLocalHeader(long offsetLocalHeader) {
/* 77 */     this.offsetLocalHeader = offsetLocalHeader;
/*    */   }
/*    */   
/*    */   public int getDiskNumberStart() {
/* 81 */     return this.diskNumberStart;
/*    */   }
/*    */   
/*    */   public void setDiskNumberStart(int diskNumberStart) {
/* 85 */     this.diskNumberStart = diskNumberStart;
/*    */   }
/*    */ }


/* Location:              C:\Users\DuduM\OneDrive\Área de Trabalho\mw10\modularwarfare-shining-2023.2.4.4f-fix10.jar!\net\lingala\zip4j\model\Zip64ExtendedInfo.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */