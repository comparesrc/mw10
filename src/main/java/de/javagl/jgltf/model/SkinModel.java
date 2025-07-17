package de.javagl.jgltf.model;

import java.util.List;

public interface SkinModel extends NamedModelElement {
  float[] getBindShapeMatrix(float[] paramArrayOffloat);
  
  List<NodeModel> getJoints();
  
  NodeModel getSkeleton();
  
  AccessorModel getInverseBindMatrices();
  
  float[] getInverseBindMatrix(int paramInt, float[] paramArrayOffloat);
}


/* Location:              C:\Users\DuduM\OneDrive\Área de Trabalho\mw10\modularwarfare-shining-2023.2.4.4f-fix10.jar!\de\javagl\jgltf\model\SkinModel.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */