package org.spongepowered.asm.service;

public interface IGlobalPropertyService {
  IPropertyKey resolveKey(String paramString);
  
  <T> T getProperty(IPropertyKey paramIPropertyKey);
  
  void setProperty(IPropertyKey paramIPropertyKey, Object paramObject);
  
  <T> T getProperty(IPropertyKey paramIPropertyKey, T paramT);
  
  String getPropertyString(IPropertyKey paramIPropertyKey, String paramString);
}


/* Location:              C:\Users\DuduM\OneDrive\Área de Trabalho\mw10\modularwarfare-shining-2023.2.4.4f-fix10.jar!\org\spongepowered\asm\service\IGlobalPropertyService.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */