package com.huangTaiQi.www.controller.impl;

import com.huangTaiQi.www.controller.BaseController;
import com.huangTaiQi.www.controller.ILikeController;
import com.huangTaiQi.www.service.impl.LikeServiceImpl;
import com.my_framework.www.annotation.Autowired;
import com.my_framework.www.annotation.Controller;
import com.my_framework.www.annotation.RequestMapping;
import com.my_framework.www.annotation.RequestParam;

import javax.servlet.http.HttpServletResponse;

/**
 * @author 14629
 */
@Controller
@RequestMapping("like")
public class LikeControllerImpl extends BaseController implements ILikeController {
    @Autowired
    LikeServiceImpl likeService;
    @RequestMapping
    public void isLike(@RequestParam("answerId") String answerId, HttpServletResponse response) throws Exception {
        String like = likeService.isLike(answerId);
        response.setContentType("text/json;charset=utf-8");
        response.getWriter().write(like);
    }
    @RequestMapping
    public void likeAnswer(@RequestParam("answerId") String answerId, HttpServletResponse response) throws Exception {
        String json = likeService.likeAnswer(answerId);
        response.setContentType("text/json;charset=utf-8");
        response.getWriter().write(json);
    }

}
