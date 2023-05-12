package com.huangTaiQi.www.utils;

import java.io.Serializable;
import java.util.function.Function;

/**
 * Function<T, R> 是 Java 8 中的函数式接口，表示一个接受一个参数并产生一个结果的函数。
 * 其中 T 表示实体类，R 表示实体类的字段类型。
 * @author 14629
 */
public interface SFunction<T, R> extends Function<T, R>, Serializable {
}