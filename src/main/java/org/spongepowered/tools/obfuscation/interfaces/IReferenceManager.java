package org.spongepowered.tools.obfuscation.interfaces;

import org.spongepowered.asm.mixin.injection.selectors.ITargetSelectorRemappable;
import org.spongepowered.asm.mixin.refmap.ReferenceMapper;
import org.spongepowered.asm.obfuscation.mapping.common.MappingField;
import org.spongepowered.asm.obfuscation.mapping.common.MappingMethod;
import org.spongepowered.tools.obfuscation.ObfuscationData;

public interface IReferenceManager {
  void setAllowConflicts(boolean paramBoolean);
  
  boolean getAllowConflicts();
  
  void write();
  
  ReferenceMapper getMapper();
  
  void addMethodMapping(String paramString1, String paramString2, ObfuscationData<MappingMethod> paramObfuscationData);
  
  void addMethodMapping(String paramString1, String paramString2, ITargetSelectorRemappable paramITargetSelectorRemappable, ObfuscationData<MappingMethod> paramObfuscationData);
  
  void addFieldMapping(String paramString1, String paramString2, ITargetSelectorRemappable paramITargetSelectorRemappable, ObfuscationData<MappingField> paramObfuscationData);
  
  void addClassMapping(String paramString1, String paramString2, ObfuscationData<String> paramObfuscationData);
}


/* Location:              C:\Users\DuduM\OneDrive\Área de Trabalho\mw10\modularwarfare-shining-2023.2.4.4f-fix10.jar!\org\spongepowered\tools\obfuscation\interfaces\IReferenceManager.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */