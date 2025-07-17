/*     */ package org.spongepowered.asm.mixin.transformer.ext.extensions;
/*     */ 
/*     */ import com.google.common.io.Files;
/*     */ import com.google.common.io.MoreFiles;
/*     */ import com.google.common.io.RecursiveDeleteOption;
/*     */ import java.io.File;
/*     */ import java.io.IOException;
/*     */ import java.lang.reflect.Constructor;
/*     */ import java.util.regex.Pattern;
/*     */ import org.apache.logging.log4j.LogManager;
/*     */ import org.apache.logging.log4j.Logger;
/*     */ import org.objectweb.asm.ClassVisitor;
/*     */ import org.objectweb.asm.tree.ClassNode;
/*     */ import org.spongepowered.asm.mixin.MixinEnvironment;
/*     */ import org.spongepowered.asm.mixin.transformer.ext.IDecompiler;
/*     */ import org.spongepowered.asm.mixin.transformer.ext.IExtension;
/*     */ import org.spongepowered.asm.mixin.transformer.ext.ITargetClassContext;
/*     */ import org.spongepowered.asm.transformers.MixinClassWriter;
/*     */ import org.spongepowered.asm.util.Constants;
/*     */ import org.spongepowered.asm.util.perf.Profiler;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class ExtensionClassExporter
/*     */   implements IExtension
/*     */ {
/*     */   private static final String DECOMPILER_CLASS = "org.spongepowered.asm.mixin.transformer.debug.RuntimeDecompiler";
/*     */   private static final String EXPORT_CLASS_DIR = "class";
/*     */   private static final String EXPORT_JAVA_DIR = "java";
/*  62 */   private static final Logger logger = LogManager.getLogger("mixin");
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  67 */   private final File classExportDir = new File(Constants.DEBUG_OUTPUT_DIR, "class");
/*     */ 
/*     */   
/*     */   private final IDecompiler decompiler;
/*     */ 
/*     */ 
/*     */   
/*     */   public ExtensionClassExporter(MixinEnvironment env) {
/*  75 */     this.decompiler = initDecompiler(env, new File(Constants.DEBUG_OUTPUT_DIR, "java"));
/*     */     
/*     */     try {
/*  78 */       MoreFiles.deleteRecursively(this.classExportDir.toPath(), new RecursiveDeleteOption[] { RecursiveDeleteOption.ALLOW_INSECURE });
/*  79 */     } catch (IOException ex) {
/*  80 */       logger.debug("Error cleaning class output directory: {}", new Object[] { ex.getMessage() });
/*     */     } 
/*     */   }
/*     */   
/*     */   public boolean isDecompilerActive() {
/*  85 */     return (this.decompiler != null);
/*     */   }
/*     */   
/*     */   private IDecompiler initDecompiler(MixinEnvironment env, File outputPath) {
/*  89 */     if (!env.getOption(MixinEnvironment.Option.DEBUG_EXPORT_DECOMPILE)) {
/*  90 */       return null;
/*     */     }
/*     */     
/*     */     try {
/*  94 */       boolean as = env.getOption(MixinEnvironment.Option.DEBUG_EXPORT_DECOMPILE_THREADED);
/*  95 */       logger.info("Attempting to load Fernflower decompiler{}", new Object[] { as ? " (Threaded mode)" : "" });
/*  96 */       String className = "org.spongepowered.asm.mixin.transformer.debug.RuntimeDecompiler" + (as ? "Async" : "");
/*     */       
/*  98 */       Class<? extends IDecompiler> clazz = (Class)Class.forName(className);
/*  99 */       Constructor<? extends IDecompiler> ctor = clazz.getDeclaredConstructor(new Class[] { File.class });
/* 100 */       IDecompiler decompiler = ctor.newInstance(new Object[] { outputPath });
/* 101 */       logger.info("Fernflower decompiler was successfully initialised, exported classes will be decompiled{}", new Object[] { as ? " in a separate thread" : "" });
/*     */       
/* 103 */       return decompiler;
/* 104 */     } catch (Throwable th) {
/* 105 */       logger.info("Fernflower could not be loaded, exported classes will not be decompiled. {}: {}", new Object[] { th
/* 106 */             .getClass().getSimpleName(), th.getMessage() });
/*     */       
/* 108 */       return null;
/*     */     } 
/*     */   }
/*     */   private String prepareFilter(String filter) {
/* 112 */     filter = "^\\Q" + filter.replace("**", "").replace("*", "").replace("?", "") + "\\E$";
/* 113 */     return filter.replace("", "\\E.*\\Q").replace("", "\\E[^\\.]+\\Q").replace("", "\\E.\\Q").replace("\\Q\\E", "");
/*     */   }
/*     */   
/*     */   private boolean applyFilter(String filter, String subject) {
/* 117 */     return Pattern.compile(prepareFilter(filter), 2).matcher(subject).matches();
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean checkActive(MixinEnvironment environment) {
/* 122 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void preApply(ITargetClassContext context) {}
/*     */ 
/*     */ 
/*     */   
/*     */   public void postApply(ITargetClassContext context) {}
/*     */ 
/*     */ 
/*     */   
/*     */   public void export(MixinEnvironment env, String name, boolean force, ClassNode classNode) {
/* 136 */     if (force || env.getOption(MixinEnvironment.Option.DEBUG_EXPORT)) {
/* 137 */       String filter = env.getOptionValue(MixinEnvironment.Option.DEBUG_EXPORT_FILTER);
/* 138 */       if (force || filter == null || applyFilter(filter, name)) {
/* 139 */         Profiler.Section exportTimer = MixinEnvironment.getProfiler().begin("debug.export");
/*     */         
/* 141 */         File outputFile = dumpClass(name.replace('.', '/'), classNode);
/* 142 */         if (this.decompiler != null) {
/* 143 */           this.decompiler.decompile(outputFile);
/*     */         }
/* 145 */         exportTimer.end();
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public File dumpClass(String fileName, ClassNode classNode) {
/* 158 */     File outputFile = new File(this.classExportDir, fileName + ".class");
/* 159 */     outputFile.getParentFile().mkdirs();
/*     */     try {
/* 161 */       byte[] bytecode = getClassBytes(classNode, true);
/* 162 */       if (bytecode != null) {
/* 163 */         Files.write(bytecode, outputFile);
/*     */       }
/* 165 */     } catch (IOException iOException) {}
/*     */ 
/*     */     
/* 168 */     return outputFile;
/*     */   }
/*     */   
/*     */   private static byte[] getClassBytes(ClassNode classNode, boolean computeFrames) {
/* 172 */     byte[] bytes = null;
/*     */     try {
/* 174 */       MixinClassWriter cw = new MixinClassWriter(computeFrames ? 2 : 0);
/* 175 */       classNode.accept((ClassVisitor)cw);
/* 176 */       bytes = cw.toByteArray();
/* 177 */     } catch (NegativeArraySizeException ex) {
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 182 */       if (computeFrames) {
/* 183 */         logger.warn("Exporting class {} with COMPUTE_FRAMES failed! Trying a raw export.", new Object[] { classNode.name });
/* 184 */         return getClassBytes(classNode, false);
/*     */       } 
/* 186 */       ex.printStackTrace();
/* 187 */     } catch (Exception ex) {
/*     */       
/* 189 */       ex.printStackTrace();
/*     */     } 
/* 191 */     return bytes;
/*     */   }
/*     */ }


/* Location:              C:\Users\DuduM\OneDrive\Área de Trabalho\mw10\modularwarfare-shining-2023.2.4.4f-fix10.jar!\org\spongepowered\asm\mixin\transformer\ext\extensions\ExtensionClassExporter.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */