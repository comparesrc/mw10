/*     */ package org.spongepowered.tools.obfuscation.mirror;
/*     */ 
/*     */ import com.google.common.collect.ImmutableList;
/*     */ import java.lang.annotation.Annotation;
/*     */ import java.util.Collections;
/*     */ import java.util.List;
/*     */ import javax.lang.model.element.Element;
/*     */ import javax.lang.model.element.ElementKind;
/*     */ import javax.lang.model.element.ExecutableElement;
/*     */ import javax.lang.model.element.Modifier;
/*     */ import javax.lang.model.element.PackageElement;
/*     */ import javax.lang.model.element.TypeElement;
/*     */ import javax.lang.model.element.VariableElement;
/*     */ import javax.lang.model.type.DeclaredType;
/*     */ import javax.lang.model.type.TypeKind;
/*     */ import javax.lang.model.type.TypeMirror;
/*     */ import org.spongepowered.asm.mixin.injection.selectors.ITargetSelectorByName;
/*     */ import org.spongepowered.asm.obfuscation.mapping.common.MappingMethod;
/*     */ import org.spongepowered.asm.util.Bytecode;
/*     */ import org.spongepowered.tools.obfuscation.mirror.mapping.MappingMethodResolvable;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class TypeHandle
/*     */ {
/*     */   private final String name;
/*     */   private final PackageElement pkg;
/*     */   private final TypeElement element;
/*     */   private TypeReference reference;
/*     */   
/*     */   public TypeHandle(PackageElement pkg, String name) {
/*  86 */     this.name = name.replace('.', '/');
/*  87 */     this.pkg = pkg;
/*  88 */     this.element = null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public TypeHandle(TypeElement element) {
/*  97 */     this.pkg = TypeUtils.getPackage(element);
/*  98 */     this.name = TypeUtils.getInternalName(element);
/*  99 */     this.element = element;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public TypeHandle(DeclaredType type) {
/* 108 */     this((TypeElement)type.asElement());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final String toString() {
/* 116 */     return this.name.replace('/', '.');
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final String getName() {
/* 123 */     return this.name;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final String getSimpleName() {
/* 130 */     return Bytecode.getSimpleName(this.name);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final PackageElement getPackage() {
/* 137 */     return this.pkg;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final TypeElement getElement() {
/* 144 */     return this.element;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected TypeElement getTargetElement() {
/* 152 */     return this.element;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public AnnotationHandle getAnnotation(Class<? extends Annotation> annotationClass) {
/* 163 */     return AnnotationHandle.of(getTargetElement(), annotationClass);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final List<? extends Element> getEnclosedElements() {
/* 170 */     return getEnclosedElements(getTargetElement());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public <T extends Element> List<T> getEnclosedElements(ElementKind... kind) {
/* 180 */     return getEnclosedElements(getTargetElement(), kind);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public TypeMirror getType() {
/* 188 */     return (getTargetElement() != null) ? getTargetElement().asType() : null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public TypeHandle getSuperclass() {
/* 196 */     TypeElement targetElement = getTargetElement();
/* 197 */     if (targetElement == null) {
/* 198 */       return null;
/*     */     }
/*     */     
/* 201 */     TypeMirror superClass = targetElement.getSuperclass();
/* 202 */     if (superClass == null || superClass.getKind() == TypeKind.NONE) {
/* 203 */       return null;
/*     */     }
/*     */     
/* 206 */     return new TypeHandle((DeclaredType)superClass);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public List<TypeHandle> getInterfaces() {
/* 213 */     if (getTargetElement() == null) {
/* 214 */       return Collections.emptyList();
/*     */     }
/*     */     
/* 217 */     ImmutableList.Builder<TypeHandle> list = ImmutableList.builder();
/* 218 */     for (TypeMirror iface : getTargetElement().getInterfaces()) {
/* 219 */       list.add(new TypeHandle((DeclaredType)iface));
/*     */     }
/*     */     
/* 222 */     return (List<TypeHandle>)list.build();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isPublic() {
/* 229 */     TypeElement targetElement = getTargetElement();
/* 230 */     if (targetElement == null || !targetElement.getModifiers().contains(Modifier.PUBLIC)) {
/* 231 */       return false;
/*     */     }
/* 233 */     for (Element e = targetElement.getEnclosingElement(); e != null && e.getKind() != ElementKind.PACKAGE; e = e.getEnclosingElement()) {
/* 234 */       if (!e.getModifiers().contains(Modifier.PUBLIC)) {
/* 235 */         return false;
/*     */       }
/*     */     } 
/* 238 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isImaginary() {
/* 245 */     return (getTargetElement() == null);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isSimulated() {
/* 252 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final TypeReference getReference() {
/* 259 */     if (this.reference == null) {
/* 260 */       this.reference = new TypeReference(this);
/*     */     }
/* 262 */     return this.reference;
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
/*     */   public MappingMethod getMappingMethod(String name, String desc) {
/* 275 */     return (MappingMethod)new MappingMethodResolvable(this, name, desc);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String findDescriptor(ITargetSelectorByName selector) {
/* 285 */     String desc = selector.getDesc();
/* 286 */     if (desc == null) {
/* 287 */       for (ExecutableElement method : getEnclosedElements(new ElementKind[] { ElementKind.METHOD })) {
/* 288 */         if (method.getSimpleName().toString().equals(selector.getName())) {
/* 289 */           desc = TypeUtils.getDescriptor(method);
/*     */           break;
/*     */         } 
/*     */       } 
/*     */     }
/* 294 */     return desc;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final FieldHandle findField(VariableElement element) {
/* 305 */     return findField(element, true);
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
/*     */   public final FieldHandle findField(VariableElement element, boolean caseSensitive) {
/* 317 */     return findField(element.getSimpleName().toString(), TypeUtils.getTypeName(element.asType()), caseSensitive);
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
/*     */   public final FieldHandle findField(String name, String type) {
/* 329 */     return findField(name, type, true);
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
/*     */   public FieldHandle findField(String name, String type, boolean caseSensitive) {
/* 342 */     String rawType = TypeUtils.stripGenerics(type);
/*     */     
/* 344 */     for (VariableElement field : getEnclosedElements(new ElementKind[] { ElementKind.FIELD })) {
/* 345 */       if (compareElement(field, name, type, caseSensitive))
/* 346 */         return new FieldHandle(getTargetElement(), field); 
/* 347 */       if (compareElement(field, name, rawType, caseSensitive)) {
/* 348 */         return new FieldHandle(getTargetElement(), field, true);
/*     */       }
/*     */     } 
/*     */     
/* 352 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final MethodHandle findMethod(ExecutableElement element) {
/* 363 */     return findMethod(element, true);
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
/*     */   public final MethodHandle findMethod(ExecutableElement element, boolean caseSensitive) {
/* 375 */     return findMethod(element.getSimpleName().toString(), TypeUtils.getJavaSignature(element), caseSensitive);
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
/*     */   public final MethodHandle findMethod(String name, String signature) {
/* 387 */     return findMethod(name, signature, true);
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
/*     */   public MethodHandle findMethod(String name, String signature, boolean matchCase) {
/* 400 */     String rawSignature = TypeUtils.stripGenerics(signature);
/* 401 */     return findMethod(this, name, signature, rawSignature, matchCase);
/*     */   }
/*     */   
/*     */   protected static MethodHandle findMethod(TypeHandle target, String name, String signature, String rawSignature, boolean matchCase) {
/* 405 */     for (ExecutableElement method : getEnclosedElements(target.getTargetElement(), new ElementKind[] { ElementKind.CONSTRUCTOR, ElementKind.METHOD })) {
/*     */       
/* 407 */       if (compareElement(method, name, signature, matchCase) || compareElement(method, name, rawSignature, matchCase)) {
/* 408 */         return new MethodHandle(target, method);
/*     */       }
/*     */     } 
/* 411 */     return null;
/*     */   }
/*     */   
/*     */   protected static boolean compareElement(Element elem, String name, String type, boolean matchCase) {
/*     */     try {
/* 416 */       String elementName = elem.getSimpleName().toString();
/* 417 */       String elementType = TypeUtils.getJavaSignature(elem);
/* 418 */       String rawElementType = TypeUtils.stripGenerics(elementType);
/* 419 */       boolean compared = matchCase ? name.equals(elementName) : name.equalsIgnoreCase(elementName);
/* 420 */       return (compared && (type.length() == 0 || type.equals(elementType) || type.equals(rawElementType)));
/* 421 */     } catch (NullPointerException ex) {
/* 422 */       return false;
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   protected static <T extends Element> List<T> getEnclosedElements(TypeElement targetElement, ElementKind... kind) {
/* 428 */     if (kind == null || kind.length < 1) {
/* 429 */       return (List)getEnclosedElements(targetElement);
/*     */     }
/*     */     
/* 432 */     if (targetElement == null) {
/* 433 */       return Collections.emptyList();
/*     */     }
/*     */     
/* 436 */     ImmutableList.Builder<T> list = ImmutableList.builder();
/* 437 */     for (Element elem : targetElement.getEnclosedElements()) {
/* 438 */       for (ElementKind ek : kind) {
/* 439 */         if (elem.getKind() == ek) {
/* 440 */           list.add(elem);
/*     */           
/*     */           break;
/*     */         } 
/*     */       } 
/*     */     } 
/* 446 */     return (List<T>)list.build();
/*     */   }
/*     */   
/*     */   protected static List<? extends Element> getEnclosedElements(TypeElement targetElement) {
/* 450 */     return (targetElement != null) ? targetElement.getEnclosedElements() : Collections.<Element>emptyList();
/*     */   }
/*     */ }


/* Location:              C:\Users\DuduM\OneDrive\Área de Trabalho\mw10\modularwarfare-shining-2023.2.4.4f-fix10.jar!\org\spongepowered\tools\obfuscation\mirror\TypeHandle.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */