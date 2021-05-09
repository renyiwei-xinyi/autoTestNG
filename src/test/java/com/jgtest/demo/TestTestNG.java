package com.jgtest.demo;

import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.jgtest.BaseTestNG;
import com.jgtest.extension.JsonFileSource;
import org.testng.Assert;
import org.testng.ITestContext;
import org.testng.ITestNGMethod;
import org.testng.annotations.*;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.IntStream;


public class TestTestNG extends BaseTestNG {




    @Test(enabled = false)
    void test_123() {
        // 忽略测试 和junit5 相比 就idea插件而言 会导致本地也无法执行 而不是在执行时进行过滤
    }

    @JsonFileSource(files = "src/main/resources/testcase/test3.json")
    @Test(dataProvider = "single")
    public void test_d_137129(Object o ,ITestContext context){
        context.setAttribute("request", o);
    }

    @Test(dependsOnMethods = "test_d_137129")
    public void test_b_1379(ITestContext context){
        Object request = context.getAttribute("request");
        String test = JSONUtil.parseObj(request).getStr("test");
        System.out.println(test);
        Assert.assertEquals(test,"111","上下文中获取字段内容失败！！！");
    }


    @Test
    public void test_a_1379(ITestContext context){
        ITestNGMethod[] allTestMethods = context.getAllTestMethods();
        System.out.println(Arrays.asList(allTestMethods));

    }



}
