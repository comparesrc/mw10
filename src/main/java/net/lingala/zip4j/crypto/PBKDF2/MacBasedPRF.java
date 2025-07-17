/*     */ package net.lingala.zip4j.crypto.PBKDF2;
/*     */ 
/*     */ import java.security.InvalidKeyException;
/*     */ import java.security.NoSuchAlgorithmException;
/*     */ import java.security.NoSuchProviderException;
/*     */ import javax.crypto.Mac;
/*     */ import javax.crypto.spec.SecretKeySpec;
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
/*     */ public class MacBasedPRF
/*     */   implements PRF
/*     */ {
/*     */   protected Mac mac;
/*     */   protected int hLen;
/*     */   protected String macAlgorithm;
/*     */   
/*     */   public MacBasedPRF(String macAlgorithm) {
/*  41 */     this.macAlgorithm = macAlgorithm;
/*     */     
/*     */     try {
/*  44 */       this.mac = Mac.getInstance(macAlgorithm);
/*  45 */       this.hLen = this.mac.getMacLength();
/*     */     }
/*  47 */     catch (NoSuchAlgorithmException e) {
/*     */       
/*  49 */       throw new RuntimeException(e);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public MacBasedPRF(String macAlgorithm, String provider) {
/*  55 */     this.macAlgorithm = macAlgorithm;
/*     */     
/*     */     try {
/*  58 */       this.mac = Mac.getInstance(macAlgorithm, provider);
/*  59 */       this.hLen = this.mac.getMacLength();
/*     */     }
/*  61 */     catch (NoSuchAlgorithmException e) {
/*     */       
/*  63 */       throw new RuntimeException(e);
/*     */     }
/*  65 */     catch (NoSuchProviderException e) {
/*     */       
/*  67 */       throw new RuntimeException(e);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public byte[] doFinal(byte[] M) {
/*  73 */     byte[] r = this.mac.doFinal(M);
/*  74 */     return r;
/*     */   }
/*     */   
/*     */   public byte[] doFinal() {
/*  78 */     byte[] r = this.mac.doFinal();
/*  79 */     return r;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getHLen() {
/*  84 */     return this.hLen;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void init(byte[] P) {
/*     */     try {
/*  91 */       this.mac.init(new SecretKeySpec(P, this.macAlgorithm));
/*     */     }
/*  93 */     catch (InvalidKeyException e) {
/*     */       
/*  95 */       throw new RuntimeException(e);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void update(byte[] U) {
/*     */     try {
/* 102 */       this.mac.update(U);
/* 103 */     } catch (IllegalStateException e) {
/* 104 */       throw new RuntimeException(e);
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\DuduM\OneDrive\Área de Trabalho\mw10\modularwarfare-shining-2023.2.4.4f-fix10.jar!\net\lingala\zip4j\crypto\PBKDF2\MacBasedPRF.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */