package com.huangTaiQi.www.controller.impl;

import com.huangTaiQi.www.controller.BaseController;
import com.huangTaiQi.www.controller.IFollowController;
import com.huangTaiQi.www.model.dto.UserDTO;
import com.huangTaiQi.www.service.impl.FollowServiceImpl;
import com.huangTaiQi.www.utils.UserHolder;
import com.my_framework.www.core.annotation.bean.Autowired;
import com.my_framework.www.core.annotation.stereotype.Controller;
import com.my_framework.www.security.annotation.Limit;
import com.my_framework.www.webmvc.annotation.Pattern;
import com.my_framework.www.webmvc.annotation.RequestMapping;
import com.my_framework.www.webmvc.annotation.RequestParam;

import javax.servlet.http.HttpServletResponse;

import static com.huangTaiQi.www.constant.RegexConstants.NUMBER_REGEX;


/**
 * @author 14629
 */
@Controller
@RequestMapping("follow")
public class FollowControllerImpl extends BaseController implements IFollowController {
    @Autowired
    FollowServiceImpl followService;
    @Override
    @RequestMapping
    public void isFollowed(@Pattern(regex = NUMBER_REGEX) @RequestParam("id") String userId, HttpServletResponse response) throws Exception {
        String json=followService.isFollowed(userId);
        response.getWriter().write(json);
    }
    @Limit(maxToken = 10,ratePerSecond = 1,costPerRequest = 1)
    @Override
    @RequestMapping
    public void follow(@Pattern(regex = NUMBER_REGEX) @RequestParam("id") Long userId,HttpServletResponse response) throws Exception {
        String json=followService.follow(userId);
        response.getWriter().write(json);
    }
    @Override
    @RequestMapping
    public void getSameFollow(@Pattern(regex = NUMBER_REGEX) @RequestParam("id") Long userId,HttpServletResponse response) throws Exception {
        UserDTO user = UserHolder.getUser();
        if(user==null){
            return;
        }
        String json=followService.getSameFollow(userId,user.getId());
        response.getWriter().write(json);
    }
}
