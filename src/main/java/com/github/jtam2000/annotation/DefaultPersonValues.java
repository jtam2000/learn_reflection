package com.github.jtam2000.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME) //LEARNING: will retain this annotation until runtime,
@Target(ElementType.METHOD) //LEARNING: means that this annotation can only apply to a method only
public @interface DefaultPersonValues {

    //LEARNING: note that annotation attribute is a method, not a field
    //          example: String name()
    //          you can provide optional default values for each annotation attribute
    String name() default "<Anonymous>";
    int age() default 0;
}
