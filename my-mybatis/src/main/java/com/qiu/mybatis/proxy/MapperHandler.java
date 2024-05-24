package com.qiu.mybatis.proxy;

import com.qiu.mybatis.annotation.Select;
import com.qiu.mybatis.annotation.Update;
import com.qiu.mybatis.plus.BaseMapper;
import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.Arrays;

/**
 * @author 14629
 */
@Slf4j
public class MapperHandler implements InvocationHandler {

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        if (Object.class.equals(method.getDeclaringClass())) {
            return method.invoke(this, args);
        }
        if(method.getDeclaringClass().equals(BaseMapper.class)){
            log.info("baseMapper");
            return 1;
        }
        Select select = method.getAnnotation(Select.class);
        if(select!=null){
            return doSelect(select.value(),args);
        }
        Update update = method.getAnnotation(Update.class);
        if(update!=null){
            return doUpdate(update.value(),args);
        }

        return null;
    }

    private Object doUpdate(String[] value, Object[] args) {
        return doSelect(value,args);
    }

    private Object doSelect(String[] value, Object[] args) {
        log.info(Arrays.toString(value));
        log.info(Arrays.toString(args));
        return null;
    }
}
