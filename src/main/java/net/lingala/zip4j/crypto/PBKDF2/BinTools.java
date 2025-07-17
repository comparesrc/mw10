/*    */ package net.lingala.zip4j.crypto.PBKDF2;
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
/*    */ class BinTools
/*    */ {
/*    */   public static final String hex = "0123456789ABCDEF";
/*    */   
/*    */   public static String bin2hex(byte[] b) {
/* 30 */     if (b == null)
/*    */     {
/* 32 */       return "";
/*    */     }
/* 34 */     StringBuffer sb = new StringBuffer(2 * b.length);
/* 35 */     for (int i = 0; i < b.length; i++) {
/*    */       
/* 37 */       int v = (256 + b[i]) % 256;
/* 38 */       sb.append("0123456789ABCDEF".charAt(v / 16 & 0xF));
/* 39 */       sb.append("0123456789ABCDEF".charAt(v % 16 & 0xF));
/*    */     } 
/* 41 */     return sb.toString();
/*    */   }
/*    */ 
/*    */   
/*    */   public static byte[] hex2bin(String s) {
/* 46 */     String m = s;
/* 47 */     if (s == null) {
/*    */ 
/*    */       
/* 50 */       m = "";
/*    */     }
/* 52 */     else if (s.length() % 2 != 0) {
/*    */ 
/*    */       
/* 55 */       m = "0" + s;
/*    */     } 
/* 57 */     byte[] r = new byte[m.length() / 2];
/* 58 */     for (int i = 0, n = 0; i < m.length(); n++) {
/*    */       
/* 60 */       char h = m.charAt(i++);
/* 61 */       char l = m.charAt(i++);
/* 62 */       r[n] = (byte)(hex2bin(h) * 16 + hex2bin(l));
/*    */     } 
/* 64 */     return r;
/*    */   }
/*    */ 
/*    */   
/*    */   public static int hex2bin(char c) {
/* 69 */     if (c >= '0' && c <= '9')
/*    */     {
/* 71 */       return c - 48;
/*    */     }
/* 73 */     if (c >= 'A' && c <= 'F')
/*    */     {
/* 75 */       return c - 65 + 10;
/*    */     }
/* 77 */     if (c >= 'a' && c <= 'f')
/*    */     {
/* 79 */       return c - 97 + 10;
/*    */     }
/* 81 */     throw new IllegalArgumentException("Input string may only contain hex digits, but found '" + c + "'");
/*    */   }
/*    */ }


/* Location:              C:\Users\DuduM\OneDrive\Área de Trabalho\mw10\modularwarfare-shining-2023.2.4.4f-fix10.jar!\net\lingala\zip4j\crypto\PBKDF2\BinTools.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */