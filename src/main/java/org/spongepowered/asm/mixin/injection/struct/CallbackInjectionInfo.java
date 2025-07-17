/*    */ package org.spongepowered.asm.mixin.injection.struct;
/*    */ 
/*    */ import com.google.common.base.Strings;
/*    */ import org.objectweb.asm.tree.AnnotationNode;
/*    */ import org.objectweb.asm.tree.MethodNode;
/*    */ import org.spongepowered.asm.mixin.injection.Inject;
/*    */ import org.spongepowered.asm.mixin.injection.callback.CallbackInjector;
/*    */ import org.spongepowered.asm.mixin.injection.callback.LocalCapture;
/*    */ import org.spongepowered.asm.mixin.injection.code.Injector;
/*    */ import org.spongepowered.asm.mixin.transformer.MixinTargetContext;
/*    */ import org.spongepowered.asm.util.Annotations;
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
/*    */ @AnnotationType(Inject.class)
/*    */ public class CallbackInjectionInfo
/*    */   extends InjectionInfo
/*    */ {
/*    */   protected CallbackInjectionInfo(MixinTargetContext mixin, MethodNode method, AnnotationNode annotation) {
/* 46 */     super(mixin, method, annotation);
/*    */   }
/*    */ 
/*    */   
/*    */   protected Injector parseInjector(AnnotationNode injectAnnotation) {
/* 51 */     boolean cancellable = ((Boolean)Annotations.getValue(injectAnnotation, "cancellable", Boolean.FALSE)).booleanValue();
/* 52 */     LocalCapture locals = (LocalCapture)Annotations.getValue(injectAnnotation, "locals", LocalCapture.class, (Enum)LocalCapture.NO_CAPTURE);
/* 53 */     String identifier = (String)Annotations.getValue(injectAnnotation, "id", "");
/*    */     
/* 55 */     return (Injector)new CallbackInjector(this, cancellable, locals, identifier);
/*    */   }
/*    */ 
/*    */   
/*    */   public String getSliceId(String id) {
/* 60 */     return Strings.nullToEmpty(id);
/*    */   }
/*    */ }


/* Location:              C:\Users\DuduM\OneDrive\Área de Trabalho\mw10\modularwarfare-shining-2023.2.4.4f-fix10.jar!\org\spongepowered\asm\mixin\injection\struct\CallbackInjectionInfo.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */