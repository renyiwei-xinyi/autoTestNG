package com.jgtest.demo;

import cn.hutool.json.JSONUtil;
import com.jgtest.BaseTestNG;
import com.jgtest.extension.*;
import lombok.Data;
import org.testng.Assert;
import org.testng.ITestContext;
import org.testng.ITestResult;
import org.testng.annotations.*;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Iterator;
import java.util.Map;
import java.util.stream.Stream;


public class TestProvider extends BaseTestNG {


    @BeforeMethod(description = "参数化测试 数据前置处理")
    public void before_method(Object[] data) {

        Stream<Object> stream = Arrays.stream(data);
        stream.forEach(System.out::println);
    }

    @AfterMethod
    public void after_method(Object[] data, Method method, ITestResult result, ITestContext context) {
        if (method.isAnnotationPresent(CheckDataAll.class) || method.isAnnotationPresent(CheckData.class)) {
            CheckDataAll checkDataAll = method.getDeclaredAnnotation(CheckDataAll.class);
            CheckData checkData = method.getDeclaredAnnotation(CheckData.class);

        }

        Stream<Object> stream = Arrays.stream(data);
        stream.forEach(System.out::println);
        System.out.println(result);
    }


    @Test
    @Parameters
    void test(@Optional("{\"test\":12}") String o) {
        System.out.println(o);
        //参数化测试 目前只支持 String 参数
    }


    @YamlFileSource(files = "src/main/resources/testcase/test.yaml", multi = true)
    @Test(dataProvider = "single")
    public void test_1729127(Object s, Object o, ITestContext context) throws InterruptedException {
        System.out.println(s);
        System.out.println(context);
        Thread.sleep(1000);
    }


    @JsonFileSource(files = {
            "src/main/resources/testcase/test3.json",
            "src/main/resources/testcase/test4.json"
    })
    @Test(dataProvider = "single")
    public void test_17123123(Object s) {
        System.out.println(s);
        //Thread.sleep(1000);
    }

    @JsonFileSource(files = {
            "src/main/resources/testcase/test3.json",
            "src/main/resources/testcase/test4.json"}, multi = true)
    @JsonFileSource(files = {
            "src/main/resources/testcase/test3.json",
            "src/main/resources/testcase/test4.json"}, multi = true)
    @Test(dataProvider = "single")
    public void test_1712312123123(Object s, Object a) throws InterruptedException {
        System.out.println(s);
        //Thread.sleep(1000);
    }

    @ValueSource(ints = {1, 2})
    @Test(dataProvider = "single")
    public void test_12739(int a, int b) {
        System.out.println(a);
    }

    @CsvFileSource(files = {
            "src/main/resources/testcase/test6.csv"
    })
    @Test(dataProvider = "single")
    public void test_1713123(String s) {
        //System.out.println(JSONUtil.parseObj(s).getStr("title1"));
        System.out.println(s);
        //Thread.sleep(1000);
    }


    @ValueSource(ints = {1, 2, 3}, multi = false)
    @Test(dataProvider = "single")
    public void test_1273912(int a) {
        System.out.println(a);
    }

    static class date {
        void getTest() {
            System.out.println("nihao");
        }
    }

    @ValueSource(classes = {date.class})
    @Test(dataProvider = "single")
    public void test_122339(Object a) {
        System.out.println(a);
    }


    @ValueSource(ints = {1, 2, 3})
    @ValueSource(ints = {2, 3, 4})
    @Test(dataProvider = "single", invocationCount = 1)
    public void test_1728(Object a, Object b, Object c) {
        System.out.println(a);
        //System.out.println(b);
    }

    @ValueSource(ints = 1, booleans = true, multi = true)
    @Test(dataProvider = "single")
    public void test_12712339(Object a) {
        System.out.println(a);
        //不同的数据类型 数据驱动会 按照 迭代来进行; 设置 单参数的话 第二个数据类型会被跳过
        //System.out.println(b);

    }

    @Data
    static class DataAll {
        public String touser;
        public String toparty;
        public String totag;
        public String msgtype;
        public int agentid;
        public Context text;
        public int safe;
        public int enable_id_trans;
        public int enable_duplicate_check;
    }

    @Data
    static class Context {
        public String content;
    }

    @JsonFileSource(files = "src/main/resources/testcase/demo.json", type = DataAll.class)
    @Test(dataProvider = "single")
    public void test_172317(DataAll dataAll) {
        System.out.println(dataAll);

    }

    @Parameters
    @Test
    void test_1231(@Optional("1") String s,
                   @Optional("2") int a,
                   @Optional("9998877.1") float b,
                   @Optional("12.8586") double c) {
        Assert.assertEquals(s, "1");
        Assert.assertEquals(a, 2);
        Assert.assertEquals(b, 9998877.1f);
        Assert.assertEquals(c, 12.8586);
        System.out.println("PASS");
    }
}
