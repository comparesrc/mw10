/*     */ package org.spongepowered.asm.service.mojang;
/*     */ 
/*     */ import java.lang.reflect.Field;
/*     */ import java.util.Collections;
/*     */ import java.util.Map;
/*     */ import java.util.Set;
/*     */ import net.minecraft.launchwrapper.LaunchClassLoader;
/*     */ import org.spongepowered.asm.service.IClassTracker;
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
/*     */ 
/*     */ final class LaunchClassLoaderUtil
/*     */   implements IClassTracker
/*     */ {
/*     */   private static final String CACHED_CLASSES_FIELD = "cachedClasses";
/*     */   private static final String INVALID_CLASSES_FIELD = "invalidClasses";
/*     */   private static final String CLASS_LOADER_EXCEPTIONS_FIELD = "classLoaderExceptions";
/*     */   private static final String TRANSFORMER_EXCEPTIONS_FIELD = "transformerExceptions";
/*     */   private final LaunchClassLoader classLoader;
/*     */   private final Map<String, Class<?>> cachedClasses;
/*     */   private final Set<String> invalidClasses;
/*     */   private final Set<String> classLoaderExceptions;
/*     */   private final Set<String> transformerExceptions;
/*     */   
/*     */   LaunchClassLoaderUtil(LaunchClassLoader classLoader) {
/*  66 */     this.classLoader = classLoader;
/*  67 */     this.cachedClasses = getField(classLoader, "cachedClasses");
/*  68 */     this.invalidClasses = getField(classLoader, "invalidClasses");
/*  69 */     this.classLoaderExceptions = getField(classLoader, "classLoaderExceptions");
/*  70 */     this.transformerExceptions = getField(classLoader, "transformerExceptions");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   LaunchClassLoader getClassLoader() {
/*  77 */     return this.classLoader;
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
/*     */   public boolean isClassLoaded(String name) {
/*  89 */     return this.cachedClasses.containsKey(name);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getClassRestrictions(String className) {
/*  98 */     String restrictions = "";
/*  99 */     if (isClassClassLoaderExcluded(className, null)) {
/* 100 */       restrictions = "PACKAGE_CLASSLOADER_EXCLUSION";
/*     */     }
/* 102 */     if (isClassTransformerExcluded(className, null)) {
/* 103 */       restrictions = ((restrictions.length() > 0) ? (restrictions + ",") : "") + "PACKAGE_TRANSFORMER_EXCLUSION";
/*     */     }
/* 105 */     return restrictions;
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
/*     */   boolean isClassExcluded(String name, String transformedName) {
/* 117 */     return (isClassClassLoaderExcluded(name, transformedName) || isClassTransformerExcluded(name, transformedName));
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
/*     */   boolean isClassClassLoaderExcluded(String name, String transformedName) {
/* 130 */     for (String exception : getClassLoaderExceptions()) {
/* 131 */       if ((transformedName != null && transformedName.startsWith(exception)) || name.startsWith(exception)) {
/* 132 */         return true;
/*     */       }
/*     */     } 
/*     */     
/* 136 */     return false;
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
/*     */   boolean isClassTransformerExcluded(String name, String transformedName) {
/* 149 */     for (String exception : getTransformerExceptions()) {
/* 150 */       if ((transformedName != null && transformedName.startsWith(exception)) || name.startsWith(exception)) {
/* 151 */         return true;
/*     */       }
/*     */     } 
/*     */     
/* 155 */     return false;
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
/*     */   public void registerInvalidClass(String name) {
/* 167 */     if (this.invalidClasses != null) {
/* 168 */       this.invalidClasses.add(name);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   Set<String> getClassLoaderExceptions() {
/* 176 */     if (this.classLoaderExceptions != null) {
/* 177 */       return this.classLoaderExceptions;
/*     */     }
/* 179 */     return Collections.emptySet();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   Set<String> getTransformerExceptions() {
/* 186 */     if (this.transformerExceptions != null) {
/* 187 */       return this.transformerExceptions;
/*     */     }
/* 189 */     return Collections.emptySet();
/*     */   }
/*     */ 
/*     */   
/*     */   private static <T> T getField(LaunchClassLoader classLoader, String fieldName) {
/*     */     try {
/* 195 */       Field field = LaunchClassLoader.class.getDeclaredField(fieldName);
/* 196 */       field.setAccessible(true);
/* 197 */       return (T)field.get(classLoader);
/* 198 */     } catch (Exception exception) {
/*     */ 
/*     */       
/* 201 */       return null;
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\DuduM\OneDrive\Área de Trabalho\mw10\modularwarfare-shining-2023.2.4.4f-fix10.jar!\org\spongepowered\asm\service\mojang\LaunchClassLoaderUtil.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */