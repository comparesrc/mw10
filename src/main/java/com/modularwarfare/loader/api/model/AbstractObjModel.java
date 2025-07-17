package com.modularwarfare.loader.api.model;

import java.util.ConcurrentModificationException;
import java.util.List;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public abstract class AbstractObjModel {
  public abstract List<ObjModelRenderer> getParts();
  
  @SideOnly(Side.CLIENT)
  public abstract ObjModelRenderer getPart(String paramString);
  
  @SideOnly(Side.CLIENT)
  public abstract void renderAll(float paramFloat);
  
  @SideOnly(Side.CLIENT)
  public abstract void renderOnly(float paramFloat, String... paramVarArgs);
  
  @SideOnly(Side.CLIENT)
  public abstract void renderOnly(float paramFloat, ObjModelRenderer... paramVarArgs);
  
  @SideOnly(Side.CLIENT)
  public abstract void renderPart(float paramFloat, String paramString);
  
  @SideOnly(Side.CLIENT)
  public abstract void renderPart(float paramFloat, ObjModelRenderer paramObjModelRenderer);
  
  @SideOnly(Side.CLIENT)
  public abstract void renderAllExcept(float paramFloat, ObjModelRenderer... paramVarArgs);
  
  public abstract void clearDuplications() throws ConcurrentModificationException;
  
  public abstract boolean hasDuplications();
  
  protected abstract void addDuplication(ObjModelRenderer paramObjModelRenderer);
}


/* Location:              C:\Users\DuduM\OneDrive\Área de Trabalho\mw10\modularwarfare-shining-2023.2.4.4f-fix10.jar!\com\modularwarfare\loader\api\model\AbstractObjModel.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */