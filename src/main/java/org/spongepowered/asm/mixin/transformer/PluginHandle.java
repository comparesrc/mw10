/*     */ package org.spongepowered.asm.mixin.transformer;
/*     */ 
/*     */ import com.google.common.base.Strings;
/*     */ import java.lang.reflect.InvocationTargetException;
/*     */ import java.lang.reflect.Method;
/*     */ import java.util.List;
/*     */ import org.apache.logging.log4j.LogManager;
/*     */ import org.apache.logging.log4j.Logger;
/*     */ import org.objectweb.asm.tree.ClassNode;
/*     */ import org.spongepowered.asm.lib.tree.ClassNode;
/*     */ import org.spongepowered.asm.mixin.extensibility.IMixinConfigPlugin;
/*     */ import org.spongepowered.asm.mixin.extensibility.IMixinInfo;
/*     */ import org.spongepowered.asm.mixin.throwables.CompanionPluginError;
/*     */ import org.spongepowered.asm.service.IMixinService;
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
/*     */ class PluginHandle
/*     */ {
/*     */   enum CompatibilityMode
/*     */   {
/*  54 */     NORMAL,
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  60 */     COMPATIBLE,
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  65 */     FAILED;
/*     */   }
/*     */ 
/*     */   
/*  69 */   private static final Logger logger = LogManager.getLogger("mixin");
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private final MixinConfig parent;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private final IMixinConfigPlugin plugin;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  84 */   private CompatibilityMode mode = CompatibilityMode.NORMAL;
/*     */   
/*     */   private Method mdPreApply;
/*     */   
/*     */   private Method mdPostApply;
/*     */ 
/*     */   
/*     */   PluginHandle(MixinConfig parent, IMixinService service, String pluginClassName) {
/*  92 */     IMixinConfigPlugin plugin = null;
/*     */     
/*  94 */     if (!Strings.isNullOrEmpty(pluginClassName)) {
/*     */       try {
/*  96 */         Class<?> pluginClass = service.getClassProvider().findClass(pluginClassName, true);
/*  97 */         plugin = (IMixinConfigPlugin)pluginClass.newInstance();
/*  98 */       } catch (Throwable th) {
/*  99 */         logger.error("Error loading companion plugin class [{}] for mixin config [{}]. The plugin may be out of date: {}:{}", new Object[] { pluginClassName, parent, th
/* 100 */               .getClass().getSimpleName(), th.getMessage(), th });
/* 101 */         plugin = null;
/*     */       } 
/*     */     }
/*     */     
/* 105 */     this.parent = parent;
/* 106 */     this.plugin = plugin;
/*     */   }
/*     */   
/*     */   IMixinConfigPlugin get() {
/* 110 */     return this.plugin;
/*     */   }
/*     */   
/*     */   boolean isAvailable() {
/* 114 */     return (this.plugin != null);
/*     */   }
/*     */   
/*     */   void onLoad(String mixinPackage) {
/* 118 */     if (this.plugin != null) {
/* 119 */       this.plugin.onLoad(mixinPackage);
/*     */     }
/*     */   }
/*     */   
/*     */   String getRefMapperConfig() {
/* 124 */     return (this.plugin != null) ? this.plugin.getRefMapperConfig() : null;
/*     */   }
/*     */   
/*     */   List<String> getMixins() {
/* 128 */     return (this.plugin != null) ? this.plugin.getMixins() : null;
/*     */   }
/*     */   
/*     */   boolean shouldApplyMixin(String targetName, String className) {
/* 132 */     return (this.plugin == null || this.plugin.shouldApplyMixin(targetName, className));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void preApply(String targetClassName, ClassNode targetClass, String mixinClassName, MixinInfo mixinInfo) {
/* 139 */     if (this.plugin == null) {
/*     */       return;
/*     */     }
/*     */     
/* 143 */     if (this.mode == CompatibilityMode.FAILED) {
/* 144 */       throw new IllegalStateException("Companion plugin failure for [" + this.parent + "] plugin [" + this.plugin.getClass() + "]");
/*     */     }
/*     */     
/* 147 */     if (this.mode == CompatibilityMode.COMPATIBLE) {
/*     */       try {
/* 149 */         applyLegacy(this.mdPreApply, targetClassName, targetClass, mixinClassName, mixinInfo);
/* 150 */       } catch (Exception ex) {
/* 151 */         this.mode = CompatibilityMode.FAILED;
/* 152 */         throw ex;
/*     */       } 
/*     */       
/*     */       return;
/*     */     } 
/*     */     try {
/* 158 */       this.plugin.preApply(targetClassName, targetClass, mixinClassName, mixinInfo);
/* 159 */     } catch (AbstractMethodError ex) {
/* 160 */       this.mode = CompatibilityMode.COMPATIBLE;
/* 161 */       initReflection();
/* 162 */       preApply(targetClassName, targetClass, mixinClassName, mixinInfo);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void postApply(String targetClassName, ClassNode targetClass, String mixinClassName, MixinInfo mixinInfo) {
/* 170 */     if (this.plugin == null) {
/*     */       return;
/*     */     }
/*     */     
/* 174 */     if (this.mode == CompatibilityMode.FAILED) {
/* 175 */       throw new IllegalStateException("Companion plugin failure for [" + this.parent + "] plugin [" + this.plugin.getClass() + "]");
/*     */     }
/*     */     
/* 178 */     if (this.mode == CompatibilityMode.COMPATIBLE) {
/*     */       try {
/* 180 */         applyLegacy(this.mdPostApply, targetClassName, targetClass, mixinClassName, mixinInfo);
/* 181 */       } catch (Exception ex) {
/* 182 */         this.mode = CompatibilityMode.FAILED;
/* 183 */         throw ex;
/*     */       } 
/*     */       
/*     */       return;
/*     */     } 
/*     */     try {
/* 189 */       this.plugin.postApply(targetClassName, targetClass, mixinClassName, mixinInfo);
/* 190 */     } catch (AbstractMethodError ex) {
/* 191 */       this.mode = CompatibilityMode.COMPATIBLE;
/* 192 */       initReflection();
/* 193 */       postApply(targetClassName, targetClass, mixinClassName, mixinInfo);
/*     */     } 
/*     */   }
/*     */   
/*     */   private void initReflection() {
/* 198 */     if (this.mdPreApply != null) {
/*     */       return;
/*     */     }
/*     */     
/*     */     try {
/* 203 */       Class<?> pluginClass = this.plugin.getClass();
/* 204 */       this.mdPreApply = pluginClass.getMethod("preApply", new Class[] { String.class, ClassNode.class, String.class, IMixinInfo.class });
/*     */       
/* 206 */       this.mdPostApply = pluginClass.getMethod("postApply", new Class[] { String.class, ClassNode.class, String.class, IMixinInfo.class });
/*     */     }
/* 208 */     catch (Throwable th) {
/* 209 */       logger.catching(th);
/*     */     } 
/*     */   }
/*     */   
/*     */   private void applyLegacy(Method method, String targetClassName, ClassNode targetClass, String mixinClassName, IMixinInfo mixinInfo) {
/*     */     try {
/* 215 */       method.invoke(this.plugin, new Object[] { targetClassName, new ClassNode(targetClass), mixinClassName, mixinInfo });
/* 216 */     } catch (LinkageError err) {
/* 217 */       throw new CompanionPluginError(apiError("Accessing [" + err.getMessage() + "]"), err);
/* 218 */     } catch (IllegalAccessException ex) {
/* 219 */       throw new CompanionPluginError(apiError("Fallback failed [" + ex.getMessage() + "]"), ex);
/* 220 */     } catch (IllegalArgumentException ex) {
/* 221 */       throw new CompanionPluginError(apiError("Fallback failed [" + ex.getMessage() + "]"), ex);
/* 222 */     } catch (InvocationTargetException ex) {
/* 223 */       Throwable th = (ex.getCause() != null) ? ex.getCause() : ex;
/* 224 */       throw new CompanionPluginError(apiError("Fallback failed [" + th.getMessage() + "]"), th);
/*     */     } 
/*     */   }
/*     */   
/*     */   private String apiError(String message) {
/* 229 */     return String.format("Companion plugin attempted to use a deprected API in [%s] plugin [%s]: %s", new Object[] { this.parent, this.plugin
/* 230 */           .getClass().getName(), message });
/*     */   }
/*     */ }


/* Location:              C:\Users\DuduM\OneDrive\Área de Trabalho\mw10\modularwarfare-shining-2023.2.4.4f-fix10.jar!\org\spongepowered\asm\mixin\transformer\PluginHandle.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */