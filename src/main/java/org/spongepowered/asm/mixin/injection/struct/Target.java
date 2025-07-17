/*     */ package org.spongepowered.asm.mixin.injection.struct;
/*     */ 
/*     */ import java.util.ArrayList;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import org.objectweb.asm.Label;
/*     */ import org.objectweb.asm.Type;
/*     */ import org.objectweb.asm.tree.AbstractInsnNode;
/*     */ import org.objectweb.asm.tree.ClassNode;
/*     */ import org.objectweb.asm.tree.InsnList;
/*     */ import org.objectweb.asm.tree.LabelNode;
/*     */ import org.objectweb.asm.tree.LocalVariableNode;
/*     */ import org.objectweb.asm.tree.MethodInsnNode;
/*     */ import org.objectweb.asm.tree.MethodNode;
/*     */ import org.objectweb.asm.tree.TypeInsnNode;
/*     */ import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
/*     */ import org.spongepowered.asm.mixin.transformer.ClassInfo;
/*     */ import org.spongepowered.asm.util.Bytecode;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class Target
/*     */   implements Comparable<Target>, Iterable<AbstractInsnNode>
/*     */ {
/*     */   public final ClassNode classNode;
/*     */   public final MethodNode method;
/*     */   public final InsnList insns;
/*     */   public final boolean isStatic;
/*     */   public final boolean isCtor;
/*     */   public final Type[] arguments;
/*     */   public final Type returnType;
/*     */   private final int maxStack;
/*     */   private final int maxLocals;
/*     */   
/*     */   public class Extension
/*     */   {
/*     */     private final boolean locals;
/*     */     private int size;
/*     */     
/*     */     Extension(boolean locals) {
/*  89 */       this.locals = locals;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public Extension add() {
/*  98 */       this.size++;
/*  99 */       return this;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public Extension add(int size) {
/* 109 */       this.size += size;
/* 110 */       return this;
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
/*     */     public Extension add(Type[] types) {
/* 122 */       return add(Bytecode.getArgsSize(types));
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public Extension set(int size) {
/* 132 */       this.size = size;
/* 133 */       return this;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public int get() {
/* 142 */       return this.size;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public void apply() {
/* 150 */       if (this.locals) {
/* 151 */         Target.this.extendLocalsBy(this.size);
/*     */       } else {
/* 153 */         Target.this.extendStackBy(this.size);
/*     */       } 
/* 155 */       this.size = 0;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 208 */   private final InjectionNodes injectionNodes = new InjectionNodes();
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private String callbackInfoClass;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private String callbackDescriptor;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private int[] argIndices;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private List<Integer> argMapVars;
/*     */ 
/*     */ 
/*     */   
/*     */   private LabelNode start;
/*     */ 
/*     */ 
/*     */   
/*     */   private LabelNode end;
/*     */ 
/*     */ 
/*     */   
/*     */   private Bytecode.DelegateInitialiser delegateInitialiser;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Target(ClassNode classNode, MethodNode method) {
/* 246 */     this.classNode = classNode;
/* 247 */     this.method = method;
/* 248 */     this.insns = method.instructions;
/* 249 */     this.isStatic = Bytecode.isStatic(method);
/* 250 */     this.isCtor = method.name.equals("<init>");
/* 251 */     this.arguments = Type.getArgumentTypes(method.desc);
/*     */     
/* 253 */     this.returnType = Type.getReturnType(method.desc);
/* 254 */     this.maxStack = method.maxStack;
/* 255 */     this.maxLocals = method.maxLocals;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public InjectionNodes.InjectionNode addInjectionNode(AbstractInsnNode node) {
/* 266 */     return this.injectionNodes.add(node);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public InjectionNodes.InjectionNode getInjectionNode(AbstractInsnNode node) {
/* 277 */     return this.injectionNodes.get(node);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getMaxLocals() {
/* 286 */     return this.maxLocals;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getMaxStack() {
/* 295 */     return this.maxStack;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getCurrentMaxLocals() {
/* 304 */     return this.method.maxLocals;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getCurrentMaxStack() {
/* 313 */     return this.method.maxStack;
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
/*     */   public int allocateLocal() {
/* 328 */     return allocateLocals(1);
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
/*     */   public int allocateLocals(int locals) {
/* 345 */     int nextLocal = this.method.maxLocals;
/* 346 */     this.method.maxLocals += locals;
/* 347 */     return nextLocal;
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
/*     */   public Extension extendLocals() {
/* 359 */     return new Extension(true);
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
/*     */   public Extension extendStack() {
/* 371 */     return new Extension(false);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   void extendLocalsBy(int locals) {
/* 380 */     setMaxLocals(this.maxLocals + locals);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void setMaxLocals(int maxLocals) {
/* 390 */     if (maxLocals > this.method.maxLocals) {
/* 391 */       this.method.maxLocals = maxLocals;
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   void extendStackBy(int stack) {
/* 401 */     setMaxStack(this.maxStack + stack);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void setMaxStack(int maxStack) {
/* 411 */     if (maxStack > this.method.maxStack) {
/* 412 */       this.method.maxStack = maxStack;
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
/*     */   public int[] generateArgMap(Type[] args, int start) {
/* 427 */     if (this.argMapVars == null) {
/* 428 */       this.argMapVars = new ArrayList<>();
/*     */     }
/*     */     
/* 431 */     int[] argMap = new int[args.length];
/* 432 */     for (int arg = start, index = 0; arg < args.length; arg++) {
/* 433 */       int size = args[arg].getSize();
/* 434 */       argMap[arg] = allocateArgMapLocal(index, size);
/* 435 */       index += size;
/*     */     } 
/* 437 */     return argMap;
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
/*     */   private int allocateArgMapLocal(int index, int size) {
/* 453 */     if (index >= this.argMapVars.size()) {
/* 454 */       int base = allocateLocals(size);
/* 455 */       for (int offset = 0; offset < size; offset++) {
/* 456 */         this.argMapVars.add(Integer.valueOf(base + offset));
/*     */       }
/* 458 */       return base;
/*     */     } 
/*     */     
/* 461 */     int local = ((Integer)this.argMapVars.get(index)).intValue();
/*     */ 
/*     */     
/* 464 */     if (size > 1 && index + size > this.argMapVars.size()) {
/* 465 */       int nextLocal = allocateLocals(1);
/* 466 */       if (nextLocal == local + 1) {
/*     */         
/* 468 */         this.argMapVars.add(Integer.valueOf(nextLocal));
/* 469 */         return local;
/*     */       } 
/*     */ 
/*     */ 
/*     */       
/* 474 */       this.argMapVars.set(index, Integer.valueOf(nextLocal));
/* 475 */       this.argMapVars.add(Integer.valueOf(allocateLocals(1)));
/* 476 */       return nextLocal;
/*     */     } 
/*     */     
/* 479 */     return local;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int[] getArgIndices() {
/* 488 */     if (this.argIndices == null) {
/* 489 */       this.argIndices = calcArgIndices(this.isStatic ? 0 : 1);
/*     */     }
/* 491 */     return this.argIndices;
/*     */   }
/*     */   
/*     */   private int[] calcArgIndices(int local) {
/* 495 */     int[] argIndices = new int[this.arguments.length];
/* 496 */     for (int arg = 0; arg < this.arguments.length; arg++) {
/* 497 */       argIndices[arg] = local;
/* 498 */       local += this.arguments[arg].getSize();
/*     */     } 
/* 500 */     return argIndices;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getCallbackInfoClass() {
/* 510 */     if (this.callbackInfoClass == null) {
/* 511 */       this.callbackInfoClass = CallbackInfo.getCallInfoClassName(this.returnType);
/*     */     }
/* 513 */     return this.callbackInfoClass;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getSimpleCallbackDescriptor() {
/* 522 */     return String.format("(L%s;)V", new Object[] { getCallbackInfoClass() });
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getCallbackDescriptor(Type[] locals, Type[] argumentTypes) {
/* 533 */     return getCallbackDescriptor(false, locals, argumentTypes, 0, 32767);
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
/*     */   public String getCallbackDescriptor(boolean captureLocals, Type[] locals, Type[] argumentTypes, int startIndex, int extra) {
/* 547 */     if (this.callbackDescriptor == null) {
/* 548 */       this.callbackDescriptor = String.format("(%sL%s;)V", new Object[] { this.method.desc.substring(1, this.method.desc.indexOf(')')), 
/* 549 */             getCallbackInfoClass() });
/*     */     }
/*     */     
/* 552 */     if (!captureLocals || locals == null) {
/* 553 */       return this.callbackDescriptor;
/*     */     }
/*     */     
/* 556 */     StringBuilder descriptor = new StringBuilder(this.callbackDescriptor.substring(0, this.callbackDescriptor.indexOf(')')));
/* 557 */     for (int l = startIndex; l < locals.length && extra > 0; l++) {
/* 558 */       if (locals[l] != null) {
/* 559 */         descriptor.append(locals[l].getDescriptor());
/* 560 */         extra--;
/*     */       } 
/*     */     } 
/*     */     
/* 564 */     return descriptor.append(")V").toString();
/*     */   }
/*     */ 
/*     */   
/*     */   public String toString() {
/* 569 */     return String.format("%s::%s%s", new Object[] { this.classNode.name, this.method.name, this.method.desc });
/*     */   }
/*     */ 
/*     */   
/*     */   public int compareTo(Target o) {
/* 574 */     if (o == null) {
/* 575 */       return Integer.MAX_VALUE;
/*     */     }
/* 577 */     return toString().compareTo(o.toString());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int indexOf(InjectionNodes.InjectionNode node) {
/* 587 */     return this.insns.indexOf(node.getCurrentTarget());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int indexOf(AbstractInsnNode insn) {
/* 597 */     return this.insns.indexOf(insn);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public AbstractInsnNode get(int index) {
/* 607 */     return this.insns.get(index);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Iterator<AbstractInsnNode> iterator() {
/* 615 */     return this.insns.iterator();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public MethodInsnNode findInitNodeFor(TypeInsnNode newNode) {
/* 626 */     int start = indexOf((AbstractInsnNode)newNode);
/* 627 */     for (Iterator<AbstractInsnNode> iter = this.insns.iterator(start); iter.hasNext(); ) {
/* 628 */       AbstractInsnNode insn = iter.next();
/* 629 */       if (insn instanceof MethodInsnNode && insn.getOpcode() == 183) {
/* 630 */         MethodInsnNode methodNode = (MethodInsnNode)insn;
/* 631 */         if ("<init>".equals(methodNode.name) && methodNode.owner.equals(newNode.desc)) {
/* 632 */           return methodNode;
/*     */         }
/*     */       } 
/*     */     } 
/* 636 */     return null;
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
/*     */   public Bytecode.DelegateInitialiser findDelegateInitNode() {
/* 649 */     if (!this.isCtor) {
/* 650 */       return null;
/*     */     }
/*     */     
/* 653 */     if (this.delegateInitialiser == null) {
/* 654 */       String superName = ClassInfo.forName(this.classNode.name).getSuperName();
/* 655 */       this.delegateInitialiser = Bytecode.findDelegateInit(this.method, superName, this.classNode.name);
/*     */     } 
/*     */     
/* 658 */     return this.delegateInitialiser;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void insertBefore(InjectionNodes.InjectionNode location, InsnList insns) {
/* 668 */     this.insns.insertBefore(location.getCurrentTarget(), insns);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void insertBefore(AbstractInsnNode location, InsnList insns) {
/* 678 */     this.insns.insertBefore(location, insns);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void replaceNode(AbstractInsnNode location, AbstractInsnNode insn) {
/* 689 */     this.insns.insertBefore(location, insn);
/* 690 */     this.insns.remove(location);
/* 691 */     this.injectionNodes.replace(location, insn);
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
/*     */   public void replaceNode(AbstractInsnNode location, AbstractInsnNode champion, InsnList insns) {
/* 703 */     this.insns.insertBefore(location, insns);
/* 704 */     this.insns.remove(location);
/* 705 */     this.injectionNodes.replace(location, champion);
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
/*     */   public void wrapNode(AbstractInsnNode location, AbstractInsnNode champion, InsnList before, InsnList after) {
/* 718 */     this.insns.insertBefore(location, before);
/* 719 */     this.insns.insert(location, after);
/* 720 */     this.injectionNodes.replace(location, champion);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void replaceNode(AbstractInsnNode location, InsnList insns) {
/* 731 */     this.insns.insertBefore(location, insns);
/* 732 */     removeNode(location);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void removeNode(AbstractInsnNode insn) {
/* 742 */     this.insns.remove(insn);
/* 743 */     this.injectionNodes.remove(insn);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void addLocalVariable(int index, String name, String desc) {
/* 754 */     if (this.start == null) {
/* 755 */       this.start = new LabelNode(new Label());
/* 756 */       this.end = new LabelNode(new Label());
/* 757 */       this.insns.insert((AbstractInsnNode)this.start);
/* 758 */       this.insns.add((AbstractInsnNode)this.end);
/*     */     } 
/* 760 */     addLocalVariable(index, name, desc, this.start, this.end);
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
/*     */   private void addLocalVariable(int index, String name, String desc, LabelNode start, LabelNode end) {
/* 773 */     if (this.method.localVariables == null) {
/* 774 */       this.method.localVariables = new ArrayList();
/*     */     }
/* 776 */     this.method.localVariables.add(new LocalVariableNode(name, desc, null, start, end, index));
/*     */   }
/*     */ }


/* Location:              C:\Users\DuduM\OneDrive\Área de Trabalho\mw10\modularwarfare-shining-2023.2.4.4f-fix10.jar!\org\spongepowered\asm\mixin\injection\struct\Target.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */