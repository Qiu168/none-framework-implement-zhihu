package com.huangTaiQi.www.controller.impl;

import com.huangTaiQi.www.constant.enums.MessageType;
import com.huangTaiQi.www.controller.BaseController;
import com.huangTaiQi.www.controller.IDynamicController;
import com.huangTaiQi.www.service.impl.DynamicServiceImpl;
import com.my_framework.www.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static com.huangTaiQi.www.constant.RegexConstants.NUMBER_REGEX;
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
    public void getDynamicQuestion(@Pattern(regex = NUMBER_REGEX) @RequestParam("offset")Integer offset,
                                   @Pattern(regex = NUMBER_REGEX) @RequestParam("max") Long max,
                                   @Pattern(regex = NUMBER_REGEX) @RequestParam("pageSize") Integer pageSize,
                                   HttpServletResponse response) throws Exception {
        String dynamicJson = dynamicService.getDynamic(MessageType.QUESTION, max, offset, pageSize);
        response.getWriter().write(dynamicJson);
    }
    @Override
    @RequestMapping
    public void getDynamicAnswer(@Pattern(regex = NUMBER_REGEX) @RequestParam("offset")Integer offset,
                                 @Pattern(regex = NUMBER_REGEX) @RequestParam("max") Long max,
                                 @Pattern(regex = NUMBER_REGEX) @RequestParam("pageSize") Integer pageSize,
                                 HttpServletResponse response) throws Exception {
        String dynamicJson = dynamicService.getDynamic(MessageType.ANSWER, max, offset, pageSize);
        response.getWriter().write(dynamicJson);
    }
    @Override
    @RequestMapping()
    public void getDynamicTotal(@Pattern @RequestParam("type") String type,HttpServletResponse response) throws IOException {
        Long dynamicCount;
        if(ANSWER.equalsIgnoreCase(type)){
            dynamicCount = dynamicService.getDynamicCount(MessageType.ANSWER);
        }else{
            dynamicCount = dynamicService.getDynamicCount(MessageType.QUESTION);
        }
        response.getWriter().write(String.valueOf(dynamicCount));
    }
}
