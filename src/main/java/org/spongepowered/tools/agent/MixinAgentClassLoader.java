/*     */ package org.spongepowered.tools.agent;
/*     */ 
/*     */ import java.util.HashMap;
/*     */ import java.util.Map;
/*     */ import org.apache.logging.log4j.LogManager;
/*     */ import org.apache.logging.log4j.Logger;
/*     */ import org.objectweb.asm.ClassVisitor;
/*     */ import org.objectweb.asm.ClassWriter;
/*     */ import org.objectweb.asm.MethodVisitor;
/*     */ import org.objectweb.asm.Type;
/*     */ import org.objectweb.asm.tree.ClassNode;
/*     */ import org.spongepowered.asm.mixin.MixinEnvironment;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ class MixinAgentClassLoader
/*     */   extends ClassLoader
/*     */ {
/*  46 */   private static final Logger logger = LogManager.getLogger("mixin.agent");
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  51 */   private Map<Class<?>, byte[]> mixins = (Map)new HashMap<>();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  57 */   private Map<String, byte[]> targets = (Map)new HashMap<>();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   void addMixinClass(String name) {
/*  65 */     logger.debug("Mixin class {} added to class loader", new Object[] { name });
/*     */     try {
/*  67 */       byte[] bytes = materialise(name);
/*  68 */       Class<?> clazz = defineClass(name, bytes, 0, bytes.length);
/*     */ 
/*     */       
/*  71 */       clazz.newInstance();
/*  72 */       this.mixins.put(clazz, bytes);
/*  73 */     } catch (Throwable e) {
/*  74 */       logger.catching(e);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   void addTargetClass(String name, ClassNode classNode) {
/*  85 */     synchronized (this.targets) {
/*  86 */       if (this.targets.containsKey(name)) {
/*     */         return;
/*     */       }
/*     */       try {
/*  90 */         ClassWriter cw = new ClassWriter(0);
/*  91 */         classNode.accept((ClassVisitor)cw);
/*  92 */         this.targets.put(name, cw.toByteArray());
/*  93 */       } catch (Exception ex) {
/*  94 */         logger.error("Error storing original class bytecode for {} in mixin hotswap agent. {}: {}", new Object[] { name, ex
/*  95 */               .getClass().getName(), ex.getMessage() });
/*  96 */         logger.debug(ex);
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   byte[] getFakeMixinBytecode(Class<?> clazz) {
/* 108 */     return this.mixins.get(clazz);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   byte[] getOriginalTargetBytecode(String name) {
/* 118 */     synchronized (this.targets) {
/* 119 */       return this.targets.get(name);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private byte[] materialise(String name) {
/* 130 */     ClassWriter cw = new ClassWriter(3);
/* 131 */     cw.visit(MixinEnvironment.getCompatibilityLevel().classVersion(), 1, name.replace('.', '/'), null, 
/* 132 */         Type.getInternalName(Object.class), null);
/*     */ 
/*     */     
/* 135 */     MethodVisitor mv = cw.visitMethod(1, "<init>", "()V", null, null);
/* 136 */     mv.visitCode();
/* 137 */     mv.visitVarInsn(25, 0);
/* 138 */     mv.visitMethodInsn(183, Type.getInternalName(Object.class), "<init>", "()V", false);
/* 139 */     mv.visitInsn(177);
/* 140 */     mv.visitMaxs(1, 1);
/* 141 */     mv.visitEnd();
/*     */     
/* 143 */     cw.visitEnd();
/* 144 */     return cw.toByteArray();
/*     */   }
/*     */ }


/* Location:              C:\Users\DuduM\OneDrive\Área de Trabalho\mw10\modularwarfare-shining-2023.2.4.4f-fix10.jar!\org\spongepowered\tools\agent\MixinAgentClassLoader.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */