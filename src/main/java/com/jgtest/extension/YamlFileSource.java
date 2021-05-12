package com.jgtest.extension;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD, ElementType.TYPE, ElementType.PARAMETER})
@Repeatable(YamlFileSources.class)
public @interface YamlFileSource {
    String[] files(); /* 绝对路径 , 相对路径会抛异常找不到*/

    /*
       默认 单形参接收
       可选参数 multi 为true时 多形参 单迭代 可重复注解实现多迭代；为false时 单形参  多迭代
     */
    boolean multi() default false;

    Class<?> type() default Object.class;

}
