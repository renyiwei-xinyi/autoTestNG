package extension;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD, ElementType.TYPE, ElementType.PARAMETER})
@Repeatable(CheckDataAll.class)
public @interface CheckData {

    String[] jsonFiles() default {}; /* 绝对路径 , 相对路径会抛异常找不到*/

    String[] yamlFiles() default {}; /* 绝对路径 , 相对路径会抛异常找不到*/

    String[] csvFiles() default {}; /* 绝对路径 , 相对路径会抛异常找不到*/

}
