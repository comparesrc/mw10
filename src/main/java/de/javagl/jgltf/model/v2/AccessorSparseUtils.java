/*     */ package de.javagl.jgltf.model.v2;
/*     */ 
/*     */ import de.javagl.jgltf.model.AccessorByteData;
/*     */ import de.javagl.jgltf.model.AccessorData;
/*     */ import de.javagl.jgltf.model.AccessorFloatData;
/*     */ import de.javagl.jgltf.model.AccessorIntData;
/*     */ import de.javagl.jgltf.model.AccessorShortData;
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
/*     */ 
/*     */ 
/*     */ class AccessorSparseUtils
/*     */ {
/*  52 */   private static final Logger logger = Logger.getLogger(AccessorSparseUtils.class.getName());
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static int[] extractIndices(AccessorData accessorData) {
/*  68 */     if (accessorData.getComponentType() == byte.class) {
/*     */       
/*  70 */       AccessorByteData accessorByteData = (AccessorByteData)accessorData;
/*     */       
/*  72 */       int numElements = accessorByteData.getNumElements();
/*  73 */       int[] indices = new int[numElements];
/*  74 */       for (int i = 0; i < numElements; i++)
/*     */       {
/*  76 */         indices[i] = accessorByteData.getInt(i, 0);
/*     */       }
/*  78 */       return indices;
/*     */     } 
/*  80 */     if (accessorData.getComponentType() == short.class) {
/*     */       
/*  82 */       AccessorShortData accessorShortData = (AccessorShortData)accessorData;
/*     */       
/*  84 */       int numElements = accessorShortData.getNumElements();
/*  85 */       int[] indices = new int[numElements];
/*  86 */       for (int i = 0; i < numElements; i++)
/*     */       {
/*  88 */         indices[i] = accessorShortData.getInt(i, 0);
/*     */       }
/*  90 */       return indices;
/*     */     } 
/*  92 */     if (accessorData.getComponentType() == int.class) {
/*     */       
/*  94 */       AccessorIntData accessorIntData = (AccessorIntData)accessorData;
/*     */       
/*  96 */       int numElements = accessorIntData.getNumElements();
/*  97 */       int[] indices = new int[numElements];
/*  98 */       for (int i = 0; i < numElements; i++)
/*     */       {
/* 100 */         indices[i] = accessorIntData.get(i, 0);
/*     */       }
/* 102 */       return indices;
/*     */     } 
/* 104 */     throw new IllegalArgumentException("Invalid type for indices: " + accessorData
/* 105 */         .getComponentType());
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static void substituteAccessorData(AccessorData denseAccessorData, AccessorData baseAccessorData, AccessorData sparseIndicesAccessorData, AccessorData sparseValuesAccessorData) {
/* 142 */     Class<?> componentType = denseAccessorData.getComponentType();
/* 143 */     if (componentType == byte.class) {
/*     */       
/* 145 */       AccessorByteData sparseValuesAccessorByteData = (AccessorByteData)sparseValuesAccessorData;
/*     */       
/* 147 */       AccessorByteData baseAccessorByteData = (AccessorByteData)baseAccessorData;
/*     */       
/* 149 */       AccessorByteData denseAccessorByteData = (AccessorByteData)denseAccessorData;
/*     */       
/* 151 */       substituteByteAccessorData(denseAccessorByteData, baseAccessorByteData, sparseIndicesAccessorData, sparseValuesAccessorByteData);
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     }
/* 157 */     else if (componentType == short.class) {
/*     */       
/* 159 */       AccessorShortData sparseValuesAccessorShortData = (AccessorShortData)sparseValuesAccessorData;
/*     */       
/* 161 */       AccessorShortData baseAccessorShortData = (AccessorShortData)baseAccessorData;
/*     */       
/* 163 */       AccessorShortData denseAccessorShortData = (AccessorShortData)denseAccessorData;
/*     */       
/* 165 */       substituteShortAccessorData(denseAccessorShortData, baseAccessorShortData, sparseIndicesAccessorData, sparseValuesAccessorShortData);
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     }
/* 171 */     else if (componentType == int.class) {
/*     */       
/* 173 */       AccessorIntData sparseValuesAccessorIntData = (AccessorIntData)sparseValuesAccessorData;
/*     */       
/* 175 */       AccessorIntData baseAccessorIntData = (AccessorIntData)baseAccessorData;
/*     */       
/* 177 */       AccessorIntData denseAccessorIntData = (AccessorIntData)denseAccessorData;
/*     */       
/* 179 */       substituteIntAccessorData(denseAccessorIntData, baseAccessorIntData, sparseIndicesAccessorData, sparseValuesAccessorIntData);
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     }
/* 185 */     else if (componentType == float.class) {
/*     */       
/* 187 */       AccessorFloatData sparseValuesAccessorFloatData = (AccessorFloatData)sparseValuesAccessorData;
/*     */       
/* 189 */       AccessorFloatData baseAccessorFloatData = (AccessorFloatData)baseAccessorData;
/*     */       
/* 191 */       AccessorFloatData denseAccessorFloatData = (AccessorFloatData)denseAccessorData;
/*     */ 
/*     */       
/* 194 */       substituteFloatAccessorData(denseAccessorFloatData, baseAccessorFloatData, sparseIndicesAccessorData, sparseValuesAccessorFloatData);
/*     */ 
/*     */     
/*     */     }
/*     */     else {
/*     */ 
/*     */ 
/*     */       
/* 202 */       logger.warning("Invalid component type for accessor: " + componentType);
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static void substituteByteAccessorData(AccessorByteData denseAccessorData, AccessorByteData baseAccessorData, AccessorData sparseIndicesAccessorData, AccessorByteData sparseValuesAccessorData) {
/* 224 */     int numElements = denseAccessorData.getNumElements();
/*     */     
/* 226 */     int numComponentsPerElement = denseAccessorData.getNumComponentsPerElement();
/*     */     
/* 228 */     if (baseAccessorData != null)
/*     */     {
/*     */       
/* 231 */       for (int e = 0; e < numElements; e++) {
/*     */         
/* 233 */         for (int c = 0; c < numComponentsPerElement; c++) {
/*     */           
/* 235 */           byte value = baseAccessorData.get(e, c);
/* 236 */           denseAccessorData.set(e, c, value);
/*     */         } 
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/* 242 */     int[] indices = extractIndices(sparseIndicesAccessorData);
/* 243 */     for (int i = 0; i < indices.length; i++) {
/*     */       
/* 245 */       int targetElementIndex = indices[i];
/* 246 */       for (int c = 0; c < numComponentsPerElement; c++) {
/*     */         
/* 248 */         byte substitution = sparseValuesAccessorData.get(i, c);
/* 249 */         denseAccessorData.set(targetElementIndex, c, substitution);
/*     */       } 
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
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static void substituteShortAccessorData(AccessorShortData denseAccessorData, AccessorShortData baseAccessorData, AccessorData sparseIndicesAccessorData, AccessorShortData sparseValuesAccessorData) {
/* 270 */     int numElements = denseAccessorData.getNumElements();
/*     */     
/* 272 */     int numComponentsPerElement = denseAccessorData.getNumComponentsPerElement();
/*     */     
/* 274 */     if (baseAccessorData != null)
/*     */     {
/*     */       
/* 277 */       for (int e = 0; e < numElements; e++) {
/*     */         
/* 279 */         for (int c = 0; c < numComponentsPerElement; c++) {
/*     */           
/* 281 */           short value = baseAccessorData.get(e, c);
/* 282 */           denseAccessorData.set(e, c, value);
/*     */         } 
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/* 288 */     int[] indices = extractIndices(sparseIndicesAccessorData);
/* 289 */     for (int i = 0; i < indices.length; i++) {
/*     */       
/* 291 */       int targetElementIndex = indices[i];
/* 292 */       for (int c = 0; c < numComponentsPerElement; c++) {
/*     */         
/* 294 */         short substitution = sparseValuesAccessorData.get(i, c);
/* 295 */         denseAccessorData.set(targetElementIndex, c, substitution);
/*     */       } 
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
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static void substituteIntAccessorData(AccessorIntData denseAccessorData, AccessorIntData baseAccessorData, AccessorData sparseIndicesAccessorData, AccessorIntData sparseValuesAccessorData) {
/* 316 */     int numElements = denseAccessorData.getNumElements();
/*     */     
/* 318 */     int numComponentsPerElement = denseAccessorData.getNumComponentsPerElement();
/*     */     
/* 320 */     if (baseAccessorData != null)
/*     */     {
/*     */       
/* 323 */       for (int e = 0; e < numElements; e++) {
/*     */         
/* 325 */         for (int c = 0; c < numComponentsPerElement; c++) {
/*     */           
/* 327 */           int value = baseAccessorData.get(e, c);
/* 328 */           denseAccessorData.set(e, c, value);
/*     */         } 
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/* 334 */     int[] indices = extractIndices(sparseIndicesAccessorData);
/* 335 */     for (int i = 0; i < indices.length; i++) {
/*     */       
/* 337 */       int targetElementIndex = indices[i];
/* 338 */       for (int c = 0; c < numComponentsPerElement; c++) {
/*     */         
/* 340 */         int substitution = sparseValuesAccessorData.get(i, c);
/* 341 */         denseAccessorData.set(targetElementIndex, c, substitution);
/*     */       } 
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
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static void substituteFloatAccessorData(AccessorFloatData denseAccessorData, AccessorFloatData baseAccessorData, AccessorData sparseIndicesAccessorData, AccessorFloatData sparseValuesAccessorData) {
/* 362 */     int numElements = denseAccessorData.getNumElements();
/*     */     
/* 364 */     int numComponentsPerElement = denseAccessorData.getNumComponentsPerElement();
/*     */     
/* 366 */     if (baseAccessorData != null)
/*     */     {
/*     */       
/* 369 */       for (int e = 0; e < numElements; e++) {
/*     */         
/* 371 */         for (int c = 0; c < numComponentsPerElement; c++) {
/*     */           
/* 373 */           float value = baseAccessorData.get(e, c);
/* 374 */           denseAccessorData.set(e, c, value);
/*     */         } 
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/* 380 */     int[] indices = extractIndices(sparseIndicesAccessorData);
/* 381 */     for (int i = 0; i < indices.length; i++) {
/*     */       
/* 383 */       int targetElementIndex = indices[i];
/* 384 */       for (int c = 0; c < numComponentsPerElement; c++) {
/*     */         
/* 386 */         float substitution = sparseValuesAccessorData.get(i, c);
/* 387 */         denseAccessorData.set(targetElementIndex, c, substitution);
/*     */       } 
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\DuduM\OneDrive\Área de Trabalho\mw10\modularwarfare-shining-2023.2.4.4f-fix10.jar!\de\javagl\jgltf\model\v2\AccessorSparseUtils.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */