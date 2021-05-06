package com.jgtest.demo;

import com.jgtest.BaseTestNG;
import org.testng.annotations.*;



public class TestTestNG extends BaseTestNG {




    @Test(enabled = false)
    void test_123() {
        // 忽略测试 和junit5 相比 就idea插件而言 会导致本地也无法执行 而不是在执行时进行过滤
    }



}
