/*     */ package net.lingala.zip4j.crypto.PBKDF2;
/*     */ 
/*     */ import java.io.UnsupportedEncodingException;
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
/*     */ public class PBKDF2Engine
/*     */ {
/*     */   protected PBKDF2Parameters parameters;
/*     */   protected PRF prf;
/*     */   
/*     */   public PBKDF2Engine() {
/*  34 */     this.parameters = null;
/*  35 */     this.prf = null;
/*     */   }
/*     */ 
/*     */   
/*     */   public PBKDF2Engine(PBKDF2Parameters parameters) {
/*  40 */     this.parameters = parameters;
/*  41 */     this.prf = null;
/*     */   }
/*     */ 
/*     */   
/*     */   public PBKDF2Engine(PBKDF2Parameters parameters, PRF prf) {
/*  46 */     this.parameters = parameters;
/*  47 */     this.prf = prf;
/*     */   }
/*     */ 
/*     */   
/*     */   public byte[] deriveKey(String inputPassword) {
/*  52 */     return deriveKey(inputPassword, 0);
/*     */   }
/*     */ 
/*     */   
/*     */   public byte[] deriveKey(String inputPassword, int dkLen) {
/*  57 */     byte[] r = null;
/*  58 */     byte[] P = null;
/*  59 */     String charset = this.parameters.getHashCharset();
/*  60 */     if (inputPassword == null)
/*     */     {
/*  62 */       inputPassword = "";
/*     */     }
/*     */     
/*     */     try {
/*  66 */       if (charset == null)
/*     */       {
/*  68 */         P = inputPassword.getBytes();
/*     */       }
/*     */       else
/*     */       {
/*  72 */         P = inputPassword.getBytes(charset);
/*     */       }
/*     */     
/*  75 */     } catch (UnsupportedEncodingException e) {
/*     */       
/*  77 */       throw new RuntimeException(e);
/*     */     } 
/*  79 */     assertPRF(P);
/*  80 */     if (dkLen == 0)
/*     */     {
/*  82 */       dkLen = this.prf.getHLen();
/*     */     }
/*  84 */     r = PBKDF2(this.prf, this.parameters.getSalt(), this.parameters.getIterationCount(), dkLen);
/*     */     
/*  86 */     return r;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean verifyKey(String inputPassword) {
/*  91 */     byte[] referenceKey = getParameters().getDerivedKey();
/*  92 */     if (referenceKey == null || referenceKey.length == 0)
/*     */     {
/*  94 */       return false;
/*     */     }
/*  96 */     byte[] inputKey = deriveKey(inputPassword, referenceKey.length);
/*     */     
/*  98 */     if (inputKey == null || inputKey.length != referenceKey.length)
/*     */     {
/* 100 */       return false;
/*     */     }
/* 102 */     for (int i = 0; i < inputKey.length; i++) {
/*     */       
/* 104 */       if (inputKey[i] != referenceKey[i])
/*     */       {
/* 106 */         return false;
/*     */       }
/*     */     } 
/* 109 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   protected void assertPRF(byte[] P) {
/* 114 */     if (this.prf == null)
/*     */     {
/* 116 */       this.prf = new MacBasedPRF(this.parameters.getHashAlgorithm());
/*     */     }
/* 118 */     this.prf.init(P);
/*     */   }
/*     */ 
/*     */   
/*     */   public PRF getPseudoRandomFunction() {
/* 123 */     return this.prf;
/*     */   }
/*     */ 
/*     */   
/*     */   protected byte[] PBKDF2(PRF prf, byte[] S, int c, int dkLen) {
/* 128 */     if (S == null)
/*     */     {
/* 130 */       S = new byte[0];
/*     */     }
/* 132 */     int hLen = prf.getHLen();
/* 133 */     int l = ceil(dkLen, hLen);
/* 134 */     int r = dkLen - (l - 1) * hLen;
/* 135 */     byte[] T = new byte[l * hLen];
/* 136 */     int ti_offset = 0;
/* 137 */     for (int i = 1; i <= l; i++) {
/*     */       
/* 139 */       _F(T, ti_offset, prf, S, c, i);
/* 140 */       ti_offset += hLen;
/*     */     } 
/* 142 */     if (r < hLen) {
/*     */ 
/*     */       
/* 145 */       byte[] DK = new byte[dkLen];
/* 146 */       System.arraycopy(T, 0, DK, 0, dkLen);
/* 147 */       return DK;
/*     */     } 
/* 149 */     return T;
/*     */   }
/*     */ 
/*     */   
/*     */   protected int ceil(int a, int b) {
/* 154 */     int m = 0;
/* 155 */     if (a % b > 0)
/*     */     {
/* 157 */       m = 1;
/*     */     }
/* 159 */     return a / b + m;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected void _F(byte[] dest, int offset, PRF prf, byte[] S, int c, int blockIndex) {
/* 165 */     int hLen = prf.getHLen();
/* 166 */     byte[] U_r = new byte[hLen];
/*     */ 
/*     */     
/* 169 */     byte[] U_i = new byte[S.length + 4];
/* 170 */     System.arraycopy(S, 0, U_i, 0, S.length);
/* 171 */     INT(U_i, S.length, blockIndex);
/*     */     
/* 173 */     for (int i = 0; i < c; i++) {
/*     */       
/* 175 */       U_i = prf.doFinal(U_i);
/* 176 */       xor(U_r, U_i);
/*     */     } 
/* 178 */     System.arraycopy(U_r, 0, dest, offset, hLen);
/*     */   }
/*     */ 
/*     */   
/*     */   protected void xor(byte[] dest, byte[] src) {
/* 183 */     for (int i = 0; i < dest.length; i++)
/*     */     {
/* 185 */       dest[i] = (byte)(dest[i] ^ src[i]);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   protected void INT(byte[] dest, int offset, int i) {
/* 191 */     dest[offset + 0] = (byte)(i / 16777216);
/* 192 */     dest[offset + 1] = (byte)(i / 65536);
/* 193 */     dest[offset + 2] = (byte)(i / 256);
/* 194 */     dest[offset + 3] = (byte)i;
/*     */   }
/*     */ 
/*     */   
/*     */   public PBKDF2Parameters getParameters() {
/* 199 */     return this.parameters;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setParameters(PBKDF2Parameters parameters) {
/* 204 */     this.parameters = parameters;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setPseudoRandomFunction(PRF prf) {
/* 209 */     this.prf = prf;
/*     */   }
/*     */ }


/* Location:              C:\Users\DuduM\OneDrive\Área de Trabalho\mw10\modularwarfare-shining-2023.2.4.4f-fix10.jar!\net\lingala\zip4j\crypto\PBKDF2\PBKDF2Engine.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */