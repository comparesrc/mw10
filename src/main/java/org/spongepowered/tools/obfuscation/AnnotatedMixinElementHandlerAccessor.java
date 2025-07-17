/*     */ package org.spongepowered.tools.obfuscation;
/*     */ 
/*     */ import com.google.common.base.Strings;
/*     */ import javax.annotation.processing.Messager;
/*     */ import javax.lang.model.element.ExecutableElement;
/*     */ import javax.lang.model.element.Modifier;
/*     */ import javax.lang.model.element.VariableElement;
/*     */ import javax.lang.model.type.TypeKind;
/*     */ import javax.lang.model.type.TypeMirror;
/*     */ import javax.tools.Diagnostic;
/*     */ import org.objectweb.asm.tree.MethodNode;
/*     */ import org.spongepowered.asm.mixin.MixinEnvironment;
/*     */ import org.spongepowered.asm.mixin.extensibility.IMixinInfo;
/*     */ import org.spongepowered.asm.mixin.gen.AccessorInfo;
/*     */ import org.spongepowered.asm.mixin.injection.selectors.ITargetSelectorRemappable;
/*     */ import org.spongepowered.asm.mixin.injection.struct.MemberInfo;
/*     */ import org.spongepowered.asm.mixin.injection.struct.Target;
/*     */ import org.spongepowered.asm.mixin.refmap.IMixinContext;
/*     */ import org.spongepowered.asm.mixin.refmap.IReferenceMapper;
/*     */ import org.spongepowered.asm.mixin.refmap.ReferenceMapper;
/*     */ import org.spongepowered.asm.mixin.transformer.ext.Extensions;
/*     */ import org.spongepowered.asm.obfuscation.mapping.common.MappingField;
/*     */ import org.spongepowered.asm.obfuscation.mapping.common.MappingMethod;
/*     */ import org.spongepowered.tools.obfuscation.interfaces.IMessagerSuppressible;
/*     */ import org.spongepowered.tools.obfuscation.interfaces.IMixinAnnotationProcessor;
/*     */ import org.spongepowered.tools.obfuscation.mirror.AnnotationHandle;
/*     */ import org.spongepowered.tools.obfuscation.mirror.FieldHandle;
/*     */ import org.spongepowered.tools.obfuscation.mirror.MethodHandle;
/*     */ import org.spongepowered.tools.obfuscation.mirror.TypeHandle;
/*     */ import org.spongepowered.tools.obfuscation.mirror.TypeUtils;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class AnnotatedMixinElementHandlerAccessor
/*     */   extends AnnotatedMixinElementHandler
/*     */   implements IMixinContext
/*     */ {
/*     */   static class AnnotatedElementAccessor
/*     */     extends AnnotatedMixinElementHandler.AnnotatedElement<ExecutableElement>
/*     */   {
/*     */     protected final boolean shouldRemap;
/*     */     protected final TypeMirror returnType;
/*     */     protected String targetName;
/*     */     
/*     */     public AnnotatedElementAccessor(ExecutableElement element, AnnotationHandle annotation, boolean shouldRemap) {
/*  77 */       super(element, annotation);
/*  78 */       this.shouldRemap = shouldRemap;
/*  79 */       this.returnType = getElement().getReturnType();
/*     */     }
/*     */ 
/*     */     
/*     */     public void attach(TypeHandle target) {}
/*     */     
/*     */     public boolean shouldRemap() {
/*  86 */       return this.shouldRemap;
/*     */     }
/*     */     
/*     */     public String getAnnotationValue() {
/*  90 */       return (String)getAnnotation().getValue();
/*     */     }
/*     */     
/*     */     public TypeMirror getTargetType() {
/*  94 */       switch (getAccessorType()) {
/*     */         case FIELD_GETTER:
/*  96 */           return this.returnType;
/*     */         case FIELD_SETTER:
/*  98 */           return ((VariableElement)getElement().getParameters().get(0)).asType();
/*     */       } 
/* 100 */       return null;
/*     */     }
/*     */ 
/*     */     
/*     */     public String getTargetTypeName() {
/* 105 */       return TypeUtils.getTypeName(getTargetType());
/*     */     }
/*     */     
/*     */     public String getAccessorDesc() {
/* 109 */       return TypeUtils.getInternalName(getTargetType());
/*     */     }
/*     */     
/*     */     public ITargetSelectorRemappable getContext() {
/* 113 */       return (ITargetSelectorRemappable)new MemberInfo(getTargetName(), null, getAccessorDesc());
/*     */     }
/*     */     
/*     */     public AccessorInfo.AccessorType getAccessorType() {
/* 117 */       return (this.returnType.getKind() == TypeKind.VOID) ? AccessorInfo.AccessorType.FIELD_SETTER : AccessorInfo.AccessorType.FIELD_GETTER;
/*     */     }
/*     */     
/*     */     public void setTargetName(String targetName) {
/* 121 */       this.targetName = targetName;
/*     */     }
/*     */     
/*     */     public String getTargetName() {
/* 125 */       return this.targetName;
/*     */     }
/*     */     
/*     */     public TypeMirror getReturnType() {
/* 129 */       return this.returnType;
/*     */     }
/*     */     
/*     */     public boolean isStatic() {
/* 133 */       return this.element.getModifiers().contains(Modifier.STATIC);
/*     */     }
/*     */ 
/*     */     
/*     */     public String toString() {
/* 138 */       return (this.targetName != null) ? this.targetName : "<invalid>";
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static class AnnotatedElementInvoker
/*     */     extends AnnotatedElementAccessor
/*     */   {
/* 148 */     private AccessorInfo.AccessorType type = AccessorInfo.AccessorType.METHOD_PROXY;
/*     */     
/*     */     public AnnotatedElementInvoker(ExecutableElement element, AnnotationHandle annotation, boolean shouldRemap) {
/* 151 */       super(element, annotation, shouldRemap);
/*     */     }
/*     */ 
/*     */     
/*     */     public void attach(TypeHandle target) {
/* 156 */       this.type = AccessorInfo.AccessorType.METHOD_PROXY;
/* 157 */       if (this.returnType.getKind() != TypeKind.DECLARED) {
/*     */         return;
/*     */       }
/*     */       
/* 161 */       String specifiedName = getAnnotationValue();
/* 162 */       if (specifiedName != null) {
/* 163 */         if ("<init>".equals(specifiedName) || target.getName().equals(specifiedName.replace('.', '/'))) {
/* 164 */           this.type = AccessorInfo.AccessorType.OBJECT_FACTORY;
/*     */         }
/*     */         
/*     */         return;
/*     */       } 
/* 169 */       AccessorInfo.AccessorName accessorName = AccessorInfo.AccessorName.of(getSimpleName(), false);
/* 170 */       if (accessorName == null) {
/*     */         return;
/*     */       }
/*     */       
/* 174 */       for (String prefix : AccessorInfo.AccessorType.OBJECT_FACTORY.getExpectedPrefixes()) {
/* 175 */         if (prefix.equals(accessorName.prefix) && ("<init>"
/* 176 */           .equals(accessorName.name) || target.getSimpleName().equals(accessorName.name))) {
/* 177 */           this.type = AccessorInfo.AccessorType.OBJECT_FACTORY;
/*     */           return;
/*     */         } 
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean shouldRemap() {
/* 185 */       return ((this.type == AccessorInfo.AccessorType.METHOD_PROXY || getAnnotationValue() != null) && super.shouldRemap());
/*     */     }
/*     */ 
/*     */     
/*     */     public String getAccessorDesc() {
/* 190 */       return TypeUtils.getDescriptor(getElement());
/*     */     }
/*     */ 
/*     */     
/*     */     public AccessorInfo.AccessorType getAccessorType() {
/* 195 */       return this.type;
/*     */     }
/*     */ 
/*     */     
/*     */     public String getTargetTypeName() {
/* 200 */       return TypeUtils.getJavaSignature(getElement());
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public AnnotatedMixinElementHandlerAccessor(IMixinAnnotationProcessor ap, AnnotatedMixin mixin) {
/* 206 */     super(ap, mixin);
/*     */   }
/*     */ 
/*     */   
/*     */   public ReferenceMapper getReferenceMapper() {
/* 211 */     return null;
/*     */   }
/*     */ 
/*     */   
/*     */   public String getClassName() {
/* 216 */     return this.mixin.getClassRef().replace('/', '.');
/*     */   }
/*     */ 
/*     */   
/*     */   public String getClassRef() {
/* 221 */     return this.mixin.getClassRef();
/*     */   }
/*     */ 
/*     */   
/*     */   public String getTargetClassRef() {
/* 226 */     throw new UnsupportedOperationException("Target class not available at compile time");
/*     */   }
/*     */ 
/*     */   
/*     */   public IMixinInfo getMixin() {
/* 231 */     throw new UnsupportedOperationException("MixinInfo not available at compile time");
/*     */   }
/*     */ 
/*     */   
/*     */   public Extensions getExtensions() {
/* 236 */     throw new UnsupportedOperationException("Mixin Extensions not available at compile time");
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean getOption(MixinEnvironment.Option option) {
/* 241 */     throw new UnsupportedOperationException("Options not available at compile time");
/*     */   }
/*     */ 
/*     */   
/*     */   public int getPriority() {
/* 246 */     throw new UnsupportedOperationException("Priority not available at compile time");
/*     */   }
/*     */ 
/*     */   
/*     */   public Target getTargetMethod(MethodNode into) {
/* 251 */     throw new UnsupportedOperationException("Target not available at compile time");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void registerAccessor(AnnotatedElementAccessor elem) {
/* 260 */     if (elem.getAccessorType() == null) {
/* 261 */       elem.printMessage((Messager)this.ap, Diagnostic.Kind.WARNING, "Unsupported accessor type");
/*     */       
/*     */       return;
/*     */     } 
/* 265 */     String targetName = getAccessorTargetName(elem);
/* 266 */     if (targetName == null) {
/* 267 */       elem.printMessage((Messager)this.ap, Diagnostic.Kind.WARNING, "Cannot inflect accessor target name");
/*     */       return;
/*     */     } 
/* 270 */     elem.setTargetName(targetName);
/*     */     
/* 272 */     for (TypeHandle target : this.mixin.getTargets()) {
/*     */       try {
/* 274 */         elem.attach(target);
/* 275 */       } catch (Exception ex) {
/* 276 */         elem.printMessage((Messager)this.ap, Diagnostic.Kind.ERROR, ex.getMessage());
/*     */         continue;
/*     */       } 
/* 279 */       if (elem.getAccessorType() == AccessorInfo.AccessorType.OBJECT_FACTORY) {
/* 280 */         registerFactoryForTarget((AnnotatedElementInvoker)elem, target); continue;
/* 281 */       }  if (elem.getAccessorType() == AccessorInfo.AccessorType.METHOD_PROXY) {
/* 282 */         registerInvokerForTarget((AnnotatedElementInvoker)elem, target); continue;
/*     */       } 
/* 284 */       registerAccessorForTarget(elem, target);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private void registerAccessorForTarget(AnnotatedElementAccessor elem, TypeHandle target) {
/* 290 */     FieldHandle targetField = target.findField(elem.getTargetName(), elem.getTargetTypeName(), false);
/* 291 */     if (targetField == null) {
/* 292 */       if (!target.isImaginary()) {
/* 293 */         elem.printMessage((Messager)this.ap, Diagnostic.Kind.ERROR, "Could not locate @Accessor target " + elem + " in target " + target);
/*     */         
/*     */         return;
/*     */       } 
/* 297 */       targetField = new FieldHandle(target.getName(), elem.getTargetName(), elem.getDesc());
/*     */     } 
/*     */     
/* 300 */     if (!elem.shouldRemap()) {
/*     */       return;
/*     */     }
/*     */     
/* 304 */     ObfuscationData<MappingField> obfData = this.obf.getDataProvider().getObfField(targetField.asMapping(false).move(target.getName()));
/* 305 */     if (obfData.isEmpty()) {
/* 306 */       String info = this.mixin.isMultiTarget() ? (" in target " + target) : "";
/* 307 */       elem.printMessage((Messager)this.ap, Diagnostic.Kind.WARNING, "Unable to locate obfuscation mapping" + info + " for @Accessor target " + elem);
/*     */       
/*     */       return;
/*     */     } 
/* 311 */     obfData = AnnotatedMixinElementHandler.stripOwnerData(obfData);
/*     */     
/*     */     try {
/* 314 */       this.obf.getReferenceManager().addFieldMapping(this.mixin.getClassRef(), elem.getTargetName(), elem.getContext(), obfData);
/* 315 */     } catch (ReferenceConflictException ex) {
/* 316 */       elem.printMessage((Messager)this.ap, Diagnostic.Kind.ERROR, "Mapping conflict for @Accessor target " + elem + ": " + ex.getNew() + " for target " + target + " conflicts with existing mapping " + ex
/* 317 */           .getOld());
/*     */     } 
/*     */   }
/*     */   
/*     */   private void registerInvokerForTarget(AnnotatedElementInvoker elem, TypeHandle target) {
/* 322 */     MethodHandle targetMethod = target.findMethod(elem.getTargetName(), elem.getTargetTypeName(), false);
/* 323 */     if (targetMethod == null) {
/* 324 */       if (!target.isImaginary()) {
/* 325 */         elem.printMessage((Messager)this.ap, Diagnostic.Kind.ERROR, "Could not locate @Invoker target " + elem + " in target " + target);
/*     */         
/*     */         return;
/*     */       } 
/* 329 */       targetMethod = new MethodHandle(target, elem.getTargetName(), elem.getDesc());
/*     */     } 
/*     */     
/* 332 */     if (!elem.shouldRemap()) {
/*     */       return;
/*     */     }
/*     */     
/* 336 */     ObfuscationData<MappingMethod> obfData = this.obf.getDataProvider().getObfMethod(targetMethod.asMapping(false).move(target.getName()));
/* 337 */     if (obfData.isEmpty()) {
/* 338 */       String info = this.mixin.isMultiTarget() ? (" in target " + target) : "";
/* 339 */       elem.printMessage((Messager)this.ap, Diagnostic.Kind.WARNING, "Unable to locate obfuscation mapping" + info + " for @Accessor target " + elem);
/*     */       
/*     */       return;
/*     */     } 
/* 343 */     obfData = AnnotatedMixinElementHandler.stripOwnerData(obfData);
/*     */     
/*     */     try {
/* 346 */       this.obf.getReferenceManager().addMethodMapping(this.mixin.getClassRef(), elem.getTargetName(), elem.getContext(), obfData);
/* 347 */     } catch (ReferenceConflictException ex) {
/* 348 */       elem.printMessage((Messager)this.ap, Diagnostic.Kind.ERROR, "Mapping conflict for @Invoker target " + elem + ": " + ex.getNew() + " for target " + target + " conflicts with existing mapping " + ex
/* 349 */           .getOld());
/*     */     } 
/*     */   }
/*     */   
/*     */   private void registerFactoryForTarget(AnnotatedElementInvoker elem, TypeHandle target) {
/* 354 */     TypeUtils.EquivalencyResult equivalency = TypeUtils.isEquivalentType(this.ap.getProcessingEnvironment(), elem.getReturnType(), target.getType());
/* 355 */     if (equivalency.type != TypeUtils.Equivalency.EQUIVALENT) {
/* 356 */       if (equivalency.type == TypeUtils.Equivalency.EQUIVALENT_BUT_RAW && equivalency.rawType == 1)
/* 357 */       { elem.printMessage((IMessagerSuppressible)this.ap, Diagnostic.Kind.WARNING, "Raw return type for Factory @Invoker", SuppressedBy.RAW_TYPES); }
/* 358 */       else { if (equivalency.type == TypeUtils.Equivalency.BOUNDS_MISMATCH) {
/* 359 */           elem.printMessage((Messager)this.ap, Diagnostic.Kind.ERROR, "Invalid Factory @Invoker return type, generic type arguments of " + target.getType() + " are incompatible with " + elem
/* 360 */               .getReturnType() + ". " + equivalency);
/*     */           return;
/*     */         } 
/* 363 */         elem.printMessage((Messager)this.ap, Diagnostic.Kind.ERROR, "Invalid Factory @Invoker return type, expected " + target.getType() + " but found " + elem
/* 364 */             .getReturnType());
/*     */         return; }
/*     */     
/*     */     }
/* 368 */     if (!elem.isStatic()) {
/* 369 */       elem.printMessage((Messager)this.ap, Diagnostic.Kind.ERROR, "Factory @Invoker must be static");
/*     */       
/*     */       return;
/*     */     } 
/* 373 */     if (!elem.shouldRemap()) {
/*     */       return;
/*     */     }
/*     */     
/* 377 */     ObfuscationData<String> obfData = this.obf.getDataProvider().getObfClass(elem.getAnnotationValue().replace('.', '/'));
/* 378 */     this.obf.getReferenceManager().addClassMapping(this.mixin.getClassRef(), elem.getAnnotationValue(), obfData);
/*     */   }
/*     */   
/*     */   private String getAccessorTargetName(AnnotatedElementAccessor elem) {
/* 382 */     String value = elem.getAnnotationValue();
/* 383 */     if (Strings.isNullOrEmpty(value)) {
/* 384 */       return inflectAccessorTarget(elem);
/*     */     }
/* 386 */     return value;
/*     */   }
/*     */   
/*     */   private String inflectAccessorTarget(AnnotatedElementAccessor elem) {
/* 390 */     return AccessorInfo.inflectTarget(elem.getSimpleName(), elem.getAccessorType(), "", this, false);
/*     */   }
/*     */ }


/* Location:              C:\Users\DuduM\OneDrive\Área de Trabalho\mw10\modularwarfare-shining-2023.2.4.4f-fix10.jar!\org\spongepowered\tools\obfuscation\AnnotatedMixinElementHandlerAccessor.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */