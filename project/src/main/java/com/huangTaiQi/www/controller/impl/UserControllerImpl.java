package com.huangTaiQi.www.controller.impl;


import com.alibaba.fastjson.JSON;
import com.huangTaiQi.www.controller.BaseController;
import com.huangTaiQi.www.controller.IUserController;
import com.huangTaiQi.www.model.dto.UserDTO;
import com.huangTaiQi.www.service.impl.RightServiceImpl;
import com.huangTaiQi.www.service.impl.UserServiceImpl;
import com.huangTaiQi.www.utils.ImageUploader;
import com.huangTaiQi.www.utils.ImgVerifyCode;
import com.huangTaiQi.www.utils.UserHolder;
import com.my_framework.www.annotation.*;


import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;


import static com.huangTaiQi.www.constant.RegexConstants.EMAIL_REGEX;
import static com.huangTaiQi.www.constant.RegexConstants.NUMBER_REGEX;
import static com.huangTaiQi.www.constant.SessionConstants.IMG_CODE;


/**
 * @author 14629
 */
@Controller
@RequestMapping(value = "home")
public class UserControllerImpl extends BaseController implements IUserController {

    @Autowired
    UserServiceImpl userService;
    @Autowired
    RightServiceImpl rightService;

    @Override
    @RequestMapping(value = "getImgVerifyCode")
    public void getImgVerifyCode(HttpServletRequest request, HttpServletResponse response) throws IOException {
        //生成验证码，并将验证码存入session中
        BufferedImage image = userService.imgCode(request.getSession());
        //将验证码图片响应给客户端
        response.setContentType("image/JPEG");
        ImgVerifyCode.output(image,response.getOutputStream());
    }


    @Override
    @RequestMapping(value = "sendEmail")
    public void sendEmail(@Pattern(regex = EMAIL_REGEX,message = "email") @RequestParam("email") String email,
                          @RequestParam("imgCode") String imgCode,
                          HttpServletRequest request,
                          HttpServletResponse response) throws IOException {
        //验证邮箱，验证验证码，发送邮件，成功后将邮箱验证码存入redis，返回信息
        String message = userService.sendEmail(email, imgCode, (String) request.getSession().getAttribute(IMG_CODE));
        //返回信息
        response.setContentType("text/html;charset=utf-8");
        response.getWriter().write(message);
    }


    @Override
    @RequestMapping(value = "register")
    public void register(@Pattern(regex = EMAIL_REGEX,message = "email") @RequestParam("email") String email,
                         @Pattern @RequestParam("code") String emailCode,
                         @Pattern @RequestParam("password") String password,
                         @Pattern @RequestParam("repassword") String rePassword,
                         HttpServletResponse response)
            throws SQLException, IOException, NoSuchAlgorithmException {
        //验证，注册
        String message = userService.register(email, emailCode, password, rePassword);
        //返回信息
        response.setContentType("text/html;charset=utf-8");
        response.getWriter().write(message);
    }

    @Override
    @RequestMapping(value = "login",method = "post")
    public void login(@Pattern @RequestParam("usernameOrEmail") String usernameOrEmail,
                      @Pattern @RequestParam("password") String password,
                      @Pattern @RequestParam("imgCode") String imgCode,
                      HttpServletRequest request, HttpServletResponse response) throws Exception {
        String code = (String) request.getSession().getAttribute(IMG_CODE);
        String message = userService.login(usernameOrEmail, password, imgCode, code);
        //返回信息
        response.setContentType("text/html;charset=utf-8");
        System.out.println("message="+message);
        response.getWriter().write(message);
    }


    @Override
    @RequestMapping(value = "checkEmail")
    public void checkEmail(@Pattern(regex = EMAIL_REGEX,message = "email") @RequestParam("email") String email,
                           HttpServletRequest request, HttpServletResponse response) throws Exception {
        boolean hasEmail = userService.hasEmail(email);
        response.getWriter().write(String.valueOf(hasEmail));
    }

    @Override
    @RequestMapping(value = "me")
    public void me(HttpServletRequest request, HttpServletResponse response) throws IOException {
        UserDTO user = UserHolder.getUser();
        String json = JSON.toJSONString(user);
        response.getWriter().write(json);
    }

    @Override
    @RequestMapping(value = "resetPassword")
    public void resetPassword(@Pattern(regex = EMAIL_REGEX,message = "email") @RequestParam("email") String email,
                              @Pattern @RequestParam("code") String emailCode,
                              @Pattern @RequestParam("password") String password,
                              @Pattern @RequestParam("repassword") String rePassword,
                              HttpServletResponse response)
            throws SQLException, IOException, NoSuchAlgorithmException {
        //验证，注册
        String message = userService.resetPassword(email, emailCode, password, rePassword);
        //返回信息
        response.getWriter().write(message);
    }

    @Override
    @RequestMapping
    public void getUser(@Pattern @RequestParam("username") String username, HttpServletResponse response) throws Exception {
        String users=userService.getUser(username);
        response.getWriter().write(users);
    }

    @Override
    @RequestMapping
    public void getUserById(@Pattern(regex = NUMBER_REGEX) @RequestParam("userId") String id, HttpServletResponse response) throws Exception {
        String user=userService.getUserById(id);
        response.getWriter().write(user);
    }

    @Override
    @RequestMapping
    public void updateSettings(@Pattern @RequestParam("introduce") String introduce,
                               @Pattern(regex = EMAIL_REGEX,message = "email") @RequestParam("email") String email,
                               @Pattern @RequestParam("username") String username,
                               @Pattern @RequestParam("gender") String gender,
                               HttpServletRequest request,
                               HttpServletResponse response) throws ServletException, IOException, SQLException {
        String imgPath = ImageUploader.processFileUpload(request);
        userService.updateUserSettings(username,gender,email,introduce,imgPath);
    }
    @Override
    @RequestMapping
    public void getRight(HttpServletResponse response) throws IOException {
        String json=rightService.getRight();
        response.getWriter().write(json);
    }
}
