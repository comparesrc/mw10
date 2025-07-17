/*    */ package moe.komi.mwprotect;
/*    */ 
/*    */ import java.io.IOException;
/*    */ import java.io.InputStream;
/*    */ import java.util.Objects;
/*    */ import net.lingala.zip4j.core.ZipFile;
/*    */ import net.lingala.zip4j.exception.ZipException;
/*    */ import net.lingala.zip4j.model.FileHeader;
/*    */ 
/*    */ public class LegacyZipEntry
/*    */   implements IZipEntry {
/*    */   private final ZipFile zipFile;
/*    */   private final FileHeader fileHeader;
/*    */   
/*    */   LegacyZipEntry(ZipFile zipFile, FileHeader fileHeader) {
/* 16 */     this.zipFile = zipFile;
/* 17 */     this.fileHeader = fileHeader;
/*    */   }
/*    */ 
/*    */   
/*    */   public String getFileName() {
/* 22 */     return this.fileHeader.getFileName();
/*    */   }
/*    */ 
/*    */   
/*    */   public InputStream getInputStream() throws IOException {
/*    */     try {
/* 28 */       return (InputStream)this.zipFile.getInputStream(this.fileHeader);
/* 29 */     } catch (ZipException e) {
/* 30 */       throw new IOException(e);
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   public long getHandle() {
/* 36 */     return 0L;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean equals(Object o) {
/* 41 */     if (this == o) return true; 
/* 42 */     if (!(o instanceof LegacyZipEntry)) return false; 
/* 43 */     LegacyZipEntry that = (LegacyZipEntry)o;
/* 44 */     return (Objects.equals(this.zipFile, that.zipFile) && Objects.equals(this.fileHeader, that.fileHeader));
/*    */   }
/*    */ 
/*    */   
/*    */   public int hashCode() {
/* 49 */     return Objects.hash(new Object[] { this.zipFile, this.fileHeader });
/*    */   }
/*    */ }


/* Location:              C:\Users\DuduM\OneDrive\Área de Trabalho\mw10\modularwarfare-shining-2023.2.4.4f-fix10.jar!\moe\komi\mwprotect\LegacyZipEntry.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */