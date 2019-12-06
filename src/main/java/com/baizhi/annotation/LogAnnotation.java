package com.baizhi.annotation;

import org.apache.poi.ss.formula.functions.T;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
/**
 * 1.RetentionPolicy.SOURCE:注解只保留源文件，当java文件编译成class文件的时候，注解被遗弃；
 * 2.RrtentionPolicy.CLASS:注解只保留到class文件,但jvm加载class文件时候被遗弃,这是默认的周期;
 * 3.RetentionPolicy.RUNTIME:注解不仅被保存到class文件中,jvm加载class文件之后,仍然存在；
 *   3个生命周期分别对应于:Java源文件(.java文件)-->.class文件 --> 内存中的字节码
 * **/
@Retention(RetentionPolicy.RUNTIME)
//描述方法
@Target({ElementType.TYPE,ElementType.METHOD})
public @interface LogAnnotation {
    String value() default "";
   public abstract Class[] getClass1() default {};
}
