/*     */ package org.spongepowered.asm.mixin.transformer;
/*     */ 
/*     */ import java.util.ArrayList;
/*     */ import java.util.Deque;
/*     */ import java.util.HashMap;
/*     */ import java.util.HashSet;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.Set;
/*     */ import java.util.SortedSet;
/*     */ import org.apache.logging.log4j.LogManager;
/*     */ import org.apache.logging.log4j.Logger;
/*     */ import org.objectweb.asm.tree.AnnotationNode;
/*     */ import org.objectweb.asm.tree.ClassNode;
/*     */ import org.objectweb.asm.tree.FieldNode;
/*     */ import org.objectweb.asm.tree.MethodNode;
/*     */ import org.spongepowered.asm.mixin.Debug;
/*     */ import org.spongepowered.asm.mixin.MixinEnvironment;
/*     */ import org.spongepowered.asm.mixin.injection.struct.Target;
/*     */ import org.spongepowered.asm.mixin.struct.SourceMap;
/*     */ import org.spongepowered.asm.mixin.transformer.ext.Extensions;
/*     */ import org.spongepowered.asm.mixin.transformer.ext.ITargetClassContext;
/*     */ import org.spongepowered.asm.mixin.transformer.throwables.InvalidMixinException;
/*     */ import org.spongepowered.asm.util.Annotations;
/*     */ import org.spongepowered.asm.util.Bytecode;
/*     */ import org.spongepowered.asm.util.ClassSignature;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ final class TargetClassContext
/*     */   extends ClassContext
/*     */   implements ITargetClassContext
/*     */ {
/*  62 */   private static final Logger logger = LogManager.getLogger("mixin");
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private final MixinEnvironment env;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private final Extensions extensions;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private final String sessionId;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private final String className;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private final ClassNode classNode;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private final ClassInfo classInfo;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private final SourceMap sourceMap;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private final ClassSignature signature;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private final SortedSet<MixinInfo> mixins;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 113 */   private final Map<String, Target> targetMethods = new HashMap<>();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 120 */   private final Set<MethodNode> mixinMethods = new HashSet<>();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 126 */   private final List<InvalidMixinException> suppressedExceptions = new ArrayList<>();
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean applied;
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean forceExport;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   TargetClassContext(MixinEnvironment env, Extensions extensions, String sessionId, String name, ClassNode classNode, SortedSet<MixinInfo> mixins) {
/* 140 */     this.env = env;
/* 141 */     this.extensions = extensions;
/* 142 */     this.sessionId = sessionId;
/* 143 */     this.className = name;
/* 144 */     this.classNode = classNode;
/* 145 */     this.classInfo = ClassInfo.fromClassNode(classNode);
/* 146 */     this.signature = this.classInfo.getSignature();
/* 147 */     this.mixins = mixins;
/* 148 */     this.sourceMap = new SourceMap(classNode.sourceFile);
/* 149 */     this.sourceMap.addFile(this.classNode);
/*     */   }
/*     */ 
/*     */   
/*     */   public String toString() {
/* 154 */     return this.className;
/*     */   }
/*     */   
/*     */   boolean isApplied() {
/* 158 */     return this.applied;
/*     */   }
/*     */   
/*     */   boolean isExportForced() {
/* 162 */     return this.forceExport;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   Extensions getExtensions() {
/* 169 */     return this.extensions;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   String getSessionId() {
/* 176 */     return this.sessionId;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   String getClassRef() {
/* 184 */     return this.classNode.name;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   String getClassName() {
/* 191 */     return this.className;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ClassNode getClassNode() {
/* 199 */     return this.classNode;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   List<MethodNode> getMethods() {
/* 206 */     return this.classNode.methods;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   List<FieldNode> getFields() {
/* 213 */     return this.classNode.fields;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ClassInfo getClassInfo() {
/* 221 */     return this.classInfo;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   SortedSet<MixinInfo> getMixins() {
/* 228 */     return this.mixins;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   SourceMap getSourceMap() {
/* 235 */     return this.sourceMap;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   void mergeSignature(ClassSignature signature) {
/* 244 */     this.signature.merge(signature);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   void addMixinMethod(MethodNode method) {
/* 253 */     this.mixinMethods.add(method);
/*     */   }
/*     */   
/*     */   void methodMerged(MethodNode method) {
/* 257 */     if (!this.mixinMethods.remove(method)) {
/* 258 */       logger.debug("Unexpected: Merged unregistered method {}{} in {}", new Object[] { method.name, method.desc, this });
/*     */     }
/*     */   }
/*     */   
/*     */   MethodNode findMethod(Deque<String> aliases, String desc) {
/* 263 */     return findAliasedMethod(aliases, desc, true);
/*     */   }
/*     */   
/*     */   MethodNode findAliasedMethod(Deque<String> aliases, String desc) {
/* 267 */     return findAliasedMethod(aliases, desc, false);
/*     */   }
/*     */   
/*     */   private MethodNode findAliasedMethod(Deque<String> aliases, String desc, boolean includeMixinMethods) {
/* 271 */     String alias = aliases.poll();
/* 272 */     if (alias == null) {
/* 273 */       return null;
/*     */     }
/*     */     
/* 276 */     for (MethodNode target : this.classNode.methods) {
/* 277 */       if (target.name.equals(alias) && target.desc.equals(desc)) {
/* 278 */         return target;
/*     */       }
/*     */     } 
/*     */     
/* 282 */     if (includeMixinMethods) {
/* 283 */       for (MethodNode target : this.mixinMethods) {
/* 284 */         if (target.name.equals(alias) && target.desc.equals(desc)) {
/* 285 */           return target;
/*     */         }
/*     */       } 
/*     */     }
/*     */     
/* 290 */     return findAliasedMethod(aliases, desc);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   FieldNode findAliasedField(Deque<String> aliases, String desc) {
/* 301 */     String alias = aliases.poll();
/* 302 */     if (alias == null) {
/* 303 */       return null;
/*     */     }
/*     */     
/* 306 */     for (FieldNode target : this.classNode.fields) {
/* 307 */       if (target.name.equals(alias) && target.desc.equals(desc)) {
/* 308 */         return target;
/*     */       }
/*     */     } 
/*     */     
/* 312 */     return findAliasedField(aliases, desc);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   Target getTargetMethod(MethodNode method) {
/* 322 */     if (!this.classNode.methods.contains(method)) {
/* 323 */       throw new IllegalArgumentException("Invalid target method supplied to getTargetMethod()");
/*     */     }
/*     */     
/* 326 */     String targetName = method.name + method.desc;
/* 327 */     Target target = this.targetMethods.get(targetName);
/* 328 */     if (target == null) {
/* 329 */       target = new Target(this.classNode, method);
/* 330 */       this.targetMethods.put(targetName, target);
/*     */     } 
/* 332 */     return target;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   void applyMixins() {
/* 339 */     if (this.applied) {
/* 340 */       throw new IllegalStateException("Mixins already applied to target class " + this.className);
/*     */     }
/* 342 */     this.applied = true;
/*     */     
/* 344 */     MixinApplicatorStandard applicator = createApplicator();
/* 345 */     applicator.apply(this.mixins);
/* 346 */     applySignature();
/* 347 */     upgradeMethods();
/* 348 */     checkMerges();
/*     */   }
/*     */   
/*     */   private MixinApplicatorStandard createApplicator() {
/* 352 */     if (this.classInfo.isInterface()) {
/* 353 */       return new MixinApplicatorInterface(this);
/*     */     }
/* 355 */     return new MixinApplicatorStandard(this);
/*     */   }
/*     */   
/*     */   private void applySignature() {
/* 359 */     this.classNode.signature = this.signature.toString();
/*     */   }
/*     */   
/*     */   private void checkMerges() {
/* 363 */     for (MethodNode method : this.mixinMethods) {
/* 364 */       if (!method.name.startsWith("<")) {
/* 365 */         logger.debug("Unexpected: Registered method {}{} in {} was not merged", new Object[] { method.name, method.desc, this });
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   void processDebugTasks() {
/* 374 */     AnnotationNode classDebugAnnotation = Annotations.getVisible(this.classNode, Debug.class);
/* 375 */     this.forceExport = (classDebugAnnotation != null && Boolean.TRUE.equals(Annotations.getValue(classDebugAnnotation, "export")));
/*     */     
/* 377 */     if (!this.env.getOption(MixinEnvironment.Option.DEBUG_VERBOSE)) {
/*     */       return;
/*     */     }
/*     */     
/* 381 */     if (classDebugAnnotation != null && 
/* 382 */       Boolean.TRUE.equals(Annotations.getValue(classDebugAnnotation, "print"))) {
/* 383 */       Bytecode.textify(this.classNode, System.err);
/*     */     }
/*     */ 
/*     */     
/* 387 */     for (MethodNode method : this.classNode.methods) {
/* 388 */       AnnotationNode methodDebugAnnotation = Annotations.getVisible(method, Debug.class);
/* 389 */       if (methodDebugAnnotation != null && Boolean.TRUE.equals(Annotations.getValue(methodDebugAnnotation, "print"))) {
/* 390 */         Bytecode.textify(method, System.err);
/*     */       }
/*     */     } 
/*     */   }
/*     */   
/*     */   void addSuppressed(InvalidMixinException ex) {
/* 396 */     this.suppressedExceptions.add(ex);
/*     */   }
/*     */   
/*     */   List<InvalidMixinException> getSuppressedExceptions() {
/* 400 */     return this.suppressedExceptions;
/*     */   }
/*     */ }


/* Location:              C:\Users\DuduM\OneDrive\Área de Trabalho\mw10\modularwarfare-shining-2023.2.4.4f-fix10.jar!\org\spongepowered\asm\mixin\transformer\TargetClassContext.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */