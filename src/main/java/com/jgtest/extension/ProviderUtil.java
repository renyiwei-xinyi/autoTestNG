package com.jgtest.extension;

import com.alibaba.fastjson.JSON;
import com.jgtest.exception.TestException;
import com.jgtest.utils.CsvUtils;
import com.jgtest.utils.JsonUtils;
import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.constructor.Constructor;

import java.io.*;
import java.lang.reflect.Array;
import java.lang.reflect.Method;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

public class ProviderUtil {

    public static Iterator<Object[]> getFileSource(Method method) {
        //  注解不能共存
        // todo: 实现注解可以共存 可以随意组合注解 形成一套参数 注入到用例中

        if (method.isAnnotationPresent(JsonFileSource.class)) {
            JsonFileSource source = method.getDeclaredAnnotation(JsonFileSource.class);
            return ProviderUtil.getSource(source, method);
        }
        if (method.isAnnotationPresent(JsonFileSources.class)) {
            JsonFileSources source = method.getDeclaredAnnotation(JsonFileSources.class);
            return ProviderUtil.getMultiSource(source, method);
        }
        if (method.isAnnotationPresent(YamlFileSource.class)) {
            YamlFileSource source = method.getDeclaredAnnotation(YamlFileSource.class);
            return ProviderUtil.getSource(source, method);
        }
        if (method.isAnnotationPresent(YamlFileSources.class)) {
            YamlFileSources source = method.getDeclaredAnnotation(YamlFileSources.class);
            return ProviderUtil.getMultiSource(source, method);
        }
        if (method.isAnnotationPresent(CsvFileSource.class)) {
            CsvFileSource source = method.getDeclaredAnnotation(CsvFileSource.class);
            return ProviderUtil.getSource(source, method);
        }
        if (method.isAnnotationPresent(CsvFileSources.class)) {
            CsvFileSources source = method.getDeclaredAnnotation(CsvFileSources.class);
            return ProviderUtil.getMultiSource(source, method);
        }
        if (method.isAnnotationPresent(ValueSource.class)) {
            ValueSource source = method.getDeclaredAnnotation(ValueSource.class);
            return ProviderUtil.getSource(source);
        }
        if (method.isAnnotationPresent(ValueSources.class)) {
            ValueSources source = method.getDeclaredAnnotation(ValueSources.class);
            return ProviderUtil.getMultiSource(source);
        }

        //如需扩展 再加if
        return null;
    }

    public static Iterator<Object[]> getIterator(Stream<Object> objectStream, boolean multi) {
        if (multi) {
            // 多形参接收 多类型接收 二维数组模式
            List<Integer> arrays = Collections.singletonList(1);
            Stream<Object[]> stream = arrays.stream().map(o -> objectStream.toArray());
            return stream.iterator();
        } else {
            // 单形参接收 多类型接收 二维数组模式
            return new DataIterator(objectStream);
        }
    }

    /**
     * 单注解获取方法
     *
     * @param source
     * @param method
     * @return
     */
    public static Iterator<Object[]> getSource(JsonFileSource source, Method method) {

        Class<?>[] parameterTypes = method.getParameterTypes();

        parameterTypes = Stream.of(parameterTypes)
                .filter(aClass -> !aClass.getName().equals("org.testng.ITestContext"))
                .toArray(Class<?>[]::new);

        boolean multi = parameterTypes.length == source.files().length;

        if (!multi) {
            throw new TestException("形参和数据不一致！");
        }

        Stream<Object> objectStream = multiSource(source, parameterTypes);

        return getIterator(objectStream, multi);
    }

    public static Iterator<Object[]> getSource(YamlFileSource source, Method method) {

        Class<?>[] parameterTypes = method.getParameterTypes();

        parameterTypes = Stream.of(parameterTypes)
                .filter(aClass -> !aClass.getName().equals("org.testng.ITestContext"))
                .toArray(Class<?>[]::new);

        boolean multi = parameterTypes.length > 1;

        Stream<Object> objectStream = multiSource(source, parameterTypes, multi);

        return getIterator(objectStream, multi);
    }

    public static Iterator<Object[]> getSource(CsvFileSource source, Method method) {

        Class<?>[] parameterTypes = method.getParameterTypes();

        parameterTypes = Stream.of(parameterTypes)
                .filter(aClass -> !aClass.getName().equals("org.testng.ITestContext"))
                .toArray(Class<?>[]::new);

        boolean multi = parameterTypes.length == source.files().length;

        if (!multi) {
            throw new TestException("形参和数据不一致！");
        }

        Stream<Object> objectStream = multiSource(source, parameterTypes);

        return getIterator(objectStream, true);
    }

    public static Iterator<Object[]> getSource(ValueSource source) {

        if (source.multi()) {
            // 多形参接收 多类型接收 二维数组模式
            return multiSource(source).iterator();
        } else {
            // 单形参接收 单类型接收 一维数组模式
            Object[] objects = singleSourceValue(source);

            return new DataIterator(objects);
        }

    }


    /**
     * 多注解获取方法
     *
     * @param sources
     * @param method
     * @return
     */
    public static Iterator<Object[]> getMultiSource(JsonFileSources sources, Method method) {
        Class<?>[] parameterTypes = method.getParameterTypes();
        parameterTypes = Stream.of(parameterTypes)
                .filter(aClass -> !aClass.getName().equals("org.testng.ITestContext"))
                .toArray(Class<?>[]::new);

        boolean multi = parameterTypes.length > 1;
        Class<?>[] finalParameterTypes = parameterTypes;
        Stream<Object[]> source = Stream.of(sources.value())
                .flatMap(source1 -> {
                    Stream<Object> objectStream = multiSource(source1, finalParameterTypes);

                    return Stream.of(1).map(o -> objectStream.toArray());
                });

        return source.iterator();
    }

    public static Iterator<Object[]> getMultiSource(YamlFileSources sources, Method method) {
        Class<?>[] parameterTypes = method.getParameterTypes();
        parameterTypes = Stream.of(parameterTypes)
                .filter(aClass -> !aClass.getName().equals("org.testng.ITestContext"))
                .toArray(Class<?>[]::new);

        boolean multi = parameterTypes.length > 1;
        Class<?>[] finalParameterTypes = parameterTypes;
        Stream<Object[]> source = Stream.of(sources.value())
                .flatMap(source1 -> {
                    Stream<Object> objectStream = multiSource(source1, finalParameterTypes, multi);

                    return Stream.of(1).map(o -> objectStream.toArray());
                });

        return source.iterator();
    }

    public static Iterator<Object[]> getMultiSource(CsvFileSources sources, Method method) {
        Class<?>[] parameterTypes = method.getParameterTypes();
        parameterTypes = Stream.of(parameterTypes)
                .filter(aClass -> !aClass.getName().equals("org.testng.ITestContext"))
                .toArray(Class<?>[]::new);

        boolean multi = parameterTypes.length > 1;
        Class<?>[] finalParameterTypes = parameterTypes;
        Stream<Object[]> source = Stream.of(sources.value())
                .flatMap(source1 -> {
                    Stream<Object> objectStream = multiSource(source1, finalParameterTypes);

                    return Stream.of(1).map(o -> objectStream.toArray());
                });

        return source.iterator();
    }

    public static Iterator<Object[]> getMultiSource(ValueSources sources) {
        Stream<Object[]> source = Stream.of(sources.value())
                .flatMap(ProviderUtil::multiSource);

        return source.iterator();
    }


    /**
     * 读取文件 转换为形参对象
     *
     * @param source
     * @return
     */
    public static Stream<Object> multiSource(JsonFileSource source, Class<?>[] parameterTypes) {

        // 用 index 就是自增索引
        AtomicInteger index = new AtomicInteger(0);

        return Stream.of(source.files())
                .map(ProviderUtil::openInputStream)
                .flatMap(inputStream -> jsonValues(inputStream, parameterTypes[index.getAndIncrement()]));
    }

    public static Stream<Object> multiSource(YamlFileSource source, Class<?>[] parameterTypes, boolean multi) {
        if (!multi) {
            return Stream.of(source.files())
                    .map(ProviderUtil::openInputStream)
                    .flatMap(inputStream -> yamlValues(inputStream, parameterTypes[0]));
        }
        // 用 index 就是自增索引
        AtomicInteger index = new AtomicInteger(0);

        return Stream.of(source.files())
                .map(ProviderUtil::openInputStream)
                .flatMap(inputStream -> yamlValues(inputStream, parameterTypes[index.getAndIncrement()]));

    }

    public static Stream<Object> multiSource(CsvFileSource source, Class<?>[] parameterTypes) {

        // 用 index 就是自增索引
        AtomicInteger index = new AtomicInteger(0);

        return Stream.of(source.files())
                .map(ProviderUtil::openInputStream)
                .flatMap(inputStream -> csvValues(inputStream, parameterTypes[index.getAndIncrement()]));
    }

    public static Stream<Object[]> multiSource(ValueSource source) {

        List<Object> arrays = Stream.of(source.shorts(), source.bytes(), source.ints(), source.longs(), source.floats(), source.doubles(), source.chars(), source.booleans(), source.strings(), source.classes())
                .filter((array) -> Array.getLength(array) > 0)
                .collect(Collectors.toList());

        return arrays.stream()
                .map(o ->
                        IntStream.range(0, Array.getLength(o))
                                .mapToObj((index) -> Array.get(o, index))
                                .toArray()
                );
    }

    public static Object[] singleSourceValue(ValueSource source) {

        List<Object> arrays = Stream.of(source.shorts(), source.bytes(), source.ints(), source.longs(), source.floats(), source.doubles(), source.chars(), source.booleans(), source.strings(), source.classes())
                .filter((array) -> Array.getLength(array) > 0)
                .collect(Collectors.toList());

        Object originalArray = arrays.get(0);
        return IntStream.range(0, Array.getLength(originalArray))
                .mapToObj((index) -> Array.get(originalArray, index)).toArray();
    }

    public static Stream<Object> getObjectStream(Iterable<Object> iterable) {

        return StreamSupport.stream(iterable.spliterator(), true);

    }

    public static Stream<Object> getObjectStream(Iterator<Object> iterator) {

        Iterable<Object> iterable = () -> iterator;

        return StreamSupport.stream(iterable.spliterator(), true);

    }

    public static Stream<Object> getObjectStream(Object jsonObject) {

        return Stream.of(jsonObject);

    }

    public static InputStream openInputStream(String resource) {
        try {
            return new FileInputStream(resource);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }


    public static Stream<Object> yamlValues(InputStream inputStream, Class<?> type) {
        Constructor constructor = new Constructor(type);
        Yaml yaml = new Yaml(constructor);
        Iterable<Object> yamlObjects;
        yamlObjects = yaml.loadAll(inputStream);
        return getObjectStream(yamlObjects);
    }

    public static Stream<Object> jsonValues(InputStream inputStream, Class<?> type) {
        Object jsonObject = null;
        try {
            jsonObject = JSON.parseObject(inputStream, type);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return getObjectStream(jsonObject);
    }

    public static Stream<Object> csvValues(InputStream inputStream, Class<?> type) {

        try {

            Iterator<Object> iterator = CsvUtils.csvMapper
                    .readerFor(type)
                    .with(CsvUtils.schema)
                    .readValues(inputStream)
                    .readAll()
                    .iterator();

            return getObjectStream(iterator);

        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;

    }
}
