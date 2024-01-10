package com.huangTaiQi.www.controller.impl;

import com.huangTaiQi.www.controller.BaseController;
import com.huangTaiQi.www.controller.IRightController;
import com.huangTaiQi.www.service.impl.RightServiceImpl;
import com.huangTaiQi.www.service.impl.UserServiceImpl;
import com.my_framework.www.security.annotation.Access;
import com.my_framework.www.core.annotation.bean.Autowired;
import com.my_framework.www.core.annotation.stereotype.Controller;
import com.my_framework.www.webmvc.annotation.Pattern;
import com.my_framework.www.webmvc.annotation.RequestMapping;
import com.my_framework.www.webmvc.annotation.RequestParam;

import javax.servlet.http.HttpServletResponse;

/**
 * @author 14629
 */
@Controller
@RequestMapping("right")
public class RightControllerImpl extends BaseController implements IRightController {
    @Autowired
    RightServiceImpl rightService;
    @Autowired
    UserServiceImpl userService;
    @Access(rightName = 5L)
    @Override
    @RequestMapping
    public void banRight(@Pattern @RequestParam("username") String username,
                         @Pattern @RequestParam("right") Integer right,
                         @Pattern @RequestParam("banTime") Long banTime,
                         HttpServletResponse response) throws Exception {
        rightService.banUserRight(username,right,banTime);
        response.getWriter().write("ok");
    }
    @Access(rightName = 5L)
    @Override
    @RequestMapping
    public void getRightByUsername(@Pattern @RequestParam("username") String username, HttpServletResponse response) throws Exception {
        String rightByUsername = rightService.getRightByUsername(username);
        response.getWriter().write(rightByUsername);
    }
    @Access(rightName = 5L)
    @Override
    @RequestMapping
    public void banUser(@Pattern @RequestParam("username") String username, HttpServletResponse response) throws Exception {
        rightService.banUserByUsername(username);
        response.getWriter().write("ok");
    }

}
