/*     */ package com.modularwarfare.loader;
/*     */ 
/*     */ import com.modularwarfare.ModularWarfare;
/*     */ import com.modularwarfare.common.type.BaseType;
/*     */ import com.modularwarfare.loader.api.model.ObjModelRenderer;
/*     */ import com.modularwarfare.loader.part.Face;
/*     */ import com.modularwarfare.loader.part.ModelObject;
/*     */ import com.modularwarfare.loader.part.TextureCoordinate;
/*     */ import com.modularwarfare.loader.part.Vertex;
/*     */ import com.modularwarfare.utility.ZipContentPack;
/*     */ import java.io.BufferedReader;
/*     */ import java.io.File;
/*     */ import java.io.FileReader;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.io.InputStreamReader;
/*     */ import java.util.ArrayList;
/*     */ import java.util.regex.Matcher;
/*     */ import java.util.regex.Pattern;
/*     */ import moe.komi.mwprotect.IZipEntry;
/*     */ import net.minecraft.client.Minecraft;
/*     */ import net.minecraft.client.resources.IResource;
/*     */ import net.minecraft.util.ResourceLocation;
/*     */ import net.minecraftforge.fml.common.Loader;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class ObjModelBuilder
/*     */ {
/*  31 */   private static Pattern vertexPattern = Pattern.compile("(v( (\\-){0,1}\\d+(\\.\\d+)?){3,4} *\\n)|(v( (\\-){0,1}\\d+(\\.\\d+)?){3,4} *$)");
/*  32 */   private static Pattern vertexNormalPattern = Pattern.compile("(vn( (\\-){0,1}\\d+(\\.\\d+)?){3,4} *\\n)|(vn( (\\-){0,1}\\d+(\\.\\d+)?){3,4} *$)");
/*  33 */   private static Pattern textureCoordinatePattern = Pattern.compile("(vt( (\\-){0,1}\\d+\\.\\d+){2,3} *\\n)|(vt( (\\-){0,1}\\d+(\\.\\d+)?){2,3} *$)");
/*  34 */   private static Pattern face_V_VT_VN_Pattern = Pattern.compile("(f( \\d+/\\d+/\\d+){3,4} *\\n)|(f( \\d+/\\d+/\\d+){3,4} *$)");
/*  35 */   private static Pattern face_V_VT_Pattern = Pattern.compile("(f( \\d+/\\d+){3,4} *\\n)|(f( \\d+/\\d+){3,4} *$)");
/*  36 */   private static Pattern face_V_VN_Pattern = Pattern.compile("(f( \\d+//\\d+){3,4} *\\n)|(f( \\d+//\\d+){3,4} *$)");
/*  37 */   private static Pattern face_V_Pattern = Pattern.compile("(f( \\d+){3,4} *\\n)|(f( \\d+){3,4} *$)");
/*  38 */   private static Pattern groupObjectPattern = Pattern.compile("([go]( [\\w\\d\\.]+) *\\n)|([go]( [\\w\\d\\.]+) *$)");
/*     */   
/*     */   private static Matcher vertexMatcher;
/*     */   private static Matcher vertexNormalMatcher;
/*     */   private static Matcher textureCoordinateMatcher;
/*     */   private static Matcher face_V_VT_VN_Matcher;
/*  44 */   public ArrayList<Vertex> vertices = new ArrayList<>(); private static Matcher face_V_VT_Matcher; private static Matcher face_V_VN_Matcher; private static Matcher face_V_Matcher; private static Matcher groupObjectMatcher;
/*  45 */   private ArrayList<Vertex> vertexNormals = new ArrayList<>();
/*  46 */   private ArrayList<TextureCoordinate> textureCoordinates = new ArrayList<>();
/*     */   
/*     */   private ModelObject currentModelObject;
/*     */   
/*     */   private String fileLocation;
/*     */   private ResourceLocation resourceLocation;
/*  52 */   private ArrayList<ObjModelRenderer> renderers = new ArrayList<>();
/*     */   
/*     */   public ObjModelBuilder(String rl) throws ModelFormatException {
/*  55 */     this.fileLocation = rl;
/*     */   }
/*     */   
/*     */   public ObjModelBuilder(ResourceLocation rl) throws ModelFormatException {
/*  59 */     this.resourceLocation = rl;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static boolean isValidVertexLine(String line) {
/*  68 */     if (vertexMatcher != null) {
/*  69 */       vertexMatcher.reset();
/*     */     }
/*     */     
/*  72 */     vertexMatcher = vertexPattern.matcher(line);
/*  73 */     return vertexMatcher.matches();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static boolean isValidVertexNormalLine(String line) {
/*  82 */     if (vertexNormalMatcher != null) {
/*  83 */       vertexNormalMatcher.reset();
/*     */     }
/*     */     
/*  86 */     vertexNormalMatcher = vertexNormalPattern.matcher(line);
/*  87 */     return vertexNormalMatcher.matches();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static boolean isValidTextureCoordinateLine(String line) {
/*  96 */     if (textureCoordinateMatcher != null) {
/*  97 */       textureCoordinateMatcher.reset();
/*     */     }
/*     */     
/* 100 */     textureCoordinateMatcher = textureCoordinatePattern.matcher(line);
/* 101 */     return textureCoordinateMatcher.matches();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static boolean isValidFace_V_VT_VN_Line(String line) {
/* 110 */     if (face_V_VT_VN_Matcher != null) {
/* 111 */       face_V_VT_VN_Matcher.reset();
/*     */     }
/*     */     
/* 114 */     face_V_VT_VN_Matcher = face_V_VT_VN_Pattern.matcher(line);
/* 115 */     return face_V_VT_VN_Matcher.matches();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static boolean isValidFace_V_VT_Line(String line) {
/* 124 */     if (face_V_VT_Matcher != null) {
/* 125 */       face_V_VT_Matcher.reset();
/*     */     }
/*     */     
/* 128 */     face_V_VT_Matcher = face_V_VT_Pattern.matcher(line);
/* 129 */     return face_V_VT_Matcher.matches();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static boolean isValidFace_V_VN_Line(String line) {
/* 138 */     if (face_V_VN_Matcher != null) {
/* 139 */       face_V_VN_Matcher.reset();
/*     */     }
/*     */     
/* 142 */     face_V_VN_Matcher = face_V_VN_Pattern.matcher(line);
/* 143 */     return face_V_VN_Matcher.matches();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static boolean isValidFace_V_Line(String line) {
/* 152 */     if (face_V_Matcher != null) {
/* 153 */       face_V_Matcher.reset();
/*     */     }
/*     */     
/* 156 */     face_V_Matcher = face_V_Pattern.matcher(line);
/* 157 */     return face_V_Matcher.matches();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static boolean isValidFaceLine(String line) {
/* 166 */     return (isValidFace_V_VT_VN_Line(line) || isValidFace_V_VT_Line(line) || isValidFace_V_VN_Line(line) || isValidFace_V_Line(line));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static boolean isValidGroupObjectLine(String line) {
/* 175 */     if (groupObjectMatcher != null) {
/* 176 */       groupObjectMatcher.reset();
/*     */     }
/*     */     
/* 179 */     groupObjectMatcher = groupObjectPattern.matcher(line);
/* 180 */     return groupObjectMatcher.matches();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ObjModel loadModelFromZIP(BaseType baseType) throws ModelFormatException {
/* 187 */     int lineCount = 0;
/* 188 */     ObjModel model = new ObjModel();
/* 189 */     boolean found = false;
/*     */     
/* 191 */     if (ModularWarfare.zipContentsPack.containsKey(baseType.contentPack)) {
/*     */       
/* 193 */       if (((ZipContentPack)ModularWarfare.zipContentsPack.get(baseType.contentPack)).models_cache.containsKey(this.fileLocation)) {
/* 194 */         return (ObjModel)((ZipContentPack)ModularWarfare.zipContentsPack.get(baseType.contentPack)).models_cache.get(this.fileLocation);
/*     */       }
/*     */       
/* 197 */       IZipEntry foundFile = ((ZipContentPack)ModularWarfare.zipContentsPack.get(baseType.contentPack)).fileHeaders.stream().filter(fileHeader -> fileHeader.getFileName().equalsIgnoreCase(this.fileLocation)).findFirst().orElse(null);
/*     */       
/* 199 */       if (foundFile != null) {
/* 200 */         found = true;
/* 201 */         InputStream stream = null;
/*     */         try {
/* 203 */           stream = foundFile.getInputStream();
/*     */           
/* 205 */           try (BufferedReader reader = new BufferedReader(new InputStreamReader(stream))) {
/*     */             String currentLine;
/*     */             
/* 208 */             while ((currentLine = reader.readLine()) != null) {
/* 209 */               lineCount++;
/* 210 */               currentLine = currentLine.replaceAll("\\s+", " ").trim();
/*     */               
/* 212 */               if (currentLine.startsWith("#") || currentLine.length() == 0)
/*     */                 continue; 
/* 214 */               if (currentLine.startsWith("v ")) {
/* 215 */                 Vertex vertex = parseVertex(currentLine, lineCount);
/* 216 */                 if (vertex != null)
/* 217 */                   this.vertices.add(vertex);  continue;
/*     */               } 
/* 219 */               if (currentLine.startsWith("vn ")) {
/* 220 */                 Vertex vertex = parseVertexNormal(currentLine, lineCount);
/* 221 */                 if (vertex != null)
/* 222 */                   this.vertexNormals.add(vertex);  continue;
/*     */               } 
/* 224 */               if (currentLine.startsWith("vt ")) {
/* 225 */                 TextureCoordinate textureCoordinate = parseTextureCoordinate(currentLine, lineCount);
/* 226 */                 if (textureCoordinate != null)
/* 227 */                   this.textureCoordinates.add(textureCoordinate);  continue;
/*     */               } 
/* 229 */               if (currentLine.startsWith("f ")) {
/*     */                 
/* 231 */                 if (this.currentModelObject == null) {
/* 232 */                   this.currentModelObject = new ModelObject("Default");
/*     */                 }
/*     */                 
/* 235 */                 Face face = parseFace(currentLine, lineCount);
/*     */                 
/* 237 */                 if (face != null)
/* 238 */                   this.currentModelObject.faces.add(face);  continue;
/*     */               } 
/* 240 */               if ((currentLine.startsWith("g ") | currentLine.startsWith("o ")) != 0) {
/* 241 */                 ModelObject group = parseGroupObject(currentLine, lineCount);
/* 242 */                 if (group != null && 
/* 243 */                   this.currentModelObject != null) {
/* 244 */                   this.renderers.add(new ObjModelRenderer(model, this.currentModelObject));
/*     */                 }
/*     */ 
/*     */                 
/* 248 */                 this.currentModelObject = group;
/*     */               } 
/*     */             } 
/* 251 */             this.renderers.add(new ObjModelRenderer(model, this.currentModelObject));
/* 252 */           } catch (IOException e) {
/* 253 */             e.printStackTrace();
/*     */           } 
/* 255 */         } catch (IOException e) {
/* 256 */           e.printStackTrace();
/*     */         } 
/*     */       } 
/* 259 */       ((ZipContentPack)ModularWarfare.zipContentsPack.get(baseType.contentPack)).models_cache.put(this.fileLocation, model);
/*     */     } 
/*     */     
/* 262 */     if (!found) {
/* 263 */       ModularWarfare.LOGGER.warn("The model file in " + baseType.contentPack + " at: " + this.fileLocation + " has not been found");
/*     */     }
/*     */     
/* 266 */     model.setParts(this.renderers);
/* 267 */     return model;
/*     */   }
/*     */ 
/*     */   
/*     */   public ObjModel loadModelFromRL() throws ModelFormatException {
/* 272 */     int lineCount = 0;
/* 273 */     ObjModel model = new ObjModel();
/*     */     
/* 275 */     try(IResource objFile = Minecraft.func_71410_x().func_110442_L().func_110536_a(this.resourceLocation); 
/* 276 */         BufferedReader reader = new BufferedReader(new InputStreamReader(objFile.func_110527_b()))) {
/*     */       String currentLine;
/*     */       
/* 279 */       while ((currentLine = reader.readLine()) != null) {
/* 280 */         lineCount++;
/* 281 */         currentLine = currentLine.replaceAll("\\s+", " ").trim();
/*     */         
/* 283 */         if (currentLine.startsWith("#") || currentLine.length() == 0)
/*     */           continue; 
/* 285 */         if (currentLine.startsWith("v ")) {
/* 286 */           Vertex vertex = parseVertex(currentLine, lineCount);
/* 287 */           if (vertex != null)
/* 288 */             this.vertices.add(vertex);  continue;
/*     */         } 
/* 290 */         if (currentLine.startsWith("vn ")) {
/* 291 */           Vertex vertex = parseVertexNormal(currentLine, lineCount);
/* 292 */           if (vertex != null)
/* 293 */             this.vertexNormals.add(vertex);  continue;
/*     */         } 
/* 295 */         if (currentLine.startsWith("vt ")) {
/* 296 */           TextureCoordinate textureCoordinate = parseTextureCoordinate(currentLine, lineCount);
/* 297 */           if (textureCoordinate != null)
/* 298 */             this.textureCoordinates.add(textureCoordinate);  continue;
/*     */         } 
/* 300 */         if (currentLine.startsWith("f ")) {
/*     */           
/* 302 */           if (this.currentModelObject == null) {
/* 303 */             this.currentModelObject = new ModelObject("Default");
/*     */           }
/*     */           
/* 306 */           Face face = parseFace(currentLine, lineCount);
/*     */           
/* 308 */           if (face != null)
/* 309 */             this.currentModelObject.faces.add(face);  continue;
/*     */         } 
/* 311 */         if ((currentLine.startsWith("g ") | currentLine.startsWith("o ")) != 0) {
/* 312 */           ModelObject group = parseGroupObject(currentLine, lineCount);
/*     */           
/* 314 */           if (group != null && 
/* 315 */             this.currentModelObject != null) {
/* 316 */             this.renderers.add(new ObjModelRenderer(model, this.currentModelObject));
/*     */           }
/*     */ 
/*     */           
/* 320 */           this.currentModelObject = group;
/*     */         } 
/*     */       } 
/*     */       
/* 324 */       this.renderers.add(new ObjModelRenderer(model, this.currentModelObject));
/*     */     }
/* 326 */     catch (IOException e) {
/* 327 */       throw new ModelFormatException("IO Exception reading model format", e);
/*     */     } 
/*     */     
/* 330 */     model.setParts(this.renderers);
/* 331 */     return model;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public ObjModel loadModel() throws ModelFormatException {
/* 337 */     int lineCount = 0;
/* 338 */     ObjModel model = new ObjModel();
/*     */     
/* 340 */     File modelFile = null;
/* 341 */     String absPath = (new File(Loader.instance().getConfigDir().getParent(), "ModularWarfare")).getAbsolutePath();
/*     */     
/* 343 */     if (!absPath.endsWith("/") || !absPath.endsWith("\\"))
/* 344 */       absPath = absPath + "/"; 
/* 345 */     modelFile = checkValidPath(absPath + this.fileLocation);
/*     */     
/* 347 */     if (modelFile == null || !modelFile.exists()) {
/* 348 */       ModularWarfare.LOGGER.info("The staticModel with the name " + this.fileLocation + " does not exist.");
/* 349 */       return null;
/*     */     } 
/*     */     
/* 352 */     try (BufferedReader reader = new BufferedReader(new FileReader(modelFile))) {
/*     */       String currentLine;
/*     */       
/* 355 */       while ((currentLine = reader.readLine()) != null) {
/* 356 */         lineCount++;
/* 357 */         currentLine = currentLine.replaceAll("\\s+", " ").trim();
/*     */         
/* 359 */         if (currentLine.startsWith("#") || currentLine.length() == 0)
/*     */           continue; 
/* 361 */         if (currentLine.startsWith("v ")) {
/* 362 */           Vertex vertex = parseVertex(currentLine, lineCount);
/* 363 */           if (vertex != null)
/* 364 */             this.vertices.add(vertex);  continue;
/*     */         } 
/* 366 */         if (currentLine.startsWith("vn ")) {
/* 367 */           Vertex vertex = parseVertexNormal(currentLine, lineCount);
/* 368 */           if (vertex != null)
/* 369 */             this.vertexNormals.add(vertex);  continue;
/*     */         } 
/* 371 */         if (currentLine.startsWith("vt ")) {
/* 372 */           TextureCoordinate textureCoordinate = parseTextureCoordinate(currentLine, lineCount);
/* 373 */           if (textureCoordinate != null)
/* 374 */             this.textureCoordinates.add(textureCoordinate);  continue;
/*     */         } 
/* 376 */         if (currentLine.startsWith("f ")) {
/*     */           
/* 378 */           if (this.currentModelObject == null) {
/* 379 */             this.currentModelObject = new ModelObject("Default");
/*     */           }
/*     */           
/* 382 */           Face face = parseFace(currentLine, lineCount);
/*     */           
/* 384 */           if (face != null)
/* 385 */             this.currentModelObject.faces.add(face);  continue;
/*     */         } 
/* 387 */         if ((currentLine.startsWith("g ") | currentLine.startsWith("o ")) != 0) {
/* 388 */           ModelObject group = parseGroupObject(currentLine, lineCount);
/*     */           
/* 390 */           if (group != null && 
/* 391 */             this.currentModelObject != null) {
/* 392 */             this.renderers.add(new ObjModelRenderer(model, this.currentModelObject));
/*     */           }
/*     */ 
/*     */           
/* 396 */           this.currentModelObject = group;
/*     */         } 
/*     */       } 
/*     */       
/* 400 */       this.renderers.add(new ObjModelRenderer(model, this.currentModelObject));
/* 401 */     } catch (IOException e) {
/* 402 */       e.printStackTrace();
/*     */     } 
/*     */ 
/*     */     
/* 406 */     String[] path = modelFile.getAbsolutePath().split("/");
/* 407 */     String fileName = path[path.length - 1].split("\\.")[0];
/* 408 */     StringBuilder newPath = new StringBuilder();
/* 409 */     for (int i = 0; i < path.length - 1; i++) {
/* 410 */       if (i != 0) {
/* 411 */         newPath.append("/");
/*     */       }
/*     */       
/* 414 */       newPath.append(path[i]);
/*     */     } 
/*     */     
/* 417 */     model.setParts(this.renderers);
/* 418 */     return model;
/*     */   }
/*     */ 
/*     */   
/*     */   private Vertex parseVertex(String line, int lineCount) throws ModelFormatException {
/* 423 */     if (isValidVertexLine(line)) {
/* 424 */       line = line.substring(line.indexOf(" ") + 1);
/* 425 */       String[] tokens = line.split(" ");
/*     */       
/*     */       try {
/* 428 */         if (tokens.length == 2)
/* 429 */           return new Vertex(Float.parseFloat(tokens[0]), Float.parseFloat(tokens[1])); 
/* 430 */         if (tokens.length == 3) {
/* 431 */           return new Vertex(Float.parseFloat(tokens[0]), Float.parseFloat(tokens[1]), Float.parseFloat(tokens[2]));
/*     */         }
/* 433 */       } catch (NumberFormatException e) {
/* 434 */         throw new ModelFormatException(String.format("Number formatting error at line %d", new Object[] { Integer.valueOf(lineCount) }), e);
/*     */       } 
/*     */     } else {
/* 437 */       throw new ModelFormatException("Error parsing entry ('" + line + "', line " + lineCount + ") in file '" + this.fileLocation + "' - Incorrect format");
/*     */     } 
/*     */     
/* 440 */     return null;
/*     */   }
/*     */   
/*     */   private Vertex parseVertexNormal(String line, int lineCount) throws ModelFormatException {
/* 444 */     if (isValidVertexNormalLine(line)) {
/* 445 */       line = line.substring(line.indexOf(" ") + 1);
/* 446 */       String[] tokens = line.split(" ");
/*     */       
/*     */       try {
/* 449 */         if (tokens.length == 3)
/* 450 */           return new Vertex(Float.parseFloat(tokens[0]), Float.parseFloat(tokens[1]), Float.parseFloat(tokens[2])); 
/* 451 */       } catch (NumberFormatException e) {
/* 452 */         throw new ModelFormatException(String.format("Number formatting error at line %d", new Object[] { Integer.valueOf(lineCount) }), e);
/*     */       } 
/*     */     } else {
/* 455 */       throw new ModelFormatException("Error parsing entry ('" + line + "', line " + lineCount + ") in file '" + this.fileLocation + "' - Incorrect format");
/*     */     } 
/*     */     
/* 458 */     return null;
/*     */   }
/*     */   
/*     */   private TextureCoordinate parseTextureCoordinate(String line, int lineCount) throws ModelFormatException {
/* 462 */     if (isValidTextureCoordinateLine(line)) {
/* 463 */       line = line.substring(line.indexOf(" ") + 1);
/* 464 */       String[] tokens = line.split(" ");
/*     */       
/*     */       try {
/* 467 */         if (tokens.length == 2)
/* 468 */           return new TextureCoordinate(Float.parseFloat(tokens[0]), 1.0F - Float.parseFloat(tokens[1])); 
/* 469 */         if (tokens.length == 3)
/* 470 */           return new TextureCoordinate(Float.parseFloat(tokens[0]), 1.0F - Float.parseFloat(tokens[1]), Float.parseFloat(tokens[2])); 
/* 471 */       } catch (NumberFormatException e) {
/* 472 */         throw new ModelFormatException(String.format("Number formatting error at line %d", new Object[] { Integer.valueOf(lineCount) }), e);
/*     */       } 
/*     */     } else {
/* 475 */       throw new ModelFormatException("Error parsing entry ('" + line + "', line " + lineCount + ") in file '" + this.fileLocation + "' - Incorrect format");
/*     */     } 
/*     */     
/* 478 */     return null;
/*     */   }
/*     */ 
/*     */   
/*     */   private Face parseFace(String line, int lineCount) throws ModelFormatException {
/*     */     Face face;
/* 484 */     if (isValidFaceLine(line)) {
/* 485 */       face = new Face();
/*     */       
/* 487 */       String trimmedLine = line.substring(line.indexOf(" ") + 1);
/* 488 */       String[] tokens = trimmedLine.split(" ");
/*     */ 
/*     */       
/* 491 */       if (tokens.length == 3) {
/* 492 */         if (this.currentModelObject.glDrawingMode == -1) {
/* 493 */           this.currentModelObject.glDrawingMode = 4;
/* 494 */         } else if (this.currentModelObject.glDrawingMode != 4) {
/* 495 */           throw new ModelFormatException("Error parsing entry ('" + line + "', line " + lineCount + ") in file '" + this.fileLocation + "' - Invalid number of points for face (expected 4, found " + tokens.length + ")");
/*     */         } 
/* 497 */       } else if (tokens.length == 4) {
/* 498 */         if (this.currentModelObject.glDrawingMode == -1) {
/* 499 */           this.currentModelObject.glDrawingMode = 7;
/* 500 */         } else if (this.currentModelObject.glDrawingMode != 7) {
/* 501 */           throw new ModelFormatException("Error parsing entry ('" + line + "', line " + lineCount + ") in file '" + this.fileLocation + "' - Invalid number of points for face (expected 3, found " + tokens.length + ")");
/*     */         } 
/*     */       } 
/*     */ 
/*     */       
/* 506 */       if (isValidFace_V_VT_VN_Line(line)) {
/* 507 */         face.vertices = new Vertex[tokens.length];
/* 508 */         face.textureCoordinates = new TextureCoordinate[tokens.length];
/* 509 */         face.vertexNormals = new Vertex[tokens.length];
/*     */         
/* 511 */         for (int i = 0; i < tokens.length; i++) {
/* 512 */           String[] subTokens = tokens[i].split("/");
/*     */           
/* 514 */           face.vertices[i] = this.vertices.get(Integer.parseInt(subTokens[0]) - 1);
/* 515 */           face.textureCoordinates[i] = this.textureCoordinates.get(Integer.parseInt(subTokens[1]) - 1);
/* 516 */           face.vertexNormals[i] = this.vertexNormals.get(Integer.parseInt(subTokens[2]) - 1);
/*     */         } 
/*     */         
/* 519 */         face.faceNormal = face.calculateFaceNormal();
/*     */       
/*     */       }
/* 522 */       else if (isValidFace_V_VT_Line(line)) {
/* 523 */         face.vertices = new Vertex[tokens.length];
/* 524 */         face.textureCoordinates = new TextureCoordinate[tokens.length];
/*     */         
/* 526 */         for (int i = 0; i < tokens.length; i++) {
/* 527 */           String[] subTokens = tokens[i].split("/");
/*     */           
/* 529 */           face.vertices[i] = this.vertices.get(Integer.parseInt(subTokens[0]) - 1);
/* 530 */           face.textureCoordinates[i] = this.textureCoordinates.get(Integer.parseInt(subTokens[1]) - 1);
/*     */         } 
/*     */         
/* 533 */         face.faceNormal = face.calculateFaceNormal();
/*     */       
/*     */       }
/* 536 */       else if (isValidFace_V_VN_Line(line)) {
/* 537 */         face.vertices = new Vertex[tokens.length];
/* 538 */         face.vertexNormals = new Vertex[tokens.length];
/*     */         
/* 540 */         for (int i = 0; i < tokens.length; i++) {
/* 541 */           String[] subTokens = tokens[i].split("//");
/*     */           
/* 543 */           face.vertices[i] = this.vertices.get(Integer.parseInt(subTokens[0]) - 1);
/* 544 */           face.vertexNormals[i] = this.vertexNormals.get(Integer.parseInt(subTokens[1]) - 1);
/*     */         } 
/*     */         
/* 547 */         face.faceNormal = face.calculateFaceNormal();
/*     */       
/*     */       }
/* 550 */       else if (isValidFace_V_Line(line)) {
/* 551 */         face.vertices = new Vertex[tokens.length];
/*     */         
/* 553 */         for (int i = 0; i < tokens.length; i++) {
/* 554 */           face.vertices[i] = this.vertices.get(Integer.parseInt(tokens[i]) - 1);
/*     */         }
/*     */         
/* 557 */         face.faceNormal = face.calculateFaceNormal();
/*     */       } else {
/* 559 */         throw new ModelFormatException("Error parsing entry ('" + line + "', line " + lineCount + ") in file '" + this.fileLocation + "' - Incorrect format");
/*     */       } 
/*     */     } else {
/* 562 */       throw new ModelFormatException("Error parsing entry ('" + line + "', line " + lineCount + ") in file '" + this.fileLocation + "' - Incorrect format");
/*     */     } 
/*     */ 
/*     */     
/* 566 */     return face;
/*     */   }
/*     */   
/*     */   private ModelObject parseGroupObject(String line, int lineCount) throws ModelFormatException {
/* 570 */     ModelObject group = null;
/*     */     
/* 572 */     if (isValidGroupObjectLine(line)) {
/* 573 */       String trimmedLine = line.substring(line.indexOf(" ") + 1);
/*     */       
/* 575 */       if (trimmedLine.length() > 0) {
/* 576 */         group = new ModelObject(trimmedLine);
/*     */       }
/*     */     } else {
/* 579 */       throw new ModelFormatException("Error parsing entry ('" + line + "', line " + lineCount + ") in file '" + this.fileLocation + "' - Incorrect format");
/*     */     } 
/*     */     
/* 582 */     return group;
/*     */   }
/*     */   
/*     */   public File checkValidPath(String path) {
/* 586 */     File file = null;
/*     */     
/* 588 */     String absPath = path;
/*     */     
/* 590 */     if (!path.endsWith(".obj")) {
/* 591 */       absPath = absPath + ".obj";
/*     */     }
/* 593 */     file = new File(absPath);
/* 594 */     if (file == null || !file.exists())
/* 595 */       return null; 
/* 596 */     return file;
/*     */   }
/*     */ }


/* Location:              C:\Users\DuduM\OneDrive\Área de Trabalho\mw9\modularwarfare-shining-2023.2.4.4f-fix9.jar!\com\modularwarfare\loader\ObjModelBuilder.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */