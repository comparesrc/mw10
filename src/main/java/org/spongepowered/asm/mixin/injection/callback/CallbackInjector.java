/*     */ package org.spongepowered.asm.mixin.injection.callback;
/*     */ 
/*     */ import com.google.common.base.Strings;
/*     */ import java.util.ArrayList;
/*     */ import java.util.HashMap;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.Set;
/*     */ import org.objectweb.asm.Type;
/*     */ import org.objectweb.asm.tree.AbstractInsnNode;
/*     */ import org.objectweb.asm.tree.InsnList;
/*     */ import org.objectweb.asm.tree.InsnNode;
/*     */ import org.objectweb.asm.tree.JumpInsnNode;
/*     */ import org.objectweb.asm.tree.LabelNode;
/*     */ import org.objectweb.asm.tree.LdcInsnNode;
/*     */ import org.objectweb.asm.tree.LocalVariableNode;
/*     */ import org.objectweb.asm.tree.MethodInsnNode;
/*     */ import org.objectweb.asm.tree.MethodNode;
/*     */ import org.objectweb.asm.tree.TypeInsnNode;
/*     */ import org.objectweb.asm.tree.VarInsnNode;
/*     */ import org.spongepowered.asm.mixin.injection.Coerce;
/*     */ import org.spongepowered.asm.mixin.injection.IInjectionPointContext;
/*     */ import org.spongepowered.asm.mixin.injection.InjectionPoint;
/*     */ import org.spongepowered.asm.mixin.injection.Surrogate;
/*     */ import org.spongepowered.asm.mixin.injection.code.Injector;
/*     */ import org.spongepowered.asm.mixin.injection.struct.InjectionInfo;
/*     */ import org.spongepowered.asm.mixin.injection.struct.InjectionNodes;
/*     */ import org.spongepowered.asm.mixin.injection.struct.Target;
/*     */ import org.spongepowered.asm.mixin.injection.throwables.InjectionError;
/*     */ import org.spongepowered.asm.mixin.injection.throwables.InvalidInjectionException;
/*     */ import org.spongepowered.asm.util.Annotations;
/*     */ import org.spongepowered.asm.util.Bytecode;
/*     */ import org.spongepowered.asm.util.Locals;
/*     */ import org.spongepowered.asm.util.PrettyPrinter;
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
/*     */ public class CallbackInjector
/*     */   extends Injector
/*     */ {
/*     */   private final boolean cancellable;
/*     */   private final LocalCapture localCapture;
/*     */   private final String identifier;
/*     */   
/*     */   private class Callback
/*     */     extends InsnList
/*     */   {
/*     */     private final MethodNode handler;
/*     */     private final AbstractInsnNode head;
/*     */     final Target target;
/*     */     final InjectionNodes.InjectionNode node;
/*     */     final LocalVariableNode[] locals;
/*     */     final Type[] localTypes;
/*     */     final int frameSize;
/*     */     final int extraArgs;
/*     */     final boolean canCaptureLocals;
/*     */     final boolean isAtReturn;
/*     */     final String desc;
/*     */     final String descl;
/*     */     final String[] argNames;
/*     */     Target.Extension ctor;
/*     */     Target.Extension invoke;
/* 157 */     private int marshalVar = -1;
/*     */ 
/*     */ 
/*     */     
/*     */     private boolean captureArgs = true;
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     Callback(MethodNode handler, Target target, InjectionNodes.InjectionNode node, LocalVariableNode[] locals, boolean captureLocals) {
/* 167 */       this.handler = handler;
/* 168 */       this.target = target;
/* 169 */       this.head = target.insns.getFirst();
/* 170 */       this.node = node;
/* 171 */       this.locals = locals;
/* 172 */       this.localTypes = (locals != null) ? new Type[locals.length] : null;
/* 173 */       this.frameSize = Bytecode.getFirstNonArgLocalIndex(target.arguments, !target.isStatic);
/* 174 */       List<String> argNames = null;
/*     */       
/* 176 */       if (locals != null) {
/* 177 */         int baseArgIndex = CallbackInjector.this.isStatic() ? 0 : 1;
/* 178 */         argNames = new ArrayList<>();
/* 179 */         for (int l = 0; l <= locals.length; l++) {
/* 180 */           if (l == this.frameSize) {
/* 181 */             argNames.add((target.returnType == Type.VOID_TYPE) ? "ci" : "cir");
/*     */           }
/* 183 */           if (l < locals.length && locals[l] != null) {
/* 184 */             this.localTypes[l] = Type.getType((locals[l]).desc);
/* 185 */             if (l >= baseArgIndex) {
/* 186 */               argNames.add(CallbackInjector.meltSnowman(l, (locals[l]).name));
/*     */             }
/*     */           } 
/*     */         } 
/*     */       } 
/*     */ 
/*     */       
/* 193 */       Type[] handlerArgs = Type.getArgumentTypes(this.handler.desc);
/* 194 */       this.extraArgs = Math.max(0, handlerArgs.length - target.arguments.length - 1);
/* 195 */       this.argNames = (argNames != null) ? argNames.<String>toArray(new String[argNames.size()]) : null;
/* 196 */       this.canCaptureLocals = (captureLocals && locals != null && locals.length > this.frameSize);
/* 197 */       this.isAtReturn = (this.node.getCurrentTarget() instanceof InsnNode && isValueReturnOpcode(this.node.getCurrentTarget().getOpcode()));
/* 198 */       this.desc = target.getCallbackDescriptor(this.localTypes, target.arguments);
/* 199 */       this.descl = target.getCallbackDescriptor(true, this.localTypes, target.arguments, this.frameSize, this.extraArgs);
/*     */ 
/*     */       
/* 202 */       this.invoke = target.extendStack();
/* 203 */       this.ctor = target.extendStack();
/*     */       
/* 205 */       this.invoke.add(target.arguments.length);
/* 206 */       if (this.canCaptureLocals) {
/* 207 */         this.invoke.add(this.localTypes.length - this.frameSize);
/*     */       }
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     private boolean isValueReturnOpcode(int opcode) {
/* 219 */       return (opcode >= 172 && opcode < 177);
/*     */     }
/*     */     
/*     */     String getDescriptor() {
/* 223 */       return this.canCaptureLocals ? this.descl : this.desc;
/*     */     }
/*     */     
/*     */     String getDescriptorWithAllLocals() {
/* 227 */       return this.target.getCallbackDescriptor(true, this.localTypes, this.target.arguments, this.frameSize, 32767);
/*     */     }
/*     */     
/*     */     String getCallbackInfoConstructorDescriptor() {
/* 231 */       return this.isAtReturn ? CallbackInfo.getConstructorDescriptor(this.target.returnType) : CallbackInfo.getConstructorDescriptor();
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     void add(AbstractInsnNode insn, boolean ctorStack, boolean invokeStack) {
/* 243 */       add(insn, ctorStack, invokeStack, false);
/*     */     }
/*     */     
/*     */     void add(AbstractInsnNode insn, boolean ctorStack, boolean invokeStack, boolean head) {
/* 247 */       if (head) {
/* 248 */         this.target.insns.insertBefore(this.head, insn);
/*     */       } else {
/* 250 */         add(insn);
/*     */       } 
/* 252 */       if (ctorStack) {
/* 253 */         this.ctor.add();
/*     */       }
/* 255 */       if (invokeStack) {
/* 256 */         this.invoke.add();
/*     */       }
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     void inject() {
/* 265 */       this.target.insertBefore(this.node, this);
/* 266 */       this.invoke.apply();
/* 267 */       this.ctor.apply();
/*     */     }
/*     */     
/*     */     boolean checkDescriptor(String desc) {
/* 271 */       if (getDescriptor().equals(desc)) {
/* 272 */         return true;
/*     */       }
/*     */       
/* 275 */       if (this.target.getSimpleCallbackDescriptor().equals(desc) && !this.canCaptureLocals) {
/* 276 */         this.captureArgs = false;
/* 277 */         return true;
/*     */       } 
/*     */       
/* 280 */       Type[] inTypes = Type.getArgumentTypes(desc);
/* 281 */       Type[] myTypes = Type.getArgumentTypes(this.descl);
/*     */       
/* 283 */       if (inTypes.length != myTypes.length) {
/* 284 */         return false;
/*     */       }
/*     */       
/* 287 */       for (int arg = 0; arg < myTypes.length; arg++) {
/* 288 */         Type type = inTypes[arg];
/* 289 */         if (!type.equals(myTypes[arg])) {
/*     */ 
/*     */ 
/*     */           
/* 293 */           if (type.getSort() == 9) {
/* 294 */             return false;
/*     */           }
/*     */           
/* 297 */           if (Annotations.getInvisibleParameter(this.handler, Coerce.class, arg) == null) {
/* 298 */             return false;
/*     */           }
/*     */           
/* 301 */           if (!Injector.canCoerce(inTypes[arg], myTypes[arg]))
/*     */           {
/*     */ 
/*     */             
/* 305 */             return false;
/*     */           }
/*     */         } 
/*     */       } 
/*     */       
/* 310 */       return true;
/*     */     }
/*     */     
/*     */     boolean captureArgs() {
/* 314 */       return this.captureArgs;
/*     */     }
/*     */     
/*     */     int marshalVar() {
/* 318 */       if (this.marshalVar < 0) {
/* 319 */         this.marshalVar = this.target.allocateLocal();
/*     */       }
/*     */       
/* 322 */       return this.marshalVar;
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
/* 345 */   private final Map<Integer, String> ids = new HashMap<>();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 351 */   private int totalInjections = 0;
/* 352 */   private int callbackInfoVar = -1;
/*     */ 
/*     */   
/*     */   private String lastId;
/*     */ 
/*     */   
/*     */   private String lastDesc;
/*     */   
/*     */   private Target lastTarget;
/*     */   
/*     */   private String callbackInfoClass;
/*     */ 
/*     */   
/*     */   public CallbackInjector(InjectionInfo info, boolean cancellable, LocalCapture localCapture, String identifier) {
/* 366 */     super(info, "@Inject");
/* 367 */     this.cancellable = cancellable;
/* 368 */     this.localCapture = localCapture;
/* 369 */     this.identifier = identifier;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void sanityCheck(Target target, List<InjectionPoint> injectionPoints) {
/* 379 */     super.sanityCheck(target, injectionPoints);
/* 380 */     checkTargetModifiers(target, true);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void addTargetNode(Target target, List<InjectionNodes.InjectionNode> myNodes, AbstractInsnNode node, Set<InjectionPoint> nominators) {
/* 390 */     InjectionNodes.InjectionNode injectionNode = target.addInjectionNode(node);
/*     */     
/* 392 */     for (InjectionPoint ip : nominators) {
/*     */       
/*     */       try {
/* 395 */         checkTargetForNode(target, injectionNode, ip.getTargetRestriction((IInjectionPointContext)this.info));
/* 396 */       } catch (InvalidInjectionException ex) {
/* 397 */         throw new InvalidInjectionException(this.info, String.format("%s selector %s", new Object[] { ip, ex.getMessage() }));
/*     */       } 
/*     */       
/* 400 */       String id = ip.getId();
/* 401 */       if (Strings.isNullOrEmpty(id)) {
/*     */         continue;
/*     */       }
/*     */       
/* 405 */       String existingId = this.ids.get(Integer.valueOf(injectionNode.getId()));
/* 406 */       if (existingId != null && !existingId.equals(id)) {
/* 407 */         Injector.logger.warn("Conflicting id for {} insn in {}, found id {} on {}, previously defined as {}", new Object[] { Bytecode.getOpcodeName(node), target
/* 408 */               .toString(), id, this.info, existingId });
/*     */         
/*     */         break;
/*     */       } 
/* 412 */       this.ids.put(Integer.valueOf(injectionNode.getId()), id);
/*     */     } 
/*     */     
/* 415 */     myNodes.add(injectionNode);
/* 416 */     this.totalInjections++;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void inject(Target target, InjectionNodes.InjectionNode node) {
/* 426 */     LocalVariableNode[] locals = null;
/*     */     
/* 428 */     if (this.localCapture.isCaptureLocals() || this.localCapture.isPrintLocals()) {
/* 429 */       locals = Locals.getLocalsAt(this.classNode, target.method, node.getCurrentTarget());
/* 430 */       for (int j = 0; j < locals.length; j++) {
/* 431 */         if (locals[j] != null && (locals[j]).desc != null && (locals[j]).desc.startsWith("Lorg/spongepowered/asm/mixin/injection/callback/")) {
/* 432 */           locals[j] = null;
/*     */         }
/*     */       } 
/*     */     } 
/*     */     
/* 437 */     inject(new Callback(this.methodNode, target, node, locals, this.localCapture.isCaptureLocals()));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void inject(Callback callback) {
/* 446 */     if (this.localCapture.isPrintLocals()) {
/* 447 */       printLocals(callback);
/* 448 */       this.info.addCallbackInvocation(this.methodNode);
/*     */ 
/*     */       
/*     */       return;
/*     */     } 
/*     */ 
/*     */     
/* 455 */     MethodNode callbackMethod = this.methodNode;
/*     */     
/* 457 */     if (!callback.checkDescriptor(this.methodNode.desc)) {
/* 458 */       if (this.info.getTargets().size() > 1) {
/*     */         return;
/*     */       }
/*     */       
/* 462 */       if (callback.canCaptureLocals) {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 468 */         MethodNode surrogateHandler = Bytecode.findMethod(this.classNode, this.methodNode.name, callback.getDescriptor());
/* 469 */         if (surrogateHandler != null && Annotations.getVisible(surrogateHandler, Surrogate.class) != null) {
/*     */           
/* 471 */           callbackMethod = surrogateHandler;
/*     */         } else {
/*     */           
/* 474 */           String message = generateBadLVTMessage(callback);
/*     */           
/* 476 */           switch (this.localCapture) {
/*     */             case CAPTURE_FAILEXCEPTION:
/* 478 */               Injector.logger.error("Injection error: {}", new Object[] { message });
/* 479 */               callbackMethod = generateErrorMethod(callback, "org/spongepowered/asm/mixin/injection/throwables/InjectionError", message);
/*     */               break;
/*     */             
/*     */             case CAPTURE_FAILSOFT:
/* 483 */               Injector.logger.warn("Injection warning: {}", new Object[] { message });
/*     */               return;
/*     */             default:
/* 486 */               Injector.logger.error("Critical injection failure: {}", new Object[] { message });
/* 487 */               throw new InjectionError(message);
/*     */           } 
/*     */         
/*     */         } 
/*     */       } else {
/* 492 */         String returnableSig = this.methodNode.desc.replace("Lorg/spongepowered/asm/mixin/injection/callback/CallbackInfo;", "Lorg/spongepowered/asm/mixin/injection/callback/CallbackInfoReturnable;");
/*     */ 
/*     */ 
/*     */         
/* 496 */         if (callback.checkDescriptor(returnableSig))
/*     */         {
/*     */           
/* 499 */           throw new InvalidInjectionException(this.info, "Invalid descriptor on " + this.info + "! CallbackInfoReturnable is required!");
/*     */         }
/*     */         
/* 502 */         MethodNode surrogateHandler = Bytecode.findMethod(this.classNode, this.methodNode.name, callback.getDescriptor());
/* 503 */         if (surrogateHandler != null && Annotations.getVisible(surrogateHandler, Surrogate.class) != null) {
/*     */           
/* 505 */           callbackMethod = surrogateHandler;
/*     */         } else {
/* 507 */           throw new InvalidInjectionException(this.info, "Invalid descriptor on " + this.info + "! Expected " + callback.getDescriptor() + " but found " + this.methodNode.desc);
/*     */         } 
/*     */       } 
/*     */     } 
/*     */ 
/*     */     
/* 513 */     dupReturnValue(callback);
/* 514 */     if (this.cancellable || this.totalInjections > 1) {
/* 515 */       createCallbackInfo(callback, true);
/*     */     }
/* 517 */     invokeCallback(callback, callbackMethod);
/* 518 */     injectCancellationCode(callback);
/*     */     
/* 520 */     callback.inject();
/* 521 */     this.info.notifyInjected(callback.target);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private String generateBadLVTMessage(Callback callback) {
/* 531 */     int position = callback.target.indexOf(callback.node);
/* 532 */     List<String> expected = summariseLocals(this.methodNode.desc, callback.target.arguments.length + 1);
/* 533 */     List<String> found = summariseLocals(callback.getDescriptor(), callback.frameSize + (callback.target.isStatic ? 1 : 0));
/* 534 */     if (expected.equals(found)) {
/* 535 */       return String.format("Invalid descriptor on %s! Expected %s but found %s", new Object[] { this.info, callback.getDescriptor(), this.methodNode.desc });
/*     */     }
/* 537 */     return String.format("LVT in %s has incompatible changes at opcode %d in callback %s.\nExpected: %s\n   Found: %s", new Object[] { callback.target, 
/* 538 */           Integer.valueOf(position), this, expected, found });
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
/*     */   private MethodNode generateErrorMethod(Callback callback, String errorClass, String message) {
/* 550 */     MethodNode method = this.info.addMethod(this.methodNode.access, this.methodNode.name + "$missing", callback.getDescriptor());
/* 551 */     method.maxLocals = Bytecode.getFirstNonArgLocalIndex(Type.getArgumentTypes(callback.getDescriptor()), !this.isStatic);
/* 552 */     method.maxStack = 3;
/* 553 */     InsnList insns = method.instructions;
/* 554 */     insns.add((AbstractInsnNode)new TypeInsnNode(187, errorClass));
/* 555 */     insns.add((AbstractInsnNode)new InsnNode(89));
/* 556 */     insns.add((AbstractInsnNode)new LdcInsnNode(message));
/* 557 */     insns.add((AbstractInsnNode)new MethodInsnNode(183, errorClass, "<init>", "(Ljava/lang/String;)V", false));
/* 558 */     insns.add((AbstractInsnNode)new InsnNode(191));
/* 559 */     return method;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void printLocals(Callback callback) {
/* 568 */     Type[] args = Type.getArgumentTypes(callback.getDescriptorWithAllLocals());
/* 569 */     SignaturePrinter methodSig = new SignaturePrinter(callback.target.method, callback.argNames);
/* 570 */     SignaturePrinter handlerSig = new SignaturePrinter(this.info.getMethodName(), callback.target.returnType, args, callback.argNames);
/* 571 */     handlerSig.setModifiers(this.methodNode);
/*     */     
/* 573 */     PrettyPrinter printer = new PrettyPrinter();
/* 574 */     printer.kv("Target Class", this.classNode.name.replace('/', '.'));
/* 575 */     printer.kv("Target Method", methodSig);
/* 576 */     printer.kv("Target Max LOCALS", Integer.valueOf(callback.target.getMaxLocals()));
/* 577 */     printer.kv("Initial Frame Size", Integer.valueOf(callback.frameSize));
/* 578 */     printer.kv("Callback Name", this.info.getMethodName());
/* 579 */     printer.kv("Instruction", "%s %s", new Object[] { callback.node.getClass().getSimpleName(), 
/* 580 */           Bytecode.getOpcodeName(callback.node.getCurrentTarget().getOpcode()) });
/* 581 */     printer.hr();
/* 582 */     if (callback.locals.length > callback.frameSize) {
/* 583 */       printer.add("  %s  %20s  %s", new Object[] { "LOCAL", "TYPE", "NAME" });
/* 584 */       for (int l = 0; l < callback.locals.length; l++) {
/* 585 */         String marker = (l == callback.frameSize) ? ">" : " ";
/* 586 */         if (callback.locals[l] != null) {
/* 587 */           printer.add("%s [%3d]  %20s  %-50s %s", new Object[] { marker, Integer.valueOf(l), SignaturePrinter.getTypeName(callback.localTypes[l], false), 
/* 588 */                 meltSnowman(l, (callback.locals[l]).name), (l >= callback.frameSize) ? "<capture>" : "" });
/*     */         } else {
/* 590 */           boolean isTop = (l > 0 && callback.localTypes[l - 1] != null && callback.localTypes[l - 1].getSize() > 1);
/* 591 */           printer.add("%s [%3d]  %20s", new Object[] { marker, Integer.valueOf(l), isTop ? "<top>" : "-" });
/*     */         } 
/*     */       } 
/* 594 */       printer.hr();
/*     */     } 
/* 596 */     printer.add().add("/**").add(" * Expected callback signature").add(" * /");
/* 597 */     printer.add("%s {", new Object[] { handlerSig });
/* 598 */     printer.add("    // Method body").add("}").add().print(System.err);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void createCallbackInfo(Callback callback, boolean store) {
/* 607 */     if (callback.target != this.lastTarget) {
/* 608 */       this.lastId = null;
/* 609 */       this.lastDesc = null;
/*     */     } 
/* 611 */     this.lastTarget = callback.target;
/*     */     
/* 613 */     String id = getIdentifier(callback);
/* 614 */     String desc = callback.getCallbackInfoConstructorDescriptor();
/*     */ 
/*     */     
/* 617 */     if (id.equals(this.lastId) && desc.equals(this.lastDesc) && !callback.isAtReturn && !this.cancellable) {
/*     */       return;
/*     */     }
/*     */     
/* 621 */     instanceCallbackInfo(callback, id, desc, store);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void loadOrCreateCallbackInfo(Callback callback) {
/* 628 */     if (this.cancellable || this.totalInjections > 1) {
/* 629 */       callback.add((AbstractInsnNode)new VarInsnNode(25, this.callbackInfoVar), false, true);
/*     */     } else {
/* 631 */       createCallbackInfo(callback, false);
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
/*     */   private void dupReturnValue(Callback callback) {
/* 644 */     if (!callback.isAtReturn) {
/*     */       return;
/*     */     }
/*     */     
/* 648 */     int dupCode = (callback.target.returnType.getSize() == 1) ? 89 : 92;
/* 649 */     callback.add((AbstractInsnNode)new InsnNode(dupCode));
/* 650 */     callback.add((AbstractInsnNode)new VarInsnNode(callback.target.returnType.getOpcode(54), callback.marshalVar()));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void instanceCallbackInfo(Callback callback, String id, String desc, boolean store) {
/* 661 */     this.lastId = id;
/* 662 */     this.lastDesc = desc;
/* 663 */     this.callbackInfoVar = callback.marshalVar();
/* 664 */     this.callbackInfoClass = callback.target.getCallbackInfoClass();
/*     */ 
/*     */ 
/*     */     
/* 668 */     boolean head = (store && this.totalInjections > 1 && !callback.isAtReturn && !this.cancellable);
/*     */     
/* 670 */     callback.add((AbstractInsnNode)new TypeInsnNode(187, this.callbackInfoClass), true, !store, head);
/* 671 */     callback.add((AbstractInsnNode)new InsnNode(89), true, true, head);
/* 672 */     callback.add((AbstractInsnNode)new LdcInsnNode(id), true, !store, head);
/* 673 */     callback.add((AbstractInsnNode)new InsnNode(this.cancellable ? 4 : 3), true, !store, head);
/*     */     
/* 675 */     if (callback.isAtReturn) {
/* 676 */       callback.add((AbstractInsnNode)new VarInsnNode(callback.target.returnType.getOpcode(21), callback.marshalVar()), true, !store);
/* 677 */       callback.add((AbstractInsnNode)new MethodInsnNode(183, this.callbackInfoClass, "<init>", desc, false));
/*     */     } else {
/*     */       
/* 680 */       callback.add((AbstractInsnNode)new MethodInsnNode(183, this.callbackInfoClass, "<init>", desc, false), false, false, head);
/*     */     } 
/*     */ 
/*     */     
/* 684 */     if (store) {
/* 685 */       callback.target.addLocalVariable(this.callbackInfoVar, "callbackInfo" + this.callbackInfoVar, "L" + this.callbackInfoClass + ";");
/* 686 */       callback.add((AbstractInsnNode)new VarInsnNode(58, this.callbackInfoVar), false, false, head);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void invokeCallback(Callback callback, MethodNode callbackMethod) {
/* 695 */     if (!this.isStatic) {
/* 696 */       callback.add((AbstractInsnNode)new VarInsnNode(25, 0), false, true);
/*     */     }
/*     */ 
/*     */     
/* 700 */     if (callback.captureArgs()) {
/* 701 */       Bytecode.loadArgs(callback.target.arguments, callback, this.isStatic ? 0 : 1, -1);
/*     */     }
/*     */ 
/*     */     
/* 705 */     loadOrCreateCallbackInfo(callback);
/*     */ 
/*     */     
/* 708 */     if (callback.canCaptureLocals) {
/* 709 */       Locals.loadLocals(callback.localTypes, callback, callback.frameSize, callback.extraArgs);
/*     */     }
/*     */ 
/*     */     
/* 713 */     invokeHandler(callback, callbackMethod);
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
/*     */   private String getIdentifier(Callback callback) {
/* 725 */     String baseId = Strings.isNullOrEmpty(this.identifier) ? callback.target.method.name : this.identifier;
/* 726 */     String locationId = this.ids.get(Integer.valueOf(callback.node.getId()));
/* 727 */     return baseId + (Strings.isNullOrEmpty(locationId) ? "" : (":" + locationId));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void injectCancellationCode(Callback callback) {
/* 736 */     if (!this.cancellable) {
/*     */       return;
/*     */     }
/*     */     
/* 740 */     callback.add((AbstractInsnNode)new VarInsnNode(25, this.callbackInfoVar));
/* 741 */     callback.add((AbstractInsnNode)new MethodInsnNode(182, this.callbackInfoClass, CallbackInfo.getIsCancelledMethodName(), 
/* 742 */           CallbackInfo.getIsCancelledMethodSig(), false));
/*     */     
/* 744 */     LabelNode notCancelled = new LabelNode();
/* 745 */     callback.add((AbstractInsnNode)new JumpInsnNode(153, notCancelled));
/*     */ 
/*     */ 
/*     */     
/* 749 */     injectReturnCode(callback);
/*     */     
/* 751 */     callback.add((AbstractInsnNode)notCancelled);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void injectReturnCode(Callback callback) {
/* 760 */     if (callback.target.returnType.equals(Type.VOID_TYPE)) {
/*     */       
/* 762 */       callback.add((AbstractInsnNode)new InsnNode(177));
/*     */     }
/*     */     else {
/*     */       
/* 766 */       callback.add((AbstractInsnNode)new VarInsnNode(25, callback.marshalVar()));
/* 767 */       String accessor = CallbackInfoReturnable.getReturnAccessor(callback.target.returnType);
/* 768 */       String descriptor = CallbackInfoReturnable.getReturnDescriptor(callback.target.returnType);
/* 769 */       callback.add((AbstractInsnNode)new MethodInsnNode(182, this.callbackInfoClass, accessor, descriptor, false));
/* 770 */       if (callback.target.returnType.getSort() >= 9) {
/* 771 */         callback.add((AbstractInsnNode)new TypeInsnNode(192, callback.target.returnType.getInternalName()));
/*     */       }
/* 773 */       callback.add((AbstractInsnNode)new InsnNode(callback.target.returnType.getOpcode(172)));
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected boolean isStatic() {
/* 783 */     return this.isStatic;
/*     */   }
/*     */   
/*     */   private static List<String> summariseLocals(String desc, int pos) {
/* 787 */     return summariseLocals(Type.getArgumentTypes(desc), pos);
/*     */   }
/*     */   
/*     */   private static List<String> summariseLocals(Type[] locals, int pos) {
/* 791 */     List<String> list = new ArrayList<>();
/* 792 */     if (locals != null) {
/* 793 */       for (; pos < locals.length; pos++) {
/* 794 */         if (locals[pos] != null) {
/* 795 */           list.add(locals[pos].toString());
/*     */         }
/*     */       } 
/*     */     }
/* 799 */     return list;
/*     */   }
/*     */   
/*     */   static String meltSnowman(int index, String varName) {
/* 803 */     return (varName != null && '☃' == varName.charAt(0)) ? ("var" + index) : varName;
/*     */   }
/*     */ }


/* Location:              C:\Users\DuduM\OneDrive\Área de Trabalho\mw10\modularwarfare-shining-2023.2.4.4f-fix10.jar!\org\spongepowered\asm\mixin\injection\callback\CallbackInjector.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */