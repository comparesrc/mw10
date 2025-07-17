/*    */ package org.spongepowered.asm.mixin.gen;
/*    */ 
/*    */ import org.objectweb.asm.Type;
/*    */ import org.objectweb.asm.tree.AbstractInsnNode;
/*    */ import org.objectweb.asm.tree.InsnNode;
/*    */ import org.objectweb.asm.tree.MethodInsnNode;
/*    */ import org.objectweb.asm.tree.MethodNode;
/*    */ import org.objectweb.asm.tree.VarInsnNode;
/*    */ import org.spongepowered.asm.util.Bytecode;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class AccessorGeneratorMethodProxy
/*    */   extends AccessorGenerator
/*    */ {
/*    */   protected final MethodNode targetMethod;
/*    */   protected final Type[] argTypes;
/*    */   protected final Type returnType;
/*    */   
/*    */   public AccessorGeneratorMethodProxy(AccessorInfo info) {
/* 56 */     super(info, Bytecode.isStatic(info.getTargetMethod()));
/* 57 */     this.targetMethod = info.getTargetMethod();
/* 58 */     this.argTypes = info.getArgTypes();
/* 59 */     this.returnType = info.getReturnType();
/* 60 */     checkModifiers();
/*    */   }
/*    */   
/*    */   protected AccessorGeneratorMethodProxy(AccessorInfo info, boolean isStatic) {
/* 64 */     super(info, isStatic);
/* 65 */     this.targetMethod = info.getTargetMethod();
/* 66 */     this.argTypes = info.getArgTypes();
/* 67 */     this.returnType = info.getReturnType();
/*    */   }
/*    */ 
/*    */   
/*    */   public MethodNode generate() {
/* 72 */     int size = Bytecode.getArgsSize(this.argTypes) + this.returnType.getSize() + (this.targetIsStatic ? 0 : 1);
/* 73 */     MethodNode method = createMethod(size, size);
/* 74 */     if (!this.targetIsStatic) {
/* 75 */       method.instructions.add((AbstractInsnNode)new VarInsnNode(25, 0));
/*    */     }
/* 77 */     Bytecode.loadArgs(this.argTypes, method.instructions, this.targetIsStatic ? 0 : 1);
/* 78 */     boolean isPrivate = Bytecode.hasFlag(this.targetMethod, 2);
/* 79 */     int opcode = this.targetIsStatic ? 184 : (isPrivate ? 183 : 182);
/* 80 */     method.instructions.add((AbstractInsnNode)new MethodInsnNode(opcode, (this.info.getClassNode()).name, this.targetMethod.name, this.targetMethod.desc, false));
/* 81 */     method.instructions.add((AbstractInsnNode)new InsnNode(this.returnType.getOpcode(172)));
/* 82 */     return method;
/*    */   }
/*    */ }


/* Location:              C:\Users\DuduM\OneDrive\Área de Trabalho\mw10\modularwarfare-shining-2023.2.4.4f-fix10.jar!\org\spongepowered\asm\mixin\gen\AccessorGeneratorMethodProxy.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */