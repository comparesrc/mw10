/*     */ package org.spongepowered.asm.mixin.transformer;
/*     */ 
/*     */ import cpw.mods.modlauncher.serviceapi.ILaunchPluginService;
/*     */ import java.util.EnumSet;
/*     */ import org.objectweb.asm.Type;
/*     */ import org.objectweb.asm.tree.ClassNode;
/*     */ import org.spongepowered.asm.launch.IClassProcessor;
/*     */ import org.spongepowered.asm.launch.Phases;
/*     */ import org.spongepowered.asm.mixin.MixinEnvironment;
/*     */ import org.spongepowered.asm.service.ISyntheticClassInfo;
/*     */ import org.spongepowered.asm.service.ISyntheticClassRegistry;
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
/*     */ 
/*     */ 
/*     */ public class MixinTransformationHandler
/*     */   implements IClassProcessor
/*     */ {
/*  48 */   private final Object initialisationLock = new Object();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private MixinTransformer transformer;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private ISyntheticClassRegistry registry;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public EnumSet<ILaunchPluginService.Phase> handlesClass(Type classType, boolean isEmpty, String reason) {
/*  67 */     if (!isEmpty) {
/*  68 */       return Phases.AFTER_ONLY;
/*     */     }
/*     */     
/*  71 */     if (this.registry == null) {
/*  72 */       return null;
/*     */     }
/*     */     
/*  75 */     ISyntheticClassInfo syntheticClass = this.registry.findSyntheticClass(classType.getClassName());
/*  76 */     return (syntheticClass != null) ? Phases.AFTER_ONLY : null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public synchronized boolean processClass(ILaunchPluginService.Phase phase, ClassNode classNode, Type classType, String reason) {
/*  87 */     if (phase == ILaunchPluginService.Phase.BEFORE) {
/*  88 */       return false;
/*     */     }
/*     */     
/*  91 */     MixinTransformer transformer = null;
/*  92 */     if (this.transformer == null) {
/*  93 */       synchronized (this.initialisationLock) {
/*  94 */         transformer = this.transformer;
/*  95 */         if (transformer == null) {
/*  96 */           transformer = this.transformer = new MixinTransformer();
/*  97 */           this.registry = transformer.getExtensions().getSyntheticClassRegistry();
/*     */         } 
/*     */       } 
/*     */     } else {
/* 101 */       transformer = this.transformer;
/*     */     } 
/*     */     
/* 104 */     MixinEnvironment environment = MixinEnvironment.getCurrentEnvironment();
/* 105 */     ISyntheticClassInfo syntheticClass = this.registry.findSyntheticClass(classType.getClassName());
/* 106 */     if (syntheticClass != null) {
/* 107 */       return transformer.generateClass(environment, classType.getClassName(), classNode);
/*     */     }
/* 109 */     return transformer.transformClass(environment, classType.getClassName(), classNode);
/*     */   }
/*     */ }


/* Location:              C:\Users\DuduM\OneDrive\Área de Trabalho\mw10\modularwarfare-shining-2023.2.4.4f-fix10.jar!\org\spongepowered\asm\mixin\transformer\MixinTransformationHandler.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */