package de.javagl.jgltf.model;

public interface TextureModel extends NamedModelElement {
  Integer getMagFilter();
  
  Integer getMinFilter();
  
  Integer getWrapS();
  
  Integer getWrapT();
  
  ImageModel getImageModel();
}


/* Location:              C:\Users\DuduM\OneDrive\Área de Trabalho\mw10\modularwarfare-shining-2023.2.4.4f-fix10.jar!\de\javagl\jgltf\model\TextureModel.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */