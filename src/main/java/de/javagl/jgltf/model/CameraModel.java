package de.javagl.jgltf.model;

import java.util.function.DoubleSupplier;
import java.util.function.Supplier;

public interface CameraModel extends NamedModelElement {
  CameraOrthographicModel getCameraOrthographicModel();
  
  CameraPerspectiveModel getCameraPerspectiveModel();
  
  float[] computeProjectionMatrix(float[] paramArrayOffloat, Float paramFloat);
  
  Supplier<float[]> createProjectionMatrixSupplier(DoubleSupplier paramDoubleSupplier);
}


/* Location:              C:\Users\DuduM\OneDrive\Área de Trabalho\mw10\modularwarfare-shining-2023.2.4.4f-fix10.jar!\de\javagl\jgltf\model\CameraModel.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */