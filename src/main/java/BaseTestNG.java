import extension.*;
import org.testng.annotations.DataProvider;

import java.lang.reflect.Method;
import java.util.Iterator;


public class BaseTestNG {

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
        if (method.isAnnotationPresent(YamlFileSource.class)){
            YamlFileSource declaredAnnotation = method.getDeclaredAnnotation(YamlFileSource.class);
            return ProviderUtil.getYaml(declaredAnnotation);
        }
        if (method.isAnnotationPresent(JsonFileSource.class)){
            JsonFileSource declaredAnnotation = method.getDeclaredAnnotation(JsonFileSource.class);
            return ProviderUtil.getJson(declaredAnnotation);
        }
        if (method.isAnnotationPresent(CsvFileSource.class)){
            CsvFileSource declaredAnnotation = method.getDeclaredAnnotation(CsvFileSource.class);
            return ProviderUtil.getCsv(declaredAnnotation);
        }
        if (method.isAnnotationPresent(ValueSource.class)){
            ValueSource declaredAnnotation = method.getDeclaredAnnotation(ValueSource.class);
            Iterator<Object[]> value = ProviderUtil.getValue(declaredAnnotation);
            return value;
        }
        //如需扩展 再加if
        return null;
    }


}
