import cn.hutool.json.JSONUtil;
import extension.CsvFileSource;
import extension.JsonFileSource;
import extension.ValueSource;
import extension.YamlFileSource;
import org.testng.Assert;
import org.testng.annotations.*;

import java.util.Arrays;
import java.util.stream.Stream;

public class TestProvider extends BaseTestNG {


    @BeforeMethod(description = "参数化测试 数据前置处理")
    public void before_method(Object[] date) {
        Stream<Object> stream = Arrays.stream(date);
        stream.forEach(System.out::println);
    }

    @AfterMethod
    public void after_method() {

    }


    @Test
    @Parameters
    void test(@Optional("{\"test\":12}") String o) {
        System.out.println(o);
        //参数化测试 目前只支持 String 参数
    }


    @YamlFileSource(files = {
            "src/test/resources/test.yaml",
            "src/test/resources/test2.yaml"
    })
    @Test(dataProvider = "single")
    public void test_1729127(Object s) throws InterruptedException {
        System.out.println(s);
        Thread.sleep(1000);
    }

    @JsonFileSource(files = {
            "src/test/resources/test3.json",
            "src/test/resources/test4.json"
    })
    @Test(dataProvider = "parallel")
    public void test_17123123(Object s) throws InterruptedException {
        System.out.println(s);
        Thread.sleep(1000);
    }

    @CsvFileSource(files = {
            "src/test/resources/test6.csv"
    })
    @Test(dataProvider = "parallel")
    public void test_1713123(Object s) throws InterruptedException {
        System.out.println(JSONUtil.parseObj(s).getStr("4564654612132465461321213132131321313"));
        System.out.println(s);
        Thread.sleep(1000);
    }

    @ValueSource(ints = {1, 2})
    @Test(dataProvider = "single")
    public void test_12739(int a) {
        System.out.println(a);
    }

    static class date{
        void getTest(){
            System.out.println("nihao");
        }
    }

    @ValueSource(classes = {date.class})
    @Test(dataProvider = "single")
    public void test_122339(Object a) {
        System.out.println(a);
    }

    @Parameters
    @Test
    void test_1231(@Optional("1") String s,
                   @Optional("2") int a,
                   @Optional("9998877.1") float b,
                   @Optional("12.8586") double c) {
        Assert.assertEquals(s, "1");
        Assert.assertEquals(a, 2);
        Assert.assertEquals(b, 9998877.1f);
        Assert.assertEquals(c, 12.8586);
        System.out.println("PASS");
    }
}
