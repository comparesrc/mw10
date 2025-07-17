/*     */ package de.javagl.jgltf.model.v1.gl;
/*     */ 
/*     */ import de.javagl.jgltf.impl.v1.Material;
/*     */ import de.javagl.jgltf.impl.v1.Program;
/*     */ import de.javagl.jgltf.impl.v1.Shader;
/*     */ import de.javagl.jgltf.impl.v1.Technique;
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
/*     */ public class GltfDefaults
/*     */ {
/*  44 */   private static final String DEFAULT_VERTEX_SHADER_ID = GltfDefaults.class
/*  45 */     .getName() + ".DEFAULT_VERTEX_SHADER_ID";
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  50 */   private static final String DEFAULT_FRAGMENT_SHADER_ID = GltfDefaults.class
/*  51 */     .getName() + ".DEFAULT_FRAGMENT_SHADER_ID";
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  56 */   private static final String DEFAULT_PROGRAM_ID = GltfDefaults.class
/*  57 */     .getName() + ".DEFAULT_PROGRAM_ID";
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  62 */   private static final String DEFAULT_TECHNIQUE_ID = GltfDefaults.class
/*  63 */     .getName() + ".DEFAULT_TECHNIQUE_ID";
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  68 */   private static final String DEFAULT_MATERIAL_ID = GltfDefaults.class
/*  69 */     .getName() + ".DEFAULT_MATERIAL_ID";
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  75 */   private static final Shader DEFAULT_VERTEX_SHADER = Shaders.createDefaultVertexShader();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  81 */   private static final Shader DEFAULT_FRAGMENT_SHADER = Shaders.createDefaultFragmentShader();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  87 */   private static final Program DEFAULT_PROGRAM = Programs.createDefaultProgram(DEFAULT_VERTEX_SHADER_ID, DEFAULT_FRAGMENT_SHADER_ID);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  94 */   private static final Technique DEFAULT_TECHNIQUE = Techniques.createDefaultTechnique(DEFAULT_PROGRAM_ID);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 100 */   private static final Material DEFAULT_MATERIAL = Materials.createDefaultMaterial(DEFAULT_TECHNIQUE_ID);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static boolean isDefaultTechniqueId(String id) {
/* 110 */     return DEFAULT_TECHNIQUE_ID.equals(id);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static boolean isDefaultMaterialId(String id) {
/* 121 */     return DEFAULT_MATERIAL_ID.equals(id);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static Shader getDefaultVertexShader() {
/* 131 */     return DEFAULT_VERTEX_SHADER;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static Shader getDefaultFragmentShader() {
/* 141 */     return DEFAULT_FRAGMENT_SHADER;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static Technique getDefaultTechnique() {
/* 151 */     return DEFAULT_TECHNIQUE;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static Material getDefaultMaterial() {
/* 161 */     return DEFAULT_MATERIAL;
/*     */   }
/*     */ }


/* Location:              C:\Users\DuduM\OneDrive\Área de Trabalho\mw10\modularwarfare-shining-2023.2.4.4f-fix10.jar!\de\javagl\jgltf\model\v1\gl\GltfDefaults.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */