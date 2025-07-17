/*    */ package org.spongepowered.asm.launch;
/*    */ 
/*    */ import java.io.File;
/*    */ import java.util.List;
/*    */ import net.minecraft.launchwrapper.ITweaker;
/*    */ import net.minecraft.launchwrapper.LaunchClassLoader;
/*    */ import org.spongepowered.asm.launch.platform.CommandLineOptions;
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
/*    */ public class MixinTweaker
/*    */   implements ITweaker
/*    */ {
/*    */   public MixinTweaker() {
/* 46 */     MixinBootstrap.start();
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public final void acceptOptions(List<String> args, File gameDir, File assetsDir, String profile) {
/* 55 */     MixinBootstrap.doInit(CommandLineOptions.ofArgs(args));
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public final void injectIntoClassLoader(LaunchClassLoader classLoader) {
/* 64 */     MixinBootstrap.inject();
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public String getLaunchTarget() {
/* 72 */     return MixinBootstrap.getPlatform().getLaunchTarget();
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public String[] getLaunchArguments() {
/* 80 */     return new String[0];
/*    */   }
/*    */ }


/* Location:              C:\Users\DuduM\OneDrive\Área de Trabalho\mw10\modularwarfare-shining-2023.2.4.4f-fix10.jar!\org\spongepowered\asm\launch\MixinTweaker.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */