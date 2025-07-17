/*    */ package org.spongepowered.asm.launch.platform;
/*    */ 
/*    */ import java.lang.reflect.Method;
/*    */ import org.apache.logging.log4j.LogManager;
/*    */ import org.apache.logging.log4j.Logger;
/*    */ import org.spongepowered.asm.launch.platform.container.IContainerHandle;
/*    */ import org.spongepowered.asm.mixin.MixinEnvironment;
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
/*    */ 
/*    */ 
/*    */ public abstract class MixinPlatformAgentAbstract
/*    */   implements IMixinPlatformAgent
/*    */ {
/* 43 */   protected static final Logger logger = LogManager.getLogger("mixin");
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   protected MixinPlatformManager manager;
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   protected IContainerHandle handle;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public IMixinPlatformAgent.AcceptResult accept(MixinPlatformManager manager, IContainerHandle handle) {
/* 60 */     this.manager = manager;
/* 61 */     this.handle = handle;
/* 62 */     return IMixinPlatformAgent.AcceptResult.ACCEPTED;
/*    */   }
/*    */ 
/*    */   
/*    */   public String getPhaseProvider() {
/* 67 */     return null;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public void prepare() {}
/*    */ 
/*    */ 
/*    */   
/*    */   public void initPrimaryContainer() {}
/*    */ 
/*    */ 
/*    */   
/*    */   public void inject() {}
/*    */ 
/*    */   
/*    */   public String toString() {
/* 84 */     return String.format("PlatformAgent[%s:%s]", new Object[] { getClass().getSimpleName(), this.handle });
/*    */   }
/*    */   
/*    */   protected static String invokeStringMethod(ClassLoader classLoader, String className, String methodName) {
/*    */     try {
/* 89 */       Class<?> clazz = Class.forName(className, false, classLoader);
/* 90 */       Method method = clazz.getDeclaredMethod(methodName, new Class[0]);
/* 91 */       return ((Enum)method.invoke(null, new Object[0])).name();
/* 92 */     } catch (Exception ex) {
/* 93 */       return null;
/*    */     } 
/*    */   }
/*    */   
/*    */   @Deprecated
/*    */   public void wire(MixinEnvironment.Phase phase, IConsumer<MixinEnvironment.Phase> phaseConsumer) {}
/*    */   
/*    */   @Deprecated
/*    */   public void unwire() {}
/*    */ }


/* Location:              C:\Users\DuduM\OneDrive\Área de Trabalho\mw10\modularwarfare-shining-2023.2.4.4f-fix10.jar!\org\spongepowered\asm\launch\platform\MixinPlatformAgentAbstract.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */