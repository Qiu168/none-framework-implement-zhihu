package com.huangTaiQi.www.controller.impl;


import com.alibaba.fastjson.JSON;
import com.huangTaiQi.www.controller.IUserController;
import com.huangTaiQi.www.model.dto.UserDTO;
import com.huangTaiQi.www.service.impl.UserServiceImpl;
import com.huangTaiQi.www.utils.ImgVerifyCode;
import com.huangTaiQi.www.utils.UserHolder;
import com.my_framework.www.annotation.Autowired;
import com.my_framework.www.annotation.Controller;
import com.my_framework.www.annotation.RequestMapping;
import com.my_framework.www.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;




import static com.huangTaiQi.www.constant.SessionConstants.IMG_CODE;


/**
 * @author 14629
 */
@Controller
@RequestMapping("user")
public class UserControllerImpl implements IUserController {

    @Autowired
    UserServiceImpl userService;

    @Override
    @RequestMapping("getImgVerifyCode")
    public void getImgVerifyCode(HttpServletRequest request, HttpServletResponse response) throws IOException {
        //生成验证码，并将验证码存入session中
        BufferedImage image = userService.imgCode(request.getSession());
        //将验证码图片响应给客户端
        response.setContentType("image/JPEG");
        ImgVerifyCode.output(image,response.getOutputStream());
    }


    @RequestMapping("sendEmail")
    public void sendEmail(@RequestParam("email") String email,
                          @RequestParam("imgCode") String imgCode,
                          HttpServletRequest request,
                          HttpServletResponse response) throws IOException {
        //验证邮箱，验证验证码，发送邮件，成功后将邮箱验证码存入redis，返回信息
        String message = userService.sendEmail(email, imgCode, (String) request.getSession().getAttribute(IMG_CODE));
        //返回信息
        response.setContentType("text/html;charset=utf-8");
        response.getWriter().write(message);
    }


    @RequestMapping("register")
    public void register(@RequestParam("email") String email,
                         @RequestParam("code") String emailCode,
                         @RequestParam("password") String password,
                         @RequestParam("repassword") String rePassword,
                         HttpServletResponse response)
            throws SQLException, IOException, NoSuchAlgorithmException {
        //验证，注册
        String message = userService.register(email, emailCode, password, rePassword);
        //返回信息
        response.setContentType("text/html;charset=utf-8");
        response.getWriter().write(message);
    }


    @RequestMapping("login")
    public void login(@RequestParam("usernameOrEmail") String usernameOrEmail,
                      @RequestParam("password") String password,
                      @RequestParam("imgCode") String imgCode,
                      HttpServletRequest request, HttpServletResponse response) throws Exception {
        String code = (String) request.getSession().getAttribute(IMG_CODE);
        String message = userService.login(usernameOrEmail, password, imgCode, code);
        //返回信息
        response.setContentType("text/html;charset=utf-8");
        System.out.println("message="+message);
        response.getWriter().write(message);
    }



    @RequestMapping("checkEmail")
    public void checkEmail(@RequestParam("email") String email,HttpServletRequest request, HttpServletResponse response) throws Exception {
        boolean hasEmail = userService.hasEmail(email);
        response.getWriter().write(String.valueOf(hasEmail));
    }

    @Override
    @RequestMapping("me")
    public void me(HttpServletRequest request, HttpServletResponse response) throws IOException {
        UserDTO user = UserHolder.getUser();
        String json = JSON.toJSONString(user);
        response.setContentType("text/json;charset=utf-8");
        response.getWriter().write(json);
    }

    @Override
    @RequestMapping("resetPassword")
    public void resetPassword(@RequestParam("email") String email,
                              @RequestParam("code") String emailCode,
                              @RequestParam("password") String password,
                              @RequestParam("repassword") String rePassword,
                              HttpServletResponse response)
            throws SQLException, IOException, NoSuchAlgorithmException {
        //验证，注册
        String message = userService.resetPassword(email, emailCode, password, rePassword);
        //返回信息
        response.setContentType("text/html;charset=utf-8");
        response.getWriter().write(message);
    }

}
