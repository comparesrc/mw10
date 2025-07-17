/*    */ package net.lingala.zip4j.crypto.engine;
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
/*    */ public class ZipCryptoEngine
/*    */ {
/* 21 */   private final int[] keys = new int[3];
/* 22 */   private static final int[] CRC_TABLE = new int[256];
/*    */   
/*    */   static {
/* 25 */     for (int i = 0; i < 256; i++) {
/* 26 */       int r = i;
/* 27 */       for (int j = 0; j < 8; j++) {
/* 28 */         if ((r & 0x1) == 1) {
/* 29 */           r = r >>> 1 ^ 0xEDB88320;
/*    */         } else {
/* 31 */           r >>>= 1;
/*    */         } 
/*    */       } 
/* 34 */       CRC_TABLE[i] = r;
/*    */     } 
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void initKeys(String password) {
/* 42 */     this.keys[0] = 305419896;
/* 43 */     this.keys[1] = 591751049;
/* 44 */     this.keys[2] = 878082192;
/* 45 */     for (int i = 0; i < password.length(); i++) {
/* 46 */       updateKeys((byte)(password.charAt(i) & 0xFF));
/*    */     }
/*    */   }
/*    */   
/*    */   public void updateKeys(byte charAt) {
/* 51 */     this.keys[0] = crc32(this.keys[0], charAt);
/* 52 */     this.keys[1] = this.keys[1] + (this.keys[0] & 0xFF);
/* 53 */     this.keys[1] = this.keys[1] * 134775813 + 1;
/* 54 */     this.keys[2] = crc32(this.keys[2], (byte)(this.keys[1] >> 24));
/*    */   }
/*    */   
/*    */   private int crc32(int oldCrc, byte charAt) {
/* 58 */     return oldCrc >>> 8 ^ CRC_TABLE[(oldCrc ^ charAt) & 0xFF];
/*    */   }
/*    */   
/*    */   public byte decryptByte() {
/* 62 */     int temp = this.keys[2] | 0x2;
/* 63 */     return (byte)(temp * (temp ^ 0x1) >>> 8);
/*    */   }
/*    */ }


/* Location:              C:\Users\DuduM\OneDrive\Área de Trabalho\mw10\modularwarfare-shining-2023.2.4.4f-fix10.jar!\net\lingala\zip4j\crypto\engine\ZipCryptoEngine.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */