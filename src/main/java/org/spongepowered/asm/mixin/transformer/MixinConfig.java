/*      */ package org.spongepowered.asm.mixin.transformer;
/*      */ 
/*      */ import com.google.common.base.Strings;
/*      */ import com.google.common.collect.ImmutableList;
/*      */ import com.google.gson.Gson;
/*      */ import com.google.gson.annotations.SerializedName;
/*      */ import java.io.InputStream;
/*      */ import java.io.InputStreamReader;
/*      */ import java.util.ArrayList;
/*      */ import java.util.Collection;
/*      */ import java.util.Collections;
/*      */ import java.util.HashMap;
/*      */ import java.util.HashSet;
/*      */ import java.util.Iterator;
/*      */ import java.util.List;
/*      */ import java.util.Locale;
/*      */ import java.util.Map;
/*      */ import java.util.Set;
/*      */ import java.util.regex.Matcher;
/*      */ import java.util.regex.Pattern;
/*      */ import org.apache.logging.log4j.Level;
/*      */ import org.apache.logging.log4j.LogManager;
/*      */ import org.apache.logging.log4j.Logger;
/*      */ import org.objectweb.asm.tree.ClassNode;
/*      */ import org.objectweb.asm.tree.InsnList;
/*      */ import org.spongepowered.asm.launch.MixinInitialisationError;
/*      */ import org.spongepowered.asm.mixin.MixinEnvironment;
/*      */ import org.spongepowered.asm.mixin.extensibility.IMixinConfig;
/*      */ import org.spongepowered.asm.mixin.extensibility.IMixinConfigPlugin;
/*      */ import org.spongepowered.asm.mixin.injection.InjectionPoint;
/*      */ import org.spongepowered.asm.mixin.refmap.IReferenceMapper;
/*      */ import org.spongepowered.asm.mixin.refmap.ReferenceMapper;
/*      */ import org.spongepowered.asm.mixin.refmap.RemappingReferenceMapper;
/*      */ import org.spongepowered.asm.mixin.transformer.throwables.InvalidMixinException;
/*      */ import org.spongepowered.asm.service.IMixinService;
/*      */ import org.spongepowered.asm.service.MixinService;
/*      */ import org.spongepowered.asm.util.VersionNumber;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ final class MixinConfig
/*      */   implements Comparable<MixinConfig>, IMixinConfig
/*      */ {
/*      */   static class InjectorOptions
/*      */   {
/*      */     @SerializedName("defaultRequire")
/*   73 */     int defaultRequireValue = 0;
/*      */     
/*      */     @SerializedName("defaultGroup")
/*   76 */     String defaultGroup = "default";
/*      */     
/*      */     @SerializedName("injectionPoints")
/*      */     List<String> injectionPoints;
/*      */     
/*      */     @SerializedName("maxShiftBy")
/*   82 */     int maxShiftBy = 0;
/*      */ 
/*      */     
/*      */     void mergeFrom(InjectorOptions parent) {
/*   86 */       if (this.defaultRequireValue == 0) {
/*   87 */         this.defaultRequireValue = parent.defaultRequireValue;
/*      */       }
/*   89 */       if ("default".equals(this.defaultGroup)) {
/*   90 */         this.defaultGroup = parent.defaultGroup;
/*      */       }
/*   92 */       if (this.maxShiftBy == 0) {
/*   93 */         this.maxShiftBy = parent.maxShiftBy;
/*      */       }
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   static class OverwriteOptions
/*      */   {
/*      */     @SerializedName("conformVisibility")
/*      */     boolean conformAccessModifiers;
/*      */ 
/*      */     
/*      */     @SerializedName("requireAnnotations")
/*      */     boolean requireOverwriteAnnotations;
/*      */ 
/*      */     
/*      */     void mergeFrom(OverwriteOptions parent) {
/*  111 */       this.conformAccessModifiers |= parent.conformAccessModifiers;
/*  112 */       this.requireOverwriteAnnotations |= parent.requireOverwriteAnnotations;
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  142 */   private static int configOrder = 0;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  147 */   private static final Set<String> globalMixinList = new HashSet<>();
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  152 */   private final Logger logger = LogManager.getLogger("mixin");
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  157 */   private final transient Map<String, List<MixinInfo>> mixinMapping = new HashMap<>();
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  162 */   private final transient Set<String> unhandledTargets = new HashSet<>();
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  167 */   private final transient List<MixinInfo> pendingMixins = new ArrayList<>();
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  172 */   private final transient List<MixinInfo> mixins = new ArrayList<>();
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private transient Config handle;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private transient MixinConfig parent;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @SerializedName("parent")
/*      */   private String parentName;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @SerializedName("target")
/*      */   private String selector;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @SerializedName("minVersion")
/*      */   private String version;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @SerializedName("compatibilityLevel")
/*      */   private String compatibility;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @SerializedName("required")
/*      */   private Boolean requiredValue;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private transient boolean required;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @SerializedName("priority")
/*  229 */   private int priority = -1;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @SerializedName("mixinPriority")
/*  238 */   private int mixinPriority = -1;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @SerializedName("package")
/*      */   private String mixinPackage;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @SerializedName("mixins")
/*      */   private List<String> mixinClasses;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @SerializedName("client")
/*      */   private List<String> mixinClassesClient;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @SerializedName("server")
/*      */   private List<String> mixinClassesServer;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @SerializedName("setSourceFile")
/*      */   private boolean setSourceFile = false;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @SerializedName("refmap")
/*      */   private String refMapperConfig;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @SerializedName("verbose")
/*      */   private boolean verboseLogging;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  289 */   private final transient int order = configOrder++;
/*      */   
/*  291 */   private final transient List<IListener> listeners = new ArrayList<>();
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private transient IMixinService service;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private transient MixinEnvironment env;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private transient String name;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @SerializedName("plugin")
/*      */   private String pluginClassName;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @SerializedName("injectors")
/*      */   private InjectorOptions injectorOptions;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @SerializedName("overwrites")
/*      */   private OverwriteOptions overwriteOptions;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private transient PluginHandle plugin;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private transient IReferenceMapper refMapper;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private transient boolean initialised = false;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private transient boolean prepared = false;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private transient boolean visited = false;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private boolean onLoad(IMixinService service, String name, MixinEnvironment fallbackEnvironment) {
/*  369 */     this.service = service;
/*  370 */     this.name = name;
/*      */ 
/*      */     
/*  373 */     if (!Strings.isNullOrEmpty(this.parentName)) {
/*  374 */       return true;
/*      */     }
/*      */ 
/*      */     
/*  378 */     this.env = parseSelector(this.selector, fallbackEnvironment);
/*  379 */     this.required = (this.requiredValue != null && this.requiredValue.booleanValue() && !this.env.getOption(MixinEnvironment.Option.IGNORE_REQUIRED));
/*  380 */     initPriority(1000, 1000);
/*      */     
/*  382 */     if (this.injectorOptions == null) {
/*  383 */       this.injectorOptions = new InjectorOptions();
/*      */     }
/*      */     
/*  386 */     if (this.overwriteOptions == null) {
/*  387 */       this.overwriteOptions = new OverwriteOptions();
/*      */     }
/*      */     
/*  390 */     return postInit();
/*      */   }
/*      */   
/*      */   String getParentName() {
/*  394 */     return this.parentName;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   boolean assignParent(Config parentConfig) {
/*  406 */     if (this.parent != null) {
/*  407 */       throw new MixinInitialisationError("Mixin config " + this.name + " was already initialised");
/*      */     }
/*      */     
/*  410 */     if (parentConfig.get() == this) {
/*  411 */       throw new MixinInitialisationError("Mixin config " + this.name + " cannot be its own parent");
/*      */     }
/*      */     
/*  414 */     this.parent = parentConfig.get();
/*      */     
/*  416 */     if (!this.parent.initialised) {
/*  417 */       throw new MixinInitialisationError("Mixin config " + this.name + " attempted to assign uninitialised parent config. This probably means that there is an indirect loop in the mixin configs: child -> parent -> child");
/*      */     }
/*      */ 
/*      */     
/*  421 */     this.env = parseSelector(this.selector, this.parent.env);
/*  422 */     this
/*  423 */       .required = (this.requiredValue == null) ? this.parent.required : ((this.requiredValue.booleanValue() && !this.env.getOption(MixinEnvironment.Option.IGNORE_REQUIRED)));
/*      */     
/*  425 */     initPriority(this.parent.priority, this.parent.mixinPriority);
/*      */     
/*  427 */     if (this.injectorOptions == null) {
/*  428 */       this.injectorOptions = this.parent.injectorOptions;
/*      */     } else {
/*  430 */       this.injectorOptions.mergeFrom(this.parent.injectorOptions);
/*      */     } 
/*      */     
/*  433 */     if (this.overwriteOptions == null) {
/*  434 */       this.overwriteOptions = this.parent.overwriteOptions;
/*      */     } else {
/*  436 */       this.overwriteOptions.mergeFrom(this.parent.overwriteOptions);
/*      */     } 
/*      */     
/*  439 */     this.setSourceFile |= this.parent.setSourceFile;
/*  440 */     this.verboseLogging |= this.parent.verboseLogging;
/*      */     
/*  442 */     return postInit();
/*      */   }
/*      */   
/*      */   private void initPriority(int defaultPriority, int defaultMixinPriority) {
/*  446 */     if (this.priority < 0) {
/*  447 */       this.priority = defaultPriority;
/*      */     }
/*      */     
/*  450 */     if (this.mixinPriority < 0) {
/*  451 */       this.mixinPriority = defaultMixinPriority;
/*      */     }
/*      */   }
/*      */   
/*      */   private boolean postInit() throws MixinInitialisationError {
/*  456 */     if (this.initialised) {
/*  457 */       throw new MixinInitialisationError("Mixin config " + this.name + " was already initialised.");
/*      */     }
/*      */     
/*  460 */     this.initialised = true;
/*  461 */     initCompatibilityLevel();
/*  462 */     initInjectionPoints();
/*  463 */     return checkVersion();
/*      */   }
/*      */ 
/*      */   
/*      */   private void initCompatibilityLevel() {
/*  468 */     if (this.compatibility == null) {
/*      */       return;
/*      */     }
/*      */     
/*  472 */     MixinEnvironment.CompatibilityLevel level = MixinEnvironment.CompatibilityLevel.valueOf(this.compatibility.trim().toUpperCase(Locale.ROOT));
/*  473 */     MixinEnvironment.CompatibilityLevel current = MixinEnvironment.getCompatibilityLevel();
/*      */     
/*  475 */     if (level == current) {
/*      */       return;
/*      */     }
/*      */ 
/*      */     
/*  480 */     if (current.isAtLeast(level) && 
/*  481 */       !current.canSupport(level)) {
/*  482 */       throw new MixinInitialisationError("Mixin config " + this.name + " requires compatibility level " + level + " which is too old");
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*  487 */     if (!current.canElevateTo(level)) {
/*  488 */       throw new MixinInitialisationError("Mixin config " + this.name + " requires compatibility level " + level + " which is prohibited by " + current);
/*      */     }
/*      */ 
/*      */     
/*  492 */     MixinEnvironment.setCompatibilityLevel(level);
/*      */   }
/*      */ 
/*      */   
/*      */   private MixinEnvironment parseSelector(String target, MixinEnvironment fallbackEnvironment) {
/*  497 */     if (target != null) {
/*  498 */       String[] selectors = target.split("[&\\| ]");
/*  499 */       for (String sel : selectors) {
/*  500 */         sel = sel.trim();
/*  501 */         Pattern environmentSelector = Pattern.compile("^@env(?:ironment)?\\(([A-Z]+)\\)$");
/*  502 */         Matcher environmentSelectorMatcher = environmentSelector.matcher(sel);
/*  503 */         if (environmentSelectorMatcher.matches())
/*      */         {
/*  505 */           return MixinEnvironment.getEnvironment(MixinEnvironment.Phase.forName(environmentSelectorMatcher.group(1)));
/*      */         }
/*      */       } 
/*      */       
/*  509 */       MixinEnvironment.Phase phase = MixinEnvironment.Phase.forName(target);
/*  510 */       if (phase != null) {
/*  511 */         return MixinEnvironment.getEnvironment(phase);
/*      */       }
/*      */     } 
/*  514 */     return fallbackEnvironment;
/*      */   }
/*      */   
/*      */   private void initInjectionPoints() {
/*  518 */     if (this.injectorOptions.injectionPoints == null) {
/*      */       return;
/*      */     }
/*      */     
/*  522 */     for (String injectionPointClassName : this.injectorOptions.injectionPoints) {
/*  523 */       initInjectionPoint(injectionPointClassName);
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   public void initInjectionPoint(String className) {
/*      */     try {
/*  530 */       Class<?> injectionPointClass = null;
/*      */       try {
/*  532 */         injectionPointClass = this.service.getClassProvider().findClass(className, true);
/*  533 */       } catch (ClassNotFoundException cnfe) {
/*  534 */         this.logger.error("Unable to register injection point {} for {}, the specified class was not found", new Object[] { className, this, cnfe });
/*      */         
/*      */         return;
/*      */       } 
/*  538 */       if (!InjectionPoint.class.isAssignableFrom(injectionPointClass)) {
/*  539 */         this.logger.error("Unable to register injection point {} for {}, class must extend InjectionPoint", new Object[] { className, this });
/*      */         
/*      */         return;
/*      */       } 
/*      */       try {
/*  544 */         injectionPointClass.getDeclaredMethod("find", new Class[] { String.class, InsnList.class, Collection.class });
/*  545 */       } catch (NoSuchMethodException cnfe) {
/*  546 */         this.logger.error("Unable to register injection point {} for {}, the class is not compatible with this version of Mixin", new Object[] { className, this, cnfe });
/*      */         
/*      */         return;
/*      */       } 
/*      */       
/*  551 */       InjectionPoint.register(injectionPointClass);
/*  552 */     } catch (Throwable th) {
/*  553 */       this.logger.catching(th);
/*      */     } 
/*      */   }
/*      */   
/*      */   private boolean checkVersion() throws MixinInitialisationError {
/*  558 */     if (this.version == null) {
/*      */ 
/*      */       
/*  561 */       if (this.parent != null && this.parent.version != null) {
/*  562 */         return true;
/*      */       }
/*  564 */       this.logger.error("Mixin config {} does not specify \"minVersion\" property", new Object[] { this.name });
/*      */     } 
/*      */     
/*  567 */     VersionNumber minVersion = VersionNumber.parse(this.version);
/*  568 */     VersionNumber curVersion = VersionNumber.parse(this.env.getVersion());
/*  569 */     if (minVersion.compareTo(curVersion) > 0) {
/*  570 */       this.logger.warn("Mixin config {} requires mixin subsystem version {} but {} was found. The mixin config will not be applied.", new Object[] { this.name, minVersion, curVersion });
/*      */ 
/*      */       
/*  573 */       if (this.required) {
/*  574 */         throw new MixinInitialisationError("Required mixin config " + this.name + " requires mixin subsystem version " + minVersion);
/*      */       }
/*      */       
/*  577 */       return false;
/*      */     } 
/*      */     
/*  580 */     return true;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   void addListener(IListener listener) {
/*  589 */     this.listeners.add(listener);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   void onSelect() {
/*  596 */     this.plugin = new PluginHandle(this, this.service, this.pluginClassName);
/*  597 */     this.plugin.onLoad(this.mixinPackage);
/*      */     
/*  599 */     if (!this.mixinPackage.endsWith(".")) {
/*  600 */       this.mixinPackage += ".";
/*      */     }
/*      */     
/*  603 */     boolean suppressRefMapWarning = false;
/*      */     
/*  605 */     if (this.refMapperConfig == null) {
/*  606 */       this.refMapperConfig = this.plugin.getRefMapperConfig();
/*      */       
/*  608 */       if (this.refMapperConfig == null) {
/*  609 */         suppressRefMapWarning = true;
/*  610 */         this.refMapperConfig = "mixin.refmap.json";
/*      */       } 
/*      */     } 
/*      */     
/*  614 */     this.refMapper = (IReferenceMapper)ReferenceMapper.read(this.refMapperConfig);
/*  615 */     this.verboseLogging |= this.env.getOption(MixinEnvironment.Option.DEBUG_VERBOSE);
/*      */     
/*  617 */     if (!suppressRefMapWarning && this.refMapper.isDefault() && !this.env.getOption(MixinEnvironment.Option.DISABLE_REFMAP)) {
/*  618 */       this.logger.warn("Reference map '{}' for {} could not be read. If this is a development environment you can ignore this message", new Object[] { this.refMapperConfig, this });
/*      */     }
/*      */ 
/*      */     
/*  622 */     if (this.env.getOption(MixinEnvironment.Option.REFMAP_REMAP)) {
/*  623 */       this.refMapper = RemappingReferenceMapper.of(this.env, this.refMapper);
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   void prepare() {
/*  641 */     if (this.prepared) {
/*      */       return;
/*      */     }
/*  644 */     this.prepared = true;
/*      */     
/*  646 */     prepareMixins(this.mixinClasses, false);
/*      */     
/*  648 */     switch (this.env.getSide()) {
/*      */       case CLIENT:
/*  650 */         prepareMixins(this.mixinClassesClient, false);
/*      */         return;
/*      */       case SERVER:
/*  653 */         prepareMixins(this.mixinClassesServer, false);
/*      */         return;
/*      */     } 
/*      */ 
/*      */     
/*  658 */     this.logger.warn("Mixin environment was unable to detect the current side, sided mixins will not be applied");
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   void postInitialise() {
/*  664 */     if (this.plugin != null) {
/*  665 */       List<String> pluginMixins = this.plugin.getMixins();
/*  666 */       prepareMixins(pluginMixins, true);
/*      */     } 
/*      */     
/*  669 */     for (Iterator<MixinInfo> iter = this.mixins.iterator(); iter.hasNext(); ) {
/*  670 */       MixinInfo mixin = iter.next();
/*      */       try {
/*  672 */         mixin.validate();
/*  673 */         for (IListener listener : this.listeners) {
/*  674 */           listener.onInit(mixin);
/*      */         }
/*  676 */       } catch (InvalidMixinException ex) {
/*  677 */         this.logger.error(ex.getMixin() + ": " + ex.getMessage(), (Throwable)ex);
/*  678 */         removeMixin(mixin);
/*  679 */         iter.remove();
/*  680 */       } catch (Exception ex) {
/*  681 */         this.logger.error(ex.getMessage(), ex);
/*  682 */         removeMixin(mixin);
/*  683 */         iter.remove();
/*      */       } 
/*      */     } 
/*      */   } static interface IListener {
/*      */     void onPrepare(MixinInfo param1MixinInfo); void onInit(MixinInfo param1MixinInfo); }
/*      */   private void removeMixin(MixinInfo remove) {
/*  689 */     for (List<MixinInfo> mixinsFor : this.mixinMapping.values()) {
/*  690 */       for (Iterator<MixinInfo> iter = mixinsFor.iterator(); iter.hasNext();) {
/*  691 */         if (remove == iter.next()) {
/*  692 */           iter.remove();
/*      */         }
/*      */       } 
/*      */     } 
/*      */   }
/*      */   
/*      */   private void prepareMixins(List<String> mixinClasses, boolean ignorePlugin) {
/*  699 */     if (mixinClasses == null) {
/*      */       return;
/*      */     }
/*      */     
/*  703 */     for (String mixinClass : mixinClasses) {
/*  704 */       String fqMixinClass = this.mixinPackage + mixinClass;
/*      */       
/*  706 */       if (mixinClass == null || globalMixinList.contains(fqMixinClass)) {
/*      */         continue;
/*      */       }
/*      */       
/*  710 */       MixinInfo mixin = null;
/*      */       
/*      */       try {
/*  713 */         this.pendingMixins.add(mixin = new MixinInfo(this.service, this, mixinClass, this.plugin, ignorePlugin));
/*  714 */         globalMixinList.add(fqMixinClass);
/*  715 */       } catch (InvalidMixinException ex) {
/*  716 */         if (this.required) {
/*  717 */           throw ex;
/*      */         }
/*  719 */         this.logger.error(ex.getMessage(), (Throwable)ex);
/*  720 */       } catch (Exception ex) {
/*  721 */         if (this.required) {
/*  722 */           throw new InvalidMixinException(mixin, "Error initialising mixin " + mixin + " - " + ex.getClass() + ": " + ex.getMessage(), ex);
/*      */         }
/*  724 */         this.logger.error(ex.getMessage(), ex);
/*      */       } 
/*      */     } 
/*      */     
/*  728 */     for (MixinInfo mixin : this.pendingMixins) {
/*      */       try {
/*  730 */         mixin.parseTargets();
/*  731 */         if (mixin.getTargetClasses().size() > 0) {
/*  732 */           for (String targetClass : mixin.getTargetClasses()) {
/*  733 */             String targetClassName = targetClass.replace('/', '.');
/*  734 */             mixinsFor(targetClassName).add(mixin);
/*  735 */             this.unhandledTargets.add(targetClassName);
/*      */           } 
/*  737 */           for (IListener listener : this.listeners) {
/*  738 */             listener.onPrepare(mixin);
/*      */           }
/*  740 */           this.mixins.add(mixin);
/*      */         } 
/*  742 */       } catch (InvalidMixinException ex) {
/*  743 */         if (this.required) {
/*  744 */           throw ex;
/*      */         }
/*  746 */         this.logger.error(ex.getMessage(), (Throwable)ex);
/*  747 */       } catch (Exception ex) {
/*  748 */         if (this.required) {
/*  749 */           throw new InvalidMixinException(mixin, "Error initialising mixin " + mixin + " - " + ex.getClass() + ": " + ex.getMessage(), ex);
/*      */         }
/*  751 */         this.logger.error(ex.getMessage(), ex);
/*      */       } 
/*      */     } 
/*      */     
/*  755 */     this.pendingMixins.clear();
/*      */   }
/*      */   
/*      */   void postApply(String transformedName, ClassNode targetClass) {
/*  759 */     this.unhandledTargets.remove(transformedName);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Config getHandle() {
/*  766 */     if (this.handle == null) {
/*  767 */       this.handle = new Config(this);
/*      */     }
/*  769 */     return this.handle;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean isRequired() {
/*  777 */     return this.required;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public MixinEnvironment getEnvironment() {
/*  786 */     return this.env;
/*      */   }
/*      */   
/*      */   MixinConfig getParent() {
/*  790 */     return this.parent;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String getName() {
/*  798 */     return this.name;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String getMixinPackage() {
/*  806 */     return this.mixinPackage;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int getPriority() {
/*  814 */     return this.priority;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int getDefaultMixinPriority() {
/*  822 */     return this.mixinPriority;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int getDefaultRequiredInjections() {
/*  832 */     return this.injectorOptions.defaultRequireValue;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String getDefaultInjectorGroup() {
/*  841 */     String defaultGroup = this.injectorOptions.defaultGroup;
/*  842 */     return (defaultGroup != null && !defaultGroup.isEmpty()) ? defaultGroup : "default";
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean conformOverwriteVisibility() {
/*  852 */     return this.overwriteOptions.conformAccessModifiers;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean requireOverwriteAnnotations() {
/*  862 */     return this.overwriteOptions.requireOverwriteAnnotations;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int getMaxShiftByValue() {
/*  874 */     return Math.min(Math.max(this.injectorOptions.maxShiftBy, 0), 5);
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean select(MixinEnvironment environment) {
/*  879 */     this.visited = true;
/*  880 */     return (this.env == environment);
/*      */   }
/*      */ 
/*      */   
/*      */   boolean isVisited() {
/*  885 */     return this.visited;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   int getDeclaredMixinCount() {
/*  894 */     return getCollectionSize((Collection<?>[])new Collection[] { this.mixinClasses, this.mixinClassesClient, this.mixinClassesServer });
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   int getMixinCount() {
/*  903 */     return this.mixins.size();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public List<String> getClasses() {
/*  911 */     ImmutableList.Builder<String> list = ImmutableList.builder();
/*  912 */     for (List<String> classes : new List[] { this.mixinClasses, this.mixinClassesClient, this.mixinClassesServer }) {
/*  913 */       if (classes != null) {
/*  914 */         for (String className : classes) {
/*  915 */           list.add(this.mixinPackage + className);
/*      */         }
/*      */       }
/*      */     } 
/*  919 */     return (List<String>)list.build();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean shouldSetSourceFile() {
/*  927 */     return this.setSourceFile;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public IReferenceMapper getReferenceMapper() {
/*  934 */     if (this.env.getOption(MixinEnvironment.Option.DISABLE_REFMAP)) {
/*  935 */       return (IReferenceMapper)ReferenceMapper.DEFAULT_MAPPER;
/*      */     }
/*  937 */     this.refMapper.setContext(this.env.getRefmapObfuscationContext());
/*  938 */     return this.refMapper;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   String remapClassName(String className, String reference) {
/*  946 */     return getReferenceMapper().remap(className, reference);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public IMixinConfigPlugin getPlugin() {
/*  954 */     return this.plugin.get();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Set<String> getTargets() {
/*  962 */     return Collections.unmodifiableSet(this.mixinMapping.keySet());
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Set<String> getUnhandledTargets() {
/*  969 */     return Collections.unmodifiableSet(this.unhandledTargets);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Level getLoggingLevel() {
/*  976 */     return this.verboseLogging ? Level.INFO : Level.DEBUG;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean packageMatch(String className) {
/*  987 */     return className.startsWith(this.mixinPackage);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean hasMixinsFor(String targetClass) {
/*  998 */     return this.mixinMapping.containsKey(targetClass);
/*      */   }
/*      */   
/*      */   boolean hasPendingMixinsFor(String targetClass) {
/* 1002 */     if (packageMatch(targetClass)) {
/* 1003 */       return false;
/*      */     }
/* 1005 */     for (MixinInfo pendingMixin : this.pendingMixins) {
/* 1006 */       if (pendingMixin.hasDeclaredTarget(targetClass)) {
/* 1007 */         return true;
/*      */       }
/*      */     } 
/* 1010 */     return false;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public List<MixinInfo> getMixinsFor(String targetClass) {
/* 1020 */     return mixinsFor(targetClass);
/*      */   }
/*      */   
/*      */   private List<MixinInfo> mixinsFor(String targetClass) {
/* 1024 */     List<MixinInfo> mixins = this.mixinMapping.get(targetClass);
/* 1025 */     if (mixins == null) {
/* 1026 */       mixins = new ArrayList<>();
/* 1027 */       this.mixinMapping.put(targetClass, mixins);
/*      */     } 
/* 1029 */     return mixins;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public List<String> reloadMixin(String mixinClass, ClassNode classNode) {
/* 1040 */     for (Iterator<MixinInfo> iter = this.mixins.iterator(); iter.hasNext(); ) {
/* 1041 */       MixinInfo mixin = iter.next();
/* 1042 */       if (mixin.getClassName().equals(mixinClass)) {
/* 1043 */         mixin.reloadMixin(classNode);
/* 1044 */         return mixin.getTargetClasses();
/*      */       } 
/*      */     } 
/* 1047 */     return Collections.emptyList();
/*      */   }
/*      */ 
/*      */   
/*      */   public String toString() {
/* 1052 */     return this.name;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int compareTo(MixinConfig other) {
/* 1060 */     if (other == null) {
/* 1061 */       return 0;
/*      */     }
/* 1063 */     if (other.priority == this.priority) {
/* 1064 */       return this.order - other.order;
/*      */     }
/* 1066 */     return this.priority - other.priority;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   static Config create(String configFile, MixinEnvironment outer) {
/*      */     try {
/* 1079 */       IMixinService service = MixinService.getService();
/* 1080 */       InputStream resource = service.getResourceAsStream(configFile);
/* 1081 */       if (resource == null) {
/* 1082 */         throw new IllegalArgumentException(String.format("The specified resource '%s' was invalid or could not be read", new Object[] { configFile }));
/*      */       }
/* 1084 */       MixinConfig config = (MixinConfig)(new Gson()).fromJson(new InputStreamReader(resource), MixinConfig.class);
/* 1085 */       if (config.onLoad(service, configFile, outer)) {
/* 1086 */         return config.getHandle();
/*      */       }
/* 1088 */       return null;
/* 1089 */     } catch (IllegalArgumentException ex) {
/* 1090 */       throw ex;
/* 1091 */     } catch (Exception ex) {
/* 1092 */       throw new IllegalArgumentException(String.format("The specified resource '%s' was invalid or could not be read", new Object[] { configFile }), ex);
/*      */     } 
/*      */   }
/*      */   
/*      */   private static int getCollectionSize(Collection<?>... collections) {
/* 1097 */     int total = 0;
/* 1098 */     for (Collection<?> collection : collections) {
/* 1099 */       if (collection != null) {
/* 1100 */         total += collection.size();
/*      */       }
/*      */     } 
/* 1103 */     return total;
/*      */   }
/*      */ }


/* Location:              C:\Users\DuduM\OneDrive\Área de Trabalho\mw10\modularwarfare-shining-2023.2.4.4f-fix10.jar!\org\spongepowered\asm\mixin\transformer\MixinConfig.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */