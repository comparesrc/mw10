package org.spongepowered.tools.obfuscation.interfaces;

import java.util.List;
import org.spongepowered.tools.obfuscation.ObfuscationEnvironment;
import org.spongepowered.tools.obfuscation.mapping.IMappingConsumer;

public interface IObfuscationManager {
  void init();
  
  IObfuscationDataProvider getDataProvider();
  
  IReferenceManager getReferenceManager();
  
  IMappingConsumer createMappingConsumer();
  
  List<ObfuscationEnvironment> getEnvironments();
  
  void writeMappings();
  
  void writeReferences();
}


/* Location:              C:\Users\DuduM\OneDrive\Área de Trabalho\mw10\modularwarfare-shining-2023.2.4.4f-fix10.jar!\org\spongepowered\tools\obfuscation\interfaces\IObfuscationManager.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */