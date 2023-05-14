package com.huangTaiQi.www.controller.impl;

import com.huangTaiQi.www.controller.BaseController;
import com.huangTaiQi.www.controller.IFollowController;
import com.huangTaiQi.www.model.dto.UserDTO;
import com.huangTaiQi.www.service.impl.FollowServiceImpl;
import com.huangTaiQi.www.utils.UserHolder;
import com.my_framework.www.annotation.Autowired;
import com.my_framework.www.annotation.Controller;
import com.my_framework.www.annotation.RequestMapping;
import com.my_framework.www.annotation.RequestParam;

import javax.servlet.http.HttpServletResponse;


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
    public void isFollowed(@RequestParam("id") String userId, HttpServletResponse response) throws Exception {
        String json=followService.isFollowed(userId);
        response.getWriter().write(json);
    }
    @Override
    @RequestMapping
    public void follow(@RequestParam("id") Long userId,HttpServletResponse response) throws Exception {
        String json=followService.follow(userId);
        response.getWriter().write(json);
    }
    @Override
    @RequestMapping
    public void getSameFollow(@RequestParam("id") Long userId,HttpServletResponse response) throws Exception {
        UserDTO user = UserHolder.getUser();
        if(user==null){
            return;
        }
        String json=followService.getSameFollow(userId,user.getId());
        response.getWriter().write(json);
    }
}
