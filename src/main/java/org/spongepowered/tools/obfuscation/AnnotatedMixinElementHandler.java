/*     */ package org.spongepowered.tools.obfuscation;
/*     */ 
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import javax.annotation.processing.Messager;
/*     */ import javax.lang.model.element.Element;
/*     */ import javax.lang.model.element.ExecutableElement;
/*     */ import javax.lang.model.element.VariableElement;
/*     */ import javax.tools.Diagnostic;
/*     */ import org.spongepowered.asm.mixin.injection.selectors.ITargetSelector;
/*     */ import org.spongepowered.asm.mixin.injection.selectors.ITargetSelectorByName;
/*     */ import org.spongepowered.asm.obfuscation.mapping.IMapping;
/*     */ import org.spongepowered.asm.obfuscation.mapping.common.MappingField;
/*     */ import org.spongepowered.asm.obfuscation.mapping.common.MappingMethod;
/*     */ import org.spongepowered.asm.util.ConstraintParser;
/*     */ import org.spongepowered.asm.util.throwables.ConstraintViolationException;
/*     */ import org.spongepowered.asm.util.throwables.InvalidConstraintException;
/*     */ import org.spongepowered.tools.obfuscation.interfaces.IMessagerSuppressible;
/*     */ import org.spongepowered.tools.obfuscation.interfaces.IMixinAnnotationProcessor;
/*     */ import org.spongepowered.tools.obfuscation.interfaces.IObfuscationManager;
/*     */ import org.spongepowered.tools.obfuscation.mapping.IMappingConsumer;
/*     */ import org.spongepowered.tools.obfuscation.mirror.AnnotationHandle;
/*     */ import org.spongepowered.tools.obfuscation.mirror.FieldHandle;
/*     */ import org.spongepowered.tools.obfuscation.mirror.MethodHandle;
/*     */ import org.spongepowered.tools.obfuscation.mirror.TypeHandle;
/*     */ import org.spongepowered.tools.obfuscation.mirror.TypeUtils;
/*     */ import org.spongepowered.tools.obfuscation.mirror.Visibility;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ abstract class AnnotatedMixinElementHandler
/*     */ {
/*     */   protected final AnnotatedMixin mixin;
/*     */   protected final String classRef;
/*     */   protected final IMixinAnnotationProcessor ap;
/*     */   protected final IObfuscationManager obf;
/*     */   private IMappingConsumer mappings;
/*     */   
/*     */   static abstract class AnnotatedElement<E extends Element>
/*     */   {
/*     */     protected final E element;
/*     */     protected final AnnotationHandle annotation;
/*     */     private final String desc;
/*     */     
/*     */     public AnnotatedElement(E element, AnnotationHandle annotation) {
/*  76 */       this.element = element;
/*  77 */       this.annotation = annotation;
/*  78 */       this.desc = TypeUtils.getDescriptor((Element)element);
/*     */     }
/*     */     
/*     */     public E getElement() {
/*  82 */       return this.element;
/*     */     }
/*     */     
/*     */     public AnnotationHandle getAnnotation() {
/*  86 */       return this.annotation;
/*     */     }
/*     */     
/*     */     public String getSimpleName() {
/*  90 */       return getElement().getSimpleName().toString();
/*     */     }
/*     */     
/*     */     public String getDesc() {
/*  94 */       return this.desc;
/*     */     }
/*     */     
/*     */     public final void printMessage(Messager messager, Diagnostic.Kind kind, CharSequence msg) {
/*  98 */       messager.printMessage(kind, msg, (Element)this.element, this.annotation.asMirror());
/*     */     }
/*     */     
/*     */     public final void printMessage(IMessagerSuppressible messager, Diagnostic.Kind kind, CharSequence msg, SuppressedBy suppressedBy) {
/* 102 */       messager.printMessage(kind, msg, (Element)this.element, this.annotation.asMirror(), suppressedBy);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static class AliasedElementName
/*     */   {
/*     */     protected final String originalName;
/*     */ 
/*     */ 
/*     */     
/*     */     private final List<String> aliases;
/*     */ 
/*     */ 
/*     */     
/*     */     private boolean caseSensitive;
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public AliasedElementName(Element element, AnnotationHandle annotation) {
/* 125 */       this.originalName = element.getSimpleName().toString();
/* 126 */       this.aliases = annotation.getList("aliases");
/*     */     }
/*     */     
/*     */     public AliasedElementName setCaseSensitive(boolean caseSensitive) {
/* 130 */       this.caseSensitive = caseSensitive;
/* 131 */       return this;
/*     */     }
/*     */     
/*     */     public boolean isCaseSensitive() {
/* 135 */       return this.caseSensitive;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public boolean hasAliases() {
/* 142 */       return (this.aliases.size() > 0);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public List<String> getAliases() {
/* 149 */       return this.aliases;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public String elementName() {
/* 156 */       return this.originalName;
/*     */     }
/*     */     
/*     */     public String baseName() {
/* 160 */       return this.originalName;
/*     */     }
/*     */     
/*     */     public boolean hasPrefix() {
/* 164 */       return false;
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static class ShadowElementName
/*     */     extends AliasedElementName
/*     */   {
/*     */     private final boolean hasPrefix;
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     private final String prefix;
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     private final String baseName;
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     private String obfuscated;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     ShadowElementName(Element element, AnnotationHandle shadow) {
/* 197 */       super(element, shadow);
/*     */       
/* 199 */       this.prefix = (String)shadow.getValue("prefix", "shadow$");
/*     */       
/* 201 */       boolean hasPrefix = false;
/* 202 */       String name = this.originalName;
/* 203 */       if (name.startsWith(this.prefix)) {
/* 204 */         hasPrefix = true;
/* 205 */         name = name.substring(this.prefix.length());
/*     */       } 
/*     */       
/* 208 */       this.hasPrefix = hasPrefix;
/* 209 */       this.obfuscated = this.baseName = name;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public String toString() {
/* 217 */       return this.baseName;
/*     */     }
/*     */ 
/*     */     
/*     */     public String baseName() {
/* 222 */       return this.baseName;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public ShadowElementName setObfuscatedName(IMapping<?> name) {
/* 232 */       this.obfuscated = name.getName();
/* 233 */       return this;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public ShadowElementName setObfuscatedName(String name) {
/* 243 */       this.obfuscated = name;
/* 244 */       return this;
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean hasPrefix() {
/* 249 */       return this.hasPrefix;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public String prefix() {
/* 256 */       return this.hasPrefix ? this.prefix : "";
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public String name() {
/* 263 */       return prefix(this.baseName);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public String obfuscated() {
/* 270 */       return prefix(this.obfuscated);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public String prefix(String name) {
/* 280 */       return this.hasPrefix ? (this.prefix + name) : name;
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
/*     */   AnnotatedMixinElementHandler(IMixinAnnotationProcessor ap, AnnotatedMixin mixin) {
/* 302 */     this.ap = ap;
/* 303 */     this.mixin = mixin;
/* 304 */     this.classRef = mixin.getClassRef();
/* 305 */     this.obf = ap.getObfuscationManager();
/*     */   }
/*     */   
/*     */   private IMappingConsumer getMappings() {
/* 309 */     if (this.mappings == null) {
/* 310 */       IMappingConsumer mappingConsumer = this.mixin.getMappings();
/* 311 */       if (mappingConsumer instanceof Mappings) {
/* 312 */         this.mappings = ((Mappings)mappingConsumer).asUnique();
/*     */       } else {
/* 314 */         this.mappings = mappingConsumer;
/*     */       } 
/*     */     } 
/* 317 */     return this.mappings;
/*     */   }
/*     */   
/*     */   protected final void addFieldMappings(String mcpName, String mcpSignature, ObfuscationData<MappingField> obfData) {
/* 321 */     for (ObfuscationType type : obfData) {
/* 322 */       MappingField obfField = obfData.get(type);
/* 323 */       addFieldMapping(type, mcpName, obfField.getSimpleName(), mcpSignature, obfField.getDesc());
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected final void addFieldMapping(ObfuscationType type, ShadowElementName name, String mcpSignature, String obfSignature) {
/* 331 */     addFieldMapping(type, name.name(), name.obfuscated(), mcpSignature, obfSignature);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected final void addFieldMapping(ObfuscationType type, String mcpName, String obfName, String mcpSignature, String obfSignature) {
/* 338 */     MappingField from = new MappingField(this.classRef, mcpName, mcpSignature);
/* 339 */     MappingField to = new MappingField(this.classRef, obfName, obfSignature);
/* 340 */     getMappings().addFieldMapping(type, from, to);
/*     */   }
/*     */   
/*     */   protected final void addMethodMappings(String mcpName, String mcpSignature, ObfuscationData<MappingMethod> obfData) {
/* 344 */     for (ObfuscationType type : obfData) {
/* 345 */       MappingMethod obfMethod = obfData.get(type);
/* 346 */       addMethodMapping(type, mcpName, obfMethod.getSimpleName(), mcpSignature, obfMethod.getDesc());
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected final void addMethodMapping(ObfuscationType type, ShadowElementName name, String mcpSignature, String obfSignature) {
/* 354 */     addMethodMapping(type, name.name(), name.obfuscated(), mcpSignature, obfSignature);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected final void addMethodMapping(ObfuscationType type, String mcpName, String obfName, String mcpSignature, String obfSignature) {
/* 361 */     MappingMethod from = new MappingMethod(this.classRef, mcpName, mcpSignature);
/* 362 */     MappingMethod to = new MappingMethod(this.classRef, obfName, obfSignature);
/* 363 */     getMappings().addMethodMapping(type, from, to);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected final void checkConstraints(ExecutableElement method, AnnotationHandle annotation) {
/*     */     try {
/* 375 */       ConstraintParser.Constraint constraint = ConstraintParser.parse((String)annotation.getValue("constraints"));
/*     */       try {
/* 377 */         constraint.check(this.ap.getTokenProvider());
/* 378 */       } catch (ConstraintViolationException ex) {
/* 379 */         this.ap.printMessage(Diagnostic.Kind.ERROR, ex.getMessage(), method, annotation.asMirror());
/*     */       } 
/* 381 */     } catch (InvalidConstraintException ex) {
/* 382 */       this.ap.printMessage(Diagnostic.Kind.WARNING, ex.getMessage(), method, annotation.asMirror(), SuppressedBy.CONSTRAINTS);
/*     */     } 
/*     */   }
/*     */   
/*     */   protected final void validateTarget(Element element, AnnotationHandle annotation, AliasedElementName name, String type) {
/* 387 */     if (element instanceof ExecutableElement) {
/* 388 */       validateTargetMethod((ExecutableElement)element, annotation, name, type, false, false);
/* 389 */     } else if (element instanceof VariableElement) {
/* 390 */       validateTargetField((VariableElement)element, annotation, name, type);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected final void validateTargetMethod(ExecutableElement method, AnnotationHandle annotation, AliasedElementName name, String type, boolean overwrite, boolean merge) {
/* 400 */     String signature = TypeUtils.getJavaSignature(method);
/*     */     
/* 402 */     for (TypeHandle target : this.mixin.getTargets()) {
/* 403 */       if (target.isImaginary()) {
/*     */         continue;
/*     */       }
/*     */ 
/*     */       
/* 408 */       MethodHandle targetMethod = target.findMethod(method);
/*     */ 
/*     */       
/* 411 */       if (targetMethod == null && name.hasPrefix()) {
/* 412 */         targetMethod = target.findMethod(name.baseName(), signature);
/*     */       }
/*     */ 
/*     */       
/* 416 */       if (targetMethod == null && name.hasAliases()) {
/* 417 */         String alias; Iterator<String> iterator = name.getAliases().iterator(); do { alias = iterator.next(); } while (iterator.hasNext() && (
/* 418 */           targetMethod = target.findMethod(alias, signature)) == null);
/*     */       } 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 424 */       if (targetMethod != null) {
/* 425 */         if (overwrite)
/* 426 */           validateMethodVisibility(method, annotation, type, target, targetMethod);  continue;
/*     */       } 
/* 428 */       if (!merge) {
/* 429 */         printMessage(Diagnostic.Kind.WARNING, "Cannot find target for " + type + " method in " + target, method, annotation, SuppressedBy.TARGET);
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private void validateMethodVisibility(ExecutableElement method, AnnotationHandle annotation, String type, TypeHandle target, MethodHandle targetMethod) {
/* 436 */     Visibility visTarget = targetMethod.getVisibility();
/* 437 */     if (visTarget == null) {
/*     */       return;
/*     */     }
/*     */     
/* 441 */     Visibility visMethod = TypeUtils.getVisibility(method);
/* 442 */     String visibility = "visibility of " + visTarget + " method in " + target;
/* 443 */     if (visTarget.ordinal() > visMethod.ordinal()) {
/* 444 */       printMessage(Diagnostic.Kind.WARNING, visMethod + " " + type + " method cannot reduce " + visibility, method, annotation, SuppressedBy.VISIBILITY);
/*     */     }
/* 446 */     else if (visTarget == Visibility.PRIVATE && visMethod.ordinal() > visTarget.ordinal()) {
/* 447 */       printMessage(Diagnostic.Kind.WARNING, visMethod + " " + type + " method will upgrade " + visibility, method, annotation, SuppressedBy.VISIBILITY);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected final void validateTargetField(VariableElement field, AnnotationHandle annotation, AliasedElementName name, String type) {
/* 457 */     String fieldType = field.asType().toString();
/*     */     
/* 459 */     for (TypeHandle target : this.mixin.getTargets()) {
/* 460 */       String alias; if (target.isImaginary()) {
/*     */         continue;
/*     */       }
/*     */ 
/*     */       
/* 465 */       FieldHandle targetField = target.findField(field);
/* 466 */       if (targetField != null) {
/*     */         continue;
/*     */       }
/*     */ 
/*     */       
/* 471 */       List<String> aliases = name.getAliases();
/* 472 */       Iterator<String> iterator = aliases.iterator(); do { alias = iterator.next(); } while (iterator.hasNext() && (
/* 473 */         targetField = target.findField(alias, fieldType)) == null);
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 478 */       if (targetField == null) {
/* 479 */         this.ap.printMessage(Diagnostic.Kind.WARNING, "Cannot find target for " + type + " field in " + target, field, annotation.asMirror(), SuppressedBy.TARGET);
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected final void validateReferencedTarget(ExecutableElement method, AnnotationHandle inject, ITargetSelector reference, String type) {
/* 490 */     if (!(reference instanceof ITargetSelectorByName)) {
/*     */       return;
/*     */     }
/*     */     
/* 494 */     ITargetSelectorByName nameRef = (ITargetSelectorByName)reference;
/* 495 */     String signature = nameRef.toDescriptor();
/*     */     
/* 497 */     for (TypeHandle target : this.mixin.getTargets()) {
/* 498 */       if (target.isImaginary()) {
/*     */         continue;
/*     */       }
/*     */       
/* 502 */       MethodHandle targetMethod = target.findMethod(nameRef.getName(), signature);
/* 503 */       if (targetMethod == null) {
/* 504 */         this.ap.printMessage(Diagnostic.Kind.WARNING, "Cannot find target method for " + type + " in " + target, method, inject.asMirror(), SuppressedBy.TARGET);
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private void printMessage(Diagnostic.Kind kind, String msg, Element e, AnnotationHandle annotation, SuppressedBy suppressedBy) {
/* 511 */     if (annotation == null) {
/* 512 */       this.ap.printMessage(kind, msg, e, suppressedBy);
/*     */     } else {
/* 514 */       this.ap.printMessage(kind, msg, e, annotation.asMirror(), suppressedBy);
/*     */     } 
/*     */   }
/*     */   
/*     */   protected static <T extends IMapping<T>> ObfuscationData<T> stripOwnerData(ObfuscationData<T> data) {
/* 519 */     ObfuscationData<T> stripped = new ObfuscationData<>();
/* 520 */     for (ObfuscationType type : data) {
/* 521 */       IMapping iMapping = (IMapping)data.get(type);
/* 522 */       stripped.put(type, (T)iMapping.move(null));
/*     */     } 
/* 524 */     return stripped;
/*     */   }
/*     */   
/*     */   protected static <T extends IMapping<T>> ObfuscationData<T> stripDescriptors(ObfuscationData<T> data) {
/* 528 */     ObfuscationData<T> stripped = new ObfuscationData<>();
/* 529 */     for (ObfuscationType type : data) {
/* 530 */       IMapping iMapping = (IMapping)data.get(type);
/* 531 */       stripped.put(type, (T)iMapping.transform(null));
/*     */     } 
/* 533 */     return stripped;
/*     */   }
/*     */ }


/* Location:              C:\Users\DuduM\OneDrive\Área de Trabalho\mw10\modularwarfare-shining-2023.2.4.4f-fix10.jar!\org\spongepowered\tools\obfuscation\AnnotatedMixinElementHandler.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */