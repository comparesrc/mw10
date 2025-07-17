/*    */ package org.spongepowered.asm.mixin.injection.invoke;
/*    */ 
/*    */ import java.util.List;
/*    */ import org.spongepowered.asm.mixin.injection.InjectionPoint;
/*    */ import org.spongepowered.asm.mixin.injection.code.Injector;
/*    */ import org.spongepowered.asm.mixin.injection.struct.InjectionInfo;
/*    */ import org.spongepowered.asm.mixin.injection.struct.InjectionNodes;
/*    */ import org.spongepowered.asm.mixin.injection.struct.Target;
/*    */ import org.spongepowered.asm.mixin.injection.throwables.InvalidInjectionException;
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
/*    */ public abstract class InvokeInjector
/*    */   extends Injector
/*    */ {
/*    */   public InvokeInjector(InjectionInfo info, String annotationType) {
/* 47 */     super(info, annotationType);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   protected void sanityCheck(Target target, List<InjectionPoint> injectionPoints) {
/* 57 */     super.sanityCheck(target, injectionPoints);
/* 58 */     checkTarget(target);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   protected void checkTarget(Target target) {
/* 67 */     checkTargetModifiers(target, true);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   protected void inject(Target target, InjectionNodes.InjectionNode node) {
/* 77 */     if (!(node.getCurrentTarget() instanceof org.objectweb.asm.tree.MethodInsnNode)) {
/* 78 */       throw new InvalidInjectionException(this.info, String.format("%s annotation on is targetting a non-method insn in %s in %s", new Object[] { this.annotationType, target, this }));
/*    */     }
/*    */ 
/*    */     
/* 82 */     injectAtInvoke(target, node);
/*    */   }
/*    */   
/*    */   protected abstract void injectAtInvoke(Target paramTarget, InjectionNodes.InjectionNode paramInjectionNode);
/*    */ }


/* Location:              C:\Users\DuduM\OneDrive\Área de Trabalho\mw10\modularwarfare-shining-2023.2.4.4f-fix10.jar!\org\spongepowered\asm\mixin\injection\invoke\InvokeInjector.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */