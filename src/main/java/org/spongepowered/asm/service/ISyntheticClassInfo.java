package org.spongepowered.asm.service;

import org.spongepowered.asm.mixin.extensibility.IMixinInfo;

public interface ISyntheticClassInfo {
  IMixinInfo getMixin();
  
  String getName();
  
  String getClassName();
  
  boolean isLoaded();
}


/* Location:              C:\Users\DuduM\OneDrive\Área de Trabalho\mw10\modularwarfare-shining-2023.2.4.4f-fix10.jar!\org\spongepowered\asm\service\ISyntheticClassInfo.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */