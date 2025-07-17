/*    */ package org.spongepowered.asm.mixin.transformer;
/*    */ 
/*    */ import org.objectweb.asm.tree.AnnotationNode;
/*    */ import org.objectweb.asm.tree.FieldNode;
/*    */ import org.objectweb.asm.tree.MethodNode;
/*    */ import org.spongepowered.asm.mixin.transformer.throwables.InvalidInterfaceMixinException;
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
/*    */ class MixinPreProcessorInterface
/*    */   extends MixinPreProcessorStandard
/*    */ {
/*    */   MixinPreProcessorInterface(MixinInfo mixin, MixinInfo.MixinClassNode classNode) {
/* 49 */     super(mixin, classNode);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   protected void prepareMethod(MixinInfo.MixinMethodNode mixinMethod, ClassInfo.Method method) {
/* 60 */     if (!Bytecode.hasFlag((MethodNode)mixinMethod, 1) && !Bytecode.hasFlag((MethodNode)mixinMethod, 4096)) {
/* 61 */       throw new InvalidInterfaceMixinException(this.mixin, "Interface mixin contains a non-public method! Found " + method + " in " + this.mixin);
/*    */     }
/*    */ 
/*    */     
/* 65 */     super.prepareMethod(mixinMethod, method);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   protected boolean validateField(MixinTargetContext context, FieldNode field, AnnotationNode shadow) {
/* 77 */     if (!Bytecode.isStatic(field)) {
/* 78 */       throw new InvalidInterfaceMixinException(this.mixin, "Interface mixin contains an instance field! Found " + field.name + " in " + this.mixin);
/*    */     }
/*    */ 
/*    */     
/* 82 */     return super.validateField(context, field, shadow);
/*    */   }
/*    */ }


/* Location:              C:\Users\DuduM\OneDrive\Área de Trabalho\mw10\modularwarfare-shining-2023.2.4.4f-fix10.jar!\org\spongepowered\asm\mixin\transformer\MixinPreProcessorInterface.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */