package com.jgtest.extension;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD, ElementType.TYPE, ElementType.PARAMETER})
@Repeatable(YamlFileSources.class)
public @interface YamlFileSource {
    String[] files(); /* 绝对路径 , 相对路径会抛异常找不到*/
}
