/*    */ package org.spongepowered.asm.launch.platform;
/*    */ 
/*    */ import java.util.Collection;
/*    */ import net.minecraft.launchwrapper.Launch;
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
/*    */ public class MixinPlatformAgentLiteLoaderLegacy
/*    */   extends MixinPlatformAgentAbstract
/*    */   implements IMixinPlatformServiceAgent
/*    */ {
/*    */   private static final String GETSIDE_METHOD = "getEnvironmentType";
/*    */   private static final String LITELOADER_TWEAKER_NAME = "com.mumfrey.liteloader.launch.LiteLoaderTweaker";
/*    */   
/*    */   public IMixinPlatformAgent.AcceptResult accept(MixinPlatformManager manager, IContainerHandle handle) {
/* 43 */     return IMixinPlatformAgent.AcceptResult.REJECTED;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public String getSideName() {
/* 52 */     return MixinPlatformAgentAbstract.invokeStringMethod((ClassLoader)Launch.classLoader, "com.mumfrey.liteloader.launch.LiteLoaderTweaker", "getEnvironmentType");
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void init() {}
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public Collection<IContainerHandle> getMixinContainers() {
/* 71 */     return null;
/*    */   }
/*    */ }


/* Location:              C:\Users\DuduM\OneDrive\Área de Trabalho\mw10\modularwarfare-shining-2023.2.4.4f-fix10.jar!\org\spongepowered\asm\launch\platform\MixinPlatformAgentLiteLoaderLegacy.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */