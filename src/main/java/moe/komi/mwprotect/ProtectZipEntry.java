/*    */ package moe.komi.mwprotect;
/*    */ 
/*    */ import java.io.IOException;
/*    */ import java.io.InputStream;
/*    */ import java.util.Objects;
/*    */ 
/*    */ public class ProtectZipEntry implements IZipEntry {
/*    */   private final String entryName;
/*    */   private final long zipNativeFileHandle;
/*    */   private final long entryNativeHandle;
/*    */   
/*    */   public ProtectZipEntry(long zipNativeFileHandle, String entryName, long entryNativeHandle) {
/* 13 */     this.entryName = entryName;
/* 14 */     this.zipNativeFileHandle = zipNativeFileHandle;
/* 15 */     this.entryNativeHandle = entryNativeHandle;
/*    */   }
/*    */   
/*    */   public ProtectInputStream getInputStream() throws IOException {
/* 19 */     return new ProtectInputStream(this.entryNativeHandle);
/*    */   }
/*    */ 
/*    */   
/*    */   public long getHandle() {
/* 24 */     return this.entryNativeHandle;
/*    */   }
/*    */   
/*    */   public String getFileName() {
/* 28 */     return this.entryName;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean equals(Object o) {
/* 33 */     if (this == o) return true; 
/* 34 */     if (!(o instanceof ProtectZipEntry)) return false; 
/* 35 */     ProtectZipEntry that = (ProtectZipEntry)o;
/* 36 */     return (this.zipNativeFileHandle == that.zipNativeFileHandle && this.entryNativeHandle == that.entryNativeHandle && Objects.equals(this.entryName, that.entryName));
/*    */   }
/*    */ 
/*    */   
/*    */   public int hashCode() {
/* 41 */     return Objects.hash(new Object[] { this.entryName, Long.valueOf(this.zipNativeFileHandle), Long.valueOf(this.entryNativeHandle) });
/*    */   }
/*    */ }


/* Location:              C:\Users\DuduM\OneDrive\Área de Trabalho\mw10\modularwarfare-shining-2023.2.4.4f-fix10.jar!\moe\komi\mwprotect\ProtectZipEntry.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */