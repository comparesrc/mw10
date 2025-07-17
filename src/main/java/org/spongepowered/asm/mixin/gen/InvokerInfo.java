/*     */ package org.spongepowered.asm.mixin.gen;
/*     */ 
/*     */ import org.objectweb.asm.Type;
/*     */ import org.objectweb.asm.tree.MethodNode;
/*     */ import org.spongepowered.asm.mixin.gen.throwables.InvalidAccessorException;
/*     */ import org.spongepowered.asm.mixin.injection.selectors.ITargetSelector;
/*     */ import org.spongepowered.asm.mixin.injection.selectors.ITargetSelectorByName;
/*     */ import org.spongepowered.asm.mixin.injection.selectors.TargetSelector;
/*     */ import org.spongepowered.asm.mixin.injection.struct.MemberInfo;
/*     */ import org.spongepowered.asm.mixin.transformer.MixinTargetContext;
/*     */ import org.spongepowered.asm.util.Bytecode;
/*     */ import org.spongepowered.asm.util.asm.ElementNode;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ class InvokerInfo
/*     */   extends AccessorInfo
/*     */ {
/*     */   InvokerInfo(MixinTargetContext mixin, MethodNode method) {
/*  46 */     super(mixin, method, (Class)Invoker.class);
/*     */   }
/*     */ 
/*     */   
/*     */   protected AccessorInfo.AccessorType initType() {
/*  51 */     if (this.specifiedName != null) {
/*  52 */       String mappedReference = this.mixin.getReferenceMapper().remap(this.mixin.getClassRef(), this.specifiedName);
/*  53 */       return initType(mappedReference.replace('.', '/'), this.mixin.getTargetClassRef());
/*     */     } 
/*     */     
/*  56 */     AccessorInfo.AccessorName accessorName = AccessorInfo.AccessorName.of(this.method.name, false);
/*  57 */     if (accessorName != null) {
/*  58 */       for (String prefix : AccessorInfo.AccessorType.OBJECT_FACTORY.getExpectedPrefixes()) {
/*  59 */         if (prefix.equals(accessorName.prefix)) {
/*  60 */           return initType(accessorName.name, this.mixin.getTargetClassInfo().getSimpleName());
/*     */         }
/*     */       } 
/*     */     }
/*     */     
/*  65 */     return AccessorInfo.AccessorType.METHOD_PROXY;
/*     */   }
/*     */   
/*     */   private AccessorInfo.AccessorType initType(String targetName, String targetClassName) {
/*  69 */     if ("<init>".equals(targetName) || targetClassName.equals(targetName)) {
/*  70 */       if (!this.returnType.equals(this.mixin.getTargetClassInfo().getType()))
/*  71 */         throw new InvalidAccessorException(this.mixin, 
/*  72 */             String.format("%s appears to have an invalid return type. %s requires matching return type. Found %s expected %s", new Object[] {
/*  73 */                 this, AccessorInfo.AccessorType.OBJECT_FACTORY, Bytecode.getSimpleName(this.returnType), this.mixin.getTargetClassInfo().getSimpleName()
/*     */               })); 
/*  75 */       if (!this.isStatic) {
/*  76 */         throw new InvalidAccessorException(this.mixin, String.format("%s for %s must be static", new Object[] { this, AccessorInfo.AccessorType.OBJECT_FACTORY, 
/*  77 */                 Bytecode.getSimpleName(this.returnType) }));
/*     */       }
/*     */       
/*  80 */       return AccessorInfo.AccessorType.OBJECT_FACTORY;
/*     */     } 
/*  82 */     return AccessorInfo.AccessorType.METHOD_PROXY;
/*     */   }
/*     */ 
/*     */   
/*     */   protected Type initTargetFieldType() {
/*  87 */     return null;
/*     */   }
/*     */ 
/*     */   
/*     */   protected ITargetSelector initTarget() {
/*  92 */     if (this.type == AccessorInfo.AccessorType.OBJECT_FACTORY) {
/*  93 */       return (ITargetSelector)new MemberInfo("<init>", null, Bytecode.changeDescriptorReturnType(this.method.desc, "V"));
/*     */     }
/*     */     
/*  96 */     return (ITargetSelector)new MemberInfo(getTargetName(this.specifiedName), null, this.method.desc);
/*     */   }
/*     */ 
/*     */   
/*     */   public void locate() {
/* 101 */     this.targetMethod = findTargetMethod();
/*     */   }
/*     */   
/*     */   private MethodNode findTargetMethod() {
/* 105 */     TargetSelector.Result<MethodNode> result = TargetSelector.run(this.target.configure(new String[] { "orphan" }, ), ElementNode.methodList(this.classNode));
/*     */     
/*     */     try {
/* 108 */       return (MethodNode)result.getSingleResult(true);
/* 109 */     } catch (IllegalStateException ex) {
/* 110 */       String message = ex.getMessage() + " matching " + this.target + " in " + this.classNode.name + " for " + this;
/* 111 */       if (this.type == AccessorInfo.AccessorType.METHOD_PROXY && this.specifiedName != null && this.target instanceof ITargetSelectorByName) {
/* 112 */         String name = ((ITargetSelectorByName)this.target).getName();
/* 113 */         if (name != null && (name.contains(".") || name.contains("/"))) {
/* 114 */           throw new InvalidAccessorException(this, "Invalid factory invoker failed to match the target class. " + message);
/*     */         }
/*     */       } 
/* 117 */       throw new InvalidAccessorException(this, message);
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\DuduM\OneDrive\Área de Trabalho\mw10\modularwarfare-shining-2023.2.4.4f-fix10.jar!\org\spongepowered\asm\mixin\gen\InvokerInfo.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */