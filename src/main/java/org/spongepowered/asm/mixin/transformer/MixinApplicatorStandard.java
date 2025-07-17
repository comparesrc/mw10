/*      */ package org.spongepowered.asm.mixin.transformer;
/*      */ 
/*      */ import com.google.common.collect.ImmutableList;
/*      */ import java.lang.annotation.Annotation;
/*      */ import java.util.ArrayDeque;
/*      */ import java.util.ArrayList;
/*      */ import java.util.Deque;
/*      */ import java.util.HashSet;
/*      */ import java.util.Iterator;
/*      */ import java.util.List;
/*      */ import java.util.Locale;
/*      */ import java.util.Map;
/*      */ import java.util.Set;
/*      */ import java.util.SortedSet;
/*      */ import org.apache.logging.log4j.LogManager;
/*      */ import org.apache.logging.log4j.Logger;
/*      */ import org.objectweb.asm.Label;
/*      */ import org.objectweb.asm.Type;
/*      */ import org.objectweb.asm.signature.SignatureReader;
/*      */ import org.objectweb.asm.signature.SignatureVisitor;
/*      */ import org.objectweb.asm.tree.AbstractInsnNode;
/*      */ import org.objectweb.asm.tree.AnnotationNode;
/*      */ import org.objectweb.asm.tree.ClassNode;
/*      */ import org.objectweb.asm.tree.FieldInsnNode;
/*      */ import org.objectweb.asm.tree.FieldNode;
/*      */ import org.objectweb.asm.tree.LabelNode;
/*      */ import org.objectweb.asm.tree.LineNumberNode;
/*      */ import org.objectweb.asm.tree.MethodInsnNode;
/*      */ import org.objectweb.asm.tree.MethodNode;
/*      */ import org.spongepowered.asm.mixin.Final;
/*      */ import org.spongepowered.asm.mixin.Intrinsic;
/*      */ import org.spongepowered.asm.mixin.MixinEnvironment;
/*      */ import org.spongepowered.asm.mixin.Overwrite;
/*      */ import org.spongepowered.asm.mixin.gen.Accessor;
/*      */ import org.spongepowered.asm.mixin.gen.Invoker;
/*      */ import org.spongepowered.asm.mixin.injection.Inject;
/*      */ import org.spongepowered.asm.mixin.injection.ModifyArg;
/*      */ import org.spongepowered.asm.mixin.injection.ModifyArgs;
/*      */ import org.spongepowered.asm.mixin.injection.ModifyConstant;
/*      */ import org.spongepowered.asm.mixin.injection.ModifyVariable;
/*      */ import org.spongepowered.asm.mixin.injection.Redirect;
/*      */ import org.spongepowered.asm.mixin.throwables.MixinError;
/*      */ import org.spongepowered.asm.mixin.transformer.ext.extensions.ExtensionClassExporter;
/*      */ import org.spongepowered.asm.mixin.transformer.meta.MixinMerged;
/*      */ import org.spongepowered.asm.mixin.transformer.meta.MixinRenamed;
/*      */ import org.spongepowered.asm.mixin.transformer.throwables.InvalidMixinException;
/*      */ import org.spongepowered.asm.mixin.transformer.throwables.MixinApplicatorException;
/*      */ import org.spongepowered.asm.service.IMixinAuditTrail;
/*      */ import org.spongepowered.asm.service.MixinService;
/*      */ import org.spongepowered.asm.util.Annotations;
/*      */ import org.spongepowered.asm.util.Bytecode;
/*      */ import org.spongepowered.asm.util.ConstraintParser;
/*      */ import org.spongepowered.asm.util.ITokenProvider;
/*      */ import org.spongepowered.asm.util.perf.Profiler;
/*      */ import org.spongepowered.asm.util.throwables.ConstraintViolationException;
/*      */ import org.spongepowered.asm.util.throwables.InvalidConstraintException;
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
/*      */ class MixinApplicatorStandard
/*      */ {
/*   83 */   protected static final List<Class<? extends Annotation>> CONSTRAINED_ANNOTATIONS = (List<Class<? extends Annotation>>)ImmutableList.of(Overwrite.class, Inject.class, ModifyArg.class, ModifyArgs.class, Redirect.class, ModifyVariable.class, ModifyConstant.class);
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
/*      */   enum ApplicatorPass
/*      */   {
/*  100 */     MAIN,
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  105 */     PREINJECT,
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  110 */     INJECT;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   enum InitialiserInjectionMode
/*      */   {
/*  121 */     DEFAULT,
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  127 */     SAFE;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   class Range
/*      */   {
/*      */     final int start;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     final int end;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     final int marker;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     Range(int start, int end, int marker) {
/*  157 */       this.start = start;
/*  158 */       this.end = end;
/*  159 */       this.marker = marker;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     boolean isValid() {
/*  169 */       return (this.start != 0 && this.end != 0 && this.end >= this.start);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     boolean contains(int value) {
/*  179 */       return (value >= this.start && value <= this.end);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     boolean excludes(int value) {
/*  188 */       return (value < this.start || value > this.end);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public String toString() {
/*  196 */       return String.format("Range[%d-%d,%d,valid=%s)", new Object[] { Integer.valueOf(this.start), Integer.valueOf(this.end), Integer.valueOf(this.marker), Boolean.valueOf(isValid()) });
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
/*  209 */   protected static final int[] INITIALISER_OPCODE_BLACKLIST = new int[] { 177, 21, 22, 23, 24, 46, 47, 48, 49, 50, 51, 52, 53, 54, 55, 56, 57, 58, 79, 80, 81, 82, 83, 84, 85, 86 };
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  219 */   protected final Logger logger = LogManager.getLogger("mixin");
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected final TargetClassContext context;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected final String targetName;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected final ClassNode targetClass;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected final ClassInfo targetClassInfo;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  244 */   protected final Profiler profiler = MixinEnvironment.getProfiler();
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected final IMixinAuditTrail auditTrail;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  254 */   protected final ActivityStack activities = new ActivityStack();
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected final boolean mergeSignatures;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   MixinApplicatorStandard(TargetClassContext context) {
/*  265 */     this.context = context;
/*  266 */     this.targetName = context.getClassName();
/*  267 */     this.targetClass = context.getClassNode();
/*  268 */     this.targetClassInfo = context.getClassInfo();
/*      */     
/*  270 */     ExtensionClassExporter exporter = (ExtensionClassExporter)context.getExtensions().getExtension(ExtensionClassExporter.class);
/*  271 */     this
/*  272 */       .mergeSignatures = (exporter.isDecompilerActive() && MixinEnvironment.getCurrentEnvironment().getOption(MixinEnvironment.Option.DEBUG_EXPORT_DECOMPILE_MERGESIGNATURES));
/*      */     
/*  274 */     this.auditTrail = MixinService.getService().getAuditTrail();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   final void apply(SortedSet<MixinInfo> mixins) {
/*  281 */     List<MixinTargetContext> mixinContexts = new ArrayList<>();
/*      */     
/*  283 */     for (Iterator<MixinInfo> iter = mixins.iterator(); iter.hasNext(); ) {
/*  284 */       MixinInfo mixin = iter.next();
/*      */       try {
/*  286 */         this.logger.log(mixin.getLoggingLevel(), "Mixing {} from {} into {}", new Object[] { mixin.getName(), mixin.getParent(), this.targetName });
/*  287 */         mixinContexts.add(mixin.createContextFor(this.context));
/*  288 */         if (this.auditTrail != null) {
/*  289 */           this.auditTrail.onApply(this.targetName, mixin.toString());
/*      */         }
/*  291 */       } catch (InvalidMixinException ex) {
/*  292 */         if (mixin.isRequired()) {
/*  293 */           throw ex;
/*      */         }
/*  295 */         this.context.addSuppressed(ex);
/*  296 */         iter.remove();
/*      */       } 
/*      */     } 
/*      */     
/*  300 */     MixinTargetContext current = null;
/*      */     
/*  302 */     this.activities.clear();
/*      */     try {
/*  304 */       ActivityStack.Activity activity = this.activities.begin("PreApply Phase");
/*  305 */       ActivityStack.Activity preApplyActivity = this.activities.begin("Mixin");
/*  306 */       for (MixinTargetContext context : mixinContexts) {
/*  307 */         preApplyActivity.next(context.toString());
/*  308 */         (current = context).preApply(this.targetName, this.targetClass);
/*      */       } 
/*  310 */       preApplyActivity.end();
/*      */       
/*  312 */       for (ApplicatorPass pass : ApplicatorPass.values()) {
/*  313 */         activity.next("%s Applicator Phase", new Object[] { pass });
/*  314 */         Profiler.Section timer = this.profiler.begin(new String[] { "pass", pass.name().toLowerCase(Locale.ROOT) });
/*  315 */         ActivityStack.Activity applyActivity = this.activities.begin("Mixin");
/*  316 */         for (Iterator<MixinTargetContext> iterator1 = mixinContexts.iterator(); iterator1.hasNext(); ) {
/*  317 */           current = iterator1.next();
/*  318 */           applyActivity.next(current.toString());
/*      */           try {
/*  320 */             applyMixin(current, pass);
/*  321 */           } catch (InvalidMixinException ex) {
/*  322 */             if (current.isRequired()) {
/*  323 */               throw ex;
/*      */             }
/*  325 */             this.context.addSuppressed(ex);
/*  326 */             iterator1.remove();
/*      */           } 
/*      */         } 
/*  329 */         applyActivity.end();
/*  330 */         timer.end();
/*      */       } 
/*      */       
/*  333 */       activity.next("PostApply Phase");
/*  334 */       ActivityStack.Activity postApplyActivity = this.activities.begin("Mixin");
/*  335 */       for (Iterator<MixinTargetContext> iterator = mixinContexts.iterator(); iterator.hasNext(); ) {
/*  336 */         current = iterator.next();
/*  337 */         postApplyActivity.next(current.toString());
/*      */         try {
/*  339 */           current.postApply(this.targetName, this.targetClass);
/*  340 */         } catch (InvalidMixinException ex) {
/*  341 */           if (current.isRequired()) {
/*  342 */             throw ex;
/*      */           }
/*  344 */           this.context.addSuppressed(ex);
/*  345 */           iterator.remove();
/*      */         } 
/*      */       } 
/*  348 */       activity.end();
/*  349 */     } catch (InvalidMixinException ex) {
/*  350 */       ex.prepend(this.activities);
/*  351 */       throw ex;
/*  352 */     } catch (Exception ex) {
/*  353 */       throw new MixinApplicatorException(current, "Unexpecteded " + ex.getClass().getSimpleName() + " whilst applying the mixin class:", ex, this.activities);
/*      */     } 
/*      */ 
/*      */     
/*  357 */     applySourceMap(this.context);
/*  358 */     this.context.processDebugTasks();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected final void applyMixin(MixinTargetContext mixin, ApplicatorPass pass) {
/*  367 */     ActivityStack.Activity activity = this.activities.begin("Apply");
/*  368 */     switch (pass) {
/*      */       case MAIN:
/*  370 */         activity.next("Apply Signature");
/*  371 */         applySignature(mixin);
/*  372 */         activity.next("Apply Interfaces");
/*  373 */         applyInterfaces(mixin);
/*  374 */         activity.next("Apply Attributess");
/*  375 */         applyAttributes(mixin);
/*  376 */         activity.next("Apply Annotations");
/*  377 */         applyAnnotations(mixin);
/*  378 */         activity.next("Apply Fields");
/*  379 */         applyFields(mixin);
/*  380 */         activity.next("Apply Methods");
/*  381 */         applyMethods(mixin);
/*  382 */         activity.next("Apply Initialisers");
/*  383 */         applyInitialisers(mixin);
/*      */         break;
/*      */       
/*      */       case PREINJECT:
/*  387 */         activity.next("Prepare Injections");
/*  388 */         prepareInjections(mixin);
/*      */         break;
/*      */       
/*      */       case INJECT:
/*  392 */         activity.next("Apply Accessors");
/*  393 */         applyAccessors(mixin);
/*  394 */         activity.next("Apply Injections");
/*  395 */         applyInjections(mixin);
/*      */         break;
/*      */ 
/*      */       
/*      */       default:
/*  400 */         throw new IllegalStateException("Invalid pass specified " + pass);
/*      */     } 
/*  402 */     activity.end();
/*      */   }
/*      */   
/*      */   protected void applySignature(MixinTargetContext mixin) {
/*  406 */     if (this.mergeSignatures) {
/*  407 */       this.context.mergeSignature(mixin.getSignature());
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void applyInterfaces(MixinTargetContext mixin) {
/*  417 */     for (String interfaceName : mixin.getInterfaces()) {
/*  418 */       if (!this.targetClass.interfaces.contains(interfaceName)) {
/*  419 */         this.targetClass.interfaces.add(interfaceName);
/*  420 */         this.targetClassInfo.addInterface(interfaceName);
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void applyAttributes(MixinTargetContext mixin) {
/*  431 */     if (mixin.shouldSetSourceFile()) {
/*  432 */       this.targetClass.sourceFile = mixin.getSourceFile();
/*      */     }
/*  434 */     this.targetClass.version = Math.max(this.targetClass.version, mixin.getMinRequiredClassVersion());
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void applyAnnotations(MixinTargetContext mixin) {
/*  443 */     ClassNode sourceClass = mixin.getClassNode();
/*  444 */     Annotations.merge(sourceClass, this.targetClass);
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
/*      */   protected void applyFields(MixinTargetContext mixin) {
/*  456 */     mergeShadowFields(mixin);
/*  457 */     mergeNewFields(mixin);
/*      */   }
/*      */   
/*      */   protected void mergeShadowFields(MixinTargetContext mixin) {
/*  461 */     for (Map.Entry<FieldNode, ClassInfo.Field> entry : mixin.getShadowFields()) {
/*  462 */       FieldNode shadow = entry.getKey();
/*  463 */       FieldNode target = findTargetField(shadow);
/*  464 */       if (target != null) {
/*  465 */         Annotations.merge(shadow, target);
/*      */ 
/*      */         
/*  468 */         if (((ClassInfo.Field)entry.getValue()).isDecoratedMutable()) {
/*  469 */           target.access &= 0xFFFFFFEF;
/*      */         }
/*      */       } 
/*      */     } 
/*      */   }
/*      */   
/*      */   protected void mergeNewFields(MixinTargetContext mixin) {
/*  476 */     for (FieldNode field : mixin.getFields()) {
/*  477 */       FieldNode target = findTargetField(field);
/*  478 */       if (target == null) {
/*      */         
/*  480 */         this.targetClass.fields.add(field);
/*      */         
/*  482 */         if (field.signature != null) {
/*  483 */           if (this.mergeSignatures) {
/*  484 */             SignatureVisitor sv = mixin.getSignature().getRemapper();
/*  485 */             (new SignatureReader(field.signature)).accept(sv);
/*  486 */             field.signature = sv.toString(); continue;
/*      */           } 
/*  488 */           field.signature = null;
/*      */         } 
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
/*      */   protected void applyMethods(MixinTargetContext mixin) {
/*  501 */     ActivityStack.Activity activity = this.activities.begin("?");
/*  502 */     for (MethodNode shadow : mixin.getShadowMethods()) {
/*  503 */       activity.next("@Shadow %s:%s", new Object[] { shadow.desc, shadow.name });
/*  504 */       applyShadowMethod(mixin, shadow);
/*      */     } 
/*      */     
/*  507 */     for (MethodNode mixinMethod : mixin.getMethods()) {
/*  508 */       activity.next("%s:%s", new Object[] { mixinMethod.desc, mixinMethod.name });
/*  509 */       applyNormalMethod(mixin, mixinMethod);
/*      */     } 
/*  511 */     activity.end();
/*      */   }
/*      */   
/*      */   protected void applyShadowMethod(MixinTargetContext mixin, MethodNode shadow) {
/*  515 */     MethodNode target = findTargetMethod(shadow);
/*  516 */     if (target != null) {
/*  517 */       Annotations.merge(shadow, target);
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   protected void applyNormalMethod(MixinTargetContext mixin, MethodNode mixinMethod) {
/*  523 */     mixin.transformMethod(mixinMethod);
/*      */     
/*  525 */     if (!mixinMethod.name.startsWith("<")) {
/*  526 */       checkMethodVisibility(mixin, mixinMethod);
/*  527 */       checkMethodConstraints(mixin, mixinMethod);
/*  528 */       mergeMethod(mixin, mixinMethod);
/*  529 */     } else if ("<clinit>".equals(mixinMethod.name)) {
/*      */       
/*  531 */       ActivityStack.Activity activity = this.activities.begin("Merge CLINIT insns");
/*  532 */       appendInsns(mixin, mixinMethod);
/*  533 */       activity.end();
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void mergeMethod(MixinTargetContext mixin, MethodNode method) {
/*  544 */     boolean isOverwrite = (Annotations.getVisible(method, Overwrite.class) != null);
/*  545 */     MethodNode target = findTargetMethod(method);
/*      */     
/*  547 */     if (target != null) {
/*  548 */       if (isAlreadyMerged(mixin, method, isOverwrite, target)) {
/*      */         return;
/*      */       }
/*      */       
/*  552 */       AnnotationNode intrinsic = Annotations.getInvisible(method, Intrinsic.class);
/*  553 */       if (intrinsic != null) {
/*  554 */         if (mergeIntrinsic(mixin, method, isOverwrite, target, intrinsic)) {
/*  555 */           mixin.getTarget().methodMerged(method);
/*      */           return;
/*      */         } 
/*      */       } else {
/*  559 */         if (mixin.requireOverwriteAnnotations() && !isOverwrite) {
/*  560 */           throw new InvalidMixinException(mixin, 
/*  561 */               String.format("%s%s in %s cannot overwrite method in %s because @Overwrite is required by the parent configuration", new Object[] {
/*  562 */                   method.name, method.desc, mixin, mixin.getTarget().getClassName()
/*      */                 }));
/*      */         }
/*  565 */         this.targetClass.methods.remove(target);
/*      */       } 
/*  567 */     } else if (isOverwrite) {
/*  568 */       throw new InvalidMixinException(mixin, String.format("Overwrite target \"%s\" was not located in target class %s", new Object[] { method.name, mixin
/*  569 */               .getTargetClassRef() }));
/*      */     } 
/*      */     
/*  572 */     this.targetClass.methods.add(method);
/*  573 */     mixin.methodMerged(method);
/*      */     
/*  575 */     if (method.signature != null) {
/*  576 */       if (this.mergeSignatures) {
/*  577 */         SignatureVisitor sv = mixin.getSignature().getRemapper();
/*  578 */         (new SignatureReader(method.signature)).accept(sv);
/*  579 */         method.signature = sv.toString();
/*      */       } else {
/*  581 */         method.signature = null;
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected boolean isAlreadyMerged(MixinTargetContext mixin, MethodNode method, boolean isOverwrite, MethodNode target) {
/*  599 */     AnnotationNode merged = Annotations.getVisible(target, MixinMerged.class);
/*  600 */     if (merged == null) {
/*  601 */       if (Annotations.getVisible(target, Final.class) != null) {
/*  602 */         this.logger.warn("Overwrite prohibited for @Final method {} in {}. Skipping method.", new Object[] { method.name, mixin });
/*  603 */         return true;
/*      */       } 
/*  605 */       return false;
/*      */     } 
/*      */     
/*  608 */     String sessionId = (String)Annotations.getValue(merged, "sessionId");
/*      */     
/*  610 */     if (!this.context.getSessionId().equals(sessionId)) {
/*  611 */       throw new ClassFormatError("Invalid @MixinMerged annotation found in" + mixin + " at " + method.name + " in " + this.targetClass.name);
/*      */     }
/*      */     
/*  614 */     if (Bytecode.hasFlag(target, 4160) && 
/*  615 */       Bytecode.hasFlag(method, 4160)) {
/*  616 */       if (mixin.getEnvironment().getOption(MixinEnvironment.Option.DEBUG_VERBOSE)) {
/*  617 */         this.logger.warn("Synthetic bridge method clash for {} in {}", new Object[] { method.name, mixin });
/*      */       }
/*  619 */       return true;
/*      */     } 
/*      */     
/*  622 */     String owner = (String)Annotations.getValue(merged, "mixin");
/*  623 */     int priority = ((Integer)Annotations.getValue(merged, "priority")).intValue();
/*      */     
/*  625 */     AnnotationNode accMethod = Annotations.getSingleVisible(method, new Class[] { Accessor.class, Invoker.class });
/*  626 */     if (accMethod != null) {
/*  627 */       AnnotationNode accTarget = Annotations.getSingleVisible(target, new Class[] { Accessor.class, Invoker.class });
/*  628 */       if (accTarget != null) {
/*  629 */         String myTarget = (String)Annotations.getValue(accMethod, "target");
/*  630 */         String trTarget = (String)Annotations.getValue(accTarget, "target");
/*  631 */         if (myTarget == null) {
/*  632 */           throw new MixinError("Encountered undecorated Accessor method in " + mixin + " applying to " + this.targetName);
/*      */         }
/*  634 */         if (myTarget.equals(trTarget))
/*      */         {
/*  636 */           return true;
/*      */         }
/*  638 */         throw new InvalidMixinException(mixin, String.format("Incompatible @%s %s (for %s) in %s previously written by %s (for %s)", new Object[] {
/*  639 */                 Bytecode.getSimpleName(accMethod), method.name, myTarget, mixin, owner, trTarget
/*      */               }));
/*      */       } 
/*      */     } 
/*  643 */     if (priority >= mixin.getPriority() && !owner.equals(mixin.getClassName())) {
/*  644 */       this.logger.warn("Method overwrite conflict for {} in {}, previously written by {}. Skipping method.", new Object[] { method.name, mixin, owner });
/*  645 */       return true;
/*      */     } 
/*      */     
/*  648 */     if (Annotations.getVisible(target, Final.class) != null) {
/*  649 */       this.logger.warn("Method overwrite conflict for @Final method {} in {} declared by {}. Skipping method.", new Object[] { method.name, mixin, owner });
/*  650 */       return true;
/*      */     } 
/*      */     
/*  653 */     return false;
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
/*      */   protected boolean mergeIntrinsic(MixinTargetContext mixin, MethodNode method, boolean isOverwrite, MethodNode target, AnnotationNode intrinsic) {
/*  672 */     if (isOverwrite) {
/*  673 */       throw new InvalidMixinException(mixin, "@Intrinsic is not compatible with @Overwrite, remove one of these annotations on " + method.name + " in " + mixin);
/*      */     }
/*      */ 
/*      */     
/*  677 */     String methodName = method.name + method.desc;
/*  678 */     if (Bytecode.hasFlag(method, 8)) {
/*  679 */       throw new InvalidMixinException(mixin, "@Intrinsic method cannot be static, found " + methodName + " in " + mixin);
/*      */     }
/*      */     
/*  682 */     if (!Bytecode.hasFlag(method, 4096)) {
/*  683 */       AnnotationNode renamed = Annotations.getVisible(method, MixinRenamed.class);
/*  684 */       if (renamed == null || !((Boolean)Annotations.getValue(renamed, "isInterfaceMember", Boolean.FALSE)).booleanValue()) {
/*  685 */         throw new InvalidMixinException(mixin, "@Intrinsic method must be prefixed interface method, no rename encountered on " + methodName + " in " + mixin);
/*      */       }
/*      */     } 
/*      */ 
/*      */     
/*  690 */     if (!((Boolean)Annotations.getValue(intrinsic, "displace", Boolean.FALSE)).booleanValue()) {
/*  691 */       this.logger.log(mixin.getLoggingLevel(), "Skipping Intrinsic mixin method {} for {}", new Object[] { methodName, mixin.getTargetClassRef() });
/*  692 */       return true;
/*      */     } 
/*      */     
/*  695 */     displaceIntrinsic(mixin, method, target);
/*  696 */     return false;
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
/*      */   protected void displaceIntrinsic(MixinTargetContext mixin, MethodNode method, MethodNode target) {
/*  709 */     String proxyName = "proxy+" + target.name;
/*      */     
/*  711 */     for (Iterator<AbstractInsnNode> iter = method.instructions.iterator(); iter.hasNext(); ) {
/*  712 */       AbstractInsnNode insn = iter.next();
/*  713 */       if (insn instanceof MethodInsnNode && insn.getOpcode() != 184) {
/*  714 */         MethodInsnNode methodNode = (MethodInsnNode)insn;
/*  715 */         if (methodNode.owner.equals(this.targetClass.name) && methodNode.name.equals(target.name) && methodNode.desc.equals(target.desc)) {
/*  716 */           methodNode.name = proxyName;
/*      */         }
/*      */       } 
/*      */     } 
/*      */     
/*  721 */     target.name = proxyName;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected final void appendInsns(MixinTargetContext mixin, MethodNode method) {
/*  732 */     if (Type.getReturnType(method.desc) != Type.VOID_TYPE) {
/*  733 */       throw new IllegalArgumentException("Attempted to merge insns from a method which does not return void");
/*      */     }
/*      */     
/*  736 */     MethodNode target = findTargetMethod(method);
/*      */     
/*  738 */     if (target != null) {
/*  739 */       AbstractInsnNode returnNode = Bytecode.findInsn(target, 177);
/*      */       
/*  741 */       if (returnNode != null) {
/*  742 */         Iterator<AbstractInsnNode> injectIter = method.instructions.iterator();
/*  743 */         while (injectIter.hasNext()) {
/*  744 */           AbstractInsnNode insn = injectIter.next();
/*  745 */           if (!(insn instanceof LineNumberNode) && insn.getOpcode() != 177) {
/*  746 */             target.instructions.insertBefore(returnNode, insn);
/*      */           }
/*      */         } 
/*      */         
/*  750 */         target.maxLocals = Math.max(target.maxLocals, method.maxLocals);
/*  751 */         target.maxStack = Math.max(target.maxStack, method.maxStack);
/*      */       } 
/*      */       
/*      */       return;
/*      */     } 
/*      */     
/*  757 */     this.targetClass.methods.add(method);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void applyInitialisers(MixinTargetContext mixin) {
/*  768 */     MethodNode ctor = getConstructor(mixin);
/*  769 */     if (ctor == null) {
/*      */       return;
/*      */     }
/*      */ 
/*      */     
/*  774 */     Deque<AbstractInsnNode> initialiser = getInitialiser(mixin, ctor);
/*  775 */     if (initialiser == null || initialiser.size() == 0) {
/*      */       return;
/*      */     }
/*      */     
/*  779 */     String superName = this.context.getClassInfo().getSuperName();
/*      */ 
/*      */     
/*  782 */     for (MethodNode method : this.targetClass.methods) {
/*  783 */       if ("<init>".equals(method.name)) {
/*  784 */         Bytecode.DelegateInitialiser superCall = Bytecode.findDelegateInit(method, superName, this.targetClass.name);
/*  785 */         if (!superCall.isPresent || superCall.isSuper) {
/*  786 */           method.maxStack = Math.max(method.maxStack, ctor.maxStack);
/*  787 */           injectInitialiser(mixin, method, initialiser);
/*      */         } 
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
/*      */   protected MethodNode getConstructor(MixinTargetContext mixin) {
/*  800 */     MethodNode ctor = null;
/*      */     
/*  802 */     for (MethodNode mixinMethod : mixin.getMethods()) {
/*  803 */       if ("<init>".equals(mixinMethod.name) && Bytecode.methodHasLineNumbers(mixinMethod)) {
/*  804 */         if (ctor == null) {
/*  805 */           ctor = mixinMethod;
/*      */           continue;
/*      */         } 
/*  808 */         this.logger.warn(String.format("Mixin %s has multiple constructors, %s was selected\n", new Object[] { mixin, ctor.desc }));
/*      */       } 
/*      */     } 
/*      */ 
/*      */     
/*  813 */     return ctor;
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
/*      */   private Range getConstructorRange(MethodNode ctor) {
/*  825 */     boolean lineNumberIsValid = false;
/*  826 */     AbstractInsnNode endReturn = null;
/*      */     
/*  828 */     int line = 0, start = 0, end = 0, superIndex = -1;
/*  829 */     for (Iterator<AbstractInsnNode> iter = ctor.instructions.iterator(); iter.hasNext(); ) {
/*  830 */       AbstractInsnNode insn = iter.next();
/*  831 */       if (insn instanceof LineNumberNode) {
/*  832 */         line = ((LineNumberNode)insn).line;
/*  833 */         lineNumberIsValid = true; continue;
/*  834 */       }  if (insn instanceof MethodInsnNode) {
/*  835 */         if (insn.getOpcode() == 183 && "<init>".equals(((MethodInsnNode)insn).name) && superIndex == -1) {
/*  836 */           superIndex = ctor.instructions.indexOf(insn);
/*  837 */           start = line;
/*      */         }  continue;
/*  839 */       }  if (insn.getOpcode() == 181) {
/*  840 */         lineNumberIsValid = false; continue;
/*  841 */       }  if (insn.getOpcode() == 177) {
/*  842 */         if (lineNumberIsValid) {
/*  843 */           end = line; continue;
/*      */         } 
/*  845 */         end = start;
/*  846 */         endReturn = insn;
/*      */       } 
/*      */     } 
/*      */ 
/*      */     
/*  851 */     if (endReturn != null) {
/*  852 */       LabelNode label = new LabelNode(new Label());
/*  853 */       ctor.instructions.insertBefore(endReturn, (AbstractInsnNode)label);
/*  854 */       ctor.instructions.insertBefore(endReturn, (AbstractInsnNode)new LineNumberNode(start, label));
/*      */     } 
/*      */     
/*  857 */     return new Range(start, end, superIndex);
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
/*      */   protected final Deque<AbstractInsnNode> getInitialiser(MixinTargetContext mixin, MethodNode ctor) {
/*  875 */     Range init = getConstructorRange(ctor);
/*  876 */     if (!init.isValid()) {
/*  877 */       return null;
/*      */     }
/*      */ 
/*      */     
/*  881 */     int line = 0;
/*  882 */     Deque<AbstractInsnNode> initialiser = new ArrayDeque<>();
/*  883 */     boolean gatherNodes = false;
/*  884 */     int trimAtOpcode = -1;
/*  885 */     LabelNode optionalInsn = null;
/*  886 */     for (Iterator<AbstractInsnNode> iter = ctor.instructions.iterator(init.marker); iter.hasNext(); ) {
/*  887 */       AbstractInsnNode insn = iter.next();
/*  888 */       if (insn instanceof LineNumberNode) {
/*  889 */         line = ((LineNumberNode)insn).line;
/*  890 */         AbstractInsnNode next = ctor.instructions.get(ctor.instructions.indexOf(insn) + 1);
/*  891 */         if (line == init.end && next.getOpcode() != 177) {
/*  892 */           gatherNodes = true;
/*  893 */           trimAtOpcode = 177; continue;
/*      */         } 
/*  895 */         gatherNodes = init.excludes(line);
/*  896 */         trimAtOpcode = -1; continue;
/*      */       } 
/*  898 */       if (gatherNodes) {
/*  899 */         if (optionalInsn != null) {
/*  900 */           initialiser.add(optionalInsn);
/*  901 */           optionalInsn = null;
/*      */         } 
/*      */         
/*  904 */         if (insn instanceof LabelNode) {
/*  905 */           optionalInsn = (LabelNode)insn; continue;
/*      */         } 
/*  907 */         int opcode = insn.getOpcode();
/*  908 */         if (opcode == trimAtOpcode) {
/*  909 */           trimAtOpcode = -1;
/*      */           continue;
/*      */         } 
/*  912 */         for (int ivalidOp : INITIALISER_OPCODE_BLACKLIST) {
/*  913 */           if (opcode == ivalidOp)
/*      */           {
/*      */             
/*  916 */             throw new InvalidMixinException(mixin, "Cannot handle " + Bytecode.getOpcodeName(opcode) + " opcode (0x" + 
/*  917 */                 Integer.toHexString(opcode).toUpperCase(Locale.ROOT) + ") in class initialiser");
/*      */           }
/*      */         } 
/*      */         
/*  921 */         initialiser.add(insn);
/*      */       } 
/*      */     } 
/*      */ 
/*      */ 
/*      */     
/*  927 */     AbstractInsnNode last = initialiser.peekLast();
/*  928 */     if (last != null && 
/*  929 */       last.getOpcode() != 181) {
/*  930 */       throw new InvalidMixinException(mixin, "Could not parse initialiser, expected 0xB5, found 0x" + 
/*  931 */           Integer.toHexString(last.getOpcode()) + " in " + mixin);
/*      */     }
/*      */ 
/*      */     
/*  935 */     return initialiser;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected final void injectInitialiser(MixinTargetContext mixin, MethodNode ctor, Deque<AbstractInsnNode> initialiser) {
/*  946 */     Map<LabelNode, LabelNode> labels = Bytecode.cloneLabels(ctor.instructions);
/*      */     
/*  948 */     AbstractInsnNode insn = findInitialiserInjectionPoint(mixin, ctor, initialiser);
/*  949 */     if (insn == null) {
/*  950 */       this.logger.warn("Failed to locate initialiser injection point in <init>{}, initialiser was not mixed in.", new Object[] { ctor.desc });
/*      */       
/*      */       return;
/*      */     } 
/*  954 */     for (AbstractInsnNode node : initialiser) {
/*  955 */       if (node instanceof LabelNode) {
/*      */         continue;
/*      */       }
/*  958 */       if (node instanceof org.objectweb.asm.tree.JumpInsnNode) {
/*  959 */         throw new InvalidMixinException(mixin, "Unsupported JUMP opcode in initialiser in " + mixin);
/*      */       }
/*  961 */       AbstractInsnNode imACloneNow = node.clone(labels);
/*  962 */       ctor.instructions.insert(insn, imACloneNow);
/*  963 */       insn = imACloneNow;
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
/*      */   protected AbstractInsnNode findInitialiserInjectionPoint(MixinTargetContext mixin, MethodNode ctor, Deque<AbstractInsnNode> initialiser) {
/*  977 */     Set<String> initialisedFields = new HashSet<>();
/*  978 */     for (AbstractInsnNode initialiserInsn : initialiser) {
/*  979 */       if (initialiserInsn.getOpcode() == 181) {
/*  980 */         initialisedFields.add(fieldKey((FieldInsnNode)initialiserInsn));
/*      */       }
/*      */     } 
/*      */     
/*  984 */     InitialiserInjectionMode mode = getInitialiserInjectionMode(mixin.getEnvironment());
/*  985 */     String targetName = this.targetClassInfo.getName();
/*  986 */     String targetSuperName = this.targetClassInfo.getSuperName();
/*  987 */     AbstractInsnNode targetInsn = null;
/*      */     
/*  989 */     for (Iterator<AbstractInsnNode> iter = ctor.instructions.iterator(); iter.hasNext(); ) {
/*  990 */       AbstractInsnNode insn = iter.next();
/*  991 */       if (insn.getOpcode() == 183 && "<init>".equals(((MethodInsnNode)insn).name)) {
/*  992 */         String owner = ((MethodInsnNode)insn).owner;
/*  993 */         if (owner.equals(targetName) || owner.equals(targetSuperName)) {
/*  994 */           targetInsn = insn;
/*  995 */           if (mode == InitialiserInjectionMode.SAFE)
/*      */             break; 
/*      */         }  continue;
/*      */       } 
/*  999 */       if (insn.getOpcode() == 181 && mode == InitialiserInjectionMode.DEFAULT) {
/* 1000 */         String key = fieldKey((FieldInsnNode)insn);
/* 1001 */         if (initialisedFields.contains(key)) {
/* 1002 */           targetInsn = insn;
/*      */         }
/*      */       } 
/*      */     } 
/*      */     
/* 1007 */     return targetInsn;
/*      */   }
/*      */   
/*      */   private InitialiserInjectionMode getInitialiserInjectionMode(MixinEnvironment environment) {
/* 1011 */     String strMode = environment.getOptionValue(MixinEnvironment.Option.INITIALISER_INJECTION_MODE);
/* 1012 */     if (strMode == null) {
/* 1013 */       return InitialiserInjectionMode.DEFAULT;
/*      */     }
/*      */     try {
/* 1016 */       return InitialiserInjectionMode.valueOf(strMode.toUpperCase(Locale.ROOT));
/* 1017 */     } catch (Exception ex) {
/* 1018 */       this.logger.warn("Could not parse unexpected value \"{}\" for mixin.initialiserInjectionMode, reverting to DEFAULT", new Object[] { strMode });
/* 1019 */       return InitialiserInjectionMode.DEFAULT;
/*      */     } 
/*      */   }
/*      */   
/*      */   private static String fieldKey(FieldInsnNode fieldNode) {
/* 1024 */     return String.format("%s:%s", new Object[] { fieldNode.desc, fieldNode.name });
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void prepareInjections(MixinTargetContext mixin) {
/* 1033 */     mixin.prepareInjections();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void applyInjections(MixinTargetContext mixin) {
/* 1042 */     mixin.applyInjections();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void applyAccessors(MixinTargetContext mixin) {
/* 1051 */     List<MethodNode> accessorMethods = mixin.generateAccessors();
/* 1052 */     for (MethodNode method : accessorMethods) {
/* 1053 */       if (!method.name.startsWith("<")) {
/* 1054 */         mergeMethod(mixin, method);
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
/*      */   protected void checkMethodVisibility(MixinTargetContext mixin, MethodNode mixinMethod) {
/* 1066 */     if (Bytecode.hasFlag(mixinMethod, 8) && 
/* 1067 */       !Bytecode.hasFlag(mixinMethod, 2) && 
/* 1068 */       !Bytecode.hasFlag(mixinMethod, 4096) && 
/* 1069 */       Annotations.getVisible(mixinMethod, Overwrite.class) == null) {
/* 1070 */       throw new InvalidMixinException(mixin, 
/* 1071 */           String.format("Mixin %s contains non-private static method %s", new Object[] { mixin, mixinMethod }));
/*      */     }
/*      */   }
/*      */   
/*      */   protected void applySourceMap(TargetClassContext context) {
/* 1076 */     this.targetClass.sourceDebug = context.getSourceMap().toString();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void checkMethodConstraints(MixinTargetContext mixin, MethodNode method) {
/* 1086 */     for (Class<? extends Annotation> annotationType : CONSTRAINED_ANNOTATIONS) {
/* 1087 */       AnnotationNode annotation = Annotations.getVisible(method, annotationType);
/* 1088 */       if (annotation != null) {
/* 1089 */         checkConstraints(mixin, method, annotation);
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
/*      */ 
/*      */   
/*      */   protected final void checkConstraints(MixinTargetContext mixin, MethodNode method, AnnotationNode annotation) {
/*      */     try {
/* 1104 */       ConstraintParser.Constraint constraint = ConstraintParser.parse(annotation);
/*      */       try {
/* 1106 */         constraint.check((ITokenProvider)mixin.getEnvironment());
/* 1107 */       } catch (ConstraintViolationException ex) {
/* 1108 */         String message = String.format("Constraint violation: %s on %s in %s", new Object[] { ex.getMessage(), method, mixin });
/* 1109 */         this.logger.warn(message);
/* 1110 */         if (!mixin.getEnvironment().getOption(MixinEnvironment.Option.IGNORE_CONSTRAINTS)) {
/* 1111 */           throw new InvalidMixinException(mixin, message, ex);
/*      */         }
/*      */       } 
/* 1114 */     } catch (InvalidConstraintException ex) {
/* 1115 */       throw new InvalidMixinException(mixin, ex.getMessage());
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected final MethodNode findTargetMethod(MethodNode searchFor) {
/* 1126 */     for (MethodNode target : this.targetClass.methods) {
/* 1127 */       if (target.name.equals(searchFor.name) && target.desc.equals(searchFor.desc)) {
/* 1128 */         return target;
/*      */       }
/*      */     } 
/*      */     
/* 1132 */     return null;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected final FieldNode findTargetField(FieldNode searchFor) {
/* 1142 */     for (FieldNode target : this.targetClass.fields) {
/* 1143 */       if (target.name.equals(searchFor.name)) {
/* 1144 */         return target;
/*      */       }
/*      */     } 
/*      */     
/* 1148 */     return null;
/*      */   }
/*      */ }


/* Location:              C:\Users\DuduM\OneDrive\Área de Trabalho\mw10\modularwarfare-shining-2023.2.4.4f-fix10.jar!\org\spongepowered\asm\mixin\transformer\MixinApplicatorStandard.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */