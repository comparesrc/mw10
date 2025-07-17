package org.spongepowered.tools.obfuscation.interfaces;

import org.spongepowered.asm.mixin.injection.selectors.ITargetSelectorRemappable;
import org.spongepowered.asm.obfuscation.mapping.IMapping;
import org.spongepowered.asm.obfuscation.mapping.common.MappingField;
import org.spongepowered.asm.obfuscation.mapping.common.MappingMethod;
import org.spongepowered.tools.obfuscation.ObfuscationData;
import org.spongepowered.tools.obfuscation.mirror.TypeHandle;

public interface IObfuscationDataProvider {
  <T> ObfuscationData<T> getObfEntryRecursive(ITargetSelectorRemappable paramITargetSelectorRemappable);
  
  <T> ObfuscationData<T> getObfEntry(ITargetSelectorRemappable paramITargetSelectorRemappable);
  
  <T> ObfuscationData<T> getObfEntry(IMapping<T> paramIMapping);
  
  ObfuscationData<MappingMethod> getObfMethodRecursive(ITargetSelectorRemappable paramITargetSelectorRemappable);
  
  ObfuscationData<MappingMethod> getObfMethod(ITargetSelectorRemappable paramITargetSelectorRemappable);
  
  ObfuscationData<MappingMethod> getRemappedMethod(ITargetSelectorRemappable paramITargetSelectorRemappable);
  
  ObfuscationData<MappingMethod> getObfMethod(MappingMethod paramMappingMethod);
  
  ObfuscationData<MappingMethod> getRemappedMethod(MappingMethod paramMappingMethod);
  
  ObfuscationData<MappingField> getObfFieldRecursive(ITargetSelectorRemappable paramITargetSelectorRemappable);
  
  ObfuscationData<MappingField> getObfField(ITargetSelectorRemappable paramITargetSelectorRemappable);
  
  ObfuscationData<MappingField> getObfField(MappingField paramMappingField);
  
  ObfuscationData<String> getObfClass(TypeHandle paramTypeHandle);
  
  ObfuscationData<String> getObfClass(String paramString);
}


/* Location:              C:\Users\DuduM\OneDrive\Área de Trabalho\mw10\modularwarfare-shining-2023.2.4.4f-fix10.jar!\org\spongepowered\tools\obfuscation\interfaces\IObfuscationDataProvider.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */