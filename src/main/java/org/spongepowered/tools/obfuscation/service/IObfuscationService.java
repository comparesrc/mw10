package org.spongepowered.tools.obfuscation.service;

import java.util.Collection;
import java.util.Set;
import org.spongepowered.tools.obfuscation.interfaces.IMixinAnnotationProcessor;

public interface IObfuscationService {
  Set<String> getSupportedOptions();
  
  Collection<ObfuscationTypeDescriptor> getObfuscationTypes(IMixinAnnotationProcessor paramIMixinAnnotationProcessor);
}


/* Location:              C:\Users\DuduM\OneDrive\Área de Trabalho\mw10\modularwarfare-shining-2023.2.4.4f-fix10.jar!\org\spongepowered\tools\obfuscation\service\IObfuscationService.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */