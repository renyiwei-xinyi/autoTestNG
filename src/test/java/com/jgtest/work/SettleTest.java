package com.jgtest.work;

import com.jgtest.BaseTestNG;
import org.testng.ITestContext;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

public class SettleTest extends BaseTestNG {

    @Test
    public void test_settle(ITestContext context){
        System.out.println(context.getAttribute("filter"));
    }

    @AfterMethod
    public void after_method(){

    }

    @Test
    public void test_12738197(ITestContext context){
        System.out.println(context.getAttribute("filter"));
    }
}
