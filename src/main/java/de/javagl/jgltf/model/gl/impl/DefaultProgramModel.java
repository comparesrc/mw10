/*     */ package de.javagl.jgltf.model.gl.impl;
/*     */ 
/*     */ import de.javagl.jgltf.model.gl.ProgramModel;
/*     */ import de.javagl.jgltf.model.gl.ShaderModel;
/*     */ import de.javagl.jgltf.model.impl.AbstractNamedModelElement;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collections;
/*     */ import java.util.List;
/*     */ import java.util.Objects;
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
/*     */ public class DefaultProgramModel
/*     */   extends AbstractNamedModelElement
/*     */   implements ProgramModel
/*     */ {
/*     */   private ShaderModel vertexShaderModel;
/*     */   private ShaderModel fragmentShaderModel;
/*  64 */   private final List<String> attributes = new ArrayList<>();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void addAttribute(String attribute) {
/*  74 */     this.attributes.add(attribute);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setVertexShaderModel(ShaderModel vertexShaderModel) {
/*  84 */     this.vertexShaderModel = Objects.<ShaderModel>requireNonNull(vertexShaderModel, "The vertexShaderModel may not be null");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ShaderModel getVertexShaderModel() {
/*  91 */     return this.vertexShaderModel;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setFragmentShaderModel(ShaderModel fragmentShaderModel) {
/* 101 */     this.fragmentShaderModel = Objects.<ShaderModel>requireNonNull(fragmentShaderModel, "The fragmentShaderModel may not be null");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ShaderModel getFragmentShaderModel() {
/* 108 */     return this.fragmentShaderModel;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public List<String> getAttributes() {
/* 114 */     return Collections.unmodifiableList(this.attributes);
/*     */   }
/*     */ }


/* Location:              C:\Users\DuduM\OneDrive\Área de Trabalho\mw10\modularwarfare-shining-2023.2.4.4f-fix10.jar!\de\javagl\jgltf\model\gl\impl\DefaultProgramModel.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */