package com.my_framework.www.db.orm;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

/**
 * @author _qqiu
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class MapperConfiguration {
    // xxMapper.xml
    private Map<String , MapperStatement> mapperStatement;
}

