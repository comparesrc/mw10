/*     */ package org.spongepowered.tools.agent;
/*     */ 
/*     */ import java.lang.instrument.ClassDefinition;
/*     */ import java.lang.instrument.ClassFileTransformer;
/*     */ import java.lang.instrument.IllegalClassFormatException;
/*     */ import java.lang.instrument.Instrumentation;
/*     */ import java.security.ProtectionDomain;
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import org.apache.logging.log4j.LogManager;
/*     */ import org.apache.logging.log4j.Logger;
/*     */ import org.objectweb.asm.ClassReader;
/*     */ import org.objectweb.asm.ClassVisitor;
/*     */ import org.objectweb.asm.tree.ClassNode;
/*     */ import org.spongepowered.asm.mixin.transformer.IMixinTransformer;
/*     */ import org.spongepowered.asm.mixin.transformer.ext.IHotSwap;
/*     */ import org.spongepowered.asm.mixin.transformer.throwables.MixinReloadException;
/*     */ import org.spongepowered.asm.service.IMixinService;
/*     */ import org.spongepowered.asm.service.MixinService;
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
/*     */ public class MixinAgent
/*     */   implements IHotSwap
/*     */ {
/*     */   class Transformer
/*     */     implements ClassFileTransformer
/*     */   {
/*     */     public byte[] transform(ClassLoader loader, String className, Class<?> classBeingRedefined, ProtectionDomain domain, byte[] classfileBuffer) throws IllegalClassFormatException {
/*  61 */       if (classBeingRedefined == null) {
/*  62 */         return null;
/*     */       }
/*     */       
/*  65 */       byte[] mixinBytecode = MixinAgent.classLoader.getFakeMixinBytecode(classBeingRedefined);
/*  66 */       if (mixinBytecode != null) {
/*  67 */         ClassNode classNode = new ClassNode(ASM.API_VERSION);
/*  68 */         ClassReader cr = new ClassReader(classfileBuffer);
/*  69 */         cr.accept((ClassVisitor)classNode, 8);
/*     */         
/*  71 */         List<String> targets = reloadMixin(className, classNode);
/*  72 */         if (targets == null || !reApplyMixins(targets)) {
/*  73 */           return MixinAgent.ERROR_BYTECODE;
/*     */         }
/*  75 */         return mixinBytecode;
/*     */       } 
/*     */       
/*     */       try {
/*  79 */         MixinAgent.logger.info("Redefining class " + className);
/*  80 */         return MixinAgent.this.classTransformer.transformClassBytes(null, className, classfileBuffer);
/*  81 */       } catch (Throwable th) {
/*  82 */         MixinAgent.logger.error("Error while re-transforming class " + className, th);
/*  83 */         return MixinAgent.ERROR_BYTECODE;
/*     */       } 
/*     */     }
/*     */     
/*     */     private List<String> reloadMixin(String className, ClassNode classNode) {
/*  88 */       MixinAgent.logger.info("Redefining mixin {}", new Object[] { className });
/*     */       try {
/*  90 */         return MixinAgent.this.classTransformer.reload(className.replace('/', '.'), classNode);
/*  91 */       } catch (MixinReloadException e) {
/*  92 */         MixinAgent.logger.error("Mixin {} cannot be reloaded, needs a restart to be applied: {} ", new Object[] { e.getMixinInfo(), e.getMessage() });
/*  93 */       } catch (Throwable th) {
/*     */         
/*  95 */         MixinAgent.logger.error("Error while finding targets for mixin " + className, th);
/*     */       } 
/*  97 */       return null;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     private boolean reApplyMixins(List<String> targets) {
/* 108 */       IMixinService service = MixinService.getService();
/*     */       
/* 110 */       for (String target : targets) {
/* 111 */         String targetName = target.replace('/', '.');
/* 112 */         MixinAgent.logger.debug("Re-transforming target class {}", new Object[] { target });
/*     */         try {
/* 114 */           Class<?> targetClass = service.getClassProvider().findClass(targetName);
/* 115 */           byte[] targetBytecode = MixinAgent.classLoader.getOriginalTargetBytecode(targetName);
/* 116 */           if (targetBytecode == null) {
/* 117 */             MixinAgent.logger.error("Target class {} bytecode is not registered", new Object[] { targetName });
/* 118 */             return false;
/*     */           } 
/* 120 */           targetBytecode = MixinAgent.this.classTransformer.transformClassBytes(null, targetName, targetBytecode);
/* 121 */           MixinAgent.instrumentation.redefineClasses(new ClassDefinition[] { new ClassDefinition(targetClass, targetBytecode) });
/* 122 */         } catch (Throwable th) {
/* 123 */           MixinAgent.logger.error("Error while re-transforming target class " + target, th);
/* 124 */           return false;
/*     */         } 
/*     */       } 
/* 127 */       return true;
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 136 */   public static final byte[] ERROR_BYTECODE = new byte[] { 1 };
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 141 */   static final MixinAgentClassLoader classLoader = new MixinAgentClassLoader();
/*     */   
/* 143 */   static final Logger logger = LogManager.getLogger("mixin.agent");
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 148 */   static Instrumentation instrumentation = null;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 153 */   private static List<MixinAgent> agents = new ArrayList<>();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   final IMixinTransformer classTransformer;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public MixinAgent(IMixinTransformer classTransformer) {
/* 168 */     this.classTransformer = classTransformer;
/* 169 */     agents.add(this);
/* 170 */     if (instrumentation != null) {
/* 171 */       initTransformer();
/*     */     }
/*     */   }
/*     */   
/*     */   private void initTransformer() {
/* 176 */     instrumentation.addTransformer(new Transformer(), true);
/*     */   }
/*     */ 
/*     */   
/*     */   public void registerMixinClass(String name) {
/* 181 */     classLoader.addMixinClass(name);
/*     */   }
/*     */ 
/*     */   
/*     */   public void registerTargetClass(String name, ClassNode classNode) {
/* 186 */     classLoader.addTargetClass(name, classNode);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void init(Instrumentation instrumentation) {
/* 196 */     MixinAgent.instrumentation = instrumentation;
/* 197 */     if (!MixinAgent.instrumentation.isRedefineClassesSupported()) {
/* 198 */       logger.error("The instrumentation doesn't support re-definition of classes");
/*     */     }
/* 200 */     for (MixinAgent agent : agents) {
/* 201 */       agent.initTransformer();
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
/*     */   public static void premain(String arg, Instrumentation instrumentation) {
/* 215 */     System.setProperty("mixin.hotSwap", "true");
/* 216 */     init(instrumentation);
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
/*     */   public static void agentmain(String arg, Instrumentation instrumentation) {
/* 229 */     init(instrumentation);
/*     */   }
/*     */ }


/* Location:              C:\Users\DuduM\OneDrive\Área de Trabalho\mw10\modularwarfare-shining-2023.2.4.4f-fix10.jar!\org\spongepowered\tools\agent\MixinAgent.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */