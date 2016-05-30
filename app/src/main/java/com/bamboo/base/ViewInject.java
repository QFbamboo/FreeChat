package com.bamboo.base;


import java.lang.annotation.Target;
import java.lang.annotation.ElementType;

@Target(ElementType.FIELD)
@java.lang.annotation.Retention(value=java.lang.annotation.RetentionPolicy.RUNTIME)
public @interface ViewInject {
	int value();
}
        