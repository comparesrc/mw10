/*     */ package de.javagl.jgltf.model.v1;
/*     */ 
/*     */ import de.javagl.jgltf.model.GltfModel;
/*     */ import de.javagl.jgltf.model.gl.ProgramModel;
/*     */ import de.javagl.jgltf.model.gl.ShaderModel;
/*     */ import de.javagl.jgltf.model.gl.TechniqueModel;
/*     */ import de.javagl.jgltf.model.gl.impl.DefaultProgramModel;
/*     */ import de.javagl.jgltf.model.gl.impl.DefaultShaderModel;
/*     */ import de.javagl.jgltf.model.gl.impl.DefaultTechniqueModel;
/*     */ import de.javagl.jgltf.model.impl.DefaultGltfModel;
/*     */ import de.javagl.jgltf.model.io.v1.GltfAssetV1;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collection;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class GltfModelV1
/*     */   extends DefaultGltfModel
/*     */   implements GltfModel
/*     */ {
/*     */   private final List<DefaultShaderModel> shaderModels;
/*     */   private final List<DefaultProgramModel> programModels;
/*     */   private final List<DefaultTechniqueModel> techniqueModels;
/*     */   
/*     */   public GltfModelV1(GltfAssetV1 gltfAsset) {
/*  79 */     Objects.requireNonNull(gltfAsset, "The gltfAsset may not be null");
/*     */ 
/*     */     
/*  82 */     this.shaderModels = new ArrayList<>();
/*  83 */     this.programModels = new ArrayList<>();
/*  84 */     this.techniqueModels = new ArrayList<>();
/*     */     
/*  86 */     GltfModelCreatorV1 gltfModelCreatorV1 = new GltfModelCreatorV1(gltfAsset, this);
/*     */     
/*  88 */     gltfModelCreatorV1.create();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public GltfModelV1() {
/*  96 */     this.shaderModels = new ArrayList<>();
/*  97 */     this.programModels = new ArrayList<>();
/*  98 */     this.techniqueModels = new ArrayList<>();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void addShaderModel(DefaultShaderModel shaderModel) {
/* 108 */     this.shaderModels.add(shaderModel);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void removeShaderModel(DefaultShaderModel shaderModel) {
/* 118 */     this.shaderModels.remove(shaderModel);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void addShaderModels(Collection<? extends DefaultShaderModel> shaderModels) {
/* 129 */     for (DefaultShaderModel shaderModel : shaderModels)
/*     */     {
/* 131 */       addShaderModel(shaderModel);
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
/*     */   public DefaultShaderModel getShaderModel(int index) {
/* 143 */     return this.shaderModels.get(index);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void clearShaderModels() {
/* 151 */     this.shaderModels.clear();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public List<ShaderModel> getShaderModels() {
/* 162 */     return (List)Collections.unmodifiableList(this.shaderModels);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void addProgramModel(DefaultProgramModel programModel) {
/* 172 */     this.programModels.add(programModel);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void removeProgramModel(DefaultProgramModel programModel) {
/* 182 */     this.programModels.remove(programModel);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void addProgramModels(Collection<? extends DefaultProgramModel> programModels) {
/* 193 */     for (DefaultProgramModel programModel : programModels)
/*     */     {
/* 195 */       addProgramModel(programModel);
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
/*     */   public DefaultProgramModel getProgramModel(int index) {
/* 207 */     return this.programModels.get(index);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void clearProgramModels() {
/* 215 */     this.programModels.clear();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public List<ProgramModel> getProgramModels() {
/* 226 */     return (List)Collections.unmodifiableList(this.programModels);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void addTechniqueModel(DefaultTechniqueModel techniqueModel) {
/* 237 */     this.techniqueModels.add(techniqueModel);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void removeTechniqueModel(DefaultTechniqueModel techniqueModel) {
/* 247 */     this.techniqueModels.remove(techniqueModel);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void addTechniqueModels(Collection<? extends DefaultTechniqueModel> techniqueModels) {
/* 258 */     for (DefaultTechniqueModel techniqueModel : techniqueModels)
/*     */     {
/* 260 */       addTechniqueModel(techniqueModel);
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
/*     */   public DefaultTechniqueModel getTechniqueModel(int index) {
/* 272 */     return this.techniqueModels.get(index);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void clearTechniqueModels() {
/* 280 */     this.techniqueModels.clear();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public List<TechniqueModel> getTechniqueModels() {
/* 291 */     return (List)Collections.unmodifiableList(this.techniqueModels);
/*     */   }
/*     */ }


/* Location:              C:\Users\DuduM\OneDrive\Área de Trabalho\mw10\modularwarfare-shining-2023.2.4.4f-fix10.jar!\de\javagl\jgltf\model\v1\GltfModelV1.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */