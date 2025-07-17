/*     */ package net.lingala.zip4j.util;
/*     */ 
/*     */ import java.io.DataInput;
/*     */ import java.io.IOException;
/*     */ import net.lingala.zip4j.exception.ZipException;
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
/*     */ public class Raw
/*     */ {
/*     */   public static long readLongLittleEndian(byte[] array, int pos) {
/*  27 */     long temp = 0L;
/*  28 */     temp |= (array[pos + 7] & 0xFF);
/*  29 */     temp <<= 8L;
/*  30 */     temp |= (array[pos + 6] & 0xFF);
/*  31 */     temp <<= 8L;
/*  32 */     temp |= (array[pos + 5] & 0xFF);
/*  33 */     temp <<= 8L;
/*  34 */     temp |= (array[pos + 4] & 0xFF);
/*  35 */     temp <<= 8L;
/*  36 */     temp |= (array[pos + 3] & 0xFF);
/*  37 */     temp <<= 8L;
/*  38 */     temp |= (array[pos + 2] & 0xFF);
/*  39 */     temp <<= 8L;
/*  40 */     temp |= (array[pos + 1] & 0xFF);
/*  41 */     temp <<= 8L;
/*  42 */     temp |= (array[pos] & 0xFF);
/*  43 */     return temp;
/*     */   }
/*     */   
/*     */   public static int readLeInt(DataInput di, byte[] b) throws ZipException {
/*     */     try {
/*  48 */       di.readFully(b, 0, 4);
/*  49 */     } catch (IOException e) {
/*  50 */       throw new ZipException(e);
/*     */     } 
/*  52 */     return b[0] & 0xFF | (b[1] & 0xFF) << 8 | (b[2] & 0xFF | (b[3] & 0xFF) << 8) << 16;
/*     */   }
/*     */ 
/*     */   
/*     */   public static int readShortLittleEndian(byte[] b, int off) {
/*  57 */     return b[off] & 0xFF | (b[off + 1] & 0xFF) << 8;
/*     */   }
/*     */   
/*     */   public static final short readShortBigEndian(byte[] array, int pos) {
/*  61 */     short temp = 0;
/*  62 */     temp = (short)(temp | array[pos] & 0xFF);
/*  63 */     temp = (short)(temp << 8);
/*  64 */     temp = (short)(temp | array[pos + 1] & 0xFF);
/*  65 */     return temp;
/*     */   }
/*     */   
/*     */   public static int readIntLittleEndian(byte[] b, int off) {
/*  69 */     return b[off] & 0xFF | (b[off + 1] & 0xFF) << 8 | (b[off + 2] & 0xFF | (b[off + 3] & 0xFF) << 8) << 16;
/*     */   }
/*     */ 
/*     */   
/*     */   public static byte[] toByteArray(int in, int outSize) {
/*  74 */     byte[] out = new byte[outSize];
/*  75 */     byte[] intArray = toByteArray(in);
/*  76 */     for (int i = 0; i < intArray.length && i < outSize; i++) {
/*  77 */       out[i] = intArray[i];
/*     */     }
/*  79 */     return out;
/*     */   }
/*     */   
/*     */   public static byte[] toByteArray(int in) {
/*  83 */     byte[] out = new byte[4];
/*     */     
/*  85 */     out[0] = (byte)in;
/*  86 */     out[1] = (byte)(in >> 8);
/*  87 */     out[2] = (byte)(in >> 16);
/*  88 */     out[3] = (byte)(in >> 24);
/*     */     
/*  90 */     return out;
/*     */   }
/*     */ 
/*     */   
/*     */   public static final void writeShortLittleEndian(byte[] array, int pos, short value) {
/*  95 */     array[pos + 1] = (byte)(value >>> 8);
/*  96 */     array[pos] = (byte)(value & 0xFF);
/*     */   }
/*     */ 
/*     */   
/*     */   public static final void writeIntLittleEndian(byte[] array, int pos, int value) {
/* 101 */     array[pos + 3] = (byte)(value >>> 24);
/* 102 */     array[pos + 2] = (byte)(value >>> 16);
/* 103 */     array[pos + 1] = (byte)(value >>> 8);
/* 104 */     array[pos] = (byte)(value & 0xFF);
/*     */   }
/*     */ 
/*     */   
/*     */   public static void writeLongLittleEndian(byte[] array, int pos, long value) {
/* 109 */     array[pos + 7] = (byte)(int)(value >>> 56L);
/* 110 */     array[pos + 6] = (byte)(int)(value >>> 48L);
/* 111 */     array[pos + 5] = (byte)(int)(value >>> 40L);
/* 112 */     array[pos + 4] = (byte)(int)(value >>> 32L);
/* 113 */     array[pos + 3] = (byte)(int)(value >>> 24L);
/* 114 */     array[pos + 2] = (byte)(int)(value >>> 16L);
/* 115 */     array[pos + 1] = (byte)(int)(value >>> 8L);
/* 116 */     array[pos] = (byte)(int)(value & 0xFFL);
/*     */   }
/*     */   
/*     */   public static byte bitArrayToByte(int[] bitArray) throws ZipException {
/* 120 */     if (bitArray == null) {
/* 121 */       throw new ZipException("bit array is null, cannot calculate byte from bits");
/*     */     }
/*     */     
/* 124 */     if (bitArray.length != 8) {
/* 125 */       throw new ZipException("invalid bit array length, cannot calculate byte");
/*     */     }
/*     */     
/* 128 */     if (!checkBits(bitArray)) {
/* 129 */       throw new ZipException("invalid bits provided, bits contain other values than 0 or 1");
/*     */     }
/*     */     
/* 132 */     int retNum = 0;
/* 133 */     for (int i = 0; i < bitArray.length; i++) {
/* 134 */       retNum = (int)(retNum + Math.pow(2.0D, i) * bitArray[i]);
/*     */     }
/*     */     
/* 137 */     return (byte)retNum;
/*     */   }
/*     */   
/*     */   private static boolean checkBits(int[] bitArray) {
/* 141 */     for (int i = 0; i < bitArray.length; i++) {
/* 142 */       if (bitArray[i] != 0 && bitArray[i] != 1) {
/* 143 */         return false;
/*     */       }
/*     */     } 
/* 146 */     return true;
/*     */   }
/*     */ }


/* Location:              C:\Users\DuduM\OneDrive\Área de Trabalho\mw10\modularwarfare-shining-2023.2.4.4f-fix10.jar!\net\lingala\zip4\\util\Raw.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */