package com.huangTaiQi.www.controller.impl;

import com.huangTaiQi.www.controller.BaseController;
import com.huangTaiQi.www.controller.IRightController;
import com.huangTaiQi.www.service.impl.RightServiceImpl;
import com.my_framework.www.annotation.Autowired;
import com.my_framework.www.annotation.Controller;
import com.my_framework.www.annotation.RequestMapping;
import com.my_framework.www.annotation.RequestParam;

/**
 * @author 14629
 */
@Controller
@RequestMapping("right")
public class RightControllerImpl extends BaseController implements IRightController {
    @Autowired
    RightServiceImpl rightService;
    @RequestMapping
    public void banRight(@RequestParam("right") Integer right,@RequestParam("banTime") Long banTime){
        rightService.banUserRight(right,banTime);
    }
}
