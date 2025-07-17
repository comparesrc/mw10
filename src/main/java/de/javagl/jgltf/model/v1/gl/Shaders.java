/*     */ package de.javagl.jgltf.model.v1.gl;
/*     */ 
/*     */ import de.javagl.jgltf.impl.v1.Shader;
/*     */ import java.util.Base64;
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
/*     */ class Shaders
/*     */ {
/*     */   private static final String DEFAULT_VERTEX_SHADER_CODE = "#ifdef GL_ES\n  precision highp float;\n#endif\n\nuniform mat4 u_modelViewMatrix;\nuniform mat4 u_projectionMatrix;\nattribute vec3 a_position;\nvoid main(void)\n{\n    gl_Position = u_projectionMatrix * u_modelViewMatrix *\n        vec4(a_position,1.0);\n}\n\n";
/*     */   private static final String DEFAULT_FRAGMENT_SHADER_CODE = "#ifdef GL_ES\n  precision highp float;\n#endif\n\nuniform vec4 u_emission;\nvoid main(void)\n{\n    gl_FragColor = u_emission;\n}\n\n";
/*     */   
/*     */   static Shader createDefaultVertexShader() {
/*  84 */     Shader shader = new Shader();
/*  85 */     shader.setType(Integer.valueOf(35633));
/*     */     
/*  87 */     String encodedCode = Base64.getEncoder().encodeToString("#ifdef GL_ES\n  precision highp float;\n#endif\n\nuniform mat4 u_modelViewMatrix;\nuniform mat4 u_projectionMatrix;\nattribute vec3 a_position;\nvoid main(void)\n{\n    gl_Position = u_projectionMatrix * u_modelViewMatrix *\n        vec4(a_position,1.0);\n}\n\n"
/*  88 */         .getBytes());
/*  89 */     String dataUriString = "data:text/plain;base64," + encodedCode;
/*  90 */     shader.setUri(dataUriString);
/*  91 */     return shader;
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
/*     */   static Shader createDefaultFragmentShader() {
/* 106 */     Shader shader = new Shader();
/* 107 */     shader.setType(Integer.valueOf(35632));
/*     */     
/* 109 */     String encodedCode = Base64.getEncoder().encodeToString("#ifdef GL_ES\n  precision highp float;\n#endif\n\nuniform vec4 u_emission;\nvoid main(void)\n{\n    gl_FragColor = u_emission;\n}\n\n"
/* 110 */         .getBytes());
/* 111 */     String dataUriString = "data:text/plain;base64," + encodedCode;
/* 112 */     shader.setUri(dataUriString);
/* 113 */     return shader;
/*     */   }
/*     */ }


/* Location:              C:\Users\DuduM\OneDrive\Área de Trabalho\mw10\modularwarfare-shining-2023.2.4.4f-fix10.jar!\de\javagl\jgltf\model\v1\gl\Shaders.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */