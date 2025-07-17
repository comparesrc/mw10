/*     */ package de.javagl.jgltf.model;
/*     */ 
/*     */ import java.nio.ByteBuffer;
/*     */ import java.util.Locale;
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
/*     */ public class AccessorDatas
/*     */ {
/*  46 */   private static final Logger logger = Logger.getLogger(AccessorDatas.class.getName());
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static AccessorData create(AccessorModel accessorModel) {
/*  57 */     BufferViewModel bufferViewModel = accessorModel.getBufferViewModel();
/*  58 */     ByteBuffer bufferViewData = bufferViewModel.getBufferViewData();
/*  59 */     return create(accessorModel, bufferViewData);
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
/*     */   public static AccessorData create(AccessorModel accessorModel, ByteBuffer byteBuffer) {
/*  73 */     if (accessorModel.getComponentDataType() == byte.class)
/*     */     {
/*  75 */       return createByte(accessorModel, byteBuffer);
/*     */     }
/*  77 */     if (accessorModel.getComponentDataType() == short.class)
/*     */     {
/*  79 */       return createShort(accessorModel, byteBuffer);
/*     */     }
/*  81 */     if (accessorModel.getComponentDataType() == int.class)
/*     */     {
/*  83 */       return createInt(accessorModel, byteBuffer);
/*     */     }
/*  85 */     if (accessorModel.getComponentDataType() == float.class)
/*     */     {
/*  87 */       return createFloat(accessorModel, byteBuffer);
/*     */     }
/*     */     
/*  90 */     logger.severe("Invalid component data type: " + accessorModel
/*  91 */         .getComponentDataType());
/*  92 */     return null;
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
/*     */   public static AccessorData create(int componentType, ByteBuffer bufferViewData, int byteOffset, int count, ElementType elementType, Integer byteStride) {
/* 117 */     if (isByteType(componentType))
/*     */     {
/* 119 */       return new AccessorByteData(componentType, bufferViewData, byteOffset, count, elementType, byteStride);
/*     */     }
/*     */ 
/*     */     
/* 123 */     if (isShortType(componentType))
/*     */     {
/* 125 */       return new AccessorShortData(componentType, bufferViewData, byteOffset, count, elementType, byteStride);
/*     */     }
/*     */ 
/*     */     
/* 129 */     if (isIntType(componentType))
/*     */     {
/* 131 */       return new AccessorIntData(componentType, bufferViewData, byteOffset, count, elementType, byteStride);
/*     */     }
/*     */ 
/*     */     
/* 135 */     if (isFloatType(componentType))
/*     */     {
/* 137 */       return new AccessorFloatData(componentType, bufferViewData, byteOffset, count, elementType, byteStride);
/*     */     }
/*     */ 
/*     */     
/* 141 */     throw new IllegalArgumentException("Not a valid component type: " + componentType);
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
/*     */   public static boolean isByteType(int type) {
/* 156 */     return (type == 5120 || type == 5121);
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
/*     */   public static boolean isShortType(int type) {
/* 170 */     return (type == 5122 || type == 5123);
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
/*     */   public static boolean isIntType(int type) {
/* 184 */     return (type == 5124 || type == 5125);
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
/*     */   public static boolean isFloatType(int type) {
/* 197 */     return (type == 5126);
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
/*     */   static boolean isUnsignedType(int type) {
/* 209 */     return (type == 5121 || type == 5123 || type == 5125);
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
/*     */   static void validateByteType(int type) {
/* 227 */     if (!isByteType(type))
/*     */     {
/* 229 */       throw new IllegalArgumentException("The type is not GL_BYTE or GL_UNSIGNED_BYTE, but " + 
/*     */           
/* 231 */           GltfConstants.stringFor(type));
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
/*     */   static void validateShortType(int type) {
/* 246 */     if (!isShortType(type))
/*     */     {
/* 248 */       throw new IllegalArgumentException("The type is not GL_SHORT or GL_UNSIGNED_SHORT, but " + 
/*     */           
/* 250 */           GltfConstants.stringFor(type));
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
/*     */   static void validateIntType(int type) {
/* 265 */     if (!isIntType(type))
/*     */     {
/* 267 */       throw new IllegalArgumentException("The type is not GL_INT or GL_UNSIGNED_INT, but " + 
/*     */           
/* 269 */           GltfConstants.stringFor(type));
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
/*     */   static void validateFloatType(int type) {
/* 283 */     if (!isFloatType(type))
/*     */     {
/* 285 */       throw new IllegalArgumentException("The type is not GL_FLOAT, but " + 
/*     */           
/* 287 */           GltfConstants.stringFor(type));
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
/*     */   static AccessorByteData createByte(AccessorModel accessorModel) {
/* 304 */     BufferViewModel bufferViewModel = accessorModel.getBufferViewModel();
/* 305 */     return createByte(accessorModel, bufferViewModel.getBufferViewData());
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
/*     */   private static AccessorByteData createByte(AccessorModel accessorModel, ByteBuffer bufferViewByteBuffer) {
/* 324 */     return new AccessorByteData(accessorModel.getComponentType(), bufferViewByteBuffer, accessorModel
/*     */         
/* 326 */         .getByteOffset(), accessorModel
/* 327 */         .getCount(), accessorModel
/* 328 */         .getElementType(), 
/* 329 */         Integer.valueOf(accessorModel.getByteStride()));
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
/*     */   static AccessorShortData createShort(AccessorModel accessorModel) {
/* 344 */     BufferViewModel bufferViewModel = accessorModel.getBufferViewModel();
/* 345 */     return createShort(accessorModel, bufferViewModel.getBufferViewData());
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
/*     */   private static AccessorShortData createShort(AccessorModel accessorModel, ByteBuffer bufferViewByteBuffer) {
/* 364 */     return new AccessorShortData(accessorModel.getComponentType(), bufferViewByteBuffer, accessorModel
/*     */         
/* 366 */         .getByteOffset(), accessorModel
/* 367 */         .getCount(), accessorModel
/* 368 */         .getElementType(), 
/* 369 */         Integer.valueOf(accessorModel.getByteStride()));
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
/*     */   static AccessorIntData createInt(AccessorModel accessorModel) {
/* 384 */     BufferViewModel bufferViewModel = accessorModel.getBufferViewModel();
/* 385 */     return createInt(accessorModel, bufferViewModel.getBufferViewData());
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
/*     */   private static AccessorIntData createInt(AccessorModel accessorModel, ByteBuffer bufferViewByteBuffer) {
/* 403 */     return new AccessorIntData(accessorModel.getComponentType(), bufferViewByteBuffer, accessorModel
/*     */         
/* 405 */         .getByteOffset(), accessorModel
/* 406 */         .getCount(), accessorModel
/* 407 */         .getElementType(), 
/* 408 */         Integer.valueOf(accessorModel.getByteStride()));
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
/*     */   public static AccessorFloatData createFloat(AccessorModel accessorModel) {
/* 422 */     BufferViewModel bufferViewModel = accessorModel.getBufferViewModel();
/* 423 */     return createFloat(accessorModel, bufferViewModel.getBufferViewData());
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
/*     */   private static AccessorFloatData createFloat(AccessorModel accessorModel, ByteBuffer bufferViewByteBuffer) {
/* 439 */     return new AccessorFloatData(accessorModel.getComponentType(), bufferViewByteBuffer, accessorModel
/*     */         
/* 441 */         .getByteOffset(), accessorModel
/* 442 */         .getCount(), accessorModel
/* 443 */         .getElementType(), 
/* 444 */         Integer.valueOf(accessorModel.getByteStride()));
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
/*     */   static void validateCapacity(int byteOffset, int numElements, int numBytesPerElement, int byteStridePerElement, int bufferCapacity) {
/* 462 */     int expectedCapacity = (numElements - 1) * byteStridePerElement + numBytesPerElement;
/*     */     
/* 464 */     if (expectedCapacity > bufferCapacity)
/*     */     {
/* 466 */       throw new IllegalArgumentException("The accessorModel has an offset of " + byteOffset + " and " + numElements + " elements with a byte stride of " + byteStridePerElement + " and a size of " + numBytesPerElement + ", requiring " + expectedCapacity + " bytes, but the buffer view has only " + bufferCapacity + " bytes");
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
/*     */   public static Number[] computeMin(AccessorData accessorData) {
/* 487 */     if (accessorData instanceof AccessorByteData) {
/*     */       
/* 489 */       AccessorByteData accessorByteData = (AccessorByteData)accessorData;
/*     */       
/* 491 */       return NumberArrays.asNumbers(accessorByteData
/* 492 */           .computeMinInt());
/*     */     } 
/* 494 */     if (accessorData instanceof AccessorShortData) {
/*     */       
/* 496 */       AccessorShortData accessorShortData = (AccessorShortData)accessorData;
/*     */       
/* 498 */       return NumberArrays.asNumbers(accessorShortData
/* 499 */           .computeMinInt());
/*     */     } 
/* 501 */     if (accessorData instanceof AccessorIntData) {
/*     */       
/* 503 */       AccessorIntData accessorIntData = (AccessorIntData)accessorData;
/*     */       
/* 505 */       return NumberArrays.asNumbers(accessorIntData
/* 506 */           .computeMinLong());
/*     */     } 
/* 508 */     if (accessorData instanceof AccessorFloatData) {
/*     */       
/* 510 */       AccessorFloatData accessorFloatData = (AccessorFloatData)accessorData;
/*     */       
/* 512 */       return NumberArrays.asNumbers(accessorFloatData
/* 513 */           .computeMin());
/*     */     } 
/* 515 */     throw new IllegalArgumentException("Invalid data type: " + accessorData);
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
/*     */   public static Number[] computeMax(AccessorData accessorData) {
/* 529 */     if (accessorData instanceof AccessorByteData) {
/*     */       
/* 531 */       AccessorByteData accessorByteData = (AccessorByteData)accessorData;
/*     */       
/* 533 */       return NumberArrays.asNumbers(accessorByteData
/* 534 */           .computeMaxInt());
/*     */     } 
/* 536 */     if (accessorData instanceof AccessorShortData) {
/*     */       
/* 538 */       AccessorShortData accessorShortData = (AccessorShortData)accessorData;
/*     */       
/* 540 */       return NumberArrays.asNumbers(accessorShortData
/* 541 */           .computeMaxInt());
/*     */     } 
/* 543 */     if (accessorData instanceof AccessorIntData) {
/*     */       
/* 545 */       AccessorIntData accessorIntData = (AccessorIntData)accessorData;
/*     */       
/* 547 */       return NumberArrays.asNumbers(accessorIntData
/* 548 */           .computeMaxLong());
/*     */     } 
/* 550 */     if (accessorData instanceof AccessorFloatData) {
/*     */       
/* 552 */       AccessorFloatData accessorFloatData = (AccessorFloatData)accessorData;
/*     */       
/* 554 */       return NumberArrays.asNumbers(accessorFloatData
/* 555 */           .computeMax());
/*     */     } 
/* 557 */     throw new IllegalArgumentException("Invalid data type: " + accessorData);
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
/*     */   public static String createString(AccessorData accessorData, int elementsPerRow) {
/* 578 */     if (accessorData instanceof AccessorByteData) {
/*     */       
/* 580 */       AccessorByteData accessorByteData = (AccessorByteData)accessorData;
/*     */ 
/*     */       
/* 583 */       String accessorDataString = accessorByteData.createString(Locale.ENGLISH, "%4d", elementsPerRow);
/*     */       
/* 585 */       return accessorDataString;
/*     */     } 
/* 587 */     if (accessorData instanceof AccessorShortData) {
/*     */       
/* 589 */       AccessorShortData accessorShortData = (AccessorShortData)accessorData;
/*     */ 
/*     */       
/* 592 */       String accessorDataString = accessorShortData.createString(Locale.ENGLISH, "%6d", elementsPerRow);
/*     */       
/* 594 */       return accessorDataString;
/*     */     } 
/* 596 */     if (accessorData instanceof AccessorIntData) {
/*     */       
/* 598 */       AccessorIntData accessorIntData = (AccessorIntData)accessorData;
/*     */ 
/*     */       
/* 601 */       String accessorDataString = accessorIntData.createString(Locale.ENGLISH, "%11d", elementsPerRow);
/*     */       
/* 603 */       return accessorDataString;
/*     */     } 
/* 605 */     if (accessorData instanceof AccessorFloatData) {
/*     */       
/* 607 */       AccessorFloatData accessorFloatData = (AccessorFloatData)accessorData;
/*     */ 
/*     */       
/* 610 */       String accessorDataString = accessorFloatData.createString(Locale.ENGLISH, "%10.5f", elementsPerRow);
/*     */       
/* 612 */       return accessorDataString;
/*     */     } 
/* 614 */     return "Unknown accessor data type: " + accessorData;
/*     */   }
/*     */ }


/* Location:              C:\Users\DuduM\OneDrive\Área de Trabalho\mw10\modularwarfare-shining-2023.2.4.4f-fix10.jar!\de\javagl\jgltf\model\AccessorDatas.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */