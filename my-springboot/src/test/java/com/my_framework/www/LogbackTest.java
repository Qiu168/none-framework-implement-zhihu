package com.my_framework.www;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

@Slf4j
public class LogbackTest {
    @Test
    public void testLogbackAndLombok(){
        if (log.isDebugEnabled()) {
            log.debug("输出DEBUG级别日志");
        }
        log.info("输出INFO级别日志");
        log.warn("输出WARN级别日志");
        log.error("输出ERROR级别日志");
    }
}
