package com.huangTaiQi.www.controller.impl;

import com.huangTaiQi.www.controller.BaseController;
import com.huangTaiQi.www.controller.IReportController;

import com.huangTaiQi.www.service.impl.ReportServiceImpl;
import com.my_framework.www.annotation.Autowired;
import com.my_framework.www.annotation.Controller;
import com.my_framework.www.annotation.RequestMapping;
import com.my_framework.www.annotation.RequestParam;

import javax.servlet.http.HttpServletResponse;
import java.sql.SQLException;


/**
 * @author 14629
 */
@Controller
@RequestMapping("report")
public class ReportControllerImpl extends BaseController implements IReportController {
    @Autowired
    ReportServiceImpl reportService;
    @RequestMapping(method = "post")
    public void reportMessage(@RequestParam("type") String type, @RequestParam("id") String messageId,@RequestParam("content")String content, HttpServletResponse response) throws SQLException {
        reportService.reportMessage(type,messageId,content);
    }
}
