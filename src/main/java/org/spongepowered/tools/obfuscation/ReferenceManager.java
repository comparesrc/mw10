/*     */ package org.spongepowered.tools.obfuscation;
/*     */ 
/*     */ import java.io.File;
/*     */ import java.io.IOException;
/*     */ import java.io.PrintWriter;
/*     */ import java.util.List;
/*     */ import javax.tools.Diagnostic;
/*     */ import javax.tools.FileObject;
/*     */ import javax.tools.StandardLocation;
/*     */ import org.spongepowered.asm.mixin.injection.selectors.ITargetSelectorRemappable;
/*     */ import org.spongepowered.asm.mixin.injection.struct.MemberInfo;
/*     */ import org.spongepowered.asm.mixin.refmap.ReferenceMapper;
/*     */ import org.spongepowered.asm.obfuscation.mapping.IMapping;
/*     */ import org.spongepowered.asm.obfuscation.mapping.common.MappingField;
/*     */ import org.spongepowered.asm.obfuscation.mapping.common.MappingMethod;
/*     */ import org.spongepowered.tools.obfuscation.interfaces.IMixinAnnotationProcessor;
/*     */ import org.spongepowered.tools.obfuscation.interfaces.IReferenceManager;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class ReferenceManager
/*     */   implements IReferenceManager
/*     */ {
/*     */   private final IMixinAnnotationProcessor ap;
/*     */   private final String outRefMapFileName;
/*     */   private final List<ObfuscationEnvironment> environments;
/*     */   
/*     */   public static class ReferenceConflictException
/*     */     extends RuntimeException
/*     */   {
/*     */     private static final long serialVersionUID = 1L;
/*     */     private final String oldReference;
/*     */     private final String newReference;
/*     */     
/*     */     public ReferenceConflictException(String oldReference, String newReference) {
/*  59 */       this.oldReference = oldReference;
/*  60 */       this.newReference = newReference;
/*     */     }
/*     */     
/*     */     public String getOld() {
/*  64 */       return this.oldReference;
/*     */     }
/*     */     
/*     */     public String getNew() {
/*  68 */       return this.newReference;
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
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  91 */   private final ReferenceMapper refMapper = new ReferenceMapper();
/*     */   
/*     */   private boolean allowConflicts;
/*     */   
/*     */   public ReferenceManager(IMixinAnnotationProcessor ap, List<ObfuscationEnvironment> environments) {
/*  96 */     this.ap = ap;
/*  97 */     this.environments = environments;
/*  98 */     this.outRefMapFileName = this.ap.getOption("outRefMapFile");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean getAllowConflicts() {
/* 107 */     return this.allowConflicts;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setAllowConflicts(boolean allowConflicts) {
/* 116 */     this.allowConflicts = allowConflicts;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void write() {
/* 124 */     if (this.outRefMapFileName == null) {
/*     */       return;
/*     */     }
/*     */     
/* 128 */     PrintWriter writer = null;
/*     */     
/*     */     try {
/* 131 */       writer = newWriter(this.outRefMapFileName, "refmap");
/* 132 */       this.refMapper.write(writer);
/* 133 */     } catch (IOException ex) {
/* 134 */       ex.printStackTrace();
/*     */     } finally {
/* 136 */       if (writer != null) {
/*     */         try {
/* 138 */           writer.close();
/* 139 */         } catch (Exception exception) {}
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private PrintWriter newWriter(String fileName, String description) throws IOException {
/* 150 */     if (fileName.matches("^.*[\\\\/:].*$")) {
/* 151 */       File outFile = new File(fileName);
/* 152 */       outFile.getParentFile().mkdirs();
/* 153 */       this.ap.printMessage(Diagnostic.Kind.NOTE, "Writing " + description + " to " + outFile.getAbsolutePath());
/* 154 */       return new PrintWriter(outFile);
/*     */     } 
/*     */     
/* 157 */     FileObject outResource = this.ap.getProcessingEnvironment().getFiler().createResource(StandardLocation.CLASS_OUTPUT, "", fileName, new javax.lang.model.element.Element[0]);
/* 158 */     this.ap.printMessage(Diagnostic.Kind.NOTE, "Writing " + description + " to " + (new File(outResource.toUri())).getAbsolutePath());
/* 159 */     return new PrintWriter(outResource.openWriter());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ReferenceMapper getMapper() {
/* 168 */     return this.refMapper;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void addMethodMapping(String className, String reference, ObfuscationData<MappingMethod> obfMethodData) {
/* 178 */     for (ObfuscationEnvironment env : this.environments) {
/* 179 */       MappingMethod obfMethod = obfMethodData.get(env.getType());
/* 180 */       if (obfMethod != null) {
/* 181 */         MemberInfo remappedReference = new MemberInfo((IMapping)obfMethod);
/* 182 */         addMapping(env.getType(), className, reference, remappedReference.toString());
/*     */       } 
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
/*     */   public void addMethodMapping(String className, String reference, ITargetSelectorRemappable context, ObfuscationData<MappingMethod> obfMethodData) {
/* 196 */     for (ObfuscationEnvironment env : this.environments) {
/* 197 */       MappingMethod obfMethod = obfMethodData.get(env.getType());
/* 198 */       if (obfMethod != null) {
/* 199 */         ITargetSelectorRemappable remappedReference = context.remapUsing(obfMethod, true);
/* 200 */         addMapping(env.getType(), className, reference, remappedReference.toString());
/*     */       } 
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
/*     */   public void addFieldMapping(String className, String reference, ITargetSelectorRemappable context, ObfuscationData<MappingField> obfFieldData) {
/* 213 */     for (ObfuscationEnvironment env : this.environments) {
/* 214 */       MappingField obfField = obfFieldData.get(env.getType());
/* 215 */       if (obfField != null) {
/* 216 */         MemberInfo remappedReference = MemberInfo.fromMapping((IMapping)obfField.transform(env.remapDescriptor(context.getDesc())));
/* 217 */         addMapping(env.getType(), className, reference, remappedReference.toString());
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void addClassMapping(String className, String reference, ObfuscationData<String> obfClassData) {
/* 229 */     for (ObfuscationEnvironment env : this.environments) {
/* 230 */       String remapped = obfClassData.get(env.getType());
/* 231 */       if (remapped != null) {
/* 232 */         addMapping(env.getType(), className, reference, remapped);
/*     */       }
/*     */     } 
/*     */   }
/*     */   
/*     */   protected void addMapping(ObfuscationType type, String className, String reference, String newReference) {
/* 238 */     String oldReference = this.refMapper.addMapping(type.getKey(), className, reference, newReference);
/* 239 */     if (type.isDefault()) {
/* 240 */       this.refMapper.addMapping(null, className, reference, newReference);
/*     */     }
/*     */     
/* 243 */     if (!this.allowConflicts && oldReference != null && !oldReference.equals(newReference))
/* 244 */       throw new ReferenceConflictException(oldReference, newReference); 
/*     */   }
/*     */ }


/* Location:              C:\Users\DuduM\OneDrive\Área de Trabalho\mw10\modularwarfare-shining-2023.2.4.4f-fix10.jar!\org\spongepowered\tools\obfuscation\ReferenceManager.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */