
import cn.hutool.json.JSONUtil;
import extension.CsvFileSource;
import extension.JsonFileSource;
import extension.YamlFileSource;
import org.testng.Assert;
import org.testng.annotations.*;



public class TestTestNG extends BaseTestNG {




    @Test(enabled = false)
    void test_123() {
        // 忽略测试 和junit5 相比 就idea插件而言 会导致本地也无法执行 而不是在执行时进行过滤
    }



}
