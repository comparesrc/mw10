/*     */ package de.javagl.jgltf.model.gl.impl;
/*     */ 
/*     */ import de.javagl.jgltf.model.gl.ShaderModel;
/*     */ import de.javagl.jgltf.model.impl.AbstractNamedModelElement;
/*     */ import de.javagl.jgltf.model.io.Buffers;
/*     */ import java.nio.ByteBuffer;
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
/*     */ public class DefaultShaderModel
/*     */   extends AbstractNamedModelElement
/*     */   implements ShaderModel
/*     */ {
/*     */   private final String uri;
/*     */   private ByteBuffer shaderData;
/*     */   private final ShaderModel.ShaderType shaderType;
/*     */   
/*     */   public DefaultShaderModel(String uri, ShaderModel.ShaderType shaderType) {
/*  65 */     this.uri = uri;
/*  66 */     this.shaderType = shaderType;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setShaderData(ByteBuffer shaderData) {
/*  76 */     this.shaderData = shaderData;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public String getUri() {
/*  82 */     return this.uri;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public ByteBuffer getShaderData() {
/*  88 */     return Buffers.createSlice(this.shaderData);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public String getShaderSource() {
/*  94 */     return Buffers.readAsString(this.shaderData);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public ShaderModel.ShaderType getShaderType() {
/* 100 */     return this.shaderType;
/*     */   }
/*     */ }


/* Location:              C:\Users\DuduM\OneDrive\Área de Trabalho\mw10\modularwarfare-shining-2023.2.4.4f-fix10.jar!\de\javagl\jgltf\model\gl\impl\DefaultShaderModel.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */