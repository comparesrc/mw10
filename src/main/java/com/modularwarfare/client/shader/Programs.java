/*     */ package com.modularwarfare.client.shader;
/*     */ 
/*     */ import org.lwjgl.opengl.GL20;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class Programs
/*     */ {
/*     */   public static int normalProgram;
/*     */   public static int overlayProgram;
/*     */   public static int scopeBorderProgram;
/*     */   public static int depthProgram;
/*     */   public static int sunglassesProgram;
/*     */   public static int alphaDepthProgram;
/*     */   
/*     */   public static void init() {
/*  19 */     int normalvshader = GL20.glCreateShader(35633);
/*  20 */     GL20.glShaderSource(normalvshader, "#version 120\r\nvarying vec4 color;\r\nvarying vec2 texcoord;\r\n\r\nvoid main()\r\n{\r\n    texcoord = (gl_TextureMatrix[0] * gl_MultiTexCoord0).st;\r\n    color = gl_Color;\r\n    gl_Position = gl_ProjectionMatrix  * gl_ModelViewMatrix * gl_Vertex;}");
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  31 */     GL20.glCompileShader(normalvshader);
/*  32 */     if (GL20.glGetShaderi(normalvshader, 35713) != 1) {
/*  33 */       throw new RuntimeException(GL20.glGetShaderInfoLog(normalvshader, 512));
/*     */     }
/*     */     
/*  36 */     int normalfshader = GL20.glCreateShader(35632);
/*  37 */     GL20.glShaderSource(normalfshader, "#version 120\r\n\r\nuniform sampler2D texture;\r\nvarying vec4 color;\r\nvarying vec2 texcoord;\r\nvoid main(){\r\n    vec4 src=texture2D(texture, texcoord.st);\r\n    gl_FragColor = src*color;\r\n}");
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  47 */     GL20.glCompileShader(normalfshader);
/*  48 */     if (GL20.glGetShaderi(normalfshader, 35713) != 1) {
/*  49 */       throw new RuntimeException(GL20.glGetShaderInfoLog(normalfshader, 512));
/*     */     }
/*     */     
/*  52 */     normalProgram = GL20.glCreateProgram();
/*  53 */     GL20.glAttachShader(normalProgram, normalvshader);
/*  54 */     GL20.glAttachShader(normalProgram, normalfshader);
/*  55 */     GL20.glDeleteShader(normalvshader);
/*  56 */     GL20.glDeleteShader(normalfshader);
/*  57 */     GL20.glLinkProgram(normalProgram);
/*  58 */     if (GL20.glGetProgrami(normalProgram, 35714) != 1) {
/*  59 */       throw new RuntimeException(GL20.glGetProgramInfoLog(normalProgram, 512));
/*     */     }
/*     */     
/*  62 */     GL20.glUseProgram(normalProgram);
/*  63 */     GL20.glUniform1i(GL20.glGetUniformLocation(normalProgram, "texture"), 0);
/*  64 */     GL20.glUseProgram(0);
/*     */     
/*  66 */     int depthvshader = GL20.glCreateShader(35633);
/*  67 */     GL20.glShaderSource(depthvshader, "#version 120\r\nvarying vec4 color;\r\nvarying vec2 texcoord;\r\n\r\nvoid main()\r\n{\r\n    texcoord = (gl_TextureMatrix[0] * gl_MultiTexCoord0).st;\r\n    color = gl_Color;\r\n    gl_Position = gl_ProjectionMatrix  * gl_ModelViewMatrix * gl_Vertex;}");
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  78 */     GL20.glCompileShader(depthvshader);
/*  79 */     if (GL20.glGetShaderi(depthvshader, 35713) != 1) {
/*  80 */       throw new RuntimeException(GL20.glGetShaderInfoLog(depthvshader, 512));
/*     */     }
/*     */     
/*  83 */     int depthfshader = GL20.glCreateShader(35632);
/*  84 */     GL20.glShaderSource(depthfshader, "#version 120\r\n\r\nuniform sampler2D texture;\r\nvarying vec4 color;\r\nvarying vec2 texcoord;\r\nvoid main(){\r\n    vec4 src=texture2D(texture, texcoord.st);\r\n    gl_FragColor=vec4(src.r,src.r,src.r,1.0);\r\n    gl_FragDepth=src.r;\r\n}");
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  95 */     GL20.glCompileShader(depthfshader);
/*  96 */     if (GL20.glGetShaderi(depthfshader, 35713) != 1) {
/*  97 */       throw new RuntimeException(GL20.glGetShaderInfoLog(depthfshader, 512));
/*     */     }
/*     */     
/* 100 */     depthProgram = GL20.glCreateProgram();
/* 101 */     GL20.glAttachShader(depthProgram, depthvshader);
/* 102 */     GL20.glAttachShader(depthProgram, depthfshader);
/* 103 */     GL20.glDeleteShader(depthvshader);
/* 104 */     GL20.glDeleteShader(depthfshader);
/* 105 */     GL20.glLinkProgram(depthProgram);
/* 106 */     if (GL20.glGetProgrami(depthProgram, 35714) != 1) {
/* 107 */       throw new RuntimeException(GL20.glGetProgramInfoLog(depthProgram, 512));
/*     */     }
/*     */     
/* 110 */     GL20.glUseProgram(depthProgram);
/* 111 */     GL20.glUniform1i(GL20.glGetUniformLocation(depthProgram, "texture"), 0);
/* 112 */     GL20.glUseProgram(0);
/*     */     
/* 114 */     int overlayvshader = GL20.glCreateShader(35633);
/* 115 */     GL20.glShaderSource(overlayvshader, "#version 120\r\nvarying vec4 color;\r\nvarying vec2 texcoord;\r\n\r\nvoid main()\r\n{\r\n    texcoord = (gl_TextureMatrix[0] * gl_MultiTexCoord0).st;\r\n    color = gl_Color;\r\n    gl_Position = gl_ProjectionMatrix  * gl_ModelViewMatrix * gl_Vertex;}");
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 126 */     GL20.glCompileShader(overlayvshader);
/* 127 */     if (GL20.glGetShaderi(overlayvshader, 35713) != 1) {
/* 128 */       throw new RuntimeException(GL20.glGetShaderInfoLog(overlayvshader, 512));
/*     */     }
/*     */     
/* 131 */     int overlayfshader = GL20.glCreateShader(35632);
/* 132 */     GL20.glShaderSource(overlayfshader, "#version 120\r\n\r\nuniform sampler2D texture;\r\nuniform sampler2D texture1;\r\nuniform sampler2D texture2;\r\nuniform vec2 size;\r\nvarying vec4 color;\r\nvarying vec2 texcoord;\r\nvoid main(){\r\nvec4 src=texture2D(texture, texcoord.st);//scope color\r\nvec4 src1=texture2D(texture1, texcoord.st);//world color\r\nvec4 src2=texture2D(texture2, texcoord.st);//lightmap color\r\n//src.rgb=src.rgb*min(vec3(1,1,1),src1.rgb+src2.rgb)*src.a+src1.rgb*(1-src.a);\r\nsrc.rgb=src.rgb*min(vec3(1,1,1),src1.rgb+src2.rgb)*src1.rgb*src.a+src1.rgb*(1-src.a);\r\n\r\nfloat srcWeight=1;\r\nfloat range=1;\r\nvec4 albedo = vec4(0,0,0,0);\r\nfloat sum=0;\r\nvec4 samp;\r\nvec4 samp1;\r\nvec4 samp2;\r\nvec2 uv;\r\nfor(int x=-1;x<=1;x++){\r\n    for(int y=-1;y<=1;y++){\r\n        uv=texcoord.st+vec2(range*x/size.x,range*y/size.y);\r\n        samp=texture2D(texture,uv);\r\n        samp1=texture2D(texture1,uv);\r\n        samp2=texture2D(texture2,uv);\r\n        samp.rgb=samp.rgb*min(vec3(1,1,1),samp1.rgb+samp2.rgb)*samp.a;\r\n        albedo+=samp;\r\n        sum+=1-step(samp.a,0);\r\n    }   \r\n}\r\nalbedo=albedo/sum;\r\nfloat flag=1-step(5,sum); \r\nflag*=min(sum,1);\r\nalbedo.a=flag*(9-sum)*0.11;\r\ngl_FragColor = (src*(1-albedo.a)+albedo*albedo.a)*color;\r\ngl_FragColor.a = step(0.1,gl_FragColor.a);\r\n}");
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 174 */     GL20.glCompileShader(overlayfshader);
/* 175 */     if (GL20.glGetShaderi(overlayfshader, 35713) != 1) {
/* 176 */       throw new RuntimeException(GL20.glGetShaderInfoLog(overlayfshader, 512));
/*     */     }
/*     */     
/* 179 */     overlayProgram = GL20.glCreateProgram();
/* 180 */     GL20.glAttachShader(overlayProgram, overlayvshader);
/* 181 */     GL20.glAttachShader(overlayProgram, overlayfshader);
/* 182 */     GL20.glDeleteShader(overlayvshader);
/* 183 */     GL20.glDeleteShader(overlayfshader);
/* 184 */     GL20.glLinkProgram(overlayProgram);
/* 185 */     if (GL20.glGetProgrami(overlayProgram, 35714) != 1) {
/* 186 */       throw new RuntimeException(GL20.glGetProgramInfoLog(overlayProgram, 512));
/*     */     }
/*     */     
/* 189 */     GL20.glUseProgram(overlayProgram);
/* 190 */     GL20.glUniform1i(GL20.glGetUniformLocation(overlayProgram, "texture"), 0);
/* 191 */     GL20.glUniform1i(GL20.glGetUniformLocation(overlayProgram, "texture1"), 3);
/* 192 */     GL20.glUniform1i(GL20.glGetUniformLocation(overlayProgram, "texture2"), 4);
/* 193 */     GL20.glUseProgram(0);
/*     */     
/* 195 */     int scopeBorderShader_V = GL20.glCreateShader(35633);
/* 196 */     GL20.glShaderSource(scopeBorderShader_V, "#version 120\r\nvarying vec4 color;\r\nvarying vec2 texcoord;\r\n\r\nvoid main()\r\n{\r\n    texcoord = (gl_TextureMatrix[0] * gl_MultiTexCoord0).st;\r\n    color = gl_Color;\r\n    gl_Position = gl_ProjectionMatrix  * gl_ModelViewMatrix * gl_Vertex;}");
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 207 */     GL20.glCompileShader(scopeBorderShader_V);
/* 208 */     if (GL20.glGetShaderi(scopeBorderShader_V, 35713) != 1) {
/* 209 */       throw new RuntimeException(GL20.glGetShaderInfoLog(scopeBorderShader_V, 512));
/*     */     }
/*     */     
/* 212 */     int scopeBorderShader_F = GL20.glCreateShader(35632);
/* 213 */     GL20.glShaderSource(scopeBorderShader_F, "#version 120\r\nuniform sampler2D texture;\r\nuniform vec2 size;\r\nvarying vec2 texcoord;\r\nvarying vec4 color;\r\nuniform float maskRange;\r\n//uniform float borderRange;\r\nuniform float drawRange;\r\nuniform float strength;\r\nuniform float scaleRangeY;\r\nuniform float scaleStrengthY;\r\nuniform float verticality;\r\n\r\nvoid main(){\r\n    float maskDir=length(vec2(maskRange,maskRange));\r\n    vec2 drawDir=vec2(drawRange,drawRange);\r\n    vec2 offset=texcoord.st-vec2(0.5,0.5);\r\n    vec2 offsetDir=vec2(abs(offset.x*(size.x/size.y)),abs(offset.y));\r\n    drawDir=normalize(offsetDir)*length(drawDir)*(1-verticality)+drawDir*verticality;\r\n    offsetDir.x=max(0,offsetDir.x-drawDir.x);\r\n    offsetDir.y=max(0,offsetDir.y-drawDir.y*scaleRangeY);\r\n    float len=length(offsetDir);\r\n    offset.x*=max(0,len)*strength;\r\n    offset.y*=max(0,len)*strength*scaleStrengthY;\r\n    vec4 albedo = texture2D(texture, texcoord.st+offset);\r\n    /*\r\n    float a=1;\r\n    a *= texture2D(texture, texcoord.st+vec2(borderRange*(size.x/size.y),0.0)).a;\r\n    a *= texture2D(texture, texcoord.st+vec2(-borderRange*(size.x/size.y),0.0)).a;\r\n    a *= texture2D(texture, texcoord.st+vec2(0.0,borderRange)).a;\r\n    a *= texture2D(texture, texcoord.st+vec2(0.0,-borderRange)).a;\r\n    albedo.a = a;\r\n    */\r\n    albedo.rgb*=step(length(offsetDir),maskDir);\r\n    gl_FragColor = albedo * color;\r\n}");
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 250 */     GL20.glCompileShader(scopeBorderShader_F);
/* 251 */     if (GL20.glGetShaderi(scopeBorderShader_F, 35713) != 1) {
/* 252 */       throw new RuntimeException(GL20.glGetShaderInfoLog(scopeBorderShader_F, 512));
/*     */     }
/*     */     
/* 255 */     scopeBorderProgram = GL20.glCreateProgram();
/* 256 */     GL20.glAttachShader(scopeBorderProgram, scopeBorderShader_V);
/* 257 */     GL20.glAttachShader(scopeBorderProgram, scopeBorderShader_F);
/* 258 */     GL20.glDeleteShader(scopeBorderShader_V);
/* 259 */     GL20.glDeleteShader(scopeBorderShader_F);
/* 260 */     GL20.glLinkProgram(scopeBorderProgram);
/* 261 */     if (GL20.glGetProgrami(scopeBorderProgram, 35714) != 1) {
/* 262 */       throw new RuntimeException(GL20.glGetProgramInfoLog(scopeBorderProgram, 512));
/*     */     }
/*     */     
/* 265 */     GL20.glUseProgram(scopeBorderProgram);
/* 266 */     GL20.glUniform1i(GL20.glGetUniformLocation(scopeBorderProgram, "texture"), 0);
/* 267 */     GL20.glUseProgram(0);
/*     */     
/* 269 */     int sunglassesvshader = GL20.glCreateShader(35633);
/* 270 */     GL20.glShaderSource(sunglassesvshader, "#version 120\r\nvarying vec4 color;\r\nvarying vec2 texcoord;\r\n\r\nvoid main()\r\n{\r\n    texcoord = (gl_TextureMatrix[0] * gl_MultiTexCoord0).st;\r\n    color = gl_Color;\r\n    gl_Position = gl_ProjectionMatrix  * gl_ModelViewMatrix * gl_Vertex;}");
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 281 */     GL20.glCompileShader(sunglassesvshader);
/* 282 */     if (GL20.glGetShaderi(sunglassesvshader, 35713) != 1) {
/* 283 */       throw new RuntimeException(GL20.glGetShaderInfoLog(sunglassesvshader, 512));
/*     */     }
/*     */     
/* 286 */     int sunglassesfshader = GL20.glCreateShader(35632);
/* 287 */     GL20.glShaderSource(sunglassesfshader, "#version 120\r\n\r\nuniform sampler2D texture;\r\nvarying vec4 color;\r\nvarying vec2 texcoord;\r\nvoid main(){\r\n    vec4 src=texture2D(texture, texcoord.st);\r\n    float flag=1.0-step(src.a,0);\r\n    gl_FragColor=vec4(1.0,0.0,0.0,src.a);\r\n    gl_FragDepth=0;\r\n}");
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 299 */     GL20.glCompileShader(sunglassesfshader);
/* 300 */     if (GL20.glGetShaderi(sunglassesfshader, 35713) != 1) {
/* 301 */       throw new RuntimeException(GL20.glGetShaderInfoLog(sunglassesfshader, 512));
/*     */     }
/*     */     
/* 304 */     sunglassesProgram = GL20.glCreateProgram();
/* 305 */     GL20.glAttachShader(sunglassesProgram, sunglassesvshader);
/* 306 */     GL20.glAttachShader(sunglassesProgram, sunglassesfshader);
/* 307 */     GL20.glDeleteShader(sunglassesvshader);
/* 308 */     GL20.glDeleteShader(sunglassesfshader);
/* 309 */     GL20.glLinkProgram(sunglassesProgram);
/* 310 */     if (GL20.glGetProgrami(sunglassesProgram, 35714) != 1) {
/* 311 */       throw new RuntimeException(GL20.glGetProgramInfoLog(sunglassesProgram, 512));
/*     */     }
/*     */     
/* 314 */     GL20.glUseProgram(sunglassesProgram);
/* 315 */     GL20.glUniform1i(GL20.glGetUniformLocation(sunglassesProgram, "texture"), 0);
/* 316 */     GL20.glUseProgram(0);
/*     */     
/* 318 */     int alphaDepthvshader = GL20.glCreateShader(35633);
/* 319 */     GL20.glShaderSource(alphaDepthvshader, "#version 120\r\nvarying vec4 color;\r\nvarying vec2 texcoord;\r\n\r\nvoid main()\r\n{\r\n    texcoord = (gl_TextureMatrix[0] * gl_MultiTexCoord0).st;\r\n    color = gl_Color;\r\n    gl_Position = gl_ProjectionMatrix  * gl_ModelViewMatrix * gl_Vertex;}");
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 330 */     GL20.glCompileShader(alphaDepthvshader);
/* 331 */     if (GL20.glGetShaderi(alphaDepthvshader, 35713) != 1) {
/* 332 */       throw new RuntimeException(GL20.glGetShaderInfoLog(alphaDepthvshader, 512));
/*     */     }
/*     */     
/* 335 */     int alphaDepthfshader = GL20.glCreateShader(35632);
/* 336 */     GL20.glShaderSource(alphaDepthfshader, "#version 120\r\n\r\nuniform sampler2D texture;//depth\r\nuniform sampler2D textureMask;//mask\r\nvarying vec4 color;\r\nvarying vec2 texcoord;\r\nvoid main(){\r\n    vec4 depth=texture2D(texture, texcoord.st);\r\n    vec4 mask=texture2D(textureMask, texcoord.st);\r\n    gl_FragDepth = depth.r;\r\n    gl_FragColor = mask;\r\n}");
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 349 */     GL20.glCompileShader(alphaDepthfshader);
/* 350 */     if (GL20.glGetShaderi(alphaDepthfshader, 35713) != 1) {
/* 351 */       throw new RuntimeException(GL20.glGetShaderInfoLog(alphaDepthfshader, 512));
/*     */     }
/*     */     
/* 354 */     alphaDepthProgram = GL20.glCreateProgram();
/* 355 */     GL20.glAttachShader(alphaDepthProgram, alphaDepthvshader);
/* 356 */     GL20.glAttachShader(alphaDepthProgram, alphaDepthfshader);
/* 357 */     GL20.glDeleteShader(alphaDepthvshader);
/* 358 */     GL20.glDeleteShader(alphaDepthfshader);
/* 359 */     GL20.glLinkProgram(alphaDepthProgram);
/* 360 */     if (GL20.glGetProgrami(alphaDepthProgram, 35714) != 1) {
/* 361 */       throw new RuntimeException(GL20.glGetProgramInfoLog(alphaDepthProgram, 512));
/*     */     }
/*     */     
/* 364 */     GL20.glUseProgram(alphaDepthProgram);
/* 365 */     GL20.glUniform1i(GL20.glGetUniformLocation(alphaDepthProgram, "texture"), 0);
/* 366 */     GL20.glUniform1i(GL20.glGetUniformLocation(alphaDepthProgram, "textureMask"), 3);
/* 367 */     GL20.glUseProgram(0);
/*     */   }
/*     */ }


/* Location:              C:\Users\DuduM\OneDrive\Área de Trabalho\mw10\modularwarfare-shining-2023.2.4.4f-fix10.jar!\com\modularwarfare\client\shader\Programs.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */