/*    */ package de.javagl.jgltf.model.image;
/*    */ 
/*    */ import java.nio.ByteBuffer;
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
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ final class DefaultPixelData
/*    */   implements PixelData
/*    */ {
/*    */   private final int width;
/*    */   private final int height;
/*    */   private final ByteBuffer pixelsRGBA;
/*    */   
/*    */   DefaultPixelData(int width, int height, ByteBuffer pixelsRGBA) {
/* 60 */     this.width = width;
/* 61 */     this.height = height;
/* 62 */     this.pixelsRGBA = pixelsRGBA;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public int getWidth() {
/* 68 */     return this.width;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public int getHeight() {
/* 74 */     return this.height;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public ByteBuffer getPixelsRGBA() {
/* 81 */     return this.pixelsRGBA.slice();
/*    */   }
/*    */ }


/* Location:              C:\Users\DuduM\OneDrive\Área de Trabalho\mw10\modularwarfare-shining-2023.2.4.4f-fix10.jar!\de\javagl\jgltf\model\image\DefaultPixelData.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */