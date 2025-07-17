/*      */ package de.javagl.jgltf.model.v1;
/*      */ 
/*      */ import de.javagl.jgltf.impl.v1.Accessor;
/*      */ import de.javagl.jgltf.impl.v1.Animation;
/*      */ import de.javagl.jgltf.impl.v1.AnimationChannel;
/*      */ import de.javagl.jgltf.impl.v1.AnimationChannelTarget;
/*      */ import de.javagl.jgltf.impl.v1.AnimationSampler;
/*      */ import de.javagl.jgltf.impl.v1.Asset;
/*      */ import de.javagl.jgltf.impl.v1.Buffer;
/*      */ import de.javagl.jgltf.impl.v1.BufferView;
/*      */ import de.javagl.jgltf.impl.v1.Camera;
/*      */ import de.javagl.jgltf.impl.v1.CameraOrthographic;
/*      */ import de.javagl.jgltf.impl.v1.CameraPerspective;
/*      */ import de.javagl.jgltf.impl.v1.GlTF;
/*      */ import de.javagl.jgltf.impl.v1.GlTFChildOfRootProperty;
/*      */ import de.javagl.jgltf.impl.v1.GlTFProperty;
/*      */ import de.javagl.jgltf.impl.v1.Image;
/*      */ import de.javagl.jgltf.impl.v1.Material;
/*      */ import de.javagl.jgltf.impl.v1.Mesh;
/*      */ import de.javagl.jgltf.impl.v1.MeshPrimitive;
/*      */ import de.javagl.jgltf.impl.v1.Node;
/*      */ import de.javagl.jgltf.impl.v1.Program;
/*      */ import de.javagl.jgltf.impl.v1.Sampler;
/*      */ import de.javagl.jgltf.impl.v1.Scene;
/*      */ import de.javagl.jgltf.impl.v1.Shader;
/*      */ import de.javagl.jgltf.impl.v1.Skin;
/*      */ import de.javagl.jgltf.impl.v1.Technique;
/*      */ import de.javagl.jgltf.impl.v1.TechniqueParameters;
/*      */ import de.javagl.jgltf.impl.v1.TechniqueStates;
/*      */ import de.javagl.jgltf.impl.v1.TechniqueStatesFunctions;
/*      */ import de.javagl.jgltf.impl.v1.Texture;
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
/*      */ import de.javagl.jgltf.model.gl.ProgramModel;
/*      */ import de.javagl.jgltf.model.gl.ShaderModel;
/*      */ import de.javagl.jgltf.model.gl.TechniqueModel;
/*      */ import de.javagl.jgltf.model.gl.TechniqueParametersModel;
/*      */ import de.javagl.jgltf.model.gl.TechniqueStatesFunctionsModel;
/*      */ import de.javagl.jgltf.model.gl.TechniqueStatesModel;
/*      */ import java.util.ArrayList;
/*      */ import java.util.Collection;
/*      */ import java.util.Collections;
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
/*      */ 
/*      */ 
/*      */ 
/*      */ public class GltfCreatorV1
/*      */ {
/*  115 */   private static final Logger logger = Logger.getLogger(GltfCreatorV1.class.getName());
/*      */   private final GltfModel gltfModel;
/*      */   private final Map<AccessorModel, String> accessorIds;
/*      */   private final Map<BufferModel, String> bufferIds;
/*      */   private final Map<BufferViewModel, String> bufferViewIds;
/*      */   private final Map<CameraModel, String> cameraIds;
/*      */   private final Map<ImageModel, String> imageIds;
/*      */   private final Map<MaterialModel, String> materialIds;
/*      */   
/*      */   public static GlTF create(GltfModel gltfModel) {
/*  125 */     GltfCreatorV1 creator = new GltfCreatorV1(gltfModel);
/*  126 */     return creator.create();
/*      */   }
/*      */   
/*      */   private final Map<MeshModel, String> meshIds;
/*      */   private final Map<NodeModel, String> nodeIds;
/*      */   private final Map<ProgramModel, String> programIds;
/*      */   private final Map<ShaderModel, String> shaderIds;
/*      */   private final Map<SkinModel, String> skinIds;
/*      */   private final Map<TechniqueModel, String> techniqueIds;
/*      */   private final Map<TextureModel, String> textureIds;
/*      */   private final Map<SamplerInfo, String> samplerIds;
/*      */   
/*      */   private static class SamplerInfo {
/*      */     final Integer magFilter;
/*      */     final Integer minFilter;
/*      */     
/*      */     SamplerInfo(TextureModel textureModel) {
/*  143 */       this.magFilter = textureModel.getMagFilter();
/*  144 */       this.minFilter = textureModel.getMinFilter();
/*  145 */       this.wrapS = textureModel.getWrapS();
/*  146 */       this.wrapT = textureModel.getWrapT();
/*      */     }
/*      */     
/*      */     final Integer wrapS;
/*      */     
/*      */     public int hashCode() {
/*  152 */       return Objects.hash(new Object[] { this.magFilter, this.minFilter, this.wrapS, this.wrapT });
/*      */     }
/*      */     
/*      */     final Integer wrapT;
/*      */     
/*      */     public boolean equals(Object object) {
/*  158 */       if (this == object)
/*      */       {
/*  160 */         return true;
/*      */       }
/*  162 */       if (object == null)
/*      */       {
/*  164 */         return false;
/*      */       }
/*  166 */       if (getClass() != object.getClass())
/*      */       {
/*  168 */         return false;
/*      */       }
/*  170 */       SamplerInfo other = (SamplerInfo)object;
/*  171 */       if (!Objects.equals(this.magFilter, other.magFilter))
/*      */       {
/*  173 */         return false;
/*      */       }
/*  175 */       if (!Objects.equals(this.minFilter, other.minFilter))
/*      */       {
/*  177 */         return false;
/*      */       }
/*  179 */       if (!Objects.equals(this.wrapS, other.wrapS))
/*      */       {
/*  181 */         return false;
/*      */       }
/*  183 */       if (!Objects.equals(this.wrapT, other.wrapT))
/*      */       {
/*  185 */         return false;
/*      */       }
/*  187 */       return true;
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private GltfCreatorV1(GltfModel gltfModel) {
/*  273 */     this.gltfModel = Objects.<GltfModel>requireNonNull(gltfModel, "The gltfModel may not be null");
/*      */ 
/*      */     
/*  276 */     this.accessorIds = computeIdMap("accessor", gltfModel
/*  277 */         .getAccessorModels());
/*  278 */     this.bufferIds = computeIdMap("buffer", gltfModel
/*  279 */         .getBufferModels());
/*  280 */     this.bufferViewIds = computeIdMap("bufferView", gltfModel
/*  281 */         .getBufferViewModels());
/*  282 */     this.cameraIds = computeIdMap("camera", gltfModel
/*  283 */         .getCameraModels());
/*  284 */     this.imageIds = computeIdMap("image", gltfModel
/*  285 */         .getImageModels());
/*  286 */     this.materialIds = computeIdMap("material", gltfModel
/*  287 */         .getMaterialModels());
/*  288 */     this.meshIds = computeIdMap("mesh", gltfModel
/*  289 */         .getMeshModels());
/*  290 */     this.nodeIds = computeIdMap("node", gltfModel
/*  291 */         .getNodeModels());
/*  292 */     this.skinIds = computeIdMap("skin", gltfModel
/*  293 */         .getSkinModels());
/*  294 */     this.textureIds = computeIdMap("texture", gltfModel
/*  295 */         .getTextureModels());
/*      */     
/*  297 */     if (gltfModel instanceof GltfModelV1) {
/*      */       
/*  299 */       GltfModelV1 gltfModelV1 = (GltfModelV1)gltfModel;
/*  300 */       this.programIds = computeIdMap("program", gltfModelV1
/*  301 */           .getProgramModels());
/*  302 */       this.shaderIds = computeIdMap("shader", gltfModelV1
/*  303 */           .getShaderModels());
/*  304 */       this.techniqueIds = computeIdMap("technique", gltfModelV1
/*  305 */           .getTechniqueModels());
/*      */     }
/*      */     else {
/*      */       
/*  309 */       this.programIds = Collections.emptyMap();
/*  310 */       this.shaderIds = Collections.emptyMap();
/*  311 */       this.techniqueIds = Collections.emptyMap();
/*      */     } 
/*      */     
/*  314 */     this.samplerIds = createSamplerIds(gltfModel.getTextureModels());
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public GlTF create() {
/*  324 */     GlTF gltf = new GlTF();
/*  325 */     transferGltfPropertyElements((ModelElement)this.gltfModel, (GlTFProperty)gltf);
/*      */     
/*  327 */     gltf.setAccessors(map("accessor", this.gltfModel
/*  328 */           .getAccessorModels(), this::createAccessor));
/*      */     
/*  330 */     gltf.setAnimations(map("animation", this.gltfModel
/*  331 */           .getAnimationModels(), this::createAnimation));
/*      */     
/*  333 */     gltf.setBuffers(map("buffer", this.gltfModel
/*  334 */           .getBufferModels(), GltfCreatorV1::createBuffer));
/*      */     
/*  336 */     gltf.setBufferViews(map("bufferView", this.gltfModel
/*  337 */           .getBufferViewModels(), this::createBufferView));
/*      */     
/*  339 */     gltf.setCameras(map("camera", this.gltfModel
/*  340 */           .getCameraModels(), this::createCamera));
/*      */     
/*  342 */     gltf.setImages(map("image", this.gltfModel
/*  343 */           .getImageModels(), this::createImage));
/*      */     
/*  345 */     gltf.setMaterials(map("material", this.gltfModel
/*  346 */           .getMaterialModels(), this::createMaterial));
/*      */     
/*  348 */     gltf.setMeshes(map("mesh", this.gltfModel
/*  349 */           .getMeshModels(), this::createMesh));
/*      */     
/*  351 */     gltf.setNodes(map("node", this.gltfModel
/*  352 */           .getNodeModels(), this::createNode));
/*      */     
/*  354 */     gltf.setScenes(map("scene", this.gltfModel
/*  355 */           .getSceneModels(), this::createScene));
/*      */     
/*  357 */     gltf.setSkins(map("skin", this.gltfModel
/*  358 */           .getSkinModels(), this::createSkin));
/*      */ 
/*      */     
/*  361 */     gltf.setSamplers(createSamplers());
/*      */     
/*  363 */     gltf.setTextures(map("texture", this.gltfModel
/*  364 */           .getTextureModels(), this::createTexture));
/*      */ 
/*      */ 
/*      */     
/*  368 */     if (this.gltfModel instanceof GltfModelV1) {
/*      */       
/*  370 */       GltfModelV1 gltfModelV1 = (GltfModelV1)this.gltfModel;
/*  371 */       gltf.setPrograms(map("program", gltfModelV1
/*  372 */             .getProgramModels(), this::createProgram));
/*      */       
/*  374 */       gltf.setShaders(map("shader", gltfModelV1
/*  375 */             .getShaderModels(), this::createShader));
/*      */       
/*  377 */       gltf.setTechniques(map("technique", gltfModelV1
/*  378 */             .getTechniqueModels(), this::createTechnique));
/*      */     } 
/*      */ 
/*      */     
/*  382 */     if (gltf.getScenes() != null && !gltf.getScenes().isEmpty())
/*      */     {
/*  384 */       gltf.setScene(gltf.getScenes().keySet().iterator().next());
/*      */     }
/*      */     
/*  387 */     ExtensionsModel extensionsModel = this.gltfModel.getExtensionsModel();
/*  388 */     List<String> extensionsUsed = extensionsModel.getExtensionsUsed();
/*  389 */     if (!extensionsUsed.isEmpty())
/*      */     {
/*  391 */       gltf.setExtensionsUsed(extensionsUsed);
/*      */     }
/*      */     
/*  394 */     Asset asset = createAsset(this.gltfModel.getAssetModel());
/*  395 */     gltf.setAsset(asset);
/*      */     
/*  397 */     return gltf;
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
/*  409 */     String bufferViewId = this.bufferViewIds.get(accessorModel.getBufferViewModel());
/*  410 */     return createAccessor(accessorModel, bufferViewId);
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
/*      */   public static Accessor createAccessor(AccessorModel accessorModel, String bufferViewId) {
/*  424 */     Accessor accessor = new Accessor();
/*  425 */     transferGltfChildOfRootPropertyElements((NamedModelElement)accessorModel, (GlTFChildOfRootProperty)accessor);
/*      */     
/*  427 */     accessor.setBufferView(bufferViewId);
/*      */     
/*  429 */     accessor.setByteOffset(Integer.valueOf(accessorModel.getByteOffset()));
/*  430 */     accessor.setComponentType(Integer.valueOf(accessorModel.getComponentType()));
/*  431 */     accessor.setCount(Integer.valueOf(accessorModel.getCount()));
/*  432 */     accessor.setType(accessorModel.getElementType().toString());
/*  433 */     accessor.setByteStride(Integer.valueOf(accessorModel.getByteStride()));
/*      */     
/*  435 */     AccessorData accessorData = accessorModel.getAccessorData();
/*  436 */     accessor.setMax(AccessorDatas.computeMax(accessorData));
/*  437 */     accessor.setMin(AccessorDatas.computeMin(accessorData));
/*      */     
/*  439 */     return accessor;
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
/*  450 */     Animation animation = new Animation();
/*  451 */     transferGltfChildOfRootPropertyElements((NamedModelElement)animationModel, (GlTFChildOfRootProperty)animation);
/*      */     
/*  453 */     Map<AnimationModel.Sampler, String> samplers = new LinkedHashMap<>();
/*  454 */     List<AnimationModel.Channel> channels = animationModel.getChannels();
/*  455 */     int counter = 0;
/*  456 */     for (AnimationModel.Channel channel : channels) {
/*      */       
/*  458 */       String id = "sampler_" + counter;
/*  459 */       samplers.put(channel.getSampler(), id);
/*  460 */       counter++;
/*      */     } 
/*      */     
/*  463 */     List<AnimationChannel> animationChannels = new ArrayList<>();
/*      */     
/*  465 */     for (AnimationModel.Channel channel : channels) {
/*      */       
/*  467 */       AnimationChannel animationChannel = new AnimationChannel();
/*      */       
/*  469 */       AnimationChannelTarget target = new AnimationChannelTarget();
/*  470 */       NodeModel nodeModel = channel.getNodeModel();
/*  471 */       target.setId(this.nodeIds.get(nodeModel));
/*  472 */       target.setPath(channel.getPath());
/*  473 */       animationChannel.setTarget(target);
/*      */       
/*  475 */       AnimationModel.Sampler sampler = channel.getSampler();
/*  476 */       animationChannel.setSampler(samplers.get(sampler));
/*      */       
/*  478 */       animationChannels.add(animationChannel);
/*      */     } 
/*  480 */     animation.setChannels(animationChannels);
/*      */     
/*  482 */     Map<String, AnimationSampler> animationSamplers = new LinkedHashMap<>();
/*      */     
/*  484 */     for (AnimationModel.Sampler sampler : samplers.keySet()) {
/*      */       
/*  486 */       AnimationSampler animationSampler = new AnimationSampler();
/*  487 */       animationSampler.setInput(this.accessorIds
/*  488 */           .get(sampler.getInput()));
/*  489 */       animationSampler.setInterpolation(sampler
/*  490 */           .getInterpolation().name());
/*  491 */       animationSampler.setOutput(this.accessorIds
/*  492 */           .get(sampler.getOutput()));
/*  493 */       String key = samplers.get(sampler);
/*  494 */       animationSamplers.put(key, animationSampler);
/*      */     } 
/*  496 */     animation.setSamplers(animationSamplers);
/*      */     
/*  498 */     return animation;
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
/*  510 */     Buffer buffer = new Buffer();
/*  511 */     transferGltfChildOfRootPropertyElements((NamedModelElement)bufferModel, (GlTFChildOfRootProperty)buffer);
/*      */     
/*  513 */     buffer.setUri(bufferModel.getUri());
/*  514 */     buffer.setByteLength(Integer.valueOf(bufferModel.getByteLength()));
/*  515 */     return buffer;
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
/*  527 */     String bufferId = this.bufferIds.get(bufferViewModel.getBufferModel());
/*  528 */     return createBufferView(bufferViewModel, bufferId);
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
/*      */   public static BufferView createBufferView(BufferViewModel bufferViewModel, String bufferId) {
/*  542 */     BufferView bufferView = new BufferView();
/*  543 */     transferGltfChildOfRootPropertyElements((NamedModelElement)bufferViewModel, (GlTFChildOfRootProperty)bufferView);
/*      */     
/*  545 */     bufferView.setBuffer(bufferId);
/*  546 */     bufferView.setByteOffset(Integer.valueOf(bufferViewModel.getByteOffset()));
/*  547 */     bufferView.setByteLength(Integer.valueOf(bufferViewModel.getByteLength()));
/*  548 */     bufferView.setTarget(bufferViewModel.getTarget());
/*      */     
/*  550 */     return bufferView;
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
/*  562 */     Camera camera = new Camera();
/*  563 */     transferGltfChildOfRootPropertyElements((NamedModelElement)cameraModel, (GlTFChildOfRootProperty)camera);
/*      */ 
/*      */     
/*  566 */     CameraPerspectiveModel cameraPerspectiveModel = cameraModel.getCameraPerspectiveModel();
/*      */     
/*  568 */     CameraOrthographicModel cameraOrthographicModel = cameraModel.getCameraOrthographicModel();
/*  569 */     if (cameraPerspectiveModel != null) {
/*      */       
/*  571 */       CameraPerspective cameraPerspective = new CameraPerspective();
/*  572 */       cameraPerspective.setAspectRatio(cameraPerspectiveModel
/*  573 */           .getAspectRatio());
/*  574 */       cameraPerspective.setYfov(cameraPerspectiveModel
/*  575 */           .getYfov());
/*  576 */       cameraPerspective.setZfar(cameraPerspectiveModel
/*  577 */           .getZfar());
/*  578 */       cameraPerspective.setZnear(cameraPerspectiveModel
/*  579 */           .getZnear());
/*  580 */       camera.setPerspective(cameraPerspective);
/*  581 */       camera.setType("perspective");
/*      */     }
/*  583 */     else if (cameraOrthographicModel != null) {
/*      */       
/*  585 */       CameraOrthographic cameraOrthographic = new CameraOrthographic();
/*  586 */       cameraOrthographic.setXmag(cameraOrthographicModel
/*  587 */           .getXmag());
/*  588 */       cameraOrthographic.setYmag(cameraOrthographicModel
/*  589 */           .getYmag());
/*  590 */       cameraOrthographic.setZfar(cameraOrthographicModel
/*  591 */           .getZfar());
/*  592 */       cameraOrthographic.setZnear(cameraOrthographicModel
/*  593 */           .getZnear());
/*  594 */       camera.setOrthographic(cameraOrthographic);
/*  595 */       camera.setType("orthographic");
/*      */     }
/*      */     else {
/*      */       
/*  599 */       logger.severe("Camera is neither perspective nor orthographic");
/*      */     } 
/*  601 */     return camera;
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
/*  612 */     Image image = new Image();
/*  613 */     transferGltfChildOfRootPropertyElements((NamedModelElement)imageModel, (GlTFChildOfRootProperty)image);
/*      */ 
/*      */     
/*  616 */     String bufferView = this.bufferViewIds.get(imageModel.getBufferViewModel());
/*  617 */     if (bufferView != null)
/*      */     {
/*  619 */       logger.severe("Images with BufferView are not supported in glTF 1.0");
/*      */     }
/*      */     
/*  622 */     image.setUri(imageModel.getUri());
/*      */     
/*  624 */     return image;
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
/*  637 */     if (materialModel instanceof MaterialModelV1) {
/*      */       
/*  639 */       MaterialModelV1 materialModelV1 = (MaterialModelV1)materialModel;
/*  640 */       return createMaterialV1(materialModelV1);
/*      */     } 
/*      */     
/*  643 */     logger.severe("Cannot store glTF 2.0 material in glTF 1.0");
/*  644 */     return null;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private Material createMaterialV1(MaterialModelV1 materialModel) {
/*  655 */     Material material = new Material();
/*  656 */     transferGltfChildOfRootPropertyElements((NamedModelElement)materialModel, (GlTFChildOfRootProperty)material);
/*      */     
/*  658 */     TechniqueModel techniqueModel = materialModel.getTechniqueModel();
/*  659 */     material.setTechnique(this.techniqueIds.get(techniqueModel));
/*      */     
/*  661 */     Map<String, Object> modelValues = materialModel.getValues();
/*  662 */     Map<String, Object> values = new LinkedHashMap<>();
/*  663 */     for (Map.Entry<String, Object> valueEntry : modelValues.entrySet()) {
/*      */       
/*  665 */       String parameterName = valueEntry.getKey();
/*  666 */       Object value = valueEntry.getValue();
/*  667 */       if (value instanceof TextureModel) {
/*      */         
/*  669 */         TextureModel textureModel = (TextureModel)value;
/*  670 */         String textureId = this.textureIds.get(textureModel);
/*  671 */         values.put(parameterName, textureId);
/*      */         
/*      */         continue;
/*      */       } 
/*  675 */       values.put(parameterName, value);
/*      */     } 
/*      */     
/*  678 */     material.setValues(values);
/*  679 */     return material;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private Program createProgram(ProgramModel programModel) {
/*  690 */     Program program = new Program();
/*  691 */     transferGltfChildOfRootPropertyElements((NamedModelElement)programModel, (GlTFChildOfRootProperty)program);
/*      */ 
/*      */     
/*  694 */     ShaderModel vertexShaderModel = programModel.getVertexShaderModel();
/*  695 */     program.setVertexShader(this.shaderIds.get(vertexShaderModel));
/*      */ 
/*      */     
/*  698 */     ShaderModel fragmentShaderModel = programModel.getFragmentShaderModel();
/*  699 */     program.setFragmentShader(this.shaderIds.get(fragmentShaderModel));
/*      */     
/*  701 */     List<String> modelAttributes = programModel.getAttributes();
/*  702 */     if (!modelAttributes.isEmpty()) {
/*      */       
/*  704 */       List<String> attributes = new ArrayList<>(modelAttributes);
/*  705 */       program.setAttributes(attributes);
/*      */     } 
/*  707 */     return program;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private Shader createShader(ShaderModel shaderModel) {
/*  718 */     Shader shader = new Shader();
/*  719 */     transferGltfChildOfRootPropertyElements((NamedModelElement)shaderModel, (GlTFChildOfRootProperty)shader);
/*      */     
/*  721 */     ShaderModel.ShaderType shaderType = shaderModel.getShaderType();
/*  722 */     if (shaderType == ShaderModel.ShaderType.VERTEX_SHADER) {
/*      */       
/*  724 */       shader.setType(Integer.valueOf(35633));
/*      */     }
/*  726 */     else if (shaderType == ShaderModel.ShaderType.FRAGMENT_SHADER) {
/*      */       
/*  728 */       shader.setType(Integer.valueOf(35632));
/*      */     }
/*      */     else {
/*      */       
/*  732 */       logger.severe("Invalid shader type: " + shaderType);
/*      */     } 
/*  734 */     shader.setUri(shaderModel.getUri());
/*  735 */     return shader;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private Technique createTechnique(TechniqueModel techniqueModel) {
/*  746 */     Technique technique = new Technique();
/*  747 */     transferGltfChildOfRootPropertyElements((NamedModelElement)techniqueModel, (GlTFChildOfRootProperty)technique);
/*      */     
/*  749 */     ProgramModel programModel = techniqueModel.getProgramModel();
/*  750 */     technique.setProgram(this.programIds.get(programModel));
/*      */     
/*  752 */     Map<String, String> uniforms = techniqueModel.getUniforms();
/*  753 */     technique.setUniforms(new LinkedHashMap<>(uniforms));
/*      */     
/*  755 */     Map<String, String> attributes = techniqueModel.getAttributes();
/*  756 */     technique.setAttributes(new LinkedHashMap<>(attributes));
/*      */ 
/*      */     
/*  759 */     Map<String, TechniqueParametersModel> parametersModel = techniqueModel.getParameters();
/*  760 */     if (!parametersModel.isEmpty()) {
/*      */       
/*  762 */       Map<String, TechniqueParameters> parameters = new LinkedHashMap<>();
/*      */ 
/*      */ 
/*      */       
/*  766 */       for (Map.Entry<String, TechniqueParametersModel> entry : parametersModel.entrySet()) {
/*      */         
/*  768 */         String key = entry.getKey();
/*      */         
/*  770 */         TechniqueParametersModel techniqueParametersModel = entry.getValue();
/*      */ 
/*      */         
/*  773 */         TechniqueParameters techniqueParameters = createTechniqueParameters(techniqueParametersModel);
/*      */         
/*  775 */         parameters.put(key, techniqueParameters);
/*      */       } 
/*  777 */       technique.setParameters(parameters);
/*      */     } 
/*  779 */     technique.setStates(createTechniqueStates(techniqueModel
/*  780 */           .getTechniqueStatesModel()));
/*      */     
/*  782 */     return technique;
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
/*      */   private TechniqueParameters createTechniqueParameters(TechniqueParametersModel techniqueParametersModel) {
/*  795 */     TechniqueParameters techniqueParameters = new TechniqueParameters();
/*      */     
/*  797 */     techniqueParameters.setSemantic(techniqueParametersModel.getSemantic());
/*  798 */     techniqueParameters.setType(Integer.valueOf(techniqueParametersModel.getType()));
/*  799 */     techniqueParameters.setCount(Integer.valueOf(techniqueParametersModel.getCount()));
/*  800 */     techniqueParameters.setValue(techniqueParametersModel.getValue());
/*      */     
/*  802 */     NodeModel nodeModel = techniqueParametersModel.getNodeModel();
/*  803 */     techniqueParameters.setNode(this.nodeIds.get(nodeModel));
/*      */     
/*  805 */     return techniqueParameters;
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
/*      */   private TechniqueStates createTechniqueStates(TechniqueStatesModel techniqueStatesModel) {
/*  818 */     if (techniqueStatesModel == null)
/*      */     {
/*  820 */       return null;
/*      */     }
/*  822 */     TechniqueStates techniqueStates = new TechniqueStates();
/*      */     
/*  824 */     List<Integer> enable = techniqueStatesModel.getEnable();
/*  825 */     if (enable != null)
/*      */     {
/*  827 */       techniqueStates.setEnable(new ArrayList<>(enable));
/*      */     }
/*      */     
/*  830 */     techniqueStates.setFunctions(createTechniqueStatesFunctions(techniqueStatesModel
/*  831 */           .getTechniqueStatesFunctionsModel()));
/*      */     
/*  833 */     return techniqueStates;
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
/*      */   private TechniqueStatesFunctions createTechniqueStatesFunctions(TechniqueStatesFunctionsModel techniqueStatesFunctionsModel) {
/*  847 */     if (techniqueStatesFunctionsModel == null)
/*      */     {
/*  849 */       return null;
/*      */     }
/*  851 */     TechniqueStatesFunctions techniqueStatesFunctions = new TechniqueStatesFunctions();
/*      */ 
/*      */     
/*  854 */     techniqueStatesFunctions.setBlendColor(Optionals.clone(techniqueStatesFunctionsModel
/*  855 */           .getBlendColor()));
/*  856 */     techniqueStatesFunctions.setBlendEquationSeparate(Optionals.clone(techniqueStatesFunctionsModel
/*  857 */           .getBlendEquationSeparate()));
/*  858 */     techniqueStatesFunctions.setBlendFuncSeparate(Optionals.clone(techniqueStatesFunctionsModel
/*  859 */           .getBlendFuncSeparate()));
/*  860 */     techniqueStatesFunctions.setColorMask(Optionals.clone(techniqueStatesFunctionsModel
/*  861 */           .getColorMask()));
/*  862 */     techniqueStatesFunctions.setCullFace(Optionals.clone(techniqueStatesFunctionsModel
/*  863 */           .getCullFace()));
/*  864 */     techniqueStatesFunctions.setDepthFunc(Optionals.clone(techniqueStatesFunctionsModel
/*  865 */           .getDepthFunc()));
/*  866 */     techniqueStatesFunctions.setDepthMask(Optionals.clone(techniqueStatesFunctionsModel
/*  867 */           .getDepthMask()));
/*  868 */     techniqueStatesFunctions.setDepthRange(Optionals.clone(techniqueStatesFunctionsModel
/*  869 */           .getDepthRange()));
/*  870 */     techniqueStatesFunctions.setFrontFace(Optionals.clone(techniqueStatesFunctionsModel
/*  871 */           .getFrontFace()));
/*  872 */     techniqueStatesFunctions.setLineWidth(Optionals.clone(techniqueStatesFunctionsModel
/*  873 */           .getLineWidth()));
/*  874 */     techniqueStatesFunctions.setPolygonOffset(Optionals.clone(techniqueStatesFunctionsModel
/*  875 */           .getPolygonOffset()));
/*      */     
/*  877 */     return techniqueStatesFunctions;
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
/*      */   private Mesh createMesh(MeshModel meshModel) {
/*  889 */     Mesh mesh = new Mesh();
/*  890 */     transferGltfChildOfRootPropertyElements((NamedModelElement)meshModel, (GlTFChildOfRootProperty)mesh);
/*      */     
/*  892 */     List<MeshPrimitive> meshPrimitives = new ArrayList<>();
/*      */     
/*  894 */     List<MeshPrimitiveModel> meshPrimitiveModels = meshModel.getMeshPrimitiveModels();
/*  895 */     for (MeshPrimitiveModel meshPrimitiveModel : meshPrimitiveModels) {
/*      */ 
/*      */       
/*  898 */       MeshPrimitive meshPrimitive = createMeshPrimitive(meshPrimitiveModel);
/*  899 */       meshPrimitives.add(meshPrimitive);
/*      */     } 
/*  901 */     mesh.setPrimitives(meshPrimitives);
/*      */     
/*  903 */     if (meshModel.getWeights() != null)
/*      */     {
/*  905 */       logger.severe("Morph target weights are not supported in glTF 1.0");
/*      */     }
/*  907 */     return mesh;
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
/*  919 */     MeshPrimitive meshPrimitive = new MeshPrimitive();
/*  920 */     transferGltfPropertyElements((ModelElement)meshPrimitiveModel, (GlTFProperty)meshPrimitive);
/*      */     
/*  922 */     meshPrimitive.setMode(Integer.valueOf(meshPrimitiveModel.getMode()));
/*      */     
/*  924 */     Map<String, String> attributes = resolveIds(meshPrimitiveModel
/*  925 */         .getAttributes(), this.accessorIds::get);
/*      */     
/*  927 */     meshPrimitive.setAttributes(attributes);
/*      */     
/*  929 */     AccessorModel Ids = meshPrimitiveModel.getIndices();
/*  930 */     meshPrimitive.setIndices(this.accessorIds.get(Ids));
/*      */ 
/*      */     
/*  933 */     List<Map<String, AccessorModel>> modelTargetsList = meshPrimitiveModel.getTargets();
/*  934 */     if (!modelTargetsList.isEmpty())
/*      */     {
/*  936 */       logger.severe("Morph targets are not supported in glTF 1.0");
/*      */     }
/*      */     
/*  939 */     String material = this.materialIds.get(meshPrimitiveModel
/*  940 */         .getMaterialModel());
/*  941 */     meshPrimitive.setMaterial(material);
/*      */     
/*  943 */     return meshPrimitive;
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
/*  954 */     Node node = new Node();
/*  955 */     transferGltfChildOfRootPropertyElements((NamedModelElement)nodeModel, (GlTFChildOfRootProperty)node);
/*      */     
/*  957 */     if (!nodeModel.getChildren().isEmpty())
/*      */     {
/*  959 */       node.setChildren(map(nodeModel
/*  960 */             .getChildren(), this.nodeIds::get));
/*      */     }
/*      */     
/*  963 */     node.setTranslation(Optionals.clone(nodeModel.getTranslation()));
/*  964 */     node.setRotation(Optionals.clone(nodeModel.getRotation()));
/*  965 */     node.setScale(Optionals.clone(nodeModel.getScale()));
/*  966 */     node.setMatrix(Optionals.clone(nodeModel.getMatrix()));
/*      */     
/*  968 */     String camera = this.cameraIds.get(nodeModel.getCameraModel());
/*  969 */     node.setCamera(camera);
/*      */     
/*  971 */     String skin = this.skinIds.get(nodeModel.getSkinModel());
/*  972 */     node.setSkin(skin);
/*      */     
/*  974 */     if (nodeModel.getWeights() != null)
/*      */     {
/*  976 */       logger.severe("Morph target weights are not supported in glTF 1.0");
/*      */     }
/*      */     
/*  979 */     List<MeshModel> nodeMeshModels = nodeModel.getMeshModels();
/*  980 */     if (!nodeMeshModels.isEmpty()) {
/*      */       
/*  982 */       List<String> meshes = new ArrayList<>();
/*  983 */       for (MeshModel meshModel : nodeMeshModels) {
/*      */         
/*  985 */         String id = this.meshIds.get(meshModel);
/*  986 */         meshes.add(id);
/*      */       } 
/*  988 */       node.setMeshes(meshes);
/*      */     } 
/*  990 */     return node;
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
/* 1001 */     Scene scene = new Scene();
/* 1002 */     transferGltfChildOfRootPropertyElements((NamedModelElement)sceneModel, (GlTFChildOfRootProperty)scene);
/*      */     
/* 1004 */     scene.setNodes(map(sceneModel
/* 1005 */           .getNodeModels(), this.nodeIds::get));
/* 1006 */     return scene;
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
/* 1017 */     Skin skin = new Skin();
/* 1018 */     transferGltfChildOfRootPropertyElements((NamedModelElement)skinModel, (GlTFChildOfRootProperty)skin);
/*      */ 
/*      */     
/* 1021 */     String inverseBindMatrices = this.accessorIds.get(skinModel.getInverseBindMatrices());
/* 1022 */     skin.setInverseBindMatrices(inverseBindMatrices);
/*      */ 
/*      */     
/* 1025 */     logger.severe("Skins are not yet fully supported");
/*      */     
/* 1027 */     return skin;
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
/*      */   private Map<SamplerInfo, String> createSamplerIds(List<TextureModel> textureModels) {
/* 1041 */     Map<SamplerInfo, String> samplerIndices = new LinkedHashMap<>();
/*      */     
/* 1043 */     for (TextureModel textureModel : textureModels) {
/*      */       
/* 1045 */       SamplerInfo samplerInfo = new SamplerInfo(textureModel);
/* 1046 */       if (!samplerIndices.containsKey(samplerInfo))
/*      */       {
/* 1048 */         samplerIndices.put(samplerInfo, "sampler_" + samplerIndices
/* 1049 */             .size());
/*      */       }
/*      */     } 
/* 1052 */     return samplerIndices;
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
/*      */   private Map<String, Sampler> createSamplers() {
/* 1064 */     if (this.samplerIds.isEmpty())
/*      */     {
/* 1066 */       return null;
/*      */     }
/* 1068 */     Map<String, Sampler> samplers = new LinkedHashMap<>();
/*      */     
/* 1070 */     for (SamplerInfo samplerInfo : this.samplerIds.keySet()) {
/*      */ 
/*      */       
/* 1073 */       Sampler sampler = createSampler(samplerInfo);
/* 1074 */       String key = this.samplerIds.get(samplerInfo);
/* 1075 */       samplers.put(key, sampler);
/*      */     } 
/* 1077 */     return samplers;
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
/* 1090 */     Sampler sampler = new Sampler();
/*      */     
/* 1092 */     sampler.setMagFilter(samplerInfo.magFilter);
/* 1093 */     sampler.setMinFilter(samplerInfo.minFilter);
/* 1094 */     sampler.setWrapS(samplerInfo.wrapS);
/* 1095 */     sampler.setWrapT(samplerInfo.wrapT);
/* 1096 */     return sampler;
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
/* 1108 */     Texture texture = new Texture();
/* 1109 */     transferGltfChildOfRootPropertyElements((NamedModelElement)textureModel, (GlTFChildOfRootProperty)texture);
/*      */     
/* 1111 */     SamplerInfo samplerInfo = new SamplerInfo(textureModel);
/* 1112 */     String id = this.samplerIds.get(samplerInfo);
/* 1113 */     texture.setSampler(id);
/*      */     
/* 1115 */     texture.setSource(this.imageIds.get(textureModel.getImageModel()));
/*      */     
/* 1117 */     return texture;
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
/* 1128 */     Asset asset = new Asset();
/* 1129 */     asset.setVersion("1.0");
/* 1130 */     asset.setGenerator("JglTF from https://github.com/javagl/JglTF");
/*      */     
/* 1132 */     transferGltfPropertyElements((ModelElement)assetModel, (GlTFProperty)asset);
/*      */     
/* 1134 */     if (assetModel.getCopyright() != null)
/*      */     {
/* 1136 */       asset.setCopyright(assetModel.getCopyright());
/*      */     }
/* 1138 */     if (assetModel.getGenerator() != null)
/*      */     {
/* 1140 */       asset.setGenerator(assetModel.getGenerator());
/*      */     }
/* 1142 */     return asset;
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
/* 1155 */     property.setExtensions(modelElement.getExtensions());
/* 1156 */     property.setExtras(modelElement.getExtras());
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
/* 1170 */     property.setName(modelElement.getName());
/* 1171 */     transferGltfPropertyElements((ModelElement)modelElement, (GlTFProperty)property);
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
/*      */   private static <T, U> Map<String, U> map(String prefix, Collection<? extends T> elements, Function<? super T, ? extends U> mapper) {
/* 1190 */     return map(prefix, map(elements, mapper));
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
/*      */   private static <T> Map<String, T> map(String prefix, Collection<? extends T> elements) {
/* 1205 */     if (elements == null || elements.isEmpty())
/*      */     {
/* 1207 */       return null;
/*      */     }
/* 1209 */     Map<String, T> map = new LinkedHashMap<>();
/* 1210 */     int index = 0;
/* 1211 */     for (T element : elements) {
/*      */       
/* 1213 */       map.put(prefix + "_" + index, element);
/* 1214 */       index++;
/*      */     } 
/* 1216 */     return map;
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
/* 1232 */     if (collection.isEmpty())
/*      */     {
/* 1234 */       return null;
/*      */     }
/* 1236 */     return (List<U>)collection.stream().<U>map(mapper).collect(Collectors.toList());
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
/*      */   private static <K, T> Map<K, String> resolveIds(Map<K, ? extends T> map, Function<? super T, String> idLookup) {
/* 1252 */     Map<K, String> result = new LinkedHashMap<>();
/* 1253 */     for (Map.Entry<K, ? extends T> entry : map.entrySet()) {
/*      */       
/* 1255 */       K key = entry.getKey();
/* 1256 */       T value = entry.getValue();
/* 1257 */       String id = idLookup.apply(value);
/* 1258 */       result.put(key, id);
/*      */     } 
/* 1260 */     return result;
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
/*      */   private static <T> Map<T, String> computeIdMap(String prefix, Collection<? extends T> elements) {
/* 1274 */     Map<T, String> ids = new LinkedHashMap<>();
/* 1275 */     int index = 0;
/* 1276 */     for (T element : elements) {
/*      */       
/* 1278 */       ids.put(element, prefix + "_" + index);
/* 1279 */       index++;
/*      */     } 
/* 1281 */     return ids;
/*      */   }
/*      */ }


/* Location:              C:\Users\DuduM\OneDrive\Área de Trabalho\mw10\modularwarfare-shining-2023.2.4.4f-fix10.jar!\de\javagl\jgltf\model\v1\GltfCreatorV1.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */