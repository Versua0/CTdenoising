package com.common.ctdenoising.service.Impl;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Java_PythonServiceImpl {

    //public static void main(String[] args1) {
    public static void run() {
        Process proc;
        try {          //有参数在后面加
            int a=18,b=23;
            String[] args =new String[]{"python","E:\\Document\\Code\\Python\\test.py",
                    String.valueOf(a), String.valueOf(b)};
            proc = Runtime.getRuntime().exec(args);// 执行py文件
            //用输入输出流来截取结果
            BufferedReader in = new BufferedReader(new InputStreamReader(proc.getInputStream()));
            String line = null;
            System.out.println(args[0]+" "+args[1]);
            System.out.println(a+"+"+b+"=");
            while ((line = in.readLine()) != null) {
                System.out.println(line);
            }
            in.close();
            proc.waitFor();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
