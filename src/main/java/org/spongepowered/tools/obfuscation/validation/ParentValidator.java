/*    */ package org.spongepowered.tools.obfuscation.validation;
/*    */ 
/*    */ import java.util.Collection;
/*    */ import javax.lang.model.element.ElementKind;
/*    */ import javax.lang.model.element.Modifier;
/*    */ import javax.lang.model.element.TypeElement;
/*    */ import org.spongepowered.tools.obfuscation.MixinValidator;
/*    */ import org.spongepowered.tools.obfuscation.interfaces.IMixinAnnotationProcessor;
/*    */ import org.spongepowered.tools.obfuscation.interfaces.IMixinValidator;
/*    */ import org.spongepowered.tools.obfuscation.mirror.AnnotationHandle;
/*    */ import org.spongepowered.tools.obfuscation.mirror.TypeHandle;
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
/*    */ public class ParentValidator
/*    */   extends MixinValidator
/*    */ {
/*    */   public ParentValidator(IMixinAnnotationProcessor ap) {
/* 49 */     super(ap, IMixinValidator.ValidationPass.EARLY);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean validate(TypeElement mixin, AnnotationHandle annotation, Collection<TypeHandle> targets) {
/* 60 */     if (mixin.getEnclosingElement().getKind() != ElementKind.PACKAGE && !mixin.getModifiers().contains(Modifier.STATIC)) {
/* 61 */       error("Inner class mixin must be declared static", mixin);
/*    */     }
/*    */     
/* 64 */     return true;
/*    */   }
/*    */ }


/* Location:              C:\Users\DuduM\OneDrive\Área de Trabalho\mw10\modularwarfare-shining-2023.2.4.4f-fix10.jar!\org\spongepowered\tools\obfuscation\validation\ParentValidator.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */