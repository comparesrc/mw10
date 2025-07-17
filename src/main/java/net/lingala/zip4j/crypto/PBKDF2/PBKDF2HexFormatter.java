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
/*    */ class PBKDF2HexFormatter
/*    */ {
/*    */   public boolean fromString(PBKDF2Parameters p, String s) {
/* 28 */     if (p == null || s == null)
/*    */     {
/* 30 */       return true;
/*    */     }
/*    */     
/* 33 */     String[] p123 = s.split(":");
/* 34 */     if (p123 == null || p123.length != 3)
/*    */     {
/* 36 */       return true;
/*    */     }
/*    */     
/* 39 */     byte[] salt = BinTools.hex2bin(p123[0]);
/* 40 */     int iterationCount = Integer.parseInt(p123[1]);
/* 41 */     byte[] bDK = BinTools.hex2bin(p123[2]);
/*    */     
/* 43 */     p.setSalt(salt);
/* 44 */     p.setIterationCount(iterationCount);
/* 45 */     p.setDerivedKey(bDK);
/* 46 */     return false;
/*    */   }
/*    */ 
/*    */   
/*    */   public String toString(PBKDF2Parameters p) {
/* 51 */     String s = BinTools.bin2hex(p.getSalt()) + ":" + String.valueOf(p.getIterationCount()) + ":" + BinTools.bin2hex(p.getDerivedKey());
/*    */ 
/*    */     
/* 54 */     return s;
/*    */   }
/*    */ }


/* Location:              C:\Users\DuduM\OneDrive\Área de Trabalho\mw10\modularwarfare-shining-2023.2.4.4f-fix10.jar!\net\lingala\zip4j\crypto\PBKDF2\PBKDF2HexFormatter.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */