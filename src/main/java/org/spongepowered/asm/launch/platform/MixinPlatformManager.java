/*     */ package org.spongepowered.asm.launch.platform;
/*     */ 
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collection;
/*     */ import java.util.Collections;
/*     */ import java.util.LinkedHashMap;
/*     */ import java.util.List;
/*     */ import java.util.Locale;
/*     */ import java.util.Map;
/*     */ import org.apache.logging.log4j.LogManager;
/*     */ import org.apache.logging.log4j.Logger;
/*     */ import org.spongepowered.asm.launch.platform.container.IContainerHandle;
/*     */ import org.spongepowered.asm.mixin.MixinEnvironment;
/*     */ import org.spongepowered.asm.mixin.Mixins;
/*     */ import org.spongepowered.asm.mixin.throwables.MixinError;
/*     */ import org.spongepowered.asm.service.MixinService;
/*     */ import org.spongepowered.asm.service.ServiceVersionError;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class MixinPlatformManager
/*     */ {
/*     */   private static final String DEFAULT_MAIN_CLASS = "net.minecraft.client.main.Main";
/*  59 */   private static final Logger logger = LogManager.getLogger("mixin");
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  69 */   private final Map<IContainerHandle, MixinContainer> containers = new LinkedHashMap<>();
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  74 */   private final MixinConnectorManager connectors = new MixinConnectorManager();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private MixinContainer primaryContainer;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean prepared = false;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean injected;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void init() {
/* 101 */     logger.debug("Initialising Mixin Platform Manager");
/*     */     
/* 103 */     IContainerHandle primaryContainerHandle = MixinService.getService().getPrimaryContainer();
/* 104 */     this.primaryContainer = addContainer(primaryContainerHandle);
/*     */ 
/*     */     
/* 107 */     scanForContainers();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Collection<String> getPhaseProviderClasses() {
/* 114 */     Collection<String> phaseProviders = this.primaryContainer.getPhaseProviders();
/* 115 */     if (phaseProviders != null) {
/* 116 */       return Collections.unmodifiableCollection(phaseProviders);
/*     */     }
/*     */     
/* 119 */     return Collections.emptyList();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final MixinContainer addContainer(IContainerHandle handle) {
/* 130 */     MixinContainer existingContainer = this.containers.get(handle);
/* 131 */     if (existingContainer != null) {
/* 132 */       return existingContainer;
/*     */     }
/*     */     
/* 135 */     MixinContainer container = createContainerFor(handle);
/* 136 */     this.containers.put(handle, container);
/* 137 */     addNestedContainers(handle);
/* 138 */     return container;
/*     */   }
/*     */   
/*     */   private MixinContainer createContainerFor(IContainerHandle handle) {
/* 142 */     logger.debug("Adding mixin platform agents for container {}", new Object[] { handle });
/* 143 */     MixinContainer container = new MixinContainer(this, handle);
/* 144 */     if (this.prepared) {
/* 145 */       container.prepare();
/*     */     }
/* 147 */     return container;
/*     */   }
/*     */   
/*     */   private void addNestedContainers(IContainerHandle handle) {
/* 151 */     for (IContainerHandle nested : handle.getNestedContainers()) {
/* 152 */       if (!this.containers.containsKey(nested)) {
/* 153 */         addContainer(nested);
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final void prepare(CommandLineOptions args) {
/* 164 */     this.prepared = true;
/* 165 */     for (MixinContainer container : this.containers.values()) {
/* 166 */       container.prepare();
/*     */     }
/*     */     
/* 169 */     for (String config : args.getConfigs()) {
/* 170 */       addConfig(config);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final void inject() {
/* 178 */     if (this.injected) {
/*     */       return;
/*     */     }
/* 181 */     this.injected = true;
/*     */     
/* 183 */     if (this.primaryContainer != null) {
/* 184 */       this.primaryContainer.initPrimaryContainer();
/*     */     }
/*     */     
/* 187 */     scanForContainers();
/* 188 */     logger.debug("inject() running with {} agents", new Object[] { Integer.valueOf(this.containers.size()) });
/* 189 */     for (MixinContainer container : this.containers.values()) {
/*     */       try {
/* 191 */         container.inject();
/* 192 */       } catch (Exception ex) {
/* 193 */         ex.printStackTrace();
/*     */       } 
/*     */     } 
/*     */     
/* 197 */     this.connectors.inject();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void scanForContainers() {
/* 204 */     Collection<IContainerHandle> mixinContainers = null;
/*     */     
/*     */     try {
/* 207 */       mixinContainers = MixinService.getService().getMixinContainers();
/* 208 */     } catch (AbstractMethodError ame) {
/* 209 */       throw new ServiceVersionError("Mixin service is out of date");
/*     */     } 
/*     */     
/* 212 */     List<IContainerHandle> existingContainers = new ArrayList<>(this.containers.keySet());
/* 213 */     for (IContainerHandle existingContainer : existingContainers) {
/* 214 */       addNestedContainers(existingContainer);
/*     */     }
/*     */     
/* 217 */     for (IContainerHandle handle : mixinContainers) {
/*     */       try {
/* 219 */         logger.debug("Adding agents for Mixin Container {}", new Object[] { handle });
/* 220 */         addContainer(handle);
/* 221 */       } catch (Exception ex) {
/* 222 */         ex.printStackTrace();
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getLaunchTarget() {
/* 232 */     return "net.minecraft.client.main.Main";
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   final void setCompatibilityLevel(String level) {
/*     */     try {
/* 244 */       MixinEnvironment.CompatibilityLevel value = MixinEnvironment.CompatibilityLevel.valueOf(level.toUpperCase(Locale.ROOT));
/* 245 */       logger.debug("Setting mixin compatibility level: {}", new Object[] { value });
/* 246 */       MixinEnvironment.setCompatibilityLevel(value);
/* 247 */     } catch (IllegalArgumentException ex) {
/* 248 */       logger.warn("Invalid compatibility level specified: {}", new Object[] { level });
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
/*     */   final void addConfig(String config) {
/* 261 */     if (config.endsWith(".json")) {
/* 262 */       logger.debug("Registering mixin config: {}", new Object[] { config });
/* 263 */       Mixins.addConfiguration(config);
/* 264 */     } else if (config.contains(".json@")) {
/* 265 */       throw new MixinError("Setting config phase via manifest is no longer supported: " + config + ". Specify target in config instead");
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
/*     */   final void addTokenProvider(String provider) {
/* 279 */     if (provider.contains("@")) {
/* 280 */       String[] parts = provider.split("@", 2);
/* 281 */       MixinEnvironment.Phase phase = MixinEnvironment.Phase.forName(parts[1]);
/* 282 */       if (phase != null) {
/* 283 */         logger.debug("Registering token provider class: {}", new Object[] { parts[0] });
/* 284 */         MixinEnvironment.getEnvironment(phase).registerTokenProviderClass(parts[0]);
/*     */       } 
/*     */       
/*     */       return;
/*     */     } 
/* 289 */     MixinEnvironment.getDefaultEnvironment().registerTokenProviderClass(provider);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   final void addConnector(String connectorClass) {
/* 299 */     this.connectors.addConnector(connectorClass);
/*     */   }
/*     */ }


/* Location:              C:\Users\DuduM\OneDrive\Área de Trabalho\mw10\modularwarfare-shining-2023.2.4.4f-fix10.jar!\org\spongepowered\asm\launch\platform\MixinPlatformManager.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */