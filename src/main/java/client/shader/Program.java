/*     */ package com.modularwarfare.client.shader;
/*     */ 
/*     */ import java.io.InputStream;
/*     */ import java.nio.FloatBuffer;
/*     */ import net.minecraft.client.Minecraft;
/*     */ import net.minecraft.client.resources.IResource;
/*     */ import net.minecraft.util.ResourceLocation;
/*     */ import org.apache.commons.io.IOUtils;
/*     */ import org.lwjgl.opengl.ARBShaderObjects;
/*     */ import org.lwjgl.util.vector.Vector2f;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class Program
/*     */ {
/*     */   private final int program;
/*     */   
/*     */   public Program(ResourceLocation vertexShader) throws Exception {
/*  24 */     int vertShader = createShader(vertexShader, 35633);
/*     */     
/*  26 */     this.program = ARBShaderObjects.glCreateProgramObjectARB();
/*  27 */     if (this.program == 0) {
/*  28 */       throw new Exception("glCreateProgramObjectARB failed");
/*     */     }
/*     */     
/*  31 */     ARBShaderObjects.glAttachObjectARB(this.program, vertShader);
/*     */     
/*  33 */     ARBShaderObjects.glLinkProgramARB(this.program);
/*  34 */     if (ARBShaderObjects.glGetObjectParameteriARB(this.program, 35714) == 0) {
/*  35 */       throw new Exception("Error linking: " + getLogInfo(this.program));
/*     */     }
/*     */     
/*  38 */     ARBShaderObjects.glValidateProgramARB(this.program);
/*  39 */     if (ARBShaderObjects.glGetObjectParameteriARB(this.program, 35715) == 0) {
/*  40 */       throw new Exception("Error validating: " + getLogInfo(this.program));
/*     */     }
/*     */   }
/*     */   
/*     */   public Program(ResourceLocation vertexShader, ResourceLocation fragmentShader) throws Exception {
/*  45 */     int vertShader = createShader(vertexShader, 35633);
/*  46 */     int fragShader = createShader(fragmentShader, 35632);
/*     */     
/*  48 */     this.program = ARBShaderObjects.glCreateProgramObjectARB();
/*  49 */     if (this.program == 0) {
/*  50 */       throw new Exception("glCreateProgramObjectARB failed");
/*     */     }
/*     */     
/*  53 */     ARBShaderObjects.glAttachObjectARB(this.program, vertShader);
/*  54 */     ARBShaderObjects.glAttachObjectARB(this.program, fragShader);
/*     */     
/*  56 */     ARBShaderObjects.glLinkProgramARB(this.program);
/*  57 */     if (ARBShaderObjects.glGetObjectParameteriARB(this.program, 35714) == 0) {
/*  58 */       throw new Exception("Error linking: " + getLogInfo(this.program));
/*     */     }
/*     */     
/*  61 */     ARBShaderObjects.glValidateProgramARB(this.program);
/*  62 */     if (ARBShaderObjects.glGetObjectParameteriARB(this.program, 35715) == 0) {
/*  63 */       throw new Exception("Error validating: " + getLogInfo(this.program));
/*     */     }
/*     */   }
/*     */   
/*     */   private int createShader(ResourceLocation resourceLocation, int shaderType) throws Exception {
/*  68 */     int shader = 0;
/*     */     try {
/*  70 */       shader = ARBShaderObjects.glCreateShaderObjectARB(shaderType);
/*     */       
/*  72 */       if (shader == 0) {
/*  73 */         throw new Exception("glCreateShaderObjectARB failed");
/*     */       }
/*  75 */       IResource resource = Minecraft.func_71410_x().func_110442_L().func_110536_a(resourceLocation);
/*  76 */       try (InputStream is = resource.func_110527_b()) {
/*  77 */         ARBShaderObjects.glShaderSourceARB(shader, IOUtils.toString(is));
/*     */       } 
/*  79 */       ARBShaderObjects.glCompileShaderARB(shader);
/*     */       
/*  81 */       if (ARBShaderObjects.glGetObjectParameteriARB(shader, 35713) == 0) {
/*  82 */         throw new RuntimeException("Error creating shader: " + getLogInfo(shader));
/*     */       }
/*  84 */       return shader;
/*     */     }
/*  86 */     catch (Exception exc) {
/*  87 */       ARBShaderObjects.glDeleteObjectARB(shader);
/*  88 */       throw exc;
/*     */     } 
/*     */   }
/*     */   
/*     */   private static String getLogInfo(int obj) {
/*  93 */     return ARBShaderObjects.glGetInfoLogARB(obj, ARBShaderObjects.glGetObjectParameteriARB(obj, 35716));
/*     */   }
/*     */   
/*     */   public void use() {
/*  97 */     ARBShaderObjects.glUseProgramObjectARB(this.program);
/*     */   }
/*     */   
/*     */   public void stopUsing() {
/* 101 */     ARBShaderObjects.glUseProgramObjectARB(0);
/*     */   }
/*     */   
/*     */   public void delete() {
/* 105 */     ARBShaderObjects.glDeleteObjectARB(this.program);
/*     */   }
/*     */   
/*     */   public Uniform getUniformVariable(String name) {
/* 109 */     return new Uniform(ARBShaderObjects.glGetUniformLocationARB(this.program, name));
/*     */   }
/*     */   
/*     */   public class Uniform {
/*     */     private final int location;
/*     */     
/*     */     public Uniform(int location) {
/* 116 */       this.location = location;
/*     */     }
/*     */     
/*     */     public void set(boolean bool) {
/* 120 */       ARBShaderObjects.glUniform1iARB(this.location, bool ? 1 : 0);
/*     */     }
/*     */     
/*     */     public void set(int integer) {
/* 124 */       ARBShaderObjects.glUniform1iARB(this.location, integer);
/*     */     }
/*     */     
/*     */     public void setVector(Vector2f vector) {
/* 128 */       ARBShaderObjects.glUniform2fARB(this.location, vector.x, vector.y);
/*     */     }
/*     */     
/*     */     public void setMatrix(FloatBuffer matrix) {
/* 132 */       ARBShaderObjects.glUniformMatrix4ARB(this.location, false, matrix);
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\DuduM\OneDrive\Área de Trabalho\mw9\modularwarfare-shining-2023.2.4.4f-fix9.jar!\com\modularwarfare\client\shader\Program.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */