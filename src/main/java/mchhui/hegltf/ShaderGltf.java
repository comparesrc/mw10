/*    */ package mchhui.hegltf;
/*    */ 
/*    */ import javax.annotation.Nullable;
/*    */ import org.lwjgl.opengl.GL20;
/*    */ import org.lwjgl.opengl.GL30;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class ShaderGltf
/*    */ {
/*    */   public static final int JOINTMATSBUFFERBINDING = 0;
/*    */   public static final int VERTEXBUFFERBINDING = 3;
/* 21 */   public static int shaderProgram = -1;
/* 22 */   public static int vshaderId = -1;
/*    */ 
/*    */   
/*    */   public static void useShader() {
/* 26 */     if (shaderProgram != -1) {
/* 27 */       GL20.glUseProgram(shaderProgram);
/*    */       
/*    */       return;
/*    */     } 
/* 31 */     String vshaderCode = readText();
/* 32 */     vshaderId = GL20.glCreateShader(35633);
/* 33 */     GL20.glShaderSource(vshaderId, vshaderCode);
/* 34 */     GL20.glCompileShader(vshaderId);
/* 35 */     if (GL20.glGetShaderi(vshaderId, 35713) == 0) {
/* 36 */       System.out.println(GL20.glGetShaderInfoLog(vshaderId, 32767));
/* 37 */       throw new RuntimeException();
/*    */     } 
/*    */     
/* 40 */     shaderProgram = GL20.glCreateProgram();
/* 41 */     GL20.glAttachShader(shaderProgram, vshaderId);
/*    */     
/* 43 */     GL30.glTransformFeedbackVaryings(shaderProgram, new CharSequence[] { "outPos", "outNormal", "outTex" }, 35981);
/*    */     
/* 45 */     GL20.glLinkProgram(shaderProgram);
/* 46 */     GL20.glDeleteShader(vshaderId);
/* 47 */     if (GL20.glGetProgrami(shaderProgram, 35714) == 0) {
/* 48 */       System.out.println(GL20.glGetProgramInfoLog(shaderProgram, 32767));
/* 49 */       throw new RuntimeException();
/*    */     } 
/*    */   }
/*    */   
/*    */   @Nullable
/*    */   public static String readText() {
/* 55 */     return "#version 430\r\n\r\nlayout (location = 0) in vec3 v_pos;\r\nlayout (location = 1) in vec2 v_tex;\r\nlayout (location = 2) in vec3 v_normal;\r\nlayout (location = 3) in vec4 v_joint;\r\nlayout (location = 4) in vec4 v_weight;\r\nlayout (location = 5) in float v_count;\r\n\r\n\r\nlayout (std430,binding = 0) buffer JointMatsBuffer {\r\n    readonly mat4 joint_mats[];\r\n};\r\n\r\nlayout (std430,binding = 3) buffer VertexBuffer {\r\n    writeonly float v_buffer[];\r\n};\r\n\r\nout vec3 outPos;\r\nout vec3 outNormal;\r\nout vec2 outTex;\r\n\r\nvoid main() {\r\n    mat4 skinMatrix = v_weight.x * joint_mats[floatBitsToUint(v_joint.x)]\r\n    +v_weight.y * joint_mats[floatBitsToUint(v_joint.y)]\r\n    +v_weight.z * joint_mats[floatBitsToUint(v_joint.z)]\r\n    +v_weight.w * joint_mats[floatBitsToUint(v_joint.w)];\r\n    mat3 matrix3d = mat3(skinMatrix);\r\n\r\n    outPos = (skinMatrix * vec4(v_pos, 1.0)).xyz;\r\n    outNormal = matrix3d * v_normal;\r\n    outTex = v_tex;\r\n    uint v_offset=floatBitsToUint(v_count)*8;\r\n    v_buffer[(v_offset+0)]=outPos.x;\r\n    v_buffer[(v_offset+1)]=outPos.y;\r\n    v_buffer[(v_offset+2)]=outPos.z;\r\n    v_buffer[(v_offset+3)]=outNormal.x;\r\n    v_buffer[(v_offset+4)]=outNormal.y;\r\n    v_buffer[(v_offset+5)]=outNormal.z;\r\n    v_buffer[(v_offset+6)]=outTex.x;\r\n    v_buffer[(v_offset+7)]=outTex.y;\r\n    \r\n    gl_Position = vec4(outPos.x, outPos.y, outPos.z, 1);\r\n}";
/*    */   }
/*    */ }


/* Location:              C:\Users\DuduM\OneDrive\Área de Trabalho\mw10\modularwarfare-shining-2023.2.4.4f-fix10.jar!\mchhui\hegltf\ShaderGltf.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */