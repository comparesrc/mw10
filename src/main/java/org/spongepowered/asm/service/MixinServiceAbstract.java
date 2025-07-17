/*     */ package org.spongepowered.asm.service;
/*     */ 
/*     */ import com.google.common.collect.ImmutableList;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collection;
/*     */ import java.util.List;
/*     */ import org.apache.logging.log4j.LogManager;
/*     */ import org.apache.logging.log4j.Logger;
/*     */ import org.spongepowered.asm.launch.platform.IMixinPlatformAgent;
/*     */ import org.spongepowered.asm.launch.platform.IMixinPlatformServiceAgent;
/*     */ import org.spongepowered.asm.launch.platform.container.IContainerHandle;
/*     */ import org.spongepowered.asm.mixin.MixinEnvironment;
/*     */ import org.spongepowered.asm.util.IConsumer;
/*     */ import org.spongepowered.asm.util.ReEntranceLock;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public abstract class MixinServiceAbstract
/*     */   implements IMixinService
/*     */ {
/*     */   protected static final String LAUNCH_PACKAGE = "org.spongepowered.asm.launch.";
/*     */   protected static final String MIXIN_PACKAGE = "org.spongepowered.asm.mixin.";
/*  57 */   protected static final Logger logger = LogManager.getLogger("mixin");
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  63 */   protected final ReEntranceLock lock = new ReEntranceLock(1);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private List<IMixinPlatformServiceAgent> serviceAgents;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private String sideName;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void prepare() {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public MixinEnvironment.Phase getInitialPhase() {
/*  87 */     return MixinEnvironment.Phase.PREINIT;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public MixinEnvironment.CompatibilityLevel getMinCompatibilityLevel() {
/*  96 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public MixinEnvironment.CompatibilityLevel getMaxCompatibilityLevel() {
/* 105 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void beginPhase() {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void checkEnv(Object bootSource) {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void init() {
/* 128 */     for (IMixinPlatformServiceAgent agent : getServiceAgents()) {
/* 129 */       agent.init();
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ReEntranceLock getReEntranceLock() {
/* 138 */     return this.lock;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Collection<IContainerHandle> getMixinContainers() {
/* 146 */     ImmutableList.Builder<IContainerHandle> list = ImmutableList.builder();
/* 147 */     getContainersFromAgents(list);
/* 148 */     return (Collection<IContainerHandle>)list.build();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected final void getContainersFromAgents(ImmutableList.Builder<IContainerHandle> list) {
/* 155 */     for (IMixinPlatformServiceAgent agent : getServiceAgents()) {
/* 156 */       Collection<IContainerHandle> containers = agent.getMixinContainers();
/* 157 */       if (containers != null) {
/* 158 */         list.addAll(containers);
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final String getSideName() {
/* 168 */     if (this.sideName != null) {
/* 169 */       return this.sideName;
/*     */     }
/*     */     
/* 172 */     for (IMixinPlatformServiceAgent agent : getServiceAgents()) {
/*     */       try {
/* 174 */         String side = agent.getSideName();
/* 175 */         if (side != null) {
/* 176 */           return this.sideName = side;
/*     */         }
/* 178 */       } catch (Exception ex) {
/* 179 */         logger.catching(ex);
/*     */       } 
/*     */     } 
/*     */     
/* 183 */     return "UNKNOWN";
/*     */   }
/*     */   
/*     */   private List<IMixinPlatformServiceAgent> getServiceAgents() {
/* 187 */     if (this.serviceAgents != null) {
/* 188 */       return this.serviceAgents;
/*     */     }
/* 190 */     this.serviceAgents = new ArrayList<>();
/* 191 */     for (String agentClassName : getPlatformAgents()) {
/*     */       
/*     */       try {
/* 194 */         Class<IMixinPlatformAgent> agentClass = (Class)getClassProvider().findClass(agentClassName, false);
/* 195 */         IMixinPlatformAgent agent = agentClass.newInstance();
/* 196 */         if (agent instanceof IMixinPlatformServiceAgent) {
/* 197 */           this.serviceAgents.add((IMixinPlatformServiceAgent)agent);
/*     */         }
/* 199 */       } catch (Exception ex) {
/*     */         
/* 201 */         ex.printStackTrace();
/*     */       } 
/*     */     } 
/* 204 */     return this.serviceAgents;
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
/*     */   @Deprecated
/*     */   public void wire(MixinEnvironment.Phase phase, IConsumer<MixinEnvironment.Phase> phaseConsumer) {
/* 219 */     for (IMixinPlatformServiceAgent agent : getServiceAgents()) {
/* 220 */       agent.wire(phase, phaseConsumer);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   public void unwire() {
/* 230 */     for (IMixinPlatformServiceAgent agent : getServiceAgents())
/* 231 */       agent.unwire(); 
/*     */   }
/*     */ }


/* Location:              C:\Users\DuduM\OneDrive\Área de Trabalho\mw10\modularwarfare-shining-2023.2.4.4f-fix10.jar!\org\spongepowered\asm\service\MixinServiceAbstract.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */