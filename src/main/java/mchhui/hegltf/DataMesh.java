/*     */ package mchhui.hegltf;
/*     */ 
/*     */ import com.modularwarfare.loader.api.model.ObjModelRenderer;
/*     */ import java.nio.ByteBuffer;
/*     */ import java.nio.FloatBuffer;
/*     */ import java.nio.IntBuffer;
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import net.minecraft.client.Minecraft;
/*     */ import net.minecraft.client.renderer.GlStateManager;
/*     */ import net.minecraft.client.renderer.OpenGlHelper;
/*     */ import org.lwjgl.BufferUtils;
/*     */ import org.lwjgl.opengl.GL11;
/*     */ import org.lwjgl.opengl.GL15;
/*     */ import org.lwjgl.opengl.GL20;
/*     */ import org.lwjgl.opengl.GL30;
/*     */ import org.lwjgl.opengl.GL42;
/*     */ import sun.nio.ch.DirectBuffer;
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
/*     */ public class DataMesh
/*     */ {
/*     */   private static final int SIZE_OF_FLOAT = 4;
/*     */   private static final int SIZE_OF_INT = 4;
/*     */   public String material;
/*     */   public boolean skin;
/*  39 */   protected List<Float> geoList = new ArrayList<>();
/*     */   protected int geoCount;
/*     */   protected ByteBuffer geoBuffer;
/*     */   protected IntBuffer elementBuffer;
/*     */   protected int elementCount;
/*     */   public int unit;
/*  45 */   public int glDrawingMode = 4;
/*  46 */   private int displayList = -1;
/*  47 */   private int ssboVao = -1;
/*  48 */   private int vertexCount = 0;
/*     */   
/*     */   private boolean compiled = false;
/*     */   
/*     */   private boolean compiling = false;
/*     */   private boolean initSkinning = false;
/*  54 */   private int pos_vbo = -1;
/*  55 */   private int tex_vbo = -1;
/*  56 */   private int normal_vbo = -1;
/*  57 */   private int vbo = -1;
/*  58 */   private int ebo = -1;
/*  59 */   private int ssbo = -1;
/*     */   
/*     */   public void render() {
/*  62 */     if (!this.compiled) {
/*     */       try {
/*  64 */         compileVAO(1.0F);
/*     */         return;
/*  66 */       } catch (Throwable t) {
/*  67 */         t.printStackTrace();
/*     */       } 
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  74 */     callVAO();
/*     */     
/*  76 */     if (ObjModelRenderer.glowTxtureMode) {
/*  77 */       if (!ObjModelRenderer.customItemRenderer.bindTextureGlow(ObjModelRenderer.glowType, ObjModelRenderer.glowPath)) {
/*     */         return;
/*     */       }
/*  80 */       float x = OpenGlHelper.lastBrightnessX;
/*  81 */       float y = OpenGlHelper.lastBrightnessY;
/*  82 */       ObjModelRenderer.glowTxtureMode = false;
/*  83 */       GlStateManager.func_179132_a(false);
/*     */       
/*  85 */       GlStateManager.func_179143_c(514);
/*  86 */       GlStateManager.func_179140_f();
/*  87 */       OpenGlHelper.func_77475_a(OpenGlHelper.field_77476_b, 240.0F, 240.0F);
/*  88 */       callVAO();
/*  89 */       GlStateManager.func_179145_e();
/*  90 */       OpenGlHelper.func_77475_a(OpenGlHelper.field_77476_b, x, y);
/*  91 */       GlStateManager.func_179143_c(515);
/*     */       
/*  93 */       GlStateManager.func_179132_a(true);
/*  94 */       ObjModelRenderer.glowTxtureMode = true;
/*  95 */       ObjModelRenderer.customItemRenderer.bindTexture(ObjModelRenderer.glowType, ObjModelRenderer.glowPath);
/*     */ 
/*     */       
/*  98 */       if ((Minecraft.func_71410_x()).field_71462_r instanceof com.modularwarfare.client.gui.GuiGunModify) {
/*  99 */         GlStateManager.func_179140_f();
/*     */       }
/*     */     } 
/*     */   }
/*     */   
/*     */   private void compileVAO(float scale) {
/* 105 */     if (this.compiling) {
/*     */       return;
/*     */     }
/* 108 */     this.compiling = true;
/* 109 */     this.ssboVao = GL30.glGenVertexArrays();
/* 110 */     this.displayList = GL30.glGenVertexArrays();
/*     */     
/* 112 */     if (this.unit == 3) {
/* 113 */       this.vertexCount = this.geoList.size() / this.unit;
/*     */       
/* 115 */       FloatBuffer pos_floatBuffer = BufferUtils.createFloatBuffer(this.vertexCount * 3);
/* 116 */       FloatBuffer tex_floatBuffer = BufferUtils.createFloatBuffer(this.vertexCount * 2);
/* 117 */       FloatBuffer normal_floatBuffer = BufferUtils.createFloatBuffer(this.vertexCount * 3);
/*     */       
/* 119 */       IntBuffer joint_intBuffer = BufferUtils.createIntBuffer(this.vertexCount * 4);
/* 120 */       FloatBuffer weight_floatBuffer = BufferUtils.createFloatBuffer(this.vertexCount * 4);
/*     */       
/* 122 */       int count = 0;
/* 123 */       for (int i = 0; i < this.geoList.size(); i++) {
/* 124 */         if (count == 8) {
/* 125 */           count = 0;
/*     */         }
/* 127 */         if (count < 3) {
/* 128 */           pos_floatBuffer.put(((Float)this.geoList.get(i)).floatValue());
/* 129 */         } else if (count < 5) {
/* 130 */           tex_floatBuffer.put(((Float)this.geoList.get(i)).floatValue());
/* 131 */         } else if (count < 8) {
/* 132 */           normal_floatBuffer.put(((Float)this.geoList.get(i)).floatValue());
/*     */         } 
/* 134 */         count++;
/*     */       } 
/* 136 */       pos_floatBuffer.flip();
/* 137 */       tex_floatBuffer.flip();
/* 138 */       normal_floatBuffer.flip();
/*     */       
/* 140 */       GL30.glBindVertexArray(this.displayList);
/* 141 */       GL11.glEnableClientState(32884);
/* 142 */       GL11.glEnableClientState(32888);
/* 143 */       GL11.glEnableClientState(32885);
/*     */       
/* 145 */       this.pos_vbo = GL15.glGenBuffers();
/* 146 */       this.tex_vbo = GL15.glGenBuffers();
/* 147 */       this.normal_vbo = GL15.glGenBuffers();
/*     */       
/* 149 */       GL15.glBindBuffer(34962, this.pos_vbo);
/* 150 */       GL15.glBufferData(34962, pos_floatBuffer, 35044);
/* 151 */       GL11.glVertexPointer(3, 5126, 0, 0L);
/*     */       
/* 153 */       GL15.glBindBuffer(34962, this.tex_vbo);
/* 154 */       GL15.glBufferData(34962, tex_floatBuffer, 35044);
/* 155 */       GL11.glTexCoordPointer(2, 5126, 0, 0L);
/*     */       
/* 157 */       GL15.glBindBuffer(34962, this.normal_vbo);
/* 158 */       GL15.glBufferData(34962, normal_floatBuffer, 35044);
/* 159 */       GL11.glNormalPointer(5126, 0, 0L);
/*     */       
/* 161 */       GL30.glBindVertexArray(0);
/* 162 */       GL15.glBindBuffer(34962, 0);
/* 163 */       GL11.glDisableClientState(32884);
/* 164 */       GL11.glDisableClientState(32888);
/* 165 */       GL11.glDisableClientState(32885);
/* 166 */       this.compiled = true;
/* 167 */       this.compiling = false;
/*     */     } else {
/*     */       
/* 170 */       this.vbo = GL15.glGenBuffers();
/* 171 */       this.ebo = GL15.glGenBuffers();
/* 172 */       this.geoBuffer.flip();
/* 173 */       this.elementBuffer.flip();
/* 174 */       GL30.glBindVertexArray(this.displayList);
/* 175 */       GL20.glEnableVertexAttribArray(0);
/* 176 */       GL20.glEnableVertexAttribArray(1);
/* 177 */       GL20.glEnableVertexAttribArray(2);
/* 178 */       GL20.glEnableVertexAttribArray(3);
/* 179 */       GL20.glEnableVertexAttribArray(4);
/* 180 */       GL20.glEnableVertexAttribArray(5);
/* 181 */       GL15.glBindBuffer(34962, this.vbo);
/* 182 */       GL15.glBufferData(34962, this.geoBuffer, 35044);
/* 183 */       int step = 68;
/* 184 */       GL20.glVertexAttribPointer(0, 3, 5126, false, step, 0L);
/* 185 */       GL20.glVertexAttribPointer(1, 2, 5126, false, step, 12L);
/* 186 */       GL20.glVertexAttribPointer(2, 3, 5126, false, step, 20L);
/*     */       
/* 188 */       GL20.glVertexAttribPointer(3, 4, 5126, false, step, 32L);
/* 189 */       GL20.glVertexAttribPointer(4, 4, 5126, false, step, 48L);
/*     */       
/* 191 */       GL20.glVertexAttribPointer(5, 1, 5126, false, step, 64L);
/*     */       
/* 193 */       GL15.glBindBuffer(34963, this.ebo);
/* 194 */       GL15.glBufferData(34963, this.elementBuffer, 35044);
/* 195 */       this.ssbo = GL15.glGenBuffers();
/* 196 */       GL15.glBindBuffer(37074, this.ssbo);
/* 197 */       GL15.glBufferData(37074, this.geoBuffer, 35050);
/* 198 */       GL15.glBindBuffer(37074, 0);
/*     */       
/* 200 */       GL30.glBindVertexArray(this.ssboVao);
/*     */       
/* 202 */       GL11.glEnableClientState(32884);
/* 203 */       GL11.glEnableClientState(32888);
/* 204 */       GL11.glEnableClientState(32885);
/*     */       
/* 206 */       GL15.glBindBuffer(34962, this.ssbo);
/* 207 */       GL11.glVertexPointer(3, 5126, 32, 0L);
/* 208 */       GL11.glNormalPointer(5126, 32, 12L);
/* 209 */       GL11.glTexCoordPointer(2, 5126, 32, 24L);
/*     */       
/* 211 */       GL15.glBindBuffer(34963, this.ebo);
/*     */       
/* 213 */       GL30.glBindVertexArray(0);
/* 214 */       GL15.glBindBuffer(34962, 0);
/* 215 */       GL15.glBindBuffer(34963, 0);
/* 216 */       GL15.glBindBuffer(35982, 0);
/*     */       
/* 218 */       GL11.glDisableClientState(32884);
/* 219 */       GL11.glDisableClientState(32888);
/* 220 */       GL11.glDisableClientState(32885);
/*     */       
/* 222 */       this.skin = true;
/* 223 */       this.compiled = true;
/* 224 */       this.compiling = false;
/*     */     } 
/*     */   }
/*     */   
/*     */   public void callSkinning() {
/* 229 */     if (!this.compiled) {
/*     */       return;
/*     */     }
/* 232 */     if (this.skin) {
/* 233 */       GL30.glBindBufferBase(37074, 3, this.ssbo);
/* 234 */       GL30.glBindVertexArray(this.displayList);
/* 235 */       GL11.glDrawElements(this.glDrawingMode, this.elementCount, 5125, 0L);
/* 236 */       GL30.glBindVertexArray(0);
/* 237 */       GL42.glMemoryBarrier(8192);
/* 238 */       this.initSkinning = true;
/*     */     } 
/*     */   }
/*     */   
/*     */   private void callVAO() {
/* 243 */     if (!this.compiled) {
/*     */       return;
/*     */     }
/* 246 */     if (this.skin) {
/* 247 */       if (!this.initSkinning) {
/*     */         return;
/*     */       }
/* 250 */       GL30.glBindVertexArray(this.ssboVao);
/* 251 */       GL11.glDrawElements(this.glDrawingMode, this.elementCount, 5125, 0L);
/* 252 */       GL30.glBindVertexArray(0);
/* 253 */       GL15.glBindBuffer(37074, 0);
/*     */     } else {
/* 255 */       GL30.glBindVertexArray(this.displayList);
/* 256 */       GL11.glDrawArrays(this.glDrawingMode, 0, this.vertexCount);
/* 257 */       GL30.glBindVertexArray(0);
/*     */     } 
/*     */   }
/*     */   
/*     */   public void delete() {
/* 262 */     if (this.geoBuffer != null && (
/* 263 */       (DirectBuffer)this.geoBuffer).cleaner() != null) {
/* 264 */       ((DirectBuffer)this.geoBuffer).cleaner().clean();
/*     */     }
/*     */     
/* 267 */     if (this.elementBuffer != null && (
/* 268 */       (DirectBuffer)this.elementBuffer).cleaner() != null) {
/* 269 */       ((DirectBuffer)this.elementBuffer).cleaner().clean();
/*     */     }
/*     */     
/* 272 */     GL30.glDeleteVertexArrays(this.displayList);
/* 273 */     GL30.glDeleteVertexArrays(this.ssboVao);
/* 274 */     if (this.pos_vbo != -1) {
/* 275 */       GL15.glDeleteBuffers(this.pos_vbo);
/*     */     }
/* 277 */     if (this.tex_vbo != -1) {
/* 278 */       GL15.glDeleteBuffers(this.tex_vbo);
/*     */     }
/* 280 */     if (this.normal_vbo != -1) {
/* 281 */       GL15.glDeleteBuffers(this.normal_vbo);
/*     */     }
/* 283 */     if (this.vbo != -1) {
/* 284 */       GL15.glDeleteBuffers(this.vbo);
/*     */     }
/* 286 */     if (this.ebo != -1) {
/* 287 */       GL15.glDeleteBuffers(this.ebo);
/*     */     }
/* 289 */     if (this.ssbo != -1)
/* 290 */       GL15.glDeleteBuffers(this.ssbo); 
/*     */   }
/*     */ }


/* Location:              C:\Users\DuduM\OneDrive\Área de Trabalho\mw10\modularwarfare-shining-2023.2.4.4f-fix10.jar!\mchhui\hegltf\DataMesh.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */