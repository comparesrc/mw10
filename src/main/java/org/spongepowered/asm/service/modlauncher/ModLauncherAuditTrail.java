/*    */ package org.spongepowered.asm.service.modlauncher;
/*    */ 
/*    */ import java.util.function.Consumer;
/*    */ import org.spongepowered.asm.service.IMixinAuditTrail;
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
/*    */ public class ModLauncherAuditTrail
/*    */   implements IMixinAuditTrail
/*    */ {
/*    */   private static final String APPLY_MIXIN_ACTIVITY = "APP";
/*    */   private static final String POST_PROCESS_ACTIVITY = "DEC";
/*    */   private static final String GENERATE_ACTIVITY = "GEN";
/*    */   private String currentClass;
/*    */   private Consumer<String[]> consumer;
/*    */   
/*    */   public void setConsumer(String className, Consumer<String[]> consumer) {
/* 55 */     this.currentClass = className;
/* 56 */     this.consumer = consumer;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void onApply(String className, String mixinName) {
/* 65 */     writeActivity(className, new String[] { "APP", mixinName });
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void onPostProcess(String className) {
/* 74 */     writeActivity(className, new String[] { "DEC" });
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void onGenerate(String className, String generatorName) {
/* 83 */     writeActivity(className, new String[] { "GEN" });
/*    */   }
/*    */   
/*    */   private void writeActivity(String className, String... activity) {
/* 87 */     if (this.consumer != null && className.equals(this.currentClass))
/* 88 */       this.consumer.accept(activity); 
/*    */   }
/*    */ }


/* Location:              C:\Users\DuduM\OneDrive\Área de Trabalho\mw10\modularwarfare-shining-2023.2.4.4f-fix10.jar!\org\spongepowered\asm\service\modlauncher\ModLauncherAuditTrail.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */