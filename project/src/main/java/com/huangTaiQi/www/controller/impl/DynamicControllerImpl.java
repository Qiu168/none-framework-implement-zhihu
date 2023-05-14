package com.huangTaiQi.www.controller.impl;

import com.huangTaiQi.www.constant.enums.MessageType;
import com.huangTaiQi.www.controller.BaseController;
import com.huangTaiQi.www.controller.IDynamicController;
import com.huangTaiQi.www.service.impl.DynamicServiceImpl;
import com.my_framework.www.annotation.Autowired;
import com.my_framework.www.annotation.Controller;
import com.my_framework.www.annotation.RequestMapping;
import com.my_framework.www.annotation.RequestParam;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static com.huangTaiQi.www.constant.TypeConstants.ANSWER;

/**
 * @author 14629
 */
@Controller
@RequestMapping("dynamic")
public class DynamicControllerImpl extends BaseController implements IDynamicController {
    @Autowired
    DynamicServiceImpl dynamicService;
    @Override
    @RequestMapping
    public void getDynamicQuestion(@RequestParam("offset")Integer offset,@RequestParam("max") Long max, @RequestParam("pageSize") Integer pageSize, HttpServletResponse response) throws Exception {
        String dynamicJson = dynamicService.getDynamic(MessageType.QUESTION, max, offset, pageSize);
        response.getWriter().write(dynamicJson);
    }
    @Override
    @RequestMapping
    public void getDynamicAnswer(@RequestParam("offset")Integer offset,@RequestParam("max") Long max,@RequestParam("pageSize") Integer pageSize,HttpServletResponse response) throws Exception {
        String dynamicJson = dynamicService.getDynamic(MessageType.ANSWER, max, offset, pageSize);
        response.getWriter().write(dynamicJson);
    }
    @Override
    @RequestMapping()
    public void getDynamicTotal(@RequestParam("type") String type,HttpServletResponse response) throws IOException {
        Long dynamicCount;
        if(ANSWER.equalsIgnoreCase(type)){
            dynamicCount = dynamicService.getDynamicCount(MessageType.ANSWER);
        }else{
            dynamicCount = dynamicService.getDynamicCount(MessageType.QUESTION);
        }
        response.getWriter().write(String.valueOf(dynamicCount));
    }
}
