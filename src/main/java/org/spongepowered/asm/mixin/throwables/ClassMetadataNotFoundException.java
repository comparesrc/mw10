/*    */ package org.spongepowered.asm.mixin.throwables;
/*    */ 
/*    */ import org.spongepowered.asm.mixin.transformer.ActivityStack;
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
/*    */ public class ClassMetadataNotFoundException
/*    */   extends MixinException
/*    */ {
/*    */   private static final long serialVersionUID = 1L;
/*    */   
/*    */   public ClassMetadataNotFoundException(String message) {
/* 42 */     super(message);
/*    */   }
/*    */   
/*    */   public ClassMetadataNotFoundException(String message, ActivityStack context) {
/* 46 */     super(message, context);
/*    */   }
/*    */   
/*    */   public ClassMetadataNotFoundException(Throwable cause) {
/* 50 */     super(cause);
/*    */   }
/*    */   
/*    */   public ClassMetadataNotFoundException(Throwable cause, ActivityStack context) {
/* 54 */     super(cause, context);
/*    */   }
/*    */   
/*    */   public ClassMetadataNotFoundException(String message, Throwable cause) {
/* 58 */     super(message, cause);
/*    */   }
/*    */   
/*    */   public ClassMetadataNotFoundException(String message, Throwable cause, ActivityStack context) {
/* 62 */     super(message, cause, context);
/*    */   }
/*    */ }


/* Location:              C:\Users\DuduM\OneDrive\Área de Trabalho\mw10\modularwarfare-shining-2023.2.4.4f-fix10.jar!\org\spongepowered\asm\mixin\throwables\ClassMetadataNotFoundException.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */