/*      */ package org.spongepowered.asm.mixin;
/*      */ 
/*      */ import com.google.common.collect.ImmutableList;
/*      */ import java.util.ArrayList;
/*      */ import java.util.Collections;
/*      */ import java.util.HashMap;
/*      */ import java.util.HashSet;
/*      */ import java.util.List;
/*      */ import java.util.Locale;
/*      */ import java.util.Map;
/*      */ import java.util.Set;
/*      */ import org.apache.logging.log4j.Level;
/*      */ import org.apache.logging.log4j.LogManager;
/*      */ import org.apache.logging.log4j.Logger;
/*      */ import org.spongepowered.asm.launch.GlobalProperties;
/*      */ import org.spongepowered.asm.mixin.extensibility.IEnvironmentTokenProvider;
/*      */ import org.spongepowered.asm.mixin.throwables.MixinException;
/*      */ import org.spongepowered.asm.mixin.transformer.IMixinTransformer;
/*      */ import org.spongepowered.asm.obfuscation.RemapperChain;
/*      */ import org.spongepowered.asm.service.IMixinService;
/*      */ import org.spongepowered.asm.service.ITransformer;
/*      */ import org.spongepowered.asm.service.ITransformerProvider;
/*      */ import org.spongepowered.asm.service.MixinService;
/*      */ import org.spongepowered.asm.service.MixinServiceAbstract;
/*      */ import org.spongepowered.asm.util.IConsumer;
/*      */ import org.spongepowered.asm.util.ITokenProvider;
/*      */ import org.spongepowered.asm.util.JavaVersion;
/*      */ import org.spongepowered.asm.util.PrettyPrinter;
/*      */ import org.spongepowered.asm.util.asm.ASM;
/*      */ import org.spongepowered.asm.util.perf.Profiler;
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
/*      */ public final class MixinEnvironment
/*      */   implements ITokenProvider
/*      */ {
/*      */   private static MixinEnvironment currentEnvironment;
/*      */   
/*      */   public static final class Phase
/*      */   {
/*   78 */     static final Phase NOT_INITIALISED = new Phase(-1, "NOT_INITIALISED");
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*   84 */     public static final Phase PREINIT = new Phase(0, "PREINIT");
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*   89 */     public static final Phase INIT = new Phase(1, "INIT");
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*   94 */     public static final Phase DEFAULT = new Phase(2, "DEFAULT");
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*   99 */     static final List<Phase> phases = (List<Phase>)ImmutableList.of(PREINIT, INIT, DEFAULT);
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     final int ordinal;
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     final String name;
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     private MixinEnvironment environment;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     private Phase(int ordinal, String name) {
/*  121 */       this.ordinal = ordinal;
/*  122 */       this.name = name;
/*      */     }
/*      */ 
/*      */     
/*      */     public String toString() {
/*  127 */       return this.name;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public static Phase forName(String name) {
/*  138 */       for (Phase phase : phases) {
/*  139 */         if (phase.name.equals(name)) {
/*  140 */           return phase;
/*      */         }
/*      */       } 
/*  143 */       return null;
/*      */     }
/*      */     
/*      */     MixinEnvironment getEnvironment() {
/*  147 */       if (this.ordinal < 0) {
/*  148 */         throw new IllegalArgumentException("Cannot access the NOT_INITIALISED environment");
/*      */       }
/*      */       
/*  151 */       if (this.environment == null) {
/*  152 */         this.environment = new MixinEnvironment(this);
/*      */       }
/*      */       
/*  155 */       return this.environment;
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public enum Side
/*      */   {
/*  167 */     UNKNOWN
/*      */     {
/*      */       protected boolean detect() {
/*  170 */         return false;
/*      */       }
/*      */     },
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  177 */     CLIENT
/*      */     {
/*      */       protected boolean detect() {
/*  180 */         String sideName = MixinService.getService().getSideName();
/*  181 */         return "CLIENT".equals(sideName);
/*      */       }
/*      */     },
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  188 */     SERVER
/*      */     {
/*      */       protected boolean detect() {
/*  191 */         String sideName = MixinService.getService().getSideName();
/*  192 */         return ("SERVER".equals(sideName) || "DEDICATEDSERVER".equals(sideName));
/*      */       }
/*      */     };
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     protected abstract boolean detect();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public enum Option
/*      */   {
/*  207 */     DEBUG_ALL("debug"),
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  214 */     DEBUG_EXPORT((String)DEBUG_ALL, "export"),
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
/*  229 */     DEBUG_EXPORT_FILTER((String)DEBUG_EXPORT, "filter", false),
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  234 */     DEBUG_EXPORT_DECOMPILE((String)DEBUG_EXPORT, Inherit.ALLOW_OVERRIDE, (Option)"decompile"),
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  242 */     DEBUG_EXPORT_DECOMPILE_THREADED((String)DEBUG_EXPORT_DECOMPILE, Inherit.ALLOW_OVERRIDE, (Option)"async"),
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  251 */     DEBUG_EXPORT_DECOMPILE_MERGESIGNATURES((String)DEBUG_EXPORT_DECOMPILE, Inherit.ALLOW_OVERRIDE, (Option)"mergeGenericSignatures"),
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  258 */     DEBUG_VERIFY((String)DEBUG_ALL, "verify"),
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  264 */     DEBUG_VERBOSE((String)DEBUG_ALL, "verbose"),
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  270 */     DEBUG_INJECTORS((String)DEBUG_ALL, "countInjections"),
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  275 */     DEBUG_STRICT((String)DEBUG_ALL, Inherit.INDEPENDENT, (Option)"strict"),
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  282 */     DEBUG_UNIQUE((String)DEBUG_STRICT, "unique"),
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  287 */     DEBUG_TARGETS((String)DEBUG_STRICT, "targets"),
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  293 */     DEBUG_PROFILER((String)DEBUG_ALL, Inherit.ALLOW_OVERRIDE, (Option)"profiler"),
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  299 */     DUMP_TARGET_ON_FAILURE("dumpTargetOnFailure"),
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  304 */     CHECK_ALL("checks"),
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  310 */     CHECK_IMPLEMENTS((String)CHECK_ALL, "interfaces"),
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  318 */     CHECK_IMPLEMENTS_STRICT((String)CHECK_IMPLEMENTS, Inherit.ALLOW_OVERRIDE, (Option)"strict"),
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  323 */     IGNORE_CONSTRAINTS("ignoreConstraints"),
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  328 */     HOT_SWAP("hotSwap"),
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  333 */     ENVIRONMENT((String)Inherit.ALWAYS_FALSE, "env"),
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  338 */     OBFUSCATION_TYPE((String)ENVIRONMENT, Inherit.ALWAYS_FALSE, (Option)"obf"),
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  343 */     DISABLE_REFMAP((String)ENVIRONMENT, Inherit.INDEPENDENT, (Option)"disableRefMap"),
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  353 */     REFMAP_REMAP((String)ENVIRONMENT, Inherit.INDEPENDENT, (Option)"remapRefMap"),
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  363 */     REFMAP_REMAP_RESOURCE((String)ENVIRONMENT, Inherit.INDEPENDENT, (Option)"refMapRemappingFile", (Inherit)""),
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  370 */     REFMAP_REMAP_SOURCE_ENV((String)ENVIRONMENT, Inherit.INDEPENDENT, (Option)"refMapRemappingEnv", (Inherit)"searge"),
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  379 */     REFMAP_REMAP_ALLOW_PERMISSIVE((String)ENVIRONMENT, Inherit.INDEPENDENT, (Option)"allowPermissiveMatch", true, "true"),
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  384 */     IGNORE_REQUIRED((String)ENVIRONMENT, Inherit.INDEPENDENT, (Option)"ignoreRequired"),
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  389 */     DEFAULT_COMPATIBILITY_LEVEL((String)ENVIRONMENT, Inherit.INDEPENDENT, (Option)"compatLevel"),
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
/*  409 */     SHIFT_BY_VIOLATION_BEHAVIOUR((String)ENVIRONMENT, Inherit.INDEPENDENT, (Option)"shiftByViolation", (Inherit)"warn"),
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  415 */     INITIALISER_INJECTION_MODE("initialiserInjectionMode", "default");
/*      */     private static final String PREFIX = "mixin";
/*      */     final Option parent;
/*      */     final Inherit inheritance;
/*      */     final String property;
/*      */     final String defaultValue;
/*      */     final boolean isFlag;
/*      */     final int depth;
/*      */     
/*      */     private enum Inherit {
/*  425 */       INHERIT,
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  432 */       ALLOW_OVERRIDE,
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  438 */       INDEPENDENT,
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  443 */       ALWAYS_FALSE;
/*      */     }
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
/*      */     Option(Option parent, Inherit inheritance, String property, boolean isFlag, String defaultStringValue) {
/*  524 */       this.parent = parent;
/*  525 */       this.inheritance = inheritance;
/*  526 */       this.property = ((parent != null) ? parent.property : "mixin") + "." + property;
/*  527 */       this.defaultValue = defaultStringValue;
/*  528 */       this.isFlag = isFlag;
/*  529 */       int depth = 0;
/*  530 */       for (; parent != null; depth++) {
/*  531 */         parent = parent.parent;
/*      */       }
/*  533 */       this.depth = depth;
/*      */     }
/*      */     
/*      */     Option getParent() {
/*  537 */       return this.parent;
/*      */     }
/*      */     
/*      */     String getProperty() {
/*  541 */       return this.property;
/*      */     }
/*      */ 
/*      */     
/*      */     public String toString() {
/*  546 */       return this.isFlag ? String.valueOf(getBooleanValue()) : getStringValue();
/*      */     }
/*      */     
/*      */     private boolean getLocalBooleanValue(boolean defaultValue) {
/*  550 */       return Boolean.parseBoolean(System.getProperty(this.property, Boolean.toString(defaultValue)));
/*      */     }
/*      */     
/*      */     private boolean getInheritedBooleanValue() {
/*  554 */       return (this.parent != null && this.parent.getBooleanValue());
/*      */     }
/*      */     
/*      */     final boolean getBooleanValue() {
/*  558 */       if (this.inheritance == Inherit.ALWAYS_FALSE) {
/*  559 */         return false;
/*      */       }
/*      */       
/*  562 */       boolean local = getLocalBooleanValue(false);
/*  563 */       if (this.inheritance == Inherit.INDEPENDENT) {
/*  564 */         return local;
/*      */       }
/*      */       
/*  567 */       boolean inherited = (local || getInheritedBooleanValue());
/*  568 */       return (this.inheritance == Inherit.INHERIT) ? inherited : getLocalBooleanValue(inherited);
/*      */     }
/*      */     
/*      */     final String getStringValue() {
/*  572 */       return (this.inheritance == Inherit.INDEPENDENT || this.parent == null || this.parent.getBooleanValue()) ? 
/*  573 */         System.getProperty(this.property, this.defaultValue) : this.defaultValue;
/*      */     }
/*      */ 
/*      */     
/*      */     <E extends Enum<E>> E getEnumValue(E defaultValue) {
/*  578 */       String value = System.getProperty(this.property, defaultValue.name());
/*      */       try {
/*  580 */         return Enum.valueOf((Class)defaultValue.getClass(), value.toUpperCase(Locale.ROOT));
/*  581 */       } catch (IllegalArgumentException ex) {
/*  582 */         return defaultValue;
/*      */       } 
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public enum CompatibilityLevel
/*      */   {
/*  595 */     JAVA_6(6, 50, 0),
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  600 */     JAVA_7(7, 51, 0)
/*      */     {
/*      */       boolean isSupported()
/*      */       {
/*  604 */         return (JavaVersion.current() >= 1.7D);
/*      */       }
/*      */     },
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  612 */     JAVA_8(8, 52, 1)
/*      */     {
/*      */       boolean isSupported()
/*      */       {
/*  616 */         return (JavaVersion.current() >= 1.8D);
/*      */       }
/*      */     },
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  624 */     JAVA_9(9, 53, 3)
/*      */     {
/*      */       boolean isSupported()
/*      */       {
/*  628 */         return (JavaVersion.current() >= 9.0D);
/*      */       }
/*      */     },
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  636 */     JAVA_10(10, 54, 3)
/*      */     {
/*      */       boolean isSupported()
/*      */       {
/*  640 */         return (JavaVersion.current() >= 10.0D);
/*      */       }
/*      */     },
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  648 */     JAVA_11(11, 55, 15)
/*      */     {
/*      */       
/*      */       boolean isSupported()
/*      */       {
/*  653 */         return (JavaVersion.current() >= 11.0D);
/*      */       }
/*      */     };
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
/*      */ 
/*      */ 
/*      */     
/*  688 */     public static CompatibilityLevel DEFAULT = JAVA_6;
/*      */ 
/*      */     
/*      */     private final int ver;
/*      */     
/*      */     private final int classVersion;
/*      */     
/*      */     private final int languageFeatures;
/*      */ 
/*      */     
/*      */     CompatibilityLevel(int ver, int classVersion, int languageFeatures) {
/*  699 */       this.ver = ver;
/*  700 */       this.classVersion = classVersion;
/*  701 */       this.languageFeatures = languageFeatures;
/*      */     } private CompatibilityLevel maxCompatibleLevel;
/*      */     public static class LanguageFeature {
/*      */       public static final int METHODS_IN_INTERFACES = 1; public static final int PRIVATE_METHODS_IN_INTERFACES = 2; public static final int NESTING = 4; public static final int DYNAMIC_CONSTANTS = 8; }
/*      */     static {
/*      */     
/*      */     }
/*      */     boolean isSupported() {
/*  709 */       return true;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public int classVersion() {
/*  716 */       return this.classVersion;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     @Deprecated
/*      */     public boolean supportsMethodsInInterfaces() {
/*  727 */       return ((this.languageFeatures & 0x1) != 0);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public boolean supports(int languageFeature) {
/*  738 */       return ((this.languageFeatures & languageFeature) != 0);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public boolean isAtLeast(CompatibilityLevel level) {
/*  749 */       return (level == null || this.ver >= level.ver);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public boolean isLessThan(CompatibilityLevel level) {
/*  759 */       return (level == null || this.ver < level.ver);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public boolean canElevateTo(CompatibilityLevel level) {
/*  769 */       if (level == null || this.maxCompatibleLevel == null) {
/*  770 */         return true;
/*      */       }
/*  772 */       return (level.ver <= this.maxCompatibleLevel.ver);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public boolean canSupport(CompatibilityLevel level) {
/*  782 */       if (level == null) {
/*  783 */         return true;
/*      */       }
/*      */       
/*  786 */       return level.canElevateTo(this);
/*      */     }
/*      */     
/*      */     static String getSupportedVersions() {
/*  790 */       StringBuilder sb = new StringBuilder();
/*  791 */       boolean comma = false;
/*  792 */       for (CompatibilityLevel level : values()) {
/*  793 */         if (level.isSupported()) {
/*  794 */           if (comma) {
/*  795 */             sb.append(", ");
/*      */           }
/*  797 */           sb.append(level.ver);
/*  798 */           comma = true;
/*      */         } 
/*      */       } 
/*  801 */       return sb.toString();
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   static class TokenProviderWrapper
/*      */     implements Comparable<TokenProviderWrapper>
/*      */   {
/*  811 */     private static int nextOrder = 0;
/*      */     
/*      */     private final int priority;
/*      */     
/*      */     private final int order;
/*      */     private final IEnvironmentTokenProvider provider;
/*      */     private final MixinEnvironment environment;
/*      */     
/*      */     public TokenProviderWrapper(IEnvironmentTokenProvider provider, MixinEnvironment environment) {
/*  820 */       this.provider = provider;
/*  821 */       this.environment = environment;
/*  822 */       this.order = nextOrder++;
/*  823 */       this.priority = provider.getPriority();
/*      */     }
/*      */ 
/*      */     
/*      */     public int compareTo(TokenProviderWrapper other) {
/*  828 */       if (other == null) {
/*  829 */         return 0;
/*      */       }
/*  831 */       if (other.priority == this.priority) {
/*  832 */         return other.order - this.order;
/*      */       }
/*  834 */       return other.priority - this.priority;
/*      */     }
/*      */     
/*      */     public IEnvironmentTokenProvider getProvider() {
/*  838 */       return this.provider;
/*      */     }
/*      */     
/*      */     Integer getToken(String token) {
/*  842 */       return this.provider.getToken(token, this.environment);
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   static class PhaseConsumer
/*      */     implements IConsumer<Phase>
/*      */   {
/*      */     public void accept(MixinEnvironment.Phase phase) {
/*  854 */       MixinEnvironment.gotoPhase(phase);
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
/*  868 */   private static Phase currentPhase = Phase.NOT_INITIALISED;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static CompatibilityLevel compatibility;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static boolean showHeader = true;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  883 */   private static final Logger logger = LogManager.getLogger("mixin");
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  888 */   private static final Profiler profiler = new Profiler();
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static IMixinTransformer transformer;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private final IMixinService service;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private final Phase phase;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private final GlobalProperties.Keys configsKey;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private final boolean[] options;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  918 */   private final Set<String> tokenProviderClasses = new HashSet<>();
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  923 */   private final List<TokenProviderWrapper> tokenProviders = new ArrayList<>();
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  928 */   private final Map<String, Integer> internalTokens = new HashMap<>();
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  933 */   private final RemapperChain remappers = new RemapperChain();
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private Side side;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  943 */   private String obfuscationContext = null;
/*      */   
/*      */   MixinEnvironment(Phase phase) {
/*  946 */     this.service = MixinService.getService();
/*  947 */     this.phase = phase;
/*  948 */     this.configsKey = GlobalProperties.Keys.of(GlobalProperties.Keys.CONFIGS + "." + this.phase.name.toLowerCase(Locale.ROOT));
/*      */ 
/*      */     
/*  951 */     Object version = getVersion();
/*  952 */     if (version == null || !"0.8".equals(version)) {
/*  953 */       throw new MixinException("Environment conflict, mismatched versions or you didn't call MixinBootstrap.init()");
/*      */     }
/*      */ 
/*      */     
/*  957 */     this.service.checkEnv(this);
/*      */     
/*  959 */     this.options = new boolean[(Option.values()).length];
/*  960 */     for (Option option : Option.values()) {
/*  961 */       this.options[option.ordinal()] = option.getBooleanValue();
/*      */     }
/*      */     
/*  964 */     if (showHeader) {
/*  965 */       showHeader = false;
/*  966 */       printHeader(version);
/*      */     } 
/*      */   }
/*      */   
/*      */   private void printHeader(Object version) {
/*  971 */     String codeSource = getCodeSource();
/*  972 */     String serviceName = this.service.getName();
/*  973 */     Side side = getSide();
/*  974 */     logger.info("SpongePowered MIXIN Subsystem Version={} Source={} Service={} Env={}", new Object[] { version, codeSource, serviceName, side });
/*      */     
/*  976 */     boolean verbose = getOption(Option.DEBUG_VERBOSE);
/*  977 */     if (verbose || getOption(Option.DEBUG_EXPORT) || getOption(Option.DEBUG_PROFILER)) {
/*  978 */       PrettyPrinter printer = new PrettyPrinter(32);
/*  979 */       printer.add("SpongePowered MIXIN%s", new Object[] { verbose ? " (Verbose debugging enabled)" : "" }).centre().hr();
/*  980 */       printer.kv("Code source", codeSource);
/*  981 */       printer.kv("Internal Version", version);
/*  982 */       printer.kv("Java Versions Supported", CompatibilityLevel.getSupportedVersions());
/*  983 */       printer.kv("Current Compatibility Level", getCompatibilityLevel());
/*  984 */       printer.kv("Detected ASM API Version", ASM.getApiVersionString()).hr();
/*  985 */       printer.kv("Service Name", serviceName);
/*  986 */       printer.kv("Mixin Service Class", this.service.getClass().getName());
/*  987 */       printer.kv("Global Property Service Class", MixinService.getGlobalPropertyService().getClass().getName()).hr();
/*  988 */       for (Option option : Option.values()) {
/*  989 */         StringBuilder indent = new StringBuilder();
/*  990 */         for (int i = 0; i < option.depth; i++) {
/*  991 */           indent.append("- ");
/*      */         }
/*  993 */         printer.kv(option.property, "%s<%s>", new Object[] { indent, option });
/*      */       } 
/*  995 */       printer.hr().kv("Detected Side", side);
/*  996 */       printer.print(System.err);
/*      */     } 
/*      */   }
/*      */   
/*      */   private String getCodeSource() {
/*      */     try {
/* 1002 */       return getClass().getProtectionDomain().getCodeSource().getLocation().toString();
/* 1003 */     } catch (Throwable th) {
/* 1004 */       return "Unknown";
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private Level getVerboseLoggingLevel() {
/* 1012 */     return getOption(Option.DEBUG_VERBOSE) ? Level.INFO : Level.DEBUG;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Phase getPhase() {
/* 1021 */     return this.phase;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Deprecated
/*      */   public List<String> getMixinConfigs() {
/* 1032 */     List<String> mixinConfigs = (List<String>)GlobalProperties.get(this.configsKey);
/* 1033 */     if (mixinConfigs == null) {
/* 1034 */       mixinConfigs = new ArrayList<>();
/* 1035 */       GlobalProperties.put(this.configsKey, mixinConfigs);
/*      */     } 
/* 1037 */     return mixinConfigs;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Deprecated
/*      */   public MixinEnvironment addConfiguration(String config) {
/* 1049 */     logger.warn("MixinEnvironment::addConfiguration is deprecated and will be removed. Use Mixins::addConfiguration instead!");
/* 1050 */     Mixins.addConfiguration(config, this);
/* 1051 */     return this;
/*      */   }
/*      */   
/*      */   void registerConfig(String config) {
/* 1055 */     List<String> configs = getMixinConfigs();
/* 1056 */     if (!configs.contains(config)) {
/* 1057 */       configs.add(config);
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public MixinEnvironment registerTokenProviderClass(String providerName) {
/* 1068 */     if (!this.tokenProviderClasses.contains(providerName)) {
/*      */       
/*      */       try {
/*      */         
/* 1072 */         Class<? extends IEnvironmentTokenProvider> providerClass = this.service.getClassProvider().findClass(providerName, true);
/* 1073 */         IEnvironmentTokenProvider provider = providerClass.newInstance();
/* 1074 */         registerTokenProvider(provider);
/* 1075 */       } catch (Throwable th) {
/* 1076 */         logger.error("Error instantiating " + providerName, th);
/*      */       } 
/*      */     }
/* 1079 */     return this;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public MixinEnvironment registerTokenProvider(IEnvironmentTokenProvider provider) {
/* 1089 */     if (provider != null && !this.tokenProviderClasses.contains(provider.getClass().getName())) {
/* 1090 */       String providerName = provider.getClass().getName();
/* 1091 */       TokenProviderWrapper wrapper = new TokenProviderWrapper(provider, this);
/* 1092 */       logger.log(getVerboseLoggingLevel(), "Adding new token provider {} to {}", new Object[] { providerName, this });
/* 1093 */       this.tokenProviders.add(wrapper);
/* 1094 */       this.tokenProviderClasses.add(providerName);
/* 1095 */       Collections.sort(this.tokenProviders);
/*      */     } 
/*      */     
/* 1098 */     return this;
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
/*      */   public Integer getToken(String token) {
/* 1110 */     token = token.toUpperCase(Locale.ROOT);
/*      */     
/* 1112 */     for (TokenProviderWrapper provider : this.tokenProviders) {
/* 1113 */       Integer value = provider.getToken(token);
/* 1114 */       if (value != null) {
/* 1115 */         return value;
/*      */       }
/*      */     } 
/*      */     
/* 1119 */     return this.internalTokens.get(token);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Deprecated
/*      */   public Set<String> getErrorHandlerClasses() {
/* 1130 */     return Mixins.getErrorHandlerClasses();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Object getActiveTransformer() {
/* 1139 */     return transformer;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setActiveTransformer(IMixinTransformer transformer) {
/* 1148 */     if (transformer != null) {
/* 1149 */       MixinEnvironment.transformer = transformer;
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public MixinEnvironment setSide(Side side) {
/* 1160 */     if (side != null && getSide() == Side.UNKNOWN && side != Side.UNKNOWN) {
/* 1161 */       this.side = side;
/*      */     }
/* 1163 */     return this;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Side getSide() {
/* 1172 */     if (this.side == null) {
/* 1173 */       for (Side side : Side.values()) {
/* 1174 */         if (side.detect()) {
/* 1175 */           this.side = side;
/*      */           
/*      */           break;
/*      */         } 
/*      */       } 
/*      */     }
/* 1181 */     return (this.side != null) ? this.side : Side.UNKNOWN;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String getVersion() {
/* 1190 */     return (String)GlobalProperties.get(GlobalProperties.Keys.INIT);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean getOption(Option option) {
/* 1200 */     return this.options[option.ordinal()];
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setOption(Option option, boolean value) {
/* 1210 */     this.options[option.ordinal()] = value;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String getOptionValue(Option option) {
/* 1220 */     return option.getStringValue();
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
/*      */   public <E extends Enum<E>> E getOption(Option option, E defaultValue) {
/* 1232 */     return option.getEnumValue(defaultValue);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setObfuscationContext(String context) {
/* 1241 */     this.obfuscationContext = context;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String getObfuscationContext() {
/* 1248 */     return this.obfuscationContext;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String getRefmapObfuscationContext() {
/* 1255 */     String overrideObfuscationType = Option.OBFUSCATION_TYPE.getStringValue();
/* 1256 */     if (overrideObfuscationType != null) {
/* 1257 */       return overrideObfuscationType;
/*      */     }
/* 1259 */     return this.obfuscationContext;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public RemapperChain getRemappers() {
/* 1266 */     return this.remappers;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void audit() {
/* 1273 */     Object activeTransformer = getActiveTransformer();
/* 1274 */     if (activeTransformer instanceof IMixinTransformer) {
/* 1275 */       ((IMixinTransformer)activeTransformer).audit(this);
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
/*      */   @Deprecated
/*      */   public List<ITransformer> getTransformers() {
/* 1288 */     logger.warn("MixinEnvironment::getTransformers is deprecated!");
/* 1289 */     ITransformerProvider transformers = this.service.getTransformerProvider();
/* 1290 */     return (transformers != null) ? (List<ITransformer>)transformers.getTransformers() : Collections.<ITransformer>emptyList();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Deprecated
/*      */   public void addTransformerExclusion(String name) {
/* 1301 */     logger.warn("MixinEnvironment::addTransformerExclusion is deprecated!");
/* 1302 */     ITransformerProvider transformers = this.service.getTransformerProvider();
/* 1303 */     if (transformers != null) {
/* 1304 */       transformers.addTransformerExclusion(name);
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String toString() {
/* 1313 */     return String.format("%s[%s]", new Object[] { getClass().getSimpleName(), this.phase });
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static Phase getCurrentPhase() {
/* 1320 */     if (currentPhase == Phase.NOT_INITIALISED) {
/* 1321 */       init(Phase.PREINIT);
/*      */     }
/*      */     
/* 1324 */     return currentPhase;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void init(Phase phase) {
/* 1334 */     if (currentPhase == Phase.NOT_INITIALISED) {
/* 1335 */       currentPhase = phase;
/* 1336 */       MixinEnvironment env = getEnvironment(phase);
/* 1337 */       getProfiler().setActive(env.getOption(Option.DEBUG_PROFILER));
/*      */ 
/*      */       
/* 1340 */       IMixinService service = MixinService.getService();
/* 1341 */       if (service instanceof MixinServiceAbstract) {
/* 1342 */         ((MixinServiceAbstract)service).wire(phase, new PhaseConsumer());
/*      */       }
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static MixinEnvironment getEnvironment(Phase phase) {
/* 1354 */     if (phase == null) {
/* 1355 */       return Phase.DEFAULT.getEnvironment();
/*      */     }
/* 1357 */     return phase.getEnvironment();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static MixinEnvironment getDefaultEnvironment() {
/* 1366 */     return getEnvironment(Phase.DEFAULT);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static MixinEnvironment getCurrentEnvironment() {
/* 1375 */     if (currentEnvironment == null) {
/* 1376 */       currentEnvironment = getEnvironment(getCurrentPhase());
/*      */     }
/*      */     
/* 1379 */     return currentEnvironment;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static CompatibilityLevel getCompatibilityLevel() {
/* 1386 */     if (compatibility == null) {
/* 1387 */       CompatibilityLevel minLevel = getMinCompatibilityLevel();
/* 1388 */       CompatibilityLevel optionLevel = Option.DEFAULT_COMPATIBILITY_LEVEL.<CompatibilityLevel>getEnumValue(minLevel);
/* 1389 */       compatibility = optionLevel.isAtLeast(minLevel) ? optionLevel : minLevel;
/*      */     } 
/* 1391 */     return compatibility;
/*      */   }
/*      */   
/*      */   private static CompatibilityLevel getMinCompatibilityLevel() {
/* 1395 */     CompatibilityLevel minLevel = MixinService.getService().getMinCompatibilityLevel();
/* 1396 */     return (minLevel == null) ? CompatibilityLevel.DEFAULT : minLevel;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Deprecated
/*      */   public static void setCompatibilityLevel(CompatibilityLevel level) throws IllegalArgumentException {
/* 1408 */     StackTraceElement[] stackTrace = Thread.currentThread().getStackTrace();
/* 1409 */     if (!"org.spongepowered.asm.mixin.transformer.MixinConfig".equals(stackTrace[2].getClassName())) {
/* 1410 */       logger.warn("MixinEnvironment::setCompatibilityLevel is deprecated and will be removed. Set level via config instead!");
/*      */     }
/*      */     
/* 1413 */     CompatibilityLevel currentLevel = getCompatibilityLevel();
/* 1414 */     if (level != currentLevel && level.isAtLeast(currentLevel)) {
/* 1415 */       if (!level.isSupported()) {
/* 1416 */         throw new IllegalArgumentException("The requested compatibility level " + level + " could not be set. Level is not supported");
/*      */       }
/*      */       
/* 1419 */       IMixinService service = MixinService.getService();
/* 1420 */       CompatibilityLevel maxLevel = service.getMaxCompatibilityLevel();
/* 1421 */       if (maxLevel != null && maxLevel.isLessThan(level)) {
/* 1422 */         logger.warn("The requested compatibility level {} is higher than the level supported by the active subsystem '{}' which supports {}. This is not a supported configuration and instability may occur.", new Object[] { level, service
/* 1423 */               .getName(), maxLevel });
/*      */       }
/*      */       
/* 1426 */       compatibility = level;
/* 1427 */       logger.info("Compatibility level set to {}", new Object[] { level });
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static Profiler getProfiler() {
/* 1437 */     return profiler;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   static void gotoPhase(Phase phase) {
/* 1447 */     if (phase == null || phase.ordinal < 0) {
/* 1448 */       throw new IllegalArgumentException("Cannot go to the specified phase, phase is null or invalid");
/*      */     }
/*      */     
/* 1451 */     IMixinService service = MixinService.getService();
/* 1452 */     if (phase.ordinal > (getCurrentPhase()).ordinal) {
/* 1453 */       service.beginPhase();
/*      */     }
/*      */     
/* 1456 */     currentPhase = phase;
/* 1457 */     currentEnvironment = getEnvironment(getCurrentPhase());
/*      */ 
/*      */     
/* 1460 */     if (service instanceof MixinServiceAbstract && phase == Phase.DEFAULT)
/* 1461 */       ((MixinServiceAbstract)service).unwire(); 
/*      */   }
/*      */ }


/* Location:              C:\Users\DuduM\OneDrive\Área de Trabalho\mw10\modularwarfare-shining-2023.2.4.4f-fix10.jar!\org\spongepowered\asm\mixin\MixinEnvironment.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */