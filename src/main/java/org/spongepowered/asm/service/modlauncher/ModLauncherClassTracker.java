/*     */ package org.spongepowered.asm.service.modlauncher;
/*     */ 
/*     */ import cpw.mods.modlauncher.serviceapi.ILaunchPluginService;
/*     */ import java.util.EnumSet;
/*     */ import java.util.HashSet;
/*     */ import java.util.Set;
/*     */ import org.objectweb.asm.Type;
/*     */ import org.objectweb.asm.tree.ClassNode;
/*     */ import org.spongepowered.asm.launch.IClassProcessor;
/*     */ import org.spongepowered.asm.launch.Phases;
/*     */ import org.spongepowered.asm.service.IClassTracker;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class ModLauncherClassTracker
/*     */   implements IClassProcessor, IClassTracker
/*     */ {
/*  46 */   private final Set<String> invalidClasses = new HashSet<>();
/*     */   
/*  48 */   private final Set<String> loadedClasses = new HashSet<>();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void registerInvalidClass(String className) {
/*  56 */     synchronized (this.invalidClasses) {
/*  57 */       this.invalidClasses.add(className);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isClassLoaded(String className) {
/*  67 */     synchronized (this.loadedClasses) {
/*  68 */       return this.loadedClasses.contains(className);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getClassRestrictions(String className) {
/*  78 */     return "";
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public EnumSet<ILaunchPluginService.Phase> handlesClass(Type classType, boolean isEmpty, String reason) {
/*  87 */     String name = classType.getClassName();
/*  88 */     synchronized (this.invalidClasses) {
/*  89 */       if (this.invalidClasses.contains(name)) {
/*  90 */         throw new NoClassDefFoundError(String.format("%s is invalid", new Object[] { name }));
/*     */       }
/*     */     } 
/*     */     
/*  94 */     return Phases.AFTER_ONLY;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean processClass(ILaunchPluginService.Phase phase, ClassNode classNode, Type classType, String reason) {
/* 105 */     synchronized (this.loadedClasses) {
/* 106 */       this.loadedClasses.add(classType.getClassName());
/*     */     } 
/*     */     
/* 109 */     return false;
/*     */   }
/*     */ }


/* Location:              C:\Users\DuduM\OneDrive\Área de Trabalho\mw10\modularwarfare-shining-2023.2.4.4f-fix10.jar!\org\spongepowered\asm\service\modlauncher\ModLauncherClassTracker.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */