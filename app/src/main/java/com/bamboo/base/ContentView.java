package com.bamboo.base;

import java.lang.annotation.ElementType;
import java.lang.annotation.Target;

@Target(ElementType.TYPE)
@java.lang.annotation.Retention(value=java.lang.annotation.RetentionPolicy.RUNTIME)
public @interface ContentView {
	int value();
}
