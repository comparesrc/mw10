/*     */ package org.spongepowered.tools.obfuscation;
/*     */ 
/*     */ import java.util.Locale;
/*     */ import javax.annotation.processing.Messager;
/*     */ import javax.lang.model.element.Element;
/*     */ import javax.lang.model.element.ExecutableElement;
/*     */ import javax.lang.model.element.VariableElement;
/*     */ import javax.tools.Diagnostic;
/*     */ import org.spongepowered.asm.obfuscation.mapping.IMapping;
/*     */ import org.spongepowered.asm.obfuscation.mapping.common.MappingField;
/*     */ import org.spongepowered.asm.obfuscation.mapping.common.MappingMethod;
/*     */ import org.spongepowered.tools.obfuscation.interfaces.IMixinAnnotationProcessor;
/*     */ import org.spongepowered.tools.obfuscation.interfaces.IObfuscationDataProvider;
/*     */ import org.spongepowered.tools.obfuscation.mirror.AnnotationHandle;
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
/*     */ 
/*     */ class AnnotatedMixinElementHandlerShadow
/*     */   extends AnnotatedMixinElementHandler
/*     */ {
/*     */   static abstract class AnnotatedElementShadow<E extends Element, M extends IMapping<M>>
/*     */     extends AnnotatedMixinElementHandler.AnnotatedElement<E>
/*     */   {
/*     */     private final boolean shouldRemap;
/*     */     private final AnnotatedMixinElementHandler.ShadowElementName name;
/*     */     private final IMapping.Type type;
/*     */     
/*     */     protected AnnotatedElementShadow(E element, AnnotationHandle annotation, boolean shouldRemap, IMapping.Type type) {
/*  64 */       super(element, annotation);
/*  65 */       this.shouldRemap = shouldRemap;
/*  66 */       this.name = new AnnotatedMixinElementHandler.ShadowElementName((Element)element, annotation);
/*  67 */       this.type = type;
/*     */     }
/*     */     
/*     */     public boolean shouldRemap() {
/*  71 */       return this.shouldRemap;
/*     */     }
/*     */     
/*     */     public AnnotatedMixinElementHandler.ShadowElementName getName() {
/*  75 */       return this.name;
/*     */     }
/*     */     
/*     */     public IMapping.Type getElementType() {
/*  79 */       return this.type;
/*     */     }
/*     */ 
/*     */     
/*     */     public String toString() {
/*  84 */       return getElementType().name().toLowerCase(Locale.ROOT);
/*     */     }
/*     */     
/*     */     public AnnotatedMixinElementHandler.ShadowElementName setObfuscatedName(IMapping<?> name) {
/*  88 */       return setObfuscatedName(name.getSimpleName());
/*     */     }
/*     */     
/*     */     public AnnotatedMixinElementHandler.ShadowElementName setObfuscatedName(String name) {
/*  92 */       return getName().setObfuscatedName(name);
/*     */     }
/*     */     
/*     */     public ObfuscationData<M> getObfuscationData(IObfuscationDataProvider provider, TypeHandle owner) {
/*  96 */       return provider.getObfEntry((IMapping)getMapping(owner, getName().toString(), getDesc()));
/*     */     }
/*     */ 
/*     */     
/*     */     public abstract M getMapping(TypeHandle param1TypeHandle, String param1String1, String param1String2);
/*     */ 
/*     */     
/*     */     public abstract void addMapping(ObfuscationType param1ObfuscationType, IMapping<?> param1IMapping);
/*     */   }
/*     */ 
/*     */   
/*     */   class AnnotatedElementShadowField
/*     */     extends AnnotatedElementShadow<VariableElement, MappingField>
/*     */   {
/*     */     public AnnotatedElementShadowField(VariableElement element, AnnotationHandle annotation, boolean shouldRemap) {
/* 111 */       super(element, annotation, shouldRemap, IMapping.Type.FIELD);
/*     */     }
/*     */ 
/*     */     
/*     */     public MappingField getMapping(TypeHandle owner, String name, String desc) {
/* 116 */       return new MappingField(owner.getName(), name, desc);
/*     */     }
/*     */ 
/*     */     
/*     */     public void addMapping(ObfuscationType type, IMapping<?> remapped) {
/* 121 */       AnnotatedMixinElementHandlerShadow.this.addFieldMapping(type, setObfuscatedName(remapped), getDesc(), remapped.getDesc());
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   class AnnotatedElementShadowMethod
/*     */     extends AnnotatedElementShadow<ExecutableElement, MappingMethod>
/*     */   {
/*     */     public AnnotatedElementShadowMethod(ExecutableElement element, AnnotationHandle annotation, boolean shouldRemap) {
/* 132 */       super(element, annotation, shouldRemap, IMapping.Type.METHOD);
/*     */     }
/*     */ 
/*     */     
/*     */     public MappingMethod getMapping(TypeHandle owner, String name, String desc) {
/* 137 */       return owner.getMappingMethod(name, desc);
/*     */     }
/*     */ 
/*     */     
/*     */     public void addMapping(ObfuscationType type, IMapping<?> remapped) {
/* 142 */       AnnotatedMixinElementHandlerShadow.this.addMethodMapping(type, setObfuscatedName(remapped), getDesc(), remapped.getDesc());
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   AnnotatedMixinElementHandlerShadow(IMixinAnnotationProcessor ap, AnnotatedMixin mixin) {
/* 148 */     super(ap, mixin);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void registerShadow(AnnotatedElementShadow<?, ?> elem) {
/* 155 */     validateTarget((Element)elem.getElement(), elem.getAnnotation(), elem.getName(), "@Shadow");
/*     */     
/* 157 */     if (!elem.shouldRemap()) {
/*     */       return;
/*     */     }
/*     */     
/* 161 */     for (TypeHandle target : this.mixin.getTargets()) {
/* 162 */       registerShadowForTarget(elem, target);
/*     */     }
/*     */   }
/*     */   
/*     */   private void registerShadowForTarget(AnnotatedElementShadow<?, ?> elem, TypeHandle target) {
/* 167 */     ObfuscationData<? extends IMapping<?>> obfData = (ObfuscationData)elem.getObfuscationData(this.obf.getDataProvider(), target);
/*     */     
/* 169 */     if (obfData.isEmpty()) {
/* 170 */       String info = this.mixin.isMultiTarget() ? (" in target " + target) : "";
/* 171 */       if (target.isSimulated()) {
/* 172 */         elem.printMessage((Messager)this.ap, Diagnostic.Kind.WARNING, "Unable to locate obfuscation mapping" + info + " for @Shadow " + elem);
/*     */       } else {
/* 174 */         elem.printMessage((Messager)this.ap, Diagnostic.Kind.WARNING, "Unable to locate obfuscation mapping" + info + " for @Shadow " + elem);
/*     */       } 
/*     */       
/*     */       return;
/*     */     } 
/* 179 */     for (ObfuscationType type : obfData) {
/*     */       try {
/* 181 */         elem.addMapping(type, obfData.get(type));
/* 182 */       } catch (MappingConflictException ex) {
/* 183 */         elem.printMessage((Messager)this.ap, Diagnostic.Kind.ERROR, "Mapping conflict for @Shadow " + elem + ": " + ex.getNew().getSimpleName() + " for target " + target + " conflicts with existing mapping " + ex
/* 184 */             .getOld().getSimpleName());
/*     */       } 
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\DuduM\OneDrive\Área de Trabalho\mw10\modularwarfare-shining-2023.2.4.4f-fix10.jar!\org\spongepowered\tools\obfuscation\AnnotatedMixinElementHandlerShadow.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */