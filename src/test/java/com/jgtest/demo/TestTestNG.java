package com.jgtest.demo;

import com.jgtest.BaseTestNG;
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

    @Test
    public void test_1379(){
        Object[] a = new Object[2];
        int length = Array.getLength(a);

//        Map<String, Object> map = new HashMap<>();
//        map.put("test", 123);
//        int length1 = Array.getLength(map);

        Object[] objects = IntStream.range(0, 1)
                .mapToObj((index) -> Array.get(a, index))
                .toArray();
        System.out.println(Arrays.asList(objects));
    }



}
