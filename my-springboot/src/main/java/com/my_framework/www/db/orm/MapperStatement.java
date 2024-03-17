package com.my_framework.www.db.orm;
import lombok.Data;

/**
 * 解析每一个mapper
 * @author _qiu
 */
@Data
public class MapperStatement {

    private String namespace;

    private String id;

    private String parameterType;

    private String resultType;

    private String sql;
}
