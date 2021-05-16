package com.jgtest.api;

import com.jgtest.utils.HttpUtils;
import com.jgtest.utils.JsonUtils;
import okhttp3.HttpUrl;

import java.util.Map;
import java.util.Objects;

// 单例模式
public class WorkWeiXin {
    // volatile 同步源语 表示易于改变的 在多线程范文的时候 会被jvm刷新 多线程是可以看到实例的变化
    private static volatile WorkWeiXin workWeiXin;

    public static final String corpId = "ww56a0dac84fe980f3";

    public static final String secretDemo = "_89HY84tEe5RFe4rYjW5acrEbYPZ-PZjr3X9WGgn65Y";

    public static final String agentId = "1000002";

    private String accessToken = "";

    public static WorkWeiXin getInstance() {

        //双检锁 作用域越小越好 性能会好一点
        if (workWeiXin == null) {
            synchronized (WorkWeiXin.class) {

                if (workWeiXin == null) {
                    workWeiXin = new WorkWeiXin();
                    workWeiXin.setToken();
                }
            }

        }
        return workWeiXin;
    }


    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    private void setToken() {
        HttpUrl baseUrl = HttpUtils.getBaseUrl("https://qyapi.weixin.qq.com/cgi-bin/gettoken")
                .addQueryParameter("corpid", corpId)
                .addQueryParameter("corpsecret", secretDemo)
                .build();
        String string = HttpUtils.get(baseUrl);
        accessToken = (String) Objects.requireNonNull(JsonUtils.readValue(string, Map.class)).get("access_token");
    }


}
