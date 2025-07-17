/*     */ package com.modularwarfare.client.shader;
/*     */ 
/*     */ import java.nio.ByteBuffer;
/*     */ import net.minecraft.crash.CrashReport;
/*     */ import net.minecraft.util.ReportedException;
/*     */ import org.lwjgl.opengl.GL11;
/*     */ import org.lwjgl.opengl.GL30;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class FrameBuffer
/*     */ {
/*     */   private int buffer;
/*     */   private int renderBuffer;
/*     */   private int width;
/*     */   private int height;
/*     */   private int[] textures;
/*     */   private int textureDepth;
/*     */   private final boolean hasRenderBuffer;
/*     */   private final boolean hasDepthTexture;
/*     */   boolean multipleTextures;
/*     */   
/*     */   public FrameBuffer(int width, int height, int textureCount, boolean hasRenderBuffer, boolean hasDepthTexture) {
/*  27 */     this.width = width;
/*  28 */     this.height = height;
/*  29 */     this.hasRenderBuffer = hasRenderBuffer;
/*  30 */     this.hasDepthTexture = hasDepthTexture;
/*  31 */     this.textures = new int[textureCount];
/*  32 */     delete();
/*  33 */     create();
/*     */   }
/*     */   
/*     */   public FrameBuffer(int width, int height) {
/*  37 */     this.width = width;
/*  38 */     this.height = height;
/*  39 */     this.hasRenderBuffer = false;
/*  40 */     this.hasDepthTexture = false;
/*  41 */     delete();
/*  42 */     create();
/*  43 */     init();
/*     */   }
/*     */   
/*     */   public void init() {
/*  47 */     bind(true);
/*  48 */     createTexture();
/*  49 */     GL11.glDrawBuffer(36064);
/*  50 */     checkComplete();
/*     */     
/*  52 */     if (this.hasRenderBuffer) {
/*  53 */       generateRenderBuffer();
/*     */     }
/*     */     
/*  56 */     if (this.hasDepthTexture) {
/*  57 */       createDepthTexture();
/*     */     }
/*     */   }
/*     */   
/*     */   public void resize(int width, int height) {
/*  62 */     this.width = width;
/*  63 */     this.height = height;
/*  64 */     deleteTextures();
/*     */   }
/*     */   
/*     */   public void create() {
/*  68 */     this.buffer = GL30.glGenFramebuffers();
/*     */   }
/*     */   
/*     */   public void bind(boolean viewport) {
/*  72 */     GL30.glBindFramebuffer(36160, this.buffer);
/*  73 */     if (viewport) GL11.glViewport(0, 0, this.width, this.height); 
/*     */   }
/*     */   
/*     */   public void unbind() {
/*  77 */     GL30.glBindFramebuffer(36160, 0);
/*     */   }
/*     */   
/*     */   private void createTexture() {
/*  81 */     int texture = GL11.glGenTextures();
/*  82 */     this.textures = new int[1];
/*  83 */     this.textures[0] = texture;
/*  84 */     GL11.glBindTexture(3553, texture);
/*  85 */     GL11.glTexImage2D(3553, 0, 34842, this.width, this.height, 0, 6408, 5126, (ByteBuffer)null);
/*     */     
/*  87 */     GL11.glTexParameteri(3553, 10240, 9729);
/*  88 */     GL11.glTexParameteri(3553, 10241, 9729);
/*  89 */     GL11.glTexParameteri(3553, 10242, 33071);
/*  90 */     GL11.glTexParameteri(3553, 10243, 33071);
/*     */     
/*  92 */     GL30.glFramebufferTexture2D(36160, 36064, 3553, texture, 0);
/*     */   }
/*     */   
/*     */   public void createTexture(int index, int textureFormat, int format, int type, int colorAttachment, int wrap, int filter) {
/*  96 */     int texture = GL11.glGenTextures();
/*  97 */     this.textures[index] = texture;
/*  98 */     GL11.glBindTexture(3553, texture);
/*  99 */     GL11.glTexImage2D(3553, 0, textureFormat, this.width, this.height, 0, format, type, (ByteBuffer)null);
/*     */     
/* 101 */     GL11.glTexParameteri(3553, 10240, filter);
/* 102 */     GL11.glTexParameteri(3553, 10241, filter);
/* 103 */     GL11.glTexParameteri(3553, 10242, wrap);
/* 104 */     GL11.glTexParameteri(3553, 10243, wrap);
/*     */     
/* 106 */     GL30.glFramebufferTexture2D(36160, colorAttachment, 3553, texture, 0);
/*     */   }
/*     */   
/*     */   public void setTexture(int texture, int colorAttachment) {
/* 110 */     GL30.glFramebufferTexture2D(36160, colorAttachment, 3553, texture, 0);
/*     */   }
/*     */   
/*     */   private void createDepthTexture() {
/* 114 */     this.textureDepth = GL11.glGenTextures();
/* 115 */     GL11.glBindTexture(3553, this.textureDepth);
/* 116 */     GL11.glTexImage2D(3553, 0, 6402, this.width, this.height, 0, 6402, 5126, (ByteBuffer)null);
/*     */     
/* 118 */     GL11.glTexParameteri(3553, 10240, 9729);
/* 119 */     GL11.glTexParameteri(3553, 10241, 9729);
/*     */     
/* 121 */     GL30.glFramebufferTexture2D(36160, 36096, 3553, this.textureDepth, 0);
/*     */   }
/*     */   
/*     */   private void generateRenderBuffer() {
/* 125 */     this.renderBuffer = GL30.glGenRenderbuffers();
/* 126 */     GL30.glBindRenderbuffer(36161, this.renderBuffer);
/* 127 */     GL30.glRenderbufferStorage(36161, 6402, this.width, this.height);
/* 128 */     GL30.glFramebufferRenderbuffer(36160, 36096, 36161, this.renderBuffer);
/*     */     
/* 130 */     if (GL30.glCheckFramebufferStatus(36160) != 36053) {
/* 131 */       Throwable t = new Throwable("Render buffer couldn't create!");
/* 132 */       CrashReport crashreport = CrashReport.func_85055_a(t, "Shader compile");
/* 133 */       throw new ReportedException(crashreport);
/*     */     } 
/*     */   }
/*     */   
/*     */   public void bindTexture(int index) {
/* 138 */     GL11.glBindTexture(3553, this.textures[index]);
/*     */   }
/*     */   
/*     */   public void bindTexture() {
/* 142 */     GL11.glBindTexture(3553, this.textures[0]);
/*     */   }
/*     */   
/*     */   public int getTexture(int index) {
/* 146 */     return this.textures[index];
/*     */   }
/*     */   
/*     */   public int getTexture() {
/* 150 */     return this.textures[0];
/*     */   }
/*     */   
/*     */   private void checkComplete() {
/* 154 */     if (GL30.glCheckFramebufferStatus(36160) != 36053) {
/* 155 */       Throwable t = new Throwable("MRT couldn't create!");
/* 156 */       CrashReport crashreport = CrashReport.func_85055_a(t, "Shader compile");
/* 157 */       throw new ReportedException(crashreport);
/*     */     } 
/*     */   }
/*     */   
/*     */   public void clear() {
/* 162 */     GL11.glClear(16640);
/*     */   }
/*     */   
/*     */   public void viewPort(int width, int height) {
/* 166 */     GL11.glViewport(0, 0, width, height);
/*     */   }
/*     */   
/*     */   public void delete() {
/* 170 */     deleteTextures();
/* 171 */     if (this.hasDepthTexture) GL11.glDeleteTextures(this.textureDepth); 
/* 172 */     if (this.hasRenderBuffer) GL30.glDeleteRenderbuffers(this.renderBuffer); 
/* 173 */     GL30.glDeleteFramebuffers(this.buffer);
/*     */   }
/*     */   
/*     */   private void deleteTextures() {
/* 177 */     if (this.textures != null)
/* 178 */       for (int texture : this.textures) {
/* 179 */         GL11.glDeleteTextures(texture);
/*     */       } 
/*     */   }
/*     */   
/*     */   public void drawBuffer(int i) {
/* 184 */     GL11.glDrawBuffer(36064 + i);
/*     */   }
/*     */   
/*     */   public int getBuffer() {
/* 188 */     return this.buffer;
/*     */   }
/*     */   
/*     */   public int getRenderBuffer() {
/* 192 */     return this.renderBuffer;
/*     */   }
/*     */   
/*     */   public int getWidth() {
/* 196 */     return this.width;
/*     */   }
/*     */   
/*     */   public int getHeight() {
/* 200 */     return this.height;
/*     */   }
/*     */   
/*     */   public int getTextureDepth() {
/* 204 */     return this.textureDepth;
/*     */   }
/*     */ }


/* Location:              C:\Users\DuduM\OneDrive\Área de Trabalho\mw10\modularwarfare-shining-2023.2.4.4f-fix10.jar!\com\modularwarfare\client\shader\FrameBuffer.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */