/*    */ package org.spongepowered.asm.service.modlauncher;
/*    */ 
/*    */ import cpw.mods.modlauncher.Launcher;
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
/*    */ public class MixinServiceModLauncherBootstrap
/*    */   implements IMixinServiceBootstrap
/*    */ {
/*    */   public String getName() {
/* 39 */     return "ModLauncher";
/*    */   }
/*    */ 
/*    */   
/*    */   public String getServiceClassName() {
/* 44 */     return "org.spongepowered.asm.service.modlauncher.MixinServiceModLauncher";
/*    */   }
/*    */ 
/*    */   
/*    */   public void bootstrap() {
/*    */     try {
/* 50 */       Launcher.INSTANCE.hashCode();
/* 51 */     } catch (Throwable th) {
/* 52 */       throw new ServiceInitialisationException(getName() + " is not available");
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\DuduM\OneDrive\Área de Trabalho\mw10\modularwarfare-shining-2023.2.4.4f-fix10.jar!\org\spongepowered\asm\service\modlauncher\MixinServiceModLauncherBootstrap.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */