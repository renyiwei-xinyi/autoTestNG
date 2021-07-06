package com.jgtest.utils;

import cn.hutool.core.lang.Console;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.LineNumberReader;

public class CallUtil {

    public static String PyCall(String cmd){

        String result = "";
        try {
            //这个方法是类似隐形开启了命令执行器，输入指令执行python脚本
            //python解释器位置（这里一定要用python解释器所在位置不要用python这个指令）+ python脚本所在路径（一定绝对路径）
            Process process = Runtime.getRuntime()
                    .exec(cmd);
            //这种方式获取返回值的方式是需要用python打印输出，然后java去获取命令行的输出，在java返回
            InputStreamReader ir = new InputStreamReader(process.getInputStream());
            LineNumberReader input = new LineNumberReader(ir);
            result = input.readLine();//中文的话这里可能会有乱码，可以尝试转一下不过问题不大
            //result1 = new String(result.getBytes("iso8859-1"),"utf-8");
            input.close();
            ir.close();
            int re = process.waitFor();
            Console.log(result);
        } catch (IOException | InterruptedException e) {
            Console.log("调用python脚本并读取结果时出错：" + e.getMessage());
        }
        return result;
    }
}
