/*    */ package org.spongepowered.asm.mixin.gen;
/*    */ 
/*    */ import java.util.ArrayList;
/*    */ import org.objectweb.asm.tree.MethodNode;
/*    */ import org.spongepowered.asm.mixin.injection.throwables.InvalidInjectionException;
/*    */ import org.spongepowered.asm.mixin.refmap.IMixinContext;
/*    */ import org.spongepowered.asm.util.asm.ASM;
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
/*    */ public abstract class AccessorGenerator
/*    */ {
/*    */   protected final AccessorInfo info;
/*    */   protected final boolean targetIsStatic;
/*    */   
/*    */   public AccessorGenerator(AccessorInfo info, boolean isStatic) {
/* 52 */     this.info = info;
/* 53 */     this.targetIsStatic = isStatic;
/*    */   }
/*    */   
/*    */   protected void checkModifiers() {
/* 57 */     if (this.info.isStatic() && !this.targetIsStatic) {
/* 58 */       IMixinContext context = this.info.getContext();
/* 59 */       throw new InvalidInjectionException(context, String.format("%s is invalid. Accessor method is%s static but the target is not.", new Object[] { this.info, 
/* 60 */               this.info.isStatic() ? "" : " not" }));
/*    */     } 
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   protected final MethodNode createMethod(int maxLocals, int maxStack) {
/* 72 */     MethodNode method = this.info.getMethod();
/* 73 */     MethodNode accessor = new MethodNode(ASM.API_VERSION, method.access & 0xFFFFFBFF | 0x1000, method.name, method.desc, null, null);
/*    */     
/* 75 */     accessor.visibleAnnotations = new ArrayList();
/* 76 */     accessor.visibleAnnotations.add(this.info.getAnnotation());
/* 77 */     accessor.maxLocals = maxLocals;
/* 78 */     accessor.maxStack = maxStack;
/* 79 */     return accessor;
/*    */   }
/*    */   
/*    */   public void validate() {}
/*    */   
/*    */   public abstract MethodNode generate();
/*    */ }


/* Location:              C:\Users\DuduM\OneDrive\Área de Trabalho\mw10\modularwarfare-shining-2023.2.4.4f-fix10.jar!\org\spongepowered\asm\mixin\gen\AccessorGenerator.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */