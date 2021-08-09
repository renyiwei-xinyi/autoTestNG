package com.jgtest.extension;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD, ElementType.TYPE, ElementType.PARAMETER})
@Repeatable(JsonFileSources.class)
public @interface JsonFileSource {
    String[] files() default ""; /* 绝对路径 , 相对路径会抛异常找不到*/
}
