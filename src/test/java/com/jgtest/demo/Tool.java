package com.jgtest.demo;

import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUnit;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.io.file.FileWriter;
import cn.hutool.http.HttpUtil;
import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import org.testng.annotations.Test;

public class Tool {

    private static final String URL = "";

    @Test
    public void getData() {
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

    @Test
    public void testDate() {
        DateTime date = DateUtil.date();
        DateTime age = DateUtil.parse("1996-08-28 06:00:00");

        int year = age.year();
        int month = age.month();

        int nowYear = date.year();
        int nowMonth = date.month();

        int ageYear = nowYear - year;
        int ageMonth = nowMonth - month;
        //比较月份 当前月份  小于 当年月份  不足一年 大于/等于 则足够一年

        if (nowMonth < month) {
            ageYear -= 1;
            ageMonth = 12 - month + nowMonth;
        }




        long betweenDay = DateUtil.between(age, date, DateUnit.DAY);


    }
}
