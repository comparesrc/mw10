/*    */ package org.spongepowered.asm.mixin.transformer;
/*    */ 
/*    */ import java.util.Locale;
/*    */ import org.apache.logging.log4j.LogManager;
/*    */ import org.apache.logging.log4j.Logger;
/*    */ import org.objectweb.asm.tree.ClassNode;
/*    */ import org.spongepowered.asm.mixin.MixinEnvironment;
/*    */ import org.spongepowered.asm.mixin.transformer.ext.Extensions;
/*    */ import org.spongepowered.asm.mixin.transformer.ext.IClassGenerator;
/*    */ import org.spongepowered.asm.service.IMixinAuditTrail;
/*    */ import org.spongepowered.asm.service.MixinService;
/*    */ import org.spongepowered.asm.util.perf.Profiler;
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
/*    */ public class MixinClassGenerator
/*    */ {
/* 48 */   static final Logger logger = LogManager.getLogger("mixin");
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   private final Extensions extensions;
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   private final Profiler profiler;
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   private final IMixinAuditTrail auditTrail;
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   MixinClassGenerator(MixinEnvironment environment, Extensions extensions) {
/* 69 */     this.extensions = extensions;
/* 70 */     this.profiler = MixinEnvironment.getProfiler();
/* 71 */     this.auditTrail = MixinService.getService().getAuditTrail();
/*    */   }
/*    */   
/*    */   synchronized boolean generateClass(MixinEnvironment environment, String name, ClassNode classNode) {
/* 75 */     if (name == null) {
/* 76 */       logger.warn("MixinClassGenerator tried to generate a class with no name!");
/* 77 */       return false;
/*    */     } 
/*    */     
/* 80 */     for (IClassGenerator generator : this.extensions.getGenerators()) {
/* 81 */       Profiler.Section genTimer = this.profiler.begin(new String[] { "generator", generator.getClass().getSimpleName().toLowerCase(Locale.ROOT) });
/* 82 */       boolean success = generator.generate(name, classNode);
/* 83 */       genTimer.end();
/* 84 */       if (success) {
/* 85 */         if (this.auditTrail != null) {
/* 86 */           this.auditTrail.onGenerate(name, generator.getName());
/*    */         }
/* 88 */         this.extensions.export(environment, name.replace('.', '/'), false, classNode);
/* 89 */         return true;
/*    */       } 
/*    */     } 
/*    */     
/* 93 */     return false;
/*    */   }
/*    */ }


/* Location:              C:\Users\DuduM\OneDrive\Área de Trabalho\mw10\modularwarfare-shining-2023.2.4.4f-fix10.jar!\org\spongepowered\asm\mixin\transformer\MixinClassGenerator.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */