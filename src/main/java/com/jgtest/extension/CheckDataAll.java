package com.jgtest.extension;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD, ElementType.TYPE, ElementType.PARAMETER})
public @interface CheckDataAll {

    CheckData[] value() ;
}
