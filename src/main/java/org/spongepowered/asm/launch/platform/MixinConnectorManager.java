/*    */ package org.spongepowered.asm.launch.platform;
/*    */ 
/*    */ import java.util.ArrayList;
/*    */ import java.util.LinkedHashSet;
/*    */ import java.util.List;
/*    */ import java.util.Set;
/*    */ import org.apache.logging.log4j.LogManager;
/*    */ import org.apache.logging.log4j.Logger;
/*    */ import org.spongepowered.asm.mixin.connect.IMixinConnector;
/*    */ import org.spongepowered.asm.service.IClassProvider;
/*    */ import org.spongepowered.asm.service.MixinService;
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
/*    */ public class MixinConnectorManager
/*    */ {
/* 46 */   private static final Logger logger = LogManager.getLogger("mixin");
/*    */   
/* 48 */   private final Set<String> connectorClasses = new LinkedHashSet<>();
/*    */   
/* 50 */   private final List<IMixinConnector> connectors = new ArrayList<>();
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   void addConnector(String connectorClass) {
/* 56 */     this.connectorClasses.add(connectorClass);
/*    */   }
/*    */   
/*    */   void inject() {
/* 60 */     loadConnectors();
/* 61 */     initConnectors();
/*    */   }
/*    */ 
/*    */   
/*    */   void loadConnectors() {
/* 66 */     IClassProvider classProvider = MixinService.getService().getClassProvider();
/*    */     
/* 68 */     for (String connectorClassName : this.connectorClasses) {
/* 69 */       Class<IMixinConnector> connectorClass = null;
/*    */       try {
/* 71 */         Class<?> clazz = classProvider.findClass(connectorClassName);
/* 72 */         if (!IMixinConnector.class.isAssignableFrom(clazz)) {
/* 73 */           logger.error("Mixin Connector [" + connectorClassName + "] does not implement IMixinConnector");
/*    */           continue;
/*    */         } 
/* 76 */         connectorClass = (Class)clazz;
/* 77 */       } catch (ClassNotFoundException ex) {
/* 78 */         logger.catching(ex);
/*    */         
/*    */         continue;
/*    */       } 
/*    */       try {
/* 83 */         IMixinConnector connector = connectorClass.newInstance();
/* 84 */         this.connectors.add(connector);
/* 85 */         logger.info("Successfully loaded Mixin Connector [" + connectorClassName + "]");
/* 86 */       } catch (ReflectiveOperationException ex) {
/* 87 */         logger.warn("Error loading Mixin Connector [" + connectorClassName + "]", ex);
/*    */       } 
/*    */     } 
/* 90 */     this.connectorClasses.clear();
/*    */   }
/*    */   
/*    */   void initConnectors() {
/* 94 */     for (IMixinConnector connector : this.connectors) {
/*    */       try {
/* 96 */         connector.connect();
/* 97 */       } catch (Exception ex) {
/* 98 */         logger.warn("Error initialising Mixin Connector [" + connector.getClass().getName() + "]", ex);
/*    */       } 
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\DuduM\OneDrive\Área de Trabalho\mw10\modularwarfare-shining-2023.2.4.4f-fix10.jar!\org\spongepowered\asm\launch\platform\MixinConnectorManager.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */