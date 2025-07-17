package de.javagl.jgltf.model;

import java.util.List;
import java.util.function.Supplier;

public interface NodeModel extends NamedModelElement {
  NodeModel getParent();
  
  List<NodeModel> getChildren();
  
  List<MeshModel> getMeshModels();
  
  SkinModel getSkinModel();
  
  CameraModel getCameraModel();
  
  void setMatrix(float[] paramArrayOffloat);
  
  float[] getMatrix();
  
  void setTranslation(float[] paramArrayOffloat);
  
  float[] getTranslation();
  
  void setRotation(float[] paramArrayOffloat);
  
  float[] getRotation();
  
  void setScale(float[] paramArrayOffloat);
  
  float[] getScale();
  
  void setWeights(float[] paramArrayOffloat);
  
  float[] getWeights();
  
  float[] computeLocalTransform(float[] paramArrayOffloat);
  
  float[] computeGlobalTransform(float[] paramArrayOffloat);
  
  Supplier<float[]> createGlobalTransformSupplier();
  
  Supplier<float[]> createLocalTransformSupplier();
}


/* Location:              C:\Users\DuduM\OneDrive\Área de Trabalho\mw10\modularwarfare-shining-2023.2.4.4f-fix10.jar!\de\javagl\jgltf\model\NodeModel.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */