package de.javagl.jgltf.model.gl;

import de.javagl.jgltf.model.NodeModel;

public interface TechniqueParametersModel {
  int getType();
  
  int getCount();
  
  String getSemantic();
  
  Object getValue();
  
  NodeModel getNodeModel();
}


/* Location:              C:\Users\DuduM\OneDrive\Área de Trabalho\mw10\modularwarfare-shining-2023.2.4.4f-fix10.jar!\de\javagl\jgltf\model\gl\TechniqueParametersModel.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */