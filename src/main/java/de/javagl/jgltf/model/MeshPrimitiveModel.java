package de.javagl.jgltf.model;

import java.util.List;
import java.util.Map;

public interface MeshPrimitiveModel extends ModelElement {
  Map<String, AccessorModel> getAttributes();
  
  AccessorModel getIndices();
  
  int getMode();
  
  MaterialModel getMaterialModel();
  
  List<Map<String, AccessorModel>> getTargets();
}


/* Location:              C:\Users\DuduM\OneDrive\Área de Trabalho\mw10\modularwarfare-shining-2023.2.4.4f-fix10.jar!\de\javagl\jgltf\model\MeshPrimitiveModel.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */