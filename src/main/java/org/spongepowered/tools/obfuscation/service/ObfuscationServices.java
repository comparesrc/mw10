/*     */ package org.spongepowered.tools.obfuscation.service;
/*     */ 
/*     */ import java.util.Collection;
/*     */ import java.util.HashSet;
/*     */ import java.util.Locale;
/*     */ import java.util.ServiceConfigurationError;
/*     */ import java.util.ServiceLoader;
/*     */ import java.util.Set;
/*     */ import javax.tools.Diagnostic;
/*     */ import org.spongepowered.tools.obfuscation.ObfuscationType;
/*     */ import org.spongepowered.tools.obfuscation.interfaces.IMixinAnnotationProcessor;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class ObfuscationServices
/*     */ {
/*     */   private static ObfuscationServices instance;
/*     */   private final ServiceLoader<IObfuscationService> serviceLoader;
/*  58 */   private final Set<IObfuscationService> services = new HashSet<>();
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private ObfuscationServices() {
/*  64 */     this.serviceLoader = ServiceLoader.load(IObfuscationService.class, getClass().getClassLoader());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static ObfuscationServices getInstance() {
/*  71 */     if (instance == null) {
/*  72 */       instance = new ObfuscationServices();
/*     */     }
/*  74 */     return instance;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void initProviders(IMixinAnnotationProcessor ap) {
/*  83 */     boolean defaultIsPresent = false;
/*     */     
/*     */     try {
/*  86 */       for (IObfuscationService service : this.serviceLoader) {
/*  87 */         if (!this.services.contains(service)) {
/*  88 */           this.services.add(service);
/*     */           
/*  90 */           String serviceName = service.getClass().getSimpleName();
/*     */           
/*  92 */           Collection<ObfuscationTypeDescriptor> obfTypes = service.getObfuscationTypes(ap);
/*  93 */           if (obfTypes != null) {
/*  94 */             for (ObfuscationTypeDescriptor obfType : obfTypes) {
/*     */               try {
/*  96 */                 ObfuscationType type = ObfuscationType.create(obfType, ap);
/*  97 */                 ap.printMessage(Diagnostic.Kind.NOTE, serviceName + " supports type: \"" + type + "\"");
/*  98 */                 defaultIsPresent |= type.isDefault();
/*  99 */               } catch (Exception ex) {
/* 100 */                 ex.printStackTrace();
/*     */               } 
/*     */             } 
/*     */           }
/*     */         } 
/*     */       } 
/* 106 */     } catch (ServiceConfigurationError serviceError) {
/* 107 */       ap.printMessage(Diagnostic.Kind.ERROR, serviceError.getClass().getSimpleName() + ": " + serviceError.getMessage());
/* 108 */       serviceError.printStackTrace();
/*     */     } 
/*     */     
/* 111 */     if (!defaultIsPresent) {
/* 112 */       String defaultEnv = ap.getOption("defaultObfuscationEnv");
/* 113 */       if (defaultEnv == null) {
/* 114 */         ap.printMessage(Diagnostic.Kind.WARNING, "No default obfuscation environment was specified and \"searge\" is not available. Please ensure defaultObfuscationEnv is specified in your build configuration");
/*     */       } else {
/*     */         
/* 117 */         ap.printMessage(Diagnostic.Kind.WARNING, "Specified default obfuscation environment \"" + defaultEnv.toLowerCase(Locale.ROOT) + "\" was not defined. This probably means your build configuration is out of date or a required service is missing");
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Set<String> getSupportedOptions() {
/* 127 */     Set<String> supportedOptions = new HashSet<>();
/* 128 */     for (IObfuscationService provider : this.services) {
/* 129 */       Set<String> options = provider.getSupportedOptions();
/* 130 */       if (options != null) {
/* 131 */         supportedOptions.addAll(options);
/*     */       }
/*     */     } 
/* 134 */     return supportedOptions;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public IObfuscationService getService(Class<? extends IObfuscationService> serviceClass) {
/* 144 */     for (IObfuscationService service : this.services) {
/* 145 */       if (serviceClass.getName().equals(service.getClass().getName())) {
/* 146 */         return service;
/*     */       }
/*     */     } 
/* 149 */     return null;
/*     */   }
/*     */ }


/* Location:              C:\Users\DuduM\OneDrive\Área de Trabalho\mw10\modularwarfare-shining-2023.2.4.4f-fix10.jar!\org\spongepowered\tools\obfuscation\service\ObfuscationServices.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */