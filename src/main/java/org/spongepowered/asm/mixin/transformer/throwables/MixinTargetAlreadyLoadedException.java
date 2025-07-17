/*    */ package org.spongepowered.asm.mixin.transformer.throwables;
/*    */ 
/*    */ import org.spongepowered.asm.mixin.extensibility.IMixinInfo;
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
/*    */ public class MixinTargetAlreadyLoadedException
/*    */   extends InvalidMixinException
/*    */ {
/*    */   private static final long serialVersionUID = 1L;
/*    */   private final String target;
/*    */   
/*    */   public MixinTargetAlreadyLoadedException(IMixinInfo mixin, String message, String target) {
/* 39 */     super(mixin, message);
/* 40 */     this.target = target;
/*    */   }
/*    */   
/*    */   public MixinTargetAlreadyLoadedException(IMixinInfo mixin, String message, String target, Throwable cause) {
/* 44 */     super(mixin, message, cause);
/* 45 */     this.target = target;
/*    */   }
/*    */   
/*    */   public String getTarget() {
/* 49 */     return this.target;
/*    */   }
/*    */ }


/* Location:              C:\Users\DuduM\OneDrive\Área de Trabalho\mw10\modularwarfare-shining-2023.2.4.4f-fix10.jar!\org\spongepowered\asm\mixin\transformer\throwables\MixinTargetAlreadyLoadedException.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */