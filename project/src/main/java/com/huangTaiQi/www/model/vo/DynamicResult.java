package com.huangTaiQi.www.model.vo;

import java.util.List;

/**
 * @author 14629
 */
public class DynamicResult {
    private List<?> list;
    private Long minTime;
    private Integer offset;

    public DynamicResult(List<?> list, Long minTime, Integer offset) {
        this.list = list;
        this.minTime = minTime;
        this.offset = offset;
    }
}
