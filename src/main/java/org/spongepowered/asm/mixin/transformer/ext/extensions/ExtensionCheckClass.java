/*    */ package org.spongepowered.asm.mixin.transformer.ext.extensions;
/*    */ 
/*    */ import org.objectweb.asm.ClassVisitor;
/*    */ import org.objectweb.asm.tree.ClassNode;
/*    */ import org.objectweb.asm.util.CheckClassAdapter;
/*    */ import org.spongepowered.asm.mixin.MixinEnvironment;
/*    */ import org.spongepowered.asm.mixin.throwables.MixinException;
/*    */ import org.spongepowered.asm.mixin.transformer.ext.IExtension;
/*    */ import org.spongepowered.asm.mixin.transformer.ext.ITargetClassContext;
/*    */ import org.spongepowered.asm.transformers.MixinClassWriter;
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
/*    */ public class ExtensionCheckClass
/*    */   implements IExtension
/*    */ {
/*    */   public static class ValidationFailedException
/*    */     extends MixinException
/*    */   {
/*    */     private static final long serialVersionUID = 1L;
/*    */     
/*    */     public ValidationFailedException(String message, Throwable cause) {
/* 51 */       super(message, cause);
/*    */     }
/*    */     
/*    */     public ValidationFailedException(String message) {
/* 55 */       super(message);
/*    */     }
/*    */     
/*    */     public ValidationFailedException(Throwable cause) {
/* 59 */       super(cause);
/*    */     }
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean checkActive(MixinEnvironment environment) {
/* 70 */     return environment.getOption(MixinEnvironment.Option.DEBUG_VERIFY);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void preApply(ITargetClassContext context) {}
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void postApply(ITargetClassContext context) {
/*    */     try {
/* 88 */       context.getClassNode().accept((ClassVisitor)new CheckClassAdapter((ClassVisitor)new MixinClassWriter(2)));
/* 89 */     } catch (RuntimeException ex) {
/* 90 */       throw new ValidationFailedException(ex.getMessage(), ex);
/*    */     } 
/*    */   }
/*    */   
/*    */   public void export(MixinEnvironment env, String name, boolean force, ClassNode classNode) {}
/*    */ }


/* Location:              C:\Users\DuduM\OneDrive\Área de Trabalho\mw10\modularwarfare-shining-2023.2.4.4f-fix10.jar!\org\spongepowered\asm\mixin\transformer\ext\extensions\ExtensionCheckClass.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */