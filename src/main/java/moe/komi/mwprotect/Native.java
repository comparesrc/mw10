/*    */ package moe.komi.mwprotect;
/*    */ 
/*    */ import java.io.File;
/*    */ import java.io.FileNotFoundException;
/*    */ import java.io.IOException;
/*    */ import java.io.InputStream;
/*    */ import java.util.Map;
/*    */ import org.apache.commons.io.FileUtils;
/*    */ import org.apache.commons.io.IOUtils;
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
/*    */ public class Native
/*    */ {
/*    */   static {
/* 27 */     String os = System.getProperty("os.name").toLowerCase();
/* 28 */     String arch = System.getProperty("os.arch").toLowerCase();
/* 29 */     if (!os.contains("win")) {
/* 30 */       throw new UnsupportedOperationException("Unsupported operating system: " + os);
/*    */     }
/*    */     
/*    */     try {
/*    */       String fName;
/* 35 */       if (arch.equals("x86")) {
/* 36 */         fName = "MWProtect_x86.dll";
/* 37 */       } else if (arch.equals("x86_64") || arch.equals("amd64")) {
/* 38 */         fName = "MWProtect_x64.dll";
/*    */       } else {
/* 40 */         throw new UnsupportedOperationException("Unsupported architecture: " + arch);
/*    */       } 
/* 42 */       File file = writeLibraryFile(fName);
/* 43 */       System.load(file.getAbsolutePath());
/* 44 */     } catch (IOException e) {
/* 45 */       String fName; throw new RuntimeException(fName);
/*    */     } 
/*    */   }
/*    */   
/*    */   private static File writeLibraryFile(String fName) throws IOException {
/* 50 */     InputStream is = Native.class.getClassLoader().getResourceAsStream("protect/" + fName);
/* 51 */     if (is == null) {
/* 52 */       throw new FileNotFoundException("Can not found lib file in jar for: " + fName);
/*    */     }
/* 54 */     File file = new File("ModularWarfare/" + fName);
/*    */     try {
/* 56 */       FileUtils.writeByteArrayToFile(file, IOUtils.toByteArray(is));
/* 57 */     } catch (IOException ioException) {
/* 58 */       ioException.printStackTrace();
/*    */     } 
/* 60 */     return file;
/*    */   }
/*    */   
/*    */   static native void close0(long paramLong) throws IOException;
/*    */   
/*    */   static native long available0(long paramLong) throws IOException;
/*    */   
/*    */   static native int read0(long paramLong1, long paramLong2) throws IOException;
/*    */   
/*    */   static native long read0(byte[] paramArrayOfbyte, int paramInt, long paramLong1, long paramLong2, long paramLong3) throws IOException;
/*    */   
/*    */   static native Map<Object, Object> getFileList0(long paramLong) throws IOException;
/*    */   
/*    */   static native long createFile0(String paramString) throws IOException;
/*    */ }


/* Location:              C:\Users\DuduM\OneDrive\Área de Trabalho\mw10\modularwarfare-shining-2023.2.4.4f-fix10.jar!\moe\komi\mwprotect\Native.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */