package com.huangTaiQi.www.controller;

import com.my_framework.www.annotation.RequestMapping;

import javax.servlet.http.HttpServlet;


/**
 * @author 14629
 * 空类,只有继承了BaseController才会被请求转发
 */
@RequestMapping("api")
public abstract class BaseController extends HttpServlet {
}
