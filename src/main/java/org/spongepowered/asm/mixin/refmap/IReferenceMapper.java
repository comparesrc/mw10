package org.spongepowered.asm.mixin.refmap;

public interface IReferenceMapper {
  boolean isDefault();
  
  String getResourceName();
  
  String getStatus();
  
  String getContext();
  
  void setContext(String paramString);
  
  String remap(String paramString1, String paramString2);
  
  String remapWithContext(String paramString1, String paramString2, String paramString3);
}


/* Location:              C:\Users\DuduM\OneDrive\Área de Trabalho\mw10\modularwarfare-shining-2023.2.4.4f-fix10.jar!\org\spongepowered\asm\mixin\refmap\IReferenceMapper.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */