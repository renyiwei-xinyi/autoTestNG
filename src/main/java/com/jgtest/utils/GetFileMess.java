package com.jgtest.utils;

import io.restassured.path.json.JsonPath;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Properties;

/*
 *读取文件的信息
 */
public class GetFileMess {
    private static Properties properties;
    private static final Logger log = LogManager.getLogger(GetFileMess.class);

    /*
     *根据properties文件主键获取对应的值
     */
    public String getValue(String key,String propertiesFileName) {
        InputStream stream = this.getClass().getClassLoader().getResourceAsStream("\\"+propertiesFileName);
        properties = new Properties();
        try {
            properties.load(stream);
        } catch (IOException e){
            e.printStackTrace();
        }
        return properties.getProperty(key);
    }

    /*
     *获取文件路径
     */
    public String getFilePath(String directory,String fileName) {

        try{
            URL resource = this.getClass().getClassLoader().getResource(directory+"/"+fileName);
            assert resource != null;
            String filePath = resource.toURI().getPath();
            log.info("filePath："+filePath);
            return filePath;
        }catch (Exception e){
            log.info(e.getMessage());
            return null;
        }
    }

    /*
     *获取测试案例数据的预期结果
     */
    public String getCaseMessKeyValue(String caseMess,String key){
        JsonPath caseMessToJson = new JsonPath(caseMess);
        String value = caseMessToJson.get(key);
        return value;
    }
}
