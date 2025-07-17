/*     */ package de.javagl.jgltf.model.image;
/*     */ 
/*     */ import de.javagl.jgltf.model.io.Buffers;
/*     */ import java.awt.Graphics2D;
/*     */ import java.awt.image.BufferedImage;
/*     */ import java.awt.image.DataBuffer;
/*     */ import java.awt.image.DataBufferInt;
/*     */ import java.awt.image.ImageObserver;
/*     */ import java.io.ByteArrayOutputStream;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.nio.ByteBuffer;
/*     */ import java.nio.ByteOrder;
/*     */ import java.nio.IntBuffer;
/*     */ import java.util.logging.Logger;
/*     */ import javax.imageio.ImageIO;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class ImageUtils
/*     */ {
/*  56 */   private static final Logger logger = Logger.getLogger(ImageUtils.class.getName());
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static BufferedImage readAsBufferedImage(ByteBuffer byteBuffer) {
/*  69 */     if (byteBuffer == null)
/*     */     {
/*  71 */       return null;
/*     */     }
/*     */     
/*  74 */     try (InputStream inputStream = Buffers.createByteBufferInputStream(byteBuffer.slice())) {
/*     */       
/*  76 */       return ImageIO.read(inputStream);
/*     */     }
/*  78 */     catch (IOException e) {
/*     */       
/*  80 */       logger.severe(e.toString());
/*  81 */       return null;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static ByteBuffer getImagePixelsARGB(BufferedImage inputImage, boolean flipVertically) {
/*  99 */     BufferedImage image = inputImage;
/* 100 */     if (flipVertically)
/*     */     {
/* 102 */       image = flipVertically(image);
/*     */     }
/* 104 */     if (image.getType() != 2)
/*     */     {
/* 106 */       image = convertToARGB(image);
/*     */     }
/* 108 */     IntBuffer imageBuffer = getBuffer(image);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 115 */     ByteBuffer outputByteBuffer = ByteBuffer.allocateDirect(imageBuffer.remaining() * 4).order(ByteOrder.BIG_ENDIAN);
/* 116 */     IntBuffer output = outputByteBuffer.asIntBuffer();
/* 117 */     output.put(imageBuffer.slice());
/* 118 */     return outputByteBuffer;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static ByteBuffer swizzleARGBtoRGBA(ByteBuffer pixels) {
/* 130 */     return swizzle(pixels, 16, 8, 0, 24);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static ByteBuffer swizzleRGBAtoARGB(ByteBuffer pixels) {
/* 142 */     return swizzle(pixels, 0, 24, 16, 8);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static ByteBuffer swizzle(ByteBuffer pixels, int s0, int s1, int s2, int s3) {
/* 160 */     IntBuffer iBuffer = pixels.asIntBuffer();
/*     */ 
/*     */     
/* 163 */     ByteBuffer oByteBuffer = ByteBuffer.allocateDirect(iBuffer.capacity() * 4).order(pixels.order());
/* 164 */     IntBuffer oBuffer = oByteBuffer.asIntBuffer();
/* 165 */     for (int i = 0; i < iBuffer.capacity(); i++) {
/*     */       
/* 167 */       int input = iBuffer.get(i);
/* 168 */       int output = swizzle(input, s0, s1, s2, s3);
/* 169 */       oBuffer.put(i, output);
/*     */     } 
/* 171 */     return oByteBuffer;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static int swizzle(int input, int s0, int s1, int s2, int s3) {
/* 186 */     int b0 = input >> s0 & 0xFF;
/* 187 */     int b1 = input >> s1 & 0xFF;
/* 188 */     int b2 = input >> s2 & 0xFF;
/* 189 */     int b3 = input >> s3 & 0xFF;
/* 190 */     return b0 << 24 | b1 << 16 | b2 << 8 | b3;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static BufferedImage convertToARGB(BufferedImage image) {
/* 203 */     BufferedImage newImage = new BufferedImage(image.getWidth(), image.getHeight(), 2);
/*     */     
/* 205 */     Graphics2D g = newImage.createGraphics();
/* 206 */     g.drawImage(image, 0, 0, (ImageObserver)null);
/* 207 */     g.dispose();
/* 208 */     return newImage;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static BufferedImage flipVertically(BufferedImage image) {
/* 219 */     int w = image.getWidth();
/* 220 */     int h = image.getHeight();
/* 221 */     BufferedImage newImage = new BufferedImage(w, h, 2);
/*     */     
/* 223 */     Graphics2D g = newImage.createGraphics();
/* 224 */     g.drawImage(image, 0, h, w, -h, null);
/* 225 */     g.dispose();
/* 226 */     return newImage;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static IntBuffer getBuffer(BufferedImage image) {
/* 240 */     DataBuffer dataBuffer = image.getRaster().getDataBuffer();
/* 241 */     if (!(dataBuffer instanceof DataBufferInt))
/*     */     {
/* 243 */       throw new IllegalArgumentException("Invalid buffer type in image, only TYPE_INT_* is allowed");
/*     */     }
/*     */ 
/*     */     
/* 247 */     DataBufferInt dataBufferInt = (DataBufferInt)dataBuffer;
/* 248 */     int[] data = dataBufferInt.getData();
/* 249 */     IntBuffer intBuffer = IntBuffer.wrap(data);
/* 250 */     return intBuffer;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static BufferedImage createBufferedImage(PixelData pixelData) {
/* 261 */     int w = pixelData.getWidth();
/* 262 */     int h = pixelData.getHeight();
/* 263 */     BufferedImage image = new BufferedImage(w, h, 2);
/*     */     
/* 265 */     IntBuffer imageBuffer = getBuffer(image);
/* 266 */     ByteBuffer pixels = pixelData.getPixelsRGBA();
/* 267 */     ByteBuffer argbBytes = swizzleRGBAtoARGB(pixels);
/* 268 */     imageBuffer.put(argbBytes.asIntBuffer());
/* 269 */     return image;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static ByteBuffer createImageDataBuffer(PixelData pixelData, String mimeType) {
/* 292 */     String formatName = null;
/* 293 */     if ("image/gif".equals(mimeType)) {
/*     */       
/* 295 */       formatName = "gif";
/*     */     }
/* 297 */     else if ("image/jpeg".equals(mimeType)) {
/*     */       
/* 299 */       formatName = "jpg";
/*     */     }
/* 301 */     else if ("image/png".equals(mimeType)) {
/*     */       
/* 303 */       formatName = "png";
/*     */     }
/*     */     else {
/*     */       
/* 307 */       throw new IllegalArgumentException("The MIME type string must be \"image/gif\", \"image/jpeg\" or \"image/png\", but is " + mimeType);
/*     */     } 
/*     */ 
/*     */     
/* 311 */     BufferedImage image = createBufferedImage(pixelData);
/* 312 */     try (ByteArrayOutputStream baos = new ByteArrayOutputStream()) {
/*     */       
/* 314 */       ImageIO.write(image, formatName, baos);
/* 315 */       return Buffers.create(baos.toByteArray());
/*     */     }
/* 317 */     catch (IOException e) {
/*     */       
/* 319 */       logger.severe(e.toString());
/* 320 */       return null;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void main(String[] args) {
/* 329 */     int input = 287454020;
/* 330 */     ByteBuffer b0 = ByteBuffer.allocate(4).order(ByteOrder.BIG_ENDIAN);
/* 331 */     b0.asIntBuffer().put(input);
/* 332 */     ByteBuffer b1 = swizzleARGBtoRGBA(b0);
/* 333 */     int rgba = b1.asIntBuffer().get();
/* 334 */     ByteBuffer b2 = swizzleRGBAtoARGB(b1);
/* 335 */     int argb = b2.asIntBuffer().get();
/*     */     
/* 337 */     System.out.println("Input: " + Integer.toHexString(input));
/* 338 */     System.out.println("RGBA : " + Integer.toHexString(rgba));
/* 339 */     System.out.println("ARGB : " + Integer.toHexString(argb));
/*     */   }
/*     */ }


/* Location:              C:\Users\DuduM\OneDrive\Área de Trabalho\mw10\modularwarfare-shining-2023.2.4.4f-fix10.jar!\de\javagl\jgltf\model\image\ImageUtils.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */