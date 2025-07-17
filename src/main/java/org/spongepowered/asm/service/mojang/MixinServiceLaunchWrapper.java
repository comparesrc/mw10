/*     */ package org.spongepowered.asm.service.mojang;
/*     */ 
/*     */ import com.google.common.collect.ImmutableList;
/*     */ import com.google.common.collect.Sets;
/*     */ import com.google.common.io.ByteStreams;
/*     */ import com.google.common.io.Closeables;
/*     */ import java.io.File;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.net.URI;
/*     */ import java.net.URISyntaxException;
/*     */ import java.net.URL;
/*     */ import java.net.URLClassLoader;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collection;
/*     */ import java.util.Collections;
/*     */ import java.util.List;
/*     */ import java.util.Locale;
/*     */ import java.util.Set;
/*     */ import net.minecraft.launchwrapper.IClassNameTransformer;
/*     */ import net.minecraft.launchwrapper.IClassTransformer;
/*     */ import net.minecraft.launchwrapper.Launch;
/*     */ import org.objectweb.asm.ClassReader;
/*     */ import org.objectweb.asm.ClassVisitor;
/*     */ import org.objectweb.asm.tree.ClassNode;
/*     */ import org.spongepowered.asm.launch.GlobalProperties;
/*     */ import org.spongepowered.asm.launch.platform.MainAttributes;
/*     */ import org.spongepowered.asm.launch.platform.container.ContainerHandleURI;
/*     */ import org.spongepowered.asm.launch.platform.container.ContainerHandleVirtual;
/*     */ import org.spongepowered.asm.launch.platform.container.IContainerHandle;
/*     */ import org.spongepowered.asm.mixin.MixinEnvironment;
/*     */ import org.spongepowered.asm.mixin.throwables.MixinException;
/*     */ import org.spongepowered.asm.service.IClassBytecodeProvider;
/*     */ import org.spongepowered.asm.service.IClassProvider;
/*     */ import org.spongepowered.asm.service.IClassTracker;
/*     */ import org.spongepowered.asm.service.ILegacyClassTransformer;
/*     */ import org.spongepowered.asm.service.IMixinAuditTrail;
/*     */ import org.spongepowered.asm.service.ITransformer;
/*     */ import org.spongepowered.asm.service.ITransformerProvider;
/*     */ import org.spongepowered.asm.service.MixinServiceAbstract;
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
/*     */ public class MixinServiceLaunchWrapper
/*     */   extends MixinServiceAbstract
/*     */   implements IClassProvider, IClassBytecodeProvider, ITransformerProvider
/*     */ {
/*  81 */   public static final GlobalProperties.Keys BLACKBOARD_KEY_TWEAKCLASSES = GlobalProperties.Keys.of("TweakClasses");
/*  82 */   public static final GlobalProperties.Keys BLACKBOARD_KEY_TWEAKS = GlobalProperties.Keys.of("Tweaks");
/*     */ 
/*     */   
/*     */   private static final String MIXIN_TWEAKER_CLASS = "org.spongepowered.asm.launch.MixinTweaker";
/*     */ 
/*     */   
/*     */   private static final String STATE_TWEAKER = "org.spongepowered.asm.mixin.EnvironmentStateTweaker";
/*     */ 
/*     */   
/*     */   private static final String TRANSFORMER_PROXY_CLASS = "org.spongepowered.asm.mixin.transformer.Proxy";
/*     */ 
/*     */   
/*  94 */   private static final Set<String> excludeTransformers = Sets.newHashSet((Object[])new String[] { "net.minecraftforge.fml.common.asm.transformers.EventSubscriptionTransformer", "cpw.mods.fml.common.asm.transformers.EventSubscriptionTransformer", "net.minecraftforge.fml.common.asm.transformers.TerminalTransformer", "cpw.mods.fml.common.asm.transformers.TerminalTransformer" });
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
/* 120 */   private final LaunchClassLoaderUtil classLoaderUtil = new LaunchClassLoaderUtil(Launch.classLoader);
/*     */   private List<ILegacyClassTransformer> delegatedTransformers;
/*     */   private IClassNameTransformer nameTransformer;
/*     */   
/*     */   public String getName() {
/* 125 */     return "LaunchWrapper";
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isValid() {
/*     */     try {
/* 135 */       Launch.classLoader.hashCode();
/* 136 */     } catch (Throwable ex) {
/* 137 */       return false;
/*     */     } 
/* 139 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void prepare() {
/* 148 */     Launch.classLoader.addClassLoaderExclusion("org.spongepowered.asm.launch.");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public MixinEnvironment.Phase getInitialPhase() {
/* 156 */     String command = System.getProperty("sun.java.command");
/* 157 */     if (command != null && command.contains("GradleStart")) {
/* 158 */       System.setProperty("mixin.env.remapRefMap", "true");
/*     */     }
/*     */     
/* 161 */     if (findInStackTrace("net.minecraft.launchwrapper.Launch", "launch") > 132) {
/* 162 */       return MixinEnvironment.Phase.DEFAULT;
/*     */     }
/* 164 */     return MixinEnvironment.Phase.PREINIT;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public MixinEnvironment.CompatibilityLevel getMaxCompatibilityLevel() {
/* 173 */     return MixinEnvironment.CompatibilityLevel.JAVA_8;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void init() {
/* 181 */     if (findInStackTrace("net.minecraft.launchwrapper.Launch", "launch") < 4) {
/* 182 */       MixinServiceAbstract.logger.error("MixinBootstrap.doInit() called during a tweak constructor!");
/*     */     }
/*     */     
/* 185 */     List<String> tweakClasses = (List<String>)GlobalProperties.get(BLACKBOARD_KEY_TWEAKCLASSES);
/* 186 */     if (tweakClasses != null) {
/* 187 */       tweakClasses.add("org.spongepowered.asm.mixin.EnvironmentStateTweaker");
/*     */     }
/*     */     
/* 190 */     super.init();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Collection<String> getPlatformAgents() {
/* 198 */     return (Collection<String>)ImmutableList.of("org.spongepowered.asm.launch.platform.MixinPlatformAgentFMLLegacy", "org.spongepowered.asm.launch.platform.MixinPlatformAgentLiteLoaderLegacy");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public IContainerHandle getPrimaryContainer() {
/* 206 */     URI uri = null;
/*     */     try {
/* 208 */       uri = getClass().getProtectionDomain().getCodeSource().getLocation().toURI();
/* 209 */       if (uri != null) {
/* 210 */         return (IContainerHandle)new ContainerHandleURI(uri);
/*     */       }
/* 212 */     } catch (URISyntaxException ex) {
/* 213 */       ex.printStackTrace();
/*     */     } 
/* 215 */     return (IContainerHandle)new ContainerHandleVirtual(getName());
/*     */   }
/*     */ 
/*     */   
/*     */   public Collection<IContainerHandle> getMixinContainers() {
/* 220 */     ImmutableList.Builder<IContainerHandle> list = ImmutableList.builder();
/* 221 */     getContainersFromClassPath(list);
/* 222 */     getContainersFromAgents(list);
/* 223 */     return (Collection<IContainerHandle>)list.build();
/*     */   }
/*     */ 
/*     */   
/*     */   private void getContainersFromClassPath(ImmutableList.Builder<IContainerHandle> list) {
/* 228 */     URL[] sources = getClassPath();
/* 229 */     if (sources != null) {
/* 230 */       for (URL url : sources) {
/*     */         try {
/* 232 */           URI uri = url.toURI();
/* 233 */           MixinServiceAbstract.logger.debug("Scanning {} for mixin tweaker", new Object[] { uri });
/* 234 */           if ("file".equals(uri.getScheme()) && (new File(uri)).exists()) {
/*     */ 
/*     */             
/* 237 */             MainAttributes attributes = MainAttributes.of(uri);
/* 238 */             String tweaker = attributes.get("TweakClass");
/* 239 */             if ("org.spongepowered.asm.launch.MixinTweaker".equals(tweaker))
/* 240 */               list.add(new ContainerHandleURI(uri)); 
/*     */           } 
/* 242 */         } catch (Exception ex) {
/* 243 */           ex.printStackTrace();
/*     */         } 
/*     */       } 
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public IClassProvider getClassProvider() {
/* 254 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public IClassBytecodeProvider getBytecodeProvider() {
/* 262 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ITransformerProvider getTransformerProvider() {
/* 270 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public IClassTracker getClassTracker() {
/* 278 */     return this.classLoaderUtil;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public IMixinAuditTrail getAuditTrail() {
/* 286 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Class<?> findClass(String name) throws ClassNotFoundException {
/* 295 */     return Launch.classLoader.findClass(name);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Class<?> findClass(String name, boolean initialize) throws ClassNotFoundException {
/* 304 */     return Class.forName(name, initialize, (ClassLoader)Launch.classLoader);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Class<?> findAgentClass(String name, boolean initialize) throws ClassNotFoundException {
/* 313 */     return Class.forName(name, initialize, Launch.class.getClassLoader());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void beginPhase() {
/* 321 */     Launch.classLoader.registerTransformer("org.spongepowered.asm.mixin.transformer.Proxy");
/* 322 */     this.delegatedTransformers = null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void checkEnv(Object bootSource) {
/* 331 */     if (bootSource.getClass().getClassLoader() != Launch.class.getClassLoader()) {
/* 332 */       throw new MixinException("Attempted to init the mixin environment in the wrong classloader");
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public InputStream getResourceAsStream(String name) {
/* 342 */     return Launch.classLoader.getResourceAsStream(name);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   public URL[] getClassPath() {
/* 351 */     return (URL[])Launch.classLoader.getSources().toArray((Object[])new URL[0]);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Collection<ITransformer> getTransformers() {
/* 359 */     List<IClassTransformer> transformers = Launch.classLoader.getTransformers();
/* 360 */     List<ITransformer> wrapped = new ArrayList<>(transformers.size());
/* 361 */     for (IClassTransformer transformer : transformers) {
/* 362 */       if (transformer instanceof ITransformer) {
/* 363 */         wrapped.add((ITransformer)transformer);
/*     */       } else {
/* 365 */         wrapped.add(new LegacyTransformerHandle(transformer));
/*     */       } 
/*     */       
/* 368 */       if (transformer instanceof IClassNameTransformer) {
/* 369 */         MixinServiceAbstract.logger.debug("Found name transformer: {}", new Object[] { transformer.getClass().getName() });
/* 370 */         this.nameTransformer = (IClassNameTransformer)transformer;
/*     */       } 
/*     */     } 
/*     */     
/* 374 */     return wrapped;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public List<ITransformer> getDelegatedTransformers() {
/* 385 */     return (List)Collections.unmodifiableList(getDelegatedLegacyTransformers());
/*     */   }
/*     */   
/*     */   private List<ILegacyClassTransformer> getDelegatedLegacyTransformers() {
/* 389 */     if (this.delegatedTransformers == null) {
/* 390 */       buildTransformerDelegationList();
/*     */     }
/*     */     
/* 393 */     return this.delegatedTransformers;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void buildTransformerDelegationList() {
/* 403 */     MixinServiceAbstract.logger.debug("Rebuilding transformer delegation list:");
/* 404 */     this.delegatedTransformers = new ArrayList<>();
/* 405 */     for (ITransformer transformer : getTransformers()) {
/* 406 */       if (!(transformer instanceof ILegacyClassTransformer)) {
/*     */         continue;
/*     */       }
/*     */       
/* 410 */       ILegacyClassTransformer legacyTransformer = (ILegacyClassTransformer)transformer;
/* 411 */       String transformerName = legacyTransformer.getName();
/* 412 */       boolean include = true;
/* 413 */       for (String excludeClass : excludeTransformers) {
/* 414 */         if (transformerName.contains(excludeClass)) {
/* 415 */           include = false;
/*     */           break;
/*     */         } 
/*     */       } 
/* 419 */       if (include && !legacyTransformer.isDelegationExcluded()) {
/* 420 */         MixinServiceAbstract.logger.debug("  Adding:    {}", new Object[] { transformerName });
/* 421 */         this.delegatedTransformers.add(legacyTransformer); continue;
/*     */       } 
/* 423 */       MixinServiceAbstract.logger.debug("  Excluding: {}", new Object[] { transformerName });
/*     */     } 
/*     */ 
/*     */     
/* 427 */     MixinServiceAbstract.logger.debug("Transformer delegation list created with {} entries", new Object[] { Integer.valueOf(this.delegatedTransformers.size()) });
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void addTransformerExclusion(String name) {
/* 437 */     excludeTransformers.add(name);
/*     */ 
/*     */     
/* 440 */     this.delegatedTransformers = null;
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
/*     */   @Deprecated
/*     */   public byte[] getClassBytes(String name, String transformedName) throws IOException {
/*     */     URLClassLoader appClassLoader;
/* 455 */     byte[] classBytes = Launch.classLoader.getClassBytes(name);
/* 456 */     if (classBytes != null) {
/* 457 */       return classBytes;
/*     */     }
/*     */ 
/*     */     
/* 461 */     if (Launch.class.getClassLoader() instanceof URLClassLoader) {
/* 462 */       appClassLoader = (URLClassLoader)Launch.class.getClassLoader();
/*     */     } else {
/* 464 */       appClassLoader = new URLClassLoader(new URL[0], Launch.class.getClassLoader());
/*     */     } 
/*     */     
/* 467 */     InputStream classStream = null;
/*     */     try {
/* 469 */       String resourcePath = transformedName.replace('.', '/').concat(".class");
/* 470 */       classStream = appClassLoader.getResourceAsStream(resourcePath);
/* 471 */       return ByteStreams.toByteArray(classStream);
/* 472 */     } catch (Exception ex) {
/* 473 */       return null;
/*     */     } finally {
/* 475 */       Closeables.closeQuietly(classStream);
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
/*     */   @Deprecated
/*     */   public byte[] getClassBytes(String className, boolean runTransformers) throws ClassNotFoundException, IOException {
/* 491 */     String transformedName = className.replace('/', '.');
/* 492 */     String name = unmapClassName(transformedName);
/*     */     
/* 494 */     Profiler profiler = MixinEnvironment.getProfiler();
/* 495 */     Profiler.Section loadTime = profiler.begin(1, "class.load");
/* 496 */     byte[] classBytes = getClassBytes(name, transformedName);
/* 497 */     loadTime.end();
/*     */     
/* 499 */     if (runTransformers) {
/* 500 */       Profiler.Section transformTime = profiler.begin(1, "class.transform");
/* 501 */       classBytes = applyTransformers(name, transformedName, classBytes, profiler);
/* 502 */       transformTime.end();
/*     */     } 
/*     */     
/* 505 */     if (classBytes == null) {
/* 506 */       throw new ClassNotFoundException(String.format("The specified class '%s' was not found", new Object[] { transformedName }));
/*     */     }
/*     */     
/* 509 */     return classBytes;
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
/*     */   private byte[] applyTransformers(String name, String transformedName, byte[] basicClass, Profiler profiler) {
/* 523 */     if (this.classLoaderUtil.isClassExcluded(name, transformedName)) {
/* 524 */       return basicClass;
/*     */     }
/*     */     
/* 527 */     for (ILegacyClassTransformer transformer : getDelegatedLegacyTransformers()) {
/*     */       
/* 529 */       this.lock.clear();
/*     */       
/* 531 */       int pos = transformer.getName().lastIndexOf('.');
/* 532 */       String simpleName = transformer.getName().substring(pos + 1);
/* 533 */       Profiler.Section transformTime = profiler.begin(2, simpleName.toLowerCase(Locale.ROOT));
/* 534 */       transformTime.setInfo(transformer.getName());
/* 535 */       basicClass = transformer.transformClassBytes(name, transformedName, basicClass);
/* 536 */       transformTime.end();
/*     */       
/* 538 */       if (this.lock.isSet()) {
/*     */         
/* 540 */         addTransformerExclusion(transformer.getName());
/*     */         
/* 542 */         this.lock.clear();
/* 543 */         MixinServiceAbstract.logger.info("A re-entrant transformer '{}' was detected and will no longer process meta class data", new Object[] { transformer
/* 544 */               .getName() });
/*     */       } 
/*     */     } 
/*     */     
/* 548 */     return basicClass;
/*     */   }
/*     */   
/*     */   private String unmapClassName(String className) {
/* 552 */     if (this.nameTransformer == null) {
/* 553 */       findNameTransformer();
/*     */     }
/*     */     
/* 556 */     if (this.nameTransformer != null) {
/* 557 */       return this.nameTransformer.unmapClassName(className);
/*     */     }
/*     */     
/* 560 */     return className;
/*     */   }
/*     */   
/*     */   private void findNameTransformer() {
/* 564 */     List<IClassTransformer> transformers = Launch.classLoader.getTransformers();
/* 565 */     for (IClassTransformer transformer : transformers) {
/* 566 */       if (transformer instanceof IClassNameTransformer) {
/* 567 */         MixinServiceAbstract.logger.debug("Found name transformer: {}", new Object[] { transformer.getClass().getName() });
/* 568 */         this.nameTransformer = (IClassNameTransformer)transformer;
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ClassNode getClassNode(String className) throws ClassNotFoundException, IOException {
/* 579 */     return getClassNode(getClassBytes(className, true), 8);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ClassNode getClassNode(String className, boolean runTransformers) throws ClassNotFoundException, IOException {
/* 588 */     return getClassNode(getClassBytes(className, true), 8);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private ClassNode getClassNode(byte[] classBytes, int flags) {
/* 599 */     ClassNode classNode = new ClassNode();
/* 600 */     ClassReader classReader = new ClassReader(classBytes);
/* 601 */     classReader.accept((ClassVisitor)classNode, flags);
/* 602 */     return classNode;
/*     */   }
/*     */   
/*     */   private static int findInStackTrace(String className, String methodName) {
/* 606 */     Thread currentThread = Thread.currentThread();
/*     */     
/* 608 */     if (!"main".equals(currentThread.getName())) {
/* 609 */       return 0;
/*     */     }
/*     */     
/* 612 */     StackTraceElement[] stackTrace = currentThread.getStackTrace();
/* 613 */     for (StackTraceElement s : stackTrace) {
/* 614 */       if (className.equals(s.getClassName()) && methodName.equals(s.getMethodName())) {
/* 615 */         return s.getLineNumber();
/*     */       }
/*     */     } 
/*     */     
/* 619 */     return 0;
/*     */   }
/*     */ }


/* Location:              C:\Users\DuduM\OneDrive\Área de Trabalho\mw10\modularwarfare-shining-2023.2.4.4f-fix10.jar!\org\spongepowered\asm\service\mojang\MixinServiceLaunchWrapper.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */