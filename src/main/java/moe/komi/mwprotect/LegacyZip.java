/*    */ package moe.komi.mwprotect;
/*    */ 
/*    */ import java.io.File;
/*    */ import java.io.IOException;
/*    */ import java.util.HashSet;
/*    */ import java.util.List;
/*    */ import java.util.Objects;
/*    */ import java.util.Set;
/*    */ import net.lingala.zip4j.core.ZipFile;
/*    */ import net.lingala.zip4j.exception.ZipException;
/*    */ import net.lingala.zip4j.model.FileHeader;
/*    */ 
/*    */ public class LegacyZip
/*    */   implements IZip {
/*    */   ZipFile zipFile;
/*    */   
/*    */   public LegacyZip(String fileName) throws IOException {
/* 18 */     this(new File(fileName));
/*    */   }
/*    */   
/*    */   public LegacyZip(File file) throws IOException {
/*    */     try {
/* 23 */       this.zipFile = new ZipFile(file);
/* 24 */     } catch (ZipException e) {
/* 25 */       throw new IOException(e);
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   public Set<IZipEntry> getFileList() throws IOException {
/*    */     try {
/* 32 */       List<FileHeader> fileHeaders = this.zipFile.getFileHeaders();
/* 33 */       Set<IZipEntry> result = new HashSet<>();
/* 34 */       fileHeaders.forEach(entry -> result.add(new LegacyZipEntry(this.zipFile, entry)));
/* 35 */       return result;
/* 36 */     } catch (ZipException e) {
/* 37 */       throw new IOException(e);
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   public int hashCode() {
/* 43 */     return this.zipFile.hashCode();
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean equals(Object obj) {
/* 48 */     if (this == obj) return true; 
/* 49 */     if (!(obj instanceof LegacyZip)) return false; 
/* 50 */     LegacyZip that = (LegacyZip)obj;
/* 51 */     return Objects.equals(this.zipFile, that.zipFile);
/*    */   }
/*    */ }


/* Location:              C:\Users\DuduM\OneDrive\Área de Trabalho\mw10\modularwarfare-shining-2023.2.4.4f-fix10.jar!\moe\komi\mwprotect\LegacyZip.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */