/*    */ package org.spongepowered.asm.mixin.injection.struct;
/*    */ 
/*    */ import org.objectweb.asm.tree.AnnotationNode;
/*    */ import org.objectweb.asm.tree.MethodNode;
/*    */ import org.spongepowered.asm.mixin.injection.ModifyArg;
/*    */ import org.spongepowered.asm.mixin.injection.code.Injector;
/*    */ import org.spongepowered.asm.mixin.injection.invoke.ModifyArgInjector;
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
/*    */ @AnnotationType(ModifyArg.class)
/*    */ @HandlerPrefix("modify")
/*    */ public class ModifyArgInjectionInfo
/*    */   extends InjectionInfo
/*    */ {
/*    */   public ModifyArgInjectionInfo(MixinTargetContext mixin, MethodNode method, AnnotationNode annotation) {
/* 45 */     super(mixin, method, annotation);
/*    */   }
/*    */ 
/*    */   
/*    */   protected Injector parseInjector(AnnotationNode injectAnnotation) {
/* 50 */     int index = ((Integer)Annotations.getValue(injectAnnotation, "index", Integer.valueOf(-1))).intValue();
/*    */     
/* 52 */     return (Injector)new ModifyArgInjector(this, index);
/*    */   }
/*    */ 
/*    */   
/*    */   protected String getDescription() {
/* 57 */     return "Argument modifier method";
/*    */   }
/*    */ }


/* Location:              C:\Users\DuduM\OneDrive\Área de Trabalho\mw10\modularwarfare-shining-2023.2.4.4f-fix10.jar!\org\spongepowered\asm\mixin\injection\struct\ModifyArgInjectionInfo.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */