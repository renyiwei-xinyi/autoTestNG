package com.jgtest.demo;

import cn.hutool.core.lang.Console;
import com.jgtest.BaseTestNG;
import com.jgtest.extension.YamlFileSource;
import org.testng.*;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Test;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;


public class TestPy extends BaseTestNG {

    @BeforeSuite
    public void beforeSuite(ITestContext context){
        Console.log("load test data from file: " );
        // 读取筛选用例配置文件


            // 非空 设置卡点用例
            System.getProperties().setProperty("testngCaseNames", "123 222");

        context.setAttribute("passCase","");
        context.setAttribute("failCase","");
    }

    @BeforeMethod(alwaysRun = true)
    public void beforeMethod(Object[] data, ITestContext context, ITestResult result) {
        if (data.length == 0){return;}
        System.out.println(data[0]);
        if (String.valueOf(data[0]).equals("123")){

            throw new AssertionError("Skip this");
        }


    }


    //@YamlFileSource(files = "src/main/resources/testcase/test.yaml")
    @Test()
    public void test_126318(){

        System.out.println("123123");
    }

    @Test
    public void test_137197(){
        String s = "2021-07-06 05:12:22.782697";
        int length = s.length();
        System.out.println(length);
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSSSS");
        LocalDateTime accItemGmtCreate = LocalDateTime.parse("2021-07-06 03:05:59.99236", dateTimeFormatter);
        System.out.println(accItemGmtCreate);
    }
}
