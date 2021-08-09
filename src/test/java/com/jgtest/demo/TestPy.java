package com.jgtest.demo;

import cn.hutool.core.lang.Console;
import com.jgtest.BaseTestNG;
import lombok.Data;
import org.testng.*;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Test;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;
import java.util.stream.IntStream;


public class TestPy extends BaseTestNG {

    @BeforeSuite(enabled = false)
    public void beforeSuite(ITestContext context) {
        Console.log("load test data from file: ");
        // 读取筛选用例配置文件


        // 非空 设置卡点用例
        System.getProperties().setProperty("testngCaseNames", "123 222");

        context.setAttribute("passCase", "");
        context.setAttribute("failCase", "");
    }

    @BeforeMethod(alwaysRun = false)
    public void beforeMethod(Object[] data, ITestContext context, ITestResult result) {
        if (data.length == 0) {
            return;
        }
        System.out.println(data[0]);
        if (String.valueOf(data[0]).equals("123")) {

            throw new AssertionError("Skip this");
        }


    }


    //@YamlFileSource(files = "src/main/resources/testcase/test.yaml")
    @Test()
    public void test_126318() {

        System.out.println("123123");
    }

    @Test()
    public void test_137197() {
        String formatter = "yyyy-MM-dd HH:mm:ss.SSSSSS";

        String accGmtCreate = "2021-07-06 05:12:22.71311";
        String insGmtCreate = "2021-07-06 05:12:22.0";


        String accFormatter = formatter.substring(0, accGmtCreate.length());
        String insFormatter = formatter.substring(0, insGmtCreate.length());
        System.out.println(accFormatter);
        System.out.println(insFormatter);
        LocalDateTime accItemGmtCreate = LocalDateTime.parse(accGmtCreate, DateTimeFormatter.ofPattern(accFormatter));
        LocalDateTime instItemGmtCreate = LocalDateTime.parse(insGmtCreate, DateTimeFormatter.ofPattern(insFormatter));

        System.out.println(accItemGmtCreate.compareTo(instItemGmtCreate) > 0);
    }


    @Data
    static
    class Man implements Cloneable {
        private String name;
        private int age;
        private Double amount;
        private List<String> p;
        private char a;
        private String[] bb;
        private int[] cc;

        public Man(String name, int age) {
            this.name = name;
            this.age = age;
        }


        @Override
        protected Man clone() {
            Man clone = null;
            try {
                clone = (Man) super.clone();
            } catch (CloneNotSupportedException e) {
                e.printStackTrace();
            }
            return clone;
        }
    }

    @Test
    public void test_1321790() {
        Man man = new Man("霍格沃兹", 1);
        ArrayList<String> strings = new ArrayList<>(Collections.singletonList("222222"));
        man.setP(strings);
        man.setA('a');
        String[] str = new String[]{"3333"};
        int[] ints = {5,6};
        man.setBb(str);
        man.setCc(ints);
        man.setAmount(new Double("99999"));
        // 浅拷贝
        Man clone = man.clone();
        // 修改被拷贝对象
        man.setName("修仙");
        man.getP().add("11111111111");
        man.getBb()[0] = "4444";
        man.getCc()[0] = 6;
        man.setAmount(new Double("0"));
        System.out.println(man);
        System.out.println(clone.toString());


        //结论 浅拷贝
        // 基本元素类型 是隔离的
        // 引用数据类型  类 接口 基本容器 - [] map list object 是指向同一对象 不隔离

        // 基本元素类型 ： 基本数据类型 4类 8种 + 对应的 包装类 + String
        //   数值型 - 整数 / 浮点 - byte short int long / float double
        //   字符型 char
        //   布尔型 boolean
        //   以及基本数据类型的包装类 Byte Short Integer Long Float Double Character Boolean
        //   引用数据类型 String


    }

    @Test
    public void test_12372197(){
        String a = "1";
        String s = new String("1");
        Assert.assertFalse(a == s);
        HashSet<String> strings = new HashSet<>();
        strings.add(a);
//        a = "2";
//        strings.add(a);
        strings.add(s);
        System.out.println(strings);

    }

    @Test
    public void test_212378917(){
        Integer[] inputArray = new Integer[]{1, 3, 5, 7, 9};
        Integer[] out = new Integer[inputArray.length-1];
        //方法一 index就是自增索引
        AtomicInteger index=new AtomicInteger(0);

        List<Integer> collect1 = Arrays.stream(inputArray).map(x -> x + index.getAndIncrement()).collect(Collectors.toList());
        System.out.println(collect1);

        //方法二
        List<Integer> collect = IntStream.range(0, inputArray.length).mapToObj(x -> inputArray[x]).collect(Collectors.toList());
        System.out.println(collect);

    }
}
