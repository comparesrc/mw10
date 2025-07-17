/*     */ package net.lingala.zip4j.crypto;
/*     */ 
/*     */ import java.util.ArrayList;
/*     */ import java.util.Arrays;
/*     */ import net.lingala.zip4j.crypto.PBKDF2.MacBasedPRF;
/*     */ import net.lingala.zip4j.crypto.PBKDF2.PBKDF2Engine;
/*     */ import net.lingala.zip4j.crypto.PBKDF2.PBKDF2Parameters;
/*     */ import net.lingala.zip4j.crypto.engine.AESEngine;
/*     */ import net.lingala.zip4j.exception.ZipException;
/*     */ import net.lingala.zip4j.model.AESExtraDataRecord;
/*     */ import net.lingala.zip4j.model.LocalFileHeader;
/*     */ import net.lingala.zip4j.util.Raw;
/*     */ import net.lingala.zip4j.util.Zip4jUtil;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class AESDecrypter
/*     */   implements IDecrypter
/*     */ {
/*     */   private LocalFileHeader localFileHeader;
/*     */   private AESEngine aesEngine;
/*     */   private MacBasedPRF mac;
/*  40 */   private final int PASSWORD_VERIFIER_LENGTH = 2;
/*     */   
/*     */   private int KEY_LENGTH;
/*     */   
/*     */   private int MAC_LENGTH;
/*     */   private int SALT_LENGTH;
/*     */   private byte[] aesKey;
/*     */   private byte[] macKey;
/*     */   private byte[] derivedPasswordVerifier;
/*     */   private byte[] storedMac;
/*  50 */   private int nonce = 1;
/*     */ 
/*     */ 
/*     */   
/*     */   public AESDecrypter(LocalFileHeader localFileHeader, byte[] salt, byte[] passwordVerifier) throws ZipException {
/*  55 */     if (localFileHeader == null) {
/*  56 */       throw new ZipException("one of the input parameters is null in AESDecryptor Constructor");
/*     */     }
/*     */     
/*  59 */     this.localFileHeader = localFileHeader;
/*  60 */     this.storedMac = null;
/*  61 */     init(salt, passwordVerifier);
/*     */   }
/*     */   
/*     */   private void init(byte[] salt, byte[] passwordVerifier) throws ZipException {
/*  65 */     if (this.localFileHeader == null) {
/*  66 */       throw new ZipException("invalid file header in init method of AESDecryptor");
/*     */     }
/*     */     
/*  69 */     AESExtraDataRecord aesExtraDataRecord = this.localFileHeader.getAesExtraDataRecord();
/*  70 */     if (aesExtraDataRecord == null) {
/*  71 */       throw new ZipException("invalid aes extra data record - in init method of AESDecryptor");
/*     */     }
/*     */     
/*  74 */     switch (aesExtraDataRecord.getAesStrength()) {
/*     */       case 1:
/*  76 */         this.KEY_LENGTH = 16;
/*  77 */         this.MAC_LENGTH = 16;
/*  78 */         this.SALT_LENGTH = 8;
/*     */         break;
/*     */       case 2:
/*  81 */         this.KEY_LENGTH = 24;
/*  82 */         this.MAC_LENGTH = 24;
/*  83 */         this.SALT_LENGTH = 12;
/*     */         break;
/*     */       case 3:
/*  86 */         this.KEY_LENGTH = 32;
/*  87 */         this.MAC_LENGTH = 32;
/*  88 */         this.SALT_LENGTH = 16;
/*     */         break;
/*     */       default:
/*  91 */         throw new ZipException("invalid aes key strength for file: " + this.localFileHeader.getFileName());
/*     */     } 
/*     */     
/*  94 */     if (!Zip4jUtil.isStringNotNullAndNotEmpty(this.localFileHeader.getPassword())) {
/*  95 */       throw new ZipException("empty or null password provided for AES Decryptor");
/*     */     }
/*     */     
/*  98 */     byte[] derivedKey = deriveKey(salt, this.localFileHeader.getPassword());
/*  99 */     if (derivedKey == null || derivedKey.length != this.KEY_LENGTH + this.MAC_LENGTH + 2)
/*     */     {
/* 101 */       throw new ZipException("invalid derived key");
/*     */     }
/*     */     
/* 104 */     this.aesKey = new byte[this.KEY_LENGTH];
/* 105 */     this.macKey = new byte[this.MAC_LENGTH];
/* 106 */     this.derivedPasswordVerifier = new byte[2];
/*     */     
/* 108 */     System.arraycopy(derivedKey, 0, this.aesKey, 0, this.KEY_LENGTH);
/* 109 */     System.arraycopy(derivedKey, this.KEY_LENGTH, this.macKey, 0, this.MAC_LENGTH);
/* 110 */     System.arraycopy(derivedKey, this.KEY_LENGTH + this.MAC_LENGTH, this.derivedPasswordVerifier, 0, 2);
/*     */     
/* 112 */     if (this.derivedPasswordVerifier == null) {
/* 113 */       throw new ZipException("invalid derived password verifier for AES");
/*     */     }
/*     */     
/* 116 */     if (!Arrays.equals(passwordVerifier, this.derivedPasswordVerifier)) {
/* 117 */       throw new ZipException("Wrong Password for file: " + this.localFileHeader.getFileName());
/*     */     }
/*     */     
/* 120 */     this.aesEngine = new AESEngine(this.aesKey);
/* 121 */     this.mac = new MacBasedPRF("HmacSHA1");
/* 122 */     this.mac.init(this.macKey);
/*     */   }
/*     */ 
/*     */   
/*     */   public int decryptData(byte[] buff, int start, int len) throws ZipException {
/* 127 */     if (this.aesEngine == null) {
/* 128 */       throw new ZipException("AES not initialized properly");
/*     */     }
/*     */ 
/*     */     
/*     */     try {
/* 133 */       ArrayList<byte[]> byteBlocks = new ArrayList();
/* 134 */       ArrayList<byte[]> decBlocks = new ArrayList();
/* 135 */       for (int i = 0; i < len; i += 16) {
/*     */         byte[] block;
/* 137 */         if (i + 16 > len) {
/* 138 */           block = new byte[len - i];
/*     */         } else {
/* 140 */           block = new byte[16];
/*     */         } 
/* 142 */         System.arraycopy(buff, i, block, 0, block.length);
/* 143 */         byteBlocks.add(block);
/*     */       } 
/* 145 */       byte[] iv = new byte[16];
/* 146 */       byte[] counterBlock = new byte[16];
/* 147 */       for (int j = 0; j < byteBlocks.size(); j++) {
/* 148 */         byte[] cipherBlock = byteBlocks.get(j);
/* 149 */         byte[] decryptedBlock = new byte[cipherBlock.length];
/*     */         
/* 151 */         this.mac.update(cipherBlock);
/* 152 */         iv = Raw.toByteArray(this.nonce, 16);
/* 153 */         this.aesEngine.processBlock(iv, counterBlock);
/* 154 */         for (int m = 0; m < cipherBlock.length; m++) {
/* 155 */           decryptedBlock[m] = (byte)(cipherBlock[m] ^ counterBlock[m]);
/*     */         }
/* 157 */         decBlocks.add(decryptedBlock);
/* 158 */         this.nonce++;
/*     */       } 
/*     */ 
/*     */       
/* 162 */       int pos = 0;
/* 163 */       for (int k = 0; k < decBlocks.size(); k++) {
/* 164 */         System.arraycopy(decBlocks.get(k), 0, buff, pos, ((byte[])decBlocks.get(k)).length);
/* 165 */         pos += ((byte[])decBlocks.get(k)).length;
/*     */       } 
/* 167 */       return len;
/*     */     }
/* 169 */     catch (ZipException e) {
/* 170 */       throw e;
/* 171 */     } catch (Exception e) {
/* 172 */       throw new ZipException(e);
/*     */     } 
/*     */   }
/*     */   
/*     */   public int decryptData(byte[] buff) throws ZipException {
/* 177 */     return decryptData(buff, 0, buff.length);
/*     */   }
/*     */   
/*     */   private byte[] deriveKey(byte[] salt, String password) throws ZipException {
/*     */     try {
/* 182 */       PBKDF2Parameters p = new PBKDF2Parameters("HmacSHA1", "ISO-8859-1", salt, 1000);
/*     */       
/* 184 */       PBKDF2Engine e = new PBKDF2Engine(p);
/* 185 */       byte[] derivedKey = e.deriveKey(password, this.KEY_LENGTH + this.MAC_LENGTH + 2);
/* 186 */       return derivedKey;
/* 187 */     } catch (Exception e) {
/* 188 */       throw new ZipException(e);
/*     */     } 
/*     */   }
/*     */   
/*     */   public int getPasswordVerifierLength() {
/* 193 */     return 2;
/*     */   }
/*     */   
/*     */   public int getSaltLength() {
/* 197 */     return this.SALT_LENGTH;
/*     */   }
/*     */   
/*     */   public byte[] getCalculatedAuthenticationBytes() {
/* 201 */     return this.mac.doFinal();
/*     */   }
/*     */   
/*     */   public void setStoredMac(byte[] storedMac) {
/* 205 */     this.storedMac = storedMac;
/*     */   }
/*     */   
/*     */   public byte[] getStoredMac() {
/* 209 */     return this.storedMac;
/*     */   }
/*     */ }


/* Location:              C:\Users\DuduM\OneDrive\Área de Trabalho\mw10\modularwarfare-shining-2023.2.4.4f-fix10.jar!\net\lingala\zip4j\crypto\AESDecrypter.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */