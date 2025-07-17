package com.modularwarfare.raycast.obb.bbloader;

import com.google.gson.annotations.SerializedName;
import java.util.ArrayList;

public class BBInfo {
  @SerializedName("groups")
  public ArrayList<Group> groups;
  
  @SerializedName("cubes")
  public ArrayList<Cube> cubes;
}


/* Location:              C:\Users\DuduM\OneDrive\Área de Trabalho\mw10\modularwarfare-shining-2023.2.4.4f-fix10.jar!\com\modularwarfare\raycast\obb\bbloader\BBInfo.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */