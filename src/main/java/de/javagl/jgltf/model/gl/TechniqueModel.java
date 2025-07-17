package de.javagl.jgltf.model.gl;

import de.javagl.jgltf.model.NamedModelElement;
import java.util.Map;

public interface TechniqueModel extends NamedModelElement {
  ProgramModel getProgramModel();
  
  Map<String, TechniqueParametersModel> getParameters();
  
  Map<String, String> getAttributes();
  
  TechniqueParametersModel getAttributeParameters(String paramString);
  
  Map<String, String> getUniforms();
  
  TechniqueParametersModel getUniformParameters(String paramString);
  
  TechniqueStatesModel getTechniqueStatesModel();
}


/* Location:              C:\Users\DuduM\OneDrive\Área de Trabalho\mw10\modularwarfare-shining-2023.2.4.4f-fix10.jar!\de\javagl\jgltf\model\gl\TechniqueModel.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */