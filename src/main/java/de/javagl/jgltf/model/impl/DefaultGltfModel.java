/*     */ package de.javagl.jgltf.model.impl;
/*     */ 
/*     */ import de.javagl.jgltf.model.AccessorModel;
/*     */ import de.javagl.jgltf.model.AnimationModel;
/*     */ import de.javagl.jgltf.model.AssetModel;
/*     */ import de.javagl.jgltf.model.BufferModel;
/*     */ import de.javagl.jgltf.model.BufferViewModel;
/*     */ import de.javagl.jgltf.model.CameraModel;
/*     */ import de.javagl.jgltf.model.ExtensionsModel;
/*     */ import de.javagl.jgltf.model.GltfModel;
/*     */ import de.javagl.jgltf.model.ImageModel;
/*     */ import de.javagl.jgltf.model.MaterialModel;
/*     */ import de.javagl.jgltf.model.MeshModel;
/*     */ import de.javagl.jgltf.model.NodeModel;
/*     */ import de.javagl.jgltf.model.SceneModel;
/*     */ import de.javagl.jgltf.model.SkinModel;
/*     */ import de.javagl.jgltf.model.TextureModel;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collection;
/*     */ import java.util.Collections;
/*     */ import java.util.List;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class DefaultGltfModel
/*     */   extends AbstractModelElement
/*     */   implements GltfModel
/*     */ {
/* 130 */   private final List<DefaultAccessorModel> accessorModels = new ArrayList<>();
/* 131 */   private final List<DefaultAnimationModel> animationModels = new ArrayList<>();
/* 132 */   private final List<DefaultBufferModel> bufferModels = new ArrayList<>();
/* 133 */   private final List<DefaultBufferViewModel> bufferViewModels = new ArrayList<>();
/* 134 */   private final List<DefaultCameraModel> cameraModels = new ArrayList<>();
/* 135 */   private final List<DefaultImageModel> imageModels = new ArrayList<>();
/* 136 */   private final List<MaterialModel> materialModels = new ArrayList<>();
/* 137 */   private final List<DefaultMeshModel> meshModels = new ArrayList<>();
/* 138 */   private final List<DefaultNodeModel> nodeModels = new ArrayList<>();
/* 139 */   private final List<DefaultSceneModel> sceneModels = new ArrayList<>();
/* 140 */   private final List<DefaultSkinModel> skinModels = new ArrayList<>();
/* 141 */   private final List<DefaultTextureModel> textureModels = new ArrayList<>();
/* 142 */   private final DefaultExtensionsModel extensionsModel = new DefaultExtensionsModel();
/* 143 */   private final DefaultAssetModel assetModel = new DefaultAssetModel();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void addAccessorModel(DefaultAccessorModel accessorModel) {
/* 153 */     this.accessorModels.add(accessorModel);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void removeAccessorModel(DefaultAccessorModel accessorModel) {
/* 163 */     this.accessorModels.remove(accessorModel);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void addAccessorModels(Collection<? extends DefaultAccessorModel> accessorModels) {
/* 174 */     for (DefaultAccessorModel accessorModel : accessorModels)
/*     */     {
/* 176 */       addAccessorModel(accessorModel);
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
/*     */   public DefaultAccessorModel getAccessorModel(int index) {
/* 188 */     return this.accessorModels.get(index);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void clearAccessorModels() {
/* 196 */     this.accessorModels.clear();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public List<AccessorModel> getAccessorModels() {
/* 202 */     return Collections.unmodifiableList((List)this.accessorModels);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void addAnimationModel(DefaultAnimationModel animationModel) {
/* 212 */     this.animationModels.add(animationModel);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void removeAnimationModel(DefaultAnimationModel animationModel) {
/* 222 */     this.animationModels.remove(animationModel);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void addAnimationModels(Collection<? extends DefaultAnimationModel> animationModels) {
/* 233 */     for (DefaultAnimationModel animationModel : animationModels)
/*     */     {
/* 235 */       addAnimationModel(animationModel);
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
/*     */   public DefaultAnimationModel getAnimationModel(int index) {
/* 247 */     return this.animationModels.get(index);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void clearAnimationModels() {
/* 255 */     this.animationModels.clear();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public List<AnimationModel> getAnimationModels() {
/* 261 */     return Collections.unmodifiableList((List)this.animationModels);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void addBufferModel(DefaultBufferModel bufferModel) {
/* 271 */     this.bufferModels.add(bufferModel);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void removeBufferModel(DefaultBufferModel bufferModel) {
/* 281 */     this.bufferModels.remove(bufferModel);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void addBufferModels(Collection<? extends DefaultBufferModel> bufferModels) {
/* 292 */     for (DefaultBufferModel bufferModel : bufferModels)
/*     */     {
/* 294 */       addBufferModel(bufferModel);
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
/*     */   public DefaultBufferModel getBufferModel(int index) {
/* 306 */     return this.bufferModels.get(index);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void clearBufferModels() {
/* 314 */     this.bufferModels.clear();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public List<BufferModel> getBufferModels() {
/* 320 */     return Collections.unmodifiableList((List)this.bufferModels);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void addBufferViewModel(DefaultBufferViewModel bufferViewModel) {
/* 330 */     this.bufferViewModels.add(bufferViewModel);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void removeBufferViewModel(DefaultBufferViewModel bufferViewModel) {
/* 340 */     this.bufferViewModels.remove(bufferViewModel);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void addBufferViewModels(Collection<? extends DefaultBufferViewModel> bufferViewModels) {
/* 351 */     for (DefaultBufferViewModel bufferViewModel : bufferViewModels)
/*     */     {
/* 353 */       addBufferViewModel(bufferViewModel);
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
/*     */   public DefaultBufferViewModel getBufferViewModel(int index) {
/* 365 */     return this.bufferViewModels.get(index);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void clearBufferViewModels() {
/* 373 */     this.bufferViewModels.clear();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public List<BufferViewModel> getBufferViewModels() {
/* 379 */     return Collections.unmodifiableList((List)this.bufferViewModels);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void addCameraModel(DefaultCameraModel cameraModel) {
/* 389 */     this.cameraModels.add(cameraModel);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void removeCameraModel(DefaultCameraModel cameraModel) {
/* 399 */     this.cameraModels.remove(cameraModel);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void addCameraModels(Collection<? extends DefaultCameraModel> cameraModels) {
/* 410 */     for (DefaultCameraModel cameraModel : cameraModels)
/*     */     {
/* 412 */       addCameraModel(cameraModel);
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
/*     */   public DefaultCameraModel getCameraModel(int index) {
/* 424 */     return this.cameraModels.get(index);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void clearCameraModels() {
/* 432 */     this.cameraModels.clear();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public List<CameraModel> getCameraModels() {
/* 438 */     return Collections.unmodifiableList((List)this.cameraModels);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void addImageModel(DefaultImageModel imageModel) {
/* 448 */     this.imageModels.add(imageModel);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void removeImageModel(DefaultImageModel imageModel) {
/* 458 */     this.imageModels.remove(imageModel);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void addImageModels(Collection<? extends DefaultImageModel> imageModels) {
/* 469 */     for (DefaultImageModel imageModel : imageModels)
/*     */     {
/* 471 */       addImageModel(imageModel);
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
/*     */   public DefaultImageModel getImageModel(int index) {
/* 483 */     return this.imageModels.get(index);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void clearImageModels() {
/* 491 */     this.imageModels.clear();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public List<ImageModel> getImageModels() {
/* 497 */     return Collections.unmodifiableList((List)this.imageModels);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void addMaterialModel(MaterialModel materialModel) {
/* 507 */     this.materialModels.add(materialModel);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void removeMaterialModel(MaterialModel materialModel) {
/* 517 */     this.materialModels.remove(materialModel);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void addMaterialModels(Collection<? extends MaterialModel> materialModels) {
/* 528 */     for (MaterialModel materialModel : materialModels)
/*     */     {
/* 530 */       addMaterialModel(materialModel);
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
/*     */   public MaterialModel getMaterialModel(int index) {
/* 542 */     return this.materialModels.get(index);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void clearMaterialModels() {
/* 550 */     this.materialModels.clear();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public List<MaterialModel> getMaterialModels() {
/* 556 */     return Collections.unmodifiableList(this.materialModels);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void addMeshModel(DefaultMeshModel meshModel) {
/* 566 */     this.meshModels.add(meshModel);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void removeMeshModel(DefaultMeshModel meshModel) {
/* 576 */     this.meshModels.remove(meshModel);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void addMeshModels(Collection<? extends DefaultMeshModel> meshModels) {
/* 587 */     for (DefaultMeshModel meshModel : meshModels)
/*     */     {
/* 589 */       addMeshModel(meshModel);
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
/*     */   public DefaultMeshModel getMeshModel(int index) {
/* 601 */     return this.meshModels.get(index);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void clearMeshModels() {
/* 609 */     this.meshModels.clear();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public List<MeshModel> getMeshModels() {
/* 615 */     return Collections.unmodifiableList((List)this.meshModels);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void addNodeModel(DefaultNodeModel nodeModel) {
/* 625 */     this.nodeModels.add(nodeModel);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void removeNodeModel(DefaultNodeModel nodeModel) {
/* 635 */     this.nodeModels.remove(nodeModel);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void addNodeModels(Collection<? extends DefaultNodeModel> nodeModels) {
/* 646 */     for (DefaultNodeModel nodeModel : nodeModels)
/*     */     {
/* 648 */       addNodeModel(nodeModel);
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
/*     */   public DefaultNodeModel getNodeModel(int index) {
/* 660 */     return this.nodeModels.get(index);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void clearNodeModels() {
/* 668 */     this.nodeModels.clear();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public List<NodeModel> getNodeModels() {
/* 674 */     return Collections.unmodifiableList((List)this.nodeModels);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void addSceneModel(DefaultSceneModel sceneModel) {
/* 684 */     this.sceneModels.add(sceneModel);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void removeSceneModel(DefaultSceneModel sceneModel) {
/* 694 */     this.sceneModels.remove(sceneModel);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void addSceneModels(Collection<? extends DefaultSceneModel> sceneModels) {
/* 705 */     for (DefaultSceneModel sceneModel : sceneModels)
/*     */     {
/* 707 */       addSceneModel(sceneModel);
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
/*     */   public DefaultSceneModel getSceneModel(int index) {
/* 719 */     return this.sceneModels.get(index);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void clearSceneModels() {
/* 727 */     this.sceneModels.clear();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public List<SceneModel> getSceneModels() {
/* 733 */     return Collections.unmodifiableList((List)this.sceneModels);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void addSkinModel(DefaultSkinModel skinModel) {
/* 743 */     this.skinModels.add(skinModel);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void removeSkinModel(DefaultSkinModel skinModel) {
/* 753 */     this.skinModels.remove(skinModel);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void addSkinModels(Collection<? extends DefaultSkinModel> skinModels) {
/* 764 */     for (DefaultSkinModel skinModel : skinModels)
/*     */     {
/* 766 */       addSkinModel(skinModel);
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
/*     */   public DefaultSkinModel getSkinModel(int index) {
/* 778 */     return this.skinModels.get(index);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void clearSkinModels() {
/* 786 */     this.skinModels.clear();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public List<SkinModel> getSkinModels() {
/* 792 */     return Collections.unmodifiableList((List)this.skinModels);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void addTextureModel(DefaultTextureModel textureModel) {
/* 802 */     this.textureModels.add(textureModel);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void removeTextureModel(DefaultTextureModel textureModel) {
/* 812 */     this.textureModels.remove(textureModel);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void addTextureModels(Collection<? extends DefaultTextureModel> textureModels) {
/* 823 */     for (DefaultTextureModel textureModel : textureModels)
/*     */     {
/* 825 */       addTextureModel(textureModel);
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
/*     */   public DefaultTextureModel getTextureModel(int index) {
/* 837 */     return this.textureModels.get(index);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void clearTextureModels() {
/* 845 */     this.textureModels.clear();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public List<TextureModel> getTextureModels() {
/* 851 */     return Collections.unmodifiableList((List)this.textureModels);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public DefaultExtensionsModel getExtensionsModel() {
/* 857 */     return this.extensionsModel;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public DefaultAssetModel getAssetModel() {
/* 863 */     return this.assetModel;
/*     */   }
/*     */ }


/* Location:              C:\Users\DuduM\OneDrive\Área de Trabalho\mw10\modularwarfare-shining-2023.2.4.4f-fix10.jar!\de\javagl\jgltf\model\impl\DefaultGltfModel.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */