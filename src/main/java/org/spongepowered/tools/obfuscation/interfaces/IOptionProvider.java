package org.spongepowered.tools.obfuscation.interfaces;

import java.util.List;

public interface IOptionProvider {
  String getOption(String paramString);
  
  String getOption(String paramString1, String paramString2);
  
  boolean getOption(String paramString, boolean paramBoolean);
  
  List<String> getOptions(String paramString);
}


/* Location:              C:\Users\DuduM\OneDrive\Área de Trabalho\mw10\modularwarfare-shining-2023.2.4.4f-fix10.jar!\org\spongepowered\tools\obfuscation\interfaces\IOptionProvider.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */