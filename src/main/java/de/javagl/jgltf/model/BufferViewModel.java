package de.javagl.jgltf.model;

import java.nio.ByteBuffer;

public interface BufferViewModel extends NamedModelElement {
  ByteBuffer getBufferViewData();
  
  BufferModel getBufferModel();
  
  int getByteOffset();
  
  int getByteLength();
  
  Integer getByteStride();
  
  Integer getTarget();
}


/* Location:              C:\Users\DuduM\OneDrive\Área de Trabalho\mw10\modularwarfare-shining-2023.2.4.4f-fix10.jar!\de\javagl\jgltf\model\BufferViewModel.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */