/*     */ package org.spongepowered.asm.mixin;
/*     */ 
/*     */ import java.util.Collections;
/*     */ import java.util.HashSet;
/*     */ import java.util.LinkedHashSet;
/*     */ import java.util.Set;
/*     */ import org.apache.logging.log4j.LogManager;
/*     */ import org.apache.logging.log4j.Logger;
/*     */ import org.spongepowered.asm.launch.GlobalProperties;
/*     */ import org.spongepowered.asm.mixin.extensibility.IMixinInfo;
/*     */ import org.spongepowered.asm.mixin.transformer.ClassInfo;
/*     */ import org.spongepowered.asm.mixin.transformer.Config;
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
/*     */ public final class Mixins
/*     */ {
/*  50 */   private static final Logger logger = LogManager.getLogger("mixin");
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  55 */   private static final GlobalProperties.Keys CONFIGS_KEY = GlobalProperties.Keys.of(GlobalProperties.Keys.CONFIGS + ".queue");
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  60 */   private static final Set<String> errorHandlers = new LinkedHashSet<>();
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  65 */   private static final Set<String> registeredConfigs = new HashSet<>();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void addConfigurations(String... configFiles) {
/*  75 */     MixinEnvironment fallback = MixinEnvironment.getDefaultEnvironment();
/*  76 */     for (String configFile : configFiles) {
/*  77 */       createConfiguration(configFile, fallback);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void addConfiguration(String configFile) {
/*  87 */     createConfiguration(configFile, MixinEnvironment.getDefaultEnvironment());
/*     */   }
/*     */   
/*     */   @Deprecated
/*     */   static void addConfiguration(String configFile, MixinEnvironment fallback) {
/*  92 */     createConfiguration(configFile, fallback);
/*     */   }
/*     */ 
/*     */   
/*     */   private static void createConfiguration(String configFile, MixinEnvironment fallback) {
/*  97 */     Config config = null;
/*     */     
/*     */     try {
/* 100 */       config = Config.create(configFile, fallback);
/* 101 */     } catch (Exception ex) {
/* 102 */       logger.error("Error encountered reading mixin config " + configFile + ": " + ex.getClass().getName() + " " + ex.getMessage(), ex);
/*     */     } 
/*     */     
/* 105 */     registerConfiguration(config);
/*     */   }
/*     */   
/*     */   private static void registerConfiguration(Config config) {
/* 109 */     if (config == null || registeredConfigs.contains(config.getName())) {
/*     */       return;
/*     */     }
/*     */     
/* 113 */     MixinEnvironment env = config.getEnvironment();
/* 114 */     if (env != null) {
/* 115 */       env.registerConfig(config.getName());
/*     */     }
/* 117 */     getConfigs().add(config);
/* 118 */     registeredConfigs.add(config.getName());
/*     */     
/* 120 */     Config parent = config.getParent();
/* 121 */     if (parent != null) {
/* 122 */       registerConfiguration(parent);
/*     */     }
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static int getUnvisitedCount() {
/* 142 */     int count = 0;
/* 143 */     for (Config config : getConfigs()) {
/* 144 */       if (!config.isVisited()) {
/* 145 */         count++;
/*     */       }
/*     */     } 
/* 148 */     return count;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static Set<Config> getConfigs() {
/* 156 */     Set<Config> mixinConfigs = (Set<Config>)GlobalProperties.get(CONFIGS_KEY);
/* 157 */     if (mixinConfigs == null) {
/* 158 */       mixinConfigs = new LinkedHashSet<>();
/* 159 */       GlobalProperties.put(CONFIGS_KEY, mixinConfigs);
/*     */     } 
/* 161 */     return mixinConfigs;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static Set<IMixinInfo> getMixinsForClass(String className) {
/* 172 */     ClassInfo classInfo = ClassInfo.fromCache(className);
/* 173 */     if (classInfo != null) {
/* 174 */       return classInfo.getAppliedMixins();
/*     */     }
/* 176 */     return Collections.emptySet();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void registerErrorHandlerClass(String handlerName) {
/* 185 */     if (handlerName != null) {
/* 186 */       errorHandlers.add(handlerName);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static Set<String> getErrorHandlerClasses() {
/* 194 */     return Collections.unmodifiableSet(errorHandlers);
/*     */   }
/*     */ }


/* Location:              C:\Users\DuduM\OneDrive\Área de Trabalho\mw10\modularwarfare-shining-2023.2.4.4f-fix10.jar!\org\spongepowered\asm\mixin\Mixins.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */