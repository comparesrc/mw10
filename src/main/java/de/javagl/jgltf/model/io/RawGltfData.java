/*     */ package de.javagl.jgltf.model.io;
/*     */ 
/*     */ import java.nio.ByteBuffer;
/*     */ import java.nio.charset.Charset;
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
/*     */ public final class RawGltfData
/*     */ {
/*     */   private final ByteBuffer jsonData;
/*     */   private final ByteBuffer binaryData;
/*     */   
/*     */   public RawGltfData(ByteBuffer jsonData, ByteBuffer binaryData) {
/*  63 */     this.jsonData = Objects.<ByteBuffer>requireNonNull(jsonData, "The jsonData may not be null");
/*     */     
/*  65 */     this.binaryData = binaryData;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getJsonString() {
/*  75 */     byte[] jsonDataArray = new byte[this.jsonData.capacity()];
/*  76 */     this.jsonData.slice().get(jsonDataArray);
/*  77 */     String jsonString = new String(jsonDataArray, Charset.forName("UTF-8"));
/*  78 */     return jsonString;
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
/*     */   public ByteBuffer getJsonData() {
/*  93 */     return Buffers.createSlice(this.jsonData);
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
/*     */   
/*     */   public ByteBuffer getBinaryData() {
/* 109 */     return Buffers.createSlice(this.binaryData);
/*     */   }
/*     */ }


/* Location:              C:\Users\DuduM\OneDrive\Área de Trabalho\mw10\modularwarfare-shining-2023.2.4.4f-fix10.jar!\de\javagl\jgltf\model\io\RawGltfData.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */