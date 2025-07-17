/*     */ package org.spongepowered.asm.mixin.injection.invoke;
/*     */ 
/*     */ import com.google.common.collect.ObjectArrays;
/*     */ import com.google.common.primitives.Ints;
/*     */ import java.util.HashMap;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.Set;
/*     */ import org.objectweb.asm.Type;
/*     */ import org.objectweb.asm.tree.AbstractInsnNode;
/*     */ import org.objectweb.asm.tree.FieldInsnNode;
/*     */ import org.objectweb.asm.tree.InsnList;
/*     */ import org.objectweb.asm.tree.InsnNode;
/*     */ import org.objectweb.asm.tree.JumpInsnNode;
/*     */ import org.objectweb.asm.tree.LabelNode;
/*     */ import org.objectweb.asm.tree.LdcInsnNode;
/*     */ import org.objectweb.asm.tree.MethodInsnNode;
/*     */ import org.objectweb.asm.tree.TypeInsnNode;
/*     */ import org.objectweb.asm.tree.VarInsnNode;
/*     */ import org.spongepowered.asm.mixin.Final;
/*     */ import org.spongepowered.asm.mixin.injection.InjectionPoint;
/*     */ import org.spongepowered.asm.mixin.injection.code.Injector;
/*     */ import org.spongepowered.asm.mixin.injection.points.BeforeFieldAccess;
/*     */ import org.spongepowered.asm.mixin.injection.points.BeforeNew;
/*     */ import org.spongepowered.asm.mixin.injection.struct.InjectionInfo;
/*     */ import org.spongepowered.asm.mixin.injection.struct.InjectionNodes;
/*     */ import org.spongepowered.asm.mixin.injection.struct.Target;
/*     */ import org.spongepowered.asm.mixin.injection.throwables.InvalidInjectionException;
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
/*     */ public class RedirectInjector
/*     */   extends InvokeInjector
/*     */ {
/*     */   private static final String GET_CLASS_METHOD = "getClass";
/*     */   private static final String IS_ASSIGNABLE_FROM_METHOD = "isAssignableFrom";
/*     */   private static final String NPE = "java/lang/NullPointerException";
/*     */   private static final String KEY_NOMINATORS = "nominators";
/*     */   private static final String KEY_FUZZ = "fuzz";
/*     */   private static final String KEY_OPCODE = "opcode";
/*     */   protected Meta meta;
/*     */   
/*     */   class Meta
/*     */   {
/*     */     public static final String KEY = "redirector";
/*     */     final int priority;
/*     */     final boolean isFinal;
/*     */     final String name;
/*     */     final String desc;
/*     */     
/*     */     public Meta(int priority, boolean isFinal, String name, String desc) {
/* 116 */       this.priority = priority;
/* 117 */       this.isFinal = isFinal;
/* 118 */       this.name = name;
/* 119 */       this.desc = desc;
/*     */     }
/*     */     
/*     */     RedirectInjector getOwner() {
/* 123 */       return RedirectInjector.this;
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   static class ConstructorRedirectData
/*     */   {
/*     */     public static final String KEY = "ctor";
/*     */ 
/*     */     
/*     */     boolean wildcard = false;
/*     */ 
/*     */     
/* 137 */     int injected = 0;
/*     */     
/*     */     InvalidInjectionException lastException;
/*     */     
/*     */     public void throwOrCollect(InvalidInjectionException ex) {
/* 142 */       if (!this.wildcard) {
/* 143 */         throw ex;
/*     */       }
/* 145 */       this.lastException = ex;
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   static class RedirectedInvokeData
/*     */     extends Injector.InjectorData
/*     */   {
/*     */     final MethodInsnNode node;
/*     */     
/*     */     final Type returnType;
/*     */     
/*     */     final Type[] targetArgs;
/*     */     final Type[] handlerArgs;
/*     */     
/*     */     RedirectedInvokeData(Target target, MethodInsnNode node) {
/* 161 */       super(target);
/* 162 */       this.node = node;
/* 163 */       this.returnType = Type.getReturnType(node.desc);
/* 164 */       this.targetArgs = Type.getArgumentTypes(node.desc);
/* 165 */       this
/*     */         
/* 167 */         .handlerArgs = (node.getOpcode() == 184) ? this.targetArgs : (Type[])ObjectArrays.concat(Type.getObjectType(node.owner), (Object[])this.targetArgs);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   static class RedirectedFieldData
/*     */     extends Injector.InjectorData
/*     */   {
/*     */     final FieldInsnNode node;
/*     */     
/*     */     final int opcode;
/*     */     
/*     */     final Type owner;
/*     */     
/*     */     final Type type;
/*     */     
/*     */     final int dimensions;
/*     */     
/*     */     final boolean isStatic;
/*     */     final boolean isGetter;
/*     */     final boolean isSetter;
/*     */     Type elementType;
/* 189 */     int extraDimensions = 1;
/*     */     
/*     */     RedirectedFieldData(Target target, FieldInsnNode node) {
/* 192 */       super(target);
/* 193 */       this.node = node;
/* 194 */       this.opcode = node.getOpcode();
/* 195 */       this.owner = Type.getObjectType(node.owner);
/* 196 */       this.type = Type.getType(node.desc);
/* 197 */       this.dimensions = (this.type.getSort() == 9) ? this.type.getDimensions() : 0;
/* 198 */       this.isStatic = (this.opcode == 178 || this.opcode == 179);
/* 199 */       this.isGetter = (this.opcode == 178 || this.opcode == 180);
/* 200 */       this.isSetter = (this.opcode == 179 || this.opcode == 181);
/* 201 */       this.description = this.isGetter ? "field getter" : (this.isSetter ? "field setter" : "handler");
/*     */     }
/*     */     
/*     */     int getTotalDimensions() {
/* 205 */       return this.dimensions + this.extraDimensions;
/*     */     }
/*     */     
/*     */     Type[] getArrayArgs(Type... extra) {
/* 209 */       int dimensions = getTotalDimensions();
/* 210 */       Type[] args = new Type[dimensions + extra.length];
/* 211 */       for (int i = 0; i < args.length; i++) {
/* 212 */         args[i] = (i == 0) ? this.type : ((i < dimensions) ? Type.INT_TYPE : extra[dimensions - i]);
/*     */       }
/* 214 */       return args;
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
/* 225 */   private Map<BeforeNew, ConstructorRedirectData> ctorRedirectors = new HashMap<>();
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public RedirectInjector(InjectionInfo info) {
/* 231 */     this(info, "@Redirect");
/*     */   }
/*     */   
/*     */   protected RedirectInjector(InjectionInfo info, String annotationType) {
/* 235 */     super(info, annotationType);
/*     */     
/* 237 */     int priority = info.getContext().getPriority();
/* 238 */     boolean isFinal = (Annotations.getVisible(this.methodNode, Final.class) != null);
/* 239 */     this.meta = new Meta(priority, isFinal, this.info.toString(), this.methodNode.desc);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void checkTarget(Target target) {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void addTargetNode(Target target, List<InjectionNodes.InjectionNode> myNodes, AbstractInsnNode insn, Set<InjectionPoint> nominators) {
/* 253 */     InjectionNodes.InjectionNode node = target.getInjectionNode(insn);
/* 254 */     ConstructorRedirectData ctorData = null;
/* 255 */     int fuzz = 8;
/* 256 */     int opcode = 0;
/*     */     
/* 258 */     if (insn instanceof MethodInsnNode && "<init>".equals(((MethodInsnNode)insn).name)) {
/* 259 */       throw new InvalidInjectionException(this.info, String.format("Illegal %s of constructor specified on %s", new Object[] { this.annotationType, this }));
/*     */     }
/*     */ 
/*     */     
/* 263 */     if (node != null) {
/* 264 */       Meta other = (Meta)node.getDecoration("redirector");
/*     */       
/* 266 */       if (other != null && other.getOwner() != this) {
/* 267 */         if (other.priority >= this.meta.priority) {
/* 268 */           Injector.logger.warn("{} conflict. Skipping {} with priority {}, already redirected by {} with priority {}", new Object[] { this.annotationType, this.info, 
/* 269 */                 Integer.valueOf(this.meta.priority), other.name, Integer.valueOf(other.priority) }); return;
/*     */         } 
/* 271 */         if (other.isFinal) {
/* 272 */           throw new InvalidInjectionException(this.info, String.format("%s conflict: %s failed because target was already remapped by %s", new Object[] { this.annotationType, this, other.name }));
/*     */         }
/*     */       } 
/*     */     } 
/*     */ 
/*     */     
/* 278 */     for (InjectionPoint ip : nominators) {
/* 279 */       if (ip instanceof BeforeNew) {
/* 280 */         ctorData = getCtorRedirect((BeforeNew)ip);
/* 281 */         ctorData.wildcard = !((BeforeNew)ip).hasDescriptor(); continue;
/* 282 */       }  if (ip instanceof BeforeFieldAccess) {
/* 283 */         BeforeFieldAccess bfa = (BeforeFieldAccess)ip;
/* 284 */         fuzz = bfa.getFuzzFactor();
/* 285 */         opcode = bfa.getArrayOpcode();
/*     */       } 
/*     */     } 
/*     */     
/* 289 */     InjectionNodes.InjectionNode targetNode = target.addInjectionNode(insn);
/* 290 */     targetNode.decorate("redirector", this.meta);
/* 291 */     targetNode.decorate("nominators", nominators);
/* 292 */     if (insn instanceof TypeInsnNode && insn.getOpcode() == 187) {
/* 293 */       targetNode.decorate("ctor", ctorData);
/*     */     } else {
/* 295 */       targetNode.decorate("fuzz", Integer.valueOf(fuzz));
/* 296 */       targetNode.decorate("opcode", Integer.valueOf(opcode));
/*     */     } 
/* 298 */     myNodes.add(targetNode);
/*     */   }
/*     */   
/*     */   private ConstructorRedirectData getCtorRedirect(BeforeNew ip) {
/* 302 */     ConstructorRedirectData ctorRedirect = this.ctorRedirectors.get(ip);
/* 303 */     if (ctorRedirect == null) {
/* 304 */       ctorRedirect = new ConstructorRedirectData();
/* 305 */       this.ctorRedirectors.put(ip, ctorRedirect);
/*     */     } 
/* 307 */     return ctorRedirect;
/*     */   }
/*     */ 
/*     */   
/*     */   protected void inject(Target target, InjectionNodes.InjectionNode node) {
/* 312 */     if (!preInject(node)) {
/*     */       return;
/*     */     }
/*     */     
/* 316 */     if (node.isReplaced()) {
/* 317 */       throw new UnsupportedOperationException("Redirector target failure for " + this.info);
/*     */     }
/*     */     
/* 320 */     if (node.getCurrentTarget() instanceof MethodInsnNode) {
/* 321 */       checkTargetForNode(target, node, InjectionPoint.RestrictTargetLevel.ALLOW_ALL);
/* 322 */       injectAtInvoke(target, node);
/*     */       
/*     */       return;
/*     */     } 
/* 326 */     if (node.getCurrentTarget() instanceof FieldInsnNode) {
/* 327 */       checkTargetForNode(target, node, InjectionPoint.RestrictTargetLevel.ALLOW_ALL);
/* 328 */       injectAtFieldAccess(target, node);
/*     */       
/*     */       return;
/*     */     } 
/* 332 */     if (node.getCurrentTarget() instanceof TypeInsnNode) {
/* 333 */       int opcode = node.getCurrentTarget().getOpcode();
/* 334 */       if (opcode == 187) {
/* 335 */         if (!this.isStatic && target.isStatic) {
/* 336 */           throw new InvalidInjectionException(this.info, String.format("non-static callback method %s has a static target which is not supported", new Object[] { this }));
/*     */         }
/*     */         
/* 339 */         injectAtConstructor(target, node); return;
/*     */       } 
/* 341 */       if (opcode == 193) {
/* 342 */         checkTargetModifiers(target, false);
/* 343 */         injectAtInstanceOf(target, node);
/*     */         
/*     */         return;
/*     */       } 
/*     */     } 
/* 348 */     throw new InvalidInjectionException(this.info, String.format("%s annotation on is targetting an invalid insn in %s in %s", new Object[] { this.annotationType, target, this }));
/*     */   }
/*     */ 
/*     */   
/*     */   protected boolean preInject(InjectionNodes.InjectionNode node) {
/* 353 */     Meta other = (Meta)node.getDecoration("redirector");
/* 354 */     if (other.getOwner() != this) {
/* 355 */       Injector.logger.warn("{} conflict. Skipping {} with priority {}, already redirected by {} with priority {}", new Object[] { this.annotationType, this.info, 
/* 356 */             Integer.valueOf(this.meta.priority), other.name, Integer.valueOf(other.priority) });
/* 357 */       return false;
/*     */     } 
/* 359 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   protected void postInject(Target target, InjectionNodes.InjectionNode node) {
/* 364 */     super.postInject(target, node);
/* 365 */     if (node.getOriginalTarget() instanceof TypeInsnNode && node.getOriginalTarget().getOpcode() == 187) {
/* 366 */       ConstructorRedirectData meta = (ConstructorRedirectData)node.getDecoration("ctor");
/* 367 */       if (meta.wildcard && meta.injected == 0) {
/* 368 */         throw new InvalidInjectionException(this.info, String.format("%s ctor invocation was not found in %s", new Object[] { this.annotationType, target }), meta.lastException);
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void injectAtInvoke(Target target, InjectionNodes.InjectionNode node) {
/* 379 */     RedirectedInvokeData invoke = new RedirectedInvokeData(target, (MethodInsnNode)node.getCurrentTarget());
/*     */     
/* 381 */     validateParams(invoke, invoke.returnType, invoke.handlerArgs);
/*     */     
/* 383 */     InsnList insns = new InsnList();
/* 384 */     Target.Extension extraLocals = target.extendLocals().add(invoke.handlerArgs).add(1);
/* 385 */     Target.Extension extraStack = target.extendStack().add(1);
/* 386 */     int[] argMap = storeArgs(target, invoke.handlerArgs, insns, 0);
/* 387 */     if (invoke.captureTargetArgs > 0) {
/* 388 */       int argSize = Bytecode.getArgsSize(target.arguments, 0, invoke.captureTargetArgs);
/* 389 */       extraLocals.add(argSize);
/* 390 */       extraStack.add(argSize);
/*     */       
/* 392 */       argMap = Ints.concat(new int[][] { argMap, target.getArgIndices() });
/*     */     } 
/* 394 */     AbstractInsnNode champion = invokeHandlerWithArgs(this.methodArgs, insns, argMap);
/* 395 */     if (invoke.coerceReturnType && invoke.returnType.getSort() >= 9) {
/* 396 */       insns.add((AbstractInsnNode)new TypeInsnNode(192, invoke.returnType.getInternalName()));
/*     */     }
/* 398 */     target.replaceNode((AbstractInsnNode)invoke.node, champion, insns);
/* 399 */     extraLocals.apply();
/* 400 */     extraStack.apply();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void injectAtFieldAccess(Target target, InjectionNodes.InjectionNode node) {
/* 407 */     RedirectedFieldData field = new RedirectedFieldData(target, (FieldInsnNode)node.getCurrentTarget());
/*     */     
/* 409 */     int handlerDimensions = (this.returnType.getSort() == 9) ? this.returnType.getDimensions() : 0;
/*     */     
/* 411 */     if (handlerDimensions > field.dimensions)
/* 412 */       throw new InvalidInjectionException(this.info, "Dimensionality of handler method is greater than target array on " + this); 
/* 413 */     if (handlerDimensions == 0 && field.dimensions > 0) {
/* 414 */       int fuzz = ((Integer)node.getDecoration("fuzz")).intValue();
/* 415 */       int opcode = ((Integer)node.getDecoration("opcode")).intValue();
/* 416 */       injectAtArrayField(field, fuzz, opcode);
/*     */     } else {
/* 418 */       injectAtScalarField(field);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void injectAtArrayField(RedirectedFieldData field, int fuzz, int opcode) {
/* 426 */     Type elementType = field.type.getElementType();
/* 427 */     if (field.opcode != 178 && field.opcode != 180)
/* 428 */       throw new InvalidInjectionException(this.info, String.format("Unspported opcode %s for array access %s", new Object[] {
/* 429 */               Bytecode.getOpcodeName(field.opcode), this.info })); 
/* 430 */     if (this.returnType.getSort() != 0) {
/* 431 */       if (opcode != 190) {
/* 432 */         opcode = elementType.getOpcode(46);
/*     */       }
/* 434 */       AbstractInsnNode varNode = BeforeFieldAccess.findArrayNode(field.target.insns, field.node, opcode, fuzz);
/* 435 */       injectAtGetArray(field, varNode);
/*     */     } else {
/* 437 */       AbstractInsnNode varNode = BeforeFieldAccess.findArrayNode(field.target.insns, field.node, elementType.getOpcode(79), fuzz);
/* 438 */       injectAtSetArray(field, varNode);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void injectAtGetArray(RedirectedFieldData field, AbstractInsnNode varNode) {
/* 446 */     field.description = "array getter";
/* 447 */     field.elementType = field.type.getElementType();
/*     */     
/* 449 */     if (varNode != null && varNode.getOpcode() == 190) {
/* 450 */       field.elementType = Type.INT_TYPE;
/* 451 */       field.extraDimensions = 0;
/*     */     } 
/*     */     
/* 454 */     validateParams(field, field.elementType, field.getArrayArgs(new Type[0]));
/* 455 */     injectArrayRedirect(field, varNode, "array getter");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void injectAtSetArray(RedirectedFieldData field, AbstractInsnNode varNode) {
/* 462 */     field.description = "array setter";
/* 463 */     Type elementType = field.type.getElementType();
/* 464 */     int valueArgIndex = field.getTotalDimensions();
/* 465 */     if (checkCoerce(valueArgIndex, elementType, String.format("%s array setter method %s from %s", new Object[] { this.annotationType, this, this.info
/* 466 */             .getContext() }), true)) {
/* 467 */       elementType = this.methodArgs[valueArgIndex];
/*     */     }
/*     */     
/* 470 */     validateParams(field, Type.VOID_TYPE, field.getArrayArgs(new Type[] { elementType }));
/* 471 */     injectArrayRedirect(field, varNode, "array setter");
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
/*     */   private void injectArrayRedirect(RedirectedFieldData field, AbstractInsnNode varNode, String type) {
/* 486 */     if (varNode == null) {
/* 487 */       String advice = "";
/* 488 */       throw new InvalidInjectionException(this.info, String.format("Array element %s on %s could not locate a matching %s instruction in %s. %s", new Object[] { this.annotationType, this, type, field.target, advice }));
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 493 */     Target.Extension extraStack = field.target.extendStack();
/*     */     
/* 495 */     if (!this.isStatic) {
/* 496 */       VarInsnNode loadThis = new VarInsnNode(25, 0);
/* 497 */       field.target.insns.insert((AbstractInsnNode)field.node, (AbstractInsnNode)loadThis);
/* 498 */       field.target.insns.insert((AbstractInsnNode)loadThis, (AbstractInsnNode)new InsnNode(95));
/* 499 */       extraStack.add();
/*     */     } 
/*     */     
/* 502 */     InsnList insns = new InsnList();
/* 503 */     if (field.captureTargetArgs > 0) {
/* 504 */       pushArgs(field.target.arguments, insns, field.target.getArgIndices(), 0, field.captureTargetArgs, extraStack);
/*     */     }
/* 506 */     extraStack.apply();
/* 507 */     AbstractInsnNode champion = invokeHandler(insns);
/* 508 */     if (field.coerceReturnType && field.type.getSort() >= 9) {
/* 509 */       insns.add((AbstractInsnNode)new TypeInsnNode(192, field.elementType.getInternalName()));
/*     */     }
/* 511 */     field.target.replaceNode(varNode, champion, insns);
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
/*     */   private void injectAtScalarField(RedirectedFieldData field) {
/* 524 */     AbstractInsnNode invoke = null;
/* 525 */     InsnList insns = new InsnList();
/* 526 */     if (field.isGetter) {
/* 527 */       invoke = injectAtGetField(field, insns);
/* 528 */     } else if (field.isSetter) {
/* 529 */       invoke = injectAtPutField(field, insns);
/*     */     } else {
/* 531 */       throw new InvalidInjectionException(this.info, String.format("Unspported opcode %s for %s", new Object[] {
/* 532 */               Bytecode.getOpcodeName(field.opcode), this.info
/*     */             }));
/*     */     } 
/* 535 */     field.target.replaceNode((AbstractInsnNode)field.node, invoke, insns);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private AbstractInsnNode injectAtGetField(RedirectedFieldData field, InsnList insns) {
/* 545 */     validateParams(field, field.type, new Type[] { field.isStatic ? null : field.owner });
/*     */     
/* 547 */     Target.Extension extraStack = field.target.extendStack();
/*     */     
/* 549 */     if (!this.isStatic) {
/* 550 */       extraStack.add();
/* 551 */       insns.add((AbstractInsnNode)new VarInsnNode(25, 0));
/* 552 */       if (!field.isStatic) {
/* 553 */         insns.add((AbstractInsnNode)new InsnNode(95));
/*     */       }
/*     */     } 
/*     */     
/* 557 */     if (field.captureTargetArgs > 0) {
/* 558 */       pushArgs(field.target.arguments, insns, field.target.getArgIndices(), 0, field.captureTargetArgs, extraStack);
/*     */     }
/*     */     
/* 561 */     extraStack.apply();
/* 562 */     AbstractInsnNode champion = invokeHandler(insns);
/* 563 */     if (field.coerceReturnType && field.type.getSort() >= 9) {
/* 564 */       insns.add((AbstractInsnNode)new TypeInsnNode(192, field.type.getInternalName()));
/*     */     }
/* 566 */     return champion;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private AbstractInsnNode injectAtPutField(RedirectedFieldData field, InsnList insns) {
/* 576 */     validateParams(field, Type.VOID_TYPE, new Type[] { field.isStatic ? null : field.owner, field.type });
/*     */     
/* 578 */     Target.Extension extraStack = field.target.extendStack();
/*     */     
/* 580 */     if (!this.isStatic) {
/* 581 */       if (field.isStatic) {
/* 582 */         insns.add((AbstractInsnNode)new VarInsnNode(25, 0));
/* 583 */         insns.add((AbstractInsnNode)new InsnNode(95));
/*     */       } else {
/* 585 */         extraStack.add();
/* 586 */         int marshallVar = field.target.allocateLocals(field.type.getSize());
/* 587 */         insns.add((AbstractInsnNode)new VarInsnNode(field.type.getOpcode(54), marshallVar));
/* 588 */         insns.add((AbstractInsnNode)new VarInsnNode(25, 0));
/* 589 */         insns.add((AbstractInsnNode)new InsnNode(95));
/* 590 */         insns.add((AbstractInsnNode)new VarInsnNode(field.type.getOpcode(21), marshallVar));
/*     */       } 
/*     */     }
/*     */     
/* 594 */     if (field.captureTargetArgs > 0) {
/* 595 */       pushArgs(field.target.arguments, insns, field.target.getArgIndices(), 0, field.captureTargetArgs, extraStack);
/*     */     }
/*     */     
/* 598 */     extraStack.apply();
/* 599 */     return invokeHandler(insns);
/*     */   }
/*     */   
/*     */   protected void injectAtConstructor(Target target, InjectionNodes.InjectionNode node) {
/* 603 */     ConstructorRedirectData meta = (ConstructorRedirectData)node.getDecoration("ctor");
/*     */     
/* 605 */     if (meta == null)
/*     */     {
/* 607 */       throw new InvalidInjectionException(this.info, String.format("%s ctor redirector has no metadata, the injector failed a preprocessing phase", new Object[] { this.annotationType }));
/*     */     }
/*     */ 
/*     */     
/* 611 */     TypeInsnNode newNode = (TypeInsnNode)node.getCurrentTarget();
/* 612 */     AbstractInsnNode dupNode = target.get(target.indexOf((AbstractInsnNode)newNode) + 1);
/* 613 */     MethodInsnNode initNode = target.findInitNodeFor(newNode);
/*     */     
/* 615 */     if (initNode == null) {
/* 616 */       meta.throwOrCollect(new InvalidInjectionException(this.info, String.format("%s ctor invocation was not found in %s", new Object[] { this.annotationType, target })));
/*     */ 
/*     */       
/*     */       return;
/*     */     } 
/*     */     
/* 622 */     boolean isAssigned = (dupNode.getOpcode() == 89);
/* 623 */     RedirectedInvokeData ctor = new RedirectedInvokeData(target, initNode);
/* 624 */     ctor.description = "factory";
/*     */     try {
/* 626 */       validateParams(ctor, Type.getObjectType(newNode.desc), ctor.targetArgs);
/* 627 */     } catch (InvalidInjectionException ex) {
/* 628 */       meta.throwOrCollect(ex);
/*     */       
/*     */       return;
/*     */     } 
/* 632 */     if (isAssigned) {
/* 633 */       target.removeNode(dupNode);
/*     */     }
/*     */     
/* 636 */     if (this.isStatic) {
/* 637 */       target.removeNode((AbstractInsnNode)newNode);
/*     */     } else {
/* 639 */       target.replaceNode((AbstractInsnNode)newNode, (AbstractInsnNode)new VarInsnNode(25, 0));
/*     */     } 
/*     */     
/* 642 */     Target.Extension extraStack = target.extendStack();
/* 643 */     InsnList insns = new InsnList();
/* 644 */     if (ctor.captureTargetArgs > 0) {
/* 645 */       pushArgs(target.arguments, insns, target.getArgIndices(), 0, ctor.captureTargetArgs, extraStack);
/*     */     }
/*     */     
/* 648 */     invokeHandler(insns);
/* 649 */     if (ctor.coerceReturnType) {
/* 650 */       insns.add((AbstractInsnNode)new TypeInsnNode(192, newNode.desc));
/*     */     }
/* 652 */     extraStack.apply();
/*     */     
/* 654 */     if (isAssigned) {
/*     */ 
/*     */ 
/*     */       
/* 658 */       doNullCheck(insns, extraStack, "constructor handler", newNode.desc.replace('/', '.'));
/*     */     } else {
/*     */       
/* 661 */       insns.add((AbstractInsnNode)new InsnNode(87));
/*     */     } 
/*     */     
/* 664 */     extraStack.apply();
/* 665 */     target.replaceNode((AbstractInsnNode)initNode, insns);
/* 666 */     meta.injected++;
/*     */   }
/*     */   
/*     */   protected void injectAtInstanceOf(Target target, InjectionNodes.InjectionNode node) {
/* 670 */     injectAtInstanceOf(target, (TypeInsnNode)node.getCurrentTarget());
/*     */   }
/*     */   
/*     */   protected void injectAtInstanceOf(Target target, TypeInsnNode typeNode) {
/* 674 */     if (this.returnType.getSort() == 1) {
/* 675 */       redirectInstanceOf(target, typeNode, false);
/*     */       
/*     */       return;
/*     */     } 
/* 679 */     if (this.returnType.equals(Type.getType("Ljava/lang/Class;"))) {
/* 680 */       redirectInstanceOf(target, typeNode, true);
/*     */ 
/*     */ 
/*     */ 
/*     */       
/*     */       return;
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 690 */     throw new InvalidInjectionException(this.info, String.format("%s on %s has an invalid signature. Found unexpected return type %s. INSTANCEOF handler expects (Ljava/lang/Object;Ljava/lang/Class;)Z or (Ljava/lang/Object;Ljava/lang/Class;)Ljava/lang/Class;", new Object[] { this.annotationType, this, 
/*     */             
/* 692 */             SignaturePrinter.getTypeName(this.returnType) }));
/*     */   }
/*     */   
/*     */   private void redirectInstanceOf(Target target, TypeInsnNode typeNode, boolean dynamic) {
/* 696 */     Target.Extension extraStack = target.extendStack();
/* 697 */     InsnList insns = new InsnList();
/* 698 */     Injector.InjectorData handler = new Injector.InjectorData(target, "instanceof handler", false);
/* 699 */     validateParams(handler, this.returnType, new Type[] { Type.getType("Ljava/lang/Object;"), Type.getType("Ljava/lang/Class;") });
/*     */     
/* 701 */     if (dynamic) {
/* 702 */       insns.add((AbstractInsnNode)new InsnNode(89));
/* 703 */       extraStack.add();
/*     */     } 
/*     */     
/* 706 */     if (!this.isStatic) {
/* 707 */       insns.add((AbstractInsnNode)new VarInsnNode(25, 0));
/* 708 */       insns.add((AbstractInsnNode)new InsnNode(95));
/* 709 */       extraStack.add();
/*     */     } 
/*     */ 
/*     */     
/* 713 */     insns.add((AbstractInsnNode)new LdcInsnNode(Type.getObjectType(typeNode.desc)));
/* 714 */     extraStack.add();
/*     */     
/* 716 */     if (handler.captureTargetArgs > 0) {
/* 717 */       pushArgs(target.arguments, insns, target.getArgIndices(), 0, handler.captureTargetArgs, extraStack);
/*     */     }
/*     */     
/* 720 */     AbstractInsnNode champion = invokeHandler(insns);
/*     */     
/* 722 */     if (dynamic) {
/*     */ 
/*     */       
/* 725 */       doNullCheck(insns, extraStack, "instanceof handler", "class type");
/*     */ 
/*     */       
/* 728 */       checkIsAssignableFrom(insns, extraStack);
/*     */     } 
/*     */     
/* 731 */     target.replaceNode((AbstractInsnNode)typeNode, champion, insns);
/* 732 */     extraStack.apply();
/*     */   }
/*     */   
/*     */   private void checkIsAssignableFrom(InsnList insns, Target.Extension extraStack) {
/* 736 */     LabelNode objectIsNull = new LabelNode();
/* 737 */     LabelNode checkComplete = new LabelNode();
/*     */ 
/*     */ 
/*     */     
/* 741 */     insns.add((AbstractInsnNode)new InsnNode(95));
/* 742 */     insns.add((AbstractInsnNode)new InsnNode(89));
/* 743 */     extraStack.add();
/* 744 */     insns.add((AbstractInsnNode)new JumpInsnNode(198, objectIsNull));
/*     */ 
/*     */     
/* 747 */     insns.add((AbstractInsnNode)new MethodInsnNode(182, "java/lang/Object", "getClass", "()Ljava/lang/Class;", false));
/*     */     
/* 749 */     insns.add((AbstractInsnNode)new MethodInsnNode(182, "java/lang/Class", "isAssignableFrom", "(Ljava/lang/Class;)Z", false));
/*     */     
/* 751 */     insns.add((AbstractInsnNode)new JumpInsnNode(167, checkComplete));
/*     */     
/* 753 */     insns.add((AbstractInsnNode)objectIsNull);
/* 754 */     insns.add((AbstractInsnNode)new InsnNode(87));
/* 755 */     insns.add((AbstractInsnNode)new InsnNode(87));
/* 756 */     insns.add((AbstractInsnNode)new InsnNode(3));
/* 757 */     insns.add((AbstractInsnNode)checkComplete);
/* 758 */     extraStack.add();
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
/*     */   private void doNullCheck(InsnList insns, Target.Extension extraStack, String type, String value) {
/* 774 */     LabelNode nullCheckSucceeded = new LabelNode();
/* 775 */     insns.add((AbstractInsnNode)new InsnNode(89));
/* 776 */     insns.add((AbstractInsnNode)new JumpInsnNode(199, nullCheckSucceeded));
/* 777 */     throwException(insns, "java/lang/NullPointerException", String.format("%s %s %s returned null for %s", new Object[] { this.annotationType, type, this, value }));
/*     */     
/* 779 */     insns.add((AbstractInsnNode)nullCheckSucceeded);
/* 780 */     extraStack.add();
/*     */   }
/*     */ }


/* Location:              C:\Users\DuduM\OneDrive\Área de Trabalho\mw10\modularwarfare-shining-2023.2.4.4f-fix10.jar!\org\spongepowered\asm\mixin\injection\invoke\RedirectInjector.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */