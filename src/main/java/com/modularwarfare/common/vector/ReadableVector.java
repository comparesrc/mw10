package com.modularwarfare.common.vector;

import java.nio.FloatBuffer;

public interface ReadableVector {
  float length();
  
  float lengthSquared();
  
  Vector store(FloatBuffer paramFloatBuffer);
}


/* Location:              C:\Users\DuduM\OneDrive\Área de Trabalho\mw10\modularwarfare-shining-2023.2.4.4f-fix10.jar!\com\modularwarfare\common\vector\ReadableVector.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */