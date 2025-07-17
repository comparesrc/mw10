/*    */ package de.javagl.jgltf.model.image;
/*    */ 
/*    */ import de.javagl.jgltf.model.io.Buffers;
/*    */ import java.io.IOException;
/*    */ import java.io.InputStream;
/*    */ import java.nio.ByteBuffer;
/*    */ import java.util.Iterator;
/*    */ import javax.imageio.ImageIO;
/*    */ import javax.imageio.ImageReader;
/*    */ import javax.imageio.stream.ImageInputStream;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class ImageReaders
/*    */ {
/*    */   public static ImageReader findImageReader(ByteBuffer imageData) throws IOException {
/* 65 */     InputStream inputStream = Buffers.createByteBufferInputStream(imageData.slice());
/*    */     
/* 67 */     ImageInputStream imageInputStream = ImageIO.createImageInputStream(inputStream);
/*    */     
/* 69 */     Iterator<ImageReader> imageReaders = ImageIO.getImageReaders(imageInputStream);
/* 70 */     if (imageReaders.hasNext()) {
/*    */       
/* 72 */       ImageReader imageReader = imageReaders.next();
/* 73 */       imageReader.setInput(imageInputStream);
/* 74 */       return imageReader;
/*    */     } 
/* 76 */     throw new IOException("Could not find ImageReader for image data");
/*    */   }
/*    */ }


/* Location:              C:\Users\DuduM\OneDrive\Área de Trabalho\mw10\modularwarfare-shining-2023.2.4.4f-fix10.jar!\de\javagl\jgltf\model\image\ImageReaders.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */