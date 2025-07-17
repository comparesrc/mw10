package com.modularwarfare.raycast.obb.bbloader;

import com.google.gson.annotations.SerializedName;

public class Cube {
  @SerializedName("name")
  public String name;
  
  @SerializedName("parent")
  public String parent;
  
  @SerializedName("from")
  public float[] from;
  
  @SerializedName("to")
  public float[] to;
}


/* Location:              C:\Users\DuduM\OneDrive\Área de Trabalho\mw10\modularwarfare-shining-2023.2.4.4f-fix10.jar!\com\modularwarfare\raycast\obb\bbloader\Cube.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */