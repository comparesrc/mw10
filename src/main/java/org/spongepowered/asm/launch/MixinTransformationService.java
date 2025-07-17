/*    */ package org.spongepowered.asm.launch;
/*    */ 
/*    */ import com.google.common.collect.ImmutableList;
/*    */ import cpw.mods.modlauncher.api.IEnvironment;
/*    */ import cpw.mods.modlauncher.api.ITransformationService;
/*    */ import cpw.mods.modlauncher.api.ITransformer;
/*    */ import cpw.mods.modlauncher.api.IncompatibleEnvironmentException;
/*    */ import cpw.mods.modlauncher.serviceapi.ILaunchPluginService;
/*    */ import java.util.ArrayList;
/*    */ import java.util.List;
/*    */ import java.util.Optional;
/*    */ import java.util.Set;
/*    */ import java.util.function.BiFunction;
/*    */ import joptsimple.ArgumentAcceptingOptionSpec;
/*    */ import joptsimple.OptionSpec;
/*    */ import joptsimple.OptionSpecBuilder;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class MixinTransformationService
/*    */   implements ITransformationService
/*    */ {
/*    */   private ArgumentAcceptingOptionSpec<String> mixinsArgument;
/* 49 */   private List<String> commandLineMixins = new ArrayList<>();
/*    */   
/*    */   private MixinLaunchPlugin plugin;
/*    */   
/*    */   public String name() {
/* 54 */     return "mixin";
/*    */   }
/*    */ 
/*    */   
/*    */   public void arguments(BiFunction<String, String, OptionSpecBuilder> argumentBuilder) {
/* 59 */     this
/* 60 */       .mixinsArgument = ((OptionSpecBuilder)argumentBuilder.apply("config", "a mixin config to load")).withRequiredArg().ofType(String.class);
/*    */   }
/*    */ 
/*    */   
/*    */   public void argumentValues(ITransformationService.OptionResult option) {
/* 65 */     this.commandLineMixins.addAll(option.values((OptionSpec)this.mixinsArgument));
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public void onLoad(IEnvironment environment, Set<String> otherServices) throws IncompatibleEnvironmentException {}
/*    */ 
/*    */   
/*    */   public void initialize(IEnvironment environment) {
/* 74 */     Optional<ILaunchPluginService> plugin = environment.findLaunchPlugin("mixin");
/* 75 */     if (!plugin.isPresent()) {
/* 76 */       throw new MixinInitialisationError("Mixin Launch Plugin Service could not be located");
/*    */     }
/* 78 */     ILaunchPluginService launchPlugin = plugin.get();
/* 79 */     if (!(launchPlugin instanceof MixinLaunchPlugin)) {
/* 80 */       throw new MixinInitialisationError("Mixin Launch Plugin Service is present but not compatible");
/*    */     }
/* 82 */     this.plugin = (MixinLaunchPlugin)launchPlugin;
/*    */     
/* 84 */     MixinBootstrap.start();
/* 85 */     this.plugin.init(environment, this.commandLineMixins);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public void beginScanning(IEnvironment environment) {}
/*    */ 
/*    */ 
/*    */   
/*    */   public List<ITransformer> transformers() {
/* 95 */     return (List<ITransformer>)ImmutableList.of();
/*    */   }
/*    */ }


/* Location:              C:\Users\DuduM\OneDrive\Área de Trabalho\mw10\modularwarfare-shining-2023.2.4.4f-fix10.jar!\org\spongepowered\asm\launch\MixinTransformationService.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */