/*      */ package org.spongepowered.asm.mixin.transformer;
/*      */ 
/*      */ import com.google.common.collect.BiMap;
/*      */ import com.google.common.collect.HashBiMap;
/*      */ import java.lang.annotation.Annotation;
/*      */ import java.util.ArrayList;
/*      */ import java.util.Collection;
/*      */ import java.util.Deque;
/*      */ import java.util.Iterator;
/*      */ import java.util.LinkedHashMap;
/*      */ import java.util.LinkedList;
/*      */ import java.util.List;
/*      */ import java.util.Map;
/*      */ import java.util.Set;
/*      */ import org.apache.logging.log4j.Level;
/*      */ import org.apache.logging.log4j.LogManager;
/*      */ import org.apache.logging.log4j.Logger;
/*      */ import org.objectweb.asm.Handle;
/*      */ import org.objectweb.asm.Type;
/*      */ import org.objectweb.asm.tree.AbstractInsnNode;
/*      */ import org.objectweb.asm.tree.AnnotationNode;
/*      */ import org.objectweb.asm.tree.ClassNode;
/*      */ import org.objectweb.asm.tree.FieldInsnNode;
/*      */ import org.objectweb.asm.tree.FieldNode;
/*      */ import org.objectweb.asm.tree.InvokeDynamicInsnNode;
/*      */ import org.objectweb.asm.tree.LdcInsnNode;
/*      */ import org.objectweb.asm.tree.LocalVariableNode;
/*      */ import org.objectweb.asm.tree.MethodInsnNode;
/*      */ import org.objectweb.asm.tree.MethodNode;
/*      */ import org.objectweb.asm.tree.TypeInsnNode;
/*      */ import org.objectweb.asm.tree.VarInsnNode;
/*      */ import org.spongepowered.asm.mixin.MixinEnvironment;
/*      */ import org.spongepowered.asm.mixin.SoftOverride;
/*      */ import org.spongepowered.asm.mixin.extensibility.IMixinInfo;
/*      */ import org.spongepowered.asm.mixin.gen.AccessorInfo;
/*      */ import org.spongepowered.asm.mixin.injection.struct.InjectionInfo;
/*      */ import org.spongepowered.asm.mixin.injection.struct.InjectorGroupInfo;
/*      */ import org.spongepowered.asm.mixin.injection.struct.Target;
/*      */ import org.spongepowered.asm.mixin.injection.throwables.InjectionError;
/*      */ import org.spongepowered.asm.mixin.injection.throwables.InjectionValidationException;
/*      */ import org.spongepowered.asm.mixin.refmap.IMixinContext;
/*      */ import org.spongepowered.asm.mixin.refmap.IReferenceMapper;
/*      */ import org.spongepowered.asm.mixin.struct.MemberRef;
/*      */ import org.spongepowered.asm.mixin.struct.SourceMap;
/*      */ import org.spongepowered.asm.mixin.throwables.ClassMetadataNotFoundException;
/*      */ import org.spongepowered.asm.mixin.transformer.ext.Extensions;
/*      */ import org.spongepowered.asm.mixin.transformer.meta.MixinMerged;
/*      */ import org.spongepowered.asm.mixin.transformer.throwables.InvalidMixinException;
/*      */ import org.spongepowered.asm.mixin.transformer.throwables.MixinTransformerError;
/*      */ import org.spongepowered.asm.obfuscation.RemapperChain;
/*      */ import org.spongepowered.asm.util.Annotations;
/*      */ import org.spongepowered.asm.util.Bytecode;
/*      */ import org.spongepowered.asm.util.ClassSignature;
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
/*      */ public class MixinTargetContext
/*      */   extends ClassContext
/*      */   implements IMixinContext
/*      */ {
/*   96 */   private static final Logger logger = LogManager.getLogger("mixin");
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  101 */   protected final ActivityStack activities = new ActivityStack(null);
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private final MixinInfo mixin;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private final ClassNode classNode;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private final TargetClassContext targetClass;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private final String sessionId;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private final ClassInfo targetClassInfo;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  131 */   private final BiMap<String, String> innerClasses = (BiMap<String, String>)HashBiMap.create();
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  136 */   private final List<MethodNode> shadowMethods = new ArrayList<>();
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  141 */   private final Map<FieldNode, ClassInfo.Field> shadowFields = new LinkedHashMap<>();
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  146 */   private final List<MethodNode> mergedMethods = new ArrayList<>();
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  151 */   private final InjectorGroupInfo.Map injectorGroups = new InjectorGroupInfo.Map();
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  156 */   private final List<InjectionInfo> injectors = new ArrayList<>();
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  161 */   private final List<AccessorInfo> accessors = new ArrayList<>();
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private final boolean inheritsFromMixin;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private final boolean detachedSuper;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private final SourceMap.File stratum;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  182 */   private int minRequiredClassVersion = MixinEnvironment.CompatibilityLevel.JAVA_6.classVersion();
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   MixinTargetContext(MixinInfo mixin, ClassNode classNode, TargetClassContext context) {
/*  192 */     this.mixin = mixin;
/*  193 */     this.classNode = classNode;
/*  194 */     this.targetClass = context;
/*  195 */     this.targetClassInfo = context.getClassInfo();
/*  196 */     this.stratum = context.getSourceMap().addFile(this.classNode);
/*  197 */     this.inheritsFromMixin = (mixin.getClassInfo().hasMixinInHierarchy() || this.targetClassInfo.hasMixinTargetInHierarchy());
/*  198 */     this.detachedSuper = !this.classNode.superName.equals((getTarget().getClassNode()).superName);
/*  199 */     this.sessionId = context.getSessionId();
/*  200 */     requireVersion(classNode.version);
/*      */     
/*  202 */     InnerClassGenerator icg = (InnerClassGenerator)context.getExtensions().getGenerator(InnerClassGenerator.class);
/*  203 */     for (String innerClass : this.mixin.getInnerClasses()) {
/*  204 */       this.innerClasses.put(innerClass, icg.registerInnerClass(this.mixin, innerClass, this));
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   void addShadowMethod(MethodNode method) {
/*  214 */     this.shadowMethods.add(method);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   void addShadowField(FieldNode fieldNode, ClassInfo.Field fieldInfo) {
/*  224 */     this.shadowFields.put(fieldNode, fieldInfo);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   void addAccessorMethod(MethodNode method, Class<? extends Annotation> type) {
/*  234 */     this.accessors.add(AccessorInfo.of(this, method, type));
/*      */   }
/*      */   
/*      */   void addMixinMethod(MethodNode method) {
/*  238 */     Annotations.setVisible(method, MixinMerged.class, new Object[] { "mixin", getClassName() });
/*  239 */     getTarget().addMixinMethod(method);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   void methodMerged(MethodNode method) {
/*  248 */     this.mergedMethods.add(method);
/*  249 */     this.targetClassInfo.addMethod(method);
/*  250 */     getTarget().methodMerged(method);
/*      */     
/*  252 */     Annotations.setVisible(method, MixinMerged.class, new Object[] { "mixin", 
/*  253 */           getClassName(), "priority", 
/*  254 */           Integer.valueOf(getPriority()), "sessionId", this.sessionId });
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String toString() {
/*  263 */     return this.mixin.toString();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public MixinEnvironment getEnvironment() {
/*  272 */     return this.mixin.getParent().getEnvironment();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean getOption(MixinEnvironment.Option option) {
/*  281 */     return getEnvironment().getOption(option);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public ClassNode getClassNode() {
/*  291 */     return this.classNode;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String getClassName() {
/*  301 */     return this.mixin.getClassName();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String getClassRef() {
/*  310 */     return this.mixin.getClassRef();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public TargetClassContext getTarget() {
/*  319 */     return this.targetClass;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String getTargetClassRef() {
/*  330 */     return getTarget().getClassRef();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public ClassNode getTargetClassNode() {
/*  339 */     return getTarget().getClassNode();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public ClassInfo getTargetClassInfo() {
/*  348 */     return this.targetClassInfo;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public ClassInfo getClassInfo() {
/*  358 */     return this.mixin.getClassInfo();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public ClassSignature getSignature() {
/*  367 */     return getClassInfo().getSignature();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public SourceMap.File getStratum() {
/*  376 */     return this.stratum;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int getMinRequiredClassVersion() {
/*  383 */     return this.minRequiredClassVersion;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int getDefaultRequiredInjections() {
/*  393 */     return this.mixin.getParent().getDefaultRequiredInjections();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String getDefaultInjectorGroup() {
/*  402 */     return this.mixin.getParent().getDefaultInjectorGroup();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int getMaxShiftByValue() {
/*  411 */     return this.mixin.getParent().getMaxShiftByValue();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public InjectorGroupInfo.Map getInjectorGroups() {
/*  420 */     return this.injectorGroups;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean requireOverwriteAnnotations() {
/*  429 */     return this.mixin.getParent().requireOverwriteAnnotations();
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
/*      */   void transformMethod(MethodNode method) {
/*  441 */     this.activities.clear();
/*      */     
/*      */     try {
/*  444 */       ActivityStack.Activity activity = this.activities.begin("Validate");
/*  445 */       validateMethod(method);
/*  446 */       activity.next("Transform Descriptor");
/*  447 */       transformDescriptor(method);
/*  448 */       activity.next("Transform LVT");
/*  449 */       transformLVT(method);
/*      */ 
/*      */       
/*  452 */       activity.next("Transform Line Numbers");
/*  453 */       this.stratum.applyOffset(method);
/*      */       
/*  455 */       activity.next("Transform Instructions");
/*  456 */       AbstractInsnNode lastInsn = null;
/*  457 */       for (Iterator<AbstractInsnNode> iter = method.instructions.iterator(); iter.hasNext(); ) {
/*  458 */         AbstractInsnNode insn = iter.next();
/*  459 */         ActivityStack.Activity insnActivity = this.activities.begin(Bytecode.getOpcodeName(insn) + " ");
/*      */         
/*  461 */         if (insn instanceof MethodInsnNode) {
/*  462 */           MethodInsnNode methodNode = (MethodInsnNode)insn;
/*  463 */           insnActivity.append("%s::%s%s", new Object[] { methodNode.owner, methodNode.name, methodNode.desc });
/*  464 */           transformMethodRef(method, iter, (MemberRef)new MemberRef.Method(methodNode));
/*  465 */         } else if (insn instanceof FieldInsnNode) {
/*  466 */           FieldInsnNode fieldNode = (FieldInsnNode)insn;
/*  467 */           insnActivity.append("%s::%s:%s", new Object[] { fieldNode.owner, fieldNode.name, fieldNode.desc });
/*  468 */           transformFieldRef(method, iter, (MemberRef)new MemberRef.Field(fieldNode));
/*  469 */           checkFinal(method, iter, fieldNode);
/*  470 */         } else if (insn instanceof TypeInsnNode) {
/*  471 */           TypeInsnNode typeNode = (TypeInsnNode)insn;
/*  472 */           insnActivity.append(typeNode.desc);
/*  473 */           transformTypeNode(method, iter, typeNode, lastInsn);
/*  474 */         } else if (insn instanceof LdcInsnNode) {
/*  475 */           transformConstantNode(method, iter, (LdcInsnNode)insn);
/*  476 */         } else if (insn instanceof InvokeDynamicInsnNode) {
/*  477 */           InvokeDynamicInsnNode invokeNode = (InvokeDynamicInsnNode)insn;
/*  478 */           insnActivity.append("%s %s", new Object[] { invokeNode.name, invokeNode.desc });
/*  479 */           transformInvokeDynamicNode(method, iter, invokeNode);
/*      */         } 
/*      */         
/*  482 */         lastInsn = insn;
/*  483 */         insnActivity.end();
/*      */       } 
/*  485 */       activity.end();
/*  486 */     } catch (InvalidMixinException ex) {
/*  487 */       ex.prepend(this.activities);
/*  488 */       throw ex;
/*  489 */     } catch (Exception ex) {
/*  490 */       throw new InvalidMixinException(this, "Unexpecteded " + ex.getClass().getSimpleName() + " whilst transforming the mixin class:", ex, this.activities);
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
/*      */   private void validateMethod(MethodNode method) {
/*  503 */     if (Annotations.getInvisible(method, SoftOverride.class) != null) {
/*  504 */       if (Bytecode.getVisibility(method) == Bytecode.Visibility.PRIVATE) {
/*  505 */         throw new InvalidMixinException(this, "Mixin method " + method.name + method.desc + " is tagged with @SoftOverride but the method is PRIVATE");
/*      */       }
/*      */ 
/*      */       
/*  509 */       ClassInfo.Method superMethod = this.targetClassInfo.findMethodInHierarchy(method.name, method.desc, ClassInfo.SearchType.SUPER_CLASSES_ONLY, ClassInfo.Traversal.SUPER);
/*      */       
/*  511 */       if (superMethod == null || !superMethod.isInjected()) {
/*  512 */         throw new InvalidMixinException(this, "Mixin method " + method.name + method.desc + " is tagged with @SoftOverride but no valid method was found in superclasses of " + 
/*  513 */             getTarget().getClassName());
/*      */       }
/*      */     } 
/*      */     
/*  517 */     if (Bytecode.isVirtual(method)) {
/*  518 */       ClassInfo.Method superMethod = this.targetClassInfo.findMethodInHierarchy(method, ClassInfo.SearchType.SUPER_CLASSES_ONLY, ClassInfo.Traversal.ALL, 0);
/*  519 */       if (superMethod != null && superMethod.isFinal()) {
/*  520 */         throw new InvalidMixinException(this.mixin, String.format("%s%s in %s overrides a final method from %s", new Object[] { method.name, method.desc, this.mixin, superMethod
/*  521 */                 .getOwner().getClassName() }));
/*      */       }
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void transformLVT(MethodNode method) {
/*  532 */     if (method.localVariables == null) {
/*      */       return;
/*      */     }
/*      */     
/*  536 */     ActivityStack.Activity localVarActivity = this.activities.begin("?");
/*  537 */     for (LocalVariableNode local : method.localVariables) {
/*  538 */       if (local == null || local.desc == null) {
/*      */         continue;
/*      */       }
/*      */       
/*  542 */       localVarActivity.next("var=%s", new Object[] { local.name });
/*  543 */       local.desc = transformSingleDescriptor(Type.getType(local.desc));
/*      */     } 
/*  545 */     localVarActivity.end();
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
/*      */   private void transformMethodRef(MethodNode method, Iterator<AbstractInsnNode> iter, MemberRef methodRef) {
/*  557 */     transformDescriptor(methodRef);
/*      */     
/*  559 */     if (methodRef.getOwner().equals(getClassRef())) {
/*  560 */       methodRef.setOwner(getTarget().getClassRef());
/*  561 */       ClassInfo.Method md = getClassInfo().findMethod(methodRef.getName(), methodRef.getDesc(), 10);
/*  562 */       if (md != null && md.isRenamed() && md.getOriginalName().equals(methodRef.getName()) && (md.isSynthetic() || md.isConformed())) {
/*  563 */         methodRef.setName(md.getName());
/*      */       }
/*  565 */       upgradeMethodRef(method, methodRef, md);
/*  566 */     } else if (this.innerClasses.containsKey(methodRef.getOwner())) {
/*  567 */       methodRef.setOwner((String)this.innerClasses.get(methodRef.getOwner()));
/*  568 */       methodRef.setDesc(transformMethodDescriptor(methodRef.getDesc()));
/*  569 */     } else if (this.detachedSuper || this.inheritsFromMixin) {
/*  570 */       if (methodRef.getOpcode() == 183) {
/*  571 */         updateStaticBinding(method, methodRef);
/*  572 */       } else if (methodRef.getOpcode() == 182 && ClassInfo.forName(methodRef.getOwner()).isMixin()) {
/*  573 */         updateDynamicBinding(method, methodRef);
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
/*      */   private void transformFieldRef(MethodNode method, Iterator<AbstractInsnNode> iter, MemberRef fieldRef) {
/*  590 */     if ("super$".equals(fieldRef.getName())) {
/*  591 */       if (fieldRef instanceof MemberRef.Field) {
/*  592 */         processImaginarySuper(method, ((MemberRef.Field)fieldRef).insn);
/*  593 */         iter.remove();
/*      */       } else {
/*  595 */         throw new InvalidMixinException(this.mixin, "Cannot call imaginary super from method handle.");
/*      */       } 
/*      */     }
/*      */     
/*  599 */     transformDescriptor(fieldRef);
/*      */     
/*  601 */     if (fieldRef.getOwner().equals(getClassRef())) {
/*  602 */       fieldRef.setOwner(getTarget().getClassRef());
/*      */       
/*  604 */       ClassInfo.Field field = getClassInfo().findField(fieldRef.getName(), fieldRef.getDesc(), 10);
/*      */       
/*  606 */       if (field != null && field.isRenamed() && field.getOriginalName().equals(fieldRef.getName()) && field.isStatic()) {
/*  607 */         fieldRef.setName(field.getName());
/*      */       }
/*      */     } else {
/*  610 */       ClassInfo fieldOwner = ClassInfo.forName(fieldRef.getOwner());
/*  611 */       if (fieldOwner.isMixin()) {
/*  612 */         ClassInfo actualOwner = this.targetClassInfo.findCorrespondingType(fieldOwner);
/*  613 */         fieldRef.setOwner((actualOwner != null) ? actualOwner.getName() : getTarget().getClassRef());
/*      */       } 
/*      */     } 
/*      */   }
/*      */   
/*      */   private void checkFinal(MethodNode method, Iterator<AbstractInsnNode> iter, FieldInsnNode fieldNode) {
/*  619 */     if (!fieldNode.owner.equals(getTarget().getClassRef())) {
/*      */       return;
/*      */     }
/*      */     
/*  623 */     int opcode = fieldNode.getOpcode();
/*  624 */     if (opcode == 180 || opcode == 178) {
/*      */       return;
/*      */     }
/*      */     
/*  628 */     for (Map.Entry<FieldNode, ClassInfo.Field> shadow : this.shadowFields.entrySet()) {
/*  629 */       FieldNode shadowFieldNode = shadow.getKey();
/*  630 */       if (!shadowFieldNode.desc.equals(fieldNode.desc) || !shadowFieldNode.name.equals(fieldNode.name)) {
/*      */         continue;
/*      */       }
/*  633 */       ClassInfo.Field shadowField = shadow.getValue();
/*  634 */       if (shadowField.isDecoratedFinal()) {
/*  635 */         if (shadowField.isDecoratedMutable()) {
/*  636 */           if (this.mixin.getParent().getEnvironment().getOption(MixinEnvironment.Option.DEBUG_VERBOSE)) {
/*  637 */             logger.warn("Write access to @Mutable @Final field {} in {}::{}", new Object[] { shadowField, this.mixin, method.name });
/*      */           }
/*      */         }
/*  640 */         else if ("<init>".equals(method.name) || "<clinit>".equals(method.name)) {
/*  641 */           logger.warn("@Final field {} in {} should be final", new Object[] { shadowField, this.mixin });
/*      */         } else {
/*  643 */           logger.error("Write access detected to @Final field {} in {}::{}", new Object[] { shadowField, this.mixin, method.name });
/*  644 */           if (this.mixin.getParent().getEnvironment().getOption(MixinEnvironment.Option.DEBUG_VERIFY)) {
/*  645 */             throw new InvalidMixinException(this.mixin, "Write access detected to @Final field " + shadowField + " in " + this.mixin + "::" + method.name);
/*      */           }
/*      */         } 
/*      */       }
/*      */       return;
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
/*      */   private void transformTypeNode(MethodNode method, Iterator<AbstractInsnNode> iter, TypeInsnNode typeInsn, AbstractInsnNode lastNode) {
/*  666 */     if (typeInsn.getOpcode() == 192 && typeInsn.desc
/*  667 */       .equals(getTarget().getClassRef()) && lastNode
/*  668 */       .getOpcode() == 25 && ((VarInsnNode)lastNode).var == 0) {
/*      */       
/*  670 */       iter.remove();
/*      */       
/*      */       return;
/*      */     } 
/*  674 */     if (typeInsn.desc.equals(getClassRef())) {
/*  675 */       typeInsn.desc = getTarget().getClassRef();
/*      */     } else {
/*  677 */       String newName = (String)this.innerClasses.get(typeInsn.desc);
/*  678 */       if (newName != null) {
/*  679 */         typeInsn.desc = newName;
/*      */       }
/*      */     } 
/*      */     
/*  683 */     transformDescriptor(typeInsn);
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
/*      */   private void transformConstantNode(MethodNode method, Iterator<AbstractInsnNode> iter, LdcInsnNode ldcInsn) {
/*  695 */     ldcInsn.cst = transformConstant(method, iter, ldcInsn.cst);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void transformInvokeDynamicNode(MethodNode method, Iterator<AbstractInsnNode> iter, InvokeDynamicInsnNode dynInsn) {
/*  706 */     requireVersion(51);
/*  707 */     dynInsn.desc = transformMethodDescriptor(dynInsn.desc);
/*  708 */     dynInsn.bsm = transformHandle(method, iter, dynInsn.bsm);
/*  709 */     for (int i = 0; i < dynInsn.bsmArgs.length; i++) {
/*  710 */       dynInsn.bsmArgs[i] = transformConstant(method, iter, dynInsn.bsmArgs[i]);
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
/*      */   private Object transformConstant(MethodNode method, Iterator<AbstractInsnNode> iter, Object constant) {
/*  723 */     if (constant instanceof Type) {
/*  724 */       Type type = (Type)constant;
/*  725 */       String desc = transformDescriptor(type);
/*  726 */       if (!type.toString().equals(desc)) {
/*  727 */         return Type.getType(desc);
/*      */       }
/*  729 */       return constant;
/*  730 */     }  if (constant instanceof Handle) {
/*  731 */       return transformHandle(method, iter, (Handle)constant);
/*      */     }
/*  733 */     return constant;
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
/*      */   private Handle transformHandle(MethodNode method, Iterator<AbstractInsnNode> iter, Handle handle) {
/*  745 */     MemberRef.Handle memberRef = new MemberRef.Handle(handle);
/*  746 */     if (memberRef.isField()) {
/*  747 */       transformFieldRef(method, iter, (MemberRef)memberRef);
/*      */     } else {
/*  749 */       transformMethodRef(method, iter, (MemberRef)memberRef);
/*      */     } 
/*  751 */     return memberRef.getMethodHandle();
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
/*      */   private void processImaginarySuper(MethodNode method, FieldInsnNode fieldInsn) {
/*  767 */     if (fieldInsn.getOpcode() != 180) {
/*  768 */       if ("<init>".equals(method.name)) {
/*  769 */         throw new InvalidMixinException(this, "Illegal imaginary super declaration: field " + fieldInsn.name + " must not specify an initialiser");
/*      */       }
/*      */ 
/*      */       
/*  773 */       throw new InvalidMixinException(this, "Illegal imaginary super access: found " + Bytecode.getOpcodeName(fieldInsn.getOpcode()) + " opcode in " + method.name + method.desc);
/*      */     } 
/*      */ 
/*      */     
/*  777 */     if ((method.access & 0x2) != 0 || (method.access & 0x8) != 0) {
/*  778 */       throw new InvalidMixinException(this, "Illegal imaginary super access: method " + method.name + method.desc + " is private or static");
/*      */     }
/*      */ 
/*      */     
/*  782 */     if (Annotations.getInvisible(method, SoftOverride.class) == null) {
/*  783 */       throw new InvalidMixinException(this, "Illegal imaginary super access: method " + method.name + method.desc + " is not decorated with @SoftOverride");
/*      */     }
/*      */ 
/*      */     
/*  787 */     for (Iterator<AbstractInsnNode> methodIter = method.instructions.iterator(method.instructions.indexOf((AbstractInsnNode)fieldInsn)); methodIter.hasNext(); ) {
/*  788 */       AbstractInsnNode insn = methodIter.next();
/*  789 */       if (insn instanceof MethodInsnNode) {
/*  790 */         MethodInsnNode methodNode = (MethodInsnNode)insn;
/*  791 */         if (methodNode.owner.equals(getClassRef()) && methodNode.name.equals(method.name) && methodNode.desc.equals(method.desc)) {
/*  792 */           methodNode.setOpcode(183);
/*  793 */           updateStaticBinding(method, (MemberRef)new MemberRef.Method(methodNode));
/*      */           
/*      */           return;
/*      */         } 
/*      */       } 
/*      */     } 
/*  799 */     throw new InvalidMixinException(this, "Illegal imaginary super access: could not find INVOKE for " + method.name + method.desc);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void updateStaticBinding(MethodNode method, MemberRef methodRef) {
/*  810 */     updateBinding(method, methodRef, ClassInfo.Traversal.SUPER);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void updateDynamicBinding(MethodNode method, MemberRef methodRef) {
/*  821 */     updateBinding(method, methodRef, ClassInfo.Traversal.ALL);
/*      */   }
/*      */   
/*      */   private void updateBinding(MethodNode method, MemberRef methodRef, ClassInfo.Traversal traversal) {
/*  825 */     if ("<init>".equals(method.name) || methodRef
/*  826 */       .getOwner().equals(getTarget().getClassRef()) || 
/*  827 */       getTarget().getClassRef().startsWith("<")) {
/*      */       return;
/*      */     }
/*      */     
/*  831 */     ClassInfo.Method superMethod = this.targetClassInfo.findMethodInHierarchy(methodRef.getName(), methodRef.getDesc(), traversal
/*  832 */         .getSearchType(), traversal);
/*  833 */     if (superMethod != null) {
/*  834 */       if (superMethod.getOwner().isMixin()) {
/*  835 */         throw new InvalidMixinException(this, "Invalid " + methodRef + " in " + this + " resolved " + superMethod.getOwner() + " but is mixin.");
/*      */       }
/*      */       
/*  838 */       methodRef.setOwner(superMethod.getImplementor().getName());
/*  839 */     } else if (ClassInfo.forName(methodRef.getOwner()).isMixin()) {
/*  840 */       throw new MixinTransformerError("Error resolving " + methodRef + " in " + this);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   void transformDescriptor(FieldNode field) {
/*  850 */     if (!this.inheritsFromMixin && this.innerClasses.size() == 0) {
/*      */       return;
/*      */     }
/*  853 */     field.desc = transformSingleDescriptor(field.desc, false);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   void transformDescriptor(MethodNode method) {
/*  862 */     if (!this.inheritsFromMixin && this.innerClasses.size() == 0) {
/*      */       return;
/*      */     }
/*  865 */     method.desc = transformMethodDescriptor(method.desc);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   void transformDescriptor(MemberRef member) {
/*  875 */     if (!this.inheritsFromMixin && this.innerClasses.size() == 0) {
/*      */       return;
/*      */     }
/*  878 */     if (member.isField()) {
/*  879 */       member.setDesc(transformSingleDescriptor(member.getDesc(), false));
/*      */     } else {
/*  881 */       member.setDesc(transformMethodDescriptor(member.getDesc()));
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   void transformDescriptor(TypeInsnNode typeInsn) {
/*  891 */     if (!this.inheritsFromMixin && this.innerClasses.size() == 0) {
/*      */       return;
/*      */     }
/*  894 */     typeInsn.desc = transformSingleDescriptor(typeInsn.desc, true);
/*      */   }
/*      */   
/*      */   private String transformDescriptor(Type type) {
/*  898 */     if (type.getSort() == 11) {
/*  899 */       return transformMethodDescriptor(type.getDescriptor());
/*      */     }
/*  901 */     return transformSingleDescriptor(type);
/*      */   }
/*      */   
/*      */   private String transformSingleDescriptor(Type type) {
/*  905 */     if (type.getSort() < 9) {
/*  906 */       return type.toString();
/*      */     }
/*      */     
/*  909 */     return transformSingleDescriptor(type.toString(), false);
/*      */   }
/*      */   
/*      */   private String transformSingleDescriptor(String desc, boolean isObject) {
/*  913 */     ActivityStack.Activity descriptorActivity = this.activities.begin("desc=%s", new Object[] { desc });
/*  914 */     String type = desc;
/*  915 */     while (type.startsWith("[") || type.startsWith("L")) {
/*  916 */       if (type.startsWith("[")) {
/*  917 */         type = type.substring(1);
/*      */         continue;
/*      */       } 
/*  920 */       type = type.substring(1, type.indexOf(";"));
/*  921 */       isObject = true;
/*      */     } 
/*  923 */     if (!isObject) {
/*  924 */       descriptorActivity.end();
/*  925 */       return desc;
/*      */     } 
/*  927 */     String innerClassName = (String)this.innerClasses.get(type);
/*  928 */     if (innerClassName != null) {
/*  929 */       descriptorActivity.end();
/*  930 */       return desc.replace(type, innerClassName);
/*      */     } 
/*  932 */     if (this.innerClasses.inverse().containsKey(type)) {
/*  933 */       descriptorActivity.end();
/*  934 */       return desc;
/*      */     } 
/*  936 */     ClassInfo typeInfo = ClassInfo.forName(type);
/*  937 */     if (typeInfo == null) {
/*  938 */       throw new ClassMetadataNotFoundException(type.replace('/', '.'));
/*      */     }
/*  940 */     if (!typeInfo.isMixin() || typeInfo.isLoadable()) {
/*  941 */       descriptorActivity.end();
/*  942 */       return desc;
/*      */     } 
/*  944 */     String realDesc = desc.replace(type, findRealType(typeInfo).toString());
/*  945 */     descriptorActivity.end();
/*  946 */     return realDesc;
/*      */   }
/*      */   
/*      */   private String transformMethodDescriptor(String desc) {
/*  950 */     StringBuilder newDesc = new StringBuilder();
/*  951 */     newDesc.append('(');
/*  952 */     for (Type arg : Type.getArgumentTypes(desc)) {
/*  953 */       newDesc.append(transformSingleDescriptor(arg));
/*      */     }
/*  955 */     return newDesc.append(')').append(transformSingleDescriptor(Type.getReturnType(desc))).toString();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Target getTargetMethod(MethodNode method) {
/*  966 */     return getTarget().getTargetMethod(method);
/*      */   }
/*      */   
/*      */   MethodNode findMethod(MethodNode method, AnnotationNode annotation) {
/*  970 */     Deque<String> aliases = new LinkedList<>();
/*  971 */     aliases.add(method.name);
/*  972 */     if (annotation != null) {
/*  973 */       List<String> aka = (List<String>)Annotations.getValue(annotation, "aliases");
/*  974 */       if (aka != null) {
/*  975 */         aliases.addAll(aka);
/*      */       }
/*      */     } 
/*      */     
/*  979 */     return getTarget().findMethod(aliases, method.desc);
/*      */   }
/*      */   
/*      */   MethodNode findRemappedMethod(MethodNode method) {
/*  983 */     RemapperChain remapperChain = getEnvironment().getRemappers();
/*  984 */     String remappedName = remapperChain.mapMethodName(getTarget().getClassRef(), method.name, method.desc);
/*  985 */     if (remappedName.equals(method.name)) {
/*  986 */       return null;
/*      */     }
/*      */     
/*  989 */     Deque<String> aliases = new LinkedList<>();
/*  990 */     aliases.add(remappedName);
/*      */     
/*  992 */     return getTarget().findAliasedMethod(aliases, method.desc);
/*      */   }
/*      */   
/*      */   FieldNode findField(FieldNode field, AnnotationNode shadow) {
/*  996 */     Deque<String> aliases = new LinkedList<>();
/*  997 */     aliases.add(field.name);
/*  998 */     if (shadow != null) {
/*  999 */       List<String> aka = (List<String>)Annotations.getValue(shadow, "aliases");
/* 1000 */       if (aka != null) {
/* 1001 */         aliases.addAll(aka);
/*      */       }
/*      */     } 
/*      */     
/* 1005 */     return getTarget().findAliasedField(aliases, field.desc);
/*      */   }
/*      */   
/*      */   FieldNode findRemappedField(FieldNode field) {
/* 1009 */     RemapperChain remapperChain = getEnvironment().getRemappers();
/* 1010 */     String remappedName = remapperChain.mapFieldName(getTarget().getClassRef(), field.name, field.desc);
/* 1011 */     if (remappedName.equals(field.name)) {
/* 1012 */       return null;
/*      */     }
/*      */     
/* 1015 */     Deque<String> aliases = new LinkedList<>();
/* 1016 */     aliases.add(remappedName);
/* 1017 */     return getTarget().findAliasedField(aliases, field.desc);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void requireVersion(int version) {
/* 1027 */     this.minRequiredClassVersion = Math.max(this.minRequiredClassVersion, version);
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1032 */     if (version > MixinEnvironment.getCompatibilityLevel().classVersion()) {
/* 1033 */       throw new InvalidMixinException(this, "Unsupported mixin class version " + version);
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Extensions getExtensions() {
/* 1042 */     return this.targetClass.getExtensions();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public IMixinInfo getMixin() {
/* 1050 */     return this.mixin;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   MixinInfo getInfo() {
/* 1057 */     return this.mixin;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   boolean isRequired() {
/* 1064 */     return this.mixin.isRequired();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int getPriority() {
/* 1074 */     return this.mixin.getPriority();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   Set<String> getInterfaces() {
/* 1083 */     return this.mixin.getInterfaces();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   Collection<MethodNode> getShadowMethods() {
/* 1092 */     return this.shadowMethods;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   List<MethodNode> getMethods() {
/* 1101 */     return this.classNode.methods;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   Set<Map.Entry<FieldNode, ClassInfo.Field>> getShadowFields() {
/* 1110 */     return this.shadowFields.entrySet();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   List<FieldNode> getFields() {
/* 1119 */     return this.classNode.fields;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   Level getLoggingLevel() {
/* 1128 */     return this.mixin.getLoggingLevel();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   boolean shouldSetSourceFile() {
/* 1138 */     return this.mixin.getParent().shouldSetSourceFile();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   String getSourceFile() {
/* 1147 */     return this.classNode.sourceFile;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public IReferenceMapper getReferenceMapper() {
/* 1156 */     return this.mixin.getParent().getReferenceMapper();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   void preApply(String transformedName, ClassNode targetClass) {
/* 1166 */     this.mixin.preApply(transformedName, targetClass);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   void postApply(String transformedName, ClassNode targetClass) {
/* 1176 */     this.activities.clear();
/*      */     
/*      */     try {
/* 1179 */       ActivityStack.Activity activity = this.activities.begin("Validating Injector Groups");
/* 1180 */       this.injectorGroups.validateAll();
/* 1181 */       activity.next("Plugin Post-Application");
/* 1182 */       this.mixin.postApply(transformedName, targetClass);
/* 1183 */       activity.end();
/* 1184 */     } catch (InjectionValidationException ex) {
/* 1185 */       InjectorGroupInfo group = ex.getGroup();
/* 1186 */       throw new InjectionError(
/* 1187 */           String.format("Critical injection failure: Callback group %s in %s failed injection check: %s", new Object[] {
/* 1188 */               group, this.mixin, ex.getMessage() }));
/* 1189 */     } catch (InvalidMixinException ex) {
/* 1190 */       ex.prepend(this.activities);
/* 1191 */       throw ex;
/* 1192 */     } catch (Exception ex) {
/* 1193 */       throw new InvalidMixinException(this, "Unexpecteded " + ex.getClass().getSimpleName() + " whilst transforming the mixin class:", ex, this.activities);
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
/*      */   String getUniqueName(MethodNode method, boolean preservePrefix) {
/* 1208 */     return this.targetClassInfo.getMethodMapper().getUniqueName(method, this.sessionId, preservePrefix);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   String getUniqueName(FieldNode field) {
/* 1219 */     return this.targetClassInfo.getMethodMapper().getUniqueName(field, this.sessionId);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   void prepareInjections() {
/* 1227 */     this.activities.clear();
/*      */     
/*      */     try {
/* 1230 */       this.injectors.clear();
/*      */       
/* 1232 */       ActivityStack.Activity prepareActivity = this.activities.begin("?");
/* 1233 */       for (MethodNode method : this.mergedMethods) {
/* 1234 */         prepareActivity.next("%s%s", new Object[] { method.name, method.desc });
/* 1235 */         ActivityStack.Activity methodActivity = this.activities.begin("Parse");
/* 1236 */         InjectionInfo injectInfo = InjectionInfo.parse(this, method);
/* 1237 */         if (injectInfo == null) {
/*      */           continue;
/*      */         }
/*      */         
/* 1241 */         methodActivity.next("Validate");
/* 1242 */         if (injectInfo.isValid()) {
/* 1243 */           methodActivity.next("Prepare");
/* 1244 */           injectInfo.prepare();
/* 1245 */           this.injectors.add(injectInfo);
/*      */         } 
/*      */         
/* 1248 */         methodActivity.next("Undecorate");
/* 1249 */         method.visibleAnnotations.remove(injectInfo.getAnnotation());
/* 1250 */         methodActivity.end();
/*      */       } 
/* 1252 */       prepareActivity.end();
/* 1253 */     } catch (InvalidMixinException ex) {
/* 1254 */       ex.prepend(this.activities);
/* 1255 */       throw ex;
/* 1256 */     } catch (Exception ex) {
/* 1257 */       throw new InvalidMixinException(this, "Unexpecteded " + ex.getClass().getSimpleName() + " whilst transforming the mixin class:", ex, this.activities);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   void applyInjections() {
/* 1266 */     this.activities.clear();
/*      */     
/*      */     try {
/* 1269 */       ActivityStack.Activity applyActivity = this.activities.begin("Inject");
/* 1270 */       ActivityStack.Activity injectActivity = this.activities.begin("?");
/* 1271 */       for (InjectionInfo injectInfo : this.injectors) {
/* 1272 */         injectActivity.next(injectInfo.toString());
/* 1273 */         injectInfo.inject();
/*      */       } 
/*      */       
/* 1276 */       applyActivity.next("PostInject");
/* 1277 */       ActivityStack.Activity postInjectActivity = this.activities.begin("?");
/* 1278 */       for (InjectionInfo injectInfo : this.injectors) {
/* 1279 */         postInjectActivity.next(injectInfo.toString());
/* 1280 */         injectInfo.postInject();
/*      */       } 
/*      */       
/* 1283 */       applyActivity.end();
/* 1284 */       this.injectors.clear();
/* 1285 */     } catch (InvalidMixinException ex) {
/* 1286 */       ex.prepend(this.activities);
/* 1287 */       throw ex;
/* 1288 */     } catch (Exception ex) {
/* 1289 */       throw new InvalidMixinException(this, "Unexpecteded " + ex.getClass().getSimpleName() + " whilst transforming the mixin class:", ex, this.activities);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   List<MethodNode> generateAccessors() {
/* 1299 */     this.activities.clear();
/* 1300 */     List<MethodNode> methods = new ArrayList<>();
/*      */     
/*      */     try {
/* 1303 */       ActivityStack.Activity accessorActivity = this.activities.begin("Locate");
/* 1304 */       ActivityStack.Activity locateActivity = this.activities.begin("?");
/* 1305 */       for (AccessorInfo accessor : this.accessors) {
/* 1306 */         locateActivity.next(accessor.toString());
/* 1307 */         accessor.locate();
/*      */       } 
/*      */       
/* 1310 */       accessorActivity.next("Validate");
/* 1311 */       ActivityStack.Activity validateActivity = this.activities.begin("?");
/* 1312 */       for (AccessorInfo accessor : this.accessors) {
/* 1313 */         validateActivity.next(accessor.toString());
/* 1314 */         accessor.validate();
/*      */       } 
/*      */       
/* 1317 */       accessorActivity.next("Generate");
/* 1318 */       ActivityStack.Activity generateActivity = this.activities.begin("?");
/* 1319 */       for (AccessorInfo accessor : this.accessors) {
/* 1320 */         generateActivity.next(accessor.toString());
/* 1321 */         MethodNode generated = accessor.generate();
/* 1322 */         getTarget().addMixinMethod(generated);
/* 1323 */         methods.add(generated);
/*      */       } 
/* 1325 */       accessorActivity.end();
/* 1326 */     } catch (InvalidMixinException ex) {
/* 1327 */       ex.prepend(this.activities);
/* 1328 */       throw ex;
/* 1329 */     } catch (Exception ex) {
/* 1330 */       throw new InvalidMixinException(this, "Unexpecteded " + ex.getClass().getSimpleName() + " whilst transforming the mixin class:", ex, this.activities);
/*      */     } 
/*      */ 
/*      */     
/* 1334 */     return methods;
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
/*      */   private ClassInfo findRealType(ClassInfo mixin) {
/* 1346 */     if (mixin == getClassInfo()) {
/* 1347 */       return this.targetClassInfo;
/*      */     }
/*      */     
/* 1350 */     ClassInfo type = this.targetClassInfo.findCorrespondingType(mixin);
/* 1351 */     if (type == null) {
/* 1352 */       throw new InvalidMixinException(this, "Resolution error: unable to find corresponding type for " + mixin + " in hierarchy of " + this.targetClassInfo);
/*      */     }
/*      */ 
/*      */     
/* 1356 */     return type;
/*      */   }
/*      */ }


/* Location:              C:\Users\DuduM\OneDrive\Área de Trabalho\mw10\modularwarfare-shining-2023.2.4.4f-fix10.jar!\org\spongepowered\asm\mixin\transformer\MixinTargetContext.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */