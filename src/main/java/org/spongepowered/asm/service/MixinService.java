/*     */ package org.spongepowered.asm.service;
/*     */ 
/*     */ import com.google.common.base.Joiner;
/*     */ import java.util.ArrayList;
/*     */ import java.util.HashSet;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import java.util.ServiceConfigurationError;
/*     */ import java.util.ServiceLoader;
/*     */ import java.util.Set;
/*     */ import org.apache.logging.log4j.LogManager;
/*     */ import org.apache.logging.log4j.Logger;
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
/*     */ public final class MixinService
/*     */ {
/*  51 */   private static final Logger logger = LogManager.getLogger("mixin");
/*     */ 
/*     */   
/*     */   private static MixinService instance;
/*     */ 
/*     */   
/*     */   private ServiceLoader<IMixinServiceBootstrap> bootstrapServiceLoader;
/*     */ 
/*     */   
/*  60 */   private final Set<String> bootedServices = new HashSet<>();
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private ServiceLoader<IMixinService> serviceLoader;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  70 */   private IMixinService service = null;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private IGlobalPropertyService propertyService;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private MixinService() {
/*  81 */     runBootServices();
/*     */   }
/*     */   
/*     */   private void runBootServices() {
/*  85 */     this.bootstrapServiceLoader = ServiceLoader.load(IMixinServiceBootstrap.class, getClass().getClassLoader());
/*  86 */     Iterator<IMixinServiceBootstrap> iter = this.bootstrapServiceLoader.iterator();
/*  87 */     while (iter.hasNext()) {
/*     */       try {
/*  89 */         IMixinServiceBootstrap bootService = iter.next();
/*  90 */         bootService.bootstrap();
/*  91 */         this.bootedServices.add(bootService.getServiceClassName());
/*  92 */       } catch (ServiceInitialisationException ex) {
/*     */         
/*  94 */         logger.debug("Mixin bootstrap service {} is not available: {}", new Object[] { ex.getStackTrace()[0].getClassName(), ex.getMessage() });
/*  95 */       } catch (Throwable th) {
/*  96 */         logger.debug("Catching {}:{} initialising service", new Object[] { th.getClass().getName(), th.getMessage(), th });
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static MixinService getInstance() {
/* 105 */     if (instance == null) {
/* 106 */       instance = new MixinService();
/*     */     }
/*     */     
/* 109 */     return instance;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void boot() {
/* 116 */     getInstance();
/*     */   }
/*     */   
/*     */   public static IMixinService getService() {
/* 120 */     return getInstance().getServiceInstance();
/*     */   }
/*     */   
/*     */   private synchronized IMixinService getServiceInstance() {
/* 124 */     if (this.service == null) {
/* 125 */       this.service = initService();
/*     */     }
/* 127 */     return this.service;
/*     */   }
/*     */   
/*     */   private IMixinService initService() {
/* 131 */     this.serviceLoader = ServiceLoader.load(IMixinService.class, getClass().getClassLoader());
/* 132 */     Iterator<IMixinService> iter = this.serviceLoader.iterator();
/* 133 */     List<String> badServices = new ArrayList<>();
/* 134 */     int brokenServiceCount = 0;
/* 135 */     while (iter.hasNext()) {
/*     */       try {
/* 137 */         IMixinService service = iter.next();
/* 138 */         if (this.bootedServices.contains(service.getClass().getName())) {
/* 139 */           logger.debug("MixinService [{}] was successfully booted in {}", new Object[] { service.getName(), getClass().getClassLoader() });
/*     */         }
/* 141 */         if (service.isValid()) {
/* 142 */           return service;
/*     */         }
/* 144 */         logger.debug("MixinService [{}] is not valid", new Object[] { service.getName() });
/* 145 */         badServices.add(String.format("INVALID[%s]", new Object[] { service.getName() }));
/* 146 */       } catch (ServiceConfigurationError sce) {
/*     */         
/* 148 */         brokenServiceCount++;
/* 149 */       } catch (Throwable th) {
/* 150 */         String faultingClassName = th.getStackTrace()[0].getClassName();
/* 151 */         logger.debug("MixinService [{}] failed initialisation: {}", new Object[] { faultingClassName, th.getMessage() });
/* 152 */         int pos = faultingClassName.lastIndexOf('.');
/* 153 */         badServices.add(String.format("ERROR[%s]", new Object[] { (pos < 0) ? faultingClassName : faultingClassName.substring(pos + 1) }));
/*     */       } 
/*     */     } 
/*     */ 
/*     */     
/* 158 */     String brokenServiceNote = (brokenServiceCount == 0) ? "" : (" and " + brokenServiceCount + " other invalid services.");
/* 159 */     throw new ServiceNotAvailableError("No mixin host service is available. Services: " + Joiner.on(", ").join(badServices) + brokenServiceNote);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static IGlobalPropertyService getGlobalPropertyService() {
/* 166 */     return getInstance().getGlobalPropertyServiceInstance();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private IGlobalPropertyService getGlobalPropertyServiceInstance() {
/* 175 */     if (this.propertyService == null) {
/* 176 */       this.propertyService = initPropertyService();
/*     */     }
/* 178 */     return this.propertyService;
/*     */   }
/*     */   
/*     */   private IGlobalPropertyService initPropertyService() {
/* 182 */     ServiceLoader<IGlobalPropertyService> serviceLoader = ServiceLoader.load(IGlobalPropertyService.class, 
/* 183 */         getClass().getClassLoader());
/*     */     
/* 185 */     Iterator<IGlobalPropertyService> iter = serviceLoader.iterator();
/* 186 */     while (iter.hasNext()) {
/*     */       try {
/* 188 */         IGlobalPropertyService service = iter.next();
/* 189 */         return service;
/* 190 */       } catch (ServiceConfigurationError serviceConfigurationError) {
/*     */       
/* 192 */       } catch (Throwable throwable) {}
/*     */     } 
/*     */ 
/*     */     
/* 196 */     throw new ServiceNotAvailableError("No mixin global property service is available");
/*     */   }
/*     */ }


/* Location:              C:\Users\DuduM\OneDrive\Área de Trabalho\mw10\modularwarfare-shining-2023.2.4.4f-fix10.jar!\org\spongepowered\asm\service\MixinService.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */