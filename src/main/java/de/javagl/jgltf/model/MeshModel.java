package de.javagl.jgltf.model;

import java.util.List;

public interface MeshModel extends NamedModelElement {
  List<MeshPrimitiveModel> getMeshPrimitiveModels();
  
  float[] getWeights();
}


/* Location:              C:\Users\DuduM\OneDrive\Área de Trabalho\mw10\modularwarfare-shining-2023.2.4.4f-fix10.jar!\de\javagl\jgltf\model\MeshModel.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */