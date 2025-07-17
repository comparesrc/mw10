/*     */ package org.spongepowered.asm.launch.platform;
/*     */ 
/*     */ import java.io.File;
/*     */ import java.lang.reflect.Field;
/*     */ import java.lang.reflect.InvocationTargetException;
/*     */ import java.lang.reflect.Method;
/*     */ import java.util.Collection;
/*     */ import java.util.Collections;
/*     */ import java.util.HashSet;
/*     */ import java.util.List;
/*     */ import java.util.Set;
/*     */ import net.minecraft.launchwrapper.ITweaker;
/*     */ import net.minecraft.launchwrapper.Launch;
/*     */ import net.minecraft.launchwrapper.LaunchClassLoader;
/*     */ import org.apache.logging.log4j.Level;
/*     */ import org.apache.logging.log4j.LogManager;
/*     */ import org.apache.logging.log4j.Logger;
/*     */ import org.apache.logging.log4j.core.Appender;
/*     */ import org.apache.logging.log4j.core.LogEvent;
/*     */ import org.apache.logging.log4j.core.Logger;
/*     */ import org.apache.logging.log4j.core.appender.AbstractAppender;
/*     */ import org.spongepowered.asm.launch.GlobalProperties;
/*     */ import org.spongepowered.asm.launch.platform.container.ContainerHandleURI;
/*     */ import org.spongepowered.asm.launch.platform.container.IContainerHandle;
/*     */ import org.spongepowered.asm.mixin.MixinEnvironment;
/*     */ import org.spongepowered.asm.mixin.extensibility.IRemapper;
/*     */ import org.spongepowered.asm.service.mojang.MixinServiceLaunchWrapper;
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
/*     */ public class MixinPlatformAgentFMLLegacy
/*     */   extends MixinPlatformAgentAbstract
/*     */   implements IMixinPlatformServiceAgent
/*     */ {
/*     */   private static final String OLD_LAUNCH_HANDLER_CLASS = "cpw.mods.fml.relauncher.FMLLaunchHandler";
/*     */   private static final String NEW_LAUNCH_HANDLER_CLASS = "net.minecraftforge.fml.relauncher.FMLLaunchHandler";
/*     */   private static final String CLIENT_TWEAKER_TAIL = ".common.launcher.FMLTweaker";
/*     */   private static final String SERVER_TWEAKER_TAIL = ".common.launcher.FMLServerTweaker";
/*     */   private static final String GETSIDE_METHOD = "side";
/*     */   private static final String LOAD_CORE_MOD_METHOD = "loadCoreMod";
/*     */   private static final String GET_REPARSEABLE_COREMODS_METHOD = "getReparseableCoremods";
/*     */   private static final String CORE_MOD_MANAGER_CLASS = "net.minecraftforge.fml.relauncher.CoreModManager";
/*     */   private static final String CORE_MOD_MANAGER_CLASS_LEGACY = "cpw.mods.fml.relauncher.CoreModManager";
/*     */   private static final String GET_IGNORED_MODS_METHOD = "getIgnoredMods";
/*     */   private static final String GET_IGNORED_MODS_METHOD_LEGACY = "getLoadedCoremods";
/*     */   private static final String FML_REMAPPER_ADAPTER_CLASS = "org.spongepowered.asm.bridge.RemapperAdapterFML";
/*     */   private static final String FML_CMDLINE_COREMODS = "fml.coreMods.load";
/*     */   private static final String FML_PLUGIN_WRAPPER_CLASS = "FMLPluginWrapper";
/*     */   private static final String FML_CORE_MOD_INSTANCE_FIELD = "coreModInstance";
/*     */   private static final String MFATT_FORCELOADASMOD = "ForceLoadAsMod";
/*     */   private static final String MFATT_FMLCOREPLUGIN = "FMLCorePlugin";
/*     */   private static final String MFATT_COREMODCONTAINSMOD = "FMLCorePluginContainsFMLMod";
/*     */   private static final String FML_TWEAKER_DEOBF = "FMLDeobfTweaker";
/*     */   private static final String FML_TWEAKER_INJECTION = "FMLInjectionAndSortingTweaker";
/*     */   private static final String FML_TWEAKER_TERMINAL = "TerminalTweaker";
/*  96 */   private static final Set<String> loadedCoreMods = new HashSet<>();
/*     */   
/*     */   private File file;
/*     */   private String fileName;
/*     */   private ITweaker coreModWrapper;
/*     */   
/*     */   static {
/* 103 */     for (String cmdLineCoreMod : System.getProperty("fml.coreMods.load", "").split(",")) {
/* 104 */       if (!cmdLineCoreMod.isEmpty()) {
/* 105 */         MixinPlatformAgentAbstract.logger.debug("FML platform agent will ignore coremod {} specified on the command line", new Object[] { cmdLineCoreMod });
/* 106 */         loadedCoreMods.add(cmdLineCoreMod);
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
/*     */   private Class<?> clCoreModManager;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean initInjectionState;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static MixinAppender appender;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static Logger log;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public IMixinPlatformAgent.AcceptResult accept(MixinPlatformManager manager, IContainerHandle handle) {
/*     */     try {
/* 144 */       this.clCoreModManager = getCoreModManagerClass();
/* 145 */     } catch (ClassNotFoundException ex) {
/* 146 */       MixinPlatformAgentAbstract.logger.info("FML platform manager could not load class {}. Proceeding without FML support.", new Object[] { ex
/* 147 */             .getMessage() });
/* 148 */       return IMixinPlatformAgent.AcceptResult.INVALID;
/*     */     } 
/*     */     
/* 151 */     if (!(handle instanceof ContainerHandleURI) || super.accept(manager, handle) != IMixinPlatformAgent.AcceptResult.ACCEPTED) {
/* 152 */       return IMixinPlatformAgent.AcceptResult.REJECTED;
/*     */     }
/*     */     
/* 155 */     this.file = ((ContainerHandleURI)handle).getFile();
/* 156 */     this.fileName = this.file.getName();
/* 157 */     this.coreModWrapper = initFMLCoreMod();
/* 158 */     return (this.coreModWrapper != null) ? IMixinPlatformAgent.AcceptResult.ACCEPTED : IMixinPlatformAgent.AcceptResult.REJECTED;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private ITweaker initFMLCoreMod() {
/*     */     try {
/* 166 */       if ("true".equalsIgnoreCase(this.handle.getAttribute("ForceLoadAsMod"))) {
/* 167 */         MixinPlatformAgentAbstract.logger.debug("ForceLoadAsMod was specified for {}, attempting force-load", new Object[] { this.fileName });
/* 168 */         loadAsMod();
/*     */       } 
/*     */       
/* 171 */       return injectCorePlugin();
/* 172 */     } catch (Exception ex) {
/* 173 */       MixinPlatformAgentAbstract.logger.catching(ex);
/* 174 */       return null;
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
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void loadAsMod() {
/*     */     try {
/* 191 */       getIgnoredMods(this.clCoreModManager).remove(this.fileName);
/* 192 */     } catch (Exception ex) {
/* 193 */       MixinPlatformAgentAbstract.logger.catching(ex);
/*     */     } 
/*     */     
/* 196 */     if (this.handle.getAttribute("FMLCorePluginContainsFMLMod") != null) {
/* 197 */       if (isIgnoredReparseable()) {
/* 198 */         MixinPlatformAgentAbstract.logger.debug("Ignoring request to add {} to reparseable coremod collection - it is a deobfuscated dependency", new Object[] { this.fileName });
/*     */         
/*     */         return;
/*     */       } 
/* 202 */       addReparseableJar();
/*     */     } 
/*     */   }
/*     */   
/*     */   private boolean isIgnoredReparseable() {
/* 207 */     return this.handle.toString().contains("deobfedDeps");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void addReparseableJar() {
/*     */     try {
/* 216 */       Method mdGetReparsedCoremods = this.clCoreModManager.getDeclaredMethod(GlobalProperties.getString(GlobalProperties.Keys.FML_GET_REPARSEABLE_COREMODS, "getReparseableCoremods"), new Class[0]);
/*     */ 
/*     */       
/* 219 */       List<String> reparsedCoremods = (List<String>)mdGetReparsedCoremods.invoke((Object)null, new Object[0]);
/* 220 */       if (!reparsedCoremods.contains(this.fileName)) {
/* 221 */         MixinPlatformAgentAbstract.logger.debug("Adding {} to reparseable coremod collection", new Object[] { this.fileName });
/* 222 */         reparsedCoremods.add(this.fileName);
/*     */       } 
/* 224 */     } catch (Exception ex) {
/* 225 */       MixinPlatformAgentAbstract.logger.catching(ex);
/*     */     } 
/*     */   }
/*     */   
/*     */   private ITweaker injectCorePlugin() throws NoSuchMethodException, IllegalAccessException, InvocationTargetException {
/* 230 */     String coreModName = this.handle.getAttribute("FMLCorePlugin");
/* 231 */     if (coreModName == null) {
/* 232 */       return null;
/*     */     }
/*     */     
/* 235 */     if (isAlreadyInjected(coreModName)) {
/* 236 */       MixinPlatformAgentAbstract.logger.debug("{} has core plugin {}. Skipping because it was already injected.", new Object[] { this.fileName, coreModName });
/* 237 */       return null;
/*     */     } 
/*     */     
/* 240 */     MixinPlatformAgentAbstract.logger.debug("{} has core plugin {}. Injecting it into FML for co-initialisation:", new Object[] { this.fileName, coreModName });
/* 241 */     Method mdLoadCoreMod = this.clCoreModManager.getDeclaredMethod(GlobalProperties.getString(GlobalProperties.Keys.FML_LOAD_CORE_MOD, "loadCoreMod"), new Class[] { LaunchClassLoader.class, String.class, File.class });
/*     */     
/* 243 */     mdLoadCoreMod.setAccessible(true);
/* 244 */     ITweaker wrapper = (ITweaker)mdLoadCoreMod.invoke((Object)null, new Object[] { Launch.classLoader, coreModName, this.file });
/* 245 */     if (wrapper == null) {
/* 246 */       MixinPlatformAgentAbstract.logger.debug("Core plugin {} could not be loaded.", new Object[] { coreModName });
/* 247 */       return null;
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 252 */     this.initInjectionState = isTweakerQueued("FMLInjectionAndSortingTweaker");
/*     */     
/* 254 */     loadedCoreMods.add(coreModName);
/* 255 */     return wrapper;
/*     */   }
/*     */ 
/*     */   
/*     */   private boolean isAlreadyInjected(String coreModName) {
/* 260 */     if (loadedCoreMods.contains(coreModName)) {
/* 261 */       return true;
/*     */     }
/*     */ 
/*     */     
/*     */     try {
/* 266 */       List<ITweaker> tweakers = (List<ITweaker>)GlobalProperties.get(MixinServiceLaunchWrapper.BLACKBOARD_KEY_TWEAKS);
/* 267 */       if (tweakers == null) {
/* 268 */         return false;
/*     */       }
/*     */       
/* 271 */       for (ITweaker tweaker : tweakers) {
/* 272 */         Class<? extends ITweaker> tweakClass = (Class)tweaker.getClass();
/* 273 */         if ("FMLPluginWrapper".equals(tweakClass.getSimpleName())) {
/* 274 */           Field fdCoreModInstance = tweakClass.getField("coreModInstance");
/* 275 */           fdCoreModInstance.setAccessible(true);
/* 276 */           Object coreMod = fdCoreModInstance.get(tweaker);
/* 277 */           if (coreModName.equals(coreMod.getClass().getName())) {
/* 278 */             return true;
/*     */           }
/*     */         } 
/*     */       } 
/* 282 */     } catch (Exception exception) {}
/*     */ 
/*     */ 
/*     */     
/* 286 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public String getPhaseProvider() {
/* 291 */     return MixinPlatformAgentFMLLegacy.class.getName() + "$PhaseProvider";
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void prepare() {
/* 299 */     this.initInjectionState |= isTweakerQueued("FMLInjectionAndSortingTweaker");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void initPrimaryContainer() {
/* 308 */     if (this.clCoreModManager != null)
/*     */     {
/* 310 */       injectRemapper();
/*     */     }
/*     */   }
/*     */   
/*     */   private void injectRemapper() {
/*     */     try {
/* 316 */       MixinPlatformAgentAbstract.logger.debug("Creating FML remapper adapter: {}", new Object[] { "org.spongepowered.asm.bridge.RemapperAdapterFML" });
/* 317 */       Class<?> clFmlRemapperAdapter = Class.forName("org.spongepowered.asm.bridge.RemapperAdapterFML", true, (ClassLoader)Launch.classLoader);
/* 318 */       Method mdCreate = clFmlRemapperAdapter.getDeclaredMethod("create", new Class[0]);
/* 319 */       IRemapper remapper = (IRemapper)mdCreate.invoke((Object)null, new Object[0]);
/* 320 */       MixinEnvironment.getDefaultEnvironment().getRemappers().add(remapper);
/* 321 */     } catch (Exception ex) {
/* 322 */       MixinPlatformAgentAbstract.logger.debug("Failed instancing FML remapper adapter, things will probably go horribly for notch-obf'd mods!");
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void inject() {
/* 331 */     if (this.coreModWrapper != null && checkForCoInitialisation()) {
/* 332 */       MixinPlatformAgentAbstract.logger.debug("FML agent is co-initiralising coremod instance {} for {}", new Object[] { this.coreModWrapper, this.handle });
/* 333 */       this.coreModWrapper.injectIntoClassLoader(Launch.classLoader);
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected final boolean checkForCoInitialisation() {
/* 350 */     boolean injectionTweaker = isTweakerQueued("FMLInjectionAndSortingTweaker");
/* 351 */     boolean terminalTweaker = isTweakerQueued("TerminalTweaker");
/* 352 */     if ((this.initInjectionState && terminalTweaker) || injectionTweaker) {
/* 353 */       MixinPlatformAgentAbstract.logger.debug("FML agent is skipping co-init for {} because FML will inject it normally", new Object[] { this.coreModWrapper });
/* 354 */       return false;
/*     */     } 
/*     */     
/* 357 */     return !isTweakerQueued("FMLDeobfTweaker");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static boolean isTweakerQueued(String tweakerName) {
/* 368 */     for (String tweaker : GlobalProperties.get(MixinServiceLaunchWrapper.BLACKBOARD_KEY_TWEAKCLASSES)) {
/* 369 */       if (tweaker.endsWith(tweakerName)) {
/* 370 */         return true;
/*     */       }
/*     */     } 
/* 373 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static Class<?> getCoreModManagerClass() throws ClassNotFoundException {
/*     */     try {
/* 382 */       return Class.forName(GlobalProperties.getString(GlobalProperties.Keys.FML_CORE_MOD_MANAGER, "net.minecraftforge.fml.relauncher.CoreModManager"));
/*     */     }
/* 384 */     catch (ClassNotFoundException ex) {
/* 385 */       return Class.forName("cpw.mods.fml.relauncher.CoreModManager");
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private static List<String> getIgnoredMods(Class<?> clCoreModManager) throws IllegalAccessException, InvocationTargetException {
/* 391 */     Method mdGetIgnoredMods = null;
/*     */     
/*     */     try {
/* 394 */       mdGetIgnoredMods = clCoreModManager.getDeclaredMethod(GlobalProperties.getString(GlobalProperties.Keys.FML_GET_IGNORED_MODS, "getIgnoredMods"), new Class[0]);
/*     */     }
/* 396 */     catch (NoSuchMethodException ex1) {
/*     */       
/*     */       try {
/* 399 */         mdGetIgnoredMods = clCoreModManager.getDeclaredMethod("getLoadedCoremods", new Class[0]);
/* 400 */       } catch (NoSuchMethodException ex2) {
/* 401 */         MixinPlatformAgentAbstract.logger.catching(Level.DEBUG, ex2);
/* 402 */         return Collections.emptyList();
/*     */       } 
/*     */     } 
/*     */     
/* 406 */     return (List<String>)mdGetIgnoredMods.invoke((Object)null, new Object[0]);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void init() {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getSideName() {
/* 426 */     List<ITweaker> tweakerList = (List<ITweaker>)GlobalProperties.get(MixinServiceLaunchWrapper.BLACKBOARD_KEY_TWEAKS);
/* 427 */     if (tweakerList == null) {
/* 428 */       return null;
/*     */     }
/* 430 */     for (ITweaker tweaker : tweakerList) {
/* 431 */       if (tweaker.getClass().getName().endsWith(".common.launcher.FMLServerTweaker"))
/* 432 */         return "SERVER"; 
/* 433 */       if (tweaker.getClass().getName().endsWith(".common.launcher.FMLTweaker")) {
/* 434 */         return "CLIENT";
/*     */       }
/*     */     } 
/*     */     
/* 438 */     String name = MixinPlatformAgentAbstract.invokeStringMethod((ClassLoader)Launch.classLoader, "net.minecraftforge.fml.relauncher.FMLLaunchHandler", "side");
/*     */     
/* 440 */     if (name != null) {
/* 441 */       return name;
/*     */     }
/*     */     
/* 444 */     return MixinPlatformAgentAbstract.invokeStringMethod((ClassLoader)Launch.classLoader, "cpw.mods.fml.relauncher.FMLLaunchHandler", "side");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Collection<IContainerHandle> getMixinContainers() {
/* 454 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   public void wire(MixinEnvironment.Phase phase, IConsumer<MixinEnvironment.Phase> phaseConsumer) {
/* 462 */     super.wire(phase, phaseConsumer);
/* 463 */     if (phase == MixinEnvironment.Phase.PREINIT) {
/* 464 */       begin(phaseConsumer);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   public void unwire() {
/* 476 */     end();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/* 481 */   static Level oldLevel = null;
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
/*     */   static void begin(IConsumer<MixinEnvironment.Phase> delegate) {
/* 497 */     Logger fmlLog = LogManager.getLogger("FML");
/* 498 */     if (!(fmlLog instanceof Logger)) {
/*     */       return;
/*     */     }
/*     */     
/* 502 */     log = (Logger)fmlLog;
/* 503 */     oldLevel = log.getLevel();
/*     */     
/* 505 */     appender = new MixinAppender(delegate);
/* 506 */     appender.start();
/* 507 */     log.addAppender((Appender)appender);
/*     */     
/* 509 */     log.setLevel(Level.ALL);
/*     */   }
/*     */   
/*     */   static void end() {
/* 513 */     if (log != null)
/*     */     {
/* 515 */       log.removeAppender((Appender)appender);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   static class MixinAppender
/*     */     extends AbstractAppender
/*     */   {
/*     */     private final IConsumer<MixinEnvironment.Phase> delegate;
/*     */ 
/*     */     
/*     */     MixinAppender(IConsumer<MixinEnvironment.Phase> delegate) {
/* 527 */       super("MixinLogWatcherAppender", null, null);
/* 528 */       this.delegate = delegate;
/*     */     }
/*     */ 
/*     */     
/*     */     public void append(LogEvent event) {
/* 533 */       if (event.getLevel() != Level.DEBUG || !"Validating minecraft".equals(event.getMessage().getFormattedMessage())) {
/*     */         return;
/*     */       }
/*     */ 
/*     */       
/* 538 */       this.delegate.accept(MixinEnvironment.Phase.INIT);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 545 */       if (MixinPlatformAgentFMLLegacy.log.getLevel() == Level.ALL)
/* 546 */         MixinPlatformAgentFMLLegacy.log.setLevel(MixinPlatformAgentFMLLegacy.oldLevel); 
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\DuduM\OneDrive\Área de Trabalho\mw10\modularwarfare-shining-2023.2.4.4f-fix10.jar!\org\spongepowered\asm\launch\platform\MixinPlatformAgentFMLLegacy.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */