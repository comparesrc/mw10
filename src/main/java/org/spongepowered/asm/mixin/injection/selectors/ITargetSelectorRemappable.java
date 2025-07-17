package org.spongepowered.asm.mixin.injection.selectors;

import org.spongepowered.asm.obfuscation.mapping.IMapping;
import org.spongepowered.asm.obfuscation.mapping.common.MappingField;
import org.spongepowered.asm.obfuscation.mapping.common.MappingMethod;

public interface ITargetSelectorRemappable extends ITargetSelectorByName {
  IMapping<?> asMapping();
  
  MappingMethod asMethodMapping();
  
  MappingField asFieldMapping();
  
  ITargetSelectorRemappable move(String paramString);
  
  ITargetSelectorRemappable transform(String paramString);
  
  ITargetSelectorRemappable remapUsing(MappingMethod paramMappingMethod, boolean paramBoolean);
}


/* Location:              C:\Users\DuduM\OneDrive\Área de Trabalho\mw10\modularwarfare-shining-2023.2.4.4f-fix10.jar!\org\spongepowered\asm\mixin\injection\selectors\ITargetSelectorRemappable.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */