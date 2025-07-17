/*    */ package org.spongepowered.asm.launch.platform;
/*    */ 
/*    */ import org.spongepowered.asm.launch.platform.container.IContainerHandle;
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
/*    */ public interface IMixinPlatformAgent
/*    */ {
/*    */   AcceptResult accept(MixinPlatformManager paramMixinPlatformManager, IContainerHandle paramIContainerHandle);
/*    */   
/*    */   String getPhaseProvider();
/*    */   
/*    */   void prepare();
/*    */   
/*    */   void initPrimaryContainer();
/*    */   
/*    */   void inject();
/*    */   
/*    */   public enum AcceptResult
/*    */   {
/* 49 */     ACCEPTED,
/*    */ 
/*    */ 
/*    */ 
/*    */     
/* 54 */     REJECTED,
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */     
/* 60 */     INVALID;
/*    */   }
/*    */ }


/* Location:              C:\Users\DuduM\OneDrive\Área de Trabalho\mw10\modularwarfare-shining-2023.2.4.4f-fix10.jar!\org\spongepowered\asm\launch\platform\IMixinPlatformAgent.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */