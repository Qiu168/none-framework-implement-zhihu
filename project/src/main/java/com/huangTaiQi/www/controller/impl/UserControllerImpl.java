package com.huangTaiQi.www.controller.impl;


import com.alibaba.fastjson.JSON;
import com.huangTaiQi.www.controller.BaseController;
import com.huangTaiQi.www.controller.IUserController;
import com.huangTaiQi.www.model.dto.UserDTO;
import com.huangTaiQi.www.service.impl.UserServiceImpl;
import com.huangTaiQi.www.utils.ImgVerifyCode;
import com.huangTaiQi.www.utils.UserHolder;
import com.my_framework.www.annotation.Autowired;
import com.my_framework.www.annotation.Controller;
import javax.servlet.annotation.WebServlet;
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
@WebServlet("/user/*")
public class UserControllerImpl extends BaseController implements IUserController {

    @Autowired
    UserServiceImpl userService;

    @Override
    public void getImgVerifyCode(HttpServletRequest request, HttpServletResponse response) throws IOException {
        //生成验证码，并将验证码存入session中
        BufferedImage image = userService.imgCode(request.getSession());
        //将验证码图片响应给客户端
        response.setContentType("image/JPEG");
        ImgVerifyCode.output(image,response.getOutputStream());
    }

    @Override
    public void sendEmail(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String email = request.getParameter("email");
        String imgCode = request.getParameter("imgCode");
        //验证邮箱，验证验证码，发送邮件，成功后将邮箱验证码存入redis，返回信息
        String message = userService.sendEmail(email, imgCode, (String) request.getSession().getAttribute(IMG_CODE));
        //返回信息
        response.setContentType("text/html;charset=utf-8");
        response.getWriter().write(message);
    }

    @Override
    public void register(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException, NoSuchAlgorithmException {
        //获取
        String email = request.getParameter("email");
        String emailCode = request.getParameter("code");
        String password = request.getParameter("password");
        String rePassword = request.getParameter("repassword");
        //验证，注册
        String message = userService.register(email, emailCode, password, rePassword);
        response.setContentType("text/html;charset=utf-8");
        //返回信息
        response.setContentType("text/html;charset=utf-8");
        response.getWriter().write(message);
    }

    @Override
    public void login(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String usernameOrEmail = request.getParameter("usernameOrEmail");
        String password = request.getParameter("password");
        String imgCode = request.getParameter("imgCode");
        String code = (String) request.getSession().getAttribute(IMG_CODE);
        String message = userService.login(usernameOrEmail, password, imgCode, code);
        //返回信息
        response.setContentType("text/html;charset=utf-8");
        System.out.println("message="+message);
        response.getWriter().write(message);
    }


    @Override
    public void checkEmail(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String email = request.getParameter("email");
        boolean hasEmail = userService.hasEmail(email);
        response.getWriter().write(String.valueOf(hasEmail));
    }

    @Override
    public void me(HttpServletRequest request, HttpServletResponse response) throws IOException {
        UserDTO user = UserHolder.getUser();
        String json = JSON.toJSONString(user);
        response.setContentType("text/json;charset=utf-8");
        response.getWriter().write(json);
    }


}
