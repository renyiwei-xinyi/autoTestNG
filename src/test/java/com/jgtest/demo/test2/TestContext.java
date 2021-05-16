package com.jgtest.demo.test2;

import com.jgtest.provider.ParameterContext;
import com.jgtest.utils.JsonUtils;
import org.testng.ITestContext;
import org.testng.annotations.Test;

import java.util.Map;

public class TestContext {

    @Test
    public void test_1327197(ITestContext context){
        Object request = context.getAttribute(ParameterContext.class.getName());
        Map<String, Object> stringObjectMap = JsonUtils.parseObj2Map(request);
        System.out.println(stringObjectMap.get("test"));
    }
}
