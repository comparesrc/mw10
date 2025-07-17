/*     */ package net.lingala.zip4j.crypto;
/*     */ 
/*     */ import java.util.Random;
/*     */ import net.lingala.zip4j.crypto.engine.ZipCryptoEngine;
/*     */ import net.lingala.zip4j.exception.ZipException;
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
/*     */ public class StandardEncrypter
/*     */   implements IEncrypter
/*     */ {
/*     */   private ZipCryptoEngine zipCryptoEngine;
/*     */   private byte[] headerBytes;
/*     */   
/*     */   public StandardEncrypter(String password, int crc) throws ZipException {
/*  32 */     if (!Zip4jUtil.isStringNotNullAndNotEmpty(password)) {
/*  33 */       throw new ZipException("input password is null or empty in standard encrpyter constructor");
/*     */     }
/*     */     
/*  36 */     this.zipCryptoEngine = new ZipCryptoEngine();
/*     */     
/*  38 */     this.headerBytes = new byte[12];
/*  39 */     init(password, crc);
/*     */   }
/*     */   
/*     */   private void init(String password, int crc) throws ZipException {
/*  43 */     if (!Zip4jUtil.isStringNotNullAndNotEmpty(password)) {
/*  44 */       throw new ZipException("input password is null or empty, cannot initialize standard encrypter");
/*     */     }
/*  46 */     this.zipCryptoEngine.initKeys(password);
/*  47 */     this.headerBytes = generateRandomBytes(12);
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  52 */     this.headerBytes[11] = (byte)(crc >> 24);
/*     */     
/*  54 */     if (this.headerBytes.length < 12) {
/*  55 */       throw new ZipException("invalid header bytes generated, cannot perform standard encryption");
/*     */     }
/*     */     
/*  58 */     for (int i = 0; i < this.headerBytes.length; i++) {
/*     */       
/*  60 */       int val = this.headerBytes[i] & 0xFF;
/*  61 */       this.headerBytes[i] = (byte)(val ^ this.zipCryptoEngine.decryptByte());
/*  62 */       this.zipCryptoEngine.updateKeys((byte)val);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int encryptData(byte[] buff) throws ZipException {
/*  71 */     if (buff == null) {
/*  72 */       throw new NullPointerException();
/*     */     }
/*  74 */     return encryptData(buff, 0, buff.length);
/*     */   }
/*     */ 
/*     */   
/*     */   public int encryptData(byte[] buff, int start, int len) throws ZipException {
/*  79 */     if (len < 0) {
/*  80 */       throw new ZipException("invalid length specified to decrpyt data");
/*     */     }
/*     */     
/*     */     try {
/*  84 */       for (int i = start; i < len; i++) {
/*  85 */         int val = buff[i] & 0xFF;
/*  86 */         buff[i] = (byte)(val ^ this.zipCryptoEngine.decryptByte() & 0xFF);
/*  87 */         this.zipCryptoEngine.updateKeys((byte)val);
/*     */       } 
/*  89 */       return len;
/*  90 */     } catch (Exception e) {
/*  91 */       throw new ZipException(e);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   protected static byte[] generateRandomBytes(int size) throws ZipException {
/*  97 */     if (size <= 0) {
/*  98 */       throw new ZipException("size is either 0 or less than 0, cannot generate header for standard encryptor");
/*     */     }
/*     */     
/* 101 */     byte[] buff = new byte[size];
/*     */     
/* 103 */     Random rand = new Random();
/*     */     
/* 105 */     for (int i = 0; i < buff.length; i++) {
/* 106 */       buff[i] = (byte)rand.nextInt(256);
/*     */     }
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
/*     */ 
/*     */     
/* 130 */     return buff;
/*     */   }
/*     */   
/*     */   public byte[] getHeaderBytes() {
/* 134 */     return this.headerBytes;
/*     */   }
/*     */ }


/* Location:              C:\Users\DuduM\OneDrive\Área de Trabalho\mw10\modularwarfare-shining-2023.2.4.4f-fix10.jar!\net\lingala\zip4j\crypto\StandardEncrypter.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */