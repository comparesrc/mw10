/*    */ package org.spongepowered.tools.obfuscation;
/*    */ 
/*    */ import com.google.common.collect.ImmutableSet;
/*    */ import java.util.Set;
/*    */ import org.spongepowered.tools.obfuscation.service.ObfuscationServices;
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
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public final class SupportedOptions
/*    */ {
/*    */   public static final String TOKENS = "tokens";
/*    */   public static final String OUT_REFMAP_FILE = "outRefMapFile";
/*    */   public static final String DISABLE_TARGET_VALIDATOR = "disableTargetValidator";
/*    */   public static final String DISABLE_TARGET_EXPORT = "disableTargetExport";
/*    */   public static final String DISABLE_OVERWRITE_CHECKER = "disableOverwriteChecker";
/*    */   public static final String OVERWRITE_ERROR_LEVEL = "overwriteErrorLevel";
/*    */   public static final String DEFAULT_OBFUSCATION_ENV = "defaultObfuscationEnv";
/*    */   public static final String DEPENDENCY_TARGETS_FILE = "dependencyTargetsFile";
/*    */   public static final String MAPPING_TYPES = "mappingTypes";
/*    */   public static final String PLUGIN_VERSION = "pluginVersion";
/*    */   
/*    */   public static Set<String> getAllOptions() {
/* 57 */     ImmutableSet.Builder<String> options = ImmutableSet.builder();
/* 58 */     options.add((Object[])new String[] { "tokens", "outRefMapFile", "disableTargetValidator", "disableTargetExport", "disableOverwriteChecker", "overwriteErrorLevel", "defaultObfuscationEnv", "dependencyTargetsFile", "mappingTypes", "pluginVersion" });
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
/* 70 */     options.addAll(
/* 71 */         ObfuscationServices.getInstance().getSupportedOptions());
/*    */     
/* 73 */     return (Set<String>)options.build();
/*    */   }
/*    */ }


/* Location:              C:\Users\DuduM\OneDrive\Área de Trabalho\mw10\modularwarfare-shining-2023.2.4.4f-fix10.jar!\org\spongepowered\tools\obfuscation\SupportedOptions.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */