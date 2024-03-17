package com.my_framework.www.mybatis;

import com.my_framework.www.db.annotation.Mapper;

@Mapper
public interface UUserInfoMapper {
    UUserInfo selectByPrimaryKey(Integer id);
}
