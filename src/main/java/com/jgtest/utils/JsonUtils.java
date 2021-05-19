package com.jgtest.utils;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.module.paranamer.ParanamerModule;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;

public class JsonUtils {

    private static final Logger LOGGER = LogManager.getLogger(JsonUtils.class);

    private static final ObjectMapper objectMapper = new ObjectMapper();
    // 日起格式化
    private static final String STANDARD_FORMAT = "yyyy-MM-dd HH:mm:ss";
    static {
        // 添加模块 反序列化错误时 添加成功反序列化 成功
        objectMapper.registerModule(new ParanamerModule());
        //为了处理Date属性，需要调用 findAndRegisterModules 方法
        objectMapper.findAndRegisterModules();
        //对象的所有字段全部列入
        objectMapper.setSerializationInclusion(JsonInclude.Include.ALWAYS);
        //取消默认转换timestamps形式
        objectMapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS,false);
        //忽略空Bean转json的错误
        objectMapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS,false);
        //所有的日期格式都统一为以下的样式，即yyyy-MM-dd HH:mm:ss
        objectMapper.setDateFormat(new SimpleDateFormat(STANDARD_FORMAT));
        //忽略 在json字符串中存在，但是在java对象中不存在对应属性的情况。防止错误
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES,false);
    }

    /**
     * 输出json信息(格式化的Json字符串)
     *
     * @param obj
     * @throws Exception
     */
    public static void printJson(Object obj) {
        try {
            String json = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(obj);
            LOGGER.info("\n" + json);

        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }

    /**
     * 对象转Json格式字符串
     * @param obj 对象
     * @return Json格式字符串
     */
    public static <T> String parseObj2Str(T obj) {
        if (obj == null) {
            return null;
        }
        try {
            return obj instanceof String ? (String) obj : objectMapper.writeValueAsString(obj);
        } catch (JsonProcessingException e) {
            LOGGER.warn("Parse Object to String error : {}", e.getMessage());
            return null;
        }
    }

    /**
     * 对象转Json格式字符串(格式化的Json字符串)
     * @param obj 对象
     * @return 美化的Json格式字符串
     */
    public static <T> String parseObj2PrettyStr(T obj) {
        if (obj == null) {
            return null;
        }
        try {
            return obj instanceof String ? (String) obj : objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(obj);
        } catch (JsonProcessingException e) {
            LOGGER.warn("Parse Object to String error : {}", e.getMessage());
            return null;
        }
    }

    /**
     * 对象转换map对象
     * @param obj 对象
     * @return map对象
     */
    public static Map<String, Object> parseObj2Map(Object obj){
        String s = parseObj2Str(obj);
        Map<String, Object> hashMap = readValue(s, HashMap.class);
        return hashMap;
    }

    /**
     * 字符串转换为自定义对象
     * @param str 要转换的字符串
     * @param clazz 自定义对象的class对象
     * @return 自定义对象
     */
    public static <T> T readValue(String str, Class<T> clazz){
        if(StringUtils.isEmpty(str) || clazz == null){
            return null;
        }
        try {
            return clazz.equals(String.class) ? (T) str : objectMapper.readValue(str, clazz);
        } catch (Exception e) {
            LOGGER.warn("Parse String to Object error : {}", e.getMessage());
            return null;
        }
    }

    /**
     * 字符串转换为嵌套对象
     * @param str 要转换的字符串
     * @param typeReference 复杂的对象
     * @return 自定义对象
     */
    public static <T> T readValue(String str, TypeReference<T> typeReference) {
        if (StringUtils.isEmpty(str) || typeReference == null) {
            return null;
        }
        try {
            return (T) (typeReference.getType().equals(String.class) ? str : objectMapper.readValue(str, typeReference));
        } catch (IOException e) {
            LOGGER.warn("Parse String to Object error", e);
            return null;
        }
    }

    /**
     * 字符串转换为嵌套对象
     * @param str 要转换的字符串
     * @param collectionClazz 包装对象
     * @param elementClazzes  成员对象
     * @return 自定义对象
     */
    public static <T> T readValue(String str, Class<?> collectionClazz, Class<?>... elementClazzes) {
        JavaType javaType = objectMapper.getTypeFactory().constructParametricType(collectionClazz, elementClazzes);
        try {
            return objectMapper.readValue(str, javaType);
        } catch (IOException e) {
            LOGGER.warn("Parse String to Object error : {}" + e.getMessage());
            return null;
        }
    }

    /**
     * 字符串转换为嵌套对象
     * @param inputStream 文件流
     * @param collectionClazz 包装对象
     * @param elementClazzes  成员对象
     * @return 自定义对象
     */
    public static <T> T readValue(InputStream inputStream, Class<?> collectionClazz, Class<?>... elementClazzes) {
        JavaType javaType = objectMapper.getTypeFactory().constructParametricType(collectionClazz, elementClazzes);
        try {
            return objectMapper.readValue(inputStream, javaType);
        } catch (IOException e) {
            LOGGER.warn("Parse String to Object error : {}" + e.getMessage());
            return null;
        }
    }



    public static <T> T readValue(InputStream inputStream, Class<T> type){
        try {
            return objectMapper.readValue(inputStream, type);
        } catch (IOException e) {
            LOGGER.warn("read input stream to Object error : {}" + e.getMessage());
        }
        return null;
    }

    /**
     * 读取json文件解析为string对象
     * @param path
     * @return
     */
    public static String readFile(String path) {
        try {
            FileInputStream inputStream = new FileInputStream(path);

            Object o = objectMapper.readValue(inputStream, Object.class);

            return objectMapper.writeValueAsString(o);

        } catch (IOException e) {
            LOGGER.warn("read file to Object error : {}" + e.getMessage());
        }
        return null;

    }

    /**
     * 读取json文件解析为Java对象
     * @param path
     * @return
     */
    public static <T> T readFile(String path, Class<T> type) {
        try {
            FileInputStream inputStream = new FileInputStream(path);

            return objectMapper.readValue(inputStream, type);
        } catch (IOException e) {
            LOGGER.warn("read file to Object error : {}" + e.getMessage());
        }
        return null;

    }





    /**
     * 将对象内容写入json文件
     *
     * @param path
     * @param obj
     */
    public static void writeFile(String path, Object obj) {
        try {
            OutputStream outputStream = new FileOutputStream(path);
            objectMapper.writeValue(outputStream, obj);
        } catch (IOException e) {
            LOGGER.warn("write file to Object error : {}" + e.getMessage());
        }

    }







}
