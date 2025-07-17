/*     */ package org.spongepowered.asm.mixin.transformer;
/*     */ 
/*     */ import com.google.common.base.Strings;
/*     */ import java.util.HashMap;
/*     */ import java.util.Map;
/*     */ import org.apache.logging.log4j.LogManager;
/*     */ import org.apache.logging.log4j.Logger;
/*     */ import org.spongepowered.asm.launch.MixinInitialisationError;
/*     */ import org.spongepowered.asm.mixin.MixinEnvironment;
/*     */ import org.spongepowered.asm.mixin.extensibility.IMixinConfig;
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
/*     */ public class Config
/*     */ {
/*  46 */   private static final Logger logger = LogManager.getLogger("mixin");
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  51 */   private static final Map<String, Config> allConfigs = new HashMap<>();
/*     */ 
/*     */ 
/*     */   
/*     */   private final String name;
/*     */ 
/*     */ 
/*     */   
/*     */   private final MixinConfig config;
/*     */ 
/*     */ 
/*     */   
/*     */   public Config(MixinConfig config) {
/*  64 */     this.name = config.getName();
/*  65 */     this.config = config;
/*     */   }
/*     */   
/*     */   public String getName() {
/*  69 */     return this.name;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   MixinConfig get() {
/*  76 */     return this.config;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isVisited() {
/*  83 */     return this.config.isVisited();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public IMixinConfig getConfig() {
/*  90 */     return this.config;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public MixinEnvironment getEnvironment() {
/*  97 */     return this.config.getEnvironment();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Config getParent() {
/* 104 */     MixinConfig parent = this.config.getParent();
/* 105 */     return (parent != null) ? parent.getHandle() : null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String toString() {
/* 113 */     return this.config.toString();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean equals(Object obj) {
/* 121 */     return (obj instanceof Config && this.name.equals(((Config)obj).name));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 129 */     return this.name.hashCode();
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
/*     */   @Deprecated
/*     */   public static Config create(String configFile, MixinEnvironment outer) {
/* 142 */     Config config = allConfigs.get(configFile);
/* 143 */     if (config != null) {
/* 144 */       return config;
/*     */     }
/*     */     
/*     */     try {
/* 148 */       config = MixinConfig.create(configFile, outer);
/* 149 */       if (config != null) {
/* 150 */         allConfigs.put(config.getName(), config);
/*     */       }
/* 152 */     } catch (Exception ex) {
/* 153 */       throw new MixinInitialisationError("Error initialising mixin config " + configFile, ex);
/*     */     } 
/*     */     
/* 156 */     if (config == null) {
/* 157 */       return null;
/*     */     }
/*     */     
/* 160 */     String parent = config.get().getParentName();
/* 161 */     if (!Strings.isNullOrEmpty(parent)) {
/*     */       Config parentConfig;
/*     */       try {
/* 164 */         parentConfig = create(parent, outer);
/* 165 */         if (parentConfig != null && 
/* 166 */           !config.get().assignParent(parentConfig)) {
/* 167 */           config = null;
/*     */         }
/*     */       }
/* 170 */       catch (Throwable th) {
/* 171 */         throw new MixinInitialisationError("Error initialising parent mixin config " + parent + " of " + configFile, th);
/*     */       } 
/* 173 */       if (parentConfig == null) {
/* 174 */         logger.error("Error encountered initialising mixin config {0}: The parent {1} could not be read.", new Object[] { configFile, parent });
/*     */       }
/*     */     } 
/*     */     
/* 178 */     return config;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static Config create(String configFile) {
/* 188 */     return MixinConfig.create(configFile, MixinEnvironment.getDefaultEnvironment());
/*     */   }
/*     */ }


/* Location:              C:\Users\DuduM\OneDrive\Área de Trabalho\mw10\modularwarfare-shining-2023.2.4.4f-fix10.jar!\org\spongepowered\asm\mixin\transformer\Config.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */