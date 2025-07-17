/*    */ package org.spongepowered.asm.service.modlauncher;
/*    */ 
/*    */ import cpw.mods.gross.Java9ClassLoaderUtil;
/*    */ import cpw.mods.modlauncher.Launcher;
/*    */ import java.net.URL;
/*    */ import org.spongepowered.asm.service.IClassProvider;
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
/*    */ class ModLauncherClassProvider
/*    */   implements IClassProvider
/*    */ {
/*    */   @Deprecated
/*    */   public URL[] getClassPath() {
/* 48 */     return Java9ClassLoaderUtil.getSystemClassPathURLs();
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public Class<?> findClass(String name) throws ClassNotFoundException {
/* 57 */     return Class.forName(name, true, Thread.currentThread().getContextClassLoader());
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public Class<?> findClass(String name, boolean initialize) throws ClassNotFoundException {
/* 66 */     return Class.forName(name, initialize, Thread.currentThread().getContextClassLoader());
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public Class<?> findAgentClass(String name, boolean initialize) throws ClassNotFoundException {
/* 75 */     return Class.forName(name, initialize, Launcher.class.getClassLoader());
/*    */   }
/*    */ }


/* Location:              C:\Users\DuduM\OneDrive\Área de Trabalho\mw10\modularwarfare-shining-2023.2.4.4f-fix10.jar!\org\spongepowered\asm\service\modlauncher\ModLauncherClassProvider.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */