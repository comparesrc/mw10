package de.javagl.jgltf.model.gl;

import de.javagl.jgltf.model.NamedModelElement;
import java.util.List;

public interface ProgramModel extends NamedModelElement {
  ShaderModel getVertexShaderModel();
  
  ShaderModel getFragmentShaderModel();
  
  List<String> getAttributes();
}


/* Location:              C:\Users\DuduM\OneDrive\Área de Trabalho\mw10\modularwarfare-shining-2023.2.4.4f-fix10.jar!\de\javagl\jgltf\model\gl\ProgramModel.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */