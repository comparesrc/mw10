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
/*      */ import de.javagl.jgltf.model.AccessorDatas;
/*      */ import de.javagl.jgltf.model.AccessorModel;
/*      */ import de.javagl.jgltf.model.Accessors;
/*      */ import de.javagl.jgltf.model.AnimationModel;
/*      */ import de.javagl.jgltf.model.BufferModel;
/*      */ import de.javagl.jgltf.model.BufferViewModel;
/*      */ import de.javagl.jgltf.model.CameraModel;
/*      */ import de.javagl.jgltf.model.CameraOrthographicModel;
/*      */ import de.javagl.jgltf.model.CameraPerspectiveModel;
/*      */ import de.javagl.jgltf.model.ElementType;
/*      */ import de.javagl.jgltf.model.ImageModel;
/*      */ import de.javagl.jgltf.model.MaterialModel;
/*      */ import de.javagl.jgltf.model.MeshModel;
/*      */ import de.javagl.jgltf.model.MeshPrimitiveModel;
/*      */ import de.javagl.jgltf.model.NodeModel;
/*      */ import de.javagl.jgltf.model.Optionals;
/*      */ import de.javagl.jgltf.model.SkinModel;
/*      */ import de.javagl.jgltf.model.TextureModel;
/*      */ import de.javagl.jgltf.model.gl.ProgramModel;
/*      */ import de.javagl.jgltf.model.gl.ShaderModel;
/*      */ import de.javagl.jgltf.model.gl.TechniqueModel;
/*      */ import de.javagl.jgltf.model.gl.TechniqueParametersModel;
/*      */ import de.javagl.jgltf.model.gl.TechniqueStatesFunctionsModel;
/*      */ import de.javagl.jgltf.model.gl.TechniqueStatesModel;
/*      */ import de.javagl.jgltf.model.gl.impl.DefaultProgramModel;
/*      */ import de.javagl.jgltf.model.gl.impl.DefaultShaderModel;
/*      */ import de.javagl.jgltf.model.gl.impl.DefaultTechniqueModel;
/*      */ import de.javagl.jgltf.model.gl.impl.DefaultTechniqueParametersModel;
/*      */ import de.javagl.jgltf.model.gl.impl.DefaultTechniqueStatesFunctionsModel;
/*      */ import de.javagl.jgltf.model.gl.impl.DefaultTechniqueStatesModel;
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
/*      */ import de.javagl.jgltf.model.io.v1.GltfAssetV1;
/*      */ import de.javagl.jgltf.model.v1.gl.DefaultModels;
/*      */ import de.javagl.jgltf.model.v1.gl.GltfDefaults;
/*      */ import de.javagl.jgltf.model.v1.gl.TechniqueStatesFunctionsModels;
/*      */ import java.nio.ByteBuffer;
/*      */ import java.util.ArrayList;
/*      */ import java.util.LinkedHashMap;
/*      */ import java.util.List;
/*      */ import java.util.Map;
/*      */ import java.util.Objects;
/*      */ import java.util.function.Function;
/*      */ import java.util.function.IntFunction;
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
/*      */ public class GltfModelCreatorV1
/*      */ {
/*  143 */   private static final Logger logger = Logger.getLogger(GltfModelCreatorV1.class.getName());
/*      */   
/*      */   private final IndexMappingSet indexMappingSet;
/*      */   
/*      */   private final GltfAsset gltfAsset;
/*      */   
/*      */   private final GlTF gltf;
/*      */   private final GltfModelV1 gltfModel;
/*      */   
/*      */   public static GltfModelV1 create(GltfAssetV1 gltfAsset) {
/*  153 */     GltfModelV1 gltfModel = new GltfModelV1();
/*  154 */     GltfModelCreatorV1 creator = new GltfModelCreatorV1(gltfAsset, gltfModel);
/*      */     
/*  156 */     creator.create();
/*  157 */     return gltfModel;
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
/*      */   GltfModelCreatorV1(GltfAssetV1 gltfAsset, GltfModelV1 gltfModel) {
/*  189 */     this.gltfAsset = (GltfAsset)Objects.requireNonNull(gltfAsset, "The gltfAsset may not be null");
/*      */     
/*  191 */     this.gltf = gltfAsset.getGltf();
/*  192 */     this.gltfModel = Objects.<GltfModelV1>requireNonNull(gltfModel, "The gltfModel may not be null");
/*      */     
/*  194 */     this.indexMappingSet = IndexMappingSets.create(gltfAsset.getGltf());
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   void create() {
/*  202 */     transferGltfPropertyElements((GlTFProperty)this.gltf, (AbstractModelElement)this.gltfModel);
/*      */     
/*  204 */     createAccessorModels();
/*  205 */     createAnimationModels();
/*  206 */     createBufferModels();
/*  207 */     createBufferViewModels();
/*  208 */     createCameraModels();
/*  209 */     createImageModels();
/*  210 */     createMaterialModels();
/*  211 */     createMeshModels();
/*  212 */     createNodeModels();
/*  213 */     createSceneModels();
/*  214 */     createSkinModels();
/*  215 */     createTextureModels();
/*  216 */     createShaderModels();
/*  217 */     createProgramModels();
/*  218 */     createTechniqueModels();
/*      */     
/*  220 */     initBufferModels();
/*  221 */     initBufferViewModels();
/*      */     
/*  223 */     initAccessorModels();
/*      */     
/*  225 */     assignBufferViewByteStrides();
/*      */     
/*  227 */     initAnimationModels();
/*  228 */     initImageModels();
/*  229 */     initTechniqueModels();
/*  230 */     initMaterialModels();
/*  231 */     initMeshModels();
/*  232 */     initNodeModels();
/*  233 */     initSceneModels();
/*  234 */     initSkinModels();
/*  235 */     initTextureModels();
/*  236 */     initShaderModels();
/*  237 */     initProgramModels();
/*      */     
/*  239 */     initExtensionsModel();
/*  240 */     initAssetModel();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void createAccessorModels() {
/*  248 */     Map<String, Accessor> accessors = Optionals.of(this.gltf.getAccessors());
/*  249 */     for (Accessor accessor : accessors.values()) {
/*      */       
/*  251 */       DefaultAccessorModel accessorModel = createAccessorModel(accessor);
/*  252 */       this.gltfModel.addAccessorModel(accessorModel);
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
/*      */   private static DefaultAccessorModel createAccessorModel(Accessor accessor) {
/*  264 */     Integer componentType = accessor.getComponentType();
/*  265 */     Integer byteOffset = accessor.getByteOffset();
/*  266 */     Integer count = accessor.getCount();
/*  267 */     ElementType elementType = ElementType.forString(accessor.getType());
/*  268 */     Integer byteStride = accessor.getByteStride();
/*  269 */     if (byteStride == null)
/*      */     {
/*  271 */       byteStride = Integer.valueOf(elementType.getNumComponents() * 
/*  272 */           Accessors.getNumBytesForAccessorComponentType(componentType
/*  273 */             .intValue()));
/*      */     }
/*      */     
/*  276 */     DefaultAccessorModel accessorModel = new DefaultAccessorModel(componentType.intValue(), count.intValue(), elementType);
/*  277 */     accessorModel.setByteOffset(byteOffset.intValue());
/*  278 */     accessorModel.setByteStride(byteStride.intValue());
/*  279 */     return accessorModel;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void createAnimationModels() {
/*  287 */     Map<String, Animation> animations = Optionals.of(this.gltf.getAnimations());
/*  288 */     for (int i = 0; i < animations.size(); i++)
/*      */     {
/*  290 */       this.gltfModel.addAnimationModel(new DefaultAnimationModel());
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void createBufferModels() {
/*  299 */     Map<String, Buffer> buffers = Optionals.of(this.gltf.getBuffers());
/*  300 */     for (Buffer buffer : buffers.values()) {
/*      */       
/*  302 */       DefaultBufferModel bufferModel = new DefaultBufferModel();
/*  303 */       bufferModel.setUri(buffer.getUri());
/*  304 */       this.gltfModel.addBufferModel(bufferModel);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void createBufferViewModels() {
/*  314 */     Map<String, BufferView> bufferViews = Optionals.of(this.gltf.getBufferViews());
/*  315 */     for (BufferView bufferView : bufferViews.values()) {
/*      */ 
/*      */       
/*  318 */       DefaultBufferViewModel bufferViewModel = createBufferViewModel(bufferView);
/*  319 */       this.gltfModel.addBufferViewModel(bufferViewModel);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void createCameraModels() {
/*  329 */     Map<String, Camera> cameras = Optionals.of(this.gltf.getCameras());
/*  330 */     for (Camera camera : cameras.values()) {
/*      */       
/*  332 */       String type = camera.getType();
/*  333 */       if ("perspective".equals(type)) {
/*      */         
/*  335 */         CameraPerspective cameraPerspective = camera.getPerspective();
/*  336 */         DefaultCameraPerspectiveModel cameraPerspectiveModel = new DefaultCameraPerspectiveModel();
/*      */         
/*  338 */         cameraPerspectiveModel.setAspectRatio(cameraPerspective
/*  339 */             .getAspectRatio());
/*  340 */         cameraPerspectiveModel.setYfov(cameraPerspective
/*  341 */             .getYfov());
/*  342 */         cameraPerspectiveModel.setZfar(cameraPerspective
/*  343 */             .getZfar());
/*  344 */         cameraPerspectiveModel.setZnear(cameraPerspective
/*  345 */             .getZnear());
/*  346 */         DefaultCameraModel cameraModel = new DefaultCameraModel();
/*      */         
/*  348 */         cameraModel.setCameraPerspectiveModel((CameraPerspectiveModel)cameraPerspectiveModel);
/*  349 */         this.gltfModel.addCameraModel(cameraModel); continue;
/*      */       } 
/*  351 */       if ("orthographic".equals(type)) {
/*      */ 
/*      */         
/*  354 */         CameraOrthographic cameraOrthographic = camera.getOrthographic();
/*  355 */         DefaultCameraOrthographicModel cameraOrthographicModel = new DefaultCameraOrthographicModel();
/*      */         
/*  357 */         cameraOrthographicModel.setXmag(cameraOrthographic
/*  358 */             .getXmag());
/*  359 */         cameraOrthographicModel.setYmag(cameraOrthographic
/*  360 */             .getYmag());
/*  361 */         cameraOrthographicModel.setZfar(cameraOrthographic
/*  362 */             .getZfar());
/*  363 */         cameraOrthographicModel.setZnear(cameraOrthographic
/*  364 */             .getZnear());
/*  365 */         DefaultCameraModel cameraModel = new DefaultCameraModel();
/*      */         
/*  367 */         cameraModel.setCameraOrthographicModel((CameraOrthographicModel)cameraOrthographicModel);
/*  368 */         this.gltfModel.addCameraModel(cameraModel);
/*      */         
/*      */         continue;
/*      */       } 
/*  372 */       logger.severe("Invalid camera type: " + type);
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
/*      */   private static DefaultBufferViewModel createBufferViewModel(BufferView bufferView) {
/*  386 */     int byteOffset = bufferView.getByteOffset().intValue();
/*  387 */     Integer byteLength = bufferView.getByteLength();
/*  388 */     if (byteLength == null) {
/*      */       
/*  390 */       logger.warning("No byteLength found in BufferView");
/*  391 */       byteLength = Integer.valueOf(0);
/*      */     } 
/*  393 */     Integer target = bufferView.getTarget();
/*  394 */     DefaultBufferViewModel bufferViewModel = new DefaultBufferViewModel(target);
/*      */     
/*  396 */     bufferViewModel.setByteOffset(byteOffset);
/*  397 */     bufferViewModel.setByteLength(byteLength.intValue());
/*  398 */     return bufferViewModel;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void createImageModels() {
/*  407 */     Map<String, Image> images = Optionals.of(this.gltf.getImages());
/*  408 */     for (Image image : images.values()) {
/*      */       
/*  410 */       DefaultImageModel imageModel = new DefaultImageModel();
/*      */       
/*  412 */       String uri = image.getUri();
/*  413 */       imageModel.setUri(uri);
/*  414 */       this.gltfModel.addImageModel(imageModel);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void createMaterialModels() {
/*  423 */     Map<String, Material> materials = Optionals.of(this.gltf.getMaterials());
/*  424 */     for (int i = 0; i < materials.size(); i++)
/*      */     {
/*  426 */       this.gltfModel.addMaterialModel(new MaterialModelV1());
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void createMeshModels() {
/*  435 */     Map<String, Mesh> meshes = Optionals.of(this.gltf.getMeshes());
/*  436 */     for (int i = 0; i < meshes.size(); i++)
/*      */     {
/*  438 */       this.gltfModel.addMeshModel(new DefaultMeshModel());
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void createNodeModels() {
/*  447 */     Map<String, Node> nodes = Optionals.of(this.gltf.getNodes());
/*  448 */     for (int i = 0; i < nodes.size(); i++)
/*      */     {
/*  450 */       this.gltfModel.addNodeModel(new DefaultNodeModel());
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void createSceneModels() {
/*  459 */     Map<String, Scene> scenes = Optionals.of(this.gltf.getScenes());
/*  460 */     for (int i = 0; i < scenes.size(); i++)
/*      */     {
/*  462 */       this.gltfModel.addSceneModel(new DefaultSceneModel());
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void createSkinModels() {
/*  471 */     Map<String, Skin> skins = Optionals.of(this.gltf.getSkins());
/*  472 */     for (Map.Entry<String, Skin> entry : skins.entrySet()) {
/*      */       
/*  474 */       Skin skin = entry.getValue();
/*  475 */       float[] bindShapeMatrix = skin.getBindShapeMatrix();
/*  476 */       DefaultSkinModel skinModel = new DefaultSkinModel();
/*  477 */       skinModel.setBindShapeMatrix(bindShapeMatrix);
/*  478 */       this.gltfModel.addSkinModel(skinModel);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void createTextureModels() {
/*  487 */     Map<String, Texture> textures = Optionals.of(this.gltf.getTextures());
/*  488 */     Map<String, Sampler> samplers = Optionals.of(this.gltf.getSamplers());
/*  489 */     for (Map.Entry<String, Texture> entry : textures.entrySet()) {
/*      */       
/*  491 */       Texture texture = entry.getValue();
/*  492 */       String samplerId = texture.getSampler();
/*  493 */       Sampler sampler = samplers.get(samplerId);
/*      */       
/*  495 */       int magFilter = ((Integer)Optionals.of(sampler
/*  496 */           .getMagFilter(), sampler.defaultMagFilter())).intValue();
/*  497 */       int minFilter = ((Integer)Optionals.of(sampler
/*  498 */           .getMinFilter(), sampler.defaultMinFilter())).intValue();
/*  499 */       int wrapS = ((Integer)Optionals.of(sampler
/*  500 */           .getWrapS(), sampler.defaultWrapS())).intValue();
/*  501 */       int wrapT = ((Integer)Optionals.of(sampler
/*  502 */           .getWrapT(), sampler.defaultWrapT())).intValue();
/*      */       
/*  504 */       DefaultTextureModel textureModel = new DefaultTextureModel();
/*  505 */       textureModel.setMagFilter(Integer.valueOf(magFilter));
/*  506 */       textureModel.setMinFilter(Integer.valueOf(minFilter));
/*  507 */       textureModel.setWrapS(Integer.valueOf(wrapS));
/*  508 */       textureModel.setWrapT(Integer.valueOf(wrapT));
/*  509 */       this.gltfModel.addTextureModel(textureModel);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void createShaderModels() {
/*  518 */     Map<String, Shader> shaders = Optionals.of(this.gltf.getShaders());
/*  519 */     for (Map.Entry<String, Shader> entry : shaders.entrySet()) {
/*      */       
/*  521 */       Shader shader = entry.getValue();
/*  522 */       Integer type = shader.getType();
/*  523 */       ShaderModel.ShaderType shaderType = null;
/*  524 */       if (type.intValue() == 35633) {
/*      */         
/*  526 */         shaderType = ShaderModel.ShaderType.VERTEX_SHADER;
/*      */       }
/*      */       else {
/*      */         
/*  530 */         shaderType = ShaderModel.ShaderType.FRAGMENT_SHADER;
/*      */       } 
/*      */       
/*  533 */       DefaultShaderModel shaderModel = new DefaultShaderModel(shader.getUri(), shaderType);
/*  534 */       this.gltfModel.addShaderModel(shaderModel);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void createProgramModels() {
/*  543 */     Map<String, Program> programs = Optionals.of(this.gltf.getPrograms());
/*  544 */     for (int i = 0; i < programs.size(); i++)
/*      */     {
/*  546 */       this.gltfModel.addProgramModel(new DefaultProgramModel());
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void createTechniqueModels() {
/*  555 */     Map<String, Technique> techniques = Optionals.of(this.gltf.getTechniques());
/*  556 */     for (int i = 0; i < techniques.size(); i++)
/*      */     {
/*  558 */       this.gltfModel.addTechniqueModel(new DefaultTechniqueModel());
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void initAccessorModels() {
/*  568 */     Map<String, Accessor> accessors = Optionals.of(this.gltf.getAccessors());
/*  569 */     for (Map.Entry<String, Accessor> entry : accessors.entrySet()) {
/*      */       
/*  571 */       String accessorId = entry.getKey();
/*  572 */       Accessor accessor = entry.getValue();
/*  573 */       String bufferViewId = accessor.getBufferView();
/*      */       
/*  575 */       BufferViewModel bufferViewModel = get("bufferViews", bufferViewId, this.gltfModel::getBufferViewModel);
/*      */ 
/*      */       
/*  578 */       DefaultAccessorModel accessorModel = get("accessors", accessorId, this.gltfModel::getAccessorModel);
/*      */ 
/*      */       
/*  581 */       transferGltfChildOfRootPropertyElements((GlTFChildOfRootProperty)accessor, (AbstractNamedModelElement)accessorModel);
/*  582 */       accessorModel.setBufferViewModel(bufferViewModel);
/*  583 */       accessorModel.setAccessorData(AccessorDatas.create((AccessorModel)accessorModel));
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void initAnimationModels() {
/*  592 */     Map<String, Animation> animations = Optionals.of(this.gltf.getAnimations());
/*  593 */     for (Map.Entry<String, Animation> entry : animations.entrySet()) {
/*      */       
/*  595 */       String animationId = entry.getKey();
/*  596 */       Animation animation = entry.getValue();
/*      */       
/*  598 */       DefaultAnimationModel animationModel = get("animations", animationId, this.gltfModel::getAnimationModel);
/*      */       
/*  600 */       transferGltfChildOfRootPropertyElements((GlTFChildOfRootProperty)animation, (AbstractNamedModelElement)animationModel);
/*      */ 
/*      */       
/*  603 */       List<AnimationChannel> channels = Optionals.of(animation.getChannels());
/*  604 */       for (AnimationChannel animationChannel : channels) {
/*      */         
/*  606 */         AnimationModel.Channel channel = createChannel(animation, animationChannel);
/*  607 */         animationModel.addChannel(channel);
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void initImageModels() {
/*  617 */     Map<String, Image> images = Optionals.of(this.gltf.getImages());
/*  618 */     for (Map.Entry<String, Image> entry : images.entrySet()) {
/*      */       
/*  620 */       String imageId = entry.getKey();
/*  621 */       Image image = entry.getValue();
/*      */       
/*  623 */       DefaultImageModel imageModel = get("images", imageId, this.gltfModel::getImageModel);
/*  624 */       transferGltfChildOfRootPropertyElements((GlTFChildOfRootProperty)image, (AbstractNamedModelElement)imageModel);
/*      */       
/*  626 */       if (BinaryGltfV1.hasBinaryGltfExtension((GlTFProperty)image)) {
/*      */ 
/*      */         
/*  629 */         String bufferViewId = BinaryGltfV1.getBinaryGltfBufferViewId((GlTFProperty)image);
/*      */         
/*  631 */         BufferViewModel bufferViewModel = get("bufferViews", bufferViewId, this.gltfModel::getBufferViewModel);
/*      */         
/*  633 */         imageModel.setBufferViewModel(bufferViewModel);
/*      */         
/*      */         continue;
/*      */       } 
/*  637 */       String uri = image.getUri();
/*  638 */       if (IO.isDataUriString(uri)) {
/*      */         
/*  640 */         byte[] data = IO.readDataUri(uri);
/*  641 */         ByteBuffer byteBuffer = Buffers.create(data);
/*  642 */         imageModel.setImageData(byteBuffer);
/*      */         
/*      */         continue;
/*      */       } 
/*  646 */       ByteBuffer imageData = this.gltfAsset.getReferenceData(uri);
/*  647 */       imageModel.setImageData(imageData);
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
/*      */   private AnimationModel.Channel createChannel(Animation animation, AnimationChannel animationChannel) {
/*  665 */     Map<String, String> parameters = Optionals.of(animation.getParameters());
/*      */     
/*  667 */     Map<String, AnimationSampler> samplers = Optionals.of(animation.getSamplers());
/*      */     
/*  669 */     String samplerId = animationChannel.getSampler();
/*  670 */     AnimationSampler animationSampler = samplers.get(samplerId);
/*      */     
/*  672 */     String inputParameterId = animationSampler.getInput();
/*  673 */     String inputAccessorId = parameters.get(inputParameterId);
/*  674 */     if (inputAccessorId == null) {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  680 */       logger.warning("Assuming " + inputParameterId + " to be an accessor ID");
/*      */       
/*  682 */       inputAccessorId = inputParameterId;
/*      */     } 
/*      */     
/*  685 */     AccessorModel inputAccessorModel = get("accessors", inputAccessorId, this.gltfModel::getAccessorModel);
/*      */     
/*  687 */     String outputParameterId = animationSampler.getOutput();
/*  688 */     String outputAccessorId = parameters.get(outputParameterId);
/*  689 */     if (outputAccessorId == null) {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  695 */       logger.warning("Assuming " + outputParameterId + " to be an accessor ID");
/*      */       
/*  697 */       outputAccessorId = outputParameterId;
/*      */     } 
/*      */     
/*  700 */     AccessorModel outputAccessorModel = get("accessors", outputAccessorId, this.gltfModel::getAccessorModel);
/*      */ 
/*      */     
/*  703 */     String interpolationString = animationSampler.getInterpolation();
/*      */ 
/*      */     
/*  706 */     AnimationModel.Interpolation interpolation = (interpolationString == null) ? AnimationModel.Interpolation.LINEAR : AnimationModel.Interpolation.valueOf(interpolationString);
/*      */     
/*  708 */     DefaultAnimationModel.DefaultSampler defaultSampler = new DefaultAnimationModel.DefaultSampler(inputAccessorModel, interpolation, outputAccessorModel);
/*      */ 
/*      */ 
/*      */     
/*  712 */     AnimationChannelTarget animationChannelTarget = animationChannel.getTarget();
/*  713 */     String nodeId = animationChannelTarget.getId();
/*  714 */     String path = animationChannelTarget.getPath();
/*      */     
/*  716 */     NodeModel nodeModel = get("nodes", nodeId, this.gltfModel::getNodeModel);
/*      */     
/*  718 */     return (AnimationModel.Channel)new DefaultAnimationModel.DefaultChannel((AnimationModel.Sampler)defaultSampler, nodeModel, path);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void initBufferModels() {
/*  728 */     ByteBuffer binaryData = null;
/*  729 */     ByteBuffer b = this.gltfAsset.getBinaryData();
/*  730 */     if (b != null && b.capacity() > 0)
/*      */     {
/*  732 */       binaryData = b;
/*      */     }
/*      */     
/*  735 */     Map<String, Buffer> buffers = Optionals.of(this.gltf.getBuffers());
/*  736 */     for (Map.Entry<String, Buffer> entry : buffers.entrySet()) {
/*      */       
/*  738 */       String bufferId = entry.getKey();
/*  739 */       Buffer buffer = entry.getValue();
/*      */       
/*  741 */       DefaultBufferModel bufferModel = get("buffers", bufferId, this.gltfModel::getBufferModel);
/*  742 */       transferGltfChildOfRootPropertyElements((GlTFChildOfRootProperty)buffer, (AbstractNamedModelElement)bufferModel);
/*      */       
/*  744 */       if (BinaryGltfV1.isBinaryGltfBufferId(bufferId)) {
/*      */         
/*  746 */         if (binaryData == null) {
/*      */           
/*  748 */           logger.severe("The glTF contains a buffer with the binary buffer ID, but no binary data has been given");
/*      */           
/*      */           continue;
/*      */         } 
/*  752 */         bufferModel.setBufferData(binaryData);
/*      */         
/*      */         continue;
/*      */       } 
/*  756 */       String uri = buffer.getUri();
/*  757 */       if (IO.isDataUriString(uri)) {
/*      */         
/*  759 */         byte[] data = IO.readDataUri(uri);
/*  760 */         ByteBuffer byteBuffer = Buffers.create(data);
/*  761 */         bufferModel.setBufferData(byteBuffer);
/*      */         
/*      */         continue;
/*      */       } 
/*  765 */       ByteBuffer bufferData = this.gltfAsset.getReferenceData(uri);
/*  766 */       bufferModel.setBufferData(bufferData);
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
/*      */   private void initBufferViewModels() {
/*  779 */     Map<String, BufferView> bufferViews = Optionals.of(this.gltf.getBufferViews());
/*  780 */     for (Map.Entry<String, BufferView> entry : bufferViews.entrySet()) {
/*      */       
/*  782 */       String bufferViewId = entry.getKey();
/*  783 */       BufferView bufferView = entry.getValue();
/*      */       
/*  785 */       String bufferId = bufferView.getBuffer();
/*      */       
/*  787 */       BufferModel bufferModel = get("buffers", bufferId, this.gltfModel::getBufferModel);
/*      */       
/*  789 */       DefaultBufferViewModel bufferViewModel = get("bufferViews", bufferViewId, this.gltfModel::getBufferViewModel);
/*  790 */       transferGltfChildOfRootPropertyElements((GlTFChildOfRootProperty)bufferView, (AbstractNamedModelElement)bufferViewModel);
/*      */       
/*  792 */       bufferViewModel.setBufferModel(bufferModel);
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
/*      */   private List<DefaultAccessorModel> computeAccessorModelsOf(BufferViewModel bufferViewModel) {
/*  806 */     List<DefaultAccessorModel> result = new ArrayList<>();
/*      */     
/*  808 */     int n = this.gltfModel.getAccessorModels().size();
/*  809 */     for (int i = 0; i < n; i++) {
/*      */       
/*  811 */       DefaultAccessorModel accessorModel = this.gltfModel.getAccessorModel(i);
/*  812 */       BufferViewModel b = accessorModel.getBufferViewModel();
/*  813 */       if (bufferViewModel.equals(b))
/*      */       {
/*  815 */         result.add(accessorModel);
/*      */       }
/*      */     } 
/*  818 */     return result;
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
/*      */   private static int computeCommonByteStride(Iterable<? extends AccessorModel> accessorModels) {
/*  832 */     int commonByteStride = -1;
/*  833 */     for (AccessorModel accessorModel : accessorModels) {
/*      */       
/*  835 */       int byteStride = accessorModel.getByteStride();
/*  836 */       if (commonByteStride == -1) {
/*      */         
/*  838 */         commonByteStride = byteStride;
/*      */         
/*      */         continue;
/*      */       } 
/*  842 */       if (commonByteStride != byteStride)
/*      */       {
/*  844 */         logger.warning("The accessor models do not have the same byte stride: " + commonByteStride + " and " + byteStride);
/*      */       }
/*      */     } 
/*      */ 
/*      */ 
/*      */     
/*  850 */     return commonByteStride;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void assignBufferViewByteStrides() {
/*  861 */     int n = this.gltfModel.getBufferModels().size();
/*  862 */     for (int i = 0; i < n; i++) {
/*      */ 
/*      */       
/*  865 */       DefaultBufferViewModel bufferViewModel = this.gltfModel.getBufferViewModel(i);
/*      */       
/*  867 */       List<DefaultAccessorModel> accessorModelsOfBufferView = computeAccessorModelsOf((BufferViewModel)bufferViewModel);
/*  868 */       if (accessorModelsOfBufferView.size() > 1) {
/*      */ 
/*      */         
/*  871 */         int byteStride = computeCommonByteStride((Iterable)accessorModelsOfBufferView);
/*  872 */         bufferViewModel.setByteStride(Integer.valueOf(byteStride));
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void initMeshModels() {
/*  883 */     Map<String, Mesh> meshes = Optionals.of(this.gltf.getMeshes());
/*  884 */     for (Map.Entry<String, Mesh> entry : meshes.entrySet()) {
/*      */       
/*  886 */       String meshId = entry.getKey();
/*  887 */       Mesh mesh = entry.getValue();
/*      */       
/*  889 */       List<MeshPrimitive> primitives = Optionals.of(mesh.getPrimitives());
/*      */       
/*  891 */       DefaultMeshModel meshModel = get("meshes", meshId, this.gltfModel::getMeshModel);
/*  892 */       transferGltfChildOfRootPropertyElements((GlTFChildOfRootProperty)mesh, (AbstractNamedModelElement)meshModel);
/*      */       
/*  894 */       for (MeshPrimitive meshPrimitive : primitives) {
/*      */ 
/*      */         
/*  897 */         DefaultMeshPrimitiveModel defaultMeshPrimitiveModel = createMeshPrimitiveModel(meshPrimitive);
/*  898 */         meshModel.addMeshPrimitiveModel((MeshPrimitiveModel)defaultMeshPrimitiveModel);
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
/*      */   private DefaultMeshPrimitiveModel createMeshPrimitiveModel(MeshPrimitive meshPrimitive) {
/*  912 */     Integer mode = (Integer)Optionals.of(meshPrimitive
/*  913 */         .getMode(), meshPrimitive
/*  914 */         .defaultMode());
/*      */     
/*  916 */     DefaultMeshPrimitiveModel meshPrimitiveModel = new DefaultMeshPrimitiveModel(mode.intValue());
/*  917 */     transferGltfPropertyElements((GlTFProperty)meshPrimitive, (AbstractModelElement)meshPrimitiveModel);
/*      */     
/*  919 */     String indicesId = meshPrimitive.getIndices();
/*  920 */     if (indicesId != null) {
/*      */ 
/*      */       
/*  923 */       AccessorModel indices = get("accessors", indicesId, this.gltfModel::getAccessorModel);
/*  924 */       meshPrimitiveModel.setIndices(indices);
/*      */     } 
/*      */     
/*  927 */     Map<String, String> attributes = Optionals.of(meshPrimitive.getAttributes());
/*  928 */     for (Map.Entry<String, String> entry : attributes.entrySet()) {
/*      */       
/*  930 */       String attributeName = entry.getKey();
/*  931 */       String attributeId = entry.getValue();
/*      */ 
/*      */       
/*  934 */       AccessorModel attribute = get("accessors", attributeId, this.gltfModel::getAccessorModel);
/*  935 */       meshPrimitiveModel.putAttribute(attributeName, attribute);
/*      */     } 
/*      */     
/*  938 */     String materialId = meshPrimitive.getMaterial();
/*  939 */     if (materialId == null || 
/*  940 */       GltfDefaults.isDefaultMaterialId(materialId)) {
/*      */       
/*  942 */       meshPrimitiveModel.setMaterialModel(
/*  943 */           DefaultModels.getDefaultMaterialModel());
/*      */     
/*      */     }
/*      */     else {
/*      */       
/*  948 */       MaterialModel materialModel = get("materials", materialId, this.gltfModel::getMaterialModel);
/*  949 */       meshPrimitiveModel.setMaterialModel(materialModel);
/*      */     } 
/*      */     
/*  952 */     return meshPrimitiveModel;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void initNodeModels() {
/*  960 */     Map<String, Node> nodes = Optionals.of(this.gltf.getNodes());
/*  961 */     for (Map.Entry<String, Node> entry : nodes.entrySet()) {
/*      */       
/*  963 */       String nodeId = entry.getKey();
/*  964 */       Node node = entry.getValue();
/*      */ 
/*      */       
/*  967 */       DefaultNodeModel nodeModel = get("nodes", nodeId, this.gltfModel::getNodeModel);
/*  968 */       transferGltfChildOfRootPropertyElements((GlTFChildOfRootProperty)node, (AbstractNamedModelElement)nodeModel);
/*      */       
/*  970 */       List<String> childIds = Optionals.of(node.getChildren());
/*  971 */       for (String childId : childIds) {
/*      */ 
/*      */         
/*  974 */         DefaultNodeModel child = get("nodes", childId, this.gltfModel::getNodeModel);
/*  975 */         nodeModel.addChild(child);
/*      */       } 
/*  977 */       List<String> meshIds = Optionals.of(node.getMeshes());
/*  978 */       for (String meshId : meshIds) {
/*      */ 
/*      */         
/*  981 */         MeshModel meshModel = get("meshes", meshId, this.gltfModel::getMeshModel);
/*  982 */         nodeModel.addMeshModel(meshModel);
/*      */       } 
/*  984 */       String skinId = node.getSkin();
/*  985 */       if (skinId != null) {
/*      */ 
/*      */         
/*  988 */         SkinModel skinModel = get("skins", skinId, this.gltfModel::getSkinModel);
/*  989 */         nodeModel.setSkinModel(skinModel);
/*      */       } 
/*  991 */       String cameraId = node.getCamera();
/*  992 */       if (cameraId != null) {
/*      */ 
/*      */         
/*  995 */         CameraModel cameraModel = get("cameras", cameraId, this.gltfModel::getCameraModel);
/*  996 */         nodeModel.setCameraModel(cameraModel);
/*      */       } 
/*      */       
/*  999 */       float[] matrix = node.getMatrix();
/* 1000 */       float[] translation = node.getTranslation();
/* 1001 */       float[] rotation = node.getRotation();
/* 1002 */       float[] scale = node.getScale();
/* 1003 */       nodeModel.setMatrix(Optionals.clone(matrix));
/* 1004 */       nodeModel.setTranslation(Optionals.clone(translation));
/* 1005 */       nodeModel.setRotation(Optionals.clone(rotation));
/* 1006 */       nodeModel.setScale(Optionals.clone(scale));
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void initSceneModels() {
/* 1015 */     Map<String, Scene> scenes = Optionals.of(this.gltf.getScenes());
/* 1016 */     for (Map.Entry<String, Scene> entry : scenes.entrySet()) {
/*      */       
/* 1018 */       String sceneId = entry.getKey();
/* 1019 */       Scene scene = entry.getValue();
/*      */ 
/*      */       
/* 1022 */       DefaultSceneModel sceneModel = get("scenes", sceneId, this.gltfModel::getSceneModel);
/* 1023 */       transferGltfChildOfRootPropertyElements((GlTFChildOfRootProperty)scene, (AbstractNamedModelElement)sceneModel);
/*      */       
/* 1025 */       List<String> nodes = Optionals.of(scene.getNodes());
/* 1026 */       for (String nodeId : nodes) {
/*      */ 
/*      */         
/* 1029 */         NodeModel nodeModel = get("nodes", nodeId, this.gltfModel::getNodeModel);
/* 1030 */         sceneModel.addNode(nodeModel);
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
/*      */   private static Map<String, String> computeJointNameToNodeIdMap(GlTF gltf) {
/* 1044 */     Map<String, String> map = new LinkedHashMap<>();
/* 1045 */     Map<String, Node> nodes = Optionals.of(gltf.getNodes());
/* 1046 */     for (Map.Entry<String, Node> entry : nodes.entrySet()) {
/*      */       
/* 1048 */       String nodeId = entry.getKey();
/* 1049 */       Node node = entry.getValue();
/* 1050 */       if (node.getJointName() != null) {
/*      */         
/* 1052 */         String oldNodeId = map.put(node.getJointName(), nodeId);
/* 1053 */         if (oldNodeId != null)
/*      */         {
/* 1055 */           logger.warning("Joint name " + node.getJointName() + " is mapped to nodes with IDs " + nodeId + " and " + oldNodeId);
/*      */         }
/*      */       } 
/*      */     } 
/*      */ 
/*      */     
/* 1061 */     return map;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void initSkinModels() {
/* 1070 */     Map<String, String> jointNameToNodeIdMap = computeJointNameToNodeIdMap(this.gltf);
/* 1071 */     Map<String, Skin> skins = Optionals.of(this.gltf.getSkins());
/* 1072 */     for (Map.Entry<String, Skin> entry : skins.entrySet()) {
/*      */       
/* 1074 */       String skinId = entry.getKey();
/* 1075 */       Skin skin = entry.getValue();
/*      */       
/* 1077 */       DefaultSkinModel skinModel = get("skins", skinId, this.gltfModel::getSkinModel);
/* 1078 */       transferGltfChildOfRootPropertyElements((GlTFChildOfRootProperty)skin, (AbstractNamedModelElement)skinModel);
/*      */       
/* 1080 */       List<String> jointNames = skin.getJointNames();
/* 1081 */       for (String jointName : jointNames) {
/*      */         
/* 1083 */         String nodeId = jointNameToNodeIdMap.get(jointName);
/*      */         
/* 1085 */         NodeModel nodeModel = get("nodes", nodeId, this.gltfModel::getNodeModel);
/* 1086 */         skinModel.addJoint(nodeModel);
/*      */       } 
/*      */       
/* 1089 */       String inverseBindMatricesId = skin.getInverseBindMatrices();
/*      */       
/* 1091 */       AccessorModel inverseBindMatrices = get("accessors", inverseBindMatricesId, this.gltfModel::getAccessorModel);
/*      */       
/* 1093 */       skinModel.setInverseBindMatrices(inverseBindMatrices);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void initTextureModels() {
/* 1102 */     Map<String, Texture> textures = Optionals.of(this.gltf.getTextures());
/* 1103 */     for (Map.Entry<String, Texture> entry : textures.entrySet()) {
/*      */       
/* 1105 */       String textureId = entry.getKey();
/* 1106 */       Texture texture = entry.getValue();
/*      */       
/* 1108 */       DefaultTextureModel textureModel = get("textures", textureId, this.gltfModel::getTextureModel);
/* 1109 */       transferGltfChildOfRootPropertyElements((GlTFChildOfRootProperty)texture, (AbstractNamedModelElement)textureModel);
/*      */       
/* 1111 */       String imageId = texture.getSource();
/*      */       
/* 1113 */       DefaultImageModel imageModel = get("images", imageId, this.gltfModel::getImageModel);
/* 1114 */       textureModel.setImageModel((ImageModel)imageModel);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void initShaderModels() {
/* 1123 */     Map<String, Shader> shaders = Optionals.of(this.gltf.getShaders());
/* 1124 */     for (Map.Entry<String, Shader> entry : shaders.entrySet()) {
/*      */       
/* 1126 */       String shaderId = entry.getKey();
/* 1127 */       Shader shader = entry.getValue();
/*      */       
/* 1129 */       DefaultShaderModel shaderModel = get("shaders", shaderId, this.gltfModel::getShaderModel);
/* 1130 */       transferGltfChildOfRootPropertyElements((GlTFChildOfRootProperty)shader, (AbstractNamedModelElement)shaderModel);
/*      */       
/* 1132 */       if (BinaryGltfV1.hasBinaryGltfExtension((GlTFProperty)shader)) {
/*      */ 
/*      */         
/* 1135 */         String bufferViewId = BinaryGltfV1.getBinaryGltfBufferViewId((GlTFProperty)shader);
/*      */         
/* 1137 */         BufferViewModel bufferViewModel = get("bufferViews", bufferViewId, this.gltfModel::getBufferViewModel);
/*      */ 
/*      */         
/* 1140 */         shaderModel.setShaderData(bufferViewModel.getBufferViewData());
/*      */         
/*      */         continue;
/*      */       } 
/* 1144 */       String uri = shader.getUri();
/* 1145 */       if (IO.isDataUriString(uri)) {
/*      */         
/* 1147 */         byte[] data = IO.readDataUri(uri);
/* 1148 */         ByteBuffer byteBuffer = Buffers.create(data);
/* 1149 */         shaderModel.setShaderData(byteBuffer);
/*      */         
/*      */         continue;
/*      */       } 
/* 1153 */       ByteBuffer shaderData = this.gltfAsset.getReferenceData(uri);
/* 1154 */       shaderModel.setShaderData(shaderData);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   void initProgramModels() {
/* 1165 */     Map<String, Program> programs = Optionals.of(this.gltf.getPrograms());
/* 1166 */     for (Map.Entry<String, Program> entry : programs.entrySet()) {
/*      */       
/* 1168 */       String programId = entry.getKey();
/* 1169 */       Program program = entry.getValue();
/*      */       
/* 1171 */       DefaultProgramModel programModel = get("programs", programId, this.gltfModel::getProgramModel);
/* 1172 */       transferGltfChildOfRootPropertyElements((GlTFChildOfRootProperty)program, (AbstractNamedModelElement)programModel);
/*      */       
/* 1174 */       String vertexShaderId = program.getVertexShader();
/*      */       
/* 1176 */       DefaultShaderModel vertexShaderModel = get("shaders", vertexShaderId, this.gltfModel::getShaderModel);
/* 1177 */       programModel.setVertexShaderModel((ShaderModel)vertexShaderModel);
/*      */       
/* 1179 */       String fragmentShaderId = program.getFragmentShader();
/*      */       
/* 1181 */       DefaultShaderModel fragmentShaderModel = get("shaders", fragmentShaderId, this.gltfModel::getShaderModel);
/* 1182 */       programModel.setFragmentShaderModel((ShaderModel)fragmentShaderModel);
/*      */       
/* 1184 */       List<String> attributes = Optionals.of(program.getAttributes());
/* 1185 */       for (String attribute : attributes)
/*      */       {
/* 1187 */         programModel.addAttribute(attribute);
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static void addParameters(Technique technique, DefaultTechniqueModel techniqueModel, Function<? super String, ? extends NodeModel> nodeLookup) {
/* 1211 */     Map<String, TechniqueParameters> parameters = Optionals.of(technique.getParameters());
/* 1212 */     for (Map.Entry<String, TechniqueParameters> entry : parameters.entrySet()) {
/*      */       
/* 1214 */       String parameterName = entry.getKey();
/* 1215 */       TechniqueParameters parameter = entry.getValue();
/*      */       
/* 1217 */       int type = parameter.getType().intValue();
/* 1218 */       int count = ((Integer)Optionals.of(parameter.getCount(), Integer.valueOf(1))).intValue();
/* 1219 */       String semantic = parameter.getSemantic();
/* 1220 */       Object value = parameter.getValue();
/* 1221 */       String nodeId = parameter.getNode();
/* 1222 */       NodeModel nodeModel = null;
/* 1223 */       if (nodeId != null)
/*      */       {
/* 1225 */         if (nodeLookup == null) {
/*      */           
/* 1227 */           logger.severe("No lookup function found for the nodes");
/*      */         }
/*      */         else {
/*      */           
/* 1231 */           nodeModel = nodeLookup.apply(nodeId);
/*      */         } 
/*      */       }
/*      */       
/* 1235 */       DefaultTechniqueParametersModel defaultTechniqueParametersModel = new DefaultTechniqueParametersModel(type, count, semantic, value, nodeModel);
/*      */ 
/*      */       
/* 1238 */       techniqueModel.addParameter(parameterName, (TechniqueParametersModel)defaultTechniqueParametersModel);
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
/*      */   private static void addAttributes(Technique technique, DefaultTechniqueModel techniqueModel) {
/* 1254 */     Map<String, String> attributes = Optionals.of(technique.getAttributes());
/* 1255 */     for (Map.Entry<String, String> entry : attributes.entrySet()) {
/*      */       
/* 1257 */       String attributeName = entry.getKey();
/* 1258 */       String parameterName = entry.getValue();
/* 1259 */       techniqueModel.addAttribute(attributeName, parameterName);
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
/*      */   private static void addUniforms(Technique technique, DefaultTechniqueModel techniqueModel) {
/* 1274 */     Map<String, String> uniforms = Optionals.of(technique.getUniforms());
/* 1275 */     for (Map.Entry<String, String> entry : uniforms.entrySet()) {
/*      */       
/* 1277 */       String uniformName = entry.getKey();
/* 1278 */       String parameterName = entry.getValue();
/* 1279 */       techniqueModel.addUniform(uniformName, parameterName);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void initTechniqueModels() {
/* 1289 */     Map<String, Technique> techniques = Optionals.of(this.gltf.getTechniques());
/* 1290 */     for (Map.Entry<String, Technique> entry : techniques.entrySet()) {
/*      */       
/* 1292 */       String techniqueId = entry.getKey();
/* 1293 */       Technique technique = entry.getValue();
/*      */ 
/*      */       
/* 1296 */       DefaultTechniqueModel techniqueModel = get("techniques", techniqueId, this.gltfModel::getTechniqueModel);
/*      */       
/* 1298 */       String programId = technique.getProgram();
/*      */       
/* 1300 */       DefaultProgramModel programModel = get("programs", programId, this.gltfModel::getProgramModel);
/* 1301 */       techniqueModel.setProgramModel((ProgramModel)programModel);
/*      */       
/* 1303 */       Function<String, NodeModel> nodeLookup = nodeId -> (NodeModel)get("nodes", nodeId, this.gltfModel::getNodeModel);
/*      */ 
/*      */       
/* 1306 */       initTechniqueModel(techniqueModel, technique, nodeLookup);
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
/*      */   public static void initTechniqueModel(DefaultTechniqueModel techniqueModel, Technique technique, Function<? super String, ? extends NodeModel> nodeLookup) {
/* 1327 */     transferGltfChildOfRootPropertyElements((GlTFChildOfRootProperty)technique, (AbstractNamedModelElement)techniqueModel);
/*      */     
/* 1329 */     addParameters(technique, techniqueModel, nodeLookup);
/* 1330 */     addAttributes(technique, techniqueModel);
/* 1331 */     addUniforms(technique, techniqueModel);
/*      */     
/* 1333 */     List<Integer> enableModel = null;
/* 1334 */     DefaultTechniqueStatesFunctionsModel techniqueStatesFunctionsModel = null;
/*      */     
/* 1336 */     TechniqueStates states = technique.getStates();
/* 1337 */     if (states != null) {
/*      */       
/* 1339 */       List<Integer> enable = states.getEnable();
/* 1340 */       if (enable != null)
/*      */       {
/* 1342 */         enableModel = new ArrayList<>(enable);
/*      */       }
/* 1344 */       TechniqueStatesFunctions functions = states.getFunctions();
/* 1345 */       if (functions != null)
/*      */       {
/*      */         
/* 1348 */         techniqueStatesFunctionsModel = TechniqueStatesFunctionsModels.create(functions);
/*      */       }
/*      */       
/* 1351 */       DefaultTechniqueStatesModel defaultTechniqueStatesModel = new DefaultTechniqueStatesModel(enableModel, (TechniqueStatesFunctionsModel)techniqueStatesFunctionsModel);
/*      */ 
/*      */       
/* 1354 */       techniqueModel.setTechniqueStatesModel((TechniqueStatesModel)defaultTechniqueStatesModel);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void initMaterialModels() {
/* 1364 */     Map<String, Material> materials = Optionals.of(this.gltf.getMaterials());
/* 1365 */     for (Map.Entry<String, Material> entry : materials.entrySet()) {
/*      */       TechniqueModel techniqueModel;
/* 1367 */       String materialId = entry.getKey();
/* 1368 */       Material material = entry.getValue();
/*      */       
/* 1370 */       MaterialModelV1 materialModel = get("materials", materialId, this.gltfModel::getMaterialModel);
/*      */ 
/*      */       
/* 1373 */       transferGltfChildOfRootPropertyElements((GlTFChildOfRootProperty)material, materialModel);
/*      */       
/* 1375 */       String techniqueId = material.getTechnique();
/*      */       
/* 1377 */       if (techniqueId == null || 
/* 1378 */         GltfDefaults.isDefaultTechniqueId(techniqueId)) {
/*      */         
/* 1380 */         techniqueModel = DefaultModels.getDefaultTechniqueModel();
/*      */       
/*      */       }
/*      */       else {
/*      */         
/* 1385 */         techniqueModel = get("techniques", techniqueId, this.gltfModel::getTechniqueModel);
/*      */       } 
/*      */       
/* 1388 */       materialModel.setTechniqueModel(techniqueModel);
/*      */ 
/*      */       
/* 1391 */       Map<String, Object> modelValues = new LinkedHashMap<>();
/*      */       
/* 1393 */       Map<String, Object> values = Optionals.of(material.getValues());
/* 1394 */       for (Map.Entry<String, Object> valueEntry : values.entrySet()) {
/*      */         
/* 1396 */         String parameterName = valueEntry.getKey();
/*      */         
/* 1398 */         TechniqueParametersModel techniqueParametersModel = (TechniqueParametersModel)techniqueModel.getParameters().get(parameterName);
/* 1399 */         if (techniqueParametersModel != null && techniqueParametersModel
/* 1400 */           .getType() == 35678) {
/*      */ 
/*      */           
/* 1403 */           TextureModel textureModel = null;
/* 1404 */           Object value = valueEntry.getValue();
/* 1405 */           if (value != null) {
/*      */             
/* 1407 */             String textureId = String.valueOf(value);
/* 1408 */             textureModel = get("textures", textureId, this.gltfModel::getTextureModel);
/*      */           } 
/*      */           
/* 1411 */           modelValues.put(parameterName, textureModel);
/*      */           
/*      */           continue;
/*      */         } 
/* 1415 */         modelValues.put(parameterName, valueEntry.getValue());
/*      */       } 
/*      */       
/* 1418 */       materialModel.setValues(modelValues);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void initExtensionsModel() {
/* 1429 */     List<String> extensionsUsed = this.gltf.getExtensionsUsed();
/* 1430 */     DefaultExtensionsModel extensionsModel = this.gltfModel.getExtensionsModel();
/* 1431 */     extensionsModel.addExtensionsUsed(extensionsUsed);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void initAssetModel() {
/* 1440 */     Asset asset = this.gltf.getAsset();
/* 1441 */     if (asset != null) {
/*      */       
/* 1443 */       DefaultAssetModel assetModel = this.gltfModel.getAssetModel();
/* 1444 */       transferGltfPropertyElements((GlTFProperty)asset, (AbstractModelElement)assetModel);
/* 1445 */       assetModel.setCopyright(asset.getCopyright());
/* 1446 */       assetModel.setGenerator(asset.getGenerator());
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
/*      */   private static void transferGltfPropertyElements(GlTFProperty property, AbstractModelElement modelElement) {
/* 1461 */     modelElement.setExtensions(property.getExtensions());
/* 1462 */     modelElement.setExtras(property.getExtras());
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
/* 1476 */     modelElement.setName(property.getName());
/* 1477 */     transferGltfPropertyElements((GlTFProperty)property, (AbstractModelElement)modelElement);
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
/*      */   private <T> T get(String name, String id, IntFunction<? extends T> getter) {
/* 1498 */     Integer index = this.indexMappingSet.getIndex(name, id);
/* 1499 */     if (index == null) {
/*      */       
/* 1501 */       logger.severe("No index found for " + name + " ID " + id);
/* 1502 */       return null;
/*      */     } 
/* 1504 */     T element = getter.apply(index.intValue());
/* 1505 */     return element;
/*      */   }
/*      */ }


/* Location:              C:\Users\DuduM\OneDrive\Área de Trabalho\mw10\modularwarfare-shining-2023.2.4.4f-fix10.jar!\de\javagl\jgltf\model\v1\GltfModelCreatorV1.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */