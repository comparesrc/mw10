package org.spongepowered.tools.obfuscation.interfaces;

import java.util.Collection;
import org.spongepowered.asm.mixin.injection.selectors.ITargetSelectorRemappable;
import org.spongepowered.asm.obfuscation.mapping.common.MappingField;
import org.spongepowered.asm.obfuscation.mapping.common.MappingMethod;
import org.spongepowered.tools.obfuscation.mapping.IMappingConsumer;

public interface IObfuscationEnvironment {
  MappingMethod getObfMethod(ITargetSelectorRemappable paramITargetSelectorRemappable);
  
  MappingMethod getObfMethod(MappingMethod paramMappingMethod);
  
  MappingMethod getObfMethod(MappingMethod paramMappingMethod, boolean paramBoolean);
  
  MappingField getObfField(ITargetSelectorRemappable paramITargetSelectorRemappable);
  
  MappingField getObfField(MappingField paramMappingField);
  
  MappingField getObfField(MappingField paramMappingField, boolean paramBoolean);
  
  String getObfClass(String paramString);
  
  ITargetSelectorRemappable remapDescriptor(ITargetSelectorRemappable paramITargetSelectorRemappable);
  
  String remapDescriptor(String paramString);
  
  void writeMappings(Collection<IMappingConsumer> paramCollection);
}


/* Location:              C:\Users\DuduM\OneDrive\Área de Trabalho\mw10\modularwarfare-shining-2023.2.4.4f-fix10.jar!\org\spongepowered\tools\obfuscation\interfaces\IObfuscationEnvironment.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */