package com.github.jtam2000.annotation;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
public @interface DefaultPersonValues {
    String name() default "<Anonymous>";
    int age() default 0;
}
