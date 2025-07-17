/*     */ package org.spongepowered.tools.obfuscation;
/*     */ 
/*     */ import com.google.common.collect.ImmutableList;
/*     */ import java.io.File;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collection;
/*     */ import java.util.HashMap;
/*     */ import java.util.List;
/*     */ import java.util.Locale;
/*     */ import java.util.Map;
/*     */ import java.util.Properties;
/*     */ import java.util.regex.Matcher;
/*     */ import java.util.regex.Pattern;
/*     */ import javax.annotation.processing.Filer;
/*     */ import javax.annotation.processing.Messager;
/*     */ import javax.annotation.processing.ProcessingEnvironment;
/*     */ import javax.annotation.processing.RoundEnvironment;
/*     */ import javax.lang.model.element.AnnotationMirror;
/*     */ import javax.lang.model.element.AnnotationValue;
/*     */ import javax.lang.model.element.Element;
/*     */ import javax.lang.model.element.ExecutableElement;
/*     */ import javax.lang.model.element.PackageElement;
/*     */ import javax.lang.model.element.TypeElement;
/*     */ import javax.lang.model.element.VariableElement;
/*     */ import javax.lang.model.type.DeclaredType;
/*     */ import javax.lang.model.type.TypeMirror;
/*     */ import javax.lang.model.util.Elements;
/*     */ import javax.tools.Diagnostic;
/*     */ import javax.tools.FileObject;
/*     */ import javax.tools.StandardLocation;
/*     */ import org.spongepowered.asm.mixin.Overwrite;
/*     */ import org.spongepowered.asm.mixin.gen.Accessor;
/*     */ import org.spongepowered.asm.mixin.gen.Invoker;
/*     */ import org.spongepowered.asm.util.ITokenProvider;
/*     */ import org.spongepowered.asm.util.VersionNumber;
/*     */ import org.spongepowered.asm.util.logging.MessageRouter;
/*     */ import org.spongepowered.tools.obfuscation.interfaces.IJavadocProvider;
/*     */ import org.spongepowered.tools.obfuscation.interfaces.IMixinAnnotationProcessor;
/*     */ import org.spongepowered.tools.obfuscation.interfaces.IMixinValidator;
/*     */ import org.spongepowered.tools.obfuscation.interfaces.IObfuscationManager;
/*     */ import org.spongepowered.tools.obfuscation.interfaces.ITypeHandleProvider;
/*     */ import org.spongepowered.tools.obfuscation.mirror.AnnotationHandle;
/*     */ import org.spongepowered.tools.obfuscation.mirror.TypeHandle;
/*     */ import org.spongepowered.tools.obfuscation.mirror.TypeHandleSimulated;
/*     */ import org.spongepowered.tools.obfuscation.mirror.TypeReference;
/*     */ import org.spongepowered.tools.obfuscation.struct.InjectorRemap;
/*     */ import org.spongepowered.tools.obfuscation.validation.ParentValidator;
/*     */ import org.spongepowered.tools.obfuscation.validation.TargetValidator;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ final class AnnotatedMixins
/*     */   implements IMixinAnnotationProcessor, ITokenProvider, ITypeHandleProvider, IJavadocProvider
/*     */ {
/*     */   private static final String MAPID_SYSTEM_PROPERTY = "mixin.target.mapid";
/*     */   private static final String RECOMMENDED_MIXINGRADLE_VERSION = "0.7";
/*  94 */   private static Map<ProcessingEnvironment, AnnotatedMixins> instances = new HashMap<>();
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private final IMixinAnnotationProcessor.CompilerEnvironment env;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private final ProcessingEnvironment processingEnv;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 109 */   private final Map<String, AnnotatedMixin> mixins = new HashMap<>();
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 114 */   private final List<AnnotatedMixin> mixinsForPass = new ArrayList<>();
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private final IObfuscationManager obf;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private final List<IMixinValidator> validators;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 129 */   private final Map<String, Integer> tokenCache = new HashMap<>();
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private final TargetMap targets;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private Properties properties;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private AnnotatedMixins(ProcessingEnvironment processingEnv) {
/* 146 */     this.env = detectEnvironment(processingEnv);
/* 147 */     this.processingEnv = processingEnv;
/*     */     
/* 149 */     MessageRouter.setMessager(processingEnv.getMessager());
/*     */     
/* 151 */     String pluginVersion = checkPluginVersion(getOption("pluginVersion"));
/* 152 */     String pluginVersionString = (pluginVersion != null) ? String.format(" (MixinGradle Version=%s)", new Object[] { pluginVersion }) : "";
/* 153 */     printMessage(Diagnostic.Kind.NOTE, "SpongePowered MIXIN Annotation Processor Version=0.8" + pluginVersionString);
/*     */     
/* 155 */     this.targets = initTargetMap();
/* 156 */     this.obf = new ObfuscationManager(this);
/* 157 */     this.obf.init();
/*     */     
/* 159 */     this.validators = (List<IMixinValidator>)ImmutableList.of(new ParentValidator(this), new TargetValidator(this));
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 164 */     initTokenCache(getOption("tokens"));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private String checkPluginVersion(String version) {
/* 173 */     if (version == null) {
/* 174 */       return null;
/*     */     }
/*     */     
/* 177 */     VersionNumber pluginVersion = VersionNumber.parse(version);
/* 178 */     VersionNumber recommendedVersion = VersionNumber.parse("0.7");
/* 179 */     if (pluginVersion.compareTo(recommendedVersion) < 0) {
/* 180 */       printMessage(Diagnostic.Kind.WARNING, String.format("MixinGradle version %s is out of date. Update to the recommended version %s", new Object[] { pluginVersion, recommendedVersion }));
/*     */     }
/*     */     
/* 183 */     return pluginVersion.toString();
/*     */   }
/*     */   
/*     */   protected TargetMap initTargetMap() {
/* 187 */     TargetMap targets = TargetMap.create(System.getProperty("mixin.target.mapid"));
/* 188 */     System.setProperty("mixin.target.mapid", targets.getSessionId());
/* 189 */     String targetsFileName = getOption("dependencyTargetsFile");
/* 190 */     if (targetsFileName != null) {
/*     */       try {
/* 192 */         targets.readImports(new File(targetsFileName));
/* 193 */       } catch (IOException ex) {
/* 194 */         printMessage(Diagnostic.Kind.WARNING, "Could not read from specified imports file: " + targetsFileName);
/*     */       } 
/*     */     }
/* 197 */     return targets;
/*     */   }
/*     */   
/*     */   private void initTokenCache(String tokens) {
/* 201 */     if (tokens != null) {
/* 202 */       Pattern tokenPattern = Pattern.compile("^([A-Z0-9\\-_\\.]+)=([0-9]+)$");
/*     */       
/* 204 */       String[] tokenValues = tokens.replaceAll("\\s", "").toUpperCase(Locale.ROOT).split("[;,]");
/* 205 */       for (String tokenValue : tokenValues) {
/* 206 */         Matcher tokenMatcher = tokenPattern.matcher(tokenValue);
/* 207 */         if (tokenMatcher.matches()) {
/* 208 */           this.tokenCache.put(tokenMatcher.group(1), Integer.valueOf(Integer.parseInt(tokenMatcher.group(2))));
/*     */         }
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public ITypeHandleProvider getTypeProvider() {
/* 216 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public ITokenProvider getTokenProvider() {
/* 221 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public IObfuscationManager getObfuscationManager() {
/* 226 */     return this.obf;
/*     */   }
/*     */ 
/*     */   
/*     */   public IJavadocProvider getJavadocProvider() {
/* 231 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public ProcessingEnvironment getProcessingEnvironment() {
/* 236 */     return this.processingEnv;
/*     */   }
/*     */ 
/*     */   
/*     */   public IMixinAnnotationProcessor.CompilerEnvironment getCompilerEnvironment() {
/* 241 */     return this.env;
/*     */   }
/*     */ 
/*     */   
/*     */   public Integer getToken(String token) {
/* 246 */     if (this.tokenCache.containsKey(token)) {
/* 247 */       return this.tokenCache.get(token);
/*     */     }
/*     */     
/* 250 */     String option = getOption(token);
/* 251 */     Integer value = null;
/*     */     try {
/* 253 */       value = Integer.valueOf(Integer.parseInt(option));
/* 254 */     } catch (Exception exception) {}
/*     */ 
/*     */ 
/*     */     
/* 258 */     this.tokenCache.put(token, value);
/* 259 */     return value;
/*     */   }
/*     */ 
/*     */   
/*     */   public String getOption(String option) {
/* 264 */     if (option == null) {
/* 265 */       return null;
/*     */     }
/*     */     
/* 268 */     String value = this.processingEnv.getOptions().get(option);
/* 269 */     if (value != null) {
/* 270 */       return value;
/*     */     }
/*     */     
/* 273 */     return getProperties().getProperty(option);
/*     */   }
/*     */ 
/*     */   
/*     */   public String getOption(String option, String defaultValue) {
/* 278 */     String value = getOption(option);
/* 279 */     return (value != null) ? value : defaultValue;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean getOption(String option, boolean defaultValue) {
/* 284 */     String value = getOption(option);
/* 285 */     return (value != null) ? Boolean.parseBoolean(value) : defaultValue;
/*     */   }
/*     */ 
/*     */   
/*     */   public List<String> getOptions(String option) {
/* 290 */     ImmutableList.Builder<String> list = ImmutableList.builder();
/* 291 */     String value = getOption(option);
/* 292 */     if (value != null) {
/* 293 */       for (String part : value.split(",")) {
/* 294 */         list.add(part);
/*     */       }
/*     */     }
/* 297 */     return (List<String>)list.build();
/*     */   }
/*     */   
/*     */   public Properties getProperties() {
/* 301 */     if (this.properties == null) {
/* 302 */       this.properties = new Properties();
/*     */       
/*     */       try {
/* 305 */         Filer filer = this.processingEnv.getFiler();
/* 306 */         FileObject propertyFile = filer.getResource(StandardLocation.SOURCE_PATH, "", "mixin.properties");
/* 307 */         if (propertyFile != null) {
/* 308 */           InputStream inputStream = propertyFile.openInputStream();
/* 309 */           this.properties.load(inputStream);
/* 310 */           inputStream.close();
/*     */         } 
/* 312 */       } catch (Exception exception) {}
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 317 */     return this.properties;
/*     */   }
/*     */   
/*     */   private IMixinAnnotationProcessor.CompilerEnvironment detectEnvironment(ProcessingEnvironment processingEnv) {
/* 321 */     if (processingEnv.getClass().getName().contains("jdt")) {
/* 322 */       return IMixinAnnotationProcessor.CompilerEnvironment.JDT;
/*     */     }
/*     */     
/* 325 */     return IMixinAnnotationProcessor.CompilerEnvironment.JAVAC;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void writeMappings() {
/* 332 */     this.obf.writeMappings();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void writeReferences() {
/* 339 */     this.obf.writeReferences();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void clear() {
/* 346 */     this.mixins.clear();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void registerMixin(TypeElement mixinType) {
/* 353 */     String name = mixinType.getQualifiedName().toString();
/*     */     
/* 355 */     if (!this.mixins.containsKey(name)) {
/* 356 */       AnnotatedMixin mixin = new AnnotatedMixin(this, mixinType);
/* 357 */       this.targets.registerTargets(mixin);
/* 358 */       mixin.runValidators(IMixinValidator.ValidationPass.EARLY, this.validators);
/* 359 */       this.mixins.put(name, mixin);
/* 360 */       this.mixinsForPass.add(mixin);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public AnnotatedMixin getMixin(TypeElement mixinType) {
/* 368 */     return getMixin(mixinType.getQualifiedName().toString());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public AnnotatedMixin getMixin(String mixinType) {
/* 375 */     return this.mixins.get(mixinType);
/*     */   }
/*     */   
/*     */   public Collection<TypeMirror> getMixinsTargeting(TypeMirror targetType) {
/* 379 */     return getMixinsTargeting((TypeElement)((DeclaredType)targetType).asElement());
/*     */   }
/*     */   
/*     */   public Collection<TypeMirror> getMixinsTargeting(TypeElement targetType) {
/* 383 */     List<TypeMirror> minions = new ArrayList<>();
/*     */     
/* 385 */     for (TypeReference mixin : this.targets.getMixinsTargeting(targetType)) {
/* 386 */       TypeHandle handle = mixin.getHandle(this.processingEnv);
/* 387 */       if (handle != null) {
/* 388 */         minions.add(handle.getType());
/*     */       }
/*     */     } 
/*     */     
/* 392 */     return minions;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void registerAccessor(TypeElement mixinType, ExecutableElement method) {
/* 402 */     AnnotatedMixin mixinClass = getMixin(mixinType);
/* 403 */     if (mixinClass == null) {
/* 404 */       printMessage(Diagnostic.Kind.ERROR, "Found @Accessor annotation on a non-mixin method", method);
/*     */       
/*     */       return;
/*     */     } 
/* 408 */     AnnotationHandle accessor = AnnotationHandle.of(method, Accessor.class);
/* 409 */     mixinClass.registerAccessor(method, accessor, shouldRemap(mixinClass, accessor));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void registerInvoker(TypeElement mixinType, ExecutableElement method) {
/* 419 */     AnnotatedMixin mixinClass = getMixin(mixinType);
/* 420 */     if (mixinClass == null) {
/* 421 */       printMessage(Diagnostic.Kind.ERROR, "Found @Accessor annotation on a non-mixin method", method);
/*     */       
/*     */       return;
/*     */     } 
/* 425 */     AnnotationHandle invoker = AnnotationHandle.of(method, Invoker.class);
/* 426 */     mixinClass.registerInvoker(method, invoker, shouldRemap(mixinClass, invoker));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void registerOverwrite(TypeElement mixinType, ExecutableElement method) {
/* 436 */     AnnotatedMixin mixinClass = getMixin(mixinType);
/* 437 */     if (mixinClass == null) {
/* 438 */       printMessage(Diagnostic.Kind.ERROR, "Found @Overwrite annotation on a non-mixin method", method);
/*     */       
/*     */       return;
/*     */     } 
/* 442 */     AnnotationHandle overwrite = AnnotationHandle.of(method, Overwrite.class);
/* 443 */     mixinClass.registerOverwrite(method, overwrite, shouldRemap(mixinClass, overwrite));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void registerShadow(TypeElement mixinType, VariableElement field, AnnotationHandle shadow) {
/* 454 */     AnnotatedMixin mixinClass = getMixin(mixinType);
/* 455 */     if (mixinClass == null) {
/* 456 */       printMessage(Diagnostic.Kind.ERROR, "Found @Shadow annotation on a non-mixin field", field);
/*     */       
/*     */       return;
/*     */     } 
/* 460 */     mixinClass.registerShadow(field, shadow, shouldRemap(mixinClass, shadow));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void registerShadow(TypeElement mixinType, ExecutableElement method, AnnotationHandle shadow) {
/* 471 */     AnnotatedMixin mixinClass = getMixin(mixinType);
/* 472 */     if (mixinClass == null) {
/* 473 */       printMessage(Diagnostic.Kind.ERROR, "Found @Shadow annotation on a non-mixin method", method);
/*     */       
/*     */       return;
/*     */     } 
/* 477 */     mixinClass.registerShadow(method, shadow, shouldRemap(mixinClass, shadow));
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
/*     */   public void registerInjector(TypeElement mixinType, ExecutableElement method, AnnotationHandle inject) {
/* 489 */     AnnotatedMixin mixinClass = getMixin(mixinType);
/* 490 */     if (mixinClass == null) {
/* 491 */       printMessage(Diagnostic.Kind.ERROR, "Found " + inject + " annotation on a non-mixin method", method);
/*     */       
/*     */       return;
/*     */     } 
/* 495 */     InjectorRemap remap = new InjectorRemap(shouldRemap(mixinClass, inject));
/* 496 */     mixinClass.registerInjector(method, inject, remap);
/* 497 */     remap.dispatchPendingMessages((Messager)this);
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
/*     */   public void registerSoftImplements(TypeElement mixin, AnnotationHandle implementsAnnotation) {
/* 509 */     AnnotatedMixin mixinClass = getMixin(mixin);
/* 510 */     if (mixinClass == null) {
/* 511 */       printMessage(Diagnostic.Kind.ERROR, "Found @Implements annotation on a non-mixin class");
/*     */       
/*     */       return;
/*     */     } 
/* 515 */     mixinClass.registerSoftImplements(implementsAnnotation);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void onPassStarted() {
/* 523 */     this.mixinsForPass.clear();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void onPassCompleted(RoundEnvironment roundEnv) {
/* 530 */     if (!"true".equalsIgnoreCase(getOption("disableTargetExport"))) {
/* 531 */       this.targets.write(true);
/*     */     }
/*     */     
/* 534 */     for (AnnotatedMixin mixin : roundEnv.processingOver() ? (Object<?>)this.mixins.values() : (Object<?>)this.mixinsForPass) {
/* 535 */       mixin.runValidators(roundEnv.processingOver() ? IMixinValidator.ValidationPass.FINAL : IMixinValidator.ValidationPass.LATE, this.validators);
/*     */     }
/*     */   }
/*     */   
/*     */   private static boolean shouldRemap(AnnotatedMixin mixinClass, AnnotationHandle annotation) {
/* 540 */     return annotation.getBoolean("remap", mixinClass.remap());
/*     */   }
/*     */   
/*     */   private static boolean shouldSuppress(Element element, SuppressedBy suppressedBy) {
/* 544 */     if (element == null || suppressedBy == null) {
/* 545 */       return false;
/*     */     }
/* 547 */     if (AnnotationHandle.of(element, SuppressWarnings.class).getList().contains(suppressedBy.getToken())) {
/* 548 */       return true;
/*     */     }
/* 550 */     return shouldSuppress(element.getEnclosingElement(), suppressedBy);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void printMessage(Diagnostic.Kind kind, CharSequence msg) {
/* 558 */     if (this.env == IMixinAnnotationProcessor.CompilerEnvironment.JAVAC || kind != Diagnostic.Kind.NOTE) {
/* 559 */       this.processingEnv.getMessager().printMessage(kind, msg);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void printMessage(Diagnostic.Kind kind, CharSequence msg, Element element) {
/* 568 */     this.processingEnv.getMessager().printMessage(kind, msg, element);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void printMessage(Diagnostic.Kind kind, CharSequence msg, Element element, SuppressedBy suppressedBy) {
/* 576 */     if (kind != Diagnostic.Kind.WARNING || !shouldSuppress(element, suppressedBy)) {
/* 577 */       this.processingEnv.getMessager().printMessage(kind, msg, element);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void printMessage(Diagnostic.Kind kind, CharSequence msg, Element element, AnnotationMirror annotation) {
/* 586 */     this.processingEnv.getMessager().printMessage(kind, msg, element, annotation);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void printMessage(Diagnostic.Kind kind, CharSequence msg, Element element, AnnotationMirror annotation, SuppressedBy suppressedBy) {
/* 594 */     if (kind != Diagnostic.Kind.WARNING || !shouldSuppress(element, suppressedBy)) {
/* 595 */       this.processingEnv.getMessager().printMessage(kind, msg, element, annotation);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void printMessage(Diagnostic.Kind kind, CharSequence msg, Element element, AnnotationMirror annotation, AnnotationValue value) {
/* 604 */     this.processingEnv.getMessager().printMessage(kind, msg, element, annotation, value);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void printMessage(Diagnostic.Kind kind, CharSequence msg, Element element, AnnotationMirror annotation, AnnotationValue value, SuppressedBy suppressedBy) {
/* 613 */     if (kind != Diagnostic.Kind.WARNING || !shouldSuppress(element, suppressedBy)) {
/* 614 */       this.processingEnv.getMessager().printMessage(kind, msg, element, annotation, value);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public TypeHandle getTypeHandle(String name) {
/* 624 */     name = name.replace('/', '.');
/*     */     
/* 626 */     Elements elements = this.processingEnv.getElementUtils();
/* 627 */     TypeElement element = elements.getTypeElement(name);
/* 628 */     if (element != null) {
/*     */       try {
/* 630 */         return new TypeHandle(element);
/* 631 */       } catch (NullPointerException nullPointerException) {}
/*     */     }
/*     */ 
/*     */ 
/*     */     
/* 636 */     int lastDotPos = name.lastIndexOf('.');
/* 637 */     if (lastDotPos > -1) {
/* 638 */       String pkg = name.substring(0, lastDotPos);
/* 639 */       PackageElement packageElement = elements.getPackageElement(pkg);
/* 640 */       if (packageElement != null) {
/* 641 */         return new TypeHandle(packageElement, name);
/*     */       }
/*     */     } 
/*     */     
/* 645 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public TypeHandle getSimulatedHandle(String name, TypeMirror simulatedTarget) {
/* 653 */     name = name.replace('/', '.');
/* 654 */     int lastDotPos = name.lastIndexOf('.');
/* 655 */     if (lastDotPos > -1) {
/* 656 */       String pkg = name.substring(0, lastDotPos);
/* 657 */       PackageElement packageElement = this.processingEnv.getElementUtils().getPackageElement(pkg);
/* 658 */       if (packageElement != null) {
/* 659 */         return (TypeHandle)new TypeHandleSimulated(packageElement, name, simulatedTarget);
/*     */       }
/*     */     } 
/*     */     
/* 663 */     return (TypeHandle)new TypeHandleSimulated(name, simulatedTarget);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getJavadoc(Element element) {
/* 672 */     Elements elements = this.processingEnv.getElementUtils();
/* 673 */     return elements.getDocComment(element);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static AnnotatedMixins getMixinsForEnvironment(ProcessingEnvironment processingEnv) {
/* 680 */     AnnotatedMixins mixins = instances.get(processingEnv);
/* 681 */     if (mixins == null) {
/* 682 */       mixins = new AnnotatedMixins(processingEnv);
/* 683 */       instances.put(processingEnv, mixins);
/*     */     } 
/* 685 */     return mixins;
/*     */   }
/*     */ }


/* Location:              C:\Users\DuduM\OneDrive\Área de Trabalho\mw10\modularwarfare-shining-2023.2.4.4f-fix10.jar!\org\spongepowered\tools\obfuscation\AnnotatedMixins.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */