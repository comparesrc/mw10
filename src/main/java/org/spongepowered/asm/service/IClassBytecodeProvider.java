package org.spongepowered.asm.service;

import java.io.IOException;
import org.objectweb.asm.tree.ClassNode;

public interface IClassBytecodeProvider {
  ClassNode getClassNode(String paramString) throws ClassNotFoundException, IOException;
  
  ClassNode getClassNode(String paramString, boolean paramBoolean) throws ClassNotFoundException, IOException;
}


/* Location:              C:\Users\DuduM\OneDrive\Área de Trabalho\mw10\modularwarfare-shining-2023.2.4.4f-fix10.jar!\org\spongepowered\asm\service\IClassBytecodeProvider.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */