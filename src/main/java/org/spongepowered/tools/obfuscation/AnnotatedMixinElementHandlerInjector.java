/*     */ package org.spongepowered.tools.obfuscation;
/*     */ 
/*     */ import java.util.HashMap;
/*     */ import java.util.Iterator;
/*     */ import java.util.Map;
/*     */ import javax.annotation.processing.Messager;
/*     */ import javax.lang.model.element.AnnotationMirror;
/*     */ import javax.lang.model.element.Element;
/*     */ import javax.lang.model.element.ExecutableElement;
/*     */ import javax.lang.model.element.VariableElement;
/*     */ import javax.tools.Diagnostic;
/*     */ import org.spongepowered.asm.mixin.injection.Coerce;
/*     */ import org.spongepowered.asm.mixin.injection.selectors.ITargetSelector;
/*     */ import org.spongepowered.asm.mixin.injection.selectors.ITargetSelectorByName;
/*     */ import org.spongepowered.asm.mixin.injection.selectors.ITargetSelectorConstructor;
/*     */ import org.spongepowered.asm.mixin.injection.selectors.ITargetSelectorRemappable;
/*     */ import org.spongepowered.asm.mixin.injection.selectors.TargetSelector;
/*     */ import org.spongepowered.asm.mixin.injection.struct.InjectionPointData;
/*     */ import org.spongepowered.asm.mixin.injection.struct.InvalidMemberDescriptorException;
/*     */ import org.spongepowered.asm.obfuscation.mapping.common.MappingField;
/*     */ import org.spongepowered.asm.obfuscation.mapping.common.MappingMethod;
/*     */ import org.spongepowered.tools.obfuscation.ext.SpecialPackages;
/*     */ import org.spongepowered.tools.obfuscation.interfaces.IMixinAnnotationProcessor;
/*     */ import org.spongepowered.tools.obfuscation.interfaces.IReferenceManager;
/*     */ import org.spongepowered.tools.obfuscation.mirror.AnnotationHandle;
/*     */ import org.spongepowered.tools.obfuscation.mirror.TypeHandle;
/*     */ import org.spongepowered.tools.obfuscation.struct.InjectorRemap;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ class AnnotatedMixinElementHandlerInjector
/*     */   extends AnnotatedMixinElementHandler
/*     */ {
/*     */   static class AnnotatedElementInjector
/*     */     extends AnnotatedMixinElementHandler.AnnotatedElement<ExecutableElement>
/*     */   {
/*     */     private final InjectorRemap state;
/*     */     
/*     */     public AnnotatedElementInjector(ExecutableElement element, AnnotationHandle annotation, InjectorRemap shouldRemap) {
/*  69 */       super(element, annotation);
/*  70 */       this.state = shouldRemap;
/*     */     }
/*     */     
/*     */     public boolean shouldRemap() {
/*  74 */       return this.state.shouldRemap();
/*     */     }
/*     */     
/*     */     public boolean hasCoerceArgument() {
/*  78 */       if (!this.annotation.toString().equals("@Inject")) {
/*  79 */         return false;
/*     */       }
/*     */       
/*  82 */       Iterator<? extends VariableElement> iterator = this.element.getParameters().iterator(); if (iterator.hasNext()) { VariableElement param = iterator.next();
/*  83 */         return AnnotationHandle.of(param, Coerce.class).exists(); }
/*     */ 
/*     */       
/*  86 */       return false;
/*     */     }
/*     */     
/*     */     public void addMessage(Diagnostic.Kind kind, CharSequence msg, Element element, AnnotationHandle annotation) {
/*  90 */       this.state.addMessage(kind, msg, element, annotation);
/*     */     }
/*     */ 
/*     */     
/*     */     public String toString() {
/*  95 */       return getAnnotation().toString();
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   static class AnnotatedElementInjectionPoint
/*     */     extends AnnotatedMixinElementHandler.AnnotatedElement<ExecutableElement>
/*     */   {
/*     */     private final AnnotationHandle at;
/*     */     
/*     */     private Map<String, String> args;
/*     */     
/*     */     private final InjectorRemap state;
/*     */ 
/*     */     
/*     */     public AnnotatedElementInjectionPoint(ExecutableElement element, AnnotationHandle inject, AnnotationHandle at, InjectorRemap state) {
/* 112 */       super(element, inject);
/* 113 */       this.at = at;
/* 114 */       this.state = state;
/*     */     }
/*     */     
/*     */     public boolean shouldRemap() {
/* 118 */       return this.at.getBoolean("remap", this.state.shouldRemap());
/*     */     }
/*     */     
/*     */     public AnnotationHandle getAt() {
/* 122 */       return this.at;
/*     */     }
/*     */     
/*     */     public String getAtArg(String key) {
/* 126 */       if (this.args == null) {
/* 127 */         this.args = new HashMap<>();
/* 128 */         for (String arg : this.at.getList("args")) {
/* 129 */           if (arg == null) {
/*     */             continue;
/*     */           }
/* 132 */           int eqPos = arg.indexOf('=');
/* 133 */           if (eqPos > -1) {
/* 134 */             this.args.put(arg.substring(0, eqPos), arg.substring(eqPos + 1)); continue;
/*     */           } 
/* 136 */           this.args.put(arg, "");
/*     */         } 
/*     */       } 
/*     */ 
/*     */       
/* 141 */       return this.args.get(key);
/*     */     }
/*     */     
/*     */     public void notifyRemapped() {
/* 145 */       this.state.notifyRemapped();
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   AnnotatedMixinElementHandlerInjector(IMixinAnnotationProcessor ap, AnnotatedMixin mixin) {
/* 151 */     super(ap, mixin);
/*     */   }
/*     */   
/*     */   public void registerInjector(AnnotatedElementInjector elem) {
/* 155 */     if (this.mixin.isInterface()) {
/* 156 */       this.ap.printMessage(Diagnostic.Kind.ERROR, "Injector in interface is unsupported", elem.getElement());
/*     */     }
/*     */     
/* 159 */     for (String reference : elem.getAnnotation().getList("method")) {
/* 160 */       ITargetSelector targetSelector = TargetSelector.parse(reference);
/*     */       
/*     */       try {
/* 163 */         targetSelector.validate();
/* 164 */       } catch (InvalidMemberDescriptorException ex) {
/* 165 */         elem.printMessage((Messager)this.ap, Diagnostic.Kind.ERROR, ex.getMessage());
/*     */       } 
/*     */       
/* 168 */       if (!(targetSelector instanceof ITargetSelectorRemappable)) {
/*     */         continue;
/*     */       }
/*     */       
/* 172 */       ITargetSelectorRemappable targetMember = (ITargetSelectorRemappable)targetSelector;
/* 173 */       if (targetMember.getName() == null) {
/*     */         continue;
/*     */       }
/*     */       
/* 177 */       if (targetMember.getDesc() != null) {
/* 178 */         validateReferencedTarget(elem.getElement(), elem.getAnnotation(), (ITargetSelector)targetMember, elem.toString());
/*     */       }
/*     */       
/* 181 */       if (!elem.shouldRemap()) {
/*     */         continue;
/*     */       }
/*     */       
/* 185 */       for (TypeHandle target : this.mixin.getTargets()) {
/* 186 */         if (!registerInjector(elem, reference, targetMember, target)) {
/*     */           break;
/*     */         }
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   private boolean registerInjector(AnnotatedElementInjector elem, String reference, ITargetSelectorRemappable targetMember, TypeHandle target) {
/* 194 */     String desc = target.findDescriptor((ITargetSelectorByName)targetMember);
/* 195 */     if (desc == null) {
/* 196 */       Diagnostic.Kind error = this.mixin.isMultiTarget() ? Diagnostic.Kind.ERROR : Diagnostic.Kind.WARNING;
/* 197 */       if (target.isSimulated()) {
/* 198 */         elem.printMessage((Messager)this.ap, Diagnostic.Kind.NOTE, elem + " target '" + reference + "' in @Pseudo mixin will not be obfuscated");
/* 199 */       } else if (target.isImaginary()) {
/* 200 */         elem.printMessage((Messager)this.ap, error, elem + " target requires method signature because enclosing type information for " + target + " is unavailable");
/*     */       }
/* 202 */       else if (!targetMember.isInitialiser()) {
/* 203 */         elem.printMessage((Messager)this.ap, error, "Unable to determine signature for " + elem + " target method");
/*     */       } 
/* 205 */       return true;
/*     */     } 
/*     */     
/* 208 */     String targetName = elem + " target " + targetMember.getName();
/* 209 */     MappingMethod targetMethod = target.getMappingMethod(targetMember.getName(), desc);
/* 210 */     ObfuscationData<MappingMethod> obfData = this.obf.getDataProvider().getObfMethod(targetMethod);
/* 211 */     if (obfData.isEmpty()) {
/* 212 */       if (target.isSimulated())
/* 213 */       { obfData = this.obf.getDataProvider().getRemappedMethod(targetMethod); }
/* 214 */       else { if (targetMember.isClassInitialiser()) {
/* 215 */           return true;
/*     */         }
/* 217 */         Diagnostic.Kind error = targetMember.isConstructor() ? Diagnostic.Kind.WARNING : Diagnostic.Kind.ERROR;
/* 218 */         elem.addMessage(error, "No obfuscation mapping for " + targetName, elem.getElement(), elem.getAnnotation());
/* 219 */         return false; }
/*     */     
/*     */     }
/*     */     
/* 223 */     IReferenceManager refMap = this.obf.getReferenceManager();
/*     */     
/*     */     try {
/* 226 */       if ((targetMember.getOwner() == null && this.mixin.isMultiTarget()) || target.isSimulated()) {
/* 227 */         obfData = AnnotatedMixinElementHandler.stripOwnerData(obfData);
/*     */       }
/* 229 */       refMap.addMethodMapping(this.classRef, reference, obfData);
/* 230 */     } catch (ReferenceConflictException ex) {
/* 231 */       String conflictType = this.mixin.isMultiTarget() ? "Multi-target" : "Target";
/*     */       
/* 233 */       if (elem.hasCoerceArgument() && targetMember.getOwner() == null && targetMember.getDesc() == null) {
/* 234 */         ITargetSelector oldMember = TargetSelector.parse(ex.getOld());
/* 235 */         ITargetSelector newMember = TargetSelector.parse(ex.getNew());
/* 236 */         String oldName = (oldMember instanceof ITargetSelectorByName) ? ((ITargetSelectorByName)oldMember).getName() : oldMember.toString();
/* 237 */         String newName = (newMember instanceof ITargetSelectorByName) ? ((ITargetSelectorByName)newMember).getName() : newMember.toString();
/* 238 */         if (oldName != null && oldName.equals(newName)) {
/* 239 */           obfData = AnnotatedMixinElementHandler.stripDescriptors(obfData);
/* 240 */           refMap.setAllowConflicts(true);
/* 241 */           refMap.addMethodMapping(this.classRef, reference, obfData);
/* 242 */           refMap.setAllowConflicts(false);
/*     */ 
/*     */           
/* 245 */           elem.printMessage((Messager)this.ap, Diagnostic.Kind.WARNING, "Coerced " + conflictType + " reference has conflicting descriptors for " + targetName + ": Storing bare references " + obfData
/* 246 */               .values() + " in refMap");
/* 247 */           return true;
/*     */         } 
/*     */       } 
/*     */       
/* 251 */       elem.printMessage((Messager)this.ap, Diagnostic.Kind.ERROR, conflictType + " reference conflict for " + targetName + ": " + reference + " -> " + ex
/* 252 */           .getNew() + " previously defined as " + ex.getOld());
/*     */     } 
/*     */     
/* 255 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void registerInjectionPoint(AnnotatedElementInjectionPoint elem, String format) {
/* 263 */     if (this.mixin.isInterface()) {
/* 264 */       this.ap.printMessage(Diagnostic.Kind.ERROR, "Injector in interface is unsupported", elem.getElement());
/*     */     }
/*     */     
/* 267 */     if (!elem.shouldRemap()) {
/*     */       return;
/*     */     }
/*     */     
/* 271 */     String type = InjectionPointData.parseType((String)elem.getAt().getValue("value"));
/* 272 */     String target = (String)elem.getAt().getValue("target");
/*     */     
/* 274 */     if ("NEW".equals(type)) {
/* 275 */       remapNewTarget(String.format(format, new Object[] { type + ".<target>" }), target, elem);
/* 276 */       remapNewTarget(String.format(format, new Object[] { type + ".args[class]" }), elem.getAtArg("class"), elem);
/*     */     } else {
/* 278 */       remapReference(String.format(format, new Object[] { type + ".<target>" }), target, elem);
/*     */     } 
/*     */   }
/*     */   
/*     */   protected final void remapNewTarget(String subject, String reference, AnnotatedElementInjectionPoint elem) {
/* 283 */     if (reference == null) {
/*     */       return;
/*     */     }
/*     */     
/* 287 */     ITargetSelector selector = TargetSelector.parse(reference);
/* 288 */     if (selector instanceof ITargetSelectorConstructor) {
/* 289 */       ITargetSelectorConstructor member = (ITargetSelectorConstructor)selector;
/* 290 */       String target = member.toCtorType();
/*     */       
/* 292 */       if (target != null) {
/* 293 */         String desc = member.toCtorDesc();
/* 294 */         MappingMethod m = new MappingMethod(target, ".", (desc != null) ? desc : "()V");
/* 295 */         ObfuscationData<MappingMethod> remapped = this.obf.getDataProvider().getRemappedMethod(m);
/* 296 */         if (remapped.isEmpty() && !SpecialPackages.isExcludedPackage(member.toCtorType())) {
/* 297 */           this.ap.printMessage(Diagnostic.Kind.WARNING, "Cannot find class mapping for " + subject + " '" + target + "'", elem.getElement(), elem
/* 298 */               .getAnnotation().asMirror(), SuppressedBy.MAPPING);
/*     */           
/*     */           return;
/*     */         } 
/* 302 */         ObfuscationData<String> mappings = new ObfuscationData<>();
/* 303 */         for (ObfuscationType type : remapped) {
/* 304 */           MappingMethod mapping = remapped.get(type);
/* 305 */           if (desc == null) {
/* 306 */             mappings.put(type, mapping.getOwner()); continue;
/*     */           } 
/* 308 */           mappings.put(type, mapping.getDesc().replace(")V", ")L" + mapping.getOwner() + ";"));
/*     */         } 
/*     */ 
/*     */         
/* 312 */         this.obf.getReferenceManager().addClassMapping(this.classRef, reference, mappings);
/*     */       } 
/*     */     } 
/*     */     
/* 316 */     elem.notifyRemapped();
/*     */   }
/*     */   
/*     */   protected final void remapReference(String subject, String reference, AnnotatedElementInjectionPoint elem) {
/* 320 */     if (reference == null) {
/*     */       return;
/*     */     }
/*     */     
/* 324 */     ITargetSelector targetSelector = TargetSelector.parse(reference);
/* 325 */     if (!(targetSelector instanceof ITargetSelectorRemappable)) {
/*     */       return;
/*     */     }
/* 328 */     ITargetSelectorRemappable targetMember = (ITargetSelectorRemappable)targetSelector;
/*     */ 
/*     */     
/* 331 */     AnnotationMirror errorsOn = ((this.ap.getCompilerEnvironment() == IMixinAnnotationProcessor.CompilerEnvironment.JDT) ? elem.getAt() : elem.getAnnotation()).asMirror();
/*     */     
/* 333 */     if (!targetMember.isFullyQualified()) {
/* 334 */       String missing = (targetMember.getOwner() == null) ? ((targetMember.getDesc() == null) ? "owner and signature" : "owner") : "signature";
/* 335 */       this.ap.printMessage(Diagnostic.Kind.ERROR, subject + " is not fully qualified, missing " + missing, elem.getElement(), errorsOn);
/*     */       
/*     */       return;
/*     */     } 
/*     */     try {
/* 340 */       targetMember.validate();
/* 341 */     } catch (InvalidMemberDescriptorException ex) {
/* 342 */       this.ap.printMessage(Diagnostic.Kind.ERROR, ex.getMessage(), elem.getElement(), errorsOn);
/*     */     } 
/*     */     
/*     */     try {
/* 346 */       if (targetMember.isField()) {
/* 347 */         ObfuscationData<MappingField> obfFieldData = this.obf.getDataProvider().getObfFieldRecursive(targetMember);
/* 348 */         if (obfFieldData.isEmpty()) {
/* 349 */           if (targetMember.getOwner() == null || !SpecialPackages.isExcludedPackage(targetMember.getOwner())) {
/* 350 */             this.ap.printMessage(Diagnostic.Kind.WARNING, "Cannot find field mapping for " + subject + " '" + reference + "'", elem.getElement(), errorsOn, SuppressedBy.MAPPING);
/*     */           }
/*     */           
/*     */           return;
/*     */         } 
/* 355 */         this.obf.getReferenceManager().addFieldMapping(this.classRef, reference, targetMember, obfFieldData);
/*     */       } else {
/* 357 */         ObfuscationData<MappingMethod> obfMethodData = this.obf.getDataProvider().getObfMethodRecursive(targetMember);
/* 358 */         if (obfMethodData.isEmpty()) {
/* 359 */           if (targetMember.getOwner() == null || !SpecialPackages.isExcludedPackage(targetMember.getOwner())) {
/* 360 */             this.ap.printMessage(Diagnostic.Kind.WARNING, "Cannot find method mapping for " + subject + " '" + reference + "'", elem.getElement(), errorsOn, SuppressedBy.MAPPING);
/*     */           }
/*     */           
/*     */           return;
/*     */         } 
/* 365 */         this.obf.getReferenceManager().addMethodMapping(this.classRef, reference, targetMember, obfMethodData);
/*     */       } 
/* 367 */     } catch (ReferenceConflictException ex) {
/*     */ 
/*     */       
/* 370 */       this.ap.printMessage(Diagnostic.Kind.ERROR, "Unexpected reference conflict for " + subject + ": " + reference + " -> " + ex
/* 371 */           .getNew() + " previously defined as " + ex.getOld(), elem.getElement(), errorsOn);
/*     */       
/*     */       return;
/*     */     } 
/* 375 */     elem.notifyRemapped();
/*     */   }
/*     */ }


/* Location:              C:\Users\DuduM\OneDrive\Área de Trabalho\mw10\modularwarfare-shining-2023.2.4.4f-fix10.jar!\org\spongepowered\tools\obfuscation\AnnotatedMixinElementHandlerInjector.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */