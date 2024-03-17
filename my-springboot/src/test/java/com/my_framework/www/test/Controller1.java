package com.my_framework.www.test;

import com.my_framework.www.core.annotation.bean.Autowired;
import com.my_framework.www.core.annotation.bean.Qualifier;
import com.my_framework.www.core.annotation.stereotype.Controller;

@Controller
public class Controller1 {
    @Qualifier("service2")
    @Autowired
    public IService service;
}
