package com.my_framework.www.test;

import com.my_framework.www.core.annotation.bean.Autowired;
import com.my_framework.www.core.annotation.bean.Value;
import com.my_framework.www.core.annotation.stereotype.Service;

@Service
public class Service1 implements IService{
    @Value("${dhasjkda}")
    public String abc;
    @Autowired
    public IDao1 dao1;
}
