package com.huangTaiQi.www.service.impl;

import com.huangTaiQi.www.service.HiService;
import com.my_framework.www.annotation.Service;

/**
 * @author 14629
 */
@Service
public class HiServiceImpl implements HiService {

    public String hi(){
        return "hi";
    }
}
