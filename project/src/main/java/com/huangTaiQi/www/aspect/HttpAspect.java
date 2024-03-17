package com.huangTaiQi.www.aspect;

import com.my_framework.www.core.annotation.aop.Around;
import com.my_framework.www.core.annotation.aop.Aspect;
import com.my_framework.www.core.annotation.stereotype.Component;
import com.my_framework.www.core.aop.ProceedingJoinPoint;
import lombok.extern.slf4j.Slf4j;

/**
 * @author _qiu
 */
@Aspect
@Slf4j
@Component
public class HttpAspect {
    @Around("@within(org.springframework.stereotype.Controller) || @within(org.springframework.web.bind.annotation.RestController)")
    public Object around(ProceedingJoinPoint point) throws Throwable{
        // 获取类名
        String className = point.getTarget().getClass().getName();
        // 获取方法
        String methodName = point.getMethod().getName();
        // 记录开始时间
        long beginTime = System.currentTimeMillis();
        // 记录返回结果
        Object result = null;
        Exception ex = null;
        try {
            // 执行方法
            result = point.proceed();
            return result;
        } catch (Exception e) {
            ex = e;
            throw e;
        } finally {
            // 计算消耗时间
            long costTime = System.currentTimeMillis() - beginTime;
            // 发生异常，则打印 ERROR 日志
            if (ex != null) {
                log.error("[className: {}][methodName: {}][cost: {} ms][args: {}][发生异常: {}]",
                        className, methodName,costTime, point.getArguments(), ex.getMessage());
                // 正常执行，则打印 INFO 日志
            } else {
                log.info("[className: {}][methodName: {}][cost: {} ms][args: {}][return: {}]",
                        className, methodName, costTime, point.getArguments(), result);
            }
        }
    }
}
