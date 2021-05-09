package com.jgtest.demo;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Factory;
import org.testng.annotations.Test;

public class TestData {

    public static class DataProviderTest
    {
        private int param;

        @Factory(dataProvider = "dataMethod")
        public DataProviderTest(int param) {
            this.param = param;
        }

        @DataProvider
        public static Object[][] dataMethod() {
            return new Object[][] { new Object[]{ 0 }, new Object[]{ 10 } };
        }

        @Test
        public void testMethodOne() {
            int opValue = param + 1;
            System.out.println("Test method one output: " + opValue);
        }

        @Test
        public void testMethodTwo() {
            int opValue = param + 2;
            System.out.println("Test method two output: " + opValue);
        }
    }
}
