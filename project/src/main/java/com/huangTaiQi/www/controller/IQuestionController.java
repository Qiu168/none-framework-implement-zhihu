package com.huangTaiQi.www.controller;

import com.my_framework.www.annotation.RequestMapping;
import com.my_framework.www.annotation.RequestParam;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author 14629
 */
public interface IQuestionController {

    void getQuestionCount(HttpServletResponse response) throws Exception;


    void getQuestionCountByTitle(String title, HttpServletResponse response) throws Exception;


    void getQuestionCountByCategory(String categoryId, HttpServletResponse response) throws Exception;

    void getQuestion(int page, int size, HttpServletResponse response) throws Exception;

    void getQuestionByTitle( String title, int page,int size,HttpServletResponse response) throws Exception;

    void getQuestionByCategory( String categoryId,int page,int size, HttpServletResponse response) throws Exception;


    void getQuestionByUser(Long userId, int page, int size, HttpServletResponse response) throws Exception;


    void getCategory(HttpServletResponse response) throws Exception;
}
