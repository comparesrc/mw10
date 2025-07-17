package com.modularwarfare.common.vector;

import java.io.Serializable;
import java.nio.FloatBuffer;

public abstract class Matrix implements Serializable {
  public abstract Matrix setIdentity();
  
  public abstract Matrix invert();
  
  public abstract Matrix load(FloatBuffer paramFloatBuffer);
  
  public abstract Matrix loadTranspose(FloatBuffer paramFloatBuffer);
  
  public abstract Matrix negate();
  
  public abstract Matrix store(FloatBuffer paramFloatBuffer);
  
  public abstract Matrix storeTranspose(FloatBuffer paramFloatBuffer);
  
  public abstract Matrix transpose();
  
  public abstract Matrix setZero();
  
  public abstract float determinant();
}


/* Location:              C:\Users\DuduM\OneDrive\Área de Trabalho\mw10\modularwarfare-shining-2023.2.4.4f-fix10.jar!\com\modularwarfare\common\vector\Matrix.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */