/*     */ package org.spongepowered.asm.service.modlauncher;
/*     */ 
/*     */ import com.google.common.collect.ImmutableList;
/*     */ import cpw.mods.modlauncher.Launcher;
/*     */ import cpw.mods.modlauncher.api.ITransformationService;
/*     */ import java.io.InputStream;
/*     */ import java.util.Collection;
/*     */ import org.spongepowered.asm.launch.IClassProcessor;
/*     */ import org.spongepowered.asm.launch.platform.container.ContainerHandleModLauncher;
/*     */ import org.spongepowered.asm.launch.platform.container.IContainerHandle;
/*     */ import org.spongepowered.asm.mixin.MixinEnvironment;
/*     */ import org.spongepowered.asm.mixin.transformer.MixinTransformationHandler;
/*     */ import org.spongepowered.asm.service.IClassBytecodeProvider;
/*     */ import org.spongepowered.asm.service.IClassProvider;
/*     */ import org.spongepowered.asm.service.IClassTracker;
/*     */ import org.spongepowered.asm.service.IMixinAuditTrail;
/*     */ import org.spongepowered.asm.service.ITransformerProvider;
/*     */ import org.spongepowered.asm.service.MixinServiceAbstract;
/*     */ import org.spongepowered.asm.util.IConsumer;
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
/*     */ public class MixinServiceModLauncher
/*     */   extends MixinServiceAbstract
/*     */ {
/*     */   private static final String MODLAUNCHER_SPECIFICATION_VERSION = "4.0";
/*     */   private IClassProvider classProvider;
/*     */   private IClassBytecodeProvider bytecodeProvider;
/*     */   private MixinTransformationHandler transformationHandler;
/*     */   private ModLauncherClassTracker classTracker;
/*     */   private ModLauncherAuditTrail auditTrail;
/*     */   private IConsumer<MixinEnvironment.Phase> phaseConsumer;
/*     */   private volatile boolean initialised;
/*  96 */   private ContainerHandleModLauncher rootContainer = new ContainerHandleModLauncher(getName());
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void onInit(IClassBytecodeProvider bytecodeProvider) {
/* 104 */     if (this.initialised) {
/* 105 */       throw new IllegalStateException("Already initialised");
/*     */     }
/* 107 */     this.initialised = true;
/* 108 */     this.bytecodeProvider = bytecodeProvider;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void onStartup() {
/* 115 */     this.phaseConsumer.accept(MixinEnvironment.Phase.DEFAULT);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void wire(MixinEnvironment.Phase phase, IConsumer<MixinEnvironment.Phase> phaseConsumer) {
/* 122 */     super.wire(phase, phaseConsumer);
/* 123 */     this.phaseConsumer = phaseConsumer;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getName() {
/* 131 */     return "ModLauncher";
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public MixinEnvironment.CompatibilityLevel getMinCompatibilityLevel() {
/* 140 */     return MixinEnvironment.CompatibilityLevel.JAVA_8;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isValid() {
/*     */     try {
/* 149 */       Launcher.INSTANCE.hashCode();
/* 150 */       Package pkg = ITransformationService.class.getPackage();
/* 151 */       if (!pkg.isCompatibleWith("4.0")) {
/* 152 */         return false;
/*     */       }
/* 154 */     } catch (Throwable th) {
/* 155 */       return false;
/*     */     } 
/* 157 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public IClassProvider getClassProvider() {
/* 165 */     if (this.classProvider == null) {
/* 166 */       this.classProvider = new ModLauncherClassProvider();
/*     */     }
/* 168 */     return this.classProvider;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public IClassBytecodeProvider getBytecodeProvider() {
/* 176 */     if (this.bytecodeProvider == null) {
/* 177 */       throw new IllegalStateException("Service initialisation incomplete");
/*     */     }
/* 179 */     return this.bytecodeProvider;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ITransformerProvider getTransformerProvider() {
/* 187 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public IClassTracker getClassTracker() {
/* 195 */     if (this.classTracker == null) {
/* 196 */       this.classTracker = new ModLauncherClassTracker();
/*     */     }
/* 198 */     return this.classTracker;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public IMixinAuditTrail getAuditTrail() {
/* 206 */     if (this.auditTrail == null) {
/* 207 */       this.auditTrail = new ModLauncherAuditTrail();
/*     */     }
/* 209 */     return this.auditTrail;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private IClassProcessor getTransformationHandler() {
/* 216 */     if (this.transformationHandler == null) {
/* 217 */       this.transformationHandler = new MixinTransformationHandler();
/*     */     }
/* 219 */     return (IClassProcessor)this.transformationHandler;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Collection<String> getPlatformAgents() {
/* 227 */     return (Collection<String>)ImmutableList.of("org.spongepowered.asm.launch.platform.MixinPlatformAgentMinecraftForge");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ContainerHandleModLauncher getPrimaryContainer() {
/* 237 */     return this.rootContainer;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public InputStream getResourceAsStream(String name) {
/* 246 */     return Thread.currentThread().getContextClassLoader().getResourceAsStream(name);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Collection<IClassProcessor> getProcessors() {
/* 254 */     return (Collection<IClassProcessor>)ImmutableList.of(
/* 255 */         getTransformationHandler(), 
/* 256 */         getClassTracker());
/*     */   }
/*     */ }


/* Location:              C:\Users\DuduM\OneDrive\Área de Trabalho\mw10\modularwarfare-shining-2023.2.4.4f-fix10.jar!\org\spongepowered\asm\service\modlauncher\MixinServiceModLauncher.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */