package com.jgtest.utils;

import java.io.*;
import java.util.Map;
import java.util.Properties;

import static com.jgtest.utils.FileUtil.copyFiles;

//写Properties文件
public class WritePropertiesUtil {

    public static void writePropertiesFile(Map<String, String> data) {

        String filename = "environment.properties";
        String filename1 = "categories.json";
        String xmlOutputPath = ".\\src\\main\\resources\\temp";
        // 生成xml文件'
        File file = new File(xmlOutputPath + "\\" + filename);   //temp目录下

        // 判断是否存在,如果不存在,则创建
        if (!file.exists()) {
            boolean newFile = false;
            try {
                newFile = file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
            assert newFile;
        }
        String filepath = file.getAbsolutePath();

        Properties props = new Properties();
        InputStream is = WritePropertiesUtil.class.getClassLoader().getResourceAsStream(filepath);
        try {
            InputStream input = new FileInputStream(filepath);
            props.load(input);

            //在保存配置文件之前还需要取得该配置文件的输出流，
            if (data != null) {
                for (Map.Entry<String, String> entry : data.entrySet()) {
                    props.setProperty(entry.getKey(), entry.getValue());
                }
            }

            OutputStream out = new FileOutputStream(filepath);
            props.store(out, null);
            input.close();
            out.close();

            //拷贝environment.properties和categories.json文件至allure-results路径下
            File file1 = new File(xmlOutputPath + "\\" + filename1);
            File toFile = new File(".\\target\\allure-results\\"+filename);
            File toFile1 = new File(".\\target\\allure-results\\"+filename1);
            copyFiles(file,toFile);
            copyFiles(file1,toFile1);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
