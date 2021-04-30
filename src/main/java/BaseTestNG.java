import extension.*;
import org.testng.annotations.DataProvider;

import java.lang.reflect.Method;
import java.util.Iterator;


public class BaseTestNG {

    @DataProvider(name = "single")
    public static Iterator<Object[]> single(Method method){
        Iterator<Object[]> fileSource = getFileSource(method);
        return fileSource;
    }

    @DataProvider(name = "parallel", parallel = true)
    public static Iterator<Object[]> parallel(Method method){
        return getFileSource(method);
    }



    private static Iterator<Object[]> getFileSource(Method method){
        //  注解不能共存
        if (method.isAnnotationPresent(YamlFileSource.class)){
            YamlFileSource yamlFileSource = method.getDeclaredAnnotation(YamlFileSource.class);
            return ProviderUtil.getYaml(yamlFileSource);
        }
        if (method.isAnnotationPresent(JsonFileSource.class)){
            JsonFileSource jsonFileSource = method.getDeclaredAnnotation(JsonFileSource.class);
            return ProviderUtil.getJson(jsonFileSource);
        }
        if (method.isAnnotationPresent(CsvFileSource.class)){
            CsvFileSource csvFileSource = method.getDeclaredAnnotation(CsvFileSource.class);
            return ProviderUtil.getCsv(csvFileSource);
        }
        if (method.isAnnotationPresent(ValueSource.class)){
            ValueSource valueSource = method.getDeclaredAnnotation(ValueSource.class);
            return ProviderUtil.getValue(valueSource);
        }
        if (method.isAnnotationPresent(ValueSources.class)){
            ValueSources valueSources = method.getDeclaredAnnotation(ValueSources.class);
            return ProviderUtil.getValues(valueSources);
        }

        //如需扩展 再加if
        return null;
    }


}
