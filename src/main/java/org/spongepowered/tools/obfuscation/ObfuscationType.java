/*     */ package org.spongepowered.tools.obfuscation;
/*     */ 
/*     */ import com.google.common.collect.ImmutableList;
/*     */ import java.lang.reflect.Constructor;
/*     */ import java.util.LinkedHashMap;
/*     */ import java.util.List;
/*     */ import java.util.Locale;
/*     */ import java.util.Map;
/*     */ import org.spongepowered.tools.obfuscation.interfaces.IMixinAnnotationProcessor;
/*     */ import org.spongepowered.tools.obfuscation.interfaces.IOptionProvider;
/*     */ import org.spongepowered.tools.obfuscation.service.ObfuscationTypeDescriptor;
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
/*     */ public final class ObfuscationType
/*     */ {
/*     */   public static final String DEFAULT_TYPE = "searge";
/*  50 */   private static final Map<String, ObfuscationType> types = new LinkedHashMap<>();
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private final String key;
/*     */ 
/*     */ 
/*     */   
/*     */   private final ObfuscationTypeDescriptor descriptor;
/*     */ 
/*     */ 
/*     */   
/*     */   private final IMixinAnnotationProcessor ap;
/*     */ 
/*     */ 
/*     */   
/*     */   private final IOptionProvider options;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private ObfuscationType(ObfuscationTypeDescriptor descriptor, IMixinAnnotationProcessor ap) {
/*  73 */     this.key = descriptor.getKey();
/*  74 */     this.descriptor = descriptor;
/*  75 */     this.ap = ap;
/*  76 */     this.options = (IOptionProvider)ap;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final ObfuscationEnvironment createEnvironment() {
/*     */     try {
/*  84 */       Class<? extends ObfuscationEnvironment> cls = this.descriptor.getEnvironmentType();
/*  85 */       Constructor<? extends ObfuscationEnvironment> ctor = cls.getDeclaredConstructor(new Class[] { ObfuscationType.class });
/*  86 */       ctor.setAccessible(true);
/*  87 */       return ctor.newInstance(new Object[] { this });
/*  88 */     } catch (Exception ex) {
/*  89 */       throw new RuntimeException(ex);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public String toString() {
/*  95 */     return this.key;
/*     */   }
/*     */ 
/*     */   
/*     */   public String getKey() {
/* 100 */     return this.key;
/*     */   }
/*     */   
/*     */   public ObfuscationTypeDescriptor getConfig() {
/* 104 */     return this.descriptor;
/*     */   }
/*     */   
/*     */   public IMixinAnnotationProcessor getAnnotationProcessor() {
/* 108 */     return this.ap;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isDefault() {
/* 115 */     String defaultEnv = this.options.getOption("defaultObfuscationEnv", "searge").toLowerCase(Locale.ROOT);
/* 116 */     return this.key.equals(defaultEnv);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isSupported() {
/* 123 */     return (getInputFileNames().size() > 0);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public List<String> getInputFileNames() {
/* 130 */     ImmutableList.Builder<String> builder = ImmutableList.builder();
/*     */     
/* 132 */     String inputFile = this.options.getOption(this.descriptor.getInputFileOption());
/* 133 */     if (inputFile != null) {
/* 134 */       builder.add(inputFile);
/*     */     }
/*     */     
/* 137 */     String extraInputFiles = this.options.getOption(this.descriptor.getExtraInputFilesOption());
/* 138 */     if (extraInputFiles != null) {
/* 139 */       for (String extraInputFile : extraInputFiles.split(";")) {
/* 140 */         builder.add(extraInputFile.trim());
/*     */       }
/*     */     }
/*     */     
/* 144 */     return (List<String>)builder.build();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getOutputFileName() {
/* 151 */     return this.options.getOption(this.descriptor.getOutputFileOption());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static Iterable<ObfuscationType> types() {
/* 158 */     return types.values();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static ObfuscationType create(ObfuscationTypeDescriptor descriptor, IMixinAnnotationProcessor ap) {
/* 169 */     String key = descriptor.getKey();
/* 170 */     if (types.containsKey(key)) {
/* 171 */       throw new IllegalArgumentException("Obfuscation type with key " + key + " was already registered");
/*     */     }
/* 173 */     ObfuscationType type = new ObfuscationType(descriptor, ap);
/* 174 */     types.put(key, type);
/* 175 */     return type;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static ObfuscationType get(String key) {
/* 186 */     ObfuscationType type = types.get(key);
/* 187 */     if (type == null) {
/* 188 */       throw new IllegalArgumentException("Obfuscation type with key " + key + " was not registered");
/*     */     }
/* 190 */     return type;
/*     */   }
/*     */ }


/* Location:              C:\Users\DuduM\OneDrive\Área de Trabalho\mw10\modularwarfare-shining-2023.2.4.4f-fix10.jar!\org\spongepowered\tools\obfuscation\ObfuscationType.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */