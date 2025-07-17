package de.javagl.jgltf.model;

public interface AccessorModel extends NamedModelElement {
  BufferViewModel getBufferViewModel();
  
  int getComponentType();
  
  Class<?> getComponentDataType();
  
  boolean isNormalized();
  
  int getComponentSizeInBytes();
  
  int getElementSizeInBytes();
  
  int getPaddedElementSizeInBytes();
  
  int getByteOffset();
  
  int getCount();
  
  ElementType getElementType();
  
  int getByteStride();
  
  AccessorData getAccessorData();
  
  Number[] getMin();
  
  Number[] getMax();
}


/* Location:              C:\Users\DuduM\OneDrive\Área de Trabalho\mw10\modularwarfare-shining-2023.2.4.4f-fix10.jar!\de\javagl\jgltf\model\AccessorModel.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */