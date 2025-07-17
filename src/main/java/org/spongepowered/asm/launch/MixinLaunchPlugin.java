/*     */ package org.spongepowered.asm.launch;
/*     */ 
/*     */ import com.google.common.io.Resources;
/*     */ import cpw.mods.modlauncher.api.IEnvironment;
/*     */ import cpw.mods.modlauncher.serviceapi.ILaunchPluginService;
/*     */ import java.io.IOException;
/*     */ import java.net.URL;
/*     */ import java.nio.file.Path;
/*     */ import java.util.ArrayList;
/*     */ import java.util.EnumSet;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.function.Consumer;
/*     */ import org.objectweb.asm.ClassReader;
/*     */ import org.objectweb.asm.ClassVisitor;
/*     */ import org.objectweb.asm.Type;
/*     */ import org.objectweb.asm.tree.ClassNode;
/*     */ import org.spongepowered.asm.launch.platform.CommandLineOptions;
/*     */ import org.spongepowered.asm.service.IClassBytecodeProvider;
/*     */ import org.spongepowered.asm.service.IMixinService;
/*     */ import org.spongepowered.asm.service.MixinService;
/*     */ import org.spongepowered.asm.service.modlauncher.MixinServiceModLauncher;
/*     */ import org.spongepowered.asm.service.modlauncher.ModLauncherAuditTrail;
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
/*     */ 
/*     */ public class MixinLaunchPlugin
/*     */   implements ILaunchPluginService, IClassBytecodeProvider
/*     */ {
/*     */   public static final String NAME = "mixin";
/*  64 */   private final List<IClassProcessor> processors = new ArrayList<>();
/*     */ 
/*     */ 
/*     */   
/*     */   private List<String> commandLineMixins;
/*     */ 
/*     */   
/*     */   private ILaunchPluginService.ITransformerLoader transformerLoader;
/*     */ 
/*     */   
/*     */   private MixinServiceModLauncher service;
/*     */ 
/*     */   
/*     */   private ModLauncherAuditTrail auditTrail;
/*     */ 
/*     */ 
/*     */   
/*     */   public String name() {
/*  82 */     return "mixin";
/*     */   }
/*     */ 
/*     */   
/*     */   public EnumSet<ILaunchPluginService.Phase> handlesClass(Type classType, boolean isEmpty) {
/*  87 */     throw new IllegalStateException("Outdated ModLauncher");
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean processClass(ILaunchPluginService.Phase phase, ClassNode classNode, Type classType) {
/*  92 */     throw new IllegalStateException("Outdated ModLauncher");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public EnumSet<ILaunchPluginService.Phase> handlesClass(Type classType, boolean isEmpty, String reason) {
/* 101 */     if ("mixin".equals(reason)) {
/* 102 */       return Phases.NONE;
/*     */     }
/*     */ 
/*     */     
/* 106 */     EnumSet<ILaunchPluginService.Phase> phases = EnumSet.noneOf(ILaunchPluginService.Phase.class);
/* 107 */     synchronized (this.processors) {
/* 108 */       for (IClassProcessor postProcessor : this.processors) {
/* 109 */         EnumSet<ILaunchPluginService.Phase> processorVote = postProcessor.handlesClass(classType, isEmpty, reason);
/* 110 */         if (processorVote != null) {
/* 111 */           phases.addAll(processorVote);
/*     */         }
/*     */       } 
/*     */     } 
/*     */     
/* 116 */     return phases;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean processClass(ILaunchPluginService.Phase phase, ClassNode classNode, Type classType, String reason) {
/* 127 */     if ("mixin".equals(reason)) {
/* 128 */       return false;
/*     */     }
/*     */     
/* 131 */     boolean processed = false;
/*     */     
/* 133 */     synchronized (this.processors) {
/* 134 */       for (IClassProcessor postProcessor : this.processors) {
/* 135 */         processed |= postProcessor.processClass(phase, classNode, classType, reason);
/*     */       }
/*     */     } 
/*     */     
/* 139 */     return processed;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   void init(IEnvironment environment, List<String> commandLineMixins) {
/* 147 */     IMixinService service = MixinService.getService();
/* 148 */     if (!(service instanceof MixinServiceModLauncher)) {
/* 149 */       throw new IllegalStateException("Unsupported service type for ModLauncher Mixin Service");
/*     */     }
/* 151 */     this.service = (MixinServiceModLauncher)service;
/* 152 */     this.auditTrail = (ModLauncherAuditTrail)this.service.getAuditTrail();
/* 153 */     synchronized (this.processors) {
/* 154 */       this.processors.addAll(this.service.getProcessors());
/*     */     } 
/* 156 */     this.commandLineMixins = commandLineMixins;
/* 157 */     this.service.onInit(this);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void customAuditConsumer(String className, Consumer<String[]> auditDataAcceptor) {
/* 166 */     if (this.auditTrail != null) {
/* 167 */       this.auditTrail.setConsumer(className, auditDataAcceptor);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   public void addResource(Path resource, String name) {
/* 174 */     this.service.getPrimaryContainer().addResource(name, resource);
/*     */   }
/*     */ 
/*     */   
/*     */   public void addResources(List<Map.Entry<String, Path>> resources) {
/* 179 */     this.service.getPrimaryContainer().addResources(resources);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public <T> T getExtension() {
/* 187 */     return null;
/*     */   }
/*     */ 
/*     */   
/*     */   public void initializeLaunch(ILaunchPluginService.ITransformerLoader transformerLoader, Path[] specialPaths) {
/* 192 */     this.transformerLoader = transformerLoader;
/* 193 */     MixinBootstrap.doInit(CommandLineOptions.of(this.commandLineMixins));
/* 194 */     MixinBootstrap.inject();
/* 195 */     this.service.onStartup();
/*     */   }
/*     */ 
/*     */   
/*     */   public ClassNode getClassNode(String name) throws ClassNotFoundException, IOException {
/* 200 */     return getClassNode(name, true);
/*     */   }
/*     */   
/*     */   public ClassNode getClassNode(String name, boolean runTransformers) throws ClassNotFoundException, IOException {
/*     */     byte[] classBytes;
/* 205 */     if (!runTransformers) {
/* 206 */       throw new IllegalArgumentException("ModLauncher service does not currently support retrieval of untransformed bytecode");
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     try {
/* 212 */       classBytes = this.transformerLoader.buildTransformedClassNodeFor(name);
/* 213 */     } catch (ClassNotFoundException ex) {
/* 214 */       URL url = Thread.currentThread().getContextClassLoader().getResource(name.replace('.', '/') + ".class");
/* 215 */       if (url == null) {
/* 216 */         throw ex;
/*     */       }
/*     */       try {
/* 219 */         classBytes = Resources.asByteSource(url).read();
/* 220 */       } catch (IOException ioex) {
/* 221 */         throw ex;
/*     */       } 
/*     */     } 
/*     */     
/* 225 */     if (classBytes == null) {
/* 226 */       throw new ClassNotFoundException(name.replace('/', '.'));
/*     */     }
/*     */     
/* 229 */     ClassNode classNode = new ClassNode();
/* 230 */     ClassReader classReader = new ClassReader(classBytes);
/* 231 */     classReader.accept((ClassVisitor)classNode, 8);
/* 232 */     return classNode;
/*     */   }
/*     */ }


/* Location:              C:\Users\DuduM\OneDrive\Área de Trabalho\mw10\modularwarfare-shining-2023.2.4.4f-fix10.jar!\org\spongepowered\asm\launch\MixinLaunchPlugin.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */