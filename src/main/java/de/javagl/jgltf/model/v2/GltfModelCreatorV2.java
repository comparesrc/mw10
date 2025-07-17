/*      */ package de.javagl.jgltf.model.v2;
/*      */ 
/*      */ import de.javagl.jgltf.impl.v2.Accessor;
/*      */ import de.javagl.jgltf.impl.v2.AccessorSparse;
/*      */ import de.javagl.jgltf.impl.v2.AccessorSparseIndices;
/*      */ import de.javagl.jgltf.impl.v2.AccessorSparseValues;
/*      */ import de.javagl.jgltf.impl.v2.Animation;
/*      */ import de.javagl.jgltf.impl.v2.AnimationChannel;
/*      */ import de.javagl.jgltf.impl.v2.AnimationChannelTarget;
/*      */ import de.javagl.jgltf.impl.v2.AnimationSampler;
/*      */ import de.javagl.jgltf.impl.v2.Asset;
/*      */ import de.javagl.jgltf.impl.v2.Buffer;
/*      */ import de.javagl.jgltf.impl.v2.BufferView;
/*      */ import de.javagl.jgltf.impl.v2.Camera;
/*      */ import de.javagl.jgltf.impl.v2.CameraOrthographic;
/*      */ import de.javagl.jgltf.impl.v2.CameraPerspective;
/*      */ import de.javagl.jgltf.impl.v2.GlTF;
/*      */ import de.javagl.jgltf.impl.v2.GlTFChildOfRootProperty;
/*      */ import de.javagl.jgltf.impl.v2.GlTFProperty;
/*      */ import de.javagl.jgltf.impl.v2.Image;
/*      */ import de.javagl.jgltf.impl.v2.Material;
/*      */ import de.javagl.jgltf.impl.v2.MaterialNormalTextureInfo;
/*      */ import de.javagl.jgltf.impl.v2.MaterialOcclusionTextureInfo;
/*      */ import de.javagl.jgltf.impl.v2.MaterialPbrMetallicRoughness;
/*      */ import de.javagl.jgltf.impl.v2.Mesh;
/*      */ import de.javagl.jgltf.impl.v2.MeshPrimitive;
/*      */ import de.javagl.jgltf.impl.v2.Node;
/*      */ import de.javagl.jgltf.impl.v2.Sampler;
/*      */ import de.javagl.jgltf.impl.v2.Scene;
/*      */ import de.javagl.jgltf.impl.v2.Skin;
/*      */ import de.javagl.jgltf.impl.v2.Texture;
/*      */ import de.javagl.jgltf.impl.v2.TextureInfo;
/*      */ import de.javagl.jgltf.model.AccessorData;
/*      */ import de.javagl.jgltf.model.AccessorDatas;
/*      */ import de.javagl.jgltf.model.AccessorModel;
/*      */ import de.javagl.jgltf.model.AnimationModel;
/*      */ import de.javagl.jgltf.model.BufferModel;
/*      */ import de.javagl.jgltf.model.BufferViewModel;
/*      */ import de.javagl.jgltf.model.CameraModel;
/*      */ import de.javagl.jgltf.model.CameraOrthographicModel;
/*      */ import de.javagl.jgltf.model.CameraPerspectiveModel;
/*      */ import de.javagl.jgltf.model.ElementType;
/*      */ import de.javagl.jgltf.model.ImageModel;
/*      */ import de.javagl.jgltf.model.MeshModel;
/*      */ import de.javagl.jgltf.model.MeshPrimitiveModel;
/*      */ import de.javagl.jgltf.model.NodeModel;
/*      */ import de.javagl.jgltf.model.Optionals;
/*      */ import de.javagl.jgltf.model.SkinModel;
/*      */ import de.javagl.jgltf.model.TextureModel;
/*      */ import de.javagl.jgltf.model.impl.AbstractModelElement;
/*      */ import de.javagl.jgltf.model.impl.AbstractNamedModelElement;
/*      */ import de.javagl.jgltf.model.impl.DefaultAccessorModel;
/*      */ import de.javagl.jgltf.model.impl.DefaultAnimationModel;
/*      */ import de.javagl.jgltf.model.impl.DefaultAssetModel;
/*      */ import de.javagl.jgltf.model.impl.DefaultBufferModel;
/*      */ import de.javagl.jgltf.model.impl.DefaultBufferViewModel;
/*      */ import de.javagl.jgltf.model.impl.DefaultCameraModel;
/*      */ import de.javagl.jgltf.model.impl.DefaultCameraOrthographicModel;
/*      */ import de.javagl.jgltf.model.impl.DefaultCameraPerspectiveModel;
/*      */ import de.javagl.jgltf.model.impl.DefaultExtensionsModel;
/*      */ import de.javagl.jgltf.model.impl.DefaultGltfModel;
/*      */ import de.javagl.jgltf.model.impl.DefaultImageModel;
/*      */ import de.javagl.jgltf.model.impl.DefaultMeshModel;
/*      */ import de.javagl.jgltf.model.impl.DefaultMeshPrimitiveModel;
/*      */ import de.javagl.jgltf.model.impl.DefaultNodeModel;
/*      */ import de.javagl.jgltf.model.impl.DefaultSceneModel;
/*      */ import de.javagl.jgltf.model.impl.DefaultSkinModel;
/*      */ import de.javagl.jgltf.model.impl.DefaultTextureModel;
/*      */ import de.javagl.jgltf.model.io.Buffers;
/*      */ import de.javagl.jgltf.model.io.GltfAsset;
/*      */ import de.javagl.jgltf.model.io.IO;
/*      */ import de.javagl.jgltf.model.io.v2.GltfAssetV2;
/*      */ import de.javagl.jgltf.model.v2.gl.Materials;
/*      */ import java.nio.ByteBuffer;
/*      */ import java.util.Collections;
/*      */ import java.util.LinkedHashMap;
/*      */ import java.util.List;
/*      */ import java.util.Map;
/*      */ import java.util.Objects;
/*      */ import java.util.function.Consumer;
/*      */ import java.util.logging.Logger;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public class GltfModelCreatorV2
/*      */ {
/*  130 */   private static final Logger logger = Logger.getLogger(GltfModelCreatorV2.class.getName());
/*      */   
/*      */   private final GltfAsset gltfAsset;
/*      */   
/*      */   private final GlTF gltf;
/*      */   
/*      */   private final DefaultGltfModel gltfModel;
/*      */ 
/*      */   
/*      */   public static DefaultGltfModel create(GltfAssetV2 gltfAsset) {
/*  140 */     DefaultGltfModel gltfModel = new DefaultGltfModel();
/*  141 */     GltfModelCreatorV2 creator = new GltfModelCreatorV2(gltfAsset, gltfModel);
/*      */     
/*  143 */     creator.create();
/*  144 */     return gltfModel;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   GltfModelCreatorV2(GltfAssetV2 gltfAsset, DefaultGltfModel gltfModel) {
/*  170 */     this.gltfAsset = (GltfAsset)Objects.requireNonNull(gltfAsset, "The gltfAsset may not be null");
/*      */     
/*  172 */     this.gltf = gltfAsset.getGltf();
/*  173 */     this.gltfModel = Objects.<DefaultGltfModel>requireNonNull(gltfModel, "The gltfModel may not be null");
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   void create() {
/*  182 */     transferGltfPropertyElements((GlTFProperty)this.gltf, (AbstractModelElement)this.gltfModel);
/*      */     
/*  184 */     createAccessorModels();
/*  185 */     createAnimationModels();
/*  186 */     createBufferModels();
/*  187 */     createBufferViewModels();
/*  188 */     createCameraModels();
/*  189 */     createImageModels();
/*  190 */     createMaterialModels();
/*  191 */     createMeshModels();
/*  192 */     createNodeModels();
/*  193 */     createSceneModels();
/*  194 */     createSkinModels();
/*  195 */     createTextureModels();
/*      */     
/*  197 */     initBufferModels();
/*  198 */     initBufferViewModels();
/*      */     
/*  200 */     initAccessorModels();
/*  201 */     initAnimationModels();
/*  202 */     initImageModels();
/*  203 */     initMeshModels();
/*  204 */     initNodeModels();
/*  205 */     initSceneModels();
/*  206 */     initSkinModels();
/*  207 */     initTextureModels();
/*  208 */     initMaterialModels();
/*      */     
/*  210 */     initExtensionsModel();
/*  211 */     initAssetModel();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void createAccessorModels() {
/*  219 */     List<Accessor> accessors = Optionals.of(this.gltf.getAccessors());
/*  220 */     for (int i = 0; i < accessors.size(); i++) {
/*      */       
/*  222 */       Accessor accessor = accessors.get(i);
/*  223 */       Integer componentType = accessor.getComponentType();
/*  224 */       Integer count = accessor.getCount();
/*  225 */       ElementType elementType = ElementType.forString(accessor.getType());
/*      */       
/*  227 */       DefaultAccessorModel accessorModel = new DefaultAccessorModel(componentType.intValue(), count.intValue(), elementType);
/*  228 */       this.gltfModel.addAccessorModel(accessorModel);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void createAnimationModels() {
/*  237 */     List<Animation> animations = Optionals.of(this.gltf.getAnimations());
/*  238 */     for (int i = 0; i < animations.size(); i++)
/*      */     {
/*  240 */       this.gltfModel.addAnimationModel(new DefaultAnimationModel());
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void createBufferModels() {
/*  249 */     List<Buffer> buffers = Optionals.of(this.gltf.getBuffers());
/*  250 */     for (int i = 0; i < buffers.size(); i++) {
/*      */       
/*  252 */       Buffer buffer = buffers.get(i);
/*  253 */       DefaultBufferModel bufferModel = new DefaultBufferModel();
/*  254 */       bufferModel.setUri(buffer.getUri());
/*  255 */       this.gltfModel.addBufferModel(bufferModel);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void createCameraModels() {
/*  264 */     List<Camera> cameras = Optionals.of(this.gltf.getCameras());
/*  265 */     for (int i = 0; i < cameras.size(); i++) {
/*      */       
/*  267 */       Camera camera = cameras.get(i);
/*  268 */       String type = camera.getType();
/*  269 */       if ("perspective".equals(type)) {
/*      */         
/*  271 */         CameraPerspective cameraPerspective = camera.getPerspective();
/*  272 */         DefaultCameraPerspectiveModel cameraPerspectiveModel = new DefaultCameraPerspectiveModel();
/*      */         
/*  274 */         cameraPerspectiveModel.setAspectRatio(cameraPerspective
/*  275 */             .getAspectRatio());
/*  276 */         cameraPerspectiveModel.setYfov(cameraPerspective
/*  277 */             .getYfov());
/*  278 */         cameraPerspectiveModel.setZfar(cameraPerspective
/*  279 */             .getZfar());
/*  280 */         cameraPerspectiveModel.setZnear(cameraPerspective
/*  281 */             .getZnear());
/*  282 */         DefaultCameraModel cameraModel = new DefaultCameraModel();
/*      */         
/*  284 */         cameraModel.setCameraPerspectiveModel((CameraPerspectiveModel)cameraPerspectiveModel);
/*  285 */         this.gltfModel.addCameraModel(cameraModel);
/*      */       }
/*  287 */       else if ("orthographic".equals(type)) {
/*      */ 
/*      */         
/*  290 */         CameraOrthographic cameraOrthographic = camera.getOrthographic();
/*  291 */         DefaultCameraOrthographicModel cameraOrthographicModel = new DefaultCameraOrthographicModel();
/*      */         
/*  293 */         cameraOrthographicModel.setXmag(cameraOrthographic
/*  294 */             .getXmag());
/*  295 */         cameraOrthographicModel.setYmag(cameraOrthographic
/*  296 */             .getYmag());
/*  297 */         cameraOrthographicModel.setZfar(cameraOrthographic
/*  298 */             .getZfar());
/*  299 */         cameraOrthographicModel.setZnear(cameraOrthographic
/*  300 */             .getZnear());
/*  301 */         DefaultCameraModel cameraModel = new DefaultCameraModel();
/*      */         
/*  303 */         cameraModel.setCameraOrthographicModel((CameraOrthographicModel)cameraOrthographicModel);
/*  304 */         this.gltfModel.addCameraModel(cameraModel);
/*      */       }
/*      */       else {
/*      */         
/*  308 */         logger.severe("Invalid camera type: " + type);
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void createBufferViewModels() {
/*  318 */     List<BufferView> bufferViews = Optionals.of(this.gltf.getBufferViews());
/*  319 */     for (int i = 0; i < bufferViews.size(); i++) {
/*      */       
/*  321 */       BufferView bufferView = bufferViews.get(i);
/*      */       
/*  323 */       DefaultBufferViewModel bufferViewModel = createBufferViewModel(bufferView);
/*  324 */       this.gltfModel.addBufferViewModel(bufferViewModel);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static DefaultBufferViewModel createBufferViewModel(BufferView bufferView) {
/*  337 */     int byteOffset = ((Integer)Optionals.of(bufferView.getByteOffset(), Integer.valueOf(0))).intValue();
/*  338 */     int byteLength = bufferView.getByteLength().intValue();
/*  339 */     Integer byteStride = bufferView.getByteStride();
/*  340 */     Integer target = bufferView.getTarget();
/*  341 */     DefaultBufferViewModel bufferViewModel = new DefaultBufferViewModel(target);
/*      */     
/*  343 */     bufferViewModel.setByteOffset(byteOffset);
/*  344 */     bufferViewModel.setByteLength(byteLength);
/*  345 */     bufferViewModel.setByteStride(byteStride);
/*  346 */     return bufferViewModel;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void createImageModels() {
/*  354 */     List<Image> images = Optionals.of(this.gltf.getImages());
/*  355 */     for (int i = 0; i < images.size(); i++) {
/*      */       
/*  357 */       Image image = images.get(i);
/*  358 */       String mimeType = image.getMimeType();
/*  359 */       DefaultImageModel imageModel = new DefaultImageModel();
/*      */       
/*  361 */       imageModel.setMimeType(mimeType);
/*  362 */       String uri = image.getUri();
/*  363 */       imageModel.setUri(uri);
/*  364 */       this.gltfModel.addImageModel(imageModel);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void createMaterialModels() {
/*  373 */     List<Material> materials = Optionals.of(this.gltf.getMaterials());
/*  374 */     for (int i = 0; i < materials.size(); i++) {
/*      */       
/*  376 */       MaterialModelV2 materialModel = new MaterialModelV2();
/*  377 */       this.gltfModel.addMaterialModel(materialModel);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void createMeshModels() {
/*  386 */     List<Mesh> meshes = Optionals.of(this.gltf.getMeshes());
/*  387 */     for (int i = 0; i < meshes.size(); i++)
/*      */     {
/*  389 */       this.gltfModel.addMeshModel(new DefaultMeshModel());
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void createNodeModels() {
/*  398 */     List<Node> nodes = Optionals.of(this.gltf.getNodes());
/*  399 */     for (int i = 0; i < nodes.size(); i++)
/*      */     {
/*  401 */       this.gltfModel.addNodeModel(new DefaultNodeModel());
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void createSceneModels() {
/*  410 */     List<Scene> scenes = Optionals.of(this.gltf.getScenes());
/*  411 */     for (int i = 0; i < scenes.size(); i++)
/*      */     {
/*  413 */       this.gltfModel.addSceneModel(new DefaultSceneModel());
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void createSkinModels() {
/*  422 */     List<Skin> skins = Optionals.of(this.gltf.getSkins());
/*  423 */     for (int i = 0; i < skins.size(); i++)
/*      */     {
/*  425 */       this.gltfModel.addSkinModel(new DefaultSkinModel());
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void createTextureModels() {
/*  434 */     List<Texture> textures = Optionals.of(this.gltf.getTextures());
/*  435 */     List<Sampler> samplers = Optionals.of(this.gltf.getSamplers());
/*  436 */     for (int i = 0; i < textures.size(); i++) {
/*      */       
/*  438 */       Texture texture = textures.get(i);
/*  439 */       Integer samplerIndex = texture.getSampler();
/*      */       
/*  441 */       Integer magFilter = Integer.valueOf(9729);
/*  442 */       Integer minFilter = Integer.valueOf(9729);
/*  443 */       Integer wrapS = Integer.valueOf(10497);
/*  444 */       Integer wrapT = Integer.valueOf(10497);
/*      */       
/*  446 */       if (samplerIndex != null) {
/*      */         
/*  448 */         Sampler sampler = samplers.get(samplerIndex.intValue());
/*  449 */         magFilter = sampler.getMagFilter();
/*  450 */         minFilter = sampler.getMinFilter();
/*  451 */         wrapS = (Integer)Optionals.of(sampler
/*  452 */             .getWrapS(), sampler.defaultWrapS());
/*  453 */         wrapT = (Integer)Optionals.of(sampler
/*  454 */             .getWrapT(), sampler.defaultWrapT());
/*      */       } 
/*      */       
/*  457 */       DefaultTextureModel textureModel = new DefaultTextureModel();
/*  458 */       textureModel.setMagFilter(magFilter);
/*  459 */       textureModel.setMinFilter(minFilter);
/*  460 */       textureModel.setWrapS(wrapS);
/*  461 */       textureModel.setWrapT(wrapT);
/*  462 */       this.gltfModel.addTextureModel(textureModel);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void initAccessorModels() {
/*  471 */     List<Accessor> accessors = Optionals.of(this.gltf.getAccessors());
/*  472 */     for (int i = 0; i < accessors.size(); i++) {
/*      */       
/*  474 */       Accessor accessor = accessors.get(i);
/*      */       
/*  476 */       DefaultAccessorModel accessorModel = this.gltfModel.getAccessorModel(i);
/*  477 */       transferGltfChildOfRootPropertyElements((GlTFChildOfRootProperty)accessor, (AbstractNamedModelElement)accessorModel);
/*      */       
/*  479 */       int byteOffset = ((Integer)Optionals.of(accessor.getByteOffset(), Integer.valueOf(0))).intValue();
/*  480 */       accessorModel.setByteOffset(byteOffset);
/*      */       
/*  482 */       Boolean normalized = accessor.isNormalized();
/*  483 */       accessorModel.setNormalized(Boolean.TRUE.equals(normalized));
/*      */       
/*  485 */       AccessorSparse accessorSparse = accessor.getSparse();
/*  486 */       if (accessorSparse == null) {
/*      */         
/*  488 */         initDenseAccessorModel(i, accessor, accessorModel);
/*      */       }
/*      */       else {
/*      */         
/*  492 */         initSparseAccessorModel(i, accessor, accessorModel);
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void initDenseAccessorModel(int accessorIndex, Accessor accessor, DefaultAccessorModel accessorModel) {
/*  511 */     Integer bufferViewIndex = accessor.getBufferView();
/*  512 */     if (bufferViewIndex != null) {
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  517 */       DefaultBufferViewModel defaultBufferViewModel = this.gltfModel.getBufferViewModel(bufferViewIndex.intValue());
/*  518 */       accessorModel.setBufferViewModel((BufferViewModel)defaultBufferViewModel);
/*  519 */       Integer byteStride = defaultBufferViewModel.getByteStride();
/*  520 */       if (byteStride != null)
/*      */       {
/*  522 */         accessorModel.setByteStride(byteStride.intValue());
/*      */       }
/*  524 */       accessorModel.setAccessorData(AccessorDatas.create((AccessorModel)accessorModel));
/*      */     
/*      */     }
/*      */     else {
/*      */ 
/*      */       
/*  530 */       int count = accessorModel.getCount();
/*      */       
/*  532 */       int elementSizeInBytes = accessorModel.getPaddedElementSizeInBytes();
/*  533 */       int byteLength = elementSizeInBytes * count;
/*  534 */       ByteBuffer bufferData = Buffers.create(byteLength);
/*  535 */       String uriString = "buffer_for_accessor" + accessorIndex + ".bin";
/*      */       
/*  537 */       DefaultBufferViewModel defaultBufferViewModel = createBufferViewModel(uriString, bufferData);
/*  538 */       accessorModel.setBufferViewModel((BufferViewModel)defaultBufferViewModel);
/*  539 */       accessorModel.setAccessorData(AccessorDatas.create((AccessorModel)accessorModel));
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void initSparseAccessorModel(int accessorIndex, Accessor accessor, DefaultAccessorModel accessorModel) {
/*  559 */     int count = accessorModel.getCount();
/*  560 */     int elementSizeInBytes = accessorModel.getPaddedElementSizeInBytes();
/*  561 */     int byteLength = elementSizeInBytes * count;
/*  562 */     ByteBuffer bufferData = Buffers.create(byteLength);
/*  563 */     String uriString = "buffer_for_accessor" + accessorIndex + ".bin";
/*      */     
/*  565 */     DefaultBufferViewModel denseBufferViewModel = createBufferViewModel(uriString, bufferData);
/*  566 */     accessorModel.setBufferViewModel((BufferViewModel)denseBufferViewModel);
/*  567 */     accessorModel.setByteOffset(0);
/*      */     
/*  569 */     Integer bufferViewIndex = accessor.getBufferView();
/*  570 */     if (bufferViewIndex != null) {
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  575 */       Consumer<ByteBuffer> sparseSubstitutionCallback = denseByteBuffer -> {
/*      */           logger.fine("Substituting sparse accessor data, based on existing buffer view");
/*      */ 
/*      */           
/*      */           DefaultBufferViewModel baseBufferViewModel = this.gltfModel.getBufferViewModel(bufferViewIndex.intValue());
/*      */ 
/*      */           
/*      */           ByteBuffer baseBufferViewData = baseBufferViewModel.getBufferViewData();
/*      */           
/*      */           AccessorData baseAccessorData = AccessorDatas.create((AccessorModel)accessorModel, baseBufferViewData);
/*      */           
/*      */           AccessorData denseAccessorData = AccessorDatas.create((AccessorModel)accessorModel, bufferData);
/*      */           
/*      */           substituteSparseAccessorData(accessor, (AccessorModel)accessorModel, denseAccessorData, baseAccessorData);
/*      */         };
/*      */       
/*  591 */       denseBufferViewModel.setSparseSubstitutionCallback(sparseSubstitutionCallback);
/*      */ 
/*      */     
/*      */     }
/*      */     else {
/*      */ 
/*      */       
/*  598 */       Consumer<ByteBuffer> sparseSubstitutionCallback = denseByteBuffer -> {
/*      */           logger.fine("Substituting sparse accessor data, without an existing buffer view");
/*      */ 
/*      */           
/*      */           AccessorData denseAccessorData = AccessorDatas.create((AccessorModel)accessorModel, bufferData);
/*      */ 
/*      */           
/*      */           substituteSparseAccessorData(accessor, (AccessorModel)accessorModel, denseAccessorData, null);
/*      */         };
/*      */       
/*  608 */       denseBufferViewModel.setSparseSubstitutionCallback(sparseSubstitutionCallback);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static DefaultBufferViewModel createBufferViewModel(String uriString, ByteBuffer bufferData) {
/*  627 */     DefaultBufferModel bufferModel = new DefaultBufferModel();
/*  628 */     bufferModel.setUri(uriString);
/*  629 */     bufferModel.setBufferData(bufferData);
/*      */     
/*  631 */     DefaultBufferViewModel bufferViewModel = new DefaultBufferViewModel(null);
/*      */     
/*  633 */     bufferViewModel.setByteOffset(0);
/*  634 */     bufferViewModel.setByteLength(bufferData.capacity());
/*  635 */     bufferViewModel.setBufferModel((BufferModel)bufferModel);
/*      */     
/*  637 */     return bufferViewModel;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void substituteSparseAccessorData(Accessor accessor, AccessorModel accessorModel, AccessorData denseAccessorData, AccessorData baseAccessorData) {
/*  658 */     AccessorSparse accessorSparse = accessor.getSparse();
/*  659 */     int count = accessorSparse.getCount().intValue();
/*      */ 
/*      */     
/*  662 */     AccessorSparseIndices accessorSparseIndices = accessorSparse.getIndices();
/*      */     
/*  664 */     AccessorData sparseIndicesAccessorData = createSparseIndicesAccessorData(accessorSparseIndices, count);
/*      */     
/*  666 */     AccessorSparseValues accessorSparseValues = accessorSparse.getValues();
/*  667 */     ElementType elementType = accessorModel.getElementType();
/*      */     
/*  669 */     AccessorData sparseValuesAccessorData = createSparseValuesAccessorData(accessorSparseValues, accessorModel
/*  670 */         .getComponentType(), elementType, count);
/*      */ 
/*      */     
/*  673 */     AccessorSparseUtils.substituteAccessorData(denseAccessorData, baseAccessorData, sparseIndicesAccessorData, sparseValuesAccessorData);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private AccessorData createSparseIndicesAccessorData(AccessorSparseIndices accessorSparseIndices, int count) {
/*  692 */     Integer componentType = accessorSparseIndices.getComponentType();
/*  693 */     Integer bufferViewIndex = accessorSparseIndices.getBufferView();
/*      */     
/*  695 */     DefaultBufferViewModel defaultBufferViewModel = this.gltfModel.getBufferViewModel(bufferViewIndex.intValue());
/*  696 */     ByteBuffer bufferViewData = defaultBufferViewModel.getBufferViewData();
/*  697 */     int byteOffset = ((Integer)Optionals.of(accessorSparseIndices.getByteOffset(), Integer.valueOf(0))).intValue();
/*  698 */     return AccessorDatas.create(componentType
/*  699 */         .intValue(), bufferViewData, byteOffset, count, ElementType.SCALAR, null);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private AccessorData createSparseValuesAccessorData(AccessorSparseValues accessorSparseValues, int componentType, ElementType elementType, int count) {
/*  718 */     Integer bufferViewIndex = accessorSparseValues.getBufferView();
/*      */     
/*  720 */     DefaultBufferViewModel defaultBufferViewModel = this.gltfModel.getBufferViewModel(bufferViewIndex.intValue());
/*  721 */     ByteBuffer bufferViewData = defaultBufferViewModel.getBufferViewData();
/*  722 */     int byteOffset = ((Integer)Optionals.of(accessorSparseValues.getByteOffset(), Integer.valueOf(0))).intValue();
/*  723 */     return AccessorDatas.create(componentType, bufferViewData, byteOffset, count, elementType, null);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void initAnimationModels() {
/*  733 */     List<Animation> animations = Optionals.of(this.gltf.getAnimations());
/*  734 */     for (int i = 0; i < animations.size(); i++) {
/*      */       
/*  736 */       Animation animation = animations.get(i);
/*      */       
/*  738 */       DefaultAnimationModel animationModel = this.gltfModel.getAnimationModel(i);
/*  739 */       transferGltfChildOfRootPropertyElements((GlTFChildOfRootProperty)animation, (AbstractNamedModelElement)animationModel);
/*      */ 
/*      */       
/*  742 */       List<AnimationChannel> channels = Optionals.of(animation.getChannels());
/*  743 */       for (AnimationChannel animationChannel : channels) {
/*      */         
/*  745 */         AnimationModel.Channel channel = createChannel(animation, animationChannel);
/*  746 */         animationModel.addChannel(channel);
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private AnimationModel.Channel createChannel(Animation animation, AnimationChannel animationChannel) {
/*      */     DefaultNodeModel defaultNodeModel;
/*  763 */     List<AnimationSampler> samplers = Optionals.of(animation.getSamplers());
/*      */     
/*  765 */     int samplerIndex = animationChannel.getSampler().intValue();
/*  766 */     AnimationSampler animationSampler = samplers.get(samplerIndex);
/*      */     
/*  768 */     int inputAccessorIndex = animationSampler.getInput().intValue();
/*      */     
/*  770 */     DefaultAccessorModel defaultAccessorModel1 = this.gltfModel.getAccessorModel(inputAccessorIndex);
/*      */     
/*  772 */     int outputAccessorIndex = animationSampler.getOutput().intValue();
/*      */     
/*  774 */     DefaultAccessorModel defaultAccessorModel2 = this.gltfModel.getAccessorModel(outputAccessorIndex);
/*      */ 
/*      */     
/*  777 */     String interpolationString = animationSampler.getInterpolation();
/*      */ 
/*      */     
/*  780 */     AnimationModel.Interpolation interpolation = (interpolationString == null) ? AnimationModel.Interpolation.LINEAR : AnimationModel.Interpolation.valueOf(interpolationString);
/*      */     
/*  782 */     DefaultAnimationModel.DefaultSampler defaultSampler = new DefaultAnimationModel.DefaultSampler((AccessorModel)defaultAccessorModel1, interpolation, (AccessorModel)defaultAccessorModel2);
/*      */ 
/*      */ 
/*      */     
/*  786 */     AnimationChannelTarget animationChannelTarget = animationChannel.getTarget();
/*      */     
/*  788 */     Integer nodeIndex = animationChannelTarget.getNode();
/*  789 */     NodeModel nodeModel = null;
/*  790 */     if (nodeIndex == null) {
/*      */ 
/*      */       
/*  793 */       logger.warning("No node index given for animation channel target");
/*      */     }
/*      */     else {
/*      */       
/*  797 */       defaultNodeModel = this.gltfModel.getNodeModel(nodeIndex.intValue());
/*      */     } 
/*  799 */     String path = animationChannelTarget.getPath();
/*      */     
/*  801 */     return (AnimationModel.Channel)new DefaultAnimationModel.DefaultChannel((AnimationModel.Sampler)defaultSampler, (NodeModel)defaultNodeModel, path);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void initBufferModels() {
/*  811 */     List<Buffer> buffers = Optionals.of(this.gltf.getBuffers());
/*      */     
/*  813 */     ByteBuffer binaryData = null;
/*  814 */     ByteBuffer b = this.gltfAsset.getBinaryData();
/*  815 */     if (b != null && b.capacity() > 0)
/*      */     {
/*  817 */       binaryData = b;
/*      */     }
/*      */     
/*  820 */     if (buffers.isEmpty() && binaryData != null) {
/*      */       
/*  822 */       logger.warning("Binary data was given, but no buffers");
/*      */       
/*      */       return;
/*      */     } 
/*  826 */     for (int i = 0; i < buffers.size(); i++) {
/*      */       
/*  828 */       Buffer buffer = buffers.get(i);
/*  829 */       DefaultBufferModel bufferModel = this.gltfModel.getBufferModel(i);
/*  830 */       transferGltfChildOfRootPropertyElements((GlTFChildOfRootProperty)buffer, (AbstractNamedModelElement)bufferModel);
/*  831 */       if (i == 0 && binaryData != null) {
/*      */         
/*  833 */         bufferModel.setBufferData(binaryData);
/*      */       }
/*      */       else {
/*      */         
/*  837 */         String uri = buffer.getUri();
/*  838 */         if (IO.isDataUriString(uri)) {
/*      */           
/*  840 */           byte[] data = IO.readDataUri(uri);
/*  841 */           ByteBuffer bufferData = Buffers.create(data);
/*  842 */           bufferModel.setBufferData(bufferData);
/*      */ 
/*      */         
/*      */         }
/*  846 */         else if (uri == null) {
/*      */           
/*  848 */           logger.warning("Buffer " + i + " does not have a uri. Binary chunks that are not the main GLB buffer are not supported.");
/*      */         
/*      */         }
/*      */         else {
/*      */ 
/*      */           
/*  854 */           ByteBuffer bufferData = this.gltfAsset.getReferenceData(uri);
/*  855 */           bufferModel.setBufferData(bufferData);
/*      */         } 
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void initBufferViewModels() {
/*  868 */     List<BufferView> bufferViews = Optionals.of(this.gltf.getBufferViews());
/*  869 */     for (int i = 0; i < bufferViews.size(); i++) {
/*      */       
/*  871 */       BufferView bufferView = bufferViews.get(i);
/*      */ 
/*      */       
/*  874 */       DefaultBufferViewModel bufferViewModel = this.gltfModel.getBufferViewModel(i);
/*  875 */       transferGltfChildOfRootPropertyElements((GlTFChildOfRootProperty)bufferView, (AbstractNamedModelElement)bufferViewModel);
/*      */ 
/*      */       
/*  878 */       int bufferIndex = bufferView.getBuffer().intValue();
/*  879 */       DefaultBufferModel defaultBufferModel = this.gltfModel.getBufferModel(bufferIndex);
/*  880 */       bufferViewModel.setBufferModel((BufferModel)defaultBufferModel);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void initMeshModels() {
/*  890 */     List<Mesh> meshes = Optionals.of(this.gltf.getMeshes());
/*  891 */     for (int i = 0; i < meshes.size(); i++) {
/*      */       
/*  893 */       Mesh mesh = meshes.get(i);
/*  894 */       DefaultMeshModel meshModel = this.gltfModel.getMeshModel(i);
/*  895 */       transferGltfChildOfRootPropertyElements((GlTFChildOfRootProperty)mesh, (AbstractNamedModelElement)meshModel);
/*      */ 
/*      */       
/*  898 */       List<MeshPrimitive> primitives = Optionals.of(mesh.getPrimitives());
/*  899 */       for (MeshPrimitive meshPrimitive : primitives) {
/*      */ 
/*      */         
/*  902 */         DefaultMeshPrimitiveModel defaultMeshPrimitiveModel = createMeshPrimitiveModel(meshPrimitive);
/*  903 */         meshModel.addMeshPrimitiveModel((MeshPrimitiveModel)defaultMeshPrimitiveModel);
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private DefaultMeshPrimitiveModel createMeshPrimitiveModel(MeshPrimitive meshPrimitive) {
/*  918 */     Integer mode = (Integer)Optionals.of(meshPrimitive
/*  919 */         .getMode(), meshPrimitive
/*  920 */         .defaultMode());
/*      */     
/*  922 */     DefaultMeshPrimitiveModel meshPrimitiveModel = new DefaultMeshPrimitiveModel(mode.intValue());
/*  923 */     transferGltfPropertyElements((GlTFProperty)meshPrimitive, (AbstractModelElement)meshPrimitiveModel);
/*      */     
/*  925 */     Integer indicesIndex = meshPrimitive.getIndices();
/*  926 */     if (indicesIndex != null) {
/*      */       
/*  928 */       DefaultAccessorModel defaultAccessorModel = this.gltfModel.getAccessorModel(indicesIndex.intValue());
/*  929 */       meshPrimitiveModel.setIndices((AccessorModel)defaultAccessorModel);
/*      */     } 
/*      */     
/*  932 */     Map<String, Integer> attributes = Optionals.of(meshPrimitive.getAttributes());
/*  933 */     for (Map.Entry<String, Integer> entry : attributes.entrySet()) {
/*      */       
/*  935 */       String attributeName = entry.getKey();
/*  936 */       int attributeIndex = ((Integer)entry.getValue()).intValue();
/*      */       
/*  938 */       DefaultAccessorModel defaultAccessorModel = this.gltfModel.getAccessorModel(attributeIndex);
/*  939 */       meshPrimitiveModel.putAttribute(attributeName, (AccessorModel)defaultAccessorModel);
/*      */     } 
/*      */ 
/*      */     
/*  943 */     List<Map<String, Integer>> morphTargets = Optionals.of(meshPrimitive.getTargets());
/*  944 */     for (Map<String, Integer> morphTarget : morphTargets) {
/*      */       
/*  946 */       Map<String, AccessorModel> morphTargetModel = new LinkedHashMap<>();
/*      */       
/*  948 */       for (Map.Entry<String, Integer> entry : morphTarget.entrySet()) {
/*      */         
/*  950 */         String attribute = entry.getKey();
/*  951 */         Integer accessorIndex = entry.getValue();
/*      */         
/*  953 */         DefaultAccessorModel defaultAccessorModel = this.gltfModel.getAccessorModel(accessorIndex.intValue());
/*  954 */         morphTargetModel.put(attribute, defaultAccessorModel);
/*      */       } 
/*  956 */       meshPrimitiveModel.addTarget(
/*  957 */           Collections.unmodifiableMap(morphTargetModel));
/*      */     } 
/*      */     
/*  960 */     Integer materialIndex = meshPrimitive.getMaterial();
/*  961 */     if (materialIndex != null) {
/*      */ 
/*      */       
/*  964 */       MaterialModelV2 materialModel = (MaterialModelV2)this.gltfModel.getMaterialModel(materialIndex.intValue());
/*  965 */       meshPrimitiveModel.setMaterialModel(materialModel);
/*      */     } 
/*      */     
/*  968 */     return meshPrimitiveModel;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void initNodeModels() {
/*  976 */     List<Node> nodes = Optionals.of(this.gltf.getNodes());
/*  977 */     for (int i = 0; i < nodes.size(); i++) {
/*      */       
/*  979 */       Node node = nodes.get(i);
/*      */       
/*  981 */       DefaultNodeModel nodeModel = this.gltfModel.getNodeModel(i);
/*  982 */       transferGltfChildOfRootPropertyElements((GlTFChildOfRootProperty)node, (AbstractNamedModelElement)nodeModel);
/*      */       
/*  984 */       List<Integer> childIndices = Optionals.of(node.getChildren());
/*  985 */       for (Integer childIndex : childIndices) {
/*      */         
/*  987 */         DefaultNodeModel child = this.gltfModel.getNodeModel(childIndex.intValue());
/*  988 */         nodeModel.addChild(child);
/*      */       } 
/*      */       
/*  991 */       Integer meshIndex = node.getMesh();
/*  992 */       if (meshIndex != null) {
/*      */         
/*  994 */         DefaultMeshModel defaultMeshModel = this.gltfModel.getMeshModel(meshIndex.intValue());
/*  995 */         nodeModel.addMeshModel((MeshModel)defaultMeshModel);
/*      */       } 
/*      */       
/*  998 */       Integer skinIndex = node.getSkin();
/*  999 */       if (skinIndex != null) {
/*      */         
/* 1001 */         DefaultSkinModel defaultSkinModel = this.gltfModel.getSkinModel(skinIndex.intValue());
/* 1002 */         nodeModel.setSkinModel((SkinModel)defaultSkinModel);
/*      */       } 
/*      */       
/* 1005 */       Integer cameraIndex = node.getCamera();
/* 1006 */       if (cameraIndex != null) {
/*      */         
/* 1008 */         DefaultCameraModel defaultCameraModel = this.gltfModel.getCameraModel(cameraIndex.intValue());
/* 1009 */         nodeModel.setCameraModel((CameraModel)defaultCameraModel);
/*      */       } 
/*      */       
/* 1012 */       float[] matrix = node.getMatrix();
/* 1013 */       float[] translation = node.getTranslation();
/* 1014 */       float[] rotation = node.getRotation();
/* 1015 */       float[] scale = node.getScale();
/* 1016 */       nodeModel.setMatrix(Optionals.clone(matrix));
/* 1017 */       nodeModel.setTranslation(Optionals.clone(translation));
/* 1018 */       nodeModel.setRotation(Optionals.clone(rotation));
/* 1019 */       nodeModel.setScale(Optionals.clone(scale));
/*      */       
/* 1021 */       List<Float> weights = node.getWeights();
/* 1022 */       if (weights != null) {
/*      */         
/* 1024 */         float[] weightsArray = new float[weights.size()];
/* 1025 */         for (int j = 0; j < weights.size(); j++)
/*      */         {
/* 1027 */           weightsArray[j] = ((Float)weights.get(j)).floatValue();
/*      */         }
/* 1029 */         nodeModel.setWeights(weightsArray);
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void initSceneModels() {
/* 1040 */     List<Scene> scenes = Optionals.of(this.gltf.getScenes());
/* 1041 */     for (int i = 0; i < scenes.size(); i++) {
/*      */       
/* 1043 */       Scene scene = scenes.get(i);
/*      */       
/* 1045 */       DefaultSceneModel sceneModel = this.gltfModel.getSceneModel(i);
/* 1046 */       transferGltfChildOfRootPropertyElements((GlTFChildOfRootProperty)scene, (AbstractNamedModelElement)sceneModel);
/*      */       
/* 1048 */       List<Integer> nodeIndices = Optionals.of(scene.getNodes());
/* 1049 */       for (Integer nodeIndex : nodeIndices) {
/*      */         
/* 1051 */         DefaultNodeModel defaultNodeModel = this.gltfModel.getNodeModel(nodeIndex.intValue());
/* 1052 */         sceneModel.addNode((NodeModel)defaultNodeModel);
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void initSkinModels() {
/* 1062 */     List<Skin> skins = Optionals.of(this.gltf.getSkins());
/* 1063 */     for (int i = 0; i < skins.size(); i++) {
/*      */       
/* 1065 */       Skin skin = skins.get(i);
/* 1066 */       DefaultSkinModel skinModel = this.gltfModel.getSkinModel(i);
/* 1067 */       transferGltfChildOfRootPropertyElements((GlTFChildOfRootProperty)skin, (AbstractNamedModelElement)skinModel);
/*      */       
/* 1069 */       List<Integer> jointIndices = skin.getJoints();
/* 1070 */       for (Integer jointIndex : jointIndices) {
/*      */         
/* 1072 */         DefaultNodeModel defaultNodeModel = this.gltfModel.getNodeModel(jointIndex.intValue());
/* 1073 */         skinModel.addJoint((NodeModel)defaultNodeModel);
/*      */       } 
/*      */       
/* 1076 */       Integer inverseBindMatricesIndex = skin.getInverseBindMatrices();
/*      */       
/* 1078 */       DefaultAccessorModel defaultAccessorModel = this.gltfModel.getAccessorModel(inverseBindMatricesIndex.intValue());
/* 1079 */       skinModel.setInverseBindMatrices((AccessorModel)defaultAccessorModel);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void initTextureModels() {
/* 1088 */     List<Texture> textures = Optionals.of(this.gltf.getTextures());
/* 1089 */     for (int i = 0; i < textures.size(); i++) {
/*      */       
/* 1091 */       Texture texture = textures.get(i);
/* 1092 */       DefaultTextureModel textureModel = this.gltfModel.getTextureModel(i);
/* 1093 */       transferGltfChildOfRootPropertyElements((GlTFChildOfRootProperty)texture, (AbstractNamedModelElement)textureModel);
/*      */ 
/*      */ 
/*      */       
/* 1097 */       Integer imageIndex = texture.getSource();
/* 1098 */       if (imageIndex != null) {
/*      */ 
/*      */         
/* 1101 */         DefaultImageModel imageModel = this.gltfModel.getImageModel(imageIndex.intValue());
/* 1102 */         textureModel.setImageModel((ImageModel)imageModel);
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void initImageModels() {
/* 1112 */     List<Image> images = Optionals.of(this.gltf.getImages());
/* 1113 */     for (int i = 0; i < images.size(); i++) {
/*      */       
/* 1115 */       Image image = images.get(i);
/* 1116 */       DefaultImageModel imageModel = this.gltfModel.getImageModel(i);
/* 1117 */       transferGltfChildOfRootPropertyElements((GlTFChildOfRootProperty)image, (AbstractNamedModelElement)imageModel);
/*      */       
/* 1119 */       Integer bufferViewIndex = image.getBufferView();
/* 1120 */       if (bufferViewIndex != null) {
/*      */ 
/*      */         
/* 1123 */         DefaultBufferViewModel defaultBufferViewModel = this.gltfModel.getBufferViewModel(bufferViewIndex.intValue());
/* 1124 */         imageModel.setBufferViewModel((BufferViewModel)defaultBufferViewModel);
/*      */       }
/*      */       else {
/*      */         
/* 1128 */         String uri = image.getUri();
/* 1129 */         if (IO.isDataUriString(uri)) {
/*      */           
/* 1131 */           byte[] data = IO.readDataUri(uri);
/* 1132 */           ByteBuffer imageData = Buffers.create(data);
/* 1133 */           imageModel.setImageData(imageData);
/*      */         }
/*      */         else {
/*      */           
/* 1137 */           ByteBuffer imageData = this.gltfAsset.getReferenceData(uri);
/* 1138 */           imageModel.setImageData(imageData);
/*      */         } 
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void initMaterialModels() {
/* 1149 */     List<Material> materials = Optionals.of(this.gltf.getMaterials());
/* 1150 */     for (int i = 0; i < materials.size(); i++) {
/*      */       
/* 1152 */       Material material = materials.get(i);
/*      */       
/* 1154 */       MaterialModelV2 materialModel = (MaterialModelV2)this.gltfModel.getMaterialModel(i);
/*      */       
/* 1156 */       transferGltfChildOfRootPropertyElements((GlTFChildOfRootProperty)material, materialModel);
/* 1157 */       initMaterialModel(materialModel, material);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void initMaterialModel(MaterialModelV2 materialModel, Material material) {
/* 1172 */     MaterialPbrMetallicRoughness pbrMetallicRoughness = material.getPbrMetallicRoughness();
/* 1173 */     if (pbrMetallicRoughness == null)
/*      */     {
/*      */       
/* 1176 */       pbrMetallicRoughness = Materials.createDefaultMaterialPbrMetallicRoughness();
/*      */     }
/*      */     
/* 1179 */     String alphaModeString = material.getAlphaMode();
/* 1180 */     if (alphaModeString != null)
/*      */     {
/* 1182 */       materialModel.setAlphaMode(MaterialModelV2.AlphaMode.valueOf(alphaModeString));
/*      */     }
/* 1184 */     materialModel.setAlphaCutoff((
/* 1185 */         (Float)Optionals.of(material.getAlphaCutoff(), Float.valueOf(0.5F))).floatValue());
/*      */     
/* 1187 */     materialModel.setDoubleSided(Boolean.TRUE
/* 1188 */         .equals(material.isDoubleSided()));
/*      */ 
/*      */     
/* 1191 */     TextureInfo baseColorTextureInfo = pbrMetallicRoughness.getBaseColorTexture();
/* 1192 */     if (baseColorTextureInfo != null) {
/*      */       
/* 1194 */       int index = baseColorTextureInfo.getIndex().intValue();
/* 1195 */       DefaultTextureModel defaultTextureModel = this.gltfModel.getTextureModel(index);
/* 1196 */       materialModel.setBaseColorTexture((TextureModel)defaultTextureModel);
/* 1197 */       materialModel.setBaseColorTexcoord(baseColorTextureInfo
/* 1198 */           .getTexCoord());
/*      */     } 
/* 1200 */     float[] baseColorFactor = (float[])Optionals.of(pbrMetallicRoughness
/* 1201 */         .getBaseColorFactor(), pbrMetallicRoughness
/* 1202 */         .defaultBaseColorFactor());
/* 1203 */     materialModel.setBaseColorFactor(baseColorFactor);
/*      */ 
/*      */     
/* 1206 */     TextureInfo metallicRoughnessTextureInfo = pbrMetallicRoughness.getMetallicRoughnessTexture();
/* 1207 */     if (metallicRoughnessTextureInfo != null) {
/*      */       
/* 1209 */       int index = metallicRoughnessTextureInfo.getIndex().intValue();
/* 1210 */       DefaultTextureModel defaultTextureModel = this.gltfModel.getTextureModel(index);
/* 1211 */       materialModel.setMetallicRoughnessTexture((TextureModel)defaultTextureModel);
/* 1212 */       materialModel.setMetallicRoughnessTexcoord(metallicRoughnessTextureInfo
/* 1213 */           .getTexCoord());
/*      */     } 
/* 1215 */     float metallicFactor = ((Float)Optionals.of(pbrMetallicRoughness
/* 1216 */         .getMetallicFactor(), pbrMetallicRoughness
/* 1217 */         .defaultMetallicFactor())).floatValue();
/* 1218 */     materialModel.setMetallicFactor(metallicFactor);
/*      */     
/* 1220 */     float roughnessFactor = ((Float)Optionals.of(pbrMetallicRoughness
/* 1221 */         .getRoughnessFactor(), pbrMetallicRoughness
/* 1222 */         .defaultRoughnessFactor())).floatValue();
/* 1223 */     materialModel.setRoughnessFactor(roughnessFactor);
/*      */ 
/*      */     
/* 1226 */     MaterialNormalTextureInfo normalTextureInfo = material.getNormalTexture();
/* 1227 */     if (normalTextureInfo != null) {
/*      */       
/* 1229 */       int index = normalTextureInfo.getIndex().intValue();
/* 1230 */       DefaultTextureModel defaultTextureModel = this.gltfModel.getTextureModel(index);
/* 1231 */       materialModel.setNormalTexture((TextureModel)defaultTextureModel);
/* 1232 */       materialModel.setNormalTexcoord(normalTextureInfo
/* 1233 */           .getTexCoord());
/*      */       
/* 1235 */       float normalScale = ((Float)Optionals.of(normalTextureInfo
/* 1236 */           .getScale(), normalTextureInfo
/* 1237 */           .defaultScale())).floatValue();
/* 1238 */       materialModel.setNormalScale(normalScale);
/*      */     } 
/*      */ 
/*      */     
/* 1242 */     MaterialOcclusionTextureInfo occlusionTextureInfo = material.getOcclusionTexture();
/* 1243 */     if (occlusionTextureInfo != null) {
/*      */       
/* 1245 */       int index = occlusionTextureInfo.getIndex().intValue();
/* 1246 */       DefaultTextureModel defaultTextureModel = this.gltfModel.getTextureModel(index);
/* 1247 */       materialModel.setOcclusionTexture((TextureModel)defaultTextureModel);
/* 1248 */       materialModel.setOcclusionTexcoord(occlusionTextureInfo
/* 1249 */           .getTexCoord());
/*      */       
/* 1251 */       float occlusionStrength = ((Float)Optionals.of(occlusionTextureInfo
/* 1252 */           .getStrength(), occlusionTextureInfo
/* 1253 */           .defaultStrength())).floatValue();
/* 1254 */       materialModel.setOcclusionStrength(occlusionStrength);
/*      */     } 
/*      */ 
/*      */     
/* 1258 */     TextureInfo emissiveTextureInfo = material.getEmissiveTexture();
/* 1259 */     if (emissiveTextureInfo != null) {
/*      */       
/* 1261 */       int index = emissiveTextureInfo.getIndex().intValue();
/* 1262 */       DefaultTextureModel defaultTextureModel = this.gltfModel.getTextureModel(index);
/* 1263 */       materialModel.setEmissiveTexture((TextureModel)defaultTextureModel);
/* 1264 */       materialModel.setEmissiveTexcoord(emissiveTextureInfo
/* 1265 */           .getTexCoord());
/*      */     } 
/*      */     
/* 1268 */     float[] emissiveFactor = (float[])Optionals.of(material
/* 1269 */         .getEmissiveFactor(), material
/* 1270 */         .defaultEmissiveFactor());
/* 1271 */     materialModel.setEmissiveFactor(emissiveFactor);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void initExtensionsModel() {
/* 1280 */     List<String> extensionsUsed = this.gltf.getExtensionsUsed();
/* 1281 */     List<String> extensionsRequired = this.gltf.getExtensionsRequired();
/* 1282 */     DefaultExtensionsModel extensionsModel = this.gltfModel.getExtensionsModel();
/* 1283 */     extensionsModel.addExtensionsUsed(extensionsUsed);
/* 1284 */     extensionsModel.addExtensionsRequired(extensionsRequired);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void initAssetModel() {
/* 1293 */     Asset asset = this.gltf.getAsset();
/* 1294 */     if (asset != null) {
/*      */       
/* 1296 */       DefaultAssetModel assetModel = this.gltfModel.getAssetModel();
/* 1297 */       transferGltfPropertyElements((GlTFProperty)asset, (AbstractModelElement)assetModel);
/* 1298 */       assetModel.setCopyright(asset.getCopyright());
/* 1299 */       assetModel.setGenerator(asset.getGenerator());
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static void transferGltfPropertyElements(GlTFProperty property, AbstractModelElement modelElement) {
/* 1313 */     modelElement.setExtensions(property.getExtensions());
/* 1314 */     modelElement.setExtras(property.getExtras());
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static void transferGltfChildOfRootPropertyElements(GlTFChildOfRootProperty property, AbstractNamedModelElement modelElement) {
/* 1328 */     modelElement.setName(property.getName());
/* 1329 */     transferGltfPropertyElements((GlTFProperty)property, (AbstractModelElement)modelElement);
/*      */   }
/*      */ }


/* Location:              C:\Users\DuduM\OneDrive\Área de Trabalho\mw10\modularwarfare-shining-2023.2.4.4f-fix10.jar!\de\javagl\jgltf\model\v2\GltfModelCreatorV2.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */