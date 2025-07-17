/*    */ package moe.komi.mwprotect;
/*    */ 
/*    */ import java.io.File;
/*    */ import java.io.IOException;
/*    */ import java.util.HashSet;
/*    */ import java.util.Map;
/*    */ import java.util.Objects;
/*    */ import java.util.Set;
/*    */ 
/*    */ public class ProtectZip
/*    */   implements IZip {
/*    */   private final long nativeFileHandle;
/*    */   private final Set<IZipEntry> files;
/*    */   
/*    */   public ProtectZip(File file) throws IOException {
/* 16 */     this(file.getAbsolutePath());
/*    */   }
/*    */   
/*    */   public ProtectZip(String fileName) throws IOException {
/* 20 */     this.nativeFileHandle = Native.createFile0(fileName);
/* 21 */     this.files = getFileList0();
/*    */   }
/*    */   
/*    */   private Set<IZipEntry> getFileList0() throws IOException {
/* 25 */     Map<Object, Object> fileNames = Native.getFileList0(this.nativeFileHandle);
/* 26 */     Set<IZipEntry> result = new HashSet<>();
/* 27 */     for (Map.Entry<Object, Object> fileInfo : fileNames.entrySet()) {
/* 28 */       result.add(new ProtectZipEntry(this.nativeFileHandle, (String)fileInfo.getKey(), ((Long)fileInfo.getValue()).longValue()));
/*    */     }
/* 30 */     return result;
/*    */   }
/*    */ 
/*    */   
/*    */   public Set<IZipEntry> getFileList() throws IOException {
/* 35 */     return this.files;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean equals(Object o) {
/* 40 */     if (this == o) return true; 
/* 41 */     if (!(o instanceof ProtectZip)) return false; 
/* 42 */     ProtectZip that = (ProtectZip)o;
/* 43 */     return (this.nativeFileHandle == that.nativeFileHandle);
/*    */   }
/*    */ 
/*    */   
/*    */   public int hashCode() {
/* 48 */     return Objects.hash(new Object[] { Long.valueOf(this.nativeFileHandle) });
/*    */   }
/*    */ }


/* Location:              C:\Users\DuduM\OneDrive\Área de Trabalho\mw10\modularwarfare-shining-2023.2.4.4f-fix10.jar!\moe\komi\mwprotect\ProtectZip.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */