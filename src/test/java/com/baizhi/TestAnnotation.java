package com.baizhi;

import com.baizhi.annotation.LogAnnotation;
import com.baizhi.entity.Banner;
import lombok.Data;
import org.junit.Test;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

public class TestAnnotation {
    @Test
    public void test() throws ClassNotFoundException {
        Class<?> aClass = Class.
                forName("com.baizhi.Demo");
        LogAnnotation annotation1 = aClass.getAnnotation(LogAnnotation.class);
        String value1 = annotation1.value();
        Class[] class1 = annotation1.getClass1();
        for (int i = 0; i < class1.length; i++) {
            System.out.println(class1[i]);
        }
        System.out.println(value1);
        Method[] declaredMethods = aClass.getDeclaredMethods();
        for (int i = 0; i <declaredMethods.length ; i++) {
            Method declaredMethod = declaredMethods[i];
            if (declaredMethod.isAnnotationPresent(LogAnnotation.class)){
                LogAnnotation annotation = declaredMethod.getAnnotation(LogAnnotation.class);
                String value = annotation.value();
                System.out.println(value+"--");
            }
        }
    }
}

@LogAnnotation(value = "bbbb",getClass1 = {Demo.class, Banner.class})
class Demo{
    private String name;
    private String[] s;
    @LogAnnotation("aaaa")

    public void getName(){}
}
