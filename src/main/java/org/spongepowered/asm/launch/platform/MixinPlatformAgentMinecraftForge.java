/*    */ package org.spongepowered.asm.launch.platform;
/*    */ 
/*    */ import cpw.mods.modlauncher.Environment;
/*    */ import cpw.mods.modlauncher.Launcher;
/*    */ import cpw.mods.modlauncher.api.IEnvironment;
/*    */ import java.util.Collection;
/*    */ import java.util.Locale;
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
/*    */ public class MixinPlatformAgentMinecraftForge
/*    */   extends MixinPlatformAgentAbstract
/*    */   implements IMixinPlatformServiceAgent
/*    */ {
/*    */   public void init() {}
/*    */   
/*    */   public IMixinPlatformAgent.AcceptResult accept(MixinPlatformManager manager, IContainerHandle handle) {
/* 58 */     return IMixinPlatformAgent.AcceptResult.REJECTED;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public String getSideName() {
/* 67 */     Environment environment = Launcher.INSTANCE.environment();
/* 68 */     String launchTarget = ((String)environment.getProperty(IEnvironment.Keys.LAUNCHTARGET.get()).orElse("missing")).toLowerCase(Locale.ROOT);
/* 69 */     if (launchTarget.contains("server")) {
/* 70 */       return "SERVER";
/*    */     }
/* 72 */     if (launchTarget.contains("client")) {
/* 73 */       return "CLIENT";
/*    */     }
/* 75 */     return null;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public Collection<IContainerHandle> getMixinContainers() {
/* 84 */     return null;
/*    */   }
/*    */ }


/* Location:              C:\Users\DuduM\OneDrive\Área de Trabalho\mw10\modularwarfare-shining-2023.2.4.4f-fix10.jar!\org\spongepowered\asm\launch\platform\MixinPlatformAgentMinecraftForge.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */