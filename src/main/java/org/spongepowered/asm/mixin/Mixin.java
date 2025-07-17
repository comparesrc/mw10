package org.spongepowered.asm.mixin;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.TYPE})
@Retention(RetentionPolicy.CLASS)
public @interface Mixin {
  Class<?>[] value() default {};
  
  String[] targets() default {};
  
  int priority() default 1000;
  
  boolean remap() default true;
}


/* Location:              C:\Users\DuduM\OneDrive\Área de Trabalho\mw10\modularwarfare-shining-2023.2.4.4f-fix10.jar!\org\spongepowered\asm\mixin\Mixin.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */