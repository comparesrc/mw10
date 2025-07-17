/*     */ package de.javagl.jgltf.model;
/*     */ 
/*     */ import java.util.List;
/*     */ import java.util.Map;
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
/*     */ class BoundingBoxComputer
/*     */ {
/*  42 */   private static final Logger logger = Logger.getLogger(BoundingBoxComputer.class.getName());
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private final GltfModel gltfModel;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   BoundingBoxComputer(GltfModel gltfModel) {
/*  56 */     this.gltfModel = gltfModel;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   BoundingBox compute() {
/*  66 */     BoundingBox boundingBox = new BoundingBox();
/*  67 */     List<SceneModel> sceneModels = this.gltfModel.getSceneModels();
/*  68 */     for (SceneModel sceneModel : sceneModels) {
/*     */       
/*  70 */       float[] rootTransform = MathUtils.createIdentity4x4();
/*  71 */       computeSceneBoundingBox(sceneModel, rootTransform, boundingBox);
/*     */     } 
/*  73 */     return boundingBox;
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
/*     */   private BoundingBox computeSceneBoundingBox(SceneModel sceneModel, float[] transform, BoundingBox boundingBox) {
/*  91 */     BoundingBox localResult = boundingBox;
/*  92 */     if (localResult == null)
/*     */     {
/*  94 */       localResult = new BoundingBox();
/*     */     }
/*  96 */     List<NodeModel> nodeModels = sceneModel.getNodeModels();
/*  97 */     for (NodeModel nodeModel : nodeModels)
/*     */     {
/*  99 */       computeNodeBoundingBox(nodeModel, transform, localResult);
/*     */     }
/* 101 */     return localResult;
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
/*     */   private BoundingBox computeNodeBoundingBox(NodeModel nodeModel, float[] parentTransform, BoundingBox boundingBox) {
/* 120 */     BoundingBox result = boundingBox;
/* 121 */     if (result == null)
/*     */     {
/* 123 */       result = new BoundingBox();
/*     */     }
/*     */     
/* 126 */     float[] localTransform = nodeModel.computeLocalTransform(null);
/* 127 */     float[] transform = new float[16];
/* 128 */     MathUtils.mul4x4(parentTransform, localTransform, transform);
/*     */     
/* 130 */     List<MeshModel> meshModels = nodeModel.getMeshModels();
/* 131 */     for (MeshModel meshModel : meshModels) {
/*     */ 
/*     */       
/* 134 */       BoundingBox meshBoundingBox = computeMeshBoundingBox(meshModel, transform, result);
/*     */       
/* 136 */       result.combine(meshBoundingBox);
/*     */     } 
/*     */     
/* 139 */     List<NodeModel> children = nodeModel.getChildren();
/* 140 */     for (NodeModel child : children)
/*     */     {
/* 142 */       computeNodeBoundingBox(child, transform, result);
/*     */     }
/* 144 */     return result;
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
/*     */   private BoundingBox computeMeshBoundingBox(MeshModel meshModel, float[] transform, BoundingBox boundingBox) {
/* 162 */     BoundingBox result = boundingBox;
/* 163 */     if (result == null)
/*     */     {
/* 165 */       result = new BoundingBox();
/*     */     }
/*     */ 
/*     */     
/* 169 */     List<MeshPrimitiveModel> primitives = meshModel.getMeshPrimitiveModels();
/* 170 */     for (MeshPrimitiveModel meshPrimitiveModel : primitives) {
/*     */ 
/*     */       
/* 173 */       BoundingBox meshPrimitiveBoundingBox = computeBoundingBox(meshPrimitiveModel, transform);
/* 174 */       if (meshPrimitiveBoundingBox != null)
/*     */       {
/* 176 */         result.combine(meshPrimitiveBoundingBox);
/*     */       }
/*     */     } 
/* 179 */     return result;
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
/*     */   private BoundingBox computeBoundingBox(MeshPrimitiveModel meshPrimitiveModel, float[] transform) {
/*     */     float[] transformedPoint;
/* 200 */     Map<String, AccessorModel> attributes = meshPrimitiveModel.getAttributes();
/* 201 */     String positionsAttributeName = "POSITION";
/* 202 */     AccessorModel accessorModel = attributes.get(positionsAttributeName);
/* 203 */     if (accessorModel == null)
/*     */     {
/* 205 */       return null;
/*     */     }
/*     */     
/* 208 */     ElementType accessorType = accessorModel.getElementType();
/* 209 */     int numComponents = accessorType.getNumComponents();
/* 210 */     if (numComponents < 3) {
/*     */       
/* 212 */       logger.warning("Mesh primitive " + positionsAttributeName + " attribute refers to an accessor with type " + accessorType + " - expected \"VEC3\" or \"VEC4\"");
/*     */ 
/*     */       
/* 215 */       return null;
/*     */     } 
/* 217 */     Class<?> componentDataType = accessorModel.getComponentDataType();
/* 218 */     if (!componentDataType.equals(float.class))
/*     */     {
/* 220 */       logger.warning("Mesh primitive " + positionsAttributeName + " attribute refers to an accessor with component type " + 
/*     */           
/* 222 */           GltfConstants.stringFor(accessorModel.getComponentType()) + " - expected GL_FLOAT");
/*     */     }
/*     */ 
/*     */     
/* 226 */     AccessorData accessorData = accessorModel.getAccessorData();
/* 227 */     AccessorFloatData accessorFloatData = (AccessorFloatData)accessorData;
/*     */     
/* 229 */     float[] point = new float[3];
/*     */     
/* 231 */     if (transform != null) {
/*     */       
/* 233 */       transformedPoint = new float[3];
/*     */     }
/*     */     else {
/*     */       
/* 237 */       transformedPoint = point;
/*     */     } 
/*     */     
/* 240 */     BoundingBox boundingBox = new BoundingBox();
/* 241 */     for (int e = 0; e < accessorData.getNumElements(); e++) {
/*     */       
/* 243 */       for (int c = 0; c < 3; c++)
/*     */       {
/* 245 */         point[c] = accessorFloatData.get(e, c);
/*     */       }
/* 247 */       if (transform != null)
/*     */       {
/* 249 */         MathUtils.transformPoint3D(transform, point, transformedPoint);
/*     */       }
/* 251 */       boundingBox.combine(transformedPoint[0], transformedPoint[1], transformedPoint[2]);
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 256 */     return boundingBox;
/*     */   }
/*     */ }


/* Location:              C:\Users\DuduM\OneDrive\Área de Trabalho\mw10\modularwarfare-shining-2023.2.4.4f-fix10.jar!\de\javagl\jgltf\model\BoundingBoxComputer.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */