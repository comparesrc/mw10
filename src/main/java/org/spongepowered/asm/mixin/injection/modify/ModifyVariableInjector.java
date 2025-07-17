/*     */ package org.spongepowered.asm.mixin.injection.modify;
/*     */ 
/*     */ import java.util.Collection;
/*     */ import java.util.List;
/*     */ import org.objectweb.asm.Type;
/*     */ import org.objectweb.asm.tree.AbstractInsnNode;
/*     */ import org.objectweb.asm.tree.InsnList;
/*     */ import org.objectweb.asm.tree.MethodNode;
/*     */ import org.objectweb.asm.tree.VarInsnNode;
/*     */ import org.spongepowered.asm.mixin.injection.InjectionPoint;
/*     */ import org.spongepowered.asm.mixin.injection.code.Injector;
/*     */ import org.spongepowered.asm.mixin.injection.struct.InjectionInfo;
/*     */ import org.spongepowered.asm.mixin.injection.struct.InjectionNodes;
/*     */ import org.spongepowered.asm.mixin.injection.struct.Target;
/*     */ import org.spongepowered.asm.mixin.injection.throwables.InvalidInjectionException;
/*     */ import org.spongepowered.asm.mixin.refmap.IMixinContext;
/*     */ import org.spongepowered.asm.util.Bytecode;
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
/*     */ public class ModifyVariableInjector
/*     */   extends Injector
/*     */ {
/*     */   private final LocalVariableDiscriminator discriminator;
/*     */   
/*     */   static class Context
/*     */     extends LocalVariableDiscriminator.Context
/*     */   {
/*  65 */     final InsnList insns = new InsnList();
/*     */     
/*     */     public Context(Type returnType, boolean argsOnly, Target target, AbstractInsnNode node) {
/*  68 */       super(returnType, argsOnly, target, node);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   static abstract class ContextualInjectionPoint
/*     */     extends InjectionPoint
/*     */   {
/*     */     protected final IMixinContext context;
/*     */ 
/*     */     
/*     */     ContextualInjectionPoint(IMixinContext context) {
/*  81 */       this.context = context;
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean find(String desc, InsnList insns, Collection<AbstractInsnNode> nodes) {
/*  86 */       throw new InvalidInjectionException(this.context, getAtCode() + " injection point must be used in conjunction with @ModifyVariable");
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     abstract boolean find(Target param1Target, Collection<AbstractInsnNode> param1Collection);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ModifyVariableInjector(InjectionInfo info, LocalVariableDiscriminator discriminator) {
/* 103 */     super(info, "@ModifyVariable");
/* 104 */     this.discriminator = discriminator;
/*     */   }
/*     */ 
/*     */   
/*     */   protected boolean findTargetNodes(MethodNode into, InjectionPoint injectionPoint, InsnList insns, Collection<AbstractInsnNode> nodes) {
/* 109 */     if (injectionPoint instanceof ContextualInjectionPoint) {
/* 110 */       Target target = this.info.getContext().getTargetMethod(into);
/* 111 */       return ((ContextualInjectionPoint)injectionPoint).find(target, nodes);
/*     */     } 
/* 113 */     return injectionPoint.find(into.desc, insns, nodes);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void sanityCheck(Target target, List<InjectionPoint> injectionPoints) {
/* 123 */     super.sanityCheck(target, injectionPoints);
/*     */     
/* 125 */     int ordinal = this.discriminator.getOrdinal();
/* 126 */     if (ordinal < -1) {
/* 127 */       throw new InvalidInjectionException(this.info, "Invalid ordinal " + ordinal + " specified in " + this);
/*     */     }
/*     */     
/* 130 */     if (this.discriminator.getIndex() == 0 && !target.isStatic) {
/* 131 */       throw new InvalidInjectionException(this.info, "Invalid index 0 specified in non-static variable modifier " + this);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void inject(Target target, InjectionNodes.InjectionNode node) {
/* 140 */     if (node.isReplaced()) {
/* 141 */       throw new InvalidInjectionException(this.info, "Variable modifier target for " + this + " was removed by another injector");
/*     */     }
/*     */     
/* 144 */     Context context = new Context(this.returnType, this.discriminator.isArgsOnly(), target, node.getCurrentTarget());
/*     */     
/* 146 */     if (this.discriminator.printLVT()) {
/* 147 */       printLocals(target, context);
/*     */     }
/*     */     
/* 150 */     checkTargetForNode(target, node, InjectionPoint.RestrictTargetLevel.ALLOW_ALL);
/*     */     
/* 152 */     Injector.InjectorData handler = new Injector.InjectorData(target, "handler", false);
/* 153 */     validateParams(handler, this.returnType, new Type[] { this.returnType });
/*     */     
/* 155 */     Target.Extension extraStack = target.extendStack();
/*     */     
/*     */     try {
/* 158 */       int local = this.discriminator.findLocal(context);
/* 159 */       if (local > -1) {
/* 160 */         inject(context, handler, extraStack, local);
/*     */       }
/* 162 */     } catch (InvalidImplicitDiscriminatorException ex) {
/* 163 */       if (this.discriminator.printLVT()) {
/* 164 */         this.info.addCallbackInvocation(this.methodNode);
/*     */         return;
/*     */       } 
/* 167 */       throw new InvalidInjectionException(this.info, "Implicit variable modifier injection failed in " + this, ex);
/*     */     } 
/*     */     
/* 170 */     extraStack.apply();
/* 171 */     target.insns.insertBefore(context.node, context.insns);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void printLocals(Target target, Context context) {
/* 178 */     SignaturePrinter handlerSig = new SignaturePrinter(this.info.getMethodName(), this.returnType, this.methodArgs, new String[] { "var" });
/* 179 */     handlerSig.setModifiers(this.methodNode);
/*     */     
/* 181 */     (new PrettyPrinter())
/* 182 */       .kvWidth(20)
/* 183 */       .kv("Target Class", this.classNode.name.replace('/', '.'))
/* 184 */       .kv("Target Method", context.target.method.name)
/* 185 */       .kv("Callback Name", this.info.getMethodName())
/* 186 */       .kv("Capture Type", SignaturePrinter.getTypeName(this.returnType, false))
/* 187 */       .kv("Instruction", "[%d] %s %s", new Object[] { Integer.valueOf(target.insns.indexOf(context.node)), context.node.getClass().getSimpleName(), 
/* 188 */           Bytecode.getOpcodeName(context.node.getOpcode()) }).hr()
/* 189 */       .kv("Match mode", this.discriminator.isImplicit(context) ? "IMPLICIT (match single)" : "EXPLICIT (match by criteria)")
/* 190 */       .kv("Match ordinal", (this.discriminator.getOrdinal() < 0) ? "any" : Integer.valueOf(this.discriminator.getOrdinal()))
/* 191 */       .kv("Match index", (this.discriminator.getIndex() < context.baseArgIndex) ? "any" : Integer.valueOf(this.discriminator.getIndex()))
/* 192 */       .kv("Match name(s)", this.discriminator.hasNames() ? this.discriminator.getNames() : "any")
/* 193 */       .kv("Args only", Boolean.valueOf(this.discriminator.isArgsOnly())).hr()
/* 194 */       .add(context)
/* 195 */       .print(System.err);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void inject(Context context, Injector.InjectorData handler, Target.Extension extraStack, int local) {
/* 206 */     if (!this.isStatic) {
/* 207 */       context.insns.add((AbstractInsnNode)new VarInsnNode(25, 0));
/* 208 */       extraStack.add();
/*     */     } 
/*     */     
/* 211 */     context.insns.add((AbstractInsnNode)new VarInsnNode(this.returnType.getOpcode(21), local));
/* 212 */     extraStack.add();
/*     */     
/* 214 */     if (handler.captureTargetArgs > 0) {
/* 215 */       pushArgs(handler.target.arguments, context.insns, handler.target.getArgIndices(), 0, handler.captureTargetArgs, extraStack);
/*     */     }
/*     */     
/* 218 */     invokeHandler(context.insns);
/* 219 */     context.insns.add((AbstractInsnNode)new VarInsnNode(this.returnType.getOpcode(54), local));
/*     */   }
/*     */ }


/* Location:              C:\Users\DuduM\OneDrive\Área de Trabalho\mw10\modularwarfare-shining-2023.2.4.4f-fix10.jar!\org\spongepowered\asm\mixin\injection\modify\ModifyVariableInjector.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */