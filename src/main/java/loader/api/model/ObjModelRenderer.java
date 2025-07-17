/*     */ package com.modularwarfare.loader.api.model;
/*     */ 
/*     */ import com.modularwarfare.ModConfig;
/*     */ import com.modularwarfare.client.fpp.basic.models.objects.CustomItemRenderer;
/*     */ import com.modularwarfare.loader.ObjModel;
/*     */ import com.modularwarfare.loader.part.ModelObject;
/*     */ import com.modularwarfare.loader.part.Vertex;
/*     */ import java.nio.FloatBuffer;
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import net.minecraft.client.renderer.BufferBuilder;
/*     */ import net.minecraft.client.renderer.GLAllocation;
/*     */ import net.minecraft.client.renderer.GlStateManager;
/*     */ import net.minecraft.client.renderer.OpenGlHelper;
/*     */ import net.minecraft.client.renderer.Tessellator;
/*     */ import net.minecraftforge.fml.relauncher.Side;
/*     */ import net.minecraftforge.fml.relauncher.SideOnly;
/*     */ import org.lwjgl.BufferUtils;
/*     */ import org.lwjgl.opengl.GL11;
/*     */ import org.lwjgl.opengl.GL15;
/*     */ import org.lwjgl.opengl.GL30;
/*     */ 
/*     */ public class ObjModelRenderer
/*     */ {
/*  25 */   public static CustomItemRenderer customItemRenderer = new CustomItemRenderer();
/*     */   public static boolean glowTxtureMode = false;
/*     */   public static String glowType;
/*     */   public static String glowPath;
/*     */   public float rotationPointX;
/*     */   public float rotationPointY;
/*     */   public float rotationPointZ;
/*     */   public float rotateAngleX;
/*     */   public float rotateAngleY;
/*     */   public float rotateAngleZ;
/*     */   public boolean isHidden;
/*  36 */   public List<ObjModelRenderer> childModels = new ArrayList<>();
/*     */   private ModelObject model;
/*     */   private AbstractObjModel parent;
/*     */   private boolean glow = false;
/*  40 */   private int vertexCount = 0;
/*     */   
/*     */   private boolean compiled;
/*     */   
/*     */   private int displayList;
/*     */ 
/*     */   
/*     */   public ObjModelRenderer(ObjModel parent, ModelObject modelForRender) {
/*  48 */     this.model = modelForRender;
/*  49 */     this.parent = (AbstractObjModel)parent;
/*  50 */     if (this.model.name.endsWith("_glow")) {
/*  51 */       this.glow = true;
/*     */     }
/*     */   }
/*     */   
/*     */   public String getName() {
/*  56 */     return this.model.name;
/*     */   }
/*     */   
/*     */   public void setRotationPoint(float rotationPointXIn, float rotationPointYIn, float rotationPointZIn) {
/*  60 */     this.rotationPointX = rotationPointXIn;
/*  61 */     this.rotationPointY = rotationPointYIn;
/*  62 */     this.rotationPointZ = rotationPointZIn;
/*     */   }
/*     */   
/*     */   public void setRotationPoint(Vertex vertex) {
/*  66 */     this.rotationPointX = vertex.x;
/*  67 */     this.rotationPointY = vertex.y;
/*  68 */     this.rotationPointZ = vertex.z;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void addChild(ObjModelRenderer child) {
/*  77 */     this.childModels.add(child);
/*  78 */     this.parent.addDuplication(child);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @SideOnly(Side.CLIENT)
/*     */   public void render(float scale) {
/*  88 */     float x = OpenGlHelper.lastBrightnessX;
/*  89 */     float y = OpenGlHelper.lastBrightnessY;
/*  90 */     if (this.glow) {
/*  91 */       GlStateManager.func_179140_f();
/*  92 */       OpenGlHelper.func_77475_a(OpenGlHelper.field_77476_b, 240.0F, 240.0F);
/*     */     } 
/*  94 */     if (!this.isHidden) {
/*  95 */       if (!this.compiled) {
/*  96 */         if (ModConfig.INSTANCE.model_optimization) {
/*  97 */           compileVAO(scale);
/*     */         } else {
/*  99 */           compileDisplayList(scale);
/*     */         } 
/*     */       }
/*     */       
/* 103 */       if (this.rotateAngleX == 0.0F && this.rotateAngleY == 0.0F && this.rotateAngleZ == 0.0F) {
/* 104 */         if (ModConfig.INSTANCE.model_optimization) {
/* 105 */           callVAO();
/*     */         } else {
/* 107 */           GlStateManager.func_179148_o(this.displayList);
/*     */         } 
/* 109 */         if (this.childModels != null) {
/* 110 */           for (ObjModelRenderer childModel : this.childModels) {
/* 111 */             childModel.render(scale);
/*     */           }
/*     */         }
/*     */       } else {
/* 115 */         GlStateManager.func_179094_E();
/*     */         
/* 117 */         GlStateManager.func_179109_b(this.rotationPointX * scale, this.rotationPointY * scale, this.rotationPointZ * scale);
/*     */ 
/*     */         
/* 120 */         if (this.rotateAngleZ != 0.0F)
/*     */         {
/* 122 */           GlStateManager.func_179114_b(this.rotateAngleZ * 57.295776F, 0.0F, 0.0F, 1.0F);
/*     */         }
/*     */         
/* 125 */         if (this.rotateAngleY != 0.0F) {
/* 126 */           GlStateManager.func_179114_b(-this.rotateAngleY * 57.295776F, 0.0F, 1.0F, 0.0F);
/*     */         }
/*     */         
/* 129 */         if (this.rotateAngleX != 0.0F) {
/* 130 */           GlStateManager.func_179114_b(this.rotateAngleX * 57.295776F, 1.0F, 0.0F, 0.0F);
/*     */         }
/*     */         
/* 133 */         GlStateManager.func_179109_b(-this.rotationPointX * scale, -this.rotationPointY * scale, -this.rotationPointZ * scale);
/*     */ 
/*     */         
/* 136 */         if (ModConfig.INSTANCE.model_optimization) {
/* 137 */           callVAO();
/*     */         } else {
/* 139 */           GlStateManager.func_179148_o(this.displayList);
/*     */         } 
/*     */         
/* 142 */         if (this.childModels != null) {
/* 143 */           for (ObjModelRenderer childModel : this.childModels) {
/* 144 */             childModel.render(scale);
/*     */           }
/*     */         }
/*     */         
/* 148 */         GlStateManager.func_179121_F();
/*     */       } 
/*     */     } 
/* 151 */     if (this.glow) {
/* 152 */       GlStateManager.func_179140_f();
/* 153 */       OpenGlHelper.func_77475_a(OpenGlHelper.field_77476_b, x, y);
/*     */     } 
/* 155 */     if (glowTxtureMode) {
/* 156 */       if (!customItemRenderer.bindTextureGlow(glowType, glowPath)) {
/*     */         return;
/*     */       }
/* 159 */       glowTxtureMode = false;
/* 160 */       GlStateManager.func_179132_a(false);
/*     */       
/* 162 */       GlStateManager.func_179143_c(514);
/* 163 */       GlStateManager.func_179140_f();
/* 164 */       OpenGlHelper.func_77475_a(OpenGlHelper.field_77476_b, 240.0F, 240.0F);
/* 165 */       render(scale);
/* 166 */       GlStateManager.func_179145_e();
/* 167 */       OpenGlHelper.func_77475_a(OpenGlHelper.field_77476_b, x, y);
/* 168 */       GlStateManager.func_179143_c(515);
/*     */       
/* 170 */       GlStateManager.func_179132_a(true);
/* 171 */       glowTxtureMode = true;
/* 172 */       customItemRenderer.bindTexture(glowType, glowPath);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @SideOnly(Side.CLIENT)
/*     */   public void renderWithRotation(float scale) {
/* 183 */     if (!this.isHidden) {
/* 184 */       if (!this.compiled) {
/* 185 */         if (ModConfig.INSTANCE.model_optimization) {
/* 186 */           compileVAO(scale);
/*     */         } else {
/* 188 */           compileDisplayList(scale);
/*     */         } 
/*     */       }
/*     */       
/* 192 */       GlStateManager.func_179094_E();
/* 193 */       GlStateManager.func_179109_b(this.rotationPointX * scale, this.rotationPointY * scale, this.rotationPointZ * scale);
/*     */ 
/*     */       
/* 196 */       if (this.rotateAngleY != 0.0F) {
/* 197 */         GlStateManager.func_179114_b(this.rotateAngleY * 57.295776F, 0.0F, 1.0F, 0.0F);
/*     */       }
/*     */       
/* 200 */       if (this.rotateAngleX != 0.0F) {
/* 201 */         GlStateManager.func_179114_b(this.rotateAngleX * 57.295776F, 1.0F, 0.0F, 0.0F);
/*     */       }
/*     */       
/* 204 */       if (this.rotateAngleZ != 0.0F) {
/* 205 */         GlStateManager.func_179114_b(this.rotateAngleZ * 57.295776F, 0.0F, 0.0F, 1.0F);
/*     */       }
/*     */       
/* 208 */       GlStateManager.func_179109_b(-this.rotationPointX * scale, -this.rotationPointY * scale, -this.rotationPointZ * scale);
/*     */ 
/*     */       
/* 211 */       if (ModConfig.INSTANCE.model_optimization) {
/* 212 */         callVAO();
/*     */       } else {
/* 214 */         GlStateManager.func_179148_o(this.displayList);
/*     */       } 
/*     */       
/* 217 */       if (this.childModels != null) {
/* 218 */         for (ObjModelRenderer childModel : this.childModels) {
/* 219 */           childModel.render(scale);
/*     */         }
/*     */       }
/* 222 */       GlStateManager.func_179121_F();
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @SideOnly(Side.CLIENT)
/*     */   public void postRender(float scale) {
/* 231 */     if (!this.isHidden) {
/* 232 */       if (!this.compiled) {
/* 233 */         if (ModConfig.INSTANCE.model_optimization) {
/* 234 */           compileVAO(scale);
/*     */         } else {
/* 236 */           compileDisplayList(scale);
/*     */         } 
/*     */       }
/*     */       
/* 240 */       if (this.rotateAngleX == 0.0F && this.rotateAngleY == 0.0F && this.rotateAngleZ == 0.0F) {
/* 241 */         if (this.rotationPointX != 0.0F || this.rotationPointY != 0.0F || this.rotationPointZ != 0.0F) {
/* 242 */           GlStateManager.func_179109_b(this.rotationPointX * scale, this.rotationPointY * scale, this.rotationPointZ * scale);
/*     */         }
/*     */       } else {
/*     */         
/* 246 */         GlStateManager.func_179109_b(this.rotationPointX * scale, this.rotationPointY * scale, this.rotationPointZ * scale);
/*     */ 
/*     */         
/* 249 */         if (this.rotateAngleZ != 0.0F) {
/* 250 */           GlStateManager.func_179114_b(this.rotateAngleZ * 57.295776F, 0.0F, 0.0F, 1.0F);
/*     */         }
/*     */         
/* 253 */         if (this.rotateAngleY != 0.0F) {
/* 254 */           GlStateManager.func_179114_b(this.rotateAngleY * 57.295776F, 0.0F, 1.0F, 0.0F);
/*     */         }
/*     */         
/* 257 */         if (this.rotateAngleX != 0.0F) {
/* 258 */           GlStateManager.func_179114_b(this.rotateAngleX * 57.295776F, 1.0F, 0.0F, 0.0F);
/*     */         }
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @SideOnly(Side.CLIENT)
/*     */   private void compileDisplayList(float scale) {
/* 269 */     this.displayList = GLAllocation.func_74526_a(1);
/* 270 */     GlStateManager.func_187423_f(this.displayList, 4864);
/* 271 */     BufferBuilder bufferbuilder = Tessellator.func_178181_a().func_178180_c();
/*     */     
/* 273 */     this.model.render(bufferbuilder, scale);
/*     */     
/* 275 */     GlStateManager.func_187415_K();
/* 276 */     this.compiled = true;
/*     */   }
/*     */   
/*     */   @SideOnly(Side.CLIENT)
/*     */   private void compileVAO(float scale) {
/* 281 */     BufferBuilder bufferbuilder = Tessellator.func_178181_a().func_178180_c();
/* 282 */     List<Float> list = new ArrayList<>();
/* 283 */     int flag = this.model.renderByVAO(list, scale);
/* 284 */     this.displayList = GL30.glGenVertexArrays();
/* 285 */     GL30.glBindVertexArray(this.displayList);
/* 286 */     this.vertexCount = list.size() / flag;
/* 287 */     FloatBuffer pos_floatBuffer = BufferUtils.createFloatBuffer(this.vertexCount * 3);
/* 288 */     FloatBuffer tex_floatBuffer = BufferUtils.createFloatBuffer(this.vertexCount * 2);
/* 289 */     FloatBuffer normal_floatBuffer = BufferUtils.createFloatBuffer(this.vertexCount * 3);
/* 290 */     int count = 0;
/* 291 */     for (int i = 0; i < list.size(); i++) {
/* 292 */       if (count == 8) {
/* 293 */         count = 0;
/*     */       }
/* 295 */       if (count < 3) {
/* 296 */         pos_floatBuffer.put(((Float)list.get(i)).floatValue());
/* 297 */       } else if (count < 5) {
/* 298 */         tex_floatBuffer.put(((Float)list.get(i)).floatValue());
/* 299 */       } else if (count < 8) {
/* 300 */         normal_floatBuffer.put(((Float)list.get(i)).floatValue());
/*     */       } 
/* 302 */       count++;
/*     */     } 
/* 304 */     pos_floatBuffer.flip();
/* 305 */     tex_floatBuffer.flip();
/* 306 */     normal_floatBuffer.flip();
/* 307 */     GL11.glEnableClientState(32884);
/* 308 */     int pos_vbo = GL15.glGenBuffers();
/* 309 */     GL15.glBindBuffer(34962, pos_vbo);
/* 310 */     GL15.glBufferData(34962, pos_floatBuffer, 35044);
/* 311 */     GL11.glVertexPointer(3, 5126, 0, 0L);
/*     */     
/* 313 */     GL11.glEnableClientState(32888);
/* 314 */     int tex_vbo = GL15.glGenBuffers();
/* 315 */     GL15.glBindBuffer(34962, tex_vbo);
/* 316 */     GL15.glBufferData(34962, tex_floatBuffer, 35044);
/* 317 */     GL11.glTexCoordPointer(2, 5126, 0, 0L);
/*     */     
/* 319 */     GL11.glEnableClientState(32885);
/* 320 */     int normal_vbo = GL15.glGenBuffers();
/* 321 */     GL15.glBindBuffer(34962, normal_vbo);
/* 322 */     GL15.glBufferData(34962, normal_floatBuffer, 35044);
/* 323 */     GL11.glNormalPointer(5126, 0, 0L);
/*     */     
/* 325 */     GL15.glBindBuffer(34962, 0);
/* 326 */     GL30.glBindVertexArray(0);
/* 327 */     GL15.glDeleteBuffers(pos_vbo);
/* 328 */     GL15.glDeleteBuffers(tex_vbo);
/* 329 */     GL15.glDeleteBuffers(normal_vbo);
/* 330 */     this.compiled = true;
/*     */   }
/*     */   
/*     */   private void callVAO() {
/* 334 */     GL30.glBindVertexArray(this.displayList);
/* 335 */     GL11.glDrawArrays(this.model.glDrawingMode, 0, this.vertexCount);
/* 336 */     GL30.glBindVertexArray(0);
/*     */   }
/*     */ }


/* Location:              C:\Users\DuduM\OneDrive\Área de Trabalho\mw9\modularwarfare-shining-2023.2.4.4f-fix9.jar!\com\modularwarfare\loader\api\model\ObjModelRenderer.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */