/*    */ package org.spongepowered.asm.bridge;
/*    */ 
/*    */ import org.apache.logging.log4j.LogManager;
/*    */ import org.apache.logging.log4j.Logger;
/*    */ import org.objectweb.asm.commons.Remapper;
/*    */ import org.spongepowered.asm.mixin.extensibility.IRemapper;
/*    */ import org.spongepowered.asm.util.ObfuscationUtil;
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
/*    */ public abstract class RemapperAdapter
/*    */   implements IRemapper, ObfuscationUtil.IClassRemapper
/*    */ {
/* 39 */   protected final Logger logger = LogManager.getLogger("mixin");
/*    */   protected final Remapper remapper;
/*    */   
/*    */   public RemapperAdapter(Remapper remapper) {
/* 43 */     this.remapper = remapper;
/*    */   }
/*    */ 
/*    */   
/*    */   public String toString() {
/* 48 */     return getClass().getSimpleName();
/*    */   }
/*    */ 
/*    */   
/*    */   public String mapMethodName(String owner, String name, String desc) {
/* 53 */     this.logger.debug("{} is remapping method {}{} for {}", new Object[] { this, name, desc, owner });
/* 54 */     String newName = this.remapper.mapMethodName(owner, name, desc);
/* 55 */     if (!newName.equals(name)) {
/* 56 */       return newName;
/*    */     }
/* 58 */     String obfOwner = unmap(owner);
/* 59 */     String obfDesc = unmapDesc(desc);
/* 60 */     this.logger.debug("{} is remapping obfuscated method {}{} for {}", new Object[] { this, name, obfDesc, obfOwner });
/* 61 */     return this.remapper.mapMethodName(obfOwner, name, obfDesc);
/*    */   }
/*    */ 
/*    */   
/*    */   public String mapFieldName(String owner, String name, String desc) {
/* 66 */     this.logger.debug("{} is remapping field {}{} for {}", new Object[] { this, name, desc, owner });
/* 67 */     String newName = this.remapper.mapFieldName(owner, name, desc);
/* 68 */     if (!newName.equals(name)) {
/* 69 */       return newName;
/*    */     }
/* 71 */     String obfOwner = unmap(owner);
/* 72 */     String obfDesc = unmapDesc(desc);
/* 73 */     this.logger.debug("{} is remapping obfuscated field {}{} for {}", new Object[] { this, name, obfDesc, obfOwner });
/* 74 */     return this.remapper.mapFieldName(obfOwner, name, obfDesc);
/*    */   }
/*    */ 
/*    */   
/*    */   public String map(String typeName) {
/* 79 */     this.logger.debug("{} is remapping class {}", new Object[] { this, typeName });
/* 80 */     return this.remapper.map(typeName);
/*    */   }
/*    */ 
/*    */   
/*    */   public String unmap(String typeName) {
/* 85 */     return typeName;
/*    */   }
/*    */ 
/*    */   
/*    */   public String mapDesc(String desc) {
/* 90 */     return this.remapper.mapDesc(desc);
/*    */   }
/*    */ 
/*    */   
/*    */   public String unmapDesc(String desc) {
/* 95 */     return ObfuscationUtil.unmapDescriptor(desc, this);
/*    */   }
/*    */ }


/* Location:              C:\Users\DuduM\OneDrive\Área de Trabalho\mw10\modularwarfare-shining-2023.2.4.4f-fix10.jar!\org\spongepowered\asm\bridge\RemapperAdapter.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */