/*     */ package com.modularwarfare.utility;
/*     */ 
/*     */ import java.lang.reflect.Field;
/*     */ import java.lang.reflect.InvocationTargetException;
/*     */ import java.lang.reflect.Method;
/*     */ import net.minecraft.client.Minecraft;
/*     */ import net.optifine.shaders.MWFOptifineShadesHelper;
/*     */ import net.optifine.shaders.Program;
/*     */ import net.optifine.shaders.Shaders;
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
/*     */ public class OptifineHelper
/*     */ {
/*  22 */   private static Boolean loaded = null;
/*     */   private static Field shaderName;
/*     */   private static Field gbuffersFormat;
/*     */   
/*     */   public static boolean isLoaded() {
/*  27 */     if (loaded == null) {
/*     */       try {
/*  29 */         Class.forName("optifine.Installer");
/*  30 */         loaded = Boolean.valueOf(true);
/*  31 */       } catch (ClassNotFoundException e) {
/*  32 */         loaded = Boolean.valueOf(false);
/*     */       } 
/*     */     }
/*  35 */     return loaded.booleanValue();
/*     */   }
/*     */   
/*     */   public static boolean isRenderingDfb() {
/*  39 */     if (isShadersEnabled()) {
/*  40 */       return Shaders.isRenderingDfb;
/*     */     }
/*  42 */     return false;
/*     */   }
/*     */   
/*     */   public static void checkBufferFlip(Program program) {
/*  46 */     if (isLoaded() && 
/*  47 */       isShadersEnabled()) {
/*     */       
/*     */       try {
/*  50 */         Class<?> clazz = Class.forName("net.optifine.shaders.Shaders");
/*  51 */         Method m = clazz.getDeclaredMethod("checkBufferFlip", new Class[] { Program.class });
/*  52 */         m.setAccessible(true);
/*  53 */         m.invoke((Object)null, new Object[] { program });
/*  54 */       } catch (ClassNotFoundException e) {
/*     */         
/*  56 */         e.printStackTrace();
/*  57 */       } catch (SecurityException e) {
/*     */         
/*  59 */         e.printStackTrace();
/*  60 */       } catch (IllegalArgumentException e) {
/*     */         
/*  62 */         e.printStackTrace();
/*  63 */       } catch (NoSuchMethodException e) {
/*     */         
/*  65 */         e.printStackTrace();
/*  66 */       } catch (IllegalAccessException e) {
/*     */         
/*  68 */         e.printStackTrace();
/*  69 */       } catch (InvocationTargetException e) {
/*     */         
/*  71 */         e.printStackTrace();
/*     */       } 
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public static void bindGbuffersTextures() {
/*  78 */     if (isLoaded() && 
/*  79 */       isShadersEnabled()) {
/*     */       
/*     */       try {
/*  82 */         Class<?> clazz = Class.forName("net.optifine.shaders.Shaders");
/*  83 */         Method m = clazz.getDeclaredMethod("bindGbuffersTextures", new Class[0]);
/*  84 */         m.setAccessible(true);
/*  85 */         m.invoke((Object)null, new Object[0]);
/*  86 */       } catch (ClassNotFoundException e) {
/*     */         
/*  88 */         e.printStackTrace();
/*  89 */       } catch (SecurityException e) {
/*     */         
/*  91 */         e.printStackTrace();
/*  92 */       } catch (IllegalArgumentException e) {
/*     */         
/*  94 */         e.printStackTrace();
/*  95 */       } catch (NoSuchMethodException e) {
/*     */         
/*  97 */         e.printStackTrace();
/*  98 */       } catch (IllegalAccessException e) {
/*     */         
/* 100 */         e.printStackTrace();
/* 101 */       } catch (InvocationTargetException e) {
/*     */         
/* 103 */         e.printStackTrace();
/*     */       } 
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public static int getDrawFrameBuffer() {
/* 110 */     if (isLoaded() && 
/* 111 */       isShadersEnabled() && 
/* 112 */       Shaders.isRenderingDfb) {
/* 113 */       return MWFOptifineShadesHelper.getDFB();
/*     */     }
/*     */ 
/*     */     
/* 117 */     return (Minecraft.func_71410_x().func_147110_a()).field_147616_f;
/*     */   }
/*     */   
/*     */   public static int getPixelFormat(int internalFormat) {
/* 121 */     switch (internalFormat) {
/*     */       case 33333:
/*     */       case 33334:
/*     */       case 33339:
/*     */       case 33340:
/*     */       case 36208:
/*     */       case 36209:
/*     */       case 36226:
/*     */       case 36227:
/* 130 */         return 36251;
/*     */     } 
/* 132 */     return 32993;
/*     */   }
/*     */   
/*     */   public static int[] getGbuffersFormat() {
/* 136 */     if (isLoaded()) {
/*     */       try {
/* 138 */         Class<?> clazz = Class.forName("net.optifine.shaders.Shaders");
/* 139 */         if (clazz != null && gbuffersFormat == null) {
/* 140 */           gbuffersFormat = clazz.getDeclaredField("gbuffersFormat");
/* 141 */           gbuffersFormat.setAccessible(true);
/*     */         } 
/* 143 */         if (gbuffersFormat != null) {
/* 144 */           int[] format = (int[])gbuffersFormat.get((Object)null);
/* 145 */           return format;
/*     */         } 
/* 147 */       } catch (Exception err) {
/* 148 */         err.printStackTrace();
/*     */       } 
/*     */     }
/* 151 */     return null;
/*     */   }
/*     */   
/*     */   public static boolean isShadersEnabled() {
/* 155 */     if (isLoaded()) {
/*     */       try {
/* 157 */         Class<?> clazz = Class.forName("net.optifine.shaders.Shaders");
/* 158 */         if (clazz != null && shaderName == null) {
/* 159 */           shaderName = clazz.getDeclaredField("shaderPackLoaded");
/*     */         }
/* 161 */         if (shaderName != null) {
/* 162 */           boolean name = ((Boolean)shaderName.get((Object)null)).booleanValue();
/* 163 */           return name;
/*     */         } 
/* 165 */       } catch (Exception exception) {}
/*     */     }
/*     */     
/* 168 */     return false;
/*     */   }
/*     */   
/*     */   public static int getProgram() {
/* 172 */     return Shaders.activeProgramID;
/*     */   }
/*     */ }


/* Location:              C:\Users\DuduM\OneDrive\Área de Trabalho\mw10\modularwarfare-shining-2023.2.4.4f-fix10.jar!\com\modularwarfar\\utility\OptifineHelper.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */