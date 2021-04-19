
import cn.hutool.json.JSONUtil;
import extension.CsvFileSource;
import extension.JsonFileSource;
import extension.YamlFileSource;
import org.testng.Assert;
import org.testng.annotations.*;


public class TestTestNG extends BaseTestNG {


    @Test
    @Parameters
    void test(@Optional("{\"test\":12}") String o) {
        System.out.println(o);
        //参数化测试 目前只支持 String 参数
    }

    @Test(enabled = false)
    void test_123() {
        // 忽略测试 和junit5 相比 就idea插件而言 会导致本地也无法执行 而不是在执行时进行过滤
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
    @Test(dataProvider = "single")
    public void test_1713123(Object s) throws InterruptedException {
        System.out.println(JSONUtil.parseObj(s).getStr("4564654612132465461321213132131321313"));
        System.out.println(s);
        Thread.sleep(1000);
    }

    @Parameters
    @Test
    void test_1231(@Optional("1") String s,
                   @Optional("2") String a) {
        Assert.assertEquals(s, "1");
        Assert.assertEquals(a, "2");
        System.out.println("PASS");


    }
}
