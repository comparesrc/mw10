/*    */ package org.spongepowered.asm.service.mojang;
/*    */ 
/*    */ import javax.annotation.Resource;
/*    */ import net.minecraft.launchwrapper.IClassTransformer;
/*    */ import org.spongepowered.asm.service.ILegacyClassTransformer;
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
/*    */ class LegacyTransformerHandle
/*    */   implements ILegacyClassTransformer
/*    */ {
/*    */   private final IClassTransformer transformer;
/*    */   
/*    */   LegacyTransformerHandle(IClassTransformer transformer) {
/* 45 */     this.transformer = transformer;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public String getName() {
/* 53 */     return this.transformer.getClass().getName();
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean isDelegationExcluded() {
/* 62 */     return (this.transformer.getClass().getAnnotation(Resource.class) != null);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public byte[] transformClassBytes(String name, String transformedName, byte[] basicClass) {
/* 71 */     return this.transformer.transform(name, transformedName, basicClass);
/*    */   }
/*    */ }


/* Location:              C:\Users\DuduM\OneDrive\Área de Trabalho\mw10\modularwarfare-shining-2023.2.4.4f-fix10.jar!\org\spongepowered\asm\service\mojang\LegacyTransformerHandle.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */