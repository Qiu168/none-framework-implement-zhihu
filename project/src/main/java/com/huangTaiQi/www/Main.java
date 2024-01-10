package com.huangTaiQi.www;

import com.my_framework.www.Application;
import com.my_framework.www.core.annotation.SpringBootApplication;


/**
 *
 * @author 14629
 */
@SpringBootApplication
public class Main {
    public static void main(String[] args) {
        Application.run(Main.class);
        System.out.println("Hello world");
    }
}