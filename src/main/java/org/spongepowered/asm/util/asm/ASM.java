/*     */ package org.spongepowered.asm.util.asm;
/*     */ 
/*     */ import java.lang.reflect.Field;
/*     */ import org.objectweb.asm.Opcodes;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class ASM
/*     */ {
/*  37 */   private static int majorVersion = 5;
/*  38 */   private static int minorVersion = 0;
/*  39 */   private static String maxVersion = "FALLBACK";
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  44 */   public static final int API_VERSION = detectVersion();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static int getApiVersionMajor() {
/*  53 */     return majorVersion;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static int getApiVersionMinor() {
/*  60 */     return minorVersion;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static String getApiVersionString() {
/*  69 */     return String.format("ASM %d.%d (%s)", new Object[] { Integer.valueOf(majorVersion), Integer.valueOf(minorVersion), maxVersion });
/*     */   }
/*     */   
/*     */   private static int detectVersion() {
/*  73 */     int apiVersion = 262144;
/*     */     
/*  75 */     for (Field field : Opcodes.class.getDeclaredFields()) {
/*  76 */       if (field.getType() == int.class && field.getName().startsWith("ASM")) {
/*     */         
/*     */         try {
/*     */ 
/*     */           
/*  81 */           int version = field.getInt(null);
/*     */ 
/*     */           
/*  84 */           int minor = version >> 8 & 0xFF;
/*  85 */           int major = version >> 16 & 0xFF;
/*  86 */           boolean experimental = ((version >> 24 & 0xFF) != 0);
/*     */           
/*  88 */           if (major >= majorVersion) {
/*  89 */             maxVersion = field.getName();
/*  90 */             if (!experimental) {
/*  91 */               apiVersion = version;
/*  92 */               majorVersion = major;
/*  93 */               minorVersion = minor;
/*     */             } 
/*     */           } 
/*  96 */         } catch (ReflectiveOperationException ex) {
/*  97 */           throw new Error(ex);
/*     */         } 
/*     */       }
/*     */     } 
/* 101 */     return apiVersion;
/*     */   }
/*     */ }


/* Location:              C:\Users\DuduM\OneDrive\Área de Trabalho\mw10\modularwarfare-shining-2023.2.4.4f-fix10.jar!\org\spongepowered\as\\util\asm\ASM.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */