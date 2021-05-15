package com.jgtest.extension;

import com.jgtest.utils.CsvUtils;
import com.jgtest.utils.JsonUtils;
import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.constructor.Constructor;

import java.io.*;
import java.lang.reflect.Array;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

public class ProviderUtil {

    public static Iterator<Object[]> getJson(JsonFileSource source) {

        if (source.multi()) {
            // 多形参接收 多类型接收 二维数组模式
            return multiSource(source).iterator();
        } else {
            // 单形参接收 单类型接收 一维数组模式
            Stream<Object> objectStream = Arrays.stream(source.files())
                    .map(ProviderUtil::openInputStream)
                    .flatMap(inputStream -> jsonValues(inputStream,source.type()));

            return new DataIterator(objectStream);
        }
    }

    public static Iterator<Object[]> getMultiJson(JsonFileSources sources) {
        Stream<Object[]> source = Stream.of(sources.value())
                .filter(JsonFileSource::multi)
                .flatMap(ProviderUtil::multiSource);

        return source.iterator();
    }

    public static Iterator<Object[]> getYaml(YamlFileSource source) {
        if (source.multi()) {
            // 多形参接收 多类型接收 二维数组模式
            return multiSource(source).iterator();
        } else {
            // 单形参接收 单类型接收 一维数组模式
            Stream<Object> objectStream = Arrays.stream(source.files())
                    .map(ProviderUtil::openInputStream)
                    .flatMap(inputStream -> yamlValues(inputStream, source.type()));

            return new DataIterator(objectStream);
        }
    }

    public static Iterator<Object[]> getMultiYaml(YamlFileSources sources) {
        Stream<Object[]> source = Stream.of(sources.value())
                .filter(YamlFileSource::multi)
                .flatMap(ProviderUtil::multiSource);

        return source.iterator();
    }

    public static Iterator<Object[]> getCsv(CsvFileSource source) {
        if (source.multi()) {
            // 多形参接收 多类型接收 二维数组模式
            return multiSource(source).iterator();
        } else {
            // 单形参接收 单类型接收 一维数组模式
            Stream<Object> objectStream = Arrays.stream(source.files())
                    .map(ProviderUtil::openInputStream)
                    .flatMap(inputStream -> Objects.requireNonNull(csvValues(inputStream, source.type())));

            return new DataIterator(objectStream);
        }
    }

    public static Iterator<Object[]> getMultiCsv(CsvFileSources sources) {
        Stream<Object[]> source = Stream.of(sources.value())
                .filter(CsvFileSource::multi)
                .flatMap(ProviderUtil::multiSource);

        return source.iterator();
    }

    public static Iterator<Object[]> getValue(ValueSource source) {

        if (source.multi()) {
            // 多形参接收 多类型接收 二维数组模式
            return multiSource(source).iterator();
        } else {
            // 单形参接收 单类型接收 一维数组模式
            Object[] objects = singleSourceValue(source);

            return new DataIterator(objects);
        }

    }

    public static Iterator<Object[]> getMultiValues(ValueSources sources) {

        Stream<Object[]> source = Stream.of(sources.value())
                .filter(ValueSource::multi)
                .flatMap(ProviderUtil::multiSource);

        return source.iterator();
    }

    public static Stream<Object> yamlValues(InputStream inputStream, Class<?> type) {
        Constructor constructor = new Constructor(type);
        Yaml yaml = new Yaml(constructor);
        Iterable<Object> yamlObjects;
        yamlObjects = yaml.loadAll(inputStream);
        return getObjectStream(yamlObjects);
    }

    public static Stream<Object> jsonValues(InputStream inputStream, Class<?> type) {

        Object jsonObject = JsonUtils.readValue(inputStream, type);

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

    public static Stream<Object[]> multiSource(JsonFileSource source){

        Object[] objects = Stream.of(source.files())
                .map(ProviderUtil::openInputStream)
                .flatMap(inputStream -> jsonValues(inputStream, source.type()))
                .toArray();
        List<Integer> arrays = Collections.singletonList(1);
        Stream<Integer> stream = arrays.stream();
        return stream.map(o -> objects);
    }

    public static Stream<Object[]> multiSource(YamlFileSource source){

        Object[] objects = Stream.of(source.files())
                .map(ProviderUtil::openInputStream)
                .flatMap(inputStream -> yamlValues(inputStream, source.type()))
                .toArray();
        List<Integer> arrays = Collections.singletonList(1);
        Stream<Integer> stream = arrays.stream();
        return stream.map(o -> objects);
    }

    public static Stream<Object[]> multiSource(CsvFileSource source){

        Object[] objects = Stream.of(source.files())
                .map(ProviderUtil::openInputStream)
                .flatMap(inputStream -> csvValues(inputStream, source.type()))
                .toArray();
        List<Integer> arrays = Collections.singletonList(1);
        Stream<Integer> stream = arrays.stream();
        return stream.map(o -> objects);
    }

    public static Stream<Object[]> multiSource(ValueSource source){

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

    public static Object[] singleSourceValue(ValueSource source){

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
}
