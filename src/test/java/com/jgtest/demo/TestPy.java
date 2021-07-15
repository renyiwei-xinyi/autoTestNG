package com.jgtest.demo;

import cn.hutool.core.lang.Console;
import com.jgtest.BaseTestNG;
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

    @Test()
    public void test_137197(){
        String formatter = "yyyy-MM-dd HH:mm:ss.SSSSSS";

        String accGmtCreate = "2021-07-06 05:12:22.71311";
        String insGmtCreate = "2021-07-06 05:12:22.0";


        String accFormatter = formatter.substring(0, accGmtCreate.length());
        String insFormatter = formatter.substring(0, insGmtCreate.length());
        System.out.println(accFormatter);
        System.out.println(insFormatter);
        LocalDateTime accItemGmtCreate = LocalDateTime.parse(accGmtCreate, DateTimeFormatter.ofPattern(accFormatter));
        LocalDateTime instItemGmtCreate = LocalDateTime.parse(insGmtCreate, DateTimeFormatter.ofPattern(insFormatter));

        System.out.println(accItemGmtCreate.compareTo(instItemGmtCreate) > 0);
    }
}
