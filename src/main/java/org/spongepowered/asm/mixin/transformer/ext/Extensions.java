/*     */ package org.spongepowered.asm.mixin.transformer.ext;
/*     */ 
/*     */ import com.google.common.collect.ImmutableList;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collections;
/*     */ import java.util.HashMap;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import org.objectweb.asm.tree.ClassNode;
/*     */ import org.spongepowered.asm.mixin.MixinEnvironment;
/*     */ import org.spongepowered.asm.service.ISyntheticClassRegistry;
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
/*     */ public final class Extensions
/*     */   implements IExtensionRegistry
/*     */ {
/*  48 */   private final List<IExtension> extensions = new ArrayList<>();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  54 */   private final Map<Class<? extends IExtension>, IExtension> extensionMap = new HashMap<>();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  60 */   private final List<IClassGenerator> generators = new ArrayList<>();
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  65 */   private final List<IClassGenerator> generatorsView = Collections.unmodifiableList(this.generators);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  71 */   private final Map<Class<? extends IClassGenerator>, IClassGenerator> generatorMap = new HashMap<>();
/*     */ 
/*     */ 
/*     */   
/*     */   private final ISyntheticClassRegistry syntheticClassRegistry;
/*     */ 
/*     */ 
/*     */   
/*  79 */   private List<IExtension> activeExtensions = Collections.emptyList();
/*     */   
/*     */   public Extensions(ISyntheticClassRegistry syntheticClassRegistry) {
/*  82 */     this.syntheticClassRegistry = syntheticClassRegistry;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void add(IExtension extension) {
/*  91 */     this.extensions.add(extension);
/*  92 */     this.extensionMap.put(extension.getClass(), extension);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public List<IExtension> getExtensions() {
/* 101 */     return Collections.unmodifiableList(this.extensions);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public List<IExtension> getActiveExtensions() {
/* 110 */     return this.activeExtensions;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public <T extends IExtension> T getExtension(Class<? extends IExtension> extensionClass) {
/* 120 */     return (T)lookup(extensionClass, this.extensionMap, this.extensions);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ISyntheticClassRegistry getSyntheticClassRegistry() {
/* 129 */     return this.syntheticClassRegistry;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void select(MixinEnvironment environment) {
/* 138 */     ImmutableList.Builder<IExtension> activeExtensions = ImmutableList.builder();
/*     */     
/* 140 */     for (IExtension extension : this.extensions) {
/* 141 */       if (extension.checkActive(environment)) {
/* 142 */         activeExtensions.add(extension);
/*     */       }
/*     */     } 
/*     */     
/* 146 */     this.activeExtensions = (List<IExtension>)activeExtensions.build();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void preApply(ITargetClassContext context) {
/* 155 */     for (IExtension extension : this.activeExtensions) {
/* 156 */       extension.preApply(context);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void postApply(ITargetClassContext context) {
/* 166 */     for (IExtension extension : this.activeExtensions) {
/* 167 */       extension.postApply(context);
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
/*     */   public void export(MixinEnvironment env, String name, boolean force, ClassNode classNode) {
/* 181 */     for (IExtension extension : this.activeExtensions) {
/* 182 */       extension.export(env, name, force, classNode);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void add(IClassGenerator generator) {
/* 192 */     this.generators.add(generator);
/* 193 */     this.generatorMap.put(generator.getClass(), generator);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public List<IClassGenerator> getGenerators() {
/* 200 */     return this.generatorsView;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public <T extends IClassGenerator> T getGenerator(Class<? extends IClassGenerator> generatorClass) {
/* 210 */     return (T)lookup(generatorClass, this.generatorMap, this.generators);
/*     */   }
/*     */   
/*     */   private static <T> T lookup(Class<? extends T> extensionClass, Map<Class<? extends T>, T> map, List<T> list) {
/* 214 */     T extension = map.get(extensionClass);
/* 215 */     if (extension == null) {
/* 216 */       for (T classGenerator : list) {
/* 217 */         if (extensionClass.isAssignableFrom(classGenerator.getClass())) {
/* 218 */           extension = classGenerator;
/*     */           
/*     */           break;
/*     */         } 
/*     */       } 
/* 223 */       if (extension == null) {
/* 224 */         throw new IllegalArgumentException("Extension for <" + extensionClass.getName() + "> could not be found");
/*     */       }
/*     */       
/* 227 */       map.put(extensionClass, extension);
/*     */     } 
/*     */     
/* 230 */     return extension;
/*     */   }
/*     */ }


/* Location:              C:\Users\DuduM\OneDrive\Área de Trabalho\mw10\modularwarfare-shining-2023.2.4.4f-fix10.jar!\org\spongepowered\asm\mixin\transformer\ext\Extensions.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */