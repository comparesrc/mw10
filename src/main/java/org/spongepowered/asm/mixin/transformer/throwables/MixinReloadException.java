/*    */ package org.spongepowered.asm.mixin.transformer.throwables;
/*    */ 
/*    */ import org.spongepowered.asm.mixin.extensibility.IMixinInfo;
/*    */ import org.spongepowered.asm.mixin.throwables.MixinException;
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
/*    */ public class MixinReloadException
/*    */   extends MixinException
/*    */ {
/*    */   private static final long serialVersionUID = 2L;
/*    */   private final IMixinInfo mixinInfo;
/*    */   
/*    */   public MixinReloadException(IMixinInfo mixinInfo, String message) {
/* 41 */     super(message);
/* 42 */     this.mixinInfo = mixinInfo;
/*    */   }
/*    */   
/*    */   public IMixinInfo getMixinInfo() {
/* 46 */     return this.mixinInfo;
/*    */   }
/*    */ }


/* Location:              C:\Users\DuduM\OneDrive\Área de Trabalho\mw10\modularwarfare-shining-2023.2.4.4f-fix10.jar!\org\spongepowered\asm\mixin\transformer\throwables\MixinReloadException.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */