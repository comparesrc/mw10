package de.javagl.jgltf.model;

import java.util.List;

public interface GltfModel extends ModelElement {
  List<AccessorModel> getAccessorModels();
  
  List<AnimationModel> getAnimationModels();
  
  List<BufferModel> getBufferModels();
  
  List<BufferViewModel> getBufferViewModels();
  
  List<CameraModel> getCameraModels();
  
  List<ImageModel> getImageModels();
  
  List<MaterialModel> getMaterialModels();
  
  List<MeshModel> getMeshModels();
  
  List<NodeModel> getNodeModels();
  
  List<SceneModel> getSceneModels();
  
  List<SkinModel> getSkinModels();
  
  List<TextureModel> getTextureModels();
  
  ExtensionsModel getExtensionsModel();
  
  AssetModel getAssetModel();
}


/* Location:              C:\Users\DuduM\OneDrive\Área de Trabalho\mw10\modularwarfare-shining-2023.2.4.4f-fix10.jar!\de\javagl\jgltf\model\GltfModel.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */