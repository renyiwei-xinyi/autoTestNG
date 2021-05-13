package com.jgtest.demo.test1;

import org.testng.annotations.Factory;
import org.testng.annotations.Test;

public class TestFactory {

    public static class SimpleTest {
        private final int para;
        public SimpleTest(int para) {
            this.para = para;
        }
        public SimpleTest() {
            this.para = 999;
        }
        @Test
        public void testMethodOne(){
            int value = para + 1;
            System.out.println("Test method one output: " + value);
        }
        @Test
        public void testMethodTwo(){
            int value = para + 2;
            System.out.println("Test method two output: " + value);
        }
        @Test
        public void simpleTest1(){
            System.out.println("simple test one");
        }
        @Test
        public void simpleTest2(){
            System.out.println("simple test two");
        }
    }

    public static class Simple12Test {
        @Test
        public void simpleTest1(){
            System.out.println("simple test one");
        }
        @Test
        public void simpleTest2(){
            System.out.println("simple test two");
        }
    }

    public static class SimpleTestFactory
    {
        @Factory
        public Object[] factoryMethod() {
            return new Object[] {new SimpleTest(), new Simple12Test()};
        }

        @Factory
        public Object[] factoryMethod_12312(){
            return new Object[] { new SimpleTest(0), new SimpleTest(10)};
        }
    }




}
