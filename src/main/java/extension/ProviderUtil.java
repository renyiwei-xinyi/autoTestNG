package extension;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.csv.CsvSchema;
import org.yaml.snakeyaml.Yaml;

import java.io.*;
import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

public class ProviderUtil {

    public static Yaml yaml = new Yaml();

    public static ObjectMapper mapper = new ObjectMapper();

    public static CsvMapper csvMapper = new CsvMapper();

    public static CsvSchema schema = CsvSchema.emptySchema()
            .withHeader()
            .withColumnReordering(false);


    public static Iterator<Object[]> getYaml(String[] files) {

        Stream<Object> objectStream = Arrays.stream(files)
                .map(ProviderUtil::openInputStream)
                .flatMap(ProviderUtil::yamlValues);

        return new DataIterator(objectStream);
    }

    public static Iterator<Object[]> getJson(String[] files) {

        Stream<Object> objectStream = Arrays.stream(files)
                .map(ProviderUtil::openInputStream)
                .flatMap(ProviderUtil::jsonValues);

        return new DataIterator(objectStream);
    }

    public static Iterator<Object[]> getCsv(String[] files) {

        Stream<Object> objectStream = Arrays.stream(files)
                .map(ProviderUtil::openInputStream)
                .flatMap(ProviderUtil::csvValues);

        return new DataIterator(objectStream);
    }

    public static Iterator<Object[]> getValue(ValueSource source) {

        if (source.multi()) {
            // 多形参接收 多类型接收 二维数组模式
            return multiSourceValue(source).iterator();
        } else {
            // 单形参接收 单类型接收 一维数组模式
            Object[] objects = singleSourceValue(source);

            return new DataIterator(objects);
        }

    }

    public static Iterator<Object[]> getValues(ValueSources sources) {

        Stream<Object[]> source = Stream.of(sources.value())
                .filter(ValueSource::multi)
                .flatMap(ProviderUtil::multiSourceValue);

        return source.iterator();
    }

    public static Stream<Object> yamlValues(InputStream inputStream) {
        Iterable<Object> yamlObjects;
        yamlObjects = yaml.loadAll(inputStream);
        return getObjectStream(yamlObjects);
    }

    public static Stream<Object> jsonValues(InputStream inputStream) {
        Object jsonObject = null;
        //为了处理Date属性，需要调用 findAndRegisterModules 方法
        mapper.findAndRegisterModules();
        try {
            jsonObject = mapper.readValue(inputStream, Object.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return getObjectStream(jsonObject);
    }

    public static Stream<Object> csvValues(InputStream inputStream) {
        try {

            Iterator<Object> iterator = csvMapper
                    .readerFor(Object.class)
                    .with(schema)
                    .readValues(inputStream)
                    .readAll()
                    .iterator();

            return getObjectStream(iterator);

        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;

    }

    public static Stream<Object[]> multiSourceValue(ValueSource source){

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
