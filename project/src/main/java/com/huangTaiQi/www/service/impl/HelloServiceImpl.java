package com.huangTaiQi.www.service.impl;

import com.huangTaiQi.www.service.HiService;
import com.my_framework.www.annotation.Autowired;
import com.my_framework.www.annotation.Service;
import com.huangTaiQi.www.service.HelloService;

@Service
public class HelloServiceImpl implements HelloService {

    @Autowired
    private HiService hiService;
    public void sayHello() {
        System.out.println("hello world!");
        System.out.println(hiService.hi());
    }
}
