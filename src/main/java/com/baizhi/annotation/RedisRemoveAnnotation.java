package com.baizhi.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
//描述方法
@Target({ElementType.TYPE, ElementType.METHOD})
public @interface RedisRemoveAnnotation {

}

