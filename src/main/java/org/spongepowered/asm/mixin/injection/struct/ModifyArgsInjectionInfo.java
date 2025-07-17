/*    */ package org.spongepowered.asm.mixin.injection.struct;
/*    */ 
/*    */ import org.objectweb.asm.tree.AnnotationNode;
/*    */ import org.objectweb.asm.tree.MethodNode;
/*    */ import org.spongepowered.asm.mixin.injection.ModifyArgs;
/*    */ import org.spongepowered.asm.mixin.injection.code.Injector;
/*    */ import org.spongepowered.asm.mixin.injection.invoke.ModifyArgsInjector;
/*    */ import org.spongepowered.asm.mixin.transformer.MixinTargetContext;
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
/*    */ @AnnotationType(ModifyArgs.class)
/*    */ @HandlerPrefix("args")
/*    */ public class ModifyArgsInjectionInfo
/*    */   extends InjectionInfo
/*    */ {
/*    */   public ModifyArgsInjectionInfo(MixinTargetContext mixin, MethodNode method, AnnotationNode annotation) {
/* 44 */     super(mixin, method, annotation);
/*    */   }
/*    */ 
/*    */   
/*    */   protected Injector parseInjector(AnnotationNode injectAnnotation) {
/* 49 */     return (Injector)new ModifyArgsInjector(this);
/*    */   }
/*    */ 
/*    */   
/*    */   protected String getDescription() {
/* 54 */     return "Multi-argument modifier method";
/*    */   }
/*    */ }


/* Location:              C:\Users\DuduM\OneDrive\Área de Trabalho\mw10\modularwarfare-shining-2023.2.4.4f-fix10.jar!\org\spongepowered\asm\mixin\injection\struct\ModifyArgsInjectionInfo.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */