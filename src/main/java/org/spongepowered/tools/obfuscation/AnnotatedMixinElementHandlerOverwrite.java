/*     */ package org.spongepowered.tools.obfuscation;
/*     */ 
/*     */ import java.lang.reflect.Method;
/*     */ import java.util.Locale;
/*     */ import javax.annotation.processing.Messager;
/*     */ import javax.lang.model.element.ExecutableElement;
/*     */ import javax.tools.Diagnostic;
/*     */ import org.spongepowered.asm.obfuscation.mapping.common.MappingMethod;
/*     */ import org.spongepowered.tools.obfuscation.interfaces.IMixinAnnotationProcessor;
/*     */ import org.spongepowered.tools.obfuscation.mirror.AnnotationHandle;
/*     */ import org.spongepowered.tools.obfuscation.mirror.TypeHandle;
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
/*     */ class AnnotatedMixinElementHandlerOverwrite
/*     */   extends AnnotatedMixinElementHandler
/*     */ {
/*     */   static class AnnotatedElementOverwrite
/*     */     extends AnnotatedMixinElementHandler.AnnotatedElement<ExecutableElement>
/*     */   {
/*     */     private final boolean shouldRemap;
/*     */     
/*     */     public AnnotatedElementOverwrite(ExecutableElement element, AnnotationHandle annotation, boolean shouldRemap) {
/*  52 */       super(element, annotation);
/*  53 */       this.shouldRemap = shouldRemap;
/*     */     }
/*     */     
/*     */     public boolean shouldRemap() {
/*  57 */       return this.shouldRemap;
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   AnnotatedMixinElementHandlerOverwrite(IMixinAnnotationProcessor ap, AnnotatedMixin mixin) {
/*  63 */     super(ap, mixin);
/*     */   }
/*     */   
/*     */   public void registerMerge(ExecutableElement method) {
/*  67 */     validateTargetMethod(method, null, new AnnotatedMixinElementHandler.AliasedElementName(method, AnnotationHandle.MISSING), "overwrite", true, true);
/*     */   }
/*     */   
/*     */   public void registerOverwrite(AnnotatedElementOverwrite elem) {
/*  71 */     AnnotatedMixinElementHandler.AliasedElementName name = new AnnotatedMixinElementHandler.AliasedElementName(elem.getElement(), elem.getAnnotation());
/*  72 */     validateTargetMethod(elem.getElement(), elem.getAnnotation(), name, "@Overwrite", true, false);
/*  73 */     checkConstraints(elem.getElement(), elem.getAnnotation());
/*     */     
/*  75 */     if (elem.shouldRemap()) {
/*  76 */       for (TypeHandle target : this.mixin.getTargets()) {
/*  77 */         if (!registerOverwriteForTarget(elem, target)) {
/*     */           return;
/*     */         }
/*     */       } 
/*     */     }
/*     */     
/*  83 */     if (!"true".equalsIgnoreCase(this.ap.getOption("disableOverwriteChecker"))) {
/*  84 */       Diagnostic.Kind overwriteErrorKind = "error".equalsIgnoreCase(this.ap.getOption("overwriteErrorLevel")) ? Diagnostic.Kind.ERROR : Diagnostic.Kind.WARNING;
/*     */ 
/*     */       
/*  87 */       String javadoc = this.ap.getJavadocProvider().getJavadoc(elem.getElement());
/*  88 */       if (javadoc == null) {
/*  89 */         this.ap.printMessage(overwriteErrorKind, "@Overwrite is missing javadoc comment", elem.getElement(), SuppressedBy.OVERWRITE);
/*     */         
/*     */         return;
/*     */       } 
/*  93 */       if (!javadoc.toLowerCase(Locale.ROOT).contains("@author")) {
/*  94 */         this.ap.printMessage(overwriteErrorKind, "@Overwrite is missing an @author tag", elem.getElement(), SuppressedBy.OVERWRITE);
/*     */       }
/*     */       
/*  97 */       if (!javadoc.toLowerCase(Locale.ROOT).contains("@reason")) {
/*  98 */         this.ap.printMessage(overwriteErrorKind, "@Overwrite is missing an @reason tag", elem.getElement(), SuppressedBy.OVERWRITE);
/*     */       }
/*     */     } 
/*     */   }
/*     */   
/*     */   private boolean registerOverwriteForTarget(AnnotatedElementOverwrite elem, TypeHandle target) {
/* 104 */     MappingMethod targetMethod = target.getMappingMethod(elem.getSimpleName(), elem.getDesc());
/* 105 */     ObfuscationData<MappingMethod> obfData = this.obf.getDataProvider().getObfMethod(targetMethod);
/*     */     
/* 107 */     if (obfData.isEmpty()) {
/* 108 */       Diagnostic.Kind error = Diagnostic.Kind.ERROR;
/*     */ 
/*     */       
/*     */       try {
/* 112 */         Method md = elem.getElement().getClass().getMethod("isStatic", new Class[0]);
/* 113 */         if (((Boolean)md.invoke(elem.getElement(), new Object[0])).booleanValue()) {
/* 114 */           error = Diagnostic.Kind.WARNING;
/*     */         }
/* 116 */       } catch (Exception exception) {}
/*     */ 
/*     */ 
/*     */       
/* 120 */       this.ap.printMessage(error, "No obfuscation mapping for @Overwrite method", elem.getElement());
/* 121 */       return false;
/*     */     } 
/*     */     
/*     */     try {
/* 125 */       addMethodMappings(elem.getSimpleName(), elem.getDesc(), obfData);
/* 126 */     } catch (MappingConflictException ex) {
/* 127 */       elem.printMessage((Messager)this.ap, Diagnostic.Kind.ERROR, "Mapping conflict for @Overwrite method: " + ex.getNew().getSimpleName() + " for target " + target + " conflicts with existing mapping " + ex
/* 128 */           .getOld().getSimpleName());
/* 129 */       return false;
/*     */     } 
/* 131 */     return true;
/*     */   }
/*     */ }


/* Location:              C:\Users\DuduM\OneDrive\Área de Trabalho\mw10\modularwarfare-shining-2023.2.4.4f-fix10.jar!\org\spongepowered\tools\obfuscation\AnnotatedMixinElementHandlerOverwrite.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */