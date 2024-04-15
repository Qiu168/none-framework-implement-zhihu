package com.huangTaiQi.www.controller.impl;

import com.alibaba.fastjson.JSON;
import com.huangTaiQi.www.controller.BaseController;
import com.huangTaiQi.www.controller.IBlackListController;
import com.huangTaiQi.www.model.entity.BlackListEntity;
import com.huangTaiQi.www.model.vo.IsSuccessVO;
import com.huangTaiQi.www.service.impl.BlackListServiceImpl;
import com.my_framework.www.core.annotation.bean.Autowired;
import com.my_framework.www.core.annotation.stereotype.Controller;
import com.my_framework.www.webmvc.annotation.Pattern;
import com.my_framework.www.webmvc.annotation.RequestMapping;
import com.my_framework.www.webmvc.annotation.RequestParam;


import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

import static com.huangTaiQi.www.constant.RegexConstants.NUMBER_REGEX;

/**
 * @author _qqiu
 */
@Controller
@RequestMapping("black")
public class BlackListControllerImpl extends BaseController implements IBlackListController {
    @Autowired
    BlackListServiceImpl blackListService;

    @Override
    @RequestMapping
    public void addBlackList(@Pattern(regex = NUMBER_REGEX) @RequestParam("userId") String bid, HttpServletResponse response) throws Exception {
        IsSuccessVO isSuccessVO = blackListService.addBlackList(bid);
        response.getWriter().write(JSON.toJSONString(isSuccessVO));
    }
    @Override
    @RequestMapping
    public void deleteBlackList(@Pattern(regex = NUMBER_REGEX) @RequestParam("userId") String bid,HttpServletResponse response) throws SQLException, IOException {
        blackListService.deleteBlackList(bid);
        response.getWriter().write(JSON.toJSONString(new IsSuccessVO(true,"success")));
    }
    @Override
    @RequestMapping
    public void isBlack(@Pattern(regex = NUMBER_REGEX) @RequestParam("userId") String bid, HttpServletResponse response) throws Exception {
        BlackListEntity blackList = blackListService.selectBlackList(bid);
        response.getWriter().write(JSON.toJSONString(new IsSuccessVO(blackList!=null,"")));
    }
}
