/*     */ package de.javagl.jgltf.model.io;
/*     */ 
/*     */ import com.fasterxml.jackson.databind.JsonNode;
/*     */ import com.fasterxml.jackson.databind.ObjectMapper;
/*     */ import de.javagl.jgltf.impl.v1.GlTF;
/*     */ import de.javagl.jgltf.impl.v2.GlTF;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.util.function.Consumer;
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
/*     */ final class GltfReader
/*     */ {
/*  49 */   private static final Logger logger = Logger.getLogger(GltfReader.class.getName());
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  56 */   private Consumer<? super JsonError> jsonErrorConsumer = JacksonUtils.loggingJsonErrorConsumer();
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private final ObjectMapper objectMapper;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private JsonNode rootNode;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   GltfReader() {
/*  73 */     this
/*  74 */       .objectMapper = JacksonUtils.createObjectMapper(this.jsonErrorConsumer);
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
/*     */   void setJsonErrorConsumer(Consumer<? super JsonError> jsonErrorConsumer) {
/*  86 */     this.jsonErrorConsumer = jsonErrorConsumer;
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
/*     */   void read(InputStream inputStream) throws IOException {
/* 101 */     JacksonUtils.configure(this.objectMapper, this.jsonErrorConsumer);
/* 102 */     this.rootNode = this.objectMapper.readTree(inputStream);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   String getVersion() {
/* 113 */     if (this.rootNode == null)
/*     */     {
/* 115 */       return null;
/*     */     }
/* 117 */     return getVersion(this.rootNode);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   int getMajorVersion() {
/* 127 */     if (this.rootNode == null)
/*     */     {
/* 129 */       return 0;
/*     */     }
/* 131 */     int[] version = VersionUtils.computeMajorMinorPatch(getVersion());
/* 132 */     return version[0];
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
/*     */   GlTF getAsGltfV1() {
/* 145 */     if (this.rootNode == null)
/*     */     {
/* 147 */       return null;
/*     */     }
/* 149 */     return (GlTF)this.objectMapper.convertValue(this.rootNode, GlTF.class);
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
/*     */   GlTF getAsGltfV2() {
/* 163 */     if (this.rootNode == null)
/*     */     {
/* 165 */       return null;
/*     */     }
/* 167 */     return (GlTF)this.objectMapper.convertValue(this.rootNode, GlTF.class);
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
/*     */   private static String getVersion(JsonNode rootNode) {
/* 181 */     JsonNode assetNode = rootNode.get("asset");
/* 182 */     if (assetNode == null)
/*     */     {
/* 184 */       return "1.0";
/*     */     }
/* 186 */     JsonNode versionNode = assetNode.get("version");
/* 187 */     if (versionNode == null)
/*     */     {
/* 189 */       return "1.0";
/*     */     }
/* 191 */     if (!versionNode.isValueNode()) {
/*     */       
/* 193 */       logger.warning("No valid 'version' property in 'asset'. Assuming version 1.0");
/*     */       
/* 195 */       return "1.0";
/*     */     } 
/* 197 */     return versionNode.asText();
/*     */   }
/*     */ }


/* Location:              C:\Users\DuduM\OneDrive\Área de Trabalho\mw10\modularwarfare-shining-2023.2.4.4f-fix10.jar!\de\javagl\jgltf\model\io\GltfReader.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */