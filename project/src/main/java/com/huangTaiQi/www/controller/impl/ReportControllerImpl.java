package com.huangTaiQi.www.controller.impl;

import com.huangTaiQi.www.controller.BaseController;
import com.huangTaiQi.www.controller.IReportController;

import com.huangTaiQi.www.service.impl.ReportServiceImpl;
import com.my_framework.www.annotation.*;

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
    @Override
    @RequestMapping(method = "post")
    public void reportMessage(@Pattern @RequestParam("type") String type,
                              @Pattern @RequestParam("id") String messageId,
                              @Pattern @RequestParam("content") String content,
                              HttpServletResponse response) throws SQLException {
        reportService.reportMessage(type,messageId,content);
    }
}
