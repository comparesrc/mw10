/*     */ package org.spongepowered.tools.obfuscation;
/*     */ 
/*     */ import java.lang.annotation.Annotation;
/*     */ import java.util.HashSet;
/*     */ import java.util.Set;
/*     */ import javax.annotation.processing.RoundEnvironment;
/*     */ import javax.lang.model.element.Element;
/*     */ import javax.lang.model.element.ElementKind;
/*     */ import javax.lang.model.element.ExecutableElement;
/*     */ import javax.lang.model.element.TypeElement;
/*     */ import javax.tools.Diagnostic;
/*     */ import org.spongepowered.asm.mixin.injection.At;
/*     */ import org.spongepowered.asm.mixin.injection.struct.InjectionInfo;
/*     */ import org.spongepowered.tools.obfuscation.mirror.AnnotationHandle;
/*     */ import org.spongepowered.tools.obfuscation.mirror.TypeUtils;
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
/*     */ public class MixinObfuscationProcessorInjection
/*     */   extends MixinObfuscationProcessor
/*     */ {
/*     */   public Set<String> getSupportedAnnotationTypes() {
/*  52 */     Set<String> supportedAnnotationTypes = new HashSet<>();
/*  53 */     supportedAnnotationTypes.add(At.class.getName());
/*  54 */     for (Class<? extends Annotation> annotationType : (Iterable<Class<? extends Annotation>>)InjectionInfo.getRegisteredAnnotations()) {
/*  55 */       supportedAnnotationTypes.add(annotationType.getName());
/*     */     }
/*  57 */     return supportedAnnotationTypes;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
/*  67 */     if (roundEnv.processingOver()) {
/*  68 */       postProcess(roundEnv);
/*  69 */       return true;
/*     */     } 
/*     */     
/*  72 */     processMixins(roundEnv);
/*  73 */     for (Class<? extends Annotation> annotationType : (Iterable<Class<? extends Annotation>>)InjectionInfo.getRegisteredAnnotations()) {
/*  74 */       processInjectors(roundEnv, annotationType);
/*     */     }
/*  76 */     postProcess(roundEnv);
/*     */     
/*  78 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   protected void postProcess(RoundEnvironment roundEnv) {
/*  83 */     super.postProcess(roundEnv);
/*     */     
/*     */     try {
/*  86 */       this.mixins.writeReferences();
/*  87 */     } catch (Exception ex) {
/*  88 */       ex.printStackTrace();
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void processInjectors(RoundEnvironment roundEnv, Class<? extends Annotation> injectorClass) {
/*  97 */     for (Element elem : roundEnv.getElementsAnnotatedWith(injectorClass)) {
/*  98 */       Element parent = elem.getEnclosingElement();
/*  99 */       if (!(parent instanceof TypeElement)) {
/* 100 */         throw new IllegalStateException("@" + injectorClass.getSimpleName() + " element has unexpected parent with type " + 
/* 101 */             TypeUtils.getElementType(parent));
/*     */       }
/*     */       
/* 104 */       AnnotationHandle inject = AnnotationHandle.of(elem, injectorClass);
/*     */       
/* 106 */       if (elem.getKind() == ElementKind.METHOD) {
/* 107 */         this.mixins.registerInjector((TypeElement)parent, (ExecutableElement)elem, inject); continue;
/*     */       } 
/* 109 */       this.mixins.printMessage(Diagnostic.Kind.WARNING, "Found an @" + injectorClass
/* 110 */           .getSimpleName() + " annotation on an element which is not a method: " + elem.toString());
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\DuduM\OneDrive\Área de Trabalho\mw10\modularwarfare-shining-2023.2.4.4f-fix10.jar!\org\spongepowered\tools\obfuscation\MixinObfuscationProcessorInjection.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */