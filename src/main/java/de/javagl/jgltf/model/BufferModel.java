package de.javagl.jgltf.model;

import java.nio.ByteBuffer;

public interface BufferModel extends NamedModelElement {
  String getUri();
  
  int getByteLength();
  
  ByteBuffer getBufferData();
}


/* Location:              C:\Users\DuduM\OneDrive\Área de Trabalho\mw10\modularwarfare-shining-2023.2.4.4f-fix10.jar!\de\javagl\jgltf\model\BufferModel.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */