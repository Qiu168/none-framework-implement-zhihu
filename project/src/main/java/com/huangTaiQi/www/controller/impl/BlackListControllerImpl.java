package com.huangTaiQi.www.controller.impl;

import com.huangTaiQi.www.controller.IBlackListController;
import com.huangTaiQi.www.service.impl.BlackListServiceImpl;
import com.my_framework.www.annotation.Autowired;
import com.my_framework.www.annotation.Controller;
import com.my_framework.www.annotation.RequestMapping;
import com.my_framework.www.annotation.RequestParam;

import java.sql.SQLException;

/**
 * @author 14629
 */
@Controller
@RequestMapping("black")
public class BlackListControllerImpl implements IBlackListController {
    @Autowired
    BlackListServiceImpl blackListService;

    @RequestMapping
    public void addBlackList(@RequestParam("userId") String bid) throws SQLException {
        blackListService.addBlackList(bid);
    }
    @RequestMapping
    public void deleteBlackList(@RequestParam("userId") String bid) throws SQLException {
        blackListService.deleteBlackList(bid);
    }
}
