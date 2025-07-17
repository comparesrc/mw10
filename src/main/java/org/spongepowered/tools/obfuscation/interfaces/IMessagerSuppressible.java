package org.spongepowered.tools.obfuscation.interfaces;

import javax.annotation.processing.Messager;
import javax.lang.model.element.AnnotationMirror;
import javax.lang.model.element.AnnotationValue;
import javax.lang.model.element.Element;
import javax.tools.Diagnostic;
import org.spongepowered.tools.obfuscation.SuppressedBy;

public interface IMessagerSuppressible extends Messager {
  void printMessage(Diagnostic.Kind paramKind, CharSequence paramCharSequence, Element paramElement, SuppressedBy paramSuppressedBy);
  
  void printMessage(Diagnostic.Kind paramKind, CharSequence paramCharSequence, Element paramElement, AnnotationMirror paramAnnotationMirror, SuppressedBy paramSuppressedBy);
  
  void printMessage(Diagnostic.Kind paramKind, CharSequence paramCharSequence, Element paramElement, AnnotationMirror paramAnnotationMirror, AnnotationValue paramAnnotationValue, SuppressedBy paramSuppressedBy);
}


/* Location:              C:\Users\DuduM\OneDrive\Área de Trabalho\mw10\modularwarfare-shining-2023.2.4.4f-fix10.jar!\org\spongepowered\tools\obfuscation\interfaces\IMessagerSuppressible.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */