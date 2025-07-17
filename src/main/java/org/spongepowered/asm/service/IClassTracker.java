package org.spongepowered.asm.service;

public interface IClassTracker {
  void registerInvalidClass(String paramString);
  
  boolean isClassLoaded(String paramString);
  
  String getClassRestrictions(String paramString);
}


/* Location:              C:\Users\DuduM\OneDrive\Área de Trabalho\mw10\modularwarfare-shining-2023.2.4.4f-fix10.jar!\org\spongepowered\asm\service\IClassTracker.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */