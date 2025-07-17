/*     */ package org.spongepowered.tools.obfuscation;
/*     */ 
/*     */ import java.io.File;
/*     */ import java.util.Collection;
/*     */ import java.util.List;
/*     */ import javax.annotation.processing.Filer;
/*     */ import javax.annotation.processing.Messager;
/*     */ import javax.lang.model.element.TypeElement;
/*     */ import javax.lang.model.type.DeclaredType;
/*     */ import javax.lang.model.type.TypeKind;
/*     */ import javax.lang.model.type.TypeMirror;
/*     */ import javax.tools.Diagnostic;
/*     */ import org.spongepowered.asm.mixin.injection.selectors.ITargetSelectorRemappable;
/*     */ import org.spongepowered.asm.obfuscation.mapping.common.MappingField;
/*     */ import org.spongepowered.asm.obfuscation.mapping.common.MappingMethod;
/*     */ import org.spongepowered.asm.util.ObfuscationUtil;
/*     */ import org.spongepowered.tools.obfuscation.interfaces.IMixinAnnotationProcessor;
/*     */ import org.spongepowered.tools.obfuscation.interfaces.IObfuscationEnvironment;
/*     */ import org.spongepowered.tools.obfuscation.mapping.IMappingConsumer;
/*     */ import org.spongepowered.tools.obfuscation.mapping.IMappingProvider;
/*     */ import org.spongepowered.tools.obfuscation.mapping.IMappingWriter;
/*     */ import org.spongepowered.tools.obfuscation.mirror.TypeHandle;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public abstract class ObfuscationEnvironment
/*     */   implements IObfuscationEnvironment
/*     */ {
/*     */   protected final ObfuscationType type;
/*     */   protected final IMappingProvider mappingProvider;
/*     */   protected final IMappingWriter mappingWriter;
/*     */   
/*     */   final class RemapperProxy
/*     */     implements ObfuscationUtil.IClassRemapper
/*     */   {
/*     */     public String map(String typeName) {
/*  70 */       if (ObfuscationEnvironment.this.mappingProvider == null) {
/*  71 */         return null;
/*     */       }
/*  73 */       return ObfuscationEnvironment.this.mappingProvider.getClassMapping(typeName);
/*     */     }
/*     */ 
/*     */     
/*     */     public String unmap(String typeName) {
/*  78 */       if (ObfuscationEnvironment.this.mappingProvider == null) {
/*  79 */         return null;
/*     */       }
/*  81 */       return ObfuscationEnvironment.this.mappingProvider.getClassMapping(typeName);
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
/*  98 */   protected final RemapperProxy remapper = new RemapperProxy();
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected final IMixinAnnotationProcessor ap;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected final String outFileName;
/*     */ 
/*     */ 
/*     */   
/*     */   protected final List<String> inFileNames;
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean initDone;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected ObfuscationEnvironment(ObfuscationType type) {
/* 122 */     this.type = type;
/* 123 */     this.ap = type.getAnnotationProcessor();
/*     */     
/* 125 */     this.inFileNames = type.getInputFileNames();
/* 126 */     this.outFileName = type.getOutputFileName();
/*     */     
/* 128 */     this.mappingProvider = getMappingProvider((Messager)this.ap, this.ap.getProcessingEnvironment().getFiler());
/* 129 */     this.mappingWriter = getMappingWriter((Messager)this.ap, this.ap.getProcessingEnvironment().getFiler());
/*     */   }
/*     */ 
/*     */   
/*     */   public String toString() {
/* 134 */     return this.type.toString();
/*     */   }
/*     */   
/*     */   protected abstract IMappingProvider getMappingProvider(Messager paramMessager, Filer paramFiler);
/*     */   
/*     */   protected abstract IMappingWriter getMappingWriter(Messager paramMessager, Filer paramFiler);
/*     */   
/*     */   private boolean initMappings() {
/* 142 */     if (!this.initDone) {
/* 143 */       this.initDone = true;
/*     */       
/* 145 */       if (this.inFileNames == null) {
/* 146 */         this.ap.printMessage(Diagnostic.Kind.ERROR, "The " + this.type.getConfig().getInputFileOption() + " argument was not supplied, obfuscation processing will not occur");
/*     */         
/* 148 */         return false;
/*     */       } 
/*     */       
/* 151 */       int successCount = 0;
/*     */       
/* 153 */       for (String inputFileName : this.inFileNames) {
/* 154 */         File inputFile = new File(inputFileName);
/*     */         try {
/* 156 */           if (inputFile.isFile()) {
/* 157 */             this.ap.printMessage(Diagnostic.Kind.NOTE, "Loading " + this.type + " mappings from " + inputFile.getAbsolutePath());
/* 158 */             this.mappingProvider.read(inputFile);
/* 159 */             successCount++;
/*     */           } 
/* 161 */         } catch (Exception ex) {
/* 162 */           ex.printStackTrace();
/*     */         } 
/*     */       } 
/*     */       
/* 166 */       if (successCount < 1) {
/* 167 */         this.ap.printMessage(Diagnostic.Kind.ERROR, "No valid input files for " + this.type + " could be read, processing may not be sucessful.");
/* 168 */         this.mappingProvider.clear();
/*     */       } 
/*     */     } 
/*     */     
/* 172 */     return !this.mappingProvider.isEmpty();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ObfuscationType getType() {
/* 179 */     return this.type;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public MappingMethod getObfMethod(ITargetSelectorRemappable method) {
/* 187 */     MappingMethod obfd = getObfMethod(method.asMethodMapping());
/* 188 */     if (obfd != null || !method.isFullyQualified()) {
/* 189 */       return obfd;
/*     */     }
/*     */ 
/*     */     
/* 193 */     TypeHandle type = this.ap.getTypeProvider().getTypeHandle(method.getOwner());
/* 194 */     if (type == null || type.isImaginary()) {
/* 195 */       return null;
/*     */     }
/*     */ 
/*     */     
/* 199 */     TypeMirror superClass = type.getElement().getSuperclass();
/* 200 */     if (superClass.getKind() != TypeKind.DECLARED) {
/* 201 */       return null;
/*     */     }
/*     */ 
/*     */     
/* 205 */     String superClassName = ((TypeElement)((DeclaredType)superClass).asElement()).getQualifiedName().toString();
/* 206 */     return getObfMethod(method.move(superClassName.replace('.', '/')));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public MappingMethod getObfMethod(MappingMethod method) {
/* 214 */     return getObfMethod(method, true);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public MappingMethod getObfMethod(MappingMethod method, boolean lazyRemap) {
/* 222 */     if (initMappings()) {
/* 223 */       boolean remapped = true;
/* 224 */       boolean superRef = false;
/* 225 */       MappingMethod mapping = null;
/* 226 */       for (MappingMethod md = method; md != null && mapping == null; md = md.getSuper(), superRef = true) {
/* 227 */         mapping = this.mappingProvider.getMethodMapping(md);
/*     */       }
/*     */ 
/*     */       
/* 231 */       if (mapping == null) {
/* 232 */         if (lazyRemap) {
/* 233 */           return null;
/*     */         }
/* 235 */         mapping = method.copy();
/* 236 */         remapped = false;
/* 237 */       } else if (superRef) {
/*     */ 
/*     */         
/* 240 */         String obfOwner = getObfClass(method.getOwner());
/* 241 */         mapping = mapping.move((obfOwner != null) ? obfOwner : method.getOwner());
/*     */       } 
/* 243 */       String remappedOwner = getObfClass(mapping.getOwner());
/* 244 */       if (remappedOwner == null || remappedOwner.equals(method.getOwner()) || remappedOwner.equals(mapping.getOwner())) {
/* 245 */         return remapped ? mapping : null;
/*     */       }
/* 247 */       if (remapped) {
/* 248 */         return mapping.move(remappedOwner);
/*     */       }
/* 250 */       String desc = ObfuscationUtil.mapDescriptor(mapping.getDesc(), this.remapper);
/* 251 */       return new MappingMethod(remappedOwner, mapping.getSimpleName(), desc);
/*     */     } 
/* 253 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ITargetSelectorRemappable remapDescriptor(ITargetSelectorRemappable method) {
/* 264 */     boolean transformed = false;
/*     */     
/* 266 */     String owner = method.getOwner();
/* 267 */     if (owner != null) {
/* 268 */       String newOwner = this.remapper.map(owner);
/* 269 */       if (newOwner != null) {
/* 270 */         owner = newOwner;
/* 271 */         transformed = true;
/*     */       } 
/*     */     } 
/*     */     
/* 275 */     String desc = method.getDesc();
/* 276 */     if (desc != null) {
/* 277 */       String newDesc = ObfuscationUtil.mapDescriptor(method.getDesc(), this.remapper);
/* 278 */       if (!newDesc.equals(method.getDesc())) {
/* 279 */         desc = newDesc;
/* 280 */         transformed = true;
/*     */       } 
/*     */     } 
/*     */     
/* 284 */     if (transformed) {  } else {  }  return null;
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
/*     */   public String remapDescriptor(String desc) {
/* 296 */     return ObfuscationUtil.mapDescriptor(desc, this.remapper);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public MappingField getObfField(ITargetSelectorRemappable field) {
/* 304 */     return getObfField(field.asFieldMapping(), true);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public MappingField getObfField(MappingField field) {
/* 312 */     return getObfField(field, true);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public MappingField getObfField(MappingField field, boolean lazyRemap) {
/* 320 */     if (!initMappings()) {
/* 321 */       return null;
/*     */     }
/*     */     
/* 324 */     MappingField mapping = this.mappingProvider.getFieldMapping(field);
/*     */     
/* 326 */     if (mapping == null) {
/* 327 */       if (lazyRemap) {
/* 328 */         return null;
/*     */       }
/* 330 */       mapping = field;
/*     */     } 
/* 332 */     String remappedOwner = getObfClass(mapping.getOwner());
/* 333 */     if (remappedOwner == null || remappedOwner.equals(field.getOwner()) || remappedOwner.equals(mapping.getOwner())) {
/* 334 */       return (mapping != field) ? mapping : null;
/*     */     }
/* 336 */     return mapping.move(remappedOwner);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getObfClass(String className) {
/* 344 */     if (!initMappings()) {
/* 345 */       return null;
/*     */     }
/* 347 */     return this.mappingProvider.getClassMapping(className);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void writeMappings(Collection<IMappingConsumer> consumers) {
/* 355 */     IMappingConsumer.MappingSet<MappingField> fields = new IMappingConsumer.MappingSet();
/* 356 */     IMappingConsumer.MappingSet<MappingMethod> methods = new IMappingConsumer.MappingSet();
/*     */     
/* 358 */     for (IMappingConsumer mappings : consumers) {
/* 359 */       fields.addAll((Collection)mappings.getFieldMappings(this.type));
/* 360 */       methods.addAll((Collection)mappings.getMethodMappings(this.type));
/*     */     } 
/*     */     
/* 363 */     this.mappingWriter.write(this.outFileName, this.type, fields, methods);
/*     */   }
/*     */ }


/* Location:              C:\Users\DuduM\OneDrive\Área de Trabalho\mw10\modularwarfare-shining-2023.2.4.4f-fix10.jar!\org\spongepowered\tools\obfuscation\ObfuscationEnvironment.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */