/*    */ package org.spongepowered.asm.mixin.transformer;
/*    */ 
/*    */ import com.google.common.base.Preconditions;
/*    */ import org.spongepowered.asm.mixin.extensibility.IMixinInfo;
/*    */ import org.spongepowered.asm.service.ISyntheticClassInfo;
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
/*    */ public abstract class SyntheticClassInfo
/*    */   implements ISyntheticClassInfo
/*    */ {
/*    */   protected final IMixinInfo mixin;
/*    */   protected final String name;
/*    */   
/*    */   protected SyntheticClassInfo(IMixinInfo mixin, String name) {
/* 48 */     Preconditions.checkNotNull(mixin, "parent");
/* 49 */     Preconditions.checkNotNull(name, "name");
/*    */     
/* 51 */     this.mixin = mixin;
/* 52 */     this.name = name.replace('.', '/');
/*    */   }
/*    */ 
/*    */   
/*    */   public final IMixinInfo getMixin() {
/* 57 */     return this.mixin;
/*    */   }
/*    */ 
/*    */   
/*    */   public String getName() {
/* 62 */     return this.name;
/*    */   }
/*    */ 
/*    */   
/*    */   public String getClassName() {
/* 67 */     return this.name.replace('/', '.');
/*    */   }
/*    */ }


/* Location:              C:\Users\DuduM\OneDrive\Área de Trabalho\mw10\modularwarfare-shining-2023.2.4.4f-fix10.jar!\org\spongepowered\asm\mixin\transformer\SyntheticClassInfo.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */