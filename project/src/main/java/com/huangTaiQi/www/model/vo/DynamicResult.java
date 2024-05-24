package com.huangTaiQi.www.model.vo;

import java.util.List;

/**
 * @author _qqiu
 */
public class DynamicResult {
    private final List<?> list;
    private final Long minTime;
    private final Integer offset;

    public DynamicResult(List<?> list, Long minTime, Integer offset) {
        this.list = list;
        this.minTime = minTime;
        this.offset = offset;
    }

    public List<?> getList() {
        return list;
    }

    public Long getMinTime() {
        return minTime;
    }

    public Integer getOffset() {
        return offset;
    }
}
