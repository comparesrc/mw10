/*     */ package org.spongepowered.asm.launch.platform;
/*     */ 
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collection;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import org.apache.logging.log4j.LogManager;
/*     */ import org.apache.logging.log4j.Logger;
/*     */ import org.spongepowered.asm.launch.GlobalProperties;
/*     */ import org.spongepowered.asm.launch.platform.container.IContainerHandle;
/*     */ import org.spongepowered.asm.service.MixinService;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class MixinContainer
/*     */ {
/*  45 */   private static final List<String> agentClasses = new ArrayList<>();
/*     */   
/*     */   static {
/*  48 */     GlobalProperties.put(GlobalProperties.Keys.AGENTS, agentClasses);
/*  49 */     for (String agent : MixinService.getService().getPlatformAgents()) {
/*  50 */       agentClasses.add(agent);
/*     */     }
/*  52 */     agentClasses.add("org.spongepowered.asm.launch.platform.MixinPlatformAgentDefault");
/*     */   }
/*     */   
/*  55 */   private static final Logger logger = LogManager.getLogger("mixin");
/*     */   
/*     */   private final IContainerHandle handle;
/*     */   
/*  59 */   private final List<IMixinPlatformAgent> agents = new ArrayList<>();
/*     */   
/*     */   public MixinContainer(MixinPlatformManager manager, IContainerHandle handle) {
/*  62 */     this.handle = handle;
/*     */     
/*  64 */     for (Iterator<String> iter = agentClasses.iterator(); iter.hasNext(); ) {
/*  65 */       String agentClass = iter.next();
/*     */       
/*     */       try {
/*  68 */         Class<IMixinPlatformAgent> clazz = (Class)Class.forName(agentClass);
/*  69 */         String simpleName = clazz.getSimpleName();
/*     */         
/*  71 */         logger.debug("Instancing new {} for {}", new Object[] { simpleName, this.handle });
/*  72 */         IMixinPlatformAgent agent = clazz.newInstance();
/*     */         
/*  74 */         IMixinPlatformAgent.AcceptResult acceptAction = agent.accept(manager, this.handle);
/*  75 */         if (acceptAction == IMixinPlatformAgent.AcceptResult.ACCEPTED) {
/*  76 */           this.agents.add(agent);
/*  77 */         } else if (acceptAction == IMixinPlatformAgent.AcceptResult.INVALID) {
/*  78 */           iter.remove();
/*     */           
/*     */           continue;
/*     */         } 
/*  82 */         logger.debug("{} {} container {}", new Object[] { simpleName, acceptAction.name().toLowerCase(), this.handle });
/*  83 */       } catch (InstantiationException ex) {
/*  84 */         Throwable cause = ex.getCause();
/*  85 */         if (cause instanceof RuntimeException) {
/*  86 */           throw (RuntimeException)cause;
/*     */         }
/*  88 */         throw new RuntimeException(cause);
/*  89 */       } catch (ReflectiveOperationException ex) {
/*  90 */         logger.catching(ex);
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public IContainerHandle getDescriptor() {
/*  99 */     return this.handle;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Collection<String> getPhaseProviders() {
/* 106 */     List<String> phaseProviders = new ArrayList<>();
/* 107 */     for (IMixinPlatformAgent agent : this.agents) {
/* 108 */       String phaseProvider = agent.getPhaseProvider();
/* 109 */       if (phaseProvider != null) {
/* 110 */         phaseProviders.add(phaseProvider);
/*     */       }
/*     */     } 
/* 113 */     return phaseProviders;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void prepare() {
/* 120 */     for (IMixinPlatformAgent agent : this.agents) {
/* 121 */       logger.debug("Processing prepare() for {}", new Object[] { agent });
/* 122 */       agent.prepare();
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void initPrimaryContainer() {
/* 131 */     for (IMixinPlatformAgent agent : this.agents) {
/* 132 */       logger.debug("Processing launch tasks for {}", new Object[] { agent });
/* 133 */       agent.initPrimaryContainer();
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void inject() {
/* 141 */     for (IMixinPlatformAgent agent : this.agents) {
/* 142 */       logger.debug("Processing inject() for {}", new Object[] { agent });
/* 143 */       agent.inject();
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\DuduM\OneDrive\Área de Trabalho\mw10\modularwarfare-shining-2023.2.4.4f-fix10.jar!\org\spongepowered\asm\launch\platform\MixinContainer.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */