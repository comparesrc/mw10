/*    */ package org.spongepowered.asm.service.mojang;
/*    */ 
/*    */ import net.minecraft.launchwrapper.Launch;
/*    */ import org.spongepowered.asm.service.IMixinServiceBootstrap;
/*    */ import org.spongepowered.asm.service.ServiceInitialisationException;
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
/*    */ public class MixinServiceLaunchWrapperBootstrap
/*    */   implements IMixinServiceBootstrap
/*    */ {
/*    */   private static final String SERVICE_PACKAGE = "org.spongepowered.asm.service.";
/*    */   private static final String MIXIN_UTIL_PACKAGE = "org.spongepowered.asm.util.";
/*    */   private static final String LEGACY_ASM_PACKAGE = "org.spongepowered.asm.lib.";
/*    */   private static final String ASM_PACKAGE = "org.objectweb.asm.";
/*    */   private static final String MIXIN_PACKAGE = "org.spongepowered.asm.mixin.";
/*    */   
/*    */   public String getName() {
/* 46 */     return "LaunchWrapper";
/*    */   }
/*    */ 
/*    */   
/*    */   public String getServiceClassName() {
/* 51 */     return "org.spongepowered.asm.service.mojang.MixinServiceLaunchWrapper";
/*    */   }
/*    */ 
/*    */   
/*    */   public void bootstrap() {
/*    */     try {
/* 57 */       Launch.classLoader.hashCode();
/* 58 */     } catch (Throwable th) {
/* 59 */       throw new ServiceInitialisationException(getName() + " is not available");
/*    */     } 
/*    */ 
/*    */     
/* 63 */     Launch.classLoader.addClassLoaderExclusion("org.spongepowered.asm.service.");
/*    */ 
/*    */     
/* 66 */     Launch.classLoader.addClassLoaderExclusion("org.objectweb.asm.");
/* 67 */     Launch.classLoader.addClassLoaderExclusion("org.spongepowered.asm.lib.");
/* 68 */     Launch.classLoader.addClassLoaderExclusion("org.spongepowered.asm.mixin.");
/* 69 */     Launch.classLoader.addClassLoaderExclusion("org.spongepowered.asm.util.");
/*    */   }
/*    */ }


/* Location:              C:\Users\DuduM\OneDrive\Área de Trabalho\mw10\modularwarfare-shining-2023.2.4.4f-fix10.jar!\org\spongepowered\asm\service\mojang\MixinServiceLaunchWrapperBootstrap.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */