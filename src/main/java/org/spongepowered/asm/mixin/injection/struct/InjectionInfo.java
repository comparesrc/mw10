/*     */ package org.spongepowered.asm.mixin.injection.struct;
/*     */ 
/*     */ import com.google.common.base.Strings;
/*     */ import com.google.common.collect.ImmutableSet;
/*     */ import java.lang.annotation.Annotation;
/*     */ import java.lang.annotation.ElementType;
/*     */ import java.lang.annotation.Retention;
/*     */ import java.lang.annotation.RetentionPolicy;
/*     */ import java.lang.annotation.Target;
/*     */ import java.lang.reflect.Constructor;
/*     */ import java.lang.reflect.InvocationTargetException;
/*     */ import java.util.ArrayDeque;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collection;
/*     */ import java.util.Deque;
/*     */ import java.util.LinkedHashMap;
/*     */ import java.util.LinkedHashSet;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.Set;
/*     */ import javax.tools.Diagnostic;
/*     */ import org.objectweb.asm.Type;
/*     */ import org.objectweb.asm.tree.AnnotationNode;
/*     */ import org.objectweb.asm.tree.MethodNode;
/*     */ import org.spongepowered.asm.mixin.Dynamic;
/*     */ import org.spongepowered.asm.mixin.Final;
/*     */ import org.spongepowered.asm.mixin.MixinEnvironment;
/*     */ import org.spongepowered.asm.mixin.extensibility.IMixinInfo;
/*     */ import org.spongepowered.asm.mixin.injection.InjectionPoint;
/*     */ import org.spongepowered.asm.mixin.injection.code.ISliceContext;
/*     */ import org.spongepowered.asm.mixin.injection.code.Injector;
/*     */ import org.spongepowered.asm.mixin.injection.code.InjectorTarget;
/*     */ import org.spongepowered.asm.mixin.injection.code.MethodSlice;
/*     */ import org.spongepowered.asm.mixin.injection.code.MethodSlices;
/*     */ import org.spongepowered.asm.mixin.injection.selectors.ITargetSelector;
/*     */ import org.spongepowered.asm.mixin.injection.selectors.InvalidSelectorException;
/*     */ import org.spongepowered.asm.mixin.injection.selectors.TargetSelector;
/*     */ import org.spongepowered.asm.mixin.injection.throwables.InjectionError;
/*     */ import org.spongepowered.asm.mixin.injection.throwables.InvalidInjectionException;
/*     */ import org.spongepowered.asm.mixin.refmap.IMixinContext;
/*     */ import org.spongepowered.asm.mixin.struct.SpecialMethodInfo;
/*     */ import org.spongepowered.asm.mixin.throwables.MixinError;
/*     */ import org.spongepowered.asm.mixin.throwables.MixinException;
/*     */ import org.spongepowered.asm.mixin.transformer.MixinTargetContext;
/*     */ import org.spongepowered.asm.mixin.transformer.meta.MixinMerged;
/*     */ import org.spongepowered.asm.mixin.transformer.throwables.InvalidMixinException;
/*     */ import org.spongepowered.asm.util.Annotations;
/*     */ import org.spongepowered.asm.util.Bytecode;
/*     */ import org.spongepowered.asm.util.asm.ASM;
/*     */ import org.spongepowered.asm.util.asm.ElementNode;
/*     */ import org.spongepowered.asm.util.asm.MethodNodeEx;
/*     */ import org.spongepowered.asm.util.logging.MessageRouter;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public abstract class InjectionInfo
/*     */   extends SpecialMethodInfo
/*     */   implements ISliceContext
/*     */ {
/*     */   public static final String DEFAULT_PREFIX = "handler";
/*     */   
/*     */   static class InjectorEntry
/*     */   {
/*     */     final Class<? extends Annotation> annotationType;
/*     */     final Class<? extends InjectionInfo> type;
/*     */     final Constructor<? extends InjectionInfo> ctor;
/*     */     final String simpleName;
/*     */     final String prefix;
/*     */     
/*     */     InjectorEntry(Class<? extends Annotation> annotationType, Class<? extends InjectionInfo> type) throws NoSuchMethodException {
/* 128 */       this.annotationType = annotationType;
/* 129 */       this.type = type;
/* 130 */       this.ctor = type.getDeclaredConstructor(new Class[] { MixinTargetContext.class, MethodNode.class, AnnotationNode.class });
/* 131 */       this.simpleName = annotationType.getSimpleName() + ";";
/*     */       
/* 133 */       InjectionInfo.HandlerPrefix handlerPrefix = type.<InjectionInfo.HandlerPrefix>getAnnotation(InjectionInfo.HandlerPrefix.class);
/* 134 */       this.prefix = (handlerPrefix != null) ? handlerPrefix.value() : "handler";
/*     */     }
/*     */     
/*     */     InjectionInfo create(MixinTargetContext mixin, MethodNode method, AnnotationNode annotation) {
/*     */       try {
/* 139 */         return this.ctor.newInstance(new Object[] { mixin, method, annotation });
/* 140 */       } catch (InvocationTargetException itex) {
/* 141 */         Throwable cause = itex.getCause();
/* 142 */         if (cause instanceof MixinException) {
/* 143 */           throw (MixinException)cause;
/*     */         }
/* 145 */         Throwable ex = (cause != null) ? cause : itex;
/* 146 */         throw new MixinError("Error initialising injector metaclass [" + this.type + "] for annotation " + annotation.desc, ex);
/* 147 */       } catch (ReflectiveOperationException ex) {
/* 148 */         throw new MixinError("Failed to instantiate injector metaclass [" + this.type + "] for annotation " + annotation.desc, ex);
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
/*     */   
/* 161 */   private static Map<String, InjectorEntry> registry = new LinkedHashMap<>();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 168 */   private static Class<? extends Annotation>[] registeredAnnotations = (Class<? extends Annotation>[])new Class[0];
/*     */   protected final boolean isStatic;
/*     */   
/*     */   static {
/* 172 */     register((Class)CallbackInjectionInfo.class);
/* 173 */     register((Class)ModifyArgInjectionInfo.class);
/* 174 */     register((Class)ModifyArgsInjectionInfo.class);
/* 175 */     register((Class)RedirectInjectionInfo.class);
/* 176 */     register((Class)ModifyVariableInjectionInfo.class);
/* 177 */     register((Class)ModifyConstantInjectionInfo.class);
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
/* 188 */   protected final Deque<MethodNode> targets = new ArrayDeque<>();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected final MethodSlices slices;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected final String atKey;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 204 */   protected final List<InjectionPoint> injectionPoints = new ArrayList<>();
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 209 */   protected final Map<Target, List<InjectionNodes.InjectionNode>> targetNodes = new LinkedHashMap<>();
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 214 */   protected int targetCount = 0;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected Injector injector;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected InjectorGroupInfo group;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 229 */   private final List<MethodNode> injectedMethods = new ArrayList<>(0);
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 234 */   private int expectedCallbackCount = 1;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 239 */   private int requiredCallbackCount = 0;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 244 */   private int maxCallbackCount = Integer.MAX_VALUE;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 249 */   private int injectedCallbackCount = 0;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected InjectionInfo(MixinTargetContext mixin, MethodNode method, AnnotationNode annotation) {
/* 259 */     this(mixin, method, annotation, "at");
/*     */   }
/*     */   
/*     */   protected InjectionInfo(MixinTargetContext mixin, MethodNode method, AnnotationNode annotation, String atKey) {
/* 263 */     super(mixin, method, annotation);
/* 264 */     this.isStatic = Bytecode.isStatic(method);
/* 265 */     this.slices = MethodSlices.parse(this);
/* 266 */     this.atKey = atKey;
/* 267 */     readAnnotation();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void readAnnotation() {
/* 274 */     if (this.annotation == null) {
/*     */       return;
/*     */     }
/*     */     
/* 278 */     List<AnnotationNode> injectionPoints = readInjectionPoints();
/* 279 */     parseRequirements();
/* 280 */     findMethods(parseTargets());
/* 281 */     parseInjectionPoints(injectionPoints);
/* 282 */     this.injector = parseInjector(this.annotation);
/*     */   }
/*     */   
/*     */   protected Set<ITargetSelector> parseTargets() {
/* 286 */     List<String> methods = Annotations.getValue(this.annotation, "method", false);
/* 287 */     if (methods == null) {
/* 288 */       throw new InvalidInjectionException(this, String.format("%s annotation on %s is missing method name", new Object[] { this.annotationType, this.methodName }));
/*     */     }
/*     */ 
/*     */     
/* 292 */     Set<ITargetSelector> selectors = new LinkedHashSet<>();
/* 293 */     for (String method : methods) {
/*     */       try {
/* 295 */         selectors.add(TargetSelector.parseAndValidate(method, (IMixinContext)this.mixin).attach((IMixinContext)this.mixin));
/* 296 */       } catch (InvalidMemberDescriptorException ex) {
/* 297 */         throw new InvalidInjectionException(this, String.format("%s annotation on %s, has invalid target descriptor: \"%s\". %s", new Object[] { this.annotationType, this.methodName, method, this.mixin
/* 298 */                 .getReferenceMapper().getStatus() }));
/* 299 */       } catch (TargetNotSupportedException ex) {
/* 300 */         throw new InvalidInjectionException(this, 
/* 301 */             String.format("%s annotation on %s specifies a target class '%s', which is not supported", new Object[] {
/* 302 */                 this.annotationType, this.methodName, ex.getMessage() }));
/* 303 */       } catch (InvalidSelectorException ex) {
/* 304 */         throw new InvalidInjectionException(this, 
/* 305 */             String.format("%s annotation on %s is decorated with an invalid selector: %s", new Object[] {
/* 306 */                 this.annotationType, this.methodName, ex.getMessage() }));
/*     */       } 
/*     */     } 
/* 309 */     return selectors;
/*     */   }
/*     */   
/*     */   protected List<AnnotationNode> readInjectionPoints() {
/* 313 */     List<AnnotationNode> ats = Annotations.getValue(this.annotation, this.atKey, false);
/* 314 */     if (ats == null) {
/* 315 */       throw new InvalidInjectionException(this, String.format("%s annotation on %s is missing '%s' value(s)", new Object[] { this.annotationType, this.methodName, this.atKey }));
/*     */     }
/*     */     
/* 318 */     return ats;
/*     */   }
/*     */   
/*     */   protected void parseInjectionPoints(List<AnnotationNode> ats) {
/* 322 */     this.injectionPoints.addAll(InjectionPoint.parse((IMixinContext)this.mixin, this.method, this.annotation, ats));
/*     */   }
/*     */   
/*     */   protected void parseRequirements() {
/* 326 */     this.group = this.mixin.getInjectorGroups().parseGroup(this.method, this.mixin.getDefaultInjectorGroup()).add(this);
/*     */     
/* 328 */     Integer expect = (Integer)Annotations.getValue(this.annotation, "expect");
/* 329 */     if (expect != null) {
/* 330 */       this.expectedCallbackCount = expect.intValue();
/*     */     }
/*     */     
/* 333 */     Integer require = (Integer)Annotations.getValue(this.annotation, "require");
/* 334 */     if (require != null && require.intValue() > -1) {
/* 335 */       this.requiredCallbackCount = require.intValue();
/* 336 */     } else if (this.group.isDefault()) {
/* 337 */       this.requiredCallbackCount = this.mixin.getDefaultRequiredInjections();
/*     */     } 
/*     */     
/* 340 */     Integer allow = (Integer)Annotations.getValue(this.annotation, "allow");
/* 341 */     if (allow != null) {
/* 342 */       this.maxCallbackCount = Math.max(Math.max(this.requiredCallbackCount, 1), allow.intValue());
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
/*     */   public boolean isValid() {
/* 356 */     return (this.targets.size() > 0 && this.injectionPoints.size() > 0);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void prepare() {
/* 363 */     this.targetNodes.clear();
/* 364 */     for (MethodNode targetMethod : this.targets) {
/* 365 */       Target target = this.mixin.getTargetMethod(targetMethod);
/* 366 */       InjectorTarget injectorTarget = new InjectorTarget(this, target);
/* 367 */       this.targetNodes.put(target, this.injector.find(injectorTarget, this.injectionPoints));
/* 368 */       injectorTarget.dispose();
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void inject() {
/* 376 */     for (Map.Entry<Target, List<InjectionNodes.InjectionNode>> entry : this.targetNodes.entrySet()) {
/* 377 */       this.injector.inject(entry.getKey(), entry.getValue());
/*     */     }
/* 379 */     this.targets.clear();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void postInject() {
/* 386 */     for (MethodNode method : this.injectedMethods) {
/* 387 */       this.classNode.methods.add(method);
/*     */     }
/*     */     
/* 390 */     String description = getDescription();
/* 391 */     String refMapStatus = this.mixin.getReferenceMapper().getStatus();
/* 392 */     String dynamicInfo = getDynamicInfo();
/* 393 */     if (this.mixin.getEnvironment().getOption(MixinEnvironment.Option.DEBUG_INJECTORS) && this.injectedCallbackCount < this.expectedCallbackCount)
/* 394 */       throw new InvalidInjectionException(this, 
/* 395 */           String.format("Injection validation failed: %s %s%s in %s expected %d invocation(s) but %d succeeded. Scanned %d target(s). %s%s", new Object[] {
/* 396 */               description, this.methodName, this.method.desc, this.mixin, Integer.valueOf(this.expectedCallbackCount), Integer.valueOf(this.injectedCallbackCount), 
/* 397 */               Integer.valueOf(this.targetCount), refMapStatus, dynamicInfo })); 
/* 398 */     if (this.injectedCallbackCount < this.requiredCallbackCount)
/* 399 */       throw new InjectionError(
/* 400 */           String.format("Critical injection failure: %s %s%s in %s failed injection check, (%d/%d) succeeded. Scanned %d target(s). %s%s", new Object[] {
/* 401 */               description, this.methodName, this.method.desc, this.mixin, Integer.valueOf(this.injectedCallbackCount), Integer.valueOf(this.requiredCallbackCount), 
/* 402 */               Integer.valueOf(this.targetCount), refMapStatus, dynamicInfo })); 
/* 403 */     if (this.injectedCallbackCount > this.maxCallbackCount) {
/* 404 */       throw new InjectionError(
/* 405 */           String.format("Critical injection failure: %s %s%s in %s failed injection check, %d succeeded of %d allowed.%s", new Object[] {
/* 406 */               description, this.methodName, this.method.desc, this.mixin, Integer.valueOf(this.injectedCallbackCount), Integer.valueOf(this.maxCallbackCount), dynamicInfo
/*     */             }));
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void notifyInjected(Target target) {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected String getDescription() {
/* 422 */     return "Callback method";
/*     */   }
/*     */ 
/*     */   
/*     */   public String toString() {
/* 427 */     return describeInjector((IMixinContext)this.mixin, this.annotation, this.method);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Collection<MethodNode> getTargets() {
/* 436 */     return this.targets;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public MethodSlice getSlice(String id) {
/* 444 */     return this.slices.get(getSliceId(id));
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
/*     */   public String getSliceId(String id) {
/* 456 */     return "";
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getInjectedCallbackCount() {
/* 465 */     return this.injectedCallbackCount;
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
/*     */   public MethodNode addMethod(int access, String name, String desc) {
/* 478 */     MethodNode method = new MethodNode(ASM.API_VERSION, access | 0x1000, name, desc, null, null);
/* 479 */     this.injectedMethods.add(method);
/* 480 */     return method;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void addCallbackInvocation(MethodNode handler) {
/* 489 */     this.injectedCallbackCount++;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void findMethods(Set<ITargetSelector> selectors) {
/* 498 */     this.targets.clear();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 504 */     int passes = this.mixin.getEnvironment().getOption(MixinEnvironment.Option.REFMAP_REMAP) ? 2 : 1;
/*     */     
/* 506 */     for (ITargetSelector selector : selectors) {
/* 507 */       int matchCount = selector.getMatchCount();
/* 508 */       for (int count = 0, pass = 0; pass < passes && count < 1; pass++) {
/* 509 */         for (MethodNode target : this.classNode.methods) {
/* 510 */           if (selector.match(ElementNode.of(this.classNode, target)).isExactMatch()) {
/* 511 */             boolean isMixinMethod = (Annotations.getVisible(target, MixinMerged.class) != null);
/* 512 */             if (matchCount > 1 && (Bytecode.isStatic(target) != this.isStatic || target == this.method || isMixinMethod)) {
/*     */               continue;
/*     */             }
/*     */             
/* 516 */             checkTarget(target);
/* 517 */             this.targets.add(target);
/* 518 */             count++;
/*     */             
/* 520 */             if (count >= matchCount) {
/*     */               break;
/*     */             }
/*     */           } 
/*     */         } 
/*     */ 
/*     */         
/* 527 */         selector = selector.configure(new String[] { "permissive" });
/*     */       } 
/*     */     } 
/*     */     
/* 531 */     this.targetCount = this.targets.size();
/* 532 */     if (this.targetCount > 0) {
/*     */       return;
/*     */     }
/*     */     
/* 536 */     if (this.mixin.getEnvironment().getOption(MixinEnvironment.Option.DEBUG_INJECTORS) && this.expectedCallbackCount > 0)
/* 537 */       throw new InvalidInjectionException(this, 
/* 538 */           String.format("Injection validation failed: %s annotation on %s could not find any targets matching %s in %s. %s%s", new Object[] {
/* 539 */               this.annotationType, this.methodName, namesOf(selectors), this.mixin.getTarget(), this.mixin
/* 540 */               .getReferenceMapper().getStatus(), getDynamicInfo() })); 
/* 541 */     if (this.requiredCallbackCount > 0)
/* 542 */       throw new InvalidInjectionException(this, 
/* 543 */           String.format("Critical injection failure: %s annotation on %s could not find any targets matching %s in %s. %s%s", new Object[] {
/* 544 */               this.annotationType, this.methodName, namesOf(selectors), this.mixin.getTarget(), this.mixin
/* 545 */               .getReferenceMapper().getStatus(), getDynamicInfo()
/*     */             })); 
/*     */   }
/*     */   
/*     */   private void checkTarget(MethodNode target) {
/* 550 */     AnnotationNode merged = Annotations.getVisible(target, MixinMerged.class);
/* 551 */     if (merged == null) {
/*     */       return;
/*     */     }
/*     */     
/* 555 */     if (Annotations.getVisible(target, Final.class) != null) {
/* 556 */       throw new InvalidInjectionException(this, String.format("%s cannot inject into @Final method %s::%s%s merged by %s", new Object[] { this, this.classNode.name, target.name, target.desc, 
/* 557 */               Annotations.getValue(merged, "mixin") }));
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected String getDynamicInfo() {
/* 568 */     AnnotationNode annotation = Annotations.getInvisible(this.method, Dynamic.class);
/* 569 */     String description = Strings.nullToEmpty((String)Annotations.getValue(annotation));
/* 570 */     Type upstream = (Type)Annotations.getValue(annotation, "mixin");
/* 571 */     if (upstream != null) {
/* 572 */       description = String.format("{%s} %s", new Object[] { upstream.getClassName(), description }).trim();
/*     */     }
/* 574 */     return (description.length() > 0) ? String.format(" Method is @Dynamic(%s)", new Object[] { description }) : "";
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
/*     */   public static InjectionInfo parse(MixinTargetContext mixin, MethodNode method) {
/* 587 */     AnnotationNode annotation = getInjectorAnnotation(mixin.getMixin(), method);
/*     */     
/* 589 */     if (annotation == null) {
/* 590 */       return null;
/*     */     }
/*     */     
/* 593 */     for (InjectorEntry injector : registry.values()) {
/* 594 */       if (annotation.desc.endsWith(injector.simpleName)) {
/* 595 */         return injector.create(mixin, method, annotation);
/*     */       }
/*     */     } 
/*     */     
/* 599 */     return null;
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
/*     */   public static AnnotationNode getInjectorAnnotation(IMixinInfo mixin, MethodNode method) {
/* 612 */     AnnotationNode annotation = null;
/*     */     try {
/* 614 */       annotation = Annotations.getSingleVisible(method, (Class[])registeredAnnotations);
/* 615 */     } catch (IllegalArgumentException ex) {
/* 616 */       throw new InvalidMixinException(mixin, String.format("Error parsing annotations on %s in %s: %s", new Object[] { method.name, mixin.getClassName(), ex
/* 617 */               .getMessage() }));
/*     */     } 
/* 619 */     return annotation;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static String getInjectorPrefix(AnnotationNode annotation) {
/* 629 */     if (annotation == null) {
/* 630 */       return "handler";
/*     */     }
/*     */     
/* 633 */     for (InjectorEntry injector : registry.values()) {
/* 634 */       if (annotation.desc.endsWith(injector.simpleName)) {
/* 635 */         return injector.prefix;
/*     */       }
/*     */     } 
/*     */     
/* 639 */     return "handler";
/*     */   }
/*     */   
/*     */   static String describeInjector(IMixinContext mixin, AnnotationNode annotation, MethodNode method) {
/* 643 */     return String.format("%s->@%s::%s%s", new Object[] { mixin.toString(), Bytecode.getSimpleName(annotation), MethodNodeEx.getName(method), method.desc });
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static String namesOf(Collection<ITargetSelector> selectors) {
/* 653 */     int index = 0, count = selectors.size();
/* 654 */     StringBuilder sb = new StringBuilder();
/* 655 */     for (ITargetSelector selector : selectors) {
/* 656 */       if (index > 0) {
/* 657 */         if (index == count - 1) {
/* 658 */           sb.append(" or ");
/*     */         } else {
/* 660 */           sb.append(", ");
/*     */         } 
/*     */       }
/* 663 */       sb.append('\'').append(selector.toString()).append('\'');
/* 664 */       index++;
/*     */     } 
/* 666 */     return sb.toString();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void register(Class<? extends InjectionInfo> type) {
/*     */     InjectorEntry entry;
/* 676 */     AnnotationType annotationType = type.<AnnotationType>getAnnotation(AnnotationType.class);
/* 677 */     if (annotationType == null) {
/* 678 */       throw new IllegalArgumentException("Injection info class " + type + " is not annotated with @AnnotationType");
/*     */     }
/*     */ 
/*     */     
/*     */     try {
/* 683 */       entry = new InjectorEntry(annotationType.value(), type);
/* 684 */     } catch (NoSuchMethodException ex) {
/* 685 */       throw new MixinError("InjectionInfo class " + type.getName() + " is missing a valid constructor");
/*     */     } 
/* 687 */     InjectorEntry existing = registry.get(entry.simpleName);
/* 688 */     if (existing != null) {
/* 689 */       MessageRouter.getMessager().printMessage(Diagnostic.Kind.WARNING, String.format("Overriding InjectionInfo for @%s with %s (previously %s)", new Object[] { annotationType
/* 690 */               .value().getSimpleName(), type.getName(), existing.type.getName() }));
/*     */     } else {
/* 692 */       MessageRouter.getMessager().printMessage(Diagnostic.Kind.OTHER, String.format("Registering new injector for @%s with %s", new Object[] { annotationType
/* 693 */               .value().getSimpleName(), type.getName() }));
/*     */     } 
/*     */     
/* 696 */     registry.put(entry.simpleName, entry);
/*     */     
/* 698 */     ArrayList<Class<? extends Annotation>> annotations = new ArrayList<>();
/* 699 */     for (InjectorEntry injector : registry.values()) {
/* 700 */       annotations.add(injector.annotationType);
/*     */     }
/* 702 */     registeredAnnotations = annotations.<Class<? extends Annotation>>toArray(registeredAnnotations);
/*     */   }
/*     */   
/*     */   public static Set<Class<? extends Annotation>> getRegisteredAnnotations() {
/* 706 */     return (Set<Class<? extends Annotation>>)ImmutableSet.copyOf((Object[])registeredAnnotations);
/*     */   }
/*     */   
/*     */   protected abstract Injector parseInjector(AnnotationNode paramAnnotationNode);
/*     */   
/*     */   @Retention(RetentionPolicy.RUNTIME)
/*     */   @Target({ElementType.TYPE})
/*     */   public static @interface HandlerPrefix {
/*     */     String value();
/*     */   }
/*     */   
/*     */   @Retention(RetentionPolicy.RUNTIME)
/*     */   @Target({ElementType.TYPE})
/*     */   public static @interface AnnotationType {
/*     */     Class<? extends Annotation> value();
/*     */   }
/*     */ }


/* Location:              C:\Users\DuduM\OneDrive\Área de Trabalho\mw10\modularwarfare-shining-2023.2.4.4f-fix10.jar!\org\spongepowered\asm\mixin\injection\struct\InjectionInfo.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */