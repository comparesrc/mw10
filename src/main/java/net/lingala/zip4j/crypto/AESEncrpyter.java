/*     */ package net.lingala.zip4j.crypto;
/*     */ 
/*     */ import java.util.ArrayList;
/*     */ import java.util.Random;
/*     */ import net.lingala.zip4j.crypto.PBKDF2.MacBasedPRF;
/*     */ import net.lingala.zip4j.crypto.PBKDF2.PBKDF2Engine;
/*     */ import net.lingala.zip4j.crypto.PBKDF2.PBKDF2Parameters;
/*     */ import net.lingala.zip4j.crypto.engine.AESEngine;
/*     */ import net.lingala.zip4j.exception.ZipException;
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
/*     */ public class AESEncrpyter
/*     */   implements IEncrypter
/*     */ {
/*     */   private String password;
/*     */   private int keyStrength;
/*     */   private AESEngine aesEngine;
/*     */   private MacBasedPRF mac;
/*     */   private int KEY_LENGTH;
/*     */   private int MAC_LENGTH;
/*     */   private int SALT_LENGTH;
/*  42 */   private final int PASSWORD_VERIFIER_LENGTH = 2;
/*     */   
/*     */   private byte[] aesKey;
/*     */   
/*     */   private byte[] macKey;
/*     */   
/*     */   private byte[] derivedPasswordVerifier;
/*     */   private byte[] saltBytes;
/*     */   private boolean finished;
/*  51 */   private int nonce = 1;
/*     */   
/*  53 */   private byte[] iv = new byte[16];
/*  54 */   private byte[] counterBlock = new byte[16];
/*  55 */   private byte[] aesSplitBlock = new byte[16];
/*     */   
/*     */   public AESEncrpyter(String password, int keyStrength) throws ZipException {
/*  58 */     if (!Zip4jUtil.isStringNotNullAndNotEmpty(password)) {
/*  59 */       throw new ZipException("input password is empty or null in AES encrypter constructor");
/*     */     }
/*  61 */     if (keyStrength != 1 && keyStrength != 3)
/*     */     {
/*  63 */       throw new ZipException("Invalid key strength in AES encrypter constructor");
/*     */     }
/*     */     
/*  66 */     this.password = password;
/*  67 */     this.keyStrength = keyStrength;
/*  68 */     this.finished = false;
/*     */     
/*  70 */     init();
/*     */   }
/*     */   
/*     */   private void init() throws ZipException {
/*  74 */     switch (this.keyStrength) {
/*     */       case 1:
/*  76 */         this.KEY_LENGTH = 16;
/*  77 */         this.MAC_LENGTH = 16;
/*  78 */         this.SALT_LENGTH = 8;
/*     */         break;
/*     */       case 3:
/*  81 */         this.KEY_LENGTH = 32;
/*  82 */         this.MAC_LENGTH = 32;
/*  83 */         this.SALT_LENGTH = 16;
/*     */         break;
/*     */       default:
/*  86 */         throw new ZipException("invalid aes key strength, cannot determine key sizes");
/*     */     } 
/*     */     
/*  89 */     this.saltBytes = generateSalt(this.SALT_LENGTH);
/*  90 */     byte[] keyBytes = deriveKey(this.saltBytes, this.password);
/*     */     
/*  92 */     if (keyBytes == null || keyBytes.length != this.KEY_LENGTH + this.MAC_LENGTH + 2) {
/*  93 */       throw new ZipException("invalid key generated, cannot decrypt file");
/*     */     }
/*     */     
/*  96 */     this.aesKey = new byte[this.KEY_LENGTH];
/*  97 */     this.macKey = new byte[this.MAC_LENGTH];
/*  98 */     this.derivedPasswordVerifier = new byte[2];
/*     */     
/* 100 */     System.arraycopy(keyBytes, 0, this.aesKey, 0, this.KEY_LENGTH);
/* 101 */     System.arraycopy(keyBytes, this.KEY_LENGTH, this.macKey, 0, this.MAC_LENGTH);
/* 102 */     System.arraycopy(keyBytes, this.KEY_LENGTH + this.MAC_LENGTH, this.derivedPasswordVerifier, 0, 2);
/*     */     
/* 104 */     this.aesEngine = new AESEngine(this.aesKey);
/* 105 */     this.mac = new MacBasedPRF("HmacSHA1");
/* 106 */     this.mac.init(this.macKey);
/*     */   }
/*     */   
/*     */   private byte[] deriveKey(byte[] salt, String password) throws ZipException {
/*     */     try {
/* 111 */       PBKDF2Parameters p = new PBKDF2Parameters("HmacSHA1", "ISO-8859-1", salt, 1000);
/*     */       
/* 113 */       PBKDF2Engine e = new PBKDF2Engine(p);
/* 114 */       byte[] derivedKey = e.deriveKey(password, this.KEY_LENGTH + this.MAC_LENGTH + 2);
/* 115 */       return derivedKey;
/* 116 */     } catch (Exception e) {
/* 117 */       throw new ZipException(e);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public int encryptData(byte[] buff) throws ZipException {
/* 123 */     if (buff == null) {
/* 124 */       throw new ZipException("input bytes are null, cannot perform AES encrpytion");
/*     */     }
/* 126 */     return encryptData(buff, 0, buff.length);
/*     */   }
/*     */ 
/*     */   
/*     */   public int encryptData(byte[] buff, int start, int len) throws ZipException {
/* 131 */     if (this.finished)
/*     */     {
/*     */ 
/*     */       
/* 135 */       throw new ZipException("AES Encrypter is in finished state (A non 16 byte block has already been passed to encrypter)");
/*     */     }
/*     */     
/* 138 */     if (len % 16 != 0) {
/* 139 */       this.finished = true;
/*     */     }
/*     */ 
/*     */     
/* 143 */     ArrayList<byte[]> byteBlocks = new ArrayList();
/* 144 */     ArrayList<byte[]> encBlocks = new ArrayList(); int i;
/* 145 */     for (i = 0; i < len; i += 16) {
/*     */       
/* 147 */       if (i + 16 > len) {
/* 148 */         byte[] block = new byte[len - i];
/* 149 */         System.arraycopy(buff, i + start, block, 0, block.length);
/* 150 */         byteBlocks.add(block);
/*     */       } else {
/* 152 */         System.arraycopy(buff, i + start, this.aesSplitBlock, 0, this.aesSplitBlock.length);
/* 153 */         byteBlocks.add(this.aesSplitBlock.clone());
/*     */       } 
/*     */     } 
/*     */     
/* 157 */     for (i = 0; i < byteBlocks.size(); i++) {
/* 158 */       byte[] plainBlock = byteBlocks.get(i);
/* 159 */       byte[] encryptedBlock = new byte[plainBlock.length];
/*     */       
/* 161 */       this.iv = Raw.toByteArray(this.nonce, 16);
/* 162 */       this.aesEngine.processBlock(this.iv, this.counterBlock);
/* 163 */       for (int k = 0; k < plainBlock.length; k++) {
/* 164 */         encryptedBlock[k] = (byte)(plainBlock[k] ^ this.counterBlock[k]);
/*     */       }
/* 166 */       encBlocks.add(encryptedBlock);
/* 167 */       this.mac.update(encryptedBlock);
/* 168 */       this.nonce++;
/*     */     } 
/*     */ 
/*     */     
/* 172 */     int pos = start;
/* 173 */     for (int j = 0; j < encBlocks.size(); j++) {
/* 174 */       System.arraycopy(encBlocks.get(j), 0, buff, pos, ((byte[])encBlocks.get(j)).length);
/* 175 */       pos += ((byte[])encBlocks.get(j)).length;
/*     */     } 
/*     */     
/* 178 */     return len;
/*     */   }
/*     */ 
/*     */   
/*     */   private static byte[] generateSalt(int size) throws ZipException {
/* 183 */     if (size != 8 && size != 16) {
/* 184 */       throw new ZipException("invalid salt size, cannot generate salt");
/*     */     }
/*     */     
/* 187 */     int rounds = 0;
/*     */     
/* 189 */     if (size == 8)
/* 190 */       rounds = 2; 
/* 191 */     if (size == 16) {
/* 192 */       rounds = 4;
/*     */     }
/* 194 */     byte[] salt = new byte[size];
/* 195 */     for (int j = 0; j < rounds; j++) {
/* 196 */       Random rand = new Random();
/* 197 */       int i = rand.nextInt();
/* 198 */       salt[0 + j * 4] = (byte)(i >> 24);
/* 199 */       salt[1 + j * 4] = (byte)(i >> 16);
/* 200 */       salt[2 + j * 4] = (byte)(i >> 8);
/* 201 */       salt[3 + j * 4] = (byte)i;
/*     */     } 
/* 203 */     return salt;
/*     */   }
/*     */   
/*     */   public byte[] getFinalMac() {
/* 207 */     byte[] rawMacBytes = this.mac.doFinal();
/* 208 */     byte[] macBytes = new byte[10];
/* 209 */     System.arraycopy(rawMacBytes, 0, macBytes, 0, 10);
/* 210 */     return macBytes;
/*     */   }
/*     */   
/*     */   public byte[] getDerivedPasswordVerifier() {
/* 214 */     return this.derivedPasswordVerifier;
/*     */   }
/*     */   
/*     */   public void setDerivedPasswordVerifier(byte[] derivedPasswordVerifier) {
/* 218 */     this.derivedPasswordVerifier = derivedPasswordVerifier;
/*     */   }
/*     */   
/*     */   public byte[] getSaltBytes() {
/* 222 */     return this.saltBytes;
/*     */   }
/*     */   
/*     */   public void setSaltBytes(byte[] saltBytes) {
/* 226 */     this.saltBytes = saltBytes;
/*     */   }
/*     */   
/*     */   public int getSaltLength() {
/* 230 */     return this.SALT_LENGTH;
/*     */   }
/*     */   
/*     */   public int getPasswordVeriifierLength() {
/* 234 */     return 2;
/*     */   }
/*     */ }


/* Location:              C:\Users\DuduM\OneDrive\Área de Trabalho\mw10\modularwarfare-shining-2023.2.4.4f-fix10.jar!\net\lingala\zip4j\crypto\AESEncrpyter.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */