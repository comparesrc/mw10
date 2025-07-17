/*     */ package org.spongepowered.asm.mixin.injection.code;
/*     */ 
/*     */ import com.google.common.collect.ObjectArrays;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Arrays;
/*     */ import java.util.Collection;
/*     */ import java.util.Collections;
/*     */ import java.util.HashSet;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.Set;
/*     */ import java.util.TreeMap;
/*     */ import org.apache.logging.log4j.LogManager;
/*     */ import org.apache.logging.log4j.Logger;
/*     */ import org.objectweb.asm.Type;
/*     */ import org.objectweb.asm.tree.AbstractInsnNode;
/*     */ import org.objectweb.asm.tree.AnnotationNode;
/*     */ import org.objectweb.asm.tree.ClassNode;
/*     */ import org.objectweb.asm.tree.InsnList;
/*     */ import org.objectweb.asm.tree.InsnNode;
/*     */ import org.objectweb.asm.tree.LdcInsnNode;
/*     */ import org.objectweb.asm.tree.MethodInsnNode;
/*     */ import org.objectweb.asm.tree.MethodNode;
/*     */ import org.objectweb.asm.tree.TypeInsnNode;
/*     */ import org.objectweb.asm.tree.VarInsnNode;
/*     */ import org.spongepowered.asm.mixin.MixinEnvironment;
/*     */ import org.spongepowered.asm.mixin.injection.Coerce;
/*     */ import org.spongepowered.asm.mixin.injection.InjectionPoint;
/*     */ import org.spongepowered.asm.mixin.injection.struct.InjectionInfo;
/*     */ import org.spongepowered.asm.mixin.injection.struct.InjectionNodes;
/*     */ import org.spongepowered.asm.mixin.injection.struct.Target;
/*     */ import org.spongepowered.asm.mixin.injection.throwables.InjectionError;
/*     */ import org.spongepowered.asm.mixin.injection.throwables.InvalidInjectionException;
/*     */ import org.spongepowered.asm.mixin.refmap.IMixinContext;
/*     */ import org.spongepowered.asm.mixin.transformer.ClassInfo;
/*     */ import org.spongepowered.asm.util.Annotations;
/*     */ import org.spongepowered.asm.util.Bytecode;
/*     */ import org.spongepowered.asm.util.SignaturePrinter;
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
/*     */ public abstract class Injector
/*     */ {
/*     */   public static final class TargetNode
/*     */   {
/*     */     final AbstractInsnNode insn;
/*  76 */     final Set<InjectionPoint> nominators = new HashSet<>();
/*     */     
/*     */     TargetNode(AbstractInsnNode insn) {
/*  79 */       this.insn = insn;
/*     */     }
/*     */     
/*     */     public AbstractInsnNode getNode() {
/*  83 */       return this.insn;
/*     */     }
/*     */     
/*     */     public Set<InjectionPoint> getNominators() {
/*  87 */       return Collections.unmodifiableSet(this.nominators);
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean equals(Object obj) {
/*  92 */       if (obj == null || obj.getClass() != TargetNode.class) {
/*  93 */         return false;
/*     */       }
/*     */       
/*  96 */       return (((TargetNode)obj).insn == this.insn);
/*     */     }
/*     */ 
/*     */     
/*     */     public int hashCode() {
/* 101 */       return this.insn.hashCode();
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
/*     */   public static class InjectorData
/*     */   {
/*     */     public final Target target;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public String description;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public boolean allowCoerceArgs;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 136 */     public int captureTargetArgs = 0;
/*     */ 
/*     */ 
/*     */     
/*     */     public boolean coerceReturnType = false;
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public InjectorData(Target target) {
/* 146 */       this(target, "handler");
/*     */     }
/*     */     
/*     */     public InjectorData(Target target, String description) {
/* 150 */       this(target, description, true);
/*     */     }
/*     */     
/*     */     public InjectorData(Target target, String description, boolean allowCoerceArgs) {
/* 154 */       this.target = target;
/* 155 */       this.description = description;
/* 156 */       this.allowCoerceArgs = allowCoerceArgs;
/*     */     }
/*     */ 
/*     */     
/*     */     public String toString() {
/* 161 */       return this.description;
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 169 */   protected static final Logger logger = LogManager.getLogger("mixin");
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected InjectionInfo info;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected final String annotationType;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected final ClassNode classNode;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected final MethodNode methodNode;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected final Type[] methodArgs;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected final Type returnType;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected final boolean isStatic;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Injector(InjectionInfo info, String annotationType) {
/* 212 */     this.info = info;
/* 213 */     this.annotationType = annotationType;
/*     */     
/* 215 */     this.classNode = info.getClassNode();
/* 216 */     this.methodNode = info.getMethod();
/*     */     
/* 218 */     this.methodArgs = Type.getArgumentTypes(this.methodNode.desc);
/* 219 */     this.returnType = Type.getReturnType(this.methodNode.desc);
/* 220 */     this.isStatic = Bytecode.isStatic(this.methodNode);
/*     */   }
/*     */ 
/*     */   
/*     */   public String toString() {
/* 225 */     return String.format("%s::%s", new Object[] { this.classNode.name, this.info.getMethodName() });
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
/*     */   public final List<InjectionNodes.InjectionNode> find(InjectorTarget injectorTarget, List<InjectionPoint> injectionPoints) {
/* 237 */     sanityCheck(injectorTarget.getTarget(), injectionPoints);
/*     */     
/* 239 */     List<InjectionNodes.InjectionNode> myNodes = new ArrayList<>();
/* 240 */     for (TargetNode node : findTargetNodes(injectorTarget, injectionPoints)) {
/* 241 */       addTargetNode(injectorTarget.getTarget(), myNodes, node.insn, node.nominators);
/*     */     }
/* 243 */     return myNodes;
/*     */   }
/*     */   
/*     */   protected void addTargetNode(Target target, List<InjectionNodes.InjectionNode> myNodes, AbstractInsnNode node, Set<InjectionPoint> nominators) {
/* 247 */     myNodes.add(target.addInjectionNode(node));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final void inject(Target target, List<InjectionNodes.InjectionNode> nodes) {
/* 257 */     for (InjectionNodes.InjectionNode node : nodes) {
/* 258 */       if (node.isRemoved()) {
/* 259 */         if (this.info.getContext().getOption(MixinEnvironment.Option.DEBUG_VERBOSE)) {
/* 260 */           logger.warn("Target node for {} was removed by a previous injector in {}", new Object[] { this.info, target });
/*     */         }
/*     */         continue;
/*     */       } 
/* 264 */       inject(target, node);
/*     */     } 
/*     */     
/* 267 */     for (InjectionNodes.InjectionNode node : nodes) {
/* 268 */       postInject(target, node);
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
/*     */   private Collection<TargetNode> findTargetNodes(InjectorTarget injectorTarget, List<InjectionPoint> injectionPoints) {
/* 282 */     IMixinContext mixin = this.info.getContext();
/* 283 */     MethodNode method = injectorTarget.getMethod();
/* 284 */     Map<Integer, TargetNode> targetNodes = new TreeMap<>();
/* 285 */     Collection<AbstractInsnNode> nodes = new ArrayList<>(32);
/*     */     
/* 287 */     for (InjectionPoint injectionPoint : injectionPoints) {
/* 288 */       nodes.clear();
/*     */       
/* 290 */       if (injectorTarget.isMerged() && 
/* 291 */         !mixin.getClassName().equals(injectorTarget.getMergedBy()) && 
/* 292 */         !injectionPoint.checkPriority(injectorTarget.getMergedPriority(), mixin.getPriority())) {
/* 293 */         throw new InvalidInjectionException(this.info, String.format("%s on %s with priority %d cannot inject into %s merged by %s with priority %d", new Object[] { injectionPoint, this, 
/* 294 */                 Integer.valueOf(mixin.getPriority()), injectorTarget, injectorTarget
/* 295 */                 .getMergedBy(), Integer.valueOf(injectorTarget.getMergedPriority()) }));
/*     */       }
/*     */       
/* 298 */       if (findTargetNodes(method, injectionPoint, injectorTarget.getSlice(injectionPoint), nodes)) {
/* 299 */         for (AbstractInsnNode insn : nodes) {
/* 300 */           Integer key = Integer.valueOf(method.instructions.indexOf(insn));
/* 301 */           TargetNode targetNode = targetNodes.get(key);
/* 302 */           if (targetNode == null) {
/* 303 */             targetNode = new TargetNode(insn);
/* 304 */             targetNodes.put(key, targetNode);
/*     */           } 
/* 306 */           targetNode.nominators.add(injectionPoint);
/*     */         } 
/*     */       }
/*     */     } 
/*     */     
/* 311 */     return targetNodes.values();
/*     */   }
/*     */   
/*     */   protected boolean findTargetNodes(MethodNode into, InjectionPoint injectionPoint, InsnList insns, Collection<AbstractInsnNode> nodes) {
/* 315 */     return injectionPoint.find(into.desc, insns, nodes);
/*     */   }
/*     */   
/*     */   protected void sanityCheck(Target target, List<InjectionPoint> injectionPoints) {
/* 319 */     if (target.classNode != this.classNode) {
/* 320 */       throw new InvalidInjectionException(this.info, "Target class does not match injector class in " + this);
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
/*     */   protected final void checkTargetModifiers(Target target, boolean exactMatch) {
/* 333 */     if (exactMatch && target.isStatic != this.isStatic)
/* 334 */       throw new InvalidInjectionException(this.info, String.format("'static' modifier of handler method does not match target in %s", new Object[] { this })); 
/* 335 */     if (!exactMatch && !this.isStatic && target.isStatic) {
/* 336 */       throw new InvalidInjectionException(this.info, 
/* 337 */           String.format("non-static callback method %s targets a static method which is not supported", new Object[] { this }));
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
/*     */   protected void checkTargetForNode(Target target, InjectionNodes.InjectionNode node, InjectionPoint.RestrictTargetLevel targetLevel) {
/* 353 */     if (target.isCtor) {
/* 354 */       if (targetLevel == InjectionPoint.RestrictTargetLevel.METHODS_ONLY) {
/* 355 */         throw new InvalidInjectionException(this.info, String.format("Found %s targetting a constructor in injector %s", new Object[] { this.annotationType, this }));
/*     */       }
/*     */ 
/*     */       
/* 359 */       Bytecode.DelegateInitialiser superCall = target.findDelegateInitNode();
/* 360 */       if (!superCall.isPresent) {
/* 361 */         throw new InjectionError(String.format("Delegate constructor lookup failed for %s target on %s", new Object[] { this.annotationType, this.info }));
/*     */       }
/*     */       
/* 364 */       int superCallIndex = target.indexOf((AbstractInsnNode)superCall.insn);
/* 365 */       int targetIndex = target.indexOf(node.getCurrentTarget());
/* 366 */       if (targetIndex <= superCallIndex) {
/* 367 */         if (targetLevel == InjectionPoint.RestrictTargetLevel.CONSTRUCTORS_AFTER_DELEGATE) {
/* 368 */           throw new InvalidInjectionException(this.info, String.format("Found %s targetting a constructor before %s() in injector %s", new Object[] { this.annotationType, superCall, this }));
/*     */         }
/*     */ 
/*     */         
/* 372 */         if (!this.isStatic) {
/* 373 */           throw new InvalidInjectionException(this.info, String.format("%s handler before %s() invocation must be static in injector %s", new Object[] { this.annotationType, superCall, this }));
/*     */         }
/*     */         
/*     */         return;
/*     */       } 
/*     */     } 
/*     */     
/* 380 */     checkTargetModifiers(target, true);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected abstract void inject(Target paramTarget, InjectionNodes.InjectionNode paramInjectionNode);
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void postInject(Target target, InjectionNodes.InjectionNode node) {}
/*     */ 
/*     */ 
/*     */   
/*     */   protected AbstractInsnNode invokeHandler(InsnList insns) {
/* 396 */     return invokeHandler(insns, this.methodNode);
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
/*     */   protected AbstractInsnNode invokeHandler(InsnList insns, MethodNode handler) {
/* 408 */     boolean isPrivate = ((handler.access & 0x2) != 0);
/* 409 */     int invokeOpcode = this.isStatic ? 184 : (isPrivate ? 183 : 182);
/* 410 */     MethodInsnNode insn = new MethodInsnNode(invokeOpcode, this.classNode.name, handler.name, handler.desc, false);
/* 411 */     insns.add((AbstractInsnNode)insn);
/* 412 */     this.info.addCallbackInvocation(handler);
/* 413 */     return (AbstractInsnNode)insn;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected AbstractInsnNode invokeHandlerWithArgs(Type[] args, InsnList insns, int[] argMap) {
/* 423 */     return invokeHandlerWithArgs(args, insns, argMap, 0, args.length);
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
/*     */   protected AbstractInsnNode invokeHandlerWithArgs(Type[] args, InsnList insns, int[] argMap, int startArg, int endArg) {
/* 435 */     if (!this.isStatic) {
/* 436 */       insns.add((AbstractInsnNode)new VarInsnNode(25, 0));
/*     */     }
/* 438 */     pushArgs(args, insns, argMap, startArg, endArg);
/* 439 */     return invokeHandler(insns);
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
/*     */   protected int[] storeArgs(Target target, Type[] args, InsnList insns, int start) {
/* 453 */     int[] argMap = target.generateArgMap(args, start);
/* 454 */     storeArgs(args, insns, argMap, start, args.length);
/* 455 */     return argMap;
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
/*     */   protected void storeArgs(Type[] args, InsnList insns, int[] argMap, int start, int end) {
/* 468 */     for (int arg = end - 1; arg >= start; arg--) {
/* 469 */       insns.add((AbstractInsnNode)new VarInsnNode(args[arg].getOpcode(54), argMap[arg]));
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
/*     */   protected void pushArgs(Type[] args, InsnList insns, int[] argMap, int start, int end) {
/* 483 */     pushArgs(args, insns, argMap, start, end, null);
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
/*     */   protected void pushArgs(Type[] args, InsnList insns, int[] argMap, int start, int end, Target.Extension extension) {
/* 496 */     for (int arg = start; arg < end && arg < args.length; arg++) {
/* 497 */       insns.add((AbstractInsnNode)new VarInsnNode(args[arg].getOpcode(21), argMap[arg]));
/* 498 */       if (extension != null) {
/* 499 */         extension.add(args[arg].getSize());
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
/*     */   protected final void validateParams(InjectorData injector, Type returnType, Type... args) {
/* 524 */     String description = String.format("%s %s method %s from %s", new Object[] { this.annotationType, injector, this, this.info.getContext() });
/* 525 */     int argIndex = 0;
/*     */     try {
/* 527 */       injector.coerceReturnType = checkCoerce(-1, returnType, description, injector.allowCoerceArgs);
/*     */       
/* 529 */       for (Type arg : args) {
/* 530 */         if (arg != null) {
/* 531 */           checkCoerce(argIndex, arg, description, injector.allowCoerceArgs);
/* 532 */           argIndex++;
/*     */         } 
/*     */       } 
/*     */       
/* 536 */       if (argIndex == this.methodArgs.length) {
/*     */         return;
/*     */       }
/*     */       
/* 540 */       for (int targetArg = 0; targetArg < injector.target.arguments.length && argIndex < this.methodArgs.length; targetArg++, argIndex++) {
/* 541 */         checkCoerce(argIndex, injector.target.arguments[targetArg], description, true);
/* 542 */         injector.captureTargetArgs++;
/*     */       } 
/* 544 */     } catch (InvalidInjectionException ex) {
/*     */ 
/*     */       
/* 547 */       String expected = (this.methodArgs.length > args.length) ? Bytecode.generateDescriptor(returnType, (Type[])ObjectArrays.concat((Object[])args, (Object[])injector.target.arguments, Type.class)) : Bytecode.generateDescriptor(returnType, args);
/* 548 */       throw new InvalidInjectionException(this.info, String.format("%s. Handler signature: %s Expected signature: %s", new Object[] { ex.getMessage(), this.methodNode.desc, expected }));
/*     */     } 
/*     */ 
/*     */     
/* 552 */     if (argIndex < this.methodArgs.length) {
/* 553 */       Type[] extraArgs = Arrays.<Type>copyOfRange(this.methodArgs, argIndex, this.methodArgs.length);
/* 554 */       throw new InvalidInjectionException(this.info, String.format("%s has an invalid signature. Found %d unexpected additional method arguments: %s", new Object[] { description, 
/*     */               
/* 556 */               Integer.valueOf(this.methodArgs.length - argIndex), (new SignaturePrinter(extraArgs)).getFormattedArgs() }));
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
/*     */ 
/*     */ 
/*     */   
/*     */   protected final boolean checkCoerce(int index, Type toType, String description, boolean allowCoercion) {
/* 576 */     Type fromType = (index < 0) ? this.returnType : this.methodArgs[index];
/* 577 */     if (index >= this.methodArgs.length) {
/* 578 */       throw new InvalidInjectionException(this.info, String.format("%s has an invalid signature. Not enough arguments: expected argument type %s at index %d", new Object[] { description, 
/*     */               
/* 580 */               SignaturePrinter.getTypeName(toType), Integer.valueOf(index) }));
/*     */     }
/*     */     
/* 583 */     AnnotationNode coerce = Annotations.getInvisibleParameter(this.methodNode, Coerce.class, index);
/* 584 */     boolean isReturn = (index < 0);
/* 585 */     String argType = isReturn ? "return" : "argument";
/* 586 */     Object argIndex = isReturn ? "" : (" at index " + index);
/*     */     
/* 588 */     if (fromType.equals(toType)) {
/* 589 */       if (coerce != null && this.info.getContext().getOption(MixinEnvironment.Option.DEBUG_VERBOSE)) {
/* 590 */         logger.info("Possibly-redundant @Coerce on {} {} type{}, {} is identical to {}", new Object[] { description, argType, argIndex, 
/* 591 */               SignaturePrinter.getTypeName(toType), SignaturePrinter.getTypeName(fromType) });
/*     */       }
/* 593 */       return false;
/*     */     } 
/*     */     
/* 596 */     if (coerce == null || !allowCoercion) {
/* 597 */       String coerceWarning = (coerce != null) ? ". @Coerce not allowed here" : "";
/* 598 */       throw new InvalidInjectionException(this.info, String.format("%s has an invalid signature. Found unexpected %s type %s%s, expected %s%s", new Object[] { description, argType, 
/*     */               
/* 600 */               SignaturePrinter.getTypeName(fromType), argIndex, SignaturePrinter.getTypeName(toType), coerceWarning }));
/*     */     } 
/*     */     
/* 603 */     boolean canCoerce = canCoerce(fromType, toType);
/* 604 */     if (!canCoerce) {
/* 605 */       throw new InvalidInjectionException(this.info, String.format("%s has an invalid signature. Cannot @Coerce %s type %s%s to %s", new Object[] { description, argType, 
/*     */               
/* 607 */               SignaturePrinter.getTypeName(toType), argIndex, SignaturePrinter.getTypeName(fromType) }));
/*     */     }
/*     */     
/* 610 */     return true;
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
/*     */   protected void throwException(InsnList insns, String exceptionType, String message) {
/* 622 */     insns.add((AbstractInsnNode)new TypeInsnNode(187, exceptionType));
/* 623 */     insns.add((AbstractInsnNode)new InsnNode(89));
/* 624 */     insns.add((AbstractInsnNode)new LdcInsnNode(message));
/* 625 */     insns.add((AbstractInsnNode)new MethodInsnNode(183, exceptionType, "<init>", "(Ljava/lang/String;)V", false));
/* 626 */     insns.add((AbstractInsnNode)new InsnNode(191));
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
/*     */   public static boolean canCoerce(Type from, Type to) {
/* 638 */     int fromSort = from.getSort();
/* 639 */     int toSort = to.getSort();
/* 640 */     if (fromSort >= 9 && toSort >= 9 && fromSort == toSort) {
/* 641 */       if (fromSort == 9 && from.getDimensions() != to.getDimensions()) {
/* 642 */         return false;
/*     */       }
/* 644 */       return canCoerce(ClassInfo.forType(from, ClassInfo.TypeLookup.ELEMENT_TYPE), ClassInfo.forType(to, ClassInfo.TypeLookup.ELEMENT_TYPE));
/*     */     } 
/*     */     
/* 647 */     return canCoerce(from.getDescriptor(), to.getDescriptor());
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
/*     */   public static boolean canCoerce(String from, String to) {
/* 659 */     if (from.length() > 1 || to.length() > 1) {
/* 660 */       return false;
/*     */     }
/*     */     
/* 663 */     return canCoerce(from.charAt(0), to.charAt(0));
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
/*     */   public static boolean canCoerce(char from, char to) {
/* 675 */     return (to == 'I' && "IBSCZ".indexOf(from) > -1);
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
/*     */   private static boolean canCoerce(ClassInfo from, ClassInfo to) {
/* 688 */     return (from != null && to != null && (to == from || to.hasSuperClass(from, ClassInfo.Traversal.ALL, true)));
/*     */   }
/*     */ }


/* Location:              C:\Users\DuduM\OneDrive\Área de Trabalho\mw10\modularwarfare-shining-2023.2.4.4f-fix10.jar!\org\spongepowered\asm\mixin\injection\code\Injector.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */