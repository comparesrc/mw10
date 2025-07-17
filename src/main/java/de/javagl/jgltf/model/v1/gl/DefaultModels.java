/*     */ package de.javagl.jgltf.model.v1.gl;
/*     */ 
/*     */ import de.javagl.jgltf.impl.v1.Material;
/*     */ import de.javagl.jgltf.impl.v1.Shader;
/*     */ import de.javagl.jgltf.impl.v1.Technique;
/*     */ import de.javagl.jgltf.impl.v1.TechniqueParameters;
/*     */ import de.javagl.jgltf.impl.v1.TechniqueStates;
/*     */ import de.javagl.jgltf.impl.v1.TechniqueStatesFunctions;
/*     */ import de.javagl.jgltf.model.MaterialModel;
/*     */ import de.javagl.jgltf.model.NodeModel;
/*     */ import de.javagl.jgltf.model.Optionals;
/*     */ import de.javagl.jgltf.model.gl.ProgramModel;
/*     */ import de.javagl.jgltf.model.gl.ShaderModel;
/*     */ import de.javagl.jgltf.model.gl.TechniqueModel;
/*     */ import de.javagl.jgltf.model.gl.TechniqueParametersModel;
/*     */ import de.javagl.jgltf.model.gl.TechniqueStatesFunctionsModel;
/*     */ import de.javagl.jgltf.model.gl.TechniqueStatesModel;
/*     */ import de.javagl.jgltf.model.gl.impl.DefaultProgramModel;
/*     */ import de.javagl.jgltf.model.gl.impl.DefaultShaderModel;
/*     */ import de.javagl.jgltf.model.gl.impl.DefaultTechniqueModel;
/*     */ import de.javagl.jgltf.model.gl.impl.DefaultTechniqueParametersModel;
/*     */ import de.javagl.jgltf.model.gl.impl.DefaultTechniqueStatesFunctionsModel;
/*     */ import de.javagl.jgltf.model.gl.impl.DefaultTechniqueStatesModel;
/*     */ import de.javagl.jgltf.model.io.Buffers;
/*     */ import de.javagl.jgltf.model.io.IO;
/*     */ import de.javagl.jgltf.model.v1.MaterialModelV1;
/*     */ import java.net.URI;
/*     */ import java.net.URISyntaxException;
/*     */ import java.nio.ByteBuffer;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.logging.Logger;
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
/*     */ public class DefaultModels
/*     */ {
/*  74 */   private static final Logger logger = Logger.getLogger(DefaultModels.class.getName());
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static final DefaultShaderModel DEFAULT_VERTEX_SHADER_MODEL;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static final DefaultShaderModel DEFAULT_FRAGMENT_SHADER_MODEL;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static {
/* 104 */     Shader vertexShader = GltfDefaults.getDefaultVertexShader();
/*     */     
/* 106 */     DEFAULT_VERTEX_SHADER_MODEL = new DefaultShaderModel(vertexShader.getUri(), ShaderModel.ShaderType.VERTEX_SHADER);
/*     */     
/* 108 */     initShaderData(DEFAULT_VERTEX_SHADER_MODEL);
/*     */     
/* 110 */     Shader fragmentShader = GltfDefaults.getDefaultFragmentShader();
/*     */     
/* 112 */     DEFAULT_FRAGMENT_SHADER_MODEL = new DefaultShaderModel(fragmentShader.getUri(), ShaderModel.ShaderType.FRAGMENT_SHADER);
/*     */     
/* 114 */     initShaderData(DEFAULT_FRAGMENT_SHADER_MODEL);
/*     */   }
/*     */   
/* 117 */   private static final DefaultProgramModel DEFAULT_PROGRAM_MODEL = new DefaultProgramModel(); static {
/* 118 */     DEFAULT_PROGRAM_MODEL.setVertexShaderModel((ShaderModel)DEFAULT_VERTEX_SHADER_MODEL);
/*     */     
/* 120 */     DEFAULT_PROGRAM_MODEL.setFragmentShaderModel((ShaderModel)DEFAULT_FRAGMENT_SHADER_MODEL);
/*     */ 
/*     */ 
/*     */     
/* 124 */     Technique technique = GltfDefaults.getDefaultTechnique();
/* 125 */   } private static final DefaultTechniqueModel DEFAULT_TECHNIQUE_MODEL = new DefaultTechniqueModel(); static {
/* 126 */     DEFAULT_TECHNIQUE_MODEL.setProgramModel((ProgramModel)DEFAULT_PROGRAM_MODEL);
/*     */     
/* 128 */     addParametersForDefaultTechnique(technique, DEFAULT_TECHNIQUE_MODEL);
/* 129 */     addAttributes(technique, DEFAULT_TECHNIQUE_MODEL);
/* 130 */     addUniforms(technique, DEFAULT_TECHNIQUE_MODEL);
/*     */     
/* 132 */     TechniqueStates states = technique.getStates();
/* 133 */     List<Integer> enable = (List<Integer>)Optionals.of(states
/* 134 */         .getEnable(), states
/* 135 */         .defaultEnable());
/*     */     
/* 137 */     TechniqueStatesFunctions functions = states.getFunctions();
/*     */     
/* 139 */     DefaultTechniqueStatesFunctionsModel defaultTechniqueStatesFunctionsModel = TechniqueStatesFunctionsModels.create(functions);
/* 140 */     DefaultTechniqueStatesModel defaultTechniqueStatesModel = new DefaultTechniqueStatesModel(enable, (TechniqueStatesFunctionsModel)defaultTechniqueStatesFunctionsModel);
/*     */ 
/*     */     
/* 143 */     DEFAULT_TECHNIQUE_MODEL.setTechniqueStatesModel((TechniqueStatesModel)defaultTechniqueStatesModel);
/*     */ 
/*     */     
/* 146 */     Material material = GltfDefaults.getDefaultMaterial();
/* 147 */   } private static final MaterialModelV1 DEFAULT_MATERIAL_MODEL = new MaterialModelV1(); static {
/* 148 */     DEFAULT_MATERIAL_MODEL.setValues(material.getValues());
/* 149 */     DEFAULT_MATERIAL_MODEL.setTechniqueModel((TechniqueModel)DEFAULT_TECHNIQUE_MODEL);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static MaterialModel getDefaultMaterialModel() {
/* 159 */     return (MaterialModel)DEFAULT_MATERIAL_MODEL;
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
/*     */   private static void initShaderData(DefaultShaderModel shaderModel) {
/*     */     try {
/* 172 */       URI uri = new URI(shaderModel.getUri());
/* 173 */       byte[] data = IO.read(uri);
/* 174 */       ByteBuffer shaderData = Buffers.create(data);
/* 175 */       shaderModel.setShaderData(shaderData);
/*     */     }
/* 177 */     catch (URISyntaxException|java.io.IOException e) {
/*     */ 
/*     */ 
/*     */       
/* 181 */       logger.severe("Failed to initialize shader data");
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static TechniqueModel getDefaultTechniqueModel() {
/* 192 */     return (TechniqueModel)DEFAULT_TECHNIQUE_MODEL;
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
/*     */   private static void addParametersForDefaultTechnique(Technique technique, DefaultTechniqueModel techniqueModel) {
/* 207 */     Map<String, TechniqueParameters> parameters = Optionals.of(technique.getParameters());
/* 208 */     for (Map.Entry<String, TechniqueParameters> entry : parameters.entrySet()) {
/*     */       
/* 210 */       String parameterName = entry.getKey();
/* 211 */       TechniqueParameters parameter = entry.getValue();
/*     */       
/* 213 */       int type = parameter.getType().intValue();
/* 214 */       int count = ((Integer)Optionals.of(parameter.getCount(), Integer.valueOf(1))).intValue();
/* 215 */       String semantic = parameter.getSemantic();
/* 216 */       Object value = parameter.getValue();
/*     */ 
/*     */       
/* 219 */       NodeModel nodeModel = null;
/*     */       
/* 221 */       DefaultTechniqueParametersModel defaultTechniqueParametersModel = new DefaultTechniqueParametersModel(type, count, semantic, value, nodeModel);
/*     */ 
/*     */       
/* 224 */       techniqueModel.addParameter(parameterName, (TechniqueParametersModel)defaultTechniqueParametersModel);
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
/*     */   private static void addAttributes(Technique technique, DefaultTechniqueModel techniqueModel) {
/* 240 */     Map<String, String> attributes = Optionals.of(technique.getAttributes());
/* 241 */     for (Map.Entry<String, String> entry : attributes.entrySet()) {
/*     */       
/* 243 */       String attributeName = entry.getKey();
/* 244 */       String parameterName = entry.getValue();
/* 245 */       techniqueModel.addAttribute(attributeName, parameterName);
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
/*     */   private static void addUniforms(Technique technique, DefaultTechniqueModel techniqueModel) {
/* 260 */     Map<String, String> uniforms = Optionals.of(technique.getUniforms());
/* 261 */     for (Map.Entry<String, String> entry : uniforms.entrySet()) {
/*     */       
/* 263 */       String uniformName = entry.getKey();
/* 264 */       String parameterName = entry.getValue();
/* 265 */       techniqueModel.addUniform(uniformName, parameterName);
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\DuduM\OneDrive\Área de Trabalho\mw10\modularwarfare-shining-2023.2.4.4f-fix10.jar!\de\javagl\jgltf\model\v1\gl\DefaultModels.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */