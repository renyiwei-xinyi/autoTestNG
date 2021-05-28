package com.jgtest.demo.test2;

import cn.hutool.json.JSONUtil;
import com.jgtest.BaseTestNG;
import com.jgtest.extension.JsonFileSource;
import com.jgtest.provider.ParameterContext;
import org.testng.Assert;
import org.testng.ITestContext;
import org.testng.ITestNGMethod;
import org.testng.annotations.*;

import java.util.Arrays;


public class TestTestNG extends BaseTestNG {




    @Test(enabled = false)
    void test_123() {
        // 忽略测试 和junit5 相比 就idea插件而言 会导致本地也无法执行 而不是在执行时进行过滤
    }

    @JsonFileSource(files = "src/main/resources/testcase/test3.json")
    @Test(dataProvider = "single")
    public void test_d_137129(Object o ,ITestContext context){
        context.setAttribute(ParameterContext.class.getName(), o);
    }

    @Test(dependsOnMethods = "test_d_137129")
    public void test_b_1379(ITestContext context){
        Object contextAttribute = context.getAttribute(ParameterContext.class.getName());
        String test = JSONUtil.parseObj(contextAttribute).getStr("test");
        System.out.println(test);
        Assert.assertEquals(test,"111","上下文中获取字段内容失败！！！");
    }


    @Test
    public void test_a_1379(ITestContext context){
        ITestNGMethod[] allTestMethods = context.getAllTestMethods();
        Arrays.stream(allTestMethods).forEach(System.out::println);

    }

    @Test(timeOut = 5000)
    public void test_z_1317() throws InterruptedException {
        int i = 0;
        while (true) {
            Thread.sleep(250); // custom poll interval 5000 / 250 = 20 time

            i++;
            if (i == 5){
                continue;// 终止本次执行 继续下个执行
            }else {
                System.out.println("sleep");
            }
            if (i == 10){
                break;// 跳出循环
            }
        }
        return; // 结束方法
        // Obtain the asynchronous result and perform assertions
    }



}
