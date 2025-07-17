/*     */ package org.spongepowered.asm.mixin.transformer;
/*     */ 
/*     */ import java.text.DecimalFormat;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collections;
/*     */ import java.util.HashSet;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import java.util.Locale;
/*     */ import java.util.Set;
/*     */ import java.util.SortedSet;
/*     */ import java.util.TreeSet;
/*     */ import java.util.UUID;
/*     */ import org.apache.logging.log4j.Level;
/*     */ import org.apache.logging.log4j.LogManager;
/*     */ import org.apache.logging.log4j.Logger;
/*     */ import org.objectweb.asm.tree.AnnotationNode;
/*     */ import org.objectweb.asm.tree.ClassNode;
/*     */ import org.spongepowered.asm.mixin.Mixin;
/*     */ import org.spongepowered.asm.mixin.MixinEnvironment;
/*     */ import org.spongepowered.asm.mixin.Mixins;
/*     */ import org.spongepowered.asm.mixin.extensibility.IMixinConfig;
/*     */ import org.spongepowered.asm.mixin.extensibility.IMixinConfigPlugin;
/*     */ import org.spongepowered.asm.mixin.extensibility.IMixinErrorHandler;
/*     */ import org.spongepowered.asm.mixin.extensibility.IMixinInfo;
/*     */ import org.spongepowered.asm.mixin.throwables.ClassAlreadyLoadedException;
/*     */ import org.spongepowered.asm.mixin.throwables.MixinApplyError;
/*     */ import org.spongepowered.asm.mixin.throwables.MixinException;
/*     */ import org.spongepowered.asm.mixin.throwables.MixinPrepareError;
/*     */ import org.spongepowered.asm.mixin.transformer.ext.Extensions;
/*     */ import org.spongepowered.asm.mixin.transformer.ext.IHotSwap;
/*     */ import org.spongepowered.asm.mixin.transformer.ext.extensions.ExtensionCheckClass;
/*     */ import org.spongepowered.asm.mixin.transformer.ext.extensions.ExtensionClassExporter;
/*     */ import org.spongepowered.asm.mixin.transformer.throwables.IllegalClassLoadError;
/*     */ import org.spongepowered.asm.mixin.transformer.throwables.InvalidMixinException;
/*     */ import org.spongepowered.asm.mixin.transformer.throwables.MixinTransformerError;
/*     */ import org.spongepowered.asm.mixin.transformer.throwables.ReEntrantTransformerError;
/*     */ import org.spongepowered.asm.service.IMixinAuditTrail;
/*     */ import org.spongepowered.asm.service.IMixinService;
/*     */ import org.spongepowered.asm.service.MixinService;
/*     */ import org.spongepowered.asm.util.Annotations;
/*     */ import org.spongepowered.asm.util.PrettyPrinter;
/*     */ import org.spongepowered.asm.util.ReEntranceLock;
/*     */ import org.spongepowered.asm.util.perf.Profiler;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class MixinProcessor
/*     */ {
/*     */   enum ErrorPhase
/*     */   {
/*  82 */     PREPARE
/*     */     {
/*     */       IMixinErrorHandler.ErrorAction onError(IMixinErrorHandler handler, String context, InvalidMixinException ex, IMixinInfo mixin, IMixinErrorHandler.ErrorAction action) {
/*     */         try {
/*  86 */           return handler.onPrepareError(mixin.getConfig(), (Throwable)ex, mixin, action);
/*  87 */         } catch (AbstractMethodError ame) {
/*     */           
/*  89 */           return action;
/*     */         } 
/*     */       }
/*     */ 
/*     */       
/*     */       protected String getContext(IMixinInfo mixin, String context) {
/*  95 */         return String.format("preparing %s in %s", new Object[] { mixin.getName(), context
/*     */ 
/*     */             
/*     */             });
/*     */       }
/*     */     },
/* 101 */     APPLY
/*     */     {
/*     */       IMixinErrorHandler.ErrorAction onError(IMixinErrorHandler handler, String context, InvalidMixinException ex, IMixinInfo mixin, IMixinErrorHandler.ErrorAction action) {
/*     */         try {
/* 105 */           return handler.onApplyError(context, (Throwable)ex, mixin, action);
/* 106 */         } catch (AbstractMethodError ame) {
/*     */           
/* 108 */           return action;
/*     */         } 
/*     */       }
/*     */ 
/*     */       
/*     */       protected String getContext(IMixinInfo mixin, String context) {
/* 114 */         return String.format("%s -> %s", new Object[] { mixin, context });
/*     */       }
/*     */     };
/*     */ 
/*     */ 
/*     */     
/*     */     private final String text;
/*     */ 
/*     */     
/*     */     ErrorPhase() {
/* 124 */       this.text = name().toLowerCase(Locale.ROOT);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public String getLogMessage(String context, InvalidMixinException ex, IMixinInfo mixin) {
/* 132 */       return String.format("Mixin %s failed %s: %s %s", new Object[] { this.text, getContext(mixin, context), ex.getClass().getName(), ex.getMessage() });
/*     */     }
/*     */     
/*     */     public String getErrorMessage(IMixinInfo mixin, IMixinConfig config, MixinEnvironment.Phase phase) {
/* 136 */       return String.format("Mixin [%s] from phase [%s] in config [%s] FAILED during %s", new Object[] { mixin, phase, config, name() });
/*     */     }
/*     */ 
/*     */     
/*     */     abstract IMixinErrorHandler.ErrorAction onError(IMixinErrorHandler param1IMixinErrorHandler, String param1String, InvalidMixinException param1InvalidMixinException, IMixinInfo param1IMixinInfo, IMixinErrorHandler.ErrorAction param1ErrorAction);
/*     */     
/*     */     protected abstract String getContext(IMixinInfo param1IMixinInfo, String param1String);
/*     */   }
/* 144 */   static final Logger logger = LogManager.getLogger("mixin");
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 149 */   private final IMixinService service = MixinService.getService();
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 154 */   private final List<MixinConfig> configs = new ArrayList<>();
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 159 */   private final List<MixinConfig> pendingConfigs = new ArrayList<>();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private final ReEntranceLock lock;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 171 */   private final String sessionId = UUID.randomUUID().toString();
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private final Extensions extensions;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private final IHotSwap hotSwapper;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private final MixinPostProcessor postProcessor;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private final Profiler profiler;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private final IMixinAuditTrail auditTrail;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private MixinEnvironment currentEnvironment;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 206 */   private Level verboseLoggingLevel = Level.DEBUG;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean errorState = false;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 216 */   private int transformedCount = 0;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   MixinProcessor(MixinEnvironment environment, Extensions extensions, IHotSwap hotSwapper) {
/* 222 */     this.lock = this.service.getReEntranceLock();
/*     */     
/* 224 */     this.extensions = extensions;
/* 225 */     this.hotSwapper = hotSwapper;
/* 226 */     this.postProcessor = new MixinPostProcessor(this.sessionId);
/*     */     
/* 228 */     this.profiler = MixinEnvironment.getProfiler();
/* 229 */     this.auditTrail = this.service.getAuditTrail();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void audit(MixinEnvironment environment) {
/* 238 */     Set<String> unhandled = new HashSet<>();
/*     */     
/* 240 */     for (MixinConfig config : this.configs) {
/* 241 */       unhandled.addAll(config.getUnhandledTargets());
/*     */     }
/*     */     
/* 244 */     Logger auditLogger = LogManager.getLogger("mixin/audit");
/*     */     
/* 246 */     for (String target : unhandled) {
/*     */       try {
/* 248 */         auditLogger.info("Force-loading class {}", new Object[] { target });
/* 249 */         this.service.getClassProvider().findClass(target, true);
/* 250 */       } catch (ClassNotFoundException ex) {
/* 251 */         auditLogger.error("Could not force-load " + target, ex);
/*     */       } 
/*     */     } 
/*     */     
/* 255 */     for (MixinConfig config : this.configs) {
/* 256 */       for (String target : config.getUnhandledTargets()) {
/* 257 */         ClassAlreadyLoadedException ex = new ClassAlreadyLoadedException(target + " was already classloaded");
/* 258 */         auditLogger.error("Could not force-load " + target, (Throwable)ex);
/*     */       } 
/*     */     } 
/*     */     
/* 262 */     if (environment.getOption(MixinEnvironment.Option.DEBUG_PROFILER)) {
/* 263 */       this.profiler.printSummary();
/*     */     }
/*     */   }
/*     */   
/*     */   synchronized boolean applyMixins(MixinEnvironment environment, String name, ClassNode targetClassNode) {
/* 268 */     if (name == null || this.errorState) {
/* 269 */       return false;
/*     */     }
/*     */     
/* 272 */     boolean locked = this.lock.push().check();
/* 273 */     Profiler.Section mixinTimer = this.profiler.begin("mixin");
/*     */     
/* 275 */     if (locked) {
/* 276 */       for (MixinConfig config : this.pendingConfigs) {
/* 277 */         if (config.hasPendingMixinsFor(name)) {
/* 278 */           ReEntrantTransformerError error = new ReEntrantTransformerError("Re-entrance error.");
/* 279 */           logger.warn("Re-entrance detected during prepare phase, this will cause serious problems.", (Throwable)error);
/* 280 */           throw error;
/*     */         } 
/*     */       } 
/*     */     } else {
/*     */       try {
/* 285 */         checkSelect(environment);
/* 286 */       } catch (Exception ex) {
/* 287 */         this.lock.pop();
/* 288 */         mixinTimer.end();
/* 289 */         throw new MixinException(ex);
/*     */       } 
/*     */     } 
/*     */     
/* 293 */     boolean success = false;
/*     */     
/*     */     try {
/* 296 */       if (this.postProcessor.canProcess(name)) {
/* 297 */         if (this.auditTrail != null) {
/* 298 */           this.auditTrail.onPostProcess(name);
/*     */         }
/* 300 */         Profiler.Section postTimer = this.profiler.begin("postprocessor");
/* 301 */         success = this.postProcessor.processClass(name, targetClassNode);
/* 302 */         postTimer.end();
/* 303 */         this.extensions.export(environment, name, false, targetClassNode);
/* 304 */         return success;
/*     */       } 
/*     */       
/* 307 */       SortedSet<MixinInfo> mixins = null;
/* 308 */       MixinConfig packageOwnedByConfig = null;
/*     */       
/* 310 */       for (MixinConfig config : this.configs) {
/* 311 */         if (config.packageMatch(name)) {
/* 312 */           int packageLen = (packageOwnedByConfig != null) ? packageOwnedByConfig.getMixinPackage().length() : 0;
/* 313 */           if (config.getMixinPackage().length() > packageLen) {
/* 314 */             packageOwnedByConfig = config;
/*     */           }
/*     */           
/*     */           continue;
/*     */         } 
/* 319 */         if (config.hasMixinsFor(name)) {
/* 320 */           if (mixins == null) {
/* 321 */             mixins = new TreeSet<>();
/*     */           }
/*     */ 
/*     */           
/* 325 */           mixins.addAll(config.getMixinsFor(name));
/*     */         } 
/*     */       } 
/*     */       
/* 329 */       if (packageOwnedByConfig != null) {
/* 330 */         throw new IllegalClassLoadError(getInvalidClassError(name, targetClassNode, packageOwnedByConfig));
/*     */       }
/*     */       
/* 333 */       if (mixins != null) {
/*     */         
/* 335 */         if (locked) {
/* 336 */           ReEntrantTransformerError error = new ReEntrantTransformerError("Re-entrance error.");
/* 337 */           logger.warn("Re-entrance detected, this will cause serious problems.", (Throwable)error);
/* 338 */           throw error;
/*     */         } 
/*     */         
/* 341 */         if (this.hotSwapper != null) {
/* 342 */           this.hotSwapper.registerTargetClass(name, targetClassNode);
/*     */         }
/*     */ 
/*     */         
/*     */         try {
/* 347 */           Profiler.Section timer = this.profiler.begin("read");
/* 348 */           TargetClassContext context = new TargetClassContext(environment, this.extensions, this.sessionId, name, targetClassNode, mixins);
/*     */           
/* 350 */           timer.end();
/* 351 */           applyMixins(environment, context);
/* 352 */           this.transformedCount++;
/* 353 */           success = true;
/* 354 */         } catch (InvalidMixinException th) {
/* 355 */           dumpClassOnFailure(name, targetClassNode, environment);
/* 356 */           handleMixinApplyError(name, th, environment);
/*     */         } 
/*     */       } 
/* 359 */     } catch (MixinTransformerError er) {
/* 360 */       throw er;
/* 361 */     } catch (Throwable th) {
/* 362 */       th.printStackTrace();
/* 363 */       dumpClassOnFailure(name, targetClassNode, environment);
/* 364 */       throw new MixinTransformerError("An unexpected critical error was encountered", th);
/*     */     } finally {
/* 366 */       this.lock.pop();
/* 367 */       mixinTimer.end();
/*     */     } 
/* 369 */     return success;
/*     */   }
/*     */   
/*     */   private String getInvalidClassError(String name, ClassNode targetClassNode, MixinConfig ownedByConfig) {
/* 373 */     if (ownedByConfig.getClasses().contains(name)) {
/* 374 */       return String.format("Illegal classload request for %s. Mixin is defined in %s and cannot be referenced directly", new Object[] { name, ownedByConfig });
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 383 */     AnnotationNode mixin = Annotations.getInvisible(targetClassNode, Mixin.class);
/* 384 */     if (mixin != null) {
/* 385 */       MixinInfo.Variant variant = MixinInfo.getVariant(targetClassNode);
/* 386 */       if (variant == MixinInfo.Variant.ACCESSOR) {
/* 387 */         return String.format("Illegal classload request for accessor mixin %s. The mixin is missing from %s which owns package %s* and the mixin has not been applied.", new Object[] { name, ownedByConfig, ownedByConfig
/* 388 */               .getMixinPackage() });
/*     */       }
/*     */     } 
/*     */     
/* 392 */     return String.format("%s is in a defined mixin package %s* owned by %s and cannot be referenced directly", new Object[] { name, ownedByConfig
/* 393 */           .getMixinPackage(), ownedByConfig });
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public List<String> reload(String mixinClass, ClassNode classNode) {
/* 404 */     if (this.lock.getDepth() > 0) {
/* 405 */       throw new MixinApplyError("Cannot reload mixin if re-entrant lock entered");
/*     */     }
/* 407 */     List<String> targets = new ArrayList<>();
/* 408 */     for (MixinConfig config : this.configs) {
/* 409 */       targets.addAll(config.reloadMixin(mixinClass, classNode));
/*     */     }
/* 411 */     return targets;
/*     */   }
/*     */   
/*     */   private void checkSelect(MixinEnvironment environment) {
/* 415 */     if (this.currentEnvironment != environment) {
/* 416 */       select(environment);
/*     */       
/*     */       return;
/*     */     } 
/* 420 */     int unvisitedCount = Mixins.getUnvisitedCount();
/* 421 */     if (unvisitedCount > 0 && this.transformedCount == 0) {
/* 422 */       select(environment);
/*     */     }
/*     */   }
/*     */   
/*     */   private void select(MixinEnvironment environment) {
/* 427 */     this.verboseLoggingLevel = environment.getOption(MixinEnvironment.Option.DEBUG_VERBOSE) ? Level.INFO : Level.DEBUG;
/* 428 */     if (this.transformedCount > 0) {
/* 429 */       logger.log(this.verboseLoggingLevel, "Ending {}, applied {} mixins", new Object[] { this.currentEnvironment, Integer.valueOf(this.transformedCount) });
/*     */     }
/* 431 */     String action = (this.currentEnvironment == environment) ? "Checking for additional" : "Preparing";
/* 432 */     logger.log(this.verboseLoggingLevel, "{} mixins for {}", new Object[] { action, environment });
/*     */     
/* 434 */     this.profiler.setActive(true);
/* 435 */     this.profiler.mark(environment.getPhase().toString() + ":prepare");
/* 436 */     Profiler.Section prepareTimer = this.profiler.begin("prepare");
/*     */     
/* 438 */     selectConfigs(environment);
/* 439 */     this.extensions.select(environment);
/* 440 */     int totalMixins = prepareConfigs(environment);
/* 441 */     this.currentEnvironment = environment;
/* 442 */     this.transformedCount = 0;
/*     */     
/* 444 */     prepareTimer.end();
/*     */     
/* 446 */     long elapsedMs = prepareTimer.getTime();
/* 447 */     double elapsedTime = prepareTimer.getSeconds();
/* 448 */     if (elapsedTime > 0.25D) {
/* 449 */       long loadTime = this.profiler.get("class.load").getTime();
/* 450 */       long transformTime = this.profiler.get("class.transform").getTime();
/* 451 */       long pluginTime = this.profiler.get("mixin.plugin").getTime();
/* 452 */       String elapsed = (new DecimalFormat("###0.000")).format(elapsedTime);
/* 453 */       String perMixinTime = (new DecimalFormat("###0.0")).format(elapsedMs / totalMixins);
/*     */       
/* 455 */       logger.log(this.verboseLoggingLevel, "Prepared {} mixins in {} sec ({}ms avg) ({}ms load, {}ms transform, {}ms plugin)", new Object[] {
/* 456 */             Integer.valueOf(totalMixins), elapsed, perMixinTime, Long.valueOf(loadTime), Long.valueOf(transformTime), Long.valueOf(pluginTime)
/*     */           });
/*     */     } 
/* 459 */     this.profiler.mark(environment.getPhase().toString() + ":apply");
/* 460 */     this.profiler.setActive(environment.getOption(MixinEnvironment.Option.DEBUG_PROFILER));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void selectConfigs(MixinEnvironment environment) {
/* 469 */     for (Iterator<Config> iter = Mixins.getConfigs().iterator(); iter.hasNext(); ) {
/* 470 */       Config handle = iter.next();
/*     */       try {
/* 472 */         MixinConfig config = handle.get();
/* 473 */         if (config.select(environment)) {
/* 474 */           iter.remove();
/* 475 */           logger.log(this.verboseLoggingLevel, "Selecting config {}", new Object[] { config });
/* 476 */           config.onSelect();
/* 477 */           this.pendingConfigs.add(config);
/*     */         } 
/* 479 */       } catch (Exception ex) {
/* 480 */         logger.warn(String.format("Failed to select mixin config: %s", new Object[] { handle }), ex);
/*     */       } 
/*     */     } 
/*     */     
/* 484 */     Collections.sort(this.pendingConfigs);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private int prepareConfigs(MixinEnvironment environment) {
/* 494 */     int totalMixins = 0;
/*     */     
/* 496 */     final IHotSwap hotSwapper = this.hotSwapper;
/* 497 */     for (MixinConfig config : this.pendingConfigs) {
/* 498 */       config.addListener(this.postProcessor);
/* 499 */       if (hotSwapper != null) {
/* 500 */         config.addListener(new MixinConfig.IListener()
/*     */             {
/*     */               public void onPrepare(MixinInfo mixin) {
/* 503 */                 hotSwapper.registerMixinClass(mixin.getClassName());
/*     */               }
/*     */ 
/*     */ 
/*     */               
/*     */               public void onInit(MixinInfo mixin) {}
/*     */             });
/*     */       }
/*     */     } 
/* 512 */     for (MixinConfig config : this.pendingConfigs) {
/*     */       try {
/* 514 */         logger.log(this.verboseLoggingLevel, "Preparing {} ({})", new Object[] { config, Integer.valueOf(config.getDeclaredMixinCount()) });
/* 515 */         config.prepare();
/* 516 */         totalMixins += config.getMixinCount();
/* 517 */       } catch (InvalidMixinException ex) {
/* 518 */         handleMixinPrepareError(config, ex, environment);
/* 519 */       } catch (Exception ex) {
/* 520 */         String message = ex.getMessage();
/* 521 */         logger.error("Error encountered whilst initialising mixin config '" + config.getName() + "': " + message, ex);
/*     */       } 
/*     */     } 
/*     */     
/* 525 */     for (MixinConfig config : this.pendingConfigs) {
/* 526 */       IMixinConfigPlugin plugin = config.getPlugin();
/* 527 */       if (plugin == null) {
/*     */         continue;
/*     */       }
/*     */       
/* 531 */       Set<String> otherTargets = new HashSet<>();
/* 532 */       for (MixinConfig otherConfig : this.pendingConfigs) {
/* 533 */         if (!otherConfig.equals(config)) {
/* 534 */           otherTargets.addAll(otherConfig.getTargets());
/*     */         }
/*     */       } 
/*     */       
/* 538 */       plugin.acceptTargets(config.getTargets(), Collections.unmodifiableSet(otherTargets));
/*     */     } 
/*     */     
/* 541 */     for (MixinConfig config : this.pendingConfigs) {
/*     */       try {
/* 543 */         config.postInitialise();
/* 544 */       } catch (InvalidMixinException ex) {
/* 545 */         handleMixinPrepareError(config, ex, environment);
/* 546 */       } catch (Exception ex) {
/* 547 */         String message = ex.getMessage();
/* 548 */         logger.error("Error encountered during mixin config postInit step'" + config.getName() + "': " + message, ex);
/*     */       } 
/*     */     } 
/*     */     
/* 552 */     this.configs.addAll(this.pendingConfigs);
/* 553 */     Collections.sort(this.configs);
/* 554 */     this.pendingConfigs.clear();
/*     */     
/* 556 */     return totalMixins;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void applyMixins(MixinEnvironment environment, TargetClassContext context) {
/*     */     int i;
/* 567 */     Profiler.Section timer = this.profiler.begin("preapply");
/* 568 */     this.extensions.preApply(context);
/* 569 */     timer = timer.next("apply");
/* 570 */     context.applyMixins();
/* 571 */     timer = timer.next("postapply");
/* 572 */     boolean export = false;
/*     */     try {
/* 574 */       this.extensions.postApply(context);
/* 575 */       export = true;
/* 576 */     } catch (org.spongepowered.asm.mixin.transformer.ext.extensions.ExtensionCheckClass.ValidationFailedException ex) {
/* 577 */       logger.info(ex.getMessage());
/*     */       
/* 579 */       i = export | ((context.isExportForced() || environment.getOption(MixinEnvironment.Option.DEBUG_EXPORT)) ? 1 : 0);
/*     */     } 
/* 581 */     timer.end();
/* 582 */     if (i != 0) {
/* 583 */       this.extensions.export(this.currentEnvironment, context.getClassName(), context.isExportForced(), context.getClassNode());
/*     */     }
/* 585 */     for (InvalidMixinException suppressed : context.getSuppressedExceptions()) {
/* 586 */       handleMixinApplyError(context.getClassName(), suppressed, environment);
/*     */     }
/*     */   }
/*     */   
/*     */   private void handleMixinPrepareError(MixinConfig config, InvalidMixinException ex, MixinEnvironment environment) throws MixinPrepareError {
/* 591 */     handleMixinError(config.getName(), ex, environment, ErrorPhase.PREPARE);
/*     */   }
/*     */   
/*     */   private void handleMixinApplyError(String targetClass, InvalidMixinException ex, MixinEnvironment environment) throws MixinApplyError {
/* 595 */     handleMixinError(targetClass, ex, environment, ErrorPhase.APPLY);
/*     */   }
/*     */   
/*     */   private void handleMixinError(String context, InvalidMixinException ex, MixinEnvironment environment, ErrorPhase errorPhase) throws Error {
/* 599 */     this.errorState = true;
/*     */     
/* 601 */     IMixinInfo mixin = ex.getMixin();
/*     */     
/* 603 */     if (mixin == null) {
/* 604 */       logger.error("InvalidMixinException has no mixin!", (Throwable)ex);
/* 605 */       throw ex;
/*     */     } 
/*     */     
/* 608 */     IMixinConfig config = mixin.getConfig();
/* 609 */     MixinEnvironment.Phase phase = mixin.getPhase();
/* 610 */     IMixinErrorHandler.ErrorAction action = config.isRequired() ? IMixinErrorHandler.ErrorAction.ERROR : IMixinErrorHandler.ErrorAction.WARN;
/*     */     
/* 612 */     if (environment.getOption(MixinEnvironment.Option.DEBUG_VERBOSE)) {
/* 613 */       (new PrettyPrinter())
/* 614 */         .wrapTo(160)
/* 615 */         .add("Invalid Mixin").centre()
/* 616 */         .hr('-')
/* 617 */         .kvWidth(10)
/* 618 */         .kv("Action", errorPhase.name())
/* 619 */         .kv("Mixin", mixin.getClassName())
/* 620 */         .kv("Config", config.getName())
/* 621 */         .kv("Phase", phase)
/* 622 */         .hr('-')
/* 623 */         .add("    %s", new Object[] { ex.getClass().getName()
/* 624 */           }).hr('-')
/* 625 */         .addWrapped("    %s", new Object[] { ex.getMessage()
/* 626 */           }).hr('-')
/* 627 */         .add((Throwable)ex, 8)
/* 628 */         .log(action.logLevel);
/*     */     }
/*     */     
/* 631 */     for (IMixinErrorHandler handler : getErrorHandlers(mixin.getPhase())) {
/* 632 */       IMixinErrorHandler.ErrorAction newAction = errorPhase.onError(handler, context, ex, mixin, action);
/* 633 */       if (newAction != null) {
/* 634 */         action = newAction;
/*     */       }
/*     */     } 
/*     */     
/* 638 */     logger.log(action.logLevel, errorPhase.getLogMessage(context, ex, mixin), (Throwable)ex);
/*     */     
/* 640 */     this.errorState = false;
/*     */     
/* 642 */     if (action == IMixinErrorHandler.ErrorAction.ERROR) {
/* 643 */       throw new MixinApplyError(errorPhase.getErrorMessage(mixin, config, phase), ex);
/*     */     }
/*     */   }
/*     */   
/*     */   private List<IMixinErrorHandler> getErrorHandlers(MixinEnvironment.Phase phase) {
/* 648 */     List<IMixinErrorHandler> handlers = new ArrayList<>();
/*     */     
/* 650 */     for (String handlerClassName : Mixins.getErrorHandlerClasses()) {
/*     */       try {
/* 652 */         logger.info("Instancing error handler class {}", new Object[] { handlerClassName });
/* 653 */         Class<?> handlerClass = this.service.getClassProvider().findClass(handlerClassName, true);
/* 654 */         IMixinErrorHandler handler = (IMixinErrorHandler)handlerClass.newInstance();
/* 655 */         if (handler != null) {
/* 656 */           handlers.add(handler);
/*     */         }
/* 658 */       } catch (Throwable throwable) {}
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 663 */     return handlers;
/*     */   }
/*     */   
/*     */   private void dumpClassOnFailure(String className, ClassNode classNode, MixinEnvironment env) {
/* 667 */     if (env.getOption(MixinEnvironment.Option.DUMP_TARGET_ON_FAILURE)) {
/* 668 */       ExtensionClassExporter exporter = (ExtensionClassExporter)this.extensions.getExtension(ExtensionClassExporter.class);
/* 669 */       exporter.dumpClass(className.replace('.', '/') + ".target", classNode);
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\DuduM\OneDrive\Área de Trabalho\mw10\modularwarfare-shining-2023.2.4.4f-fix10.jar!\org\spongepowered\asm\mixin\transformer\MixinProcessor.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */