/*    */ package org.spongepowered.tools.obfuscation.interfaces;
/*    */ 
/*    */ import javax.annotation.processing.ProcessingEnvironment;
/*    */ import org.spongepowered.asm.util.ITokenProvider;
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
/*    */ public interface IMixinAnnotationProcessor
/*    */   extends IMessagerSuppressible, IOptionProvider
/*    */ {
/*    */   CompilerEnvironment getCompilerEnvironment();
/*    */   
/*    */   ProcessingEnvironment getProcessingEnvironment();
/*    */   
/*    */   IObfuscationManager getObfuscationManager();
/*    */   
/*    */   ITokenProvider getTokenProvider();
/*    */   
/*    */   ITypeHandleProvider getTypeProvider();
/*    */   
/*    */   IJavadocProvider getJavadocProvider();
/*    */   
/*    */   public enum CompilerEnvironment
/*    */   {
/* 45 */     JAVAC,
/*    */ 
/*    */ 
/*    */ 
/*    */     
/* 50 */     JDT;
/*    */   }
/*    */ }


/* Location:              C:\Users\DuduM\OneDrive\Área de Trabalho\mw10\modularwarfare-shining-2023.2.4.4f-fix10.jar!\org\spongepowered\tools\obfuscation\interfaces\IMixinAnnotationProcessor.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */