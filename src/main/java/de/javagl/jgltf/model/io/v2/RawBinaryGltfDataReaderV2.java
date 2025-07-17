/*     */ package de.javagl.jgltf.model.io.v2;
/*     */ 
/*     */ import de.javagl.jgltf.model.io.Buffers;
/*     */ import de.javagl.jgltf.model.io.RawGltfData;
/*     */ import java.io.IOException;
/*     */ import java.nio.ByteBuffer;
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
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
/*     */ public class RawBinaryGltfDataReaderV2
/*     */ {
/*  48 */   private static final Logger logger = Logger.getLogger(RawBinaryGltfDataReaderV2.class.getName());
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static final int BINARY_GLTF_VERSION_2_HEADER_LENGTH_IN_BYTES = 12;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static final int CHUNK_TYPE_JSON = 1313821514;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static final int CHUNK_TYPE_BIN = 5130562;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static RawGltfData readBinaryGltf(ByteBuffer data) throws IOException {
/*  78 */     int headerLength = 12;
/*  79 */     if (data.capacity() < headerLength)
/*     */     {
/*  81 */       throw new IOException("Expected header of size " + headerLength + ", but only found " + data
/*  82 */           .capacity() + " bytes");
/*     */     }
/*  84 */     int length = data.getInt(8);
/*  85 */     if (length > data.capacity())
/*     */     {
/*  87 */       throw new IOException("Data length is " + data
/*  88 */           .capacity() + ", expected " + length);
/*     */     }
/*  90 */     if (length < data.capacity()) {
/*     */       
/*  92 */       logger.info("Data length is " + data.capacity() + ", expected " + length + " - truncating");
/*     */       
/*  94 */       data = Buffers.createSlice(data, 0, length);
/*     */     } 
/*     */     
/*  97 */     List<Chunk> chunks = readChunks(data);
/*  98 */     if (chunks.isEmpty())
/*     */     {
/* 100 */       throw new IOException("Found no chunks in binary glTF data");
/*     */     }
/*     */     
/* 103 */     Chunk jsonChunk = chunks.get(0);
/* 104 */     if (jsonChunk.type != 1313821514)
/*     */     {
/* 106 */       throw new IOException("First chunk must be of type JSON (1313821514), but found " + jsonChunk.type);
/*     */     }
/*     */     
/* 109 */     ByteBuffer jsonData = jsonChunk.data;
/* 110 */     ByteBuffer binData = null;
/* 111 */     if (chunks.size() > 1) {
/*     */       
/* 113 */       Chunk binChunk = chunks.get(1);
/* 114 */       if (binChunk.type != 5130562)
/*     */       {
/* 116 */         throw new IOException("Second chunk must be of type BIN (5130562), but found " + jsonChunk.type);
/*     */       }
/*     */       
/* 119 */       binData = binChunk.data;
/*     */     } 
/* 121 */     return new RawGltfData(jsonData, binData);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static class Chunk
/*     */   {
/*     */     int length;
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     int type;
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     ByteBuffer data;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     private Chunk() {}
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static List<Chunk> readChunks(ByteBuffer data) throws IOException {
/* 154 */     int headerLength = 12;
/* 155 */     List<Chunk> chunks = new ArrayList<>();
/* 156 */     int offset = headerLength;
/* 157 */     while (offset < data.capacity()) {
/*     */       
/* 159 */       Chunk chunk = new Chunk();
/* 160 */       chunk.length = data.getInt(offset);
/* 161 */       offset += 4;
/* 162 */       chunk.type = data.getInt(offset);
/* 163 */       offset += 4;
/* 164 */       if (offset + chunk.length > data.capacity())
/*     */       {
/* 166 */         throw new IOException("The offset for the data of chunk " + chunks
/* 167 */             .size() + " is " + offset + ", its length is " + chunk.length + ", but " + (offset + chunk.length) + " is larger than capacity of the buffer, which is only " + data
/*     */ 
/*     */             
/* 170 */             .capacity());
/*     */       }
/* 172 */       if (chunk.length > 0)
/*     */       {
/* 174 */         chunk.data = Buffers.createSlice(data, offset, chunk.length);
/*     */       }
/* 176 */       offset += chunk.length;
/* 177 */       chunks.add(chunk);
/*     */     } 
/* 179 */     return chunks;
/*     */   }
/*     */ }


/* Location:              C:\Users\DuduM\OneDrive\Área de Trabalho\mw10\modularwarfare-shining-2023.2.4.4f-fix10.jar!\de\javagl\jgltf\model\io\v2\RawBinaryGltfDataReaderV2.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */