/*     */ package org.spongepowered.asm.launch;
/*     */ 
/*     */ import java.util.List;
/*     */ import org.apache.logging.log4j.LogManager;
/*     */ import org.apache.logging.log4j.Logger;
/*     */ import org.spongepowered.asm.launch.platform.CommandLineOptions;
/*     */ import org.spongepowered.asm.launch.platform.MixinPlatformManager;
/*     */ import org.spongepowered.asm.mixin.MixinEnvironment;
/*     */ import org.spongepowered.asm.service.MixinService;
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
/*     */ public abstract class MixinBootstrap
/*     */ {
/*     */   public static final String VERSION = "0.8";
/*  69 */   private static final Logger logger = LogManager.getLogger("mixin");
/*     */   
/*     */   private static boolean initialised = false;
/*     */   
/*     */   private static boolean initState = true;
/*     */   private static MixinPlatformManager platform;
/*     */   
/*     */   static {
/*  77 */     MixinService.boot();
/*  78 */     MixinService.getService().prepare();
/*     */   }
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
/*     */   @Deprecated
/*     */   public static void addProxy() {
/*  93 */     MixinService.getService().beginPhase();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static MixinPlatformManager getPlatform() {
/* 100 */     if (platform == null) {
/* 101 */       Object globalPlatformManager = GlobalProperties.get(GlobalProperties.Keys.PLATFORM_MANAGER);
/* 102 */       if (globalPlatformManager instanceof MixinPlatformManager) {
/* 103 */         platform = (MixinPlatformManager)globalPlatformManager;
/*     */       } else {
/* 105 */         platform = new MixinPlatformManager();
/* 106 */         GlobalProperties.put(GlobalProperties.Keys.PLATFORM_MANAGER, platform);
/* 107 */         platform.init();
/*     */       } 
/*     */     } 
/* 110 */     return platform;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void init() {
/* 117 */     if (!start()) {
/*     */       return;
/*     */     }
/*     */     
/* 121 */     doInit(CommandLineOptions.defaultArgs());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static boolean start() {
/* 128 */     if (isSubsystemRegistered()) {
/* 129 */       if (!checkSubsystemVersion()) {
/* 130 */         throw new MixinInitialisationError("Mixin subsystem version " + getActiveSubsystemVersion() + " was already initialised. Cannot bootstrap version " + "0.8");
/*     */       }
/*     */       
/* 133 */       return false;
/*     */     } 
/*     */     
/* 136 */     registerSubsystem("0.8");
/*     */     
/* 138 */     if (!initialised) {
/* 139 */       initialised = true;
/*     */       
/* 141 */       MixinEnvironment.Phase initialPhase = MixinService.getService().getInitialPhase();
/* 142 */       if (initialPhase == MixinEnvironment.Phase.DEFAULT) {
/* 143 */         logger.error("Initialising mixin subsystem after game pre-init phase! Some mixins may be skipped.");
/* 144 */         MixinEnvironment.init(initialPhase);
/* 145 */         getPlatform().prepare(CommandLineOptions.defaultArgs());
/* 146 */         initState = false;
/*     */       } else {
/* 148 */         MixinEnvironment.init(initialPhase);
/*     */       } 
/*     */       
/* 151 */       MixinService.getService().beginPhase();
/*     */     } 
/*     */     
/* 154 */     getPlatform();
/*     */     
/* 156 */     return true;
/*     */   }
/*     */   
/*     */   @Deprecated
/*     */   static void doInit(List<String> args) {
/* 161 */     doInit(CommandLineOptions.ofArgs(args));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static void doInit(CommandLineOptions args) {
/* 168 */     if (!initialised) {
/* 169 */       if (isSubsystemRegistered()) {
/* 170 */         logger.warn("Multiple Mixin containers present, init suppressed for 0.8");
/*     */         
/*     */         return;
/*     */       } 
/* 174 */       throw new IllegalStateException("MixinBootstrap.doInit() called before MixinBootstrap.start()");
/*     */     } 
/*     */     
/* 177 */     getPlatform().getPhaseProviderClasses();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 183 */     if (initState) {
/* 184 */       getPlatform().prepare(args);
/* 185 */       MixinService.getService().init();
/*     */     } 
/*     */   }
/*     */   
/*     */   static void inject() {
/* 190 */     getPlatform().inject();
/*     */   }
/*     */   
/*     */   private static boolean isSubsystemRegistered() {
/* 194 */     return (GlobalProperties.get(GlobalProperties.Keys.INIT) != null);
/*     */   }
/*     */   
/*     */   private static boolean checkSubsystemVersion() {
/* 198 */     return "0.8".equals(getActiveSubsystemVersion());
/*     */   }
/*     */   
/*     */   private static Object getActiveSubsystemVersion() {
/* 202 */     Object version = GlobalProperties.get(GlobalProperties.Keys.INIT);
/* 203 */     return (version != null) ? version : "";
/*     */   }
/*     */   
/*     */   private static void registerSubsystem(String version) {
/* 207 */     GlobalProperties.put(GlobalProperties.Keys.INIT, version);
/*     */   }
/*     */ }


/* Location:              C:\Users\DuduM\OneDrive\Área de Trabalho\mw10\modularwarfare-shining-2023.2.4.4f-fix10.jar!\org\spongepowered\asm\launch\MixinBootstrap.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */