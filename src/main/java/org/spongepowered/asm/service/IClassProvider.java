package org.spongepowered.asm.service;

import java.net.URL;

public interface IClassProvider {
  @Deprecated
  URL[] getClassPath();
  
  Class<?> findClass(String paramString) throws ClassNotFoundException;
  
  Class<?> findClass(String paramString, boolean paramBoolean) throws ClassNotFoundException;
  
  Class<?> findAgentClass(String paramString, boolean paramBoolean) throws ClassNotFoundException;
}


/* Location:              C:\Users\DuduM\OneDrive\Área de Trabalho\mw10\modularwarfare-shining-2023.2.4.4f-fix10.jar!\org\spongepowered\asm\service\IClassProvider.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */