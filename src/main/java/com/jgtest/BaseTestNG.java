package com.jgtest;

import com.jgtest.common.SetUpTearDown;
import com.jgtest.extension.*;
import org.testng.annotations.DataProvider;

import java.lang.reflect.Method;
import java.util.Iterator;


public class BaseTestNG extends SetUpTearDown {

    @DataProvider(name = "single")
    public static Iterator<Object[]> single(Method method){
        return getFileSource(method);
    }

    @DataProvider(name = "parallel", parallel = true)
    public static Iterator<Object[]> parallel(Method method){
        return getFileSource(method);
    }



    private static Iterator<Object[]> getFileSource(Method method){
        //  注解不能共存
        // todo: 实现注解可以共存 可以随意组合注解 形成一套参数 注入到用例中

        if (method.isAnnotationPresent(JsonFileSource.class)){
            JsonFileSource source = method.getDeclaredAnnotation(JsonFileSource.class);
            return ProviderUtil.getJson(source);
        }
        if (method.isAnnotationPresent(JsonFileSources.class)){
            JsonFileSources source = method.getDeclaredAnnotation(JsonFileSources.class);
            return ProviderUtil.getMultiJson(source);
        }
        if (method.isAnnotationPresent(YamlFileSource.class)){
            YamlFileSource source = method.getDeclaredAnnotation(YamlFileSource.class);
            return ProviderUtil.getYaml(source);
        }
        if (method.isAnnotationPresent(YamlFileSources.class)){
            YamlFileSources source = method.getDeclaredAnnotation(YamlFileSources.class);
            return ProviderUtil.getMultiYaml(source);
        }
        if (method.isAnnotationPresent(CsvFileSource.class)){
            CsvFileSource source = method.getDeclaredAnnotation(CsvFileSource.class);
            return ProviderUtil.getCsv(source);
        }
        if (method.isAnnotationPresent(CsvFileSources.class)){
            CsvFileSources source = method.getDeclaredAnnotation(CsvFileSources.class);
            return ProviderUtil.getMultiCsv(source);
        }
        if (method.isAnnotationPresent(ValueSource.class)){
            ValueSource source = method.getDeclaredAnnotation(ValueSource.class);
            return ProviderUtil.getValue(source);
        }
        if (method.isAnnotationPresent(ValueSources.class)){
            ValueSources source = method.getDeclaredAnnotation(ValueSources.class);
            return ProviderUtil.getMultiValues(source);
        }

        //如需扩展 再加if
        return null;
    }


}
