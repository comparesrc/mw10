package com.modularwarfare.api;

import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public interface IMWModel {
  @SideOnly(Side.CLIENT)
  void renderPart(String paramString, float paramFloat);
}


/* Location:              C:\Users\DuduM\OneDrive\Área de Trabalho\mw9\modularwarfare-shining-2023.2.4.4f-fix9.jar!\com\modularwarfare\api\IMWModel.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */