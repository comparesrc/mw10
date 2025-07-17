/*     */ package org.spongepowered.asm.util;
/*     */ 
/*     */ import com.google.common.base.Function;
/*     */ import com.google.common.base.Preconditions;
/*     */ import com.google.common.collect.Lists;
/*     */ import java.lang.annotation.Annotation;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collections;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import java.util.ListIterator;
/*     */ import java.util.regex.Pattern;
/*     */ import org.apache.logging.log4j.LogManager;
/*     */ import org.apache.logging.log4j.Logger;
/*     */ import org.objectweb.asm.Type;
/*     */ import org.objectweb.asm.tree.AnnotationNode;
/*     */ import org.objectweb.asm.tree.ClassNode;
/*     */ import org.objectweb.asm.tree.FieldNode;
/*     */ import org.objectweb.asm.tree.MethodNode;
/*     */ import org.spongepowered.asm.mixin.Debug;
/*     */ import org.spongepowered.asm.mixin.Final;
/*     */ import org.spongepowered.asm.mixin.Intrinsic;
/*     */ import org.spongepowered.asm.mixin.Overwrite;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class Annotations
/*     */ {
/*  59 */   private static final Class<?>[] MERGEABLE_MIXIN_ANNOTATIONS = new Class[] { Overwrite.class, Intrinsic.class, Final.class, Debug.class };
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  66 */   private static Pattern mergeableAnnotationPattern = getMergeableAnnotationPattern();
/*     */   
/*  68 */   private static final Logger logger = LogManager.getLogger("mixin");
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void setVisible(FieldNode field, Class<? extends Annotation> annotationClass, Object... value) {
/*  83 */     AnnotationNode node = createNode(Type.getDescriptor(annotationClass), value);
/*  84 */     field.visibleAnnotations = add(field.visibleAnnotations, node);
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
/*     */   public static void setInvisible(FieldNode field, Class<? extends Annotation> annotationClass, Object... value) {
/*  96 */     AnnotationNode node = createNode(Type.getDescriptor(annotationClass), value);
/*  97 */     field.invisibleAnnotations = add(field.invisibleAnnotations, node);
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
/*     */   public static void setVisible(MethodNode method, Class<? extends Annotation> annotationClass, Object... value) {
/* 109 */     AnnotationNode node = createNode(Type.getDescriptor(annotationClass), value);
/* 110 */     method.visibleAnnotations = add(method.visibleAnnotations, node);
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
/*     */   public static void setInvisible(MethodNode method, Class<? extends Annotation> annotationClass, Object... value) {
/* 122 */     AnnotationNode node = createNode(Type.getDescriptor(annotationClass), value);
/* 123 */     method.invisibleAnnotations = add(method.invisibleAnnotations, node);
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
/*     */   private static AnnotationNode createNode(String annotationType, Object... value) {
/* 135 */     AnnotationNode node = new AnnotationNode(annotationType);
/* 136 */     for (int pos = 0; pos < value.length - 1; pos += 2) {
/* 137 */       if (!(value[pos] instanceof String)) {
/* 138 */         throw new IllegalArgumentException("Annotation keys must be strings, found " + value[pos].getClass().getSimpleName() + " with " + value[pos]
/* 139 */             .toString() + " at index " + pos + " creating " + annotationType);
/*     */       }
/* 141 */       node.visit((String)value[pos], value[pos + 1]);
/*     */     } 
/* 143 */     return node;
/*     */   }
/*     */   
/*     */   private static List<AnnotationNode> add(List<AnnotationNode> annotations, AnnotationNode node) {
/* 147 */     if (annotations == null) {
/* 148 */       annotations = new ArrayList<>(1);
/*     */     } else {
/* 150 */       annotations.remove(get(annotations, node.desc));
/*     */     } 
/* 152 */     annotations.add(node);
/* 153 */     return annotations;
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
/*     */   public static AnnotationNode getVisible(FieldNode field, Class<? extends Annotation> annotationClass) {
/* 165 */     return get(field.visibleAnnotations, Type.getDescriptor(annotationClass));
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
/*     */   public static AnnotationNode getInvisible(FieldNode field, Class<? extends Annotation> annotationClass) {
/* 177 */     return get(field.invisibleAnnotations, Type.getDescriptor(annotationClass));
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
/*     */   public static AnnotationNode getVisible(MethodNode method, Class<? extends Annotation> annotationClass) {
/* 189 */     return get(method.visibleAnnotations, Type.getDescriptor(annotationClass));
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
/*     */   public static AnnotationNode getInvisible(MethodNode method, Class<? extends Annotation> annotationClass) {
/* 201 */     return get(method.invisibleAnnotations, Type.getDescriptor(annotationClass));
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
/*     */   public static AnnotationNode getSingleVisible(MethodNode method, Class<? extends Annotation>... annotationClasses) {
/* 214 */     return getSingle(method.visibleAnnotations, annotationClasses);
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
/*     */   public static AnnotationNode getSingleInvisible(MethodNode method, Class<? extends Annotation>... annotationClasses) {
/* 227 */     return getSingle(method.invisibleAnnotations, annotationClasses);
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
/*     */   public static AnnotationNode getVisible(ClassNode classNode, Class<? extends Annotation> annotationClass) {
/* 239 */     return get(classNode.visibleAnnotations, Type.getDescriptor(annotationClass));
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
/*     */   public static AnnotationNode getInvisible(ClassNode classNode, Class<? extends Annotation> annotationClass) {
/* 251 */     return get(classNode.invisibleAnnotations, Type.getDescriptor(annotationClass));
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
/*     */   public static AnnotationNode getVisibleParameter(MethodNode method, Class<? extends Annotation> annotationClass, int paramIndex) {
/* 265 */     if (paramIndex < 0) {
/* 266 */       return getVisible(method, annotationClass);
/*     */     }
/* 268 */     return getParameter((List<AnnotationNode>[])method.visibleParameterAnnotations, Type.getDescriptor(annotationClass), paramIndex);
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
/*     */   public static AnnotationNode getInvisibleParameter(MethodNode method, Class<? extends Annotation> annotationClass, int paramIndex) {
/* 282 */     if (paramIndex < 0) {
/* 283 */       return getInvisible(method, annotationClass);
/*     */     }
/* 285 */     return getParameter((List<AnnotationNode>[])method.invisibleParameterAnnotations, Type.getDescriptor(annotationClass), paramIndex);
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
/*     */   public static AnnotationNode getParameter(List<AnnotationNode>[] parameterAnnotations, String annotationType, int paramIndex) {
/* 298 */     if (parameterAnnotations == null || paramIndex < 0 || paramIndex >= parameterAnnotations.length) {
/* 299 */       return null;
/*     */     }
/*     */     
/* 302 */     return get(parameterAnnotations[paramIndex], annotationType);
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
/*     */   public static AnnotationNode get(List<AnnotationNode> annotations, String annotationType) {
/* 315 */     if (annotations == null) {
/* 316 */       return null;
/*     */     }
/*     */     
/* 319 */     for (AnnotationNode annotation : annotations) {
/* 320 */       if (annotationType.equals(annotation.desc)) {
/* 321 */         return annotation;
/*     */       }
/*     */     } 
/*     */     
/* 325 */     return null;
/*     */   }
/*     */   
/*     */   private static AnnotationNode getSingle(List<AnnotationNode> annotations, Class<? extends Annotation>[] annotationClasses) {
/* 329 */     List<AnnotationNode> nodes = new ArrayList<>();
/* 330 */     for (Class<? extends Annotation> annotationClass : annotationClasses) {
/* 331 */       AnnotationNode annotation = get(annotations, Type.getDescriptor(annotationClass));
/* 332 */       if (annotation != null) {
/* 333 */         nodes.add(annotation);
/*     */       }
/*     */     } 
/*     */     
/* 337 */     int foundNodes = nodes.size();
/* 338 */     if (foundNodes > 1) {
/* 339 */       throw new IllegalArgumentException("Conflicting annotations found: " + Lists.transform(nodes, new Function<AnnotationNode, String>() {
/*     */               public String apply(AnnotationNode input) {
/* 341 */                 return input.desc;
/*     */               }
/*     */             }));
/*     */     }
/*     */     
/* 346 */     return (foundNodes == 0) ? null : nodes.get(0);
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
/*     */   public static <T> T getValue(AnnotationNode annotation) {
/* 358 */     return getValue(annotation, "value");
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
/*     */   public static <T> T getValue(AnnotationNode annotation, String key, T defaultValue) {
/* 374 */     T returnValue = getValue(annotation, key);
/* 375 */     return (returnValue != null) ? returnValue : defaultValue;
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
/*     */   public static <T> T getValue(AnnotationNode annotation, String key, Class<?> annotationClass) {
/* 392 */     Preconditions.checkNotNull(annotationClass, "annotationClass cannot be null");
/* 393 */     T value = getValue(annotation, key);
/* 394 */     if (value == null) {
/*     */       try {
/* 396 */         value = (T)annotationClass.getDeclaredMethod(key, new Class[0]).getDefaultValue();
/* 397 */       } catch (NoSuchMethodException noSuchMethodException) {}
/*     */     }
/*     */ 
/*     */     
/* 401 */     return value;
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
/*     */   public static <T> T getValue(AnnotationNode annotation, String key) {
/* 416 */     boolean getNextValue = false;
/*     */     
/* 418 */     if (annotation == null || annotation.values == null) {
/* 419 */       return null;
/*     */     }
/*     */ 
/*     */     
/* 423 */     for (Object value : annotation.values) {
/* 424 */       if (getNextValue) {
/* 425 */         return (T)value;
/*     */       }
/* 427 */       if (value.equals(key)) {
/* 428 */         getNextValue = true;
/*     */       }
/*     */     } 
/*     */     
/* 432 */     return null;
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
/*     */   public static <T extends Enum<T>> T getValue(AnnotationNode annotation, String key, Class<T> enumClass, T defaultValue) {
/* 447 */     String[] value = getValue(annotation, key);
/* 448 */     if (value == null) {
/* 449 */       return defaultValue;
/*     */     }
/* 451 */     return toEnumValue(enumClass, value);
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
/*     */   public static <T> List<T> getValue(AnnotationNode annotation, String key, boolean notNull) {
/* 465 */     Object value = getValue(annotation, key);
/* 466 */     if (value instanceof List)
/* 467 */       return (List<T>)value; 
/* 468 */     if (value != null) {
/* 469 */       List<T> list = new ArrayList<>();
/* 470 */       list.add((T)value);
/* 471 */       return list;
/*     */     } 
/* 473 */     return Collections.emptyList();
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
/*     */   public static <T extends Enum<T>> List<T> getValue(AnnotationNode annotation, String key, boolean notNull, Class<T> enumClass) {
/* 488 */     Object value = getValue(annotation, key);
/* 489 */     if (value instanceof List) {
/* 490 */       for (ListIterator<Object> iter = ((List<Object>)value).listIterator(); iter.hasNext();) {
/* 491 */         iter.set(toEnumValue(enumClass, (String[])iter.next()));
/*     */       }
/* 493 */       return (List<T>)value;
/* 494 */     }  if (value instanceof String[]) {
/* 495 */       List<T> list = new ArrayList<>();
/* 496 */       list.add(toEnumValue(enumClass, (String[])value));
/* 497 */       return list;
/*     */     } 
/* 499 */     return Collections.emptyList();
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
/*     */   public static void setValue(AnnotationNode annotation, String key, Object value) {
/* 512 */     if (annotation == null) {
/*     */       return;
/*     */     }
/*     */     
/* 516 */     int existingIndex = 0;
/* 517 */     if (annotation.values != null) {
/* 518 */       for (int pos = 0; pos < annotation.values.size() - 1; pos += 2) {
/* 519 */         String keyName = annotation.values.get(pos).toString();
/* 520 */         if (key.equals(keyName)) {
/* 521 */           existingIndex = pos + 1;
/*     */           break;
/*     */         } 
/*     */       } 
/*     */     } else {
/* 526 */       annotation.values = new ArrayList();
/*     */     } 
/*     */     
/* 529 */     if (existingIndex > 0) {
/* 530 */       annotation.values.set(existingIndex, packValue(value));
/*     */       
/*     */       return;
/*     */     } 
/* 534 */     annotation.values.add(key);
/* 535 */     annotation.values.add(packValue(value));
/*     */   }
/*     */   
/*     */   private static Object packValue(Object value) {
/* 539 */     Class<? extends Object> type = (Class)value.getClass();
/* 540 */     if (type.isEnum()) {
/* 541 */       return new String[] { Type.getDescriptor(type), value.toString() };
/*     */     }
/* 543 */     return value;
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
/*     */   public static void merge(ClassNode from, ClassNode to) {
/* 558 */     to.visibleAnnotations = merge(from.visibleAnnotations, to.visibleAnnotations, "class", from.name);
/* 559 */     to.invisibleAnnotations = merge(from.invisibleAnnotations, to.invisibleAnnotations, "class", from.name);
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
/*     */   public static void merge(MethodNode from, MethodNode to) {
/* 574 */     to.visibleAnnotations = merge(from.visibleAnnotations, to.visibleAnnotations, "method", from.name);
/* 575 */     to.invisibleAnnotations = merge(from.invisibleAnnotations, to.invisibleAnnotations, "method", from.name);
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
/*     */   public static void merge(FieldNode from, FieldNode to) {
/* 590 */     to.visibleAnnotations = merge(from.visibleAnnotations, to.visibleAnnotations, "field", from.name);
/* 591 */     to.invisibleAnnotations = merge(from.invisibleAnnotations, to.invisibleAnnotations, "field", from.name);
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
/*     */   private static List<AnnotationNode> merge(List<AnnotationNode> from, List<AnnotationNode> to, String type, String name) {
/*     */     try {
/* 606 */       if (from == null) {
/* 607 */         return to;
/*     */       }
/*     */       
/* 610 */       if (to == null) {
/* 611 */         to = new ArrayList<>();
/*     */       }
/*     */       
/* 614 */       for (AnnotationNode annotation : from) {
/* 615 */         if (!isMergeableAnnotation(annotation)) {
/*     */           continue;
/*     */         }
/*     */         
/* 619 */         for (Iterator<AnnotationNode> iter = to.iterator(); iter.hasNext();) {
/* 620 */           if (((AnnotationNode)iter.next()).desc.equals(annotation.desc)) {
/* 621 */             iter.remove();
/*     */             
/*     */             break;
/*     */           } 
/*     */         } 
/* 626 */         to.add(annotation);
/*     */       } 
/* 628 */     } catch (Exception ex) {
/* 629 */       logger.warn("Exception encountered whilst merging annotations for {} {}", new Object[] { type, name });
/*     */     } 
/*     */     
/* 632 */     return to;
/*     */   }
/*     */   
/*     */   private static boolean isMergeableAnnotation(AnnotationNode annotation) {
/* 636 */     if (annotation.desc.startsWith("L" + Constants.MIXIN_PACKAGE_REF)) {
/* 637 */       return mergeableAnnotationPattern.matcher(annotation.desc).matches();
/*     */     }
/* 639 */     return true;
/*     */   }
/*     */   
/*     */   private static Pattern getMergeableAnnotationPattern() {
/* 643 */     StringBuilder sb = new StringBuilder("^L(");
/* 644 */     for (int i = 0; i < MERGEABLE_MIXIN_ANNOTATIONS.length; i++) {
/* 645 */       if (i > 0) {
/* 646 */         sb.append('|');
/*     */       }
/* 648 */       sb.append(MERGEABLE_MIXIN_ANNOTATIONS[i].getName().replace('.', '/'));
/*     */     } 
/* 650 */     return Pattern.compile(sb.append(");$").toString());
/*     */   }
/*     */   
/*     */   private static <T extends Enum<T>> T toEnumValue(Class<T> enumClass, String[] value) {
/* 654 */     if (!enumClass.getName().equals(Type.getType(value[0]).getClassName())) {
/* 655 */       throw new IllegalArgumentException("The supplied enum class does not match the stored enum value");
/*     */     }
/* 657 */     return Enum.valueOf(enumClass, value[1]);
/*     */   }
/*     */ }


/* Location:              C:\Users\DuduM\OneDrive\Área de Trabalho\mw10\modularwarfare-shining-2023.2.4.4f-fix10.jar!\org\spongepowered\as\\util\Annotations.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */