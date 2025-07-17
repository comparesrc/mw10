/*    */ package org.spongepowered.asm.launch.platform;
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
/*    */ public class MixinPlatformAgentDefault
/*    */   extends MixinPlatformAgentAbstract
/*    */ {
/*    */   public void prepare() {
/* 38 */     String compatibilityLevel = this.handle.getAttribute("MixinCompatibilityLevel");
/* 39 */     if (compatibilityLevel != null) {
/* 40 */       this.manager.setCompatibilityLevel(compatibilityLevel);
/*    */     }
/*    */     
/* 43 */     String mixinConfigs = this.handle.getAttribute("MixinConfigs");
/* 44 */     if (mixinConfigs != null) {
/* 45 */       for (String config : mixinConfigs.split(",")) {
/* 46 */         this.manager.addConfig(config.trim());
/*    */       }
/*    */     }
/*    */     
/* 50 */     String tokenProviders = this.handle.getAttribute("MixinTokenProviders");
/* 51 */     if (tokenProviders != null) {
/* 52 */       for (String provider : tokenProviders.split(",")) {
/* 53 */         this.manager.addTokenProvider(provider.trim());
/*    */       }
/*    */     }
/*    */     
/* 57 */     String connectorClass = this.handle.getAttribute("MixinConnector");
/* 58 */     if (connectorClass != null)
/* 59 */       this.manager.addConnector(connectorClass.trim()); 
/*    */   }
/*    */ }


/* Location:              C:\Users\DuduM\OneDrive\Área de Trabalho\mw10\modularwarfare-shining-2023.2.4.4f-fix10.jar!\org\spongepowered\asm\launch\platform\MixinPlatformAgentDefault.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */