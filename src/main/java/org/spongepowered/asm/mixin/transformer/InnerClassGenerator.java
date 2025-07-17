/*     */ package org.spongepowered.asm.mixin.transformer;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.util.HashMap;
/*     */ import java.util.Map;
/*     */ import java.util.UUID;
/*     */ import org.apache.logging.log4j.LogManager;
/*     */ import org.apache.logging.log4j.Logger;
/*     */ import org.objectweb.asm.AnnotationVisitor;
/*     */ import org.objectweb.asm.ClassVisitor;
/*     */ import org.objectweb.asm.commons.ClassRemapper;
/*     */ import org.objectweb.asm.commons.Remapper;
/*     */ import org.objectweb.asm.tree.ClassNode;
/*     */ import org.spongepowered.asm.mixin.extensibility.IMixinInfo;
/*     */ import org.spongepowered.asm.mixin.transformer.ext.IClassGenerator;
/*     */ import org.spongepowered.asm.mixin.transformer.throwables.InvalidMixinException;
/*     */ import org.spongepowered.asm.service.ISyntheticClassInfo;
/*     */ import org.spongepowered.asm.service.MixinService;
/*     */ import org.spongepowered.asm.util.IConsumer;
/*     */ import org.spongepowered.asm.util.asm.ASM;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ final class InnerClassGenerator
/*     */   implements IClassGenerator
/*     */ {
/*     */   static class InnerClassInfo
/*     */     extends Remapper
/*     */     implements ISyntheticClassInfo
/*     */   {
/*     */     private final IMixinInfo mixin;
/*     */     private final String name;
/*     */     private final String originalName;
/*     */     private final MixinInfo owner;
/*     */     private final MixinTargetContext target;
/*     */     private final String ownerName;
/*     */     private final String targetName;
/*     */     private int loadCounter;
/*     */     
/*     */     InnerClassInfo(IMixinInfo mixin, String name, String originalName, MixinInfo owner, MixinTargetContext target) {
/* 101 */       this.mixin = mixin;
/* 102 */       this.name = name;
/* 103 */       this.originalName = originalName;
/* 104 */       this.owner = owner;
/* 105 */       this.ownerName = owner.getClassRef();
/* 106 */       this.target = target;
/* 107 */       this.targetName = target.getTargetClassRef();
/*     */     }
/*     */ 
/*     */     
/*     */     public IMixinInfo getMixin() {
/* 112 */       return this.mixin;
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean isLoaded() {
/* 117 */       return (this.loadCounter > 0);
/*     */     }
/*     */ 
/*     */     
/*     */     public String getName() {
/* 122 */       return this.name;
/*     */     }
/*     */ 
/*     */     
/*     */     public String getClassName() {
/* 127 */       return this.name.replace('/', '.');
/*     */     }
/*     */     
/*     */     String getOriginalName() {
/* 131 */       return this.originalName;
/*     */     }
/*     */     
/*     */     MixinInfo getOwner() {
/* 135 */       return this.owner;
/*     */     }
/*     */     
/*     */     MixinTargetContext getTarget() {
/* 139 */       return this.target;
/*     */     }
/*     */     
/*     */     String getOwnerName() {
/* 143 */       return this.ownerName;
/*     */     }
/*     */     
/*     */     String getTargetName() {
/* 147 */       return this.targetName;
/*     */     }
/*     */     
/*     */     void accept(ClassVisitor classVisitor) throws ClassNotFoundException, IOException {
/* 151 */       ClassNode classNode = MixinService.getService().getBytecodeProvider().getClassNode(this.originalName);
/* 152 */       classNode.accept(classVisitor);
/* 153 */       this.loadCounter++;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public String mapMethodName(String owner, String name, String desc) {
/* 162 */       if (this.ownerName.equalsIgnoreCase(owner)) {
/* 163 */         ClassInfo.Method method = this.owner.getClassInfo().findMethod(name, desc, 10);
/* 164 */         if (method != null) {
/* 165 */           return method.getName();
/*     */         }
/*     */       } 
/* 168 */       return super.mapMethodName(owner, name, desc);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public String map(String key) {
/* 176 */       if (this.originalName.equals(key))
/* 177 */         return this.name; 
/* 178 */       if (this.ownerName.equals(key)) {
/* 179 */         return this.targetName;
/*     */       }
/* 181 */       return key;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public String toString() {
/* 189 */       return this.name;
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   static class InnerClassAdapter
/*     */     extends ClassRemapper
/*     */   {
/*     */     private final InnerClassGenerator.InnerClassInfo info;
/*     */ 
/*     */ 
/*     */     
/*     */     public InnerClassAdapter(ClassVisitor cv, InnerClassGenerator.InnerClassInfo info) {
/* 203 */       super(ASM.API_VERSION, cv, info);
/* 204 */       this.info = info;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public void visitSource(String source, String debug) {
/* 213 */       super.visitSource(source, debug);
/* 214 */       AnnotationVisitor av = this.cv.visitAnnotation("Lorg/spongepowered/asm/mixin/transformer/meta/MixinInner;", false);
/* 215 */       av.visit("mixin", this.info.getOwner().toString());
/* 216 */       av.visit("name", this.info.getOriginalName().substring(this.info.getOriginalName().lastIndexOf('/') + 1));
/* 217 */       av.visitEnd();
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public void visitInnerClass(String name, String outerName, String innerName, int access) {
/* 227 */       if (name.startsWith(this.info.getOriginalName() + "$")) {
/* 228 */         throw new InvalidMixinException(this.info.getOwner(), "Found unsupported nested inner class " + name + " in " + this.info
/* 229 */             .getOriginalName());
/*     */       }
/*     */       
/* 232 */       super.visitInnerClass(name, outerName, innerName, access);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 240 */   private static final Logger logger = LogManager.getLogger("mixin");
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private final IConsumer<ISyntheticClassInfo> registry;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 251 */   private final Map<String, String> innerClassNames = new HashMap<>();
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 256 */   private final Map<String, InnerClassInfo> innerClasses = new HashMap<>();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public InnerClassGenerator(IConsumer<ISyntheticClassInfo> registry) {
/* 264 */     this.registry = registry;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getName() {
/* 273 */     return "inner";
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String registerInnerClass(MixinInfo owner, String originalName, MixinTargetContext context) {
/* 283 */     String id = String.format("%s%s", new Object[] { originalName, context });
/* 284 */     String ref = this.innerClassNames.get(id);
/* 285 */     if (ref == null) {
/* 286 */       ref = getUniqueReference(originalName, context);
/* 287 */       InnerClassInfo info = new InnerClassInfo(owner, ref, originalName, owner, context);
/* 288 */       this.innerClassNames.put(id, ref);
/* 289 */       this.innerClasses.put(ref, info);
/* 290 */       this.registry.accept(info);
/* 291 */       logger.debug("Inner class {} in {} on {} gets unique name {}", new Object[] { originalName, owner.getClassRef(), context
/* 292 */             .getTargetClassRef(), ref });
/*     */     } 
/* 294 */     return ref;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean generate(String name, ClassNode classNode) {
/* 303 */     String ref = name.replace('.', '/');
/* 304 */     InnerClassInfo info = this.innerClasses.get(ref);
/* 305 */     if (info == null) {
/* 306 */       return false;
/*     */     }
/* 308 */     return generate(info, classNode);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean generate(InnerClassInfo info, ClassNode classNode) {
/*     */     try {
/* 320 */       logger.debug("Generating mapped inner class {} (originally {})", new Object[] { info.getName(), info.getOriginalName() });
/* 321 */       info.accept((ClassVisitor)new InnerClassAdapter((ClassVisitor)classNode, info));
/* 322 */       return true;
/* 323 */     } catch (InvalidMixinException ex) {
/* 324 */       throw ex;
/* 325 */     } catch (Exception ex) {
/* 326 */       logger.catching(ex);
/*     */ 
/*     */       
/* 329 */       return false;
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
/*     */   private static String getUniqueReference(String originalName, MixinTargetContext context) {
/* 341 */     String name = originalName.substring(originalName.lastIndexOf('$') + 1);
/* 342 */     if (name.matches("^[0-9]+$")) {
/* 343 */       name = "Anonymous";
/*     */     }
/* 345 */     return String.format("%s$%s$%s", new Object[] { context.getTargetClassRef(), name, UUID.randomUUID().toString().replace("-", "") });
/*     */   }
/*     */ }


/* Location:              C:\Users\DuduM\OneDrive\Área de Trabalho\mw10\modularwarfare-shining-2023.2.4.4f-fix10.jar!\org\spongepowered\asm\mixin\transformer\InnerClassGenerator.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */