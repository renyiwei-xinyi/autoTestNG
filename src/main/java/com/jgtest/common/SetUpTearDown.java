package com.jgtest.common;


import io.restassured.RestAssured;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeSuite;
import com.jgtest.utils.GetFileMess;

import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import static com.jgtest.utils.WritePropertiesUtil.writePropertiesFile;


/*
 *父类的注解可以被子类继承，所有的环境变量在父类进行配置
 *根据testng.xml文件传入的参数来选择环境参数，默认为yg_env
 */
public class SetUpTearDown {

    @BeforeSuite
    public void dataSetUp() {
        //todo: 执行前置sql
    }

    //环境配置
    @BeforeClass
    public void envSetUp() {
        String system = "env.properties";    //环境由filter配置
        GetFileMess getFileMess = new GetFileMess();
        RestAssured.baseURI = getFileMess.getValue("baseURI", system);
        RestAssured.basePath = getFileMess.getValue("basePath", system);
        RestAssured.port = Integer.parseInt(getFileMess.getValue("port", system));
    }

    /*
     *创建environment.properties并放到allure-results目录下，测试报告展现
     */
    @AfterSuite
    public void createEnvPropertiesForReport() {
        Map<String, String> data = new HashMap<>();
        String database = "iiaccount_db.properties";
        GetFileMess getFileMess = new GetFileMess();
        data.put("DatabaseLoginName", getFileMess.getValue("DP_Name", database));
        data.put("DatabaseLoginPass", getFileMess.getValue("DP_Password", database));
        data.put("DatabaseLoginIP", getFileMess.getValue("DP_IP", database));
        data.put("baseURI", RestAssured.baseURI + ":" + RestAssured.port + "/" + RestAssured.basePath);

        writePropertiesFile(data);
    }

    @AfterSuite
    public void dataTearDown() throws SQLException, IOException, ClassNotFoundException {
        //案例执行结束后，对数据池的数据进行清理（删除或更新状态）
        //todo: 执行后置sql
    }

}
