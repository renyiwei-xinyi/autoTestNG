package com.jgtest.demo;

import cn.hutool.core.io.file.FileWriter;
import cn.hutool.http.HttpUtil;
import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import org.testng.annotations.Test;

public class Tool {

    private static final String URL = "";
    @Test
    public void getData(){
        JSONArray list = new JSONArray();
        JSONArray list2 = new JSONArray();

        String s = HttpUtil.get(URL);
        JSONObject jsonObject = JSONUtil.parseObj(s);
        JSONArray objects = JSONUtil.parseArray(jsonObject.getByPath("data.dataSource"));
        objects.forEach(o -> {
            JSONObject source = JSONUtil.parseObj(o);
            JSONObject data = new JSONObject();
            data.set(String.valueOf(source.getByPath("labName")),
                    source.getByPath("envConfig.configFilePath"));
            list.add(data);
            list2.add(source.getByPath("envConfig.configFilePath"));
        });
        System.out.println(list.toStringPretty());
        FileWriter fileWriter = new FileWriter("lab.json");
        FileWriter data = new FileWriter("data.json");

        fileWriter.write(list.toStringPretty());
        data.write(list2.toStringPretty());


    }
}
