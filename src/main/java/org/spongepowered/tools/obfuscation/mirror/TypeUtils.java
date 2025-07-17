/*     */ package org.spongepowered.tools.obfuscation.mirror;
/*     */ 
/*     */ import java.util.List;
/*     */ import javax.annotation.processing.ProcessingEnvironment;
/*     */ import javax.lang.model.element.Element;
/*     */ import javax.lang.model.element.ExecutableElement;
/*     */ import javax.lang.model.element.Modifier;
/*     */ import javax.lang.model.element.Name;
/*     */ import javax.lang.model.element.PackageElement;
/*     */ import javax.lang.model.element.TypeElement;
/*     */ import javax.lang.model.element.VariableElement;
/*     */ import javax.lang.model.type.ArrayType;
/*     */ import javax.lang.model.type.DeclaredType;
/*     */ import javax.lang.model.type.TypeKind;
/*     */ import javax.lang.model.type.TypeMirror;
/*     */ import javax.lang.model.type.TypeVariable;
/*     */ import org.spongepowered.asm.util.SignaturePrinter;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public abstract class TypeUtils
/*     */ {
/*     */   private static final int MAX_GENERIC_RECURSION_DEPTH = 5;
/*     */   private static final String OBJECT_SIG = "java.lang.Object";
/*     */   
/*     */   public enum Equivalency
/*     */   {
/*  60 */     NOT_EQUIVALENT,
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  65 */     EQUIVALENT_BUT_RAW,
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  70 */     BOUNDS_MISMATCH,
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  76 */     EQUIVALENT;
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
/*     */   public static class EquivalencyResult
/*     */   {
/*  89 */     static final EquivalencyResult EQUIVALENT = new EquivalencyResult(TypeUtils.Equivalency.EQUIVALENT, "", 0);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public final TypeUtils.Equivalency type;
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public final String detail;
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public final int rawType;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     EquivalencyResult(TypeUtils.Equivalency type, String detail, int rawType) {
/* 112 */       this.type = type;
/* 113 */       this.detail = detail;
/* 114 */       this.rawType = rawType;
/*     */     }
/*     */ 
/*     */     
/*     */     public String toString() {
/* 119 */       return this.detail;
/*     */     }
/*     */     
/*     */     static EquivalencyResult notEquivalent(String format, Object... args) {
/* 123 */       return new EquivalencyResult(TypeUtils.Equivalency.NOT_EQUIVALENT, String.format(format, args), 0);
/*     */     }
/*     */     
/*     */     static EquivalencyResult boundsMismatch(String format, Object... args) {
/* 127 */       return new EquivalencyResult(TypeUtils.Equivalency.BOUNDS_MISMATCH, String.format(format, args), 0);
/*     */     }
/*     */     
/*     */     static EquivalencyResult equivalentButRaw(int rawType) {
/* 131 */       return new EquivalencyResult(TypeUtils.Equivalency.EQUIVALENT_BUT_RAW, String.format("Type %d is raw", new Object[] { Integer.valueOf(rawType) }), rawType);
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
/*     */   public static PackageElement getPackage(TypeMirror type) {
/* 155 */     if (!(type instanceof DeclaredType)) {
/* 156 */       return null;
/*     */     }
/* 158 */     return getPackage((TypeElement)((DeclaredType)type).asElement());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static PackageElement getPackage(TypeElement type) {
/* 167 */     Element parent = type.getEnclosingElement();
/* 168 */     while (parent != null && !(parent instanceof PackageElement)) {
/* 169 */       parent = parent.getEnclosingElement();
/*     */     }
/* 171 */     return (PackageElement)parent;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static String getElementType(Element element) {
/* 182 */     if (element instanceof TypeElement)
/* 183 */       return "TypeElement"; 
/* 184 */     if (element instanceof ExecutableElement)
/* 185 */       return "ExecutableElement"; 
/* 186 */     if (element instanceof VariableElement)
/* 187 */       return "VariableElement"; 
/* 188 */     if (element instanceof PackageElement)
/* 189 */       return "PackageElement"; 
/* 190 */     if (element instanceof javax.lang.model.element.TypeParameterElement) {
/* 191 */       return "TypeParameterElement";
/*     */     }
/*     */     
/* 194 */     return element.getClass().getSimpleName();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static String stripGenerics(String type) {
/* 204 */     StringBuilder sb = new StringBuilder();
/* 205 */     for (int pos = 0, depth = 0; pos < type.length(); pos++) {
/* 206 */       char c = type.charAt(pos);
/* 207 */       if (c == '<') {
/* 208 */         depth++;
/*     */       }
/* 210 */       if (depth == 0) {
/* 211 */         sb.append(c);
/* 212 */       } else if (c == '>') {
/* 213 */         depth--;
/*     */       } 
/*     */     } 
/* 216 */     return sb.toString();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static String getName(VariableElement field) {
/* 226 */     return (field != null) ? field.getSimpleName().toString() : null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static String getName(ExecutableElement method) {
/* 236 */     return (method != null) ? method.getSimpleName().toString() : null;
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
/*     */   public static String getJavaSignature(Element element) {
/* 249 */     if (element instanceof ExecutableElement) {
/* 250 */       ExecutableElement method = (ExecutableElement)element;
/* 251 */       StringBuilder desc = (new StringBuilder()).append("(");
/* 252 */       boolean extra = false;
/* 253 */       for (VariableElement arg : method.getParameters()) {
/* 254 */         if (extra) {
/* 255 */           desc.append(',');
/*     */         }
/* 257 */         desc.append(getTypeName(arg.asType()));
/* 258 */         extra = true;
/*     */       } 
/* 260 */       desc.append(')').append(getTypeName(method.getReturnType()));
/* 261 */       return desc.toString();
/*     */     } 
/* 263 */     return getTypeName(element.asType());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static String getJavaSignature(String descriptor) {
/* 273 */     return (new SignaturePrinter("", descriptor)).setFullyQualified(true).toDescriptor();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static String getSimpleName(TypeMirror type) {
/* 283 */     String name = getTypeName(type);
/* 284 */     int pos = name.lastIndexOf('.');
/* 285 */     return (pos > 0) ? name.substring(pos + 1) : name;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static String getTypeName(TypeMirror type) {
/* 295 */     switch (type.getKind()) { case PUBLIC:
/* 296 */         return getTypeName(((ArrayType)type).getComponentType()) + "[]";
/* 297 */       case PROTECTED: return getTypeName((DeclaredType)type);
/* 298 */       case PRIVATE: return getTypeName(getUpperBound(type));
/* 299 */       case null: return "java.lang.Object"; }
/* 300 */      return type.toString();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static String getTypeName(DeclaredType type) {
/* 311 */     if (type == null) {
/* 312 */       return "java.lang.Object";
/*     */     }
/* 314 */     return getInternalName((TypeElement)type.asElement()).replace('/', '.');
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static String getDescriptor(Element element) {
/* 324 */     if (element instanceof ExecutableElement)
/* 325 */       return getDescriptor((ExecutableElement)element); 
/* 326 */     if (element instanceof VariableElement) {
/* 327 */       return getInternalName((VariableElement)element);
/*     */     }
/*     */     
/* 330 */     return getInternalName(element.asType());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static String getDescriptor(ExecutableElement method) {
/* 340 */     if (method == null) {
/* 341 */       return null;
/*     */     }
/*     */     
/* 344 */     StringBuilder signature = new StringBuilder();
/*     */     
/* 346 */     for (VariableElement var : method.getParameters()) {
/* 347 */       signature.append(getInternalName(var));
/*     */     }
/*     */     
/* 350 */     String returnType = getInternalName(method.getReturnType());
/* 351 */     return String.format("(%s)%s", new Object[] { signature, returnType });
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static String getInternalName(VariableElement field) {
/* 361 */     if (field == null) {
/* 362 */       return null;
/*     */     }
/* 364 */     return getInternalName(field.asType());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static String getInternalName(TypeMirror type) {
/* 374 */     switch (type.getKind()) { case PUBLIC:
/* 375 */         return "[" + getInternalName(((ArrayType)type).getComponentType());
/* 376 */       case PROTECTED: return "L" + getInternalName((DeclaredType)type) + ";";
/* 377 */       case PRIVATE: return "L" + getInternalName(getUpperBound(type)) + ";";
/* 378 */       case null: return "Z";
/* 379 */       case null: return "B";
/* 380 */       case null: return "C";
/* 381 */       case null: return "D";
/* 382 */       case null: return "F";
/* 383 */       case null: return "I";
/* 384 */       case null: return "J";
/* 385 */       case null: return "S";
/* 386 */       case null: return "V";
/*     */       case null:
/* 388 */         return "Ljava/lang/Object;"; }
/*     */ 
/*     */ 
/*     */     
/* 392 */     throw new IllegalArgumentException("Unable to parse type symbol " + type + " with " + type.getKind() + " to equivalent bytecode type");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static String getInternalName(DeclaredType type) {
/* 402 */     if (type == null) {
/* 403 */       return "java/lang/Object";
/*     */     }
/* 405 */     return getInternalName((TypeElement)type.asElement());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static String getInternalName(TypeElement element) {
/* 415 */     if (element == null) {
/* 416 */       return null;
/*     */     }
/* 418 */     StringBuilder reference = new StringBuilder();
/* 419 */     reference.append(element.getSimpleName());
/* 420 */     Element parent = element.getEnclosingElement();
/* 421 */     while (parent != null) {
/* 422 */       if (parent instanceof TypeElement) {
/* 423 */         reference.insert(0, "$").insert(0, parent.getSimpleName());
/* 424 */       } else if (parent instanceof PackageElement) {
/* 425 */         reference.insert(0, "/").insert(0, ((PackageElement)parent).getQualifiedName().toString().replace('.', '/'));
/*     */       } 
/* 427 */       parent = parent.getEnclosingElement();
/*     */     } 
/* 429 */     return reference.toString();
/*     */   }
/*     */   
/*     */   private static DeclaredType getUpperBound(TypeMirror type) {
/*     */     try {
/* 434 */       return getUpperBound0(type, 5);
/* 435 */     } catch (IllegalStateException ex) {
/* 436 */       throw new IllegalArgumentException("Type symbol \"" + type + "\" is too complex", ex);
/* 437 */     } catch (IllegalArgumentException ex) {
/* 438 */       throw new IllegalArgumentException("Unable to compute upper bound of type symbol " + type, ex);
/*     */     } 
/*     */   }
/*     */   
/*     */   private static DeclaredType getUpperBound0(TypeMirror type, int depth) {
/* 443 */     if (depth == 0) {
/* 444 */       throw new IllegalStateException("Generic symbol \"" + type + "\" is too complex, exceeded " + '\005' + " iterations attempting to determine upper bound");
/*     */     }
/*     */     
/* 447 */     if (type instanceof DeclaredType) {
/* 448 */       return (DeclaredType)type;
/*     */     }
/* 450 */     if (type instanceof TypeVariable) {
/*     */       try {
/* 452 */         TypeMirror upper = ((TypeVariable)type).getUpperBound();
/* 453 */         return getUpperBound0(upper, --depth);
/* 454 */       } catch (IllegalStateException ex) {
/* 455 */         throw ex;
/* 456 */       } catch (IllegalArgumentException ex) {
/* 457 */         throw ex;
/* 458 */       } catch (Exception ex) {
/* 459 */         throw new IllegalArgumentException("Unable to compute upper bound of type symbol " + type);
/*     */       } 
/*     */     }
/* 462 */     return null;
/*     */   }
/*     */   
/*     */   private static String describeGenericBound(TypeMirror type) {
/* 466 */     if (type instanceof TypeVariable) {
/* 467 */       StringBuilder description = new StringBuilder("<");
/* 468 */       TypeVariable typeVar = (TypeVariable)type;
/* 469 */       description.append(typeVar.toString());
/* 470 */       TypeMirror lowerBound = typeVar.getLowerBound();
/* 471 */       if (lowerBound.getKind() != TypeKind.NULL) {
/* 472 */         description.append(" super ").append(lowerBound);
/*     */       }
/* 474 */       TypeMirror upperBound = typeVar.getUpperBound();
/* 475 */       if (upperBound.getKind() != TypeKind.NULL) {
/* 476 */         description.append(" extends ").append(upperBound);
/*     */       }
/* 478 */       return description.append(">").toString();
/*     */     } 
/*     */     
/* 481 */     return type.toString();
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
/*     */   public static boolean isAssignable(ProcessingEnvironment processingEnv, TypeMirror targetType, TypeMirror superClass) {
/* 493 */     boolean assignable = processingEnv.getTypeUtils().isAssignable(targetType, superClass);
/* 494 */     if (!assignable && targetType instanceof DeclaredType && superClass instanceof DeclaredType) {
/* 495 */       TypeMirror rawTargetType = toRawType(processingEnv, (DeclaredType)targetType);
/* 496 */       TypeMirror rawSuperType = toRawType(processingEnv, (DeclaredType)superClass);
/* 497 */       return processingEnv.getTypeUtils().isAssignable(rawTargetType, rawSuperType);
/*     */     } 
/*     */     
/* 500 */     return assignable;
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
/*     */   public static EquivalencyResult isEquivalentType(ProcessingEnvironment processingEnv, TypeMirror t1, TypeMirror t2) {
/* 517 */     if (t1 == null || t2 == null) {
/* 518 */       return EquivalencyResult.notEquivalent("Invalid types supplied: %s, %s", new Object[] { t1, t2 });
/*     */     }
/*     */     
/* 521 */     if (processingEnv.getTypeUtils().isSameType(t1, t2)) {
/* 522 */       return EquivalencyResult.EQUIVALENT;
/*     */     }
/*     */     
/* 525 */     if (t1 instanceof TypeVariable && t2 instanceof TypeVariable) {
/* 526 */       t1 = getUpperBound(t1);
/* 527 */       t2 = getUpperBound(t2);
/* 528 */       if (processingEnv.getTypeUtils().isSameType(t1, t2)) {
/* 529 */         return EquivalencyResult.EQUIVALENT;
/*     */       }
/*     */     } 
/*     */     
/* 533 */     if (t1 instanceof DeclaredType && t2 instanceof DeclaredType) {
/* 534 */       DeclaredType dtT1 = (DeclaredType)t1;
/* 535 */       DeclaredType dtT2 = (DeclaredType)t2;
/* 536 */       TypeMirror rawT1 = toRawType(processingEnv, dtT1);
/* 537 */       TypeMirror rawT2 = toRawType(processingEnv, dtT2);
/* 538 */       if (!processingEnv.getTypeUtils().isSameType(rawT1, rawT2)) {
/* 539 */         return EquivalencyResult.notEquivalent("Base types %s and %s are not compatible", new Object[] { rawT1, rawT2 });
/*     */       }
/* 541 */       List<? extends TypeMirror> argsT1 = dtT1.getTypeArguments();
/* 542 */       List<? extends TypeMirror> argsT2 = dtT2.getTypeArguments();
/* 543 */       if (argsT1.size() != argsT2.size()) {
/* 544 */         if (argsT1.size() == 0) {
/* 545 */           return EquivalencyResult.equivalentButRaw(1);
/*     */         }
/* 547 */         if (argsT2.size() == 0) {
/* 548 */           return EquivalencyResult.equivalentButRaw(2);
/*     */         }
/* 550 */         return EquivalencyResult.notEquivalent("Mismatched generic argument counts %s<[%d]> and %s<[%d]>", new Object[] { rawT1, Integer.valueOf(argsT1.size()), rawT2, Integer.valueOf(argsT2.size()) });
/*     */       } 
/*     */       
/* 553 */       for (int arg = 0; arg < argsT1.size(); arg++) {
/* 554 */         TypeMirror argT1 = argsT1.get(arg);
/* 555 */         TypeMirror argT2 = argsT2.get(arg);
/* 556 */         if ((isEquivalentType(processingEnv, argT1, argT2)).type != Equivalency.EQUIVALENT) {
/* 557 */           return EquivalencyResult.boundsMismatch("Generic bounds mismatch between %s and %s", new Object[] {
/* 558 */                 describeGenericBound(argT1), describeGenericBound(argT2)
/*     */               });
/*     */         }
/*     */       } 
/* 562 */       return EquivalencyResult.EQUIVALENT;
/*     */     } 
/*     */     
/* 565 */     return EquivalencyResult.notEquivalent("%s and %s do not match", new Object[] { t1, t2 });
/*     */   }
/*     */   
/*     */   private static TypeMirror toRawType(ProcessingEnvironment processingEnv, DeclaredType targetType) {
/* 569 */     if (targetType.getKind() == TypeKind.INTERSECTION) {
/* 570 */       return targetType;
/*     */     }
/* 572 */     Name qualifiedName = ((TypeElement)targetType.asElement()).getQualifiedName();
/* 573 */     TypeElement typeElement = processingEnv.getElementUtils().getTypeElement(qualifiedName);
/* 574 */     return (typeElement != null) ? typeElement.asType() : targetType;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static Visibility getVisibility(Element element) {
/* 584 */     if (element == null) {
/* 585 */       return null;
/*     */     }
/*     */     
/* 588 */     for (Modifier modifier : element.getModifiers()) {
/* 589 */       switch (modifier) { case PUBLIC:
/* 590 */           return Visibility.PUBLIC;
/* 591 */         case PROTECTED: return Visibility.PROTECTED;
/* 592 */         case PRIVATE: return Visibility.PRIVATE; }
/*     */ 
/*     */ 
/*     */     
/*     */     } 
/* 597 */     return Visibility.PACKAGE;
/*     */   }
/*     */ }


/* Location:              C:\Users\DuduM\OneDrive\Área de Trabalho\mw10\modularwarfare-shining-2023.2.4.4f-fix10.jar!\org\spongepowered\tools\obfuscation\mirror\TypeUtils.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */