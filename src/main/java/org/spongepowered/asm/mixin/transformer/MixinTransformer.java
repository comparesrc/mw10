/*     */ package org.spongepowered.asm.mixin.transformer;
/*     */ 
/*     */ import java.lang.reflect.Constructor;
/*     */ import java.util.List;
/*     */ import org.objectweb.asm.tree.ClassNode;
/*     */ import org.spongepowered.asm.mixin.MixinEnvironment;
/*     */ import org.spongepowered.asm.mixin.throwables.MixinException;
/*     */ import org.spongepowered.asm.mixin.transformer.ext.Extensions;
/*     */ import org.spongepowered.asm.mixin.transformer.ext.IExtensionRegistry;
/*     */ import org.spongepowered.asm.mixin.transformer.ext.IHotSwap;
/*     */ import org.spongepowered.asm.transformers.TreeTransformer;
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
/*     */ class MixinTransformer
/*     */   extends TreeTransformer
/*     */   implements IMixinTransformer
/*     */ {
/*     */   private static final String MIXIN_AGENT_CLASS = "org.spongepowered.tools.agent.MixinAgent";
/*     */   private final SyntheticClassRegistry syntheticClassRegistry;
/*     */   private final Extensions extensions;
/*     */   private final IHotSwap hotSwapper;
/*     */   private final MixinProcessor processor;
/*     */   private final MixinClassGenerator generator;
/*     */   
/*     */   public MixinTransformer() {
/*  74 */     MixinEnvironment environment = MixinEnvironment.getCurrentEnvironment();
/*     */     
/*  76 */     Object globalMixinTransformer = environment.getActiveTransformer();
/*  77 */     if (globalMixinTransformer instanceof IMixinTransformer) {
/*  78 */       throw new MixinException("Terminating MixinTransformer instance " + this);
/*     */     }
/*     */ 
/*     */     
/*  82 */     environment.setActiveTransformer(this);
/*     */     
/*  84 */     this.syntheticClassRegistry = new SyntheticClassRegistry();
/*  85 */     this.extensions = new Extensions(this.syntheticClassRegistry);
/*     */     
/*  87 */     this.hotSwapper = initHotSwapper(environment);
/*     */     
/*  89 */     this.processor = new MixinProcessor(environment, this.extensions, this.hotSwapper);
/*  90 */     this.generator = new MixinClassGenerator(environment, this.extensions);
/*     */     
/*  92 */     DefaultExtensions.create(environment, this.extensions, this.syntheticClassRegistry);
/*     */   }
/*     */   
/*     */   private IHotSwap initHotSwapper(MixinEnvironment environment) {
/*  96 */     if (!environment.getOption(MixinEnvironment.Option.HOT_SWAP)) {
/*  97 */       return null;
/*     */     }
/*     */     
/*     */     try {
/* 101 */       MixinProcessor.logger.info("Attempting to load Hot-Swap agent");
/*     */ 
/*     */       
/* 104 */       Class<? extends IHotSwap> clazz = (Class)Class.forName("org.spongepowered.tools.agent.MixinAgent");
/* 105 */       Constructor<? extends IHotSwap> ctor = clazz.getDeclaredConstructor(new Class[] { IMixinTransformer.class });
/* 106 */       return ctor.newInstance(new Object[] { this });
/* 107 */     } catch (Throwable th) {
/* 108 */       MixinProcessor.logger.info("Hot-swap agent could not be loaded, hot swapping of mixins won't work. {}: {}", new Object[] { th
/* 109 */             .getClass().getSimpleName(), th.getMessage() });
/*     */ 
/*     */       
/* 112 */       return null;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public IExtensionRegistry getExtensions() {
/* 121 */     return (IExtensionRegistry)this.extensions;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getName() {
/* 129 */     return getClass().getName();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isDelegationExcluded() {
/* 137 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void audit(MixinEnvironment environment) {
/* 147 */     this.processor.audit(environment);
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
/*     */   public List<String> reload(String mixinClass, ClassNode classNode) {
/* 159 */     return this.processor.reload(mixinClass, classNode);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public byte[] transformClassBytes(String name, String transformedName, byte[] basicClass) {
/* 168 */     if (transformedName == null) {
/* 169 */       return basicClass;
/*     */     }
/*     */     
/* 172 */     MixinEnvironment environment = MixinEnvironment.getCurrentEnvironment();
/*     */     
/* 174 */     if (basicClass == null) {
/* 175 */       return generateClass(environment, transformedName);
/*     */     }
/*     */     
/* 178 */     return transformClass(environment, transformedName, basicClass);
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
/*     */   public byte[] transformClass(MixinEnvironment environment, String name, byte[] classBytes) {
/* 190 */     ClassNode classNode = readClass(classBytes);
/* 191 */     if (this.processor.applyMixins(environment, name, classNode)) {
/* 192 */       return writeClass(classNode);
/*     */     }
/* 194 */     return classBytes;
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
/*     */   public boolean transformClass(MixinEnvironment environment, String name, ClassNode classNode) {
/* 206 */     return this.processor.applyMixins(environment, name, classNode);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public byte[] generateClass(MixinEnvironment environment, String name) {
/* 217 */     ClassNode classNode = createEmptyClass(name);
/* 218 */     if (this.generator.generateClass(environment, name, classNode)) {
/* 219 */       return writeClass(classNode);
/*     */     }
/* 221 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean generateClass(MixinEnvironment environment, String name, ClassNode classNode) {
/* 231 */     return this.generator.generateClass(environment, name, classNode);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static ClassNode createEmptyClass(String name) {
/* 238 */     ClassNode classNode = new ClassNode(ASM.API_VERSION);
/* 239 */     classNode.name = name.replace('.', '/');
/* 240 */     classNode.version = MixinEnvironment.getCompatibilityLevel().classVersion();
/* 241 */     classNode.superName = "java/lang/Object";
/* 242 */     return classNode;
/*     */   }
/*     */ }


/* Location:              C:\Users\DuduM\OneDrive\Área de Trabalho\mw10\modularwarfare-shining-2023.2.4.4f-fix10.jar!\org\spongepowered\asm\mixin\transformer\MixinTransformer.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */