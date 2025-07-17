/*    */ package net.lingala.zip4j.crypto;
/*    */ 
/*    */ import net.lingala.zip4j.crypto.engine.ZipCryptoEngine;
/*    */ import net.lingala.zip4j.exception.ZipException;
/*    */ import net.lingala.zip4j.model.FileHeader;
/*    */ import net.lingala.zip4j.util.Zip4jUtil;
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
/*    */ public class StandardDecrypter
/*    */   implements IDecrypter
/*    */ {
/*    */   private FileHeader fileHeader;
/* 29 */   private byte[] crc = new byte[4];
/*    */   private ZipCryptoEngine zipCryptoEngine;
/*    */   
/*    */   public StandardDecrypter(FileHeader fileHeader, byte[] headerBytes) throws ZipException {
/* 33 */     if (fileHeader == null) {
/* 34 */       throw new ZipException("one of more of the input parameters were null in StandardDecryptor");
/*    */     }
/*    */     
/* 37 */     this.fileHeader = fileHeader;
/* 38 */     this.zipCryptoEngine = new ZipCryptoEngine();
/* 39 */     init(headerBytes);
/*    */   }
/*    */   
/*    */   public int decryptData(byte[] buff) throws ZipException {
/* 43 */     return decryptData(buff, 0, buff.length);
/*    */   }
/*    */   
/*    */   public int decryptData(byte[] buff, int start, int len) throws ZipException {
/* 47 */     if (start < 0 || len < 0) {
/* 48 */       throw new ZipException("one of the input parameters were null in standard decrpyt data");
/*    */     }
/*    */     
/*    */     try {
/* 52 */       for (int i = 0; i < len; i++) {
/* 53 */         int val = buff[i] & 0xFF;
/* 54 */         val = (val ^ this.zipCryptoEngine.decryptByte()) & 0xFF;
/* 55 */         this.zipCryptoEngine.updateKeys((byte)val);
/* 56 */         buff[i] = (byte)val;
/*    */       } 
/* 58 */       return len;
/* 59 */     } catch (Exception e) {
/* 60 */       throw new ZipException(e);
/*    */     } 
/*    */   }
/*    */   
/*    */   public void init(byte[] headerBytes) throws ZipException {
/* 65 */     byte[] crcBuff = this.fileHeader.getCrcBuff();
/* 66 */     this.crc[3] = (byte)(crcBuff[3] & 0xFF);
/* 67 */     this.crc[2] = (byte)(crcBuff[3] >> 8 & 0xFF);
/* 68 */     this.crc[1] = (byte)(crcBuff[3] >> 16 & 0xFF);
/* 69 */     this.crc[0] = (byte)(crcBuff[3] >> 24 & 0xFF);
/*    */     
/* 71 */     if (this.crc[2] > 0 || this.crc[1] > 0 || this.crc[0] > 0) {
/* 72 */       throw new IllegalStateException("Invalid CRC in File Header");
/*    */     }
/* 74 */     String password = this.fileHeader.getPassword();
/* 75 */     if (!Zip4jUtil.isStringNotNullAndNotEmpty(password)) {
/* 76 */       throw new ZipException("Wrong password!", 5);
/*    */     }
/*    */     
/* 79 */     this.zipCryptoEngine.initKeys(password);
/*    */     
/*    */     try {
/* 82 */       int result = headerBytes[0];
/* 83 */       for (int i = 0; i < 12; i++) {
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */         
/* 90 */         this.zipCryptoEngine.updateKeys((byte)(result ^ this.zipCryptoEngine.decryptByte()));
/* 91 */         if (i + 1 != 12)
/* 92 */           result = headerBytes[i + 1]; 
/*    */       } 
/* 94 */     } catch (Exception e) {
/* 95 */       throw new ZipException(e);
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\DuduM\OneDrive\Área de Trabalho\mw10\modularwarfare-shining-2023.2.4.4f-fix10.jar!\net\lingala\zip4j\crypto\StandardDecrypter.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */