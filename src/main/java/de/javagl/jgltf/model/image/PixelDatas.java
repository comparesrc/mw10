/*    */ package de.javagl.jgltf.model.image;
/*    */ 
/*    */ import java.awt.image.BufferedImage;
/*    */ import java.nio.ByteBuffer;
/*    */ import java.nio.ByteOrder;
/*    */ import java.nio.IntBuffer;
/*    */ import java.util.logging.Logger;
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
/*    */ public class PixelDatas
/*    */ {
/* 46 */   private static final Logger logger = Logger.getLogger(PixelDatas.class.getName());
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
/*    */   public static PixelData create(ByteBuffer imageData) {
/* 60 */     BufferedImage textureImage = ImageUtils.readAsBufferedImage(imageData);
/* 61 */     if (textureImage == null) {
/*    */       
/* 63 */       logger.warning("Could not read image from image data");
/* 64 */       return null;
/*    */     } 
/*    */ 
/*    */     
/* 68 */     ByteBuffer pixelDataARGB = ImageUtils.getImagePixelsARGB(textureImage, false);
/*    */     
/* 70 */     ByteBuffer pixelDataRGBA = ImageUtils.swizzleARGBtoRGBA(pixelDataARGB);
/* 71 */     int width = textureImage.getWidth();
/* 72 */     int height = textureImage.getHeight();
/* 73 */     return new DefaultPixelData(width, height, pixelDataRGBA);
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
/*    */   public static PixelData createErrorPixelData() {
/* 85 */     ByteBuffer pixelDataRGBA = ByteBuffer.allocateDirect(128);
/*    */     
/* 87 */     IntBuffer intPixelDataRGBA = pixelDataRGBA.order(ByteOrder.BIG_ENDIAN).asIntBuffer();
/* 88 */     intPixelDataRGBA.put(0, -16776961);
/* 89 */     intPixelDataRGBA.put(1, -1);
/* 90 */     intPixelDataRGBA.put(2, -16776961);
/* 91 */     intPixelDataRGBA.put(3, -1);
/* 92 */     int width = 2;
/* 93 */     int height = 2;
/* 94 */     return new DefaultPixelData(width, height, pixelDataRGBA);
/*    */   }
/*    */ }


/* Location:              C:\Users\DuduM\OneDrive\Área de Trabalho\mw10\modularwarfare-shining-2023.2.4.4f-fix10.jar!\de\javagl\jgltf\model\image\PixelDatas.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */