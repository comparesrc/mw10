/*    */ package de.javagl.jgltf.impl.v2;
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
/*    */ public class MaterialOcclusionTextureInfo
/*    */   extends TextureInfo
/*    */ {
/*    */   private Float strength;
/*    */   
/*    */   public void setStrength(Float strength) {
/* 44 */     if (strength == null) {
/* 45 */       this.strength = strength;
/*    */       return;
/*    */     } 
/* 48 */     if (strength.floatValue() > 1.0D) {
/* 49 */       throw new IllegalArgumentException("strength > 1.0");
/*    */     }
/* 51 */     if (strength.floatValue() < 0.0D) {
/* 52 */       throw new IllegalArgumentException("strength < 0.0");
/*    */     }
/* 54 */     this.strength = strength;
/*    */   }
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
/*    */   public Float getStrength() {
/* 68 */     return this.strength;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public Float defaultStrength() {
/* 79 */     return Float.valueOf(1.0F);
/*    */   }
/*    */ }


/* Location:              C:\Users\DuduM\OneDrive\Área de Trabalho\mw10\modularwarfare-shining-2023.2.4.4f-fix10.jar!\de\javagl\jgltf\impl\v2\MaterialOcclusionTextureInfo.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */