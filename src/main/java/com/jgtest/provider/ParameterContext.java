package com.jgtest.provider;

import lombok.Data;

/*
参数上下文
 */
@Data
public class ParameterContext {

    private Object json;

    private Object yaml;

    private Object random;

    private Object csv;

    private Object value;

    private Class<?> javaBean;
}
