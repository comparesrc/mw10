/*    */ package org.spongepowered.asm.mixin.transformer;
/*    */ 
/*    */ import org.spongepowered.asm.mixin.MixinEnvironment;
/*    */ import org.spongepowered.asm.mixin.injection.invoke.arg.ArgsClassGenerator;
/*    */ import org.spongepowered.asm.mixin.transformer.ext.Extensions;
/*    */ import org.spongepowered.asm.mixin.transformer.ext.IClassGenerator;
/*    */ import org.spongepowered.asm.mixin.transformer.ext.IExtension;
/*    */ import org.spongepowered.asm.mixin.transformer.ext.extensions.ExtensionCheckClass;
/*    */ import org.spongepowered.asm.mixin.transformer.ext.extensions.ExtensionCheckInterfaces;
/*    */ import org.spongepowered.asm.mixin.transformer.ext.extensions.ExtensionClassExporter;
/*    */ import org.spongepowered.asm.service.ISyntheticClassInfo;
/*    */ import org.spongepowered.asm.util.IConsumer;
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
/*    */ 
/*    */ final class DefaultExtensions
/*    */ {
/*    */   static void create(MixinEnvironment environment, Extensions extensions, final SyntheticClassRegistry registry) {
/* 45 */     IConsumer<ISyntheticClassInfo> registryDelegate = new IConsumer<ISyntheticClassInfo>()
/*    */       {
/*    */         public void accept(ISyntheticClassInfo item) {
/* 48 */           registry.registerSyntheticClass(item);
/*    */         }
/*    */       };
/*    */     
/* 52 */     extensions.add((IClassGenerator)new ArgsClassGenerator(registryDelegate));
/* 53 */     extensions.add(new InnerClassGenerator(registryDelegate));
/*    */     
/* 55 */     extensions.add((IExtension)new ExtensionClassExporter(environment));
/* 56 */     extensions.add((IExtension)new ExtensionCheckClass());
/* 57 */     extensions.add((IExtension)new ExtensionCheckInterfaces());
/*    */   }
/*    */ }


/* Location:              C:\Users\DuduM\OneDrive\Área de Trabalho\mw10\modularwarfare-shining-2023.2.4.4f-fix10.jar!\org\spongepowered\asm\mixin\transformer\DefaultExtensions.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */