/*    */ package org.spongepowered.asm.mixin.gen;
/*    */ 
/*    */ import org.objectweb.asm.tree.AbstractInsnNode;
/*    */ import org.objectweb.asm.tree.InsnNode;
/*    */ import org.objectweb.asm.tree.MethodInsnNode;
/*    */ import org.objectweb.asm.tree.MethodNode;
/*    */ import org.objectweb.asm.tree.TypeInsnNode;
/*    */ import org.spongepowered.asm.mixin.injection.throwables.InvalidInjectionException;
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
/*    */ public class AccessorGeneratorObjectFactory
/*    */   extends AccessorGeneratorMethodProxy
/*    */ {
/*    */   public AccessorGeneratorObjectFactory(AccessorInfo info) {
/* 42 */     super(info, true);
/* 43 */     if (!info.isStatic()) {
/* 44 */       throw new InvalidInjectionException(info.getContext(), String.format("%s is invalid. Factory method must be static.", new Object[] { this.info }));
/*    */     }
/*    */   }
/*    */ 
/*    */   
/*    */   public MethodNode generate() {
/* 50 */     int returnSize = this.returnType.getSize();
/*    */     
/* 52 */     int size = Bytecode.getArgsSize(this.argTypes) + returnSize * 2;
/* 53 */     MethodNode method = createMethod(size, size);
/*    */     
/* 55 */     String className = (this.info.getClassNode()).name;
/* 56 */     method.instructions.add((AbstractInsnNode)new TypeInsnNode(187, className));
/* 57 */     method.instructions.add((AbstractInsnNode)new InsnNode((returnSize == 1) ? 89 : 92));
/* 58 */     Bytecode.loadArgs(this.argTypes, method.instructions, 0);
/* 59 */     method.instructions.add((AbstractInsnNode)new MethodInsnNode(183, className, "<init>", this.targetMethod.desc, false));
/* 60 */     method.instructions.add((AbstractInsnNode)new InsnNode(176));
/*    */     
/* 62 */     return method;
/*    */   }
/*    */ }


/* Location:              C:\Users\DuduM\OneDrive\Área de Trabalho\mw10\modularwarfare-shining-2023.2.4.4f-fix10.jar!\org\spongepowered\asm\mixin\gen\AccessorGeneratorObjectFactory.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */