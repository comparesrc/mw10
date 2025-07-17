/*      */ package de.javagl.jgltf.model.v2;
/*      */ 
/*      */ import de.javagl.jgltf.impl.v2.Accessor;
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
/*      */ import de.javagl.jgltf.model.AssetModel;
/*      */ import de.javagl.jgltf.model.BufferModel;
/*      */ import de.javagl.jgltf.model.BufferViewModel;
/*      */ import de.javagl.jgltf.model.CameraModel;
/*      */ import de.javagl.jgltf.model.CameraOrthographicModel;
/*      */ import de.javagl.jgltf.model.CameraPerspectiveModel;
/*      */ import de.javagl.jgltf.model.ExtensionsModel;
/*      */ import de.javagl.jgltf.model.GltfModel;
/*      */ import de.javagl.jgltf.model.ImageModel;
/*      */ import de.javagl.jgltf.model.MaterialModel;
/*      */ import de.javagl.jgltf.model.MeshModel;
/*      */ import de.javagl.jgltf.model.MeshPrimitiveModel;
/*      */ import de.javagl.jgltf.model.ModelElement;
/*      */ import de.javagl.jgltf.model.NamedModelElement;
/*      */ import de.javagl.jgltf.model.NodeModel;
/*      */ import de.javagl.jgltf.model.Optionals;
/*      */ import de.javagl.jgltf.model.SceneModel;
/*      */ import de.javagl.jgltf.model.SkinModel;
/*      */ import de.javagl.jgltf.model.TextureModel;
/*      */ import de.javagl.jgltf.model.impl.DefaultNodeModel;
/*      */ import java.util.ArrayList;
/*      */ import java.util.Collection;
/*      */ import java.util.LinkedHashMap;
/*      */ import java.util.List;
/*      */ import java.util.Map;
/*      */ import java.util.Objects;
/*      */ import java.util.function.Function;
/*      */ import java.util.logging.Logger;
/*      */ import java.util.stream.Collectors;
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
/*      */ public class GltfCreatorV2
/*      */ {
/*  104 */   private static final Logger logger = Logger.getLogger(GltfCreatorV2.class.getName());
/*      */   
/*      */   private final GltfModel gltfModel;
/*      */   private final List<NodeModel> nodesWithSingleMeshes;
/*      */   private final Map<AccessorModel, Integer> accessorIndices;
/*      */   private final Map<BufferModel, Integer> bufferIndices;
/*      */   private final Map<BufferViewModel, Integer> bufferViewIndices;
/*      */   private final Map<CameraModel, Integer> cameraIndices;
/*      */   
/*      */   public static GlTF create(GltfModel gltfModel) {
/*  114 */     GltfCreatorV2 creator = new GltfCreatorV2(gltfModel);
/*  115 */     return creator.create();
/*      */   }
/*      */   
/*      */   private final Map<ImageModel, Integer> imageIndices;
/*      */   private final Map<MaterialModel, Integer> materialIndices;
/*      */   private final Map<MeshModel, Integer> meshIndices;
/*      */   private final Map<NodeModel, Integer> nodeIndices;
/*      */   private final Map<SkinModel, Integer> skinIndices;
/*      */   private final Map<TextureModel, Integer> textureIndices;
/*      */   private final Map<SamplerInfo, Integer> samplerIndices;
/*      */   
/*      */   private static class SamplerInfo
/*      */   {
/*      */     final Integer magFilter;
/*      */     final Integer minFilter;
/*      */     
/*      */     SamplerInfo(TextureModel textureModel) {
/*  132 */       this.magFilter = textureModel.getMagFilter();
/*  133 */       this.minFilter = textureModel.getMinFilter();
/*  134 */       this.wrapS = textureModel.getWrapS();
/*  135 */       this.wrapT = textureModel.getWrapT();
/*      */     }
/*      */     final Integer wrapS;
/*      */     final Integer wrapT;
/*      */     
/*      */     public int hashCode() {
/*  141 */       return Objects.hash(new Object[] { this.magFilter, this.minFilter, this.wrapS, this.wrapT });
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public boolean equals(Object object) {
/*  147 */       if (this == object)
/*      */       {
/*  149 */         return true;
/*      */       }
/*  151 */       if (object == null)
/*      */       {
/*  153 */         return false;
/*      */       }
/*  155 */       if (getClass() != object.getClass())
/*      */       {
/*  157 */         return false;
/*      */       }
/*  159 */       SamplerInfo other = (SamplerInfo)object;
/*  160 */       if (!Objects.equals(this.magFilter, other.magFilter))
/*      */       {
/*  162 */         return false;
/*      */       }
/*  164 */       if (!Objects.equals(this.minFilter, other.minFilter))
/*      */       {
/*  166 */         return false;
/*      */       }
/*  168 */       if (!Objects.equals(this.wrapS, other.wrapS))
/*      */       {
/*  170 */         return false;
/*      */       }
/*  172 */       if (!Objects.equals(this.wrapT, other.wrapT))
/*      */       {
/*  174 */         return false;
/*      */       }
/*  176 */       return true;
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
/*      */   private GltfCreatorV2(GltfModel gltfModel) {
/*  255 */     this.gltfModel = Objects.<GltfModel>requireNonNull(gltfModel, "The gltfModel may not be null");
/*      */ 
/*      */     
/*  258 */     this.accessorIndices = computeIndexMap(gltfModel.getAccessorModels());
/*  259 */     this.bufferIndices = computeIndexMap(gltfModel.getBufferModels());
/*  260 */     this.bufferViewIndices = computeIndexMap(gltfModel.getBufferViewModels());
/*  261 */     this.cameraIndices = computeIndexMap(gltfModel.getCameraModels());
/*  262 */     this.imageIndices = computeIndexMap(gltfModel.getImageModels());
/*  263 */     this.materialIndices = computeIndexMap(gltfModel.getMaterialModels());
/*  264 */     this.meshIndices = computeIndexMap(gltfModel.getMeshModels());
/*      */     
/*  266 */     this
/*  267 */       .nodesWithSingleMeshes = createNodesWithSingleMeshes(gltfModel.getNodeModels());
/*  268 */     this.nodeIndices = computeIndexMap(this.nodesWithSingleMeshes);
/*  269 */     this.skinIndices = computeIndexMap(gltfModel.getSkinModels());
/*  270 */     this.textureIndices = computeIndexMap(gltfModel.getTextureModels());
/*      */     
/*  272 */     this.samplerIndices = createSamplerIndices(gltfModel.getTextureModels());
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public GlTF create() {
/*  282 */     GlTF gltf = new GlTF();
/*  283 */     transferGltfPropertyElements((ModelElement)this.gltfModel, (GlTFProperty)gltf);
/*      */     
/*  285 */     gltf.setAccessors(map(this.gltfModel
/*  286 */           .getAccessorModels(), this::createAccessor));
/*  287 */     gltf.setAnimations(map(this.gltfModel
/*  288 */           .getAnimationModels(), this::createAnimation));
/*  289 */     gltf.setBuffers(map(this.gltfModel
/*  290 */           .getBufferModels(), GltfCreatorV2::createBuffer));
/*  291 */     gltf.setBufferViews(map(this.gltfModel
/*  292 */           .getBufferViewModels(), this::createBufferView));
/*  293 */     gltf.setCameras(map(this.gltfModel
/*  294 */           .getCameraModels(), this::createCamera));
/*  295 */     gltf.setImages(map(this.gltfModel
/*  296 */           .getImageModels(), this::createImage));
/*  297 */     gltf.setMaterials(map(this.gltfModel
/*  298 */           .getMaterialModels(), this::createMaterial));
/*  299 */     gltf.setMeshes(map(this.gltfModel
/*  300 */           .getMeshModels(), this::createMesh));
/*  301 */     gltf.setNodes(map(this.nodesWithSingleMeshes, this::createNode));
/*      */     
/*  303 */     gltf.setScenes(map(this.gltfModel
/*  304 */           .getSceneModels(), this::createScene));
/*  305 */     gltf.setSkins(map(this.gltfModel
/*  306 */           .getSkinModels(), this::createSkin));
/*      */     
/*  308 */     gltf.setSamplers(createSamplers());
/*      */     
/*  310 */     gltf.setTextures(map(this.gltfModel
/*  311 */           .getTextureModels(), this::createTexture));
/*      */     
/*  313 */     if (gltf.getScenes() != null && !gltf.getScenes().isEmpty())
/*      */     {
/*  315 */       gltf.setScene(Integer.valueOf(0));
/*      */     }
/*      */     
/*  318 */     ExtensionsModel extensionsModel = this.gltfModel.getExtensionsModel();
/*  319 */     List<String> extensionsUsed = extensionsModel.getExtensionsUsed();
/*  320 */     if (!extensionsUsed.isEmpty())
/*      */     {
/*  322 */       gltf.setExtensionsUsed(extensionsUsed);
/*      */     }
/*      */     
/*  325 */     List<String> extensionsRequired = extensionsModel.getExtensionsRequired();
/*  326 */     if (!extensionsRequired.isEmpty())
/*      */     {
/*  328 */       gltf.setExtensionsRequired(extensionsRequired);
/*      */     }
/*      */     
/*  331 */     Asset asset = createAsset(this.gltfModel.getAssetModel());
/*  332 */     gltf.setAsset(asset);
/*      */     
/*  334 */     return gltf;
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
/*      */   private static List<NodeModel> createNodesWithSingleMeshes(List<NodeModel> nodeModels) {
/*  349 */     List<NodeModel> newNodes = new ArrayList<>();
/*  350 */     List<NodeModel> nodeModelsWithSingleMeshes = new ArrayList<>(nodeModels);
/*      */     
/*  352 */     for (int i = 0; i < nodeModelsWithSingleMeshes.size(); i++) {
/*      */       
/*  354 */       NodeModel nodeModel = nodeModelsWithSingleMeshes.get(i);
/*  355 */       List<MeshModel> meshModels = nodeModel.getMeshModels();
/*  356 */       if (meshModels.size() > 1) {
/*      */         
/*  358 */         DefaultNodeModel newParentNodeModel = new DefaultNodeModel(nodeModel);
/*      */ 
/*      */         
/*  361 */         for (int j = 0; j < meshModels.size(); j++) {
/*      */           
/*  363 */           MeshModel meshModel = meshModels.get(j);
/*  364 */           DefaultNodeModel child = new DefaultNodeModel();
/*  365 */           child.addMeshModel(meshModel);
/*  366 */           newNodes.add(child);
/*  367 */           newParentNodeModel.addChild(child);
/*      */         } 
/*  369 */         nodeModelsWithSingleMeshes.set(i, newParentNodeModel);
/*      */       } 
/*      */     } 
/*  372 */     nodeModelsWithSingleMeshes.addAll(newNodes);
/*  373 */     return nodeModelsWithSingleMeshes;
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
/*      */   private Accessor createAccessor(AccessorModel accessorModel) {
/*  385 */     Integer bufferViewIndex = this.bufferViewIndices.get(accessorModel.getBufferViewModel());
/*  386 */     return createAccessor(accessorModel, bufferViewIndex);
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
/*      */   public static Accessor createAccessor(AccessorModel accessorModel, Integer bufferViewIndex) {
/*  400 */     Accessor accessor = new Accessor();
/*  401 */     transferGltfChildOfRootPropertyElements((NamedModelElement)accessorModel, (GlTFChildOfRootProperty)accessor);
/*      */     
/*  403 */     accessor.setBufferView(bufferViewIndex);
/*      */     
/*  405 */     accessor.setByteOffset(Integer.valueOf(accessorModel.getByteOffset()));
/*  406 */     accessor.setComponentType(Integer.valueOf(accessorModel.getComponentType()));
/*  407 */     accessor.setCount(Integer.valueOf(accessorModel.getCount()));
/*  408 */     accessor.setType(accessorModel.getElementType().toString());
/*  409 */     accessor.setNormalized(
/*  410 */         accessorModel.isNormalized() ? Boolean.valueOf(true) : null);
/*      */     
/*  412 */     AccessorData accessorData = accessorModel.getAccessorData();
/*  413 */     accessor.setMax(AccessorDatas.computeMax(accessorData));
/*  414 */     accessor.setMin(AccessorDatas.computeMin(accessorData));
/*      */     
/*  416 */     return accessor;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private Animation createAnimation(AnimationModel animationModel) {
/*  427 */     Animation animation = new Animation();
/*  428 */     transferGltfChildOfRootPropertyElements((NamedModelElement)animationModel, (GlTFChildOfRootProperty)animation);
/*      */     
/*  430 */     List<AnimationModel.Sampler> samplers = new ArrayList<>();
/*  431 */     List<AnimationModel.Channel> channels = animationModel.getChannels();
/*  432 */     for (AnimationModel.Channel channel : channels)
/*      */     {
/*  434 */       samplers.add(channel.getSampler());
/*      */     }
/*      */     
/*  437 */     List<AnimationChannel> animationChannels = new ArrayList<>();
/*      */     
/*  439 */     for (AnimationModel.Channel channel : channels) {
/*      */       
/*  441 */       AnimationChannel animationChannel = new AnimationChannel();
/*      */       
/*  443 */       AnimationChannelTarget target = new AnimationChannelTarget();
/*  444 */       NodeModel nodeModel = channel.getNodeModel();
/*  445 */       target.setNode(this.nodeIndices.get(nodeModel));
/*  446 */       target.setPath(channel.getPath());
/*  447 */       animationChannel.setTarget(target);
/*      */       
/*  449 */       AnimationModel.Sampler sampler = channel.getSampler();
/*  450 */       animationChannel.setSampler(Integer.valueOf(samplers.indexOf(sampler)));
/*      */       
/*  452 */       animationChannels.add(animationChannel);
/*      */     } 
/*  454 */     animation.setChannels(animationChannels);
/*      */     
/*  456 */     List<AnimationSampler> animationSamplers = new ArrayList<>();
/*      */     
/*  458 */     for (AnimationModel.Sampler sampler : samplers) {
/*      */       
/*  460 */       AnimationSampler animationSampler = new AnimationSampler();
/*  461 */       animationSampler.setInput(this.accessorIndices
/*  462 */           .get(sampler.getInput()));
/*  463 */       animationSampler.setInterpolation(sampler
/*  464 */           .getInterpolation().name());
/*  465 */       animationSampler.setOutput(this.accessorIndices
/*  466 */           .get(sampler.getOutput()));
/*  467 */       animationSamplers.add(animationSampler);
/*      */     } 
/*  469 */     animation.setSamplers(animationSamplers);
/*      */     
/*  471 */     return animation;
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
/*      */   public static Buffer createBuffer(BufferModel bufferModel) {
/*  483 */     Buffer buffer = new Buffer();
/*  484 */     transferGltfChildOfRootPropertyElements((NamedModelElement)bufferModel, (GlTFChildOfRootProperty)buffer);
/*      */     
/*  486 */     buffer.setUri(bufferModel.getUri());
/*  487 */     buffer.setByteLength(Integer.valueOf(bufferModel.getByteLength()));
/*  488 */     return buffer;
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
/*      */   private BufferView createBufferView(BufferViewModel bufferViewModel) {
/*  500 */     Integer bufferIndex = this.bufferIndices.get(bufferViewModel.getBufferModel());
/*  501 */     return createBufferView(bufferViewModel, bufferIndex);
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
/*      */   public static BufferView createBufferView(BufferViewModel bufferViewModel, Integer bufferIndex) {
/*  515 */     BufferView bufferView = new BufferView();
/*  516 */     transferGltfChildOfRootPropertyElements((NamedModelElement)bufferViewModel, (GlTFChildOfRootProperty)bufferView);
/*      */     
/*  518 */     bufferView.setBuffer(bufferIndex);
/*  519 */     bufferView.setByteOffset(Integer.valueOf(bufferViewModel.getByteOffset()));
/*  520 */     bufferView.setByteLength(Integer.valueOf(bufferViewModel.getByteLength()));
/*  521 */     bufferView.setByteStride(bufferViewModel.getByteStride());
/*  522 */     bufferView.setTarget(bufferViewModel.getTarget());
/*      */     
/*  524 */     return bufferView;
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
/*      */   private Camera createCamera(CameraModel cameraModel) {
/*  536 */     Camera camera = new Camera();
/*  537 */     transferGltfChildOfRootPropertyElements((NamedModelElement)cameraModel, (GlTFChildOfRootProperty)camera);
/*      */ 
/*      */     
/*  540 */     CameraPerspectiveModel cameraPerspectiveModel = cameraModel.getCameraPerspectiveModel();
/*      */     
/*  542 */     CameraOrthographicModel cameraOrthographicModel = cameraModel.getCameraOrthographicModel();
/*  543 */     if (cameraPerspectiveModel != null) {
/*      */       
/*  545 */       CameraPerspective cameraPerspective = new CameraPerspective();
/*  546 */       cameraPerspective.setAspectRatio(cameraPerspectiveModel
/*  547 */           .getAspectRatio());
/*  548 */       cameraPerspective.setYfov(cameraPerspectiveModel
/*  549 */           .getYfov());
/*  550 */       cameraPerspective.setZfar(cameraPerspectiveModel
/*  551 */           .getZfar());
/*  552 */       cameraPerspective.setZnear(cameraPerspectiveModel
/*  553 */           .getZnear());
/*  554 */       camera.setPerspective(cameraPerspective);
/*  555 */       camera.setType("perspective");
/*      */     }
/*  557 */     else if (cameraOrthographicModel != null) {
/*      */       
/*  559 */       CameraOrthographic cameraOrthographic = new CameraOrthographic();
/*  560 */       cameraOrthographic.setXmag(cameraOrthographicModel
/*  561 */           .getXmag());
/*  562 */       cameraOrthographic.setYmag(cameraOrthographicModel
/*  563 */           .getYmag());
/*  564 */       cameraOrthographic.setZfar(cameraOrthographicModel
/*  565 */           .getZfar());
/*  566 */       cameraOrthographic.setZnear(cameraOrthographicModel
/*  567 */           .getZnear());
/*  568 */       camera.setOrthographic(cameraOrthographic);
/*  569 */       camera.setType("orthographic");
/*      */     }
/*      */     else {
/*      */       
/*  573 */       logger.severe("Camera is neither perspective nor orthographic");
/*      */     } 
/*  575 */     return camera;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private Image createImage(ImageModel imageModel) {
/*  586 */     Image image = new Image();
/*  587 */     transferGltfChildOfRootPropertyElements((NamedModelElement)imageModel, (GlTFChildOfRootProperty)image);
/*      */ 
/*      */     
/*  590 */     Integer bufferView = this.bufferViewIndices.get(imageModel.getBufferViewModel());
/*  591 */     image.setBufferView(bufferView);
/*      */     
/*  593 */     image.setMimeType(imageModel.getMimeType());
/*  594 */     image.setUri(imageModel.getUri());
/*      */     
/*  596 */     return image;
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
/*      */   private Material createMaterial(MaterialModel materialModel) {
/*  609 */     if (materialModel instanceof MaterialModelV2) {
/*      */       
/*  611 */       MaterialModelV2 materialModelV2 = (MaterialModelV2)materialModel;
/*  612 */       return createMaterialV2(materialModelV2);
/*      */     } 
/*      */     
/*  615 */     logger.severe("Cannot store glTF 1.0 material in glTF 2.0");
/*  616 */     return null;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private Material createMaterialV2(MaterialModelV2 materialModel) {
/*  627 */     Material material = new Material();
/*  628 */     transferGltfChildOfRootPropertyElements((NamedModelElement)materialModel, (GlTFChildOfRootProperty)material);
/*      */     
/*  630 */     MaterialModelV2.AlphaMode alphaMode = materialModel.getAlphaMode();
/*  631 */     if (alphaMode == null) {
/*      */       
/*  633 */       material.setAlphaMode(MaterialModelV2.AlphaMode.OPAQUE.name());
/*      */     }
/*      */     else {
/*      */       
/*  637 */       material.setAlphaMode(alphaMode.name());
/*      */     } 
/*  639 */     if (MaterialModelV2.AlphaMode.MASK.equals(alphaMode))
/*      */     {
/*  641 */       material.setAlphaCutoff(Float.valueOf(materialModel.getAlphaCutoff()));
/*      */     }
/*  643 */     material.setDoubleSided(Boolean.valueOf(materialModel.isDoubleSided()));
/*      */     
/*  645 */     MaterialPbrMetallicRoughness pbrMetallicRoughness = new MaterialPbrMetallicRoughness();
/*      */     
/*  647 */     material.setPbrMetallicRoughness(pbrMetallicRoughness);
/*      */     
/*  649 */     pbrMetallicRoughness.setBaseColorFactor(materialModel
/*  650 */         .getBaseColorFactor());
/*      */     
/*  652 */     TextureModel baseColorTexture = materialModel.getBaseColorTexture();
/*  653 */     if (baseColorTexture != null) {
/*      */       
/*  655 */       TextureInfo baseColorTextureInfo = new TextureInfo();
/*  656 */       baseColorTextureInfo.setIndex(this.textureIndices
/*  657 */           .get(baseColorTexture));
/*  658 */       baseColorTextureInfo.setTexCoord(materialModel
/*  659 */           .getBaseColorTexcoord());
/*  660 */       pbrMetallicRoughness.setBaseColorTexture(baseColorTextureInfo);
/*      */     } 
/*      */     
/*  663 */     pbrMetallicRoughness.setMetallicFactor(
/*  664 */         Float.valueOf(materialModel.getMetallicFactor()));
/*  665 */     pbrMetallicRoughness.setRoughnessFactor(
/*  666 */         Float.valueOf(materialModel.getRoughnessFactor()));
/*      */     
/*  668 */     TextureModel metallicRoughnessTexture = materialModel.getMetallicRoughnessTexture();
/*  669 */     if (metallicRoughnessTexture != null) {
/*      */       
/*  671 */       TextureInfo metallicRoughnessTextureInfo = new TextureInfo();
/*  672 */       metallicRoughnessTextureInfo.setIndex(this.textureIndices
/*  673 */           .get(metallicRoughnessTexture));
/*  674 */       metallicRoughnessTextureInfo.setTexCoord(materialModel
/*  675 */           .getMetallicRoughnessTexcoord());
/*  676 */       pbrMetallicRoughness.setMetallicRoughnessTexture(metallicRoughnessTextureInfo);
/*      */     } 
/*      */ 
/*      */     
/*  680 */     TextureModel normalTexture = materialModel.getNormalTexture();
/*  681 */     if (normalTexture != null) {
/*      */       
/*  683 */       MaterialNormalTextureInfo normalTextureInfo = new MaterialNormalTextureInfo();
/*      */       
/*  685 */       normalTextureInfo.setIndex(this.textureIndices
/*  686 */           .get(normalTexture));
/*  687 */       normalTextureInfo.setTexCoord(materialModel
/*  688 */           .getNormalTexcoord());
/*  689 */       normalTextureInfo.setScale(
/*  690 */           Float.valueOf(materialModel.getNormalScale()));
/*  691 */       material.setNormalTexture(normalTextureInfo);
/*      */     } 
/*      */     
/*  694 */     TextureModel occlusionTexture = materialModel.getOcclusionTexture();
/*  695 */     if (occlusionTexture != null) {
/*      */       
/*  697 */       MaterialOcclusionTextureInfo occlusionTextureInfo = new MaterialOcclusionTextureInfo();
/*      */       
/*  699 */       occlusionTextureInfo.setIndex(this.textureIndices
/*  700 */           .get(occlusionTexture));
/*  701 */       occlusionTextureInfo.setTexCoord(materialModel
/*  702 */           .getOcclusionTexcoord());
/*  703 */       occlusionTextureInfo.setStrength(
/*  704 */           Float.valueOf(materialModel.getOcclusionStrength()));
/*  705 */       material.setOcclusionTexture(occlusionTextureInfo);
/*      */     } 
/*      */ 
/*      */     
/*  709 */     TextureModel emissiveTexture = materialModel.getEmissiveTexture();
/*  710 */     if (emissiveTexture != null) {
/*      */       
/*  712 */       TextureInfo emissiveTextureInfo = new TextureInfo();
/*  713 */       emissiveTextureInfo.setIndex(this.textureIndices
/*  714 */           .get(emissiveTexture));
/*  715 */       emissiveTextureInfo.setTexCoord(materialModel
/*  716 */           .getEmissiveTexcoord());
/*  717 */       material.setEmissiveFactor(materialModel
/*  718 */           .getEmissiveFactor());
/*  719 */       material.setEmissiveTexture(emissiveTextureInfo);
/*      */     } 
/*      */     
/*  722 */     return material;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private Mesh createMesh(MeshModel meshModel) {
/*  733 */     Mesh mesh = new Mesh();
/*  734 */     transferGltfChildOfRootPropertyElements((NamedModelElement)meshModel, (GlTFChildOfRootProperty)mesh);
/*      */     
/*  736 */     List<MeshPrimitive> meshPrimitives = new ArrayList<>();
/*      */     
/*  738 */     List<MeshPrimitiveModel> meshPrimitiveModels = meshModel.getMeshPrimitiveModels();
/*  739 */     for (MeshPrimitiveModel meshPrimitiveModel : meshPrimitiveModels) {
/*      */ 
/*      */       
/*  742 */       MeshPrimitive meshPrimitive = createMeshPrimitive(meshPrimitiveModel);
/*  743 */       meshPrimitives.add(meshPrimitive);
/*      */     } 
/*  745 */     mesh.setPrimitives(meshPrimitives);
/*  746 */     mesh.setWeights(toList(meshModel.getWeights()));
/*  747 */     return mesh;
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
/*      */   private MeshPrimitive createMeshPrimitive(MeshPrimitiveModel meshPrimitiveModel) {
/*  759 */     MeshPrimitive meshPrimitive = new MeshPrimitive();
/*  760 */     transferGltfPropertyElements((ModelElement)meshPrimitiveModel, (GlTFProperty)meshPrimitive);
/*      */     
/*  762 */     meshPrimitive.setMode(Integer.valueOf(meshPrimitiveModel.getMode()));
/*      */     
/*  764 */     Map<String, Integer> attributes = resolveIndices(meshPrimitiveModel
/*  765 */         .getAttributes(), this.accessorIndices::get);
/*      */     
/*  767 */     meshPrimitive.setAttributes(attributes);
/*      */     
/*  769 */     AccessorModel indices = meshPrimitiveModel.getIndices();
/*  770 */     meshPrimitive.setIndices(this.accessorIndices.get(indices));
/*      */ 
/*      */     
/*  773 */     List<Map<String, AccessorModel>> modelTargetsList = meshPrimitiveModel.getTargets();
/*  774 */     if (!modelTargetsList.isEmpty()) {
/*      */       
/*  776 */       List<Map<String, Integer>> targetsList = new ArrayList<>();
/*      */       
/*  778 */       for (Map<String, AccessorModel> modelTargets : modelTargetsList) {
/*      */         
/*  780 */         Map<String, Integer> targets = resolveIndices(modelTargets, this.accessorIndices::get);
/*      */         
/*  782 */         targetsList.add(targets);
/*      */       } 
/*  784 */       meshPrimitive.setTargets(targetsList);
/*      */     } 
/*      */     
/*  787 */     Integer material = this.materialIndices.get(meshPrimitiveModel
/*  788 */         .getMaterialModel());
/*  789 */     meshPrimitive.setMaterial(material);
/*      */     
/*  791 */     return meshPrimitive;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private Node createNode(NodeModel nodeModel) {
/*  802 */     Node node = new Node();
/*  803 */     transferGltfChildOfRootPropertyElements((NamedModelElement)nodeModel, (GlTFChildOfRootProperty)node);
/*      */     
/*  805 */     if (!nodeModel.getChildren().isEmpty())
/*      */     {
/*  807 */       node.setChildren(map(nodeModel
/*  808 */             .getChildren(), this.nodeIndices::get));
/*      */     }
/*      */     
/*  811 */     node.setTranslation(Optionals.clone(nodeModel.getTranslation()));
/*  812 */     node.setRotation(Optionals.clone(nodeModel.getRotation()));
/*  813 */     node.setScale(Optionals.clone(nodeModel.getScale()));
/*  814 */     node.setMatrix(Optionals.clone(nodeModel.getMatrix()));
/*      */     
/*  816 */     Integer camera = this.cameraIndices.get(nodeModel.getCameraModel());
/*  817 */     node.setCamera(camera);
/*      */     
/*  819 */     Integer skin = this.skinIndices.get(nodeModel.getSkinModel());
/*  820 */     node.setSkin(skin);
/*      */     
/*  822 */     node.setWeights(toList(nodeModel.getWeights()));
/*      */     
/*  824 */     List<MeshModel> nodeMeshModels = nodeModel.getMeshModels();
/*  825 */     if (nodeMeshModels.size() > 1)
/*      */     {
/*      */ 
/*      */ 
/*      */       
/*  830 */       logger.severe("Warning: glTF 2.0 only supports one mesh per node");
/*      */     }
/*  832 */     if (!nodeMeshModels.isEmpty()) {
/*      */       
/*  834 */       MeshModel nodeMeshModel = nodeMeshModels.get(0);
/*  835 */       Integer mesh = this.meshIndices.get(nodeMeshModel);
/*  836 */       node.setMesh(mesh);
/*      */     } 
/*  838 */     return node;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private Scene createScene(SceneModel sceneModel) {
/*  849 */     Scene scene = new Scene();
/*  850 */     transferGltfChildOfRootPropertyElements((NamedModelElement)sceneModel, (GlTFChildOfRootProperty)scene);
/*      */     
/*  852 */     scene.setNodes(map(sceneModel
/*  853 */           .getNodeModels(), this.nodeIndices::get));
/*  854 */     return scene;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private Skin createSkin(SkinModel skinModel) {
/*  865 */     Skin skin = new Skin();
/*  866 */     transferGltfChildOfRootPropertyElements((NamedModelElement)skinModel, (GlTFChildOfRootProperty)skin);
/*      */ 
/*      */     
/*  869 */     Integer inverseBindMatrices = this.accessorIndices.get(skinModel.getInverseBindMatrices());
/*  870 */     skin.setInverseBindMatrices(inverseBindMatrices);
/*      */     
/*  872 */     skin.setJoints(map(skinModel
/*  873 */           .getJoints(), this.nodeIndices::get));
/*      */     
/*  875 */     Integer skeleton = this.nodeIndices.get(skinModel.getSkeleton());
/*  876 */     skin.setSkeleton(skeleton);
/*      */     
/*  878 */     return skin;
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
/*      */   private Map<SamplerInfo, Integer> createSamplerIndices(List<TextureModel> textureModels) {
/*  892 */     Map<SamplerInfo, Integer> samplerIndices = new LinkedHashMap<>();
/*      */     
/*  894 */     for (TextureModel textureModel : textureModels) {
/*      */       
/*  896 */       SamplerInfo samplerInfo = new SamplerInfo(textureModel);
/*  897 */       if (!samplerIndices.containsKey(samplerInfo))
/*      */       {
/*  899 */         samplerIndices.put(samplerInfo, Integer.valueOf(samplerIndices.size()));
/*      */       }
/*      */     } 
/*  902 */     return samplerIndices;
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
/*      */   private List<Sampler> createSamplers() {
/*  914 */     if (this.samplerIndices.isEmpty())
/*      */     {
/*  916 */       return null;
/*      */     }
/*  918 */     List<Sampler> samplers = new ArrayList<>();
/*      */     
/*  920 */     for (SamplerInfo samplerInfo : this.samplerIndices.keySet()) {
/*      */ 
/*      */       
/*  923 */       Sampler sampler = createSampler(samplerInfo);
/*  924 */       samplers.add(sampler);
/*      */     } 
/*  926 */     return samplers;
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
/*      */   private static Sampler createSampler(SamplerInfo samplerInfo) {
/*  939 */     Sampler sampler = new Sampler();
/*      */     
/*  941 */     sampler.setMagFilter(samplerInfo.magFilter);
/*  942 */     sampler.setMinFilter(samplerInfo.minFilter);
/*  943 */     sampler.setWrapS(samplerInfo.wrapS);
/*  944 */     sampler.setWrapT(samplerInfo.wrapT);
/*  945 */     return sampler;
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
/*      */   private Texture createTexture(TextureModel textureModel) {
/*  957 */     Texture texture = new Texture();
/*  958 */     transferGltfChildOfRootPropertyElements((NamedModelElement)textureModel, (GlTFChildOfRootProperty)texture);
/*      */     
/*  960 */     SamplerInfo samplerInfo = new SamplerInfo(textureModel);
/*  961 */     Integer index = this.samplerIndices.get(samplerInfo);
/*  962 */     texture.setSampler(index);
/*      */     
/*  964 */     texture.setSource(this.imageIndices.get(textureModel.getImageModel()));
/*      */     
/*  966 */     return texture;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private Asset createAsset(AssetModel assetModel) {
/*  977 */     Asset asset = new Asset();
/*  978 */     asset.setVersion("2.0");
/*  979 */     asset.setGenerator("JglTF from https://github.com/javagl/JglTF");
/*      */     
/*  981 */     transferGltfPropertyElements((ModelElement)assetModel, (GlTFProperty)asset);
/*      */     
/*  983 */     if (assetModel.getCopyright() != null)
/*      */     {
/*  985 */       asset.setCopyright(assetModel.getCopyright());
/*      */     }
/*  987 */     if (assetModel.getGenerator() != null)
/*      */     {
/*  989 */       asset.setGenerator(assetModel.getGenerator());
/*      */     }
/*  991 */     return asset;
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
/*      */   private static void transferGltfPropertyElements(ModelElement modelElement, GlTFProperty property) {
/* 1004 */     property.setExtensions(modelElement.getExtensions());
/* 1005 */     property.setExtras(modelElement.getExtras());
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
/*      */   private static void transferGltfChildOfRootPropertyElements(NamedModelElement modelElement, GlTFChildOfRootProperty property) {
/* 1019 */     property.setName(modelElement.getName());
/* 1020 */     transferGltfPropertyElements((ModelElement)modelElement, (GlTFProperty)property);
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
/*      */   private static <T, U> List<U> map(Collection<? extends T> collection, Function<? super T, ? extends U> mapper) {
/* 1036 */     if (collection.isEmpty())
/*      */     {
/* 1038 */       return null;
/*      */     }
/* 1040 */     return (List<U>)collection.stream().<U>map(mapper).collect(Collectors.toList());
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
/*      */   private static <K, T> Map<K, Integer> resolveIndices(Map<K, ? extends T> map, Function<? super T, Integer> indexLookup) {
/* 1056 */     Map<K, Integer> result = new LinkedHashMap<>();
/* 1057 */     for (Map.Entry<K, ? extends T> entry : map.entrySet()) {
/*      */       
/* 1059 */       K key = entry.getKey();
/* 1060 */       T value = entry.getValue();
/* 1061 */       Integer index = indexLookup.apply(value);
/* 1062 */       result.put(key, index);
/*      */     } 
/* 1064 */     return result;
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
/*      */   private static <T> Map<T, Integer> computeIndexMap(Collection<? extends T> elements) {
/* 1077 */     Map<T, Integer> indices = new LinkedHashMap<>();
/* 1078 */     int index = 0;
/* 1079 */     for (T element : elements) {
/*      */       
/* 1081 */       indices.put(element, Integer.valueOf(index));
/* 1082 */       index++;
/*      */     } 
/* 1084 */     return indices;
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
/*      */   private static List<Float> toList(float[] array) {
/* 1097 */     if (array == null)
/*      */     {
/* 1099 */       return null;
/*      */     }
/* 1101 */     List<Float> list = new ArrayList<>();
/* 1102 */     for (float f : array)
/*      */     {
/* 1104 */       list.add(Float.valueOf(f));
/*      */     }
/* 1106 */     return list;
/*      */   }
/*      */ }


/* Location:              C:\Users\DuduM\OneDrive\Área de Trabalho\mw10\modularwarfare-shining-2023.2.4.4f-fix10.jar!\de\javagl\jgltf\model\v2\GltfCreatorV2.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */