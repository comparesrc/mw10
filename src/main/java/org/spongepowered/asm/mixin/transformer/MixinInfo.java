/*      */ package org.spongepowered.asm.mixin.transformer;
/*      */ 
/*      */ import com.google.common.base.Functions;
/*      */ import com.google.common.collect.Iterables;
/*      */ import com.google.common.collect.Lists;
/*      */ import java.io.IOException;
/*      */ import java.lang.annotation.Annotation;
/*      */ import java.util.ArrayList;
/*      */ import java.util.Collection;
/*      */ import java.util.Collections;
/*      */ import java.util.HashSet;
/*      */ import java.util.List;
/*      */ import java.util.Set;
/*      */ import org.apache.logging.log4j.Level;
/*      */ import org.apache.logging.log4j.LogManager;
/*      */ import org.apache.logging.log4j.Logger;
/*      */ import org.objectweb.asm.ClassVisitor;
/*      */ import org.objectweb.asm.Handle;
/*      */ import org.objectweb.asm.MethodVisitor;
/*      */ import org.objectweb.asm.Type;
/*      */ import org.objectweb.asm.tree.AbstractInsnNode;
/*      */ import org.objectweb.asm.tree.AnnotationNode;
/*      */ import org.objectweb.asm.tree.ClassNode;
/*      */ import org.objectweb.asm.tree.FieldNode;
/*      */ import org.objectweb.asm.tree.InnerClassNode;
/*      */ import org.objectweb.asm.tree.InvokeDynamicInsnNode;
/*      */ import org.objectweb.asm.tree.MethodNode;
/*      */ import org.spongepowered.asm.mixin.Implements;
/*      */ import org.spongepowered.asm.mixin.Mixin;
/*      */ import org.spongepowered.asm.mixin.MixinEnvironment;
/*      */ import org.spongepowered.asm.mixin.Overwrite;
/*      */ import org.spongepowered.asm.mixin.Pseudo;
/*      */ import org.spongepowered.asm.mixin.Shadow;
/*      */ import org.spongepowered.asm.mixin.Unique;
/*      */ import org.spongepowered.asm.mixin.extensibility.IMixinConfig;
/*      */ import org.spongepowered.asm.mixin.extensibility.IMixinInfo;
/*      */ import org.spongepowered.asm.mixin.injection.Surrogate;
/*      */ import org.spongepowered.asm.mixin.injection.struct.InjectionInfo;
/*      */ import org.spongepowered.asm.mixin.transformer.throwables.InvalidMixinException;
/*      */ import org.spongepowered.asm.mixin.transformer.throwables.MixinReloadException;
/*      */ import org.spongepowered.asm.mixin.transformer.throwables.MixinTargetAlreadyLoadedException;
/*      */ import org.spongepowered.asm.service.IClassTracker;
/*      */ import org.spongepowered.asm.service.IMixinService;
/*      */ import org.spongepowered.asm.util.Annotations;
/*      */ import org.spongepowered.asm.util.Bytecode;
/*      */ import org.spongepowered.asm.util.asm.ASM;
/*      */ import org.spongepowered.asm.util.asm.MethodNodeEx;
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
/*      */ 
/*      */ class MixinInfo
/*      */   implements Comparable<MixinInfo>, IMixinInfo
/*      */ {
/*      */   enum Variant
/*      */   {
/*   95 */     STANDARD,
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  100 */     INTERFACE,
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  105 */     ACCESSOR,
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  110 */     PROXY;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   class MixinMethodNode
/*      */     extends MethodNodeEx
/*      */   {
/*      */     public MixinMethodNode(int access, String name, String desc, String signature, String[] exceptions) {
/*  120 */       super(access, name, desc, signature, exceptions, MixinInfo.this);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public void visitInvokeDynamicInsn(String name, String descriptor, Handle bootstrapMethodHandle, Object... bootstrapMethodArguments) {
/*  129 */       Object[] bsmArgs = new Object[bootstrapMethodArguments.length];
/*  130 */       System.arraycopy(bootstrapMethodArguments, 0, bsmArgs, 0, bootstrapMethodArguments.length);
/*  131 */       this.instructions.add((AbstractInsnNode)new InvokeDynamicInsnNode(name, descriptor, bootstrapMethodHandle, bsmArgs));
/*      */     }
/*      */     
/*      */     public boolean isInjector() {
/*  135 */       return (getInjectorAnnotation() != null || isSurrogate());
/*      */     }
/*      */     
/*      */     public boolean isSurrogate() {
/*  139 */       return (getVisibleAnnotation((Class)Surrogate.class) != null);
/*      */     }
/*      */     
/*      */     public boolean isSynthetic() {
/*  143 */       return Bytecode.hasFlag((MethodNode)this, 4096);
/*      */     }
/*      */     
/*      */     public AnnotationNode getVisibleAnnotation(Class<? extends Annotation> annotationClass) {
/*  147 */       return Annotations.getVisible((MethodNode)this, annotationClass);
/*      */     }
/*      */     
/*      */     public AnnotationNode getInjectorAnnotation() {
/*  151 */       return InjectionInfo.getInjectorAnnotation(MixinInfo.this, (MethodNode)this);
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   class MixinClassNode
/*      */     extends ClassNode
/*      */   {
/*      */     public final List<MixinInfo.MixinMethodNode> mixinMethods;
/*      */ 
/*      */     
/*      */     MixinClassNode(MixinInfo mixin) {
/*  164 */       this(ASM.API_VERSION);
/*      */     }
/*      */ 
/*      */     
/*      */     protected MixinClassNode(int api) {
/*  169 */       super(api);
/*  170 */       this.mixinMethods = this.methods;
/*      */     }
/*      */     
/*      */     public MixinInfo getMixin() {
/*  174 */       return MixinInfo.this;
/*      */     }
/*      */     
/*      */     public List<FieldNode> getFields() {
/*  178 */       return new ArrayList<>(this.fields);
/*      */     }
/*      */ 
/*      */     
/*      */     public MethodVisitor visitMethod(int access, String name, String desc, String signature, String[] exceptions) {
/*  183 */       MixinInfo.MixinMethodNode mixinMethodNode = new MixinInfo.MixinMethodNode(access, name, desc, signature, exceptions);
/*  184 */       this.methods.add(mixinMethodNode);
/*  185 */       return (MethodVisitor)mixinMethodNode;
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   class State
/*      */   {
/*      */     private final ClassNode classNode;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     private final ClassInfo classInfo;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     private boolean detachedSuper;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     private boolean unique;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  220 */     protected final Set<String> interfaces = new HashSet<>();
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  225 */     protected final List<InterfaceInfo> softImplements = new ArrayList<>();
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  230 */     protected final Set<String> syntheticInnerClasses = new HashSet<>();
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  235 */     protected final Set<String> innerClasses = new HashSet<>();
/*      */ 
/*      */ 
/*      */     
/*      */     protected MixinInfo.MixinClassNode validationClassNode;
/*      */ 
/*      */ 
/*      */     
/*      */     State(ClassNode classNode) {
/*  244 */       this(classNode, null);
/*      */     }
/*      */     
/*      */     State(ClassNode classNode, ClassInfo classInfo) {
/*  248 */       this.classNode = classNode;
/*  249 */       connect();
/*  250 */       this.classInfo = (classInfo != null) ? classInfo : ClassInfo.fromClassNode(getValidationClassNode());
/*      */     }
/*      */     
/*      */     protected void connect() {
/*  254 */       this.validationClassNode = createClassNode(0);
/*      */     }
/*      */     
/*      */     protected void complete() {
/*  258 */       this.validationClassNode = null;
/*      */     }
/*      */     
/*      */     ClassInfo getClassInfo() {
/*  262 */       return this.classInfo;
/*      */     }
/*      */     
/*      */     ClassNode getClassNode() {
/*  266 */       return this.classNode;
/*      */     }
/*      */     
/*      */     MixinInfo.MixinClassNode getValidationClassNode() {
/*  270 */       if (this.validationClassNode == null) {
/*  271 */         throw new IllegalStateException("Attempted a validation task after validation is complete on " + this + " in " + MixinInfo.this);
/*      */       }
/*  273 */       return this.validationClassNode;
/*      */     }
/*      */     
/*      */     boolean isDetachedSuper() {
/*  277 */       return this.detachedSuper;
/*      */     }
/*      */     
/*      */     boolean isUnique() {
/*  281 */       return this.unique;
/*      */     }
/*      */     
/*      */     List<? extends InterfaceInfo> getSoftImplements() {
/*  285 */       return this.softImplements;
/*      */     }
/*      */     
/*      */     Set<String> getSyntheticInnerClasses() {
/*  289 */       return this.syntheticInnerClasses;
/*      */     }
/*      */     
/*      */     Set<String> getInnerClasses() {
/*  293 */       return this.innerClasses;
/*      */     }
/*      */     
/*      */     Set<String> getInterfaces() {
/*  297 */       return this.interfaces;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     MixinInfo.MixinClassNode createClassNode(int flags) {
/*  307 */       MixinInfo.MixinClassNode mixinClassNode = new MixinInfo.MixinClassNode(MixinInfo.this);
/*  308 */       this.classNode.accept((ClassVisitor)mixinClassNode);
/*  309 */       return mixinClassNode;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     void validate(MixinInfo.SubType type, List<ClassInfo> targetClasses) {
/*  319 */       MixinInfo.MixinClassNode classNode = getValidationClassNode();
/*  320 */       MixinPreProcessorStandard preProcessor = type.createPreProcessor(classNode).prepare();
/*  321 */       for (ClassInfo target : targetClasses) {
/*  322 */         preProcessor.conform(target);
/*      */       }
/*      */       
/*  325 */       type.validate(this, targetClasses);
/*      */       
/*  327 */       this.detachedSuper = type.isDetachedSuper();
/*  328 */       this.unique = (Annotations.getVisible(classNode, Unique.class) != null);
/*      */ 
/*      */       
/*  331 */       validateInner();
/*  332 */       validateClassVersion();
/*  333 */       validateRemappables(targetClasses);
/*      */ 
/*      */       
/*  336 */       readImplementations(type);
/*  337 */       readInnerClasses();
/*      */ 
/*      */       
/*  340 */       validateChanges(type, targetClasses);
/*      */ 
/*      */       
/*  343 */       complete();
/*      */     }
/*      */ 
/*      */     
/*      */     private void validateInner() {
/*  348 */       if (!this.classInfo.isProbablyStatic()) {
/*  349 */         throw new InvalidMixinException(MixinInfo.this, "Inner class mixin must be declared static");
/*      */       }
/*      */     }
/*      */     
/*      */     private void validateClassVersion() {
/*  354 */       if (this.validationClassNode.version > MixinEnvironment.getCompatibilityLevel().classVersion()) {
/*  355 */         String helpText = ".";
/*  356 */         for (MixinEnvironment.CompatibilityLevel level : MixinEnvironment.CompatibilityLevel.values()) {
/*  357 */           if (level.classVersion() >= this.validationClassNode.version) {
/*  358 */             helpText = String.format(". Mixin requires compatibility level %s or above.", new Object[] { level.name() });
/*      */           }
/*      */         } 
/*      */         
/*  362 */         throw new InvalidMixinException(MixinInfo.this, "Unsupported mixin class version " + this.validationClassNode.version + helpText);
/*      */       } 
/*      */     }
/*      */ 
/*      */     
/*      */     private void validateRemappables(List<ClassInfo> targetClasses) {
/*  368 */       if (targetClasses.size() > 1) {
/*  369 */         for (FieldNode field : this.validationClassNode.fields) {
/*  370 */           validateRemappable(Shadow.class, field.name, Annotations.getVisible(field, Shadow.class));
/*      */         }
/*      */         
/*  373 */         for (MethodNode method : this.validationClassNode.methods) {
/*  374 */           validateRemappable(Shadow.class, method.name, Annotations.getVisible(method, Shadow.class));
/*  375 */           AnnotationNode overwrite = Annotations.getVisible(method, Overwrite.class);
/*  376 */           if (overwrite != null && ((method.access & 0x8) == 0 || (method.access & 0x1) == 0)) {
/*  377 */             throw new InvalidMixinException(MixinInfo.this, "Found @Overwrite annotation on " + method.name + " in " + MixinInfo.this);
/*      */           }
/*      */         } 
/*      */       } 
/*      */     }
/*      */     
/*      */     private void validateRemappable(Class<Shadow> annotationClass, String name, AnnotationNode annotation) {
/*  384 */       if (annotation != null && ((Boolean)Annotations.getValue(annotation, "remap", Boolean.TRUE)).booleanValue()) {
/*  385 */         throw new InvalidMixinException(MixinInfo.this, "Found a remappable @" + annotationClass.getSimpleName() + " annotation on " + name + " in " + this);
/*      */       }
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     void readImplementations(MixinInfo.SubType type) {
/*  394 */       this.interfaces.addAll(this.validationClassNode.interfaces);
/*  395 */       this.interfaces.addAll(type.getInterfaces());
/*      */       
/*  397 */       AnnotationNode implementsAnnotation = Annotations.getInvisible(this.validationClassNode, Implements.class);
/*  398 */       if (implementsAnnotation == null) {
/*      */         return;
/*      */       }
/*      */       
/*  402 */       List<AnnotationNode> interfaces = (List<AnnotationNode>)Annotations.getValue(implementsAnnotation);
/*  403 */       if (interfaces == null) {
/*      */         return;
/*      */       }
/*      */       
/*  407 */       for (AnnotationNode interfaceNode : interfaces) {
/*  408 */         InterfaceInfo interfaceInfo = InterfaceInfo.fromAnnotation(MixinInfo.this, interfaceNode);
/*  409 */         this.softImplements.add(interfaceInfo);
/*  410 */         this.interfaces.add(interfaceInfo.getInternalName());
/*      */         
/*  412 */         if (!(this instanceof MixinInfo.Reloaded)) {
/*  413 */           this.classInfo.addInterface(interfaceInfo.getInternalName());
/*      */         }
/*      */       } 
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     void readInnerClasses() {
/*  424 */       for (InnerClassNode inner : this.validationClassNode.innerClasses) {
/*  425 */         ClassInfo innerClass = ClassInfo.forName(inner.name);
/*  426 */         if ((inner.outerName != null && inner.outerName.equals(this.classInfo.getName())) || inner.name
/*  427 */           .startsWith(this.validationClassNode.name + "$")) {
/*  428 */           if (innerClass.isProbablyStatic() && innerClass.isSynthetic()) {
/*  429 */             this.syntheticInnerClasses.add(inner.name); continue;
/*      */           } 
/*  431 */           this.innerClasses.add(inner.name);
/*      */         } 
/*      */       } 
/*      */     }
/*      */ 
/*      */     
/*      */     protected void validateChanges(MixinInfo.SubType type, List<ClassInfo> targetClasses) {
/*  438 */       type.createPreProcessor(this.validationClassNode).prepare();
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   class Reloaded
/*      */     extends State
/*      */   {
/*      */     private final MixinInfo.State previous;
/*      */ 
/*      */ 
/*      */     
/*      */     Reloaded(MixinInfo.State previous, ClassNode classNode) {
/*  453 */       super(classNode, previous.getClassInfo());
/*  454 */       this.previous = previous;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     protected void validateChanges(MixinInfo.SubType type, List<ClassInfo> targetClasses) {
/*  463 */       if (!this.syntheticInnerClasses.equals(this.previous.syntheticInnerClasses)) {
/*  464 */         throw new MixinReloadException(MixinInfo.this, "Cannot change inner classes");
/*      */       }
/*  466 */       if (!this.interfaces.equals(this.previous.interfaces)) {
/*  467 */         throw new MixinReloadException(MixinInfo.this, "Cannot change interfaces");
/*      */       }
/*  469 */       if (!(new HashSet(this.softImplements)).equals(new HashSet<>(this.previous.softImplements))) {
/*  470 */         throw new MixinReloadException(MixinInfo.this, "Cannot change soft interfaces");
/*      */       }
/*  472 */       List<ClassInfo> targets = MixinInfo.this.readTargetClasses(this.validationClassNode, true);
/*  473 */       if (!(new HashSet(targets)).equals(new HashSet<>(targetClasses))) {
/*  474 */         throw new MixinReloadException(MixinInfo.this, "Cannot change target classes");
/*      */       }
/*  476 */       int priority = MixinInfo.this.readPriority(this.validationClassNode);
/*  477 */       if (priority != MixinInfo.this.getPriority()) {
/*  478 */         throw new MixinReloadException(MixinInfo.this, "Cannot change mixin priority");
/*      */       }
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   static abstract class SubType
/*      */   {
/*      */     protected final MixinInfo mixin;
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     protected final String annotationType;
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     protected final boolean targetMustBeInterface;
/*      */ 
/*      */ 
/*      */     
/*      */     protected boolean detached;
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     SubType(MixinInfo info, String annotationType, boolean targetMustBeInterface) {
/*  509 */       this.mixin = info;
/*  510 */       this.annotationType = annotationType;
/*  511 */       this.targetMustBeInterface = targetMustBeInterface;
/*      */     }
/*      */     
/*      */     Collection<String> getInterfaces() {
/*  515 */       return Collections.emptyList();
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     boolean isDetachedSuper() {
/*  525 */       return this.detached;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     boolean isLoadable() {
/*  535 */       return false;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     void validateTarget(String targetName, ClassInfo targetInfo) {
/*  545 */       boolean targetIsInterface = targetInfo.isInterface();
/*  546 */       if (targetIsInterface != this.targetMustBeInterface) {
/*  547 */         String not = targetIsInterface ? "" : "not ";
/*  548 */         throw new InvalidMixinException(this.mixin, this.annotationType + " target type mismatch: " + targetName + " is " + not + "an interface in " + this);
/*      */       } 
/*      */     }
/*      */ 
/*      */     
/*      */     abstract void validate(MixinInfo.State param1State, List<ClassInfo> param1List);
/*      */ 
/*      */     
/*      */     abstract MixinPreProcessorStandard createPreProcessor(MixinInfo.MixinClassNode param1MixinClassNode);
/*      */ 
/*      */     
/*      */     static class Standard
/*      */       extends SubType
/*      */     {
/*      */       Standard(MixinInfo info) {
/*  563 */         super(info, "@Mixin", false);
/*      */       }
/*      */ 
/*      */       
/*      */       void validate(MixinInfo.State state, List<ClassInfo> targetClasses) {
/*  568 */         ClassNode classNode = state.getValidationClassNode();
/*      */         
/*  570 */         for (ClassInfo targetClass : targetClasses) {
/*  571 */           if (classNode.superName.equals(targetClass.getSuperName())) {
/*      */             continue;
/*      */           }
/*      */           
/*  575 */           if (!targetClass.hasSuperClass(classNode.superName, ClassInfo.Traversal.SUPER)) {
/*  576 */             ClassInfo superClass = ClassInfo.forName(classNode.superName);
/*  577 */             if (superClass.isMixin())
/*      */             {
/*  579 */               for (ClassInfo superTarget : superClass.getTargets()) {
/*  580 */                 if (targetClasses.contains(superTarget)) {
/*  581 */                   throw new InvalidMixinException(this.mixin, "Illegal hierarchy detected. Derived mixin " + this + " targets the same class " + superTarget
/*  582 */                       .getClassName() + " as its superclass " + superClass
/*  583 */                       .getClassName());
/*      */                 }
/*      */               } 
/*      */             }
/*      */             
/*  588 */             throw new InvalidMixinException(this.mixin, "Super class '" + classNode.superName.replace('/', '.') + "' of " + this.mixin
/*  589 */                 .getName() + " was not found in the hierarchy of target class '" + targetClass + "'");
/*      */           } 
/*      */           
/*  592 */           this.detached = true;
/*      */         } 
/*      */       }
/*      */ 
/*      */       
/*      */       MixinPreProcessorStandard createPreProcessor(MixinInfo.MixinClassNode classNode) {
/*  598 */         return new MixinPreProcessorStandard(this.mixin, classNode);
/*      */       }
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     static class Interface
/*      */       extends SubType
/*      */     {
/*      */       Interface(MixinInfo info) {
/*  608 */         super(info, "@Mixin", true);
/*      */       }
/*      */ 
/*      */       
/*      */       void validate(MixinInfo.State state, List<ClassInfo> targetClasses) {
/*  613 */         if (!MixinEnvironment.getCompatibilityLevel().supports(1)) {
/*  614 */           throw new InvalidMixinException(this.mixin, "Interface mixin not supported in current enviromnment");
/*      */         }
/*      */         
/*  617 */         ClassNode classNode = state.getValidationClassNode();
/*      */         
/*  619 */         if (!"java/lang/Object".equals(classNode.superName)) {
/*  620 */           throw new InvalidMixinException(this.mixin, "Super class of " + this + " is invalid, found " + classNode.superName
/*  621 */               .replace('/', '.'));
/*      */         }
/*      */       }
/*      */ 
/*      */       
/*      */       MixinPreProcessorStandard createPreProcessor(MixinInfo.MixinClassNode classNode) {
/*  627 */         return new MixinPreProcessorInterface(this.mixin, classNode);
/*      */       }
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     static class Accessor
/*      */       extends SubType
/*      */     {
/*  637 */       private final Collection<String> interfaces = new ArrayList<>();
/*      */       
/*      */       Accessor(MixinInfo info) {
/*  640 */         super(info, "@Mixin", false);
/*  641 */         this.interfaces.add(info.getClassRef());
/*      */       }
/*      */ 
/*      */       
/*      */       boolean isLoadable() {
/*  646 */         return true;
/*      */       }
/*      */ 
/*      */       
/*      */       Collection<String> getInterfaces() {
/*  651 */         return this.interfaces;
/*      */       }
/*      */ 
/*      */       
/*      */       void validateTarget(String targetName, ClassInfo targetInfo) {
/*  656 */         boolean targetIsInterface = targetInfo.isInterface();
/*  657 */         if (targetIsInterface && !MixinEnvironment.getCompatibilityLevel().supports(1)) {
/*  658 */           throw new InvalidMixinException(this.mixin, "Accessor mixin targetting an interface is not supported in current enviromnment");
/*      */         }
/*      */       }
/*      */ 
/*      */       
/*      */       void validate(MixinInfo.State state, List<ClassInfo> targetClasses) {
/*  664 */         ClassNode classNode = state.getValidationClassNode();
/*      */         
/*  666 */         if (!"java/lang/Object".equals(classNode.superName)) {
/*  667 */           throw new InvalidMixinException(this.mixin, "Super class of " + this + " is invalid, found " + classNode.superName
/*  668 */               .replace('/', '.'));
/*      */         }
/*      */       }
/*      */ 
/*      */       
/*      */       MixinPreProcessorStandard createPreProcessor(MixinInfo.MixinClassNode classNode) {
/*  674 */         return new MixinPreProcessorAccessor(this.mixin, classNode);
/*      */       }
/*      */     }
/*      */     
/*      */     static SubType getTypeFor(MixinInfo mixin) {
/*  679 */       MixinInfo.Variant variant = MixinInfo.getVariant(mixin.getClassInfo());
/*  680 */       switch (variant) {
/*      */         case STANDARD:
/*  682 */           return new Standard(mixin);
/*      */         case INTERFACE:
/*  684 */           return new Interface(mixin);
/*      */         case ACCESSOR:
/*  686 */           return new Accessor(mixin);
/*      */       } 
/*  688 */       throw new IllegalStateException("Unsupported Mixin variant " + variant + " for " + mixin);
/*      */     } } static class Accessor extends SubType {
/*      */     private final Collection<String> interfaces = new ArrayList<>(); Accessor(MixinInfo info) { super(info, "@Mixin", false);
/*      */       this.interfaces.add(info.getClassRef()); } boolean isLoadable() { return true; } Collection<String> getInterfaces() { return this.interfaces; } void validateTarget(String targetName, ClassInfo targetInfo) {
/*      */       boolean targetIsInterface = targetInfo.isInterface();
/*      */       if (targetIsInterface && !MixinEnvironment.getCompatibilityLevel().supports(1))
/*      */         throw new InvalidMixinException(this.mixin, "Accessor mixin targetting an interface is not supported in current enviromnment"); 
/*      */     } void validate(MixinInfo.State state, List<ClassInfo> targetClasses) {
/*      */       ClassNode classNode = state.getValidationClassNode();
/*      */       if (!"java/lang/Object".equals(classNode.superName))
/*      */         throw new InvalidMixinException(this.mixin, "Super class of " + this + " is invalid, found " + classNode.superName.replace('/', '.')); 
/*      */     } MixinPreProcessorStandard createPreProcessor(MixinInfo.MixinClassNode classNode) {
/*      */       return new MixinPreProcessorAccessor(this.mixin, classNode);
/*      */     }
/*      */   } static final class DeclaredTarget {
/*      */     final String name; private DeclaredTarget(String name, boolean isPrivate) {
/*  704 */       this.name = name;
/*  705 */       this.isPrivate = isPrivate;
/*      */     }
/*      */     final boolean isPrivate;
/*      */     
/*      */     public String toString() {
/*  710 */       return this.name;
/*      */     }
/*      */     
/*      */     static DeclaredTarget of(Object target, MixinInfo info) {
/*  714 */       if (target instanceof String) {
/*  715 */         String remappedName = info.remapClassName((String)target);
/*  716 */         return (remappedName != null) ? new DeclaredTarget(remappedName, true) : null;
/*  717 */       }  if (target instanceof Type) {
/*  718 */         return new DeclaredTarget(((Type)target).getClassName(), false);
/*      */       }
/*  720 */       return null;
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  729 */   static int mixinOrder = 0;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  734 */   private final transient Logger logger = LogManager.getLogger("mixin");
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  739 */   private final transient Profiler profiler = MixinEnvironment.getProfiler();
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private final transient MixinConfig parent;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private final String name;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private final String className;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private final int priority;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private final boolean virtual;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private final transient List<DeclaredTarget> declaredTargets;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  775 */   private final transient List<ClassInfo> targetClasses = new ArrayList<>();
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  780 */   private final List<String> targetClassNames = new ArrayList<>();
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  785 */   private final transient int order = mixinOrder++;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private final transient IMixinService service;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private final transient PluginHandle plugin;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private final transient MixinEnvironment.Phase phase;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private final transient ClassInfo info;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private final transient SubType type;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private final transient boolean strict;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private transient State pendingState;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private transient State state;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   MixinInfo(IMixinService service, MixinConfig parent, String name, PluginHandle plugin, boolean ignorePlugin) {
/*  837 */     this.service = service;
/*  838 */     this.parent = parent;
/*  839 */     this.name = name;
/*  840 */     this.className = parent.getMixinPackage() + name;
/*  841 */     this.plugin = plugin;
/*  842 */     this.phase = parent.getEnvironment().getPhase();
/*  843 */     this.strict = parent.getEnvironment().getOption(MixinEnvironment.Option.DEBUG_TARGETS);
/*      */ 
/*      */     
/*      */     try {
/*  847 */       ClassNode mixinClassNode = loadMixinClass(this.className);
/*  848 */       this.pendingState = new State(mixinClassNode);
/*  849 */       this.info = this.pendingState.getClassInfo();
/*  850 */       this.type = SubType.getTypeFor(this);
/*  851 */     } catch (InvalidMixinException ex) {
/*  852 */       throw ex;
/*  853 */     } catch (Exception ex) {
/*  854 */       throw new InvalidMixinException(this, ex.getMessage(), ex);
/*      */     } 
/*      */     
/*  857 */     if (!this.type.isLoadable()) {
/*      */ 
/*      */ 
/*      */       
/*  861 */       IClassTracker tracker = this.service.getClassTracker();
/*  862 */       if (tracker != null) {
/*  863 */         tracker.registerInvalidClass(this.className);
/*      */       }
/*      */     } 
/*      */ 
/*      */     
/*      */     try {
/*  869 */       this.priority = readPriority(this.pendingState.getClassNode());
/*  870 */       this.virtual = readPseudo(this.pendingState.getValidationClassNode());
/*  871 */       this.declaredTargets = readDeclaredTargets(this.pendingState.getValidationClassNode(), ignorePlugin);
/*  872 */     } catch (InvalidMixinException ex) {
/*  873 */       throw ex;
/*  874 */     } catch (Exception ex) {
/*  875 */       throw new InvalidMixinException(this, ex);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   void parseTargets() {
/*      */     try {
/*  885 */       this.targetClasses.addAll(readTargetClasses(this.declaredTargets));
/*  886 */       this.targetClassNames.addAll(Lists.transform(this.targetClasses, Functions.toStringFunction()));
/*  887 */     } catch (InvalidMixinException ex) {
/*  888 */       throw ex;
/*  889 */     } catch (Exception ex) {
/*  890 */       throw new InvalidMixinException(this, ex);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   void validate() {
/*  898 */     if (this.pendingState == null) {
/*  899 */       throw new IllegalStateException("No pending validation state for " + this);
/*      */     }
/*      */     
/*      */     try {
/*  903 */       this.pendingState.validate(this.type, this.targetClasses);
/*  904 */       this.state = this.pendingState;
/*      */     } finally {
/*  906 */       this.pendingState = null;
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
/*      */   protected List<DeclaredTarget> readDeclaredTargets(MixinClassNode classNode, boolean ignorePlugin) {
/*  918 */     if (classNode == null) {
/*  919 */       return Collections.emptyList();
/*      */     }
/*      */     
/*  922 */     AnnotationNode mixin = Annotations.getInvisible(classNode, Mixin.class);
/*  923 */     if (mixin == null) {
/*  924 */       throw new InvalidMixinException(this, String.format("The mixin '%s' is missing an @Mixin annotation", new Object[] { this.className }));
/*      */     }
/*      */     
/*  927 */     IClassTracker tracker = this.service.getClassTracker();
/*  928 */     List<DeclaredTarget> declaredTargets = new ArrayList<>();
/*  929 */     for (Object target : readTargets(mixin)) {
/*  930 */       DeclaredTarget declaredTarget = DeclaredTarget.of(target, this);
/*  931 */       if (declaredTarget == null) {
/*      */         continue;
/*      */       }
/*  934 */       if (tracker != null && tracker.isClassLoaded(declaredTarget.name) && !isReloading()) {
/*  935 */         String message = String.format("Critical problem: %s target %s was loaded too early.", new Object[] { this, declaredTarget.name });
/*  936 */         if (this.parent.isRequired()) {
/*  937 */           throw new MixinTargetAlreadyLoadedException(this, message, declaredTarget.name);
/*      */         }
/*  939 */         this.logger.error(message);
/*      */       } 
/*      */       
/*  942 */       if (shouldApplyMixin(ignorePlugin, declaredTarget.name)) {
/*  943 */         declaredTargets.add(declaredTarget);
/*      */       }
/*      */     } 
/*  946 */     return declaredTargets;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private Iterable<Object> readTargets(AnnotationNode mixin) {
/*  957 */     Iterable<Object> publicTargets = (Iterable<Object>)Annotations.getValue(mixin, "value");
/*  958 */     Iterable<Object> privateTargets = (Iterable<Object>)Annotations.getValue(mixin, "targets");
/*  959 */     if (publicTargets == null && privateTargets == null) {
/*  960 */       return Collections.emptyList();
/*      */     }
/*  962 */     if (publicTargets == null) {
/*  963 */       return privateTargets;
/*      */     }
/*  965 */     return (privateTargets == null) ? publicTargets : Iterables.concat(publicTargets, privateTargets);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private boolean shouldApplyMixin(boolean ignorePlugin, String targetName) {
/*  976 */     Profiler.Section pluginTimer = this.profiler.begin("plugin");
/*  977 */     boolean result = (ignorePlugin || this.plugin.shouldApplyMixin(targetName, this.className));
/*  978 */     pluginTimer.end();
/*  979 */     return result;
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
/*      */   List<ClassInfo> readTargetClasses(MixinClassNode classNode, boolean ignorePlugin) {
/*  991 */     return readTargetClasses(readDeclaredTargets(classNode, ignorePlugin));
/*      */   }
/*      */   
/*      */   private List<ClassInfo> readTargetClasses(List<DeclaredTarget> declaredTargets) throws InvalidMixinException {
/*  995 */     List<ClassInfo> targetClasses = new ArrayList<>();
/*  996 */     for (DeclaredTarget target : declaredTargets) {
/*  997 */       ClassInfo targetClass = getTargetClass(target);
/*  998 */       if (targetClass != null) {
/*  999 */         targetClasses.add(targetClass);
/* 1000 */         targetClass.addMixin(this);
/*      */       } 
/*      */     } 
/* 1003 */     return targetClasses;
/*      */   }
/*      */   
/*      */   private ClassInfo getTargetClass(DeclaredTarget target) throws InvalidMixinException {
/* 1007 */     ClassInfo targetInfo = ClassInfo.forName(target.name);
/* 1008 */     if (targetInfo == null) {
/* 1009 */       if (isVirtual()) {
/* 1010 */         this.logger.debug("Skipping virtual target {} for {}", new Object[] { target.name, this });
/*      */       } else {
/* 1012 */         handleTargetError(String.format("@Mixin target %s was not found %s", new Object[] { target.name, this }));
/*      */       } 
/* 1014 */       return null;
/*      */     } 
/* 1016 */     this.type.validateTarget(target.name, targetInfo);
/* 1017 */     if (target.isPrivate && targetInfo.isPublic() && !isVirtual()) {
/* 1018 */       handleTargetError(String.format("@Mixin target %s is public in %s and should be specified in value", new Object[] { target.name, this }));
/*      */     }
/* 1020 */     return targetInfo;
/*      */   }
/*      */   
/*      */   private void handleTargetError(String message) {
/* 1024 */     if (this.strict) {
/* 1025 */       this.logger.error(message);
/* 1026 */       throw new InvalidMixinException(this, message);
/*      */     } 
/* 1028 */     this.logger.warn(message);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected int readPriority(ClassNode classNode) {
/* 1038 */     if (classNode == null) {
/* 1039 */       return this.parent.getDefaultMixinPriority();
/*      */     }
/*      */     
/* 1042 */     AnnotationNode mixin = Annotations.getInvisible(classNode, Mixin.class);
/* 1043 */     if (mixin == null) {
/* 1044 */       throw new InvalidMixinException(this, String.format("The mixin '%s' is missing an @Mixin annotation", new Object[] { this.className }));
/*      */     }
/*      */     
/* 1047 */     Integer priority = (Integer)Annotations.getValue(mixin, "priority");
/* 1048 */     return (priority == null) ? this.parent.getDefaultMixinPriority() : priority.intValue();
/*      */   }
/*      */   
/*      */   protected boolean readPseudo(ClassNode classNode) {
/* 1052 */     return (Annotations.getInvisible(classNode, Pseudo.class) != null);
/*      */   }
/*      */   
/*      */   private boolean isReloading() {
/* 1056 */     return this.pendingState instanceof Reloaded;
/*      */   }
/*      */   
/*      */   String remapClassName(String className) {
/* 1060 */     return this.parent.remapClassName(getClassRef(), className);
/*      */   }
/*      */   
/*      */   public boolean hasDeclaredTarget(String targetClass) {
/* 1064 */     for (DeclaredTarget declaredTarget : this.declaredTargets) {
/* 1065 */       if (targetClass.equals(declaredTarget.name)) {
/* 1066 */         return true;
/*      */       }
/*      */     } 
/* 1069 */     return false;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private State getState() {
/* 1077 */     return (this.state != null) ? this.state : this.pendingState;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   ClassInfo getClassInfo() {
/* 1084 */     return this.info;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public IMixinConfig getConfig() {
/* 1092 */     return this.parent;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   MixinConfig getParent() {
/* 1099 */     return this.parent;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int getPriority() {
/* 1107 */     return this.priority;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String getName() {
/* 1115 */     return this.name;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String getClassName() {
/* 1123 */     return this.className;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String getClassRef() {
/* 1131 */     return getClassInfo().getName();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public byte[] getClassBytes() {
/* 1140 */     throw new RuntimeException("NO");
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean isDetachedSuper() {
/* 1149 */     return getState().isDetachedSuper();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean isUnique() {
/* 1156 */     return getState().isUnique();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean isVirtual() {
/* 1163 */     return this.virtual;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean isAccessor() {
/* 1170 */     return this.type instanceof SubType.Accessor;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean isLoadable() {
/* 1177 */     return this.type.isLoadable();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean isRequired() {
/* 1184 */     return this.parent.isRequired();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Level getLoggingLevel() {
/* 1191 */     return this.parent.getLoggingLevel();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public MixinEnvironment.Phase getPhase() {
/* 1199 */     return this.phase;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public MixinClassNode getClassNode(int flags) {
/* 1207 */     return getState().createClassNode(flags);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   List<String> getDeclaredTargetClasses() {
/* 1214 */     return Collections.unmodifiableList(Lists.transform(this.declaredTargets, Functions.toStringFunction()));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public List<String> getTargetClasses() {
/* 1222 */     return Collections.unmodifiableList(this.targetClassNames);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   List<InterfaceInfo> getSoftImplements() {
/* 1229 */     return Collections.unmodifiableList(getState().getSoftImplements());
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   Set<String> getSyntheticInnerClasses() {
/* 1236 */     return Collections.unmodifiableSet(getState().getSyntheticInnerClasses());
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   Set<String> getInnerClasses() {
/* 1243 */     return Collections.unmodifiableSet(getState().getInnerClasses());
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   List<ClassInfo> getTargets() {
/* 1250 */     return Collections.unmodifiableList(this.targetClasses);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   Set<String> getInterfaces() {
/* 1259 */     return getState().getInterfaces();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   MixinTargetContext createContextFor(TargetClassContext target) {
/* 1269 */     MixinClassNode classNode = getClassNode(8);
/* 1270 */     Profiler.Section preTimer = this.profiler.begin("pre");
/* 1271 */     MixinTargetContext context = this.type.createPreProcessor(classNode).prepare().createContextFor(target);
/* 1272 */     preTimer.end();
/* 1273 */     return context;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private ClassNode loadMixinClass(String mixinClassName) throws ClassNotFoundException {
/* 1284 */     ClassNode classNode = null;
/*      */     
/*      */     try {
/* 1287 */       IClassTracker tracker = this.service.getClassTracker();
/* 1288 */       if (tracker != null) {
/* 1289 */         String restrictions = tracker.getClassRestrictions(mixinClassName);
/* 1290 */         if (restrictions.length() > 0) {
/* 1291 */           this.logger.error("Classloader restrictions [{}] encountered loading {}, name: {}", new Object[] { restrictions, this, mixinClassName });
/*      */         }
/*      */       } 
/* 1294 */       classNode = this.service.getBytecodeProvider().getClassNode(mixinClassName, true);
/* 1295 */     } catch (ClassNotFoundException ex) {
/* 1296 */       throw new ClassNotFoundException(String.format("The specified mixin '%s' was not found", new Object[] { mixinClassName }));
/* 1297 */     } catch (IOException ex) {
/* 1298 */       this.logger.warn("Failed to load mixin {}, the specified mixin will not be applied", new Object[] { mixinClassName });
/* 1299 */       throw new InvalidMixinException(this, "An error was encountered whilst loading the mixin class", ex);
/*      */     } 
/*      */     
/* 1302 */     return classNode;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   void reloadMixin(ClassNode classNode) {
/* 1311 */     if (this.pendingState != null) {
/* 1312 */       throw new IllegalStateException("Cannot reload mixin while it is initialising");
/*      */     }
/* 1314 */     this.pendingState = new Reloaded(this.state, classNode);
/* 1315 */     validate();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int compareTo(MixinInfo other) {
/* 1323 */     if (other == null) {
/* 1324 */       return 0;
/*      */     }
/* 1326 */     if (other.priority == this.priority) {
/* 1327 */       return this.order - other.order;
/*      */     }
/* 1329 */     return this.priority - other.priority;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void preApply(String transformedName, ClassNode targetClass) {
/* 1336 */     if (this.plugin.isAvailable()) {
/* 1337 */       Profiler.Section pluginTimer = this.profiler.begin("plugin");
/*      */       try {
/* 1339 */         this.plugin.preApply(transformedName, targetClass, this.className, this);
/*      */       } finally {
/* 1341 */         pluginTimer.end();
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void postApply(String transformedName, ClassNode targetClass) {
/* 1350 */     if (this.plugin.isAvailable()) {
/* 1351 */       Profiler.Section pluginTimer = this.profiler.begin("plugin");
/*      */       try {
/* 1353 */         this.plugin.postApply(transformedName, targetClass, this.className, this);
/*      */       } finally {
/* 1355 */         pluginTimer.end();
/*      */       } 
/*      */     } 
/*      */     
/* 1359 */     this.parent.postApply(transformedName, targetClass);
/* 1360 */     this.info.addAppliedMixin(this);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String toString() {
/* 1368 */     return String.format("%s:%s", new Object[] { this.parent.getName(), this.name });
/*      */   }
/*      */   
/*      */   static Variant getVariant(ClassNode classNode) {
/* 1372 */     return getVariant(ClassInfo.fromClassNode(classNode));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   static Variant getVariant(ClassInfo classInfo) {
/*      */     int i;
/* 1380 */     if (!classInfo.isInterface()) {
/* 1381 */       return Variant.STANDARD;
/*      */     }
/*      */     
/* 1384 */     boolean containsNonAccessorMethod = false;
/* 1385 */     for (ClassInfo.Method method : classInfo.getMethods()) {
/* 1386 */       i = containsNonAccessorMethod | ((!method.isAccessor() && !method.isSynthetic()) ? 1 : 0);
/*      */     }
/*      */     
/* 1389 */     if (i != 0)
/*      */     {
/* 1391 */       return Variant.INTERFACE;
/*      */     }
/*      */ 
/*      */     
/* 1395 */     return Variant.ACCESSOR;
/*      */   }
/*      */ }


/* Location:              C:\Users\DuduM\OneDrive\Área de Trabalho\mw10\modularwarfare-shining-2023.2.4.4f-fix10.jar!\org\spongepowered\asm\mixin\transformer\MixinInfo.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */