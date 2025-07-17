/*     */ package org.spongepowered.asm.mixin.transformer;
/*     */ 
/*     */ import com.google.common.base.Strings;
/*     */ import java.lang.annotation.Annotation;
/*     */ import java.util.Iterator;
/*     */ import org.apache.logging.log4j.LogManager;
/*     */ import org.apache.logging.log4j.Logger;
/*     */ import org.objectweb.asm.Type;
/*     */ import org.objectweb.asm.tree.AbstractInsnNode;
/*     */ import org.objectweb.asm.tree.AnnotationNode;
/*     */ import org.objectweb.asm.tree.FieldInsnNode;
/*     */ import org.objectweb.asm.tree.FieldNode;
/*     */ import org.objectweb.asm.tree.MethodInsnNode;
/*     */ import org.objectweb.asm.tree.MethodNode;
/*     */ import org.spongepowered.asm.mixin.Dynamic;
/*     */ import org.spongepowered.asm.mixin.MixinEnvironment;
/*     */ import org.spongepowered.asm.mixin.Overwrite;
/*     */ import org.spongepowered.asm.mixin.Shadow;
/*     */ import org.spongepowered.asm.mixin.Unique;
/*     */ import org.spongepowered.asm.mixin.gen.Accessor;
/*     */ import org.spongepowered.asm.mixin.gen.Invoker;
/*     */ import org.spongepowered.asm.mixin.gen.throwables.InvalidAccessorException;
/*     */ import org.spongepowered.asm.mixin.throwables.ClassMetadataNotFoundException;
/*     */ import org.spongepowered.asm.mixin.throwables.MixinException;
/*     */ import org.spongepowered.asm.mixin.transformer.meta.MixinRenamed;
/*     */ import org.spongepowered.asm.mixin.transformer.throwables.InvalidMixinException;
/*     */ import org.spongepowered.asm.mixin.transformer.throwables.MixinPreProcessorException;
/*     */ import org.spongepowered.asm.util.Annotations;
/*     */ import org.spongepowered.asm.util.Bytecode;
/*     */ import org.spongepowered.asm.util.perf.Profiler;
/*     */ import org.spongepowered.asm.util.throwables.SyntheticBridgeException;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ class MixinPreProcessorStandard
/*     */ {
/*     */   enum SpecialMethod
/*     */   {
/* 100 */     MERGE(true),
/* 101 */     OVERWRITE(true, Overwrite.class),
/* 102 */     SHADOW(false, Shadow.class),
/* 103 */     ACCESSOR(false, Accessor.class),
/* 104 */     INVOKER(false, Invoker.class);
/*     */     
/*     */     final boolean isOverwrite;
/*     */     
/*     */     final Class<? extends Annotation> annotation;
/*     */     
/*     */     final String description;
/*     */     
/*     */     SpecialMethod(boolean isOverwrite, Class<? extends Annotation> type) {
/* 113 */       this.isOverwrite = isOverwrite;
/* 114 */       this.annotation = type;
/* 115 */       this.description = "@" + Bytecode.getSimpleName(type);
/*     */     }
/*     */     
/*     */     SpecialMethod(boolean isOverwrite) {
/* 119 */       this.isOverwrite = isOverwrite;
/* 120 */       this.annotation = null;
/* 121 */       this.description = "overwrite";
/*     */     }
/*     */ 
/*     */     
/*     */     public String toString() {
/* 126 */       return this.description;
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 134 */   private static final Logger logger = LogManager.getLogger("mixin");
/*     */ 
/*     */ 
/*     */   
/*     */   protected final MixinInfo mixin;
/*     */ 
/*     */ 
/*     */   
/*     */   protected final MixinInfo.MixinClassNode classNode;
/*     */ 
/*     */   
/*     */   protected final MixinEnvironment env;
/*     */ 
/*     */   
/* 148 */   protected final Profiler profiler = MixinEnvironment.getProfiler();
/*     */   
/* 150 */   protected final ActivityStack activities = new ActivityStack();
/*     */   private final boolean verboseLogging;
/*     */   private final boolean strictUnique;
/*     */   private boolean prepared;
/*     */   private boolean attached;
/*     */   
/*     */   MixinPreProcessorStandard(MixinInfo mixin, MixinInfo.MixinClassNode classNode) {
/* 157 */     this.mixin = mixin;
/* 158 */     this.classNode = classNode;
/* 159 */     this.env = mixin.getParent().getEnvironment();
/* 160 */     this.verboseLogging = this.env.getOption(MixinEnvironment.Option.DEBUG_VERBOSE);
/* 161 */     this.strictUnique = this.env.getOption(MixinEnvironment.Option.DEBUG_UNIQUE);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   final MixinPreProcessorStandard prepare() {
/* 170 */     if (this.prepared) {
/* 171 */       return this;
/*     */     }
/*     */     
/* 174 */     this.prepared = true;
/*     */     
/* 176 */     this.activities.clear();
/* 177 */     Profiler.Section prepareTimer = this.profiler.begin("prepare");
/*     */     
/*     */     try {
/* 180 */       ActivityStack.Activity activity = this.activities.begin("Prepare method");
/* 181 */       for (MixinInfo.MixinMethodNode mixinMethod : this.classNode.mixinMethods) {
/* 182 */         ClassInfo.Method method = this.mixin.getClassInfo().findMethod((MethodNode)mixinMethod);
/* 183 */         ActivityStack.Activity methodActivity = this.activities.begin(mixinMethod.toString());
/* 184 */         prepareMethod(mixinMethod, method);
/* 185 */         methodActivity.end();
/*     */       } 
/* 187 */       activity.next("Prepare field");
/* 188 */       for (FieldNode mixinField : this.classNode.fields) {
/* 189 */         ActivityStack.Activity fieldActivity = this.activities.begin(String.format("%s:%s", new Object[] { mixinField.name, mixinField.desc }));
/* 190 */         prepareField(mixinField);
/* 191 */         fieldActivity.end();
/*     */       } 
/* 193 */       activity.end();
/* 194 */     } catch (MixinException ex) {
/* 195 */       throw ex;
/* 196 */     } catch (Exception ex) {
/* 197 */       throw new MixinPreProcessorException(String.format("Prepare error for %s during activity:", new Object[] { this.mixin }), ex, this.activities);
/*     */     } 
/* 199 */     prepareTimer.end();
/* 200 */     return this;
/*     */   }
/*     */   
/*     */   protected void prepareMethod(MixinInfo.MixinMethodNode mixinMethod, ClassInfo.Method method) {
/* 204 */     prepareShadow(mixinMethod, method);
/* 205 */     prepareSoftImplements(mixinMethod, method);
/*     */   }
/*     */   
/*     */   protected void prepareShadow(MixinInfo.MixinMethodNode mixinMethod, ClassInfo.Method method) {
/* 209 */     AnnotationNode shadowAnnotation = Annotations.getVisible((MethodNode)mixinMethod, Shadow.class);
/* 210 */     if (shadowAnnotation == null) {
/*     */       return;
/*     */     }
/*     */     
/* 214 */     String prefix = (String)Annotations.getValue(shadowAnnotation, "prefix", Shadow.class);
/* 215 */     if (mixinMethod.name.startsWith(prefix)) {
/* 216 */       Annotations.setVisible((MethodNode)mixinMethod, MixinRenamed.class, new Object[] { "originalName", mixinMethod.name });
/* 217 */       String newName = mixinMethod.name.substring(prefix.length());
/* 218 */       mixinMethod.name = method.renameTo(newName);
/*     */     } 
/*     */   }
/*     */   
/*     */   protected void prepareSoftImplements(MixinInfo.MixinMethodNode mixinMethod, ClassInfo.Method method) {
/* 223 */     for (InterfaceInfo iface : this.mixin.getSoftImplements()) {
/* 224 */       if (iface.renameMethod((MethodNode)mixinMethod)) {
/* 225 */         method.renameTo(mixinMethod.name);
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   protected void prepareField(FieldNode mixinField) {}
/*     */ 
/*     */   
/*     */   final MixinPreProcessorStandard conform(TargetClassContext target) {
/* 235 */     return conform(target.getClassInfo());
/*     */   }
/*     */   
/*     */   final MixinPreProcessorStandard conform(ClassInfo target) {
/* 239 */     this.activities.clear();
/* 240 */     Profiler.Section conformTimer = this.profiler.begin("conform");
/*     */     try {
/* 242 */       for (MixinInfo.MixinMethodNode mixinMethod : this.classNode.mixinMethods) {
/* 243 */         if (mixinMethod.isInjector()) {
/* 244 */           ClassInfo.Method method = this.mixin.getClassInfo().findMethod((MethodNode)mixinMethod, 10);
/* 245 */           ActivityStack.Activity methodActivity = this.activities.begin("Conform injector %s", new Object[] { mixinMethod });
/* 246 */           conformInjector(target, mixinMethod, method);
/* 247 */           methodActivity.end();
/*     */         } 
/*     */       } 
/* 250 */     } catch (MixinException ex) {
/* 251 */       throw ex;
/* 252 */     } catch (Exception ex) {
/* 253 */       throw new MixinPreProcessorException(String.format("Conform error for %s during activity:", new Object[] { this.mixin }), ex, this.activities);
/*     */     } 
/* 255 */     conformTimer.end();
/* 256 */     return this;
/*     */   }
/*     */   
/*     */   private void conformInjector(ClassInfo targetClass, MixinInfo.MixinMethodNode mixinMethod, ClassInfo.Method method) {
/* 260 */     MethodMapper methodMapper = targetClass.getMethodMapper();
/* 261 */     methodMapper.remapHandlerMethod(this.mixin, (MethodNode)mixinMethod, method);
/*     */   }
/*     */   
/*     */   MixinTargetContext createContextFor(TargetClassContext target) {
/* 265 */     MixinTargetContext context = new MixinTargetContext(this.mixin, this.classNode, target);
/* 266 */     conform(target);
/* 267 */     attach(context);
/* 268 */     return context;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   final MixinPreProcessorStandard attach(MixinTargetContext context) {
/* 277 */     if (this.attached) {
/* 278 */       throw new IllegalStateException("Preprocessor was already attached");
/*     */     }
/*     */     
/* 281 */     this.attached = true;
/*     */     
/* 283 */     this.activities.clear();
/* 284 */     Profiler.Section attachTimer = this.profiler.begin("attach");
/*     */     
/*     */     try {
/* 287 */       Profiler.Section timer = this.profiler.begin("methods");
/* 288 */       ActivityStack.Activity activity = this.activities.begin("Attach method");
/* 289 */       attachMethods(context);
/* 290 */       timer = timer.next("fields");
/* 291 */       activity.next("Attach field");
/* 292 */       attachFields(context);
/*     */ 
/*     */       
/* 295 */       timer = timer.next("transform");
/* 296 */       activity.next("Transform");
/* 297 */       transform(context);
/* 298 */       activity.end();
/* 299 */       timer.end();
/* 300 */     } catch (MixinException ex) {
/* 301 */       throw ex;
/* 302 */     } catch (Exception ex) {
/* 303 */       throw new MixinPreProcessorException(String.format("Attach error for %s during activity:", new Object[] { this.mixin }), ex, this.activities);
/*     */     } 
/* 305 */     attachTimer.end();
/* 306 */     return this;
/*     */   }
/*     */   
/*     */   protected void attachMethods(MixinTargetContext context) {
/* 310 */     ActivityStack.Activity methodActivity = this.activities.begin("?");
/* 311 */     for (Iterator<MixinInfo.MixinMethodNode> iter = this.classNode.mixinMethods.iterator(); iter.hasNext(); ) {
/* 312 */       MixinInfo.MixinMethodNode mixinMethod = iter.next();
/* 313 */       methodActivity.next(mixinMethod.toString());
/*     */       
/* 315 */       if (!validateMethod(context, mixinMethod)) {
/* 316 */         iter.remove();
/*     */         
/*     */         continue;
/*     */       } 
/* 320 */       if (attachInjectorMethod(context, mixinMethod)) {
/* 321 */         context.addMixinMethod((MethodNode)mixinMethod);
/*     */         
/*     */         continue;
/*     */       } 
/* 325 */       if (attachAccessorMethod(context, mixinMethod)) {
/* 326 */         iter.remove();
/*     */         
/*     */         continue;
/*     */       } 
/* 330 */       if (attachShadowMethod(context, mixinMethod)) {
/* 331 */         context.addShadowMethod((MethodNode)mixinMethod);
/* 332 */         iter.remove();
/*     */         
/*     */         continue;
/*     */       } 
/* 336 */       if (attachOverwriteMethod(context, mixinMethod)) {
/* 337 */         context.addMixinMethod((MethodNode)mixinMethod);
/*     */         
/*     */         continue;
/*     */       } 
/* 341 */       if (attachUniqueMethod(context, mixinMethod)) {
/* 342 */         iter.remove();
/*     */         
/*     */         continue;
/*     */       } 
/* 346 */       attachMethod(context, mixinMethod);
/* 347 */       context.addMixinMethod((MethodNode)mixinMethod);
/*     */     } 
/* 349 */     methodActivity.end();
/*     */   }
/*     */   
/*     */   protected boolean validateMethod(MixinTargetContext context, MixinInfo.MixinMethodNode mixinMethod) {
/* 353 */     return true;
/*     */   }
/*     */   
/*     */   protected boolean attachInjectorMethod(MixinTargetContext context, MixinInfo.MixinMethodNode mixinMethod) {
/* 357 */     return mixinMethod.isInjector();
/*     */   }
/*     */   
/*     */   protected boolean attachAccessorMethod(MixinTargetContext context, MixinInfo.MixinMethodNode mixinMethod) {
/* 361 */     return (attachAccessorMethod(context, mixinMethod, SpecialMethod.ACCESSOR) || 
/* 362 */       attachAccessorMethod(context, mixinMethod, SpecialMethod.INVOKER));
/*     */   }
/*     */   
/*     */   protected boolean attachAccessorMethod(MixinTargetContext context, MixinInfo.MixinMethodNode mixinMethod, SpecialMethod type) {
/* 366 */     AnnotationNode annotation = mixinMethod.getVisibleAnnotation(type.annotation);
/* 367 */     if (annotation == null) {
/* 368 */       return false;
/*     */     }
/*     */     
/* 371 */     String description = type + " method " + mixinMethod.name;
/* 372 */     ClassInfo.Method method = getSpecialMethod(mixinMethod, type);
/* 373 */     if (MixinEnvironment.getCompatibilityLevel().isAtLeast(MixinEnvironment.CompatibilityLevel.JAVA_8) && method.isStatic()) {
/* 374 */       if (this.mixin.getTargets().size() > 1) {
/* 375 */         throw new InvalidAccessorException(context, description + " in multi-target mixin is invalid. Mixin must have exactly 1 target.");
/*     */       }
/*     */       
/* 378 */       if (method.isConformed()) {
/* 379 */         mixinMethod.name = method.getName();
/*     */       } else {
/* 381 */         String uniqueName = context.getUniqueName((MethodNode)mixinMethod, true);
/* 382 */         logger.log(this.mixin.getLoggingLevel(), "Renaming @{} method {}{} to {} in {}", new Object[] {
/* 383 */               Bytecode.getSimpleName(annotation), mixinMethod.name, mixinMethod.desc, uniqueName, this.mixin });
/* 384 */         mixinMethod.name = method.conform(uniqueName);
/*     */       } 
/*     */     } else {
/*     */       
/* 388 */       if (!method.isAbstract()) {
/* 389 */         throw new InvalidAccessorException(context, description + " is not abstract");
/*     */       }
/*     */       
/* 392 */       if (method.isStatic()) {
/* 393 */         throw new InvalidAccessorException(context, description + " cannot be static");
/*     */       }
/*     */     } 
/*     */     
/* 397 */     context.addAccessorMethod((MethodNode)mixinMethod, type.annotation);
/* 398 */     return true;
/*     */   }
/*     */   
/*     */   protected boolean attachShadowMethod(MixinTargetContext context, MixinInfo.MixinMethodNode mixinMethod) {
/* 402 */     return attachSpecialMethod(context, mixinMethod, SpecialMethod.SHADOW);
/*     */   }
/*     */   
/*     */   protected boolean attachOverwriteMethod(MixinTargetContext context, MixinInfo.MixinMethodNode mixinMethod) {
/* 406 */     return attachSpecialMethod(context, mixinMethod, SpecialMethod.OVERWRITE);
/*     */   }
/*     */ 
/*     */   
/*     */   protected boolean attachSpecialMethod(MixinTargetContext context, MixinInfo.MixinMethodNode mixinMethod, SpecialMethod type) {
/* 411 */     AnnotationNode annotation = mixinMethod.getVisibleAnnotation(type.annotation);
/* 412 */     if (annotation == null) {
/* 413 */       return false;
/*     */     }
/*     */     
/* 416 */     if (type.isOverwrite) {
/* 417 */       checkMixinNotUnique(mixinMethod, type);
/*     */     }
/*     */     
/* 420 */     ClassInfo.Method method = getSpecialMethod(mixinMethod, type);
/* 421 */     MethodNode target = context.findMethod((MethodNode)mixinMethod, annotation);
/* 422 */     if (target == null) {
/* 423 */       if (type.isOverwrite) {
/* 424 */         return false;
/*     */       }
/* 426 */       target = context.findRemappedMethod((MethodNode)mixinMethod);
/* 427 */       if (target == null)
/* 428 */         throw new InvalidMixinException(this.mixin, 
/* 429 */             String.format("%s method %s in %s was not located in the target class %s. %s%s", new Object[] {
/* 430 */                 type, mixinMethod.name, this.mixin, context.getTarget(), context.getReferenceMapper().getStatus(), 
/* 431 */                 getDynamicInfo(mixinMethod)
/*     */               })); 
/* 433 */       mixinMethod.name = method.renameTo(target.name);
/*     */     } 
/*     */     
/* 436 */     if ("<init>".equals(target.name)) {
/* 437 */       throw new InvalidMixinException(this.mixin, String.format("Nice try! %s in %s cannot alias a constructor", new Object[] { mixinMethod.name, this.mixin }));
/*     */     }
/*     */     
/* 440 */     if (!Bytecode.compareFlags((MethodNode)mixinMethod, target, 8)) {
/* 441 */       throw new InvalidMixinException(this.mixin, String.format("STATIC modifier of %s method %s in %s does not match the target", new Object[] { type, mixinMethod.name, this.mixin }));
/*     */     }
/*     */ 
/*     */     
/* 445 */     conformVisibility(context, mixinMethod, type, target);
/*     */     
/* 447 */     if (!target.name.equals(mixinMethod.name)) {
/* 448 */       if (type.isOverwrite && (target.access & 0x2) == 0) {
/* 449 */         throw new InvalidMixinException(this.mixin, "Non-private method cannot be aliased. Found " + target.name);
/*     */       }
/*     */       
/* 452 */       mixinMethod.name = method.renameTo(target.name);
/*     */     } 
/*     */     
/* 455 */     return true;
/*     */   }
/*     */   
/*     */   private void conformVisibility(MixinTargetContext context, MixinInfo.MixinMethodNode mixinMethod, SpecialMethod type, MethodNode target) {
/* 459 */     Bytecode.Visibility visTarget = Bytecode.getVisibility(target);
/* 460 */     Bytecode.Visibility visMethod = Bytecode.getVisibility((MethodNode)mixinMethod);
/* 461 */     if (visMethod.ordinal() >= visTarget.ordinal()) {
/* 462 */       if (visTarget == Bytecode.Visibility.PRIVATE && visMethod.ordinal() > Bytecode.Visibility.PRIVATE.ordinal()) {
/* 463 */         context.getTarget().addUpgradedMethod(target);
/*     */       }
/*     */       
/*     */       return;
/*     */     } 
/* 468 */     String message = String.format("%s %s method %s in %s cannot reduce visibiliy of %s target method", new Object[] { visMethod, type, mixinMethod.name, this.mixin, visTarget });
/*     */ 
/*     */     
/* 471 */     if (type.isOverwrite && !this.mixin.getParent().conformOverwriteVisibility()) {
/* 472 */       throw new InvalidMixinException(this.mixin, message);
/*     */     }
/*     */     
/* 475 */     if (visMethod == Bytecode.Visibility.PRIVATE) {
/* 476 */       if (type.isOverwrite) {
/* 477 */         logger.warn("Static binding violation: {}, visibility will be upgraded.", new Object[] { message });
/*     */       }
/* 479 */       context.addUpgradedMethod((MethodNode)mixinMethod);
/* 480 */       Bytecode.setVisibility((MethodNode)mixinMethod, visTarget);
/*     */     } 
/*     */   }
/*     */   
/*     */   protected ClassInfo.Method getSpecialMethod(MixinInfo.MixinMethodNode mixinMethod, SpecialMethod type) {
/* 485 */     ClassInfo.Method method = this.mixin.getClassInfo().findMethod((MethodNode)mixinMethod, 10);
/* 486 */     checkMethodNotUnique(method, type);
/* 487 */     return method;
/*     */   }
/*     */   
/*     */   protected void checkMethodNotUnique(ClassInfo.Method method, SpecialMethod type) {
/* 491 */     if (method.isUnique()) {
/* 492 */       throw new InvalidMixinException(this.mixin, String.format("%s method %s in %s cannot be @Unique", new Object[] { type, method.getName(), this.mixin }));
/*     */     }
/*     */   }
/*     */   
/*     */   protected void checkMixinNotUnique(MixinInfo.MixinMethodNode mixinMethod, SpecialMethod type) {
/* 497 */     if (this.mixin.isUnique()) {
/* 498 */       throw new InvalidMixinException(this.mixin, String.format("%s method %s found in a @Unique mixin %s", new Object[] { type, mixinMethod.name, this.mixin }));
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   protected boolean attachUniqueMethod(MixinTargetContext context, MixinInfo.MixinMethodNode mixinMethod) {
/* 504 */     ClassInfo.Method method = this.mixin.getClassInfo().findMethod((MethodNode)mixinMethod, 10);
/* 505 */     if (method == null || (!method.isUnique() && !this.mixin.isUnique() && !method.isSynthetic())) {
/* 506 */       return false;
/*     */     }
/*     */     
/* 509 */     boolean synthetic = method.isSynthetic();
/* 510 */     if (synthetic) {
/* 511 */       context.transformDescriptor((MethodNode)mixinMethod);
/* 512 */       method.remapTo(mixinMethod.desc);
/*     */     } 
/*     */     
/* 515 */     MethodNode target = context.findMethod((MethodNode)mixinMethod, (AnnotationNode)null);
/* 516 */     if (target == null && !synthetic) {
/* 517 */       return false;
/*     */     }
/*     */     
/* 520 */     String type = synthetic ? "synthetic" : "@Unique";
/*     */     
/* 522 */     if (Bytecode.getVisibility((MethodNode)mixinMethod).ordinal() < Bytecode.Visibility.PUBLIC.ordinal()) {
/* 523 */       if (method.isConformed()) {
/* 524 */         mixinMethod.name = method.getName();
/*     */       } else {
/* 526 */         String uniqueName = context.getUniqueName((MethodNode)mixinMethod, false);
/* 527 */         logger.log(this.mixin.getLoggingLevel(), "Renaming {} method {}{} to {} in {}", new Object[] { type, mixinMethod.name, mixinMethod.desc, uniqueName, this.mixin });
/*     */         
/* 529 */         mixinMethod.name = method.conform(uniqueName);
/*     */       } 
/* 531 */       return false;
/*     */     } 
/*     */     
/* 534 */     if (target == null) {
/* 535 */       return false;
/*     */     }
/*     */     
/* 538 */     if (this.strictUnique) {
/* 539 */       throw new InvalidMixinException(this.mixin, String.format("Method conflict, %s method %s in %s cannot overwrite %s%s in %s", new Object[] { type, mixinMethod.name, this.mixin, target.name, target.desc, context
/* 540 */               .getTarget() }));
/*     */     }
/*     */     
/* 543 */     AnnotationNode unique = Annotations.getVisible((MethodNode)mixinMethod, Unique.class);
/* 544 */     if (unique == null || !((Boolean)Annotations.getValue(unique, "silent", Boolean.FALSE)).booleanValue()) {
/* 545 */       if (Bytecode.hasFlag((MethodNode)mixinMethod, 64)) {
/*     */         
/*     */         try {
/* 548 */           Bytecode.compareBridgeMethods(target, (MethodNode)mixinMethod);
/* 549 */           logger.debug("Discarding sythetic bridge method {} in {} because existing method in {} is compatible", new Object[] { type, mixinMethod.name, this.mixin, context
/* 550 */                 .getTarget() });
/* 551 */           return true;
/* 552 */         } catch (SyntheticBridgeException ex) {
/* 553 */           if (this.verboseLogging || this.env.getOption(MixinEnvironment.Option.DEBUG_VERIFY))
/*     */           {
/* 555 */             ex.printAnalysis(context, target, (MethodNode)mixinMethod);
/*     */           }
/* 557 */           throw new InvalidMixinException(this.mixin, ex.getMessage());
/*     */         } 
/*     */       }
/*     */       
/* 561 */       logger.warn("Discarding {} public method {} in {} because it already exists in {}", new Object[] { type, mixinMethod.name, this.mixin, context
/* 562 */             .getTarget() });
/* 563 */       return true;
/*     */     } 
/*     */     
/* 566 */     context.addMixinMethod((MethodNode)mixinMethod);
/* 567 */     return true;
/*     */   }
/*     */   
/*     */   protected void attachMethod(MixinTargetContext context, MixinInfo.MixinMethodNode mixinMethod) {
/* 571 */     ClassInfo.Method method = this.mixin.getClassInfo().findMethod((MethodNode)mixinMethod);
/* 572 */     if (method == null) {
/*     */       return;
/*     */     }
/*     */     
/* 576 */     ClassInfo.Method parentMethod = this.mixin.getClassInfo().findMethodInHierarchy((MethodNode)mixinMethod, ClassInfo.SearchType.SUPER_CLASSES_ONLY);
/* 577 */     if (parentMethod != null && parentMethod.isRenamed()) {
/* 578 */       mixinMethod.name = method.renameTo(parentMethod.getName());
/*     */     }
/*     */     
/* 581 */     MethodNode target = context.findMethod((MethodNode)mixinMethod, (AnnotationNode)null);
/* 582 */     if (target != null) {
/* 583 */       conformVisibility(context, mixinMethod, SpecialMethod.MERGE, target);
/*     */     }
/*     */   }
/*     */   
/*     */   protected void attachFields(MixinTargetContext context) {
/* 588 */     ActivityStack.Activity fieldActivity = this.activities.begin("?");
/* 589 */     for (Iterator<FieldNode> iter = this.classNode.getFields().iterator(); iter.hasNext(); ) {
/* 590 */       FieldNode mixinField = iter.next();
/* 591 */       fieldActivity.next("%s:%s", new Object[] { mixinField.name, mixinField.desc });
/* 592 */       AnnotationNode shadow = Annotations.getVisible(mixinField, Shadow.class);
/* 593 */       boolean isShadow = (shadow != null);
/*     */       
/* 595 */       if (!validateField(context, mixinField, shadow)) {
/* 596 */         iter.remove();
/*     */         
/*     */         continue;
/*     */       } 
/* 600 */       ClassInfo.Field field = this.mixin.getClassInfo().findField(mixinField);
/* 601 */       context.transformDescriptor(mixinField);
/* 602 */       field.remapTo(mixinField.desc);
/*     */       
/* 604 */       if (field.isUnique() && isShadow) {
/* 605 */         throw new InvalidMixinException(this.mixin, String.format("@Shadow field %s cannot be @Unique", new Object[] { mixinField.name }));
/*     */       }
/*     */       
/* 608 */       FieldNode target = context.findField(mixinField, shadow);
/* 609 */       if (target == null) {
/* 610 */         if (shadow == null) {
/*     */           continue;
/*     */         }
/* 613 */         target = context.findRemappedField(mixinField);
/* 614 */         if (target == null)
/*     */         {
/* 616 */           throw new InvalidMixinException(this.mixin, String.format("@Shadow field %s was not located in the target class %s. %s%s", new Object[] { mixinField.name, context
/* 617 */                   .getTarget(), context.getReferenceMapper().getStatus(), 
/* 618 */                   getDynamicInfo(mixinField) }));
/*     */         }
/* 620 */         mixinField.name = field.renameTo(target.name);
/*     */       } 
/*     */       
/* 623 */       if (!Bytecode.compareFlags(mixinField, target, 8)) {
/* 624 */         throw new InvalidMixinException(this.mixin, String.format("STATIC modifier of @Shadow field %s in %s does not match the target", new Object[] { mixinField.name, this.mixin }));
/*     */       }
/*     */ 
/*     */       
/* 628 */       if (field.isUnique()) {
/* 629 */         if ((mixinField.access & 0x6) != 0) {
/* 630 */           String uniqueName = context.getUniqueName(mixinField);
/* 631 */           logger.log(this.mixin.getLoggingLevel(), "Renaming @Unique field {}{} to {} in {}", new Object[] { mixinField.name, mixinField.desc, uniqueName, this.mixin });
/*     */           
/* 633 */           mixinField.name = field.renameTo(uniqueName);
/*     */           
/*     */           continue;
/*     */         } 
/* 637 */         if (this.strictUnique) {
/* 638 */           throw new InvalidMixinException(this.mixin, String.format("Field conflict, @Unique field %s in %s cannot overwrite %s%s in %s", new Object[] { mixinField.name, this.mixin, target.name, target.desc, context
/* 639 */                   .getTarget() }));
/*     */         }
/*     */         
/* 642 */         logger.warn("Discarding @Unique public field {} in {} because it already exists in {}. Note that declared FIELD INITIALISERS will NOT be removed!", new Object[] { mixinField.name, this.mixin, context
/* 643 */               .getTarget() });
/*     */         
/* 645 */         iter.remove();
/*     */         
/*     */         continue;
/*     */       } 
/*     */       
/* 650 */       if (!target.desc.equals(mixinField.desc)) {
/* 651 */         throw new InvalidMixinException(this.mixin, String.format("The field %s in the target class has a conflicting signature", new Object[] { mixinField.name }));
/*     */       }
/*     */ 
/*     */       
/* 655 */       if (!target.name.equals(mixinField.name)) {
/* 656 */         if ((target.access & 0x2) == 0 && (target.access & 0x1000) == 0) {
/* 657 */           throw new InvalidMixinException(this.mixin, "Non-private field cannot be aliased. Found " + target.name);
/*     */         }
/*     */         
/* 660 */         mixinField.name = field.renameTo(target.name);
/*     */       } 
/*     */ 
/*     */       
/* 664 */       iter.remove();
/*     */       
/* 666 */       if (isShadow) {
/* 667 */         boolean isFinal = field.isDecoratedFinal();
/* 668 */         if (this.verboseLogging && Bytecode.hasFlag(target, 16) != isFinal) {
/* 669 */           String message = isFinal ? "@Shadow field {}::{} is decorated with @Final but target is not final" : "@Shadow target {}::{} is final but shadow is not decorated with @Final";
/*     */ 
/*     */           
/* 672 */           logger.warn(message, new Object[] { this.mixin, mixinField.name });
/*     */         } 
/*     */         
/* 675 */         context.addShadowField(mixinField, field);
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   protected boolean validateField(MixinTargetContext context, FieldNode field, AnnotationNode shadow) {
/* 682 */     if (Bytecode.isStatic(field) && 
/* 683 */       !Bytecode.hasFlag(field, 2) && 
/* 684 */       !Bytecode.hasFlag(field, 4096) && shadow == null)
/*     */     {
/* 686 */       throw new InvalidMixinException(context, String.format("Mixin %s contains non-private static field %s:%s", new Object[] { context, field.name, field.desc }));
/*     */     }
/*     */ 
/*     */ 
/*     */     
/* 691 */     String prefix = (String)Annotations.getValue(shadow, "prefix", Shadow.class);
/* 692 */     if (field.name.startsWith(prefix)) {
/* 693 */       throw new InvalidMixinException(context, String.format("@Shadow field %s.%s has a shadow prefix. This is not allowed.", new Object[] { context, field.name }));
/*     */     }
/*     */ 
/*     */ 
/*     */     
/* 698 */     if ("super$".equals(field.name)) {
/* 699 */       if (field.access != 2) {
/* 700 */         throw new InvalidMixinException(this.mixin, String.format("Imaginary super field %s.%s must be private and non-final", new Object[] { context, field.name }));
/*     */       }
/*     */       
/* 703 */       if (!field.desc.equals("L" + this.mixin.getClassRef() + ";"))
/* 704 */         throw new InvalidMixinException(this.mixin, 
/* 705 */             String.format("Imaginary super field %s.%s must have the same type as the parent mixin (%s)", new Object[] {
/* 706 */                 context, field.name, this.mixin.getClassName()
/*     */               })); 
/* 708 */       return false;
/*     */     } 
/*     */     
/* 711 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void transform(MixinTargetContext context) {
/* 719 */     ActivityStack.Activity methodActivity = this.activities.begin("method");
/* 720 */     for (MethodNode mixinMethod : this.classNode.methods) {
/* 721 */       methodActivity.next("Method %s", new Object[] { mixinMethod });
/* 722 */       for (Iterator<AbstractInsnNode> iter = mixinMethod.instructions.iterator(); iter.hasNext(); ) {
/* 723 */         AbstractInsnNode insn = iter.next();
/* 724 */         ActivityStack.Activity activity = this.activities.begin(Bytecode.getOpcodeName(insn));
/* 725 */         if (insn instanceof MethodInsnNode) {
/* 726 */           transformMethod((MethodInsnNode)insn);
/* 727 */         } else if (insn instanceof FieldInsnNode) {
/* 728 */           transformField((FieldInsnNode)insn);
/*     */         } 
/* 730 */         activity.end();
/*     */       } 
/*     */     } 
/* 733 */     methodActivity.end();
/*     */   }
/*     */   
/*     */   protected void transformMethod(MethodInsnNode methodNode) {
/* 737 */     ActivityStack.Activity activity = this.activities.begin("%s::%s%s", new Object[] { methodNode.owner, methodNode.name, methodNode.desc });
/* 738 */     Profiler.Section metaTimer = this.profiler.begin("meta");
/* 739 */     ClassInfo owner = ClassInfo.forDescriptor(methodNode.owner, ClassInfo.TypeLookup.DECLARED_TYPE);
/* 740 */     if (owner == null) {
/* 741 */       throw new ClassMetadataNotFoundException(methodNode.owner.replace('/', '.'));
/*     */     }
/*     */     
/* 744 */     ClassInfo.Method method = owner.findMethodInHierarchy(methodNode, ClassInfo.SearchType.ALL_CLASSES, 2);
/* 745 */     metaTimer.end();
/*     */     
/* 747 */     if (method != null && method.isRenamed()) {
/* 748 */       methodNode.name = method.getName();
/*     */     }
/* 750 */     activity.end();
/*     */   }
/*     */   
/*     */   protected void transformField(FieldInsnNode fieldNode) {
/* 754 */     ActivityStack.Activity activity = this.activities.begin("%s::%s:%s", new Object[] { fieldNode.owner, fieldNode.name, fieldNode.desc });
/* 755 */     Profiler.Section metaTimer = this.profiler.begin("meta");
/* 756 */     ClassInfo owner = ClassInfo.forDescriptor(fieldNode.owner, ClassInfo.TypeLookup.DECLARED_TYPE);
/* 757 */     if (owner == null) {
/* 758 */       throw new ClassMetadataNotFoundException(fieldNode.owner.replace('/', '.'));
/*     */     }
/*     */     
/* 761 */     ClassInfo.Field field = owner.findField(fieldNode, 2);
/* 762 */     metaTimer.end();
/*     */     
/* 764 */     if (field != null && field.isRenamed()) {
/* 765 */       fieldNode.name = field.getName();
/*     */     }
/* 767 */     activity.end();
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
/*     */   protected static String getDynamicInfo(MethodNode method) {
/* 780 */     return getDynamicInfo("Method", Annotations.getInvisible(method, Dynamic.class));
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
/*     */   protected static String getDynamicInfo(FieldNode method) {
/* 793 */     return getDynamicInfo("Field", Annotations.getInvisible(method, Dynamic.class));
/*     */   }
/*     */   
/*     */   private static String getDynamicInfo(String targetType, AnnotationNode annotation) {
/* 797 */     String description = Strings.nullToEmpty((String)Annotations.getValue(annotation));
/* 798 */     Type upstream = (Type)Annotations.getValue(annotation, "mixin");
/* 799 */     if (upstream != null) {
/* 800 */       description = String.format("{%s} %s", new Object[] { upstream.getClassName(), description }).trim();
/*     */     }
/* 802 */     return (description.length() > 0) ? String.format(" %s is @Dynamic(%s)", new Object[] { targetType, description }) : "";
/*     */   }
/*     */ }


/* Location:              C:\Users\DuduM\OneDrive\Área de Trabalho\mw10\modularwarfare-shining-2023.2.4.4f-fix10.jar!\org\spongepowered\asm\mixin\transformer\MixinPreProcessorStandard.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */