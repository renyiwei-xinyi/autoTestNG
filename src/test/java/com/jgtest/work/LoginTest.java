package com.jgtest.work;

import com.jgtest.BaseTestNG;
import com.jgtest.api.WorkWeiXin;
import com.jgtest.extension.YamlFileSource;
import org.testng.ITestContext;
import org.testng.annotations.Test;

public class LoginTest extends BaseTestNG {


    @YamlFileSource(files = "src/test/java/com/jgtest/work/requestData/send_message_test.yaml")
    @Test
    public void test_login(ITestContext context){

        //构建请求参数
        String accessToken = WorkWeiXin.getInstance().getAccessToken();


        //业务驱动

        //获取响应

        //校验

        //存储上下文信息
    }
}
