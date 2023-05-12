package com.huangTaiQi.www.controller.impl;

import com.alibaba.fastjson.JSON;
import com.huangTaiQi.www.controller.BaseController;
import com.huangTaiQi.www.controller.IBlackListController;
import com.huangTaiQi.www.model.vo.IsSuccessVO;
import com.huangTaiQi.www.service.impl.BlackListServiceImpl;
import com.huangTaiQi.www.utils.UserHolder;
import com.my_framework.www.annotation.Autowired;
import com.my_framework.www.annotation.Controller;
import com.my_framework.www.annotation.RequestMapping;
import com.my_framework.www.annotation.RequestParam;
import com.my_framework.www.utils.CastUtil;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

/**
 * @author 14629
 */
@Controller
@RequestMapping("black")
public class BlackListControllerImpl extends BaseController implements IBlackListController {
    @Autowired
    BlackListServiceImpl blackListService;

    @Override
    @RequestMapping
    public void addBlackList(@RequestParam("userId") String bid, HttpServletResponse response) throws SQLException, IOException {
        if(CastUtil.castLong(bid) == UserHolder.getUser().getId()){
            response.getWriter().write(JSON.toJSONString(new IsSuccessVO(false,"不能拉黑自己")));
        }
        blackListService.addBlackList(bid);
    }
    @Override
    @RequestMapping
    public void deleteBlackList(@RequestParam("userId") String bid) throws SQLException {
        blackListService.deleteBlackList(bid);
    }

    @RequestMapping
    public void isBlack(@RequestParam("userId") String bid, HttpServletResponse response) throws Exception {
        String blackList = blackListService.isBlackList(bid);
        response.getWriter().write(blackList);
    }
}
