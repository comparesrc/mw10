package org.spongepowered.asm.mixin.injection;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface ModifyVariable {
  String[] method();
  
  Slice slice() default @Slice;
  
  At at();
  
  boolean print() default false;
  
  int ordinal() default -1;
  
  int index() default -1;
  
  String[] name() default {};
  
  boolean argsOnly() default false;
  
  boolean remap() default true;
  
  int require() default -1;
  
  int expect() default 1;
  
  int allow() default -1;
  
  String constraints() default "";
}


/* Location:              C:\Users\DuduM\OneDrive\Área de Trabalho\mw10\modularwarfare-shining-2023.2.4.4f-fix10.jar!\org\spongepowered\asm\mixin\injection\ModifyVariable.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */