/*    */ package org.spongepowered.asm.mixin.injection.struct;
/*    */ 
/*    */ import org.objectweb.asm.tree.AnnotationNode;
/*    */ import org.objectweb.asm.tree.MethodNode;
/*    */ import org.spongepowered.asm.mixin.injection.Redirect;
/*    */ import org.spongepowered.asm.mixin.injection.code.Injector;
/*    */ import org.spongepowered.asm.mixin.injection.invoke.RedirectInjector;
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
/*    */ @AnnotationType(Redirect.class)
/*    */ @HandlerPrefix("redirect")
/*    */ public class RedirectInjectionInfo
/*    */   extends InjectionInfo
/*    */ {
/*    */   public RedirectInjectionInfo(MixinTargetContext mixin, MethodNode method, AnnotationNode annotation) {
/* 44 */     super(mixin, method, annotation);
/*    */   }
/*    */ 
/*    */   
/*    */   protected Injector parseInjector(AnnotationNode injectAnnotation) {
/* 49 */     return (Injector)new RedirectInjector(this);
/*    */   }
/*    */ 
/*    */   
/*    */   protected String getDescription() {
/* 54 */     return "Redirector";
/*    */   }
/*    */ }


/* Location:              C:\Users\DuduM\OneDrive\Área de Trabalho\mw10\modularwarfare-shining-2023.2.4.4f-fix10.jar!\org\spongepowered\asm\mixin\injection\struct\RedirectInjectionInfo.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */