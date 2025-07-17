/*     */ package net.lingala.zip4j.crypto.PBKDF2;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ public class PBKDF2Parameters
/*     */ {
/*     */   protected byte[] salt;
/*     */   protected int iterationCount;
/*     */   protected String hashAlgorithm;
/*     */   protected String hashCharset;
/*     */   protected byte[] derivedKey;
/*     */   
/*     */   public PBKDF2Parameters() {
/*  36 */     this.hashAlgorithm = null;
/*  37 */     this.hashCharset = "UTF-8";
/*  38 */     this.salt = null;
/*  39 */     this.iterationCount = 1000;
/*  40 */     this.derivedKey = null;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public PBKDF2Parameters(String hashAlgorithm, String hashCharset, byte[] salt, int iterationCount) {
/*  46 */     this.hashAlgorithm = hashAlgorithm;
/*  47 */     this.hashCharset = hashCharset;
/*  48 */     this.salt = salt;
/*  49 */     this.iterationCount = iterationCount;
/*  50 */     this.derivedKey = null;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public PBKDF2Parameters(String hashAlgorithm, String hashCharset, byte[] salt, int iterationCount, byte[] derivedKey) {
/*  56 */     this.hashAlgorithm = hashAlgorithm;
/*  57 */     this.hashCharset = hashCharset;
/*  58 */     this.salt = salt;
/*  59 */     this.iterationCount = iterationCount;
/*  60 */     this.derivedKey = derivedKey;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getIterationCount() {
/*  65 */     return this.iterationCount;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setIterationCount(int iterationCount) {
/*  70 */     this.iterationCount = iterationCount;
/*     */   }
/*     */ 
/*     */   
/*     */   public byte[] getSalt() {
/*  75 */     return this.salt;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setSalt(byte[] salt) {
/*  80 */     this.salt = salt;
/*     */   }
/*     */ 
/*     */   
/*     */   public byte[] getDerivedKey() {
/*  85 */     return this.derivedKey;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setDerivedKey(byte[] derivedKey) {
/*  90 */     this.derivedKey = derivedKey;
/*     */   }
/*     */ 
/*     */   
/*     */   public String getHashAlgorithm() {
/*  95 */     return this.hashAlgorithm;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setHashAlgorithm(String hashAlgorithm) {
/* 100 */     this.hashAlgorithm = hashAlgorithm;
/*     */   }
/*     */ 
/*     */   
/*     */   public String getHashCharset() {
/* 105 */     return this.hashCharset;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setHashCharset(String hashCharset) {
/* 110 */     this.hashCharset = hashCharset;
/*     */   }
/*     */ }


/* Location:              C:\Users\DuduM\OneDrive\Área de Trabalho\mw10\modularwarfare-shining-2023.2.4.4f-fix10.jar!\net\lingala\zip4j\crypto\PBKDF2\PBKDF2Parameters.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */