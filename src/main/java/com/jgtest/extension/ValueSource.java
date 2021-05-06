package com.jgtest.extension;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD, ElementType.TYPE, ElementType.PARAMETER})
@Repeatable(ValueSources.class)
public @interface ValueSource {

    /*
       默认 多形参接收 单类型接收
       可选参数 multi 为true时 多形参 多类型接收 单迭代 可重复注解实现多迭代；为false时 单形参 单类型 多迭代
     */
    boolean multi() default true;

    short[] shorts() default {};

    byte[] bytes() default {};

    int[] ints() default {};

    long[] longs() default {};

    float[] floats() default {};

    double[] doubles() default {};

    char[] chars() default {};

    boolean[] booleans() default {};

    String[] strings() default {};

    Class<?>[] classes() default {};
}
