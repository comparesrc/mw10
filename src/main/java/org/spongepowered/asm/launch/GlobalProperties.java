/*     */ package org.spongepowered.asm.launch;
/*     */ 
/*     */ import java.util.HashMap;
/*     */ import java.util.Map;
/*     */ import org.spongepowered.asm.service.IGlobalPropertyService;
/*     */ import org.spongepowered.asm.service.IPropertyKey;
/*     */ import org.spongepowered.asm.service.MixinService;
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
/*     */ public final class GlobalProperties
/*     */ {
/*     */   private static IGlobalPropertyService service;
/*     */   
/*     */   public static final class Keys
/*     */   {
/*  45 */     public static final Keys INIT = of("mixin.initialised");
/*  46 */     public static final Keys AGENTS = of("mixin.agents");
/*  47 */     public static final Keys CONFIGS = of("mixin.configs");
/*  48 */     public static final Keys PLATFORM_MANAGER = of("mixin.platform");
/*     */     
/*  50 */     public static final Keys FML_LOAD_CORE_MOD = of("mixin.launch.fml.loadcoremodmethod");
/*  51 */     public static final Keys FML_GET_REPARSEABLE_COREMODS = of("mixin.launch.fml.reparseablecoremodsmethod");
/*  52 */     public static final Keys FML_CORE_MOD_MANAGER = of("mixin.launch.fml.coremodmanagerclass");
/*  53 */     public static final Keys FML_GET_IGNORED_MODS = of("mixin.launch.fml.ignoredmodsmethod");
/*     */     
/*     */     private static Map<String, Keys> keys;
/*     */     
/*     */     private final String name;
/*     */     
/*     */     private IPropertyKey key;
/*     */     
/*     */     private Keys(String name) {
/*  62 */       this.name = name;
/*     */     }
/*     */     
/*     */     IPropertyKey resolve(IGlobalPropertyService service) {
/*  66 */       if (this.key != null) {
/*  67 */         return this.key;
/*     */       }
/*  69 */       if (service == null) {
/*  70 */         return null;
/*     */       }
/*     */       
/*  73 */       return this.key = service.resolveKey(this.name);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public static Keys of(String name) {
/*  83 */       if (keys == null) {
/*  84 */         keys = new HashMap<>();
/*     */       }
/*     */       
/*  87 */       Keys key = keys.get(name);
/*  88 */       if (key == null) {
/*  89 */         key = new Keys(name);
/*  90 */         keys.put(name, key);
/*     */       } 
/*  92 */       return key;
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static IGlobalPropertyService getService() {
/* 102 */     if (service == null) {
/* 103 */       service = MixinService.getGlobalPropertyService();
/*     */     }
/* 105 */     return service;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static <T> T get(Keys key) {
/* 116 */     IGlobalPropertyService service = getService();
/* 117 */     return (T)service.getProperty(key.resolve(service));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void put(Keys key, Object value) {
/* 127 */     IGlobalPropertyService service = getService();
/* 128 */     service.setProperty(key.resolve(service), value);
/*     */   }
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
/*     */   public static <T> T get(Keys key, T defaultValue) {
/* 141 */     IGlobalPropertyService service = getService();
/* 142 */     return (T)service.getProperty(key.resolve(service), defaultValue);
/*     */   }
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
/*     */   public static String getString(Keys key, String defaultValue) {
/* 155 */     IGlobalPropertyService service = getService();
/* 156 */     return service.getPropertyString(key.resolve(service), defaultValue);
/*     */   }
/*     */ }


/* Location:              C:\Users\DuduM\OneDrive\Área de Trabalho\mw10\modularwarfare-shining-2023.2.4.4f-fix10.jar!\org\spongepowered\asm\launch\GlobalProperties.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */