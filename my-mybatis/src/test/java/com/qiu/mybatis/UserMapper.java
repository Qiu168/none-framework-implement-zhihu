package com.qiu.mybatis;

import com.qiu.mybatis.annotation.Select;
import com.qiu.mybatis.plus.BaseMapper;

import java.util.List;
import java.util.concurrent.Callable;

public interface UserMapper extends BaseMapper<User>, Callable {
    @Select("select id from user where username=?")
    List<Integer> selectUserId(String username);
}
