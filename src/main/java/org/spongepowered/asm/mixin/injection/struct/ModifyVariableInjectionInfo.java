/*    */ package org.spongepowered.asm.mixin.injection.struct;
/*    */ 
/*    */ import org.objectweb.asm.tree.AnnotationNode;
/*    */ import org.objectweb.asm.tree.MethodNode;
/*    */ import org.spongepowered.asm.mixin.injection.ModifyVariable;
/*    */ import org.spongepowered.asm.mixin.injection.code.Injector;
/*    */ import org.spongepowered.asm.mixin.injection.modify.LocalVariableDiscriminator;
/*    */ import org.spongepowered.asm.mixin.injection.modify.ModifyVariableInjector;
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
/*    */ @AnnotationType(ModifyVariable.class)
/*    */ @HandlerPrefix("localvar")
/*    */ public class ModifyVariableInjectionInfo
/*    */   extends InjectionInfo
/*    */ {
/*    */   public ModifyVariableInjectionInfo(MixinTargetContext mixin, MethodNode method, AnnotationNode annotation) {
/* 45 */     super(mixin, method, annotation);
/*    */   }
/*    */ 
/*    */   
/*    */   protected Injector parseInjector(AnnotationNode injectAnnotation) {
/* 50 */     return (Injector)new ModifyVariableInjector(this, LocalVariableDiscriminator.parse(injectAnnotation));
/*    */   }
/*    */ 
/*    */   
/*    */   protected String getDescription() {
/* 55 */     return "Variable modifier method";
/*    */   }
/*    */ }


/* Location:              C:\Users\DuduM\OneDrive\Área de Trabalho\mw10\modularwarfare-shining-2023.2.4.4f-fix10.jar!\org\spongepowered\asm\mixin\injection\struct\ModifyVariableInjectionInfo.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */