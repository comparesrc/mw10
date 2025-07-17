/*     */ package org.spongepowered.asm.mixin.transformer.debug;
/*     */ 
/*     */ import com.google.common.base.Charsets;
/*     */ import com.google.common.collect.ImmutableMap;
/*     */ import com.google.common.io.Files;
/*     */ import com.google.common.io.MoreFiles;
/*     */ import com.google.common.io.RecursiveDeleteOption;
/*     */ import java.io.File;
/*     */ import java.io.IOException;
/*     */ import java.util.Map;
/*     */ import java.util.jar.Manifest;
/*     */ import org.apache.logging.log4j.Level;
/*     */ import org.apache.logging.log4j.LogManager;
/*     */ import org.apache.logging.log4j.Logger;
/*     */ import org.jetbrains.java.decompiler.main.Fernflower;
/*     */ import org.jetbrains.java.decompiler.main.extern.IBytecodeProvider;
/*     */ import org.jetbrains.java.decompiler.main.extern.IFernflowerLogger;
/*     */ import org.jetbrains.java.decompiler.main.extern.IResultSaver;
/*     */ import org.jetbrains.java.decompiler.util.InterpreterUtil;
/*     */ import org.spongepowered.asm.mixin.transformer.ext.IDecompiler;
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
/*     */ public class RuntimeDecompiler
/*     */   extends IFernflowerLogger
/*     */   implements IDecompiler, IResultSaver
/*     */ {
/*  53 */   private static final Level[] SEVERITY_LEVELS = new Level[] { Level.TRACE, Level.INFO, Level.WARN, Level.ERROR };
/*     */   
/*  55 */   private final Map<String, Object> options = (Map<String, Object>)ImmutableMap.builder()
/*  56 */     .put("din", "0").put("rbr", "0").put("dgs", "1").put("asc", "1")
/*  57 */     .put("den", "1").put("hdc", "1").put("ind", "    ").build();
/*     */   
/*     */   private final File outputPath;
/*     */   
/*  61 */   protected final Logger logger = LogManager.getLogger("fernflower");
/*     */   
/*     */   public RuntimeDecompiler(File outputPath) {
/*  64 */     this.outputPath = outputPath;
/*  65 */     if (this.outputPath.exists()) {
/*     */       try {
/*  67 */         MoreFiles.deleteRecursively(this.outputPath.toPath(), new RecursiveDeleteOption[] { RecursiveDeleteOption.ALLOW_INSECURE });
/*  68 */       } catch (IOException ex) {
/*  69 */         this.logger.debug("Error cleaning output directory: {}", new Object[] { ex.getMessage() });
/*     */       } 
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public void decompile(File file) {
/*     */     try {
/*  77 */       Fernflower fernflower = new Fernflower(new IBytecodeProvider()
/*     */           {
/*     */             private byte[] byteCode;
/*     */ 
/*     */             
/*     */             public byte[] getBytecode(String externalPath, String internalPath) throws IOException {
/*  83 */               if (this.byteCode == null) {
/*  84 */                 this.byteCode = InterpreterUtil.getBytes(new File(externalPath));
/*     */               }
/*  86 */               return this.byteCode;
/*     */             }
/*     */           },  this, this.options, this);
/*     */ 
/*     */       
/*  91 */       fernflower.getStructContext().addSpace(file, true);
/*  92 */       fernflower.decompileContext();
/*  93 */     } catch (Throwable ex) {
/*  94 */       this.logger.warn("Decompilation error while processing {}", new Object[] { file.getName() });
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void saveFolder(String path) {}
/*     */ 
/*     */   
/*     */   public void saveClassFile(String path, String qualifiedName, String entryName, String content, int[] mapping) {
/* 104 */     File file = new File(this.outputPath, qualifiedName + ".java");
/* 105 */     file.getParentFile().mkdirs();
/*     */     try {
/* 107 */       this.logger.info("Writing {}", new Object[] { file.getAbsolutePath() });
/* 108 */       Files.write(content, file, Charsets.UTF_8);
/* 109 */     } catch (IOException ex) {
/* 110 */       writeMessage("Cannot write source file " + file, ex);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void startReadingClass(String className) {
/* 116 */     this.logger.info("Decompiling {}", new Object[] { className });
/*     */   }
/*     */ 
/*     */   
/*     */   public void writeMessage(String message, IFernflowerLogger.Severity severity) {
/* 121 */     this.logger.log(SEVERITY_LEVELS[severity.ordinal()], message);
/*     */   }
/*     */ 
/*     */   
/*     */   public void writeMessage(String message, Throwable t) {
/* 126 */     this.logger.warn("{} {}: {}", new Object[] { message, t.getClass().getSimpleName(), t.getMessage() });
/*     */   }
/*     */ 
/*     */   
/*     */   public void writeMessage(String message, IFernflowerLogger.Severity severity, Throwable t) {
/* 131 */     this.logger.log(SEVERITY_LEVELS[severity.ordinal()], message, t);
/*     */   }
/*     */   
/*     */   public void copyFile(String source, String path, String entryName) {}
/*     */   
/*     */   public void createArchive(String path, String archiveName, Manifest manifest) {}
/*     */   
/*     */   public void saveDirEntry(String path, String archiveName, String entryName) {}
/*     */   
/*     */   public void copyEntry(String source, String path, String archiveName, String entry) {}
/*     */   
/*     */   public void saveClassEntry(String path, String archiveName, String qualifiedName, String entryName, String content) {}
/*     */   
/*     */   public void closeArchive(String path, String archiveName) {}
/*     */ }


/* Location:              C:\Users\DuduM\OneDrive\Área de Trabalho\mw10\modularwarfare-shining-2023.2.4.4f-fix10.jar!\org\spongepowered\asm\mixin\transformer\debug\RuntimeDecompiler.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */