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


    public static Iterator<Object[]> getYaml(YamlFileSource declaredAnnotation) {
        String[] files = declaredAnnotation.files();

        Stream<Object> objectStream = Arrays.stream(files)
                .map(ProviderUtil::openInputStream)
                .flatMap(ProviderUtil::yamlValues);

        return new DataIterator(objectStream);
    }

    public static Iterator<Object[]> getJson(JsonFileSource declaredAnnotation) {
        String[] files = declaredAnnotation.files();

        Stream<Object> objectStream = Arrays.stream(files)
                .map(ProviderUtil::openInputStream)
                .flatMap(ProviderUtil::jsonValues);

        return new DataIterator(objectStream);
    }

    public static Iterator<Object[]> getCsv(CsvFileSource declaredAnnotation) {
        String[] files = declaredAnnotation.files();

        Stream<Object> objectStream = Arrays.stream(files)
                .map(ProviderUtil::openInputStream)
                .flatMap(ProviderUtil::csvValues);

        return new DataIterator(objectStream);
    }

    public static Iterator<Object[]> getValue(ValueSource declaredAnnotation){
        Stream<? extends Cloneable> shorts = Stream.of(
                declaredAnnotation.shorts(), declaredAnnotation.bytes(),
                declaredAnnotation.ints(), declaredAnnotation.longs(),
                declaredAnnotation.floats(), declaredAnnotation.doubles(),
                declaredAnnotation.chars(), declaredAnnotation.booleans(), declaredAnnotation.strings(),
                declaredAnnotation.classes());
        Stream<? extends Cloneable> stream = shorts.filter((array) -> Array.getLength(array) > 0);
        List<Object> arrays = stream.collect(Collectors.toList());
        Object originalArray = arrays.get(0);
        Object[] arguments = IntStream.range(0, Array.getLength(originalArray))
                .mapToObj((index) -> Array.get(originalArray, index)).toArray();

        return new DataIterator(arguments);

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
                    .iterator()
                    ;

            return getObjectStream(iterator);

        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;

    }

    private static Stream<Object> getObjectStream(Iterable<Object> iterable) {

        return StreamSupport.stream(iterable.spliterator(), true);

    }

    private static Stream<Object> getObjectStream(Iterator<Object> iterator) {

        Iterable<Object> iterable = () -> iterator;

        return StreamSupport.stream(iterable.spliterator(), true);

    }

    private static Stream<Object> getObjectStream(Object jsonObject) {

        return Stream.of(jsonObject);

    }

    private static InputStream openInputStream(String resource) {
        try {
            return new FileInputStream(resource);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }
}
