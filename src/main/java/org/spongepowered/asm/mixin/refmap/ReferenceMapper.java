/*     */ package org.spongepowered.asm.mixin.refmap;
/*     */ 
/*     */ import com.google.common.collect.Maps;
/*     */ import com.google.common.io.Closeables;
/*     */ import com.google.gson.Gson;
/*     */ import com.google.gson.GsonBuilder;
/*     */ import com.google.gson.JsonParseException;
/*     */ import java.io.InputStream;
/*     */ import java.io.InputStreamReader;
/*     */ import java.io.Reader;
/*     */ import java.io.Serializable;
/*     */ import java.util.HashMap;
/*     */ import java.util.Map;
/*     */ import javax.tools.Diagnostic;
/*     */ import org.spongepowered.asm.service.IMixinService;
/*     */ import org.spongepowered.asm.service.MixinService;
/*     */ import org.spongepowered.asm.util.logging.MessageRouter;
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
/*     */ 
/*     */ 
/*     */ public final class ReferenceMapper
/*     */   implements IReferenceMapper, Serializable
/*     */ {
/*     */   private static final long serialVersionUID = 2L;
/*     */   public static final String DEFAULT_RESOURCE = "mixin.refmap.json";
/*  68 */   public static final ReferenceMapper DEFAULT_MAPPER = new ReferenceMapper(true, "invalid");
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  75 */   private final Map<String, Map<String, String>> mappings = Maps.newHashMap();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  81 */   private final Map<String, Map<String, Map<String, String>>> data = Maps.newHashMap();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private final transient boolean readOnly;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  92 */   private transient String context = null;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private transient String resource;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ReferenceMapper() {
/* 103 */     this(false, "mixin.refmap.json");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private ReferenceMapper(boolean readOnly, String resource) {
/* 112 */     this.readOnly = readOnly;
/* 113 */     this.resource = resource;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isDefault() {
/* 121 */     return this.readOnly;
/*     */   }
/*     */   
/*     */   private void setResourceName(String resource) {
/* 125 */     if (!this.readOnly) {
/* 126 */       this.resource = (resource != null) ? resource : "<unknown resource>";
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getResourceName() {
/* 136 */     return this.resource;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getStatus() {
/* 144 */     return isDefault() ? "No refMap loaded." : ("Using refmap " + getResourceName());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getContext() {
/* 152 */     return this.context;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setContext(String context) {
/* 161 */     this.context = context;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String remap(String className, String reference) {
/* 170 */     return remapWithContext(this.context, className, reference);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String remapWithContext(String context, String className, String reference) {
/* 180 */     Map<String, Map<String, String>> mappings = this.mappings;
/* 181 */     if (context != null) {
/* 182 */       mappings = this.data.get(context);
/* 183 */       if (mappings == null) {
/* 184 */         mappings = this.mappings;
/*     */       }
/*     */     } 
/* 187 */     return remap(mappings, className, reference);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private String remap(Map<String, Map<String, String>> mappings, String className, String reference) {
/* 194 */     if (className == null) {
/* 195 */       for (Map<String, String> mapping : mappings.values()) {
/* 196 */         if (mapping.containsKey(reference)) {
/* 197 */           return mapping.get(reference);
/*     */         }
/*     */       } 
/*     */     }
/*     */     
/* 202 */     Map<String, String> classMappings = mappings.get(className);
/* 203 */     if (classMappings == null) {
/* 204 */       return reference;
/*     */     }
/* 206 */     String remappedReference = classMappings.get(reference);
/* 207 */     return (remappedReference != null) ? remappedReference : reference;
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
/*     */   public String addMapping(String context, String className, String reference, String newReference) {
/* 220 */     if (this.readOnly || reference == null || newReference == null) {
/* 221 */       return null;
/*     */     }
/* 223 */     String conformedReference = reference.replaceAll("\\s", "");
/* 224 */     if (conformedReference.equals(newReference)) {
/* 225 */       return null;
/*     */     }
/* 227 */     Map<String, Map<String, String>> mappings = this.mappings;
/* 228 */     if (context != null) {
/* 229 */       mappings = this.data.get(context);
/* 230 */       if (mappings == null) {
/* 231 */         mappings = Maps.newHashMap();
/* 232 */         this.data.put(context, mappings);
/*     */       } 
/*     */     } 
/* 235 */     Map<String, String> classMappings = mappings.get(className);
/* 236 */     if (classMappings == null) {
/* 237 */       classMappings = new HashMap<>();
/* 238 */       mappings.put(className, classMappings);
/*     */     } 
/* 240 */     return classMappings.put(conformedReference, newReference);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void write(Appendable writer) {
/* 249 */     (new GsonBuilder()).setPrettyPrinting().create().toJson(this, writer);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static ReferenceMapper read(String resourcePath) {
/* 259 */     Reader reader = null;
/*     */     try {
/* 261 */       IMixinService service = MixinService.getService();
/* 262 */       InputStream resource = service.getResourceAsStream(resourcePath);
/* 263 */       if (resource != null) {
/* 264 */         reader = new InputStreamReader(resource);
/* 265 */         ReferenceMapper mapper = readJson(reader);
/* 266 */         mapper.setResourceName(resourcePath);
/* 267 */         return mapper;
/*     */       } 
/* 269 */     } catch (JsonParseException ex) {
/* 270 */       MessageRouter.getMessager().printMessage(Diagnostic.Kind.ERROR, String.format("Invalid REFMAP JSON in %s: %s %s", new Object[] { resourcePath, ex
/* 271 */               .getClass().getName(), ex.getMessage() }));
/* 272 */     } catch (Exception ex) {
/* 273 */       MessageRouter.getMessager().printMessage(Diagnostic.Kind.ERROR, String.format("Failed reading REFMAP JSON from %s: %s %s", new Object[] { resourcePath, ex
/* 274 */               .getClass().getName(), ex.getMessage() }));
/*     */     } finally {
/* 276 */       Closeables.closeQuietly(reader);
/*     */     } 
/*     */     
/* 279 */     return DEFAULT_MAPPER;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static ReferenceMapper read(Reader reader, String name) {
/*     */     try {
/* 291 */       ReferenceMapper mapper = readJson(reader);
/* 292 */       mapper.setResourceName(name);
/* 293 */       return mapper;
/* 294 */     } catch (Exception ex) {
/* 295 */       return DEFAULT_MAPPER;
/*     */     } 
/*     */   }
/*     */   
/*     */   private static ReferenceMapper readJson(Reader reader) {
/* 300 */     return (ReferenceMapper)(new Gson()).fromJson(reader, ReferenceMapper.class);
/*     */   }
/*     */ }


/* Location:              C:\Users\DuduM\OneDrive\Área de Trabalho\mw10\modularwarfare-shining-2023.2.4.4f-fix10.jar!\org\spongepowered\asm\mixin\refmap\ReferenceMapper.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */